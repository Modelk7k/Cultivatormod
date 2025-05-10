package net.model2k.cultivatormod.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.FallingBlock;
import org.jetbrains.annotations.NotNull;

public class SandBlock extends FallingBlock {
    public SandBlock(Properties properties) {
        super(properties);
    }
    @Override
    protected @NotNull MapCodec<? extends FallingBlock> codec() {
        return null;
    }
    @Override
    protected void falling(@NotNull FallingBlockEntity entity) {
        super.falling(entity);
    }
}