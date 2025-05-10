package net.model2k.cultivatormod.recipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.model2k.cultivatormod.CultivatorMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, CultivatorMod.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, CultivatorMod.MOD_ID);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<JadeFurnaceRecipe>> JADE_FURNACE_SERIALIZER =
            SERIALIZERS.register("jade_furnace", JadeFurnaceRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<JadeFurnaceRecipe>> JADE_FURNACE_TYPE =
            TYPES.register("jade_furnace", () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return "jade_furnace";
                }
            });
    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}