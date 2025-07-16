package com.pla.annoyingvillagers.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

public class BlueDemonSkillLightingEffectDuringDurationProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            if (levelaccessor instanceof ServerLevel) {
                ServerLevel serverlevel = (ServerLevel) levelaccessor;
                LightningBolt lightningbolt = (LightningBolt) EntityType.LIGHTNING_BOLT.create(serverlevel);

                lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(d0, d1, d2)));
                lightningbolt.setVisualOnly(false);
                serverlevel.addFreshEntity(lightningbolt);
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect clear @s annoyingvillagers:block");
            }

        }
    }
}
