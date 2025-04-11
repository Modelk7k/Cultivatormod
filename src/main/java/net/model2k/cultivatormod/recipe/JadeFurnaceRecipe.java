package net.model2k.cultivatormod.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public record JadeFurnaceRecipe(List<Ingredient> inputItems, ItemStack output) implements Recipe<JadeFurnaceRecipeInput> {
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.copyOf(inputItems);
    }
    @Override
    public boolean matches(JadeFurnaceRecipeInput jadeFurnaceRecipeInput, Level level) {
        if (level.isClientSide()) return false;
        List<ItemStack> inputStacks = jadeFurnaceRecipeInput.input();
        List<Ingredient> ingredientsCopy = new ArrayList<>(inputItems);

        // Check each item stack in input against ingredients
        for (ItemStack stack : inputStacks) {
            boolean matched = false;
            for (Ingredient ingredient : ingredientsCopy) {
                if (ingredient.test(stack)) {
                    matched = true;
                    ingredientsCopy.remove(ingredient); // Remove matched ingredient
                    break;  // No need to check further once a match is found
                }
            }
            if (!matched) return false; // If any item doesn't match, return false
        }
        return ingredientsCopy.isEmpty(); // If all ingredients are matched, return true
    }
    @Override
    public ItemStack assemble(JadeFurnaceRecipeInput input, HolderLookup.Provider registries) {
        return output.copy();
    }
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }
    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output;
    }
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.JADE_FURNACE_SERIALIZER.get();
    }
    @Override
    public RecipeType<?> getType() {
        return ModRecipes.JADE_FURNACE_TYPE.get();
    }
    public static class Serializer implements RecipeSerializer<JadeFurnaceRecipe> {
        public static final MapCodec<JadeFurnaceRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").forGetter(JadeFurnaceRecipe::inputItems),
                ItemStack.CODEC.fieldOf("result").forGetter(JadeFurnaceRecipe::output)
        ).apply(inst, JadeFurnaceRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, JadeFurnaceRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), JadeFurnaceRecipe::inputItems,
                        ItemStack.STREAM_CODEC, JadeFurnaceRecipe::output,
                        JadeFurnaceRecipe::new);
        @Override
        public MapCodec<JadeFurnaceRecipe> codec() {
            return CODEC;
        }
        @Override
        public StreamCodec<RegistryFriendlyByteBuf, JadeFurnaceRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
