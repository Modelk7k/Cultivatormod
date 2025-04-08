package net.model2k.cultivatormod.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.FallingBlock;

public class SandBlock extends FallingBlock {
    public SandBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends FallingBlock> codec() {
        return null;
    }

    @Override
    protected void falling(FallingBlockEntity entity) {
        super.falling(entity);
    }
}
