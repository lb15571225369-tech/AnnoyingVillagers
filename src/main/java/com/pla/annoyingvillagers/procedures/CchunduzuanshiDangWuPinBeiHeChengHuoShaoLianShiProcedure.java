package com.pla.annoyingvillagers.procedures;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class CchunduzuanshiDangWuPinBeiHeChengHuoShaoLianShiProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            itemstack.enchant(Enchantments.VANISHING_CURSE, 10);
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                if (!livingentity.level.isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance(MobEffects.POISON, 20, 2, false, false));
                }
            }

            if (levelaccessor instanceof Level) {
                Level level = (Level) levelaccessor;

                if (!level.isClientSide()) {
                    level.addFreshEntity(new ExperienceOrb(level, d0, d1, d2, 2));
                }
            }

            entity.hurt(DamageSource.GENERIC, 5.0F);
            if (entity instanceof ServerPlayer) {
                ServerPlayer serverplayer = (ServerPlayer) entity;

                serverplayer.setGameMode(GameType.SURVIVAL);
            }

            entity.setSecondsOnFire(15);
        }
    }
}

