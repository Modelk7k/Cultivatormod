package net.model2k.cultivatormod.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class VanishRenderLayer {
    private static final RenderType TRANSLUCENT_LAYER = RenderType.entityTranslucent(ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/player.png"));
    public static void applyTransparency(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Player player, float partialTick) {
        if (player.getTags().contains("vanished")) {
            Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(player).render(player, partialTick,packedLight , poseStack, bufferSource, 0);
            bufferSource.getBuffer(TRANSLUCENT_LAYER);
        } else {
            Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(player)
                    .render(player, partialTick, packedLight, poseStack, bufferSource, 0);
        }
    }
}