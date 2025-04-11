package net.model2k.cultivatormod.event;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.command.ReturnHomeCommand;
import net.model2k.cultivatormod.command.SetHomeCommand;
import net.model2k.cultivatormod.command.SetMaxQiCommand;
import net.model2k.cultivatormod.command.SetQiCommand;
import net.model2k.cultivatormod.effect.ModEffects;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;

import java.awt.event.InputEvent;

@EventBusSubscriber(modid = CultivatorMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModServerEvents {
    @SubscribeEvent
    public static void joinedPlayer (PlayerEvent.PlayerLoggedInEvent event){
        event.getEntity().addEffect(new MobEffectInstance(ModEffects.YANG_QI_EFFECT, Integer.MAX_VALUE, 1));
    }
    @SubscribeEvent
    public static void livingDamage (LivingDamageEvent.Pre event) {
        Entity sheep = null;
        if (event.getEntity() instanceof Sheep && event.getSource().getDirectEntity() instanceof Player player) {
            sheep = event.getEntity();
            sheep.kill();
            sheep.level().explode(sheep, sheep.getX(), sheep.getY(), sheep.getZ(), 30, true, Level.ExplosionInteraction.TNT);
        }
    }
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        new SetMaxQiCommand(event.getDispatcher());
        new SetQiCommand(event.getDispatcher());
        new SetHomeCommand(event.getDispatcher());
        new ReturnHomeCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }
}