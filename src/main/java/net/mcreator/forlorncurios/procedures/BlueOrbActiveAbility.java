package net.mcreator.forlorncurios.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;

import java.util.List;

public class BlueOrbActiveAbility {

    private static final String COOLDOWN_TAG = "BlueOrbCooldown";
    private static final int COOLDOWN_TICKS = 600; // 30 seconds

    public static void execute(Player player) {
        if (player == null) return;
        if (getCooldown(player) > 0) return;

        double range = 6.0;
        float damage = 8f;

        List<LivingEntity> entities = player.level.getEntitiesOfClass(LivingEntity.class,
                player.getBoundingBox().inflate(range), e -> e != player && e instanceof Mob);

        for (LivingEntity target : entities) {
            target.hurt(player.damageSources().playerAttack(player), damage);
        }

        player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100, 0, false, false));
        player.level.playSound(null, player.blockPosition(), SoundEvents.ENDERDRAGON_FLAP, SoundSource.PLAYERS, 1f, 1f);

        if (player.level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.END_ROD,
                    player.getX(), player.getY() + 1, player.getZ(),
                    50, range, range / 2, range, 0.2);
        }

        setCooldown(player, COOLDOWN_TICKS);
    }

    public static void tickCooldown(Player player) {
        int cooldown = getCooldown(player);
        if (cooldown > 0) {
            setCooldown(player, cooldown - 1);
        }
    }

    private static int getCooldown(Player player) {
        return player.getPersistentData().getInt(COOLDOWN_TAG);
    }

    private static void setCooldown(Player player, int ticks) {
        player.getPersistentData().putInt(COOLDOWN_TAG, ticks);
    }
}
