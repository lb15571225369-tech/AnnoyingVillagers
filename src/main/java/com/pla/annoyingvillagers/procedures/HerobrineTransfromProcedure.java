package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.util.InventoryUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.Items;
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
            entity.getPersistentData().putBoolean("die_by_possess", true);
            Entity possessed;
            if (herobrineEntity instanceof Herobrine1Entity || herobrineEntity instanceof NullEntity
                    || herobrineEntity instanceof NullSwordEntity || herobrineEntity instanceof NullAxeEntity
                    || herobrineEntity instanceof  NullPickaxeEntity || herobrineEntity instanceof NullShovelEntity
                    || herobrineEntity instanceof  NullHoeEntity || herobrineEntity instanceof GlaiveHerobrineEntity
                    || herobrineEntity instanceof AegisHerobrineEntity || herobrineEntity instanceof ReaperHerobrineEntity
                    || herobrineEntity instanceof SwordsManHerobrineEntity || herobrineEntity instanceof SledgehammerHerobrineEntity) {
                possessed = new Herobrine5Entity(AnnoyingVillagersModEntities.HEROBRINE_5.get(), serverLevel);
            } else {
                possessed = new Herobrine6Entity(AnnoyingVillagersModEntities.HEROBRINE_6.get(), serverLevel);
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
            if (mob instanceof Herobrine5Entity herobrine5Entity) {
                herobrine5Entity.setUsername(((PlayerMobEntity) entity).getUsername());
                herobrine5Entity.setProfile(((PlayerMobEntity) entity).getProfile());
            }
            mob.finalizeSpawn(serverLevel, world.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            serverLevel.addFreshEntity(possessed);
            if (!possessed.level().isClientSide() && possessed.getServer() != null) {
                try {
                    possessed.getServer().getCommands().getDispatcher().execute(
                            "indestructible @s play \"epicfight:biped/living/landing\" 0 1",
                            possessed.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {

                }
            }
        }
    }
}
