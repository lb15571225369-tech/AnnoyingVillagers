package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import net.minecraft.util.RandomSource;
public class EscapeMobEffectOnAddAttributeModifiersProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                if (livingentity.hasEffect((MobEffect) AnnoyingVillagersModMobEffects.BLEED.get())) {
                    LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

                    if (livingentitypatch != null) {
                        DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();

                        if (!(dynamicanimation instanceof LongHitAnimation) || !(dynamicanimation instanceof ActionAnimation)) {
                            Level level;

                            if (levelaccessor instanceof Level) {
                                level = (Level) levelaccessor;
                                if (!level.isClientSide()) {
                                    level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "whoosh")), SoundSource.NEUTRAL, (float) Mth.nextDouble(RandomSource.create(), 0.5D, 0.9D), 1.0F);
                                } else {
                                    level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "whoosh")), SoundSource.NEUTRAL, (float) Mth.nextDouble(RandomSource.create(), 0.5D, 0.9D), 1.0F, false);
                                }
                            }

                            if (levelaccessor instanceof Level) {
                                level = (Level) levelaccessor;
                                if (!level.isClientSide()) {
                                    level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "get_out")), SoundSource.NEUTRAL, (float) Mth.nextDouble(RandomSource.create(), 0.5D, 0.9D), 1.0F);
                                } else {
                                    level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "get_out")), SoundSource.NEUTRAL, (float) Mth.nextDouble(RandomSource.create(), 0.5D, 0.9D), 1.0F, false);
                                }
                            }

                            if (levelaccessor instanceof Level) {
                                level = (Level) levelaccessor;
                                if (!level.isClientSide()) {
                                    level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "cooldown")), SoundSource.NEUTRAL, (float) Mth.nextDouble(RandomSource.create(), 0.5D, 0.9D), 1.0F);
                                } else {
                                    level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "cooldown")), SoundSource.NEUTRAL, (float) Mth.nextDouble(RandomSource.create(), 0.5D, 0.9D), 1.0F, false);
                                }
                            }

                            if (levelaccessor instanceof Level) {
                                level = (Level) levelaccessor;
                                if (!level.isClientSide()) {
                                    level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "wing")), SoundSource.NEUTRAL, (float) Mth.nextDouble(RandomSource.create(), 0.5D, 0.9D), 1.0F);
                                } else {
                                    level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "wing")), SoundSource.NEUTRAL, (float) Mth.nextDouble(RandomSource.create(), 0.5D, 0.9D), 1.0F, false);
                                }
                            }

                            entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * (double) Math.max(-1, -2), 0.1D, entity.getLookAngle().z * (double) Math.max(-1, -2)));
                            if (levelaccessor instanceof ServerLevel) {
                                ServerLevel serverlevel = (ServerLevel) levelaccessor;

                                serverlevel.sendParticles(ParticleTypes.POOF, d0, d1, d2, 30, 2.0D, 2.0D, 2.0D, 0.1D);
                            }

                            if (entity instanceof LivingEntity) {
                                LivingEntity livingentity1 = (LivingEntity) entity;

                                livingentity1.removeEffect((MobEffect) AnnoyingVillagersModMobEffects.BLEED.get());
                            }

                            if (entity instanceof Player) {
                                Player player = (Player) entity;

                                player.causeFoodExhaustion(10.0F);
                            }

                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                try {
                                    entity.getServer().getCommands().getDispatcher().execute(
                                            "indestructible @s play \"epicfight:biped/skill/roll_backward\" 0 1",
                                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
