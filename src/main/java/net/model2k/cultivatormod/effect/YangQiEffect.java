package net.model2k.cultivatormod.effect;


import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class YangQiEffect extends MobEffect {
   public static int Tier = 0;
    protected YangQiEffect(MobEffectCategory category, int color) {
        super(category, color);
    }
    private String yangQiString(int qi) {
        return String.valueOf(qi);
    }
    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
    public static int getYangQiTier() {
        return Tier;
    }
    public static void setYangQiTier(int yangQiTier) {
        Tier = yangQiTier;
    }
    public static String getYangQiString() {
        return Integer.toString(Tier);
    }
}
