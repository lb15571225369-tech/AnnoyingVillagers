package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.animations.BowAttackAnimation;
import com.pla.efclash_blade.skill.ClashBladeSkill;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(value = ClashBladeSkill.class, remap = false)
public class ClashBladeMixin {
    @Inject(method = "blacklistClashBladeAnimation", at = @At("HEAD"), cancellable = true)
    private static void rejectClashBladeFromAnimationsCondition(AssetAccessor<? extends StaticAnimation> dynamicAnimation,
                                                                EntityState entityState,
                                                                ServerPlayer serverPlayer,
                                                                CallbackInfoReturnable<Boolean> cir) {
        if (dynamicAnimation.get() instanceof BowAttackAnimation) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "getWeaponDestroyValueOnClash", at = @At("HEAD"), cancellable = true)
    private static void forceDestroyWeaponOnClash(AssetAccessor<? extends StaticAnimation> dynamicAnimation,
                                                                DamageSource damageSource,
                                                                CallbackInfoReturnable<Integer> cir) {
//        AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG] getWeaponDestroyValueOnClash is called with animation played: {}, for mob {}", dynamicAnimation, damageSource.getEntity() != null ? damageSource.getEntity().getDisplayName().getString() : "null");
    }

    @Inject(method = "moreLogicAfterClashing", at = @At("HEAD"), cancellable = true)
    private static void forceMoreLogicAfterClash(AssetAccessor<? extends StaticAnimation> dynamicAnimation,
                                                  DamageSource damageSource,
                                                  PlayerPatch<?> playerPatch,
                                                  ServerLevel serverLevel,
                                                  CallbackInfo ci) {
//        AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG] moreLogicAfterClashing is called with animation played: {}, for mob {}", dynamicAnimation, damageSource.getEntity() != null ? damageSource.getEntity().getDisplayName().getString() : "null");
    }
}