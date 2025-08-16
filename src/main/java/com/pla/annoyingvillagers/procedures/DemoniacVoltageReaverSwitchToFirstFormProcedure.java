package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.SnakeBladeHit;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class DemoniacVoltageReaverSwitchToFirstFormProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity, ItemStack itemStack) {
        if (entity != null) {
            if (entity.isShiftKeyDown()) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity) entity;
                    ItemStack itemstack = new ItemStack((ItemLike) AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get());

                    itemstack.setCount(1);
                    livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                    if (livingentity instanceof Player) {
                        Player player = (Player) livingentity;

                        player.getInventory().setChanged();
                    }
                }

                Level level;

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:ender_shot")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:ender_shot")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:wing")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:wing")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:cooldown")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:cooldown")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:end_sr")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:end_sr")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (entity instanceof Player) {
                    Player player1 = (Player) entity;
                    ItemCooldowns itemcooldowns = player1.getCooldowns();
                    ItemStack itemstack1;

                    if (entity instanceof LivingEntity) {
                        LivingEntity livingentity1 = (LivingEntity) entity;

                        itemstack1 = livingentity1.getMainHandItem();
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    itemcooldowns.addCooldown(itemstack1.getItem(), 100);
                }
            } else {
                if (entity instanceof LivingEntity livingEntity) {
                    if (SnakeBladeHit.process(itemStack, livingEntity)) {
                        itemStack.getOrCreateTag().putBoolean("SnakeAnimation", true);
                    };
                }
            }
        }
    }
}
