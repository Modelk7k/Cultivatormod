package net.model2k.cultivatormod.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.block.ModBlocks;
import net.model2k.cultivatormod.block.custom.ChineseLamp;
import net.model2k.cultivatormod.block.custom.SpiritFlower;
import net.model2k.cultivatormod.block.custom.YangQiTree;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CultivatorMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {


        blockWithItem(ModBlocks.LOW_GRADE_JADE);
        blockWithItem(ModBlocks.QUARK_PLANK);
        blockWithItem(ModBlocks.BLACK_SAND);
        blockWithItem(ModBlocks.WHITE_SAND);
        blockWithItem(ModBlocks.RED_SAND);
        blockWithItem(ModBlocks.BLUE_SAND);
        blockWithItem(ModBlocks.LIGHT_BLUE_SAND);
        blockWithItem(ModBlocks.YELLOW_SAND);
        blockWithItem(ModBlocks.PINK_SAND);
        blockWithItem(ModBlocks.PURPLE_SAND);
        blockWithItem(ModBlocks.BROWN_SAND);
        blockWithItem(ModBlocks.ORANGE_SAND);
        blockWithItem(ModBlocks.GREEN_SAND);
        blockWithItem(ModBlocks.GREY_SAND);
        blockWithItem(ModBlocks.RAINBOW_SAND);
        blockWithItem(ModBlocks.MAGENTA_SAND);

        simpleBlockWithItem(ModBlocks.LOW_GRADE_HERB.get(), models().cross("low_grade_herb",modLoc("block/low_grade_herb")).renderType("cutout"));

        stairsBlock(ModBlocks.QUARK_STAIRS.get(), blockTexture(ModBlocks.QUARK_PLANK.get()));

        fenceBlock(ModBlocks.QUARK_FENCE.get(), blockTexture(ModBlocks.QUARK_PLANK.get()));

        fenceGateBlock(ModBlocks.QUARK_FENCE_GATE.get(), blockTexture(ModBlocks.QUARK_PLANK.get()));

        slabBlock(ModBlocks.QUARK_SLAB.get(), blockTexture(ModBlocks.QUARK_PLANK.get()), blockTexture(ModBlocks.QUARK_PLANK.get()));

        pressurePlateBlock(ModBlocks.QUARK_PRESSURE_PLATE.get(), blockTexture(ModBlocks.QUARK_PLANK.get()));

        buttonBlock(ModBlocks.QUARK_BUTTON.get(), blockTexture(ModBlocks.QUARK_PLANK.get()));

        wallBlock(ModBlocks.QUARK_WALL.get(), blockTexture(ModBlocks.QUARK_PLANK.get()));

        doorBlockWithRenderType(ModBlocks.QUARK_DOOR.get(), modLoc("block/quark_plank"), modLoc("block/quark_plank"), "cutout");
        trapdoorBlockWithRenderType(ModBlocks.QUARK_TRAP_DOOR.get(), modLoc("block/quark_plank"), true, "cutout");

        logBlock((RotatedPillarBlock) ModBlocks.QUARK_LOG.get());
        axisBlock(((RotatedPillarBlock) ModBlocks.QUARK_WOOD.get()), blockTexture(ModBlocks.QUARK_LOG.get()), blockTexture(ModBlocks.QUARK_LOG.get()));
        logBlock((RotatedPillarBlock) ModBlocks.QUARK_STRIPPED_LOG.get());
        axisBlock((RotatedPillarBlock) ModBlocks.QUARK_STRIPPED_WOOD.get(), blockTexture(ModBlocks.QUARK_STRIPPED_LOG.get()), blockTexture(ModBlocks.QUARK_STRIPPED_LOG.get()));
//Grass
        simpleBlockWithItem(ModBlocks.ALIEN_DIRT.get(), models().cubeAll("alien_dirt_block", modLoc("block/alien_dirt_block")));
        simpleBlockWithItem(ModBlocks.GREEN_GRASS.get(), models().cubeBottomTop("green_grass_block", modLoc("block/green_grass_block"), modLoc("block/alien_dirt_block"), modLoc("block/green_grass_block_top")));
        simpleBlockWithItem(ModBlocks.BLUE_GRASS.get(), models().cubeBottomTop("blue_grass_block", modLoc("block/blue_grass_block"), modLoc("block/alien_dirt_block"), modLoc("block/blue_grass_block_top")));
        simpleBlockWithItem(ModBlocks.LIGHT_BLUE_GRASS.get(), models().cubeBottomTop("light_blue_grass_block", modLoc("block/light_blue_grass_block"), modLoc("block/alien_dirt_block"), modLoc("block/light_blue_grass_block_top")));
        simpleBlockWithItem(ModBlocks.PURPLE_GRASS.get(), models().cubeBottomTop("purple_grass_block", modLoc("block/purple_grass_block"), modLoc("block/alien_dirt_block"), modLoc("block/purple_grass_block_top")));
        simpleBlockWithItem(ModBlocks.YELLOW_GRASS.get(), models().cubeBottomTop("yellow_grass_block", modLoc("block/yellow_grass_block"), modLoc("block/alien_dirt_block"), modLoc("block/yellow_grass_block_top")));
        simpleBlockWithItem(ModBlocks.ORANGE_GRASS.get(), models().cubeBottomTop("orange_grass_block", modLoc("block/orange_grass_block"), modLoc("block/alien_dirt_block"), modLoc("block/orange_grass_block_top")));
        simpleBlockWithItem(ModBlocks.RED_GRASS.get(), models().cubeBottomTop("red_grass_block", modLoc("block/red_grass_block"), modLoc("block/alien_dirt_block"), modLoc("block/red_grass_block_top")));
        simpleBlockWithItem(ModBlocks.PINK_GRASS.get(), models().cubeBottomTop("pink_grass_block", modLoc("block/pink_grass_block"), modLoc("block/alien_dirt_block"), modLoc("block/pink_grass_block_top")));
        simpleBlockWithItem(ModBlocks.GREY_GRASS.get(), models().cubeBottomTop("grey_grass_block", modLoc("block/grey_grass_block"), modLoc("block/alien_dirt_block"), modLoc("block/grey_grass_block_top")));
        simpleBlockWithItem(ModBlocks.WHITE_GRASS.get(), models().cubeBottomTop("white_grass_block", modLoc("block/white_grass_block"), modLoc("block/alien_dirt_block"), modLoc("block/white_grass_block_top")));
        simpleBlockWithItem(ModBlocks.BROWN_GRASS.get(), models().cubeBottomTop("brown_grass_block", modLoc("block/brown_grass_block"), modLoc("block/alien_dirt_block"), modLoc("block/brown_grass_block_top")));
        simpleBlockWithItem(ModBlocks.BLACK_GRASS.get(), models().cubeBottomTop("black_grass_block", modLoc("block/black_grass_block"), modLoc("block/alien_dirt_block"), modLoc("block/black_grass_block_top")));
        simpleBlockWithItem(ModBlocks.RAINBOW_GRASS.get(), models().cubeBottomTop("rainbow_grass_block", modLoc("block/rainbow_grass_block"), modLoc("block/alien_dirt_block"), modLoc("block/rainbow_grass_block_top")));
//BlockItems
        blockItem(ModBlocks.QUARK_LOG);
        blockItem(ModBlocks.QUARK_WOOD);
        blockItem(ModBlocks.QUARK_STRIPPED_LOG);
        blockItem(ModBlocks.QUARK_STAIRS);
        blockItem(ModBlocks.QUARK_TRAP_DOOR, "_bottom");
        blockItem(ModBlocks.QUARK_FENCE_GATE);
        blockItem(ModBlocks.QUARK_PRESSURE_PLATE);
        blockItem(ModBlocks.QUARK_SLAB);
        blockItem(ModBlocks.CHINESE_LAMP);
        blockItem(ModBlocks.QUARK_WOOD);
        blockItem(ModBlocks.QUARK_STRIPPED_WOOD);

        makeCrop((CropBlock) ModBlocks.LOW_GRADE_SPIRIT_FLOWER.get(), "low_grade_spirit_flower_stage", "low_grade_spirit_flower_stage");
        makeBush(((SweetBerryBushBlock) ModBlocks.LOW_GRADE_YANG_FRUIT_BUSH.get()), "low_grade_yang_fruit_bush_stage", "low_grade_yang_fruit_bush_stage");
        leavesBlock(ModBlocks.QUARK_LEAVES);
        saplingBlock(ModBlocks.QUARK_SAPLING);
        //simpleBlock(ModBlocks.OLD_SCHOOL_FIRE.get(), models().cross("old_school_fire", modLoc("block/fire_layer_0")).renderType("cutout"));
        customLamp();
    }
    private void saplingBlock(DeferredBlock<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }
    private void leavesBlock(DeferredBlock<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get()).getPath(), ResourceLocation.parse("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }
    public void makeBush(BushBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }
    private ConfiguredModel[] states(BlockState state, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().cross(modelName + state.getValue(YangQiTree.AGE),
                ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "block/" + textureName + state.getValue(YangQiTree.AGE))).renderType("cutout"));
        return models;
    }
    public void makeCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }
    private ConfiguredModel[] states(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((SpiritFlower) block).getAgeProperty()),
                ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "block/" + textureName + state.getValue(((SpiritFlower) block).getAgeProperty()))).renderType("cutout"));
        return models;
    }
    private void customLamp() {
        getVariantBuilder(ModBlocks.CHINESE_LAMP.get()).forAllStates(state -> {
            if (state.getValue(ChineseLamp.CLICKED)) {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll("chinese_lamp_lit",
                        ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "block/" + "chinese_lamp_lit")))};
            } else {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll("chinese_lamp_unlit",
                        ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "block/" + "chinese_lamp_unlit")))};
            }
        });
        simpleBlockItem(ModBlocks.CHINESE_LAMP.get(), models().cubeAll("chinese_lamp_lit",
                ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "block/" + "chinese_lamp_lit")));
    }
        private void blockWithItem (DeferredBlock < ? > deferredBlock) {
            simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
        }
        private void blockItem (DeferredBlock < ? > deferredBlock) {
            simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("cultivatormod:block/" + deferredBlock.getId().getPath()));
        }
        private void blockItem (DeferredBlock < ? > deferredBlock, String appendix){
            simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("cultivatormod:block/" + deferredBlock.getId().getPath() + appendix));
        }
    }