package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.entity.StealthAttackEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class Herobrine6OnPlayerTouchProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            if (Math.random() <= 0.2D) {
                Level level;
                if (levelaccessor instanceof Level) {
                    level = (Level)levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "ob_place")), SoundSource.BLOCKS, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "ob_place")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^ ^-1 ^2 annoyingvillagers:shadow_obsidian",
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
                                        "execute as @s at @s anchored eyes run setblock ^ ^ ^2 annoyingvillagers:shadow_obsidian",
                                        entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {
                            }
                        }
                    }
                };
            }

            if (Math.random() <= 0.2D) {
                Level level;
                if (levelaccessor instanceof Level) {
                    level = (Level)levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "ob_place")), SoundSource.BLOCKS, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "ob_place")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                    }
                }
                new DelayedTask(1) {
                    public void run() {
                        Entity entity2 = entity;

                        if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                            try {
                                entity2.getServer().getCommands().getDispatcher().execute(
                                        "execute as @s at @s anchored eyes run setblock ^ ^-1 ^1 annoyingvillagers:shadow_obsidian",
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
                                                "execute as @s at @s anchored eyes run setblock ^ ^ ^1 annoyingvillagers:shadow_obsidian",
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
                                                        "execute as @s at @s anchored eyes run setblock ^ ^ ^2 annoyingvillagers:shadow_obsidian",
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
                                                                "execute as @s at @s anchored eyes run setblock ^ ^ ^3 annoyingvillagers:shadow_obsidian",
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
                                                                        "execute as @s at @s anchored eyes run setblock ^ ^ ^4 annoyingvillagers:shadow_obsidian",
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
                                                                                "execute as @s at @s anchored eyes run setblock ^ ^ ^5 annoyingvillagers:shadow_obsidian",
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
                                                                                        "execute as @s at @s anchored eyes run setblock ^ ^ ^6 annoyingvillagers:shadow_obsidian",
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
                                    level1.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "ob_place")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                } else {
                                    level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "ob_place")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                }
                            }

                            Entity entity2 = entity;

                            if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                try {
                                    entity2.getServer().getCommands().getDispatcher().execute(
                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^-1 annoyingvillagers:shadow_obsidian",
                                            entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {
                                }
                            }

                            entity2 = entity;
                            if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                try {
                                    entity2.getServer().getCommands().getDispatcher().execute(
                                            "execute as @s at @s anchored eyes run setblock ^ ^-1 ^-1 annoyingvillagers:shadow_obsidian",
                                            entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {
                                }
                            }

                            entity2 = entity;
                            if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                try {
                                    entity2.getServer().getCommands().getDispatcher().execute(
                                            "execute as @s at @s anchored eyes run setblock ^ ^1 ^-1 annoyingvillagers:shadow_obsidian",
                                            entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {
                                }
                            }

                            entity2 = entity;
                            if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                try {
                                    entity2.getServer().getCommands().getDispatcher().execute(
                                            "execute as @s at @s anchored eyes run setblock ^ ^1 ^ annoyingvillagers:shadow_obsidian",
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
                                                    "execute as @s at @s anchored eyes run setblock ^ ^1 ^1 annoyingvillagers:shadow_obsidian",
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
                                                            "execute as @s at @s anchored eyes run setblock ^ ^1 ^2 annoyingvillagers:shadow_obsidian",
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
                                                                    "execute as @s at @s anchored eyes run setblock ^ ^1 ^3 annoyingvillagers:shadow_obsidian",
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
                                                                            "execute as @s at @s anchored eyes run setblock ^ ^1 ^3 annoyingvillagers:shadow_obsidian",
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
                                                                                    "execute as @s at @s anchored eyes run setblock ^ ^1 ^3 annoyingvillagers:shadow_obsidian",
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
                                                                                            "execute as @s at @s anchored eyes run setblock ^ ^1 ^4 annoyingvillagers:shadow_obsidian",
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
                                                        "execute as @s at @s anchored eyes run setblock ^ ^ ^5 annoyingvillagers:shadow_obsidian",
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
                                                                "execute as @s at @s anchored eyes run setblock ^ ^ ^6 annoyingvillagers:shadow_obsidian",
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
            }
        }
    }
}
