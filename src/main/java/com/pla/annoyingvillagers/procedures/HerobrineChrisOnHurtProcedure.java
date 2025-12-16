package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class HerobrineChrisOnHurtProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity, final Entity entity1) {
        if (entity != null && entity1 != null) {
            LivingEntity livingentity;

            if (entity instanceof Mob) {
                Mob mob = (Mob)entity;

                livingentity = mob.getTarget();
            } else {
                livingentity = null;
            }

            if (entity1 == livingentity) {
                Level level;

                if (Math.random() <= 0.25D) {
                    if (levelaccessor instanceof Level) {
                        level = (Level)levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "obsidian_place")), SoundSource.BLOCKS, 0.2F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "obsidian_place")), SoundSource.BLOCKS, 0.2F, 1.0F, false);
                        }
                    }

                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        try {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "execute as @s at @s anchored eyes run setblock ^ ^-1 ^2 annoyingvillagers:obsidian",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                        }
                    }
                    new DelayedTask(1) {
                        @Override
                        public void run() {
                            Entity entity2 = entity;

                            if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                try {
                                    entity2.getServer().getCommands().getDispatcher().execute(
                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^2 annoyingvillagers:obsidian",
                                            entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {
                                }
                            }
                        }
                    };
                }

                if (Math.random() <= 0.25D) {
                    if (levelaccessor instanceof Level) {
                        level = (Level)levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "obsidian_place")), SoundSource.BLOCKS, 0.2F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "obsidian_place")), SoundSource.BLOCKS, 0.2F, 1.0F, false);
                        }
                    }

                    new DelayedTask(1) {
                        @Override
                        public void run() {
                            Entity entity2 = entity;

                            if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                try {
                                    entity2.getServer().getCommands().getDispatcher().execute(
                                            "execute as @s at @s anchored eyes run setblock ^ ^-1 ^1 annoyingvillagers:obsidian",
                                            entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {
                                }
                            }

                            new DelayedTask(1) {
                                public void run() {
                                    Entity entity3 = entity;

                                    if (!entity3.level().isClientSide() && entity3.getServer() != null) {
                                        try {
                                            entity3.getServer().getCommands().getDispatcher().execute(
                                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^1 annoyingvillagers:obsidian",
                                                    entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                        } catch (CommandSyntaxException e) {
                                        }
                                    }

                                    new DelayedTask(1) {
                                        public void run() {
                                            Entity entity4 = entity;

                                            if (!entity4.level().isClientSide() && entity4.getServer() != null) {
                                                try {
                                                    entity4.getServer().getCommands().getDispatcher().execute(
                                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^2 annoyingvillagers:obsidian",
                                                            entity4.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                } catch (CommandSyntaxException e) {
                                                }
                                            }

                                            new DelayedTask(1) {
                                                public void run() {
                                                    Entity entity5 = entity;

                                                    if (!entity5.level().isClientSide() && entity5.getServer() != null) {
                                                        try {
                                                            entity5.getServer().getCommands().getDispatcher().execute(
                                                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^3 annoyingvillagers:obsidian",
                                                                    entity5.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                        } catch (CommandSyntaxException e) {
                                                        }
                                                    }

                                                    new DelayedTask(1) {
                                                        public void run() {
                                                            Entity entity6 = entity;

                                                            if (!entity6.level().isClientSide() && entity6.getServer() != null) {
                                                                try {
                                                                    entity6.getServer().getCommands().getDispatcher().execute(
                                                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^4 annoyingvillagers:obsidian",
                                                                            entity6.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                } catch (CommandSyntaxException e) {
                                                                }
                                                            }

                                                            new DelayedTask(1) {
                                                                public void run() {
                                                                    Entity entity7 = entity;

                                                                    if (!entity7.level().isClientSide() && entity7.getServer() != null) {
                                                                        try {
                                                                            entity7.getServer().getCommands().getDispatcher().execute(
                                                                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^5 annoyingvillagers:obsidian",
                                                                                    entity7.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                        } catch (CommandSyntaxException e) {
                                                                        }
                                                                    }

                                                                    new DelayedTask(1) {
                                                                        public void run() {
                                                                            Entity entity8 = entity;

                                                                            if (!entity8.level().isClientSide() && entity8.getServer() != null) {
                                                                                try {
                                                                                    entity8.getServer().getCommands().getDispatcher().execute(
                                                                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^6 annoyingvillagers:obsidian",
                                                                                            entity8.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                } catch (CommandSyntaxException e) {
                                                                                }
                                                                            }
                                                                        }
                                                                    };
                                                                }
                                                            };
                                                        }
                                                    };
                                                }
                                            };
                                        }
                                    };
                                }
                            };
                        }
                    };
                    new DelayedTask(20) {
                        public void run() {
                            if (Math.random() <= 0.5D) {
                                LevelAccessor levelaccessor1 = levelaccessor;

                                if (levelaccessor1 instanceof Level) {
                                    Level level1 = (Level)levelaccessor1;

                                    if (!level1.isClientSide()) {
                                        level1.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "obsidian_place")), SoundSource.BLOCKS, 0.2F, 1.0F);
                                    } else {
                                        level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "obsidian_place")), SoundSource.BLOCKS, 0.2F, 1.0F, false);
                                    }
                                }

                                Entity entity2 = entity;

                                if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                    try {
                                        entity2.getServer().getCommands().getDispatcher().execute(
                                                "execute as @s at @s anchored eyes run setblock ^ ^ ^-1 annoyingvillagers:obsidian",
                                                entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                    } catch (CommandSyntaxException e) {
                                    }
                                }

                                entity2 = entity;
                                if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                    try {
                                        entity2.getServer().getCommands().getDispatcher().execute(
                                                "execute as @s at @s anchored eyes run setblock ^ ^-1 ^-1 annoyingvillagers:obsidian",
                                                entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                    } catch (CommandSyntaxException e) {
                                    }
                                }

                                entity2 = entity;
                                if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                    try {
                                        entity2.getServer().getCommands().getDispatcher().execute(
                                                "execute as @s at @s anchored eyes run setblock ^ ^1 ^-1 annoyingvillagers:obsidian",
                                                entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                    } catch (CommandSyntaxException e) {
                                    }
                                }

                                entity2 = entity;
                                if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                    try {
                                        entity2.getServer().getCommands().getDispatcher().execute(
                                                "execute as @s at @s anchored eyes run setblock ^ ^1 ^ annoyingvillagers:obsidian",
                                                entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                    } catch (CommandSyntaxException e) {
                                    }
                                }

                                new DelayedTask(1) {
                                    public void run() {
                                        Entity entity3 = entity;

                                        if (!entity3.level().isClientSide() && entity3.getServer() != null) {
                                            try {
                                                entity3.getServer().getCommands().getDispatcher().execute(
                                                        "execute as @s at @s anchored eyes run setblock ^ ^1 ^1 annoyingvillagers:obsidian",
                                                        entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                            } catch (CommandSyntaxException e) {
                                            }
                                        }

                                        new DelayedTask(1) {
                                            public void run() {
                                                Entity entity4 = entity;

                                                if (!entity4.level().isClientSide() && entity4.getServer() != null) {
                                                    try {
                                                        entity4.getServer().getCommands().getDispatcher().execute(
                                                                "execute as @s at @s anchored eyes run setblock ^ ^1 ^2 annoyingvillagers:obsidian",
                                                                entity4.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                    } catch (CommandSyntaxException e) {
                                                    }
                                                }

                                                new DelayedTask(1) {
                                                    public void run() {
                                                        Entity entity5 = entity;

                                                        if (!entity5.level().isClientSide() && entity5.getServer() != null) {
                                                            try {
                                                                entity5.getServer().getCommands().getDispatcher().execute(
                                                                        "execute as @s at @s anchored eyes run setblock ^ ^1 ^3 annoyingvillagers:obsidian",
                                                                        entity5.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                            } catch (CommandSyntaxException e) {
                                                            }
                                                        }

                                                        new DelayedTask(1) {
                                                            public void run() {
                                                                Entity entity6 = entity;

                                                                if (!entity6.level().isClientSide() && entity6.getServer() != null) {
                                                                    try {
                                                                        entity6.getServer().getCommands().getDispatcher().execute(
                                                                                "execute as @s at @s anchored eyes run setblock ^ ^1 ^3 annoyingvillagers:obsidian",
                                                                                entity6.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                    } catch (CommandSyntaxException e) {
                                                                    }
                                                                }
                                                                new DelayedTask(1) {
                                                                    public void run() {
                                                                        Entity entity7 = entity;

                                                                        if (!entity7.level().isClientSide() && entity7.getServer() != null) {
                                                                            try {
                                                                                entity7.getServer().getCommands().getDispatcher().execute(
                                                                                        "execute as @s at @s anchored eyes run setblock ^ ^1 ^3 annoyingvillagers:obsidian",
                                                                                        entity7.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                            } catch (CommandSyntaxException e) {
                                                                            }
                                                                        }

                                                                        new DelayedTask(1) {
                                                                            public void run() {
                                                                                Entity entity8 = entity;

                                                                                if (!entity8.level().isClientSide() && entity8.getServer() != null) {
                                                                                    try {
                                                                                        entity8.getServer().getCommands().getDispatcher().execute(
                                                                                                "execute as @s at @s anchored eyes run setblock ^ ^1 ^4 annoyingvillagers:obsidian",
                                                                                                entity8.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                    } catch (CommandSyntaxException e) {
                                                                                    }
                                                                                }
                                                                            }
                                                                        };
                                                                    }
                                                                };
                                                            }
                                                        };
                                                    }
                                                };
                                            }
                                        };
                                    }
                                };
                                if (Math.random() <= 0.5D) {
                                    new DelayedTask(1) {
                                        public void run() {
                                            Entity entity3 = entity;

                                            if (!entity3.level().isClientSide() && entity3.getServer() != null) {
                                                try {
                                                    entity3.getServer().getCommands().getDispatcher().execute(
                                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^5 annoyingvillagers:obsidian",
                                                            entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                } catch (CommandSyntaxException e) {
                                                }
                                            }
                                            new DelayedTask(1) {
                                                @Override
                                                public void run() {
                                                    Entity entity4 = entity;

                                                    if (!entity4.level().isClientSide() && entity4.getServer() != null) {
                                                        try {
                                                            entity4.getServer().getCommands().getDispatcher().execute(
                                                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^6 annoyingvillagers:obsidian",
                                                                    entity4.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                        } catch (CommandSyntaxException e) {
                                                        }
                                                    }
                                                }
                                            };
                                        }
                                    };
                                }
                            }
                        }
                    };
                } else if (Math.random() <= 0.1D) {
                    if (levelaccessor instanceof Level) {
                        level = (Level)levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "obsidian_place")), SoundSource.BLOCKS, 0.2F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "obsidian_place")), SoundSource.BLOCKS, 0.2F, 1.0F, false);
                        }
                    }

                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        try {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "execute as @s at @s anchored eyes run setblock ^ ^-1 ^1 annoyingvillagers:obsidian",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                        }
                    }

                    new DelayedTask(1) {
                        public void run() {
                            Entity entity2 = entity;

                            if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                try {
                                    entity2.getServer().getCommands().getDispatcher().execute(
                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^1 annoyingvillagers:obsidian",
                                            entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {
                                }
                            }
                            new DelayedTask(1) {
                                public void run() {
                                    Entity entity3 = entity;

                                    if (!entity3.level().isClientSide() && entity3.getServer() != null) {
                                        try {
                                            entity3.getServer().getCommands().getDispatcher().execute(
                                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^2 annoyingvillagers:obsidian",
                                                    entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                        } catch (CommandSyntaxException e) {
                                        }
                                    }
                                    new DelayedTask(1) {
                                        public void run() {
                                            Entity entity4 = entity;

                                            if (!entity4.level().isClientSide() && entity4.getServer() != null) {
                                                try {
                                                    entity4.getServer().getCommands().getDispatcher().execute(
                                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^3 annoyingvillagers:obsidian",
                                                            entity4.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                } catch (CommandSyntaxException e) {
                                                }
                                            }
                                            new DelayedTask(1) {
                                                public void run() {
                                                    Entity entity5 = entity;

                                                    if (!entity5.level().isClientSide() && entity5.getServer() != null) {
                                                        try {
                                                            entity5.getServer().getCommands().getDispatcher().execute(
                                                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^4 annoyingvillagers:obsidian",
                                                                    entity5.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                        } catch (CommandSyntaxException e) {
                                                        }
                                                    }
                                                    new DelayedTask(1) {
                                                        public void run() {
                                                            Entity entity6 = entity;

                                                            if (!entity6.level().isClientSide() && entity6.getServer() != null) {
                                                                try {
                                                                    entity6.getServer().getCommands().getDispatcher().execute(
                                                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^5 annoyingvillagers:obsidian",
                                                                            entity6.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                } catch (CommandSyntaxException e) {
                                                                }
                                                            }
                                                            new DelayedTask(1) {
                                                                public void run() {
                                                                    Entity entity7 = entity;

                                                                    if (!entity7.level().isClientSide() && entity7.getServer() != null) {
                                                                        try {
                                                                            entity7.getServer().getCommands().getDispatcher().execute(
                                                                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^6 annoyingvillagers:obsidian",
                                                                                    entity7.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                        } catch (CommandSyntaxException e) {
                                                                        }
                                                                    }
                                                                    new DelayedTask(1) {
                                                                        public void run() {
                                                                            Entity entity8 = entity;

                                                                            if (!entity8.level().isClientSide() && entity8.getServer() != null) {
                                                                                try {
                                                                                    entity8.getServer().getCommands().getDispatcher().execute(
                                                                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^7 annoyingvillagers:obsidian",
                                                                                            entity8.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                } catch (CommandSyntaxException e) {
                                                                                }
                                                                            }
                                                                            new DelayedTask(1) {
                                                                                public void run() {
                                                                                    Entity entity9 = entity;

                                                                                    if (!entity9.level().isClientSide() && entity9.getServer() != null) {
                                                                                        try {
                                                                                            entity9.getServer().getCommands().getDispatcher().execute(
                                                                                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^8 annoyingvillagers:obsidian",
                                                                                                    entity9.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                        } catch (CommandSyntaxException e) {
                                                                                        }
                                                                                    }
                                                                                    new DelayedTask(1) {
                                                                                        public void run() {
                                                                                            Entity entity10 = entity;

                                                                                            if (!entity10.level().isClientSide() && entity10.getServer() != null) {
                                                                                                try {
                                                                                                    entity10.getServer().getCommands().getDispatcher().execute(
                                                                                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^9 annoyingvillagers:obsidian",
                                                                                                            entity10.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                } catch (CommandSyntaxException e) {
                                                                                                }
                                                                                            }
                                                                                            new DelayedTask(1) {
                                                                                                public void run() {
                                                                                                    Entity entity11 = entity;

                                                                                                    if (!entity11.level().isClientSide() && entity11.getServer() != null) {
                                                                                                        try {
                                                                                                            entity11.getServer().getCommands().getDispatcher().execute(
                                                                                                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^10 annoyingvillagers:obsidian",
                                                                                                                    entity11.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                        } catch (CommandSyntaxException e) {
                                                                                                        }
                                                                                                    }
                                                                                                    new DelayedTask(1) {
                                                                                                        public void run() {
                                                                                                            Entity entity12 = entity;

                                                                                                            if (!entity12.level().isClientSide() && entity12.getServer() != null) {
                                                                                                                try {
                                                                                                                    entity12.getServer().getCommands().getDispatcher().execute(
                                                                                                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^11 annoyingvillagers:obsidian",
                                                                                                                            entity12.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                                } catch (CommandSyntaxException e) {
                                                                                                                }
                                                                                                            }
                                                                                                            new DelayedTask(1) {
                                                                                                                public void run() {
                                                                                                                    Entity entity13 = entity;

                                                                                                                    if (!entity13.level().isClientSide() && entity13.getServer() != null) {
                                                                                                                        try {
                                                                                                                            entity13.getServer().getCommands().getDispatcher().execute(
                                                                                                                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^12 annoyingvillagers:obsidian",
                                                                                                                                    entity13.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                                        } catch (CommandSyntaxException e) {
                                                                                                                        }
                                                                                                                    }
                                                                                                                    new DelayedTask(1) {
                                                                                                                        public void run() {
                                                                                                                            Entity entity14 = entity;

                                                                                                                            if (!entity14.level().isClientSide() && entity14.getServer() != null) {
                                                                                                                                try {
                                                                                                                                    entity14.getServer().getCommands().getDispatcher().execute(
                                                                                                                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^13 annoyingvillagers:obsidian",
                                                                                                                                            entity14.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                                                } catch (CommandSyntaxException e) {
                                                                                                                                }
                                                                                                                            }
                                                                                                                            new DelayedTask(1) {
                                                                                                                                public void run() {
                                                                                                                                    Entity entity15 = entity;

                                                                                                                                    if (!entity15.level().isClientSide() && entity15.getServer() != null) {
                                                                                                                                        try {
                                                                                                                                            entity15.getServer().getCommands().getDispatcher().execute(
                                                                                                                                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^14 annoyingvillagers:obsidian",
                                                                                                                                                    entity15.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                                                        } catch (CommandSyntaxException e) {
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            };
                                                                                                                        }
                                                                                                                    };
                                                                                                                }
                                                                                                            };
                                                                                                        }
                                                                                                    };
                                                                                                }
                                                                                            };
                                                                                        }
                                                                                    };
                                                                                }
                                                                            };
                                                                        }
                                                                    };
                                                                }
                                                            };
                                                        }
                                                    };
                                                }
                                            };
                                        }
                                    };
                                }
                            };
                        }
                    };
                }

                LivingEntity livingentity1;

                if (Math.random() <= 0.1D) {
                    if (entity1 instanceof LivingEntity) {
                        livingentity1 = (LivingEntity)entity1;
                        if (!livingentity1.level().isClientSide()) {
                            livingentity1.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.HEROBRINE.get(), 200, 0, false, false));
                        }
                    }

                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity)entity;
                        if (!livingentity1.level().isClientSide()) {
                            livingentity1.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 60, 1, false, false));
                        }
                    }

                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity)entity;
                        ItemStack itemstack = new ItemStack((ItemLike) AnnoyingVillagersModItems.BEDROCK_WEAPON.get());

                        itemstack.setCount(1);
                        livingentity1.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                        if (livingentity1 instanceof Player) {
                            Player player = (Player)livingentity1;

                            player.getInventory().setChanged();
                        }
                    }

                    new DelayedTask(4) {
                        @Override
                        public void run() {
                            Entity entity2 = entity;

                            entity2.teleportTo(entity1.getX(), entity1.getY(), entity1.getZ());
                            if (entity2 instanceof ServerPlayer) {
                                ServerPlayer serverplayer = (ServerPlayer)entity2;

                                serverplayer.connection.teleport(entity1.getX(), entity1.getY(), entity1.getZ(), entity2.getYRot(), entity2.getXRot());
                            }

                            entity2 = entity;
                            if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity2, LivingEntityPatch.class);
                                if (livingEntityPatch != null) {
                                    livingEntityPatch.playAnimationSynchronized(AVAnimations.FIST_DASH, 0.0F);
                                }
                            }

                            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<§5Herobrine§r> " +
                                        Component.translatable("subtitles.herobrine_chris_say_weak").getString()), false);
                            }

                            LevelAccessor levelaccessor1 = levelaccessor;

                            if (levelaccessor1 instanceof Level) {
                                Level level1 = (Level)levelaccessor1;

                                if (!level1.isClientSide()) {
                                    level1.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "herobrine_youareweak")), SoundSource.NEUTRAL, 2.0F, 1.0F);
                                } else {
                                    level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "herobrine_youareweak")), SoundSource.NEUTRAL, 2.0F, 1.0F, false);
                                }
                            }
                        }
                    };
                    new DelayedTask(100) {
                        @Override
                        public void run() {
                            if (entity instanceof LivingEntity) {
                                LivingEntity livingentity2 = (LivingEntity)entity;
                                ItemStack itemstack1 = new ItemStack((ItemLike)AnnoyingVillagersModItems.OBSIDIAN_WEAPON.get());

                                itemstack1.setCount(1);
                                livingentity2.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                                if (livingentity2 instanceof Player) {
                                    Player player1 = (Player)livingentity2;

                                    player1.getInventory().setChanged();
                                }
                            }
                        }
                    };
                }

                entity.setSprinting(true);
                new DelayedTask(10) {
                    @Override
                    public void run() {
                        entity.setSprinting(false);
                    }
                };
                if (Math.random() <= 0.05D) {
                    new DelayedTask(20) {
                        @Override
                        public void run() {
                            LevelAccessor levelaccessor1 = levelaccessor;

                            if (levelaccessor1 instanceof Level) {
                                Level level1 = (Level)levelaccessor1;

                                if (!level1.isClientSide()) {
                                    level1.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "soul_legend")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                } else {
                                    level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "soul_legend")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                }
                            }
                        }
                    };
                }

                if (Math.random() <= 0.01D) {
                    new DelayedTask(5) {
                        @Override
                        public void run() {
                            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<§5Herobrine§r> " +
                                        Component.translatable("subtitles.herobrine_chris_say_enough").getString()), false);
                            }

                            LevelAccessor levelaccessor1 = levelaccessor;

                            if (levelaccessor1 instanceof Level) {
                                Level level1 = (Level)levelaccessor1;

                                if (!level1.isClientSide()) {
                                    level1.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "herobrine_enough")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                } else {
                                    level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "herobrine_enough")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                }
                            }
                        }
                    };
                }

                if (Math.random() <= 0.009D) {
                    new DelayedTask(5) {
                        @Override
                        public void run() {
                            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<§5Herobrine§r> " +
                                        Component.translatable("subtitles.herobrine_chris_say_how_foolish").getString()), false);
                            }

                            LevelAccessor levelaccessor1 = levelaccessor;

                            if (levelaccessor1 instanceof Level) {
                                Level level1 = (Level)levelaccessor1;

                                if (!level1.isClientSide()) {
                                    level1.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "herobrine_howfoolish")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                } else {
                                    level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "herobrine_howfoolish")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                }
                            }
                        }
                    };
                }

                if (Math.random() <= 0.01D) {
                    new DelayedTask(5) {
                        @Override
                        public void run() {
                            LevelAccessor levelaccessor1 = levelaccessor;

                            if (levelaccessor1 instanceof Level) {
                                Level level1 = (Level)levelaccessor1;

                                if (!level1.isClientSide()) {
                                    level1.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "herobrine_attack_2")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                } else {
                                    level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "herobrine_attack_2")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                }
                            }
                        }
                    };
                }
            }
        }
    }
}
