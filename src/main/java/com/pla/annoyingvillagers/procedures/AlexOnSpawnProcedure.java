package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.AlexEntity;
import com.pla.annoyingvillagers.entity.JevEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;

public class AlexOnSpawnProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            entity.getPersistentData().putBoolean("av_npc", true);
            LivingEntity livingentity;

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

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team add alex",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team modify alex friendlyFire false",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team join alex @s",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
            }
        }
    }
}
