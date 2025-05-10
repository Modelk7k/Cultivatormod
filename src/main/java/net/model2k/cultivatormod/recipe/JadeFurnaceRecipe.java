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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public record JadeFurnaceRecipe(List<Ingredient> inputItems, ItemStack output) implements Recipe<JadeFurnaceRecipeInput> {
    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return NonNullList.copyOf(inputItems);
    }
    @Override
    public boolean matches(@NotNull JadeFurnaceRecipeInput jadeFurnaceRecipeInput, Level level) {
        if (level.isClientSide()) return false;
        List<ItemStack> inputStacks = jadeFurnaceRecipeInput.input();
        List<Ingredient> ingredientsCopy = new ArrayList<>(inputItems);
        for (ItemStack stack : inputStacks) {
            boolean matched = false;
            for (Ingredient ingredient : ingredientsCopy) {
                if (ingredient.test(stack)) {
                    matched = true;
                    ingredientsCopy.remove(ingredient);
                    break;
                }
            }
            if (!matched) return false;
        }
        return ingredientsCopy.isEmpty();
    }
    @Override
    public @NotNull ItemStack assemble(@NotNull JadeFurnaceRecipeInput input, HolderLookup.@NotNull Provider registries) {
        return output.copy();
    }
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }
    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider registries) {
        return output;
    }
    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.JADE_FURNACE_SERIALIZER.get();
    }
    @Override
    public @NotNull RecipeType<?> getType() {
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
        public @NotNull MapCodec<JadeFurnaceRecipe> codec() {
            return CODEC;
        }
        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, JadeFurnaceRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}