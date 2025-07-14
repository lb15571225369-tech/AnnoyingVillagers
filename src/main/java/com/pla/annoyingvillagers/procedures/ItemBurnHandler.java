package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.util.BurnItemScheduler;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.util.TaskScheduler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber
public class ItemBurnHandler {
    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingUpdateEvent event) {
        if (event != null && event.getEntity() != null
                && event.getEntity() instanceof Mob mob
                && !mob.level.isClientSide()
                && (ForgeRegistries.ENTITIES.getKey(event.getEntity().getType()).toString().equals("player_mobs:player_mob")
                || ForgeRegistries.ENTITIES.getKey(event.getEntity().getType()).toString().equals("minecraft:zombie")
                || ForgeRegistries.ENTITIES.getKey(event.getEntity().getType()).toString().equals("annoyingvillagers:cun_min_zhen_cha_bing"))) {
            handleItemBurning((Mob) event.getEntity());
        }
    }

    private static void handleItemBurning(Mob mob) {
        if (mob.getTarget() != null) return;
        CompoundTag data = mob.getPersistentData();
        if (!data.contains("backup_main_hand")) {
            TaskScheduler.schedule(() -> {
                try {
                    new BurnItemScheduler(mob).run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 20);
        }
    }
}