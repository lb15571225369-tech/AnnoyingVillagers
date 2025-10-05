package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.Herobrine4Entity;
import com.pla.annoyingvillagers.util.HerobrineMob;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RiseFromGroundHandler {

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        var level = entity.level();
        if (level.isClientSide()) return;

        var tag = entity.getPersistentData();
        if (tag.getBoolean(HerobrinePortalProcedure.NBT_RISING)) {
            double targetY = tag.getDouble(HerobrinePortalProcedure.NBT_TARGET_Y);
            double speed   = tag.getDouble(HerobrinePortalProcedure.NBT_SPEED);
            int    ticks   = tag.getInt(HerobrinePortalProcedure.NBT_TICKS);
            int    max     = tag.getInt(HerobrinePortalProcedure.NBT_MAX_TICKS);

            if ((ticks % 8) == 0) {
                level.playSound(null, entity.blockPosition(), SoundEvents.SOUL_ESCAPE, SoundSource.HOSTILE, 0.25f, 0.9f + entity.getRandom().nextFloat() * 0.2f);
            }

            double ny = entity.getY() + speed;
            if (ny >= targetY || ticks > max) {
                entity.setPos(entity.getX(), targetY, entity.getZ());
                finishRise(entity);
            } else {
                entity.setPos(entity.getX(), ny, entity.getZ());
                tag.putInt(HerobrinePortalProcedure.NBT_TICKS, ticks + 1);
            }
            return;
        }

        if (tag.getBoolean(HerobrinePortalProcedure.NBT_SINKING)) {
            double targetY = tag.getDouble(HerobrinePortalProcedure.NBT_SINK_TARGET_Y);
            double speed   = tag.getDouble(HerobrinePortalProcedure.NBT_SINK_SPEED);
            int    ticks   = tag.getInt(HerobrinePortalProcedure.NBT_SINK_TICKS);
            int    max     = tag.getInt(HerobrinePortalProcedure.NBT_SINK_MAX_TICKS);

            if ((ticks % 8) == 0) {
                level.playSound(null, entity.blockPosition(), SoundEvents.SOUL_ESCAPE, SoundSource.HOSTILE, 0.25f, 1.05f + entity.getRandom().nextFloat() * 0.2f);
            }

            entity.setPos(entity.getX(), entity.getY() - speed, entity.getZ());
            tag.putInt(HerobrinePortalProcedure.NBT_SINK_TICKS, ticks + 1);
        }
    }

    private static void finishRise(LivingEntity entity) {
        var level = (net.minecraft.server.level.ServerLevel) entity.level();
        var tag = entity.getPersistentData();

        level.playSound(null, entity.blockPosition(), SoundEvents.SAND_BREAK, SoundSource.BLOCKS, 0.7f, 0.9f);

        entity.noPhysics = false;
        entity.setNoGravity(false);
        entity.setInvulnerable(false);
        if (entity instanceof Mob mob) {
            mob.setNoAi(false);
        }

        tag.remove(HerobrinePortalProcedure.NBT_RISING);
        tag.remove(HerobrinePortalProcedure.NBT_TARGET_Y);
        tag.remove(HerobrinePortalProcedure.NBT_SPEED);
        tag.remove(HerobrinePortalProcedure.NBT_TICKS);
        tag.remove(HerobrinePortalProcedure.NBT_MAX_TICKS);

        if (entity instanceof HerobrineMob herobrineMob) {
            if (herobrineMob.getGregUUID() != null) {
                Entity greg = ((ServerLevel) herobrineMob.level()).getEntity(herobrineMob.getGregUUID());
                if (greg instanceof Herobrine4Entity herobrine4Entity && herobrine4Entity.isAlive()) {
                    if (herobrine4Entity.isSummoning()) {
                        herobrine4Entity.setSummoning(false);
                        herobrine4Entity.setNoAi(false);
                    }
                }
            }
        }
    }
}
