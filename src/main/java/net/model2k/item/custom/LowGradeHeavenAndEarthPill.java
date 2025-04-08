package net.model2k.item.custom;


import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.model2k.cultivatormod.block.entity.JadeFurnaceEntity;
import net.model2k.cultivatormod.effect.ModEffects;
import net.model2k.cultivatormod.effect.YangQiEffect;

public class LowGradeHeavenAndEarthPill extends Item {

    static int usedTimes = 0;
    public LowGradeHeavenAndEarthPill(Properties properties) {
        super(properties);
    }
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (YangQiEffect.getYangQiTier() < 5 && usedTimes == 0 ) {
           if(Minecraft.getInstance().player.hasEffect(ModEffects.YANG_QI_EFFECT)) {
               YangQiEffect.setYangQiTier(YangQiEffect.getYangQiTier() + 1);
               Minecraft.getInstance().player.sendSystemMessage(Component.literal("Yang Qi Quality: " +YangQiEffect.getYangQiString()));
               JadeFurnaceEntity.qiEfficiency();
               usedTimes++;

           }
        return super.finishUsingItem(stack, level ,livingEntity);
        }if (usedTimes >= 1 ) {
            usedTimes = 0;
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }
}
