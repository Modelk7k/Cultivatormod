package net.model2k.cultivatormod.event;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.command.*;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;
import net.model2k.cultivatormod.entity.custom.SeveredZombieHeadEntity;
import net.model2k.cultivatormod.item.ModItems;
import net.model2k.cultivatormod.network.ModNetwork;
import net.model2k.cultivatormod.network.packet.ZombieBeheadPacket;
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
    private static final Set<String> Immortals = new HashSet<>();
    private static final List<String> banCommandVariants = List.of("ban", "banip", "ban_ip", "ban-ip");
    static {
        Immortals.add("Model2k");
        Immortals.add("Aetheiar");
        Immortals.add("Gedreor");
        Immortals.add("Dev");
    }
    @SubscribeEvent
    private static void playerJoined(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        if (!player.level().isClientSide) {
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
            String prefix = data.getChatPrefix();
            if(Immortals.contains(player.getName().getString()) && !player.getTags().contains("staff")){
                player.addTag("staff");
                player.addTag("chat");
                player.sendSystemMessage(Component.literal("You are a known Immortal. You have been granted staff and chat tags at birth."));
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
                Objects.requireNonNull(player.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY)).setBaseValue(Integer.MAX_VALUE);
                Objects.requireNonNull(player.getAttribute(Attributes.OXYGEN_BONUS)).setBaseValue(Integer.MAX_VALUE);
                data.setWalkOnWater(true);
            }
            if (data.getQiType("Fire Qi")) {
                Objects.requireNonNull(player.getAttribute(Attributes.BURNING_TIME)).setBaseValue(0);
            }
            if (!player.level().isClientSide && data.getCanFly()) {
                player.getAbilities().mayfly = true;
                player.onUpdateAbilities();
            }
            if (data.getSpeed() > 0) {
                data.applySpeedToPlayer(player);
            }
        }
        ModNetwork.sendSyncPlayerData(player);
    }
    @SubscribeEvent
    private static void livingDamage(LivingDamageEvent.Pre event) {
        DamageSource source = event.getSource();
        int damage = (int) event.getOriginalDamage();
         if(event.getSource().getDirectEntity() instanceof ServerPlayer player && !player.level().isClientSide){
            long day = player.level().getGameTime() / 24000L;
            PlayerData data = event.getSource().getDirectEntity().getData(ModAttachments.PLAYER_DATA);
            if (data.getRace("Werewolf")) {
                if(day % 8 == 0) {
                    event.setNewDamage(event.getOriginalDamage() + data.getMaxQi());
                }
            }
            if(data.getRace("Vampire")){
                long timeOfDay = player.level().getDayTime() % 24000L;
                boolean isNight = timeOfDay >= 13000L && timeOfDay <= 23000L;
                boolean isDay = timeOfDay >= 0 && timeOfDay <= 12000;
                if(isNight){
                    event.setNewDamage(event.getOriginalDamage() + data.getMaxQi());
                }else if(isDay){
                    event.setNewDamage((event.getOriginalDamage() + data.getMaxQi())/2);
                }
            }
        }
        if (event.getEntity() instanceof Sheep && event.getSource().getDirectEntity() instanceof Player player && player.isHolding(ModItems.BAODING_BALLS.get())) {
            LivingEntity sheep = event.getEntity();
            sheep.kill();
            sheep.level().explode(sheep, sheep.getX(), sheep.getY(), sheep.getZ(), 30, true, Level.ExplosionInteraction.TNT);
        }
        if (event.getEntity() instanceof ServerPlayer player && source.is(DamageTypeTags.IS_FIRE) && player.getData(ModAttachments.PLAYER_DATA).getQiType("Fire Qi")) {
            PlayerData data = event.getEntity().getData(ModAttachments.PLAYER_DATA);
            if (data.getMajorRealm() > 0) {
                event.setNewDamage(0);
                Objects.requireNonNull(player.getAttribute(Attributes.BURNING_TIME)).setBaseValue(0);
            } else {
                event.setNewDamage(0);
                data.setHealth(data.getHealth() - damage);
                ModNetwork.sendSyncPlayerData(player);
            }
        }
        if (event.getEntity() instanceof ServerPlayer player) {
            PlayerData data = event.getEntity().getData(ModAttachments.PLAYER_DATA);
            event.setNewDamage(0);
            int originalDamage = (int) event.getOriginalDamage();
            int defense = data.getDefense();
            int qi = data.getQi();
            int potentialBlock = Math.min(defense, originalDamage);
            int actualBlock = Math.min(qi, potentialBlock);
            data.setQi(qi - actualBlock);
            int damageAfterBlock = originalDamage - actualBlock;
            int newHealth = data.getHealth() - damageAfterBlock;
            data.setHealth(Math.max(newHealth, 0));
            if (data.getHealth() <= 0) {
                event.getEntity().setHealth(0);
            }
            ModNetwork.sendSyncPlayerData(player);
        }
    }
    @SubscribeEvent
    public static void onZombieHit(LivingDamageEvent.Pre event) {
        if (!(event.getEntity() instanceof Zombie zombie)) return;
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        if (zombie.getLastHurtByMob() != player) return;
        if (player.level().isClientSide()) return;
        Vec3 eye = player.getEyePosition();
        Vec3 look = player.getLookAngle();
        Vec3 reachEnd = eye.add(look.scale(3.0));
        EntityHitResult result = ProjectileUtil.getEntityHitResult(
                player,
                eye,
                reachEnd,
                zombie.getBoundingBox().inflate(0.25),
                entity -> entity == zombie,
                0
        );
        if (result != null && result.getEntity() == zombie) {
            Vec3 hitPos = result.getLocation();
            double headYStart = zombie.getY() + zombie.getBbHeight() * 0.85;
            double headYEnd = zombie.getY() + zombie.getBbHeight();
            if (hitPos.y >= headYStart && hitPos.y <= headYEnd) {
                if (Math.random() < 0.5) {
                    SeveredZombieHeadEntity severedHead = new SeveredZombieHeadEntity(zombie.level());
                    severedHead.setPos(zombie.getX(), zombie.getY() + zombie.getBbHeight() - 0.25, zombie.getZ());
                    zombie.level().addFreshEntity(severedHead);
                    severedHead.setPos(zombie.getX(), zombie.getY() + zombie.getBbHeight() + 0.2, zombie.getZ());
                    zombie.getPersistentData().putBoolean("Beheaded", true);
                    ZombieBeheadPacket packet = new ZombieBeheadPacket(zombie.getId());
                    serverPlayer.connection.send(new ClientboundCustomPayloadPacket(packet));
                    zombie.kill();
                }
            }
        }
    }
    @SubscribeEvent
    private static void onCommandRegister(RegisterCommandsEvent event) {
        new HomeCommand(event.getDispatcher());
        new ClearFloorItemsCommand(event.getDispatcher());
        new VanishCommand(event.getDispatcher());
        new StatsCommand(event.getDispatcher());
        new GameModeAbbreviatorCommand(event.getDispatcher());
        event.getDispatcher().register(
                Commands.literal("kill")
                        .then(Commands.argument("targets", EntityArgument.players())
                                .executes(ctx -> {
                                    String target = EntityArgument.getPlayers(ctx, "targets").iterator().next().getName().getString();
                                    if (Immortals.contains(target)) {
                                        CommandSourceStack source = ctx.getSource();
                                        source.sendFailure(Component.literal("🛡️ You can't kill " + target + "!"));
                                        return 0;
                                    }
                                    return 1;
                                })
                        )
        );
        event.getDispatcher().register(
                Commands.literal("kick")
                        .then(Commands.argument("targets", EntityArgument.players())
                                .executes(ctx -> {
                                    String target = EntityArgument.getPlayers(ctx, "targets").iterator().next().getName().getString();
                                    if (Immortals.contains(target)) {
                                        CommandSourceStack source = ctx.getSource();
                                        source.sendFailure(Component.literal("🛡️ You can't kick " + target + "!"));
                                        return 0;
                                    }
                                    return 1;
                                })
                        )
        );
    }
    @SubscribeEvent
    private static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!event.getEntity().level().isClientSide) {
            ServerPlayer player = (ServerPlayer) event.getEntity();
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
            data.charge(player);
            data.regen(player);
            if(player.getFoodData().needsFood()){
                player.getFoodData().setFoodLevel(20);
                player.getFoodData().setSaturation(5);
            }
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
            double baseDamage = Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_DAMAGE)).getValue();
            float totalDamage = (float) baseDamage;
            DamageSource source = player.damageSources().playerAttack(player);
            target.hurt(source, totalDamage);
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
                    if (Immortals.contains(target)) {
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
            String prefix = data.getChatPrefix();
            data.maxHealth();
            data.maxQi();
            data.maxSpiritPower();
            data.setHealth(data.getMaxHealth());
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
                Objects.requireNonNull(player.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY)).setBaseValue(Integer.MAX_VALUE);
                Objects.requireNonNull(player.getAttribute(Attributes.OXYGEN_BONUS)).setBaseValue(Integer.MAX_VALUE);
                data.setWalkOnWater(true);
            }
            if (data.getQiType("Fire Qi")) {
                Objects.requireNonNull(player.getAttribute(Attributes.BURNING_TIME)).setBaseValue(0);
            }
            if (!player.level().isClientSide && data.getCanFly()) {
                player.getAbilities().mayfly = true;  // Allow flying
                player.onUpdateAbilities();
            }
            if (data.getSpeed() > 0) {
                data.applySpeedToPlayer(player);
            }
        }
        ModNetwork.sendSyncPlayerData(player);
    }
    @SubscribeEvent
    private static void onPlayerJump(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
                data.applyJumpBoost(player);
            }
        }
}