package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Finish;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class GoldenAppleEatProcedure {

    @SubscribeEvent
    public static void onUseItemFinish(Finish finish) {
        if (finish != null && finish.getEntity() != null) {
            execute(finish, finish.getEntity(), finish.getItem());
        }

    }

    public static void execute(Entity entity, ItemStack itemstack) {
        execute((Event) null, entity, itemstack);
    }

    private static void execute(@Nullable Event event, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            LivingEntity livingentity;

            if (itemstack.getItem() == Items.ENCHANTED_GOLDEN_APPLE && entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                if (livingentity.hasEffect(MobEffects.ABSORPTION)) {
                    LivingEntity livingentity1;

                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity) entity;
                        livingentity1.removeEffect(MobEffects.ABSORPTION);
                    }

                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity) entity;
                        if (!livingentity1.level.isClientSide()) {
                            livingentity1.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 80, 2, false, false));
                        }
                    }

                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity) entity;
                        if (!livingentity1.level.isClientSide()) {
                            livingentity1.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, 1, false, false));
                        }
                    }
                }
            }

            if (itemstack.getItem().isEdible() && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, itemstack) != 0) {
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    if (!livingentity.level.isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 80, EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, itemstack), false, false));
                    }
                }

                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    if (!livingentity.level.isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 60, EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, itemstack), false, false));
                    }
                }
            }

        }
    }
}
