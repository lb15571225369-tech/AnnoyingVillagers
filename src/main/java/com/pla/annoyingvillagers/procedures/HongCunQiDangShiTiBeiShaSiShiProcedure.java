package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HongCunQiDangShiTiBeiShaSiShiProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            new DelayedTask(20) {
                public void run() {
                    LevelAccessor levelaccessor1 = levelaccessor;
                    Level level;
                    ItemEntity itementity;

                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.GOU_ZHUANG_ZUAN_SHI_JIAN.get()));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.BREAD));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.BREAD));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.WHEAT));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.WHEAT));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack((ItemLike) AnnoyingVillagersModItems.CCHUNDUZUANSHI.get()));
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
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Blocks.OAK_PLANKS));
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
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
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
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.IRON_PICKAXE));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.APPLE));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.APPLE));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLD_INGOT));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLD_INGOT));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLD_INGOT));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.STICK));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.STICK));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.FIREWORK_ROCKET));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.FIREWORK_ROCKET));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.POISONOUS_POTATO));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.EMERALD));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.EMERALD));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.EMERALD));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.EMERALD));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }
                }
            };
            if (Math.random() <= 0.11D) {
                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/summon firework_rocket ~ ~10 ~ {LifeTime:10,FireworksItem:{id:firework_rocket,Count:1,tag:{Fireworks:{Explosions:[{Type:3,Colors:[0],Flicker:1}]}},display:{Name:\"Black Creeper Firework\"}}}");
                }

                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<Villager Red General> We've encountered a skilled player, requesting support!"), ChatType.SYSTEM, Util.NIL_UUID);
                }

                new DelayedTask(400) {
                    public void run() {
                        if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                            levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<Villager Scout> Reinforcements have arrived!"), ChatType.SYSTEM, Util.NIL_UUID);
                        }

                        Entity entity1 = entity;

                        if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                            entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/summon annoyingvillagers:cun_min_zhen_cha_bing ^ ^ ^10");
                        }

                        entity1 = entity;
                        if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                            entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/summon annoyingvillagers:cun_min_zhen_cha_bing ^ ^ ^15");
                        }

                        entity1 = entity;
                        if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                            entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/summon annoyingvillagers:lan_cun_qi ^10 ^ ^20");
                        }
                    }
                };
            } else if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<Villager Red General> I swear to serve the Villager King to the death..."), ChatType.SYSTEM, Util.NIL_UUID);
            }

        }
    }
}
