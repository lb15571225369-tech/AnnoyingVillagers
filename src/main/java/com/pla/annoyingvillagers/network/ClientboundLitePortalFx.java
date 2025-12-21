package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.engine.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ClientboundLitePortalFx(Vec3 from) {

    public static void encode(ClientboundLitePortalFx msg, FriendlyByteBuf buf) {
        buf.writeDouble(msg.from.x);
        buf.writeDouble(msg.from.y);
        buf.writeDouble(msg.from.z);
    }

    public static ClientboundLitePortalFx decode(FriendlyByteBuf buf) {
        Vec3 f = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        return new ClientboundLitePortalFx(f);
    }

    public static void handle(ClientboundLitePortalFx msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context c = ctx.get();
        c.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlers.handleLitePortalFx(msg));
        });
        c.setPacketHandled(true);
    }
}
