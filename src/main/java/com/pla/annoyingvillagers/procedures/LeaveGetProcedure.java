package com.pla.annoyingvillagers.procedures;

import java.util.Comparator;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import com.pla.annoyingvillagers.entity.HerobrineEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;

public class LeaveGetProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            LivingEntity livingentity;
            Player player;

            if (levelaccessor.players().size() == 1) {
                if (((<undefinedtype>)(new Object() {
                    public boolean checkGamemode(Entity entity1) {
                        if (entity1 instanceof ServerPlayer) {
                            ServerPlayer serverplayer = (ServerPlayer)entity1;

                            return serverplayer.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
                        } else if (entity1.level.isClientSide() && entity1 instanceof Player) {
                            Player player1 = (Player)entity1;

                            return Minecraft.getInstance().getConnection().getPlayerInfo(player1.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(player1.getGameProfile().getId()).getGameMode() == GameType.SPECTATOR;
                        } else {
                            return false;
                        }
                    }
                })).checkGamemode(entity)) {
                    if (ForgeRegistries.ENTITIES.getKey(entity.getVehicle().getType()).toString().equals("annoying_villagers:herobrine")) {
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
                    ((<undefinedtype>)(new Object() {
                        private int ticks = 0;
                        private float waitTicks;
                        private LevelAccessor world;

                        public void start(LevelAccessor levelaccessor1, int i) {
                            this.waitTicks = (float)i;
                            MinecraftForge.EVENT_BUS.register(this);
                            this.world = levelaccessor1;
                        }

                        @SubscribeEvent
                        public void tick(ServerTickEvent servertickevent) {
                            if (servertickevent.phase == Phase.END) {
                                ++this.ticks;
                                if ((float)this.ticks >= this.waitTicks) {
                                    this.run();
                                }
                            }

                        }

                        private void run() {
                            Entity entity2 = (Entity)this.world.getEntitiesOfClass(HerobrineEntity.class, AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D), (herobrineentity) -> {
                                return true;
                            }).stream().sorted(((<undefinedtype>)(new Object() {
                                Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
                                    return Comparator.comparingDouble((entity3) -> {
                                        return entity3.distanceToSqr(d3, d4, d5);
                                    });
                                }
                            })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null);

                            if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect clear @s annoying_villagersbychentu:gedang");
                            }

                            entity2 = (Entity)this.world.getEntitiesOfClass(HerobrineEntity.class, AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D), (herobrineentity) -> {
                                return true;
                            }).stream().sorted(((<undefinedtype>)(new Object() {
                                Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
                                    return Comparator.comparingDouble((entity3) -> {
                                        return entity3.distanceToSqr(d3, d4, d5);
                                    });
                                }
                            })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null);
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

                            MinecraftForge.EVENT_BUS.unregister(this);
                        }
                    })).start(levelaccessor, 1);
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
                    if (((<undefinedtype>)(new Object() {
                        public boolean checkGamemode(Entity entity2) {
                            if (entity2 instanceof ServerPlayer) {
                                ServerPlayer serverplayer = (ServerPlayer)entity2;

                                return serverplayer.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
                            } else if (entity2.level.isClientSide() && entity2 instanceof Player) {
                                Player player3 = (Player)entity2;

                                return Minecraft.getInstance().getConnection().getPlayerInfo(player3.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(player3.getGameProfile().getId()).getGameMode() == GameType.SPECTATOR;
                            } else {
                                return false;
                            }
                        }
                    })).checkGamemode(entity)) {
                        if (entity instanceof LivingEntity) {
                            livingentity = (LivingEntity)entity;
                            livingentity.removeEffect((MobEffect)AnnoyingVillagersModMobEffects.HEROBRINE_EFFECT.get());
                        }

                        if (entity instanceof Player) {
                            player = (Player)entity;
                            player.giveExperienceLevels(-9);
                        }

                        entity.stopRiding();
                        ((<undefinedtype>)(new Object() {
                            private int ticks = 0;
                            private float waitTicks;
                            private LevelAccessor world;

                            public void start(LevelAccessor levelaccessor1, int j) {
                                this.waitTicks = (float)j;
                                MinecraftForge.EVENT_BUS.register(this);
                                this.world = levelaccessor1;
                            }

                            @SubscribeEvent
                            public void tick(ServerTickEvent servertickevent) {
                                if (servertickevent.phase == Phase.END) {
                                    ++this.ticks;
                                    if ((float)this.ticks >= this.waitTicks) {
                                        this.run();
                                    }
                                }

                            }

                            private void run() {
                                Entity entity2 = (Entity)this.world.getEntitiesOfClass(HerobrineEntity.class, AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D), (herobrineentity) -> {
                                    return true;
                                }).stream().sorted(((<undefinedtype>)(new Object() {
                                    Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
                                        return Comparator.comparingDouble((entity3) -> {
                                            return entity3.distanceToSqr(d3, d4, d5);
                                        });
                                    }
                                })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null);

                                if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                    entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect clear @s annoying_villagersbychentu:gedang");
                                }

                                entity2 = (Entity)this.world.getEntitiesOfClass(HerobrineEntity.class, AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D), (herobrineentity) -> {
                                    return true;
                                }).stream().sorted(((<undefinedtype>)(new Object() {
                                    Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
                                        return Comparator.comparingDouble((entity3) -> {
                                            return entity3.distanceToSqr(d3, d4, d5);
                                        });
                                    }
                                })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null);
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

                                MinecraftForge.EVENT_BUS.unregister(this);
                            }
                        })).start(levelaccessor, 1);
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
