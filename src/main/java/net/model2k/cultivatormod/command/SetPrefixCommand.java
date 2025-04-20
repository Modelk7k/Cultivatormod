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
                Commands.literal("setprefix")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("color", StringArgumentType.word())
                                .then(Commands.argument("prefix", StringArgumentType.greedyString())
                                        .executes(this::execute)))
        );
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        String color = StringArgumentType.getString(context, "color");
        String prefix = StringArgumentType.getString(context, "prefix");
        if (!prefix.startsWith("["))
            prefix = "[" + prefix;
        if (!prefix.endsWith("]"))
            prefix = prefix + "]";
        ChatPrefixHandler.setPrefix(player.getUUID(), prefix, color);
        String finalPrefix = prefix;
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        data.setChatPrefix(prefix);
        data.setChatColor(color); // optional
        data.syncStatsToClient(player);
        context.getSource().sendSuccess(
                () -> Component.literal("Set your prefix to: " + finalPrefix + " with color: " + color), false
        );
        return 1;
    }
}
