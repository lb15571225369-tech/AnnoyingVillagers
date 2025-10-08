package com.pla.annoyingvillagers.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(value = {AbstractVillager.class}, remap = true)
public class VillagerMixin {
    @Inject(method = "finalizeSpawn", at = @At("RETURN"))
    private void villagerTeamJoin(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag, CallbackInfoReturnable<SpawnGroupData> cir) {
        AbstractVillager self = (AbstractVillager) (Object) this;
        if (!self.level().isClientSide() && self.getServer() != null) {
            try {
                self.getServer().getCommands().getDispatcher().execute(
                        "team add villagers",
                        self.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {

            }
        }

        if (!self.level().isClientSide() && self.getServer() != null) {
            try {
                self.getServer().getCommands().getDispatcher().execute(
                        "team modify villagers friendlyFire false",
                        self.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {

            }
        }

        if (!self.level().isClientSide() && self.getServer() != null) {
            try {
                self.getServer().getCommands().getDispatcher().execute(
                        "team join villagers @s",
                        self.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {

            }
        }
    }
}