package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class HerobrineNaturalSpawnConditionProcedure {

    public static boolean execute(LevelAccessor levelaccessor) {
        boolean flag;

        if (levelaccessor instanceof Level) {
            Level level = (Level) levelaccessor;

            if (level.isDay()) {
                flag = true;
                return !flag;
            }
        }

        flag = false;
        return !flag;
    }
}
