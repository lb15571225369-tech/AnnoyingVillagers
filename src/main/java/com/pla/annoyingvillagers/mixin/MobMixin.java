package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import com.pla.annoyingvillagers.util.CommonGoals;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {Mob.class}, remap = true)
public class MobMixin {
    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void villagerRunAway(CallbackInfo ci) {
        Mob self = (Mob) (Object) this;
        if (self instanceof AbstractVillager abstractVillager) {
            CommonGoals.runAwayFromHerobrineGoals(abstractVillager, 5.0F);
            abstractVillager.goalSelector.addGoal(1, new AvoidEntityGoal<>(abstractVillager, AbstractIllager.class, 5.0F, 1.2D, 1.8D));
            abstractVillager.goalSelector.addGoal(1, new AvoidEntityGoal<>(abstractVillager, PlayerNpcEntity.class, 5.0F, 1.2D, 1.8D));
            abstractVillager.goalSelector.addGoal(1, new AvoidEntityGoal<>(abstractVillager, Player.class, 5.0F, 1.2D, 1.8D));
        }
    }
}

