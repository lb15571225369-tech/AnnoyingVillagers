package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.EnderAegisItem;
import com.pla.annoyingvillagers.item.ObsidianSledgehammerItem;
import com.pla.annoyingvillagers.network.ClientboundGlaiveExplosionFx;
import com.pla.annoyingvillagers.network.ClientboundMuteExplosionAtPos;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import com.pla.annoyingvillagers.util.SnakeBladeHit;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import reascer.wom.gameasset.animations.weapons.AnimsAgony;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.BiFunction;

import static com.pla.annoyingvillagers.combatbehaviour.CombatCommon.getIntegerIntegerBiFunction;

public class HerobrineCommon {
    public static boolean canJump(MobPatch<?> mobpatch) {
        return mobpatch.getOriginal().onGround() && !mobpatch.getOriginal().isPassenger();
    }

    public static boolean canPerformHealing(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            return !herobrineMob.isSacrificing() && !herobrineMob.isHealing() && herobrineMob.getHealingCooldown() == 0;
        }
        return false;
    }

    public static boolean canChangeToSecondForm(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            ItemStack item = herobrineMob.getMainHandItem();
            if (herobrineMob instanceof SwordsmanHerobrineEntity
                    && item.getTag() != null && item.getTag().contains("SnakeAnimation")) {
                return false;
            }
            return herobrineMob.getState() == 0;
        }
        return false;
    }

    public static boolean canPlaySecondFormAnimation(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            ItemStack item = herobrineMob.getMainHandItem();
            if (herobrineMob instanceof SwordsmanHerobrineEntity
                    && item.getTag() != null && item.getTag().contains("SnakeAnimation")) {
                return false;
            }
            return herobrineMob.getState() != 0;
        }
        return false;
    }

    public static boolean canPerformGuarding(MobPatch<?> mobpatch) {
        return true;
    }

    public static void performHealingAnimation(MobPatch<?> mobpatch) {
        LivingEntity entity = mobpatch.getOriginal();
        if (!(entity.level() instanceof ServerLevel serverLevel)) return;
        if (!(entity instanceof HerobrineMob herobrineMob)) return;

        herobrineMob.setHealing(true);
        List<Entity> bound = getEntities(herobrineMob);

        Random random = new Random();
        Entity chosen;
        if (bound.isEmpty()) {
            double radius = 3.0D + random.nextDouble() * 3.0D;
            double angle  = random.nextDouble() * (Math.PI * 2.0D);

            double dx = Math.cos(angle) * radius;
            double dz = Math.sin(angle) * radius;

            Vec3 rawPos = new Vec3(entity.getX() + dx, entity.getY(), entity.getZ() + dz);
            BlockPos xz = BlockPos.containing(rawPos.x, 0.0D, rawPos.z);
            int y = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, xz).getY();
            Vec3 spawnPos = new Vec3(rawPos.x, y, rawPos.z);

            Entity spawned;
            if (random.nextBoolean()) {
                LowHerobrineCloneEntity low = new LowHerobrineCloneEntity(
                        AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE.get(), serverLevel
                );
                low.moveTo(spawnPos.x, spawnPos.y, spawnPos.z, herobrineMob.getYRot(), herobrineMob.getXRot());
                low.setPossessedByEntity(herobrineMob);
                low.setRenderPortal(false);
                low.setPossessedByUuid(herobrineMob.getUUID());
                low.setNoAi(true);
                TeamUtil.addOrJoinTeam(low, "herobrine");
                serverLevel.addFreshEntity(low);
                spawned = low;
            } else {
                LowShadowHerobrineCloneEntity low = new LowShadowHerobrineCloneEntity(
                        AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get(), serverLevel
                );
                low.moveTo(spawnPos.x, spawnPos.y, spawnPos.z, herobrineMob.getYRot(), herobrineMob.getXRot());
                low.setPossessedByEntity(herobrineMob);
                low.setRenderPortal(false);
                low.setPossessedByUuid(herobrineMob.getUUID());
                low.setNoAi(true);
                TeamUtil.addOrJoinTeam(low, "herobrine");
                serverLevel.addFreshEntity(low);
                spawned = low;
            }

            herobrineMob.boundPossessed(spawned);
            chosen = spawned;
        } else {
            chosen = bound.get(random.nextInt(bound.size()));
        }

        if (chosen instanceof LowShadowHerobrineCloneEntity lowShadow) {
            if (lowShadow.isHealing()) return;
            lowShadow.setPossessedByEntity(herobrineMob);
            lowShadow.setPossessedByUuid(herobrineMob.getUUID());
            lowShadow.setSacrificing(false);
            lowShadow.setHealing(true);
            lowShadow.setNoAi(true);
            return;
        }

        if (chosen instanceof LowHerobrineCloneEntity low) {
            if (low.isHealing()) return;
            low.setPossessedByEntity(herobrineMob);
            low.setPossessedByUuid(herobrineMob.getUUID());
            low.setHealing(true);
            low.setNoAi(true);
        }

        chosen.playSound(AnnoyingVillagersModSounds.HEROBRINE_UNDERSTOOD.get(), 1.0F, 1.0F);
    }

    public static @NotNull List<Entity> getEntities(HerobrineMob herobrineMob) {
        List<Entity> bound = new ArrayList<>(4);
        Entity c1 = herobrineMob.getFirstPossessedHerobrine();
        Entity c2 = herobrineMob.getSecondPossessedHerobrine();
        Entity c3 = herobrineMob.getThirdPossessedHerobrine();
        Entity c4 = herobrineMob.getFourthPossessedHerobrine();

        if (c1 != null && c1.isAlive()) bound.add(c1);
        if (c2 != null && c2.isAlive()) bound.add(c2);
        if (c3 != null && c3.isAlive()) bound.add(c3);
        if (c4 != null && c4.isAlive()) bound.add(c4);
        return bound;
    }

    public static void changeToSecondForm(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            herobrineMob.setState(1);
            herobrineMob.setSecondFormHitLeft(new Random().nextInt(2, 3));
            if (herobrineMob instanceof AegisHerobrineEntity || herobrineMob instanceof SwordsmanHerobrineEntity
                    || herobrineMob instanceof SledgehammerHerobrineEntity || herobrineMob instanceof ReaperHerobrineEntity
                    || herobrineMob instanceof GlaiveHerobrineEntity) {
                herobrineMob.playSound(AnnoyingVillagersModSounds.SECOND_FORM_RELEASE.get(), 1.0F, 1.0F);
            }
        }
    }

    public static void playSecondFormAnimation(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            ItemStack item = herobrineMob.getMainHandItem();
            herobrineMob.setSecondFormHitLeft(herobrineMob.getSecondFormHitLeft() - 1);
            if (herobrineMob instanceof AegisHerobrineEntity && herobrineMob.level() instanceof ServerLevel serverLevel) {
                new DelayedTask(10) {
                    @Override
                    public void run() {
                        EnderAegisItem.shieldShoot(serverLevel, herobrineMob);
                    }
                };
            } else if (herobrineMob instanceof SwordsmanHerobrineEntity && herobrineMob.level() instanceof ServerLevel) {
                if (herobrineMob.getTarget() != null) {
                    herobrineMob.getLookControl().setLookAt(herobrineMob.getTarget() , 30.0F, 30.0F);
                }
                if (SnakeBladeHit.process(item, herobrineMob)) {
                    item.getOrCreateTag().putBoolean("SnakeAnimation", true);
                }
            } else if (herobrineMob instanceof GlaiveHerobrineEntity && herobrineMob.level() instanceof ServerLevel) {
                herobrineMob.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 40, 3));
                new DelayedTask(10) {
                    @Override
                    public void run() {
                        Vec3 tipPos = EpicfightUtil.getJointWithTranslation(
                                herobrineMob,
                                new Vec3f(0.0F, 0.0F, 0.0F),
                                Armatures.BIPED.get().toolR,
                                4.3F,
                                2.3F
                        );
                        if (tipPos != null) {
                            BlockPos mutePos = BlockPos.containing(tipPos);
                            AnnoyingVillagers.PACKET_HANDLER.send(
                                    PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> herobrineMob),
                                    new ClientboundMuteExplosionAtPos(mutePos, 4)
                            );
                            herobrineMob.level().explode(herobrineMob, tipPos.x, tipPos.y, tipPos.z,
                                    2.0F, true, Level.ExplosionInteraction.TNT);
                            Vec3 glaivePos = EpicfightUtil.getJointWithTranslation(herobrineMob, new Vec3f(0, 0, 0),
                                    Armatures.BIPED.get().toolR, 1.3F, 2.3F);
                            Vec3 explosionPos = EpicfightUtil.getJointWithTranslation(herobrineMob, new Vec3f(0, 0, 0),
                                    Armatures.BIPED.get().toolR, 10.3F, 2.3F);
                            AnnoyingVillagers.PACKET_HANDLER.send(
                                    PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> herobrineMob),
                                    new ClientboundGlaiveExplosionFx(glaivePos, explosionPos)
                            );
                            if (explosionPos != null) {
                                herobrineMob.level().playSound(null, new BlockPos((int) explosionPos.x, (int) explosionPos.y, (int) explosionPos.z), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_shot"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                            }
                        }
                    }
                };
            } else if (herobrineMob instanceof SledgehammerHerobrineEntity && herobrineMob.level() instanceof ServerLevel) {
                ObsidianSledgehammerItem.triggerCircleWhenGroundHits(mobpatch, true);
            }
        }
    }

    public static void playSecondFormSpecialAnimation(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            herobrineMob.setSecondFormHitLeft(herobrineMob.getSecondFormHitLeft() - 1);
            if (herobrineMob instanceof GlaiveHerobrineEntity && herobrineMob.level() instanceof ServerLevel) {
                herobrineMob.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 40, 3));
                new DelayedTask(14) {
                    @Override
                    public void run() {
                        Vec3 tipPos = EpicfightUtil.getJointWithTranslation(
                                herobrineMob,
                                new Vec3f(0.0F, 0.0F, 0.0F),
                                Armatures.BIPED.get().toolR,
                                4.3F,
                                2.3F
                        );
                        if (tipPos != null) {
                            BlockPos mutePos = BlockPos.containing(tipPos);
                            AnnoyingVillagers.PACKET_HANDLER.send(
                                    PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> herobrineMob),
                                    new ClientboundMuteExplosionAtPos(mutePos, 4)
                            );
                            herobrineMob.level().explode(herobrineMob, tipPos.x, tipPos.y, tipPos.z,
                                    2.0F, true, Level.ExplosionInteraction.TNT);
                            Vec3 glaivePos = EpicfightUtil.getJointWithTranslation(herobrineMob, new Vec3f(0, 0, 0),
                                    Armatures.BIPED.get().toolR, 1.3F, 2.3F);
                            Vec3 explosionPos = EpicfightUtil.getJointWithTranslation(herobrineMob, new Vec3f(0, 0, 0),
                                    Armatures.BIPED.get().toolR, 10.3F, 2.3F);
                            AnnoyingVillagers.PACKET_HANDLER.send(
                                    PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> herobrineMob),
                                    new ClientboundGlaiveExplosionFx(glaivePos, explosionPos)
                            );
                            if (explosionPos != null) {
                                herobrineMob.level().playSound(null, new BlockPos((int) explosionPos.x, (int) explosionPos.y, (int) explosionPos.z), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_shot"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                            }
                        }
                    }
                };
            } else if (herobrineMob instanceof SledgehammerHerobrineEntity && herobrineMob.level() instanceof ServerLevel) {
                ObsidianSledgehammerItem.triggerCircleWhenGroundHits(mobpatch, false);
            }
        }
    }

    public static void playSecondFormGuardAnimation(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            ItemStack item = herobrineMob.getMainHandItem();
            herobrineMob.setSecondFormHitLeft(herobrineMob.getSecondFormHitLeft() - 1);
            if (herobrineMob instanceof SwordsmanHerobrineEntity && herobrineMob.level() instanceof ServerLevel) {
                if (herobrineMob.getTarget() != null) {
                    herobrineMob.getLookControl().setLookAt(herobrineMob.getTarget() , 30.0F, 30.0F);
                }
                if (SnakeBladeHit.processGuard(item, herobrineMob)) {
                    item.getOrCreateTag().putBoolean("SnakeAnimation", true);
                }
            }
        }
    }

    public static void jump(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (entity instanceof HerobrineMob herobrineMob) {
            herobrineMob.jump();
        }
    }

    public static void giveSlowFalling(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (entity instanceof HerobrineMob herobrineMob) {
            herobrineMob.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60, 1));
        }
    }

    public static void performEscapeRunAwayWithLowClone(MobPatch<?> mobpatch) {
        final Mob mob = mobpatch.getOriginal();
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;
        CombatCommon.performEscapeRunAway(mobpatch);
        new DelayedTask(1) {
            @Override public void run() {
                mobpatch.playAnimationSynchronized(AVAnimations.CASTING_ONE_HAND_BUFF, 0.0F);
                if (!mob.isAlive()) return;

                mobpatch.playAnimationSynchronized(AVAnimations.CASTING_ONE_HAND_INWARD, 0.0F);

                final LivingEntity target = mob.getTarget();
                final Direction dir = (target != null)
                        ? Direction.getNearest(target.getX() - mob.getX(), 0.0D, target.getZ() - mob.getZ())
                        : mob.getDirection();

                final Random random = new Random();
                final int dist = 1 + random.nextInt(3);

                final int rot = random.nextInt(4);
                final BiFunction<Integer, Integer, int[]> toWorld = getIntegerIntegerBiFunction(mob, rot);
                final int lateral = random.nextInt(3) - 1;
                final int[] dxz = toWorld.apply(lateral, 0);

                BlockPos baseXZ = mob.blockPosition().relative(dir, dist).offset(dxz[0], 0, dxz[1]);
                int surfaceY = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, baseXZ).getY();
                BlockPos spawnPos = new BlockPos(baseXZ.getX(), surfaceY, baseXZ.getZ());

                LowShadowHerobrineCloneEntity clone =
                        new LowShadowHerobrineCloneEntity(AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get(), serverLevel);

                float yaw = dir.toYRot();
                clone.moveTo(spawnPos.getX() + 0.5D, spawnPos.getY(), spawnPos.getZ() + 0.5D, yaw, 0.0F);
                clone.setRenderPortal(false);

                clone.setForEscaping(true);
                clone.setNoAi(true);
                if (mob instanceof HerobrineMob herobrineMob) {
                    clone.setPossessedByEntity(herobrineMob);
                    clone.setPossessedByUuid(herobrineMob.getUUID());
                }

                serverLevel.addFreshEntity(clone);
            }
        };
    }

    public static void performAgonySpecialAttack(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (entity instanceof HerobrineMob) {
            new DelayedTask(10) {
                @Override
                public void run() {
                    mobpatch.playAnimationSynchronized(AnimsAgony.AGONY_RIPPING_FANGS, 0.0F);
                }
            };
        }
    }
}
