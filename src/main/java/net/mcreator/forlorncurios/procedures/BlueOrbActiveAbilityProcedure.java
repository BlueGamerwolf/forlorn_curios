package net.mcreator.forlorncurios.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class BlueOrbActiveAbility {

    public static void execute(Player player) {
        if (player == null) return;

        Level level = player.level;

        if (!level.isClientSide && BlueOrbCooldown.canActivate(player)) {
            double range = 5.0; // Shockwave radius

            // Damage nearby entities
            level.getEntities(player, player.getBoundingBox().inflate(range))
                .forEach(e -> {
                    if (e instanceof LivingEntity target && target != player) {
                        target.hurt(player.damageSources().playerAttack(player), 10f);
                    }
                });

            // Play shockwave sound
            level.playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0f, 1.0f);

            // Spawn particles
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, player.getX(), player.getY() + 1.0, player.getZ(), 50, 1, 1, 1, 0.1);
            }

            // Start cooldown
            BlueOrbCooldown.startCooldown(player, 30 * 20); // 30 seconds
        } else {
            player.displayClientMessage(
                net.minecraft.network.chat.Component.literal(
                    "§cBlue Orb is recharging! " + (BlueOrbCooldown.getRemaining(player) / 20) + "s remaining"
                ), true
            );
        }
    }
}
