package net.model2k.cultivatormod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;

public class SetHomeCommand {
    public SetHomeCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("set").then(Commands.literal("home")
                .executes(this::execute)));
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        BlockPos playerPos = player.blockPosition();
        String homeString = playerPos.getX() + "," + playerPos.getY() + "," + playerPos.getZ();
        data.setHome(homeString);
        player.getPersistentData().putIntArray("cultivator.homepos",
                new int[] { playerPos.getX(), playerPos.getY(), playerPos.getZ() });

        data.setHome(homeString);
        context.getSource().sendSuccess(() -> Component.literal("Set Home at " + homeString), true);
        return 1;
    }
}
