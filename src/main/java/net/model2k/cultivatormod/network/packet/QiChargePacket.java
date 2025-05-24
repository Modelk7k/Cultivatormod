package net.model2k.cultivatormod.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.model2k.cultivatormod.CultivatorMod;
import org.jetbrains.annotations.NotNull;

public class QiChargePacket implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "qi_charge_request");
    public static final CustomPacketPayload.Type<QiChargePacket> TYPE = new CustomPacketPayload.Type<>(ID);
    public QiChargePacket() {}
    public static final QiChargePacket INSTANCE = new QiChargePacket();
    public static final StreamCodec<FriendlyByteBuf, QiChargePacket> STREAM_CODEC =
            StreamCodec.unit(INSTANCE);
    @Override
    public CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
