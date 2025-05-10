package net.model2k.cultivatormod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.model2k.cultivatormod.item.ModItems;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {
    protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }
    @Override
    protected void gather(HolderLookup.@NotNull Provider provider) {
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(ModItems.TEST_FUEL.getId(), new FurnaceFuel(1200), false);
        this.builder(NeoForgeDataMaps.COMPOSTABLES)
                .add(ModItems.LOW_GRADE_SPIRIT_FLOWER_SEEDS.getId(), new Compostable(0.25f), false)
                .add(ModItems.LOW_GRADE_SPIRIT_FLOWER_BUNDLE.getId(), new Compostable(0.25f), false);
        }
    }