package net.model2k.cultivatormod.dimension;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.OptionalLong;

public class SpawnDimensionType {
    // Create DeferredRegisters for DimensionType and LevelStem
    public static final DeferredRegister<DimensionType> DIMENSION_TYPES = DeferredRegister.create(Registries.DIMENSION_TYPE, "cultivatormod");
    public static final DimensionType SPAWN_DIMENSION_TYPE = new DimensionType(
            OptionalLong.empty(), // Fixed time
            true, // Has Skylight
            false, // Has Ceiling
            false, // Ultra Warm
            true, // Natural
            1.0, // Coordinate Scale
            true, // Bed Works
            true, // Respawn Anchor Works
            -64, // Min Y
            128, // Height
            128, // Logical Height
            TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("minecraft", "infiniburn")), // Infiniburn Block
            ResourceLocation.fromNamespaceAndPath("minecraft", "overworld"), // Effects Location
            0.5f, // Ambient Light
            new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 0), 0) // No Monsters
    );
}