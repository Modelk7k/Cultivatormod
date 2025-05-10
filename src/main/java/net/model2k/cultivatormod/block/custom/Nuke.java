package net.model2k.cultivatormod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class Nuke extends TntBlock {
    public Nuke(Properties properties) {
        super(properties);
    }
    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        createCustomExplosion(level, player, pos.getX(), pos.getY(), pos.getZ(), 50);
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }
    public static void createCustomExplosion(Level level, Entity source, double x, double y, double z, float power) {
    }
}