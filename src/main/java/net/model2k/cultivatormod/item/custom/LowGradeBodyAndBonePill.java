package net.model2k.cultivatormod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

        if (usedTimes == 0 && !level.isClientSide()) {
            PlayerData data = livingEntity.getData(ModAttachments.PLAYER_DATA);
            if (data.getMaxQi() < 100) {
                data.setMaxQi(data.getMaxQi() + 5);
                data.setStrength(data.getStrength() + 5);
                data.setQi(data.getMaxQi());
                data.setHealth(data.getHealth() + 5);
                livingEntity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(data.getHealth());
                livingEntity.heal(20);
                data.realmChecker((Player) livingEntity);
                data.syncQiToClient((Player)livingEntity);
                usedTimes++;
            }
            if (usedTimes >= 1) {
                usedTimes = 0;
            }
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }
}
