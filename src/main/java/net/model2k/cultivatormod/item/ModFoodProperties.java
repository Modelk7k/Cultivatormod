package net.model2k.cultivatormod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties LOW_GRADE_PILL = new FoodProperties.Builder().nutrition(10).saturationModifier(10).effect(
            () -> new MobEffectInstance(MobEffects.REGENERATION, 1000),1).alwaysEdible().build();
    public static final FoodProperties LOW_GRADE_YANG_FRUIT = new FoodProperties.Builder().nutrition(10).saturationModifier(10).effect(
            () -> new MobEffectInstance(MobEffects.REGENERATION, 2000), 1).alwaysEdible().build();
}
