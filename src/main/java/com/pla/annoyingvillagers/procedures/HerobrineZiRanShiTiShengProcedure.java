package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class HerobrineZiRanShiTiShengProcedure {

    public static boolean execute(LevelAccessor levelaccessor, double d0, double d1, double d2) {
        return !levelaccessor.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3(d0, d1, d2), 5.0D, 5.0D, 5.0D), (monster) -> {
            return true;
        }).isEmpty();
    }
}
