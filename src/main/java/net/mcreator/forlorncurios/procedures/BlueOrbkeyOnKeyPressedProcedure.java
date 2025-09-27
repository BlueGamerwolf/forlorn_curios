import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class BlueOrbkeyOnKeyPressedProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null || world == null) return;
        if (!(entity instanceof Player player)) return;

        // ✅ Check if player has Blue Orb equipped
        var equippedCurios = CuriosApi.getCuriosHelper().getEquippedCurios(player);
        boolean hasBlueOrb = equippedCurios.stream()
            .anyMatch(curio -> curio.getItem() instanceof BlueOrbItem);

        if (!hasBlueOrb) {
            if (!world.isClientSide()) {
                player.displayClientMessage(Component.literal("Item is not equipped"), true);
            }
            return; // Stop ability if not equipped
        }

        // Summon lightning in a 5-block radius
        if (world instanceof Level level && !level.isClientSide()) {
            BlockPos playerPos = player.blockPosition();
            for (int dx = -5; dx <= 5; dx++) {
                for (int dz = -5; dz <= 5; dz++) {
                    if (Math.sqrt(dx * dx + dz * dz) <= 5) {
                        BlockPos pos = playerPos.offset(dx, 0, dz);
                        LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
                        if (lightning != null) {
                            lightning.moveTo(pos.getX(), pos.getY(), pos.getZ());

                            // Safe cause assignment
                            if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                                lightning.setCause(serverPlayer);
                            }

                            level.addFreshEntity(lightning);
                        }
                    }
                }
            }

            List<Entity> entities = level.getEntities(player, AABB.unitCubeFromLowerCorner(player.position()).inflate(5));
            for (Entity target : entities) {
                if (target != player && target instanceof LivingEntity living) {
                    living.hurt(level.damageSources().lightningBolt(), 10.0F);
                }
            }
        }
    }
}
