package net.model2k.cultivatormod.network;

import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;
import net.model2k.cultivatormod.entity.custom.QiSlashEntity;
import net.model2k.cultivatormod.network.packet.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = CultivatorMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModNetwork {
    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(CultivatorMod.MOD_ID);
        registrar.playToClient(
                SyncPlayerDataPacket.TYPE,
                SyncPlayerDataPacket.STREAM_CODEC,
                (packet, context) -> {
                    context.enqueueWork(() -> {
                        int qi = packet.getQi();
                        int maxQi = packet.getMaxQi();
                        int maxSpiritPower = packet.getMaxSpiritPower();
                        int spiritPower = packet.getSpiritPower();
                        int speed = packet.getSpeed();
                        int jump = packet.getJump();
                        int dash = packet.getDash();
                        int jumpLevel = packet.getJumpLevel();
                        int speedLevel = packet.getSpeedLevel();
                        boolean canDash = packet.getCanDash();
                        boolean walkOnWater = packet.getWalkOnWater();
                        PlayerStatsClient.setQi(qi);
                        PlayerStatsClient.setMaxQi(maxQi);
                        PlayerStatsClient.setSpiritPower(spiritPower);
                        PlayerStatsClient.setMaxSpiritPower(maxSpiritPower);
                        PlayerStatsClient.setSpeed(speed);
                        PlayerStatsClient.setJump(jump);
                        PlayerStatsClient.setDash(dash);
                        PlayerStatsClient.setJumpLevel(jumpLevel);
                        PlayerStatsClient.setSpeedLevel(speedLevel);
                        PlayerStatsClient.setCanDash(canDash);
                        PlayerStatsClient.setWalkOnWater(walkOnWater);
                    });
                }
        );
        registrar.playToServer(
                QiSlashPacket.TYPE,
                QiSlashPacket.STREAM_CODEC,
                (packet, context) -> {
                    context.enqueueWork(() -> {
                        ServerPlayer player = (ServerPlayer) context.player();
                        if (player != null && player.isAlive()) {
                            Vec3 look = player.getLookAngle();
                            QiSlashEntity slash = new QiSlashEntity(player.level(), player, look);
                            player.level().addFreshEntity(slash); // Only add the entity on the server side
                        }
                    });
                }
        );
        registrar.playToClient(
                VanishStatusPacket.TYPE,
                VanishStatusPacket.STREAM_CODEC,
                (packet, context) -> {
                    // Handle the packet using the VanishStatusPacketHandler
                    new VanishStatusPacketHandler().handle(packet);
                }
        );
        registrar.playToServer(
                DashPacket.TYPE,
                DashPacket.STREAM_CODEC,
                (packet, context) -> {
                    context.enqueueWork(() -> {
                        ServerPlayer player = (ServerPlayer) context.player();
                        if (!player.level().isClientSide() && player != null && player.isAlive()) {
                            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
                            data.dashForward(player, data.getDash());
                        }
                    });
                }
        );
        registrar.playToServer(
                SpeedPacket.TYPE,
                SpeedPacket.STREAM_CODEC,
                (packet, context) -> {
                    context.enqueueWork(() -> {
                        ServerPlayer player = (ServerPlayer) context.player();
                        if (player != null && player.isAlive() && !player.level().isClientSide()) {
                            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
                            data.applySpeedToPlayer(player);
                        }
                    });
                }
        );
        registrar.playToServer(
                JumpPacket.TYPE,
                JumpPacket.STREAM_CODEC,
                (packet, context) -> {
                    context.enqueueWork(() -> {
                        ServerPlayer player = (ServerPlayer) context.player();
                        if (player != null && player.isAlive() && !player.level().isClientSide()) {
                            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
                            data.jumpLogic(player);
                            data.jumpPercentMessage(player, data.getJumpStrength());
                        }
                    });
                }
        );
    }
    public static void sendSyncPlayerData(ServerPlayer player) {
        var data = player.getData(ModAttachments.PLAYER_DATA);
        var packet = new SyncPlayerDataPacket(
                data.getQi(),
                data.getMaxQi(),
                data.getSpiritPower(),
                data.getMaxSpiritPower(),
                data.getSpeed(),
                data.getJump(),
                data.getDash(),
                data.getJumpStrength(),
                data.getSpeedLevel(),
                data.getCanDash(),
                data.getWalkOnWater()
        );
        player.connection.send(new ClientboundCustomPayloadPacket(packet));
    }
    public static void sendVanishStatus(ServerPlayer player, boolean isVanished) {
        VanishStatusPacket packet = new VanishStatusPacket(player.getUUID(), isVanished);
        player.connection.send(new ClientboundCustomPayloadPacket(packet));
    }
}