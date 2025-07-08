package com.pla.annoyingvillagers.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class DropAllItemSpawnDangShiTiZaiFangKuaiZhongPengZhuangShiProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            if (ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("minecraft:player")) {
                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "player " + entity.getDisplayName().getString() + " drop all");
                }

                levelaccessor.setBlock(new BlockPos(d0, d1, d2), Blocks.AIR.defaultBlockState(), 3);
            }

        }
    }
}
