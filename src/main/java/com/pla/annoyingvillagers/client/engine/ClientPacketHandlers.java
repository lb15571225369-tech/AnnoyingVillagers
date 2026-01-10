package com.pla.annoyingvillagers.client.engine;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.network.*;
import com.pla.annoyingvillagers.util.AAAParticlesUtil;
import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import com.pla.annoyingvillagers.util.ExplosionFxMute;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;

@OnlyIn(Dist.CLIENT)
public final class ClientPacketHandlers {
    private ClientPacketHandlers() {}

    public static void handleGlaiveExplosion(ClientboundGlaiveExplosionFx msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        if (ModList.get().isLoaded("aaa_particles")) {
            AAAParticlesUtil.sendEnderGlaiveExplosion(msg.from(), msg.to(), level);
        } else {
            level.addParticle(
                    AnnoyingVillagersModParticleTypes.FIREBALL.get(),
                    true,
                    msg.to().x, msg.to().y, msg.to().z,
                    4 * 1.25F, 1, 0);
        }

        level.playLocalSound(msg.from().x, msg.from().y, msg.from().z, AnnoyingVillagersModSounds.ENDER_SHOT.get(),
                SoundSource.NEUTRAL, 1.0F, 1.0F, false);
    }

    public static void handleMuteExplosionAtPos(ClientboundMuteExplosionAtPos msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;
        ExplosionFxMute.mark(msg.pos().asLong(), level.getGameTime() + msg.lifetimeTicks());
    }

    public static void handleHerobrinePortalFx(ClientboundHerobrinePortalFx msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        AAALevel.addParticle(level, false,
                new ParticleEmitterInfo(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "herobrine_portal"))
                        .clone()
                        .position(msg.from().x, msg.from().y, msg.from().z));
    }

    public static void handleLitePortalFx(ClientboundLitePortalFx msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        AAALevel.addParticle(level, false,
                new ParticleEmitterInfo(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "lite_portal"))
                        .clone()
                        .position(msg.from().x, msg.from().y, msg.from().z));
    }

    public static void handleWoopieSwordWind(ClientboundWoopieSwordWindFx msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        AAALevel.addParticle(level, false,
                new ParticleEmitterInfo(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "woopie_sword_wind"))
                        .clone()
                        .position(msg.from().x, msg.from().y, msg.from().z));

        level.playLocalSound(msg.from().x, msg.from().y, msg.from().z, AnnoyingVillagersModSounds.WOOPIE_WIND.get(),
                SoundSource.NEUTRAL, 1.0F, 1.0F, false);
    }
}
