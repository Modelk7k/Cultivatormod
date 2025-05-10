package net.model2k.cultivatormod.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.model2k.cultivatormod.CultivatorMod;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModBiomeModifiers {

    public static final ResourceKey<BiomeModifier> ADD_LOW_GRADE_JADE_ORE = registerKey("add_low_grade_jade_ore");
    public static final ResourceKey<BiomeModifier> ADD_TREE_QUARK = registerKey("add_tree_quark");
    public static final ResourceKey<BiomeModifier> ADD_YANG_BUSH = registerKey("add_yang_bush");
    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        // CF -> PF -> BM
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);
//Spawn Jade In Overworld
        context.register(ADD_LOW_GRADE_JADE_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.LOW_GRADE_JADE_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
//Trees
        context.register(ADD_TREE_QUARK, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.QUARK_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
//Bushes
        context.register(ADD_YANG_BUSH, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.YANG_BUSH_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

    }
    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, name));
    }
}