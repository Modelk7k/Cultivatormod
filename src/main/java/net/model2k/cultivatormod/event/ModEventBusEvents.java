package net.model2k.cultivatormod.event;

import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.entity.ModEntities;
import net.model2k.cultivatormod.entity.client.MindlessSlimeModel;
import net.model2k.cultivatormod.entity.custom.SlimeEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = CultivatorMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)

public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition((MindlessSlimeModel.LAYER_LOCATION), MindlessSlimeModel:: createBodyLayer);
    }
@SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.MINDLESS_SLIME.get(), SlimeEntity.createAttributes().build());
    }
}
