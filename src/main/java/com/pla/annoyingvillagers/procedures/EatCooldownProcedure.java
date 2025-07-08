package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Finish;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class EatCooldownProcedure {

    @SubscribeEvent
    public static void onUseItemFinish(Finish finish) {
        if (finish != null && finish.getEntity() != null) {
            execute(finish, finish.getEntity());
        }

    }

    public static void execute(Entity entity) {
        execute((Event) null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity != null) {
            ItemStack itemstack;

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                itemstack = livingentity.getMainHandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            IForgeRegistry iforgeregistry;
            LivingEntity livingentity1;
            ItemStack itemstack1;
            Player player;
            ItemCooldowns itemcooldowns;
            LivingEntity livingentity2;
            float f;

            if (itemstack.getItem().isEdible()) {
                iforgeregistry = ForgeRegistries.ITEMS;
                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack1 = livingentity1.getMainHandItem();
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                if (iforgeregistry.getKey(itemstack1.getItem()).toString().equals("annoying_villagersbychentu:yyumijuan") && entity instanceof Player) {
                    player = (Player) entity;
                    itemcooldowns = player.getCooldowns();
                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity) entity;
                        itemstack1 = livingentity2.getMainHandItem();
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    itemcooldowns.addCooldown(itemstack1.getItem(), 1000);
                }

                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    f = livingentity1.getHealth();
                } else {
                    f = -1.0F;
                }

                if (f >= 20.0F && entity instanceof Player) {
                    player = (Player) entity;
                    itemcooldowns = player.getCooldowns();
                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity) entity;
                        itemstack1 = livingentity2.getMainHandItem();
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    itemcooldowns.addCooldown(itemstack1.getItem(), 300);
                }
            } else {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity3 = (LivingEntity) entity;

                    itemstack = livingentity3.getOffhandItem();
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                if (itemstack.getItem().isEdible()) {
                    iforgeregistry = ForgeRegistries.ITEMS;
                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity) entity;
                        itemstack1 = livingentity1.getOffhandItem();
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    if (iforgeregistry.getKey(itemstack1.getItem()).toString().equals("annoying_villagersbychentu:yyumijuan") && entity instanceof Player) {
                        player = (Player) entity;
                        itemcooldowns = player.getCooldowns();
                        if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity) entity;
                            itemstack1 = livingentity2.getOffhandItem();
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        itemcooldowns.addCooldown(itemstack1.getItem(), 500);
                    }

                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity) entity;
                        f = livingentity1.getHealth();
                    } else {
                        f = -1.0F;
                    }

                    if (f == 20.0F && entity instanceof Player) {
                        player = (Player) entity;
                        itemcooldowns = player.getCooldowns();
                        if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity) entity;
                            itemstack1 = livingentity2.getOffhandItem();
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        itemcooldowns.addCooldown(itemstack1.getItem(), 200);
                    }
                }
            }

        }
    }
}
