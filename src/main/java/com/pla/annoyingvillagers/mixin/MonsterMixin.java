//package com.pla.annoyingvillagers.mixin;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.Difficulty;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.Mob;
//import net.minecraft.world.entity.MobSpawnType;
//import net.minecraft.world.entity.monster.Monster;
//import net.minecraft.world.level.ServerLevelAccessor;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import se.gory_moon.player_mobs.entity.EntityRegistry;
//
//import java.util.Random;
//
//@Mixin(Monster.class)
//public abstract class MonsterMixin {
//
//    @Inject(method = "checkMonsterSpawnRules", at = @At("HEAD"))
//    public static boolean skipLightForPlayerMob(EntityType<? extends Monster> entityType, ServerLevelAccessor world, MobSpawnType spawnType, BlockPos pos, Random random) {
//        if (entityType == (EntityType<? extends Monster>) EntityRegistry.PLAYER_MOB_ENTITY.get()) {
//            return world.getDifficulty() != Difficulty.PEACEFUL &&
//                    Mob.checkMobSpawnRules(entityType, world, spawnType, pos, random);
//        }
//    }
//}
