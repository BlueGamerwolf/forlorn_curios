package net.mcreator.forlorncurios.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

public class BlueOrbBaubleIsEquippedProcedure {
    public static void execute(LivingEntity entity) {
        if (entity == null) return;

        // Apply constant glowing effect
        entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 2, 0, false, false));

        // Add particle aura
        if (entity.level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                ParticleTypes.END_ROD,            // Blue spark-like particles
                entity.getX(), entity.getY() + 1.0, entity.getZ(),
                5,                                // particle count
                0.5, 1.0, 0.5,                   // offset in x, y, z
                0.05                              // particle speed
            );
        }
    }
}
