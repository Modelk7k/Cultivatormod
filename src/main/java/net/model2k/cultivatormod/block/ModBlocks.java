package net.model2k.cultivatormod.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.block.custom.*;
import net.model2k.cultivatormod.worldgen.tree.ModTreeGrowers;
import net.model2k.cultivatormod.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CultivatorMod.MOD_ID);



    //Ores
    public static final DeferredBlock<Block> LOW_GRADE_JADE = registerBlock("low_grade_jade",
            () -> new DropExperienceBlock(UniformInt.of(2, 4), BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().sound(SoundType.STONE).strength(.5f, 1f)));



    //Plants
    public static final DeferredBlock<BushBlock> LOW_GRADE_HERB = registerBlock("low_grade_herb",
            () -> new LowGradeHerb(BlockBehaviour.Properties.of().instabreak().noCollission()));

    public static final DeferredBlock<Block> LOW_GRADE_SPIRIT_FLOWER = BLOCKS.register("low_grade_spirit_flower",
            ()-> new SpiritFlower(BlockBehaviour.Properties.ofFullCopy(Blocks.BEETROOTS)));

    public static final DeferredBlock<Block> LOW_GRADE_YANG_FRUIT_BUSH = BLOCKS.register("low_grade_yang_fruit_bush",
            () -> new YangQiTree(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));



    //TreeStuff
    public static final DeferredBlock<Block> QUARK_LEAVES = registerBlock("quark_leaves",
            () -> new CustomLeavesBlock(BlockBehaviour.Properties.of().strength(0.2f, 0.2f).requiresCorrectToolForDrops().sound(SoundType.CHERRY_LEAVES).ignitedByLava().noOcclusion()));

    public static final DeferredBlock<Block> QUARK_SAPLING = registerBlock("quark_sapling",
            () -> new SaplingBlock(ModTreeGrowers.QUARK_TREE,BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).instabreak().sound(SoundType.BAMBOO_SAPLING)));

    public static final DeferredBlock<RotatedPillarBlock> QUARK_LOG = registerBlock("quark_log",
            () -> new FlamableRotatingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_LOG).strength(1f, 1f).sound(SoundType.WOOD).ignitedByLava()));

    public static final DeferredBlock<RotatedPillarBlock> QUARK_WOOD = registerBlock("quark_wood",
            () -> new FlamableRotatingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_WOOD).strength(1f, 1f).sound(SoundType.WOOD).ignitedByLava()));

    public static final DeferredBlock<RotatedPillarBlock> QUARK_STRIPPED_WOOD = registerBlock("quark_stripped_wood",
            () -> new FlamableRotatingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_ACACIA_WOOD).strength(1f, 1f).requiresCorrectToolForDrops().sound(SoundType.WOOD).ignitedByLava()));

    public static final DeferredBlock<Block> QUARK_PLANK = registerBlock("quark_plank",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(1f, 1f).requiresCorrectToolForDrops().sound(SoundType.WOOD)));

    public static final DeferredBlock<RotatedPillarBlock> QUARK_STRIPPED_LOG = registerBlock("quark_stripped_log",
            () -> new FlamableRotatingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_ACACIA_LOG).strength(1f, 1f).requiresCorrectToolForDrops().sound(SoundType.WOOD)));



    //WoodStuff

    public static final DeferredBlock<StairBlock> QUARK_STAIRS = registerBlock("quark_stairs",
            () -> new StairBlock(ModBlocks.QUARK_PLANK.get().defaultBlockState(), BlockBehaviour.Properties.of().strength(1f, 1f).requiresCorrectToolForDrops().sound(SoundType.WOOD)));

    public static final DeferredBlock<SlabBlock> QUARK_SLAB = registerBlock("quark_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().strength(1f, 1f).requiresCorrectToolForDrops().sound(SoundType.WOOD)));

    public static final DeferredBlock<FenceBlock> QUARK_FENCE = registerBlock("quark_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of().strength(1f, 1f).requiresCorrectToolForDrops().sound(SoundType.WOOD)));

    public static final DeferredBlock<FenceGateBlock> QUARK_FENCE_GATE = registerBlock("quark_fence_gate",
            () -> new FenceGateBlock(WoodType.ACACIA, BlockBehaviour.Properties.of().strength(1f, 1f).requiresCorrectToolForDrops().sound(SoundType.WOOD)));

    public static final DeferredBlock<ButtonBlock> QUARK_BUTTON = registerBlock("quark_button",
            () -> new ButtonBlock(BlockSetType.IRON, 20, BlockBehaviour.Properties.of().strength(1f, 1f).requiresCorrectToolForDrops().sound(SoundType.WOOD)));

    public static final DeferredBlock<PressurePlateBlock> QUARK_PRESSURE_PLATE = registerBlock("quark_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().strength(1f, 1f).requiresCorrectToolForDrops().sound(SoundType.WOOD)));

    public static final DeferredBlock<WallBlock> QUARK_WALL = registerBlock("quark_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(1f, 1f).requiresCorrectToolForDrops().sound(SoundType.WOOD)));

    public static final DeferredBlock<DoorBlock> QUARK_DOOR = registerBlock("quark_door",
            () -> new DoorBlock(BlockSetType.ACACIA, BlockBehaviour.Properties.of().strength(1f, 1f).requiresCorrectToolForDrops().sound(SoundType.WOOD)));

    public static final DeferredBlock<TrapDoorBlock> QUARK_TRAP_DOOR = registerBlock("quark_trap_door",
            () -> new TrapDoorBlock(BlockSetType.ACACIA, BlockBehaviour.Properties.of().strength(1f, 1f).requiresCorrectToolForDrops().sound(SoundType.WOOD)));



//Sand Blocks

public static final DeferredBlock<Block> RED_SAND = registerBlock("red_sand",
        () -> new SandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).sound(SoundType.SAND)));

    public static final DeferredBlock<Block> YELLOW_SAND = registerBlock("yellow_sand",
            () -> new SandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).sound(SoundType.SAND)));

    public static final DeferredBlock<Block> PINK_SAND = registerBlock("pink_sand",
            () -> new SandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).sound(SoundType.SAND)));

    public static final DeferredBlock<Block> PURPLE_SAND = registerBlock("purple_sand",
            () -> new SandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).sound(SoundType.SAND)));

    public static final DeferredBlock<Block> BLUE_SAND = registerBlock("blue_sand",
            () -> new SandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).sound(SoundType.SAND)));

    public static final DeferredBlock<Block> LIGHT_BLUE_SAND = registerBlock("light_blue_sand",
            () -> new SandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).sound(SoundType.SAND)));

    public static final DeferredBlock<Block> BROWN_SAND = registerBlock("brown_sand",
            () -> new SandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).sound(SoundType.SAND)));

    public static final DeferredBlock<Block> BLACK_SAND = registerBlock("black_sand",
            () -> new SandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).sound(SoundType.SAND)));

    public static final DeferredBlock<Block> GREY_SAND = registerBlock("grey_sand",
            () -> new SandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).sound(SoundType.SAND)));

    public static final DeferredBlock<Block> WHITE_SAND = registerBlock("white_sand",
            () -> new SandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).sound(SoundType.SAND)));

    public static final DeferredBlock<Block> ORANGE_SAND = registerBlock("orange_sand",
            () -> new SandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).sound(SoundType.SAND)));

    public static final DeferredBlock<Block> RAINBOW_SAND = registerBlock("rainbow_sand",
            () -> new SandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).sound(SoundType.SAND)));

    public static final DeferredBlock<Block> MAGENTA_SAND = registerBlock("magenta_sand",
            () -> new SandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).sound(SoundType.SAND)));

    public static final DeferredBlock<Block> GREEN_SAND = registerBlock("green_sand",
            () -> new SandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).sound(SoundType.SAND)));

//GrassBlocks

    public static final DeferredBlock<Block> ALIEN_DIRT = registerBlock("alien_dirt",
            () -> new GrassSpreadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).sound(SoundType.GRASS)));

    public static final DeferredBlock<Block> GREEN_GRASS = registerBlock("green_grass",
            () -> new GrassSpreadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).sound(SoundType.GRASS)));

    public static final DeferredBlock<Block> RED_GRASS = registerBlock("red_grass",
            () -> new GrassSpreadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).sound(SoundType.GRASS)));

    public static final DeferredBlock<Block> PINK_GRASS = registerBlock("pink_grass",
            () -> new GrassSpreadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).sound(SoundType.GRASS)));

    public static final DeferredBlock<Block> BLUE_GRASS = registerBlock("blue_grass",
            () -> new GrassSpreadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).sound(SoundType.GRASS)));

    public static final DeferredBlock<Block> LIGHT_BLUE_GRASS = registerBlock("light_blue_grass",
            () -> new GrassSpreadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).sound(SoundType.GRASS)));

    public static final DeferredBlock<Block> PURPLE_GRASS = registerBlock("purple_grass",
            () -> new GrassSpreadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).sound(SoundType.GRASS)));

    public static final DeferredBlock<Block> ORANGE_GRASS = registerBlock("orange_grass",
            () -> new GrassSpreadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).sound(SoundType.GRASS)));

    public static final DeferredBlock<Block> YELLOW_GRASS = registerBlock("yellow_grass",
            () -> new GrassSpreadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).sound(SoundType.GRASS)));

    public static final DeferredBlock<Block> GREY_GRASS = registerBlock("grey_grass",
            () -> new GrassSpreadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).sound(SoundType.GRASS)));

    public static final DeferredBlock<Block> BROWN_GRASS = registerBlock("brown_grass",
            () -> new GrassSpreadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).sound(SoundType.GRASS)));

    public static final DeferredBlock<Block> BLACK_GRASS = registerBlock("black_grass",
            () -> new GrassSpreadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).sound(SoundType.GRASS)));

    public static final DeferredBlock<Block> WHITE_GRASS = registerBlock("white_grass",
            () -> new GrassSpreadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).sound(SoundType.GRASS)));

    public static final DeferredBlock<Block> RAINBOW_GRASS = registerBlock("rainbow_grass",
            () -> new GrassSpreadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).sound(SoundType.GRASS)));


    //Lamps
    public static final DeferredBlock<Block> CHINESE_LAMP = registerBlock("chinese_lamp",
            () -> new ChineseLamp(BlockBehaviour.Properties.of().strength(1f, 1f)
                    .lightLevel(state -> state.getValue(ChineseLamp.CLICKED) ? 15 : 0).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> LOW_GRADE_JADE_FURNACE = registerBlock("low_grade_jade_furnace",
            () -> new JadeFurnace(BlockBehaviour.Properties.of().noOcclusion()));

    public static final DeferredBlock<Block> NUKE = registerBlock("nuke",
            () -> new Nuke(BlockBehaviour.Properties.of()));


    public static final DeferredBlock<Block> OLD_SCHOOL_FIRE = registerBlock("old_school_fire",
            () -> new OldSchoolFireBlock(BlockBehaviour.Properties.of().speedFactor(5).noLootTable().randomTicks().replaceable().ignitedByLava().noCollission().instabreak().lightLevel(state -> 15)));


    //Junk

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }

}
