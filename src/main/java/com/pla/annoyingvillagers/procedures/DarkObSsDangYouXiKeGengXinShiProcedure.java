package com.pla.annoyingvillagers.procedures;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class DarkObSsDangYouXiKeGengXinShiProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2) {
        if (!levelaccessor.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(d0, d1, d2), 10.0D, 10.0D, 10.0D), (player) -> {
            return true;
        }).isEmpty()) {
            if (levelaccessor instanceof ServerLevel) {
                ServerLevel serverlevel = (ServerLevel) levelaccessor;

                serverlevel.getServer().getCommands().performCommand((new CommandSourceStack(CommandSource.NULL, new Vec3(d0, d1, d2), Vec2.ZERO, serverlevel, 4, "", new TextComponent(""), serverlevel.getServer(), (Entity) null)).withSuppressedOutput(), "1");
            }
        } else {
            levelaccessor.setBlock(new BlockPos(d0, d1, d2), Blocks.AIR.defaultBlockState(), 3);
            if (levelaccessor instanceof Level) {
                Level level = (Level) levelaccessor;

                if (!level.isClientSide()) {
                    level.playSound((Player) null, new BlockPos(d0, d1, d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:ob_place")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                } else {
                    level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:ob_place")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                }
            }
        }

    }
}
