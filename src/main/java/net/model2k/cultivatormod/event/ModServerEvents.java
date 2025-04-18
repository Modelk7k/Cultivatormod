package net.model2k.cultivatormod.event;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.command.*;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;
import net.model2k.cultivatormod.entity.custom.QiSlashEntity;
import net.model2k.cultivatormod.item.ModItems;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.UUID;

@EventBusSubscriber(modid = CultivatorMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModServerEvents {
    private static final UUID LIGHTNING_STAFF_REACH_UUID = UUID.fromString("de305d54-75b4-431b-adb2-eb6b9e546014");

    @SubscribeEvent
    public static void playerJoined(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level().isClientSide) {
            PlayerData data = event.getEntity().getData(ModAttachments.PLAYER_DATA);
            data.syncQiToClient((Player)event.getEntity());
            if (!data.getFirstQiType()) {
                data.setFirstQiType(true);
                Random rand = new Random();
                int value = rand.nextInt(4);
                switch (value) {
                    case 0:
                        data.setDemonQi(true);
                        event.getEntity().sendSystemMessage(Component.literal("You start with Demon Qi"));
                        break;
                    case 1:
                        data.setHeavenlyQi(true);
                        event.getEntity().sendSystemMessage(Component.literal("You start with Heavenly Qi"));
                        break;
                    case 2:
                        data.setYangQi(true);
                        event.getEntity().sendSystemMessage(Component.literal("You start with Yang Qi"));
                        break;
                    case 3:
                        data.setYinQi(true);
                        event.getEntity().sendSystemMessage(Component.literal("You start with Yin Qi"));
                        break;
                }
            }
            if (!event.getEntity().level().isClientSide() && event.getEntity().getData(ModAttachments.PLAYER_DATA).getCanFly()) {
                Player player = event.getEntity();
                player.getAbilities().mayfly = true;  // Allow flying
                player.onUpdateAbilities();
            }
        }
    }

    @SubscribeEvent
    public static void livingDamage(LivingDamageEvent.Pre event) {
        Entity sheep = null;
        if (event.getEntity() instanceof Sheep && event.getSource().getDirectEntity() instanceof Player player && player.isHolding(ModItems.BAODING_BALLS.get())) {
            sheep = event.getEntity();
            sheep.kill();
            sheep.level().explode(sheep, sheep.getX(), sheep.getY(), sheep.getZ(), 30, true, Level.ExplosionInteraction.TNT);
        }
        if (event.getEntity() instanceof Player player) {
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
            float qi = data.getMaxQi();
            float spirit = data.getMaxSpiritPower();
            float defensePower = qi + spirit;
            float rawReduction = (float) (1 - (1 / (1 + defensePower * 0.0015))); // tweak this multiplier!
            float maxReduction = 0.8f;
            float reductionPercent = Math.min(rawReduction, maxReduction);
            float originalDamage = event.getOriginalDamage();
            float reducedDamage = originalDamage * (1.0f - reductionPercent);
            event.setNewDamage(reducedDamage);
            // player.sendSystemMessage(Component.literal("Damage reduced by " + Math.round(reductionPercent * 100) + "% (" + (originalDamage - reducedDamage) + ")"));
        }
    }

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        new SetStrengthCommand(event.getDispatcher());
        new SetMaxQiCommand(event.getDispatcher());
        new SetQiCommand(event.getDispatcher());
        new SetHomeCommand(event.getDispatcher());
        new ReturnHomeCommand(event.getDispatcher());
        new SetSpiritPowerCommand(event.getDispatcher());
        new SetMaxSpiritPowerCommand(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player != null && !player.level().isClientSide) {
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
            data.charge(player);
        }
    }
    @SubscribeEvent
    public static void onPlayerDealDamage(LivingDamageEvent.Pre event) {
        if (event.getSource().getDirectEntity() instanceof Player player && !player.level().isClientSide) {
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
            float strengthMulitplier = data.getStrength() * 1.1f;
            event.setNewDamage(event.getOriginalDamage() + strengthMulitplier);
            player.sendSystemMessage(Component.literal("Damage: " + event.getNewDamage()));
        }
    }

    @SubscribeEvent
    public static void onAttributeModifiers(ItemAttributeModifierEvent event) {
        if (!event.getItemStack().is(ModItems.LIGHTNING_STAFF.get())) return;
        AttributeModifier reachModifier = new AttributeModifier(
                ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "lightning_staff_reach_boost"),
                50f,
                AttributeModifier.Operation.ADD_VALUE
        );
        event.addModifier(Attributes.BLOCK_INTERACTION_RANGE, reachModifier, EquipmentSlotGroup.MAINHAND);
    }

    @SubscribeEvent
    public static void onPlayerChat(ServerChatEvent event) {
        ServerPlayer sender = event.getPlayer();
        String message = event.getRawText();
        if (message.startsWith("!staff ")) {
            if (sender.getTags().contains("staff")) {
                String staffMsg = message.substring(7);
                System.out.println("[STAFF CHAT] " + sender.getName().getString() + ": " + staffMsg);
                for (ServerPlayer player : sender.server.getPlayerList().getPlayers()) {
                    if (player.getTags().contains("staff")) {
                        player.sendSystemMessage(Component.literal("[STAFF] " + sender.getName().getString() + ": " + staffMsg));
                    }
                }
            } else {
                sender.sendSystemMessage(Component.literal("You are not staff."));
            }
            // Cancel normal chat broadcasting
            event.setCanceled(true);
            // This should log to the console correctly
        }
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
       Player player = event.getEntity();

       if (!player.level().isClientSide && event.getTarget() instanceof LivingEntity target) {
           event.setCanceled(true); // Cancel vanilla damage handling

           // Base attack damage from player attributes
           double baseDamage = player.getAttribute(Attributes.ATTACK_DAMAGE).getValue();

           // Modern NeoForge: get enchantment bonus using a static helper


           float totalDamage = (float) baseDamage;

           // Build damage source
           DamageSource source = player.damageSources().playerAttack(player);

           // Apply the damage manually
           target.hurt(source, totalDamage);

           // Reset animation + cooldown visuals (optional)
           player.resetAttackStrengthTicker();
           player.swing(player.getUsedItemHand(), true);
       }
   }
}
