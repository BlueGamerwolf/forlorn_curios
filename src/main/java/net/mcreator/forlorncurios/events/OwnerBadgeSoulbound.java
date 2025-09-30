package net.mcreator.forlorncurios.events;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import net.mcreator.forlorncurios.item.OwnerBadgeItem;
import net.mcreator.forlorncurios.backdoors.Owneruuid;

import top.theillusivec4.curios.api.CuriosApi;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber
public class OwnerBadgeSoulbound {

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!(event.getEntity() instanceof ServerPlayer newPlayer)) return;
        if (!(event.getOriginal() instanceof ServerPlayer oldPlayer)) return;

        // Only for the true owner
        if (!Owneruuid.isOwner(newPlayer)) return;

        AtomicReference<ItemStack> badgeRef = new AtomicReference<>(ItemStack.EMPTY);

        // 1. Check normal inventory
        for (ItemStack stack : oldPlayer.getInventory().items) {
            if (stack.getItem() instanceof OwnerBadgeItem) {
                badgeRef.set(stack.copy());
                break;
            }
        }

        // 2. Check curios slots if not found yet
        if (badgeRef.get().isEmpty()) {
            CuriosApi.getCuriosInventory(oldPlayer).ifPresent(inv -> {
                inv.getCurios().forEach((id, stacksHandler) -> {
                    IItemHandlerModifiable stacks = stacksHandler.getStacks();
                    for (int i = 0; i < stacks.getSlots(); i++) {
                        ItemStack stack = stacks.getStackInSlot(i);
                        if (!stack.isEmpty() && stack.getItem() instanceof OwnerBadgeItem) {
                            badgeRef.set(stack.copy());
                            return;
                        }
                    }
                });
            });
        }

        ItemStack badge = badgeRef.get();

        // 3. Restore it back to the new player
        if (!badge.isEmpty()) {
            newPlayer.getInventory().add(badge.copy());

            // Also try to put it back into curios slot
            CuriosApi.getCuriosInventory(newPlayer).ifPresent(inv -> {
                inv.getCurios().forEach((id, stacksHandler) -> {
                    IItemHandlerModifiable stacks = stacksHandler.getStacks();
                    for (int i = 0; i < stacks.getSlots(); i++) {
                        if (stacks.getStackInSlot(i).isEmpty()) {
                            stacks.setStackInSlot(i, badge.copy());
                            return;
                        }
                    }
                });
            });
        }
    }
}
