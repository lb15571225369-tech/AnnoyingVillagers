package com.pla.annoyingvillagers.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import com.pla.annoyingvillagers.procedures.FFireHelpUseProcedure;

@EventBusSubscriber
public class FriendlyFireHelpCommand {

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent registercommandsevent) {
        registercommandsevent.getDispatcher().register((LiteralArgumentBuilder) Commands.literal("f-fire-help").executes((commandcontext) -> {
            ServerLevel serverlevel = ((CommandSourceStack) commandcontext.getSource()).getLevel();
            double d0 = ((CommandSourceStack) commandcontext.getSource()).getPosition().x();
            double d1 = ((CommandSourceStack) commandcontext.getSource()).getPosition().y();
            double d2 = ((CommandSourceStack) commandcontext.getSource()).getPosition().z();
            Object object = ((CommandSourceStack) commandcontext.getSource()).getEntity();

            if (object == null) {
                object = FakePlayerFactory.getMinecraft(serverlevel);
            }

            Direction direction = ((Entity) object).getDirection();

            FFireHelpUseProcedure.execute((Entity) object);
            return 0;
        }));
    }
}
