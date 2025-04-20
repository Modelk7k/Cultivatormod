package net.model2k.cultivatormod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;
import net.model2k.cultivatormod.network.ModNetwork;

public class SetMaxSpiritPowerCommand {
    public SetMaxSpiritPowerCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("set").requires(permission -> permission.hasPermission(4))
                .then(Commands.literal("maxspiritpower").then(Commands.argument("amount", IntegerArgumentType.integer(0))
                        .executes(qi -> IntegerArgumentType.getInteger(qi, "amount")).executes(this::execute))));
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        PlayerData data = context.getSource().getPlayer().getData(ModAttachments.PLAYER_DATA);
        data.setMaxSpiritPower(IntegerArgumentType.getInteger(context, "amount"));
        data.syncStatsToClient(context.getSource().getPlayer());
        return 1;
    }
}