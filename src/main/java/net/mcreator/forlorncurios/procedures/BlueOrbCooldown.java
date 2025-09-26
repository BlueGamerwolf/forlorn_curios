package net.mcreator.forlorncurios.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;

public class BlueOrbCooldown {

    private static final String TAG_KEY = "BlueOrbCooldown";

    public static boolean canActivate(Player player) {
        CompoundTag data = player.getPersistentData();
        long time = data.getLong(TAG_KEY);
        return player.level.getGameTime() >= time;
    }

    public static void startCooldown(Player player, long ticks) {
        CompoundTag data = player.getPersistentData();
        data.putLong(TAG_KEY, player.level.getGameTime() + ticks);
    }

    public static long getRemaining(Player player) {
        CompoundTag data = player.getPersistentData();
        return Math.max(0, data.getLong(TAG_KEY) - player.level.getGameTime());
    }
}
