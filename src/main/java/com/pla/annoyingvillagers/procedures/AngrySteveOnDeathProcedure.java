package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.entity.SteveDeadEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class AngrySteveOnDeathProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, Entity entity) {
        if (entity != null) {
            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "tellraw @a {\"text\":\"<Steve> Nooooooooooooooo !!\"}",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
            }

            if (levelaccessor instanceof ServerLevel) {
                ServerLevel serverlevel = (ServerLevel)levelaccessor;
                SteveDeadEntity stevedeadentity = new SteveDeadEntity((EntityType) AnnoyingVillagersModEntities.STEVE_DEAD.get(), serverlevel);

                stevedeadentity.moveTo(d0, d1, d2, 0.0F, 0.0F);
                stevedeadentity.setYBodyRot(0.0F);
                stevedeadentity.setYHeadRot(0.0F);
                if (stevedeadentity instanceof Mob) {
                    Mob mob = (Mob)stevedeadentity;

                    mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(stevedeadentity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                }

                levelaccessor.addFreshEntity(stevedeadentity);
            }

            if (levelaccessor instanceof Level) {
                Level level = (Level)levelaccessor;

                if (!level.isClientSide()) {
                    level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevesayno")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                } else {
                    level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevesayno")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                }
            }
            new DelayedTask(20) {
                public void run() {
                    LevelAccessor levelaccessor1 = levelaccessor;
                    Level level1;
                    ItemEntity itementity;

                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND_SWORD));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLDEN_APPLE));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.WHEAT));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.IRON_INGOT));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.IRON_INGOT));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.IRON_INGOT));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.IRON_INGOT));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.BIRCH_PLANKS));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike) AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.ENCHANTED_WOODEN_DOOR.get()));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.TNT));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.TNT));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            ItemStack enchantedLadder = new ItemStack(Blocks.LADDER);
                            enchantedLadder.enchant(Enchantments.MENDING, 5);
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, enchantedLadder);
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            ItemStack enchantedLadder = new ItemStack(Blocks.OAK_TRAPDOOR);
                            enchantedLadder.enchant(Enchantments.UNBREAKING, 5);
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, enchantedLadder);
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.DIAMOND_GREAT_SWORD.get()));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get()));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND_CHESTPLATE));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND_CHESTPLATE));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIAMOND_BLOCK));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.WHITE_BED));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.CAKE));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.WATER_BUCKET));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.IRON_CHESTPLATE));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.STICK));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.STICK));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.STICK));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND_PICKAXE));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND_PICKAXE));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.IRON_SWORD));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.IRON_SWORD));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.COOKED_BEEF));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.COOKED_BEEF));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.COOKED_BEEF));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.FISHING_ROD));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.LIGHT_GRAY_DYE));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.BIRCH_SIGN));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.BIRCH_SIGN));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.CARROT));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.CARROT));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.BAKED_POTATO));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.BAKED_POTATO));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DRAGON_EGG));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.STICK));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level1 = (Level)levelaccessor1;
                        if (!level1.isClientSide()) {
                            itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.STICK));
                            itementity.setPickUpDelay(10);
                            level1.addFreshEntity(itementity);
                        }
                    }
                    
                }
            };
        }
    }
}
