package com.pla.annoyingvillagers.procedures;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.HitAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class BlueVillagerGeneralOnTickProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            LivingEntity livingentity;

            if (entity instanceof Mob) {
                Mob mob = (Mob)entity;

                livingentity = mob.getTarget();
            } else {
                livingentity = null;
            }

            Entity nearestMob = levelaccessor.getEntitiesOfClass(Mob.class,
                            AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D),
                            mob -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble(entity1 -> entity1.distanceToSqr(d0, d1, d2)))
                    .findFirst()
                    .orElse(null);

            if (livingentity == nearestMob && Math.random() <= 0.01D && entity instanceof LivingEntity) {
                LivingEntity livingentity1 = (LivingEntity) entity;

                if (!livingentity1.level.isClientSide()) {
                    livingentity1.addEffect(
                            new MobEffectInstance(
                                    AnnoyingVillagersModMobEffects.BLOCK.get(),
                                    10,
                                    1,
                                    false,
                                    false
                            )
                    );
                }
            }


            Vec3 vec3 = new Vec3(d0, d1, d2);
            Vec3 finalVec1 = vec3;
            List<Entity> list = (List)levelaccessor.getEntitiesOfClass(Entity.class, (new AABB(vec3, vec3)).inflate(20.0D), (entity1) -> {
                return true;
            }).stream().sorted(Comparator.comparingDouble((entity1) -> {
                return entity1.distanceToSqr(finalVec1);
            })).collect(Collectors.toList());
            Iterator iterator = list.iterator();

            Entity entity1;
            Mob mob1;
            LivingEntity livingentity2;
            ItemStack itemstack;

            while(iterator.hasNext()) {
                entity1 = (Entity)iterator.next();
                if (entity.isAlive()) {
                    if (entity instanceof Mob) {
                        mob1 = (Mob)entity;
                        livingentity2 = mob1.getTarget();
                    } else {
                        livingentity2 = null;
                    }

                    if (entity1 == livingentity2) {
                        LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

                        if (livingentitypatch != null) {
                            DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();

                            if (!(dynamicanimation instanceof AttackAnimation) && !(dynamicanimation instanceof LongHitAnimation) && !(dynamicanimation instanceof HitAnimation)) {
                                if (entity instanceof Mob) {
                                    Mob mob2 = (Mob)entity;

                                    livingentity = mob2.getTarget();
                                } else {
                                    livingentity = null;
                                }

                                LivingEntity livingentity3 = livingentity;

                                if (livingentity3 instanceof LivingEntity) {
                                    LivingEntity livingentity4 = (LivingEntity)livingentity3;

                                    itemstack = livingentity4.getMainHandItem();
                                } else {
                                    itemstack = ItemStack.EMPTY;
                                }

                                Anchor anchor;
                                Vec3 vec31 = null;
                                Mob mob3;
                                LivingEntity livingentity5;
                                double d3;
                                LivingEntity livingentity6;
                                double d4;
                                LivingEntity livingentity7;
                                ItemStack itemstack1;
                                Player player;

                                if (itemstack.getItem() instanceof BowItem) {
                                    anchor = Anchor.EYES;
                                    if (entity instanceof Mob) {
                                        mob3 = (Mob)entity;
                                        livingentity5 = mob3.getTarget();
                                    } else {
                                        livingentity5 = null;
                                    }

                                    d3 = livingentity5.getX();
                                    if (entity instanceof Mob) {
                                        mob3 = (Mob)entity;
                                        livingentity6 = mob3.getTarget();
                                    } else {
                                        livingentity6 = null;
                                    }

                                    d4 = livingentity6.getY();
                                    if (entity instanceof Mob) {
                                        mob3 = (Mob)entity;
                                        livingentity7 = mob3.getTarget();
                                    } else {
                                        livingentity7 = null;
                                    }

                                    vec31 = new Vec3(d3, d4, livingentity7.getZ());
                                    entity.lookAt(anchor, vec31);
                                    if (entity instanceof LivingEntity) {
                                        livingentity3 = (LivingEntity)entity;
                                        itemstack1 = new ItemStack(Items.BOW);
                                        itemstack1.setCount(1);
                                        livingentity3.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                                        if (livingentity3 instanceof Player) {
                                            player = (Player)livingentity3;
                                            player.getInventory().setChanged();
                                        }
                                    }

                                    if (Math.random() <= 0.05D) {
                                        new DelayedTask(Mth.nextInt(AnnoyingVillagers.randomSource, 1, 10)) {
                                            public void run() {
                                                Entity entity2 = entity;

                                                if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                    try {
                                                        entity2.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/combat/bow_shot_mid\" 0 1", entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                    } catch (CommandSyntaxException e) {
                                                        
                                                    }
                                                }

                                                entity2 = entity;
                                                Level level = entity2.level;

                                                if (!level.isClientSide()) {
                                                    Projectile projectile = new Arrow(EntityType.ARROW, level);
                                                    projectile.setOwner(entity2);
                                                    ((Arrow) projectile).setBaseDamage((double)4.0F);
                                                    ((Arrow) projectile).setKnockback(0);
                                                    ((Arrow) projectile).setPierceLevel((byte)1);
                                                    projectile.setPos(entity2.getX(), entity2.getEyeY() - 0.1D, entity2.getZ());
                                                    projectile.shoot(entity2.getLookAngle().x, entity2.getLookAngle().y, entity2.getLookAngle().z, 3.0F, 0.0F);
                                                    level.addFreshEntity(projectile);
                                                }

                                                LevelAccessor levelaccessor1 = levelaccessor;

                                                if (levelaccessor1 instanceof Level) {
                                                    Level level1 = (Level)levelaccessor1;

                                                    if (!level1.isClientSide()) {
                                                        level1.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.arrow.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                    } else {
                                                        level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.arrow.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                    }
                                                }
                                            }
                                        };
                                    }
                                } else {
                                    if (entity instanceof Mob) {
                                        Mob mob4 = (Mob)entity;

                                        livingentity = mob4.getTarget();
                                    } else {
                                        livingentity = null;
                                    }

                                    livingentity3 = livingentity;
                                    if (livingentity3 instanceof LivingEntity) {
                                        LivingEntity livingentity8 = (LivingEntity)livingentity3;

                                        itemstack = livingentity8.getMainHandItem();
                                    } else {
                                        itemstack = ItemStack.EMPTY;
                                    }

                                    if (itemstack.getItem() instanceof CrossbowItem) {
                                        anchor = Anchor.EYES;
                                        if (entity instanceof Mob) {
                                            mob3 = (Mob)entity;
                                            livingentity5 = mob3.getTarget();
                                        } else {
                                            livingentity5 = null;
                                        }

                                        d3 = livingentity5.getX();
                                        if (entity instanceof Mob) {
                                            mob3 = (Mob)entity;
                                            livingentity6 = mob3.getTarget();
                                        } else {
                                            livingentity6 = null;
                                        }

                                        d4 = livingentity6.getY();
                                        if (entity instanceof Mob) {
                                            mob3 = (Mob)entity;
                                            livingentity7 = mob3.getTarget();
                                        } else {
                                            livingentity7 = null;
                                        }

                                        vec31 = new Vec3(d3, d4, livingentity7.getZ());
                                        entity.lookAt(anchor, vec31);
                                        if (entity instanceof LivingEntity) {
                                            livingentity3 = (LivingEntity)entity;
                                            itemstack1 = new ItemStack(Items.BOW);
                                            itemstack1.setCount(1);
                                            livingentity3.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                                            if (livingentity3 instanceof Player) {
                                                player = (Player)livingentity3;
                                                player.getInventory().setChanged();
                                            }
                                        }

                                        if (Math.random() <= 0.05D) {
                                            new DelayedTask(Mth.nextInt(AnnoyingVillagers.randomSource, 1, 10)) {
                                                public void run() {
                                                    Entity entity2 = entity;
                                                    Level level = entity2.level;

                                                    if (!level.isClientSide()) {
                                                        Projectile projectile = new Arrow(EntityType.ARROW, level);
                                                        projectile.setOwner(entity2);
                                                        ((Arrow) projectile).setBaseDamage((double)4.0F);
                                                        ((Arrow) projectile).setKnockback(0);
                                                        ((Arrow) projectile).setPierceLevel((byte)1);
                                                        projectile.setPos(entity2.getX(), entity2.getEyeY() - 0.1D, entity2.getZ());
                                                        projectile.shoot(entity2.getLookAngle().x, entity2.getLookAngle().y, entity2.getLookAngle().z, 3.0F, 0.0F);
                                                        level.addFreshEntity(projectile);
                                                    }

                                                    entity2 = entity;
                                                    if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                        try {
                                                            entity2.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/combat/bow_shot_mid\" 0 1", entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                        } catch (CommandSyntaxException e) {
                                                            
                                                        }
                                                    }

                                                    LevelAccessor levelaccessor1 = levelaccessor;

                                                    if (levelaccessor1 instanceof Level) {
                                                        Level level1 = (Level)levelaccessor1;

                                                        if (!level1.isClientSide()) {
                                                            level1.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.arrow.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                        } else {
                                                            level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.arrow.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                        }
                                                    }
                                                }
                                            };
                                        }
                                    }
                                }
                            } else {
                                entity.clearFire();
                            }
                        }
                    }
                }
            }

            vec3 = new Vec3(d0, d1, d2);
            Vec3 finalVec = vec3;
            list = (List)levelaccessor.getEntitiesOfClass(Entity.class, (new AABB(vec3, vec3)).inflate(2.0D), (entity2) -> {
                return true;
            }).stream().sorted(Comparator.comparingDouble((entity2) -> {
                return entity2.distanceToSqr(finalVec);
            })).collect(Collectors.toList());
            iterator = list.iterator();

            while(iterator.hasNext()) {
                entity1 = (Entity)iterator.next();
                if (entity instanceof Mob) {
                    mob1 = (Mob)entity;
                    livingentity2 = mob1.getTarget();
                } else {
                    livingentity2 = null;
                }

                if (entity1 == livingentity2) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingentity9 = (LivingEntity)entity;
                        ItemStack itemstack2 = new ItemStack((ItemLike) AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get());

                        itemstack2.setCount(1);
                        livingentity9.setItemInHand(InteractionHand.MAIN_HAND, itemstack2);
                        if (livingentity9 instanceof Player) {
                            Player player1 = (Player)livingentity9;

                            player1.getInventory().setChanged();
                        }
                    }

                    Entity nearestCow = levelaccessor.getEntitiesOfClass(Cow.class,
                                    AABB.ofSize(new Vec3(d0, d1, d2), 3.0D, 3.0D, 3.0D),
                                    cow -> true)
                            .stream()
                            .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(d0, d1, d2)))
                            .findFirst()
                            .orElse(null);

                    if (entity.getVehicle() == nearestCow) {
                        entity.stopRiding();
                    }

                }
            }

            if (entity.getVehicle() instanceof Animal) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity10 = (LivingEntity)entity;

                    itemstack = livingentity10.getMainHandItem();
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                if (itemstack.getItem() instanceof SwordItem) {
                    entity.stopRiding();
                }
            }

        }
    }
}

