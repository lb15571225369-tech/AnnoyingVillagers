package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class Herobrine7OnEntityInitialSpawnProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity) {
        if (entity != null) {
            ServerLevel serverlevel;

            if (levelaccessor instanceof ServerLevel) {
                serverlevel = (ServerLevel)levelaccessor;
                LightningBolt lightningbolt = (LightningBolt)EntityType.LIGHTNING_BOLT.create(serverlevel);

                lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos((int) d0, (int) d1, (int) d2)));
                lightningbolt.setVisualOnly(true);
                serverlevel.addFreshEntity(lightningbolt);
            }

            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("Herobrine has arrived."), false);
            }

            Player player;
            LivingEntity livingentity;

            if (entity instanceof Player) {
                player = (Player)entity;
                player.getInventory().armor.set(0, new ItemStack(Blocks.AIR));
                player.getInventory().setChanged();
            } else if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                livingentity.setItemSlot(EquipmentSlot.FEET, new ItemStack(Blocks.AIR));
            }

            if (entity instanceof Player) {
                player = (Player)entity;
                player.getInventory().armor.set(1, new ItemStack(Blocks.AIR));
                player.getInventory().setChanged();
            } else if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                livingentity.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Blocks.AIR));
            }
            new DelayedTask(1) {
                public void run() {
                    Entity entity1 = entity;

                    if (entity1 instanceof Player) {
                        Player player1 = (Player)entity1;

                        player1.getInventory().armor.set(3, new ItemStack(Blocks.AIR));
                        player1.getInventory().setChanged();
                    } else if (entity1 instanceof LivingEntity) {
                        LivingEntity livingentity1 = (LivingEntity)entity1;

                        livingentity1.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Blocks.AIR));
                    }

                    if (entity instanceof LivingEntity) {
                        LivingEntity livingentity2 = (LivingEntity)entity;
                        ItemStack itemstack = new ItemStack(Items.ENDER_PEARL);

                        itemstack.setCount(1);
                        livingentity2.setItemInHand(InteractionHand.OFF_HAND, itemstack);
                        if (livingentity2 instanceof Player) {
                            Player player2 = (Player)livingentity2;

                            player2.getInventory().setChanged();
                        }
                    }
                }
            };

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team add herobrine",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {

                }
            }

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team join herobrine @s",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {

                }
            }
            entity.getPersistentData().putBoolean("a_player", true);
        }
    }
}
