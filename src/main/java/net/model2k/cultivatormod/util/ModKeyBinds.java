package net.model2k.cultivatormod.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.model2k.cultivatormod.CultivatorMod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

public class ModKeyBinds {
    public static KeyMapping QI_SLASH_KEY;

    @EventBusSubscriber(modid = CultivatorMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public class ModKeybinds {
        public static final KeyMapping QI_SLASH_KEY = new KeyMapping(
                "key.cultivatormod.qi_slash", // Translation key
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_R, // Default key (e.g., R)
                "key.categories.cultivatormod" // Category
        );
        @SubscribeEvent
        public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
            event.register(QI_SLASH_KEY);
        }
    }
}
