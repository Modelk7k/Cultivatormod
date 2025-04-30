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
import net.model2k.cultivatormod.network.ModNetwork;
import net.model2k.cultivatormod.util.ChatPrefixHandler;

public class SetPrefixCommand {
    public SetPrefixCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("prefix")
                        .requires(source -> {
                            ServerPlayer player = source.getPlayer();
                            return player != null && player.getTags().contains("chat"); // Requires 'chat' tag
                        })
                        .then(Commands.argument("prefix", StringArgumentType.greedyString())
                                .executes(this::execute))
        );
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        String rawPrefix = StringArgumentType.getString(context, "prefix");
        if (!rawPrefix.startsWith("[")) rawPrefix = "&r[" + rawPrefix;
        if (!rawPrefix.endsWith("]")) rawPrefix = rawPrefix + "&r]";
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        data.setChatPrefix(rawPrefix);
        ModNetwork.sendSyncPlayerData(player);
        Component parsedPrefix = ChatPrefixHandler.parseFormattedPrefix(rawPrefix);
        context.getSource().sendSuccess(
                () -> Component.literal("Set your prefix to: ").append(parsedPrefix),
                false
        );
        return 1;
    }
}