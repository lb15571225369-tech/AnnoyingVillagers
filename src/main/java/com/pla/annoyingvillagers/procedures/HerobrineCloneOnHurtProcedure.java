package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;

public class HerobrineCloneOnHurtProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (Math.random() <= 0.5D && !entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "effect give @s annoyingvillagers:block 1 0 true",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                    
                }
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.NPC_KICK_EFFECT.get(), 10, 0, false, false));
                }
            }

        }
    }
}
