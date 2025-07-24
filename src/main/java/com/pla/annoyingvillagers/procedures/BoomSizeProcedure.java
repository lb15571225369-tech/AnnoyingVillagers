package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.apache.logging.log4j.Logger;
import com.pla.annoyingvillagers.AnnoyingVillagers;

public class BoomSizeProcedure {

    public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> context) {
        if (!(world instanceof Level level)) return;

        BlockPos pos;
        double size;

        try {
            pos = BlockPosArgument.getLoadedBlockPos(context, "xyz");
            size = DoubleArgumentType.getDouble(context, "size");
        } catch (CommandSyntaxException e) {
            AnnoyingVillagers.LOGGER.error("Failed to parse explosion command coordinates or size", e);
            return;
        }

        if (!level.isClientSide()) {
            // Perform explosion twice as in original (if intentional)
            level.explode(null, pos.getX(), pos.getY(), pos.getZ(), (float) size, Level.ExplosionInteraction.BLOCK);
            level.explode(null, pos.getX(), pos.getY(), pos.getZ(), (float) size, Level.ExplosionInteraction.BLOCK);
        }
    }
}
