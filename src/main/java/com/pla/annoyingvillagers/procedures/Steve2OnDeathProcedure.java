package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.spawnhandler.SteveData;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.util.EquipmentDataLoader;
import com.pla.annoyingvillagers.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class Steve2OnDeathProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            if (Math.random() >= 0.38D) {
                LevelAccessor levelaccessor1 = levelaccessor;

                if (levelaccessor1 instanceof Level) {
                    Level level = (Level)levelaccessor1;

                    if (!level.isClientSide()) {
                        level.explode((Entity)null, d0, d1 + 1.0D, d2, 3.0F, Level.ExplosionInteraction.NONE);
                    }
                }
                Entity entity1 = entity;

                if (!entity1.level().isClientSide() && entity1.getServer() != null) {
                    try {
                        entity1.getServer().getCommands().getDispatcher().execute(
                                "execute at @s run particle minecraft:totem_of_undying ^ ^1.5 ^ 0 0 0 1 1000",
                                entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (levelaccessor instanceof ServerLevel serverLevel) {
                    AngrySteveEntity angrySteveEntity = new AngrySteveEntity((EntityType) AnnoyingVillagersModEntities.ANGRY_STEVE.get(), serverLevel);

                    angrySteveEntity.moveTo(d0, d1, d2, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                    InventoryUtils.transferInventory(((Steve2Entity) entity).getInventory(), angrySteveEntity.getInventory());


                    entity.discard();
                    SteveData steveData = SteveData.get(serverLevel);
                    steveData.forceClaim(serverLevel, angrySteveEntity.getUUID());

                    angrySteveEntity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(angrySteveEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                    levelaccessor.addFreshEntity(angrySteveEntity);
                }
            } else {
                if (levelaccessor instanceof Level) {
                    Level level = (Level)levelaccessor;

                    if (!level.isClientSide()) {
                        level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "stevesayno")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "stevesayno")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity.getDisplayName().getString() + "> Noooooooooooooooooooooooooo!"), false);
                }

                new DelayedTask(20) {
                    public void run() {
                        LevelAccessor levelaccessor1 = levelaccessor;
                        Level level1;
                        ItemEntity itementity;

                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                ItemStack itemStack = new ItemStack((ItemLike) AnnoyingVillagersModItems.COMPRESSED_DIAMOND_HELMET.get());
                                itemStack.setDamageValue(EquipmentDataLoader.getRandomDamage(itemStack));
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, itemStack);
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                ItemStack itemStack = new ItemStack((ItemLike) AnnoyingVillagersModItems.COMPRESSED_DIAMOND_CHESTPLATE.get());
                                itemStack.setDamageValue(EquipmentDataLoader.getRandomDamage(itemStack));
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, itemStack);
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                ItemStack itemStack = new ItemStack(Items.DIAMOND_CHESTPLATE);
                                itemStack.setDamageValue(EquipmentDataLoader.getRandomDamage(itemStack));
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, itemStack);
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLDEN_APPLE));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLDEN_APPLE));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLDEN_APPLE));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLDEN_APPLE));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLDEN_APPLE));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLDEN_APPLE));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLDEN_APPLE));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                ItemStack itemStack = new ItemStack((ItemLike) AnnoyingVillagersModItems.DIAMOND_GREAT_SWORD.get());
                                itemStack.setDamageValue(EquipmentDataLoader.getRandomDamage(itemStack));
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, itemStack);
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                ItemStack itemStack = new ItemStack((ItemLike) AnnoyingVillagersModItems.ENCHANTED_WOODEN_DOOR.get());
                                itemStack.setDamageValue(EquipmentDataLoader.getRandomDamage(itemStack));
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, itemStack);
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike) AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND_SWORD));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                ItemStack itemStack = new ItemStack((ItemLike) AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get());
                                itemStack.setDamageValue(EquipmentDataLoader.getRandomDamage(itemStack));
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, itemStack);
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND_SWORD));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.WHITE_BED));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.CAKE));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike) AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level) levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike) AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }
                    }
                };

                double posX = entity.getX();
                double posY = entity.getY();
                double posZ = entity.getZ();
                LevelAccessor levelAccessor = entity.level();
                if (levelAccessor instanceof ServerLevel && AnnoyingVillagersConfig.PHYSIC_MOD_COMPAT.get()) {
                    ServerLevel serverlevel = (ServerLevel)levelaccessor;
                    SteveDeadEntity steveDeadEntity = new SteveDeadEntity((EntityType) AnnoyingVillagersModEntities.STEVE_DEAD.get(), serverlevel);

                    steveDeadEntity.moveTo(posX, posY, posZ, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                    if (steveDeadEntity instanceof Mob) {
                        Mob mob = (Mob)steveDeadEntity;

                        mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(steveDeadEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                    }
                    entity.remove(Entity.RemovalReason.KILLED);
                    levelaccessor.addFreshEntity(steveDeadEntity);
                    try {
                        steveDeadEntity.getServer().getCommands().getDispatcher().execute(
                                "kill @s",
                                steveDeadEntity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }
            }

        }
    }
}
