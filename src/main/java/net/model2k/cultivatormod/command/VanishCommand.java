package net.model2k.cultivatormod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;
import net.model2k.cultivatormod.network.ModNetwork;

public class VanishCommand {
    public VanishCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("vanish")
                .requires(source -> source.getPlayer() != null && source.getPlayer().getTags().contains("staff")) // Require staff tag here
                .executes(this::execute));
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        boolean isVanished = data.getVanished();
        if (!isVanished) {
            data.setVanished(true);
            context.getSource().sendSuccess(() -> Component.literal("You are now vanished"), true);
        } else {
            data.setVanished(false);
            context.getSource().sendSuccess(() -> Component.literal("You are now visible"), true);
        }
        ModNetwork.sendSyncPlayerData(player);
        return 1;
    }
}