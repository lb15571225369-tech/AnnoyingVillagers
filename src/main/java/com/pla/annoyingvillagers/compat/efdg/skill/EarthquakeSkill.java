package com.pla.annoyingvillagers.compat.efdg.skill;

import java.util.UUID;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AVSkillDataKeys;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataKey;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.effect.EpicFightMobEffects;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

public class EarthquakeSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("b19d1eb4-fefa-11ec-b939-0242ac12000");
    public EarthquakeSkill(Builder builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecuter().getEventListener().addEventListener(
                EventType.HURT_EVENT_POST,
                EVENT_UUID,
                event -> {
                    if ((Boolean)container.getDataManager().getDataValue((SkillDataKey)AVSkillDataKeys.SUPERARMOR.get())) {
                        ((EpicFightDamageSource) event.getDamageSource()).setStunType(StunType.NONE);
                    }
                });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecuter().getEventListener().removeListener(EventType.HURT_EVENT_POST, EVENT_UUID);
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        executer.playAnimationSynchronized(AVAnimations.GREATSWORD_DUAL_EARTHQUAKE, 0.0F);
        executer.getSkill(this).getDataManager().setDataSync((SkillDataKey)AVSkillDataKeys.SUPERARMOR.get(), true, (ServerPlayer) executer.getOriginal());
        super.executeOnServer(executer, args);
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        if ((Boolean)container.getDataManager().getDataValue((SkillDataKey)AVSkillDataKeys.SUPERARMOR.get())) {
            Player player = (Player) container.getExecuter().getOriginal();
            player.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 5, 0, true, false, false));
        }
    }

    @Override
    public WeaponInnateSkill registerPropertiesToAnimation() {
        return this;
    }
}
