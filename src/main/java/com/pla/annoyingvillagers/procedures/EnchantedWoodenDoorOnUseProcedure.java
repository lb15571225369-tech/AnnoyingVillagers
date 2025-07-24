package com.pla.annoyingvillagers.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantedWoodenDoorOnUseProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            if (levelaccessor instanceof Level) {
                Level level = (Level) levelaccessor;

                if (!level.isClientSide()) {
                    level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "block.wooden_door.close")), SoundSource.BLOCKS, 1.0F, 1.0F);
                } else {
                    level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "block.wooden_door.close")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }
            }

            LivingEntity livingentity;

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1, 5, false, false));
                }
            }

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 2, 10, false, false));
                }
            }

        }
    }
}
