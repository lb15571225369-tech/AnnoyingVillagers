package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class SteveOnHurtProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            if (!entity.getPersistentData().getBoolean("kick_x")) {
                if (Math.random() <= 0.16D) {
                    new DelayedTask(100) {
                        public void run() {
                            if (Math.random() <= 0.12D) {
                                new DelayedTask(40) {
                                    public void run() {
                                        LivingEntity livingentity;

                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            ItemStack itemstack = new ItemStack((ItemLike) AnnoyingVillagersModItems.LEGENDARY_SWORD_MOB.get());

                                            itemstack.setCount(1);
                                            livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                                            if (livingentity instanceof Player) {
                                                Player player = (Player)livingentity;

                                                player.getInventory().setChanged();
                                            }
                                        }

                                        ItemStack itemstack1;

                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            itemstack1 = livingentity.getMainHandItem();
                                        } else {
                                            itemstack1 = ItemStack.EMPTY;
                                        }

                                        itemstack1.enchant(Enchantments.SMITE, 4);
                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            itemstack1 = livingentity.getMainHandItem();
                                        } else {
                                            itemstack1 = ItemStack.EMPTY;
                                        }

                                        itemstack1.enchant(Enchantments.SHARPNESS, 4);
                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            itemstack1 = livingentity.getMainHandItem();
                                        } else {
                                            itemstack1 = ItemStack.EMPTY;
                                        }

                                        itemstack1.enchant(Enchantments.SWEEPING_EDGE, 4);
                                        
                                    }
                                };
                            } else if (Math.random() <= 0.14D) {
                                new DelayedTask(40) {
                                    public void run() {
                                        LivingEntity livingentity;

                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            ItemStack itemstack = new ItemStack((ItemLike)AnnoyingVillagersModItems.CRAFTING_TABLE.get());

                                            itemstack.setCount(1);
                                            livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                                            if (livingentity instanceof Player) {
                                                Player player = (Player)livingentity;

                                                player.getInventory().setChanged();
                                            }
                                        }

                                        ItemStack itemstack1;

                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            itemstack1 = livingentity.getMainHandItem();
                                        } else {
                                            itemstack1 = ItemStack.EMPTY;
                                        }

                                        itemstack1.enchant(Enchantments.SMITE, 4);
                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            itemstack1 = livingentity.getMainHandItem();
                                        } else {
                                            itemstack1 = ItemStack.EMPTY;
                                        }

                                        itemstack1.enchant(Enchantments.SHARPNESS, 4);
                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            itemstack1 = livingentity.getMainHandItem();
                                        } else {
                                            itemstack1 = ItemStack.EMPTY;
                                        }

                                        itemstack1.enchant(Enchantments.SWEEPING_EDGE, 4);
                                        
                                    }
                                };
                            } else if (Math.random() <= 0.1D) {
                                new DelayedTask(40) {
                                    public void run() {
                                        LivingEntity livingentity;

                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            ItemStack itemstack = new ItemStack(Items.DIAMOND_SWORD);

                                            itemstack.setCount(1);
                                            livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                                            if (livingentity instanceof Player) {
                                                Player player = (Player)livingentity;

                                                player.getInventory().setChanged();
                                            }
                                        }

                                        ItemStack itemstack1;

                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            itemstack1 = livingentity.getMainHandItem();
                                        } else {
                                            itemstack1 = ItemStack.EMPTY;
                                        }

                                        itemstack1.enchant(Enchantments.SHARPNESS, 7);
                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            itemstack1 = livingentity.getMainHandItem();
                                        } else {
                                            itemstack1 = ItemStack.EMPTY;
                                        }

                                        itemstack1.enchant(Enchantments.SWEEPING_EDGE, 4);
                                        
                                    }
                                };
                            } else if (Math.random() <= 0.1D) {
                                new DelayedTask(40) {
                                    public void run() {
                                        LivingEntity livingentity;

                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            ItemStack itemstack = new ItemStack((ItemLike)AnnoyingVillagersModItems.DIAMOND_GREAT_SWORD.get());

                                            itemstack.setCount(1);
                                            livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                                            if (livingentity instanceof Player) {
                                                Player player = (Player)livingentity;

                                                player.getInventory().setChanged();
                                            }
                                        }

                                        ItemStack itemstack1;

                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            itemstack1 = livingentity.getMainHandItem();
                                        } else {
                                            itemstack1 = ItemStack.EMPTY;
                                        }

                                        itemstack1.enchant(Enchantments.SHARPNESS, 7);
                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            itemstack1 = livingentity.getMainHandItem();
                                        } else {
                                            itemstack1 = ItemStack.EMPTY;
                                        }

                                        itemstack1.enchant(Enchantments.SWEEPING_EDGE, 4);
                                        
                                    }
                                };
                            } else if (Math.random() <= 0.1D) {
                                new DelayedTask(40) {
                                    public void run() {
                                        LivingEntity livingentity;

                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            ItemStack itemstack = new ItemStack((ItemLike)AnnoyingVillagersModItems.ENCHANTED_WOODEN_DOOR.get());

                                            itemstack.setCount(1);
                                            livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                                            if (livingentity instanceof Player) {
                                                Player player = (Player)livingentity;

                                                player.getInventory().setChanged();
                                            }
                                        }

                                        ItemStack itemstack1;

                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            itemstack1 = livingentity.getMainHandItem();
                                        } else {
                                            itemstack1 = ItemStack.EMPTY;
                                        }

                                        itemstack1.enchant(Enchantments.SMITE, 4);
                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            itemstack1 = livingentity.getMainHandItem();
                                        } else {
                                            itemstack1 = ItemStack.EMPTY;
                                        }

                                        itemstack1.enchant(Enchantments.SHARPNESS, 4);
                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            itemstack1 = livingentity.getMainHandItem();
                                        } else {
                                            itemstack1 = ItemStack.EMPTY;
                                        }

                                        itemstack1.enchant(Enchantments.SWEEPING_EDGE, 4);
                                        
                                    }
                                };
                            } else if (Math.random() <= 0.3D) {
                                new DelayedTask(40) {
                                    public void run() {
                                        LivingEntity livingentity;

                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            ItemStack itemstack = new ItemStack(Items.DIAMOND_SWORD);

                                            itemstack.setCount(1);
                                            livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                                            if (livingentity instanceof Player) {
                                                Player player = (Player)livingentity;

                                                player.getInventory().setChanged();
                                            }
                                        }

                                        ItemStack itemstack1;

                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            itemstack1 = livingentity.getMainHandItem();
                                        } else {
                                            itemstack1 = ItemStack.EMPTY;
                                        }

                                        itemstack1.enchant(Enchantments.SHARPNESS, 7);
                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            itemstack1 = livingentity.getMainHandItem();
                                        } else {
                                            itemstack1 = ItemStack.EMPTY;
                                        }

                                        itemstack1.enchant(Enchantments.SWEEPING_EDGE, 4);
                                        
                                    }
                                };
                            } else if (Math.random() <= 0.7D) {
                                new DelayedTask(20) {
                                    public void run() {
                                        LivingEntity livingentity;

                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            ItemStack itemstack = new ItemStack((ItemLike)AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get());

                                            itemstack.setCount(1);
                                            livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                                            if (livingentity instanceof Player) {
                                                Player player = (Player)livingentity;

                                                player.getInventory().setChanged();
                                            }
                                        }

                                        ItemStack itemstack1;

                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            itemstack1 = livingentity.getMainHandItem();
                                        } else {
                                            itemstack1 = ItemStack.EMPTY;
                                        }

                                        itemstack1.enchant(Enchantments.SMITE, 4);
                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            itemstack1 = livingentity.getMainHandItem();
                                        } else {
                                            itemstack1 = ItemStack.EMPTY;
                                        }

                                        itemstack1.enchant(Enchantments.SHARPNESS, 4);
                                        if (entity instanceof LivingEntity) {
                                            livingentity = (LivingEntity)entity;
                                            itemstack1 = livingentity.getMainHandItem();
                                        } else {
                                            itemstack1 = ItemStack.EMPTY;
                                        }

                                        itemstack1.enchant(Enchantments.SWEEPING_EDGE, 4);
                                    }
                                };
                            }
                        }
                    };
                }

                Level level;

                if (Math.random() <= 0.0098D) {
                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<Steve> Why?"), false);
                    }

                    if (levelaccessor instanceof Level) {
                        level = (Level)levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevesaywhy")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevesaywhy")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                        }
                    }
                }

                if (Math.random() <= 0.09D) {
                    if (Math.random() <= 0.03D && levelaccessor instanceof Level) {
                        level = (Level)levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "steveattack")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "steveattack")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                        }
                    }
                    new DelayedTask(20) {
                        public void run() {
                            Entity entity1 = entity;

                            entity1.setYRot(0.0F);
                            entity1.setXRot((float)Mth.nextDouble(AnnoyingVillagers.randomSource, -90.0D, -180.0D));
                            entity1.setYBodyRot(entity1.getYRot());
                            entity1.setYHeadRot(entity1.getYRot());
                            entity1.yRotO = entity1.getYRot();
                            entity1.xRotO = entity1.getXRot();
                            if (entity1 instanceof LivingEntity) {
                                LivingEntity livingentity = (LivingEntity)entity1;

                                livingentity.yBodyRotO = livingentity.getYRot();
                                livingentity.yHeadRotO = livingentity.getYRot();
                            }

                            entity1 = entity;
                            Level level1 = entity1.level();

                            if (!level1.isClientSide()) {
                                Projectile projectile = new ThrownEnderpearl(EntityType.ENDER_PEARL, level1);
                                projectile.setOwner(entity1);
                                projectile.setPos(entity1.getX(), entity1.getEyeY() - 0.1D, entity1.getZ());
                                projectile.shoot(entity1.getLookAngle().x, entity1.getLookAngle().y, entity1.getLookAngle().z, 2.0F, 0.0F);
                                level1.addFreshEntity(projectile);
                            }

                            if (Math.random() <= 0.2D) {
                                new DelayedTask(40) {
                                    public void run() {
                                        Entity entity2 = entity;
                                        Level level2 = entity2.level();

                                        if (!level2.isClientSide()) {
                                            Projectile projectile1 = new ThrownEnderpearl(EntityType.ENDER_PEARL, level2);
                                            projectile1.setOwner(entity2);
                                            projectile1.setPos(entity2.getX(), entity2.getEyeY() - 0.1D, entity2.getZ());
                                            projectile1.shoot(entity2.getLookAngle().x, entity2.getLookAngle().y, entity2.getLookAngle().z, 1.8F, 0.0F);
                                            level2.addFreshEntity(projectile1);
                                        }

                                        LevelAccessor levelaccessor1 = levelaccessor;

                                        if (levelaccessor1 instanceof Level) {
                                            Level level3 = (Level)levelaccessor1;

                                            if (!level3.isClientSide()) {
                                                level3.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevechuanqi")), SoundSource.NEUTRAL, 0.4F, 1.0F);
                                            } else {
                                                level3.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevechuanqi")), SoundSource.NEUTRAL, 0.4F, 1.0F, false);
                                            }
                                        }
                                    }
                                };
                            }
                        }
                    };
                }

                if (Math.random() <= 0.05D && levelaccessor instanceof Level) {
                    level = (Level)levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevechuanqi")), SoundSource.NEUTRAL, 0.4F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevechuanqi")), SoundSource.NEUTRAL, 0.4F, 1.0F, false);
                    }
                }

                if (Math.random() <= 0.09D) {
                    new DelayedTask(20) {
                        public void run() {
                            Entity entity1 = entity;

                            entity1.setYRot(0.0F);
                            entity1.setXRot(180.0F);
                            entity1.setYBodyRot(entity1.getYRot());
                            entity1.setYHeadRot(entity1.getYRot());
                            entity1.yRotO = entity1.getYRot();
                            entity1.xRotO = entity1.getXRot();
                            if (entity1 instanceof LivingEntity) {
                                LivingEntity livingentity = (LivingEntity)entity1;

                                livingentity.yBodyRotO = livingentity.getYRot();
                                livingentity.yHeadRotO = livingentity.getYRot();
                            }

                            entity1 = entity;
                            Level level1 = entity1.level();

                            if (!level1.isClientSide()) {
                                Projectile projectile = new ThrownEnderpearl(EntityType.ENDER_PEARL, level1);
                                projectile.setOwner(entity1);
                                projectile.setPos(entity1.getX(), entity1.getEyeY() - 0.1D, entity1.getZ());
                                projectile.shoot(entity1.getLookAngle().x, entity1.getLookAngle().y, entity1.getLookAngle().z, 2.0F, 0.0F);
                                level1.addFreshEntity(projectile);
                            }

                            LivingEntity livingentity1;

                            if (entity instanceof LivingEntity) {
                                livingentity1 = (LivingEntity)entity;
                                ItemStack itemstack = new ItemStack(Items.BOW);

                                itemstack.setCount(1);
                                livingentity1.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                                if (livingentity1 instanceof Player) {
                                    Player player = (Player)livingentity1;

                                    player.getInventory().setChanged();
                                }
                            }

                            ItemStack itemstack1;

                            if (entity instanceof LivingEntity) {
                                livingentity1 = (LivingEntity)entity;
                                itemstack1 = livingentity1.getMainHandItem();
                            } else {
                                itemstack1 = ItemStack.EMPTY;
                            }

                            itemstack1.enchant(Enchantments.POWER_ARROWS, 5);
                            if (entity instanceof LivingEntity) {
                                livingentity1 = (LivingEntity)entity;
                                itemstack1 = livingentity1.getMainHandItem();
                            } else {
                                itemstack1 = ItemStack.EMPTY;
                            }

                            itemstack1.enchant(Enchantments.PUNCH_ARROWS, 5);
                            new DelayedTask(20) {
                                @Override
                                public void run() {
                                    if (entity instanceof LivingEntity) {
                                        LivingEntity livingentity2 = (LivingEntity)entity;

                                        if (!livingentity2.level().isClientSide()) {
                                            livingentity2.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 2, false, false));
                                        }
                                    }
                                }
                            };
                            new DelayedTask(80) {
                                public void run() {
                                    Entity entity2 = entity;

                                    entity2.setYRot(0.0F);
                                    entity2.setXRot(180.0F);
                                    entity2.setYBodyRot(entity2.getYRot());
                                    entity2.setYHeadRot(entity2.getYRot());
                                    entity2.yRotO = entity2.getYRot();
                                    entity2.xRotO = entity2.getXRot();
                                    if (entity2 instanceof LivingEntity) {
                                        LivingEntity livingentity2 = (LivingEntity)entity2;

                                        livingentity2.yBodyRotO = livingentity2.getYRot();
                                        livingentity2.yHeadRotO = livingentity2.getYRot();
                                    }

                                    LevelAccessor levelaccessor1 = levelaccessor;

                                    if (levelaccessor1 instanceof Level) {
                                        Level level2 = (Level)levelaccessor1;

                                        if (!level2.isClientSide()) {
                                            level2.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevechuanqi")), SoundSource.NEUTRAL, 0.4F, 1.0F);
                                        } else {
                                            level2.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevechuanqi")), SoundSource.NEUTRAL, 0.4F, 1.0F, false);
                                        }
                                    }

                                    entity2 = entity;
                                    Level level3 = entity2.level();

                                    if (!level3.isClientSide()) {
                                        Projectile projectile1 = new ThrownEnderpearl(EntityType.ENDER_PEARL, level3);
                                        projectile1.setOwner(entity2);
                                        projectile1.setPos(entity2.getX(), entity2.getEyeY() - 0.1D, entity2.getZ());
                                        projectile1.shoot(entity2.getLookAngle().x, entity2.getLookAngle().y, entity2.getLookAngle().z, 2.0F, 0.0F);
                                        level3.addFreshEntity(projectile1);
                                    }

                                    LivingEntity livingentity3;

                                    if (entity instanceof LivingEntity) {
                                        livingentity3 = (LivingEntity)entity;
                                        ItemStack itemstack2 = new ItemStack((ItemLike)AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get());

                                        itemstack2.setCount(1);
                                        livingentity3.setItemInHand(InteractionHand.MAIN_HAND, itemstack2);
                                        if (livingentity3 instanceof Player) {
                                            Player player1 = (Player)livingentity3;

                                            player1.getInventory().setChanged();
                                        }
                                    }

                                    ItemStack itemstack3;

                                    if (entity instanceof LivingEntity) {
                                        livingentity3 = (LivingEntity)entity;
                                        itemstack3 = livingentity3.getMainHandItem();
                                    } else {
                                        itemstack3 = ItemStack.EMPTY;
                                    }

                                    itemstack3.enchant(Enchantments.SHARPNESS, 5);
                                    if (entity instanceof LivingEntity) {
                                        livingentity3 = (LivingEntity)entity;
                                        itemstack3 = livingentity3.getMainHandItem();
                                    } else {
                                        itemstack3 = ItemStack.EMPTY;
                                    }

                                    itemstack3.enchant(Enchantments.MENDING, 5);

                                }
                            };
                        }
                    };
                }

                LivingEntity livingentity;
                ItemStack itemstack;

                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                if (itemstack.getItem() == Blocks.AIR.asItem()) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingentity1 = (LivingEntity)entity;

                        itemstack = livingentity1.getItemBySlot(EquipmentSlot.CHEST);
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    if (itemstack.getItem() == Blocks.AIR.asItem()) {
                        new DelayedTask(5) {
                            public void run() {
                                Entity entity1 = entity;

                                if (entity1 instanceof Player) {
                                    Player player = (Player)entity1;

                                    player.getInventory().armor.set(3, new ItemStack((ItemLike)AnnoyingVillagersModItems.COMPRESSED_DIAMOND_HELMET.get()));
                                    player.getInventory().setChanged();
                                } else if (entity1 instanceof LivingEntity) {
                                    LivingEntity livingentity2 = (LivingEntity)entity1;

                                    livingentity2.setItemSlot(EquipmentSlot.HEAD, new ItemStack((ItemLike)AnnoyingVillagersModItems.COMPRESSED_DIAMOND_HELMET.get()));
                                }

                                LevelAccessor levelaccessor1 = levelaccessor;

                                if (levelaccessor1 instanceof Level) {
                                    Level level1 = (Level)levelaccessor1;

                                    if (!level1.isClientSide()) {
                                        level1.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_diamond")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                    } else {
                                        level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_diamond")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                    }
                                }
                                new DelayedTask(10) {
                                    public void run() {
                                        Entity entity2 = entity;

                                        if (entity2 instanceof Player) {
                                            Player player1 = (Player)entity2;

                                            player1.getInventory().armor.set(2, new ItemStack((ItemLike)AnnoyingVillagersModItems.COMPRESSED_DIAMOND_CHESTPLATE.get()));
                                            player1.getInventory().setChanged();
                                        } else if (entity2 instanceof LivingEntity) {
                                            LivingEntity livingentity3 = (LivingEntity)entity2;

                                            livingentity3.setItemSlot(EquipmentSlot.CHEST, new ItemStack((ItemLike)AnnoyingVillagersModItems.COMPRESSED_DIAMOND_CHESTPLATE.get()));
                                        }

                                        LevelAccessor levelaccessor2 = levelaccessor;

                                        if (levelaccessor2 instanceof Level) {
                                            Level level2 = (Level)levelaccessor2;

                                            if (!level2.isClientSide()) {
                                                level2.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_diamond")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                            } else {
                                                level2.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_diamond")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                            }
                                        }

                                        if (entity instanceof LivingEntity) {
                                            LivingEntity livingentity4 = (LivingEntity)entity;
                                            livingentity4.removeEffect(MobEffects.DAMAGE_RESISTANCE);
                                        }
                                    }
                                };
                            }
                        };
                    }
                }

                if (Math.random() <= 0.009D) {
                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<Steve> Why do we have to keep fighting?"), false);
                    }

                    if (levelaccessor instanceof Level) {
                        level = (Level)levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevesaywhyfighting")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevesaywhyfighting")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                        }
                    }
                }

                if (Math.random() <= 0.01D) {
                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<Steve> Why?"), false);
                    }

                    if (levelaccessor instanceof Level) {
                        level = (Level)levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevesaywhy")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevesaywhy")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                        }
                    }
                }

                if (Math.random() <= 0.5D && entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    if (!livingentity.level().isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 1, 1, false, false));
                    }
                }

                if (Math.random() <= 0.37D && entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    if (!livingentity.level().isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.ESCAPE.get(), 1, 1, false, false));
                    }
                }
                new DelayedTask(80) {
                    @Override
                    public void run() {
                        if (entity instanceof LivingEntity) {
                            LivingEntity livingentity2 = (LivingEntity)entity;
                            ItemStack itemstack1 = new ItemStack(Items.ENDER_PEARL);

                            itemstack1.setCount(1);
                            livingentity2.setItemInHand(InteractionHand.OFF_HAND, itemstack1);
                            if (livingentity2 instanceof Player) {
                                Player player = (Player)livingentity2;

                                player.getInventory().setChanged();
                            }
                        }
                    }
                };
            }

        }
    }
}

