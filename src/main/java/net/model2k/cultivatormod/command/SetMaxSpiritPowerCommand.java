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
        dispatcher.register(Commands.literal("set")
                .requires(source -> {
                    ServerPlayer player = source.getPlayer();
                    return player != null && player.getTags().contains("staff"); // Requires 'staff' tag
                })
                .then(Commands.literal("maxspiritpower")
                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                .executes(this::execute))));
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        data.setMaxSpiritPower(IntegerArgumentType.getInteger(context, "amount"));
        ModNetwork.sendSyncPlayerData(player);
        return 1;
    }
}