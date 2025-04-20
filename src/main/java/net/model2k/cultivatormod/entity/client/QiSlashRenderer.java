package net.model2k.cultivatormod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.entity.custom.QiSlashEntity;

public class QiSlashRenderer extends MobRenderer<QiSlashEntity, QiSlashModel<QiSlashEntity>> {
    private static final ResourceLocation TEXTURE =  ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "textures/entity/qislash/qislash.png");
    public QiSlashRenderer(EntityRendererProvider.Context context) {
        super(context, new QiSlashModel<>(context.bakeLayer(QiSlashModel.LAYER_LOCATION)), 0.25f);
    }
    @Override
    public void render(QiSlashEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0, -1.5, 0); // Offset if needed
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        var vertexConsumer = bufferSource.getBuffer(model.renderType(getTextureLocation(entity)));
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY,
                1);
        poseStack.popPose();
    }
    @Override
    public ResourceLocation getTextureLocation(QiSlashEntity entity) {
        return TEXTURE;
    }
}