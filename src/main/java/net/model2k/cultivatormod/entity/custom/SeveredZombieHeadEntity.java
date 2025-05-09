package net.model2k.cultivatormod.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.model2k.cultivatormod.entity.ModEntities;

public class SeveredZombieHeadEntity extends Entity {
    private final Zombie zombie;
    private float yawVelocity = 10f;
    private float pitchVelocity = 7f;
    private float rollVelocity = 5f;
    private float roll = 0f;  // Custom roll variable since vanilla doesn't support it visually
    public SeveredZombieHeadEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.zombie = null;
    }
    public SeveredZombieHeadEntity(Zombie zombie, Level level) {
        super(ModEntities.SEVERED_ZOMBIE_HEAD.get(), level);
        this.setNoGravity(false);  // Enable gravity
        this.zombie = zombie;
        Vec3 launchDirection = new Vec3(0, 0.3, 0);  // Slightly weaker upward force
        this.setDeltaMovement(launchDirection); // Apply launch velocity
    }
    @Override
    public void tick() {
        super.tick();
        Vec3 velocity = this.getDeltaMovement().add(0, -0.05, 0);
        this.setDeltaMovement(velocity);
        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setYRot(this.getYRot() + yawVelocity);
        this.setXRot(this.getXRot() + pitchVelocity);
        roll += rollVelocity;
        yawVelocity *= 0.98f;
        pitchVelocity *= 0.98f;
        rollVelocity *= 0.98f;
        if (!this.level().isClientSide && this.onGround()) {
            BlockPos below = this.blockPosition();
            if (level().getBlockState(below).canBeReplaced()) {
                level().setBlock(below, Blocks.ZOMBIE_HEAD.defaultBlockState(), 3);
            }
            this.discard();
        }
    }
    @Override
    public AABB makeBoundingBox() {
        return new AABB(this.getX() - 0.25, this.getY(), this.getZ() - 0.25,
                this.getX() + 0.25, this.getY() + 0.5, this.getZ() + 0.25);
    }
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }
    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
    }
    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
    }
}