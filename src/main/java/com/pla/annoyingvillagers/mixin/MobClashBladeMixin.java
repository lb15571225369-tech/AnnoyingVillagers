package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.animations.BowAttackAnimation;
import com.pla.annoyingvillagers.animations.KickAttackAnimation;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.clazz.PathfinderMobInventory;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.entity.AegisHerobrineEntity;
import com.pla.annoyingvillagers.entity.AngrySteveEntity;
import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import com.pla.efclash_blade.event.MobClashBladeEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import reascer.wom.gameasset.animations.weapons.AnimsAgony;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.Objects;
import java.util.Random;
import java.util.function.BiFunction;

@Mixin(value = MobClashBladeEvent.class, remap = false)
public class MobClashBladeMixin {
    @Inject(method = "customAdditionClashBladeLogic", at = @At("HEAD"), cancellable = true)
    private static void addMoreClashBladeCondition(LivingAttackEvent livingAttackEvent,
                                                   LivingEntityPatch<?> defenderLivingEntityPatch,
                                                   AssetAccessor<? extends DynamicAnimation> defenderDynamicAnimation,
                                                   EntityState defenderEntityState, Entity attacker, Entity defender,
                                                   CallbackInfoReturnable<Boolean> cir) {
        if (EpicfightUtil.isLongHitAnimation(defenderDynamicAnimation)) {
            cir.setReturnValue(false);
            return;
        }

        // Auto clash while playing animation
        if (defender instanceof AegisHerobrineEntity
                && defenderDynamicAnimation == AVAnimations.SHIELD_MAINHAND
                && defenderEntityState.getLevel() == 3) {
            cir.setReturnValue(true);
            return;
        }

        if (defender instanceof AegisHerobrineEntity
                && defenderDynamicAnimation == AVAnimations.ENDER_AEGIS_NAPOLEON_RELOAD_1) {
            cir.setReturnValue(true);
            return;
        }

        if (livingAttackEvent.getSource().getDirectEntity() instanceof Projectile projectile
                && defender.onGround()
                && !defender.isPassenger()
                && defender.level() instanceof ServerLevel) {
            // Projectile clashing
            if (defender instanceof PathfinderMobInventory pathfinderMobInventory
                    && pathfinderMobInventory.getBlockDamage() == null
                    && new Random().nextDouble() <= pathfinderMobInventory.getBlockProjectileChance()
                    && (pathfinderMobInventory.getItemInHand(InteractionHand.MAIN_HAND).getItem()
                    .equals(pathfinderMobInventory.getMainWeaponItem().getItem())
                    || pathfinderMobInventory.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof BowItem)
                    && !pathfinderMobInventory.isHealing()) {
                pathfinderMobInventory.setBlockDamage(projectile);
                CombatCommon.swapToBlock((MobPatch<?>) defenderLivingEntityPatch);
                cir.setReturnValue(true);
                return;
            }

            if (defender instanceof PlayerNpcEntity playerNpcEntity
                    && playerNpcEntity.getBlockDamage() == null
                    && new Random().nextDouble() <= playerNpcEntity.getBlockProjectileChance()
                    && (playerNpcEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem()
                    .equals(playerNpcEntity.getMainWeaponItem().getItem())
                    || playerNpcEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof BowItem)
                    && playerNpcEntity.isHealing()) {
                playerNpcEntity.setBlockDamage(projectile);
                CombatCommon.swapToBlock((MobPatch<?>) defenderLivingEntityPatch);
                cir.setReturnValue(true);
                return;
            }

            if (defender instanceof HerobrineMob) {
                if (!(projectile instanceof AbstractArrow)) {
                    cir.setReturnValue(true);
                    return;
                }
            }

        } else if (!(livingAttackEvent.getSource().getDirectEntity() instanceof Projectile)
                && defender.onGround()
                && !defender.isPassenger()
                && defender.level() instanceof ServerLevel) {
            // Non-projectile clashing
            ResourceLocation indirectEntity = BuiltInRegistries.ENTITY_TYPE.getKey(Objects.requireNonNull(livingAttackEvent.getSource().getDirectEntity()).getType());
            boolean isDamageFromGunKnight = indirectEntity.getNamespace().equals("torchesbecomesunlight")
                    && (indirectEntity.getPath().equals("gun_knight_patriot") || indirectEntity.getPath().equals("turret"));
            if (isDamageFromGunKnight || livingAttackEvent.getSource().is(DamageTypes.EXPLOSION)) {
                if (defender instanceof PathfinderMobInventory pathfinderMobInventory
                        && pathfinderMobInventory.getBlockDamage() == null
                        && new Random().nextDouble() <= pathfinderMobInventory.getBlockProjectileChance()
                        && (pathfinderMobInventory.getItemInHand(InteractionHand.MAIN_HAND).getItem()
                        .equals(pathfinderMobInventory.getMainWeaponItem().getItem())
                        || pathfinderMobInventory.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof BowItem)) {
                    pathfinderMobInventory.setBlockDamage(livingAttackEvent.getSource().getDirectEntity());
                    CombatCommon.swapToBlock((MobPatch<?>) defenderLivingEntityPatch);
                    cir.setReturnValue(true);
                    return;
                }

                if (defender instanceof PlayerNpcEntity playerNpcEntity
                        && playerNpcEntity.getBlockDamage() == null
                        && new Random().nextDouble() <= playerNpcEntity.getBlockProjectileChance()
                        && (playerNpcEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem()
                        .equals(playerNpcEntity.getMainWeaponItem().getItem())
                        || playerNpcEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof BowItem)) {
                    playerNpcEntity.setBlockDamage(livingAttackEvent.getSource().getDirectEntity());
                    CombatCommon.swapToBlock((MobPatch<?>) defenderLivingEntityPatch);
                    cir.setReturnValue(true);
                    return;
                }
            }
        }
    }

//    @Inject(method = "forceClashBlade", at = @At("HEAD"), cancellable = true)
//    private static void clashAgainstOpSkill(LivingAttackEvent livingAttackEvent,
//                                                   LivingEntityPatch<?> defenderLivingEntityPatch,
//                                                   AssetAccessor<? extends DynamicAnimation> defenderDynamicAnimation,
//                                                   EntityState defenderEntityState, Entity attacker, Entity defender,
//                                                   CallbackInfoReturnable<Boolean> cir) {
//        // Clash custom animation
//        if (EpicfightUtil.isLongHitAnimation(defenderDynamicAnimation)) {
//            cir.setReturnValue(false);
//            return;
//        }
//
//        if (defender instanceof HerobrineMob || defender instanceof AngrySteveEntity) {
//            LivingEntityPatch<?> attackerLivingEntityPatch = EpicFightCapabilities.getEntityPatch(attacker, LivingEntityPatch.class);
//            if (attackerLivingEntityPatch != null) {
//                AssetAccessor<? extends DynamicAnimation> attackerDynamicAnimation = Objects.requireNonNull(attackerLivingEntityPatch.getAnimator().getPlayerFor(null)).getAnimation();
//                if (attackerDynamicAnimation != null && attackerDynamicAnimation.get().getRegistryName() != null) {
//                    AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG] damage passed {}, defenderEntityState {}", attackerDynamicAnimation.get().getRegistryName().toString(), defenderEntityState);
//                }
//            }
//        }
//    }

    @Inject(method = "customPreAdditionClashBlade", at = @At("HEAD"), cancellable = true)
    private static void customLogicBeforeClashing(LivingAttackEvent livingAttackEvent,
                                                  LivingEntityPatch<?> defenderLivingEntityPatch,
                                                  AssetAccessor<? extends DynamicAnimation> defenderDynamicAnimation,
                                                  EntityState defenderEntityState, Entity attacker,
                                                  Entity defender, int clashBy,
                                                  CallbackInfo ci) {
        if (defender instanceof LivingEntity livingEntity
                && defender.level() instanceof ServerLevel serverLevel) {
            // Herobrine playing animation
            if (clashBy != 0) {
                if (defender instanceof AegisHerobrineEntity) {
                    defenderLivingEntityPatch.playAnimationSynchronized(AnimsAgony.AGONY_GUARD_HIT_1, 0.0F);
                }
            }

            // Place block to clash
            if (clashBy == 1) {
                Entity projectile = null;
                if (defender instanceof PathfinderMobInventory pathfinderMobInventory) {
                    projectile = pathfinderMobInventory.getBlockDamage();
                } else if (defender instanceof PlayerNpcEntity playerNpcEntity) {
                    projectile = playerNpcEntity.getBlockDamage();
                }
                if (projectile != null) {
                    Random random = new Random();
                    int pattern = random.nextInt(11);
                    int rot = random.nextInt(4);

                    BiFunction<Integer, Integer, int[]> toWorld = getIntegerIntegerBiFunction(defender, rot);

                    ItemStack handStack = livingEntity.getItemInHand(InteractionHand.MAIN_HAND);
                    BlockState placeState = Blocks.COBBLESTONE.defaultBlockState();
                    if (handStack.getItem() instanceof BlockItem blockItem) {
                        placeState = blockItem.getBlock().defaultBlockState();
                    }

                    BlockPos baseXZ;
                    int topY;
                    ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(projectile.getType());
                    if (key.getNamespace().equals("tacz")
                            || (key.getNamespace().equals("torchesbecomesunlight") && (key.getPath().equals("gun_knight_patriot") || key.getPath().equals("turret")))) {
                        Direction facing = defender.getDirection();
                        baseXZ = defender.blockPosition().relative(facing, 1);
                        topY = Mth.floor(defender.getY() + defender.getBbHeight());
                    } else {
                        baseXZ = BlockPos.containing(projectile.getX(), 0.0, projectile.getZ());
                        topY = Mth.floor(projectile.getY());
                    }
                    int surfaceY = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, baseXZ).getY();
                    BlockPos projXZ = new BlockPos(baseXZ.getX(), 0, baseXZ.getZ());

                    for (int y = surfaceY; y <= topY; y++) {
                        int layer = y - surfaceY;

                        BlockPos center = new BlockPos(projXZ.getX(), y, projXZ.getZ());
                        if (!serverLevel.getBlockState(center).canBeReplaced()) break;

                        defenderLivingEntityPatch.playAnimationSynchronized(AVAnimations.PLACE_BLOCK, 0.0F);
                        defender.playSound(SoundEvents.STONE_PLACE, 2.0F, 1.0F);
                        serverLevel.setBlockAndUpdate(center, placeState);

                        int[][] extrasLocal = switch (pattern) {
                            case 0 -> new int[][]{};

                            case 1 -> {
                                if (layer == 3) yield new int[][]{{1, 0}};
                                else yield new int[][]{};
                            }

                            case 2 -> {
                                if (layer == 0) yield new int[][]{{-1, 0}, {1, 0}, {2, 0}};
                                else if (layer == 1) yield new int[][]{{1, 0}};
                                else yield new int[][]{};
                            }

                            case 3 -> {
                                if (layer == 1) yield new int[][]{{-1, 0}, {1, 0}};
                                else yield new int[][]{};
                            }

                            case 4 -> {
                                if (layer == 0) yield new int[][]{{-1, 0}, {1, 0}};
                                else yield new int[][]{};
                            }

                            case 5 -> new int[][]{{1, 0}};

                            case 6 -> {
                                if (layer <= 1) yield new int[][]{{1, 0}};
                                else yield new int[][]{};
                            }

                            case 7 -> {
                                if (layer == 0) yield new int[][]{{1, 0}};
                                else yield new int[][]{};
                            }

                            case 8 -> {
                                if (layer == 1) yield new int[][]{{1, 0}};
                                else yield new int[][]{};
                            }

                            case 9 -> {
                                if (layer == 0) yield new int[][]{{-1, 0}};
                                else yield new int[][]{};
                            }

                            default -> {
                                if (layer == 1) yield new int[][]{{-1, 0}};
                                else yield new int[][]{};
                            }
                        };

                        for (int[] ab : extrasLocal) {
                            int[] dzdx = toWorld.apply(ab[0], ab[1]);
                            int dx = dzdx[0];
                            int dz = dzdx[1];

                            BlockPos p = center.offset(dx, 0, dz);
                            if (serverLevel.getBlockState(p).canBeReplaced()) {
                                serverLevel.setBlockAndUpdate(p, placeState);
                            }
                        }
                    }
                }
            }
        }
    }

    private static @NotNull BiFunction<Integer, Integer, int[]> getIntegerIntegerBiFunction(Entity defender, int rot) {
        Direction facing = defender.getDirection();

        int fx = facing.getStepX();
        int fz = facing.getStepZ();
        int rx = -fz;
        int rz = fx;

        for (int i = 0; i < rot; i++) {
            int nfx = rx, nfz = rz;
            int nrx = -fz, nrz = fx;
            fx = nfx; fz = nfz;
            rx = nrx; rz = nrz;
        }

        int finalRx = rx;
        int finalFx = fx;
        int finalRz = rz;
        int finalFz = fz;
        return (a, b) ->
                new int[]{a * finalRx + b * finalFx, a * finalRz + b * finalFz};
    }

    @Inject(method = "blacklistClashBladeAnimation", at = @At("HEAD"), cancellable = true)
    private static void rejectClashBladeFromAnimationsCondition(LivingAttackEvent livingAttackEvent,
                                                   LivingEntityPatch<?> defenderLivingEntityPatch,
                                                   AssetAccessor<? extends DynamicAnimation> defenderDynamicAnimation,
                                                   EntityState defenderEntityState, Entity attacker, Entity defender,
                                                   CallbackInfoReturnable<Boolean> cir) {
        if (defenderDynamicAnimation.get() instanceof BowAttackAnimation) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "conditionToPlayClashSound", at = @At("HEAD"), cancellable = true)
    private static void disableClashSoundFromProjectileBlock(LivingAttackEvent livingAttackEvent,
                                                                LivingEntityPatch<?> defenderLivingEntityPatch,
                                                                AssetAccessor<? extends DynamicAnimation> defenderDynamicAnimation,
                                                                EntityState defenderEntityState, Entity attacker, Entity defender,
                                                                CallbackInfoReturnable<Boolean> cir) {
        if (defender instanceof PathfinderMobInventory pathfinderMobInventory
                && pathfinderMobInventory.getBlockDamage() != null
                && defender.level() instanceof ServerLevel) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "customPostAdditionClashBlade", at = @At("HEAD"), cancellable = true)
    private static void revertWeaponAfterClashing(LivingAttackEvent livingAttackEvent,
                                                  LivingEntityPatch<?> defenderLivingEntityPatch,
                                                  AssetAccessor<? extends DynamicAnimation> defenderDynamicAnimation,
                                                  EntityState defenderEntityState, Entity attacker,
                                                  Entity defender, int clashBy,
                                                  CallbackInfo ci) {
        if (!(defender.level() instanceof ServerLevel serverLevel)) return;

        LivingEntityPatch<?> attackerLivingEntityPatch = EpicFightCapabilities.getEntityPatch(attacker, LivingEntityPatch.class);

        // Clash kick post
        if (clashBy == 0) {
            if (attackerLivingEntityPatch != null) {
                AssetAccessor<? extends DynamicAnimation> attackerDynamicAnimation = Objects.requireNonNull(attackerLivingEntityPatch.getAnimator().getPlayerFor(null)).getAnimation();
                if (attackerDynamicAnimation != null) {
                    if ((defender instanceof PathfinderMobInventory pathfinderMobInventory && pathfinderMobInventory.getBlockDamage() != null)
                            || (defender instanceof PlayerNpcEntity playerNpcEntity && playerNpcEntity.getBlockDamage() != null)) {
                        return;
                    }
                    if (attackerDynamicAnimation.get() instanceof KickAttackAnimation) {
                        attacker.playSound(AnnoyingVillagersModSounds.KICK_GUARD_BREAK.get());
                        attackerLivingEntityPatch.playAnimationSynchronized(Animations.BIPED_COMMON_NEUTRALIZED, 0.0F);
                        attacker.hurt(serverLevel.damageSources().generic(), 1.0F);
                    }
                }
            }
        }

        // Clash projectile post
        if (clashBy == 1) {
            if ((defender instanceof PathfinderMobInventory pathfinderMobInventory && pathfinderMobInventory.getBlockDamage() != null)
                    || (defender instanceof PlayerNpcEntity playerNpcEntity && playerNpcEntity.getBlockDamage() != null)) {
                new DelayedTask(10) {
                    @Override
                    public void run() {
                        boolean rollAndSwap = false;
                        if (defender instanceof PathfinderMobInventory pathfinderMobInventory
                                && pathfinderMobInventory.getBlockDamage() != null) {
                            pathfinderMobInventory.setBlockDamage(null);
                            rollAndSwap = true;
                        }

                        if (defender instanceof PlayerNpcEntity playerNpcEntity
                                && playerNpcEntity.getBlockDamage() != null) {
                            playerNpcEntity.setBlockDamage(null);
                            rollAndSwap = true;
                        }

                        if (rollAndSwap) {
                            if (CombatCommon.canSwapToBow((MobPatch<?>) defenderLivingEntityPatch)) {
                                double chance = new Random().nextDouble(0.0, 1.0);
                                if (chance <= 0.25) {
                                    defenderLivingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_RIGHT, 0.0F);
                                    CombatCommon.swapToBow((MobPatch<?>) defenderLivingEntityPatch);
                                } else if (chance <= 0.5) {
                                    defenderLivingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_LEFT, 0.0F);
                                    CombatCommon.swapToBow((MobPatch<?>) defenderLivingEntityPatch);
                                } else if (chance <= 0.7) {
                                    defenderLivingEntityPatch.playAnimationSynchronized(Animations.BIPED_ROLL_BACKWARD, 0.0F);
                                    CombatCommon.swapToBow((MobPatch<?>) defenderLivingEntityPatch);
                                } else if (chance <= 0.8) {
                                    defenderLivingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_RIGHT, 0.0F);
                                    CombatCommon.swapToMelee((MobPatch<?>) defenderLivingEntityPatch);
                                } else if (chance <= 0.9) {
                                    defenderLivingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_LEFT, 0.0F);
                                    CombatCommon.swapToMelee((MobPatch<?>) defenderLivingEntityPatch);
                                } else {
                                    defenderLivingEntityPatch.playAnimationSynchronized(Animations.BIPED_ROLL_BACKWARD, 0.0F);
                                    CombatCommon.swapToMelee((MobPatch<?>) defenderLivingEntityPatch);
                                }
                            } else {
                                double chance = new Random().nextDouble(0.0, 1.0);
                                if (chance <= 0.4) {
                                    defenderLivingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_RIGHT, 0.0F);
                                    CombatCommon.swapToMelee((MobPatch<?>) defenderLivingEntityPatch);
                                } else if (chance <= 0.5) {
                                    defenderLivingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_LEFT, 0.0F);
                                    CombatCommon.swapToMelee((MobPatch<?>) defenderLivingEntityPatch);
                                } else {
                                    defenderLivingEntityPatch.playAnimationSynchronized(Animations.BIPED_ROLL_BACKWARD, 0.0F);
                                    CombatCommon.swapToMelee((MobPatch<?>) defenderLivingEntityPatch);
                                }
                            }
                        }
                    }
                };
            }
        }

        // Clash custom attack post
//        if (clashBy == 2) {
//            if (defender instanceof HerobrineMob || defender instanceof AngrySteveEntity) {
//                defenderLivingEntityPatch.playAnimationSynchronized(AVAnimations.GUARD_BREAK_ATTACK, 0.0F);
//                defender.hurt(serverLevel.damageSources().generic(), 1.0F);
//            }
//        }
    }
}