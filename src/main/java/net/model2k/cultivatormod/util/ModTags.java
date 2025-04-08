package net.model2k.cultivatormod.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.model2k.cultivatormod.CultivatorMod;

public class ModTags {
    public static class Blocks {

        public static final TagKey<Block> NEEDS_JADE_TOOL = createTag("needs_jade_tool");
        public static final TagKey<Block> INCORRECT_FOR_JADE_TOOL = createTag("incorrect_for_jade_tool");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, name));
        }
    }
    public static class Items {
        public static final TagKey<Item> CULTIVATOR_ITEMS = createTag("cultivator_items");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, name));
        }
    }
}
