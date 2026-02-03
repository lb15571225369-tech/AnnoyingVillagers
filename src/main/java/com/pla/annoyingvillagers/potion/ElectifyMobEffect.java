package com.pla.annoyingvillagers.potion;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class ElectifyMobEffect extends MobEffect {

    public ElectifyMobEffect() {
        super(MobEffectCategory.HARMFUL, -16711681);
    }

    public String getDescriptionId() {
        return "effect.annoyingvillagers.electify";
    }

    public void applyEffectTick(LivingEntity livingentity, int i) {
        double d0 = livingentity.getX();
        double d1 = livingentity.getY();
        double d2 = livingentity.getZ();
        if (Math.random() <= 0.1D) {
            if (!livingentity.level().isClientSide() && livingentity.getServer() != null) {
                try {
                    livingentity.getServer().getCommands().getDispatcher().execute("execute at @s run particle annoyingvillagers:electric_spark ^ ^ ^ 0.3 1.2 0.3 0 1", livingentity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {

                }
            }

            if (Math.random() <= 0.8D) {
                Level level = (Level) livingentity.level();

                if (!level.isClientSide()) {
                    level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "electify")), SoundSource.NEUTRAL, (float) Mth.nextDouble(RandomSource.create(), 0.05D, 0.5D), (float) Mth.nextDouble(RandomSource.create(), 0.8D, 1.1D));
                } else {
                    level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "electify")), SoundSource.NEUTRAL, (float) Mth.nextDouble(RandomSource.create(), 0.05D, 0.5D), (float) Mth.nextDouble(RandomSource.create(), 0.8D, 1.1D), false);
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
