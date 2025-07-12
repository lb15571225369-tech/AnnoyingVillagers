package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import com.pla.annoyingvillagers.entity.BlueDemonEndEntity;

public class BdTridentDangShiTiGengXinKeShiProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity) {
        if (entity != null) {
            if (!levelaccessor.getEntitiesOfClass(BlueDemonEndEntity.class, AABB.ofSize(new Vec3(d0, d1, d2), 90.0D, 90.0D, 90.0D), (bluedemonendentity) -> {
                return true;
            }).isEmpty()) {
                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "kill @s");
                }

                if (!entity.level.isClientSide()) {
                    entity.discard();
                }
            }

            LivingEntity livingentity;
            ItemStack itemstack;
            Player player;

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                itemstack = new ItemStack(Blocks.AIR);
                itemstack.setCount(1);
                livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                if (livingentity instanceof Player) {
                    player = (Player)livingentity;
                    player.getInventory().setChanged();
                }
            }

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                itemstack = new ItemStack(Blocks.AIR);
                itemstack.setCount(1);
                livingentity.setItemInHand(InteractionHand.OFF_HAND, itemstack);
                if (livingentity instanceof Player) {
                    player = (Player)livingentity;
                    player.getInventory().setChanged();
                }
            }
            new DelayedTask(120) {
                @Override
                public void run() {
                    if (entity.isAlive() && !entity.level.isClientSide()) {
                        entity.discard();
                    }
                }
            };
        }
    }
}
