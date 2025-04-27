package net.model2k.cultivatormod.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.model2k.cultivatormod.CultivatorMod;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class VanishStatusPacket implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "vanish_status");
    public static final Type<VanishStatusPacket> TYPE = new Type<>(ID);
    private final UUID playerUUID;
    private final boolean isVanished;

    // Constructor
    public VanishStatusPacket(UUID playerUUID, boolean isVanished) {
        this.playerUUID = playerUUID;
        this.isVanished = isVanished;
    }

    // Default constructor for deserialization
    public VanishStatusPacket() {
        this.playerUUID = null;  // Default to null, to be populated in deserialization
        this.isVanished = false;
    }

    // Codec for serializing and deserializing
    public static final StreamCodec<FriendlyByteBuf, VanishStatusPacket> STREAM_CODEC = StreamCodec.of(
            (buf, packet) -> {
                buf.writeUUID(packet.playerUUID);
                buf.writeBoolean(packet.isVanished);
            },
            buf -> new VanishStatusPacket(buf.readUUID(), buf.readBoolean())
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    // Getter methods
    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public boolean isVanished() {
        return isVanished;
    }
}
