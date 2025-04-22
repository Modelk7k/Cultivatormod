package net.model2k.cultivatormod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;

public class ReturnHomeCommand {
    public ReturnHomeCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("home").executes(this::execute));
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        String home = data.getHome();
        if (home == null || home.isEmpty()) {
            context.getSource().sendFailure(Component.literal("No Home Position has been set!"));
            return -1;
        }
        String[] coords = home.split(",");
        if (coords.length != 3) {
            context.getSource().sendFailure(Component.literal("Stored Home Position is invalid!"));
            return -1;
        }
        try {
            double x = Double.parseDouble(coords[0]);
            double y = Double.parseDouble(coords[1]);
            double z = Double.parseDouble(coords[2]);
            player.teleportTo(x, y, z);
            context.getSource().sendSuccess(() -> Component.literal("Player returned Home!"), false);
            return 1;
        } catch (NumberFormatException e) {
            context.getSource().sendFailure(Component.literal("Failed to parse Home Position!"));
            return -1;
        }
    }
}