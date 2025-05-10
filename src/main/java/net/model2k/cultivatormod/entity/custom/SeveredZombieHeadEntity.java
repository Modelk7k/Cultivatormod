package net.model2k.cultivatormod.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.model2k.cultivatormod.entity.ModEntities;
import org.jetbrains.annotations.NotNull;

public class SeveredZombieHeadEntity extends Entity {
    public SeveredZombieHeadEntity(EntityType<?> type, Level level) {
        super(type, level);
    }
    public SeveredZombieHeadEntity(Level level) {
        super(ModEntities.SEVERED_ZOMBIE_HEAD.get(), level);
        this.setNoGravity(false);
        Vec3 launchDirection = new Vec3(0, 0.3, 0);
        this.setDeltaMovement(launchDirection);
    }
    @Override
    public void tick() {
        super.tick();
        Vec3 velocity = this.getDeltaMovement().add(0, -0.05, 0);
        this.setDeltaMovement(velocity);
        this.move(MoverType.SELF, this.getDeltaMovement());
        float yawVelocity = 10f;
        this.setYRot(this.getYRot() + yawVelocity);
        float pitchVelocity = 7f;
        this.setXRot(this.getXRot() + pitchVelocity);
        if (!this.level().isClientSide && this.onGround()) {
            BlockPos below = this.blockPosition();
            if (level().getBlockState(below).canBeReplaced()) {
                level().setBlock(below, Blocks.ZOMBIE_HEAD.defaultBlockState(), 3);
            }
            this.discard();
        }
    }
    @Override
    public @NotNull AABB makeBoundingBox() {
        return new AABB(this.getX() - 0.25, this.getY(), this.getZ() - 0.25,
                this.getX() + 0.25, this.getY() + 0.5, this.getZ() + 0.25);
    }
    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
    }
    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag compound) {
    }
    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag compound) {
    }
}