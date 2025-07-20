package com.pla.annoyingvillagers.procedures;

import java.util.Random;
import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

import static net.minecraft.world.item.enchantment.Enchantments.PROJECTILE_PROTECTION;

@EventBusSubscriber
public class ProjectiveProcedure {

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent livingattackevent) throws CommandSyntaxException {
        if (livingattackevent != null && livingattackevent.getEntity() != null) {
            execute(livingattackevent, livingattackevent.getEntity().level, livingattackevent.getEntity().getX(), livingattackevent.getEntity().getY(), livingattackevent.getEntity().getZ(), livingattackevent.getEntity(), livingattackevent.getSource().getDirectEntity(), (double) livingattackevent.getAmount());
        }

    }

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity, Entity entity1, double d3) throws CommandSyntaxException {
        execute((Event) null, levelaccessor, d0, d1, d2, entity, entity1, d3);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity, Entity entity1, double d3) throws CommandSyntaxException {
        if (entity != null && entity1 != null) {
            double d4;

            if (entity1 instanceof Projectile) {
                Projectile projectile = (Projectile)entity1;

                d4 = projectile.getDeltaMovement().length();
            } else {
                d4 = 0.0D;
            }

            if (d4 != 0.0D) {
                Enchantment enchantment = PROJECTILE_PROTECTION;
                ItemStack itemstack;

                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity)entity;

                    itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                LivingEntity livingentity1;
                ItemStack itemstack1;
                ItemStack itemstack2;

                if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack) != 0) {
                    if (event != null && event.isCancelable()) {
                        event.setCanceled(true);
                    }

                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity)entity;
                        itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.HEAD);
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    itemstack2 = itemstack1;
                    Enchantment enchantment1 = PROJECTILE_PROTECTION;
                    ItemStack itemstack3;

                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity)entity;
                        itemstack3 = livingentity1.getItemBySlot(EquipmentSlot.HEAD);
                    } else {
                        itemstack3 = ItemStack.EMPTY;
                    }

                    if (itemstack2.hurt((int)(d3 / (double)EnchantmentHelper.getItemEnchantmentLevel(enchantment1, itemstack3)), (RandomSource) new Random(), (ServerPlayer)null)) {
                        itemstack2.shrink(1);
                        itemstack2.setDamageValue(0);
                    }

                    if (levelaccessor instanceof Level) {
                        Level level = (Level)levelaccessor;

                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "target_block_hit")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "target_block_hit")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                        }
                    }

                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                        entity1.getServer().getCommands().getDispatcher().execute(
                                "execute at @s run particle annoyingvillagers:spark ~ ~ ~ 0 0 0 0.1 10",
                                entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    }
                } else {
                    enchantment = PROJECTILE_PROTECTION;
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingentity2 = (LivingEntity)entity;

                        itemstack = livingentity2.getItemBySlot(EquipmentSlot.CHEST);
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    LivingEntity livingentity3 = (LivingEntity)entity;;
                    MobEffectInstance mobeffectinstance;
                    MobEffect mobeffect;
                    Enchantment enchantment2;
                    ItemStack itemstack4;

                    if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack) != 0) {
                        if (entity instanceof LivingEntity && !livingentity3.level.isClientSide()) {
                            ItemStack chestItem = livingentity3.getItemBySlot(EquipmentSlot.CHEST);
                            int level = net.minecraft.world.item.enchantment.EnchantmentHelper.getItemEnchantmentLevel(
                                    PROJECTILE_PROTECTION, chestItem
                            );

                            MobEffectInstance resistanceEffect = new MobEffectInstance(
                                    net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE,
                                    3,
                                    level,
                                    false,
                                    false
                            );

                            livingentity3.addEffect(resistanceEffect);
                        }

                        if (entity instanceof LivingEntity) {
                            livingentity1 = (LivingEntity)entity;
                            itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.CHEST);
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        itemstack2 = itemstack1;
                        if (itemstack2.hurt((int)d3, (RandomSource) new Random(), (ServerPlayer)null)) {
                            itemstack2.shrink(1);
                            itemstack2.setDamageValue(0);
                        }
                    } else {
                        enchantment = PROJECTILE_PROTECTION;
                        if (entity instanceof LivingEntity) {
                            LivingEntity livingentity4 = (LivingEntity)entity;

                            itemstack = livingentity4.getItemBySlot(EquipmentSlot.LEGS);
                        } else {
                            itemstack = ItemStack.EMPTY;
                        }

                        if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack) != 0) {
                            if (entity instanceof LivingEntity && !livingentity3.level.isClientSide()) {
                                ItemStack legArmor = livingentity3.getItemBySlot(EquipmentSlot.LEGS);
                                int enchantLevel = net.minecraft.world.item.enchantment.EnchantmentHelper.getItemEnchantmentLevel(
                                        net.minecraft.world.item.enchantment.Enchantments.PROJECTILE_PROTECTION,
                                        legArmor
                                );

                                MobEffectInstance effect = new MobEffectInstance(
                                        net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE,
                                        3,
                                        enchantLevel,
                                        false,
                                        false
                                );

                                livingentity3.addEffect(effect);
                            }


                            if (entity instanceof LivingEntity) {
                                livingentity1 = (LivingEntity)entity;
                                itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.LEGS);
                            } else {
                                itemstack1 = ItemStack.EMPTY;
                            }

                            itemstack2 = itemstack1;
                            if (itemstack2.hurt((int)d3, (RandomSource) new Random(), (ServerPlayer)null)) {
                                itemstack2.shrink(1);
                                itemstack2.setDamageValue(0);
                            }
                        } else {
                            enchantment = PROJECTILE_PROTECTION;
                            if (entity instanceof LivingEntity) {
                                LivingEntity livingentity5 = (LivingEntity)entity;

                                itemstack = livingentity5.getItemBySlot(EquipmentSlot.FEET);
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack) != 0) {
                                if (entity instanceof LivingEntity livingEntity && !livingEntity.level.isClientSide()) {
                                    ItemStack boots = livingEntity.getItemBySlot(EquipmentSlot.FEET);
                                    int enchantLevel = net.minecraft.world.item.enchantment.EnchantmentHelper.getItemEnchantmentLevel(
                                            net.minecraft.world.item.enchantment.Enchantments.PROJECTILE_PROTECTION,
                                            boots
                                    );

                                    MobEffectInstance effect = new MobEffectInstance(
                                            net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE,
                                            3,
                                            enchantLevel,
                                            false,
                                            false
                                    );

                                    livingEntity.addEffect(effect);
                                }

                                if (entity instanceof LivingEntity) {
                                    livingentity1 = (LivingEntity)entity;
                                    itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.FEET);
                                } else {
                                    itemstack1 = ItemStack.EMPTY;
                                }

                                itemstack2 = itemstack1;
                                if (itemstack2.hurt((int)d3, (RandomSource) new Random(), (ServerPlayer)null)) {
                                    itemstack2.shrink(1);
                                    itemstack2.setDamageValue(0);
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
