package net.model2k.cultivatormod.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.block.ModBlocks;
import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> LOW_GRADE_JADE_ORE_PLACED_KEY = registerKey("low_grade_jade_ore_placed");
    public static final ResourceKey<PlacedFeature> QUARK_PLACED_KEY = registerKey("quark_placed");
    public static final ResourceKey<PlacedFeature> YANG_BUSH_PLACED_KEY = registerKey("yang_bush_placed");
    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
//Spawn Jade In stone and deepslate
        register(context, LOW_GRADE_JADE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_LOW_GRADE_JADE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
//Trees
        register(context, QUARK_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.QUARK_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1f, 0),
                        ModBlocks.QUARK_SAPLING.get()));
//Bushes
        register(context, YANG_BUSH_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.YANG_BUSH_KEY),
                List.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE,BiomeFilter.biome()));
    }
    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, name));
    }
    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}