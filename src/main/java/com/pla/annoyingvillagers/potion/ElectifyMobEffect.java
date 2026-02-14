package com.pla.annoyingvillagers.potion;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class ElectifyMobEffect extends MobEffect {

    public ElectifyMobEffect() {
        super(MobEffectCategory.HARMFUL, -16711681);
    }

    public @NotNull String getDescriptionId() {
        return "effect.annoyingvillagers.electify";
    }

    public void applyEffectTick(LivingEntity livingentity, int i) {
        double d0 = livingentity.getX();
        double d1 = livingentity.getY();
        double d2 = livingentity.getZ();
        if (Math.random() <= 0.1D) {
            if (livingentity.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        AnnoyingVillagersModParticleTypes.ELECTRIC_SPARK.get(),
                        livingentity.getX(), livingentity.getY(), livingentity.getZ(),
                        1,
                        0.3D, 1.2D, 0.3D,
                        0.0D
                );

                if (serverLevel.random.nextDouble() <= 0.8D) {
                    float volume = (float) Mth.nextDouble(serverLevel.random, 0.05D, 0.5D);
                    float pitch  = (float) Mth.nextDouble(serverLevel.random, 0.8D, 1.1D);

                    serverLevel.playSound(
                            null,
                            BlockPos.containing(d0, d1, d2),
                            AnnoyingVillagersModSounds.ELECTIFY.get(),
                            SoundSource.NEUTRAL,
                            volume,
                            pitch
                    );
                }
            }
        }

        if (Math.random() <= 0.001D) {
            livingentity.hurt(livingentity.level().damageSources().generic(), 1.0F);
        }
    }

    public boolean isDurationEffectTick(int i, int j) {
        return true;
    }
}
