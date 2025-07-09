package com.pla.annoyingvillagers.procedures;

import java.util.ArrayList;
import java.util.Iterator;

import com.pla.annoyingvillagers.util.CheckGameMode;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class HerobrineShiTiChuShiShengChengShiProcedure {

    public static void execute(LevelAccessor levelaccessor, final Entity entity) {
        if (entity != null) {
            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("Herobrine\u5df2\u8bde\u751f\u65b0\u7684\u9644\u8eab\u4f53"), ChatType.SYSTEM, Util.NIL_UUID);
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "fill ~-2 ~ ~-2 ~2 ~3 ~2 minecraft:air");
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "team add herobrinexintu");
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "team join herobrinexintu @s");
            }
            new DelayedTask(1) {
                @Override
                public void run() {
                    if (entity.isVehicle()) {
                        Iterator iterator = (new ArrayList(entity.getPassengers())).iterator();

                        while(iterator.hasNext()) {
                            Entity entity1 = (Entity)iterator.next();

                            if (ForgeRegistries.ENTITIES.getKey(entity1.getType()).toString().equals("minecraft:player") && CheckGameMode.isSpectatorGamemode(entity1)) {
                                Entity entity2 = entity;
                                Player player;
                                NonNullList nonnulllist;
                                LivingEntity livingentity;
                                ItemStack itemstack;
                                LivingEntity livingentity1;
                                EquipmentSlot equipmentslot;

                                if (entity2 instanceof Player) {
                                    player = (Player)entity2;
                                    nonnulllist = player.getInventory().armor;
                                    if (entity1 instanceof LivingEntity) {
                                        livingentity = (LivingEntity)entity1;
                                        itemstack = livingentity.getItemBySlot(EquipmentSlot.FEET);
                                    } else {
                                        itemstack = ItemStack.EMPTY;
                                    }

                                    nonnulllist.set(0, itemstack);
                                    player.getInventory().setChanged();
                                } else if (entity2 instanceof LivingEntity) {
                                    livingentity1 = (LivingEntity)entity2;
                                    equipmentslot = EquipmentSlot.FEET;
                                    if (entity1 instanceof LivingEntity) {
                                        livingentity = (LivingEntity)entity1;
                                        itemstack = livingentity.getItemBySlot(EquipmentSlot.FEET);
                                    } else {
                                        itemstack = ItemStack.EMPTY;
                                    }

                                    livingentity1.setItemSlot(equipmentslot, itemstack);
                                }

                                entity2 = entity;
                                if (entity2 instanceof Player) {
                                    player = (Player)entity2;
                                    nonnulllist = player.getInventory().armor;
                                    if (entity1 instanceof LivingEntity) {
                                        livingentity = (LivingEntity)entity1;
                                        itemstack = livingentity.getItemBySlot(EquipmentSlot.LEGS);
                                    } else {
                                        itemstack = ItemStack.EMPTY;
                                    }

                                    nonnulllist.set(1, itemstack);
                                    player.getInventory().setChanged();
                                } else if (entity2 instanceof LivingEntity) {
                                    livingentity1 = (LivingEntity)entity2;
                                    equipmentslot = EquipmentSlot.LEGS;
                                    if (entity1 instanceof LivingEntity) {
                                        livingentity = (LivingEntity)entity1;
                                        itemstack = livingentity.getItemBySlot(EquipmentSlot.LEGS);
                                    } else {
                                        itemstack = ItemStack.EMPTY;
                                    }

                                    livingentity1.setItemSlot(equipmentslot, itemstack);
                                }

                                entity2 = entity;
                                if (entity2 instanceof Player) {
                                    player = (Player)entity2;
                                    nonnulllist = player.getInventory().armor;
                                    if (entity1 instanceof LivingEntity) {
                                        livingentity = (LivingEntity)entity1;
                                        itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
                                    } else {
                                        itemstack = ItemStack.EMPTY;
                                    }

                                    nonnulllist.set(2, itemstack);
                                    player.getInventory().setChanged();
                                } else if (entity2 instanceof LivingEntity) {
                                    livingentity1 = (LivingEntity)entity2;
                                    equipmentslot = EquipmentSlot.CHEST;
                                    if (entity1 instanceof LivingEntity) {
                                        livingentity = (LivingEntity)entity1;
                                        itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
                                    } else {
                                        itemstack = ItemStack.EMPTY;
                                    }

                                    livingentity1.setItemSlot(equipmentslot, itemstack);
                                }

                                entity2 = entity;
                                if (entity2 instanceof Player) {
                                    player = (Player)entity2;
                                    nonnulllist = player.getInventory().armor;
                                    if (entity1 instanceof LivingEntity) {
                                        livingentity = (LivingEntity)entity1;
                                        itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
                                    } else {
                                        itemstack = ItemStack.EMPTY;
                                    }

                                    nonnulllist.set(3, itemstack);
                                    player.getInventory().setChanged();
                                } else if (entity2 instanceof LivingEntity) {
                                    livingentity1 = (LivingEntity)entity2;
                                    equipmentslot = EquipmentSlot.HEAD;
                                    if (entity1 instanceof LivingEntity) {
                                        livingentity = (LivingEntity)entity1;
                                        itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
                                    } else {
                                        itemstack = ItemStack.EMPTY;
                                    }

                                    livingentity1.setItemSlot(equipmentslot, itemstack);
                                }
                            }
                        }
                    }
                }
            };
        }
    }
}
