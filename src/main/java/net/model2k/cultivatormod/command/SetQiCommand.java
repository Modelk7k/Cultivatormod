package net.model2k.cultivatormod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;

public class SetQiCommand {
    public SetQiCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("set").requires(permission -> permission.hasPermission(4))
                .then(Commands.literal("qi").then(Commands.argument("amount", IntegerArgumentType.integer(0))
                        .executes(qi -> IntegerArgumentType.getInteger(qi, "amount")).executes(this::execute))));
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        PlayerData data = context.getSource().getEntity().getData(ModAttachments.PLAYER_DATA);
        if (IntegerArgumentType.getInteger(context, "amount") <= data.getMaxQi()) {
            data.setQi(IntegerArgumentType.getInteger(context, "amount"));
            context.getSource().sendSuccess(() -> Component.literal("Qi set to " + data.getQi()), true);
            return 1;
        }else {
            context.getSource().sendFailure((Component.literal("Cannot set above your max qi")));
            return 0;
        }
    }
}
