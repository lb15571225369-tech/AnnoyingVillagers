package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;

public class BlueDemon2ParryingProcedure {

    public static void execute(Entity entity) throws CommandSyntaxException {
        if (entity != null) {
            if (Math.random() <= 0.2D && !entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().getDispatcher().execute("effect give @s annoyingvillagers:block 1 0 true", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                if (!livingentity.level.isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.NPC_KICK_EFFECT.get(), 12, 0, false, false));
                }
            }

        }
    }
}
