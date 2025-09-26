package net.mcreator.forlorncurios.init;

import top.theillusivec4.curios.api.SlotTypeMessage;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;

import net.minecraft.resources.ResourceLocation;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForlornCuriosModCustomCuriosSlots {
    @SubscribeEvent
    public static void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> new SlotTypeMessage.Builder("blue_orb_slot")
                        .icon(new ResourceLocation("curios:slot/miner_blessing"))
                        .size(1)
                        .build());
    }
}
