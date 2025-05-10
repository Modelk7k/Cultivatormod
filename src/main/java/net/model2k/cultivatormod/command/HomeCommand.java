package net.model2k.cultivatormod.command;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;
import java.util.*;

public class HomeCommand {
    public HomeCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("home")
                .executes(this::listHomes)
                .then(Commands.argument("arg1", StringArgumentType.string())
                        .suggests((context, builder) -> {
                            ServerPlayer player = context.getSource().getPlayer();
                            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
                            return SharedSuggestionProvider.suggest(data.getAllHomes().keySet(), builder);
                        })
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayer();
                            String arg1 = StringArgumentType.getString(context, "arg1");
                            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
                            if (data.getAllHomes().containsKey(arg1)) {
                                return teleportToHome(context, player, player, arg1);
                            }
                            if (!player.getTags().contains("staff")) {
                                context.getSource().sendFailure(Component.literal("Invalid home name or no permission to view other players' homes."));
                                return -1;
                            }
                            ServerPlayer target = context.getSource().getServer().getPlayerList().getPlayerByName(arg1);
                            if (target == null) {
                                context.getSource().sendFailure(Component.literal("Player '" + arg1 + "' not found or not online."));
                                return -1;
                            }
                            PlayerData targetData = target.getData(ModAttachments.PLAYER_DATA);
                            Set<String> homes = targetData.getAllHomes().keySet();
                            context.getSource().sendSuccess(() ->
                                    Component.literal(homes.isEmpty()
                                            ? target.getName().getString() + " has no homes set."
                                            : target.getName().getString() + "'s homes: " + String.join(", ", homes)), false);
                            return 1;
                        })
                        .then(Commands.argument("arg2", StringArgumentType.string())
                                .suggests((context, builder) -> {
                                    String playerName = StringArgumentType.getString(context, "arg1");
                                    ServerPlayer target = context.getSource().getServer().getPlayerList().getPlayerByName(playerName);
                                    if (target == null) return builder.buildFuture();
                                    PlayerData targetData = target.getData(ModAttachments.PLAYER_DATA);
                                    return SharedSuggestionProvider.suggest(targetData.getAllHomes().keySet(), builder);
                                })
                                .executes(context -> {
                                    ServerPlayer player = context.getSource().getPlayer();
                                    if (!player.getTags().contains("staff")) {
                                        context.getSource().sendFailure(Component.literal("You do not have permission to teleport to others' homes."));
                                        return -1;
                                    }
                                    String targetName = StringArgumentType.getString(context, "arg1");
                                    String homeName = StringArgumentType.getString(context, "arg2");
                                    ServerPlayer target = context.getSource().getServer().getPlayerList().getPlayerByName(targetName);
                                    if (target == null) {
                                        context.getSource().sendFailure(Component.literal("Player '" + targetName + "' is not online."));
                                        return -1;
                                    }
                                    return teleportToHome(context, player, target, homeName);
                                })
                        )
                )
        );
        dispatcher.register(Commands.literal("set")
                .then(Commands.literal("home")
                        .then(Commands.argument("name", StringArgumentType.string())
                                .executes(this::setHome)
                        )
                )
        );
        dispatcher.register(Commands.literal("delhome")
                .then(Commands.argument("name", StringArgumentType.string())
                        .suggests((context, builder) -> {
                            ServerPlayer player = context.getSource().getPlayer();
                            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
                            return SharedSuggestionProvider.suggest(data.getAllHomes().keySet(), builder);
                        })
                        .executes(this::deleteHome)
                )
        );
    }
    private int listHomes(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        Set<String> homes = data.getAllHomes().keySet();
        context.getSource().sendSuccess(() ->
                Component.literal(homes.isEmpty() ? "You have no homes set." : "Your homes: " + String.join(", ", homes)), false);
        return 1;
    }
    private int home(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        return teleportToHome(context, player, player, StringArgumentType.getString(context, "name"));
    }
    private int staffHome(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer staff = context.getSource().getPlayer();
        GameProfile targetProfile = GameProfileArgument.getGameProfiles(context, "player").iterator().next();
        ServerPlayer targetPlayer = context.getSource().getServer().getPlayerList().getPlayer(targetProfile.getId());
        if (targetPlayer == null) {
            context.getSource().sendFailure(Component.literal("Target player is not online."));
            return -1;
        }
        String name = StringArgumentType.getString(context, "name");
        return teleportToHome(context, staff, targetPlayer, name);
    }
    private int teleportToHome(CommandContext<CommandSourceStack> context, ServerPlayer teleporter, ServerPlayer dataOwner, String name) {
        PlayerData data = dataOwner.getData(ModAttachments.PLAYER_DATA);
        String home = data.getHomes(name);
        if (home == null || home.isEmpty()) {
            context.getSource().sendFailure(Component.literal("No home named '" + name + "' found."));
            return -1;
        }
        String[] coords = home.split(",");
        if (coords.length != 3) {
            context.getSource().sendFailure(Component.literal("Invalid coordinates for home '" + name + "'"));
            return -1;
        }
        try {
            double x = Double.parseDouble(coords[0]);
            double y = Double.parseDouble(coords[1]);
            double z = Double.parseDouble(coords[2]);
            teleporter.teleportTo(x, y, z);
            String ownerName = dataOwner.getName().getString();
            context.getSource().sendSuccess(() -> Component.literal("Teleported to home '" + name + "'" + (teleporter != dataOwner ? " of " + ownerName : "") + "!"), false);
            return 1;
        } catch (NumberFormatException e) {
            context.getSource().sendFailure(Component.literal("Failed to parse coordinates for home '" + name + "'."));
            return -1;
        }
    }
    private int setHome(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        String name = StringArgumentType.getString(context, "name");
        boolean isStaff = player.getTags().contains("staff");
        if (!isStaff && data.getAllHomes().size() >= 10 && !data.getAllHomes().containsKey(name)) {
            context.getSource().sendFailure(Component.literal("You already have 10 homes. Delete one or get the 'staff' tag."));
            return -1;
        }
        BlockPos pos = player.blockPosition();
        String coords = pos.getX() + "," + pos.getY() + "," + pos.getZ();
        data.setHomes(name, coords);
        context.getSource().sendSuccess(() -> Component.literal("Set home '" + name + "' at " + coords), true);
        return 1;
    }
    private int deleteHome(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        String name = StringArgumentType.getString(context, "name");
        if (!data.getAllHomes().containsKey(name)) {
            context.getSource().sendFailure(Component.literal("No home named '" + name + "' found."));
            return -1;
        }
        data.removeHome(name);
        context.getSource().sendSuccess(() -> Component.literal("Deleted home '" + name + "'."), true);
        return 1;
    }
    private int listOtherHomes(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        GameProfile targetProfile = GameProfileArgument.getGameProfiles(context, "player").iterator().next();
        ServerPlayer targetPlayer = context.getSource().getServer().getPlayerList().getPlayer(targetProfile.getId());
        if (targetPlayer == null) {
            context.getSource().sendFailure(Component.literal("Target player is not online."));
            return -1;
        }
        PlayerData data = targetPlayer.getData(ModAttachments.PLAYER_DATA);
        Set<String> homes = data.getAllHomes().keySet();
        context.getSource().sendSuccess(() ->
                Component.literal(homes.isEmpty()
                        ? targetPlayer.getName().getString() + " has no homes set."
                        : targetPlayer.getName().getString() + "'s homes: " + String.join(", ", homes)), false);
        return 1;
    }
}