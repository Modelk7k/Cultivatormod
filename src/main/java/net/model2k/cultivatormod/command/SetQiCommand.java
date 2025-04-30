package net.model2k.cultivatormod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;
import net.model2k.cultivatormod.network.ModNetwork;

public class SetQiCommand {
    public SetQiCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("set")
                .requires(source -> {
                    ServerPlayer player = source.getPlayer();

                    return player != null && player.getTags().contains("staff"); // Requires 'staff' tag
                })
                .then(Commands.literal("qi")
                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                .executes(qi -> IntegerArgumentType.getInteger(qi, "amount"))
                                .executes(this::execute))));
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        PlayerData data = context.getSource().getPlayer().getData(ModAttachments.PLAYER_DATA);
        if (IntegerArgumentType.getInteger(context, "amount") <= data.getMaxQi()) {
            data.setQi(IntegerArgumentType.getInteger(context, "amount"));
            ModNetwork.sendSyncPlayerData(context.getSource().getPlayer());
            return 1;
        }else {
            context.getSource().sendFailure((Component.literal("Cannot set above your max qi")));
            return 0;
        }
    }
}