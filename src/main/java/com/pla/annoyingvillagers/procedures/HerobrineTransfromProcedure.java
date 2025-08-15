package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.Herobrine1Entity;
import com.pla.annoyingvillagers.entity.Herobrine2Entity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

import java.util.Random;

public class HerobrineTransfromProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity herobrineEntity) {
        if (entity == null) return;
        Random random = new Random();
        if (random.nextFloat() >= AnnoyingVillagersConfig.HEROBRINE_POSSESS_RATE.get().floatValue()) {
            return;
        }

        if (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString().equals("player_mobs:player_mob")) {
            if (!(world instanceof ServerLevel serverLevel)) return;
            Entity possessed;
            if (herobrineEntity instanceof Herobrine1Entity) {
                possessed = new Herobrine1Entity(AnnoyingVillagersModEntities.HEROBRINE_1.get(), serverLevel);
            } else if (herobrineEntity instanceof Herobrine2Entity) {
                possessed = new Herobrine2Entity(AnnoyingVillagersModEntities.HEROBRINE_2.get(), serverLevel);
            } else {
                return;
            }

            possessed.moveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
            if (entity instanceof LivingEntity victim) {
                if (victim.getCustomName() != null) {
                    possessed.getPersistentData().putString("killed_name", victim.getCustomName().getString());
                }

                possessed.setItemSlot(EquipmentSlot.HEAD, victim.getItemBySlot(EquipmentSlot.HEAD).copy());
                possessed.setItemSlot(EquipmentSlot.CHEST, victim.getItemBySlot(EquipmentSlot.CHEST).copy());
                possessed.setItemSlot(EquipmentSlot.LEGS, victim.getItemBySlot(EquipmentSlot.LEGS).copy());
                possessed.setItemSlot(EquipmentSlot.FEET, victim.getItemBySlot(EquipmentSlot.FEET).copy());
            }
            Mob mob = (Mob) possessed;
            mob.finalizeSpawn(serverLevel, world.getCurrentDifficultyAt(possessed.blockPosition()),
                    MobSpawnType.MOB_SUMMONED, null, null);
            entity.getPersistentData().putBoolean("die_by_possess", true);
            serverLevel.addFreshEntity(possessed);
        }
    }
}
