//package com.pla.annoyingvillagers.command;
//
//import com.mojang.brigadier.arguments.StringArgumentType;
//import com.mojang.brigadier.builder.LiteralArgumentBuilder;
//import net.minecraft.commands.CommandSourceStack;
//import net.minecraft.commands.Commands;
//import net.minecraft.commands.arguments.MessageArgument;
//import net.minecraft.core.Direction;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.entity.Entity;
//import net.minecraftforge.common.util.FakePlayerFactory;
//import net.minecraftforge.event.RegisterCommandsEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
//import com.pla.annoyingvillagers.procedures.ReportUseProcedure;
//
//@EventBusSubscriber
//public class ReportCommand {
//
//    @SubscribeEvent
//    public static void registerCommand(RegisterCommandsEvent registercommandsevent) {
//        registercommandsevent.getDispatcher().register((LiteralArgumentBuilder) Commands.literal("report").then(Commands.argument("name", StringArgumentType.string()).then(Commands.argument("reason", MessageArgument.message()).executes((commandcontext) -> {
//            ServerLevel serverlevel = ((CommandSourceStack) commandcontext.getSource()).getLevel();
//            double d0 = ((CommandSourceStack) commandcontext.getSource()).getPosition().x();
//            double d1 = ((CommandSourceStack) commandcontext.getSource()).getPosition().y();
//            double d2 = ((CommandSourceStack) commandcontext.getSource()).getPosition().z();
//            Object object = ((CommandSourceStack) commandcontext.getSource()).getEntity();
//
//            if (object == null) {
//                object = FakePlayerFactory.getMinecraft(serverlevel);
//            }
//
//            Direction direction = ((Entity) object).getDirection();
//
//            ReportUseProcedure.execute(commandcontext, (Entity) object);
//            return 0;
//        }))));
//    }
//}
