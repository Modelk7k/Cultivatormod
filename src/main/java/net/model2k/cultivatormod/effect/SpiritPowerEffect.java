package net.model2k.cultivatormod.effect;


import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.model2k.item.ModItems;


public class SpiritPowerEffect extends MobEffect {
    public static int spiritPowerCharge = 0;
    public static int spiritPower = 0;
    public static int maxSpiritPower = 10;
    protected SpiritPowerEffect(MobEffectCategory category, int color) {
        super(category, color);
    }
    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (Minecraft.getInstance().player.isHolding(ModItems.BAODING_BALLS.get())) {
            spiritPowerCharge++;
            if (spiritPowerCharge == 20) {
                spiritPowerCharge = 0;
                if (spiritPower != maxSpiritPower) {
                    spiritPower++;
                }
                livingEntity.sendSystemMessage(Component.literal("Spirit Power :" + getSpiritPower()));
            }
        }
        return super.applyEffectTick(livingEntity, amplifier);
    }
    private String spiritPowerString(int spiritPower) {
        return String.valueOf(spiritPower);
    }
    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
    public static int getSpiritPower() {
        return spiritPower;
    }
    public static void setSpiritPower(int spiritPower) {
        SpiritPowerEffect.spiritPower = spiritPower;
    }
    public static String getSpiritPowerString() {
        return Integer.toString(spiritPower);
    }
    public static int getMaxSpiritPower() {
        return maxSpiritPower;
    }
    public static void setMaxSpiritPower(int setMaxSpiritPower) {
        maxSpiritPower = setMaxSpiritPower;
    }
    public static String getMaxSpiritPowerString() {
        return Integer.toString(maxSpiritPower);
    }
}

