package net.model2k.cultivatormod.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.effect.ModEffects;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;

@EventBusSubscriber(modid = CultivatorMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void keyDown(InputEvent.Key event) {
        Player player = Minecraft.getInstance().player;
        if (event.getKey() == InputConstants.KEY_LSHIFT && player != null) {
            player.addEffect(new MobEffectInstance(ModEffects.QI_EFFECT, 1));
            player.addEffect(new MobEffectInstance(ModEffects.SPIRIT_POWER_EFFECT, 1));
        }
        if (player != null && InputConstants.KEY_LSHIFT != event.getKey()) {
            player.removeEffect(ModEffects.QI_EFFECT);
            player.removeEffect(ModEffects.SPIRIT_POWER_EFFECT);
        }
    }
}
