package net.model2k.cultivatormod.dimension;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.model2k.cultivatormod.CultivatorMod;

public class ModDimensions {
    public static final ResourceKey<Level> SPAWN_LEVEL_KEY =
            ResourceKey.create(Registries.DIMENSION,  ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "spawn"));
     public static ServerLevel getDimensionByName(String name, MinecraftServer server) {
        if ("spawn".equalsIgnoreCase(name)) {
            return server.getLevel(ModDimensions.SPAWN_LEVEL_KEY); // not stem key!
        }
        return switch (name.toLowerCase()) {
            case "overworld" -> server.getLevel(Level.OVERWORLD);
            case "nether" -> server.getLevel(Level.NETHER);
            case "end" -> server.getLevel(Level.END);
            default -> null;
        };
    }
}
