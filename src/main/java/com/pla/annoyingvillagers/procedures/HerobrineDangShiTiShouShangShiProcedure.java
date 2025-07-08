package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;

public class HerobrineDangShiTiShouShangShiProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (Math.random() <= 0.5D && !entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect give @s annoying_villagersbychentu:gedang 1 0 true");
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                if (!livingentity.level.isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.NPC_KICK_EFFECT.get(), 10, 0, false, false));
                }
            }

        }
    }
}
