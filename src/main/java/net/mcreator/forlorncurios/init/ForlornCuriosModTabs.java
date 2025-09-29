
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.forlorncurios.init;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.core.registries.Registries;

import net.mcreator.forlorncurios.ForlornCuriosMod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForlornCuriosModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ForlornCuriosMod.MODID);

	@SubscribeEvent
	public static void buildTabContentsVanilla(BuildCreativeModeTabContentsEvent tabData) {

		if (tabData.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			tabData.accept(ForlornCuriosModItems.BLUE_ORB.get());
			tabData.accept(ForlornCuriosModItems.WARDEN_HEART.get());
			tabData.accept(ForlornCuriosModItems.BLUE_EGG.get());
			tabData.accept(ForlornCuriosModItems.OWNER_BADGE.get());
		}
	}
}
