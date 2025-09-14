package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.block.DarkObUpBlock;
import com.pla.annoyingvillagers.blockentity.DarkObUpBlockEntity;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.util.ObsidianWeaponUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class DarkObSsOnEntityInsideProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            BlockPos pos = BlockPos.containing(d0, d1, d2);
            BlockState state = levelaccessor.getBlockState(pos);

            if (entity instanceof Player && levelaccessor.getBlockEntity(pos) instanceof DarkObUpBlockEntity dark) {
                var owner = dark.getOwner();
                if (owner != null && owner.equals(((Player) entity).getUUID())) {
                    return;
                }
            }

            boolean fromPlayer =
                    state.getBlock() instanceof DarkObUpBlock
                            && state.hasProperty(DarkObUpBlock.FROM_PLAYER)
                            && state.getValue(DarkObUpBlock.FROM_PLAYER);

            if (!fromPlayer && ObsidianWeaponUtil.isHerobrineFaction(entity)) {
                return;
            }

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute("execute at @s run particle annoyingvillagers:spark ^ ^1.5 ^0.8 0 0 0 0.1 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                    
                }
            }

            if (AnnoyingVillagersConfig.HEROBRINE_OBSIDIAN_BREAK_ARMOR.get()) {

                LivingEntity livingentity1;
                ItemStack itemstack;

                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack = livingentity1.getItemBySlot(EquipmentSlot.FEET);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                if (itemstack.hurt((int) Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), RandomSource.create(), (ServerPlayer) null)) {
                    itemstack.shrink(1);
                    itemstack.setDamageValue(0);
                }

                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack = livingentity1.getItemBySlot(EquipmentSlot.LEGS);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                if (itemstack.hurt((int) Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), RandomSource.create(), (ServerPlayer) null)) {
                    itemstack.shrink(1);
                    itemstack.setDamageValue(0);
                }

                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack = livingentity1.getItemBySlot(EquipmentSlot.CHEST);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                if (itemstack.hurt((int) Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), RandomSource.create(), (ServerPlayer) null)) {
                    itemstack.shrink(1);
                    itemstack.setDamageValue(0);
                }

                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack = livingentity1.getItemBySlot(EquipmentSlot.HEAD);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                if (itemstack.hurt((int) Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), RandomSource.create(), (ServerPlayer) null)) {
                    itemstack.shrink(1);
                    itemstack.setDamageValue(0);
                }
            }

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/combat/hit_short\" 0 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                    
                }
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity2 = (LivingEntity) entity;

                if (!livingentity2.level().isClientSide()) {
                    livingentity2.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 8, false, false));
                }
            }

            entity.setDeltaMovement(new Vec3(0.0D, 0.0D, 0.0D));
            if (Math.random() <= 0.2D) {
                if (entity instanceof LivingEntity livingEntity) {
                    float strength = 1.0F;
                    double dx = d0 - entity.getX();
                    double dz = d2 - entity.getZ();
                    livingEntity.knockback(strength, dx, dz);
                }
            }
            entity.hurt(entity.level().damageSources().generic(), 1.0F);
        }
    }
}
