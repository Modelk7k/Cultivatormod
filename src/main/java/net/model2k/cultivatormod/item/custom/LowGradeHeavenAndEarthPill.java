package net.model2k.cultivatormod.item.custom;


import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;

public class LowGradeHeavenAndEarthPill extends Item {

    static int usedTimes = 0;
    public LowGradeHeavenAndEarthPill(Properties properties) {
        super(properties);
    }
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        PlayerData data = livingEntity.getData(ModAttachments.PLAYER_DATA);
        if (usedTimes == 0 && !level.isClientSide()) {
           if(data.getQiQuality() < 5) {
               data.setQiQuality(data.getQiQuality() + 1);
               livingEntity.sendSystemMessage(Component.literal( "Qi Quality: " + data.getQiQuality()));
               data.realmChecker((Player)livingEntity);
               data.syncQiToClient((Player)livingEntity);
               usedTimes++;
           }
        }if (usedTimes >= 1 ) {
            usedTimes = 0;
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }
}
