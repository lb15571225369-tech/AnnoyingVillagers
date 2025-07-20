package com.pla.annoyingvillagers.procedures;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.util.CheckGameMode;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@EventBusSubscriber
public class BoomlitProcedure {

    @SubscribeEvent
    public static void onExplode(ExplosionEvent.Detonate detonate) throws CommandSyntaxException {
        execute(detonate, detonate.getLevel(), detonate.getExplosion().getPosition().x, detonate.getExplosion().getPosition().y, detonate.getExplosion().getPosition().z);
    }

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2) throws CommandSyntaxException {
        execute((Event) null, levelaccessor, d0, d1, d2);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, double d0, double d1, double d2) throws CommandSyntaxException {
        ServerLevel serverlevel;

        if (levelaccessor instanceof ServerLevel) {
            serverlevel = (ServerLevel)levelaccessor;
            serverlevel.getServer().getCommands().getDispatcher().execute("particle minecraft:campfire_signal_smoke ~ ~ ~ 0 0 0 0.02 100", (new CommandSourceStack(CommandSource.NULL, new Vec3(d0, d1, d2), Vec2.ZERO, serverlevel, 4, "", Component.literal(""), serverlevel.getServer(), (Entity)null)).withSuppressedOutput());
        }

        if (levelaccessor instanceof ServerLevel) {
            serverlevel = (ServerLevel)levelaccessor;
            serverlevel.getServer().getCommands().getDispatcher().execute("particle epicfight:air_burst ^ ^1.5 ^ 0 0 0 10 1", (new CommandSourceStack(CommandSource.NULL, new Vec3(d0, d1, d2), Vec2.ZERO, serverlevel, 4, "", Component.literal(""), serverlevel.getServer(), (Entity)null)).withSuppressedOutput());
        }

        if (levelaccessor instanceof ServerLevel) {
            serverlevel = (ServerLevel)levelaccessor;
            serverlevel.getServer().getCommands().getDispatcher().execute("particle epicfight:air_burst ~ ~1.5 ~ 0 0 0 10 1", (new CommandSourceStack(CommandSource.NULL, new Vec3(d0, d1, d2), Vec2.ZERO, serverlevel, 4, "", Component.literal(""), serverlevel.getServer(), (Entity)null)).withSuppressedOutput());
        }

        if (!levelaccessor.isEmptyBlock(new BlockPos(d0, d1 - 1.0D, d2)) && levelaccessor instanceof ServerLevel) {
            serverlevel = (ServerLevel)levelaccessor;
            serverlevel.getServer().getCommands().getDispatcher().execute("particle epicfight:ground_slam ~ ~-3 ~ 0 0 0 2 90", (new CommandSourceStack(CommandSource.NULL, new Vec3(d0, d1, d2), Vec2.ZERO, serverlevel, 4, "", Component.literal(""), serverlevel.getServer(), (Entity)null)).withSuppressedOutput());
        }

        Vec3 vec3 = new Vec3(d0, d1, d2);
        Vec3 finalVec = vec3;
        List<Entity> list = (List)levelaccessor.getEntitiesOfClass(Entity.class, (new AABB(vec3, vec3)).inflate(3.0D), (entity) -> {
            return true;
        }).stream().sorted(Comparator.comparingDouble((entity) -> {
            return entity.distanceToSqr(finalVec);
        })).collect(Collectors.toList());
        Iterator iterator = list.iterator();

        Entity entity;

        while(iterator.hasNext()) {
            entity = (Entity)iterator.next();
            if (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString().equals("minecraft:player")) {
                if (!entity.isPassenger()) {
                    LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

                    if (livingentitypatch != null) {
                        DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();

                        if (dynamicanimation != AVAnimations.HARD_GREAT_SWORD_GUARD_SKILL) {
                            entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * (double)Math.max(-1, -3), 1.0D, entity.getLookAngle().z * (double)Math.max(-1, -3)));
                            Enchantment enchantment = Enchantments.BLAST_PROTECTION;
                            LivingEntity livingentity;
                            ItemStack itemstack;

                            if (entity instanceof LivingEntity) {
                                livingentity = (LivingEntity)entity;
                                itemstack = livingentity.getItemBySlot(EquipmentSlot.FEET);
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            LivingEntity livingentity1;
                            ItemStack itemstack1;
                            ItemStack itemstack2;

                            if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack) == 0) {
                                if (entity instanceof LivingEntity) {
                                    livingentity1 = (LivingEntity)entity;
                                    itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.FEET);
                                } else {
                                    itemstack1 = ItemStack.EMPTY;
                                }

                                itemstack2 = itemstack1;
                                if (itemstack2.hurt(300, (RandomSource) new Random(), (ServerPlayer)null)) {
                                    itemstack2.shrink(1);
                                    itemstack2.setDamageValue(0);
                                }
                            }

                            enchantment = Enchantments.BLAST_PROTECTION;
                            if (entity instanceof LivingEntity) {
                                livingentity = (LivingEntity)entity;
                                itemstack = livingentity.getItemBySlot(EquipmentSlot.LEGS);
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack) == 0) {
                                if (entity instanceof LivingEntity) {
                                    livingentity1 = (LivingEntity)entity;
                                    itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.LEGS);
                                } else {
                                    itemstack1 = ItemStack.EMPTY;
                                }

                                itemstack2 = itemstack1;
                                if (itemstack2.hurt(300, (RandomSource) new Random(), (ServerPlayer)null)) {
                                    itemstack2.shrink(1);
                                    itemstack2.setDamageValue(0);
                                }
                            }

                            enchantment = Enchantments.BLAST_PROTECTION;
                            if (entity instanceof LivingEntity) {
                                livingentity = (LivingEntity)entity;
                                itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack) == 0) {
                                if (entity instanceof LivingEntity) {
                                    livingentity1 = (LivingEntity)entity;
                                    itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.CHEST);
                                } else {
                                    itemstack1 = ItemStack.EMPTY;
                                }

                                itemstack2 = itemstack1;
                                if (itemstack2.hurt(300, (RandomSource) new Random(), (ServerPlayer)null)) {
                                    itemstack2.shrink(1);
                                    itemstack2.setDamageValue(0);
                                }
                            }

                            enchantment = Enchantments.BLAST_PROTECTION;
                            if (entity instanceof LivingEntity) {
                                livingentity = (LivingEntity)entity;
                                itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack) == 0) {
                                if (entity instanceof LivingEntity) {
                                    livingentity1 = (LivingEntity)entity;
                                    itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.HEAD);
                                } else {
                                    itemstack1 = ItemStack.EMPTY;
                                }

                                itemstack2 = itemstack1;
                                if (itemstack2.hurt(300, (RandomSource) new Random(), (ServerPlayer)null)) {
                                    itemstack2.shrink(1);
                                    itemstack2.setDamageValue(0);
                                }
                            }
                        }
                    }
                }
            }

            float f;

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity2 = (LivingEntity)entity;

                f = livingentity2.getMaxHealth();
            } else {
                f = -1.0F;
            }

            if (f <= 30.0F && entity.isAlive() && !CheckGameMode.isSpectatorGamemode(entity)) {
                LivingEntityPatch<?> livingentitypatch1 = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

                if (livingentitypatch1 != null) {
                    DynamicAnimation dynamicanimation1 = livingentitypatch1.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();

                    if (dynamicanimation1 != AVAnimations.HARD_GREAT_SWORD_GUARD_SKILL) {
                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                            entity.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/combat/knockdown\" 0 10", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        }

                        if (entity.getPersistentData().getBoolean("a_player") && entity instanceof LivingEntity) {
                            LivingEntity livingentity3 = (LivingEntity)entity;

                            if (!livingentity3.level.isClientSide()) {
                                livingentity3.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.NPC_KICK_EFFECT.get(), 10, 0, false, false));
                            }
                        }
                    }
                }
            }
        }

        vec3 = new Vec3(d0, d1, d2);
        Vec3 finalVec1 = vec3;
        list = (List)levelaccessor.getEntitiesOfClass(Entity.class, (new AABB(vec3, vec3)).inflate(10.0D), (entity1) -> {
            return true;
        }).stream().sorted(Comparator.comparingDouble((entity1) -> {
            return entity1.distanceToSqr(finalVec1);
        })).collect(Collectors.toList());
        iterator = list.iterator();

        while(iterator.hasNext()) {
            entity = (Entity)iterator.next();
            if (entity instanceof Player && !entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().getDispatcher().execute("impactful @s shake 40 6 6", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            }
        }

    }
}
