package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.entity.VillagerScoutCaptainEntity;
import com.pla.annoyingvillagers.entity.VillagerScoutEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;

public class WantedMobEffectOnStartProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, Entity entity) {
        if (entity != null) {
            if (entity instanceof Player) {
                Player player = (Player)entity;

                if (!player.level.isClientSide()) {
                    player.displayClientMessage(new TextComponent("\u4f60\u88ab\u901a\u7f09\u4e86\uff01"), false);
                }
            }

            if (Math.random() <= 0.08D) {
                new DelayedTask(30) {
                    public void run() {
                        LevelAccessor levelaccessor1 = levelaccessor;
                        ServerLevel serverlevel;
                        VillagerScoutCaptainEntity cczdzentity;
                        Mob mob;

                        if (levelaccessor1 instanceof ServerLevel) {
                            serverlevel = (ServerLevel)levelaccessor1;
                            cczdzentity = new VillagerScoutCaptainEntity((EntityType) AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN.get(), serverlevel);
                            cczdzentity.moveTo(d0, d1, d2 + 10.0D, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                            if (cczdzentity instanceof Mob) {
                                mob = (Mob)cczdzentity;
                                mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(cczdzentity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                            }

                            levelaccessor.addFreshEntity(cczdzentity);
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof ServerLevel) {
                            serverlevel = (ServerLevel)levelaccessor1;
                            cczdzentity = new VillagerScoutCaptainEntity((EntityType)AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN.get(), serverlevel);
                            cczdzentity.moveTo(d0, d1, d2 + 8.0D, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                            if (cczdzentity instanceof Mob) {
                                mob = (Mob)cczdzentity;
                                mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(cczdzentity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                            }

                            levelaccessor.addFreshEntity(cczdzentity);
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof ServerLevel) {
                            serverlevel = (ServerLevel)levelaccessor1;
                            VillagerScoutEntity cunminzhenchabingentity = new VillagerScoutEntity((EntityType)AnnoyingVillagersModEntities.VILLAGER_SCOUT.get(), serverlevel);

                            cunminzhenchabingentity.moveTo(d0, d1, d2 + 5.0D, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                            if (cunminzhenchabingentity instanceof Mob) {
                                mob = (Mob)cunminzhenchabingentity;
                                mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(cunminzhenchabingentity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                            }

                            levelaccessor.addFreshEntity(cunminzhenchabingentity);
                        }

                        if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                            levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<Villager Scout> Found them! There's a wanted criminal here!"), ChatType.SYSTEM, Util.NIL_UUID);
                        }
                    }
                };
            }

        }
    }
}
