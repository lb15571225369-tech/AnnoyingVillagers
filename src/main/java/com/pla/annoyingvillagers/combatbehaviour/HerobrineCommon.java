package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.EnderAegisItem;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.SnakeBladeHit;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            if (herobrineMob instanceof SwordsmanHerobrineEntity && herobrineMob.getState() > 0) {
                return false;
            }
            return true;
        }
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
            herobrineMob.setSecondFormHitLeft(new Random().nextInt(1, 3));
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
}
