package net.model2k.cultivatormod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.monster.Zombie;
import org.jetbrains.annotations.NotNull;

public class CustomZombieModel<T extends Zombie> extends ZombieModel<T> {
    public CustomZombieModel(ModelPart root) {
        super(root);
    }
    @Override
    public void setupAnim(@NotNull T zombie, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(zombie, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        boolean beheaded = zombie.getPersistentData().getBoolean("Beheaded");
        this.head.visible = !beheaded;
        this.hat.visible = !beheaded;
    }
    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        boolean isBeheaded = false;
        if (!isBeheaded) {
            this.head.render(poseStack, buffer, packedLight, packedOverlay, color);
            this.hat.render(poseStack, buffer, packedLight, packedOverlay,color);
        }
        this.body.render(poseStack, buffer, packedLight, packedOverlay,color);
        this.leftArm.render(poseStack, buffer, packedLight, packedOverlay,color);
        this.rightArm.render(poseStack, buffer, packedLight, packedOverlay,color);
        this.leftLeg.render(poseStack, buffer, packedLight, packedOverlay,color);
        this.rightLeg.render(poseStack, buffer, packedLight, packedOverlay,color);
    }
}