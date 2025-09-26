package net.mcreator.forlorncurios.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;

import java.util.List;

public class BlueOrbKeybindOnKeyPressedProcedure {
    public static void execute(Player player) {
        if (player == null) return;

        double range = 6.0; // shockwave radius
        float damage = 8f; // damage amount

        // Deal damage to nearby entities
        List<LivingEntity> entities = player.level.getEntitiesOfClass(LivingEntity.class,
                player.getBoundingBox().inflate(range), e -> e != player && e instanceof Mob);

        for (LivingEntity target : entities) {
            target.hurt(player.damageSources().playerAttack(player), damage);
        }

        // Apply glowing to self for 5 seconds
        player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100, 0, false, false));

        // Play shockwave sound
        player.level.playSound(null, player.blockPosition(), SoundEvents.ENDERDRAGON_FLAP, SoundSource.PLAYERS, 1f, 1f);

        // Spawn particles
        if (player.level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.END_ROD,
                    player.getX(), player.getY() + 1, player.getZ(),
                    50, range, range / 2, range, 0.2);
        }

        // TODO: Add cooldown logic
    }
}
