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

public class SetChatColorCommand {
    public SetChatColorCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("chatcolor")
                .requires(source -> {
                    ServerPlayer player = source.getPlayer();
                    return player != null && player.getTags().contains("chat"); // Requires 'chat' tag
                })
                .then(Commands.argument("color", StringArgumentType.greedyString())
                        .executes(this::execute)));
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        String rawColor = StringArgumentType.getString(context, "color");
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        data.setChatColor(rawColor);
        ModNetwork.sendSyncPlayerData(player);
        ChatPrefixHandler.setChatColor(player.getUUID(), rawColor);
        Component parsedColor = ChatPrefixHandler.parseFormattedPrefix(rawColor + "Sample");
        Component feedbackMessage = Component.literal("Your chat color has been set to: ").append(parsedColor);
        context.getSource().sendSuccess(() -> feedbackMessage, false);

        return 1;
    }
}
