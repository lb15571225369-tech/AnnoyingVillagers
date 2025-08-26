package com.pla.annoyingvillagers.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.client.Minecraft;
import com.pla.annoyingvillagers.util.ExplosionFxMute;

import java.util.function.Supplier;

public class ClientboundMuteExplosionAtPos {
    public final BlockPos pos;
    public final int lifetimeTicks;

    public ClientboundMuteExplosionAtPos(BlockPos pos, int lifetimeTicks) {
        this.pos = pos;
        this.lifetimeTicks = lifetimeTicks;
    }

    public static void encode(ClientboundMuteExplosionAtPos msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeVarInt(msg.lifetimeTicks);
    }

    public static ClientboundMuteExplosionAtPos decode(FriendlyByteBuf buf) {
        return new ClientboundMuteExplosionAtPos(buf.readBlockPos(), buf.readVarInt());
    }

    public static void handle(ClientboundMuteExplosionAtPos msg, Supplier<NetworkEvent.Context> ctx) {
        var c = ctx.get();
        c.enqueueWork(() -> {
            var level = Minecraft.getInstance().level;
            if (level == null) return;
            long key = msg.pos.asLong();
            ExplosionFxMute.mark(key, level.getGameTime() + msg.lifetimeTicks);
        });
        c.setPacketHandled(true);
    }
}
