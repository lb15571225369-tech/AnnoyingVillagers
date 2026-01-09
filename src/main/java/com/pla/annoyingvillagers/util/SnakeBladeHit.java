package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.capabilities.SnakeBladeCapability;
import com.pla.annoyingvillagers.entity.SnakeBladeEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModCapabilities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;


import java.util.Random;
import java.util.UUID;

public class SnakeBladeHit {
    public static boolean process(ItemStack stack, LivingEntity playerIn) {
        if(stack.is(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get())) {
            Level worldIn = playerIn.level();
            Entity closestValid = null;
            Vec3 playerEyes = playerIn.getEyePosition(1.0F);
            worldIn.clip(new ClipContext(playerEyes, playerEyes.add(playerIn.getLookAngle().scale(16.0D)), ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, playerIn));
            for (Entity entity : worldIn.getEntitiesOfClass(LivingEntity.class, playerIn.getBoundingBox().inflate(16.0D))) {
                if (!entity.equals(playerIn) && !playerIn.isAlliedTo(entity) && !entity.isAlliedTo(playerIn) && (entity instanceof Mob || entity instanceof Player) && playerIn.hasLineOfSight(entity)) {
                    if (closestValid == null || playerIn.distanceTo(entity) < playerIn.distanceTo(closestValid)) {
                        closestValid = entity;
                    }
                }
            }
            return launchSnakeBladeAt(playerIn, closestValid, stack);
        }
        return false;
    }

    public static boolean processGuard(ItemStack stack, LivingEntity entityToGuard) {
        if (!stack.is(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get())) return false;

        Level worldIn = entityToGuard.level();
        SnakeBladeCapability.ISnakeBladeCapability snakeBladeCapability =
                AnnoyingVillagersModCapabilities.getCapability(entityToGuard, AnnoyingVillagersModCapabilities.SNAKE_BLADE_CAPABILITY);

        if (snakeBladeCapability != null) {
            if (canLaunchTentacles(worldIn, entityToGuard)) {
                retractFarFragments(worldIn, entityToGuard);
                if (!worldIn.isClientSide) {
                    return launchSnakeBladeAt(entityToGuard, stack);
                }
            }
        }
        return false;
    }

    public static boolean launchSnakeBladeAt(LivingEntity playerIn, Entity closestValid, ItemStack stack) {
        Level worldIn = playerIn.level();
        SnakeBladeCapability.ISnakeBladeCapability snakeBladeCapability = AnnoyingVillagersModCapabilities.getCapability(playerIn, AnnoyingVillagersModCapabilities.SNAKE_BLADE_CAPABILITY);
        if (snakeBladeCapability != null) {
            if (canLaunchTentacles(worldIn, playerIn)) {
                retractFarFragments(worldIn, playerIn);
                if (!worldIn.isClientSide) {
                    if (closestValid != null) {
                        SnakeBladeEntity segment = AnnoyingVillagersModEntities.SNAKE_BLADE.get().create(worldIn);
                        if (segment != null) {
                            if (stack.hasFoil()) {
                                segment.setEnchanted(true);
                            }
                            segment.copyPosition(playerIn);
                            worldIn.addFreshEntity(segment);
                            segment.setCreatorEntityUUID(playerIn.getUUID());
                            segment.setFromEntityID(playerIn.getId());
                            segment.setToEntityID(closestValid.getId());
                            segment.copyPosition(playerIn);
                            segment.setProgress(0.0F);
                            setLastFragment(playerIn, segment);
                            return true;
                        }
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public static boolean launchSnakeBladeAt(LivingEntity playerIn, ItemStack stack) {
        Level worldIn = playerIn.level();
        SnakeBladeEntity segment = AnnoyingVillagersModEntities.SNAKE_BLADE.get().create(worldIn);
        if (segment == null) return false;

        if (stack.hasFoil()) {
            segment.setEnchanted(true);
        }

        segment.setCreatorEntityUUID(playerIn.getUUID());
        segment.setFromEntityID(playerIn.getId());
        segment.setToEntityID(-1);
        segment.setProgress(0.0F);
        segment.setGuardDirection("forward_left");

        Vec3 spawn = guardTargetFor(playerIn, "forward_left");
        segment.setPos(spawn.x, spawn.y, spawn.z);

        worldIn.addFreshEntity(segment);
        setLastFragment(playerIn, segment);
        return true;
    }

    public static final class LocalSpace {
        private static final Vec3 UP = new Vec3(0, 1, 0);

        public static Vec3 forward(LivingEntity e) {
            float yawRad = e.yBodyRot * Mth.DEG_TO_RAD;
            return new Vec3(-Mth.sin(yawRad), 0.0D, Mth.cos(yawRad)).normalize();
        }

        public static Vec3 right(LivingEntity e) {
            Vec3 f = forward(e);
            return UP.cross(f).normalize();
        }

        public static Vec3 left(LivingEntity e) {
            return right(e).scale(-1.0D);
        }

        public static Vec3 back(LivingEntity e) {
            return forward(e).scale(-1.0D);
        }

        public static Vec3 localOffsetPos(LivingEntity e, double leftU, double upU, double forwardU) {
            Vec3 base = e.position();
            Vec3 off = left(e).scale(leftU)
                    .add(UP.scale(upU))
                    .add(forward(e).scale(forwardU));
            return base.add(off);
        }
    }

    public static Vec3 guardTargetFor(LivingEntity ent, String direction) {
        Random random = new Random();
        if ("forward_left".equalsIgnoreCase(direction)) {
            return LocalSpace.localOffsetPos(ent, 1, 0, -1);
        } else if ("forward_right".equalsIgnoreCase(direction)) {
            return LocalSpace.localOffsetPos(ent, 2, 1, 1);
        } else if ("backward_right".equalsIgnoreCase(direction)) {
            return LocalSpace.localOffsetPos(ent, -1, 0, 2);
        } else {
            return LocalSpace.localOffsetPos(ent, -1, 2, -1);
        }
    }

    public static void setLastFragment(LivingEntity entity, SnakeBladeEntity tendon) {
        SnakeBladeCapability.ISnakeBladeCapability TentacleCapability = AnnoyingVillagersModCapabilities.getCapability(entity, AnnoyingVillagersModCapabilities.SNAKE_BLADE_CAPABILITY);
        if (TentacleCapability != null) {
            TentacleCapability.setHasSnakeBlade(tendon != null);
        }
    }

    public static void retractFarFragments(Level level, LivingEntity livingEntity) {
        SnakeBladeEntity last = getLastFragment(livingEntity);
        if (last != null) {
            last.remove(Entity.RemovalReason.DISCARDED);
            setLastFragment(livingEntity, null);
        }
    }

    public static boolean canLaunchTentacles(Level level, LivingEntity livingEntity) {
        SnakeBladeEntity last = getLastFragment(livingEntity);
        if (last != null) {
            return last.isRemoved();
        }
        return true;
    }


    public static SnakeBladeEntity getLastFragment(LivingEntity livingEntity) {
        SnakeBladeCapability.ISnakeBladeCapability snakeBladeCapability = AnnoyingVillagersModCapabilities.getCapability(livingEntity, AnnoyingVillagersModCapabilities.SNAKE_BLADE_CAPABILITY);
        if (snakeBladeCapability != null) {
            UUID uuid = snakeBladeCapability.getLastSnakeBladeUUID();
            int id = snakeBladeCapability.getLastSnakeBladeID();
            if (!livingEntity.level().isClientSide) {
                if (uuid != null) {
                    Entity entity = livingEntity.level().getEntity(id);
                    if (entity == null || !entity.isAlive() || !(entity instanceof SnakeBladeEntity)) {
                        return null;
                    }
                    return (SnakeBladeEntity) entity;
                }
            } else {
                if (id != -1) {
                    Entity entity = livingEntity.level().getEntity(id);
                    if (entity == null || !entity.isAlive() || !(entity instanceof SnakeBladeEntity)) {
                        return null;
                    }
                    return (SnakeBladeEntity) entity;
                }
            }
            return null;
        }
        return null;
    }

    public static Vec3 getToolTipPos(Entity ent, float partialTicks, float handToTip) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (patch == null) return null;

        OpenMatrix4f joint = patch.getArmature()
                .getBoundTransformFor(patch.getAnimator().getPose(partialTicks), Armatures.BIPED.get().toolR);

        OpenMatrix4f localOffset = new OpenMatrix4f().translate(new Vec3f(0.0F, 0.0F, -handToTip));
        OpenMatrix4f.mul(joint, localOffset, joint);

        float yawRad = (float) -Math.toRadians(((LivingEntity) ent).yBodyRotO + 180.0F);
        OpenMatrix4f worldYaw = new OpenMatrix4f().rotate(yawRad, new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f.mul(worldYaw, joint, joint);

        return new Vec3(
                joint.m30 + ent.getX(),
                joint.m31 + (ent.getY() + (ent.getBbHeight() / 1.8F) - 1.0F),
                joint.m32 + ent.getZ()
        );
    }
}
