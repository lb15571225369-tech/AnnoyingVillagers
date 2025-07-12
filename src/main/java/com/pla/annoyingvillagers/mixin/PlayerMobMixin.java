package com.pla.annoyingvillagers.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

import javax.annotation.Nullable;
import java.util.Random;

@Mixin(value = {PlayerMobEntity.class}, remap = false)
public class PlayerMobMixin {
    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void injectTargetingAI(CallbackInfo ci) {
        PlayerMobEntity self = (PlayerMobEntity) (Object) this;
        CompoundTag data = self.getPersistentData();

        String role;
        if (!data.contains("av_hunt_target")) {
            role = (new Random().nextFloat() < 0.8f) ? "hostile_hunt" : "passive_hunt";
            data.putString("av_hunt_target", role);
        } else {
            role = data.getString("av_hunt_target");
        }

        switch (role) {
            case "hostile_hunt" -> {
                self.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(self, Monster.class, true));
                self.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(self, PlayerMobEntity.class, true));
            }
            case "passive_hunt" -> {
                self.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(self, Villager.class, true));
                self.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(self, Animal.class, true));
            }
        }
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

        BlockPos pos = self.blockPosition();
        int currentY = pos.getY();
        int surfaceY = world.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, pos).getY();

        if (currentY < surfaceY - 5 && world.getRandom().nextFloat() < 0.6F) {
            BlockPos surfacePos = new BlockPos(pos.getX(), surfaceY, pos.getZ());
            self.setPos(surfacePos.getX() + 0.5, surfacePos.getY(), surfacePos.getZ() + 0.5);
        }
    }
}
