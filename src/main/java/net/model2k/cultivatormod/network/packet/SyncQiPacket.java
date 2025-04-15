package net.model2k.cultivatormod.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SyncQiPacket(int qi, int maxQi) implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("cultivatormod", "sync_qi");
    // Constructor to pass data when creating the packet
    public SyncQiPacket(int qi, int maxQi) {
        this.qi = qi;
        this.maxQi = maxQi;
    }
    // Serialize data into the buffer
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.qi);
        buf.writeInt(this.maxQi);
    }
    // Define what happens when the packet is received
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return new Type<>(ID);
    }
    // Accessor methods for data
    public int getQi() {
        return qi;
    }
    public int getMaxQi() {
        return maxQi;
    }
}