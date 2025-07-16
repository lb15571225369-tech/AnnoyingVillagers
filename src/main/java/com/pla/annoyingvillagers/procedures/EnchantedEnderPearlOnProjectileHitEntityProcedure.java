package com.pla.annoyingvillagers.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantedEnderPearlOnProjectileHitEntityProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            if (levelaccessor instanceof Level) {
                Level level = (Level) levelaccessor;

                if (!level.isClientSide()) {
                    level.playSound((Player) null, new BlockPos(d0, d1, d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.teleport")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                } else {
                    level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.teleport")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                }
            }

            entity.teleportTo(d0, d1 + 1.0D, d2);
            if (entity instanceof ServerPlayer) {
                ServerPlayer serverplayer = (ServerPlayer) entity;

                serverplayer.connection.teleport(d0, d1 + 1.0D, d2, entity.getYRot(), entity.getXRot());
            }

            if (levelaccessor instanceof ServerLevel) {
                ServerLevel serverlevel = (ServerLevel) levelaccessor;

                serverlevel.sendParticles(ParticleTypes.PORTAL, d0, d1, d2, 50, 4.0D, 4.0D, 4.0D, 1.0D);
            }

        }
    }
}
