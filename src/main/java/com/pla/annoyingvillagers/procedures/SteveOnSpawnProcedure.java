package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class SteveOnSpawnProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            LivingEntity livingentity;
            ItemStack itemstack;

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.MENDING, 5);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.UNBREAKING, 5);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = livingentity.getItemBySlot(EquipmentSlot.LEGS);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.UNBREAKING, 5);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = livingentity.getItemBySlot(EquipmentSlot.LEGS);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = livingentity.getItemBySlot(EquipmentSlot.FEET);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.MENDING, 5);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.UNBREAKING, 5);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = livingentity.getItemBySlot(EquipmentSlot.FEET);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.UNBREAKING, 5);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = livingentity.getMainHandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.UNBREAKING, 5);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = livingentity.getMainHandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.SHARPNESS, 5);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = livingentity.getMainHandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.KNOCKBACK, 1);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = livingentity.getMainHandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.MENDING, 3);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = livingentity.getMainHandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.MOB_LOOTING, 3);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 9999999, 0, false, false));
                }
            }

            if (levelaccessor instanceof Level) {
                Level level = (Level) levelaccessor;

                if (!level.isClientSide()) {
                    level.playSound((Player) null, new BlockPos((int) d0, (int)d1, (int)d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevespawn")), SoundSource.BLOCKS, 1.0F, 1.0F);
                } else {
                    level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevespawn")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }
            }

            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<Steve> My name is Steve. I'm here to destroy Herobrine."), false);
            }

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team join ce @e[type=annoyingvillagers:steve]",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
            }

            entity.getPersistentData().putBoolean("a_player", true);
        }
    }
}
