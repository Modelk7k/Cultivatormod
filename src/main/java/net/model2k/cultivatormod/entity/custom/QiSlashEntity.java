package net.model2k.cultivatormod.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.model2k.cultivatormod.entity.ModEntities;

import java.util.List;

public class QiSlashEntity extends Entity {
    private int lifeTicks = 40; // Lifetime in ticks (2 seconds)
    private LivingEntity owner;

    public QiSlashEntity(EntityType<? extends Entity> type, Level level) {
        super(type, level);
    }
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }
    public QiSlashEntity(Level level, LivingEntity owner, Vec3 direction) {
        this(ModEntities.QI_SLASH.get(), level);
        this.owner = owner;
        this.setPos(owner.getX(), owner.getEyeY(), owner.getZ());
        this.setDeltaMovement(direction.normalize().scale(1.5)); // Customize speed
    }
    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {}
    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {}
    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            Vec3 movement = this.getDeltaMovement();
            this.moveTo(this.position().add(movement));
            AABB hitbox = this.getBoundingBox().inflate(0.75);
            List<LivingEntity> targets = level().getEntitiesOfClass(LivingEntity.class, hitbox, e ->
                    e != owner && e.isAlive() && !(e instanceof Player && owner instanceof Player));
            for (LivingEntity target : targets) {
                DamageSource source = owner != null
                        ? this.damageSources().mobAttack(owner)
                        : this.damageSources().magic();
                target.hurt(source, 6.0f);
                this.discard();
                break;
            }
            if (--lifeTicks <= 0) {
                this.discard();
            }
        }
    }
    @Override
    public boolean isPickable() {
        return true;
    }
}