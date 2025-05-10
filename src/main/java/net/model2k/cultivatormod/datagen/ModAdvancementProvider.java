package net.model2k.cultivatormod.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.model2k.cultivatormod.advancement.RealmAdvancementTrigger;
import net.model2k.cultivatormod.item.ModItems;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends AdvancementProvider {
    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, List.of(new ModAdvancementSubProvider()));
    }
    private static class ModAdvancementSubProvider implements AdvancementSubProvider {
        @Override
        public void generate(HolderLookup.@NotNull Provider registries, Consumer<AdvancementHolder> writer) {
            AdvancementHolder minorBreakthrough = Advancement.Builder.advancement()
                    .display(
                            ModItems.BAODING_BALLS,
                            Component.translatable("advancement.cultivatormod.minor_breakthrough.title"),
                            Component.translatable("advancement.cultivatormod.minor_breakthrough.desc"),
                             ResourceLocation.parse("minecraft:textures/block/end_stone.png"),
                            net.minecraft.advancements.AdvancementType.GOAL,
                            true, true, false
                    )
                    .addCriterion("minor_realm", RealmAdvancementTrigger.hasMinorRealm(1))
                    .build( ResourceLocation.fromNamespaceAndPath("cultivatormod", "minor_breakthrough"));

            writer.accept(minorBreakthrough); // Add the advancement to the writer
        }
    }
}