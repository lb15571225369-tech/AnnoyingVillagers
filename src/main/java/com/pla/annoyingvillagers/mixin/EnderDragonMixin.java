package com.pla.annoyingvillagers.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.world.level.pathfinder.Node;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragon.class)
public abstract class EnderDragonMixin {
    @Shadow public abstract BlockPos getFightOrigin();

    @Redirect(
            method = "findClosestNode()I",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/pathfinder/Node"
            )
    )
    private Node redirectNode(int x, int y, int z) {
        EnderDragon dragon = (EnderDragon)(Object)this;
        if (!dragon.getTags().contains("av_dragon")) {
            return new Node(x, y, z);
        }

        BlockPos center = EndPodiumFeature.getLocation(getFightOrigin());
        return new Node(x + center.getX(), y, z + center.getZ());
    }

    @Redirect(
            method = "findClosestNode()I",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;getHeightmapPos(Lnet/minecraft/world/level/levelgen/Heightmap$Types;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/BlockPos;"
            )
    )
    private BlockPos redirectHeightmap(Level level, Heightmap.Types type, BlockPos originalPos) {
        EnderDragon dragon = (EnderDragon)(Object)this;
        if (!dragon.getTags().contains("av_dragon")) {
            return level.getHeightmapPos(type, originalPos);
        }

        BlockPos center = EndPodiumFeature.getLocation(getFightOrigin());
        return level.getHeightmapPos(type, center.offset(originalPos));
    }

    @Shadow private BlockPos fightOrigin;

    @Inject(method = "addAdditionalSaveData", at = @At("RETURN"))
    private void saveFightOrigin(CompoundTag tag, CallbackInfo ci) {
        EnderDragon dragon = (EnderDragon)(Object)this;
        if (dragon.getTags().contains("av_dragon")) {
            tag.putInt("av_fight_origin_x", fightOrigin.getX());
            tag.putInt("av_fight_origin_y", fightOrigin.getY());
            tag.putInt("av_fight_origin_z", fightOrigin.getZ());
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("RETURN"))
    private void loadFightOrigin(CompoundTag tag, CallbackInfo ci) {
        EnderDragon dragon = (EnderDragon)(Object)this;
        if (dragon.getTags().contains("av_dragon") &&
                tag.contains("av_fight_origin_x")) {
            int x = tag.getInt("av_fight_origin_x");
            int y = tag.getInt("av_fight_origin_y");
            int z = tag.getInt("av_fight_origin_z");
            this.fightOrigin = new BlockPos(x, y, z);
        }
    }

    @Redirect(
            method = "findClosestNode()I",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;getSeaLevel()I"
            )
    )
    private int redirectSeaLevel(Level level) {
        EnderDragon dragon = (EnderDragon)(Object)this;
        if (!dragon.getTags().contains("av_dragon")) {
            return level.getSeaLevel();
        }
        BlockPos origin = dragon.getFightOrigin();
        return level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, origin).getY();
    }
}