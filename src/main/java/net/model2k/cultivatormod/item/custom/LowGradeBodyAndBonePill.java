package net.model2k.cultivatormod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;

public class LowGradeBodyAndBonePill extends Item {
    static int usedTimes = 0;
    public LowGradeBodyAndBonePill(Properties properties) {
        super(properties);
    }
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        PlayerData data = livingEntity.getData(ModAttachments.PLAYER_DATA);
        if (data.getMaxQi() < 100 && usedTimes == 0 && !level.isClientSide()) {
            data.setMaxQi(data.getMaxQi() + 5);
            data.setStrength(data.getStrength() + 5);
            data.setQi(data.getMaxQi());
            livingEntity.heal(20);
            livingEntity.setHealth(livingEntity.getMaxHealth() + 5);
            livingEntity.sendSystemMessage(Component.literal("Max Qi power increased by 5"));
            livingEntity.sendSystemMessage(Component.literal("Strength increased by 5"));
            livingEntity.sendSystemMessage(Component.literal("Health increased by 5"));
            data.realmChecker((Player) livingEntity);
            usedTimes++;
        }if (usedTimes >= 1 ) {
            usedTimes = 0;
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }
}
