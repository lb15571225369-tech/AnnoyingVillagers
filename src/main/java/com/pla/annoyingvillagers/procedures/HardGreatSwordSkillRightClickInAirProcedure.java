package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
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

public class HardGreatSwordSkillRightClickInAirProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity == null || !entity.isShiftKeyDown()) return;

        CompoundTag tag = itemstack.getOrCreateTag();
        double currentPower = tag.getDouble("power");

        if (currentPower >= 10.0D) {
            // Consume energy
            tag.putDouble("power", currentPower - 10.0D);

            // Play animation
            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(
                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                        "indestructible @s play \"annoyingvillagers:biped/combat/hard_great_sword_skill\" 0 1"
                );
            }

            // Apply potion effect
            if (entity instanceof LivingEntity living && !living.level.isClientSide()) {
                living.addEffect(new MobEffectInstance(AnnoyingVillagersModMobEffects.EC_PLAYER.get(), 160, 0, false, false));
            }

            // Delayed effects (particles + sound)
            new DelayedTask(4) {
                @Override
                public void run() {
                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().performCommand(
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                "execute at @s run particle annoyingvillagers:red_spark ^ ^1.5 ^1 0 0 0 0.6 35"
                        );
                    }

                    if (world instanceof Level level) {
                        SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":hard_great_sword_skill"));
                        if (!level.isClientSide()) {
                            level.playSound(null, new BlockPos(x, y, z), sound, SoundSource.NEUTRAL, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(x, y, z, sound, SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                        }
                    }
                }
            };

        } else if (entity instanceof Player player && !player.level.isClientSide()) {
            player.displayClientMessage(
                    new TextComponent("能量不足，目前充能 " + currentPower + "/10"),
                    true
            );
        }
    }
}
