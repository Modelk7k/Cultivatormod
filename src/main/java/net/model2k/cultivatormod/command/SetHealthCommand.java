package net.model2k.cultivatormod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;
import net.model2k.cultivatormod.network.ModNetwork;

import java.util.Objects;

public class SetHealthCommand {

    public SetHealthCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        register(dispatcher);
    }
    public void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("set")
                        .requires(source -> source.isPlayer() && Objects.requireNonNull(source.getPlayer()).getTags().contains("staff"))
                        .then(Commands.literal("maxhealth")
                                .then(Commands.argument("health", IntegerArgumentType.integer())
                                        .executes(this::execute)
                                )
                        )
        );
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        assert player != null;
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        int maxHealth = IntegerArgumentType.getInteger(context, "health");
        data.setMaxHealth(maxHealth);
        ModNetwork.sendSyncPlayerData(player);
        context.getSource().sendSuccess(() -> Component.literal("Health set to " + data.getMaxHealth()),true);
        return 1;
    }
}
