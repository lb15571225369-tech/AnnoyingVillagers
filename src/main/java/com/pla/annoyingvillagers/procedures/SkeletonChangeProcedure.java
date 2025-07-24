package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class SkeletonChangeProcedure {

    @SubscribeEvent
    public static void onEntityStruckByLightning(EntityStruckByLightningEvent entitystruckbylightningevent) {
        execute(entitystruckbylightningevent, entitystruckbylightningevent.getEntity().level(), entitystruckbylightningevent.getEntity().getX(), entitystruckbylightningevent.getEntity().getY(), entitystruckbylightningevent.getEntity().getZ(), entitystruckbylightningevent.getEntity());
    }

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        execute((Event) null, levelaccessor, d0, d1, d2, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            if (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString().equals("minecraft:skeleton")) {
                if (!entity.level().isClientSide()) {
                    entity.discard();
                }

                if (levelaccessor instanceof ServerLevel) {
                    ServerLevel serverlevel = (ServerLevel) levelaccessor;
                    WitherSkeleton witherskeleton = new WitherSkeleton(EntityType.WITHER_SKELETON, serverlevel);

                    witherskeleton.moveTo(d0, d1, d2, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                    if (witherskeleton instanceof Mob) {
                        Mob mob = (Mob) witherskeleton;

                        mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(witherskeleton.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
                    }

                    levelaccessor.addFreshEntity(witherskeleton);
                }
            }

        }
    }
}
