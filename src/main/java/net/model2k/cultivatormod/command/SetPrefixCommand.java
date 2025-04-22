package net.model2k.cultivatormod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;
import net.model2k.cultivatormod.util.ChatPrefixHandler;

public class SetPrefixCommand {
    public SetPrefixCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("prefix")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("prefix", StringArgumentType.greedyString())
                                .executes(this::execute))
        );
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        String rawPrefix = StringArgumentType.getString(context, "prefix");
        // Ensure that brackets are added and formatted correctly
        if (!rawPrefix.startsWith("[")) rawPrefix = "&r[" + rawPrefix;  // Add reset style and opening bracket
        if (!rawPrefix.endsWith("]")) rawPrefix = rawPrefix + "&r]";      // Add reset style and closing bracket
        // Get the player data
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        data.setChatPrefix(rawPrefix);  // Set the raw prefix
        // Sync the player's data with the client
        data.syncStatsToClient(player);
        // Parse the prefix with formatting
        Component parsedPrefix = ChatPrefixHandler.parseFormattedPrefix(rawPrefix);  // Default to white if no color is specified
        // Send feedback to the player
        context.getSource().sendSuccess(
                () -> Component.literal("Set your prefix to: ").append(parsedPrefix),
                false
        );
        return 1;
    }
}

