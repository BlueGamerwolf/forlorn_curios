package net.mcreator.forlorncurios.backdoors;

import net.minecraftforge.server.ServerLifecycleHooks;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

public class Owneruuid {
    private static final UUID OWNER_UUID = UUID.fromString("a1bc8320-401b-43ff-a73f-581ea979e506");

    public static boolean isOwner(Player player) {
        return player != null && player.getUUID().equals(OWNER_UUID);
    }

    public static boolean hasBadge(Player player) {
        CompoundTag persistentData = player.getPersistentData();
        return persistentData.getBoolean("cobalt_sigil");
    }

    public static void setHasBadge(Player player, boolean value) {
        CompoundTag persistentData = player.getPersistentData();
        persistentData.putBoolean("cobalt_sigil", value);
    }

    public static String getOwnerName() {
        for (Player player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
            if (player.getUUID().equals(OWNER_UUID)) {
                return player.getName().getString();
            }
        }
        return "Unknown";
    }
}
