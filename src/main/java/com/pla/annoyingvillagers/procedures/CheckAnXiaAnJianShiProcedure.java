package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import com.pla.annoyingvillagers.animations.types.AttackBreakAnimation;
import com.pla.annoyingvillagers.capabilities.AVCategories;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.HitAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;

public class CheckAnXiaAnJianShiProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

            if (livingentitypatch != null) {
                DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();

                if (!(dynamicanimation instanceof AttackAnimation) && !(dynamicanimation instanceof LongHitAnimation) && !(dynamicanimation instanceof HitAnimation) && !(dynamicanimation instanceof AttackBreakAnimation)) {
                    PlayerPatch<?> playerpatch = (PlayerPatch) EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);

                    if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.SWORD && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.AXE && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.TACHI && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.DAGGER && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.UCHIGATANA && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.TRIDENT) {
                        if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.KNIFE && !entity.level.isClientSide() && entity.getServer() != null) {
                            entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoyingvillagers:biped/other/knife_check\" 0 1");
                        }
                    } else if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoyingvillagers:biped/other/check\" 0 1");
                    }
                } else {
                    entity.clearFire();
                }
            }

        }
    }
}
