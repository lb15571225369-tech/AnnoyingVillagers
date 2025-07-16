package com.pla.annoyingvillagers.procedures;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEnchantments;

public class LegendarySwordWhenInInventoryProcedure {

    public static void execute(Entity entity, ItemStack itemstack) {
        if (entity != null) {
            LivingEntity livingentity;
            boolean flag;

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                flag = livingentity.isHolding(itemstack.getItem());
            } else {
                flag = false;
            }

            Player player;
            int i;

            if (!flag) {
                if (entity instanceof Player) {
                    player = (Player) entity;
                    i = player.getFoodData().getFoodLevel();
                } else {
                    i = 0;
                }

                if (i <= 5 && entity instanceof LivingEntity) {
                    LivingEntity livingentity1 = (LivingEntity) entity;

                    if (!livingentity1.level.isClientSide()) {
                        livingentity1.addEffect(new MobEffectInstance(MobEffects.SATURATION, 15, 0, false, false));
                    }
                }
            }

            if (!itemstack.getOrCreateTag().getBoolean("enchanted")) {
                itemstack.getOrCreateTag().putBoolean("enchanted", true);
                itemstack.enchant(Enchantments.SHARPNESS, 10);
                itemstack.enchant(Enchantments.SMITE, 8);
                itemstack.enchant(Enchantments.KNOCKBACK, 9);
                itemstack.enchant(Enchantments.UNBREAKING, 7);
                itemstack.enchant(Enchantments.MENDING, 5);
                itemstack.enchant((Enchantment) AnnoyingVillagersModEnchantments.ENERGY.get(), 4);
                itemstack.enchant((Enchantment) AnnoyingVillagersModEnchantments.HEAVY_ATTACK.get(), 5);
                itemstack.enchant(Enchantments.PROJECTILE_PROTECTION, 7);
                itemstack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 10);
                itemstack.enchant((Enchantment) AnnoyingVillagersModEnchantments.QUICK_DRAW.get(), 10);
                itemstack.enchant((Enchantment) AnnoyingVillagersModEnchantments.POSSESSION.get(), 10);
                itemstack.enchant((Enchantment) AnnoyingVillagersModEnchantments.HOLY_BLEESSING.get(), 10);
                itemstack.enchant((Enchantment) AnnoyingVillagersModEnchantments.EXPERIENCE.get(), 0);
                itemstack.enchant((Enchantment) AnnoyingVillagersModEnchantments.LEARNING.get(), 0);
                itemstack.enchant((Enchantment) AnnoyingVillagersModEnchantments.UNKNOWN.get(), 0);
                itemstack.enchant((Enchantment) AnnoyingVillagersModEnchantments.ERROR.get(), 0);
                itemstack.getOrCreateTag().putString("l_g_ower", entity.getUUID().toString());
            }

            Player player1;

            if (!entity.getPersistentData().getBoolean("warn")) {
                entity.getPersistentData().putBoolean("warn", true);
                if (itemstack.getOrCreateTag().getString("l_g_ower").equals(entity.getUUID().toString())) {
                    if (entity instanceof Player) {
                        player1 = (Player) entity;
                        if (!player1.level.isClientSide()) {
                            player1.displayClientMessage(new TextComponent("\u60a8\u662f\u6b64\u6b66\u5668\u7684\u4e3b\u4eba"), false);
                        }
                    }
                } else if (entity instanceof Player) {
                    player1 = (Player) entity;
                    if (!player1.level.isClientSide()) {
                        player1.displayClientMessage(new TextComponent("\u4f60\u4e0d\u662f\u6b64\u6b66\u5668\u7684\u4e3b\u4eba\uff0c\u6b64\u6b66\u5668\u5c06\u4e0d\u53d7\u4f60\u7684\u63a7\u5236"), false);
                    }
                }
            }

            if (!itemstack.getOrCreateTag().getString("l_g_ower").equals(entity.getUUID().toString())) {
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    if (!livingentity.level.isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 5, 3, false, false));
                    }
                }

                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    if (!livingentity.level.isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 3, false, false));
                    }
                }

                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    if (!livingentity.level.isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 5, 3, false, false));
                    }
                }

                if (entity instanceof Player) {
                    player1 = (Player) entity;
                    i = player1.getFoodData().getFoodLevel();
                } else {
                    i = 0;
                }

                if (i <= 5) {
                    if (entity instanceof Player) {
                        player = (Player) entity;
                        if (!player.level.isClientSide()) {
                            player.displayClientMessage(new TextComponent("\u4f60\u6ca1\u6709\u80fd\u529b\u9a7e\u9a6d\u8fd9\u4e2a\u6b66\u5668"), true);
                        }
                    }

                    if (entity instanceof Player) {
                        player = (Player) entity;
                        if (!player.level.isClientSide()) {
                            player.displayClientMessage(new TextComponent("\u4f60\u6ca1\u6709\u80fd\u529b\u9a7e\u9a6d\u8fd9\u4e2a\u6b66\u5668"), false);
                        }
                    }

                    if (entity instanceof LivingEntity) {
                        LivingEntity livingentity2 = (LivingEntity) entity;

                        if (!livingentity2.level.isClientSide()) {
                            livingentity2.addEffect(new MobEffectInstance(MobEffects.WITHER, 160, 3, false, false));
                        }
                    }
                }
            }

        }
    }
}
