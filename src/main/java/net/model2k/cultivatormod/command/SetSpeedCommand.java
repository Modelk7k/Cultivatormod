package net.model2k.cultivatormod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;
import net.model2k.cultivatormod.event.ModServerEvents;
import net.model2k.cultivatormod.network.ModNetwork;

public class SetSpeedCommand {
    public SetSpeedCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("set")
                        .requires(source -> {
                            ServerPlayer player = source.getPlayer();
                            return player != null && player.getTags().contains("staff");  // Only staff can use this
                        })
                        .then(Commands.literal("speed")  // This is the second argument, "speed"
                                .then(Commands.argument("speed", IntegerArgumentType.integer(1, 6))  // Accepts integer values between 1 and 6
                                        .executes(this::execute))  // Executes the method when the command is run
                        )
        );
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        if(context.getSource().getPlayer() != null) {
            ServerPlayer player = context.getSource().getPlayer();
            int speed = IntegerArgumentType.getInteger(context, "speed");
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
            data.setSpeed(speed);
            data.applySpeedToPlayer(player);
            ModNetwork.sendSyncPlayerData(player);
            context.getSource().sendSuccess(() -> Component.literal("Speed set to: " + speed), true);
        } return 1;
    }
}