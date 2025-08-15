package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;

import com.pla.annoyingvillagers.entity.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class DarkObBlockOnEntityInsideProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            if (entity instanceof Herobrine1Entity || entity instanceof Herobrine2Entity || entity instanceof Herobrine3Entity || entity instanceof Herobrine7Entity || entity instanceof ArmoredHerobrineEntity || entity instanceof ShadowHerobrineEntity || entity instanceof InfectedPlayerMobEntity || entity instanceof InfectedTheMostMoistBurrit0Entity || entity instanceof InfectedChrisEntity) {
                return;
            }

            if (levelaccessor instanceof Level) {
                Level level = (Level) levelaccessor;

                if (!level.isClientSide()) {
                    level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID, "ob_place")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                } else {
                    level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID, "ob_place")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                }
            }

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute("execute at @s run particle epicfight:hit_blunt ^ ^1.5 ^0.8 0.1 0.1 0.1 1 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));

                    entity.getServer().getCommands().getDispatcher().execute("execute at @s run particle annoyingvillagers:spark ^ ^1.5 ^0.8 0 0 0 0.1 5", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                    
                }
            }

            entity.hurt(entity.level().damageSources().magic(), 4.0F);
            entity.setDeltaMovement(new Vec3(Mth.nextDouble(RandomSource.create(), -1.0D, -6.0D) * entity.getLookAngle().x, 0.0D, Mth.nextDouble(RandomSource.create(), -1.0D, -6.0D) * entity.getLookAngle().z));
            LivingEntity livingentity1;
            ItemStack itemstack;

            if (entity instanceof LivingEntity) {
                livingentity1 = (LivingEntity) entity;
                itemstack = livingentity1.getItemBySlot(EquipmentSlot.HEAD);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            ItemStack itemstack1 = itemstack;

            if (itemstack1.hurt((int) Mth.nextDouble(RandomSource.create(), 1.0D, 5.0D), RandomSource.create(), (ServerPlayer) null)) {
                itemstack1.shrink(1);
                itemstack1.setDamageValue(0);
            }

            if (entity instanceof LivingEntity) {
                livingentity1 = (LivingEntity) entity;
                itemstack = livingentity1.getItemBySlot(EquipmentSlot.CHEST);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack1 = itemstack;
            if (itemstack1.hurt((int) Mth.nextDouble(RandomSource.create(), 1.0D, 5.0D), RandomSource.create(), (ServerPlayer) null)) {
                itemstack1.shrink(1);
                itemstack1.setDamageValue(0);
            }

            if (entity instanceof LivingEntity) {
                livingentity1 = (LivingEntity) entity;
                itemstack = livingentity1.getItemBySlot(EquipmentSlot.LEGS);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack1 = itemstack;
            if (itemstack1.hurt((int) Mth.nextDouble(RandomSource.create(), 1.0D, 5.0D), RandomSource.create(), (ServerPlayer) null)) {
                itemstack1.shrink(1);
                itemstack1.setDamageValue(0);
            }

            if (entity instanceof LivingEntity) {
                livingentity1 = (LivingEntity) entity;
                itemstack = livingentity1.getItemBySlot(EquipmentSlot.FEET);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack1 = itemstack;
            if (itemstack1.hurt((int) Mth.nextDouble(RandomSource.create(), 1.0D, 5.0D), RandomSource.create(), (ServerPlayer) null)) {
                itemstack1.shrink(1);
                itemstack1.setDamageValue(0);
            }

            if (entity instanceof LivingEntity) {
                livingentity1 = (LivingEntity) entity;
                itemstack = livingentity1.getMainHandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack1 = itemstack;
            if (itemstack1.hurt((int) Mth.nextDouble(RandomSource.create(), 1.0D, 5.0D), RandomSource.create(), (ServerPlayer) null)) {
                itemstack1.shrink(1);
                itemstack1.setDamageValue(0);
            }

            if (entity instanceof LivingEntity) {
                livingentity1 = (LivingEntity) entity;
                itemstack = livingentity1.getOffhandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack1 = itemstack;
            if (itemstack1.hurt((int) Mth.nextDouble(RandomSource.create(), 1.0D, 5.0D), RandomSource.create(), (ServerPlayer) null)) {
                itemstack1.shrink(1);
                itemstack1.setDamageValue(0);
            }

        }
    }
}
