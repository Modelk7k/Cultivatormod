package net.model2k.cultivatormod.datagen;

import net.model2k.cultivatormod.CultivatorMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, CultivatorMod.MOD_ID);
    public static AttachmentType<PlayerData> PLAYER_DATA;
    public static void register(IEventBus modEventBus) {
        ATTACHMENTS.register(modEventBus);
        ATTACHMENTS.register("player_data", () -> {
            PLAYER_DATA = AttachmentType.serializable(PlayerData::new).copyOnDeath().build();
            return PLAYER_DATA;
        });
    }
}