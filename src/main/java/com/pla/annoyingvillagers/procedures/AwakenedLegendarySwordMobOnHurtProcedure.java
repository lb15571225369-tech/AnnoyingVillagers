package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class AwakenedLegendarySwordMobOnHurtProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            levelaccessor.addParticle(ParticleTypes.EXPLOSION_EMITTER, d0, d1, d2, 1.0D, 1.0D, 1.0D);
            if (levelaccessor instanceof Level) {
                Level level = (Level) levelaccessor;

                if (!level.isClientSide()) {
                    level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.deepslate_bricks.hit")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                } else {
                    level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.deepslate_bricks.hit")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                }
            }

            levelaccessor.addParticle(ParticleTypes.EXPLOSION, d0, d1, d2, 1.0D, 1.0D, 1.0D);
            levelaccessor.levelEvent(2001, new BlockPos((int) d0, (int) d1, (int) d2), Block.getId(Blocks.GOLD_BLOCK.defaultBlockState()));
            levelaccessor.levelEvent(2001, new BlockPos((int) d0, (int) d1, (int) d2), Block.getId(Blocks.AMETHYST_CLUSTER.defaultBlockState()));
            levelaccessor.levelEvent(2001, new BlockPos((int) d0, (int) d1, (int) d2), Block.getId(Blocks.REDSTONE_WIRE.defaultBlockState()));
            if (Math.random() <= 0.35D && !entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "kill @s",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
            }

        }
    }
}
