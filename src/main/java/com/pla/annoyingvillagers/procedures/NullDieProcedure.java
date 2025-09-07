package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class NullDieProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceEntity) {
        if (sourceEntity == null) return;
        
        new DelayedTask(20) {
            @Override
            public void run() {
                dropLoot(world, x, y, z);
            }
        };
    }

    private static void dropLoot(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof Level level) || level.isClientSide()) return;

        Item[] drops = new Item[]{
                Items.DIAMOND, Items.DIAMOND,
                Items.ENDER_PEARL, Items.COMPASS,
                Items.ENDER_PEARL, Items.ENDER_PEARL, Items.EMERALD,
                Items.ENCHANTED_GOLDEN_APPLE, Items.NETHERITE_INGOT,
                Items.ENDER_PEARL, Items.ENCHANTED_GOLDEN_APPLE,
                Items.ENDER_EYE, Items.MUSIC_DISC_11
        };

        for (Item item : drops) {
            ItemEntity entity = new ItemEntity(level, x, y, z, new ItemStack(item));
            entity.setPickUpDelay(10);
            level.addFreshEntity(entity);
        }
    }
}
