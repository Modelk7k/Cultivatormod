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

public class SetCanWalkOnWaterCommand {
    public SetCanWalkOnWaterCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("set")
                .requires(source -> {
                    ServerPlayer player = source.getPlayer();
                    return player != null && player.getTags().contains("staff");
                })
                .then(Commands.literal("waterwalk")
                        .then(Commands.argument("enabled", IntegerArgumentType.integer(0, 1))
                                .executes(this::execute))));
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFailure(Component.literal("Only players can run this."));
            return 0;
        }
        int enabled = IntegerArgumentType.getInteger(context, "enabled");
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        data.setWalkOnWater(enabled == 1);
        ModNetwork.sendSyncPlayerData(player);
        context.getSource().sendSuccess(() ->
                Component.literal("Water walk ability has been " + (enabled == 1 ? "enabled" : "disabled") + "."), false);
        return 1;
    }
}