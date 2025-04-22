package net.model2k.cultivatormod.command;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.MinecraftServer;
import net.minecraft.core.BlockPos;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TeleportToDimensionCommand {
    private static final Map<String, BlockPos> warpPoints = new HashMap<>();
    private static final File warpFile = new File("config/cultivator_mod/warps.json");

    public TeleportToDimensionCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("warp") // Command to teleport player to a warp point or dimension
                        .then(Commands.argument("destination", StringArgumentType.word()) // Argument for destination
                                .executes(this::execute))
                        .executes(this::listWarps) // This handles the case when no argument is passed
        );
        dispatcher.register(
                Commands.literal("setwarp") // Staff only command to set a warp point
                        .requires(source -> source.hasPermission(2)) // Permission level 2 (staff)
                        .then(Commands.argument("name", StringArgumentType.word())
                                .executes(this::setWarp))
        );
        dispatcher.register(
                Commands.literal("removewarp")
                        .requires(source -> source.hasPermission(2)) // Staff only
                        .then(Commands.argument("name", StringArgumentType.word())
                                .executes(this::removeWarp))
        );
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFailure(Component.literal("Player not found."));
            return 0;
        }
        String destination = StringArgumentType.getString(context, "destination").toLowerCase();
        BlockPos warpPos = warpPoints.get(destination);
        if (warpPos != null) {
            teleportToWarpPoint(player, warpPos);
            context.getSource().sendSuccess(() -> Component.literal("Teleported to warp: " + destination), true);
            return 1;
        }
        ServerLevel dimensionLevel = getDimensionByName(destination, context);
        if (dimensionLevel != null) {
            teleportPlayerToDimension(player, dimensionLevel);
            context.getSource().sendSuccess(() -> Component.literal("Teleported to dimension: " + destination), true);
            return 1;
        }
        context.getSource().sendFailure(Component.literal("Warp or dimension not found: " + destination));
        return 0;
    }
    private int listWarps(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFailure(Component.literal("Player not found."));
            return 0;
        }
        StringBuilder warpList = new StringBuilder("Available warps: ");
        warpList.append("overworld, nether, end"); // Always included
        if (!warpPoints.isEmpty()) {
            warpList.append(", ");
            warpPoints.keySet().forEach(warp -> warpList.append(warp).append(", "));
            // Remove trailing comma
            if (warpList.charAt(warpList.length() - 2) == ',') {
                warpList.setLength(warpList.length() - 2);
            }
        }
        context.getSource().sendSuccess(() -> Component.literal(warpList.toString()), false);
        return 1;
    }
    private int setWarp(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFailure(Component.literal("Player not found."));
            return 0;
        }
        if (!player.getTags().contains("staff")) {
            context.getSource().sendFailure(Component.literal("You must be a staff member to set a warp."));
            return 0;
        }
        String warpName = StringArgumentType.getString(context, "name");
        BlockPos playerPos = player.blockPosition();
        setWarpPoint(warpName, playerPos);
        context.getSource().sendSuccess(() -> Component.literal("Warp set at: " + playerPos.toString()), true);
        return 1;
    }
    private void teleportToWarpPoint(ServerPlayer player, BlockPos warpPos) {
        player.teleportTo(player.serverLevel(), warpPos.getX(), warpPos.getY(), warpPos.getZ(), player.getYRot(), player.getXRot());
    }
    private ServerLevel getDimensionByName(String dimensionName, CommandContext<CommandSourceStack> context) {
        if ("overworld".equalsIgnoreCase(dimensionName)) {
            dimensionName = "minecraft:overworld";  // Overworld
        } else if ("nether".equalsIgnoreCase(dimensionName)) {
            dimensionName = "minecraft:the_nether";  // Nether
        } else if ("end".equalsIgnoreCase(dimensionName)) {
            dimensionName = "minecraft:the_end";  // End
        } else {
            dimensionName = "minecraft:" + dimensionName;
        }
        MinecraftServer server = context.getSource().getServer();
        if (server == null) {
            return null;
        }
        for (ServerLevel level : server.getAllLevels()) {
            String levelName = level.dimension().location().toString();
            if (levelName.equalsIgnoreCase(dimensionName)) {
                return level;
            }
        }
        return null;
    }
    private void teleportPlayerToDimension(ServerPlayer player, ServerLevel dimensionLevel) {
        player.teleportTo(dimensionLevel, player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
    }
    public static void setWarpPoint(String warpName, BlockPos pos) {
        warpPoints.put(warpName.toLowerCase(), pos);
        saveWarpPoints();
    }
    private static void saveWarpPoints() {
        File directory = warpFile.getParentFile();
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                System.out.println("Failed to create directories for warp file.");
                return;
            }
        }
        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        warpPoints.forEach((name, pos) -> {
            JsonObject warpData = new JsonObject();
            warpData.addProperty("x", pos.getX());
            warpData.addProperty("y", pos.getY());
            warpData.addProperty("z", pos.getZ());
            json.add(name, warpData);
        });
        try (FileWriter writer = new FileWriter(warpFile)) {
            gson.toJson(json, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadWarpPoints() {
        if (!warpFile.exists()) {
            return;
        }
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(warpFile)) {
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            json.entrySet().forEach(entry -> {
                JsonObject warpData = entry.getValue().getAsJsonObject();
                int x = warpData.get("x").getAsInt();
                int y = warpData.get("y").getAsInt();
                int z = warpData.get("z").getAsInt();
                BlockPos pos = new BlockPos(x, y, z);
                warpPoints.put(entry.getKey().toLowerCase(), pos);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private int removeWarp(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFailure(Component.literal("Player not found."));
            return 0;
        }
        String warpName = StringArgumentType.getString(context, "name").toLowerCase();
        if (warpName.equals("overworld") || warpName.equals("nether") || warpName.equals("end")) {
            context.getSource().sendFailure(Component.literal("Cannot remove default dimension warps."));
            return 0;
        }
        if (!warpPoints.containsKey(warpName)) {
            context.getSource().sendFailure(Component.literal("Warp '" + warpName + "' does not exist."));
            return 0;
        }
        warpPoints.remove(warpName);
        saveWarpPoints(); // Save updated warps
        context.getSource().sendSuccess(() -> Component.literal("Removed warp: " + warpName), true);
        return 1;
    }
}