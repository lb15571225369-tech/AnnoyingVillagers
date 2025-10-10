package com.pla.annoyingvillagers.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import com.pla.annoyingvillagers.util.CommonGoals;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

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

    @Inject(method = "finalizeSpawn", at = @At("RETURN"))
    private void villagerTeamJoin(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag, CallbackInfoReturnable<SpawnGroupData> cir) {
        Mob self = (Mob) (Object) this;
        if (self instanceof AbstractGolem abstractGolem) {
            if (!abstractGolem.level().isClientSide() && self.getServer() != null) {
                try {
                    abstractGolem.getServer().getCommands().getDispatcher().execute(
                            "team add villagers",
                            abstractGolem.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {

                }
            }

            if (!abstractGolem.level().isClientSide() && abstractGolem.getServer() != null) {
                try {
                    abstractGolem.getServer().getCommands().getDispatcher().execute(
                            "team modify villagers friendlyFire false",
                            abstractGolem.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {

                }
            }

            if (!abstractGolem.level().isClientSide() && abstractGolem.getServer() != null) {
                try {
                    abstractGolem.getServer().getCommands().getDispatcher().execute(
                            "team join villagers @s",
                            abstractGolem.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {

                }
            }
        }
    }
}

