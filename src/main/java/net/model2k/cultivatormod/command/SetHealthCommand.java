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

public class SetHealthCommand {
    public SetHealthCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        register(dispatcher);
    }
    public void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("set")
                        .requires(source -> source.isPlayer() && source.getPlayer().getTags().contains("staff"))
                        .then(Commands.literal("health")
                                .then(Commands.argument("health", IntegerArgumentType.integer()) // Accepts spaces
                                        .executes(context -> execute(context))
                                )
                        )
        );
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        int health = IntegerArgumentType.getInteger(context, "health");
        data.setHealth(health);
        player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(data.getHealth());
        context.getSource().sendSuccess(() -> Component.literal("Health set to " + data.getHealth()),true);
        return 1;
    }
}
