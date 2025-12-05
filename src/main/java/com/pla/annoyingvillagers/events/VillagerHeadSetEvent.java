package com.pla.annoyingvillagers.events;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;

@EventBusSubscriber
public class VillagerHeadSetEvent {

    @SubscribeEvent
    public static void onRightClickBlock(RightClickBlock rightclickblock) {
        if (rightclickblock.getHand() == rightclickblock.getEntity().getUsedItemHand()) {
            execute(rightclickblock, rightclickblock.getLevel(), rightclickblock.getEntity());
        }
    }

    public static void execute(LevelAccessor levelaccessor, Entity entity) {
        execute((Event) null, levelaccessor, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, final Entity entity) {
        if (entity != null) {
            if (entity.isShiftKeyDown()) {
                ItemStack itemstack;

                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity)entity;

                    itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                if (itemstack.getItem() == AnnoyingVillagersModItems.VILLAGER_HEAD.get()) {
                    Player player;

                    if (!entity.getPersistentData().getBoolean("villager_head")) {
                        if (!entity.getPersistentData().getBoolean("villager_head_used")) {
                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                try {
                                    entity.getServer().getCommands().getDispatcher().execute(
                                            "team leave @s[team=villagers]",
                                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {
                                    
                                }
                            }

                            if (entity instanceof Player) {
                                player = (Player)entity;
                                if (!player.level().isClientSide()) {
                                    player.displayClientMessage(Component.literal("Switched to Attack Mode"), false);
                                }
                            }

                            entity.getPersistentData().putBoolean("villager_head_used", true);
                            new DelayedTask(200) {
                                @Override
                                public void run() {
                                    entity.getPersistentData().putBoolean("villager_head", true);
                                    entity.getPersistentData().putBoolean("villager_head_used", false);
                                }
                            };
                        } else if (entity instanceof Player) {
                            player = (Player)entity;
                            if (!player.level().isClientSide()) {
                                player.displayClientMessage(Component.literal("On Cooldown"), true);
                            }
                        }
                    } else if (entity.getPersistentData().getBoolean("villager_head")) {
                        if (!entity.getPersistentData().getBoolean("villager_head_used")) {
                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                try {
                                    entity.getServer().getCommands().getDispatcher().execute(
                                            "team join villagers @s",
                                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {
                                    
                                }
                            }

                            if (entity instanceof Player) {
                                player = (Player)entity;
                                if (!player.level().isClientSide()) {
                                    player.displayClientMessage(Component.literal("Switched to Disguise Mode"), false);
                                }
                            }

                            entity.getPersistentData().putBoolean("villager_head_used", true);
                            new DelayedTask(200) {
                                @Override
                                public void run() {
                                    entity.getPersistentData().putBoolean("villager_head", false);
                                    entity.getPersistentData().putBoolean("villager_head_used", false);
                                }
                            };

                        } else if (entity instanceof Player) {
                            player = (Player)entity;
                            if (!player.level().isClientSide()) {
                                player.displayClientMessage(Component.literal("On Cooldown"), true);
                            }
                        }
                    }
                }
            }

        }
    }
}
