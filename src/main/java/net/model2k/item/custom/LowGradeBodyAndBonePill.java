package net.model2k.item.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.model2k.cultivatormod.block.entity.JadeFurnaceEntity;
import net.model2k.cultivatormod.effect.QiEffect;

public class LowGradeBodyAndBonePill extends Item {
    static int usedTimes = 0;
    public LowGradeBodyAndBonePill(Properties properties) {
        super(properties);
    }
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (QiEffect.getMaxQi() < 100 && usedTimes == 0 ) {
            QiEffect.setMaxQi(QiEffect.getMaxQi() + 5);
            QiEffect.setQi(QiEffect.getMaxQi());
            Minecraft.getInstance().player.heal(20);
            Minecraft.getInstance().player.setHealth(Minecraft.getInstance().player.getMaxHealth() + 5);
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Max Qi power increased by 5"));
            usedTimes++;
            return super.finishUsingItem(stack, level ,livingEntity);
        }if (usedTimes >= 1 ) {
            usedTimes = 0;
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }
}
