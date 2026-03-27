package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.compat.EpicFightNightFall;
import com.pla.annoyingvillagers.compat.EpicFightSwordSoaring;
import com.pla.annoyingvillagers.entity.SledgehammerHerobrineEntity;
import com.pla.annoyingvillagers.entity.SwordsmanHerobrineEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.fml.ModList;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.*;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.pla.annoyingvillagers.util.EpicfightUtil.isAnimationDangerous;

public class EscapeUtil {
    public static boolean checkEscape(Mob mob) {
        LivingEntity target = mob.getTarget();
        LivingEntityPatch<?> targetLivingEntityPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
        if (target == null || targetLivingEntityPatch == null) return false;
        AssetAccessor<? extends StaticAnimation> targetDynamicAnimation = Objects.requireNonNull(targetLivingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
        return isAnimationDangerous(targetDynamicAnimation);
    }
}
