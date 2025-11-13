package com.pla.annoyingvillagers.procedures;

import java.util.*;
import java.util.stream.Collectors;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.HitAnimation;
import yesman.epicfight.api.animation.types.KnockdownAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class BlueDemonOnEntityUpdateProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity) {
        if (entity != null) {
            if (!levelaccessor.getEntitiesOfClass(ThrownTrident.class, AABB.ofSize(new Vec3(d0, d1, d2), 40.0D, 40.0D, 40.0D), (throwntrident) -> {
                return true;
            }).isEmpty() && Math.random() <= 0.02D) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute("execute at @e[type=minecraft:trident] run particle annoyingvillagers:electric_spark ^ ^ ^0.1 0.2 0.2 0.1 0 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                        
                    }
                }

                if (levelaccessor instanceof Level) {
                    Level level = (Level)levelaccessor;

                    if (!level.isClientSide()) {
                        level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "electify"))), SoundSource.BLOCKS, (float)Mth.nextDouble(RandomSource.create(), 0.0D, 0.1D), (float)Mth.nextDouble(RandomSource.create(), 0.7D, 1.05D));
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "electify"))), SoundSource.BLOCKS, (float)Mth.nextDouble(RandomSource.create(), 0.0D, 0.1D), (float)Mth.nextDouble(RandomSource.create(), 0.7D, 1.05D), false);
                    }
                }
            }

            Vec3 vec3 = new Vec3(d0, d1, d2);
            List<Entity> list = (List)levelaccessor.getEntitiesOfClass(Entity.class, (new AABB(vec3, vec3)).inflate(15.0D), (entity1) -> {
                return true;
            }).stream().sorted(Comparator.comparingDouble((entity1) -> {
                return entity1.distanceToSqr(vec3);
            })).collect(Collectors.toList());
            Iterator iterator = list.iterator();

            while(iterator.hasNext()) {
                Entity entity1 = (Entity)iterator.next();
                LivingEntity livingentity;

                if (entity instanceof Mob) {
                    Mob mob = (Mob)entity;

                    livingentity = mob.getTarget();
                } else {
                    livingentity = null;
                }

                if (entity1 == livingentity && Math.random() <= 0.12D) {
                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        try {
                            entity.getServer().getCommands().getDispatcher().execute("execute at @s run particle annoyingvillagers:electric_spark ^ ^ ^ 0.3 1.2 0.3 0 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                            
                        }
                    }

                    if (Math.random() <= 0.8D && levelaccessor instanceof Level) {
                        Level level1 = (Level)levelaccessor;

                        if (!level1.isClientSide()) {
                            level1.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "electify"))), SoundSource.BLOCKS, (float)Mth.nextDouble(RandomSource.create(), 0.0D, 0.15D), (float)Mth.nextDouble(RandomSource.create(), 0.7D, 1.05D));
                        } else {
                            level1.playLocalSound(d0, d1, d2, (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "electify"))), SoundSource.BLOCKS, (float)Mth.nextDouble(RandomSource.create(), 0.0D, 0.15D), (float)Mth.nextDouble(RandomSource.create(), 0.7D, 1.05D), false);
                        }
                    }
                }
            }

            if (entity.isPassenger()) {
                entity.stopRiding();
            }

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute("fill ~-1 ~ ~ ~ ~ ~ minecraft:air replace", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                    
                }
            }

            LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

            if (livingentitypatch != null) {
                final AssetAccessor<? extends DynamicAnimation> dynamicanimation = livingentitypatch.getAnimator().getPlayerFor(null).getAnimation();

                if (!(dynamicanimation instanceof AttackAnimation) && !(dynamicanimation instanceof LongHitAnimation) && !(dynamicanimation instanceof HitAnimation)) {
                    new DelayedTask(10) {
                        @Override
                        public void run() {
                            if (dynamicanimation instanceof KnockdownAnimation) {
                                if (!entity.level().isClientSide() && entity.getServer() != null) {
                                    try {
                                        entity.getServer().getCommands().getDispatcher().execute(
                                                "indestructible @s play \"epicfight:biped/skill/knockdown_wakeup_left\" 0 1",
                                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                                        );
                                    } catch (CommandSyntaxException e) {
                                        
                                    }
                                }
                            }
                        }
                    };
                } else {
                    entity.clearFire();
                }
            }

        }
    }
}
