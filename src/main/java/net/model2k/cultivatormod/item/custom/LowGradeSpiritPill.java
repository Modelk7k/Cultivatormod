package net.model2k.cultivatormod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.model2k.cultivatormod.effect.SpiritPowerEffect;

public class LowGradeSpiritPill extends Item {

    static int usedTimes = 0;
    public LowGradeSpiritPill(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {

        if (SpiritPowerEffect.getMaxSpiritPower() < 50 && usedTimes == 0 && !level.isClientSide()) {
            SpiritPowerEffect.setMaxSpiritPower(SpiritPowerEffect.getMaxSpiritPower() + 5);
            SpiritPowerEffect.setSpiritPower(SpiritPowerEffect.getMaxSpiritPower());
            livingEntity.sendSystemMessage(Component.literal("Max spirit power increased by 5"));
            usedTimes++;
        }if (usedTimes >= 1 ) {
            usedTimes = 0;
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }
}

