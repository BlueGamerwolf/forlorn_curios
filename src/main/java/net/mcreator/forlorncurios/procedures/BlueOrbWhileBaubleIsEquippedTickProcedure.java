package net.mcreator.forlorncurios.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class BlueOrbWhileBaubleIsEquippedTickProcedure {
    public static void execute(LivingEntity entity) {
        if (entity == null) return;

        // Example: Give Strength II while equipped
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 1, false, false));
    }
}
