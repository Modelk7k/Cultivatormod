package net.model2k.cultivatormod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.AABB;

public class ClearFloorItemsCommand {
    public ClearFloorItemsCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("clearfloor")
                        .requires(source -> source.isPlayer() && source.getPlayer().getTags().contains("staff")) // Staff-only
                        .executes(this::execute)
        );
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFailure(Component.literal("Must be run by a player."));
            return 0;
        }
        ServerLevel level = player.serverLevel();
        return clearFloorItems(level, context);
    }
    private int clearFloorItems(ServerLevel level, CommandContext<CommandSourceStack> context) {
        AABB aabb = new AABB(level.getMinBuildHeight(), 0, level.getMinBuildHeight(), level.getMaxBuildHeight(), level.getMaxBuildHeight(), level.getMaxBuildHeight());
        int removed = 0;
        for (ItemEntity item : level.getEntitiesOfClass(ItemEntity.class, aabb)) {
            item.discard(); // Remove the item from the world
            removed++;
        }
        if (context != null) {
            int finalRemoved = removed;
            context.getSource().sendSuccess(() -> Component.literal("Removed " + finalRemoved + " dropped item(s) from the entire server."), false);
        }
        return removed;
    }
}