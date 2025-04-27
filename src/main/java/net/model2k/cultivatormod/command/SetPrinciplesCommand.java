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

public class SetPrinciplesCommand {
    public SetPrinciplesCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        register(dispatcher);
    }
    public void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("set")
                        .requires(source -> source.isPlayer() && source.getPlayer().getTags().contains("staff"))
                        .then(Commands.literal("principle")
                                .then(Commands.argument("principle", StringArgumentType.greedyString()) // Accepts spaces
                                        .suggests((context, builder) -> {
                                            ServerPlayer player = context.getSource().getPlayer();
                                            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
                                            return SharedSuggestionProvider.suggest(new ArrayList<>(data.getAllPrinciples().keySet()), builder);
                                        })
                                        .executes(context -> execute(context))
                                )
                        )
        );
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        String principle = StringArgumentType.getString(context, "principle");
        if (data.getAllPrinciples().containsKey(principle)) {
            for (String otherprinciples : data.getAllPrinciples().keySet()) {
                if (!otherprinciples.equals(principle)) {
                    data.setPrinciples(otherprinciples, false);
                }
            }
            data.setPrinciples(principle, true);
            String status = data.getPrinciples(principle) ? "set" : "removed";
            context.getSource().sendSuccess(() -> Component.literal("Principle " + principle + " has been " + status + "."), false);
        } else {
            context.getSource().sendFailure(Component.literal("Invalid Principle: " + principle));
        }
        return 1;
    }
}