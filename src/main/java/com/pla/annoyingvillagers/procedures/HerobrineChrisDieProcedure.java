package com.pla.annoyingvillagers.procedures;
import com.pla.annoyingvillagers.entity.InfectedChrisEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.LevelAccessor;

public class HerobrineChrisDieProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity, Entity entity1) {
        if (entity != null && entity1 != null) {
            if (levelaccessor instanceof ServerLevel) {
                ServerLevel serverlevel = (ServerLevel) levelaccessor;
                InfectedChrisEntity infectedChrisEntity = new InfectedChrisEntity((EntityType) AnnoyingVillagersModEntities.INJECTED_CHRIS.get(), serverlevel);

                infectedChrisEntity.moveTo(d0, d1, d2, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                if (infectedChrisEntity instanceof Mob) {
                    Mob mob = (Mob) infectedChrisEntity;

                    mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(infectedChrisEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
                }

                levelaccessor.addFreshEntity(infectedChrisEntity);
            }

            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.translatable("subtitles.herobrine_clone_die"), false);
            }

            levelaccessor.addParticle(ParticleTypes.EXPLOSION, d0, d1, d2, 0.0D, 1.0D, 0.0D);
            if (!entity.level().isClientSide()) {
                entity.discard();
            }

            if (entity1 instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity1;

                livingentity.removeEffect((MobEffect) AnnoyingVillagersModMobEffects.HEROBRINE.get());
            }

        }
    }
}
