package net.model2k.cultivatormod.worldgen.tree;

import net.minecraft.world.level.block.grower.TreeGrower;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.worldgen.ModConfiguredFeatures;
import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower QUARK_TREE = new TreeGrower(CultivatorMod.MOD_ID + "quark" , Optional.empty(), Optional.of(ModConfiguredFeatures.QUARK_KEY), Optional.empty());
}