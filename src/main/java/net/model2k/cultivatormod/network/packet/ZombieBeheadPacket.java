package net.model2k.cultivatormod.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.model2k.cultivatormod.CultivatorMod;
import org.jetbrains.annotations.NotNull;

public class ZombieBeheadPacket implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "zombie_behead");
    public static final Type<ZombieBeheadPacket> TYPE = new Type<>(ID);
    private final int entityId;
    public ZombieBeheadPacket(int entityId) {
        this.entityId = entityId;
    }
    public static final StreamCodec<FriendlyByteBuf, ZombieBeheadPacket> STREAM_CODEC =
            StreamCodec.of(
                    (buf, packet) -> buf.writeVarInt(packet.entityId),
                    buf -> new ZombieBeheadPacket(buf.readVarInt())
            );
    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    public int getEntityId() {
        return entityId;
    }
}