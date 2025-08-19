package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.item.EnderAegisItem;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.skill.guard.ImpactGuardSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.entity.eventlistener.HurtEvent;

@Mixin(value = {ImpactGuardSkill.class}, remap = false)
public abstract class ImpactGuardSkillMixin {
    private static boolean isAdvancedBlockableDamageSource(DamageSource damageSource) {
        return damageSource.is(DamageTypeTags.IS_EXPLOSION) || damageSource.is(DamageTypes.MAGIC) || damageSource.is(DamageTypeTags.IS_FIRE) || damageSource.is(DamageTypeTags.IS_PROJECTILE) || damageSource.is(DamageTypeTags.BYPASSES_ARMOR);
    }

    @Inject(method = {"dealEvent"}, at = {@At("HEAD")}, cancellable = true)
    private void playerOnGuard(PlayerPatch<?> playerpatch, HurtEvent.Pre event, boolean advanced, CallbackInfo ci) {
        boolean isSpecialSource = isAdvancedBlockableDamageSource((DamageSource)event.getDamageSource());
        if (!isSpecialSource) {
            Player player = playerpatch.getOriginal();
            ItemStack itemStack = player.getMainHandItem();
            if (itemStack.getItem() instanceof EnderAegisItem && !player.level().isClientSide()) {
                if (itemStack.getTag().getBoolean("SecondForm")) {
                    ((EnderAegisItem) itemStack.getItem()).shieldShoot(player.level(), player);
                } else {
                    itemStack.getTag().putInt("ParryCount", (itemStack.getTag().contains("ParryCount") ? itemStack.getTag().getInt("ParryCount") : 0) + 1);
                }
            }
        }
    }
}
