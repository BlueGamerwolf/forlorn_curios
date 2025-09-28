package net.mcreator.forlorncurios.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import net.mcreator.forlorncurios.init.ForlornCuriosModItems;
import net.mcreator.forlorncurios.events.CuriosItemDetectHandler;

import java.util.List;

public class BlueEggWhileBaubleIsEquippedTickProcedure {
    public static void execute(Level world, LivingEntity entity) {
        if (entity == null) return;

        if (entity instanceof Player player) {
            // Check if Blue Egg is equipped
            boolean hasBlueEgg = CuriosItemDetectHandler.playerHasCurio(player,
                    stack -> stack.getItem() == ForlornCuriosModItems.BLUE_EGG.get());

            if (hasBlueEgg) {
                // Give glowing effect
                player.setGlowingTag(true);

                // Repel mobs nearby
                List<Mob> mobs = world.getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(5));
                for (Mob mob : mobs) {
                    double dx = mob.getX() - player.getX();
                    double dz = mob.getZ() - player.getZ();
                    double dist = Math.sqrt(dx * dx + dz * dz);

                    if (dist > 0) {
                        double strength = 0.3 / dist; // repel strength decreases with distance
                        mob.push(dx * strength, 0.1, dz * strength);
                    }
                }
            } else {
                // Remove glowing if not equipped
                player.setGlowingTag(false);
            }
        }
    }
}
