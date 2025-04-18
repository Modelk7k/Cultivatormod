package net.model2k.cultivatormod.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.model2k.cultivatormod.CultivatorMod;

public class SyncPlayerDataPacket implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "sync_player_data");
    public static final Type<SyncPlayerDataPacket> TYPE = new Type<>(ID);
    private final int qi;
    private final int maxQi;
    private final int spiritPower;
    private final int maxSpiritPower;

    // Constructor
    public SyncPlayerDataPacket(int qi, int maxQi, int spiritPower, int maxSpiritPower) {
        this.qi = qi;
        this.maxQi = maxQi;
        this.spiritPower = spiritPower;
        this.maxSpiritPower = maxSpiritPower;
    }

    // Encode method
    public static void encode(SyncPlayerDataPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.qi);
        buf.writeInt(msg.maxQi);
        buf.writeInt(msg.spiritPower);
        buf.writeInt(msg.maxSpiritPower);
    }

    // Decode method
    public static SyncPlayerDataPacket decode(FriendlyByteBuf buf) {
        return new SyncPlayerDataPacket(buf.readInt(), buf.readInt(),buf.readInt(), buf.readInt());
    }

    // StreamCodec for serialization/deserialization
    public static final StreamCodec<FriendlyByteBuf, SyncPlayerDataPacket> STREAM_CODEC = StreamCodec.of(
            (buf, packet) -> {
                buf.writeInt(packet.getQi());
                buf.writeInt(packet.getMaxQi());
                buf.writeInt(packet.getSpiritPower());
                buf.writeInt(packet.getMaxSpiritPower());
            },
            buf -> new SyncPlayerDataPacket(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt())
    );
    // Getter methods
    public int getQi() {
        return qi;
    }
    public int getMaxQi() {
        return maxQi;
    }
    // Getter methods
    public int getSpiritPower() {
        return qi;
    }
    public int getMaxSpiritPower() {
        return maxQi;
    }
    @Override
    public Type<SyncPlayerDataPacket> type() {
        return TYPE;
    }
}