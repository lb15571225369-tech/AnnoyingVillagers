package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.entity.EnchantedEnderPearlEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;

public class ThrowingPearlKeyPressedProcedure {
    public static Vec3 getJointWithTranslation(Entity entity, Vec3f translation, Joint joint) {
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (livingEntityPatch == null) return null;

        float interpolation = 0.0F;
        OpenMatrix4f m = livingEntityPatch.getArmature()
                .getBoundTransformFor(livingEntityPatch.getAnimator().getPose(interpolation), joint);

        if (translation != null) {
            OpenMatrix4f tLocal = new OpenMatrix4f().translate(translation);
            OpenMatrix4f.mul(m, tLocal, m);
        }

        float yawRad = (float) -Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F);
        OpenMatrix4f worldYaw = new OpenMatrix4f().rotate(yawRad, new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f.mul(worldYaw, m, m);

        LivingEntity base = livingEntityPatch.getOriginal();
        return new Vec3(
                m.m30 + base.getX(),
                m.m31 + (base.getY() + (entity.getBbHeight() / 1.8) - 1.0),
                m.m32 + base.getZ()
        );
    }

    public static void execute(LevelAccessor levelaccessor, final Entity entity) {
        if (entity != null) {
            if (!(entity.level() instanceof ServerLevel)) return;

            LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
            if (livingEntityPatch == null) return;
            AssetAccessor<? extends DynamicAnimation> dynamicAnimation = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getAnimation();
            if (dynamicAnimation.get() instanceof LongHitAnimation) {
                return;
            }

            if (entity instanceof Player player) {
                boolean used = player.getInventory().items.stream()
                        .filter(s -> !s.isEmpty() && s.is(AnnoyingVillagersModItems.ENCHANTED_ENDER_PEARL.get()))
                        .findFirst()
                        .map(stack -> {
                            if (!entity.getPersistentData().getBoolean("ender_pearl_used")) {
                                entity.getPersistentData().putBoolean("ender_pearl_used", true);
                                livingEntityPatch.playAnimationSynchronized(AVAnimations.CASTING_ONE_HAND_TOP, 0.0F);
                                Level level = entity.level();
                                var projectile = new EnchantedEnderPearlEntity(
                                        AnnoyingVillagersModEntities.ENCHANTED_ENDER_PEARL_PROJECTILE.get(), level);
                                projectile.setOwner(entity);
                                Vec3 handPos = getJointWithTranslation(
                                        entity,
                                        new Vec3f(0.0F, 0.0F, 0.0F),
                                        Armatures.BIPED.get().toolL
                                );

                                if (handPos != null) {
                                    projectile.setBaseDamage(0.0D);
                                    projectile.setKnockback(0);
                                    projectile.setSilent(true);
                                    projectile.setPos(handPos.x, handPos.y, handPos.z);
                                    projectile.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 1.5F, 0.0F);
                                    level.addFreshEntity(projectile);
                                    entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (entity.level().getRandom().nextFloat() * 0.4F + 0.8F));
                                    stack.hurtAndBreak(1, player, p -> {
                                    });

                                    new DelayedTask(20) {
                                        @Override
                                        public void run() {
                                            entity.getPersistentData().putBoolean("ender_pearl_used", false);
                                        }
                                    };
                                    return true;
                                }
                                return false;
                            }
                            return false;
                        }).orElse(false);

                if (used) {
                    return;
                }
            }

            Level level;
            Projectile projectile;

            if (entity instanceof Player player) {

                if (player.getInventory().contains(new ItemStack(Items.ENDER_PEARL)) && !entity.getPersistentData().getBoolean("ender_pearl_used")) {
                    entity.getPersistentData().putBoolean("ender_pearl_used", true);
                    livingEntityPatch.playAnimationSynchronized(AVAnimations.CASTING_ONE_HAND_TOP, 0.0F);
                    level = entity.level();
                    projectile = new ThrownEnderpearl(EntityType.ENDER_PEARL, level);
                    projectile.setOwner(entity);
                    Vec3 handPos = getJointWithTranslation(
                            entity,
                            new Vec3f(0.0F, 0.0F, 0.0F),
                            Armatures.BIPED.get().toolL
                    );

                    if (handPos != null) {
                        projectile.setPos(handPos.x, handPos.y, handPos.z);
                        projectile.setPos(entity.getX(), entity.getEyeY() - 0.1D, entity.getZ());
                        projectile.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 1.5F, 0.0F);
                        level.addFreshEntity(projectile);

                        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (entity.level().getRandom().nextFloat() * 0.4F + 0.8F));

                        new DelayedTask(15) {
                            @Override
                            public void run() {
                                entity.getPersistentData().putBoolean("ender_pearl_used", false);
                            }
                        };
                        Player player2 = (Player) entity;
                        ItemStack itemStack = new ItemStack(Items.ENDER_PEARL);

                        player2.getInventory().clearOrCountMatchingItems((stack) -> {
                            return itemStack.getItem() == stack.getItem();
                        }, 1, player2.inventoryMenu.getCraftSlots());
                    }
                }
            }
        }
    }
}

