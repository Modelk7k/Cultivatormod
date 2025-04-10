package net.model2k.cultivatormod.effect;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.model2k.cultivatormod.item.ModItems;

public class QiEffect extends MobEffect {
    public static int QiCharge = 0;
    public static int QiChargeEfficiency = 1;
    public static int Qi = 0;
    public static int MaxQi = 10;
    protected QiEffect(MobEffectCategory category, int color) {
        super(category, color);
    }
    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if(Minecraft.getInstance().player.isHolding(ModItems.BAODING_BALLS.get())) {
            QiCharge++;
            if (QiCharge == 20) {
                QiCharge = 0;
                if (!(Qi >=MaxQi)) {
                    Qi = Qi + qiChargeEfficiency();
                }
                livingEntity.sendSystemMessage(Component.literal("Qi :" + qiString(Qi)));
            }
        }
        return super.applyEffectTick(livingEntity, amplifier);
    }
    private String qiString(int qi) {
        return String.valueOf(qi);
    }
    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
            return true;
    }
    public static int qiChargeEfficiency() {
        switch (MaxQi) {
            case 10:
                break;
            case 20:
                QiChargeEfficiency = 2;
                break;
            case 30:
                QiChargeEfficiency = 3;
                break;
            case 40:
                QiChargeEfficiency = 4;
                break;
            case 50:
                QiChargeEfficiency = 5;
                break;
            case 60:
                QiChargeEfficiency = 6;
                break;
            case 70:
                QiChargeEfficiency = 7;
                break;
            case 80:
                QiChargeEfficiency = 8;
                break;
            case 90:
                QiChargeEfficiency = 9;
                break;
            case 100:
                QiChargeEfficiency = 10;
                break;
        }
        return QiChargeEfficiency;
    }
   public static int getQi () {
        return Qi;
   }
   public static void setQi (int qi) {
        Qi = qi;
   }
   public static String getQiString () {
        return Integer.toString(Qi);
   }
   public static int getMaxQi () {
        return MaxQi;
    }
   public static void setMaxQi (int qi) {
        MaxQi = qi;
    }
   public static String getMaxQiString () {
        return Integer.toString(MaxQi);
    }
}
