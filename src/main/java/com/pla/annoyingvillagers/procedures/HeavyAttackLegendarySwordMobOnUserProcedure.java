package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class HeavyAttackLegendarySwordMobOnUserProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            ItemStack itemstack;

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;

                itemstack = livingentity.getMainHandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            if (itemstack.getItem() == AnnoyingVillagersModItems.HEAVY_ATTACK_LEGENDARY_SWORD_MOB.get() && entity.isShiftKeyDown()) {
                float f;

                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity1 = (LivingEntity)entity;

                    f = livingentity1.getHealth();
                } else {
                    f = -1.0F;
                }

                LivingEntity livingentity2;
                ItemStack itemstack1;
                Player player;

                if (f <= 19.0F) {
                    entity.setDeltaMovement(new Vec3(0.0D, 2.0D, 0.0D));
                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity)entity;
                        if (!livingentity2.level().isClientSide()) {
                            livingentity2.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 50, 4, false, false));
                        }
                    }

                    entity.setYRot(0.0F);
                    entity.setXRot(80.0F);
                    entity.setYBodyRot(entity.getYRot());
                    entity.setYHeadRot(entity.getYRot());
                    entity.yRotO = entity.getYRot();
                    entity.xRotO = entity.getXRot();
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingentity3 = (LivingEntity)entity;

                        livingentity3.yBodyRotO = livingentity3.getYRot();
                        livingentity3.yHeadRotO = livingentity3.getYRot();
                    }

                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity)entity;
                        itemstack1 = new ItemStack((ItemLike)AnnoyingVillagersModItems.HEAVY_ATTACK_LEGENDARY_SWORD_MOB_AWAKENED.get());
                        itemstack1.setCount(1);
                        livingentity2.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                        if (livingentity2 instanceof Player) {
                            player = (Player)livingentity2;
                            player.getInventory().setChanged();
                        }
                    }

                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity)entity;
                        livingentity2.swing(InteractionHand.MAIN_HAND, true);
                    }

                    levelaccessor.addParticle(ParticleTypes.EXPLOSION_EMITTER, d0, d1, d2, 1.0D, 2.0D, 2.0D);
                    levelaccessor.addParticle(ParticleTypes.EXPLOSION_EMITTER, d0, d1, d2, 1.0D, 2.0D, 2.0D);
                    Level level;

                    if (levelaccessor instanceof Level) {
                        level = (Level)levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_dragon.flap")), SoundSource.BLOCKS, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_dragon.flap")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                        }
                    }

                    levelaccessor.addParticle(ParticleTypes.EXPLOSION, d0, d1, d2, 0.0D, 2.0D, 1.0D);
                    levelaccessor.addParticle(ParticleTypes.TOTEM_OF_UNDYING, d0, d1, d2, 1.0D, 2.0D, 0.0D);
                    levelaccessor.addParticle(ParticleTypes.TOTEM_OF_UNDYING, d0, d1, d2, 0.0D, 2.0D, 1.0D);
                    levelaccessor.addParticle(ParticleTypes.TOTEM_OF_UNDYING, d0, d1, d2, 0.0D, 2.0D, 0.0D);
                    levelaccessor.addParticle(ParticleTypes.TOTEM_OF_UNDYING, d0, d1, d2, 0.0D, 1.0D, 0.0D);
                    levelaccessor.addParticle(ParticleTypes.TOTEM_OF_UNDYING, d0, d1, d2, 0.0D, 2.0D, 1.0D);
                    if (levelaccessor instanceof Level) {
                        level = (Level)levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.big_fall")), SoundSource.BLOCKS, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.big_fall")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                        }
                    }

                    levelaccessor.addParticle(ParticleTypes.TOTEM_OF_UNDYING, d0, d1, d2, 0.0D, 2.0D, 1.0D);
                    levelaccessor.addParticle(ParticleTypes.TOTEM_OF_UNDYING, d0, d1, d2, 0.0D, 2.0D, 1.0D);
                    levelaccessor.addParticle(ParticleTypes.TOTEM_OF_UNDYING, d0, d1, d2, 0.0D, 2.0D, 1.0D);
                    if (levelaccessor instanceof Level) {
                        level = (Level)levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.deepslate_bricks.break")), SoundSource.BLOCKS, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.deepslate_bricks.break")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                        }
                    }

                    if (levelaccessor instanceof Level) {
                        level = (Level)levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "zhanshenzhirenjuexing")), SoundSource.BLOCKS, 2.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "zhanshenzhirenjuexing")), SoundSource.BLOCKS, 2.0F, 1.0F, false);
                        }
                    }

                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        try {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "execute as @s run execute if entity @s run title @s actionbar {\"text\":\"wake up!\",\"italic\":true,\"color\":\"yellow\"}",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                        }
                    }

                    levelaccessor.levelEvent(2001, new BlockPos((int) d0, (int) d1, (int) d2), Block.getId(Blocks.DIAMOND_BLOCK.defaultBlockState()));
                    levelaccessor.levelEvent(2001, new BlockPos((int) d0, (int) d1, (int) d2), Block.getId(Blocks.GOLD_BLOCK.defaultBlockState()));
                    levelaccessor.levelEvent(2001, new BlockPos((int) d0, (int) d1, (int) d2), Block.getId(Blocks.HORN_CORAL.defaultBlockState()));
                    new DelayedTask(50) {
                        public void run() {
                            levelaccessor.addParticle(ParticleTypes.TOTEM_OF_UNDYING, d0, d1, d2, 0.0D, 2.0D, 0.0D);
                            LevelAccessor levelaccessor1 = levelaccessor;
                            Level level1;

                            if (levelaccessor1 instanceof Level) {
                                level1 = (Level)levelaccessor1;
                                if (!level1.isClientSide()) {
                                    level1.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                } else {
                                    level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                }
                            }

                            levelaccessor.addParticle(ParticleTypes.TOTEM_OF_UNDYING, d0, d1, d2, 0.0D, 2.0D, 1.0D);
                            levelaccessor.addParticle(ParticleTypes.TOTEM_OF_UNDYING, d0, d1, d2, 0.0D, 1.0D, 0.0D);
                            levelaccessor1 = levelaccessor;
                            if (levelaccessor1 instanceof Level) {
                                level1 = (Level)levelaccessor1;
                                if (!level1.isClientSide()) {
                                    level1.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.deepslate_bricks.break")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                } else {
                                    level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.deepslate_bricks.break")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                }
                            }

                            levelaccessor.levelEvent(2001, new BlockPos((int) d0, (int) d1, (int) d2), Block.getId(Blocks.GOLD_BLOCK.defaultBlockState()));
                            levelaccessor.levelEvent(2001, new BlockPos((int) d0, (int) d1, (int) d2), Block.getId(Blocks.HORN_CORAL.defaultBlockState()));
                            Player player1;
                            ItemStack itemstack2;

                            if (entity instanceof Player) {
                                player1 = (Player)entity;
                                itemstack2 = new ItemStack((ItemLike)AnnoyingVillagersModItems.HEAVY_ATTACK_LEGENDARY_SWORD_MOB_AWAKENED.get());
                                ItemStack finalItemstack = itemstack2;
                                player1.getInventory().clearOrCountMatchingItems((itemstack3) -> {
                                    return finalItemstack.getItem() == itemstack3.getItem();
                                }, 1, player1.inventoryMenu.getCraftSlots());
                            }

                            if (entity instanceof Player) {
                                player1 = (Player)entity;
                                itemstack2 = new ItemStack((ItemLike)AnnoyingVillagersModItems.HEAVY_ATTACK_LEGENDARY_SWORD_MOB.get());
                                itemstack2.setCount(1);
                                ItemHandlerHelper.giveItemToPlayer(player1, itemstack2);
                            }

                            ItemStack itemstack3;

                            if (entity instanceof LivingEntity) {
                                LivingEntity livingentity4 = (LivingEntity)entity;

                                itemstack3 = livingentity4.getMainHandItem();
                            } else {
                                itemstack3 = ItemStack.EMPTY;
                            }

                            ItemStack itemstack4 = itemstack3;

                            if (itemstack4.hurt(100, AnnoyingVillagers.randomSource, (ServerPlayer)null)) {
                                itemstack4.shrink(1);
                                itemstack4.setDamageValue(0);
                            }
                        }
                    };
                } else if (entity instanceof LivingEntity) {
                    livingentity2 = (LivingEntity)entity;
                    itemstack1 = new ItemStack((ItemLike)AnnoyingVillagersModItems.LEGENDARY_SWORD_MOB.get());
                    itemstack1.setCount(1);
                    livingentity2.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                    if (livingentity2 instanceof Player) {
                        player = (Player)livingentity2;
                        player.getInventory().setChanged();
                    }
                }
            }

        }
    }
}

