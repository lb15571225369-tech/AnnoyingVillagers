package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.apache.logging.log4j.Logger;
import com.pla.annoyingvillagers.AnnoyingVillagersMod;

public class BoomSizeProcedure {

    public static void execute(LevelAccessor levelaccessor, final CommandContext<CommandSourceStack> commandcontext) {
        Level level;

        if (levelaccessor instanceof Level) {
            level = (Level)levelaccessor;
            if (!level.isClientSide()) {
                level.explode((Entity)null, ((<undefinedtype>)(new Object() {
                    public double getX() {
                        try {
                            return (double)BlockPosArgument.getLoadedBlockPos(commandcontext, "xyz").getX();
                        } catch (CommandSyntaxException commandsyntaxexception) {
                            commandsyntaxexception.printStackTrace();
                            return 0.0D;
                        }
                    }
                })).getX(), ((<undefinedtype>)(new Object() {
                    public double getY() {
                        try {
                            return (double)BlockPosArgument.getLoadedBlockPos(commandcontext, "xyz").getY();
                        } catch (CommandSyntaxException commandsyntaxexception) {
                            commandsyntaxexception.printStackTrace();
                            return 0.0D;
                        }
                    }
                })).getY(), ((<undefinedtype>)(new Object() {
                    public double getZ() {
                        try {
                            return (double)BlockPosArgument.getLoadedBlockPos(commandcontext, "xyz").getZ();
                        } catch (CommandSyntaxException commandsyntaxexception) {
                            commandsyntaxexception.printStackTrace();
                            return 0.0D;
                        }
                    }
                })).getZ(), (float)DoubleArgumentType.getDouble(commandcontext, "size"), BlockInteraction.DESTROY);
            }
        }

        if (levelaccessor instanceof Level) {
            level = (Level)levelaccessor;
            if (!level.isClientSide()) {
                level.explode((Entity)null, ((<undefinedtype>)(new Object() {
                    public double getX() {
                        try {
                            return (double)BlockPosArgument.getLoadedBlockPos(commandcontext, "xyz").getX();
                        } catch (CommandSyntaxException commandsyntaxexception) {
                            commandsyntaxexception.printStackTrace();
                            return 0.0D;
                        }
                    }
                })).getX(), ((<undefinedtype>)(new Object() {
                    public double getY() {
                        try {
                            return (double)BlockPosArgument.getLoadedBlockPos(commandcontext, "xyz").getY();
                        } catch (CommandSyntaxException commandsyntaxexception) {
                            commandsyntaxexception.printStackTrace();
                            return 0.0D;
                        }
                    }
                })).getY(), ((<undefinedtype>)(new Object() {
                    public double getZ() {
                        try {
                            return (double)BlockPosArgument.getLoadedBlockPos(commandcontext, "xyz").getZ();
                        } catch (CommandSyntaxException commandsyntaxexception) {
                            commandsyntaxexception.printStackTrace();
                            return 0.0D;
                        }
                    }
                })).getZ(), (float)DoubleArgumentType.getDouble(commandcontext, "size"), BlockInteraction.DESTROY);
            }
        }

        Logger logger = AnnoyingVillagersMod.LOGGER;
        double d0 = ((<undefinedtype>)(new Object() {
            public double getX() {
                try {
                    return (double)BlockPosArgument.getLoadedBlockPos(commandcontext, "xyz").getX();
                } catch (CommandSyntaxException commandsyntaxexception) {
                    commandsyntaxexception.printStackTrace();
                    return 0.0D;
                }
            }
        })).getX();

        logger.info("\u5df2\u5728" + d0 + " " + ((<undefinedtype>)(new Object() {
            public double getY() {
                try {
                    return (double)BlockPosArgument.getLoadedBlockPos(commandcontext, "xyz").getY();
                } catch (CommandSyntaxException commandsyntaxexception) {
                    commandsyntaxexception.printStackTrace();
                    return 0.0D;
                }
            }
        })).getY() + " " + ((<undefinedtype>)(new Object() {
            public double getZ() {
                try {
                    return (double)BlockPosArgument.getLoadedBlockPos(commandcontext, "xyz").getZ();
                } catch (CommandSyntaxException commandsyntaxexception) {
                    commandsyntaxexception.printStackTrace();
                    return 0.0D;
                }
            }
        })).getZ() + " \u4ea7\u751f\u5927\u5c0f\u4e3a" + DoubleArgumentType.getDouble(commandcontext, "size") + "\u7684boom");
    }
}
