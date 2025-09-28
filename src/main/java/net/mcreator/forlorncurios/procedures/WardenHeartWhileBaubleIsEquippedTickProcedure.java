package net.mcreator.forlorncurios.procedures;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.server.level.ServerLevel;

import java.util.List;

public class WardenHeartWhileBaubleIsEquippedTickProcedure {
	public static void execute(ServerLevel world, LivingEntity entity) {
		if (entity == null)
			return;
		if (!(entity instanceof Player player))
			return;
		int tickCooldown = player.getPersistentData().getInt("warden_heart_tick_cooldown");
		int heartCooldown = player.getPersistentData().getInt("warden_heart_timer");
		double nearestDist = player.getPersistentData().getDouble("warden_heart_nearest_dist");
		// Scan for wardens every 10 ticks (~0.5s)
		if (tickCooldown <= 0) {
			List<Warden> wardens = world.getEntitiesOfClass(Warden.class, new AABB(entity.getX() - 32, entity.getY() - 32, entity.getZ() - 32, entity.getX() + 32, entity.getY() + 32, entity.getZ() + 32));
			nearestDist = 9999;
			for (Warden warden : wardens) {
				double dist = warden.distanceTo(player);
				if (dist < nearestDist)
					nearestDist = dist;
				// Stop wardens targeting player
				if (warden.getTarget() == entity) {
					warden.setTarget(null);
				}
			}
			player.getPersistentData().putDouble("warden_heart_nearest_dist", nearestDist);
			player.getPersistentData().putInt("warden_heart_tick_cooldown", 10);
		} else {
			player.getPersistentData().putInt("warden_heart_tick_cooldown", tickCooldown - 1);
		}
		// Heartbeat logic
		if (heartCooldown <= 0) {
			float volume = 0.6f;
			float pitch = 1.0f;
			int newCooldown = 40;
			if (nearestDist < 40) {
				double scale = 1.0 - (nearestDist / 40.0);
				volume = (float) (0.5f + scale * 1.5f);
				pitch = (float) (1.2f - scale * 0.7f);
				newCooldown = (int) (40 - scale * 25);
			}
			List<Player> players = world.getEntitiesOfClass(Player.class, new AABB(entity.getX() - 15, entity.getY() - 15, entity.getZ() - 15, entity.getX() + 15, entity.getY() + 15, entity.getZ() + 15));
			for (Player p : players) {
				world.playSound(null, p.blockPosition(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.PLAYERS, volume, pitch);
			}
			player.getPersistentData().putInt("warden_heart_timer", newCooldown);
		} else {
			player.getPersistentData().putInt("warden_heart_timer", heartCooldown - 1);
		}
	}
}
