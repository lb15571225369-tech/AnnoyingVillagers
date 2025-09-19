package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundHerobrinePortalFx {
    public final Vec3 from;

    public ClientboundHerobrinePortalFx(Vec3 from) {
        this.from = from;
    }

    public static void encode(ClientboundHerobrinePortalFx msg, FriendlyByteBuf buf) {
        buf.writeDouble(msg.from.x); buf.writeDouble(msg.from.y); buf.writeDouble(msg.from.z);
    }

    public static ClientboundHerobrinePortalFx decode(FriendlyByteBuf buf) {
        Vec3 f = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        return new ClientboundHerobrinePortalFx(f);
    }

    public static void handle(ClientboundHerobrinePortalFx msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context c = ctx.get();
        c.enqueueWork(() -> {
            Level level = Minecraft.getInstance().level;
            if (level == null) {
                return;
            }
            AAALevel.addParticle(level, false,
                    new ParticleEmitterInfo(new ResourceLocation(AnnoyingVillagers.MODID, "herobrine_portal"))
                            .clone()
                            .position(msg.from.x, msg.from.y, msg.from.z));
        });
        c.setPacketHandled(true);
    }
}
