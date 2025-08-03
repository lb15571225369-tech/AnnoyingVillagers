package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.JevEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;

public class AlexOnSpawnProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            LivingEntity livingentity;
            ItemStack itemstack;

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = livingentity.getMainHandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.SMITE, 4);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = livingentity.getMainHandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.SHARPNESS, 9);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = livingentity.getMainHandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.SWEEPING_EDGE, 4);
            if (levelaccessor instanceof ServerLevel) {
                ServerLevel serverlevel = (ServerLevel) levelaccessor;
                JevEntity alexvillagerentity = new JevEntity((EntityType) AnnoyingVillagersModEntities.JEV.get(), serverlevel);

                alexvillagerentity.moveTo(d0 + Mth.nextDouble(AnnoyingVillagers.randomSource, 1.0D, 10.0D), d1 + Mth.nextDouble(AnnoyingVillagers.randomSource, 1.0D, 10.0D), d2 + Mth.nextDouble(AnnoyingVillagers.randomSource, 1.0D, 10.0D), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                if (alexvillagerentity instanceof Mob) {
                    Mob mob = (Mob) alexvillagerentity;

                    mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(alexvillagerentity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
                }

                levelaccessor.addFreshEntity(alexvillagerentity);
            }

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "tellraw @a {\"text\":\"Alex has joined the game\",\"color\":\"yellow\"}",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
            }

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 999999, 0, false, false));
                }
            }

            entity.getPersistentData().putBoolean("a_player", true);
        }
    }
}
