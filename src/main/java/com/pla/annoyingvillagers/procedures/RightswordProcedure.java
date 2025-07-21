package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class RightswordProcedure {

    @SubscribeEvent
    public static void onRightClickItem(RightClickItem rightclickitem) {
        if (rightclickitem.getHand() == rightclickitem.getEntity().getUsedItemHand()) {
            execute(rightclickitem, rightclickitem.getEntity());
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

            if (itemstack.getItem() instanceof SwordItem) {
                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "epicfight mode battle @s",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                        
                    }
                }
            } else {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity1 = (LivingEntity) entity;

                    itemstack = livingentity1.getMainHandItem();
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                if (itemstack.getItem() instanceof AxeItem) {
                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        try {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "epicfight mode battle @s",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                            
                        }
                    }
                } else {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingentity2 = (LivingEntity) entity;

                        itemstack = livingentity2.getMainHandItem();
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    if (itemstack.getItem() instanceof TridentItem) {
                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                            try {
                                entity.getServer().getCommands().getDispatcher().execute(
                                        "epicfight mode battle @s",
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {
                                
                            }
                        }
                    } else {
                        if (entity instanceof LivingEntity) {
                            LivingEntity livingentity3 = (LivingEntity) entity;

                            itemstack = livingentity3.getMainHandItem();
                        } else {
                            itemstack = ItemStack.EMPTY;
                        }

                        if (itemstack.getItem() instanceof PickaxeItem && !entity.level.isClientSide() && entity.getServer() != null) {
                            try {
                                entity.getServer().getCommands().getDispatcher().execute(
                                        "epicfight mode battle @s",
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {
                                
                            }
                        }
                    }
                }
            }

        }
    }
}
