package net.mcreator.forlorncurios.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

public class BlueOrbWhileBaubleIsEquippedTickProcedure {
	public static void execute(LevelAccessor world, LivingEntity entity) {
		if (entity == null)
			return;
		// Give the player hidden effects while equipped
		if (entity instanceof Player player) {
			player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 1, false, false, false));
			player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 1, false, false, false));
		}
		// Add a persistent tag "Blue"
		entity.getPersistentData().putBoolean("Blue", true);
	}
}
