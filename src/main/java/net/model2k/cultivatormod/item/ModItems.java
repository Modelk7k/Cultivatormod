package net.model2k.cultivatormod.item;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.block.ModBlocks;
import net.model2k.cultivatormod.entity.ModEntities;
import net.model2k.cultivatormod.item.custom.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.spongepowered.asm.mixin.injection.At;

import java.util.UUID;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CultivatorMod.MOD_ID);

    //Gems and Bars
    public static final DeferredItem<Item> LOW_GRADE_JADE_STONE  = ITEMS.register("low_grade_jade_stone" ,
            () -> new Item(new Item.Properties()));
    //Fuel
    public static final DeferredItem<Item> TEST_FUEL = ITEMS.register("test_fuel",
            () -> new FuelItem(new Item.Properties(), 800));
    //Pills
    public static final DeferredItem<Item> LOW_GRADE_FOUNDATION_PILL = ITEMS.register("low_grade_foundation_pill",
            () -> new Item(new Item.Properties().food(ModFoodProperties.LOW_GRADE_PILL)));
    public static final DeferredItem<Item> LOW_GRADE_SPIRIT_PILL = ITEMS.register("low_grade_spirit_pill",
            () -> new LowGradeSpiritPill(new Item.Properties().food(ModFoodProperties.LOW_GRADE_PILL)));
    public static final DeferredItem<Item> LOW_GRADE_HEAVEN_AND_EARTH_PILL = ITEMS.register("low_grade_heaven_and_earth_pill",
            () -> new LowGradeHeavenAndEarthPill(new Item.Properties().food(ModFoodProperties.LOW_GRADE_PILL)));
    public static final DeferredItem<Item> LOW_GRADE_BODY_AND_BONE_PILL = ITEMS.register("low_grade_body_and_bone_pill",
            () -> new LowGradeBodyAndBonePill(new Item.Properties().food(ModFoodProperties.LOW_GRADE_PILL)));
    public static final DeferredItem<Item> LOW_GRADE_BLOOD_BEAD = ITEMS.register("low_grade_blood_bead",
            () -> new Item(new Item.Properties().food(ModFoodProperties.LOW_GRADE_PILL)));
    //Ingredients
    public static final DeferredItem<Item> LOW_GRADE_HERB_CLUMP  = ITEMS.register("low_grade_herb_clump" ,
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LOW_GRADE_SPIRIT_FLOWER_BUNDLE  = ITEMS.register("low_grade_spirit_flower_bundle" ,
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LOW_GRADE_GINSENG  = ITEMS.register("low_grade_ginseng" ,
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLOOD_FROM_EARTH  = ITEMS.register("blood_from_earth" ,
            () -> new Item(new Item.Properties()));
    //Fruits
    public static final DeferredItem<Item> LOW_GRADE_YANG_FRUIT = ITEMS.register("low_grade_yang_fruit",
            () -> new ItemNameBlockItem(ModBlocks.LOW_GRADE_YANG_FRUIT_BUSH.get(), new Item.Properties().food(ModFoodProperties.LOW_GRADE_YANG_FRUIT)));
    //Seeds
    public static final DeferredItem<Item> LOW_GRADE_SPIRIT_FLOWER_SEEDS = ITEMS.register("low_grade_spirit_flower_seeds",
            () -> new ItemNameBlockItem(ModBlocks.LOW_GRADE_SPIRIT_FLOWER.get(), new Item.Properties()));
    //Tools
    public static final DeferredItem<PickaxeItem> LOW_GRADE_JADE_PICKAXE = ITEMS.register("low_grade_jade_pickaxe",
            ()-> new PickaxeItem(ModToolTiers.JADE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.JADE, 3f, -2.4f ))));
    public static final DeferredItem<AxeItem> LOW_GRADE_JADE_AXE = ITEMS.register("low_grade_jade_axe",
            ()-> new AxeItem(ModToolTiers.JADE, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.JADE, 6f, -3f ))));
    public static final DeferredItem<ShovelItem> LOW_GRADE_JADE_SHOVEL = ITEMS.register("low_grade_jade_shovel",
            ()-> new ShovelItem(ModToolTiers.JADE, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.JADE, 1.5f, -3f ))));
    public static final DeferredItem<HoeItem> LOW_GRADE_JADE_HOE = ITEMS.register("low_grade_jade_hoe",
            ()-> new HoeItem(ModToolTiers.JADE, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.JADE, 0f, -3f ))));
    //Weapons
    public static final DeferredItem<Item> LIGHTNING_STAFF = ITEMS.register("lightning_staff",
            () -> new LightningStaff(new Item.Properties().durability(1)));
    public static final DeferredItem<SwordItem> LOW_GRADE_JADE_SWORD = ITEMS.register("low_grade_jade_sword",
            ()-> new SwordItem(ModToolTiers.JADE, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.JADE, 5, 3f ))));
    //Armour
    //Eggs
    public static final DeferredItem<Item> MINDLESS_SLIME_EGG = ITEMS.register("mindless_slime_egg",
            () -> new DeferredSpawnEggItem(ModEntities.MINDLESS_SLIME, 0x32afaf, 0xffac00, new Item.Properties()));
   //Qi Charge Item
    public static final DeferredItem<Item> BAODING_BALLS = ITEMS.register("baoding_balls",
            () -> new Item( new Item.Properties()));
    //Misc
    public static final DeferredItem<Item> CULT_LOGO  = ITEMS.register("cult_logo" ,
            () -> new Item(new Item.Properties()));





    //Register
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
