package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.clazz.PathfinderMobInventory;
import com.pla.annoyingvillagers.entity.AegisHerobrineEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.efclash_blade.event.MobClashBladeEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.asset.AssetAccessor;

@Mixin(value = MobClashBladeEvent.class, remap = false)
public class ClashBladeMixin {
    @Inject(method = "customAdditionClashBladeLogic", at = @At("HEAD"))
    private static void addMoreClashBladeCondition(LivingAttackEvent livingAttackEvent,
                                 AssetAccessor<? extends DynamicAnimation> defenderDynamicAnimation,
                                 EntityState defenderEntityState, CallbackInfoReturnable<Boolean> cir) {
        Entity defenderEntity = livingAttackEvent.getEntity();
        if (defenderEntity instanceof AegisHerobrineEntity
                && defenderDynamicAnimation == AVAnimations.SHIELD_MAINHAND
                && defenderEntityState.getLevel() == 3) {
            cir.setReturnValue(true);
        }

        if (defenderEntity instanceof AegisHerobrineEntity
                && defenderDynamicAnimation == AVAnimations.MOB_NAPOLEON_RELOAD_1) {
            cir.setReturnValue(true);
        }

        if (defenderEntity instanceof PathfinderMobInventory pathfinderMobInventory
                && livingAttackEvent.getSource().getDirectEntity() instanceof Projectile projectile
                && pathfinderMobInventory.getBlockDamage() == null
                && defenderEntity.onGround()) {
            pathfinderMobInventory.setBlockDamage(projectile);
            cir.setReturnValue(true);
        }
    }
}