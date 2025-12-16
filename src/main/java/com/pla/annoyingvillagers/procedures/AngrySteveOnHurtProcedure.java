package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.clazz.PathfinderMobInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;
import java.util.Random;

public class AngrySteveOnHurtProcedure {

    public static void execute(LevelAccessor levelaccessor, final PathfinderMobInventory entity, final Entity attacker) {
        if (entity != null && attacker != null) {
            if (entity.getEnderPearlCooldown() == 0) {
                CombatBehaviour.throwEnderPearl(entity, (float) new Random().nextDouble(90.0D, 180.0D));
                if (Math.random() <= 0.4D) {
                    new DelayedTask(40) {
                        public void run() {
                            if (entity.isAlive()) {
                                CombatBehaviour.throwEnderPearl(entity, 0.0F);
                            }
                        }
                    };
                }

                if (Math.random() <= 0.1D) {
                    new DelayedTask(100) {
                        public void run() {
                            if (entity.isAlive()) {
                                CombatBehaviour.throwEnderPearl(entity, 0.0F);
                            }
                        }
                    };
                }

                if (Math.random() <= 0.075D && !entity.getPersistentData().getBoolean("steve_l_g_h_a")) {
                    entity.getPersistentData().putBoolean("steve_l_g_h_a", true);
                    entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.HEAVY_ATTACK_LEGENDARY_SWORD.get()));
                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        try {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "playsound epicfight:sfx.entity_move neutral @p",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                        }
                    }

                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                        if (livingEntityPatch != null) {
                            livingEntityPatch.playAnimationSynchronized(Animations.BIPED_DEMOLITION_LEAP, 0.0F);
                        }
                    }
                    new DelayedTask(2) {
                        @Override
                        public void run() {
                            entity.setDeltaMovement(new Vec3(0.0D, 1.3D, 0.0D));
                        }
                    };
                    new DelayedTask(8) {
                        public void run() {
                            if (entity.isAlive()) {
                                entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.HEAVY_ATTACK_LEGENDARY_SWORD.get()));

                                if (levelaccessor instanceof Level level) {
                                    if (!level.isClientSide()) {
                                        level.playSound(null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "zhanshenzhirenjuexing"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                    } else {
                                        level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "zhanshenzhirenjuexing"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                    }
                                }

                                if (levelaccessor instanceof ServerLevel serverLevel) {
                                    serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, entity.getX(), entity.getZ(), entity.getY(), 15, 0.0D, 0.0D, 0.0D, 0.2D);
                                }

                                entity.setDeltaMovement(new Vec3(attacker.getX(), attacker.getY(), attacker.getZ()));
                                Entity entity2 = entity;

                                if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                    try {
                                        entity2.getServer().getCommands().getDispatcher().execute(
                                                "playsound annoyingvillagers:heavy_attack_start neutral @p",
                                                entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                    } catch (CommandSyntaxException e) {
                                    }
                                }

                                entity2 = entity;
                                if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                    try {
                                        entity2.getServer().getCommands().getDispatcher().execute(
                                                "execute as @s at @s anchored eyes run particle minecraft:totem_of_undying ~ ~ ~ 0 0 0 0.5 100",
                                                entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                    } catch (CommandSyntaxException e) {
                                    }
                                }

                                if (!entity.level().isClientSide()) {
                                    entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 120, 2, false, false));
                                }

                                entity2 = entity;
                                if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                    LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity2, LivingEntityPatch.class);
                                    if (livingEntityPatch != null) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.LEGENDARY_SWORD_HEAVY_ATTACK, 0.0F);
                                    }
                                }
                                new DelayedTask(20) {
                                    @Override
                                    public void run() {
                                        if (entity.isAlive()) {
                                            entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.LEGENDARY_SWORD.get()));
                                            entity.getPersistentData().putBoolean("steve_l_g_h_a", false);
                                        }
                                    }
                                };
                            }
                        }
                    };
                }

                entity.setEnderPearlCooldown();
            }
        }
    }
}
