package net.model2k.cultivatormod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.block.ModBlocks;
import net.model2k.cultivatormod.util.ModTags;
import net.model2k.cultivatormod.item.ModItems;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, CultivatorMod.MOD_ID, existingFileHelper);
    }
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(ModTags.Items.CULTIVATOR_ITEMS)
                .add(ModItems.CULT_LOGO.get())
                .add(ModItems.LOW_GRADE_JADE_STONE.get())
                .add(ModItems.LOW_GRADE_HERB_CLUMP.get())
                .add(ModItems.LIGHTNING_STAFF.get())
                .add(ModItems.LOW_GRADE_FOUNDATION_PILL.get());
        tag(ItemTags.PICKAXES)
                .add(ModItems.LOW_GRADE_JADE_PICKAXE.get());
        tag(ItemTags.AXES)
                .add(ModItems.LOW_GRADE_JADE_AXE.get());
        tag(ItemTags.SHOVELS)
                .add(ModItems.LOW_GRADE_JADE_SHOVEL.get());
        tag(ItemTags.SWORDS)
                .add(ModItems.LOW_GRADE_JADE_SWORD.get());
        tag(ItemTags.HOES)
                .add(ModItems.LOW_GRADE_JADE_HOE.get());
        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.QUARK_LOG.get().asItem())
                .add(ModBlocks.QUARK_WOOD.get().asItem())
                .add(ModBlocks.QUARK_STRIPPED_LOG.get().asItem())
                .add(ModBlocks.QUARK_STRIPPED_WOOD.get().asItem());
        this.tag(ItemTags.PLANKS)
                .add(ModBlocks.QUARK_PLANK.asItem());
    }
}