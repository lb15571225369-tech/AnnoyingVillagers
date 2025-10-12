package com.pla.annoyingvillagers.client.engine;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.network.ClientboundGlaiveExplosionFx;
import com.pla.annoyingvillagers.network.ClientboundHerobrinePortalFx;
import com.pla.annoyingvillagers.network.ClientboundMuteExplosionAtPos;
import com.pla.annoyingvillagers.compat.aaa_particles.EnderGlaiveExplosionParticleEmitterInfo;
import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import com.pla.annoyingvillagers.util.ExplosionFxMute;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public final class ClientPacketHandlers {
    private ClientPacketHandlers() {}

    public static void handleGlaiveExplosion(ClientboundGlaiveExplosionFx msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        new EnderGlaiveExplosionParticleEmitterInfo(
                new ResourceLocation(AnnoyingVillagers.MODID, "ender_glaive_explosion"))
                .fromTo(msg.from, msg.to,
                        EnderGlaiveExplosionParticleEmitterInfo.ForwardAxis.PLUS_Z, 0f, true)
                .spawnInWorld(level, null);

        SoundEvent se = ForgeRegistries.SOUND_EVENTS.getValue(
                new ResourceLocation("annoyingvillagers:ender_shot"));
        if (se != null) {
            level.playLocalSound(msg.from.x, msg.from.y, msg.from.z, se,
                    SoundSource.NEUTRAL, 1.0F, 1.0F, false);
        }
    }

    public static void handleMuteExplosionAtPos(ClientboundMuteExplosionAtPos msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;
        ExplosionFxMute.mark(msg.pos.asLong(), level.getGameTime() + msg.lifetimeTicks);
    }

    public static void handleHerobrinePortalFx(ClientboundHerobrinePortalFx msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        AAALevel.addParticle(level, false,
                new ParticleEmitterInfo(new ResourceLocation(AnnoyingVillagers.MODID, "herobrine_portal"))
                        .clone()
                        .position(msg.from.x, msg.from.y, msg.from.z));
    }
}
