package net.model2k.cultivatormod.advancement;

import com.mojang.serialization.Codec;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.CriterionValidator;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class RealmAdvancementTrigger extends SimpleCriterionTrigger<RealmAdvancementTrigger.TriggerInstance> {
    public static Criterion<TriggerInstance> hasMinorRealm(int minorRealm) {
        return new RealmAdvancementTrigger().createCriterion(new TriggerInstance(minorRealm));
    }
    @Override
    public @NotNull Codec<RealmAdvancementTrigger.TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }
    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> instance.matches(player));
    }
    public static class TriggerInstance implements CriterionTriggerInstance, SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = Codec.INT
                .fieldOf("minorRealm")
                .xmap(TriggerInstance::new, instance -> instance.minorRealm)
                .codec();
        private final int minorRealm;
        public TriggerInstance(int minorRealm) {
            this.minorRealm = minorRealm;
        }
        public boolean matches(ServerPlayer player) {
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
            return data.getMinorRealm() == this.minorRealm;
        }
        @Override
        public void validate(@NotNull CriterionValidator validator) {
        }
        @Override
        public @NotNull Optional<ContextAwarePredicate> player() {
            return Optional.empty();
        }
    }
}