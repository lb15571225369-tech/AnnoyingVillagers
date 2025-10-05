package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class NullWeaponPlayerProcedure {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase != TickEvent.Phase.END) return;
        if (player.level().isClientSide()) return;

        ServerLevel server = (ServerLevel) player.level();
        CompoundTag data = player.getPersistentData();

        if (data.contains("NullSwordUUID")) {
            Entity entity = server.getEntity(data.getUUID("NullSwordUUID"));
            if (entity != null && entity instanceof NullSwordEntity) {
            } else {
                data.remove("NullSwordUUID");
            }
        }
        if (data.contains("NullAxeUUID")) {
            Entity entity = server.getEntity(data.getUUID("NullAxeUUID"));
            if (entity != null && entity instanceof NullAxeEntity) {
            } else {
                data.remove("NullAxeUUID");
            }
        }
        if (data.contains("NullPickaxeUUID")) {
            Entity entity = server.getEntity(data.getUUID("NullPickaxeUUID"));
            if (entity != null && entity instanceof NullPickaxeEntity) {
            } else {
                data.remove("NullPickaxeUUID");
            }
        }
        if (data.contains("NullHoeUUID")) {
            Entity entity = server.getEntity(data.getUUID("NullHoeUUID"));
            if (entity != null && entity instanceof NullHoeEntity) {
            } else {
                data.remove("NullHoeUUID");
            }
        }
        if (data.contains("NullShovelUUID")) {
            Entity entity = server.getEntity(data.getUUID("NullShovelUUID"));
            if (entity != null && entity instanceof NullShovelEntity) {
            } else {
                data.remove("NullShovelUUID");
            }
        }

        if (Math.random() <= 0.1D) {
            RandomSource randomSource = RandomSource.create();
            if (data.contains("NullSwordUUID")) {
                Entity entity = server.getEntity(data.getUUID("NullSwordUUID"));
                if (entity instanceof NullSwordEntity nullSwordEntity) {
                    nullSwordEntity.moveTo(player.getX() + (double) Mth.nextInt(randomSource, -4, 4), player.getY() + (double) Mth.nextInt(randomSource, -2, 2), player.getZ() + (double) Mth.nextInt(randomSource, -4, 4));
                }
            }
            if (data.contains("NullAxeUUID")) {
                Entity entity = server.getEntity(data.getUUID("NullAxeUUID"));
                if (entity instanceof NullAxeEntity nullAxeEntity) {
                    nullAxeEntity.moveTo(player.getX() + (double) Mth.nextInt(randomSource, -4, 4), player.getY() + (double) Mth.nextInt(randomSource, -2, 2), player.getZ() + (double) Mth.nextInt(randomSource, -4, 4));
                }
            }
            if (data.contains("NullPickaxeUUID")) {
                Entity entity = server.getEntity(data.getUUID("NullPickaxeUUID"));
                if (entity instanceof NullPickaxeEntity nullPickaxeEntity) {
                    nullPickaxeEntity.moveTo(player.getX() + (double) Mth.nextInt(randomSource, -4, 4), player.getY() + (double) Mth.nextInt(randomSource, -2, 2), player.getZ() + (double) Mth.nextInt(randomSource, -4, 4));
                }
            }
            if (data.contains("NullHoeUUID")) {
                Entity entity = server.getEntity(data.getUUID("NullHoeUUID"));
                if (entity instanceof NullHoeEntity nullHoeEntity) {
                    nullHoeEntity.moveTo(player.getX() + (double) Mth.nextInt(randomSource, -4, 4), player.getY() + (double) Mth.nextInt(randomSource, -2, 2), player.getZ() + (double) Mth.nextInt(randomSource, -4, 4));
                }
            }
            if (data.contains("NullShovelUUID")) {
                Entity entity = server.getEntity(data.getUUID("NullShovelUUID"));
                if (entity instanceof NullShovelEntity nullShovelEntity) {
                    nullShovelEntity.moveTo(player.getX() + (double) Mth.nextInt(randomSource, -4, 4), player.getY() + (double) Mth.nextInt(randomSource, -2, 2), player.getZ() + (double) Mth.nextInt(randomSource, -4, 4));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttacked(LivingAttackEvent event) {
        LivingEntity target = event.getEntity();
        DamageSource source = event.getSource();

        if (source.getEntity() == null) {
            return;
        }

        if (target instanceof Player player && !player.level().isClientSide()) {
            CompoundTag data = player.getPersistentData();
            ServerLevel server = (ServerLevel) player.level();
            boolean parried = false;

            if (data.contains("NullSwordUUID") && Math.random() <= 0.5D) {
                Entity entity = server.getEntity(data.getUUID("NullSwordUUID"));
                if (entity instanceof NullSwordEntity nullSwordEntity) {
                    parried = true;
                    nullSwordEntity.moveTo(player.getX(), player.getY(), player.getZ());
                    nullSwordEntity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 20, 1, false, false));
                }
            }
            if (data.contains("NullAxeUUID") && Math.random() <= 0.5D && !parried) {
                Entity entity = server.getEntity(data.getUUID("NullAxeUUID"));
                if (entity instanceof NullAxeEntity nullAxeEntity) {
                    parried = true;
                    nullAxeEntity.moveTo(player.getX(), player.getY(), player.getZ());
                    nullAxeEntity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 20, 1, false, false));
                }
            }
            if (data.contains("NullPickaxeUUID") && Math.random() <= 0.5D && !parried) {
                Entity entity = server.getEntity(data.getUUID("NullPickaxeUUID"));
                parried = true;
                if (entity instanceof NullPickaxeEntity nullPickaxeEntity) {
                    nullPickaxeEntity.moveTo(player.getX(), player.getY(), player.getZ());
                    nullPickaxeEntity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 20, 1, false, false));
                }
            }
            if (data.contains("NullHoeUUID") && Math.random() <= 0.5D && !parried) {
                Entity entity = server.getEntity(data.getUUID("NullHoeUUID"));
                if (entity instanceof NullHoeEntity nullHoeEntity) {
                    parried = true;
                    nullHoeEntity.moveTo(player.getX(), player.getY(), player.getZ());
                    nullHoeEntity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 20, 1, false, false));
                }
            }
            if (data.contains("NullShovelUUID") && Math.random() <= 0.5D && !parried) {
                Entity entity = server.getEntity(data.getUUID("NullShovelUUID"));
                if (entity instanceof NullShovelEntity nullShovelEntity) {
                    nullShovelEntity.moveTo(player.getX(), player.getY(), player.getZ());
                    nullShovelEntity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 20, 1, false, false));
                }
            }
        }

        if (target instanceof NullEntity nullEntity && !nullEntity.level().isClientSide()) {
            double d0 = nullEntity.getX();
            double d1 = nullEntity.getY();
            double d2 = nullEntity.getZ();
            double chance = Math.random();
            if (chance <= 0.2D && nullEntity.getNullSwordEntity() != null) {
                nullEntity.getNullSwordEntity().moveTo(d0, d1, d2);
                nullEntity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 20, 1, false, false));
            } else if (chance <= 0.4D && nullEntity.getNullAxeEntity() != null) {
                nullEntity.getNullAxeEntity().moveTo(d0, d1, d2);
                nullEntity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 20, 1, false, false));
            } else if (chance <= 0.6D && nullEntity.getNullPickaxeEntity() != null) {
                nullEntity.getNullPickaxeEntity().moveTo(d0, d1, d2);
                nullEntity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 20, 1, false, false));
            } else if (chance <= 0.8D && nullEntity.getNullShovelEntity() != null) {
                nullEntity.getNullShovelEntity().moveTo(d0, d1, d2);
                nullEntity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 20, 1, false, false));
            } else if (nullEntity.getNullHoeEntity() != null) {
                nullEntity.getNullHoeEntity().moveTo(d0, d1, d2);
                nullEntity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 20, 1, false, false));
            }
        }
    }
}
