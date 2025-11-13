package com.pla.annoyingvillagers.procedures;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import net.minecraft.util.RandomSource;
public class SteveOnTickProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            LivingEntity livingentity;

            if (entity.isInWater() && entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2, 5, false, false));
                }
            }

            if (entity.isPassenger()) {
                entity.stopRiding();
            }

            if (!levelaccessor.getEntitiesOfClass(PrimedTnt.class, AABB.ofSize(new Vec3(d0, d1, d2), 5.0D, 5.0D, 5.0D), (primedtnt) -> {
                return true;
            }).isEmpty()) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^ ^ ^1 minecraft:oak_planks",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^ ^-1 ^1 minecraft:oak_planks",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^ ^1 ^1 minecraft:oak_planks",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^1 ^ ^1 minecraft:oak_planks",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^1 ^-1 ^1 minecraft:oak_planks",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^1 ^1 ^1 minecraft:oak_planks",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^-1 ^ ^1 minecraft:oak_planks",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^-1 ^1 ^1 minecraft:oak_planks",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^-1 ^-1 ^1 minecraft:oak_planks",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }
            }

            if (!levelaccessor.getEntitiesOfClass(VillagerScoutEntity.class, AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D), (cunminzhenchabingentity) -> {
                return true;
            }).isEmpty() && entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 10, 4, false, false));
                }
            }

            if (!levelaccessor.getEntitiesOfClass(VillagerScoutCaptainEntity.class, AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D), (cczdzentity) -> {
                return true;
            }).isEmpty() && entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.BLOCK.get(), 10, 4, false, false));
                }
            }

            if (!levelaccessor.getEntitiesOfClass(BlueVillagerGeneralEntity.class, AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D), (lancunqientity) -> {
                return true;
            }).isEmpty() && entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.BLOCK.get(), 10, 4, false, false));
                }
            }

            if (!levelaccessor.getEntitiesOfClass(GreenVillagerGeneralEntity.class, AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D), (lucunqientity) -> {
                return true;
            }).isEmpty() && entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.BLOCK.get(), 10, 4, false, false));
                }
            }

            if (!levelaccessor.getEntitiesOfClass(RedVillagerGeneralEntity.class, AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D), (lancunqientity) -> {
                return true;
            }).isEmpty() && entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.BLOCK.get(), 10, 4, false, false));
                }
            }

            if (!levelaccessor.getEntitiesOfClass(PurpleVillagerGeneralEntity.class, AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D), (lucunqientity) -> {
                return true;
            }).isEmpty() && entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.BLOCK.get(), 10, 4, false, false));
                }
            }

            if (!levelaccessor.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D), (monster) -> {
                return true;
            }).isEmpty() && Math.random() <= 0.02D && entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.BLOCK.get(), 10, 4, false, false));
                }
            }

            Vec3 vec3 = new Vec3(d0, d1, d2);
            List<Entity> list = (List)levelaccessor.getEntitiesOfClass(Entity.class, (new AABB(vec3, vec3)).inflate(30.0D), (entity1) -> {
                return true;
            }).stream().sorted(Comparator.comparingDouble((entity1) -> {
                return entity1.distanceToSqr(vec3);
            })).collect(Collectors.toList());
            Iterator iterator = list.iterator();

            ItemStack itemstack;

            while(iterator.hasNext()) {
                Entity entity1 = (Entity) iterator.next();
                LivingEntity livingentity1;

                if (entity instanceof Mob) {
                    Mob mob = (Mob) entity;

                    livingentity1 = mob.getTarget();
                } else {
                    livingentity1 = null;
                }

                if (entity1 == livingentity1) {
                    LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

                    if (livingentitypatch != null) {
                        AssetAccessor<? extends DynamicAnimation> dynamicanimation = livingentitypatch.getAnimator().getPlayerFor(null).getAnimation();

                        if (!(dynamicanimation instanceof AttackAnimation) && !(dynamicanimation instanceof LongHitAnimation) && !(dynamicanimation instanceof HitAnimation)) {
                            LivingEntity livingentity2;

                            if (entity instanceof Mob) {
                                Mob mob1 = (Mob) entity;

                                livingentity2 = mob1.getTarget();
                            } else {
                                livingentity2 = null;
                            }

                            LivingEntity livingentity3 = livingentity2;

                            if (livingentity3 instanceof LivingEntity) {
                                LivingEntity livingentity4 = (LivingEntity) livingentity3;

                                itemstack = livingentity4.getMainHandItem();
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            ItemStack itemstack1;
                            Player player;
                            Anchor anchor;
                            Vec3 vec31;
                            Mob mob2;
                            LivingEntity livingentity5;
                            double d3;
                            LivingEntity livingentity6;
                            double d4;
                            LivingEntity livingentity7;

                            if (itemstack.getItem() instanceof BowItem) {
                                if (entity instanceof LivingEntity) {
                                    livingentity3 = (LivingEntity) entity;
                                    itemstack1 = new ItemStack(Items.BOW);
                                    itemstack1.setCount(1);
                                    livingentity3.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                                    if (livingentity3 instanceof Player) {
                                        player = (Player) livingentity3;
                                        player.getInventory().setChanged();
                                    }
                                }

                                anchor = Anchor.EYES;
                                if (entity instanceof Mob) {
                                    mob2 = (Mob) entity;
                                    livingentity5 = mob2.getTarget();
                                } else {
                                    livingentity5 = null;
                                }

                                d3 = livingentity5.getX();
                                if (entity instanceof Mob) {
                                    mob2 = (Mob) entity;
                                    livingentity6 = mob2.getTarget();
                                } else {
                                    livingentity6 = null;
                                }

                                d4 = livingentity6.getY();
                                if (entity instanceof Mob) {
                                    mob2 = (Mob) entity;
                                    livingentity7 = mob2.getTarget();
                                } else {
                                    livingentity7 = null;
                                }

                                vec31 = new Vec3(d3, d4, livingentity7.getZ());
                                entity.lookAt(anchor, vec31);
                                if (Math.random() <= 0.3D) {
                                    new DelayedTask(Mth.nextInt(RandomSource.create(), 1, 10)) {
                                        public void run() {
                                            Entity entity2 = entity;
                                            Level level = entity2.level();

                                            if (!level.isClientSide()) {
                                                Projectile projectile = new Arrow(EntityType.ARROW, level);
                                                {
                                                    projectile.setOwner(entity);
                                                    ((Arrow) projectile).setBaseDamage(4.0F);
                                                    ((Arrow) projectile).setKnockback(0);
                                                    ((Arrow) projectile).setPierceLevel((byte) 1);
                                                    ((Arrow) projectile).setPos(entity2.getX(), entity2.getEyeY() - 0.1D, entity2.getZ());
                                                    projectile.shoot(entity2.getLookAngle().x, entity2.getLookAngle().y, entity2.getLookAngle().z, 4.0F, 0.0F);
                                                    level.addFreshEntity(projectile);
                                                }

                                                entity2 = entity;
                                                if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                                    LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity2, LivingEntityPatch.class);
                                                    if (livingEntityPatch != null) {
                                                        livingEntityPatch.playAnimationSynchronized(Animations.BIPED_BOW_SHOT, 0.0F);
                                                    }
                                                }

                                                LevelAccessor levelaccessor1 = levelaccessor;

                                                if (levelaccessor1 instanceof Level) {
                                                    Level level1 = (Level) levelaccessor1;

                                                    if (!level1.isClientSide()) {
                                                        level1.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.arrow.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                    } else {
                                                        level1.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.arrow.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                    }
                                                }
                                            }
                                        }
                                    };
                                } else {
                                    if (entity instanceof Mob) {
                                        Mob mob3 = (Mob) entity;

                                        livingentity2 = mob3.getTarget();
                                    } else {
                                        livingentity2 = null;
                                    }

                                    livingentity3 = livingentity2;
                                    if (livingentity3 instanceof LivingEntity) {
                                        LivingEntity livingentity8 = (LivingEntity) livingentity3;

                                        itemstack = livingentity8.getMainHandItem();
                                    } else {
                                        itemstack = ItemStack.EMPTY;
                                    }

                                    if (itemstack.getItem() instanceof CrossbowItem) {
                                        if (entity instanceof LivingEntity) {
                                            livingentity3 = (LivingEntity) entity;
                                            itemstack1 = new ItemStack(Items.BOW);
                                            itemstack1.setCount(1);
                                            livingentity3.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                                            if (livingentity3 instanceof Player) {
                                                player = (Player) livingentity3;
                                                player.getInventory().setChanged();
                                            }
                                        }

                                        anchor = Anchor.EYES;
                                        if (entity instanceof Mob) {
                                            mob2 = (Mob) entity;
                                            livingentity5 = mob2.getTarget();
                                        } else {
                                            livingentity5 = null;
                                        }

                                        d3 = livingentity5.getX();
                                        if (entity instanceof Mob) {
                                            mob2 = (Mob) entity;
                                            livingentity6 = mob2.getTarget();
                                        } else {
                                            livingentity6 = null;
                                        }

                                        d4 = livingentity6.getY();
                                        if (entity instanceof Mob) {
                                            mob2 = (Mob) entity;
                                            livingentity7 = mob2.getTarget();
                                        } else {
                                            livingentity7 = null;
                                        }

                                        vec31 = new Vec3(d3, d4, livingentity7.getZ());
                                        entity.lookAt(anchor, vec31);
                                        if (Math.random() <= 0.3D) {
                                            new DelayedTask(Mth.nextInt(RandomSource.create(), 1, 10)) {
                                                public void run() {
                                                    Entity entity2 = entity;
                                                    Level level = entity2.level();

                                                    if (!level.isClientSide()) {
                                                        Projectile projectile = new Arrow(EntityType.ARROW, level);
                                                        {
                                                            projectile.setOwner(entity);
                                                            ((Arrow) projectile).setBaseDamage(4.0F);
                                                            ((Arrow) projectile).setKnockback(0);
                                                            ((Arrow) projectile).setPierceLevel((byte) 1);
                                                            projectile.setPos(entity2.getX(), entity2.getEyeY() - 0.1D, entity2.getZ());
                                                            projectile.shoot(entity2.getLookAngle().x, entity2.getLookAngle().y, entity2.getLookAngle().z, 4.0F, 0.0F);
                                                            level.addFreshEntity(projectile);
                                                        }

                                                        entity2 = entity;
                                                        if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                                            LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity2, LivingEntityPatch.class);
                                                            if (livingEntityPatch != null) {
                                                                livingEntityPatch.playAnimationSynchronized(Animations.BIPED_BOW_SHOT, 0.0F);
                                                            }
                                                        }

                                                        LevelAccessor levelaccessor1 = levelaccessor;

                                                        if (levelaccessor1 instanceof Level) {
                                                            Level level1 = (Level) levelaccessor1;

                                                            if (!level1.isClientSide()) {
                                                                level1.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.arrow.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                            } else {
                                                                level1.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.arrow.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                            }
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

                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    itemstack = livingentity.getMainHandItem();
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                if (itemstack.getItem() instanceof BowItem) {
                    new DelayedTask(40) {
                        @Override
                        public void run() {
                            if (entity instanceof LivingEntity) {
                                LivingEntity livingentity9 = (LivingEntity) entity;
                                ItemStack itemstack2 = new ItemStack((ItemLike) AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get());

                                itemstack2.setCount(1);
                                livingentity9.setItemInHand(InteractionHand.MAIN_HAND, itemstack2);
                                if (livingentity9 instanceof Player) {
                                    Player player1 = (Player) livingentity9;

                                    player1.getInventory().setChanged();
                                }
                            }
                        }
                    };
                }
            }

        }
    }
}
