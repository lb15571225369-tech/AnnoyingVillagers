package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Herobrine1OnEntityTickUpdateProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity) {
        if (entity != null) {
            Vec3 vec3 = new Vec3(d0, d1, d2);
            List<Entity> list = (List)levelaccessor.getEntitiesOfClass(Entity.class, (new AABB(vec3, vec3)).inflate(16.0D), (entity1) -> {
                return true;
            }).stream().sorted(Comparator.comparingDouble((entity1) -> {
                return entity1.distanceToSqr(vec3);
            })).collect(Collectors.toList());
            Iterator iterator = list.iterator();

            while(iterator.hasNext()) {
                Entity entity1 = (Entity)iterator.next();
                LivingEntity livingentity;

                if (entity instanceof Mob) {
                    Mob mob = (Mob)entity;

                    livingentity = mob.getTarget();
                } else {
                    livingentity = null;
                }

                if (entity1 == livingentity) {
                    if (!entity1.level().isClientSide() && entity1.getServer() != null) {
                    }
                } else {
                    LivingEntity livingentity1;
                    ItemStack itemstack;
                    Player player;

                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity)entity;
                        itemstack = new ItemStack(Blocks.AIR);
                        itemstack.setCount(1);
                        livingentity1.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                        if (livingentity1 instanceof Player) {
                            player = (Player)livingentity1;
                            player.getInventory().setChanged();
                        }
                    }

                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity)entity;
                        itemstack = new ItemStack(Blocks.AIR);
                        itemstack.setCount(1);
                        livingentity1.setItemInHand(InteractionHand.OFF_HAND, itemstack);
                        if (livingentity1 instanceof Player) {
                            player = (Player)livingentity1;
                            player.getInventory().setChanged();
                        }
                    }
                }
            }

            if (entity.isPassenger()) {
                entity.stopRiding();
            }

            LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

            if (livingentitypatch != null) {
                final DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();

                if (!(dynamicanimation instanceof AttackAnimation) && !(dynamicanimation instanceof LongHitAnimation) && !(dynamicanimation instanceof HitAnimation)) {
                    if (dynamicanimation instanceof KnockdownAnimation) {
                        new DelayedTask(10) {
                            @Override
                            public void run() {
                                if (dynamicanimation instanceof KnockdownAnimation) {
                                    Entity entity2 = entity;

                                    if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                        try {
                                            entity2.getServer().getCommands().getDispatcher().execute(
                                                    "indestructible @s play \"epicfight:biped/skill/knockdown_wakeup_left\" 0 1",
                                                    entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                        } catch (CommandSyntaxException e) {
                                            
                                        }
                                    }
                                }
                            }
                        };
                    }
                } else {
                    entity.clearFire();
                }
            }

        }
    }
}
