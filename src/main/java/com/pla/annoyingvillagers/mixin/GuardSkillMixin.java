package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.item.EnderAegisItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.entity.eventlistener.HurtEvent;

@Mixin(value = {GuardSkill.class}, remap = false)
public abstract class GuardSkillMixin {
    @Inject(method = {"dealEvent"}, at = {@At("HEAD")}, cancellable = true)
    private void playerOnGuard(PlayerPatch<?> playerpatch, HurtEvent.Pre event, boolean advanced, CallbackInfo ci) {
        Player player = playerpatch.getOriginal();
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem() instanceof EnderAegisItem) {
            if (itemStack.getTag().getBoolean("SecondForm")) {
                ((EnderAegisItem) itemStack.getItem()).shieldShoot(player.level(), player);
            } else {
                itemStack.getTag().putInt("ParryCount", (itemStack.getTag().contains("ParryCount") ? itemStack.getTag().getInt("ParryCount") : 0) + 1);
            }
        }
    }
}
