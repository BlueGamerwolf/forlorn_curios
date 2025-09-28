package net.mcreator.forlorncurios.item;

import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.SlotContext;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;

import net.mcreator.forlorncurios.procedures.WardenHeartWhileBaubleIsEquippedTickProcedure;

import java.util.List;

public class WardenHeartItem extends Item implements ICurioItem {
	public WardenHeartItem() {
		super(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.COMMON));
	}

	@Override
	public boolean makesPiglinsNeutral(SlotContext slotContext, ItemStack stack) {
		return true;
	}

	@Override
	public boolean isEnderMask(SlotContext slotContext, EnderMan enderMan, ItemStack stack) {
		return true;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
	}

	@Override
	public void curioTick(SlotContext slotContext, ItemStack stack) {
		if (slotContext.entity() instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) slotContext.entity();
			Level level = livingEntity.level(); // Use getter method
			if (level instanceof ServerLevel serverLevel) {
				WardenHeartWhileBaubleIsEquippedTickProcedure.execute(serverLevel, livingEntity);
			}
		}
	}
}
