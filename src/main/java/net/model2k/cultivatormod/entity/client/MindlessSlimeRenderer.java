package net.model2k.cultivatormod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.entity.custom.SlimeEntity;
import org.jetbrains.annotations.NotNull;

public class MindlessSlimeRenderer extends MobRenderer<SlimeEntity, MindlessSlimeModel<SlimeEntity>> {
    public MindlessSlimeRenderer(EntityRendererProvider.Context context) {
        super(context, new MindlessSlimeModel<>(context.bakeLayer(MindlessSlimeModel.LAYER_LOCATION)), 0.25f);
    }
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull SlimeEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "textures/entity/mindlessslime/slime.png");
    }
    @Override
    public void render(SlimeEntity entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        if(entity.isBaby()){
            poseStack.scale(0.45f, 0.45f, 0.45f);
        }else{
            poseStack.scale(1f,1f,1f);
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}