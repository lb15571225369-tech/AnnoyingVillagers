package com.pla.annoyingvillagers.procedures;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tictim.paraglider.capabilities.Caps;
import tictim.paraglider.capabilities.PlayerMovement;

public class KickSongKaiAnJianShiProcedure {

    public static void execute(LevelAccessor levelaccessor, final Entity entity) {
        if (entity != null) {
            PlayerMovement playermovement = (PlayerMovement)entity.getCapability(Caps.playerMovement, (Direction)null).resolve().orElseThrow();

            if (playermovement.canAction() && entity.isShiftKeyDown() && entity.isSprinting()) {
                ItemStack itemstack;

                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity)entity;

                    itemstack = livingentity.getMainHandItem();
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                if (itemstack.getItem() instanceof SwordItem) {
                    if (entity.getPersistentData().getDouble("dash_auto") != 1.0D) {
                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                            entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"wom:biped/combat/torment_charged_attack_2\" 0 1");
                        }

                        entity.getPersistentData().putDouble("dash_auto", 1.0D);
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
                                entity.getPersistentData().putDouble("dash_auto", 0.0D);
                                MinecraftForge.EVENT_BUS.unregister(this);
                            }
                        })).start(levelaccessor, 50);
                    }
                } else {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingentity1 = (LivingEntity)entity;

                        itemstack = livingentity1.getMainHandItem();
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    if (itemstack.getItem() instanceof AxeItem && entity.getPersistentData().getDouble("dash_auto") != 1.0D) {
                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                            entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"wom:biped/combat/torment_charged_attack_2\" 0 1");
                        }

                        entity.getPersistentData().putDouble("dash_auto", 1.0D);
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
                                entity.getPersistentData().putDouble("dash_auto", 0.0D);
                                MinecraftForge.EVENT_BUS.unregister(this);
                            }
                        })).start(levelaccessor, 50);
                    }
                }
            }

        }
    }
}
