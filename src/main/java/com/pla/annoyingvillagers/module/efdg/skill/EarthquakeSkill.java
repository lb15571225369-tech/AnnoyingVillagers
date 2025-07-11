package com.pla.annoyingvillagers.module.efdg.skill;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.skill.Skill.Builder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager.SkillDataKey;
import yesman.epicfight.skill.SkillDataManager.ValueType;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.effect.EpicFightMobEffects;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

public class EarthquakeSkill extends WeaponInnateSkill {

    private static final UUID EVENT_UUID = UUID.fromString("b19d1eb4-fefa-11ec-b939-0242ac12000");
    public static final SkillDataKey<Boolean> SUPERARMOR = SkillDataKey.createDataKey(ValueType.BOOLEAN);

    public EarthquakeSkill(Builder builder) {
        super(builder);
    }

    public void onInitiate(SkillContainer skillcontainer) {
        super.onInitiate(skillcontainer);
        skillcontainer.getDataManager().registerData(EarthquakeSkill.SUPERARMOR);
        skillcontainer.getExecuter().getEventListener().addEventListener(EventType.HURT_EVENT_POST, EarthquakeSkill.EVENT_UUID, (post) -> {
            if ((Boolean) skillcontainer.getDataManager().getDataValue(EarthquakeSkill.SUPERARMOR)) {
                ((EpicFightDamageSource) post.getDamageSource()).setStunType(StunType.NONE);
            }

        });
    }

    public void onRemoved(SkillContainer skillcontainer) {
        skillcontainer.getExecuter().getEventListener().removeListener(EventType.HURT_EVENT_POST, EarthquakeSkill.EVENT_UUID);
    }

    public void executeOnServer(ServerPlayerPatch serverplayerpatch, FriendlyByteBuf friendlybytebuf) {
        serverplayerpatch.playAnimationSynchronized(AVAnimations.GREATSWORD_DUAL_EARTHQUAKE, 0.0F);
        serverplayerpatch.getSkill(this).getDataManager().setDataSync(EarthquakeSkill.SUPERARMOR, true, (ServerPlayer) serverplayerpatch.getOriginal());
        super.executeOnServer(serverplayerpatch, friendlybytebuf);
    }

    public List<Component> getTooltipOnItem(ItemStack itemstack, CapabilityItem capabilityitem, PlayerPatch<?> playerpatch) {
        List<Component> list = super.getTooltipOnItem(itemstack, capabilityitem, playerpatch);

        this.generateTooltipforPhase(list, itemstack, capabilityitem, playerpatch, (Map) this.properties.get(1), "Swing:");
        this.generateTooltipforPhase(list, itemstack, capabilityitem, playerpatch, (Map) this.properties.get(2), "Slam :");
        return list;
    }

    public void updateContainer(SkillContainer skillcontainer) {
        super.updateContainer(skillcontainer);
        if ((Boolean) skillcontainer.getDataManager().getDataValue(EarthquakeSkill.SUPERARMOR)) {
            ((Player) skillcontainer.getExecuter().getOriginal()).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 5, 0, true, false, false));
        }

    }

    public WeaponInnateSkill registerPropertiesToAnimation() {
        return this;
    }
}
