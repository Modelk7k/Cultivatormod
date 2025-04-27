package net.model2k.cultivatormod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;

public class SetJumpStrengthCommand {
    public SetJumpStrengthCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("set")
                        .requires(source -> {
                            ServerPlayer player = source.getPlayer();
                            return player != null && player.getTags().contains("staff");  // Only staff can use this
                        })
                        .then(Commands.literal("jump")  // This is the second argument, "speed"
                                .then(Commands.argument("strength", IntegerArgumentType.integer(1, 10))  // Accepts integer values between 1 and 6
                                        .executes(this::execute))  // Executes the method when the command is run
                        )
        );
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        if(context.getSource().getPlayer() != null) {
            ServerPlayer player = context.getSource().getPlayer();
            int strength = IntegerArgumentType.getInteger(context, "strength");
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
            data.syncStatsToClient(player);
            data.setJump(strength);
            context.getSource().sendSuccess(() -> Component.literal("Jump set to: " + strength), true);
        } return 1;
    }
}
