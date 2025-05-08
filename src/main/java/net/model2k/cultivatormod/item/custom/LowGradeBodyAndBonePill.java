package net.model2k.cultivatormod.item.custom;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;
import net.model2k.cultivatormod.network.ModNetwork;

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
                data.maxQi();
                data.setHealth(data.getHealth() + 10);
                data.setMaxHealth(data.getMaxHealth() + 5);
                data.realmChecker((Player) livingEntity);
                ModNetwork.sendSyncPlayerData((ServerPlayer) livingEntity);
                usedTimes++;
            }
            if (usedTimes >= 1) {
                usedTimes = 0;
            }
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }
}
