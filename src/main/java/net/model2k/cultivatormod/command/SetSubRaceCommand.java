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

public class SetSubRaceCommand {
    public SetSubRaceCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("subrace")
                .then(Commands.argument("subrace", StringArgumentType.word())
                        .executes(this::setSubrace)
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
        // Clear all existing subraces
        for (String sub : data.getAllSubRaces().keySet()) {
            data.setSubRace(sub, false);
        }
        // Set new one
        data.setSubRace(matchedKey, true);
        context.getSource().sendSuccess(() -> Component.literal("Your subrace has been set to: " + matchedKey), false);
        return 1;
    }
}