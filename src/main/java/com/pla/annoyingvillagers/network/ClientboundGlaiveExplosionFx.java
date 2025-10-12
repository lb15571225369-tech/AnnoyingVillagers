package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.engine.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public class ClientboundGlaiveExplosionFx {
    public final Vec3 from, to;

    public ClientboundGlaiveExplosionFx(Vec3 from, Vec3 to) {
        this.from = from; this.to = to;
    }

    public static void encode(ClientboundGlaiveExplosionFx msg, FriendlyByteBuf buf) {
        buf.writeDouble(msg.from.x); buf.writeDouble(msg.from.y); buf.writeDouble(msg.from.z);
        buf.writeDouble(msg.to.x);   buf.writeDouble(msg.to.y);   buf.writeDouble(msg.to.z);
    }

    public static ClientboundGlaiveExplosionFx decode(FriendlyByteBuf buf) {
        Vec3 f = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        Vec3 t = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        return new ClientboundGlaiveExplosionFx(f, t);
    }

    public static void handle(ClientboundGlaiveExplosionFx msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context c = ctx.get();
        c.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlers.handleGlaiveExplosion(msg));
        });
        c.setPacketHandled(true);
    }
}
