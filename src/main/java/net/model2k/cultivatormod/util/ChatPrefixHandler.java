package net.model2k.cultivatormod.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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
    private static final Map<UUID, Component> NICKNAMES = new HashMap<>();
    private static final Map<UUID, Component> COLORS = new HashMap<>();
    private static final int MAX_VISIBLE_LENGTH = 20;

    public static void setPrefix(UUID uuid, String prefix, ServerPlayer player) {
        if (isTooLong(prefix, MAX_VISIBLE_LENGTH)) {
            player.sendSystemMessage(Component.literal("Prefix too long! Max 20 visible characters."));
            return;
        }
        Component prefixComponent = parseFormattedPrefix(prefix);
        PREFIXES.put(uuid, prefixComponent);
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        data.setChatPrefix(prefix);
    }
    public static void setNickname(UUID uuid, String nickname, ServerPlayer player) {
        Component nicknameComponent;
        if (nickname.equalsIgnoreCase("blank")) {
            nicknameComponent = Component.empty();
        } else {
            if (isTooLong(nickname, MAX_VISIBLE_LENGTH)) {
                player.sendSystemMessage(Component.literal("Nickname too long! Max 20 visible characters."));
                return;
            }
            nicknameComponent = parseFormattedPrefix(nickname);
        }
        NICKNAMES.put(uuid, nicknameComponent);
        player.setCustomName(nicknameComponent);
        player.setCustomNameVisible(true);
    }
    public static void setChatColor(UUID uuid, String color) {
        Component colorComponent = parseFormattedPrefix(color);
        COLORS.put(uuid, colorComponent);
    }
    public static void register() {
        NeoForge.EVENT_BUS.addListener(ChatPrefixHandler::onServerChat);
    }
    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        String message = event.getMessage().getString();
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        String prefixText = data.getChatPrefix();
        Component prefix = parseFormattedPrefix(prefixText + " ");
        Component nickname = NICKNAMES.get(player.getUUID());
        if (nickname == null) {
            nickname = player.getDisplayName();
        }
        if (nickname.getString().isBlank()) {
            nickname = Component.empty();
        }
        String chatColor = data.getChatColor(); // Stored as string like "&a" or "#ffaa00"
        Component messageComponent = parseFormattedPrefix(chatColor + ": " + message);
        Component fullMessage = Component.empty()
                .append(prefix)
                .append(nickname)
                .append(messageComponent);
        if (message.startsWith("!staff ")) {
            if (player.getTags().contains("staff")) {
                String staffMsg = message.substring(7);
                Component staffPrefix = Component.literal("[Staff] ").withStyle(ChatFormatting.YELLOW);
                for (ServerPlayer p : player.getServer().getPlayerList().getPlayers()) {
                    if (p.getTags().contains("staff")) {
                        Component staffMessage = Component.empty()
                                .append(staffPrefix)
                                .append(player.getDisplayName())
                                .append(Component.literal(": " + staffMsg));
                        p.sendSystemMessage(staffMessage);
                    }
                }
            } else {
                player.sendSystemMessage(Component.literal("You are not staff.").withStyle(ChatFormatting.YELLOW));
            }
            event.setCanceled(true);
        } else {
            player.getServer().getPlayerList().getPlayers().forEach(p -> {
                p.sendSystemMessage(fullMessage);
            });
            event.setCanceled(true);
        }
    }
    public static Component parseFormattedPrefix(String rawPrefix) {
        MutableComponent result = Component.empty();
        StringBuilder buffer = new StringBuilder();
        boolean bold = false, italic = false, underlined = false, strikethrough = false, obfuscated = false;
        ChatFormatting color = null;
        Style currentStyle = Style.EMPTY;
        for (int i = 0; i < rawPrefix.length(); i++) {
            char c = rawPrefix.charAt(i);
            if (c == '&' && i + 1 < rawPrefix.length()) {
                if (!buffer.isEmpty()) {
                    result.append(Component.literal(buffer.toString()).setStyle(currentStyle));
                    buffer.setLength(0);
                }
                char code = Character.toLowerCase(rawPrefix.charAt(++i));
                switch (code) {
                    case 'l' -> bold = true;
                    case 'o' -> italic = true;
                    case 'n' -> underlined = true;
                    case 'm' -> strikethrough = true;
                    case 'k' -> obfuscated = true;
                    case 'r' -> {
                        bold = italic = underlined = strikethrough = obfuscated = false;
                        color = null;
                        currentStyle = Style.EMPTY;
                        continue;
                    }
                    default -> {
                        ChatFormatting parsed = ChatFormatting.getByCode(code);
                        if (parsed != null && parsed.isColor()) {
                            color = parsed;
                        }
                    }
                }
                currentStyle = Style.EMPTY
                        .withBold(bold)
                        .withItalic(italic)
                        .withUnderlined(underlined)
                        .withStrikethrough(strikethrough)
                        .withObfuscated(obfuscated);
                if (color != null) {
                    currentStyle = currentStyle.withColor(color);
                }
            } else if (c == '#' && i + 6 < rawPrefix.length()) {
                String hex = rawPrefix.substring(i + 1, i + 7);
                try {
                    int rgb = Integer.parseInt(hex, 16);
                    if (!buffer.isEmpty()) {
                        result.append(Component.literal(buffer.toString()).setStyle(currentStyle));
                        buffer.setLength(0);
                    }
                    currentStyle = currentStyle.withColor(rgb);
                    i += 6;
                } catch (NumberFormatException ignored) {
                    buffer.append(c);
                }
            } else {
                buffer.append(c);
            }
        }
        if (!buffer.isEmpty()) {
            result.append(Component.literal(buffer.toString()).setStyle(currentStyle));
        }
        return result;
    }
    public static boolean isTooLong(String input, int maxLength) {
        int visibleCharCount = 0;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '&' && i + 1 < input.length()) {
                i++; // skip & and format code
            } else if (c == '#' && i + 6 < input.length()) {
                i += 6; // skip #xxxxxx
            } else {
                visibleCharCount++;
                if (visibleCharCount > maxLength) {
                    return true;
                }
            }
        }
        return false;
    }
}
