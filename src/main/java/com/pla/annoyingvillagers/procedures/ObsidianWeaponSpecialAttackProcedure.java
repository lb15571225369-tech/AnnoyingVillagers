package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class ObsidianWeaponSpecialAttackProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity, ItemStack itemstack, InteractionHand hand) {
        if (!(levelaccessor instanceof Level level) || entity == null) return;

        new DelayedTask(15) {
            @Override
            public void run() {
                if (!level.isClientSide && entity instanceof ServerPlayer serverPlayer) {
                    itemstack.hurtAndBreak(100, serverPlayer, p -> p.broadcastBreakEvent(hand));
                }

                if (!level.isClientSide()) {
                    level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "obsidian_place")), SoundSource.BLOCKS, 0.3F, 1.0F);
                } else {
                    level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "obsidian_place")), SoundSource.BLOCKS, 0.3F, 1.0F, false);
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run fill ^-2 ^-1 ^3 ^2 ^2 ^3 annoyingvillagers:obsidian[from_player=true] keep",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run data modify block ^-2 ^-1 ^3 ^2 ^2 ^3 Owner set from entity @s UUID",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                        );
                    } catch (CommandSyntaxException e) {

                    }
                }
            }
        };
    }
}
