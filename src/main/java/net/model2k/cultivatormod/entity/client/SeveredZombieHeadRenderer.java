package net.model2k.cultivatormod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.model2k.cultivatormod.entity.custom.SeveredZombieHeadEntity;

public class SeveredZombieHeadRenderer extends EntityRenderer<SeveredZombieHeadEntity> {
    public SeveredZombieHeadRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(SeveredZombieHeadEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0D, 0.25D, 0.0D); // Adjust as needed for height
        poseStack.mulPose(Axis.YP.rotationDegrees(-entityYaw)); // Optional: make it rotate

        ItemStack head = new ItemStack(Items.ZOMBIE_HEAD);
        Minecraft.getInstance().getItemRenderer().renderStatic(
                head,
                ItemDisplayContext.GROUND,  // or FIXED / NONE depending on how you want it to appear
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                entity.level(),
                entity.getId()
        );

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(SeveredZombieHeadEntity entity) {
        return null;
    }

}
