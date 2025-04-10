package net.model2k.cultivatormod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.model2k.cultivatormod.effect.QiEffect;

public class SetMaxQiCommand {
    public SetMaxQiCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("set").requires(permission -> permission.hasPermission(4))
                .then(Commands.literal("maxqi").then(Commands.argument("amount", IntegerArgumentType.integer(10))
                .executes(qi -> IntegerArgumentType.getInteger(qi, "amount")).executes(this::execute))));
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        QiEffect.setMaxQi(IntegerArgumentType.getInteger(context, "amount"));
        context.getSource().sendSuccess(() -> Component.literal("Max qi set to " + QiEffect.getMaxQi()), true);
        return 1;
    }
}
