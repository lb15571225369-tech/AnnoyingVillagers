package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.util.CommonGoals;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

import java.util.List;
import java.util.Random;

@Mixin(value = {PlayerMobEntity.class}, remap = false)
public class PlayerMobMixinRemapFalse {
    private void hostileHunterPlayerMob(PlayerMobEntity self) {
        self.goalSelector.addGoal(2, new MeleeAttackGoal(self, 1.2D, false));
        self.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(self, Player.class, true));
        self.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(self, PlayerMobEntity.class, true));
        CommonGoals.attackAllMonstersGoals(self);
        CommonGoals.attackAllNpcGoals(self);
    }
    private void villagerHunterPlayerMob(PlayerMobEntity self) {
        CommonGoals.runAwayFromHerobrineGoals(self, 20.0F);
        self.goalSelector.addGoal(2, new AvoidEntityGoal<>(self, PlayerMobEntity.class, 12.0F, 1.2D, 1.8D));
        self.goalSelector.addGoal(2, new AvoidEntityGoal<>(self, Player.class, 12.0F, 1.2D, 1.8D));
        self.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(self, Villager.class, true));
        self.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(self, JevEntity.class, true));
        self.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(self, IronGolem.class, true));
        CommonGoals.attackAllVillagerArmyGoal(self);
        self.goalSelector.addGoal(3, new MeleeAttackGoal(self, 1.2D, false));
    }
    private void monsterHunterPlayerMob(PlayerMobEntity self) {
        CommonGoals.attackAllMonstersGoals(self);
        CommonGoals.runAwayFromVillagerArmyGoals(self);
    }
    private void playerHunterPlayerMob(PlayerMobEntity self) {
        CommonGoals.runAwayFromHerobrineGoals(self, 20.0F);
        CommonGoals.runAwayFromVillagerArmyGoals(self);
        self.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(self, Player.class, true));
        self.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(self, PlayerMobEntity.class, true));
        CommonGoals.attackAllNpcGoals(self);
    }
    private void animalHunterPlayerMob(PlayerMobEntity self) {
        CommonGoals.runAwayFromHerobrineGoals(self, 20.0F);
        CommonGoals.runAwayFromVillagerArmyGoals(self);
        self.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(self, Animal.class, true));
        self.goalSelector.addGoal(2, new AvoidEntityGoal<>(self, PlayerMobEntity.class, 12.0F, 1.2D, 1.8D));
        self.goalSelector.addGoal(2, new AvoidEntityGoal<>(self, Player.class, 12.0F, 1.2D, 1.8D));
    }
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
                hostileHunterPlayerMob(self);
            }
            case "village_hunter" -> {
                villagerHunterPlayerMob(self);
            }
            case "monster_hunter" -> {
                monsterHunterPlayerMob(self);
            }
            case "player_hunter" -> {
                playerHunterPlayerMob(self);
            }
            case "animal_hunter" -> {
                animalHunterPlayerMob(self);
            }
            default -> {
                Random random = new Random();
                if (random.nextFloat() < 0.2) {
                    data.putString("behavior", "hostile_hunter");
                    hostileHunterPlayerMob(self);
                } else if (random.nextFloat() < 0.4) {
                    data.putString("behavior", "village_hunter");
                    villagerHunterPlayerMob(self);
                } else if (random.nextFloat() < 0.6) {
                    data.putString("behavior", "monster_hunter");
                    monsterHunterPlayerMob(self);
                } else if (random.nextFloat() < 0.8) {
                    data.putString("behavior", "player_hunter");
                    playerHunterPlayerMob(self);
                } else {
                    data.putString("behavior", "animal_hunter");
                    animalHunterPlayerMob(self);
                }
            }
        }

        ci.cancel();
    }
}
