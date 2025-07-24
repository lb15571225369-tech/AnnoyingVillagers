package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class CraftingTableOnUseProcedure {

    public static void execute(Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20, 3));
                }
            }

            if (entity instanceof Player) {
                Player player = (Player) entity;

                player.getCooldowns().addCooldown(itemstack.getItem(), 28);
            }

        }
    }
}

