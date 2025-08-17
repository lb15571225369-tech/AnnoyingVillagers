package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.capabilities.SnakeBladeCapability;
import com.pla.annoyingvillagers.entity.SnakeBladeEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModCapabilities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.JointTransform;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import net.minecraft.client.player.LocalPlayer;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;


import java.util.UUID;

public class SnakeBladeHit {
    private static boolean isCharged(Player player){
        return player.getAttackStrengthScale(0.5F) > 0.9F;
    }

    public static boolean process(ItemStack stack, LivingEntity playerIn) {
        if(stack.is(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get()) && (!(playerIn instanceof Player) || isCharged((Player)playerIn))) {
            Level worldIn = playerIn.level();
            Entity closestValid = null;
            Vec3 playerEyes = playerIn.getEyePosition(1.0F);
            HitResult hitresult = worldIn.clip(new ClipContext(playerEyes, playerEyes.add(playerIn.getLookAngle().scale(16.0D)), ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, playerIn));
            if (hitresult instanceof EntityHitResult) {
                Entity entity = ((EntityHitResult) hitresult).getEntity();
                if (!entity.equals(playerIn) && !playerIn.isAlliedTo(entity) && !entity.isAlliedTo(playerIn) && entity instanceof Mob && playerIn.hasLineOfSight(entity)) {
                    closestValid = entity;
                }
            } else {
                for (Entity entity : worldIn.getEntitiesOfClass(LivingEntity.class, playerIn.getBoundingBox().inflate(16.0D))) {
                    if (!entity.equals(playerIn) && !playerIn.isAlliedTo(entity) && !entity.isAlliedTo(playerIn) && entity instanceof Mob && playerIn.hasLineOfSight(entity)) {
                        if (closestValid == null || playerIn.distanceTo(entity) < playerIn.distanceTo(closestValid)) {
                            closestValid = entity;
                        }
                    }
                }
            }
            return launchSnakeBladeAt(playerIn, closestValid, stack);
        }
        return false;
    }

    public static boolean launchSnakeBladeAt(LivingEntity playerIn, Entity closestValid, ItemStack stack) {
        Level worldIn = playerIn.level();
        SnakeBladeCapability.ISnakeBladeCapability tentacleCapability = AnnoyingVillagersModCapabilities.getCapability(playerIn, AnnoyingVillagersModCapabilities.SNAKE_BLADE_CAPABILITY);
        if (tentacleCapability != null) {
            if (canLaunchTentacles(worldIn, playerIn)) {
                retractFarFragments(worldIn, playerIn);
                if (!worldIn.isClientSide) {
                    if (closestValid != null) {
                        SnakeBladeEntity segment = AnnoyingVillagersModEntities.SNAKE_BLADE.get().create(worldIn);

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
                }
            }
        }
        return false;
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
        SnakeBladeCapability.ISnakeBladeCapability TentacleCapability = AnnoyingVillagersModCapabilities.getCapability(livingEntity, AnnoyingVillagersModCapabilities.SNAKE_BLADE_CAPABILITY);
        if (TentacleCapability != null) {
            UUID uuid = TentacleCapability.getLastSnakeBladeUUID();
            int id = TentacleCapability.getLastSnakeBladeID();
            if (!livingEntity.level().isClientSide) {
                if (uuid != null) {
                    Entity e = livingEntity.level().getEntity(id);
                    return e instanceof SnakeBladeEntity ? (SnakeBladeEntity) e : null;
                }
            } else {
                if (id != -1) {
                    Entity e = livingEntity.level().getEntity(id);
                    return e instanceof SnakeBladeEntity ? (SnakeBladeEntity) e : null;
                }
            }
            return null;
        }
        return null;
    }

    public static Vec3 getJointWithTranslation(Entity ent, Vec3f translation, Joint joint) {
        LivingEntityPatch entitypatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (entitypatch != null) {
            float interpolation = 0.0F;
            OpenMatrix4f transformMatrix;
            transformMatrix = entitypatch.getArmature().getBindedTransformFor(entitypatch.getAnimator().getPose(interpolation), joint);
            transformMatrix.translate(translation);
            OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians((double) (((LivingEntity) entitypatch.getOriginal()).yBodyRotO + 180.0F))), new Vec3f(0.0F, 1.0F, 0.0F)), transformMatrix, transformMatrix);
            return new Vec3(
                    (double) transformMatrix.m30 + (entitypatch.getOriginal()).getX(),
                    (double) transformMatrix.m31 + ((entitypatch.getOriginal()).getY() + (ent.getBbHeight() / 1.8) - 1),
                    (double) transformMatrix.m32 + (entitypatch.getOriginal()).getZ()
            );
        }
        return null;
    }
}
