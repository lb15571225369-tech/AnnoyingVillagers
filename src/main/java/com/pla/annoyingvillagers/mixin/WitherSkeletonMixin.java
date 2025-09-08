package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.entity.NullEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
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
        WitherSkeleton self = (WitherSkeleton) (Object) this;
        self.goalSelector.addGoal(2, new Goal() {
            @Override
            public boolean canUse() {
                if (self.getPersistentData().contains("SpawnByNull")) {
                    UUID nullUUID = self.getPersistentData().getUUID("SpawnByNull");
                    Entity entity = ((ServerLevel) self.level()).getEntity(nullUUID);
                    if (entity instanceof NullEntity nullEntity) {
                        return nullEntity != null && nullEntity.isAlive();
                    }
                }
                return false;
            }

            @Override
            public void tick() {
                if (self.getPersistentData().contains("SpawnByNull")) {
                    UUID nullUUID = self.getPersistentData().getUUID("SpawnByNull");
                    Entity entity = ((ServerLevel) self.level()).getEntity(nullUUID);
                    if (entity instanceof NullEntity nullEntity) {
                        self.getNavigation().moveTo(nullEntity, 2.0D);
                    }
                }
            }

            @Override
            public boolean canContinueToUse() {
                if (self.getPersistentData().contains("SpawnByNull")) {
                    UUID nullUUID = self.getPersistentData().getUUID("SpawnByNull");
                    Entity entity = ((ServerLevel) self.level()).getEntity(nullUUID);
                    if (entity instanceof NullEntity nullEntity) {
                        return nullEntity != null && nullEntity.isAlive() && self.distanceTo(nullEntity) > 10.0D;
                    }
                }
                return false;
            }
        });
        self.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(self, LivingEntity.class, 10, true, false, (target) -> {
            if (self.getPersistentData().contains("SpawnByNull")) {
                UUID nullUUID = self.getPersistentData().getUUID("SpawnByNull");
                Entity entity = ((ServerLevel) self.level()).getEntity(nullUUID);
                if (entity instanceof NullEntity nullEntity) {
                    return target != null && nullEntity.isAlive() && nullEntity.getTarget() == target;
                }
            }
            return false;
        }));
        self.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(self, LivingEntity.class, 10, true, false, (target) -> {
            if (self.getPersistentData().contains("SpawnByNull")) {
                UUID nullUUID = self.getPersistentData().getUUID("SpawnByNull");
                Entity entity = ((ServerLevel) self.level()).getEntity(nullUUID);
                if (entity instanceof NullEntity nullEntity) {
                    return target != null && nullEntity.isAlive() && target.getLastHurtMob() == nullEntity;
                }
            }
            return false;
        }));
    }
}
