package net.model2k.cultivatormod.network.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class VanishStatusPacketHandler {
    public static void handle(VanishStatusPacket packet) {
        Player player = Minecraft.getInstance().level.getPlayerByUUID(packet.getPlayerUUID());
        if (player != null) {
            boolean isVanished = packet.isVanished();
            // Apply the vanish state
            if (isVanished) {
                player.setInvisible(true);  // Set the player as invisible
            } else {
                player.setInvisible(false); // Set the player as visible
            }
        }
    }
}