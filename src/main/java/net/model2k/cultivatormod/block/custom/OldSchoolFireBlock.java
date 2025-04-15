package net.model2k.cultivatormod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class OldSchoolFireBlock extends FireBlock {
    public OldSchoolFireBlock(Properties properties) {
        super(properties);
    }
    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.tick(state, level, pos, random);
        spreadFireAround(level, pos, random, Integer.MAX_VALUE); // Higher chance = more aggressive
        for (int i = 0; i < 3; i++) {
            level.scheduleTick(pos, this, i + 1);
        }
    }
    private void spreadFireAround(ServerLevel level, BlockPos pos, RandomSource random, int chance) {
        for (Direction dir : Direction.values()) {
            BlockPos targetPos = pos.relative(dir);
            if (canBurn(level.getLevel(), targetPos)) {
                if (random.nextInt(100) < chance) {
                    level.setBlockAndUpdate(targetPos, this.defaultBlockState());
                }
            }
        }
    }
    private boolean canBurn(Level level, BlockPos pos) {
        return level.getBlockState(pos).isFlammable(level, pos, Direction.UP);
    }
}