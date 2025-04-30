package net.model2k.cultivatormod.block.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.block.ModBlocks;import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CultivatorMod.MOD_ID);

    public static final Supplier<BlockEntityType<JadeFurnaceEntity>> JADE_FURNACE_BE =
            BLOCK_ENTITIES.register("jade_furnace_be", () -> BlockEntityType.Builder.of(
                    JadeFurnaceEntity::new, ModBlocks.LOW_GRADE_JADE_FURNACE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}