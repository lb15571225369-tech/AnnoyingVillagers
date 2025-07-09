package com.pla.annoyingvillagers.procedures;

import java.util.Comparator;

import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.HerobrineEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;

public class LeaveGetProcedure {
    public static boolean isSpectator(Entity entity) {
        if (entity instanceof ServerPlayer sp) {
            return sp.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
        } else if (entity instanceof Player player && entity.level.isClientSide()) {
            var info = Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId());
            return info != null && info.getGameMode() == GameType.SPECTATOR;
        }
        return false;
    }

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            LivingEntity livingentity;
            Player player;

            if (levelaccessor.players().size() == 1) {
                if (isSpectator(entity)) {
                    if (ForgeRegistries.ENTITIES.getKey(entity.getVehicle().getType()).toString().equals(AnnoyingVillagers.MODID + ":herobrine")) {
                        Player player1;
                        NonNullList nonnulllist;
                        Entity entity1;
                        LivingEntity livingentity1;
                        ItemStack itemstack;
                        LivingEntity livingentity2;
                        EquipmentSlot equipmentslot;

                        if (entity instanceof Player) {
                            player1 = (Player)entity;
                            nonnulllist = player1.getInventory().armor;
                            entity1 = entity.getVehicle();
                            if (entity1 instanceof LivingEntity) {
                                livingentity1 = (LivingEntity)entity1;
                                itemstack = livingentity1.getItemBySlot(EquipmentSlot.FEET);
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            nonnulllist.set(0, itemstack);
                            player1.getInventory().setChanged();
                        } else if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity)entity;
                            equipmentslot = EquipmentSlot.FEET;
                            entity1 = entity.getVehicle();
                            if (entity1 instanceof LivingEntity) {
                                livingentity1 = (LivingEntity)entity1;
                                itemstack = livingentity1.getItemBySlot(EquipmentSlot.FEET);
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            livingentity2.setItemSlot(equipmentslot, itemstack);
                        }

                        if (entity instanceof Player) {
                            player1 = (Player)entity;
                            nonnulllist = player1.getInventory().armor;
                            entity1 = entity.getVehicle();
                            if (entity1 instanceof LivingEntity) {
                                livingentity1 = (LivingEntity)entity1;
                                itemstack = livingentity1.getItemBySlot(EquipmentSlot.LEGS);
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            nonnulllist.set(1, itemstack);
                            player1.getInventory().setChanged();
                        } else if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity)entity;
                            equipmentslot = EquipmentSlot.LEGS;
                            entity1 = entity.getVehicle();
                            if (entity1 instanceof LivingEntity) {
                                livingentity1 = (LivingEntity)entity1;
                                itemstack = livingentity1.getItemBySlot(EquipmentSlot.LEGS);
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            livingentity2.setItemSlot(equipmentslot, itemstack);
                        }

                        if (entity instanceof Player) {
                            player1 = (Player)entity;
                            nonnulllist = player1.getInventory().armor;
                            entity1 = entity.getVehicle();
                            if (entity1 instanceof LivingEntity) {
                                livingentity1 = (LivingEntity)entity1;
                                itemstack = livingentity1.getItemBySlot(EquipmentSlot.CHEST);
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            nonnulllist.set(2, itemstack);
                            player1.getInventory().setChanged();
                        } else if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity)entity;
                            equipmentslot = EquipmentSlot.CHEST;
                            entity1 = entity.getVehicle();
                            if (entity1 instanceof LivingEntity) {
                                livingentity1 = (LivingEntity)entity1;
                                itemstack = livingentity1.getItemBySlot(EquipmentSlot.CHEST);
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            livingentity2.setItemSlot(equipmentslot, itemstack);
                        }

                        if (entity instanceof Player) {
                            player1 = (Player)entity;
                            nonnulllist = player1.getInventory().armor;
                            entity1 = entity.getVehicle();
                            if (entity1 instanceof LivingEntity) {
                                livingentity1 = (LivingEntity)entity1;
                                itemstack = livingentity1.getItemBySlot(EquipmentSlot.HEAD);
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            nonnulllist.set(3, itemstack);
                            player1.getInventory().setChanged();
                        } else if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity)entity;
                            equipmentslot = EquipmentSlot.HEAD;
                            entity1 = entity.getVehicle();
                            if (entity1 instanceof LivingEntity) {
                                livingentity1 = (LivingEntity)entity1;
                                itemstack = livingentity1.getItemBySlot(EquipmentSlot.HEAD);
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            livingentity2.setItemSlot(equipmentslot, itemstack);
                        }
                    }

                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity)entity;
                        livingentity.removeEffect((MobEffect)AnnoyingVillagersModMobEffects.HEROBRINE_EFFECT.get());
                    }

                    entity.stopRiding();
                    new DelayedTask(1) {
                        @Override
                        public void run() {
                            Entity entity2 = levelaccessor.getEntitiesOfClass(HerobrineEntity.class,
                                            AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D),
                                            herobrineentity -> true)
                                    .stream()
                                    .sorted(Comparator.comparingDouble(entity3 -> entity3.distanceToSqr(d0, d1, d2)))
                                    .findFirst()
                                    .orElse(null);

                            if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect clear @s annoying_villagersbychentu:gedang");
                            }

                            entity2 = levelaccessor.getEntitiesOfClass(HerobrineEntity.class,
                                            AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D),
                                            herobrineentity -> true)
                                    .stream()
                                    .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(d0, d1, d2)))
                                    .findFirst()
                                    .orElse(null);

                            if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "kill @s");
                            }

                            LivingEntity livingentity3;

                            if (entity instanceof LivingEntity) {
                                livingentity3 = (LivingEntity)entity;
                                livingentity3.removeEffect((MobEffect)AnnoyingVillagersModMobEffects.HEROBRINE_EFFECT.get());
                            }

                            if (entity instanceof ServerPlayer) {
                                ServerPlayer serverplayer = (ServerPlayer)entity;

                                serverplayer.setGameMode(GameType.SURVIVAL);
                            }

                            if (entity instanceof LivingEntity) {
                                livingentity3 = (LivingEntity)entity;
                                livingentity3.setHealth(5.0F);
                            }

                            entity2 = entity;
                            if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect give @s annoying_villagersbychentu:fulu 5 1 true");
                            }

                            entity2 = entity;
                            if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "title @s title {\"text\":\"\u5df2\u89e3\u8131\",\"color\":\"green\"}");
                            }

                            entity2 = entity;
                            if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "titles refresh");
                            }

                            entity2 = entity;
                            if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "titles remove @s titles:herobrine");
                            }
                        }
                    };
                } else if (entity instanceof Player) {
                    player = (Player)entity;
                    if (!player.level.isClientSide()) {
                        player.displayClientMessage(new TextComponent("\u8be5\u73a9\u5bb6\u672a\u88ab\u9644\u8eab"), false);
                    }
                }
            } else {
                int i;

                if (entity instanceof Player) {
                    Player player2 = (Player)entity;

                    i = player2.experienceLevel;
                } else {
                    i = 0;
                }

                if (i >= 10) {
                    if (isSpectator(entity)) {
                        if (entity instanceof LivingEntity) {
                            livingentity = (LivingEntity)entity;
                            livingentity.removeEffect((MobEffect)AnnoyingVillagersModMobEffects.HEROBRINE_EFFECT.get());
                        }

                        if (entity instanceof Player) {
                            player = (Player)entity;
                            player.giveExperienceLevels(-9);
                        }

                        entity.stopRiding();
                        new DelayedTask(1) {
                            @Override
                            public void run() {
                                Entity entity2 = levelaccessor.getEntitiesOfClass(HerobrineEntity.class,
                                                AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D),
                                                herobrineentity -> true)
                                        .stream()
                                        .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(d0, d1, d2)))
                                        .findFirst()
                                        .orElse(null);

                                if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                    entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect clear @s annoying_villagersbychentu:gedang");
                                }

                                entity2 = levelaccessor.getEntitiesOfClass(
                                                HerobrineEntity.class,
                                                AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D),
                                                e -> true)
                                        .stream()
                                        .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(d0, d1, d2)))
                                        .findFirst()
                                        .orElse(null);

                                if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                    entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "kill @s");
                                }

                                LivingEntity livingentity3;

                                if (entity instanceof LivingEntity) {
                                    livingentity3 = (LivingEntity)entity;
                                    livingentity3.removeEffect((MobEffect)AnnoyingVillagersModMobEffects.HEROBRINE_EFFECT.get());
                                }

                                entity2 = entity;
                                if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                    entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "titles remove @s titles:herobrine");
                                }

                                entity2 = entity;
                                if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                    entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "titles refresh");
                                }

                                if (entity instanceof ServerPlayer) {
                                    ServerPlayer serverplayer = (ServerPlayer)entity;

                                    serverplayer.setGameMode(GameType.SURVIVAL);
                                }

                                if (entity instanceof LivingEntity) {
                                    livingentity3 = (LivingEntity)entity;
                                    livingentity3.setHealth(5.0F);
                                }

                                entity2 = entity;
                                if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                    entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect give @s annoying_villagersbychentu:fulu 5 1 true");
                                }

                                entity2 = entity;
                                if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                    entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "title @s title {\"text\":\"\u5df2\u89e3\u8131\",\"color\":\"green\"}");
                                }

                            }
                        };
                    } else if (entity instanceof Player) {
                        player = (Player)entity;
                        if (!player.level.isClientSide()) {
                            player.displayClientMessage(new TextComponent("\u8be5\u73a9\u5bb6\u672a\u88ab\u9644\u8eab"), false);
                        }
                    }
                } else if (entity instanceof Player) {
                    player = (Player)entity;
                    if (!player.level.isClientSide()) {
                        player.displayClientMessage(new TextComponent("\u591a\u4eba\u6e38\u620f\u4e0b\u7ecf\u9a8c\u7b49\u7ea7\u5c0f\u4e8e10\u65e0\u6cd5\u89e3\u8131"), false);
                    }
                }
            }

        }
    }
}
