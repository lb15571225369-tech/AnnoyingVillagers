package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.entity.Herobrine2Entity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class Herobrine2OnAwardKillScoreProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) return;

        if (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString().equals("player_mobs:player_mob")) {
            new DelayedTask(50) {
                @Override
                public void run() {
                    if (!(world instanceof ServerLevel serverLevel)) return;

                    Herobrine2Entity herobrine = new Herobrine2Entity(AnnoyingVillagersModEntities.HEROBRINE_2.get(), serverLevel);
                    herobrine.moveTo(x, y, z, world.getRandom().nextFloat() * 360.0F, 0.0F);

                    if (entity instanceof LivingEntity victim) {
                        if (victim.getCustomName() != null) {
                            herobrine.getPersistentData().putString("killed_name", victim.getCustomName().getString());
                        }

                        herobrine.setItemSlot(EquipmentSlot.HEAD, victim.getItemBySlot(EquipmentSlot.HEAD).copy());
                        herobrine.setItemSlot(EquipmentSlot.CHEST, victim.getItemBySlot(EquipmentSlot.CHEST).copy());
                        herobrine.setItemSlot(EquipmentSlot.LEGS, victim.getItemBySlot(EquipmentSlot.LEGS).copy());
                        herobrine.setItemSlot(EquipmentSlot.FEET, victim.getItemBySlot(EquipmentSlot.FEET).copy());
                    }

                    if (herobrine instanceof Mob) {
                        Mob mob = (Mob) herobrine;
                        mob.finalizeSpawn(serverLevel, world.getCurrentDifficultyAt(herobrine.blockPosition()),
                                MobSpawnType.MOB_SUMMONED, null, null);
                    }
                    serverLevel.addFreshEntity(herobrine);
                }
            };
        }
    }
}
