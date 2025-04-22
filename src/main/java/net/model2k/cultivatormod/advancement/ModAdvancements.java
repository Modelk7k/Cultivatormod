package net.model2k.cultivatormod.advancement;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModAdvancements {

    // Register for advancements
    public static final DeferredRegister<Advancement> ADVANCEMENTS =
            DeferredRegister.create(Registries.ADVANCEMENT, CultivatorMod.MOD_ID);

    // Register for triggers
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS =
            DeferredRegister.create(Registries.TRIGGER_TYPE, CultivatorMod.MOD_ID);

    // Register the RealmAdvancementTrigger
    public static final Supplier<RealmAdvancementTrigger> REALM_ADVANCEMENT_TRIGGER =
            TRIGGERS.register("realm_advancement", RealmAdvancementTrigger::new);

 //  // This method provides the advancement data to the data generator
 //  private static AdvancementSubProvider newProvider() {
 //      return new AdvancementSubProvider() {
 //          @Override
 //          public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
 //              // Construct the advancement correctly here
 //              Advancement minorBreakthrough = Advancement.Builder.advancement()
 //                      .display(
 //                              ModItems.BAODING_BALLS, // The item used to trigger the advancement
 //                              Component.translatable("advancement.cultivatormod.minor_breakthrough.title"),
 //                              Component.translatable("advancement.cultivatormod.minor_breakthrough.desc"),
 //                               ResourceLocation.parse("minecraft:textures/block/end_stone.png"),
 //                              net.minecraft.advancements.AdvancementType.GOAL,
 //                              true,
 //                              true,
 //                              false
 //                      )
 //                      // Adding criterion properly using the RealmAdvancementTrigger
 //                      .addCriterion("minor_realm", REALM_ADVANCEMENT_TRIGGER.get().createCriterion(1))  // Correctly create the criterion here
 //                      .build( ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "minor_breakthrough"));
 //              // Accept the advancement for registration
 //              writer.accept(minorBreakthrough);
 //          }
 //      };
 //  }

    // This method registers the advancements and triggers
    public static void register(IEventBus eventBus) {
        // Register the triggers and advancements to the event bus
        TRIGGERS.register(eventBus);
        ADVANCEMENTS.register(eventBus);
    }
}
