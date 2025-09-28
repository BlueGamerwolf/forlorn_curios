package net.mcreator.forlorncurios.events;

import top.theillusivec4.curios.api.CuriosApi;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class CuriosItemDetectHandler {

    public static boolean playerHasCurio(Player player, Predicate<ItemStack> itemCheck) {
        return !CuriosApi.getCuriosHelper()
                .findCurios(player, itemCheck)
                .isEmpty();
    }
}
