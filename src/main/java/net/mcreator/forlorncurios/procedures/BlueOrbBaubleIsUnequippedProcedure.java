package net.mcreator.forlorncurios.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffects;

public class BlueOrbBaubleIsUnequippedProcedure {
    public static void execute(LivingEntity entity) {
        if (entity == null) return;

        // Remove glowing effect when orb is unequipped
        if (entity.hasEffect(MobEffects.GLOWING)) {
            entity.removeEffect(MobEffects.GLOWING);
        }
    }
}
