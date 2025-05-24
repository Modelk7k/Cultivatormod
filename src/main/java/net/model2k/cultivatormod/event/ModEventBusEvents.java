package net.model2k.cultivatormod.event;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.entity.ModEntities;
import net.model2k.cultivatormod.entity.client.*;
import net.model2k.cultivatormod.entity.custom.SlimeEntity;
import net.model2k.cultivatormod.entity.custom.YangBearEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = CultivatorMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition((MindlessSlimeModel.LAYER_LOCATION), MindlessSlimeModel::createBodyLayer);
        event.registerLayerDefinition((YangBearModel.LAYER_LOCATION), YangBearModel::createBodyLayer);
        event.registerLayerDefinition((QiSlashModel.LAYER_LOCATION), QiSlashModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityType.ZOMBIE, CustomZombieRenderer::new);

    }
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.MINDLESS_SLIME.get(), SlimeEntity.createAttributes().build());
        event.put(ModEntities.YANG_BEAR.get(), YangBearEntity.createAttributes().build());
        event.put(ModEntities.QI_SLASH.get(), Mob.createMobAttributes().build());
    }
}