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

public class SetMajorRealmCommand {
    public SetMajorRealmCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("set")
                        .requires(source -> {
                            ServerPlayer player = source.getPlayer();
                            return player != null && player.getTags().contains("staff");
                        })
                        .then(Commands.literal("majorrealm")
                                .then(Commands.argument("realm", IntegerArgumentType.integer(0, 10))
                                        .executes(this::execute))
                        )
        );
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) return 0;
        int realm = IntegerArgumentType.getInteger(context, "realm");
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        data.setMajorRealm(realm);
        ModNetwork.sendSyncPlayerData(player);
        context.getSource().sendSuccess(() -> Component.literal("Major Realm set to: " + realm), true);  // Send confirmation message
        return 1;
    }
}
