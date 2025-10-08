package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.util.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber
public class IdleHandlerProcedure {
    static Random RANDOM = new Random();
    public enum IdleAction {
        BURN_ITEM,
        PLAY_ANIMATION,
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        Entity entity = event.getEntity();
        if (entity != null && !entity.level().isClientSide()) {
            if (entity instanceof VillagerScoutEntity || entity instanceof VillagerScoutCaptainEntity ||
            entity instanceof RedVillagerGeneralEntity || entity instanceof BlueVillagerGeneralEntity ||
            entity instanceof GreenVillagerGeneralEntity || entity instanceof PurpleVillagerGeneralEntity) {
                performIdleAction(entity, IdleAction.BURN_ITEM);
            }
            else if (ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType()).toString().equals("player_mobs:player_mob")) {
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
        if (entity.getPersistentData().contains("av_idle_animate_backup_main_hand")) {
            CompoundTag fullTag = null;
            try {
                fullTag = TagParser.parseTag(entity.getPersistentData().getString("av_idle_animate_backup_main_hand"));
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
            entity.getPersistentData().remove("av_idle_animate_backup_main_hand");
            if (entity.getPersistentData().contains("av_idle_action")) {
                entity.getPersistentData().remove("av_idle_action");
            }
            if (entity.getPersistentData().contains("idle_message_broadcasted")) {
                entity.getPersistentData().remove("idle_message_broadcasted");
            }
        }
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
        return RANDOM.nextFloat() < 0.8f ? IdleAction.BURN_ITEM : IdleAction.PLAY_ANIMATION;
    }

    private static void scheduleIdleActionDecision(Mob mob) {
        if (mob.isPassenger()) {
            return;
        }
        CompoundTag data = mob.getPersistentData();
        if (!data.contains("av_idle_action")) {
            IdleAction action = getRandomIdleAction();
            data.putString("av_idle_action", action.toString());
            performIdleAction(mob, action);
        } else {
            performIdleAction(mob, IdleAction.valueOf(data.getString("av_idle_action")));
        }
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
                            e.printStackTrace();
                        }
                    }, 10);
                }
            }
            case PLAY_ANIMATION -> {
                if (!mob.level().isClientSide()) {
                    mob.getNavigation().stop();
                    mob.setDeltaMovement(0, 0, 0);
                    mob.setYRot(mob.getYHeadRot());
                }
                LivingEntityPatch livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(mob, LivingEntityPatch.class);

                if (livingentitypatch != null) {
                    DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();
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
                        new DelayedTask(40) {
                            @Override
                            public void run() {
                                TaskScheduler.schedule(() -> {
                                    if (mob == null || !mob.isAlive() || mob.isRemoved() || ((LivingEntity) mob).isDeadOrDying())
                                        return;
                                    new AnimationSheduler(mob).run(idleAnimation, false, false);
                                    TaskScheduler.schedule(() -> {
                                        if (mob == null || !mob.isAlive() || mob.isRemoved() || ((LivingEntity) mob).isDeadOrDying())
                                            return;
                                        new AnimationSheduler(mob).run(idleAnimation, false, false);
                                        TaskScheduler.schedule(() -> {
                                            if (mob == null || !mob.isAlive() || mob.isRemoved() || ((LivingEntity) mob).isDeadOrDying())
                                                return;
                                            new AnimationSheduler(mob).run(idleAnimation, false, false);
                                            TaskScheduler.schedule(() -> {
                                                if (mob == null || !mob.isAlive() || mob.isRemoved() || ((LivingEntity) mob).isDeadOrDying())
                                                    return;
                                                new AnimationSheduler(mob).run(idleAnimation, false, false);
                                                TaskScheduler.schedule(() -> {
                                                    if (mob == null || !mob.isAlive() || mob.isRemoved() || ((LivingEntity) mob).isDeadOrDying())
                                                        return;
                                                    new AnimationSheduler(mob).run(idleAnimation, false, false);
                                                    TaskScheduler.schedule(() -> {
                                                        if (mob == null || !mob.isAlive() || mob.isRemoved() || ((LivingEntity) mob).isDeadOrDying())
                                                            return;
                                                        new AnimationSheduler(mob).run(idleAnimation, false, false);
                                                        TaskScheduler.schedule(() -> {
                                                            if (mob == null || !mob.isAlive() || mob.isRemoved() || ((LivingEntity) mob).isDeadOrDying())
                                                                return;
                                                            new AnimationSheduler(mob).run(idleAnimation, false, false);
                                                            TaskScheduler.schedule(() -> {
                                                                if (mob == null || !mob.isAlive() || mob.isRemoved() || ((LivingEntity) mob).isDeadOrDying())
                                                                    return;
                                                                new AnimationSheduler(mob).run(idleAnimation, false, true);
                                                            }, 5);
                                                        }, 5);
                                                    }, 5);
                                                }, 5);
                                            }, 5);
                                        }, 5);
                                    }, 5);
                                }, 5);
                            }
                        };
                    }
                }
            }
        }
    }
}