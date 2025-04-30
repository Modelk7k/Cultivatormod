package net.model2k.cultivatormod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;
import java.util.Map;
import java.util.Objects;

public class GetStatsCommand {
    public GetStatsCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        register(dispatcher);
    }
    public void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("stats")
                        .requires(source -> {
                            if (!source.isPlayer()) return false;
                            ServerPlayer player = source.getPlayer();
                            assert player != null;
                            return player.getTags().contains("staff");
                        })
                        .executes(context -> execute(context, Objects.requireNonNull(context.getSource().getPlayer())))
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(context -> execute(context, EntityArgument.getPlayer(context, "player"))))
        );
    }
    private int execute(CommandContext<CommandSourceStack> context, ServerPlayer target) {
        PlayerData data = target.getData(ModAttachments.PLAYER_DATA);
        CommandSourceStack source = context.getSource();
        source.sendSuccess(() -> Component.literal("Stats for " + target.getName().getString() + ":"), false);
        source.sendSuccess(() -> Component.literal("• Nickname: " + data.getNickName()), false);
        source.sendSuccess(() -> Component.literal("• Home: " + data.getHome()), false);
        for (Map.Entry<String, Boolean> entry : data.getAllSubRaces().entrySet()) {
            if (entry.getValue()) {
                source.sendSuccess(() -> Component.literal("• Race: " + entry.getKey()), false);
            }
        }
        for (Map.Entry<String, Boolean> entry : data.getAllBodies().entrySet()) {
            if (entry.getValue()) {
                source.sendSuccess(() -> Component.literal("• Body: " + entry.getKey()), false);
            }
        }
        for (Map.Entry<String, Boolean> entry : data.getAllPrinciples().entrySet()) {
            if (entry.getValue()) {
                source.sendSuccess(() -> Component.literal("• Principle: " + entry.getKey()), false);
            }
        }
        for (Map.Entry<String, Boolean> entry : data.getAllQiTypes().entrySet()) {
            if (entry.getValue()) {
                source.sendSuccess(() -> Component.literal("• Qi Type: " + entry.getKey()), false);
            }
        }
        source.sendSuccess(() -> Component.literal("• Minor Realm: " + data.getMinorRealm()), false);
        source.sendSuccess(() -> Component.literal("• Major Realm: " + data.getMajorRealm()), false);
        source.sendSuccess(() -> Component.literal("• Qi Quality: " + data.getQiQuality()), false);
        source.sendSuccess(() -> Component.literal("• Health: " + data.getHealth() + " / " + data.getMaxHealth()), false);
        source.sendSuccess(() -> Component.literal("• Strength: " + data.getStrength()), false);
        source.sendSuccess(() -> Component.literal("• Defense: " + data.getDefense()), false);
        source.sendSuccess(() -> Component.literal("• Speed: " + data.getSpeed()), false);
        source.sendSuccess(() -> Component.literal("• Jump: " + data.getJump()), false);
        source.sendSuccess(() -> Component.literal("• Dash: " + data.getDash()), false);
        source.sendSuccess(() -> Component.literal("• Qi: " + data.getQi() + " / " + data.getMaxQi()), false);
        source.sendSuccess(() -> Component.literal("• Spirit Power: " + data.getSpiritPower() + " / " + data.getMaxSpiritPower()), false);
        source.sendSuccess(() -> Component.literal("• Walk On Water: " + data.getWalkOnWater() + "• Can Dash: " + data.getCanDash() + "• Can Fly: " + data.getCanFly()), false);
        return 1;
    }
}