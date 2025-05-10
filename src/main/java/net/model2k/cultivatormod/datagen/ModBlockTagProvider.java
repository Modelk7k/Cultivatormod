package net.model2k.cultivatormod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.block.ModBlocks;
import net.model2k.cultivatormod.util.ModTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CultivatorMod.MOD_ID, existingFileHelper);
    }
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.LOW_GRADE_JADE.get());
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.QUARK_STAIRS.get())
                .add(ModBlocks.QUARK_PRESSURE_PLATE.get())
                .add(ModBlocks.QUARK_BUTTON.get())
                .add(ModBlocks.QUARK_SLAB.get());
        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.GREEN_GRASS.get())
                .add(ModBlocks.BLUE_GRASS.get())
                .add(ModBlocks.LIGHT_BLUE_GRASS.get())
                .add(ModBlocks.PINK_GRASS.get())
                .add(ModBlocks.PURPLE_GRASS.get())
                .add(ModBlocks.BROWN_GRASS.get())
                .add(ModBlocks.BLACK_GRASS.get())
                .add(ModBlocks.WHITE_GRASS.get())
                .add(ModBlocks.YELLOW_GRASS.get())
                .add(ModBlocks.ORANGE_GRASS.get())
                .add(ModBlocks.GREY_GRASS.get())
                .add(ModBlocks.RAINBOW_GRASS.get())
                .add(ModBlocks.ALIEN_DIRT.get());
        tag(ModTags.Blocks.NEEDS_JADE_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_JADE_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .remove(ModTags.Blocks.NEEDS_JADE_TOOL);
        tag(BlockTags.SAND)
                .add(ModBlocks.BLACK_SAND.get())
                .add(ModBlocks.BLUE_SAND.get())
                .add(ModBlocks.BLACK_SAND.get())
                .add(ModBlocks.GREEN_SAND.get())
                .add(ModBlocks.RED_SAND.get())
                .add(ModBlocks.LIGHT_BLUE_SAND.get())
                .add(ModBlocks.PURPLE_SAND.get())
                .add(ModBlocks.PINK_SAND.get())
                .add(ModBlocks.BROWN_SAND.get())
                .add(ModBlocks.GREY_SAND.get())
                .add(ModBlocks.RAINBOW_SAND.get())
                .add(ModBlocks.ORANGE_SAND.get())
                .add(ModBlocks.YELLOW_SAND.get())
                .add(ModBlocks.MAGENTA_SAND.get());
        tag(BlockTags.DIRT)
                .add(ModBlocks.ALIEN_DIRT.get());
        tag(BlockTags.FENCES)
                .add(ModBlocks.QUARK_FENCE.get());
        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.QUARK_FENCE_GATE.get());
        tag(BlockTags.TRAPDOORS)
                .add(ModBlocks.QUARK_TRAP_DOOR.get());
        tag(BlockTags.WALLS)
                .add(ModBlocks.QUARK_WALL.get());
        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.QUARK_LOG.get())
                .add(ModBlocks.QUARK_WOOD.get())
                .add(ModBlocks.QUARK_STRIPPED_LOG.get())
                .add(ModBlocks.QUARK_STRIPPED_WOOD.get());
        tag(BlockTags.PLANKS)
                .add(ModBlocks.QUARK_PLANK.get());
        tag(BlockTags.FIRE)
                .add(ModBlocks.OLD_SCHOOL_FIRE.get());
    }
}