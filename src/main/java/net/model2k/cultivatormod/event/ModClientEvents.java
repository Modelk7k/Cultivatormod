package net.model2k.cultivatormod.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.effect.ModEffects;
import net.model2k.cultivatormod.effect.QiEffect;
import net.model2k.cultivatormod.effect.SpiritPowerEffect;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;

@EventBusSubscriber(modid = CultivatorMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void keyDown(InputEvent.Key event) {
        if (event.getKey() == InputConstants.KEY_LSHIFT && Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.addEffect(new MobEffectInstance(ModEffects.QI_EFFECT, 1));
            Minecraft.getInstance().player.addEffect(new MobEffectInstance(ModEffects.SPIRIT_POWER_EFFECT, 1));
        }
        if (Minecraft.getInstance().player != null && InputConstants.KEY_LSHIFT != event.getKey()) {
            Minecraft.getInstance().player.removeEffect(ModEffects.QI_EFFECT);
            Minecraft.getInstance().player.removeEffect(ModEffects.SPIRIT_POWER_EFFECT);
        }
        if (event.getKey() == InputConstants.KEY_Z && Minecraft.getInstance().player != null) {
            QiEffect.setQi(QiEffect.getMaxQi());
            SpiritPowerEffect.setSpiritPower(SpiritPowerEffect.getMaxSpiritPower());
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Qi maxed"));
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Spirit Power Maxed"));
        }

    }
}
