package net.mcreator.forlorncurios.backdoors;

import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

public class Owneruuid {
    // Replace with YOUR real UUID
    private static final UUID OWNER_UUID = UUID.fromString("380df991-f603-344c-a090-369bad2a924a");

    // Works with both Player and ServerPlayer
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
}
