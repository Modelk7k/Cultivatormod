package net.model2k.cultivatormod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.model2k.cultivatormod.entity.custom.YangBearEntity;

public class YangBearModel <T extends YangBearEntity> extends HierarchicalModel<YangBearEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("cultivatormod", "yangbear"), "main");
    private final ModelPart Bear;
    private final ModelPart Body;
    private final ModelPart FrontLeftLeg;
    private final ModelPart BackLeftLeg;
    private final ModelPart BackRightLeg;
    private final ModelPart FrontRightLeg;
    private final ModelPart Tail;
    private final ModelPart Neck;
    private final ModelPart Head;
    private final ModelPart Mouth;

    public YangBearModel(ModelPart root) {
        this.Bear = root.getChild("Bear");
        this.Body = this.Bear.getChild("Body");
        this.FrontLeftLeg = this.Bear.getChild("FrontLeftLeg");
        this.BackLeftLeg = this.Bear.getChild("BackLeftLeg");
        this.BackRightLeg = this.Bear.getChild("BackRightLeg");
        this.FrontRightLeg = this.Bear.getChild("FrontRightLeg");
        this.Tail = this.Bear.getChild("Tail");
        this.Neck = this.Bear.getChild("Neck");
        this.Head = this.Neck.getChild("Head");
        this.Mouth = this.Head.getChild("Mouth");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Bear = partdefinition.addOrReplaceChild("Bear", CubeListBuilder.create(), PartPose.offsetAndRotation(-6.0F, 9.8F, -17.3F, 0.0F, 1.5708F, 0.0F));

        PartDefinition Body = Bear.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-19.0F, -5.0F, -7.0F, 23.0F, 5.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.5F, 10.7F, 6.3F));

        PartDefinition cube_r1 = Body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 19).addBox(-7.0F, -13.0F, -7.0F, 15.0F, 13.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.1F, -3.7F, 0.0F, 0.0F, 0.0F, -0.1309F));

        PartDefinition cube_r2 = Body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 46).addBox(-9.0F, -13.0F, -7.0F, 13.0F, 13.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

        PartDefinition FrontLeftLeg = Bear.addOrReplaceChild("FrontLeftLeg", CubeListBuilder.create().texOffs(78, 79).addBox(-4.8F, 11.0F, -1.0F, 9.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(96, 51).addBox(-4.8F, 6.0F, -1.0F, 7.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.6F, 0.0F, 14.3F));

        PartDefinition cube_r3 = FrontLeftLeg.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(26, 73).addBox(-6.0F, -10.0F, 1.0F, 7.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 8.0F, -2.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition BackLeftLeg = Bear.addOrReplaceChild("BackLeftLeg", CubeListBuilder.create().texOffs(30, 94).addBox(-2.0F, 5.8F, -2.0F, 7.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(86, 69).addBox(-2.0F, 11.0F, -2.0F, 9.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-25.8F, 0.0F, 15.3F));

        PartDefinition cube_r4 = BackLeftLeg.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 73).addBox(-6.0F, -10.0F, 1.0F, 7.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.2F, 7.0F, -3.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition BackRightLeg = Bear.addOrReplaceChild("BackRightLeg", CubeListBuilder.create().texOffs(78, 88).addBox(-2.2F, 10.0F, -2.0F, 9.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(96, 39).addBox(-2.2F, 4.8F, -2.0F, 7.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-25.6F, 1.0F, -4.7F));

        PartDefinition cube_r5 = BackRightLeg.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(52, 79).addBox(-6.0F, -10.0F, 1.0F, 7.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 6.0F, -3.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition FrontRightLeg = Bear.addOrReplaceChild("FrontRightLeg", CubeListBuilder.create().texOffs(0, 88).addBox(-4.8F, 11.0F, -3.0F, 9.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 97).addBox(-4.8F, 6.0F, -3.0F, 7.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.6F, 0.0F, -3.7F));

        PartDefinition cube_r6 = FrontRightLeg.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(74, 0).addBox(-6.0F, -10.0F, 1.0F, 7.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 8.0F, -4.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition Tail = Bear.addOrReplaceChild("Tail", CubeListBuilder.create(), PartPose.offset(-32.6F, -3.0F, 6.3F));

        PartDefinition cube_r7 = Tail.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(56, 94).addBox(-6.0F, -4.0F, -7.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.2182F));

        PartDefinition Neck = Bear.addOrReplaceChild("Neck", CubeListBuilder.create(), PartPose.offset(-4.5F, 1.7F, 6.3F));

        PartDefinition cube_r8 = Neck.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(58, 19).addBox(-9.0F, -9.0F, -6.0F, 8.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

        PartDefinition Head = Neck.addOrReplaceChild("Head", CubeListBuilder.create(), PartPose.offset(3.5F, 0.3F, -0.3F));

        PartDefinition cube_r9 = Head.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(30, 88).addBox(-1.0F, -3.0F, -1.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -2.0F, -6.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r10 = Head.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(80, 39).addBox(-1.0F, -3.0F, -1.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -2.1F, 5.7F, -0.3054F, 0.0F, 0.0F));

        PartDefinition cube_r11 = Head.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(54, 69).addBox(-9.0F, -7.0F, -2.0F, 11.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.5F, 6.7F, -0.4F, 0.0F, 0.0F, 0.0873F));

        PartDefinition cube_r12 = Head.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(54, 46).addBox(-9.0F, -9.0F, -6.0F, 8.0F, 10.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, 5.7F, -0.1F, 0.0F, 0.0F, 0.0873F));

        PartDefinition Mouth = Head.addOrReplaceChild("Mouth", CubeListBuilder.create(), PartPose.offset(5.5F, 4.7F, -0.2F));

        PartDefinition cube_r13 = Mouth.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(58, 39).addBox(-3.9564F, -2.0009F, -2.0F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 2.0F, -0.2F, 0.0F, 0.0F, 0.0436F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(YangBearEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(YangBearAnimations.Walking, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(entity.idleAnimationState, YangBearAnimations.Idle, ageInTicks, 1f);
        applyHeadRotation(netHeadYaw,headPitch);
    }
    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

        this.Head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.Head.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }
    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        Bear.render(poseStack, vertexConsumer, packedLight, packedOverlay,color);
    }
    @Override
    public ModelPart root() {
        return Bear;
    }
}
