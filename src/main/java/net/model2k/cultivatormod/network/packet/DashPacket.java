package net.model2k.cultivatormod.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.model2k.cultivatormod.CultivatorMod;


public class DashPacket implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "dash_request");
    public static final Type<DashPacket> TYPE = new Type<>(ID);
    public DashPacket() {}
    public static final DashPacket INSTANCE = new DashPacket();
    public static final StreamCodec<FriendlyByteBuf, DashPacket> STREAM_CODEC =
            StreamCodec.unit(INSTANCE);
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}