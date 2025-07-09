//package com.pla.annoyingvillagers.procedures;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraftforge.registries.ForgeRegistries;
//import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
//
//public class C4spawnDangShiTiZaiFangKuaiZhongPengZhuangShiProcedure {
//
//    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
//        if (entity != null) {
//            if (ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("minecraft:player")) {
//                BlockPos blockpos;
//                ServerPlayer serverplayer;
//                int i;
//                label56: {
//                    blockpos = new BlockPos;
//                    if (entity instanceof ServerPlayer) {
//                        serverplayer = (ServerPlayer)entity;
//                        if (!serverplayer.level.isClientSide()) {
//                            i = serverplayer.getRespawnDimension().equals(serverplayer.level.dimension()) && serverplayer.getRespawnPosition() != null ? serverplayer.getRespawnPosition().getX() : serverplayer.level.getLevelData().getXSpawn();
//                            break label56;
//                        }
//                    }
//
//                    i = 0;
//                }
//
//                int j;
//                label47: {
//                    if (entity instanceof ServerPlayer) {
//                        serverplayer = (ServerPlayer)entity;
//                        if (!serverplayer.level.isClientSide()) {
//                            j = serverplayer.getRespawnDimension().equals(serverplayer.level.dimension()) && serverplayer.getRespawnPosition() != null ? serverplayer.getRespawnPosition().getY() : serverplayer.level.getLevelData().getYSpawn();
//                            break label47;
//                        }
//                    }
//
//                    j = 0;
//                }
//
//                int k;
//                label38: {
//                    if (entity instanceof ServerPlayer) {
//                        serverplayer = (ServerPlayer)entity;
//                        if (!serverplayer.level.isClientSide()) {
//                            k = serverplayer.getRespawnDimension().equals(serverplayer.level.dimension()) && serverplayer.getRespawnPosition() != null ? serverplayer.getRespawnPosition().getZ() : serverplayer.level.getLevelData().getZSpawn();
//                            break label38;
//                        }
//                    }
//
//                    k = 0;
//                }
//
//                blockpos.<init>(i, j, k);
//                levelaccessor.setBlock(blockpos, ((Block)AnnoyingVillagersModBlocks.C_4.get()).defaultBlockState(), 3);
//                levelaccessor.setBlock(new BlockPos(d0, d1, d2), Blocks.AIR.defaultBlockState(), 3);
//            }
//
//        }
//    }
//}
