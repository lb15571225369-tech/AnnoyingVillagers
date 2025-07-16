package com.pla.annoyingvillagers.procedures;

import java.util.Random;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class ObsidianWeaponsOnUseProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity, ItemStack itemstack) {
        if (entity != null) {
            ItemStack itemstack1;

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;

                itemstack1 = livingentity.getMainHandItem();
            } else {
                itemstack1 = ItemStack.EMPTY;
            }

            LivingEntity livingentity1;
            ItemStack itemstack2;
            Level level;
            Player player;

            if (itemstack1.getItem() == AnnoyingVillagersModItems.OBSIDIAN_WEAPONS.get()) {
                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity)entity;
                    itemstack1 = livingentity1.getMainHandItem();
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                itemstack2 = itemstack1;
                if (itemstack2.hurt(100, new Random(), (ServerPlayer)null)) {
                    itemstack2.shrink(1);
                    itemstack2.setDamageValue(0);
                }

                if (levelaccessor instanceof Level) {
                    level = (Level)levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:fangzhiheiyaoshi")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:fangzhiheiyaoshi")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (entity instanceof Player) {
                    player = (Player)entity;
                    player.getCooldowns().addCooldown(itemstack.getItem(), 20);
                }

                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^2 annoyingvillagers:putongheiyaoshi keep");
                }

                new DelayedTask(1) {
                    @Override
                    public void run() {
                        Entity entity1 = entity;

                        if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                            entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^3 annoyingvillagers:putongheiyaoshi keep");
                        }
                    }
                };

                new DelayedTask(1) {
                    @Override
                    public void run() {
                        Entity entity2 = entity;

                        if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                            entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^4 annoyingvillagers:putongheiyaoshi keep");
                        }
                    }
                };

                new DelayedTask(1) {
                    @Override
                    public void run() {
                        Entity entity3 = entity;

                        if (!entity3.level.isClientSide() && entity3.getServer() != null) {
                            entity3.getServer().getCommands().performCommand(entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^5 annoyingvillagers:putongheiyaoshi keep");
                        }
                    }
                };

                new DelayedTask(1) {
                    @Override
                    public void run() {
                        Entity entity4 = entity;

                        if (!entity4.level.isClientSide() && entity4.getServer() != null) {
                            entity4.getServer().getCommands().performCommand(entity4.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^6 annoyingvillagers:putongheiyaoshi keep");
                        }
                    }
                };

                new DelayedTask(1) {
                    @Override
                    public void run() {
                        Entity entity5 = entity;

                        if (!entity5.level.isClientSide() && entity5.getServer() != null) {
                            entity5.getServer().getCommands().performCommand(entity5.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^7 annoyingvillagers:putongheiyaoshi keep");
                        }
                    }
                };

                new DelayedTask(1) {
                    @Override
                    public void run() {
                        Entity entity6 = entity;

                        if (!entity6.level.isClientSide() && entity6.getServer() != null) {
                            entity6.getServer().getCommands().performCommand(entity6.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^8 annoyingvillagers:putongheiyaoshi keep");
                        }
                    }
                };

                new DelayedTask(1) {
                    @Override
                    public void run() {
                        Entity entity7 = entity;

                        if (!entity7.level.isClientSide() && entity7.getServer() != null) {
                            entity7.getServer().getCommands().performCommand(entity7.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^9 annoyingvillagers:putongheiyaoshi keep");
                        }
                    }
                };

                new DelayedTask(1) {
                    @Override
                    public void run() {
                        Entity entity8 = entity;

                        if (!entity8.level.isClientSide() && entity8.getServer() != null) {
                            entity8.getServer().getCommands().performCommand(entity8.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^10 annoyingvillagers:putongheiyaoshi keep");
                        }
                    }
                };
            } else {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity2 = (LivingEntity)entity;

                    itemstack1 = livingentity2.getOffhandItem();
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                if (itemstack1.getItem() == AnnoyingVillagersModItems.OBSIDIAN_WEAPONS.get()) {
                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity)entity;
                        itemstack1 = livingentity1.getOffhandItem();
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    itemstack2 = itemstack1;
                    if (itemstack2.hurt(100, new Random(), (ServerPlayer)null)) {
                        itemstack2.shrink(1);
                        itemstack2.setDamageValue(0);
                    }

                    if (entity instanceof Player) {
                        player = (Player)entity;
                        player.getCooldowns().addCooldown(itemstack.getItem(), 20);
                    }

                    if (levelaccessor instanceof Level) {
                        level = (Level)levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:fangzhiheiyaoshi")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:fangzhiheiyaoshi")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                        }
                    }

                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^2 annoyingvillagers:putongheiyaoshi keep");
                    }

                    new DelayedTask(1) {
                        @Override
                        public void run() {
                            Entity entity1 = entity;

                            if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^3 annoyingvillagers:putongheiyaoshi keep");
                            }
                        }
                    };

                    new DelayedTask(1) {
                        @Override
                        public void run() {
                            Entity entity2 = entity;

                            if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^4 annoyingvillagers:putongheiyaoshi keep");
                            }
                        }
                    };

                    new DelayedTask(1) {
                        @Override
                        public void run() {
                            Entity entity3 = entity;

                            if (!entity3.level.isClientSide() && entity3.getServer() != null) {
                                entity3.getServer().getCommands().performCommand(entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^5 annoyingvillagers:putongheiyaoshi keep");
                            }
                        }
                    };

                    new DelayedTask(1) {
                        @Override
                        public void run() {
                            Entity entity4 = entity;

                            if (!entity4.level.isClientSide() && entity4.getServer() != null) {
                                entity4.getServer().getCommands().performCommand(entity4.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^6 annoyingvillagers:putongheiyaoshi keep");
                            }
                        }
                    };

                    new DelayedTask(1) {
                        @Override
                        public void run() {
                            Entity entity5 = entity;

                            if (!entity5.level.isClientSide() && entity5.getServer() != null) {
                                entity5.getServer().getCommands().performCommand(entity5.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^7 annoyingvillagers:putongheiyaoshi keep");
                            }
                        }
                    };

                    new DelayedTask(1) {
                        @Override
                        public void run() {
                            Entity entity6 = entity;

                            if (!entity6.level.isClientSide() && entity6.getServer() != null) {
                                entity6.getServer().getCommands().performCommand(entity6.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^8 annoyingvillagers:putongheiyaoshi keep");
                            }
                        }
                    };

                    new DelayedTask(1) {
                        @Override
                        public void run() {
                            Entity entity7 = entity;

                            if (!entity7.level.isClientSide() && entity7.getServer() != null) {
                                entity7.getServer().getCommands().performCommand(entity7.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^9 annoyingvillagers:putongheiyaoshi keep");
                            }
                        }
                    };

                    new DelayedTask(1) {
                        @Override
                        public void run() {
                            Entity entity8 = entity;

                            if (!entity8.level.isClientSide() && entity8.getServer() != null) {
                                entity8.getServer().getCommands().performCommand(entity8.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^10 annoyingvillagers:putongheiyaoshi keep");
                            }
                        }
                    };
                }
            }

        }
    }
}
