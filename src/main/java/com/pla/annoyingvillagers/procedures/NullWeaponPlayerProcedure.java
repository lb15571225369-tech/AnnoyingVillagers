package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.NullSwordEntity;
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

        if (Math.random() <= 0.1D) {
            RandomSource randomSource = RandomSource.create();
            if (data.contains("NullSwordUUID")) {
                Entity entity = server.getEntity(data.getUUID("NullSwordUUID"));
                if (entity instanceof NullSwordEntity nullSwordEntity) {
                    nullSwordEntity.moveTo(player.getX() + (double) Mth.nextInt(randomSource, -4, 4), player.getY() + (double) Mth.nextInt(randomSource, -2, 2), player.getZ() + (double) Mth.nextInt(randomSource, -4, 4));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttacked(LivingAttackEvent event) {
        LivingEntity target = event.getEntity();
        DamageSource source = event.getSource();

        if (target instanceof Player player && source.getEntity() instanceof NullSwordEntity nullSwordEntity) {
            if (player.level().isClientSide()) return;
            if (nullSwordEntity.getPlayerUUID() != null && nullSwordEntity.getPlayerUUID().equals(player.getUUID())) {
                event.setCanceled(true);
            }
        }
        if (target instanceof NullSwordEntity nullSwordEntity && source.getEntity() instanceof Player player) {
            if (nullSwordEntity.level().isClientSide()) return;
            if (nullSwordEntity.getPlayerUUID() != null && player.getUUID().equals(nullSwordEntity.getPlayerUUID())) {
                event.setCanceled(true);
            }
        }

        if (source.getEntity() == null) {
            return;
        }

        if (target instanceof Player player && !player.level().isClientSide()) {
            CompoundTag data = player.getPersistentData();
            ServerLevel server = (ServerLevel) player.level();
            if (data.contains("NullSwordUUID")) {
                Entity entity = server.getEntity(data.getUUID("NullSwordUUID"));
                if (entity instanceof NullSwordEntity nullSwordEntity) {
                    nullSwordEntity.moveTo(player.getX(), player.getY(), player.getZ());
                    nullSwordEntity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 20, 1, false, false));
                }
            }
        }
    }
}
