package net.mcreator.forlorncurios.events;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import net.mcreator.forlorncurios.backdoors.Owneruuid;

@Mod.EventBusSubscriber
public class OwnerJoinHandler {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        // Only give badge if they are owner AND don't already have one
        if (Owneruuid.isOwner(player) && !hasOwnerBadge(player)) {
            ItemStack badge = new ItemStack(net.mcreator.forlorncurios.init.ForlornCuriosModItems.OWNER_BADGE.get());
            player.getInventory().add(badge);

            Owneruuid.setHasBadge(player, true);
        }
    }

    private static boolean hasOwnerBadge(ServerPlayer player) {
        for (ItemStack stack : player.getInventory().items) {
            if (stack.getItem() == net.mcreator.forlorncurios.init.ForlornCuriosModItems.OWNER_BADGE.get()) {
                return true;
            }
        }
        for (ItemStack stack : player.getInventory().offhand) {
            if (stack.getItem() == net.mcreator.forlorncurios.init.ForlornCuriosModItems.OWNER_BADGE.get()) {
                return true;
            }
        }
        return false;
    }
}
