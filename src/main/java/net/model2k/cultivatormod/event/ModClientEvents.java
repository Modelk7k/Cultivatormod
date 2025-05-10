package net.model2k.cultivatormod.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
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
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.joml.Matrix4f;
import java.util.List;

@EventBusSubscriber(modid = CultivatorMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        if (!PlayerStatsClient.isHasSynced()) return;
        Minecraft mc = Minecraft.getInstance();
        GuiGraphics gui = event.getGuiGraphics();
        int screenHeight = mc.getWindow().getGuiScaledHeight();
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int barWidth = 180;
        int barHeight = 8;
        int spacing = 2;
        // Hotbar metrics
        int hotbarHeight = 22;
        int hotbarOffset = 4;
        int bottomOfHotbarY = screenHeight - hotbarOffset;
        int topOfHotbarY = bottomOfHotbarY - hotbarHeight;
        int healthBarY = topOfHotbarY - barHeight - spacing;
        int qiBarY = healthBarY - barHeight - spacing;
        int spiritBarY = qiBarY - barHeight - spacing;
        int barX = (screenWidth - barWidth) / 2;
        displayLookedAtInfo(mc, event.getGuiGraphics().pose(), screenWidth, screenHeight);
        if(mc.player.isCreative()){return;}
        // === Health Bar (Bottom) ===
        float health = PlayerStatsClient.getHealth() < 1 ? 20 : PlayerStatsClient.getHealth();
        float maxHealth = PlayerStatsClient.getMaxHealth() < 1 ? 20 : PlayerStatsClient.getMaxHealth();
        float healthRatio = maxHealth > 0 ? Math.min(health / maxHealth, 1.0f) : 0.0f;
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
        int experienceLevel = mc.player.experienceLevel;
        int experienceLevelX = barX - 20;
        int experienceLevelY = healthBarY + (barHeight / 2) - 4;
        Component text = Component.literal("" + experienceLevel).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x00FF00)));
        Font font = Minecraft.getInstance().font;
        event.getGuiGraphics().drawString(font, text, experienceLevelX, experienceLevelY, 0x00FF00);
        // === Breathe Bar ===
        float breathe = mc.player.getAirSupply();
        if(mc.player.isUnderWater() || breathe != 300) {
            float maxBreathe = 300.0f;
            float breatheRatio = Math.min(breathe / maxBreathe, 1.0f);
            int breatheBarWidth = 8;
            int breatheBarHeight = 20;
            int breatheFillHeight = (int) (breatheRatio * breatheBarHeight);
            int customXPTextY = healthBarY + barHeight / 2;
            int breatheBarX = (screenWidth / 2 - breatheBarWidth / 2) + 99;
            int breatheBarY = customXPTextY + 10;
            breatheBarY = Math.min(breatheBarY, screenHeight - breatheBarHeight);
            breatheBarX = Math.min(breatheBarX, screenWidth - breatheBarWidth);
            breatheBarY = Math.max(breatheBarY, 0);
            breatheBarX = Math.max(breatheBarX, 0);
            event.getGuiGraphics().fill(breatheBarX, breatheBarY, breatheBarX + breatheBarWidth, breatheBarY + breatheBarHeight, 0xFF333333);
            event.getGuiGraphics().fill(breatheBarX, breatheBarY + breatheBarHeight - breatheFillHeight, breatheBarX + breatheBarWidth, breatheBarY + breatheBarHeight, 0xFF00008B); // Dark blue
        }

    }
    @SubscribeEvent
    public static void onRenderVanillaGui(RenderGuiLayerEvent.Pre event) {
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
        if(event.getName().toString().equals("minecraft:crosshair")){
            ResourceLocation CROSSHAIR_TEXTURE = ResourceLocation.fromNamespaceAndPath("cultivatormod", "textures/gui/hud/crosshair.png");
            event.setCanceled(true);
            Minecraft mc = Minecraft.getInstance();
            GuiGraphics guiGraphics = event.getGuiGraphics();
            int screenWidth = mc.getWindow().getGuiScaledWidth();
            int screenHeight = mc.getWindow().getGuiScaledHeight();
            int size = 16;
            int x = (screenWidth - size) / 2;
            int y = (screenHeight - size) / 2;
            guiGraphics.blit(CROSSHAIR_TEXTURE, x, y, 0, 0, size, size, size, size);
        }
        if(event.getName().toString().equals("minecraft:selected_item_name")){
            event.setCanceled(true);
        }
        if(event.getName().toString().equals("minecraft:air_level")){
            event.setCanceled(true);
        }
        if(event.getName().toString().equals("minecraft:hotbar")){
            ResourceLocation HOTBAR_TEXTURE = ResourceLocation.parse("cultivatormod:textures/gui/hud/hotbar.png");
            Minecraft mc = Minecraft.getInstance();
            GuiGraphics guiGraphics = event.getGuiGraphics();
            int x = (mc.getWindow().getGuiScaledWidth() - 182) / 2; // center like vanilla
            int y = mc.getWindow().getGuiScaledHeight() - 22;       // adjust Y if needed
            guiGraphics.blit(HOTBAR_TEXTURE, x, y, 0, 0, 184, 24, 184, 24);
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
            BlockPos below = player.blockPosition().below();
            Block blockBelow = player.level().getBlockState(below).getBlock();
            if (player.onGround() || blockBelow == Blocks.WATER || blockBelow == Blocks.LAVA) {
                ClientPacketListener connection = Minecraft.getInstance().getConnection();
                if (connection != null) {
                    connection.send(new ServerboundCustomPayloadPacket(DashPacket.INSTANCE));            }
            }else if(PlayerStatsClient.isCanFly() || player.isCreative()) {
                ClientPacketListener connection = Minecraft.getInstance().getConnection();
                if (connection != null) {
                    connection.send(new ServerboundCustomPayloadPacket(DashPacket.INSTANCE));
                }
            }
        }
    }
    private static void displayLookedAtInfo(Minecraft mc, PoseStack poseStack, int screenWidth, int screenHeight) {
        if (mc.player != null && mc.level != null) {
            Vec3 eyePos = mc.player.getEyePosition(1.0F);
            Vec3 lookVec = mc.player.getViewVector(1.0F);
            Vec3 reachVec = eyePos.add(lookVec.scale(2.5D));
            HitResult hit = mc.level.clip(new ClipContext(eyePos, reachVec, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, mc.player));
            Component text = null;
            if (hit != null && hit.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHit = (BlockHitResult) hit;
                BlockState state = mc.level.getBlockState(blockHit.getBlockPos());
                String modId = state.getBlock().getDescriptionId().toString();
                text = Component.literal(modId.toUpperCase());
            }
            if (hit == null || hit.getType() != HitResult.Type.BLOCK) {
                List<Entity> entities = mc.level.getEntities(mc.player, mc.player.getBoundingBox().expandTowards(lookVec.scale(5.0D)), e -> e instanceof LivingEntity); // Filter for living entities
                for (Entity entity : entities) {
                    Vec3 entityPos = entity.getPosition(1.0F);
                    double distance = eyePos.distanceTo(entityPos);
                    if (distance < 2.5D && entity.getBoundingBox().intersects(eyePos, reachVec)) {
                        String entityName = entity.getEncodeId().toString();
                        text = Component.literal(entityName.toUpperCase());
                        break;
                    }
                }
            }
            if (text != null) {
                Font font = mc.font;
                int x = (screenWidth / 2) - (font.width(text) / 2);
                int y = 10;
                Matrix4f matrix4f = poseStack.last().pose();
                MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();
                font.drawInBatch(
                        text, x, y, 0xFFFFFF, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880
                );
                bufferSource.endBatch();
            }
        }
    }
    private static void handleWaterWalking(Player player) {
        if (!PlayerStatsClient.getWalkOnWater()) return;
        if (player.isShiftKeyDown() && !player.isHolding(ModItems.BAODING_BALLS.get())) return;
        Vec3 feet = player.position();
        double velocityY = player.getDeltaMovement().y;
        double checkDistance = Math.max(0.5, -velocityY * 1.2); // Increase depth based on falling speed
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