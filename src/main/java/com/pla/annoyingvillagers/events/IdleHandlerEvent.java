package com.pla.annoyingvillagers.events;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.clazz.PathfinderMobInventory;
import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import com.pla.annoyingvillagers.task.TaskScheduler;
import com.pla.annoyingvillagers.util.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber
public class IdleHandlerEvent {
    static Random RANDOM = new Random();
    public enum IdleAction {
        BURN_ITEM,
        PLAY_ANIMATION,
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        Entity entity = event.getEntity();
        if (entity != null && !entity.level().isClientSide()) {
            if (entity instanceof PathfinderMobInventory) {
                scheduleIdleActionDecision((Mob) event.getEntity());
            }
            else if (entity instanceof Zombie || entity instanceof AbstractSkeleton) {
                LivingEntity livingEntity = (LivingEntity) entity;
                if (canPlayAnimation(livingEntity.getMainHandItem())) {
                    performIdleAction(entity, IdleAction.PLAY_ANIMATION);
                }
            }
            else if (entity instanceof PlayerNpcEntity) {
                scheduleIdleActionDecision((Mob) event.getEntity());
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        Entity hurtEntity = event.getEntity();
        if (hurtEntity.level().isClientSide()) return;
        resetItem(hurtEntity);
    }

    private static void resetItem(Entity entity) {
        if (entity.getPersistentData().contains("av_idle_burn_backup_main_hand")) {
            CompoundTag fullTag = null;
            try {
                fullTag = TagParser.parseTag(entity.getPersistentData().getString("av_idle_burn_backup_main_hand"));
            } catch (CommandSyntaxException e) {

            }
            String id = fullTag.getString("id");
            String nbtPart = fullTag.contains("tag") ? fullTag.getCompound("tag").toString() : "";
            String cmd = "item replace entity @s weapon.mainhand with " + id;
            if (!nbtPart.isEmpty()) {
                cmd += nbtPart;
            }
            try {
                entity.getServer().getCommands().getDispatcher().execute(
                        cmd,
                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                );
            } catch (CommandSyntaxException e) {

            }
            entity.getPersistentData().remove("av_idle_burn_backup_main_hand");
        }
    }

    public static IdleAction getRandomIdleAction() {
        return RANDOM.nextFloat() < 0.25f ? IdleAction.BURN_ITEM : IdleAction.PLAY_ANIMATION;
    }

    private static void scheduleIdleActionDecision(Mob mob) {
        if (mob.isPassenger()) {
            return;
        }
        if (!canPlayAnimation(mob.getMainHandItem())) {
            performIdleAction(mob, IdleAction.BURN_ITEM);
        } else {
            CompoundTag data = mob.getPersistentData();
            if (!data.contains("av_idle_action")) {
                IdleAction action = getRandomIdleAction();
                data.putString("av_idle_action", action.toString());
                performIdleAction(mob, action);
            } else {
                performIdleAction(mob, IdleAction.valueOf(data.getString("av_idle_action")));
            }
        }
    }

    public static boolean canPlayAnimation(ItemStack stack) {
        CapabilityItem cap = EpicFightCapabilities.getItemStackCapability(stack);

        if (cap instanceof WeaponCapability weaponCap) {
            return weaponCap.getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD ||
                    weaponCap.getWeaponCategory() == CapabilityItem.WeaponCategories.AXE ||
                    weaponCap.getWeaponCategory() == CapabilityItem.WeaponCategories.FIST ||
                    weaponCap.getWeaponCategory() == CapabilityItem.WeaponCategories.RANGED ||
                    weaponCap.getWeaponCategory() == CapabilityItem.WeaponCategories.HOE ||
                    weaponCap.getWeaponCategory() == CapabilityItem.WeaponCategories.TRIDENT;
        }

        return false;
    }

    private static void performIdleAction(Entity entity, IdleAction action) {
        if (!(entity instanceof Mob mob)) return;
        if (mob.isRemoved() || mob.isDeadOrDying()) return;
        CompoundTag data = mob.getPersistentData();
        if (mob.getTarget() != null) {
            if (data.contains("av_idle_action")) {
                data.remove("av_idle_action");
            }
            if (data.contains("av_idle_animation_playing")) {
                data.remove("av_idle_animation_playing");
                mob.setNoAi(false);
            }
            resetItem(mob);
            return;
        }
        switch (action) {
            case BURN_ITEM -> {
                List<ItemEntity> nearbyItems = mob.level().getEntitiesOfClass(ItemEntity.class, mob.getBoundingBox().inflate(2));
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
                        }
                    }, 10);
                }
            }
            case PLAY_ANIMATION -> {
                if (!data.contains("av_idle_animation_playing")) {
                    data.putString("av_idle_animation_playing", "playing");
                    mob.setNoAi(true);
                    IdleAnimation idleAnimation;
                    if (mob instanceof PlayerNpcEntity) {
                        idleAnimation = IdleAnimation.values()[RANDOM.nextInt(IdleAnimation.values().length)];
                    } else {
                        IdleAnimation[] subset = {IdleAnimation.SIT, IdleAnimation.LAY};
                        idleAnimation = subset[new Random().nextInt(subset.length)];
                    }
                    TaskScheduler.schedule(() -> {
                        try {
                            new AnimationSheduler(mob).run(idleAnimation, false, true);
                        } catch (Exception e) {
                        }
                    }, 20);
                }
            }
        }
    }
}