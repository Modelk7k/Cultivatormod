package net.model2k.cultivatormod.effect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.model2k.cultivatormod.network.packet.QiChargePacket;
import org.jetbrains.annotations.NotNull;

public class QiEfficiencyEffect extends MobEffect {
    protected QiEfficiencyEffect(MobEffectCategory category, int color) {
        super(category, color);
    }
    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
    @Override
    public boolean applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        ClientPacketListener connection = Minecraft.getInstance().getConnection();
        if (connection != null) {
            connection.send(new ServerboundCustomPayloadPacket(QiChargePacket.INSTANCE));
            return true;
        } else {
            return false;
        }
    }
}