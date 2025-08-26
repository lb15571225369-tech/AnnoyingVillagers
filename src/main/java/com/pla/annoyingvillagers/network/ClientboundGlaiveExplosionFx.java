package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.compat.aaa_particles.EnderGlaiveExplosionParticleEmitterInfo;
import com.pla.annoyingvillagers.util.ExplosionFxMute;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

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
        if (c.getDirection().getReceptionSide().isClient()) {
            c.enqueueWork(() -> {
                Level level = Minecraft.getInstance().level;
                if (level == null) return;
                new EnderGlaiveExplosionParticleEmitterInfo(
                        new ResourceLocation(AnnoyingVillagers.MODID, "EnderGlaiveExplosion"))
                        .fromTo(msg.from, msg.to,
                                EnderGlaiveExplosionParticleEmitterInfo.ForwardAxis.PLUS_Z, 0f, true)
                        .spawnInWorld(level, null);
                level.playLocalSound(msg.from.x, msg.from.y, msg.from.z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:ender_shot")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
            });
        }
        c.setPacketHandled(true);
    }
}
