package com.pla.annoyingvillagers.procedures;

import java.util.Iterator;
import java.util.Random;
import javax.annotation.Nullable;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.util.QueuedTaskScheduler;
import net.minecraft.Util;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class PlayernpcdeadProcedure {

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent livingdeathevent) {
        if (livingdeathevent != null && livingdeathevent.getEntity() != null) {
            execute(livingdeathevent, livingdeathevent.getEntity().level, livingdeathevent.getEntity().getX(), livingdeathevent.getEntity().getY(), livingdeathevent.getEntity().getZ(), livingdeathevent.getEntity(), livingdeathevent.getSource().getEntity());
        }

    }

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity, Entity entity1) {
        execute((Event) null, levelaccessor, d0, d1, d2, entity, entity1);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity, final Entity entity1) {
        if (entity != null && entity1 != null) {
            if (ForgeRegistries.ENTITIES.getKey(entity1.getType()).toString().equals("player_mobs:player_mob")) {
                new DelayedTask(Mth.nextInt(new Random(), 70, 100)) {
                    @Override
                    public void run() {
                        if (Math.random() <= 0.05D) {
                            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> fw"), ChatType.SYSTEM, Util.NIL_UUID);
                            }
                        } else if (Math.random() <= 0.05D) {
                            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> \u5c31\u8fd9\uff1f"), ChatType.SYSTEM, Util.NIL_UUID);
                            }
                        } else if (Math.random() <= 0.05D) {
                            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> LLLLLLLLLLLLL"), ChatType.SYSTEM, Util.NIL_UUID);
                            }
                        } else if (Math.random() <= 0.05D) {
                            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> \u54ce\u4f60\u600e\u4e48\u4f3c\u4e86\uff1f"), ChatType.SYSTEM, Util.NIL_UUID);
                            }
                        } else if (Math.random() <= 0.05D) {
                            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> \u8bd7\u4eba\u63e1\u6301"), ChatType.SYSTEM, Util.NIL_UUID);
                            }
                        } else {
                            PlayerList playerlist;
                            String s;

                            if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> " + entity.getDisplayName().getString() + "\u4f60\u5c31\u8fd9\u70b9\u5b9e\u529b\u5417\uff1f"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.1D) {
                                if (ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("minecraft:player") && !levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> " + entity.getDisplayName().getString() + "\u5c0f\u54c8\u57fa\u4eba\u5c31\u8fd9\uff1f"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("minecraft:player") && !levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> " + entity.getDisplayName().getString() + "\u4f60\u4e5f\u662f\u4e2a\u795e\u4eba\u4e86"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> \u54c8\u54c8\u54c8\ud83d\ude02\u771f\u597d\u7b11\uff0c\u4e00\u4e2a" + entity.getDisplayName().getString() + "\u8eba\u5728\u5e8a\u4e0a\uff0c\u5634\u91cc\u6709\u8282\u594f\u5730\u5ff5\u7740\u7740\u76d6\u4f26\uff5e\u53d1\u53d1"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> " + entity.getDisplayName().getString() + "\u6ca1\u5b9e\u529b\u5c31\u522b\u56a3\u5f20"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> " + entity.getDisplayName().getString() + "\u4eba\u673a\u64cd\u4f5c"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> " + entity.getDisplayName().getString() + "\u7b11\u6b7b\u6211\u4e86\u7eaf\ud83e\udd21\u4e00\u4e2a"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> \u62fc\u5c3d\u5168\u529b\u4e5f\u65e0\u6cd5\u6218\u80dc\u6211\u5417\uff1f\u54c8\u57fa" + entity.getDisplayName().getString() + "\u4f60\u8fd9\u5bb6\u4f19"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> \u4e50\u5b50" + entity.getDisplayName().getString()), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> " + entity.getDisplayName().getString() + "\u4e0d\u662f\u633a\u80fd\u88c5\u7684\u5417\u8001\u5f1f\uff1f"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> lol"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> " + entity.getDisplayName().getString() + "\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\u6ca1\u5b9e\u529b"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> " + entity.getDisplayName().getString() + "\u83dc\u5c31\u591a\u7ec3\uff0c\u5c0f\u8001\u5f1f"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> \u5510\u5b8c\u4e86"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> \u54c8\u54c8\uff0c\u6025\u4e86"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> \u597d\u4f3c"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> \ud83e\udd13\ud83e\udd13\ud83e\udd13"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> \u88ab\u6211\u6309\u5728\u5730\u4e0a\u6253\u54c8\u54c8"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> \u8fd9\u4e48\u83dc\uff1f\u4e0d\u4f1a\u662f\u539f\u795e\u73a9\u591a\u4e86\u5bfc\u81f4\u7684\u5427\uff1f\ud83d\ude05"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> \u73a9\u4e0d\u8d77\uff0c\u6025\u4e86\u54c8\u54c8"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D && !levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> \u4f60\u4e5f\u5c31\u53ea\u4f1a\u5de6\u952e\u6441\u6b7b\u4e86\ud83e\udd13"), ChatType.SYSTEM, Util.NIL_UUID);
                            }
                        }

                        MinecraftForge.EVENT_BUS.unregister(this);
                    }
                };
            }

            if (ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("player_mobs:player_mob")) {
                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                    PlayerList playerlist = levelaccessor.getServer().getPlayerList();
                    String s = entity.getDisplayName().getString();

                    playerlist.broadcastMessage(new TextComponent(s + "\u88ab" + entity1.getDisplayName().getString() + "\u6740\u6b7b\u4e86"), ChatType.SYSTEM, Util.NIL_UUID);
                }

                new DelayedTask(5) {
                    @Override
                    public void run() {
                        LevelAccessor levelaccessor1;
                        Level level;
                        ItemEntity itementity;

                        if (Math.random() <= 0.7D) {
                            levelaccessor1 = levelaccessor;
                            ItemEntity itementity1;
                            double d3;
                            double d4;
                            double d5;
                            LivingEntity livingentity = (LivingEntity)entity;
                            ItemStack itemstack;

                            if (levelaccessor1 instanceof Level) {
                                level = (Level)levelaccessor1;
                                if (!level.isClientSide()) {
                                    if (entity instanceof LivingEntity) {
                                        itemstack = livingentity.getItemBySlot(EquipmentSlot.FEET);
                                    } else {
                                        itemstack = ItemStack.EMPTY;
                                    }

                                    itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, itemstack);
                                    itementity.setPickUpDelay(10);
                                    level.addFreshEntity(itementity);
                                }
                            }

                            levelaccessor1 = levelaccessor;
                            if (levelaccessor1 instanceof Level) {
                                level = (Level)levelaccessor1;
                                if (!level.isClientSide()) {
                                    if (entity instanceof LivingEntity) {
                                        itemstack = livingentity.getItemBySlot(EquipmentSlot.LEGS);
                                    } else {
                                        itemstack = ItemStack.EMPTY;
                                    }

                                    itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, itemstack);
                                    itementity.setPickUpDelay(10);
                                    level.addFreshEntity(itementity);
                                }
                            }

                            levelaccessor1 = levelaccessor;
                            if (levelaccessor1 instanceof Level) {
                                level = (Level)levelaccessor1;
                                if (!level.isClientSide()) {
                                    if (entity instanceof LivingEntity) {
                                        itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
                                    } else {
                                        itemstack = ItemStack.EMPTY;
                                    }

                                    itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, itemstack);
                                    itementity.setPickUpDelay(10);
                                    level.addFreshEntity(itementity);
                                }
                            }

                            levelaccessor1 = levelaccessor;
                            if (levelaccessor1 instanceof Level) {
                                level = (Level)levelaccessor1;
                                if (!level.isClientSide()) {
                                    if (entity instanceof LivingEntity) {
                                        itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
                                    } else {
                                        itemstack = ItemStack.EMPTY;
                                    }

                                    itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, itemstack);
                                    itementity.setPickUpDelay(10);
                                    level.addFreshEntity(itementity);
                                }
                            }

                            levelaccessor1 = levelaccessor;
                            if (levelaccessor1 instanceof Level) {
                                level = (Level)levelaccessor1;
                                if (!level.isClientSide()) {
                                    if (entity instanceof LivingEntity) {
                                        itemstack = livingentity.getMainHandItem();
                                    } else {
                                        itemstack = ItemStack.EMPTY;
                                    }

                                    itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, itemstack);
                                    itementity.setPickUpDelay(10);
                                    level.addFreshEntity(itementity);
                                }
                            }

                            levelaccessor1 = levelaccessor;
                            if (levelaccessor1 instanceof Level) {
                                level = (Level)levelaccessor1;
                                if (!level.isClientSide()) {
                                    if (entity instanceof LivingEntity) {
                                        itemstack = livingentity.getOffhandItem();
                                    } else {
                                        itemstack = ItemStack.EMPTY;
                                    }

                                    itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, itemstack);
                                    itementity.setPickUpDelay(10);
                                    level.addFreshEntity(itementity);
                                }
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Blocks.OAK_PLANKS));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Blocks.OAK_PLANKS));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.BOW));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ARROW));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ARROW));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ARROW));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ARROW));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLDEN_APPLE));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLDEN_APPLE));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }

                        MinecraftForge.EVENT_BUS.unregister(this);
                    }
                };
                new DelayedTask(Mth.nextInt(new Random(), 40, 80)) {
                    @Override
                    public void run() {
                        Entity entity2;

                        if (Math.random() <= 0.05D) {
                            entity2 = entity;
                            if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "tellraw @a [{\"text\":\"<\"},{\"selector\":\"@s\"},{\"text\":\"> " + entity1.getDisplayName().getString() + "\u54e5\u4eec\u6211\u8bb0\u4f4f\u4f60\u4e86\ud83d\ude21\"}]");
                            }
                        } else if (Math.random() <= 0.05D) {
                            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity.getDisplayName().getString() + "> \u5d29\u6e83\u4e86\ud83d\ude2d"), ChatType.SYSTEM, Util.NIL_UUID);
                            }
                        } else {
                            PlayerList playerlist1;
                            String s1;

                            if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + "\u6211\u64cd\u4f60\u5988"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity.getDisplayName().getString() + "> \u771f\u65e0\u8bed\ud83d\ude05"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity.getDisplayName().getString() + "> 666\u8fd9\u4e2a\u5165\u662f\u6842"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity.getDisplayName().getString() + "> \u96be\u7ef7\ud83d\ude05"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity.getDisplayName().getString() + "> \u6211\u771f\u670d\u4e86\ud83d\ude2d"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + "\u4f60\u5988\u6b7b\u4e86\uff0c\u6211\u64cd\u4f60\u5988\u7684"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + "\u5144\u5f1f\uff0c\u8bf4\u5b9e\u8bdd\uff0c\u4f60\u662f\u4e0d\u662f\u5f00\u4e86\uff1f\ud83d\ude05"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity.getDisplayName().getString() + "> \u73a9\u725b\u9b54\uff0c\u88c5\u5907\u5168\u6ca1\u4e86\ud83d\ude05"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + "\u4f60\u7b49\u7740\ud83d\ude21"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + "\u9a6c\u4e0a\u62a5\u590d\u4f60\ud83d\ude21"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + "\u64cd\u4f60\u5988\uff0c\u6562\u4e0d\u6562\u88c5\u5907\u516c\u5e73\u5355\u6311\uff1f\ud83d\ude21"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + "\u4f60\u6b7b\u5b9a\u4e86"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + "\u4f60\u628a\u6211\u5c31\u8fd9\u6837\u6740\u4e86\uff0c\u4f60\u9ad8\u5174\u4e86\u662f\u5427\ud83d\ude05"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity.getDisplayName().getString() + "> \uff1f\uff1f\uff1f\uff1f\uff1f\uff1f\uff1f\uff1f\uff1f\uff1f\uff1f\uff1f\uff1f\uff1f\uff1f\uff1f"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity.getDisplayName().getString() + "> \u6211\u8fd8\u6ca1\u8ba4\u771f\u5462"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + "\u4e0b\u6b21\u518d\u4f1a\u4f1a\u4f60\ud83d\ude21"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + "\u4f60\u662f\u771f\u7684\ud83d\udc36"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + "\u4f60\u8fd9\u6837\u5077\u88ad\u6211\u4e00\u4e2a\u666e\u901a\u73a9\u5bb6\uff0c\u8fd9\u597d\u5417\uff1f\u8fd9\u4e0d\u597d\ud83d\udc4e"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity.getDisplayName().getString() + "> \u2026\u2026"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity.getDisplayName().getString() + "> \u50bb\u903c\u64cd\u4f60\u5988\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                entity2 = entity;
                                if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                    entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "tellraw @a [{\"text\":\"<\"},{\"selector\":\"@s\"},{\"text\":\"> \u522b\u8d70\uff0c\u6211\u53eb\u51e0\u4e2a\u4eba\ud83d\ude21\ud83d\ude21\ud83d\ude21\"}]");
                                }

                                new QueuedTaskScheduler()
                                        .schedule(() -> {
                                            // first task
                                            entity.getServer().getCommands().performCommand(
                                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                                    "summon player_mobs:player_mob"
                                            );
                                        }, 50)
                                        .schedule(() -> {
                                            // second task
                                            entity.getServer().getCommands().performCommand(
                                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                                    "summon player_mobs:player_mob"
                                            );
                                        }, 20)
                                        .schedule(() -> {
                                            // third task
                                            entity.getServer().getCommands().performCommand(
                                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                                    "summon player_mobs:player_mob"
                                            );
                                        }, 20)
                                        .schedule(() -> {
                                            // final task
                                            entity.getServer().getCommands().performCommand(
                                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                                    "tellraw @a [{\"text\":\"<\"},{\"selector\":\"@s\"},{\"text\":\"> 来了哼，哥们🤓\"}]"
                                            );
                                        }, 20);
                            } else if (Math.random() <= 0.05D && !levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                playerlist1 = levelaccessor.getServer().getPlayerList();
                                s1 = entity.getDisplayName().getString();
                                playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + "\u7528\u4f60\u90a3\u8f6e\u6905\u6b66\u5668\uff0c\u5f88\u597d\u73a9\u662f\u5417\uff1f\ud83d\ude05"), ChatType.SYSTEM, Util.NIL_UUID);
                            }
                        }

                        new DelayedTask(Mth.nextInt(new Random(), 25, 100)) {
                            @Override
                            public void run() {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("\u00a7e" + entity.getDisplayName().getString() + "\u00a7e\u9000\u51fa\u4e86\u6e38\u620f"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            }
                        };
                    }
                };
            }

            if (ForgeRegistries.ENTITIES.getKey(entity1.getType()).toString().equals("minecraft:player")) {
                float f;

                if (entity1 instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity)entity1;

                    f = livingentity.getHealth();
                } else {
                    f = -1.0F;
                }

                if (f <= 7.0F) {
                    boolean flag;
                    label56: {
                        if (entity1 instanceof ServerPlayer) {
                            ServerPlayer serverplayer = (ServerPlayer)entity1;

                            if (serverplayer.level instanceof ServerLevel && serverplayer.getAdvancements().getOrStartProgress(serverplayer.server.getAdvancements().getAdvancement(new ResourceLocation(AnnoyingVillagers.MODID + ":hard_kill"))).isDone()) {
                                flag = true;
                                break label56;
                            }
                        }

                        flag = false;
                    }

                    if (!flag) {
                        if (entity1 instanceof ServerPlayer) {
                            ServerPlayer serverplayer1 = (ServerPlayer)entity1;
                            Advancement advancement = serverplayer1.server.getAdvancements().getAdvancement(new ResourceLocation(AnnoyingVillagers.MODID + ":hard_kill"));
                            AdvancementProgress advancementprogress = serverplayer1.getAdvancements().getOrStartProgress(advancement);

                            if (!advancementprogress.isDone()) {
                                Iterator iterator = advancementprogress.getRemainingCriteria().iterator();

                                while(iterator.hasNext()) {
                                    serverplayer1.getAdvancements().award(advancement, (String)iterator.next());
                                }
                            }
                        }

                        if (levelaccessor instanceof Level) {
                            Level level = (Level)levelaccessor;

                            if (!level.isClientSide()) {
                                level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":dash_star")), SoundSource.RECORDS, 2.0F, 1.0F);
                            } else {
                                level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":dash_star")), SoundSource.RECORDS, 2.0F, 1.0F, false);
                            }
                        }
                    }
                }
            }

        }
    }
}
