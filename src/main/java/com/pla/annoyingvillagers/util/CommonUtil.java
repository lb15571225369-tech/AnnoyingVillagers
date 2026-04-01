package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.entity.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class CommonUtil {
    public static boolean isAvDamageableEfnWeaponsMob(Entity livingEntity) {
        return livingEntity instanceof BlueDemonEntity
                || livingEntity instanceof AngrySteveEntity
                || livingEntity instanceof HerobrineMob
                || livingEntity instanceof HerobrineGregEntity
                || livingEntity instanceof LowHerobrineCloneEntity
                || livingEntity instanceof LowShadowHerobrineCloneEntity;
    }

    public static boolean isAvRunawayJudgementCutEndMob(Entity livingEntity) {
        return livingEntity instanceof BlueDemonEntity
                || livingEntity instanceof AVNpc
                || livingEntity instanceof HerobrineMob
                || livingEntity instanceof HerobrineGregEntity
                || livingEntity instanceof LowHerobrineCloneEntity
                || livingEntity instanceof LowShadowHerobrineCloneEntity;
    }
}
