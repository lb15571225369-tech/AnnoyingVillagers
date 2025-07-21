package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.entity.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

import java.util.List;

@Mixin(value = {PlayerMobEntity.class}, remap = false)
public class PlayerMobMixinRemapFalse {
    @Inject(method = "addBehaviourGoals", at = @At("HEAD"), cancellable = true)
    private void injectTargetingAI(CallbackInfo ci) {
        PlayerMobEntity self = (PlayerMobEntity) (Object) this;
        self.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(self, 1.0D));
        self.goalSelector.addGoal(3, new OpenDoorGoal(self, true));
        self.targetSelector.addGoal(1, new HurtByTargetGoal(self).setAlertOthers());
        ((GroundPathNavigation) self.getNavigation()).setCanOpenDoors(true);

        CompoundTag data = self.getPersistentData();
        String role;

        if (!data.contains("behavior")) {
            List<String> roles = List.of(
                    "monster_hunter",
                    "player_hunter",
                    "hostile_hunt",
                    "passive_hunt",
                    "animal_hunter"
            );
            role = roles.get(self.getRandom().nextInt(roles.size()));
            data.putString("behavior", role);
        } else {
            role = data.getString("behavior");
        }

        switch (role) {
            case "hostile_hunter" -> {
                self.goalSelector.addGoal(2, new MeleeAttackGoal(self, 1.2D, false));
                self.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(self, Monster.class, true));
                self.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(self, Player.class, true));
                self.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(self, HerobrineEntity.class, true));
                self.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(self, Herobrine2Entity.class, true));
                self.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(self, BlueDemonEntity.class, true));
                self.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(self, BlueDemon2Entity.class, true));
            }
            case "village_hunter" -> {
                self.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(self, Villager.class, true));
                self.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(self, IronGolem.class, true));
                self.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(self, VillagerScoutEntity.class, true));
                self.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(self, VillagerScoutCaptainEntity.class, true));
                self.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(self, RedVillagerGeneralEntity.class, true));
                self.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(self, BlueVillagerGeneralEntity.class, true));
                self.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(self, GreenVillagerGeneralEntity.class, true));
                self.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(self, PurpleVillagerGeneralEntity.class, true));
                self.goalSelector.addGoal(3, new MeleeAttackGoal(self, 1.2D, false));
            }
            case "monster_hunter" -> {
                self.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(self, Monster.class, true));
                self.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(self, HerobrineEntity.class, true));
                self.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(self, Herobrine2Entity.class, true));
                self.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(self, BlueDemonEntity.class, true));
                self.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(self, BlueDemon2Entity.class, true));
            }
            case "player_hunter" -> {
                self.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(self, Player.class, true));
            }
            case "animal_hunter" -> {
                self.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(self, Animal.class, true));
            }
        }

        ci.cancel();
    }
}
