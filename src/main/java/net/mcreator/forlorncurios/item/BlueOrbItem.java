package net.mcreator.forlorncurios.item;

import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.SlotContext;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.Component;

import net.mcreator.forlorncurios.procedures.BlueOrbWhileBaubleIsEquippedTickProcedure;
import net.mcreator.forlorncurios.init.ModCuriosSlots;

import java.util.List;

public class BlueOrbItem extends Item implements ICurioItem {
    public BlueOrbItem() {
        super(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC));
    }

    @Override
    public String getSlot() {
        return ModCuriosSlots.BLUE_ORB_SLOT;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.literal("§bA powerful orb that grants passive and active abilities"));
        list.add(Component.literal("§bWhile equipped: grants powerful buffs"));
        list.add(Component.literal("§bPress §eR §bfor a devastating shockwave"));
        list.add(Component.literal("§bCooldown: 30 seconds"));
    }

    @Override
    public void curioTick(String identifier, int index, SlotContext slotContext, ItemStack stack) {
        if (!(slotContext.entity() instanceof LivingEntity entity)) return;

        if (!entity.level().isClientSide()) {
            BlueOrbWhileBaubleIsEquippedTickProcedure.execute(entity);

            if (entity instanceof Player player) {
                BlueOrbActiveAbility.tickCooldown(player);
            }
        }
    }
}
