package net.model2k.cultivatormod.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.entity.custom.QiSlashEntity;
import net.model2k.cultivatormod.entity.custom.SlimeEntity;
import net.model2k.cultivatormod.entity.custom.YangBearEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities  {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, CultivatorMod.MOD_ID);

    public static final Supplier<EntityType<SlimeEntity>> MINDLESS_SLIME =
            ENTITY_TYPES.register("mindlessslime" , () -> EntityType.Builder.of(SlimeEntity::new, MobCategory.CREATURE)
                    .sized(.75f, .35f).build("mindlessslime"));
    public static final Supplier<EntityType<YangBearEntity>> YANG_BEAR =
            ENTITY_TYPES.register("yangbear" , () -> EntityType.Builder.of(YangBearEntity::new, MobCategory.CREATURE)
                    .sized(1f, 1f).build("yangbear"));
    public static final DeferredHolder<EntityType<?>, EntityType<Entity>> QI_SLASH =
            ENTITY_TYPES.register("qislash" , () -> EntityType.Builder.of(QiSlashEntity::new, MobCategory.MISC)
                    .sized(1f, 1f).build("qislash"));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
