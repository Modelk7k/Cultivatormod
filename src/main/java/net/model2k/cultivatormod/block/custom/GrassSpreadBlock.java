package net.model2k.cultivatormod.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;

public class GrassSpreadBlock extends SpreadingSnowyDirtBlock {
    public GrassSpreadBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends SpreadingSnowyDirtBlock> codec() {
        return null;
    }


}
