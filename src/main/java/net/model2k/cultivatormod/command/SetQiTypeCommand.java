package net.model2k.cultivatormod.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;

import java.util.ArrayList;

public class SetQiTypeCommand {
    public SetQiTypeCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        register(dispatcher);
    }

    public void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("set")
                        .requires(source -> source.isPlayer() && source.getPlayer().getTags().contains("staff"))
                        .then(Commands.literal("qitype")
                                .then(Commands.argument("qiType", StringArgumentType.greedyString()) // Accepts spaces
                                        .suggests((context, builder) -> {
                                            ServerPlayer player = context.getSource().getPlayer();
                                            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
                                            return SharedSuggestionProvider.suggest(new ArrayList<>(data.getAllQiTypes().keySet()), builder);
                                        })
                                        .executes(context -> execute(context))
                                )
                        )
        );
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        String qiType = StringArgumentType.getString(context, "qiType");
        if (data.getAllQiTypes().containsKey(qiType)) {
            boolean currentValue = data.getQiType(qiType);
            data.setQiType(qiType, !currentValue);
            String status = currentValue ? "removed" : "set";
            context.getSource().sendSuccess(() -> Component.literal("Qi type " + qiType + " has been " + status + "."), false);
        if(data.getQiType("Water Qi")){
            player.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY).setBaseValue(Integer.MAX_VALUE);
            player.getAttribute(Attributes.OXYGEN_BONUS).setBaseValue(Integer.MAX_VALUE);
            data.setWalkOnWater(true);
        }if(!data.getQiType("Water Qi")){
            player.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY).setBaseValue(0);
            player.getAttribute(Attributes.OXYGEN_BONUS).setBaseValue(0);
            data.setWalkOnWater(false);
        }
        } else {
            context.getSource().sendFailure(Component.literal("Invalid Qi type: " + qiType));
        }
        return 1;
    }
}