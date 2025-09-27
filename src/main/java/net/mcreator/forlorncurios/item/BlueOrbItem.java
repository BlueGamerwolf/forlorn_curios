package net.mcreator.forlorncurios.item;

import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.SlotContext;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

import net.mcreator.forlorncurios.procedures.BlueOrbWhileBaubleIsEquippedTickProcedure;

import java.util.List;

public class BlueOrbItem extends Item implements ICurioItem {
    public BlueOrbItem() {
        super(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON));
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
    	    LivingEntity living = (LivingEntity) slotContext.entity();
    	    BlueOrbWhileBaubleIsEquippedTickProcedure.execute(slotContext.entity().level(), living);
    	}
	}
}
