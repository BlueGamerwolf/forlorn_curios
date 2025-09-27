
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.forlorncurios.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Item;

import net.mcreator.forlorncurios.item.BlueOrbItem;
import net.mcreator.forlorncurios.ForlornCuriosMod;

public class ForlornCuriosModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ForlornCuriosMod.MODID);
	public static final RegistryObject<Item> BLUE_ORB = REGISTRY.register("blue_orb", () -> new BlueOrbItem());
}
