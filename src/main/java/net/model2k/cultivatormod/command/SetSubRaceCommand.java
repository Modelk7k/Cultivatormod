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

public class SetSubRaceCommand {
    public SetSubRaceCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("set")
                        .then(Commands.literal("race")
                                .requires(source -> {
                                    if (!source.isPlayer()) return false;
                                    ServerPlayer player = source.getPlayer();
                                    return player.getTags().contains("staff");
                                })
                                .then(Commands.argument("subrace", StringArgumentType.word())
                                        .suggests((context, builder) -> {
                                            ServerPlayer player = context.getSource().getPlayer();
                                            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
                                            return SharedSuggestionProvider.suggest(new ArrayList<>(data.getAllSubRaces().keySet()), builder);
                                        })
                                        .executes(this::setSubrace)
                                )
                        )
        );
    }
    private int setSubrace(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFailure(Component.literal("Only players can execute this command!"));
            return -1;
        }
        String input = StringArgumentType.getString(context, "subrace").trim();
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        String matchedKey = data.getAllSubRaces().keySet().stream()
                .filter(key -> key.equalsIgnoreCase(input))
                .findFirst()
                .orElse(null);
        if (matchedKey == null) {
            context.getSource().sendFailure(Component.literal("Invalid subrace: " + input));
            return -1;
        }
        for (String sub : data.getAllSubRaces().keySet()) {
            data.setRace(sub, false);
        }
        data.setRace(matchedKey, true);
        context.getSource().sendSuccess(() -> Component.literal("Your subrace has been set to: " + matchedKey), true);
        return 1;
    }
}