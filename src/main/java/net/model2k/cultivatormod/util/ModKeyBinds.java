package net.model2k.cultivatormod.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.model2k.cultivatormod.CultivatorMod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = CultivatorMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModKeyBinds {
   public static final KeyMapping QI_SLASH_KEY = new KeyMapping(
           "key.cultivatormod.qi_slash",
           InputConstants.Type.KEYSYM,
           GLFW.GLFW_KEY_R,
           "key.categories.cultivatormod"
   );
      public static final KeyMapping SPEED_TOGGLE_KEY = new KeyMapping(
              "key.cultivatormod.speed_toggle",
              InputConstants.Type.KEYSYM,
              GLFW.GLFW_KEY_K,
              "key.categories.cultivatormod"
      );
    public static final KeyMapping JUMP_STRENGTH_TOGGLE_KEY = new KeyMapping(
            "key.cultivatormod.jump_toggle",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_J,
            "key.categories.cultivatormod"
    );
    public static final KeyMapping DASH_KEY = new KeyMapping(
            "key.cultivatormod.dash",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_C,
            "key.categories.cultivatormod"
    );
   @SubscribeEvent
   public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
       event.register(QI_SLASH_KEY);
       event.register(SPEED_TOGGLE_KEY);
       event.register(JUMP_STRENGTH_TOGGLE_KEY);
       event.register(DASH_KEY);
   }
}