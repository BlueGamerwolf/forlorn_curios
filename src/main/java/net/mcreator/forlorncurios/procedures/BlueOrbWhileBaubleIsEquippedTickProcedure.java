package net.mcreator.forlorncurios.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

public class BlueOrbWhileBaubleIsEquippedTickProcedure {
    public static void execute(LivingEntity entity) {
        if (entity == null) return;

        // Glow effect
        entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 40, 0, false, false));

        // Speed boost
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 1, false, false));

        // Damage resistance
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 0, false, false));
    }
}
