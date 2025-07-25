package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class AddStarterSkillProcedure {
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent playerloggedinevent) {
        execute(playerloggedinevent, playerloggedinevent.getEntity().level, playerloggedinevent.getEntity().getX(), playerloggedinevent.getEntity().getY(), playerloggedinevent.getEntity().getZ(), playerloggedinevent.getEntity());
    }

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        execute((Event) null, levelaccessor, d0, d1, d2, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity) {
        if (entity != null) {
            if (!entity.getPersistentData().getBoolean("init_epic_fight_skill")) {
                entity.getPersistentData().putBoolean("init_epic_fight_skill", true);

                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    CommandSourceStack source = entity.createCommandSourceStack()
                            .withSuppressedOutput()
                            .withPermission(4);
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "epicfight skill add @s passive1 annoyingvillagers:clash",
                                source
                        );
                        entity.getServer().getCommands().getDispatcher().execute(
                                "epicfight skill add @s guard epicfight:guard",
                                source
                        );
                        entity.getServer().getCommands().getDispatcher().execute(
                                "epicfight skill add @s dodge epicfight:roll",
                                source
                        );
                    } catch (CommandSyntaxException e) {
                    }
                }
            }

            if (entity.getPersistentData().getBoolean("b_d_aim")) {
                Level level;

                if (levelaccessor instanceof Level) {
                    level = (Level)levelaccessor;
                    if (!level.isClientSide()) {
                        level.explode((Entity)null, d0, d1, d2, 20.0F, Explosion.BlockInteraction.NONE);
                    }
                }

                if (levelaccessor instanceof Level) {
                    level = (Level)levelaccessor;
                    if (!level.isClientSide()) {
                        level.explode((Entity)null, d0, d1, d2, 20.0F, Explosion.BlockInteraction.DESTROY);
                    }
                }

                entity.getPersistentData().putBoolean("b_d_aim", false);
            }

            entity.getPersistentData().putBoolean("kick_x", false);
            entity.getPersistentData().putDouble("air_kick", 0.0D);
            entity.getPersistentData().putDouble("kick", 0.0D);
            entity.getPersistentData().putDouble("axe_a", 0.0D);
            entity.getPersistentData().putDouble("sword_a", 0.0D);
            entity.getPersistentData().putDouble("fist_a", 0.0D);
            entity.getPersistentData().putDouble("dash_auto", 0.0D);
        }
    }
}
