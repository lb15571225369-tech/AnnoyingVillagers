package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.entity.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

public class DarkObSsOnEntityInsideProcedure {

    public static void execute(LevelAccessor levelaccessor, Entity entity) {
        if (entity != null) {
            if (entity instanceof Herobrine1Entity || entity instanceof Herobrine2Entity || entity instanceof Herobrine3Entity || entity instanceof Herobrine7Entity || entity instanceof ArmoredHerobrineEntity || entity instanceof ShadowHerobrineEntity || entity instanceof InfectedPlayerMobEntity || entity instanceof InfectedTheMostMoistBurrit0Entity || entity instanceof InfectedChrisEntity) {
                return;
            }

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute("execute at @s run particle annoyingvillagers:spark ^ ^1.5 ^0.8 0 0 0 0.1 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                    
                }
            }

            LivingEntity livingentity1;
            ItemStack itemstack;

            if (entity instanceof LivingEntity) {
                livingentity1 = (LivingEntity) entity;
                itemstack = livingentity1.getItemBySlot(EquipmentSlot.CHEST);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            ItemStack itemstack1 = itemstack;

            if (itemstack1.hurt(1, RandomSource.create(), (ServerPlayer) null)) {
                itemstack1.shrink(1);
                itemstack1.setDamageValue(0);
            }

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/combat/hit_short\" 0 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                    
                }
            }

            if (entity instanceof LivingEntity) {
                livingentity1 = (LivingEntity) entity;
                itemstack = livingentity1.getItemBySlot(EquipmentSlot.HEAD);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack1 = itemstack;
            if (itemstack1.hurt(1, RandomSource.create(), (ServerPlayer) null)) {
                itemstack1.shrink(1);
                itemstack1.setDamageValue(0);
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity2 = (LivingEntity) entity;

                if (!livingentity2.level().isClientSide()) {
                    livingentity2.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 8, false, false));
                }
            }

            entity.setDeltaMovement(new Vec3(0.0D, 0.0D, 0.0D));
            entity.hurt(entity.level().damageSources().magic(), 3.0F);
        }
    }
}
