package net.model2k.cultivatormod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.block.ModBlocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CultivatorMod.MOD_ID);

    public static final Supplier<CreativeModeTab> CULTIVATOR_ITEM_TAB = CREATIVE_MODE_TAB.register("cultivator_item_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CULT_LOGO.get()))
                    .title(Component.translatable("creativetab.cultivatormod.cultivator_item"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.LIGHTNING_STAFF);
                        output.accept(ModItems.LOW_GRADE_JADE_PICKAXE);
                        output.accept(ModItems.LOW_GRADE_JADE_AXE);
                        output.accept(ModItems.LOW_GRADE_JADE_SHOVEL);
                        output.accept(ModItems.LOW_GRADE_JADE_SWORD);
                        output.accept(ModItems.LOW_GRADE_JADE_HOE);
                        output.accept(ModItems.MINDLESS_SLIME_EGG);
                        output.accept(ModItems.BAODING_BALLS);
    })
                    .build()
    );
    public static final Supplier<CreativeModeTab> CULTIVATOR_BLOCK_TAB = CREATIVE_MODE_TAB.register("cultivator_block_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CULT_LOGO.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "cultivator_item_tab"))
                    .title(Component.translatable("creativetab.cultivatormod.cultivator_block"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(ModBlocks.ALIEN_DIRT);
                        output.accept(ModBlocks.GREEN_GRASS);
                        output.accept(ModBlocks.RED_GRASS);
                        output.accept(ModBlocks.PINK_GRASS);
                        output.accept(ModBlocks.PURPLE_GRASS);
                        output.accept(ModBlocks.BLUE_GRASS);
                        output.accept(ModBlocks.LIGHT_BLUE_GRASS);
                        output.accept(ModBlocks.YELLOW_GRASS);
                        output.accept(ModBlocks.GREY_GRASS);
                        output.accept(ModBlocks.ORANGE_GRASS);
                        output.accept(ModBlocks.BROWN_GRASS);
                        output.accept(ModBlocks.BLACK_GRASS);
                        output.accept(ModBlocks.WHITE_GRASS);
                        output.accept(ModBlocks.RAINBOW_GRASS);
                        output.accept(ModBlocks.MAGENTA_SAND);
                        output.accept(ModBlocks.GREEN_SAND);
                        output.accept(ModBlocks.RED_SAND);
                        output.accept(ModBlocks.PINK_SAND);
                        output.accept(ModBlocks.PURPLE_GRASS);
                        output.accept(ModBlocks.BLUE_SAND);
                        output.accept(ModBlocks.LIGHT_BLUE_SAND);
                        output.accept(ModBlocks.YELLOW_SAND);
                        output.accept(ModBlocks.GREY_SAND);
                        output.accept(ModBlocks.ORANGE_SAND);
                        output.accept(ModBlocks.BROWN_SAND);
                        output.accept(ModBlocks.BLACK_SAND);
                        output.accept(ModBlocks.WHITE_SAND);
                        output.accept(ModBlocks.RAINBOW_SAND);
                        output.accept(ModBlocks.QUARK_LOG);
                        output.accept(ModBlocks.QUARK_WOOD);
                        output.accept(ModBlocks.QUARK_PLANK);
                        output.accept(ModBlocks.QUARK_STRIPPED_LOG);
                        output.accept(ModBlocks.QUARK_STRIPPED_WOOD);
                        output.accept(ModBlocks.QUARK_STAIRS);
                        output.accept(ModBlocks.QUARK_FENCE);
                        output.accept(ModBlocks.QUARK_BUTTON);
                        output.accept(ModBlocks.QUARK_FENCE_GATE);
                        output.accept(ModBlocks.QUARK_SLAB);
                        output.accept(ModBlocks.QUARK_PRESSURE_PLATE);
                        output.accept(ModBlocks.QUARK_WALL);
                        output.accept(ModBlocks.QUARK_DOOR);
                        output.accept(ModBlocks.QUARK_TRAP_DOOR);
                        output.accept(ModBlocks.LOW_GRADE_JADE);
                        output.accept(ModBlocks.QUARK_LEAVES);
                        output.accept(ModBlocks.QUARK_SAPLING);
                        output.accept(ModBlocks.LOW_GRADE_HERB);
                        output.accept(ModBlocks.CHINESE_LAMP);
                        output.accept(ModBlocks.LOW_GRADE_JADE_FURNACE);
                    })
                    .build()
    );
    public static final Supplier<CreativeModeTab> CULTIVATOR_INGREDIENT_TAB = CREATIVE_MODE_TAB.register("cultivator_ingredient_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CULT_LOGO.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "cultivator_block_tab"))
                    .title(Component.translatable("creativetab.cultivatormod.cultivator_ingredient"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(ModItems.LOW_GRADE_JADE_STONE);
                        output.accept(ModItems.LOW_GRADE_HERB_CLUMP);
                        output.accept(ModItems.BLOOD_FROM_EARTH);
                        output.accept(ModItems.LOW_GRADE_GINSENG);
                        output.accept(ModItems.LOW_GRADE_SPIRIT_FLOWER_BUNDLE);
                        output.accept(ModItems.LOW_GRADE_SPIRIT_FLOWER_SEEDS);
                    })
                    .build()
    );

    public static final Supplier<CreativeModeTab> CULTIVATOR_EDIBLE_TAB = CREATIVE_MODE_TAB.register("cultivator_edible_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CULT_LOGO.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "cultivator_ingredient_tab"))
                    .title(Component.translatable("creativetab.cultivatormod.cultivator_edible"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(ModItems.LOW_GRADE_FOUNDATION_PILL);
                        output.accept(ModItems.LOW_GRADE_BLOOD_BEAD);
                        output.accept(ModItems.LOW_GRADE_BODY_AND_BONE_PILL);
                        output.accept(ModItems.LOW_GRADE_HEAVEN_AND_EARTH_PILL);
                        output.accept(ModItems.LOW_GRADE_SPIRIT_PILL);
                        output.accept(ModItems.LOW_GRADE_YANG_FRUIT);
                    })
                    .build()
    );

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }

}
