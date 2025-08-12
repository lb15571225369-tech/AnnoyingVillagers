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

public class ArmoredHerobrineOnPlayerTouchProcedure {

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

            LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

            if (livingentitypatch != null) {
                DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();

                if (!(dynamicanimation instanceof LongHitAnimation) && !entity.getPersistentData().getBoolean("kick_x") && Math.random() <= 0.1D) {
                    Level level;

                    if (levelaccessor instanceof Level) {
                        level = (Level)levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")), SoundSource.BLOCKS, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                        }
                    }

                    if (levelaccessor instanceof Level) {
                        level = (Level)levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.stone.place")), SoundSource.BLOCKS, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.stone.place")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                        }
                    }

                    Level level1 = entity.level();
                    Projectile projectile;

                    if (!level1.isClientSide()) {
                        StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level1);
                        stealthAttackEntity.setOwner(entity);
                        stealthAttackEntity.setBaseDamage((double)21.0F);
                        stealthAttackEntity.setKnockback(3);
                        stealthAttackEntity.setSilent(true);
                        stealthAttackEntity.setPierceLevel((byte)3);
                        stealthAttackEntity.setPos(entity.getX(), entity.getEyeY() - 0.1D, entity.getZ());
                        stealthAttackEntity.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 1.0F, 0.0F);
                        level1.addFreshEntity(stealthAttackEntity);
                    }

                    LivingEntity livingentity;
                    ItemStack itemstack;
                    Player player;

                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity)entity;
                        itemstack = new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_BURST.get());
                        itemstack.setCount(1);
                        livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                        if (livingentity instanceof Player) {
                            player = (Player)livingentity;
                            player.getInventory().setChanged();
                        }
                    }

                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity)entity;
                        itemstack = new ItemStack((ItemLike)AnnoyingVillagersModItems.SHADOW_OBSIDIAN_STRAIGHT.get());
                        itemstack.setCount(1);
                        livingentity.setItemInHand(InteractionHand.OFF_HAND, itemstack);
                        if (livingentity instanceof Player) {
                            player = (Player)livingentity;
                            player.getInventory().setChanged();
                        }
                    }
                    new DelayedTask(7) {
                        public void run() {
                            LevelAccessor levelaccessor1 = levelaccessor;

                            if (levelaccessor1 instanceof Level) {
                                Level level2 = (Level)levelaccessor1;

                                if (!level2.isClientSide()) {
                                    level2.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                } else {
                                    level2.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                }
                            }

                            LivingEntity livingentity1;
                            ItemStack itemstack1;
                            Player player1;

                            if (entity instanceof LivingEntity) {
                                livingentity1 = (LivingEntity)entity;
                                itemstack1 = new ItemStack((ItemLike)AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get());
                                itemstack1.setCount(1);
                                livingentity1.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                                if (livingentity1 instanceof Player) {
                                    player1 = (Player)livingentity1;
                                    player1.getInventory().setChanged();
                                }
                            }

                            if (entity instanceof LivingEntity) {
                                livingentity1 = (LivingEntity)entity;
                                itemstack1 = new ItemStack(Blocks.AIR);
                                itemstack1.setCount(1);
                                livingentity1.setItemInHand(InteractionHand.OFF_HAND, itemstack1);
                                if (livingentity1 instanceof Player) {
                                    player1 = (Player)livingentity1;
                                    player1.getInventory().setChanged();
                                }
                            }
                        }
                    };
                    if (Math.random() <= 0.43D) {
                        if (levelaccessor instanceof Level) {
                            level = (Level)levelaccessor;
                            if (!level.isClientSide()) {
                                level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")), SoundSource.BLOCKS, 1.0F, 1.0F);
                            } else {
                                level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                            }
                        }

                        if (levelaccessor instanceof Level) {
                            level = (Level)levelaccessor;
                            if (!level.isClientSide()) {
                                level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.stone.place")), SoundSource.BLOCKS, 1.0F, 1.0F);
                            } else {
                                level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.stone.place")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                            }
                        }

                        level1 = entity.level();
                        if (!level1.isClientSide()) {
                            StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level1);
                            stealthAttackEntity.setOwner(entity);
                            stealthAttackEntity.setBaseDamage((double)23.0F);
                            stealthAttackEntity.setKnockback(5);
                            stealthAttackEntity.setSilent(true);
                            stealthAttackEntity.setPierceLevel((byte)3);
                            stealthAttackEntity.setPos(entity.getX(), entity.getEyeY() - 0.1D, entity.getZ());
                            stealthAttackEntity.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 1.0F, 0.0F);
                            level1.addFreshEntity(stealthAttackEntity);
                        }

                        level1 = entity.level();
                        if (!level1.isClientSide()) {
                            StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level1);
                            stealthAttackEntity.setOwner(entity);
                            stealthAttackEntity.setBaseDamage((double)20.0F);
                            stealthAttackEntity.setKnockback(5);
                            stealthAttackEntity.setSilent(true);
                            stealthAttackEntity.setPierceLevel((byte)3);
                            stealthAttackEntity.setPos(entity.getX(), entity.getEyeY() - 0.1D, entity.getZ());
                            stealthAttackEntity.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 1.0F, 0.0F);
                            level1.addFreshEntity(stealthAttackEntity);
                        }

                        if (entity instanceof LivingEntity) {
                            livingentity = (LivingEntity)entity;
                            itemstack = new ItemStack((ItemLike)AnnoyingVillagersModItems.SHADOW_OBSIDIAN_BURST.get());
                            itemstack.setCount(1);
                            livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                            if (livingentity instanceof Player) {
                                player = (Player)livingentity;
                                player.getInventory().setChanged();
                            }
                        }

                        if (entity instanceof LivingEntity) {
                            livingentity = (LivingEntity)entity;
                            itemstack = new ItemStack((ItemLike)AnnoyingVillagersModItems.SHADOW_OBSIDIAN_STRAIGHT.get());
                            itemstack.setCount(1);
                            livingentity.setItemInHand(InteractionHand.OFF_HAND, itemstack);
                            if (livingentity instanceof Player) {
                                player = (Player)livingentity;
                                player.getInventory().setChanged();
                            }
                        }
                        new DelayedTask(5) {
                            public void run() {
                                LevelAccessor levelaccessor1 = levelaccessor;
                                Level level2;

                                if (levelaccessor1 instanceof Level) {
                                    level2 = (Level)levelaccessor1;
                                    if (!level2.isClientSide()) {
                                        level2.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                    } else {
                                        level2.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                    }
                                }

                                levelaccessor1 = levelaccessor;
                                if (levelaccessor1 instanceof Level) {
                                    level2 = (Level)levelaccessor1;
                                    if (!level2.isClientSide()) {
                                        level2.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                    } else {
                                        level2.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                    }
                                }

                                levelaccessor1 = levelaccessor;
                                if (levelaccessor1 instanceof Level) {
                                    level2 = (Level)levelaccessor1;
                                    if (!level2.isClientSide()) {
                                        level2.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.stone.place")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                    } else {
                                        level2.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.stone.place")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                    }
                                }

                                LivingEntity livingentity1;
                                ItemStack itemstack1;
                                Player player1;

                                if (entity instanceof LivingEntity) {
                                    livingentity1 = (LivingEntity)entity;
                                    itemstack1 = new ItemStack((ItemLike)AnnoyingVillagersModItems.SHADOW_OBSIDIAN_STRAIGHT.get());
                                    itemstack1.setCount(1);
                                    livingentity1.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                                    if (livingentity1 instanceof Player) {
                                        player1 = (Player)livingentity1;
                                        player1.getInventory().setChanged();
                                    }
                                }

                                if (entity instanceof LivingEntity) {
                                    livingentity1 = (LivingEntity)entity;
                                    itemstack1 = new ItemStack((ItemLike)AnnoyingVillagersModItems.SHADOW_OBSIDIAN_BURST.get());
                                    itemstack1.setCount(1);
                                    livingentity1.setItemInHand(InteractionHand.OFF_HAND, itemstack1);
                                    if (livingentity1 instanceof Player) {
                                        player1 = (Player)livingentity1;
                                        player1.getInventory().setChanged();
                                    }
                                }

                                Entity entity1 = entity;
                                Level level3 = entity1.level();

                                if (!level3.isClientSide()) {
                                    StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level3);
                                    stealthAttackEntity.setOwner(entity1);
                                    stealthAttackEntity.setBaseDamage((double)19.0F);
                                    stealthAttackEntity.setKnockback(5);
                                    stealthAttackEntity.setSilent(true);
                                    stealthAttackEntity.setPierceLevel((byte)2);
                                    stealthAttackEntity.setPos(entity1.getX(), entity1.getEyeY() - 0.1D, entity1.getZ());
                                    stealthAttackEntity.shoot(entity1.getLookAngle().x, entity1.getLookAngle().y, entity1.getLookAngle().z, 1.0F, 0.0F);
                                    level3.addFreshEntity(stealthAttackEntity);
                                }

                                entity1 = entity;
                                level3 = entity1.level();
                                if (!level3.isClientSide()) {
                                    StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level3);
                                    stealthAttackEntity.setOwner(entity1);
                                    stealthAttackEntity.setBaseDamage((double)20.0F);
                                    stealthAttackEntity.setKnockback(5);
                                    stealthAttackEntity.setSilent(true);
                                    stealthAttackEntity.setPierceLevel((byte)3);
                                    stealthAttackEntity.setPos(entity1.getX(), entity1.getEyeY() - 0.1D, entity1.getZ());
                                    stealthAttackEntity.shoot(entity1.getLookAngle().x, entity1.getLookAngle().y, entity1.getLookAngle().z, 1.0F, 0.0F);
                                    level3.addFreshEntity(stealthAttackEntity);
                                }

                                new DelayedTask(2) {
                                    public void run() {
                                        LevelAccessor levelaccessor2 = levelaccessor;
                                        Level level4;

                                        if (levelaccessor2 instanceof Level) {
                                            level4 = (Level)levelaccessor2;
                                            if (!level4.isClientSide()) {
                                                level4.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                            } else {
                                                level4.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                            }
                                        }

                                        levelaccessor2 = levelaccessor;
                                        if (levelaccessor2 instanceof Level) {
                                            level4 = (Level)levelaccessor2;
                                            if (!level4.isClientSide()) {
                                                level4.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                            } else {
                                                level4.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                            }
                                        }

                                        levelaccessor2 = levelaccessor;
                                        if (levelaccessor2 instanceof Level) {
                                            level4 = (Level)levelaccessor2;
                                            if (!level4.isClientSide()) {
                                                level4.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.stone.place")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                            } else {
                                                level4.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.stone.place")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                            }
                                        }

                                        LivingEntity livingentity2;
                                        ItemStack itemstack2;
                                        Player player2;

                                        if (entity instanceof LivingEntity) {
                                            livingentity2 = (LivingEntity)entity;
                                            itemstack2 = new ItemStack((ItemLike)AnnoyingVillagersModItems.SHADOW_OBSIDIAN_BURST.get());
                                            itemstack2.setCount(1);
                                            livingentity2.setItemInHand(InteractionHand.MAIN_HAND, itemstack2);
                                            if (livingentity2 instanceof Player) {
                                                player2 = (Player)livingentity2;
                                                player2.getInventory().setChanged();
                                            }
                                        }

                                        if (entity instanceof LivingEntity) {
                                            livingentity2 = (LivingEntity)entity;
                                            itemstack2 = new ItemStack((ItemLike)AnnoyingVillagersModItems.SHADOW_OBSIDIAN_STRAIGHT.get());
                                            itemstack2.setCount(1);
                                            livingentity2.setItemInHand(InteractionHand.OFF_HAND, itemstack2);
                                            if (livingentity2 instanceof Player) {
                                                player2 = (Player)livingentity2;
                                                player2.getInventory().setChanged();
                                            }
                                        }

                                        Entity entity2 = entity;
                                        Level level5 = entity2.level();
                                        if (!level5.isClientSide()) {
                                            StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level5);
                                            stealthAttackEntity.setOwner(entity2);
                                            stealthAttackEntity.setBaseDamage((double)20.0F);
                                            stealthAttackEntity.setKnockback(5);
                                            stealthAttackEntity.setSilent(true);
                                            stealthAttackEntity.setPierceLevel((byte)3);
                                            stealthAttackEntity.setPos(entity2.getX(), entity2.getEyeY() - 0.1D, entity2.getZ());
                                            stealthAttackEntity.shoot(entity2.getLookAngle().x, entity2.getLookAngle().y, entity2.getLookAngle().z, 1.0F, 0.0F);
                                            level5.addFreshEntity(stealthAttackEntity);
                                        }

                                        entity2 = entity;
                                        level5 = entity2.level();
                                        if (!level5.isClientSide()) {
                                            StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level5);
                                            stealthAttackEntity.setOwner(entity2);
                                            stealthAttackEntity.setBaseDamage((double)23.0F);
                                            stealthAttackEntity.setKnockback(6);
                                            stealthAttackEntity.setSilent(true);
                                            stealthAttackEntity.setPierceLevel((byte)3);
                                            stealthAttackEntity.setPos(entity2.getX(), entity2.getEyeY() - 0.1D, entity2.getZ());
                                            stealthAttackEntity.shoot(entity2.getLookAngle().x, entity2.getLookAngle().y, entity2.getLookAngle().z, 1.0F, 0.0F);
                                            level5.addFreshEntity(stealthAttackEntity);
                                        }
                                        new DelayedTask(5) {
                                            @Override
                                            public void run() {
                                                LevelAccessor levelaccessor3 = levelaccessor;

                                                if (levelaccessor3 instanceof Level) {
                                                    Level level6 = (Level)levelaccessor3;

                                                    if (!level6.isClientSide()) {
                                                        level6.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                                    } else {
                                                        level6.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                                    }
                                                }

                                                LivingEntity livingentity3;
                                                ItemStack itemstack3;
                                                Player player3;

                                                if (entity instanceof LivingEntity) {
                                                    livingentity3 = (LivingEntity)entity;
                                                    itemstack3 = new ItemStack((ItemLike)AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get());
                                                    itemstack3.setCount(1);
                                                    livingentity3.setItemInHand(InteractionHand.MAIN_HAND, itemstack3);
                                                    if (livingentity3 instanceof Player) {
                                                        player3 = (Player)livingentity3;
                                                        player3.getInventory().setChanged();
                                                    }
                                                }

                                                if (entity instanceof LivingEntity) {
                                                    livingentity3 = (LivingEntity)entity;
                                                    itemstack3 = new ItemStack(Blocks.AIR);
                                                    itemstack3.setCount(1);
                                                    livingentity3.setItemInHand(InteractionHand.OFF_HAND, itemstack3);
                                                    if (livingentity3 instanceof Player) {
                                                        player3 = (Player)livingentity3;
                                                        player3.getInventory().setChanged();
                                                    }
                                                }
                                            }
                                        };
                                    }
                                };
                            }
                        };
                    }
                }
            }

        }
    }
}
