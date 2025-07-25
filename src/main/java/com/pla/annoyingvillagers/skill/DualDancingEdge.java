package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.network.FriendlyByteBuf;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class DualDancingEdge extends WeaponInnateSkill {
    protected final StaticAnimation attackAnimation;

    public DualDancingEdge(Builder builder) {
        super(builder);
        this.attackAnimation = AVAnimations.DUAL_DANCING_EDGE;
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
}
