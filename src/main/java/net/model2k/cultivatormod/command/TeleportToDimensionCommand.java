package net.model2k.cultivatormod.command;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.MinecraftServer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;
import net.model2k.cultivatormod.dimension.ModDimensions;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TeleportToDimensionCommand {
    private static final Map<String, BlockPos> warpPoints = new HashMap<>();
    private static final Map<ResourceKey<Level>, Map<String, BlockPos>> warpPointsByWorld = new HashMap<>();
    private static File getWarpFile(MinecraftServer server, ResourceKey<Level> worldName) {
        ServerLevel world = server.getLevel(worldName);
        if (world == null) {
            return null;
        }

        File worldDir = world.getServer().getWorldPath(LevelResource.ROOT).toFile();
        File cultivatorModDir = new File(worldDir, "cultivator_mod");
        if (!cultivatorModDir.exists() && !cultivatorModDir.mkdirs()) {
            System.out.println("Failed to create directories for world " + worldName);
        }

        // Return the warp file specific to the world
        return new File(cultivatorModDir, "warps.json");
    }


    public TeleportToDimensionCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("warp")
                        .then(Commands.argument("destination", StringArgumentType.word())
                                .suggests((context, builder) -> {
                                    // Add default dimensions to the suggestions
                                    builder.suggest("spawn");
                                    builder.suggest("overworld");
                                    builder.suggest("nether");
                                    builder.suggest("end");
                                    warpPoints.keySet().forEach(warp -> builder.suggest(warp));
                                    return builder.buildFuture();
                                })
                                .executes(this::execute))
                        .executes(this::listWarps)
        );
        dispatcher.register(
                Commands.literal("setwarp")
                        .requires(source -> {
                            ServerPlayer player = source.getPlayer();
                            return player != null && player.getTags().contains("staff");  // Staff tag required
                        })
                        .then(Commands.argument("name", StringArgumentType.word())
                                .executes(this::setWarp))
        );
        dispatcher.register(
                Commands.literal("removewarp")
                        .requires(source -> {
                            ServerPlayer player = source.getPlayer();
                            return player != null && player.getTags().contains("staff");  // Staff tag required
                        })
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
        ResourceKey<Level> worldName = player.level().dimension();  // Get the player's current world

        // Try to teleport to a warp point in the current world
        BlockPos warpPos = warpPointsByWorld.getOrDefault(worldName, new HashMap<>()).get(destination);
        if (warpPos != null) {
            teleportToWarpPoint(player, warpPos);
            context.getSource().sendSuccess(() -> Component.literal("Teleported to warp: " + destination), true);
            return 1;
        }

        // Try to teleport to a default dimension
        if ("spawn".equalsIgnoreCase(destination)) { // Assuming "spawn" is a custom dimension
            teleportToCustomDimension(player);
            context.getSource().sendSuccess(() -> Component.literal("Teleported to custom dimension."), true);
            return 1;
        }

        // Handle default Minecraft dimensions (overworld, nether, end)
        ServerLevel dimensionLevel = getDimensionByName(destination, context);
        if (dimensionLevel != null) {
            teleportPlayerToDimension(player, dimensionLevel);
            context.getSource().sendSuccess(() -> Component.literal("Teleported to dimension: " + destination), true);
            return 1;
        }

        // If no valid destination was found
        context.getSource().sendFailure(Component.literal("Warp or dimension not found: " + destination));
        return 0;
    }


    private int listWarps(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFailure(Component.literal("Player not found."));
            return 0;
        }

        ResourceKey<Level> worldName = player.level().dimension(); // Get the player's current world
        Map<String, BlockPos> currentWorldWarps = warpPointsByWorld.getOrDefault(worldName, new HashMap<>());

        StringBuilder warpList = new StringBuilder("Available warps: ");
        warpList.append("overworld, nether, end, spawn"); // Always included
        if (!currentWorldWarps.isEmpty()) {
            warpList.append(", ");
            currentWorldWarps.keySet().forEach(warp -> warpList.append(warp).append(", "));
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

        // Ensure you have the correct ResourceKey for the world
        ResourceKey<Level> worldName = player.level().dimension(); // Gets the player's current world

        setWarpPoint(warpName, playerPos, player.server, worldName); // Set the warp point

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
    public void setWarpPoint(String warpName, BlockPos pos, MinecraftServer server, ResourceKey<Level> worldName) {
        // Ensure we have a map of warp points for the given world
        warpPointsByWorld.computeIfAbsent(worldName, k -> new HashMap<>()).put(warpName.toLowerCase(), pos);
        saveWarpPoints(server, worldName);
    }


    private void saveWarpPoints(MinecraftServer server, ResourceKey<Level> worldName) {
        // Get the world-specific warp points
        Map<String, BlockPos> worldWarps = warpPointsByWorld.get(worldName);
        if (worldWarps == null || worldWarps.isEmpty()) {
            return;
        }

        // Create the directory if it doesn't exist
        File directory = getWarpFile(server, worldName).getParentFile();
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                System.out.println("Failed to create directories for warp file.");
                return;
            }
        }

        // Create the JSON structure to save warp points
        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        worldWarps.forEach((name, pos) -> {
            JsonObject warpData = new JsonObject();
            warpData.addProperty("x", pos.getX());
            warpData.addProperty("y", pos.getY());
            warpData.addProperty("z", pos.getZ());
            json.add(name, warpData);
        });

        // Write the data to file
        try (FileWriter writer = new FileWriter(getWarpFile(server, worldName))) {
            gson.toJson(json, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadWarpPoints(MinecraftServer server, ResourceKey<Level> worldName) {
        File warpFile = getWarpFile(server, worldName);
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
                // Add the warp to the world-specific map
                warpPointsByWorld.computeIfAbsent(worldName, k -> new HashMap<>()).put(entry.getKey().toLowerCase(), pos);
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

        if (!player.getTags().contains("staff")) {
            context.getSource().sendFailure(Component.literal("You must be a staff member to remove a warp."));
            return 0;
        }

        String warpName = StringArgumentType.getString(context, "name").toLowerCase();

        // Ensure you have the correct ResourceKey for the world
        ResourceKey<Level> worldName = player.level().dimension(); // Gets the player's current world

        // Attempt to remove the warp point from the world
        Map<String, BlockPos> worldWarps = warpPointsByWorld.getOrDefault(worldName, new HashMap<>());
        if (!worldWarps.containsKey(warpName)) {
            context.getSource().sendFailure(Component.literal("Warp '" + warpName + "' does not exist."));
            return 0;
        }

        worldWarps.remove(warpName);
        saveWarpPoints(player.server, worldName); // Save updated warps

        context.getSource().sendSuccess(() -> Component.literal("Removed warp: " + warpName), true);
        return 1;
    }

    private void teleportToCustomDimension(ServerPlayer player) {
        // Assuming ModDimensions.getDimensionByName handles custom dimensions
        ServerLevel customDimension = ModDimensions.getDimensionByName("spawn", player.server);
        if (customDimension != null) {
            player.teleportTo(customDimension, 0, 100, 0, player.getYRot(), player.getXRot());
            player.sendSystemMessage(Component.literal("Teleported to: spawn"));
        } else {
            player.sendSystemMessage(Component.literal("Custom dimension 'spawn' not found."));
        }
    }
}
