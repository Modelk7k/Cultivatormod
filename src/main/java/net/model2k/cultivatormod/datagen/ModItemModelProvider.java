package net.model2k.cultivatormod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.block.ModBlocks;
import net.model2k.item.ModItems;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CultivatorMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        basicItem(ModItems.CULT_LOGO.get());
        basicItem(ModItems.LOW_GRADE_HERB_CLUMP.get());
        basicItem(ModItems.LOW_GRADE_GINSENG.get());
        basicItem(ModItems.BLOOD_FROM_EARTH.get());
        basicItem(ModItems.LIGHTNING_STAFF.get());
        basicItem(ModItems.LOW_GRADE_FOUNDATION_PILL.get());
        basicItem(ModItems.LOW_GRADE_BLOOD_BEAD.get());
        basicItem(ModItems.LOW_GRADE_SPIRIT_PILL.get());
        basicItem(ModItems.LOW_GRADE_BODY_AND_BONE_PILL.get());
        basicItem(ModItems.LOW_GRADE_HEAVEN_AND_EARTH_PILL.get());
        basicItem(ModItems.LOW_GRADE_JADE_STONE.get());
        basicItem(ModItems.TEST_FUEL.get());
        basicItem(ModItems.BAODING_BALLS.get());

        buttonItem(ModBlocks.QUARK_BUTTON, ModBlocks.QUARK_PLANK);
        fenceItem(ModBlocks.QUARK_FENCE, ModBlocks.QUARK_PLANK);
        wallItem(ModBlocks.QUARK_WALL, ModBlocks.QUARK_PLANK);


        basicItem(ModBlocks.QUARK_DOOR.asItem());

        basicItem(ModItems.LOW_GRADE_SPIRIT_FLOWER_SEEDS.asItem());
        basicItem(ModItems.LOW_GRADE_SPIRIT_FLOWER_BUNDLE.asItem());
        basicItem(ModItems.LOW_GRADE_YANG_FRUIT.asItem());


        handheldItem(ModItems.LOW_GRADE_JADE_PICKAXE);
        handheldItem(ModItems.LOW_GRADE_JADE_SWORD);
        handheldItem(ModItems.LOW_GRADE_JADE_AXE);
        handheldItem(ModItems.LOW_GRADE_JADE_HOE);
        handheldItem(ModItems.LOW_GRADE_JADE_SHOVEL);

        saplingItem(ModBlocks.QUARK_SAPLING);

        withExistingParent(ModItems.MINDLESS_SLIME_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }


    private ItemModelBuilder saplingItem(DeferredBlock<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID,"block/" + item.getId().getPath()));
    }

    public void buttonItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void fenceItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID,"item/" + item.getId().getPath()));
    }
}
