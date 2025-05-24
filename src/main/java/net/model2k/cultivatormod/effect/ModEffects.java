package net.model2k.cultivatormod.effect;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.model2k.cultivatormod.CultivatorMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, CultivatorMod.MOD_ID);

    public static final Holder<MobEffect> QI_EFFICIENCY_EFFECT = MOB_EFFECTS.register("qi_efficiency",
            () -> new QiEfficiencyEffect(MobEffectCategory.BENEFICIAL, 0x36ebab)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE,
                            ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "qi_efficiency"),
                            1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static void register(IEventBus event){
        MOB_EFFECTS.register(event);
    }
}