package net.model2k.cultivatormod.entity;


import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.entity.custom.SlimeEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities  {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, CultivatorMod.MOD_ID);

    public static final Supplier<EntityType<SlimeEntity>> MINDLESS_SLIME =
            ENTITY_TYPES.register("mindlessslime" , () -> EntityType.Builder.of(SlimeEntity::new, MobCategory.CREATURE)
                    .sized(.75f, .35f).build("mindlessslime"));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
