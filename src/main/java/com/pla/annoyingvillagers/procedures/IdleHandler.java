package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.util.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber
public class IdleHandler {
    static Random RANDOM = new Random();
    public enum IdleAction {
        NOTHING,
        BURN_ITEM,
        PLAY_ANIMATION,
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingUpdateEvent event) {
        if (!(event.getEntity() instanceof Mob mob)) return;
        if (mob.level.isClientSide()) return;
        if (event.getEntity() != null && !mob.level.isClientSide() && (ForgeRegistries.ENTITIES.getKey(event.getEntity().getType()).toString().equals("minecraft:zombie") || ForgeRegistries.ENTITIES.getKey(event.getEntity().getType()).toString().equals("minecraft:skeleton") || ForgeRegistries.ENTITIES.getKey(event.getEntity().getType()).toString().equals("annoyingvillagers:cun_min_zhen_cha_bing"))) {
            performIdleAction(mob, IdleAction.BURN_ITEM);
        }
        if (event.getEntity() != null && ForgeRegistries.ENTITIES.getKey(event.getEntity().getType()).toString().equals("player_mobs:player_mob")) {
            scheduleIdleActionDecision((Mob) event.getEntity());
        }
    }

    private static void scheduleIdleActionDecision(Mob mob) {
        CompoundTag data = mob.getPersistentData();
        if (!data.contains("av_idle_action")) {
            LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(mob, LivingEntityPatch.class);
            IdleAction action = IdleAction.values()[RANDOM.nextInt(IdleAction.values().length)];
            data.putString("av_idle_action", action.toString());
            performIdleAction(mob, action);
        } else {
            performIdleAction(mob, IdleAction.valueOf(data.getString("av_idle_action")));
        }
    }

    private static void performIdleAction(Mob mob, IdleAction action) {
        CompoundTag data = mob.getPersistentData();
        if (mob.getTarget() != null) {
            if (data.contains("av_idle_action")) {
                data.remove("av_idle_action");
            }
            if (data.contains("av_idle_animation_playing")) {
                data.remove("av_idle_animation_playing");
            }
            return;
        }
        switch (action) {
            case NOTHING -> {

            }
            case BURN_ITEM -> {
                List<ItemEntity> nearbyItems = mob.level.getEntitiesOfClass(ItemEntity.class, mob.getBoundingBox().inflate(2));
                if (!nearbyItems.isEmpty() && !data.contains("av_idle_burn_backup_main_hand")) {
                    ItemStack held = mob.getMainHandItem();
                    if (!held.isEmpty() && held.getItem() != Items.FLINT_AND_STEEL) {
                        CompoundTag tag = new CompoundTag();
                        held.save(tag);
                        data.putString("av_idle_burn_backup_main_hand", tag.toString());
                    }
                    TaskScheduler.schedule(() -> {
                        try {
                            new BurnItemScheduler(mob).run();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, 10);
                }
            }
            case PLAY_ANIMATION -> {
                mob.getNavigation().stop();
                mob.setDeltaMovement(0, 0, 0);
                mob.setYRot(mob.getYHeadRot());
                if (!data.contains("av_idle_animate_backup_main_hand")) {
                    ItemStack held = mob.getMainHandItem();
                    if (!held.isEmpty()) {
                        CompoundTag tag = new CompoundTag();
                        held.save(tag);
                        data.putString("av_idle_animate_backup_main_hand", tag.toString());
                    }
                    IdleAnimation idleAnimation = IdleAnimation.values()[RANDOM.nextInt(IdleAnimation.values().length)];
                    new DelayedTask(20) {
                        @Override
                        public void run() throws CommandSyntaxException {
                            TaskScheduler.schedule(() -> {
                                try {
                                    new AnimationSheduler(mob).run(idleAnimation, false, false);
                                    TaskScheduler.schedule(() -> {
                                        try {
                                            new AnimationSheduler(mob).run(idleAnimation, false, false);
                                            TaskScheduler.schedule(() -> {
                                                try {
                                                    new AnimationSheduler(mob).run(idleAnimation, false, false);
                                                    TaskScheduler.schedule(() -> {
                                                        try {
                                                            new AnimationSheduler(mob).run(idleAnimation, false, true);
                                                        } catch (CommandSyntaxException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }, 5);
                                                } catch (CommandSyntaxException e) {
                                                    e.printStackTrace();
                                                }
                                            }, 5);
                                        } catch (CommandSyntaxException e) {
                                            e.printStackTrace();
                                        }
                                    }, 5);
                                } catch (CommandSyntaxException e) {
                                    e.printStackTrace();
                                }
                            }, 5);
                        }
                    };
                }
            }
        }
    }
}