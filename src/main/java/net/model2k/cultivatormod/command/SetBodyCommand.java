package net.model2k.cultivatormod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;
import java.util.ArrayList;

public class SetBodyCommand {
    public SetBodyCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        register(dispatcher);
    }
    public void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("set")
                        .requires(source -> source.isPlayer() && source.getPlayer().getTags().contains("staff"))
                        .then(Commands.literal("body")
                                .then(Commands.argument("body", StringArgumentType.greedyString()) // Accepts spaces
                                        .suggests((context, builder) -> {
                                            ServerPlayer player = context.getSource().getPlayer();
                                            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
                                            return SharedSuggestionProvider.suggest(new ArrayList<>(data.getAllBodies().keySet()), builder);
                                        })
                                        .executes(context -> execute(context))
                                )
                        )
        );
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        String body = StringArgumentType.getString(context, "body");
        if (data.getAllBodies().containsKey(body)) {
            for (String otherBody : data.getAllBodies().keySet()) {
                if (!otherBody.equals(body)) {
                    data.setBody(otherBody, false);
                }
            }
            data.setBody(body, true);
            String status = data.getPrinciples(body) ? "set" : "removed";
            context.getSource().sendSuccess(() -> Component.literal("Body " + body + " has been " + status + "."), false);
        } else {
            context.getSource().sendFailure(Component.literal("Invalid Body: " + body));
        }
        return 1;
    }
}