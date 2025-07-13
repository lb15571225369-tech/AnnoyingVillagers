package com.pla.annoyingvillagers.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

@Mixin(value = {PlayerMobEntity.class}, remap = false)
public class PlayerMobMixin {
    @Inject(method = "addBehaviourGoals", at = @At("HEAD"), cancellable = true)
    private void injectTargetingAI(CallbackInfo ci) {
        PlayerMobEntity self = (PlayerMobEntity) (Object) this;

        self.goalSelector.addGoal(3, new MeleeAttackGoal(self, 1.2D, false));
        self.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(self, 1.0D));
        self.goalSelector.addGoal(1, new OpenDoorGoal(self, true));
        ((GroundPathNavigation) self.getNavigation()).setCanOpenDoors(true);
        self.targetSelector.addGoal(1, new HurtByTargetGoal(self, ZombifiedPiglin.class));

        CompoundTag data = self.getPersistentData();
        String role;

        if (!data.contains("av_hunt_target")) {
            List<String> roles = List.of(
                    "monster_hunter",
                    "player_hunter",
                    "hostile_hunt",
                    "passive_hunt",
                    "friendly"
            );
            role = roles.get(self.getRandom().nextInt(roles.size()));
            data.putString("av_hunt_target", role);
        } else {
            role = data.getString("av_hunt_target");
        }

        switch (role) {
            case "hostile_hunt" -> {
                self.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(self, Monster.class, true));
                self.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(self, Player.class, true));
            }
            case "passive_hunt" -> {
                self.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(self, Villager.class, true));
                self.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(self, Animal.class, true));
                self.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(self, IronGolem.class, true));
            }
            case "monster_hunter" -> {
                self.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(self, Monster.class, true));
            }
            case "player_hunter" -> {
                self.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(self, Player.class, true));
            }
            case "friendly" -> {
                self.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(self, Animal.class, true));
            }
        }

        ci.cancel();
    }

    @Inject(method = "isBaby", at = @At("HEAD"), cancellable = true)
    private void forceIsNotBaby(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

    @Inject(method = "setBaby", at = @At("HEAD"), cancellable = true)
    private void blockSetBaby(boolean isChild, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "finalizeSpawn", at = @At("RETURN"))
    private void teleportToSurfaceIfUnderground(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag, CallbackInfoReturnable<SpawnGroupData> cir) {
        Entity self = (Entity) (Object) this;

        if (world instanceof ServerLevel level && level.isDay()) {
            BlockPos pos = self.blockPosition();
            int currentY = pos.getY();
            int surfaceY = world.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, pos).getY();
            if (currentY < surfaceY - 5 && world.getRandom().nextFloat() < 0.6F) {
                BlockPos surfacePos = new BlockPos(pos.getX(), surfaceY, pos.getZ());
                self.setPos(surfacePos.getX() + 0.5, surfacePos.getY(), surfacePos.getZ() + 0.5);
            }
        }
    }
}
