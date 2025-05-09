package net.model2k.cultivatormod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;

public class CustomZombieRenderer extends ZombieRenderer {
    public CustomZombieRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new CustomZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE));
    }
    @Override
    public void render(Zombie zombie, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(zombie, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }
    @Override
    public ResourceLocation getTextureLocation(Zombie entity) {
        return  ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/zombie/zombie.png");
    }
    @Override
    protected void scale(Zombie zombie, PoseStack poseStack, float partialTickTime) {
        if (zombie.isBaby()) {
            float scale = 0.5F;
            poseStack.scale(scale, scale, scale);
            poseStack.translate(0.0F,(1.0F - scale - 0.5f), 0.0F); // = 0.75F upward
        } else {
            poseStack.scale(1.0F, 1.0F, 1.0F);
        }
    }
}