package net.model2k.cultivatormod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.model2k.cultivatormod.effect.QiEffect;

public class LowGradeBodyAndBonePill extends Item {
    static int usedTimes = 0;
    public LowGradeBodyAndBonePill(Properties properties) {
        super(properties);
    }
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (QiEffect.getMaxQi() < 100 && usedTimes == 0 && !level.isClientSide()) {
            QiEffect.setMaxQi(QiEffect.getMaxQi() + 5);
            QiEffect.setQi(QiEffect.getMaxQi());
            livingEntity.heal(20);
            livingEntity.setHealth(livingEntity.getMaxHealth() + 5);
            livingEntity.sendSystemMessage(Component.literal("Max Qi power increased by 5"));
            usedTimes++;
        }if (usedTimes >= 1 ) {
            usedTimes = 0;
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }
}
