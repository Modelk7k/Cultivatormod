package net.model2k.cultivatormod;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.model2k.cultivatormod.block.ModBlocks;
import net.model2k.cultivatormod.block.entity.ModBlockEntities;
import net.model2k.cultivatormod.component.ModDataComponents;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.effect.ModEffects;
import net.model2k.cultivatormod.entity.ModEntities;
import net.model2k.cultivatormod.entity.client.MindlessSlimeRenderer;
import net.model2k.cultivatormod.entity.client.QiSlashRenderer;
import net.model2k.cultivatormod.entity.client.YangBearRenderer;
import net.model2k.cultivatormod.entity.custom.QiSlashEntity;
import net.model2k.cultivatormod.recipe.ModRecipes;
import net.model2k.cultivatormod.screen.ModMenuTypes;
import net.model2k.cultivatormod.screen.custom.LowGradeJadeFurnaceScreen;
import net.model2k.cultivatormod.item.ModCreativeModeTabs;
import net.model2k.cultivatormod.item.ModItems;
import net.model2k.cultivatormod.util.ChatPrefixHandler;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import static net.model2k.cultivatormod.datagen.ModAttachments.PLAYER_DATA;

@Mod(CultivatorMod.MOD_ID)
public class CultivatorMod
{
    public static final String MOD_ID = "cultivatormod";
    private static final Logger LOGGER = LogUtils.getLogger();
    public CultivatorMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModEntities.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModEffects.register(modEventBus);
        ModDataComponents.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModAttachments.register(modEventBus);
        ChatPrefixHandler.register();
        modEventBus.addListener(this::addCreative);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    private void commonSetup(final FMLCommonSetupEvent event) {
    }
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
        }
    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.MINDLESS_SLIME.get(), MindlessSlimeRenderer::new);
            EntityRenderers.register(ModEntities.YANG_BEAR.get(), YangBearRenderer::new);
            EntityRenderers.register(ModEntities.QI_SLASH.get(), QiSlashRenderer::new);
        }
        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.LOW_GRADE_JADE_FURNACE_MENU.get(), LowGradeJadeFurnaceScreen::new);
        }
    }
}
