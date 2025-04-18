package net.model2k.cultivatormod.network;

import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.network.packet.PlayerStatsClient;
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
                    });
                }
        );
    }
}