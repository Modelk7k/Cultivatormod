package net.model2k.cultivatormod.event;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.network.packet.PlayerStatsClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(modid = CultivatorMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        int barWidth = 80;
        int barHeight = 8;
        int spacing = 2;
        int textPadding = 4;
        int bottomPadding = 4; // how close to bottom of screen

        int baseX = 4; // left padding
        int baseY = screenHeight - bottomPadding - (barHeight * 2 + spacing); // Qi bar is above Spirit bar

// Qi values
        int qi = PlayerStatsClient.getQi();
        int maxQi = PlayerStatsClient.getMaxQi();
        float qiRatio = Math.min((float) qi / maxQi, 1.0f);
        int qiFillWidth = (int) (barWidth * qiRatio);

// Spirit Power values
        int spirit = PlayerStatsClient.getSpiritPower();
        int maxSpirit = PlayerStatsClient.getMaxSpiritPower();
        float spiritRatio = Math.min((float) spirit / maxSpirit, 1.0f);
        int spiritFillWidth = (int) (barWidth * spiritRatio);

// Draw Qi bar (yellow, on top)
        event.getGuiGraphics().fill(baseX, baseY, baseX + barWidth, baseY + barHeight, 0xFF333333); // background
        event.getGuiGraphics().fill(baseX, baseY, baseX + qiFillWidth, baseY + barHeight, 0xFFFFFF00); // fill
        event.getGuiGraphics().drawString(mc.font, "Qi: " + qi + "/" + maxQi, baseX + barWidth + textPadding, baseY, 0xFFFFFF);

// Draw Spirit bar (light blue, just below)
        int spiritY = baseY + barHeight + spacing;
        event.getGuiGraphics().fill(baseX, spiritY, baseX + barWidth, spiritY + barHeight, 0xFF333333); // background
        event.getGuiGraphics().fill(baseX, spiritY, baseX + spiritFillWidth, spiritY + barHeight, 0xFF00FFFF); // fill
        event.getGuiGraphics().drawString(mc.font, "Spirit: " + spirit + "/" + maxSpirit, baseX + barWidth + textPadding, spiritY, 0xFFFFFF);
    }
}