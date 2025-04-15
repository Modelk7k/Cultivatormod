package net.model2k.cultivatormod.datagen;

import net.model2k.cultivatormod.CultivatorMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;


public class ModAttachments {
    // Set up the DeferredRegister
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, CultivatorMod.MOD_ID);
    // A normal field for the actual AttachmentType (not a RegistryObject)
    public static AttachmentType<PlayerData> PLAYER_DATA;
    public static void register(IEventBus modEventBus) {
        // Register with the event bus
        ATTACHMENTS.register(modEventBus);
        // Register our type and assign it at runtime
        ATTACHMENTS.register("player_data", () -> {
            PLAYER_DATA = AttachmentType.serializable(PlayerData::new).build();
            return PLAYER_DATA;
        });
    }
}