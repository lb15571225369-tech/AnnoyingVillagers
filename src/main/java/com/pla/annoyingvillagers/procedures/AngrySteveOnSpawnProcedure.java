package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class AngrySteveOnSpawnProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            entity.getPersistentData().putBoolean("av_npc", true);
            new DelayedTask(5000) {
                @Override
                public void run() {
                    Entity entity1 = entity;

                    if (!entity1.level().isClientSide() && entity1.getServer() != null) {
                        try {
                            entity1.getServer().getCommands().getDispatcher().execute(
                                    "tellraw @a {\"text\":\"Steve has left the game\",\"color\":\"yellow\"}",
                                    entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                        }
                    }

                    if (!entity.level().isClientSide()) {
                        entity.discard();
                    }
                }
            };
            new DelayedTask(10) {
                @Override
                public void run() {
                    LevelAccessor levelaccessor1 = levelaccessor;

                    if (levelaccessor1 instanceof Level) {
                        Level level = (Level)levelaccessor1;

                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "steve2")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "steve2")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                        }
                    }

                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<Steve> I will kill you"), false);
                    }
                }
            };
            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team join ce",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
            }

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "data merge entity @s {Tags:[\"OP\"]}",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
            }

            entity.getPersistentData().putBoolean("a_player", true);
        }
    }
}
