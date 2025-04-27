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

public class SetNicknameCommand {
    public SetNicknameCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("nickname")
                        .requires(source -> {
                            ServerPlayer player = source.getPlayer();
                            return player != null && player.getTags().contains("chat"); // Requires 'chat' tag
                        })
                        .then(Commands.argument("nickname", StringArgumentType.greedyString())
                                .executes(this::execute))
        );
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        String rawNickname = StringArgumentType.getString(context, "nickname");
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        data.setNickName(rawNickname);  // Save the nickname to player data
        data.syncStatsToClient(player);  // Sync data with client
        Component parsedNickname = ChatPrefixHandler.parseFormattedPrefix(rawNickname);
        ChatPrefixHandler.setNickname(player.getUUID(), rawNickname, player);
        context.getSource().sendSuccess(
                () -> Component.literal("Set your nickname to: ").append(parsedNickname),
                false
        );
        return 1;
    }
}
