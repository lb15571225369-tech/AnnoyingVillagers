package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.entity.HerobrineEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

public class HerobrineOnAwardKillScoreProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) return;

        if (ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("player_mobs:player_mob")) {
            new DelayedTask(50) {
                @Override
                public void run() {
                    if (!(world instanceof ServerLevel serverLevel)) return;

                    HerobrineEntity herobrine = new HerobrineEntity(AnnoyingVillagersModEntities.HEROBRINE.get(), serverLevel);
                    double spawnX = x + Mth.nextDouble(new Random(), -100.0D, 100.0D);
                    double spawnY = y + Mth.nextDouble(new Random(), 5.0D, 30.0D);
                    double spawnZ = z + Mth.nextDouble(new Random(), -100.0D, 100.0D);

                    herobrine.moveTo(spawnX, spawnY, spawnZ, world.getRandom().nextFloat() * 360.0F, 0.0F);

                    if (herobrine instanceof Mob) {
                        Mob mob = (Mob) herobrine;
                        mob.finalizeSpawn(serverLevel, world.getCurrentDifficultyAt(herobrine.blockPosition()),
                                MobSpawnType.MOB_SUMMONED, null, null);
                    }

                    serverLevel.addFreshEntity(herobrine);
                }
            };
        }
    }
}
