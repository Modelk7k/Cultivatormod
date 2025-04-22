package net.model2k.cultivatormod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class SetWarpCommand {
    public SetWarpCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("setwarp")
                .requires(source -> source.hasPermission(2)) // Staff permission check
                .then(Commands.argument("name", StringArgumentType.word())
                        .executes(this::execute)));
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFailure(Component.literal("Player not found."));
            return 0;
        }
        if (!player.getTags().contains("staff")) {
            context.getSource().sendFailure(Component.literal("You must be a staff member to set a warp."));
            return 0;
        }
        String warpName = StringArgumentType.getString(context, "name");
        BlockPos playerPos = player.blockPosition();
        TeleportToDimensionCommand.setWarpPoint(warpName, playerPos);
        context.getSource().sendSuccess(() -> Component.literal("Warp set at: " + playerPos.toString()), true);
        return 1;
    }
}