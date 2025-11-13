package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class DiamondSaberOnUserProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity, ItemStack itemstack, InteractionHand interactionHand) {
        if (entity != null) {
            if (entity.isShiftKeyDown()) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;

                    player.getCooldowns().addCooldown(itemstack.getItem(), 60);
                }

                entity.setDeltaMovement(new Vec3(0.0D, 1.5D, 0.0D));
                if (levelaccessor instanceof Level level && !level.isClientSide && entity instanceof ServerPlayer serverPlayer) {
                    itemstack.hurtAndBreak(2, serverPlayer, p -> p.broadcastBreakEvent(interactionHand));
                }
                levelaccessor.addParticle(ParticleTypes.EXPLOSION, d0, d1, d2, 0.0D, 1.0D, 0.0D);
                if (levelaccessor instanceof ServerLevel) {
                    ServerLevel serverlevel = (ServerLevel) levelaccessor;

                    serverlevel.sendParticles(ParticleTypes.POOF, d0, d1, d2, 20, 0.0D, 0.0D, 0.0D, 1.0D);
                }

                Level level;

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "wing")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "wing")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "cooldown")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "cooldown")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }
            }

        }
    }
}
