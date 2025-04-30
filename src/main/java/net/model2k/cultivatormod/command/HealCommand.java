package net.model2k.cultivatormod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;
import net.model2k.cultivatormod.network.ModNetwork;

public class HealCommand {
    public HealCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("heal").executes(this::execute));
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        if (!context.getSource().getPlayer().level().isClientSide()) {
            ServerPlayer player = context.getSource().getPlayer();
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
            data.setHealth(data.getMaxHealth());
            ModNetwork.sendSyncPlayerData(player);
            context.getSource().sendSuccess(() -> Component.literal("You were healed"), false);
            return 1;
        }else {
            return 0;
        }
    }
}
