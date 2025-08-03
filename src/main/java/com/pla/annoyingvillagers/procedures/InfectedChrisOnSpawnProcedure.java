package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class InfectedChrisOnSpawnProcedure {

    public static void execute(LevelAccessor levelaccessor, final Entity entity) {
        if (entity != null) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;

                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.HEROBRINE.get(), 3000, 1));
                }
            }
            new DelayedTask(1) {
                public void run() {
                    Entity entity1 = entity;
                    Player player;
                    LivingEntity livingentity1;

                    if (entity1 instanceof Player) {
                        player = (Player)entity1;
                        player.getInventory().armor.set(0, new ItemStack(Blocks.AIR));
                        player.getInventory().setChanged();
                    } else if (entity1 instanceof LivingEntity) {
                        livingentity1 = (LivingEntity)entity1;
                        livingentity1.setItemSlot(EquipmentSlot.FEET, new ItemStack(Blocks.AIR));
                    }

                    entity1 = entity;
                    if (entity1 instanceof Player) {
                        player = (Player)entity1;
                        player.getInventory().armor.set(1, new ItemStack(Blocks.AIR));
                        player.getInventory().setChanged();
                    } else if (entity1 instanceof LivingEntity) {
                        livingentity1 = (LivingEntity)entity1;
                        livingentity1.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Blocks.AIR));
                    }

                    entity1 = entity;
                    if (entity1 instanceof Player) {
                        player = (Player)entity1;
                        player.getInventory().armor.set(2, new ItemStack(Blocks.AIR));
                        player.getInventory().setChanged();
                    } else if (entity1 instanceof LivingEntity) {
                        livingentity1 = (LivingEntity)entity1;
                        livingentity1.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Blocks.AIR));
                    }

                    entity1 = entity;
                    if (entity1 instanceof Player) {
                        player = (Player)entity1;
                        player.getInventory().armor.set(3, new ItemStack(Blocks.AIR));
                        player.getInventory().setChanged();
                    } else if (entity1 instanceof LivingEntity) {
                        livingentity1 = (LivingEntity)entity1;
                        livingentity1.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Blocks.AIR));
                    }
                }
            };
        }
    }
}
