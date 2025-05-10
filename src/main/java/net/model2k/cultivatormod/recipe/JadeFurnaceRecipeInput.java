package net.model2k.cultivatormod.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record JadeFurnaceRecipeInput(List<ItemStack> input) implements RecipeInput {
    @Override
    public @NotNull ItemStack getItem(int index) {
        if (index < 0 || index >= input.size()) {
            return ItemStack.EMPTY;
        }
        return input.get(index);
    }
    @Override
    public int size() {
        return input.size();
    }
}