package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.world.EmoteMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenEmoteMenuMessage {

    public OpenEmoteMenuMessage() {
    }

    public static void encode(OpenEmoteMenuMessage msg, FriendlyByteBuf buf) {
    }

    public static OpenEmoteMenuMessage decode(FriendlyByteBuf buf) {
        return new OpenEmoteMenuMessage();
    }

    public static void handle(OpenEmoteMenuMessage msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        ctx.enqueueWork(() -> {
            ServerPlayer serverPlayer = ctx.getSender();
            if (serverPlayer == null) {
                return;
            }

            serverPlayer.openMenu(
                    new SimpleMenuProvider(
                            (containerId, inventory, player) -> new EmoteMenu(containerId, inventory),
                            Component.translatable("gui.annoyingvillagers.emote.title")
                    )
            );
        });

        ctx.setPacketHandled(true);
    }
}