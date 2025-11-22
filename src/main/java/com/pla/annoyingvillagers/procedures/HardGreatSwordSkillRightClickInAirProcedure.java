package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class HardGreatSwordSkillRightClickInAirProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity == null || !entity.isShiftKeyDown()) return;

        CompoundTag tag = itemstack.getOrCreateTag();
        double currentPower = tag.getDouble("power");

        if (currentPower >= 10.0D) {
            tag.putDouble("power", currentPower - 10.0D);

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                if (livingEntityPatch != null) {
                    livingEntityPatch.playAnimationSynchronized(AVAnimations.HARD_GREAT_SWORD_GUARD_SKILL, 0.0F);
                }
            }

            new DelayedTask(4) {
                @Override
                public void run() {
                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        try {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "execute at @s run particle annoyingvillagers:red_spark ^ ^1.5 ^1 0 0 0 0.6 35",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                            );
                        } catch (CommandSyntaxException e) {
                            
                        }
                    }

                    if (world instanceof Level level) {
                        SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "hard_great_sword_skill"));
                        if (!level.isClientSide()) {
                            level.playSound(null, new BlockPos((int) x, (int) y, (int) z), sound, SoundSource.NEUTRAL, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(x, y, z, sound, SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                        }
                    }
                }
            };

        } else if (entity instanceof Player player && !player.level().isClientSide()) {
            player.displayClientMessage(
                    Component.literal("Not enough energy. Current charge: " + (int) currentPower + "/10"),
                    true
            );
        }
    }
}
