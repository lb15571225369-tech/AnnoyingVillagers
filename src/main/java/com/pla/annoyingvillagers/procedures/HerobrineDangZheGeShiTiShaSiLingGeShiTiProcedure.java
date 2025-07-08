package com.pla.annoyingvillagers.procedures;

import java.util.Random;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import com.pla.annoyingvillagers.entity.HerobrineEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;

public class HerobrineDangZheGeShiTiShaSiLingGeShiTiProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, Entity entity) {
        if (entity != null) {
            if (ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("player_mobs:player_mob")) {
                ((<undefinedtype>)(new Object() {
                    private int ticks = 0;
                    private float waitTicks;
                    private LevelAccessor world;

                    public void start(LevelAccessor levelaccessor1, int i) {
                        this.waitTicks = (float)i;
                        MinecraftForge.EVENT_BUS.register(this);
                        this.world = levelaccessor1;
                    }

                    @SubscribeEvent
                    public void tick(ServerTickEvent servertickevent) {
                        if (servertickevent.phase == Phase.END) {
                            ++this.ticks;
                            if ((float)this.ticks >= this.waitTicks) {
                                this.run();
                            }
                        }

                    }

                    private void run() {
                        LevelAccessor levelaccessor1 = this.world;

                        if (levelaccessor1 instanceof ServerLevel) {
                            ServerLevel serverlevel = (ServerLevel)levelaccessor1;
                            HerobrineEntity herobrineentity = new HerobrineEntity((EntityType)AnnoyingVillagersModEntities.HEROBRINE.get(), serverlevel);

                            herobrineentity.moveTo(d0 + Mth.nextDouble(new Random(), -100.0D, 100.0D), d1 + Mth.nextDouble(new Random(), 5.0D, 30.0D), d2 + Mth.nextDouble(new Random(), -100.0D, 100.0D), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                            if (herobrineentity instanceof Mob) {
                                Mob mob = (Mob)herobrineentity;

                                mob.finalizeSpawn(serverlevel, this.world.getCurrentDifficultyAt(herobrineentity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                            }

                            this.world.addFreshEntity(herobrineentity);
                        }

                        MinecraftForge.EVENT_BUS.unregister(this);
                    }
                })).start(levelaccessor, 50);
            }

        }
    }
}
