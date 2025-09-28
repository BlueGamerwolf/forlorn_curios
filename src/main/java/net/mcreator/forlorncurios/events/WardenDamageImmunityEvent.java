package net.mcreator.forlorncurios.events;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.LivingEntity;

import net.mcreator.forlorncurios.init.ForlornCuriosModItems;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

@Mod.EventBusSubscriber
public class WardenDamageImmunityEvent {
    private static final Map<Player, Integer> wardenHitCounts = new HashMap<>();

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(event.getSource().getEntity() instanceof Warden warden))
            return;

        if (entity instanceof Player player) {
            if (CuriosItemDetectHandler.playerHasCurio(player,
                    stack -> stack.getItem() == ForlornCuriosModItems.WARDEN_HEART.get())) {
                event.setCanceled(true); // Cancel damage

                // Increment hit counter
                wardenHitCounts.put(player, wardenHitCounts.getOrDefault(player, 0) + 1);

                // If hits reach 5
                if (wardenHitCounts.get(player) >= 5) {
                    Level world = player.level();

                    List<Warden> wardens = world.getEntitiesOfClass(Warden.class,
                            player.getBoundingBox().inflate(20));

                    for (Warden nearbyWarden : wardens) {
                        System.out.println("Killing Warden UUID: " + nearbyWarden.getUUID());
                        nearbyWarden.remove(Entity.RemovalReason.KILLED);
                    }

                    wardenHitCounts.put(player, 0);
                }
            }
        }
    }
}
