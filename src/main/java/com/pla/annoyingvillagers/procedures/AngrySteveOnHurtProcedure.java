package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.util.RandomSource;
public class AngrySteveOnHurtProcedure {

    public static void execute(LevelAccessor levelaccessor, final Entity entity, final Entity entity1) {
        if (entity != null && entity1 != null) {
            LivingEntity livingentity;

            if (entity.isAlive()) {
                if (Math.random() <= 0.08D) {
                    entity.setYRot(0.0F);
                    entity.setXRot((float)Mth.nextDouble(RandomSource.create(), -90.0D, -180.0D));
                    entity.setYBodyRot(entity.getYRot());
                    entity.setYHeadRot(entity.getYRot());
                    entity.yRotO = entity.getYRot();
                    entity.xRotO = entity.getXRot();
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingentity1 = (LivingEntity)entity;

                        livingentity1.yBodyRotO = livingentity1.getYRot();
                        livingentity1.yHeadRotO = livingentity1.getYRot();
                    }

                    Level level = entity.level();

                    if (!level.isClientSide()) {
                        Projectile projectile = new ThrownEnderpearl(EntityType.ENDER_PEARL, level);
                        projectile.setOwner(entity);
                        projectile.setPos(entity.getX(), entity.getEyeY() - 0.1D, entity.getZ());
                        projectile.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 2.0F, 0.0F);
                        level.addFreshEntity(projectile);
                    }

                    if (Math.random() <= 0.4D) {
                        new DelayedTask(40) {
                            public void run() {
                                if (entity.isAlive()) {
                                    Entity entity2 = entity;
                                    Level level1 = entity2.level();

                                    if (!level1.isClientSide()) {
                                        Projectile projectile1 = new ThrownEnderpearl(EntityType.ENDER_PEARL, level1);
                                        projectile1.setOwner(entity2);
                                        projectile1.setPos(entity2.getX(), entity2.getEyeY() - 0.1D, entity2.getZ());
                                        projectile1.shoot(entity2.getLookAngle().x, entity2.getLookAngle().y, entity2.getLookAngle().z, 1.0F, 0.0F);
                                        level1.addFreshEntity(projectile1);
                                    }
                                }
                            }
                        };
                    }
                }

                if (Math.random() <= 0.08D) {
                    new DelayedTask(100) {
                        public void run() {
                            Entity entity2 = entity;
                            Level level1 = entity2.level();

                            if (!level1.isClientSide()) {
                                Projectile projectile1 = new ThrownEnderpearl(EntityType.ENDER_PEARL, level1);
                                projectile1.setOwner(entity2);
                                projectile1.setPos(entity2.getX(), entity2.getEyeY() - 0.1D, entity2.getZ());
                                projectile1.shoot(entity2.getLookAngle().x, entity2.getLookAngle().y, entity2.getLookAngle().z, 1.5F, 0.0F);
                                level1.addFreshEntity(projectile1);
                            }
                        }
                    };
                }

                if (Math.random() <= 0.075D && !entity.getPersistentData().getBoolean("steve_l_g_h_a")) {
                    entity.getPersistentData().putBoolean("steve_l_g_h_a", true);
                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity)entity;
                        ItemStack itemstack = new ItemStack((ItemLike) AnnoyingVillagersModItems.HEAVY_ATTACK_LEGENDARY_SWORD_MOB.get());

                        itemstack.setCount(1);
                        livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                        if (livingentity instanceof Player) {
                            Player player = (Player)livingentity;

                            player.getInventory().setChanged();
                        }
                    }

                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        try {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "playsound epicfight:sfx.entity_move neutral @p",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                        }
                    }

                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        try {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "indestructible @s play \"epicfight:biped/skill/demolition_leap\" 0 1",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
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
                            LivingEntity livingentity2;

                            if (entity instanceof LivingEntity) {
                                livingentity2 = (LivingEntity)entity;
                                ItemStack itemstack1 = new ItemStack((ItemLike)AnnoyingVillagersModItems.AXE_ATTACK_LEGENDARY_SWORD_MOB_AWAKENED.get());

                                itemstack1.setCount(1);
                                livingentity2.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                                if (livingentity2 instanceof Player) {
                                    Player player1 = (Player)livingentity2;

                                    player1.getInventory().setChanged();
                                }
                            }

                            LevelAccessor levelaccessor1 = levelaccessor;
                            Level level1;

                            if (levelaccessor1 instanceof Level) {
                                level1 = (Level)levelaccessor1;
                                if (!level1.isClientSide()) {
                                    level1.playSound((Player)null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "l_g_w_u")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                } else {
                                    level1.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "l_g_w_u")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                }
                            }

                            levelaccessor1 = levelaccessor;
                            if (levelaccessor1 instanceof Level) {
                                level1 = (Level)levelaccessor1;
                                if (!level1.isClientSide()) {
                                    level1.playSound((Player)null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "zhanshenzhirenjuexing")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                } else {
                                    level1.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "zhanshenzhirenjuexing")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                }
                            }

                            levelaccessor1 = levelaccessor;
                            if (levelaccessor1 instanceof ServerLevel) {
                                ServerLevel serverlevel = (ServerLevel)levelaccessor1;

                                serverlevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, entity.getX(), entity.getZ(), entity.getY(), 15, 0.0D, 0.0D, 0.0D, 0.2D);
                            }

                            entity.setDeltaMovement(new Vec3(entity1.getX(), entity1.getY(), entity1.getZ()));
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

                            if (entity instanceof LivingEntity) {
                                livingentity2 = (LivingEntity)entity;
                                if (!livingentity2.level().isClientSide()) {
                                    livingentity2.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 120, 2, false, false));
                                }
                            }

                            entity2 = entity;
                            if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                try {
                                    entity2.getServer().getCommands().getDispatcher().execute(
                                            "indestructible @s play \"annoyingvillagers:biped/combat/legendary_sword_heavy_attack\" 0 1",
                                            entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {
                                }
                            }
                            new DelayedTask(20) {
                                @Override
                                public void run() {
                                    if (entity instanceof LivingEntity) {
                                        LivingEntity livingentity3 = (LivingEntity)entity;
                                        ItemStack itemstack2 = new ItemStack((ItemLike)AnnoyingVillagersModItems.LEGENDARY_SWORD_MOB.get());

                                        itemstack2.setCount(1);
                                        livingentity3.setItemInHand(InteractionHand.MAIN_HAND, itemstack2);
                                        if (livingentity3 instanceof Player) {
                                            Player player2 = (Player)livingentity3;

                                            player2.getInventory().setChanged();
                                        }
                                    }

                                    entity.getPersistentData().putBoolean("steve_l_g_h_a", false);
                                }
                            };
                        }
                    };
                }
            }

            if (Math.random() <= 0.58D && entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 2, 0, false, false));
                }
            }

        }
    }
}
