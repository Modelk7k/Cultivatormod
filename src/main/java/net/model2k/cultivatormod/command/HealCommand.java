package net.model2k.cultivatormod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class HealCommand {
    public HealCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("heal").executes(this::execute));
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        if (!context.getSource().getPlayer().level().isClientSide()) {
            ServerPlayer player = context.getSource().getPlayer();
            player.heal(player.getMaxHealth());
            context.getSource().sendSuccess(() -> Component.literal("You were healed"), false);
            return 1;
        }else {
            return 0;
        }
    }
}
