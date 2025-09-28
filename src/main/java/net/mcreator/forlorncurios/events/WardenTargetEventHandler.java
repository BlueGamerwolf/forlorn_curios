package net.mcreator.forlorncurios.events;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;

import net.mcreator.forlorncurios.init.ForlornCuriosModItems; // Your mod items registry

@Mod.EventBusSubscriber
public class WardenTargetEventHandler {
    @SubscribeEvent
    public static void onWardenTarget(LivingChangeTargetEvent event) {
        if (!(event.getEntityLiving() instanceof Warden warden)) return;

        if (event.getNewTarget() instanceof Player player) {
            for (ItemStack stack : CuriosApi.getCuriosHelper().findEquippedCurio(player, ForlornCuriosModItems.WARDEN_HEART.get()).map(curio -> curio.stack).stream().toList()) {
                if (stack != null) {
                    event.setNewTarget(null); // Cancel targeting
                }
            }
        }
    }
}
