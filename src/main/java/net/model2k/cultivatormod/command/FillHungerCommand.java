package net.model2k.cultivatormod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class FillHungerCommand {
    public FillHungerCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        register(dispatcher);
    }
    public void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("eat")
                        .requires(source -> source.isPlayer() && source.getPlayer().getTags().contains("food")) // Requires "food" tag
                        .executes(this::fillSelf) // No argument = fill self
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(this::fillOther)) // Argument = fill other
        );
    }
    private int fillSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        player.getFoodData().setFoodLevel(20);
        player.getFoodData().setSaturation(5.0f);
        context.getSource().sendSuccess(() ->
                Component.literal("Filled your hunger."), false);
        return 1;
    }
    private int fillOther(CommandContext<CommandSourceStack> context) {
        ServerPlayer target = context.getSource().getPlayer();
        target.getFoodData().setFoodLevel(20);
        target.getFoodData().setSaturation(5.0f);
        context.getSource().sendSuccess(() ->
                Component.literal("Filled hunger for " + target.getName().getString()), false);
        return 1;
    }
}