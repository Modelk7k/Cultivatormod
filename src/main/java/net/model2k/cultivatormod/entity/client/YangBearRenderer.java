package net.model2k.cultivatormod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.entity.custom.YangBearEntity;

public class YangBearRenderer extends MobRenderer<YangBearEntity, YangBearModel<YangBearEntity>> {
    public YangBearRenderer(EntityRendererProvider.Context context) {
        super(context, new YangBearModel<>(context.bakeLayer(YangBearModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(YangBearEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "textures/entity/yangbear/yangbear.png");
    }

    @Override
    public void render(YangBearEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        if(entity.isBaby()){
            poseStack.scale(0.45f, 0.45f, 0.45f);
        }else{
            poseStack.scale(1f,1f,1f);
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
