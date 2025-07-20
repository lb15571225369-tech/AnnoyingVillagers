package com.pla.annoyingvillagers.procedures;

import java.util.Random;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class ObsidianWeaponsWhenSwingingProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            new DelayedTask(10) {
                @Override
                public void run() throws CommandSyntaxException {
                    ItemStack itemstack;
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingentity = (LivingEntity) entity;

                        itemstack = livingentity.getMainHandItem();
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    LivingEntity livingentity1;
                    ItemStack itemstack1;
                    LevelAccessor levelaccessor1;
                    Level level;
                    Entity entity1;

                    if (itemstack.getItem() == AnnoyingVillagersModItems.OBSIDIAN_WEAPONS.get()) {
                        if (entity instanceof LivingEntity) {
                            livingentity1 = (LivingEntity) entity;
                            itemstack = livingentity1.getMainHandItem();
                        } else {
                            itemstack = ItemStack.EMPTY;
                        }

                        itemstack1 = itemstack;
                        if (itemstack1.hurt(100, (RandomSource) new Random(), (ServerPlayer) null)) {
                            itemstack1.shrink(1);
                            itemstack1.setDamageValue(0);
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level) levelaccessor1;
                            if (!level.isClientSide()) {
                                level.playSound((Player) null, new BlockPos(d0, d1, d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("annoyingvillagers", "obsidian_place")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                            } else {
                                level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("annoyingvillagers", "obsidian_place")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                            }
                        }

                        entity1 = entity;
                        if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                            entity1.getServer().getCommands().getDispatcher().execute(
                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^2 annoyingvillagers:obsidian",
                                    entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        }

                        new DelayedTask(1) {
                            @Override
                            public void run() throws CommandSyntaxException {
                                Entity entity2 = entity;

                                if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                    entity2.getServer().getCommands().getDispatcher().execute(
                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^3 annoyingvillagers:obsidian",
                                            entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                }
                            }
                        };

                        new DelayedTask(1) {
                            @Override
                            public void run() throws CommandSyntaxException {
                                Entity entity3 = entity;

                                if (!entity3.level.isClientSide() && entity3.getServer() != null) {
                                    entity3.getServer().getCommands().getDispatcher().execute(
                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^4 annoyingvillagers:obsidian",
                                            entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                }
                            }
                        };

                        new DelayedTask(1) {
                            @Override
                            public void run() throws CommandSyntaxException {
                                Entity entity4 = entity;

                                if (!entity4.level.isClientSide() && entity4.getServer() != null) {
                                    entity4.getServer().getCommands().getDispatcher().execute(
                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^5 annoyingvillagers:obsidian",
                                            entity4.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                }
                            }
                        };

                        new DelayedTask(1) {
                            @Override
                            public void run() throws CommandSyntaxException {
                                Entity entity5 = entity;

                                if (!entity5.level.isClientSide() && entity5.getServer() != null) {
                                    entity5.getServer().getCommands().getDispatcher().execute(
                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^6 annoyingvillagers:obsidian",
                                            entity5.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                }
                            }
                        };

                        new DelayedTask(1) {
                            @Override
                            public void run() throws CommandSyntaxException {
                                Entity entity6 = entity;

                                if (!entity6.level.isClientSide() && entity6.getServer() != null) {
                                    entity6.getServer().getCommands().getDispatcher().execute(
                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^7 annoyingvillagers:obsidian",
                                            entity6.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                }

                                if (Math.random() < 0.5D) {
                                    new DelayedTask(1) {
                                        @Override
                                        public void run() throws CommandSyntaxException {
                                            Entity entity7 = entity;

                                            if (!entity7.level.isClientSide() && entity7.getServer() != null) {
                                                entity7.getServer().getCommands().getDispatcher().execute(
                                                        "execute as @s at @s anchored eyes run setblock ^ ^ ^8 annoyingvillagers:obsidian",
                                                        entity7.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                            }
                                        }
                                    };

                                    new DelayedTask(1) {
                                        @Override
                                        public void run() throws CommandSyntaxException {
                                            Entity entity8 = entity;

                                            if (!entity8.level.isClientSide() && entity8.getServer() != null) {
                                                entity8.getServer().getCommands().getDispatcher().execute(
                                                        "execute as @s at @s anchored eyes run setblock ^ ^ ^9 annoyingvillagers:obsidian",
                                                        entity8.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                            }
                                        }
                                    };

                                    new DelayedTask(1) {
                                        @Override
                                        public void run() throws CommandSyntaxException {
                                            Entity entity9 = entity;

                                            if (!entity9.level.isClientSide() && entity9.getServer() != null) {
                                                entity9.getServer().getCommands().getDispatcher().execute(
                                                        "execute as @s at @s anchored eyes run setblock ^ ^ ^10 annoyingvillagers:obsidian",
                                                        entity9.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                            }

                                            if (Math.random() < 0.5D) {
                                                new DelayedTask(1) {
                                                    @Override
                                                    public void run() throws CommandSyntaxException {
                                                        Entity entity10 = entity;

                                                        if (!entity10.level.isClientSide() && entity10.getServer() != null) {
                                                            entity10.getServer().getCommands().getDispatcher().execute(
                                                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^11 annoyingvillagers:obsidian",
                                                                    entity10.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                        }
                                                    }
                                                };

                                                new DelayedTask(1) {
                                                    @Override
                                                    public void run() throws CommandSyntaxException {
                                                        Entity entity11 = entity;

                                                        if (!entity11.level.isClientSide() && entity11.getServer() != null) {
                                                            entity11.getServer().getCommands().getDispatcher().execute(
                                                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^12 annoyingvillagers:obsidian",
                                                                    entity11.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                        }
                                                    }
                                                };

                                                new DelayedTask(1) {
                                                    @Override
                                                    public void run() throws CommandSyntaxException {
                                                        Entity entity12 = entity;

                                                        if (!entity12.level.isClientSide() && entity12.getServer() != null) {
                                                            entity12.getServer().getCommands().getDispatcher().execute(
                                                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^13 annoyingvillagers:obsidian",
                                                                    entity12.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                        }
                                                    }
                                                };

                                                new DelayedTask(1) {
                                                    @Override
                                                    public void run() throws CommandSyntaxException {
                                                        Entity entity13 = entity;

                                                        if (!entity13.level.isClientSide() && entity13.getServer() != null) {
                                                            entity13.getServer().getCommands().getDispatcher().execute(
                                                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^15 annoyingvillagers:obsidian",
                                                                    entity13.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                        }
                                                    }
                                                };
                                            }
                                        }
                                    };
                                }
                            }
                        };
                    } else {
                        if (entity instanceof LivingEntity) {
                            LivingEntity livingentity2 = (LivingEntity) entity;

                            itemstack = livingentity2.getOffhandItem();
                        } else {
                            itemstack = ItemStack.EMPTY;
                        }

                        if (itemstack.getItem() == AnnoyingVillagersModItems.OBSIDIAN_WEAPONS.get()) {
                            if (entity instanceof LivingEntity) {
                                livingentity1 = (LivingEntity) entity;
                                itemstack = livingentity1.getOffhandItem();
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            itemstack1 = itemstack;
                            if (itemstack1.hurt(100, (RandomSource) new Random(), (ServerPlayer) null)) {
                                itemstack1.shrink(1);
                                itemstack1.setDamageValue(0);
                            }

                            levelaccessor1 = levelaccessor;
                            if (levelaccessor1 instanceof Level) {
                                level = (Level) levelaccessor1;
                                if (!level.isClientSide()) {
                                    level.playSound((Player) null, new BlockPos(d0, d1, d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("annoyingvillagers", "obsidian_place")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                } else {
                                    level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("annoyingvillagers", "obsidian_place")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                }
                            }

                            entity1 = entity;
                            if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                entity1.getServer().getCommands().getDispatcher().execute(
                                        "execute as @s at @s anchored eyes run setblock ^ ^ ^2 annoyingvillagers:obsidian",
                                        entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            }

                            new DelayedTask(1) {
                                @Override
                                public void run() throws CommandSyntaxException {
                                    Entity entity2 = entity;

                                    if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                        entity2.getServer().getCommands().getDispatcher().execute(
                                                "execute as @s at @s anchored eyes run setblock ^ ^ ^3 annoyingvillagers:obsidian",
                                                entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                    }
                                }
                            };

                            new DelayedTask(1) {
                                @Override
                                public void run() throws CommandSyntaxException {
                                    Entity entity3 = entity;

                                    if (!entity3.level.isClientSide() && entity3.getServer() != null) {
                                        entity3.getServer().getCommands().getDispatcher().execute(
                                                "execute as @s at @s anchored eyes run setblock ^ ^ ^4 annoyingvillagers:obsidian",
                                                entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                    }
                                }
                            };

                            new DelayedTask(1) {
                                @Override
                                public void run() throws CommandSyntaxException {
                                    Entity entity4 = entity;

                                    if (!entity4.level.isClientSide() && entity4.getServer() != null) {
                                        entity4.getServer().getCommands().getDispatcher().execute(
                                                "execute as @s at @s anchored eyes run setblock ^ ^ ^5 annoyingvillagers:obsidian",
                                                entity4.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                    }
                                }
                            };

                            new DelayedTask(1) {
                                @Override
                                public void run() throws CommandSyntaxException {
                                    Entity entity5 = entity;

                                    if (!entity5.level.isClientSide() && entity5.getServer() != null) {
                                        entity5.getServer().getCommands().getDispatcher().execute(
                                                "execute as @s at @s anchored eyes run setblock ^ ^ ^6 annoyingvillagers:obsidian",
                                                entity5.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                    }
                                }
                            };

                            new DelayedTask(1) {
                                @Override
                                public void run() throws CommandSyntaxException {
                                    Entity entity6 = entity;

                                    if (!entity6.level.isClientSide() && entity6.getServer() != null) {
                                        entity6.getServer().getCommands().getDispatcher().execute(
                                                "execute as @s at @s anchored eyes run setblock ^ ^ ^7 annoyingvillagers:obsidian",
                                                entity6.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                    }
                                    if (Math.random() < 0.5D) {
                                        new DelayedTask(1) {
                                            @Override
                                            public void run() throws CommandSyntaxException {
                                                Entity entity7 = entity;

                                                if (!entity7.level.isClientSide() && entity7.getServer() != null) {
                                                    entity7.getServer().getCommands().getDispatcher().execute(
                                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^8 annoyingvillagers:obsidian",
                                                            entity7.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                }
                                            }
                                        };

                                        new DelayedTask(1) {
                                            @Override
                                            public void run() throws CommandSyntaxException {
                                                Entity entity8 = entity;

                                                if (!entity8.level.isClientSide() && entity8.getServer() != null) {
                                                    entity8.getServer().getCommands().getDispatcher().execute(
                                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^9 annoyingvillagers:obsidian",
                                                            entity8.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                }
                                            }
                                        };

                                        new DelayedTask(1) {
                                            @Override
                                            public void run() throws CommandSyntaxException {
                                                Entity entity9 = entity;

                                                if (!entity9.level.isClientSide() && entity9.getServer() != null) {
                                                    entity9.getServer().getCommands().getDispatcher().execute(
                                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^10 annoyingvillagers:obsidian",
                                                            entity9.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                }
                                                if (Math.random() < 0.5D) {
                                                    new DelayedTask(1) {
                                                        @Override
                                                        public void run() throws CommandSyntaxException {
                                                            Entity entity10 = entity;

                                                            if (!entity10.level.isClientSide() && entity10.getServer() != null) {
                                                                entity10.getServer().getCommands().getDispatcher().execute(
                                                                        "execute as @s at @s anchored eyes run setblock ^ ^ ^11 annoyingvillagers:obsidian",
                                                                        entity10.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                            }
                                                        }
                                                    };

                                                    new DelayedTask(1) {
                                                        @Override
                                                        public void run() throws CommandSyntaxException {
                                                            Entity entity11 = entity;

                                                            if (!entity11.level.isClientSide() && entity11.getServer() != null) {
                                                                entity11.getServer().getCommands().getDispatcher().execute(
                                                                        "execute as @s at @s anchored eyes run setblock ^ ^ ^12 annoyingvillagers:obsidian",
                                                                        entity11.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                            }
                                                        }
                                                    };

                                                    new DelayedTask(1) {
                                                        @Override
                                                        public void run() throws CommandSyntaxException {
                                                            Entity entity12 = entity;

                                                            if (!entity12.level.isClientSide() && entity12.getServer() != null) {
                                                                entity12.getServer().getCommands().getDispatcher().execute(
                                                                        "execute as @s at @s anchored eyes run setblock ^ ^ ^13 annoyingvillagers:obsidian",
                                                                        entity12.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                            }
                                                        }
                                                    };

                                                    new DelayedTask(1) {
                                                        @Override
                                                        public void run() throws CommandSyntaxException {
                                                            Entity entity13 = entity;

                                                            if (!entity13.level.isClientSide() && entity13.getServer() != null) {
                                                                entity13.getServer().getCommands().getDispatcher().execute(
                                                                        "execute as @s at @s anchored eyes run setblock ^ ^ ^15 annoyingvillagers:obsidian",
                                                                        entity13.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                            }
                                                        }
                                                    };
                                                }
                                            }
                                        };
                                    }
                                }
                            };
                        }
                    }
                }
            };
        }
    }
}
