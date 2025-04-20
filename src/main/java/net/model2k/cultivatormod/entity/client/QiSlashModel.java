package net.model2k.cultivatormod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.model2k.cultivatormod.entity.custom.QiSlashEntity;

public class QiSlashModel <T extends QiSlashEntity> extends HierarchicalModel<QiSlashEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("cultivatormod", "qislash"), "main");
    private final ModelPart QiSlash;

    public QiSlashModel(ModelPart root) {
        this.QiSlash = root.getChild("QiSlash");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition QiSlash = partdefinition.addOrReplaceChild("QiSlash", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(-1.0F, -4.0F, -3.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(4, 0).addBox(-1.0F, -6.0F, -3.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(4, 4).addBox(-1.0F, -8.0F, -3.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-1.0F, -10.0F, -3.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 0).addBox(-1.0F, -12.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }
    @Override
    public void setupAnim(QiSlashEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float randomRotation = (Math.random() > 0.5) ? 45.0f : -45.0f;

        // Apply this rotation to the base part of the model
        this.QiSlash.yRot = randomRotation;

    }
    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        QiSlash.render(poseStack, vertexConsumer, packedLight, packedOverlay,color);
    }

    @Override
    public ModelPart root() {
        return QiSlash;
    }
}
