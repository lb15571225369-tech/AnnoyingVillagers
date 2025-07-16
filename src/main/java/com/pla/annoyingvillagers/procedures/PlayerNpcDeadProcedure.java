package com.pla.annoyingvillagers.procedures;

import java.util.Iterator;
import java.util.Random;
import javax.annotation.Nullable;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.util.DelayedTask;
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
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class PlayerNpcDeadProcedure {

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
                                levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> Is that all ?"), ChatType.SYSTEM, Util.NIL_UUID);
                            }
                        } else if (Math.random() <= 0.05D) {
                            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> LLLLLLLLLLLLL"), ChatType.SYSTEM, Util.NIL_UUID);
                            }
                        } else if (Math.random() <= 0.05D) {
                            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> Hey, what happened to you?"), ChatType.SYSTEM, Util.NIL_UUID);
                            }
                        } else if (Math.random() <= 0.05D) {
                            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> Poet's grasp"), ChatType.SYSTEM, Util.NIL_UUID);
                            }
                        } else {
                            PlayerList playerlist;
                            String s;

                            if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> " + entity.getDisplayName().getString() + ", is that all the strength you've got?"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.1D) {
                                if (ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("minecraft:player") && !levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> " + entity.getDisplayName().getString() + ", that's all the Little Hajiki can do?"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("minecraft:player") && !levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> " + entity.getDisplayName().getString() + ", you're godlike too"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> Hahaha \uD83D\uDE02 so funny, a " + entity.getDisplayName().getString() + " is lying in bed, rhythmically chanting “Garen~ fafah”"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> " + entity.getDisplayName().getString() + ", don't act tough if you're weak"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> " + entity.getDisplayName().getString() + " plays like a bot"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> " + entity.getDisplayName().getString() + ", I'm dying of laughter, a total \uD83E\uDD21"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> Even giving it your all, you still can't defeat me? Haki " + entity.getDisplayName().getString() + ", you bastard!"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> Joke " + entity.getDisplayName().getString()), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> " + entity.getDisplayName().getString() + ", weren't you acting all tough, bro?"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> lol"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> " + entity.getDisplayName().getString() + "\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95 So weak!"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist = levelaccessor.getServer().getPlayerList();
                                    s = entity1.getDisplayName().getString();
                                    playerlist.broadcastMessage(new TextComponent("<" + s + "> " + entity.getDisplayName().getString() + ", if you're bad, just practice more, little bro"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> It's over"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> Haha, you're getting mad"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> Oh really?"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> \ud83e\udd13\ud83e\udd13\ud83e\udd13"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> I pinned you to the ground and beat you up, haha"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> So bad? Could it be that you've played too much Genshin Impact?\ud83d\ude05"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + "> Desperate now?"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D && !levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity1.getDisplayName().getString() + ">, all you can do is spam left-click to death\ud83e\udd13"), ChatType.SYSTEM, Util.NIL_UUID);
                            }
                        }
                    }
                };
            }

            if (ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("player_mobs:player_mob")) {
                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                    PlayerList playerlist = levelaccessor.getServer().getPlayerList();
                    String s = entity.getDisplayName().getString();

                    playerlist.broadcastMessage(new TextComponent(s + " was killed by " + entity1.getDisplayName().getString()), ChatType.SYSTEM, Util.NIL_UUID);
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
                    }
                };
                new DelayedTask(Mth.nextInt(new Random(), 40, 80)) {
                    @Override
                    public void run() {
                        Entity entity2;

                        if (Math.random() <= 0.05D) {
                            entity2 = entity;
                            if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "tellraw @a [{\"text\":\"<\"},{\"selector\":\"@s\"},{\"text\":\"> " + entity1.getDisplayName().getString() + " Bro, I'll remember you for this\ud83d\ude21\"}]");
                            }
                        } else if (Math.random() <= 0.05D) {
                            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity.getDisplayName().getString() + "> I'm breaking down\ud83d\ude2d"), ChatType.SYSTEM, Util.NIL_UUID);
                            }
                        } else {
                            PlayerList playerlist1;
                            String s1;

                            if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + ", f** you"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity.getDisplayName().getString() + "> So speechless\ud83d\ude05"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity.getDisplayName().getString() + "> 666 this guy is a boss"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity.getDisplayName().getString() + "> That was tough\ud83d\ude05"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity.getDisplayName().getString() + "> I really give up\ud83d\ude2d"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + " , your mom is dead. F** your mom"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + " bro, be honest, are you hacking?\ud83d\ude05"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity.getDisplayName().getString() + "> Played as Bull Demon, lost all my gear\ud83d\ude05"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + ", I'll get you later\ud83d\ude21"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + ", I will take revenge on you soon\ud83d\ude21"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + ", f*** your mom! Dare to fight fair with proper gear?\ud83d\ude21"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + ", you're dead for sure"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + ", you just killed me like that. Are you happy now?\ud83d\ude05"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> Please, " + entity1.getDisplayName().getString() + ", don't burnt my items !!!!"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity.getDisplayName().getString() + "> ????????????????????????????????"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity.getDisplayName().getString() + "> I haven't even gotten serious yet"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + ", I'll get you next time\ud83d\ude21"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + ", you are really a \ud83d\udc36"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + " , ambushing an ordinary player like me, is this okay? No, it's not\ud83d\udc4e"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity.getDisplayName().getString() + "> ......"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<" + entity.getDisplayName().getString() + "> Idiot, f** your mom\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95"), ChatType.SYSTEM, Util.NIL_UUID);
                                }
                            } else if (Math.random() <= 0.05D) {
                                entity2 = entity;
                                if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                    entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "tellraw @a [{\"text\":\"<\"},{\"selector\":\"@s\"},{\"text\":\"> Don't leave, I'm calling some people\ud83d\ude21\ud83d\ude21\ud83d\ude21\"}]");
                                }

                                new DelayedTask(50) {
                                    @Override
                                    public void run() {
                                        entity.getServer().getCommands().performCommand(
                                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                                "summon player_mobs:player_mob"
                                        );
                                    }
                                };

                                new DelayedTask(20) {
                                    @Override
                                    public void run() {
                                        entity.getServer().getCommands().performCommand(
                                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                                "summon player_mobs:player_mob"
                                        );
                                    }
                                };

                                new DelayedTask(20) {
                                    @Override
                                    public void run() {
                                        entity.getServer().getCommands().performCommand(
                                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                                "summon player_mobs:player_mob"
                                        );
                                    }
                                };

                                new DelayedTask(20) {
                                    @Override
                                    public void run() {
                                        entity.getServer().getCommands().performCommand(
                                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                                "tellraw @a [{\"text\":\"<\"},{\"selector\":\"@s\"},{\"text\":\"> Hmmm, you're here… bro.🤓\"}]"
                                        );
                                    }
                                };
                            } else if (Math.random() <= 0.05D && !levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                playerlist1 = levelaccessor.getServer().getPlayerList();
                                s1 = entity.getDisplayName().getString();
                                playerlist1.broadcastMessage(new TextComponent("<" + s1 + "> " + entity1.getDisplayName().getString() + ", using your OP weapon, is that fun for you?\ud83d\ude05"), ChatType.SYSTEM, Util.NIL_UUID);
                            }
                        }

                        new DelayedTask(Mth.nextInt(new Random(), 25, 100)) {
                            @Override
                            public void run() {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("\u00a7e" + entity.getDisplayName().getString() + "\u00a7e has left the game"), ChatType.SYSTEM, Util.NIL_UUID);
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
