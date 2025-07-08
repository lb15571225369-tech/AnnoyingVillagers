package com.pla.annoyingvillagers.Mixin;

import java.util.UUID;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.skill.BasicAttack;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

@Mixin(value = {BasicAttack.class}, remap = false)
public abstract class NoKnockDownProMixin {

    @Shadow
    @Final
    private static final UUID EVENT_UUID = UUID.fromString("a42e0198-fdbc-11eb-9a03-0242ac130003");

    @Inject(at = {@At("HEAD")}, method = {"onInitiate"})
    public void InjectOnInitiate(SkillContainer skillcontainer, CallbackInfo callbackinfo) {
        skillcontainer.getExecuter().getEventListener().addEventListener(EventType.DEALT_DAMAGE_EVENT_PRE, NoKnockDownProMixin.EVENT_UUID, (dealtdamageevent) -> {
            dealtdamageevent.getTarget().getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent((entitypatch) -> {
                if (entitypatch instanceof LivingEntityPatch) {
                    LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) entitypatch;

                    if (livingentitypatch.getEntityState().knockDown()) {
                        dealtdamageevent.getDamageSource().setStunType(StunType.NONE);
                    }
                }

            });
        });
    }
}
