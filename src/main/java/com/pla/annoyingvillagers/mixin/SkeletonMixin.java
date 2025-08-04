package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.util.CommonGoals;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

@Mixin(value = {AbstractSkeleton.class}, remap = true)
public class SkeletonMixin {
    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void monsterTargetNpc(CallbackInfo ci) {
        if ((Object) this instanceof Monster monster) {
            CommonGoals.attackAllVillagerArmyGoal(monster);
            monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, PlayerMobEntity.class, true));
            CommonGoals.attackAllNpcGoals(monster);
        }
    }
}
