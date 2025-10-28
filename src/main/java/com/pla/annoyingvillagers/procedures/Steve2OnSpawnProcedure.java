package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class Steve2OnSpawnProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
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

            Level level;

            if (levelaccessor instanceof Level) {
                level = (Level)levelaccessor;
                if (!level.isClientSide()) {
                    level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "pop")), SoundSource.BLOCKS, 1.0F, 1.0F);
                } else {
                    level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "pop")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }
            }

            if (levelaccessor instanceof Level) {
                level = (Level)levelaccessor;
                if (!level.isClientSide()) {
                    level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevesayiwanttuseuthere")), SoundSource.BLOCKS, 1.0F, 1.0F);
                } else {
                    level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevesayiwanttuseuthere")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }
            }

            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity.getDisplayName().getString() + "> I can't believe I'm using it here."), false);
            }
            new DelayedTask(20) {
                public void run() {
                    Entity entity1 = entity;

                    if (entity1 instanceof Player) {
                        Player player1 = (Player)entity1;

                        player1.getInventory().armor.set(3, new ItemStack((ItemLike)AnnoyingVillagersModItems.COMPRESSED_DIAMOND_HELMET.get()));
                        player1.getInventory().setChanged();
                    } else if (entity1 instanceof LivingEntity) {
                        LivingEntity livingentity1 = (LivingEntity)entity1;

                        livingentity1.setItemSlot(EquipmentSlot.HEAD, new ItemStack((ItemLike)AnnoyingVillagersModItems.COMPRESSED_DIAMOND_HELMET.get()));
                    }

                    LevelAccessor levelaccessor1 = levelaccessor;

                    if (levelaccessor1 instanceof Level) {
                        Level level1 = (Level)levelaccessor1;

                        if (!level1.isClientSide()) {
                            level1.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_diamond")), SoundSource.BLOCKS, 1.0F, 1.0F);
                        } else {
                            level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_diamond")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                        }
                    }

                    LivingEntity livingentity2;
                    ItemStack itemstack1;

                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity)entity;
                        itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.HEAD);
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    itemstack1.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 7);
                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity)entity;
                        itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.HEAD);
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    itemstack1.enchant(Enchantments.MENDING, 5);
                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity)entity;
                        itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.HEAD);
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    itemstack1.enchant(Enchantments.UNBREAKING, 5);
                    new DelayedTask(20) {
                        public void run() {
                            LevelAccessor levelaccessor2 = levelaccessor;

                            if (levelaccessor2 instanceof Level) {
                                Level level2 = (Level)levelaccessor2;

                                if (!level2.isClientSide()) {
                                    level2.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_diamond")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                } else {
                                    level2.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_diamond")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                }
                            }

                            Entity entity2 = entity;

                            if (entity2 instanceof Player) {
                                Player player2 = (Player)entity2;

                                player2.getInventory().armor.set(2, new ItemStack((ItemLike)AnnoyingVillagersModItems.COMPRESSED_DIAMOND_CHESTPLATE.get()));
                                player2.getInventory().setChanged();
                            } else if (entity2 instanceof LivingEntity) {
                                LivingEntity livingentity3 = (LivingEntity)entity2;

                                livingentity3.setItemSlot(EquipmentSlot.CHEST, new ItemStack((ItemLike)AnnoyingVillagersModItems.COMPRESSED_DIAMOND_CHESTPLATE.get()));
                            }

                            LivingEntity livingentity4;
                            ItemStack itemstack2;

                            if (entity instanceof LivingEntity) {
                                livingentity4 = (LivingEntity)entity;
                                itemstack2 = livingentity4.getItemBySlot(EquipmentSlot.CHEST);
                            } else {
                                itemstack2 = ItemStack.EMPTY;
                            }

                            itemstack2.enchant(Enchantments.MENDING, 5);
                            if (entity instanceof LivingEntity) {
                                livingentity4 = (LivingEntity)entity;
                                itemstack2 = livingentity4.getItemBySlot(EquipmentSlot.CHEST);
                            } else {
                                itemstack2 = ItemStack.EMPTY;
                            }

                            itemstack2.enchant(Enchantments.UNBREAKING, 5);
                            if (entity instanceof LivingEntity) {
                                livingentity4 = (LivingEntity)entity;
                                itemstack2 = livingentity4.getItemBySlot(EquipmentSlot.CHEST);
                            } else {
                                itemstack2 = ItemStack.EMPTY;
                            }

                            itemstack2.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 10);
                        }
                    };
                }
            };
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 999999, 0, false, false));
                }
            }

            ItemStack itemstack1;

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                itemstack1 = livingentity.getMainHandItem();
            } else {
                itemstack1 = ItemStack.EMPTY;
            }

            itemstack1.enchant(Enchantments.UNBREAKING, 5);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                itemstack1 = livingentity.getMainHandItem();
            } else {
                itemstack1 = ItemStack.EMPTY;
            }

            itemstack1.enchant(Enchantments.SHARPNESS, 5);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                itemstack1 = livingentity.getMainHandItem();
            } else {
                itemstack1 = ItemStack.EMPTY;
            }

            itemstack1.enchant(Enchantments.KNOCKBACK, 1);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                itemstack1 = livingentity.getMainHandItem();
            } else {
                itemstack1 = ItemStack.EMPTY;
            }

            itemstack1.enchant(Enchantments.MENDING, 3);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                itemstack1 = livingentity.getMainHandItem();
            } else {
                itemstack1 = ItemStack.EMPTY;
            }

            itemstack1.enchant(Enchantments.MOB_LOOTING, 3);
            entity.getPersistentData().putBoolean("a_player", true);
        }
    }
}
