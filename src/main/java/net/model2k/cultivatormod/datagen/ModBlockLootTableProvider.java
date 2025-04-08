package net.model2k.cultivatormod.datagen;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.model2k.cultivatormod.block.ModBlocks;
import net.model2k.cultivatormod.block.custom.SpiritFlower;
import net.model2k.item.ModItems;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider( HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {


        this.dropSelf(ModBlocks.LOW_GRADE_JADE_FURNACE.get());
        this.dropSelf(ModBlocks.QUARK_LOG.get());
        this.dropSelf(ModBlocks.QUARK_WOOD.get());
        this.dropSelf(ModBlocks.QUARK_STRIPPED_WOOD.get());
        this.dropSelf(ModBlocks.QUARK_STRIPPED_LOG.get());
        this.dropSelf(ModBlocks.QUARK_PLANK.get());
        this.dropSelf(ModBlocks.QUARK_STAIRS.get());
        this.dropSelf(ModBlocks.QUARK_FENCE_GATE.get());
        this.dropSelf(ModBlocks.QUARK_BUTTON.get());
        this.dropSelf(ModBlocks.QUARK_FENCE.get());
        this.dropSelf(ModBlocks.QUARK_PRESSURE_PLATE.get());
        this.dropSelf(ModBlocks.QUARK_WALL.get());
        this.dropSelf(ModBlocks.QUARK_TRAP_DOOR.get());
        this.dropSelf(ModBlocks.CHINESE_LAMP.get());
        this.dropSelf(ModBlocks.QUARK_SAPLING.get());
        this.dropSelf(ModBlocks.LOW_GRADE_JADE.get());



        this.dropSelf(ModBlocks.ALIEN_DIRT.get());
        this.dropSelf(ModBlocks.GREEN_GRASS.get());
        this.dropSelf(ModBlocks.BLUE_GRASS.get());
        this.dropSelf(ModBlocks.LIGHT_BLUE_GRASS.get());
        this.dropSelf(ModBlocks.RED_GRASS.get());
        this.dropSelf(ModBlocks.PINK_GRASS.get());
        this.dropSelf(ModBlocks.GREY_GRASS.get());
        this.dropSelf(ModBlocks.WHITE_GRASS.get());
        this.dropSelf(ModBlocks.BLACK_GRASS.get());
        this.dropSelf(ModBlocks.ORANGE_GRASS.get());
        this.dropSelf(ModBlocks.YELLOW_GRASS.get());
        this.dropSelf(ModBlocks.RAINBOW_GRASS.get());
        this.dropSelf(ModBlocks.BROWN_GRASS.get());
        this.dropSelf(ModBlocks.PURPLE_GRASS.get());

        this.dropSelf(ModBlocks.BROWN_SAND.get());
        this.dropSelf(ModBlocks.GREEN_SAND.get());
        this.dropSelf(ModBlocks.MAGENTA_SAND.get());
        this.dropSelf(ModBlocks.RAINBOW_SAND.get());
        this.dropSelf(ModBlocks.RED_SAND.get());
        this.dropSelf(ModBlocks.BLACK_SAND.get());
        this.dropSelf(ModBlocks.GREY_SAND.get());
        this.dropSelf(ModBlocks.WHITE_SAND.get());
        this.dropSelf(ModBlocks.BLUE_SAND.get());
        this.dropSelf(ModBlocks.LIGHT_BLUE_SAND.get());
        this.dropSelf(ModBlocks.YELLOW_SAND.get());
        this.dropSelf(ModBlocks.PURPLE_SAND.get());
        this.dropSelf(ModBlocks.PINK_SAND.get());
        this.dropSelf(ModBlocks.MAGENTA_SAND.get());
        this.dropSelf(ModBlocks.ORANGE_SAND.get());






        this.add(ModBlocks.QUARK_LEAVES.get(), block ->
                createLeavesDrops(block,ModBlocks.QUARK_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        add(ModBlocks.QUARK_DOOR.get(),
                block -> createDoorTable(ModBlocks.QUARK_DOOR.get()));
        add(ModBlocks.QUARK_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.QUARK_SLAB.get()));
        add(ModBlocks.LOW_GRADE_HERB.get(),
                block -> createOreDrop(ModBlocks.LOW_GRADE_HERB.get(), ModItems.LOW_GRADE_HERB_CLUMP.get()));

        LootItemCondition.Builder lootItemConditionBuilder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.LOW_GRADE_SPIRIT_FLOWER.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SpiritFlower.AGE, 3));

        this.add(ModBlocks.LOW_GRADE_SPIRIT_FLOWER.get(), this.createCropDrops(ModBlocks.LOW_GRADE_SPIRIT_FLOWER.get(),
                ModItems.LOW_GRADE_SPIRIT_FLOWER_BUNDLE.get(), ModItems.LOW_GRADE_SPIRIT_FLOWER_SEEDS.get(), lootItemConditionBuilder));



        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        this.add(ModBlocks.LOW_GRADE_YANG_FRUIT_BUSH.get(), block -> this.applyExplosionDecay(
            block, LootTable.lootTable().withPool(LootPool.lootPool().when(
            LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.LOW_GRADE_YANG_FRUIT_BUSH.get())
            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 3))
            ).add(LootItem.lootTableItem(ModItems.LOW_GRADE_YANG_FRUIT.get()))
            .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
            .apply(ApplyBonusCount.addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
            ).withPool(LootPool.lootPool().when(
            LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.LOW_GRADE_YANG_FRUIT_BUSH.get())
            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 2))
            ).add(LootItem.lootTableItem(ModItems.LOW_GRADE_YANG_FRUIT.get()))
            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
            .apply(ApplyBonusCount.addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
            )));
}


    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
