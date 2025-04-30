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

public class SetDashCommand {
    public SetDashCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("set")  // Main 'set' command
                        .then(Commands.literal("dash")  // Sub-command for setting fly
                                .requires(source -> {
                                    ServerPlayer player = source.getPlayer();
                                    return player != null && player.getTags().contains("staff");  // Check if the player has "staff" tag
                                })
                                .then(Commands.argument("enabled", IntegerArgumentType.integer(0, 1))  // Argument for 0 or 1
                                        .executes(this::execute)  // Execution method
                                )
                        )
        );
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        if (!context.getSource().getPlayer().level().isClientSide()){
            ServerPlayer player = context.getSource().getPlayer();
            int value = IntegerArgumentType.getInteger(context, "enabled");
            boolean enabled = value == 1;
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
            ModNetwork.sendSyncPlayerData(player);
            data.setCanDash(enabled);
            context.getSource().sendSuccess(() ->
                    Component.literal("Dash has been " + (enabled ? "enabled." : "disabled.")), true);

        }
        return 1;
    }
}
