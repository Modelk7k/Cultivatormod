package net.model2k.cultivatormod.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.model2k.cultivatormod.CultivatorMod;

public class SpeedPacket implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "speed_request");
    public static final CustomPacketPayload.Type<SpeedPacket> TYPE = new CustomPacketPayload.Type<>(ID);
    public SpeedPacket() {}
    public static final SpeedPacket INSTANCE = new SpeedPacket();
    public static final StreamCodec<FriendlyByteBuf, SpeedPacket> STREAM_CODEC =
            StreamCodec.unit(INSTANCE);
    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
