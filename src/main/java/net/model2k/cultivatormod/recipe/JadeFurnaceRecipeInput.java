package net.model2k.cultivatormod.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record JadeFurnaceRecipeInput(ItemStack input) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        return input;
    }
    @Override
    public int size() {
        return 1;
    }
}
