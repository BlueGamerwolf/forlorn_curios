package net.mcreator.forlorncurios.network;

import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;

import net.mcreator.forlorncurios.procedures.BlueOrbActiveAbility;
import net.mcreator.forlorncurios.ForlornCuriosMod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlueOrbKeybindMessage {
    private final int type, pressedms;

    public BlueOrbKeybindMessage(int type, int pressedms) {
        this.type = type;
        this.pressedms = pressedms;
    }

    public BlueOrbKeybindMessage(FriendlyByteBuf buffer) {
        this.type = buffer.readInt();
        this.pressedms = buffer.readInt();
    }

    public static void buffer(BlueOrbKeybindMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.type);
        buffer.writeInt(message.pressedms);
    }

    public static void handler(BlueOrbKeybindMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player player = context.getSender();
            if (player != null && message.type == 0) {
                BlueOrbActiveAbility.execute(player);
            }
        });
        context.setPacketHandled(true);
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        ForlornCuriosMod.addNetworkMessage(
                BlueOrbKeybindMessage.class,
                BlueOrbKeybindMessage::buffer,
                BlueOrbKeybindMessage::new,
                BlueOrbKeybindMessage::handler
        );
    }
}
