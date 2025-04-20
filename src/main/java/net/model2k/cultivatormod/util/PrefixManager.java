package net.model2k.cultivatormod.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PrefixManager {
    private static final Map<UUID, String> PREFIXES = new HashMap<>();
    public static void setPrefix(UUID uuid, String prefix) {
        System.out.println("Setting prefix for " + uuid + " to: " + prefix); // debug log
        PREFIXES.put(uuid, prefix);
    }
    public static String getPrefix(UUID uuid) {
        String result = PREFIXES.getOrDefault(uuid, "");
        System.out.println("Fetching prefix for " + uuid + ": " + result); // debug log
        return result;
    }
}