package net.model2k.cultivatormod.network;

import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.entity.custom.QiSlashEntity;
import net.model2k.cultivatormod.network.packet.PlayerStatsClient;
import net.model2k.cultivatormod.network.packet.QiSlashPacket;
import net.model2k.cultivatormod.network.packet.SyncPlayerDataPacket;
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
                        PlayerStatsClient.setQi(qi);
                        PlayerStatsClient.setMaxQi(maxQi);
                        PlayerStatsClient.setSpiritPower(spiritPower);
                        PlayerStatsClient.setMaxSpiritPower(maxSpiritPower);
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
                            System.out.println("Server received Qi Slash packet with direction: " + look);
                            QiSlashEntity slash = new QiSlashEntity(player.level(), player, look);
                            player.level().addFreshEntity(slash); // Only add the entity on the server side
                        } else {
                            System.out.println("Player is null or not alive on the server.");
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
                data.getMaxSpiritPower()
        );
        player.connection.send(new ClientboundCustomPayloadPacket(packet));
    }
}