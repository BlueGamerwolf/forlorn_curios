
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.forlorncurios.init;

import org.lwjgl.glfw.GLFW;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;

import net.mcreator.forlorncurios.network.BlueOrbkeyMessage;
import net.mcreator.forlorncurios.ForlornCuriosMod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class ForlornCuriosModKeyMappings {
	public static final KeyMapping BLUE_ORBKEY = new KeyMapping("key.forlorn_curios.blue_orbkey", GLFW.GLFW_KEY_R, "key.categories.misc") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				ForlornCuriosMod.PACKET_HANDLER.sendToServer(new BlueOrbkeyMessage(0, 0));
				BlueOrbkeyMessage.pressAction(Minecraft.getInstance().player, 0, 0);
			}
			isDownOld = isDown;
		}
	};

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(BLUE_ORBKEY);
	}

	@Mod.EventBusSubscriber({Dist.CLIENT})
	public static class KeyEventListener {
		@SubscribeEvent
		public static void onClientTick(TickEvent.ClientTickEvent event) {
			if (Minecraft.getInstance().screen == null) {
				BLUE_ORBKEY.consumeClick();
			}
		}
	}
}
