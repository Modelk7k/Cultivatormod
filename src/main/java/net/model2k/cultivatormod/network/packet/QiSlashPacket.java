package net.model2k.cultivatormod.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.model2k.cultivatormod.CultivatorMod;

public class QiSlashPacket implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "qislash");
    public static final Type<QiSlashPacket> TYPE = new Type<>(ID);
    private final Vec3 direction;
    public QiSlashPacket(Vec3 direction) {
        this.direction = direction;
    }
    public QiSlashPacket() {
        this.direction = Vec3.ZERO;
    }
    public static final StreamCodec<FriendlyByteBuf, QiSlashPacket> STREAM_CODEC = StreamCodec.of(
            (buf, packet) -> {
                buf.writeDouble(packet.direction.x);
                buf.writeDouble(packet.direction.y);
                buf.writeDouble(packet.direction.z);
            },
            buf -> new QiSlashPacket(new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble()))
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    public Vec3 getDirection() {
        return direction;
    }
}