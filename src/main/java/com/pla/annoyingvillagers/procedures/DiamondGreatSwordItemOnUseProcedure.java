package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class DiamondGreatSwordItemOnUseProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (entity.isShiftKeyDown() && itemstack.getOrCreateTag().getDouble("sword_skill") >= 1.0D) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute("execute at @s run particle epicfight:air_burst ^ ^1.5 ^ 0 0 0 7 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                        
                    }
                }

                entity.setDeltaMovement(new Vec3(0.0D, 1.0D, 0.0D));
                if (levelaccessor instanceof Level) {
                    Level level = (Level) levelaccessor;

                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.ender_dragon.flap")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.ender_dragon.flap")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                levelaccessor.addParticle(ParticleTypes.EXPLOSION, d0, d1, d2, 0.0D, 1.0D, 0.0D);
                if (entity instanceof Player) {
                    Player player = (Player) entity;

                    player.getCooldowns().addCooldown(itemstack.getItem(), 40);
                }

                itemstack.getOrCreateTag().putDouble("sword_skill", itemstack.getOrCreateTag().getDouble("sword_skill") - 1.0D);
            }

        }
    }
}
