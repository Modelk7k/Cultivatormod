package net.model2k.cultivatormod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.model2k.cultivatormod.item.ModItems;
import org.jetbrains.annotations.NotNull;

public class SpiritFlower extends CropBlock {
    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
              Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
                      Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
                      Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
                      Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
                      Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0) };
    public SpiritFlower(Properties properties) {
        super(properties);
    }
    @Override
    protected @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE_BY_AGE[state.getValue(AGE)];
    }
    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return ModItems.LOW_GRADE_SPIRIT_FLOWER_SEEDS;
    }
    @Override
    public @NotNull IntegerProperty getAgeProperty() {
        return AGE;
    }
    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}