package net.model2k.cultivatormod.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.model2k.cultivatormod.CultivatorMod;

public class SyncPlayerDataPacket implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "sync_player_data");
    public static final Type<SyncPlayerDataPacket> TYPE = new Type<>(ID);
    private final int qi, maxQi, spiritPower, maxSpiritPower, speed, jump, dash, jumpLevel, speedLevel;
    private final boolean walkOnWater, canDash;
    public SyncPlayerDataPacket(int qi, int maxQi, int spiritPower, int maxSpiritPower,  int speed, int jump,int dash, int jumpLevel, int speedLevel, boolean canDash,boolean walkOnWater ) {
        this.qi = qi;
        this.maxQi = maxQi;
        this.spiritPower = spiritPower;
        this.maxSpiritPower = maxSpiritPower;
        this.speed = speed;
        this.jump = jump;
        this.dash = dash;
        this.jumpLevel = jumpLevel;
        this.speedLevel = speedLevel;
        this.canDash = canDash;
        this.walkOnWater = walkOnWater;
    }
    public static void encode(SyncPlayerDataPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.qi);
        buf.writeInt(msg.maxQi);
        buf.writeInt(msg.spiritPower);
        buf.writeInt(msg.maxSpiritPower);
        buf.writeInt(msg.speed);
        buf.writeInt(msg.jump);
        buf.writeInt(msg.dash);
        buf.writeInt(msg.jumpLevel);
        buf.writeInt(msg.speedLevel);
        buf.writeBoolean(msg.canDash);
        buf.writeBoolean(msg.walkOnWater);
    }
    public static SyncPlayerDataPacket decode(FriendlyByteBuf buf) {
        return new SyncPlayerDataPacket(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(),
                buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(),buf.readBoolean(),buf.readBoolean());
    }
    public static final StreamCodec<FriendlyByteBuf, SyncPlayerDataPacket> STREAM_CODEC = StreamCodec.of(
            (buf, packet) -> {
                buf.writeInt(packet.qi);
                buf.writeInt(packet.maxQi);
                buf.writeInt(packet.spiritPower);
                buf.writeInt(packet.maxSpiritPower);
                buf.writeInt(packet.speed);
                buf.writeInt(packet.jump);
                buf.writeInt(packet.dash);
                buf.writeInt(packet.jumpLevel);
                buf.writeInt(packet.speedLevel);
                buf.writeBoolean(packet.canDash);
                buf.writeBoolean(packet.walkOnWater);
            },
            buf -> new SyncPlayerDataPacket(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(),
                    buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(),buf.readBoolean(),buf.readBoolean()));
    public int getQi() {
        return qi;
    }
    public int getMaxQi() {
        return maxQi;
    }
    public int getSpiritPower() {
        return spiritPower;
    }
    public int getMaxSpiritPower() {
        return maxSpiritPower;
    }
    public int getJump() {
        return jump;
    }
    public int getSpeed() {
        return speed;
    }
    public int getDash() {
        return dash;
    }
    public int getJumpLevel() {
        return jumpLevel;
    }
    public int getSpeedLevel() {
        return speedLevel;
    }
    public boolean getWalkOnWater(){return walkOnWater;}
    public boolean getCanDash(){return canDash;}
    @Override
    public Type<SyncPlayerDataPacket> type() {
        return TYPE;
    }
}