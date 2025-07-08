package com.pla.annoyingvillagers.procedures;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;

public class HerobrineDangShiTiSiWangShiProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, Entity entity) {
        if (entity != null) {
            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "tag @a remove aim");
            }

            if (entity.isVehicle() && ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("minecraft:player")) {
                Iterator iterator = (new ArrayList(entity.getPassengers())).iterator();

                while(iterator.hasNext()) {
                    Entity entity1 = (Entity)iterator.next();

                    if (((<undefinedtype>)(new Object() {
                        public boolean checkGamemode(Entity entity2) {
                            if (entity2 instanceof ServerPlayer) {
                                ServerPlayer serverplayer = (ServerPlayer)entity2;

                                return serverplayer.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
                            } else if (entity2.level.isClientSide() && entity2 instanceof Player) {
                                Player player = (Player)entity2;

                                return Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId()).getGameMode() == GameType.SPECTATOR;
                            } else {
                                return false;
                            }
                        }
                    })).checkGamemode(entity1)) {
                        if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                            entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "tag @s remove sp");
                        }

                        Player player;
                        NonNullList nonnulllist;
                        LivingEntity livingentity;
                        ItemStack itemstack;
                        LivingEntity livingentity1;
                        EquipmentSlot equipmentslot;

                        if (entity1 instanceof Player) {
                            player = (Player)entity1;
                            nonnulllist = player.getInventory().armor;
                            if (entity instanceof LivingEntity) {
                                livingentity = (LivingEntity)entity;
                                itemstack = livingentity.getItemBySlot(EquipmentSlot.FEET);
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            nonnulllist.set(0, itemstack);
                            player.getInventory().setChanged();
                        } else if (entity1 instanceof LivingEntity) {
                            livingentity1 = (LivingEntity)entity1;
                            equipmentslot = EquipmentSlot.FEET;
                            if (entity instanceof LivingEntity) {
                                livingentity = (LivingEntity)entity;
                                itemstack = livingentity.getItemBySlot(EquipmentSlot.FEET);
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            livingentity1.setItemSlot(equipmentslot, itemstack);
                        }

                        if (entity1 instanceof Player) {
                            player = (Player)entity1;
                            nonnulllist = player.getInventory().armor;
                            if (entity instanceof LivingEntity) {
                                livingentity = (LivingEntity)entity;
                                itemstack = livingentity.getItemBySlot(EquipmentSlot.LEGS);
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            nonnulllist.set(1, itemstack);
                            player.getInventory().setChanged();
                        } else if (entity1 instanceof LivingEntity) {
                            livingentity1 = (LivingEntity)entity1;
                            equipmentslot = EquipmentSlot.LEGS;
                            if (entity instanceof LivingEntity) {
                                livingentity = (LivingEntity)entity;
                                itemstack = livingentity.getItemBySlot(EquipmentSlot.LEGS);
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            livingentity1.setItemSlot(equipmentslot, itemstack);
                        }

                        if (entity1 instanceof Player) {
                            player = (Player)entity1;
                            nonnulllist = player.getInventory().armor;
                            if (entity instanceof LivingEntity) {
                                livingentity = (LivingEntity)entity;
                                itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            nonnulllist.set(2, itemstack);
                            player.getInventory().setChanged();
                        } else if (entity1 instanceof LivingEntity) {
                            livingentity1 = (LivingEntity)entity1;
                            equipmentslot = EquipmentSlot.CHEST;
                            if (entity instanceof LivingEntity) {
                                livingentity = (LivingEntity)entity;
                                itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            livingentity1.setItemSlot(equipmentslot, itemstack);
                        }

                        if (entity1 instanceof Player) {
                            player = (Player)entity1;
                            nonnulllist = player.getInventory().armor;
                            if (entity instanceof LivingEntity) {
                                livingentity = (LivingEntity)entity;
                                itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            nonnulllist.set(3, itemstack);
                            player.getInventory().setChanged();
                        } else if (entity1 instanceof LivingEntity) {
                            livingentity1 = (LivingEntity)entity1;
                            equipmentslot = EquipmentSlot.HEAD;
                            if (entity instanceof LivingEntity) {
                                livingentity = (LivingEntity)entity;
                                itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            livingentity1.setItemSlot(equipmentslot, itemstack);
                        }

                        if (entity1 instanceof LivingEntity) {
                            LivingEntity livingentity2 = (LivingEntity)entity1;

                            livingentity2.removeEffect((MobEffect)AnnoyingVillagersModMobEffects.HEROBRINE_EFFECT.get());
                        }

                        entity1.stopRiding();
                        if (entity1 instanceof ServerPlayer) {
                            ServerPlayer serverplayer = (ServerPlayer)entity1;

                            serverplayer.setGameMode(GameType.SURVIVAL);
                        }
                    }
                }
            }

            ((<undefinedtype>)(new Object() {
                private int ticks = 0;
                private float waitTicks;
                private LevelAccessor world;

                public void start(LevelAccessor levelaccessor1, int i) {
                    this.waitTicks = (float)i;
                    MinecraftForge.EVENT_BUS.register(this);
                    this.world = levelaccessor1;
                }

                @SubscribeEvent
                public void tick(ServerTickEvent servertickevent) {
                    if (servertickevent.phase == Phase.END) {
                        ++this.ticks;
                        if ((float)this.ticks >= this.waitTicks) {
                            this.run();
                        }
                    }

                }

                private void run() {
                    LevelAccessor levelaccessor1 = this.world;
                    Level level;
                    ItemEntity itementity;

                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1, d2, new ItemStack(Items.DIAMOND));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = this.world;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1, d2, new ItemStack(Items.DIAMOND));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = this.world;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1, d2, new ItemStack(Items.MUSIC_DISC_11));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = this.world;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1, d2, new ItemStack(Items.IRON_INGOT));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = this.world;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1, d2, new ItemStack(Items.WRITABLE_BOOK));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = this.world;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1, d2, new ItemStack(Items.EMERALD));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = this.world;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1, d2, new ItemStack(Items.EMERALD));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = this.world;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1, d2, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = this.world;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1, d2, new ItemStack(Items.NETHERITE_INGOT));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = this.world;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1, d2, new ItemStack(Items.ENDER_PEARL));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = this.world;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1, d2, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = this.world;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1, d2, new ItemStack(Items.ENDER_EYE));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = this.world;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1, d2, new ItemStack(Blocks.TNT));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = this.world;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1, d2, new ItemStack(Blocks.TNT));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    MinecraftForge.EVENT_BUS.unregister(this);
                }
            })).start(levelaccessor, 20);
        }
    }
}
