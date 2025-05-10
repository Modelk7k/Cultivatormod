package net.model2k.cultivatormod.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import org.jetbrains.annotations.NotNull;

public class GrassSpreadBlock extends SpreadingSnowyDirtBlock {
    public GrassSpreadBlock(Properties properties) {
        super(properties);
    }
    @Override
    protected @NotNull MapCodec<? extends SpreadingSnowyDirtBlock> codec() {
        return null;
    }
}