package net.model2k.cultivatormod.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.model2k.cultivatormod.CultivatorMod;

public class JumpPacket implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "jump_request");
    public static final CustomPacketPayload.Type<JumpPacket> TYPE = new CustomPacketPayload.Type<>(ID);
    public JumpPacket() {}
    public static final JumpPacket INSTANCE = new JumpPacket();
    public static final StreamCodec<FriendlyByteBuf, JumpPacket> STREAM_CODEC =
            StreamCodec.unit(INSTANCE);
    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
