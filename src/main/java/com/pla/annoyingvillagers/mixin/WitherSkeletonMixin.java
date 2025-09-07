package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.entity.AlexEntity;
import com.pla.annoyingvillagers.entity.NullEntity;
import com.pla.annoyingvillagers.util.CommonGoals;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(value = {WitherSkeleton.class}, remap = true)
public class WitherSkeletonMixin {
    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void nullWitherSkeleton(CallbackInfo ci) {
        if ((Object) this instanceof WitherSkeleton witherSkeleton && witherSkeleton.getPersistentData().contains("SpawnByNull")) {
            UUID nullUUID = witherSkeleton.getPersistentData().getUUID("SpawnByNull");
            Entity entity = ((ServerLevel) witherSkeleton.level()).getEntity(nullUUID);
            if (entity instanceof NullEntity nullEntity) {
                witherSkeleton.goalSelector.addGoal(2, new Goal() {
                    @Override
                    public boolean canUse() {
                        return nullEntity != null && nullEntity.isAlive();
                    }

                    @Override
                    public void tick() {
                        if (nullEntity != null && nullEntity.isAlive()) {
                            witherSkeleton.getNavigation().moveTo(nullEntity, 2.0D);
                        }
                    }

                    @Override
                    public boolean canContinueToUse() {
                        return nullEntity != null && nullEntity.isAlive() && witherSkeleton.distanceTo(nullEntity) > 10.0D;
                    }
                });
                witherSkeleton.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(witherSkeleton, LivingEntity.class, 10, true, false, (target) -> nullEntity != null
                        && nullEntity.isAlive()
                        && target != null
                        && nullEntity.getTarget() == target));
                witherSkeleton.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(witherSkeleton, LivingEntity.class, 10, true, false, (target) -> nullEntity != null
                        && nullEntity.isAlive()
                        && target != null
                        && target.getLastHurtMob() == nullEntity));
            }
        }
    }
}
