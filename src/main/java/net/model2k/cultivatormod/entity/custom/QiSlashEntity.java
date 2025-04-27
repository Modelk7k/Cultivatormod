package net.model2k.cultivatormod.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;
import net.model2k.cultivatormod.entity.ModEntities;
import net.model2k.cultivatormod.network.ModNetwork;
import net.neoforged.bus.api.BusBuilder;

import java.util.List;

public class QiSlashEntity extends Mob {
    private int lifeTicks = 40;
    private Player Owner;
    public QiSlashEntity(Level level, LivingEntity owner, Vec3 direction) {
        this(ModEntities.QI_SLASH.get(), level);
        Player player = (Player) owner;
        this.Owner = player;
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        int cost = data.getQi() - (10 - data.getQiQuality());
        if (cost >= 0) {
            qiCost(player,data,cost);
            Vec3 dirNormalized = direction.normalize();
            Vec3 offset = dirNormalized.scale(.5);
            Vec3 spawnPos = owner.position().add(0, owner.getEyeHeight() * .75, 0)
                    .add(offset);
            spawnPos = spawnPos.add(0, .5, 0);
            if (owner instanceof Mob) {
                spawnPos = spawnPos.add(0, 0, 0);
            }
            this.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
            this.setDeltaMovement(dirNormalized.scale(1.5));
            this.setYRot((float) (Math.toDegrees(Math.atan2(-dirNormalized.x, dirNormalized.z))));
            this.yRotO = this.getYRot();
            this.setNoGravity(true);
        }
    }
    public QiSlashEntity(EntityType entityEntityType, Level level) {super(entityEntityType, level);}
    public static BusBuilder createAttributes() {return null;}
    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) return;
        this.move(MoverType.SELF, this.getDeltaMovement());
        Vec3 start = this.position();
        Vec3 direction = this.getDeltaMovement().normalize();
        Vec3 frontOffset = direction.scale(1.5);
        Vec3 rightOffset = new Vec3(1, 0, 0);
        Vec3 leftOffset = new Vec3(-1, 0, 0);
        BlockHitResult frontHit = level().clip(new ClipContext(
                start, start.add(frontOffset),
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                this
        ));
        BlockHitResult rightHit = level().clip(new ClipContext(
                start, start.add(rightOffset),
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                this
        ));
        BlockHitResult leftHit = level().clip(new ClipContext(
                start, start.add(leftOffset),
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                this
        ));
        AABB hitbox = this.getBoundingBox().inflate(0.5);
        List<LivingEntity> entitiesInRange = level().getEntitiesOfClass(LivingEntity.class, hitbox, e ->
                e != Owner && e.isAlive() && !(e instanceof QiSlashEntity));
        for (LivingEntity entity : entitiesInRange) {
            DamageSource source = Owner != null ? this.damageSources().mobAttack(Owner) : this.damageSources().magic();
            entity.hurt(source, (float) Owner.getData(ModAttachments.PLAYER_DATA).getMaxQi()/3);
            this.kill();
            this.remove(RemovalReason.KILLED);
            break;
        }
        if (frontHit.getType() == HitResult.Type.BLOCK) {
            destroyBlockIfValid(frontHit);
        } else if (rightHit.getType() == HitResult.Type.BLOCK) {
            destroyBlockIfValid(rightHit);
        } else if (leftHit.getType() == HitResult.Type.BLOCK) {
            destroyBlockIfValid(leftHit);
        }
        if (--lifeTicks <= 0) {
            this.kill();
            this.remove(RemovalReason.KILLED);
        }
    }
    private void destroyBlockIfValid(BlockHitResult hitResult) {
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos hitPos = hitResult.getBlockPos();
            BlockState blockState = level().getBlockState(hitPos);
            if (!blockState.isAir() && blockState.getBlock() != Blocks.BEDROCK) {
                level().destroyBlock(hitPos, false);
            }
            this.kill();
            this.remove(RemovalReason.KILLED);
        }
    }
    @Override
    public boolean isPickable() {
        return true;
    }
    public void qiCost(Player player, PlayerData data, int cost){
        if(player.isCreative()){return;}
        else{
            data.setQi(cost);
            data.syncStatsToClient(player);
        }
    }
}