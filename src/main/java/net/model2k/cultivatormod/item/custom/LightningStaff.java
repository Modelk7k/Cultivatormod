package net.model2k.cultivatormod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.model2k.cultivatormod.block.ModBlocks;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;
import net.model2k.cultivatormod.network.ModNetwork;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Map;

public class LightningStaff extends Item {
    private static final Map<Block, Block> SAND_TO_GLASS = Map.ofEntries(
            Map.entry(ModBlocks.MAGENTA_SAND.get(), Blocks.MAGENTA_STAINED_GLASS),
            Map.entry(ModBlocks.GREEN_SAND.get(), Blocks.GREEN_STAINED_GLASS),
            Map.entry(ModBlocks.GREY_SAND.get(), Blocks.GLASS),
            Map.entry(ModBlocks.ORANGE_SAND.get(), Blocks.ORANGE_STAINED_GLASS),
            Map.entry(ModBlocks.RED_SAND.get(), Blocks.RED_STAINED_GLASS),
            Map.entry(ModBlocks.BLACK_SAND.get(), Blocks.BLACK_STAINED_GLASS),
            Map.entry(ModBlocks.BROWN_SAND.get(), Blocks.BROWN_STAINED_GLASS),
            Map.entry(ModBlocks.PINK_SAND.get(), Blocks.PINK_STAINED_GLASS),
            Map.entry(ModBlocks.BLUE_SAND.get(), Blocks.BLUE_STAINED_GLASS),
            Map.entry(ModBlocks.LIGHT_BLUE_SAND.get(), Blocks.LIGHT_BLUE_STAINED_GLASS),
            Map.entry(ModBlocks.PURPLE_SAND.get(), Blocks.PURPLE_STAINED_GLASS),
            Map.entry(ModBlocks.WHITE_SAND.get(), Blocks.WHITE_STAINED_GLASS),
            Map.entry(ModBlocks.YELLOW_SAND.get(), Blocks.YELLOW_STAINED_GLASS),
            Map.entry(Blocks.SAND, Blocks.GLASS)
    );
    private static final List<Block> RAINBOW_GLASS = List.of(
            Blocks.RED_STAINED_GLASS, Blocks.GREEN_STAINED_GLASS, Blocks.BLUE_STAINED_GLASS,
            Blocks.YELLOW_STAINED_GLASS, Blocks.ORANGE_STAINED_GLASS, Blocks.PURPLE_STAINED_GLASS,
            Blocks.LIGHT_BLUE_STAINED_GLASS, Blocks.PINK_STAINED_GLASS, Blocks.GLASS
    );
    private long lastUseTime = 0;
    public LightningStaff(Properties properties) {
        super(properties);
    }
    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!(context.getPlayer() instanceof ServerPlayer player)) {
            return InteractionResult.PASS;
        }
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        BlockPos pos = context.getClickedPos();
        int maxQi = data.getMaxQi();
        int damage = maxQi / 3;
        int qiCost = Math.max(damage / 2, 10);
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUseTime >= 1000 && !level.isClientSide() && data.getQi() - qiCost >= 0 || player.isCreative() && !level.isClientSide()) {
            lastUseTime = currentTime;
            if(!player.isCreative()) {
                data.setQi(data.getQi() - qiCost);
                ModNetwork.sendSyncPlayerData(player);
            }
            List<BlockPos> boltPositions = new java.util.ArrayList<>();
            int radius = 2;
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (Math.abs(dx) + Math.abs(dz) <= radius) {
                        boltPositions.add(pos.offset(dx, 0, dz));
                    }
                }
            }
            for (BlockPos boltPos : boltPositions) {
                LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
                if (bolt != null) {
                    bolt.moveTo(boltPos.getX(), boltPos.getY(), boltPos.getZ());
                    bolt.setVisualOnly(false);
                    level.addFreshEntity(bolt);
                    level.broadcastEntityEvent(bolt, (byte) 0xA);
                    List<Entity> nearbyEntities = level.getEntitiesOfClass(Entity.class, bolt.getBoundingBox().inflate(5));
                    for (Entity entity : nearbyEntities) {
                        entity.hurt(player.damageSources().magic(), damage);
                    }
                }
                BlockPos targetPos = boltPos.below();
                BlockState targetState = level.getBlockState(targetPos);
                Block targetBlock = targetState.getBlock();
                if (targetBlock == ModBlocks.RAINBOW_SAND.get()) {
                    Block randomGlass = RAINBOW_GLASS_COLORS[level.random.nextInt(RAINBOW_GLASS_COLORS.length)];
                    level.setBlock(targetPos, randomGlass.defaultBlockState(), 3);
                } else if (SAND_TO_GLASS.containsKey(targetBlock)) {
                    level.setBlock(targetPos, SAND_TO_GLASS.get(targetBlock).defaultBlockState(), 3);
                }
                BlockPos.betweenClosed(boltPos.offset(-1, -1, -1), boltPos.offset(1, 1, 1)).forEach(p -> {
                    Block block = level.getBlockState(p).getBlock();
                    if (block == Blocks.BEDROCK) {
                        return;
                    }
                    if (!SAND_TO_GLASS.containsKey(block) && block != ModBlocks.RAINBOW_SAND.get() && !RAINBOW_GLASS.contains(block)) {
                        level.destroyBlock(p, true);
                    }
                });
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER, .5f, 1f);
                ((ServerLevel) level).sendParticles(
                        ParticleTypes.ELECTRIC_SPARK,
                        boltPos.getX() + 0.5, boltPos.getY() + 1, boltPos.getZ() + 0.5,
                        30, 0.5, 1, 0.5, 0.1
                );
            }
            BlockState clicked = level.getBlockState(pos);
            Block clickedBlock = clicked.getBlock();
            if (clickedBlock == ModBlocks.RAINBOW_SAND.get()) {
                Block randomGlass = RAINBOW_GLASS_COLORS[level.random.nextInt(RAINBOW_GLASS_COLORS.length)];
                level.setBlock(pos, randomGlass.defaultBlockState(), 3);
            } else if (SAND_TO_GLASS.containsKey(clickedBlock)) {
                level.setBlock(pos, SAND_TO_GLASS.get(clickedBlock).defaultBlockState(), 3);
            }
        }
        return InteractionResult.SUCCESS;
    }
    private static final Block[] RAINBOW_GLASS_COLORS = {
            Blocks.RED_STAINED_GLASS, Blocks.GREEN_STAINED_GLASS, Blocks.BLUE_STAINED_GLASS,
            Blocks.YELLOW_STAINED_GLASS, Blocks.ORANGE_STAINED_GLASS, Blocks.PURPLE_STAINED_GLASS,
            Blocks.LIGHT_BLUE_STAINED_GLASS, Blocks.PINK_STAINED_GLASS
    };
    @Override
    public boolean canAttackBlock(@NotNull BlockState state, @NotNull Level level, BlockPos pos, Player player) {
        return player.position().distanceTo(pos.getCenter()) < 5;
    }
}