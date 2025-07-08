package com.pla.annoyingvillagers.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;

public class DarkOBFarDangTouSheWuSheZhongZheGeFangKuaiProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            if (levelaccessor.isEmptyBlock(new BlockPos(d0, d1 + 1.0D, d2))) {
                levelaccessor.setBlock(new BlockPos(d0, d1 + 1.0D, d2), ((Block) AnnoyingVillagersModBlocks.DARK_OB_UP.get()).defaultBlockState(), 3);
                entity.setDeltaMovement(new Vec3(0.0D, 0.0D, 0.0D));
                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity) entity;

                    if (!livingentity.level.isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 8, false, false));
                    }
                }
            }

        }
    }
}
