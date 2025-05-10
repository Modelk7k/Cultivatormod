package net.model2k.cultivatormod.item;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.model2k.cultivatormod.util.ModTags;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {
    public static final Tier JADE = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_JADE_TOOL, 1400, 4f, 3f , 28, () -> Ingredient.of(ModItems.LOW_GRADE_JADE_STONE));
}