package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class NullWeaponPlayerEvent {
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

        if (player.tickCount % 20 == 0) {
            if (data.contains("NullSwordUUID")) {
                Entity entity = server.getEntity(data.getUUID("NullSwordUUID"));
                if (entity instanceof NullSwordEntity nullSwordEntity) {
                    if (!nullSwordEntity.isReleased()) {
                        nullSwordEntity.moveTo(player.getX() + new Random().nextDouble(-4, 4), player.getY() + new Random().nextDouble(-2, 2), player.getZ() + new Random().nextDouble(-4, 4));
                    } else if (nullSwordEntity.isReleased() && player.getLastHurtByMob() != null || player.getLastHurtMob() != null) {
                        LivingEntity target = player.getLastHurtByMob() != null ? player.getLastHurtByMob() : (player.getLastHurtMob() != null ? player.getLastHurtMob() : null);
                        if (target != null && target.isAlive()) {
                            nullSwordEntity.moveTo(target.getX() + new Random().nextDouble(-4, 4), target.getY() + new Random().nextDouble(-2, 2), target.getZ() + new Random().nextDouble(-4, 4));
                        } else {
                            nullSwordEntity.stopRelease();
                        }
                    }
                }
            }
            if (data.contains("NullAxeUUID")) {
                Entity entity = server.getEntity(data.getUUID("NullAxeUUID"));
                if (entity instanceof NullAxeEntity nullAxeEntity) {
                    if (!nullAxeEntity.isReleased()) {
                        nullAxeEntity.moveTo(player.getX() + new Random().nextDouble(-4, 4), player.getY() + new Random().nextDouble(-2, 2), player.getZ() + new Random().nextDouble(-4, 4));
                    } else if (nullAxeEntity.isReleased() && player.getLastHurtByMob() != null || player.getLastHurtMob() != null) {
                        LivingEntity target = player.getLastHurtByMob() != null ? player.getLastHurtByMob() : (player.getLastHurtMob() != null ? player.getLastHurtMob() : null);
                        if (target != null && target.isAlive()) {
                            nullAxeEntity.moveTo(target.getX() + new Random().nextDouble(-4, 4), target.getY() + new Random().nextDouble(-2, 2), target.getZ() + new Random().nextDouble(-4, 4));
                        } else {
                            nullAxeEntity.stopRelease();
                        }
                    }
                }
            }
            if (data.contains("NullPickaxeUUID")) {
                Entity entity = server.getEntity(data.getUUID("NullPickaxeUUID"));
                if (entity instanceof NullPickaxeEntity nullPickaxeEntity) {
                    if (!nullPickaxeEntity.isReleased()) {
                        nullPickaxeEntity.moveTo(player.getX() + new Random().nextDouble(-4, 4), player.getY() + new Random().nextDouble(-2, 2), player.getZ() + new Random().nextDouble(-4, 4));
                    } else if (nullPickaxeEntity.isReleased() && player.getLastHurtByMob() != null || player.getLastHurtMob() != null) {
                        LivingEntity target = player.getLastHurtByMob() != null ? player.getLastHurtByMob() : (player.getLastHurtMob() != null ? player.getLastHurtMob() : null);
                        if (target != null && target.isAlive()) {
                            nullPickaxeEntity.moveTo(target.getX() + new Random().nextDouble(-4, 4), target.getY() + new Random().nextDouble(-2, 2), target.getZ() + new Random().nextDouble(-4, 4));
                        } else {
                            nullPickaxeEntity.stopRelease();
                        }
                    }
                }
            }
            if (data.contains("NullHoeUUID")) {
                Entity entity = server.getEntity(data.getUUID("NullHoeUUID"));
                if (entity instanceof NullHoeEntity nullHoeEntity) {
                    if (!nullHoeEntity.isReleased()) {
                        nullHoeEntity.moveTo(player.getX() + new Random().nextDouble(-4, 4), player.getY() + new Random().nextDouble(-2, 2), player.getZ() + new Random().nextDouble(-4, 4));
                    } else if (nullHoeEntity.isReleased() && player.getLastHurtByMob() != null || player.getLastHurtMob() != null) {
                        LivingEntity target = player.getLastHurtByMob() != null ? player.getLastHurtByMob() : (player.getLastHurtMob() != null ? player.getLastHurtMob() : null);
                        if (target != null && target.isAlive()) {
                            nullHoeEntity.moveTo(target.getX() + new Random().nextDouble(-4, 4), target.getY() + new Random().nextDouble(-2, 2), target.getZ() + new Random().nextDouble(-4, 4));
                        } else {
                            nullHoeEntity.stopRelease();
                        }
                    }
                }
            }
            if (data.contains("NullShovelUUID")) {
                Entity entity = server.getEntity(data.getUUID("NullShovelUUID"));
                if (entity instanceof NullShovelEntity nullShovelEntity) {
                    if (!nullShovelEntity.isReleased()) {
                        nullShovelEntity.moveTo(player.getX() + new Random().nextDouble(-4, 4), player.getY() + new Random().nextDouble(-2, 2), player.getZ() + new Random().nextDouble(-4, 4));
                    } else if (nullShovelEntity.isReleased()) {
                        LivingEntity target = player.getLastHurtByMob() != null ? player.getLastHurtByMob() : (player.getLastHurtMob() != null ? player.getLastHurtMob() : null);
                        if (target != null && target.isAlive()) {
                            nullShovelEntity.moveTo(target.getX() + new Random().nextDouble(-4, 4), target.getY() + new Random().nextDouble(-2, 2), target.getZ() + new Random().nextDouble(-4, 4));
                        } else {
                            nullShovelEntity.stopRelease();
                        }
                    }
                }
            }
        }

        if (player.tickCount % 60 == 0 && (player.getLastHurtByMob() != null || player.getLastHurtMob() != null)) {
            LivingEntity target = player.getLastHurtByMob() != null ? player.getLastHurtByMob() : player.getLastHurtMob();
            if (target == null || !target.isAlive()) return;
            if (data.contains("NullSwordUUID")) {
                Entity entity = server.getEntity(data.getUUID("NullSwordUUID"));
                if (entity instanceof NullSwordEntity nullSwordEntity) {
                    nullSwordEntity.releaseForAWhile();
                    nullSwordEntity.moveTo(target.getX(), target.getY(), target.getZ());
                }
            }
            if (data.contains("NullAxeUUID")) {
                Entity entity = server.getEntity(data.getUUID("NullAxeUUID"));
                if (entity instanceof NullAxeEntity nullAxeEntity && new Random().nextBoolean()) {
                    nullAxeEntity.releaseForAWhile();
                    nullAxeEntity.moveTo(target.getX(), target.getY(), target.getZ());
                }
            }
            if (data.contains("NullPickaxeUUID")) {
                Entity entity = server.getEntity(data.getUUID("NullPickaxeUUID"));
                if (entity instanceof NullPickaxeEntity nullPickaxeEntity && new Random().nextBoolean()) {
                    nullPickaxeEntity.releaseForAWhile();
                    nullPickaxeEntity.moveTo(target.getX(), target.getY(), target.getZ());
                }
            }
            if (data.contains("NullHoeUUID")) {
                Entity entity = server.getEntity(data.getUUID("NullHoeUUID"));
                if (entity instanceof NullHoeEntity nullHoeEntity && new Random().nextBoolean()) {
                    nullHoeEntity.releaseForAWhile();
                    nullHoeEntity.moveTo(target.getX(), target.getY(), target.getZ());
                }
            }
            if (data.contains("NullShovelUUID")) {
                Entity entity = server.getEntity(data.getUUID("NullShovelUUID"));
                if (entity instanceof NullShovelEntity nullShovelEntity && new Random().nextBoolean()) {
                    nullShovelEntity.releaseForAWhile();
                    nullShovelEntity.moveTo(target.getX(), target.getY(), target.getZ());
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
                }
            }
            if (data.contains("NullAxeUUID") && Math.random() <= 0.5D && !parried) {
                Entity entity = server.getEntity(data.getUUID("NullAxeUUID"));
                if (entity instanceof NullAxeEntity nullAxeEntity) {
                    parried = true;
                    nullAxeEntity.moveTo(player.getX(), player.getY(), player.getZ());
                }
            }
            if (data.contains("NullPickaxeUUID") && Math.random() <= 0.5D && !parried) {
                Entity entity = server.getEntity(data.getUUID("NullPickaxeUUID"));
                parried = true;
                if (entity instanceof NullPickaxeEntity nullPickaxeEntity) {
                    nullPickaxeEntity.moveTo(player.getX(), player.getY(), player.getZ());
                }
            }
            if (data.contains("NullHoeUUID") && Math.random() <= 0.5D && !parried) {
                Entity entity = server.getEntity(data.getUUID("NullHoeUUID"));
                if (entity instanceof NullHoeEntity nullHoeEntity) {
                    parried = true;
                    nullHoeEntity.moveTo(player.getX(), player.getY(), player.getZ());
                }
            }
            if (data.contains("NullShovelUUID") && Math.random() <= 0.5D && !parried) {
                Entity entity = server.getEntity(data.getUUID("NullShovelUUID"));
                if (entity instanceof NullShovelEntity nullShovelEntity) {
                    nullShovelEntity.moveTo(player.getX(), player.getY(), player.getZ());
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
            } else if (chance <= 0.4D && nullEntity.getNullAxeEntity() != null) {
                nullEntity.getNullAxeEntity().moveTo(d0, d1, d2);
            } else if (chance <= 0.6D && nullEntity.getNullPickaxeEntity() != null) {
                nullEntity.getNullPickaxeEntity().moveTo(d0, d1, d2);
            } else if (chance <= 0.8D && nullEntity.getNullShovelEntity() != null) {
                nullEntity.getNullShovelEntity().moveTo(d0, d1, d2);
            } else if (nullEntity.getNullHoeEntity() != null) {
                nullEntity.getNullHoeEntity().moveTo(d0, d1, d2);
            }
        }
    }
}
