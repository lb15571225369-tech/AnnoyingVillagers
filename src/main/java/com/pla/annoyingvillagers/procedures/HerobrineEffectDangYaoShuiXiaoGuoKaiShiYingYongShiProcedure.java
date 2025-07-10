//package com.pla.annoyingvillagers.procedures;
//
//import com.pla.annoyingvillagers.util.DelayedTask;
//import net.minecraft.Util;
//import net.minecraft.client.Minecraft;
//import net.minecraft.core.NonNullList;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.chat.ChatType;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.EquipmentSlot;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.Mob;
//import net.minecraft.world.entity.MobSpawnType;
//import net.minecraft.world.entity.SpawnGroupData;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.GameType;
//import net.minecraft.world.level.LevelAccessor;
//import com.pla.annoyingvillagers.entity.HerobrineEntity;
//import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
//
//public class HerobrineEffectDangYaoShuiXiaoGuoKaiShiYingYongShiProcedure {
//
//    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity) {
//        if (entity != null) {
//            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
//                levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("\u7cfb\u7edf\u63d0\u793a\uff1a" + entity.getDisplayName().getString() + "\u5df2\u88abHerobrine\u9644\u8eab"), ChatType.SYSTEM, Util.NIL_UUID);
//            }
//
//            if (levelaccessor instanceof ServerLevel) {
//                ServerLevel serverlevel = (ServerLevel)levelaccessor;
//                HerobrineEntity herobrineentity = new HerobrineEntity((EntityType)AnnoyingVillagersModEntities.HEROBRINE.get(), serverlevel);
//
//                herobrineentity.moveTo(d0, d1, d2, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
//                if (herobrineentity instanceof Mob) {
//                    Mob mob = (Mob)herobrineentity;
//
//                    mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(herobrineentity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
//                }
//
//                levelaccessor.addFreshEntity(herobrineentity);
//            }
//
//            if (entity instanceof ServerPlayer) {
//                ServerPlayer serverplayer = (ServerPlayer)entity;
//
//                serverplayer.setGameMode(GameType.SPECTATOR);
//            }
//
//            Entity entity1 = entity.getVehicle();
//            Player player;
//            NonNullList nonnulllist;
//            LivingEntity livingentity;
//            ItemStack itemstack;
//            LivingEntity livingentity1;
//            EquipmentSlot equipmentslot;
//
//            if (entity1 instanceof Player) {
//                player = (Player)entity1;
//                nonnulllist = player.getInventory().armor;
//                if (entity instanceof LivingEntity) {
//                    livingentity = (LivingEntity)entity;
//                    itemstack = livingentity.getItemBySlot(EquipmentSlot.FEET);
//                } else {
//                    itemstack = ItemStack.EMPTY;
//                }
//
//                nonnulllist.set(0, itemstack);
//                player.getInventory().setChanged();
//            } else if (entity1 instanceof LivingEntity) {
//                livingentity1 = (LivingEntity)entity1;
//                equipmentslot = EquipmentSlot.FEET;
//                if (entity instanceof LivingEntity) {
//                    livingentity = (LivingEntity)entity;
//                    itemstack = livingentity.getItemBySlot(EquipmentSlot.FEET);
//                } else {
//                    itemstack = ItemStack.EMPTY;
//                }
//
//                livingentity1.setItemSlot(equipmentslot, itemstack);
//            }
//
//            entity1 = entity.getVehicle();
//            if (entity1 instanceof Player) {
//                player = (Player)entity1;
//                nonnulllist = player.getInventory().armor;
//                if (entity instanceof LivingEntity) {
//                    livingentity = (LivingEntity)entity;
//                    itemstack = livingentity.getItemBySlot(EquipmentSlot.LEGS);
//                } else {
//                    itemstack = ItemStack.EMPTY;
//                }
//
//                nonnulllist.set(1, itemstack);
//                player.getInventory().setChanged();
//            } else if (entity1 instanceof LivingEntity) {
//                livingentity1 = (LivingEntity)entity1;
//                equipmentslot = EquipmentSlot.LEGS;
//                if (entity instanceof LivingEntity) {
//                    livingentity = (LivingEntity)entity;
//                    itemstack = livingentity.getItemBySlot(EquipmentSlot.LEGS);
//                } else {
//                    itemstack = ItemStack.EMPTY;
//                }
//
//                livingentity1.setItemSlot(equipmentslot, itemstack);
//            }
//
//            entity1 = entity.getVehicle();
//            if (entity1 instanceof Player) {
//                player = (Player)entity1;
//                nonnulllist = player.getInventory().armor;
//                if (entity instanceof LivingEntity) {
//                    livingentity = (LivingEntity)entity;
//                    itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
//                } else {
//                    itemstack = ItemStack.EMPTY;
//                }
//
//                nonnulllist.set(2, itemstack);
//                player.getInventory().setChanged();
//            } else if (entity1 instanceof LivingEntity) {
//                livingentity1 = (LivingEntity)entity1;
//                equipmentslot = EquipmentSlot.CHEST;
//                if (entity instanceof LivingEntity) {
//                    livingentity = (LivingEntity)entity;
//                    itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
//                } else {
//                    itemstack = ItemStack.EMPTY;
//                }
//
//                livingentity1.setItemSlot(equipmentslot, itemstack);
//            }
//
//            entity1 = entity.getVehicle();
//            if (entity1 instanceof Player) {
//                player = (Player)entity1;
//                nonnulllist = player.getInventory().armor;
//                if (entity instanceof LivingEntity) {
//                    livingentity = (LivingEntity)entity;
//                    itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
//                } else {
//                    itemstack = ItemStack.EMPTY;
//                }
//
//                nonnulllist.set(3, itemstack);
//                player.getInventory().setChanged();
//            } else if (entity1 instanceof LivingEntity) {
//                livingentity1 = (LivingEntity)entity1;
//                equipmentslot = EquipmentSlot.HEAD;
//                if (entity instanceof LivingEntity) {
//                    livingentity = (LivingEntity)entity;
//                    itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
//                } else {
//                    itemstack = ItemStack.EMPTY;
//                }
//
//                livingentity1.setItemSlot(equipmentslot, itemstack);
//            }
//
//            if (!entity.level.isClientSide() && entity.getServer() != null) {
//                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "tag @s remove sp");
//            }
//
//            if (!entity.level.isClientSide() && entity.getServer() != null) {
//                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "title @s title {\"text\":\"\u4f60\u5df2\u88ab\u9644\u8eab\",\"color\":\"red\"}");
//            }
//
//            if (!entity.level.isClientSide() && entity.getServer() != null) {
//                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "titles display titles:herobrine");
//            }
//
//            Player player1;
//
//            if (levelaccessor.players().size() == 1) {
//                if (entity instanceof Player) {
//                    player1 = (Player)entity;
//                    if (!player1.level.isClientSide()) {
//                        player1.displayClientMessage(new TextComponent("\u5728\u804a\u5929\u680f\u91cc\u8f93\u5165/leave @s\u53ef\u89e3\u8131"), false);
//                    }
//                }
//            } else {
//                if (entity instanceof Player) {
//                    player1 = (Player)entity;
//                    if (!player1.level.isClientSide()) {
//                        player1.displayClientMessage(new TextComponent("\u4f60\u5df2\u88ab\u9644\u8eab\uff0c\u53ef\u82b1\u8d3910\u7ea7\u7ecf\u9a8c\u6765\u89e3\u8131"), false);
//                    }
//                }
//
//                if (entity instanceof Player) {
//                    player1 = (Player)entity;
//                    if (!player1.level.isClientSide()) {
//                        player1.displayClientMessage(new TextComponent("\u4e2d\u9014\u82e5\u9000\u51fa\u6e38\u620f\uff0c\u5206\u8eab\u5219\u81ea\u52a8\u89e3\u8131\uff0c\u4f60\u5c06\u7acb\u5373\u6b7b\u4ea1\u5e76\u8fd4\u56de\u91cd\u751f\u70b9"), false);
//                    }
//                }
//
//                new DelayedTask(20) {
//                    @Override
//                    public void run() {
//                        Entity entity2 = entity;
//
//                        if (!entity2.level.isClientSide() && entity2.getServer() != null) {
//                            entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "title @s title {\"text\":\"\u9000\u51fa\u6e38\u620f\u5c06\u4f1a\u6b7b\u4ea1\",\"color\":\"yellow\"}");
//                        }
//                    }
//                };
//            }
//
//            Minecraft.getInstance().gameRenderer.loadEffect(new ResourceLocation("minecraft:shaders/post/invert.json"));
//        }
//    }
//}
