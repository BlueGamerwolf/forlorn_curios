package net.mcreator.forlorncurios.init;

import org.lwjgl.glfw.GLFW;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;

import net.mcreator.forlorncurios.network.BlueOrbKeybindMessage;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForlornCuriosModKeyMappings {
    public static final KeyMapping BLUE_ORBKEYBIND = new KeyMapping(
            "key.forlorn_curios.blue_orbkeybind",
            GLFW.GLFW_KEY_R,
            "key.categories.misc"
    );

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(BLUE_ORBKEYBIND);
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT)
    public static class KeyEventListener {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (Minecraft.getInstance().screen == null && BLUE_ORBKEYBIND.consumeClick()) {
                // Send message to server when key pressed
                net.mcreator.forlorncurios.ForlornCuriosMod.PACKET_HANDLER.sendToServer(
                        new BlueOrbKeybindMessage(0, 0)
                );
            }
        }
    }
}
