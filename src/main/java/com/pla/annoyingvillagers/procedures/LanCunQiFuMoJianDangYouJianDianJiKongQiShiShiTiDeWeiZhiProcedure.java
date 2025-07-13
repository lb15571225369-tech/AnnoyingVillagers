package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class LanCunQiFuMoJianDangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (entity.isSprinting()) {
                if (itemstack.getOrCreateTag().getDouble("woopie_dash") >= 1.0D) {
                    ItemStack itemstack1;

                    if (entity instanceof LivingEntity) {
                        LivingEntity livingentity = (LivingEntity)entity;

                        itemstack1 = livingentity.getMainHandItem();
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    Player player;

                    if (itemstack1.getItem() == AnnoyingVillagersModItems.LAN_CUN_QI_FU_MO_JIAN.get()) {
                        itemstack.getOrCreateTag().putDouble("woopie_dash", itemstack.getOrCreateTag().getDouble("woopie_dash") - 1.0D);
                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                            entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoyingvillagers:biped/combat/rush_sword\" 0 1");
                        }
                        new DelayedTask(4) {
                            public void run() {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity livingentity1 = (LivingEntity)entity;

                                    if (!livingentity1.level.isClientSide()) {
                                        livingentity1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 6, 10, false, false));
                                    }
                                }

                                Entity entity1 = entity;

                                if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                    entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute at @s run particle annoyingvillagers:bluespark ~ ~1 ~ 0 0 0 0.1 500");
                                }

                                LevelAccessor levelaccessor1 = levelaccessor;
                                Level level;

                                if (levelaccessor1 instanceof Level) {
                                    level = (Level)levelaccessor1;
                                    if (!level.isClientSide()) {
                                        level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_dragon.flap")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                    } else {
                                        level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_dragon.flap")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                    }
                                }

                                levelaccessor1 = levelaccessor;
                                if (levelaccessor1 instanceof Level) {
                                    level = (Level)levelaccessor1;
                                    if (!level.isClientSide()) {
                                        level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:wing")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                    } else {
                                        level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:wing")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                    }
                                }

                                entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * 2.0D, entity.getLookAngle().y * 2.0D, entity.getLookAngle().z * 2.0D));
                            }
                        };
                        if (entity instanceof Player) {
                            player = (Player)entity;
                            player.getCooldowns().addCooldown((Item)AnnoyingVillagersModItems.LAN_CUN_QI_FU_MO_JIAN.get(), 25);
                        }
                    } else {
                        if (entity instanceof LivingEntity) {
                            LivingEntity livingentity1 = (LivingEntity)entity;

                            itemstack1 = livingentity1.getOffhandItem();
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        if (itemstack1.getItem() == AnnoyingVillagersModItems.LAN_CUN_QI_FU_MO_JIAN.get()) {
                            if (entity instanceof LivingEntity) {
                                LivingEntity livingentity2 = (LivingEntity)entity;

                                if (!livingentity2.level.isClientSide()) {
                                    livingentity2.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 6, 10, false, false));
                                }
                            }

                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute at @s run particle annoyingvillagers:bluespark ~ ~1 ~ 0 0 0 0.1 500");
                            }

                            Level level;

                            if (levelaccessor instanceof Level) {
                                level = (Level)levelaccessor;
                                if (!level.isClientSide()) {
                                    level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_dragon.flap")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                } else {
                                    level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_dragon.flap")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                }
                            }

                            if (levelaccessor instanceof Level) {
                                level = (Level)levelaccessor;
                                if (!level.isClientSide()) {
                                    level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:wing")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                } else {
                                    level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:wing")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                }
                            }

                            entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * 2.0D, entity.getLookAngle().y * 2.0D, entity.getLookAngle().z * 2.0D));
                            if (entity instanceof Player) {
                                player = (Player)entity;
                                player.getCooldowns().addCooldown((Item) AnnoyingVillagersModItems.LAN_CUN_QI_FU_MO_JIAN.get(), 25);
                            }
                        }
                    }
                } else if (entity instanceof Player) {
                    Player player1 = (Player)entity;

                    if (!player1.level.isClientSide()) {
                        player1.displayClientMessage(new TextComponent("\u80fd\u91cf\u4e0d\u8db3\uff01"), true);
                    }
                }
            }

        }
    }
}
