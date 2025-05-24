package net.model2k.cultivatormod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;

public class GameModeAbbreviatorCommand {
    public GameModeAbbreviatorCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("gmc")
                        .requires(source -> source.isPlayer() && source.getPlayer().getTags().contains("staff")) // Staff-only
                        .executes(this::gmc)
        );
        dispatcher.register(
                Commands.literal("s")
                        .requires(source -> source.isPlayer() && source.getPlayer().getTags().contains("staff")) // Staff-only
                        .executes(this::s)
        );
        dispatcher.register(
                Commands.literal("sp")
                        .requires(source -> source.isPlayer() && source.getPlayer().getTags().contains("staff")) // Staff-only
                        .executes(this::sp)
        );
        dispatcher.register(
                Commands.literal("a")
                        .requires(source -> source.isPlayer() && source.getPlayer().getTags().contains("staff")) // Staff-only
                        .executes(this::a)
        );
    }
    private int gmc(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFailure(Component.literal("Must be run by a player."));
            return 0;
        }
        ServerLevel level = player.serverLevel();
        player.setGameMode(GameType.CREATIVE);
        return 1;
    }
    private int s(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFailure(Component.literal("Must be run by a player."));
            return 0;
        }
        ServerLevel level = player.serverLevel();
        player.setGameMode(GameType.SURVIVAL);
        return 1;
    }
    private int sp(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFailure(Component.literal("Must be run by a player."));
            return 0;
        }
        ServerLevel level = player.serverLevel();
        player.setGameMode(GameType.SPECTATOR);
        return 1;
    }
    private int a(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFailure(Component.literal("Must be run by a player."));
            return 0;
        }
        ServerLevel level = player.serverLevel();
        player.setGameMode(GameType.ADVENTURE);
        return 1;
    }
}
