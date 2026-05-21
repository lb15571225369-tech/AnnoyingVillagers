package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.util.projectile.BreakPowerHolder;
import com.pla.annoyingvillagers.util.projectile.ProjectileBlockBreaker;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowBreakMixin implements BreakPowerHolder {
    @Unique private float breakPower = 0.0f;
    @Unique private boolean powerInit = false;

    @Override
    public float getBreakPower() {
        return breakPower;
    }

    @Override
    public void setBreakPower(float power) {
        breakPower = power;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void initPower(CallbackInfo ci) {
        AbstractArrow self = (AbstractArrow) (Object) this;
        if (self.level().isClientSide) return;

        if (!powerInit) {
            breakPower = ProjectileBlockBreaker.computeInitialPower(self);
            powerInit = true;
        }
    }

    @Redirect(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;clip(Lnet/minecraft/world/level/ClipContext;)Lnet/minecraft/world/phys/BlockHitResult;")
    )
    private BlockHitResult redirectClip(Level level, ClipContext ctx) {
        AbstractArrow self = (AbstractArrow) (Object) this;
        return ProjectileBlockBreaker.clip(level, ctx, self);
    }
}

