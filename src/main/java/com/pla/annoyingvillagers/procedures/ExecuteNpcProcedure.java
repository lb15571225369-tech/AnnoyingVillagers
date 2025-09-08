package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.capabilities.AVCategories;
import com.pla.annoyingvillagers.entity.NullEntity;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.corruptdog.cdm.gameasset.CorruptSound;
import net.corruptdog.cdm.world.CDWeaponCapabilityPresets;
import net.corruptdog.cdm.world.CorruptWeaponCategories;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.effect.EpicFightMobEffects;
import net.minecraft.util.RandomSource;
@EventBusSubscriber
public class ExecuteNpcProcedure {

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent livingattackevent) {
        if (livingattackevent != null && livingattackevent.getEntity() != null) {
            execute(livingattackevent, livingattackevent.getEntity().level(), livingattackevent.getEntity(), livingattackevent.getSource().getEntity());
        }

    }

    public static void execute(LevelAccessor levelaccessor, Entity victim, Entity attacker) {
        if (attacker instanceof NullEntity) return;
        execute((Event) null, levelaccessor, victim, attacker);
    }

    private static void prepareForNpcExecution(Vec3 vec3, final HumanoidMobPatch<?> humanoidmobpatch, final LivingEntityPatch<?> livingentitypatch, final Entity victim, final Entity attacker, float xTeleport, float zTeleport) {
        humanoidmobpatch.setGrapplingTarget((LivingEntity)livingentitypatch.getOriginal());
        attacker.teleportTo(((LivingEntity)livingentitypatch.getOriginal()).getX() + vec3.x * xTeleport, ((LivingEntity)livingentitypatch.getOriginal()).getY(), ((LivingEntity)livingentitypatch.getOriginal()).getZ() + vec3.z * zTeleport);
        victim.getPersistentData().putBoolean("kick_x", true);
        LivingEntity livingentity2;

        if (victim instanceof LivingEntity) {
            livingentity2 = (LivingEntity)victim;
            if (!livingentity2.level().isClientSide()) {
                livingentity2.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
            }
        }

        if (attacker instanceof LivingEntity) {
            livingentity2 = (LivingEntity)attacker;
            if (!livingentity2.level().isClientSide()) {
                livingentity2.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
            }
        }

        if (attacker instanceof LivingEntity) {
            livingentity2 = (LivingEntity)attacker;
            if (!livingentity2.level().isClientSide()) {
                livingentity2.addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 100, 0));
            }
        }

        if (attacker instanceof LivingEntity) {
            livingentity2 = (LivingEntity)attacker;
            if (!livingentity2.level().isClientSide()) {
                livingentity2.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 50, 1));
            }
        }

        if (attacker instanceof LivingEntity) {
            livingentity2 = (LivingEntity)attacker;
            if (!livingentity2.level().isClientSide()) {
                livingentity2.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 5));
            }
        }

        if (victim instanceof LivingEntity) {
            livingentity2 = (LivingEntity)victim;
            if (!livingentity2.level().isClientSide()) {
                livingentity2.addEffect(new MobEffectInstance((MobEffect)EpicFightMobEffects.STUN_IMMUNITY.get(), 40, 0));
            }
        }

        if (victim instanceof LivingEntity) {
            livingentity2 = (LivingEntity)victim;
            if (!livingentity2.level().isClientSide()) {
                livingentity2.playSound((SoundEvent) CorruptSound.EXECUTE.get(), 1.0F, 1.0F);
            }
        }

        victim.lookAt(Anchor.EYES, new Vec3(attacker.getX(), attacker.getY() + 1.0D, attacker.getZ()));
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, final Entity victim, final Entity attacker) {
        if (attacker instanceof NullEntity) return;
        if (victim != null && attacker != null) {
            if (!(attacker instanceof Player)) {
                boolean flag = false;

                if (victim instanceof LivingEntity livingEntity) {
                    flag = livingEntity.hasEffect(AnnoyingVillagersModMobEffects.EC.get());
                }

                if (!flag && attacker instanceof LivingEntity livingEntity) {
                    flag = livingEntity.hasEffect(AnnoyingVillagersModMobEffects.EC.get());
                }

                if (!flag && attacker.isAlive()) {
                    final HumanoidMobPatch<?> humanoidmobpatch = (HumanoidMobPatch) EpicFightCapabilities.getEntityPatch(attacker, HumanoidMobPatch.class);
                    final LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(victim, LivingEntityPatch.class);

                    if (humanoidmobpatch != null && livingentitypatch != null) {
                        DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();

                        if (dynamicanimation instanceof LongHitAnimation || dynamicanimation == Animations.BIPED_COMMON_NEUTRALIZED || dynamicanimation == Animations.BIPED_KNOCKDOWN) {
                            if (humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CorruptWeaponCategories.YAMATO && humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CDWeaponCapabilityPresets.EX_YAMATO) {
                                if (humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CorruptWeaponCategories.KATANA && humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.UCHIGATANA) {
                                    if ((humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CorruptWeaponCategories.S_DAGGER || humanoidmobpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CorruptWeaponCategories.S_DAGGER) && (humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.DAGGER || humanoidmobpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.DAGGER)) {
                                        if (humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CorruptWeaponCategories.S_SPEAR && humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.SPEAR) {
                                            if (humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CorruptWeaponCategories.S_GREATSWORD && humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.GREATSWORD && humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != AVCategories.LEGENDARYSWORD) {
                                                if (humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.TRIDENT) {
                                                    new DelayedTask(4) {
                                                        @Override
                                                        public void run() {
                                                            if (victim.isAlive() && attacker.isAlive()) {
                                                                prepareForNpcExecution(((LivingEntity)livingentitypatch.getOriginal()).getViewVector(1.0F), humanoidmobpatch, livingentitypatch, victim, attacker, 1.5F, 1.5F);
                                                                if (!victim.level().isClientSide()) {
                                                                    humanoidmobpatch.playAnimationSynchronized(CorruptAnimations.TRIDENT_EXECUTE, 0.0F);
                                                                    livingentitypatch.playAnimationSynchronized(CorruptAnimations.TRIDENT_EXECUTED, 0.0F);
                                                                }
                                                            }
                                                        }
                                                    };
                                                } else if ((humanoidmobpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CorruptWeaponCategories.S_DAGGER || humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CorruptWeaponCategories.S_DAGGER) && (humanoidmobpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.DAGGER || humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.DAGGER)) {
                                                    if (humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CorruptWeaponCategories.S_TACHI && humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.TACHI) {
                                                        if (humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CorruptWeaponCategories.GREAT_TACHI && humanoidmobpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CorruptWeaponCategories.GREAT_TACHI) {
                                                            if ((humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CorruptWeaponCategories.S_LONGSWORD || humanoidmobpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SHIELD) && (humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != AVCategories.HARDGREATSWORD) && (humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.LONGSWORD || humanoidmobpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SHIELD)) {
                                                                new DelayedTask(4) {
                                                                    @Override
                                                                    public void run() {
                                                                        if (victim.isAlive() && attacker.isAlive()) {
                                                                            prepareForNpcExecution(((LivingEntity) livingentitypatch.getOriginal()).getViewVector(1.0F), humanoidmobpatch, livingentitypatch, victim, attacker, 1.5F, 1.5F);
                                                                            if (!victim.level().isClientSide()) {
                                                                                humanoidmobpatch.playAnimationSynchronized(CorruptAnimations.EXECUTE_WEAPON, 0.0F);
                                                                                livingentitypatch.playAnimationSynchronized(CorruptAnimations.EXECUTED_WEAPON, 0.0F);
                                                                            }
                                                                        }
                                                                    }
                                                                };
                                                            } else {
                                                                new DelayedTask(4) {
                                                                    @Override
                                                                    public void run() {
                                                                        if (victim.isAlive() && attacker.isAlive()) {
                                                                            prepareForNpcExecution(((LivingEntity) livingentitypatch.getOriginal()).getViewVector(1.0F), humanoidmobpatch, livingentitypatch, victim, attacker, 2.0F, 2.0F);
                                                                            if (!victim.level().isClientSide()) {
                                                                                humanoidmobpatch.playAnimationSynchronized(CorruptAnimations.LONGSWORD_EXECUTE, 0.0F);
                                                                                livingentitypatch.playAnimationSynchronized(CorruptAnimations.LONGSWORD_EXECUTED, 0.0F);
                                                                            }
                                                                        }
                                                                    }
                                                                };
                                                            }
                                                        } else if (humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.FIST) {
                                                            new DelayedTask(4) {
                                                                @Override
                                                                public void run() {
                                                                    if (victim.isAlive() && attacker.isAlive()) {
                                                                        prepareForNpcExecution(((LivingEntity) livingentitypatch.getOriginal()).getViewVector(3.0F), humanoidmobpatch, livingentitypatch, victim, attacker, 1.5F, 1.5F);
                                                                        if (!victim.level().isClientSide()) {
                                                                            if (RandomSource.create().nextFloat() < 0.35F) {
                                                                                humanoidmobpatch.playAnimationSynchronized(AVAnimations.WRESTLING_BACK, 0.0F);
                                                                                livingentitypatch.playAnimationSynchronized(AVAnimations.WRESTLING_HIT_BACK, 0.0F);
                                                                            } else if (RandomSource.create().nextFloat() < 0.7F) {
                                                                                humanoidmobpatch.playAnimationSynchronized(AVAnimations.WRESTLING, 0.0F);
                                                                                livingentitypatch.playAnimationSynchronized(AVAnimations.WRESTLING_HIT, 0.0F);
                                                                            } else {
                                                                                humanoidmobpatch.playAnimationSynchronized(AVAnimations.BOSS_EXECUTE, 0.0F);
                                                                                livingentitypatch.playAnimationSynchronized(AVAnimations.BOSS_EXECUTE_HIT, 0.0F);
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            };
                                                        } else if (humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD || humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.AXE || humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CorruptWeaponCategories.S_SWORD) {
                                                            if (humanoidmobpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD) {
                                                                new DelayedTask(4) {
                                                                    @Override
                                                                    public void run() {
                                                                        if (victim.isAlive() && attacker.isAlive()) {
                                                                            prepareForNpcExecution(((LivingEntity) livingentitypatch.getOriginal()).getViewVector(1.0F), humanoidmobpatch, livingentitypatch, victim, attacker, 1.0F, 1.0F);
                                                                            if (!victim.level().isClientSide()) {
                                                                                humanoidmobpatch.playAnimationSynchronized(AVAnimations.EXECUTE_WEAPON_SHIELD, 0.0F);
                                                                                livingentitypatch.playAnimationSynchronized(AVAnimations.EXECUTE_WEAPON_SHIELD_HIT, 0.0F);
                                                                            }
                                                                        }
                                                                    }
                                                                };
                                                            } else if (humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.AXE && humanoidmobpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.AXE) {
                                                                new DelayedTask(4) {
                                                                    @Override
                                                                    public void run() {
                                                                        if (victim.isAlive() && attacker.isAlive()) {
                                                                            prepareForNpcExecution(((LivingEntity) livingentitypatch.getOriginal()).getViewVector(1.0F), humanoidmobpatch, livingentitypatch, victim, attacker, 1.0F, 1.0F);
                                                                            if (!victim.level().isClientSide()) {
                                                                                humanoidmobpatch.playAnimationSynchronized(AVAnimations.EXECUTE_DUAL_AXE, 0.0F);
                                                                                livingentitypatch.playAnimationSynchronized(AVAnimations.EXECUTE_DUAL_AXE_HIT, 0.0F);
                                                                            }
                                                                        }
                                                                    }
                                                                };
                                                            } else if (humanoidmobpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD || humanoidmobpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CorruptWeaponCategories.S_SWORD) {
                                                                new DelayedTask(4) {
                                                                    @Override
                                                                    public void run() {
                                                                        if (victim.isAlive() && attacker.isAlive()) {
                                                                            prepareForNpcExecution(((LivingEntity) livingentitypatch.getOriginal()).getViewVector(1.0F), humanoidmobpatch, livingentitypatch, victim, attacker, 1.9F, 1.9F);
                                                                            if (!victim.level().isClientSide()) {
                                                                                humanoidmobpatch.playAnimationSynchronized(AVAnimations.EXECUTE_DUAL, 0.0F);
                                                                                livingentitypatch.playAnimationSynchronized(AVAnimations.EXECUTE_DUAL_HIT, 0.0F);
                                                                            }
                                                                        }
                                                                    }
                                                                };
                                                            } else if (humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD) {
                                                                new DelayedTask(4) {
                                                                    @Override
                                                                    public void run() {
                                                                        if (victim.isAlive() && attacker.isAlive()) {
                                                                            prepareForNpcExecution(((LivingEntity) livingentitypatch.getOriginal()).getViewVector(1.0F), humanoidmobpatch, livingentitypatch, victim, attacker, 1.5F, 1.5F);
                                                                            if (!victim.level().isClientSide()) {
                                                                                humanoidmobpatch.playAnimationSynchronized(CorruptAnimations.EXECUTE_WEAPON, 0.0F);
                                                                                livingentitypatch.playAnimationSynchronized(CorruptAnimations.EXECUTED_WEAPON, 0.0F);
                                                                            }
                                                                        }
                                                                    }
                                                                };
                                                            } else {
                                                                new DelayedTask(4) {
                                                                    @Override
                                                                    public void run() {
                                                                        if (victim.isAlive() && attacker.isAlive()) {
                                                                            prepareForNpcExecution(((LivingEntity) livingentitypatch.getOriginal()).getViewVector(1.0F), humanoidmobpatch, livingentitypatch, victim, attacker, 1.4F, 1.4F);
                                                                            if (!victim.level().isClientSide()) {
                                                                                humanoidmobpatch.playAnimationSynchronized(AVAnimations.EXECUTE_ONE_HAND, 0.0F);
                                                                                livingentitypatch.playAnimationSynchronized(AVAnimations.EXECUTE_DUAL_HIT, 0.0F);
                                                                            }
                                                                        }
                                                                    }
                                                                };
                                                            }
                                                        } else {
                                                            new DelayedTask(4) {
                                                                @Override
                                                                public void run() {
                                                                    if (victim.isAlive() && attacker.isAlive()) {
                                                                        prepareForNpcExecution(((LivingEntity)livingentitypatch.getOriginal()).getViewVector(1.0F), humanoidmobpatch, livingentitypatch, victim, attacker, 2.0F, 2.0F);
                                                                        if (!victim.level().isClientSide()) {
                                                                            humanoidmobpatch.playAnimationSynchronized(CorruptAnimations.LONGSWORD_EXECUTE, 0.0F);
                                                                            livingentitypatch.playAnimationSynchronized(CorruptAnimations.LONGSWORD_EXECUTED, 0.0F);
                                                                        }
                                                                    }
                                                                }
                                                            };
                                                        }
                                                    } else {
                                                        new DelayedTask(4) {
                                                            @Override
                                                            public void run() {
                                                                if (victim.isAlive() && attacker.isAlive()) {
                                                                    prepareForNpcExecution(((LivingEntity)livingentitypatch.getOriginal()).getViewVector(1.0F), humanoidmobpatch, livingentitypatch, victim, attacker, 2.0F, 2.0F);
                                                                    if (!victim.level().isClientSide()) {
                                                                        humanoidmobpatch.playAnimationSynchronized(CorruptAnimations.TACHI_EXECUTE, 0.0F);
                                                                        livingentitypatch.playAnimationSynchronized(CorruptAnimations.TACHI_EXECUTED, 0.0F);
                                                                    }
                                                                }
                                                            }
                                                        };
                                                    }
                                                } else {
                                                    new DelayedTask(4) {
                                                        @Override
                                                        public void run() {
                                                            if (victim.isAlive() && attacker.isAlive()) {
                                                                prepareForNpcExecution(((LivingEntity)livingentitypatch.getOriginal()).getViewVector(1.0F), humanoidmobpatch, livingentitypatch, victim, attacker, 1.0F, 1.0F);
                                                                if (!victim.level().isClientSide()) {
                                                                    humanoidmobpatch.playAnimationSynchronized(CorruptAnimations.DUAL_DAGGER_EXECUTE, 0.0F);
                                                                    livingentitypatch.playAnimationSynchronized(CorruptAnimations.DUAL_DAGGER_EXECUTED, 0.0F);
                                                                }
                                                            }
                                                        }
                                                    };
                                                }
                                            } else {
                                                if (victim.isAlive() && attacker.isAlive()) {
                                                    prepareForNpcExecution(((LivingEntity)livingentitypatch.getOriginal()).getViewVector(1.0F), humanoidmobpatch, livingentitypatch, victim, attacker, 1.5F, 1.5F);
                                                    if (!victim.level().isClientSide()) {
                                                        humanoidmobpatch.playAnimationSynchronized(CorruptAnimations.GREATSWORD_EXECUTE, 0.0F);
                                                        livingentitypatch.playAnimationSynchronized(CorruptAnimations.GREATSWORD_EXECUTED, 0.0F);
                                                    }
                                                }
                                            }
                                        } else {
                                            if (victim.isAlive() && attacker.isAlive()) {
                                                prepareForNpcExecution(((LivingEntity)livingentitypatch.getOriginal()).getViewVector(1.0F), humanoidmobpatch, livingentitypatch, victim, attacker, 2.0F, 2.0F);
                                                if (!victim.level().isClientSide()) {
                                                    humanoidmobpatch.playAnimationSynchronized(CorruptAnimations.SPEAR_EXECUTE, 0.0F);
                                                    livingentitypatch.playAnimationSynchronized(CorruptAnimations.SPEAR_EXECUTED, 0.0F);
                                                }
                                            }
                                        }
                                    } else {
                                        if (victim.isAlive() && attacker.isAlive()) {
                                            prepareForNpcExecution(((LivingEntity)livingentitypatch.getOriginal()).getViewVector(1.0F), humanoidmobpatch, livingentitypatch, victim, attacker, 2.0F, 2.0F);
                                            if (!victim.level().isClientSide()) {
                                                humanoidmobpatch.playAnimationSynchronized(CorruptAnimations.DAGGER_EXECUTE, 0.0F);
                                                livingentitypatch.playAnimationSynchronized(CorruptAnimations.DAGGER_EXECUTED, 0.0F);
                                            }
                                        }
                                    }
                                } else {
                                    if (victim.isAlive() && attacker.isAlive()) {
                                        prepareForNpcExecution(((LivingEntity)livingentitypatch.getOriginal()).getViewVector(1.0F), humanoidmobpatch, livingentitypatch, victim, attacker, 2.0F, 2.0F);
                                        if (!victim.level().isClientSide()) {
                                            humanoidmobpatch.playAnimationSynchronized(CorruptAnimations.KATANA_EXECUTE, 0.0F);
                                            livingentitypatch.playAnimationSynchronized(CorruptAnimations.KATANA_EXECUTED, 0.0F);
                                        }
                                    }
                                }
                            } else {
                                if (victim.isAlive() && attacker.isAlive()) {
                                    prepareForNpcExecution(((LivingEntity)livingentitypatch.getOriginal()).getViewVector(1.0F), humanoidmobpatch, livingentitypatch, victim, attacker, 2.0F, 2.0F);
                                    if (!victim.level().isClientSide()) {
                                        humanoidmobpatch.playAnimationSynchronized(CorruptAnimations.EXECUTE_YAMATO, 0.0F);
                                        livingentitypatch.playAnimationSynchronized(CorruptAnimations.EXECUTED_YAMATO, 0.0F);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
