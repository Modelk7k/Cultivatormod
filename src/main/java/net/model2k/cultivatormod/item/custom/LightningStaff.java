package net.model2k.cultivatormod.item.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;


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
            level.broadcastEntityEvent(bolt,(byte) 0xA);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER,
                    SoundSource.WEATHER, 100f, 1f);
        }
        return super.useOn(context);
    }
}