package com.pla.annoyingvillagers.procedures;

import java.util.ArrayList;
import java.util.Iterator;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.util.CheckGameMode;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class Herobrine1OnInitialSpawnProcedure {

    public static void execute(LevelAccessor levelaccessor, final Entity entity) {
        if (entity != null) {
            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                String killedName = entity.getPersistentData().getString("killed_name");
                if (!killedName.isEmpty()) {
                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal(killedName + " has been possessed by §5Herobrine§r."), false);
                } else {
                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("§5Herobrine§r has spawned a new possessed body."), false);
                }
            }

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "fill ~-2 ~ ~-2 ~2 ~3 ~2 minecraft:air",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                    
                }
            }

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team add herobrine",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                    
                }
            }

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team join herobrine @s",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                    
                }
            }
            new DelayedTask(1) {
                @Override
                public void run() {
                    if (entity.isVehicle()) {
                        Iterator iterator = (new ArrayList(entity.getPassengers())).iterator();

                        while(iterator.hasNext()) {
                            Entity entity1 = (Entity)iterator.next();

                            if (ForgeRegistries.ENTITY_TYPES.getKey(entity1.getType()).toString().equals("minecraft:player") && CheckGameMode.isSpectatorGamemode(entity1)) {
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
