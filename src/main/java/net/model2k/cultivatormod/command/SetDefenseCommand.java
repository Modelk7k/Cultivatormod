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

public class SetDefenseCommand {
    public SetDefenseCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("set")
                        .requires(source -> {
                            ServerPlayer player = source.getPlayer();
                            return player != null && player.getTags().contains("staff");  // Only staff can use this
                        })
                        .then(Commands.literal("defense")  // This is the second argument, "defense"
                                .then(Commands.argument("defense", IntegerArgumentType.integer(0))  // Accepts integer values between 1 and 100
                                        .executes(this::execute))  // Executes the method when the command is run
                        )
        );
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) return 0;
        int defense = IntegerArgumentType.getInteger(context, "defense");
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        data.setDefense(defense);
        data.syncStatsToClient(player);
        context.getSource().sendSuccess(() -> Component.literal("Defense set to: " + defense), true);  // Send confirmation message
        return 1;
    }
}
