package net.model2k.cultivatormod.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.entity.custom.QiSlashEntity;
import net.model2k.cultivatormod.entity.custom.SeveredZombieHeadEntity;
import net.model2k.cultivatormod.entity.custom.SlimeEntity;
import net.model2k.cultivatormod.entity.custom.YangBearEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities  {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, CultivatorMod.MOD_ID);

    public static final Supplier<EntityType<SlimeEntity>> MINDLESS_SLIME =
            ENTITY_TYPES.register("mindlessslime" , () -> EntityType.Builder.of(SlimeEntity::new, MobCategory.CREATURE)
                    .sized(.75f, .35f).build("mindlessslime"));
    public static final Supplier<EntityType<YangBearEntity>> YANG_BEAR =
            ENTITY_TYPES.register("yangbear" , () -> EntityType.Builder.of(YangBearEntity::new, MobCategory.CREATURE)
                    .sized(1f, 1f).build("yangbear"));
    public static final Supplier<EntityType> QI_SLASH =
            ENTITY_TYPES.register("qislash", () -> EntityType.Builder
                    .of(QiSlashEntity::new, MobCategory.MISC)
                    .sized(.5f, .5f)
                    .build("qislash"));
    public static final Supplier<EntityType<SeveredZombieHeadEntity>> SEVERED_ZOMBIE_HEAD =
            ENTITY_TYPES.register("severed_zombie_head", () -> EntityType.Builder
                    .<SeveredZombieHeadEntity>of(SeveredZombieHeadEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f) // Size of a dropped item
                    .clientTrackingRange(8)
                    .updateInterval(10)
                    .build("severed_zombie_head"));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}