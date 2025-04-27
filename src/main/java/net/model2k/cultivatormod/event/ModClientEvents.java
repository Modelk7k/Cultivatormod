package net.model2k.cultivatormod.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;
import net.model2k.cultivatormod.item.ModItems;
import net.model2k.cultivatormod.network.packet.*;
import net.model2k.cultivatormod.util.ModKeyBinds;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;


@EventBusSubscriber(modid = CultivatorMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ModClientEvents {
    private static int lastExperienceLevel = 0;
    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        int screenHeight = mc.getWindow().getGuiScaledHeight();
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int barWidth = 180;
        int barHeight = 8;
        int spacing = 2;
        // Hotbar metrics
        int hotbarHeight = 22;
        int hotbarOffset = 4;
        // Positioning base: stack above hotbar
        int bottomOfHotbarY = screenHeight - hotbarOffset;
        int topOfHotbarY = bottomOfHotbarY - hotbarHeight;
        // Stack bars in correct order: Spirit on top, Qi in middle, Health at the bottom
        int healthBarY = topOfHotbarY - barHeight - spacing; // Health at the bottom
        int qiBarY = healthBarY - barHeight - spacing;       // Qi in the middle
        int spiritBarY = qiBarY - barHeight - spacing;       // Spirit at the top
        int barX = (screenWidth - barWidth) / 2;
        // === Health Bar (Bottom) ===
        float health = mc.player.getHealth();
        float maxHealth = mc.player.getMaxHealth();
        float healthRatio = Math.min(health / maxHealth, 1.0f);
        int healthFillWidth = (int) (barWidth * healthRatio);
        event.getGuiGraphics().fill(barX, healthBarY, barX + barWidth, healthBarY + barHeight, 0xFF333333);
        event.getGuiGraphics().fill(barX, healthBarY, barX + healthFillWidth, healthBarY + barHeight, 0xFFFF0000);
        // === Qi Bar (Middle) ===
        int qi = PlayerStatsClient.getQi();
        int maxQi = PlayerStatsClient.getMaxQi();
        float qiRatio = Math.min((float) qi / maxQi, 1.0f);
        int qiFillWidth = (int) (barWidth * qiRatio);
        event.getGuiGraphics().fill(barX, qiBarY, barX + barWidth, qiBarY + barHeight, 0xFF333333);
        event.getGuiGraphics().fill(barX, qiBarY, barX + qiFillWidth, qiBarY + barHeight, 0xFFFFFF00);
        // === Spirit Bar (Top) ===
        int spirit = PlayerStatsClient.getSpiritPower();
        int maxSpirit = PlayerStatsClient.getMaxSpiritPower();
        float spiritRatio = Math.min((float) spirit / maxSpirit, 1.0f);
        int spiritFillWidth = (int) (barWidth * spiritRatio);
        event.getGuiGraphics().fill(barX, spiritBarY, barX + barWidth, spiritBarY + barHeight, 0xFF333333);
        event.getGuiGraphics().fill(barX, spiritBarY, barX + spiritFillWidth, spiritBarY + barHeight, 0xFF00FFFF);
        // === Experience Level (Left of Health Bar) ===
        int experienceLevel = lastExperienceLevel;
        if(lastExperienceLevel == 0) {
            experienceLevel = mc.player.experienceLevel;
        }
        int experienceLevelX = barX - 15;
        int experienceLevelY = healthBarY + (barHeight / 2) - 4;
        Component text = Component.literal("" + experienceLevel)
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x00FF00)));
        Font font = Minecraft.getInstance().font;
        event.getGuiGraphics().drawString(font, text, experienceLevelX, experienceLevelY, 0x00FF00);
    }
    @SubscribeEvent
    public static void onXpChange(PlayerXpEvent.XpChange event) {
        event.getEntity();
        Player player = event.getEntity();
        lastExperienceLevel = player.experienceLevel;
    }
    @SubscribeEvent
    public static void onRenderHealthBar(RenderGuiLayerEvent.Pre event) {
        if (event.getName().toString().equals("minecraft:player_health")) {
            event.setCanceled(true);
        }
        if(event.getName().toString().equals("minecraft:food_level")){
            event.setCanceled(true);
        }
        if(event.getName().toString().equals("minecraft:experience_bar")){
            event.setCanceled(true);
        }
        if(event.getName().toString().equals("minecraft:experience_level")){
            event.setCanceled(true);
        }
        if(event.getName().toString().equals("minecraft:air_level")){
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;
        if (mc.player.getPersistentData().contains("qiSlashCooldown")) {
            int cooldown = mc.player.getPersistentData().getInt("qiSlashCooldown");
            if (cooldown > 0) {
                mc.player.getPersistentData().putInt("qiSlashCooldown", cooldown - 1);
            }
        } else {
            mc.player.getPersistentData().putInt("qiSlashCooldown", 0);  // Initialize the counter
        }
        if (ModKeyBinds.QI_SLASH_KEY.consumeClick()) {
            int cooldown = mc.player.getPersistentData().getInt("qiSlashCooldown");
            if (cooldown == 0) {
                Vec3 direction = mc.player.getLookAngle();
                QiSlashPacket packet = new QiSlashPacket(direction);
                ClientPacketListener connection = mc.getConnection();
                if (connection != null) {
                    connection.send(packet);
                    System.out.println("Sent QiSlashPacket to server");
                }
                mc.player.getPersistentData().putInt("qiSlashCooldown", 20);
            } else {
                System.out.println("QiSlash on cooldown: " + cooldown + " ticks remaining.");
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (!(player instanceof LocalPlayer)) return;
        handleWaterWalking(player);
    }
    @SubscribeEvent
    public static void onFovUpdate(ComputeFovModifierEvent event) {
        Player player = event.getPlayer();
        if (player != null) {
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
            if (data != null) {
                // Lock FOV to default value and block speed-based FOV changes
                event.setNewFovModifier(Minecraft.getInstance().options.fov().get().floatValue());
            }
        }
    }
    @SubscribeEvent
    public static void inputEvent(InputEvent.Key event) {
        Player player = Minecraft.getInstance().player;
        if (player == null || !player.level().isClientSide()) return;
        if (ModKeyBinds.SPEED_TOGGLE_KEY.consumeClick()) {
            ClientPacketListener connection = Minecraft.getInstance().getConnection();
            if (connection != null) {
                connection.send(new ServerboundCustomPayloadPacket(SpeedPacket.INSTANCE));
            }
        }
        if (ModKeyBinds.JUMP_STRENGTH_TOGGLE_KEY.consumeClick()) {
            ClientPacketListener connection = Minecraft.getInstance().getConnection();
            if (connection != null) {
                connection.send(new ServerboundCustomPayloadPacket(JumpPacket.INSTANCE));
            }
        }
        if (ModKeyBinds.DASH_KEY.consumeClick() && PlayerStatsClient.getCanDash()) {
            ClientPacketListener connection = Minecraft.getInstance().getConnection();
            if (connection != null) {
                connection.send(new ServerboundCustomPayloadPacket(DashPacket.INSTANCE));            }
        }
    }
    private static void handleWaterWalking(Player player) {
        if (!PlayerStatsClient.getWalkOnWater()) return;
        if (player.isShiftKeyDown() && !player.isHolding(ModItems.BAODING_BALLS.get())) return;
        Vec3 feet = player.position();
        double velocityY = player.getDeltaMovement().y;
        double checkDistance = Math.max(1.5, -velocityY * 2); // Increase depth based on falling speed
        Vec3 down = feet.add(0, -checkDistance, 0);
        var result = player.level().clip(new net.minecraft.world.level.ClipContext(
                feet,
                down,
                net.minecraft.world.level.ClipContext.Block.OUTLINE,
                net.minecraft.world.level.ClipContext.Fluid.SOURCE_ONLY,
                player
        ));
        if (result.getType() == net.minecraft.world.phys.HitResult.Type.BLOCK) {
            BlockPos pos = result.getBlockPos();
            var fluid = player.level().getFluidState(pos);
            if (fluid.getType() == Fluids.WATER && !player.isInWater() || fluid.getType() == Fluids.LAVA && !player.isInLava()) {
                Vec3 velocity = player.getDeltaMovement();
                if (velocity.y < -0.05) {
                    player.setDeltaMovement(velocity.x, 0, velocity.z);
                    player.fallDistance = 0f;
                    player.setOnGround(true);
                    double surfaceY = pos.getY() + 1.0;
                    player.setPos(player.getX(), surfaceY, player.getZ());
                }
            }
        }
    }
}