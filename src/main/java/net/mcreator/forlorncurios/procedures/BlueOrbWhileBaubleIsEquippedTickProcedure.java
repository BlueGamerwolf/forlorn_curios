package net.mcreator.forlorncurios.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.LevelAccessor;

public class BlueOrbWhileBaubleIsEquippedTickProcedure {
	public static void execute(LevelAccessor world, LivingEntity entity) {
		if (entity == null)
			return;

		// Give the player Strength while equipped
		if (entity instanceof Player player) {
			player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20, 0, // duration=20 ticks (1s), amplifier=0 = Strength I
					false, false)); // no particles, no icon
		}

		// Add a persistent tag "Blue"
		entity.getPersistentData().putBoolean("Blue", true);
	}
}
