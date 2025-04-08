package net.model2k.item.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class LightningStaff extends Item {
    public LightningStaff(Properties properties) {

        super(properties);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {

        return super.onLeftClickEntity(stack, player, entity);
    }
}
