package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

import java.util.Random;

public class HerobrineTransfromProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity herobrineEntity) {
        if (entity == null) return;
        Random random = new Random();
        if (random.nextFloat() >= AnnoyingVillagersConfig.HEROBRINE_POSSESS_RATE.get().floatValue()) {
            return;
        }

        if (entity instanceof PlayerNpcEntity) {
            if (!(world instanceof ServerLevel serverLevel)) return;
            entity.getPersistentData().putBoolean("die_by_possess", true);
            Entity possessed;
            if (herobrineEntity instanceof HerobrineCloneEntity || herobrineEntity instanceof HerobrineChrisEntity || herobrineEntity instanceof NullEntity
                    || herobrineEntity instanceof NullSwordEntity || herobrineEntity instanceof NullAxeEntity
                    || herobrineEntity instanceof NullPickaxeEntity || herobrineEntity instanceof NullShovelEntity
                    || herobrineEntity instanceof NullHoeEntity || herobrineEntity instanceof GlaiveHerobrineEntity
                    || herobrineEntity instanceof AegisHerobrineEntity || herobrineEntity instanceof ReaperHerobrineEntity
                    || herobrineEntity instanceof SwordsmanHerobrineEntity || herobrineEntity instanceof SledgehammerHerobrineEntity) {
                possessed = new LowHerobrineCloneEntity(AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE.get(), serverLevel);
            } else {
                possessed = new LowShadowHerobrineCloneEntity(AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get(), serverLevel);
            }
            possessed.moveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
            if (entity instanceof LivingEntity victim) {
                if (victim.getCustomName() != null) {
                    possessed.getPersistentData().putString("killed_name", victim.getCustomName().getString());
                }

                if (!victim.getItemBySlot(EquipmentSlot.HEAD).getItem().equals(Items.PLAYER_HEAD)) {
                    possessed.setItemSlot(EquipmentSlot.HEAD, victim.getItemBySlot(EquipmentSlot.HEAD).copy());
                }
                possessed.setItemSlot(EquipmentSlot.CHEST, victim.getItemBySlot(EquipmentSlot.CHEST).copy());
                possessed.setItemSlot(EquipmentSlot.LEGS, victim.getItemBySlot(EquipmentSlot.LEGS).copy());
                possessed.setItemSlot(EquipmentSlot.FEET, victim.getItemBySlot(EquipmentSlot.FEET).copy());
                possessed.setItemSlot(EquipmentSlot.MAINHAND, victim.getItemBySlot(EquipmentSlot.MAINHAND).copy());
                possessed.setItemSlot(EquipmentSlot.OFFHAND, victim.getItemBySlot(EquipmentSlot.OFFHAND).copy());
            }
            Mob mob = (Mob) possessed;
            if (mob instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity) {
                lowHerobrineCloneEntity.setUsername(((PlayerMobEntity) entity).getUsername());
                lowHerobrineCloneEntity.setProfile(((PlayerMobEntity) entity).getProfile());
                if (herobrineEntity instanceof HerobrineMob herobrineMob) {
                    lowHerobrineCloneEntity.setPossessedByEntity(herobrineMob);
                    lowHerobrineCloneEntity.setPossessedByUuid(herobrineMob.getUUID());
                } else if (herobrineEntity instanceof NullSwordEntity nullSwordEntity) {
                    lowHerobrineCloneEntity.setPossessedByEntity(nullSwordEntity.getNullEntity());
                    lowHerobrineCloneEntity.setPossessedByUuid(nullSwordEntity.getNullUUID());
                } else if (herobrineEntity instanceof NullAxeEntity nullAxeEntity) {
                    lowHerobrineCloneEntity.setPossessedByEntity(nullAxeEntity.getNullEntity());
                    lowHerobrineCloneEntity.setPossessedByUuid(nullAxeEntity.getNullUUID());
                } else if (herobrineEntity instanceof NullPickaxeEntity nullPickaxeEntity) {
                    lowHerobrineCloneEntity.setPossessedByEntity(nullPickaxeEntity.getNullEntity());
                    lowHerobrineCloneEntity.setPossessedByUuid(nullPickaxeEntity.getNullUUID());
                } else if (herobrineEntity instanceof NullShovelEntity nullShovelEntity) {
                    lowHerobrineCloneEntity.setPossessedByEntity(nullShovelEntity.getNullEntity());
                    lowHerobrineCloneEntity.setPossessedByUuid(nullShovelEntity.getNullUUID());
                } else {
                    NullHoeEntity nullHoeEntity = (NullHoeEntity) herobrineEntity;
                    lowHerobrineCloneEntity.setPossessedByEntity(nullHoeEntity.getNullEntity());
                    lowHerobrineCloneEntity.setPossessedByUuid(nullHoeEntity.getNullUUID());
                }
            }
            if (mob instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity) {
                if (herobrineEntity instanceof HerobrineMob herobrineMob) {
                    lowShadowHerobrineCloneEntity.setPossessedByEntity(herobrineMob);
                    lowShadowHerobrineCloneEntity.setPossessedByUuid(herobrineMob.getUUID());
                }
            }
            mob.finalizeSpawn(serverLevel, world.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            serverLevel.addFreshEntity(possessed);
        }
    }
}
