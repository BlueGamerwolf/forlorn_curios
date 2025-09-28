package net.mcreator.forlorncurios.procedures;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

import net.mcreator.forlorncurios.item.BlueOrbItem;
import net.mcreator.forlorncurios.events.CuriosItemDetectHandler;

import java.util.UUID;
import java.util.Map;
import java.util.List;
import java.util.HashMap;

public class BlueOrbkeyOnKeyPressedProcedure {
	// Store cooldowns per player
	private static final Map<UUID, Long> cooldowns = new HashMap<>();
	private static final long COOLDOWN_TICKS = 400; // 20 ticks = 1 second

	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null || !(entity instanceof Player player))
			return;
		// ✅ Use CuriosItemDetectHandler to check if player has Blue Orb equipped
		if (!CuriosItemDetectHandler.playerHasCurio(player, stack -> stack.getItem() instanceof BlueOrbItem)) {
			return;
		}
		// ✅ Handle cooldown
		long gameTime = ((Level) world).getGameTime();
		UUID playerId = player.getUUID();
		long lastUse = cooldowns.getOrDefault(playerId, 0L);
		if (gameTime - lastUse < COOLDOWN_TICKS) {
			long ticksLeft = COOLDOWN_TICKS - (gameTime - lastUse);
			long secondsLeft = ticksLeft / 20; // Convert to seconds
			// ⏳ Send cooldown feedback (action bar)
			if (!world.isClientSide()) {
				player.displayClientMessage(Component.literal("⚡ Ability on cooldown (" + secondsLeft + "s)"), true // true = action bar
				);
			}
			return;
		}
		// Update cooldown
		cooldowns.put(playerId, gameTime);
		// ⚡ Lightning ability
		if (world instanceof Level level && !level.isClientSide()) {
			BlockPos playerPos = player.blockPosition();
			for (int dx = -5; dx <= 5; dx++) {
				for (int dz = -5; dz <= 5; dz++) {
					if (Math.sqrt(dx * dx + dz * dz) <= 5) {
						BlockPos pos = playerPos.offset(dx, 0, dz);
						LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
						if (lightning != null) {
							lightning.moveTo(pos.getX(), pos.getY(), pos.getZ());
							if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
								lightning.setCause(serverPlayer);
							}
							level.addFreshEntity(lightning);
						}
					}
				}
			}
			// Damage nearby mobs
			List<Entity> entities = level.getEntities(player, AABB.unitCubeFromLowerCorner(player.position()).inflate(5));
			for (Entity target : entities) {
				if (target != player && target instanceof LivingEntity living) {
					living.hurt(level.damageSources().lightningBolt(), 10.0F);
				}
			}
		}
	}
}
