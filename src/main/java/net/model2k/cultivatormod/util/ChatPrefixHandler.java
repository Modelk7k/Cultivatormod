package net.model2k.cultivatormod.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.ServerChatEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChatPrefixHandler {
    private static final Map<UUID, Component> PREFIXES = new HashMap<>();

    public static void setPrefix(UUID uuid, String prefix, String color) {
        Component prefixComponent;
        try {
            ChatFormatting chatColor = ChatFormatting.valueOf(color.toUpperCase());
            prefixComponent = Component.literal(prefix).withStyle(chatColor);
        } catch (IllegalArgumentException e) {
            try {
                int colorInt = Integer.parseInt(color.replace("#", ""), 16);
                prefixComponent = Component.literal(prefix).setStyle(Style.EMPTY.withColor(colorInt));
            } catch (NumberFormatException ex) {
                System.out.println("Invalid color format: " + color + ". Defaulting to white.");
                prefixComponent = Component.literal(prefix).withStyle(ChatFormatting.WHITE);
            }
        }
        PREFIXES.put(uuid, prefixComponent);
    }
    public static void register() {
        NeoForge.EVENT_BUS.addListener(ChatPrefixHandler::onServerChat);
    }
    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        String prefixText = data.getChatPrefix();
        String color = data.getChatColor();
        ChatFormatting formatting = ChatFormatting.getByName(color.toUpperCase());
        if (formatting == null) formatting = ChatFormatting.WHITE;
        Component prefix = Component.literal("" + prefixText + " ").withStyle(formatting);
        Component name = player.getDisplayName();
        Component message = Component.literal(": ").append(event.getMessage());
        Component fullMessage = Component.empty()
                .append(prefix)
                .append(name)
                .append(message);
        event.setCanceled(true);
        player.getServer().getPlayerList().getPlayers().forEach(p -> {
            p.sendSystemMessage(fullMessage);
        });
    }
}
