package net.model2k.cultivatormod.item.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.model2k.cultivatormod.block.ModBlocks;
import net.neoforged.fml.common.Mod;


public class LightningStaff extends Item {

    public LightningStaff(Properties properties) {
        super(properties);
    }
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (!level.isClientSide()) {
            LightningBolt bolt = EntityType.LIGHTNING_BOLT.create((ServerLevel) level, LightningBolt::tick, context.getClickedPos(), MobSpawnType.NATURAL, true, true);
            level.addFreshEntity(bolt);
            level.broadcastEntityEvent(bolt, (byte) 0xA);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER,
                    SoundSource.WEATHER, 100f, 1f);
        }if (!level.isClientSide()) {
            for (int i = 0; i < 15; i++) {
            switch (i) {
                case 0:
                    if (level.getBlockState(context.getClickedPos()).is(ModBlocks.MAGENTA_SAND)) {
                        level.setBlock(context.getClickedPos(), Blocks.MAGENTA_STAINED_GLASS.defaultBlockState(), 3);
                    }
                    break;
                case 1:
                    if (level.getBlockState(context.getClickedPos()).is(ModBlocks.RAINBOW_SAND)) {
                        level.setBlock(context.getClickedPos(), Blocks.GLASS.defaultBlockState(), 3);
                    }
                    break;
                case 2:
                    if (level.getBlockState(context.getClickedPos()).is(ModBlocks.GREEN_SAND)) {
                        level.setBlock(context.getClickedPos(), Blocks.GREEN_STAINED_GLASS.defaultBlockState(), 3);
                    }
                    break;
                case 3:
                    if (level.getBlockState(context.getClickedPos()).is(ModBlocks.GREY_SAND)) {
                        level.setBlock(context.getClickedPos(), Blocks.GLASS.defaultBlockState(), 3);
                    }
                    break;
                case 4:
                    if (level.getBlockState(context.getClickedPos()).is(ModBlocks.ORANGE_SAND)) {
                        level.setBlock(context.getClickedPos(), Blocks.ORANGE_STAINED_GLASS.defaultBlockState(), 3);
                    }
                    break;
                case 5:
                    if (level.getBlockState(context.getClickedPos()).is(ModBlocks.RED_SAND)) {
                        level.setBlock(context.getClickedPos(), Blocks.RED_STAINED_GLASS.defaultBlockState(), 3);
                    }
                    break;
                case 6:
                    if (level.getBlockState(context.getClickedPos()).is(ModBlocks.BLACK_SAND)) {
                        level.setBlock(context.getClickedPos(), Blocks.BLACK_STAINED_GLASS.defaultBlockState(), 3);
                    }
                    break;
                case 7:
                    if (level.getBlockState(context.getClickedPos()).is(ModBlocks.BROWN_SAND)) {
                        level.setBlock(context.getClickedPos(), Blocks.BROWN_STAINED_GLASS.defaultBlockState(), 3);
                    }
                    break;
                case 8:
                    if (level.getBlockState(context.getClickedPos()).is(ModBlocks.PINK_SAND)) {
                        level.setBlock(context.getClickedPos(), Blocks.PINK_STAINED_GLASS.defaultBlockState(), 3);
                    }
                    break;
                case 9:
                    if (level.getBlockState(context.getClickedPos()).is(ModBlocks.BLUE_SAND)) {
                        level.setBlock(context.getClickedPos(), Blocks.BLUE_STAINED_GLASS.defaultBlockState(), 3);
                    }
                    break;
                case 10:
                    if (level.getBlockState(context.getClickedPos()).is(ModBlocks.LIGHT_BLUE_SAND)) {
                        level.setBlock(context.getClickedPos(), Blocks.LIGHT_BLUE_STAINED_GLASS.defaultBlockState(), 3);
                    }
                    break;
                case 11:
                    if (level.getBlockState(context.getClickedPos()).is(ModBlocks.PURPLE_SAND)) {
                        level.setBlock(context.getClickedPos(), Blocks.PURPLE_STAINED_GLASS.defaultBlockState(), 3);
                    }
                    break;
                case 12:
                    if (level.getBlockState(context.getClickedPos()).is(ModBlocks.WHITE_SAND)) {
                        level.setBlock(context.getClickedPos(), Blocks.WHITE_STAINED_GLASS.defaultBlockState(), 3);
                    }
                    break;
                case 13:
                    if (level.getBlockState(context.getClickedPos()).is(ModBlocks.YELLOW_SAND)) {
                        level.setBlock(context.getClickedPos(), Blocks.YELLOW_STAINED_GLASS.defaultBlockState(), 3);
                    }
                    break;
                case 14:
                    if (level.getBlockState(context.getClickedPos()).is(Blocks.SAND)) {
                        level.setBlock(context.getClickedPos(), Blocks.GLASS.defaultBlockState(), 3);
                    }
                 }
            }
        }
        return InteractionResult.SUCCESS;
    }
}