package net.model2k.cultivatormod.item.custom;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;
import net.model2k.cultivatormod.network.ModNetwork;
import org.jetbrains.annotations.NotNull;

public class LowGradeSpiritPill extends Item {
    static int usedTimes = 0;
    public LowGradeSpiritPill(Properties properties) {
        super(properties);
    }
    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, LivingEntity livingEntity) {
            PlayerData data = livingEntity.getData(ModAttachments.PLAYER_DATA);
        if (data.getMaxSpiritPower() < 50 && usedTimes == 0 && !level.isClientSide()) {
            data.setMaxSpiritPower(data.getMaxSpiritPower() + 5);
            data.maxSpiritPower();
            data.realmChecker((Player) livingEntity);
            ModNetwork.sendSyncPlayerData((ServerPlayer) livingEntity);
            usedTimes++;
        }if (usedTimes >= 1 ) {
            usedTimes = 0;
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }
}