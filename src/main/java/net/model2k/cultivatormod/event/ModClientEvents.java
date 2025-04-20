package net.model2k.cultivatormod.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.phys.Vec3;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.network.packet.PlayerStatsClient;
import net.model2k.cultivatormod.network.packet.QiSlashPacket;
import net.model2k.cultivatormod.util.ModKeyBinds;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(modid = CultivatorMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        int screenHeight = mc.getWindow().getGuiScaledHeight();
        int barWidth = 80;
        int barHeight = 8;
        int spacing = 2;
        int textPadding = 4;
        int bottomPadding = 4;
        int baseX = 4;
        int baseY = screenHeight - bottomPadding - (barHeight * 2 + spacing); // Qi bar is above Spirit bar
        int qi = PlayerStatsClient.getQi();
        int maxQi = PlayerStatsClient.getMaxQi();
        float qiRatio = Math.min((float) qi / maxQi, 1.0f);
        int qiFillWidth = (int) (barWidth * qiRatio);
        int spirit = PlayerStatsClient.getSpiritPower();
        int maxSpirit = PlayerStatsClient.getMaxSpiritPower();
        float spiritRatio = Math.min((float) spirit / maxSpirit, 1.0f);
        int spiritFillWidth = (int) (barWidth * spiritRatio);
        event.getGuiGraphics().fill(baseX, baseY, baseX + barWidth, baseY + barHeight, 0xFF333333); // background
        event.getGuiGraphics().fill(baseX, baseY, baseX + qiFillWidth, baseY + barHeight, 0xFFFFFF00); // fill
        event.getGuiGraphics().drawString(mc.font, "Qi: " + qi + "/" + maxQi, baseX + barWidth + textPadding, baseY, 0xFFFFFF);
        int spiritY = baseY + barHeight + spacing;
        event.getGuiGraphics().fill(baseX, spiritY, baseX + barWidth, spiritY + barHeight, 0xFF333333); // background
        event.getGuiGraphics().fill(baseX, spiritY, baseX + spiritFillWidth, spiritY + barHeight, 0xFF00FFFF); // fill
        event.getGuiGraphics().drawString(mc.font, "Spirit: " + spirit + "/" + maxSpirit, baseX + barWidth + textPadding, spiritY, 0xFFFFFF);
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
                // Fire the QiSlash
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

}

