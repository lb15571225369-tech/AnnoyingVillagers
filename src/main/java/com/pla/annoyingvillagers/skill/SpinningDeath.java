package com.pla.annoyingvillagers.skill;

import java.util.List;
import java.util.Map;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class SpinningDeath extends WeaponInnateSkill {

    protected final StaticAnimation attackAnimation;

    public SpinningDeath(Builder builder) {
        super(builder);
        this.attackAnimation = AVAnimations.SpinningDeath;
    }

    public void onInitiate(SkillContainer skillcontainer) {
        if (!skillcontainer.getExecuter().isLogicalClient()) {
            this.setConsumption(skillcontainer, 6.0F);
            this.setConsumptionSynchronize((ServerPlayerPatch) skillcontainer.getExecuter(), 6.0F);
        }

        skillcontainer.setResource(6.0F);
    }

    public void executeOnServer(ServerPlayerPatch serverplayerpatch, FriendlyByteBuf friendlybytebuf) {
        serverplayerpatch.playAnimationSynchronized(this.attackAnimation, 0.0F);
        super.executeOnServer(serverplayerpatch, friendlybytebuf);
    }

    public List<Component> getTooltipOnItem(ItemStack itemstack, CapabilityItem capabilityitem, PlayerPatch<?> playerpatch) {
        List<Component> list = super.getTooltipOnItem(itemstack, capabilityitem, playerpatch);

        this.generateTooltipforPhase(list, itemstack, capabilityitem, playerpatch, (Map) this.properties.get(0), "Each Strike:");
        return list;
    }

    public boolean isExecutableState(PlayerPatch<?> playerpatch) {
        playerpatch.updateEntityState();
        EntityState entitystate = playerpatch.getEntityState();

        return !((Player) playerpatch.getOriginal()).isFallFlying() && playerpatch.currentLivingMotion != LivingMotions.FALL && entitystate.canUseSkill() && playerpatch.getEntityState().canBasicAttack();
    }

    public WeaponInnateSkill registerPropertiesToAnimation() {
        return this;
    }

    public void updateContainer(SkillContainer skillcontainer) {
        if (!skillcontainer.getExecuter().isLogicalClient()) {
            this.setConsumption(skillcontainer, 6.0F);
            this.setConsumptionSynchronize((ServerPlayerPatch) skillcontainer.getExecuter(), 6.0F);
        }

        skillcontainer.setResource(6.0F);
        skillcontainer.deactivate();
    }
}
