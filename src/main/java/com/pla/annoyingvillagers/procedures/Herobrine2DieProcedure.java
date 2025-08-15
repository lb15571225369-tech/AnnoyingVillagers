package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class Herobrine2DieProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceEntity, Entity targetEntity) {
        if (sourceEntity == null || targetEntity == null) return;

        if (!world.isClientSide() && world.getServer() != null) {
            world.getServer().getPlayerList().broadcastSystemMessage(Component.literal("The clone has been destroyed, data has been transmitted to the terminal."), false);
        }
        
        new DelayedTask(20) {
            @Override
            public void run() {
                dropLoot(world, x, y, z);
            }
        };

        if (Math.random() <= 0.5 && targetEntity instanceof LivingEntity living) {
            living.removeAllEffects();
            living.setHealth(4.0F);
            if (!living.level().isClientSide()) {
                living.addEffect(new MobEffectInstance(MobEffects.WITHER, 9999, 3, false, false));
            }
        }
    }

    private static void dropLoot(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof Level level) || level.isClientSide()) return;

        Item[] drops = new Item[]{
                Items.DIAMOND, Items.DIAMOND,
                Items.MUSIC_DISC_11, Items.IRON_INGOT,
                Items.WRITABLE_BOOK, Items.EMERALD, Items.EMERALD,
                Items.ENCHANTED_GOLDEN_APPLE, Items.NETHERITE_INGOT,
                Items.ENDER_PEARL, Items.ENCHANTED_GOLDEN_APPLE,
                Items.ENDER_EYE, Items.TNT, Items.TNT
        };

        for (Item item : drops) {
            ItemEntity entity = new ItemEntity(level, x, y, z, new ItemStack(item));
            entity.setPickUpDelay(10);
            level.addFreshEntity(entity);
        }
    }
}
