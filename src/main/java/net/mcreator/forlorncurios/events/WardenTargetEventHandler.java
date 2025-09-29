package net.mcreator.forlorncurios.events;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.LivingEntity;

import net.mcreator.forlorncurios.init.ForlornCuriosModItems;

@Mod.EventBusSubscriber
public class WardenTargetEventHandler {

    @SubscribeEvent
    public static void onWardenTarget(LivingChangeTargetEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof Warden warden))
            return;

        if (event.getNewTarget() instanceof Player player) {
            if (CuriosItemDetectHandler.playerHasCurio(player,
                    stack -> stack.getItem() == ForlornCuriosModItems.WARDEN_HEART.get())) {
                warden.setTarget(null);
                event.setCanceled(true);
            }
        }
    }
}
