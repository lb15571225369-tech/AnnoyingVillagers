package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Start;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class EatBlockProcedure {

    @SubscribeEvent
    public static void onUseItemStart(Start start) {
        if (start != null && start.getEntity() != null) {
            execute(start, start.getEntity(), start.getItem());
        }

    }

    public static void execute(Entity entity, ItemStack itemstack) {
        execute((Event) null, entity, itemstack);
    }

    private static void execute(@Nullable Event event, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (itemstack.getItem().isEdible()) {
                float f;

                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity) entity;

                    f = livingentity.getHealth();
                } else {
                    f = -1.0F;
                }

                float f1;

                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity1 = (LivingEntity) entity;

                    f1 = livingentity1.getMaxHealth();
                } else {
                    f1 = -1.0F;
                }

                if (f == f1) {
                    int i;

                    if (entity instanceof Player) {
                        Player player = (Player) entity;

                        i = player.getFoodData().getFoodLevel();
                    } else {
                        i = 0;
                    }

                    if (i == 20 && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, itemstack) == 0 && event != null && event.isCancelable()) {
                        event.setCanceled(true);
                    }
                }
            }

        }
    }
}
