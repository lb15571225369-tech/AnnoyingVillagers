package com.pla.annoyingvillagers.mixin;

import com.nameless.indestructible.world.capability.Utils.CapabilityState;
import com.nameless.indestructible.world.capability.Utils.IAdvancedCapability;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.AegisHerobrineEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.EpicFightDamageType;

@Mixin(value = {CapabilityState.class}, remap = false)
public abstract class IndestructibleMixin {
    @Inject(method = {"tryProcess"}, at = {@At("HEAD")}, cancellable = true)
    private void aegisHerobrineOnGuard(DamageSource damageSource, float amount, CallbackInfoReturnable<AttackResult> cir) {
        CapabilityState self = (CapabilityState) (Object) this;
        Entity victim = self.getPatch().getOriginal();

        if (victim instanceof AegisHerobrineEntity aegisHerobrineEntity) {
            boolean guardSuccess = true;
            MobPatch animation = self.getPatch();
            if (!(animation instanceof IAdvancedCapability iac)) {
            } else {
                if (iac.isBlocking()) {
                    boolean isFront = false;
                    Vec3 sourceLocation = damageSource.getSourcePosition();
                    if (sourceLocation != null) {
                        Vec3 viewVector = ((Mob)self.getPatch().getOriginal()).getViewVector(1.0F);
                        Vec3 toSourceLocation = sourceLocation.subtract(((Mob)self.getPatch().getOriginal()).position()).normalize();
                        if (toSourceLocation.dot(viewVector) > (double)0.0F) {
                            isFront = true;
                        }
                    }
                    if (iac.isBlockableSource(damageSource) && isFront) {
                        float impact;
                        if (damageSource instanceof EpicFightDamageSource) {
                            EpicFightDamageSource efDamageSource = (EpicFightDamageSource) damageSource;
                            impact = efDamageSource.getImpact();
                            if (efDamageSource.is(EpicFightDamageType.GUARD_PUNCTURE)) {
                                guardSuccess = false;
                            }
                        } else {
                            impact = amount / 3.0F;
                        }

                        float cost = self.maxParryTimes > 0 && self.parryCounter + 1 < self.maxParryTimes ? iac.getParryCostMultiply() : iac.getGuardCostMultiply();
                        float stamina = iac.getStamina() - impact * cost;
                        if (!(stamina >= 0.0F)) {
                            guardSuccess = false;
                        }

                        if (guardSuccess) {
                            AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG]: IndestructibleMixin guardSuccess for: {}", victim.getName());
                        } else {
                            AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG]: IndestructibleMixin guardFailed for: {}", victim.getName());
                        }
                    }
                }
            }
        }
    }
}
