package net.model2k.cultivatormod.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class VanishRenderLayer {

    // Create a RenderType for transparency
    private static final RenderType TRANSLUCENT_LAYER = RenderType.entityTranslucent(ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/player.png"));

    // Method to apply transparency to the player model
    public static void applyTransparency(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Player player, float partialTick) {
        if (player.getTags().contains("vanished")) {
            // Make the player transparent if they have the "vanished" tag
            Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(player)
                    .render(player, partialTick,packedLight , poseStack, bufferSource, 0);  // Render with transparency
            bufferSource.getBuffer(TRANSLUCENT_LAYER);  // Apply the translucent render type
        } else {
            // Default rendering for visible players
            Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(player)
                    .render(player, partialTick, packedLight, poseStack, bufferSource, 0);
        }
    }
}
