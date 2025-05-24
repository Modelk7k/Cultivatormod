package net.model2k.cultivatormod.entity.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.model2k.cultivatormod.network.packet.PlayerStatsClient;

public class CustomPlayerRenderer extends PlayerRenderer {
    public CustomPlayerRenderer(EntityRendererProvider.Context context, boolean isSlim) {
        super(context, isSlim);
    }
    @Override
    public void render(AbstractClientPlayer player, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        if (PlayerStatsClient.getVanished() && !player.getTags().contains("staff")) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.5F);
        } else {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
        super.render(player, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }
    @Override
    public ResourceLocation getTextureLocation(AbstractClientPlayer entity) {
        return entity.getSkin().texture();
    }
}