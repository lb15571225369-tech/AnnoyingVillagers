package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.util.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.api.animation.types.MovementAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
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
        if (!(event.getEntity() instanceof Mob mob)) return;
        if (mob.level().isClientSide()) return;
        if (event.getEntity() != null && !mob.level().isClientSide() && (ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType()).toString().equals("minecraft:zombie") || ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType()).toString().equals("minecraft:husk") || ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType()).toString().equals("minecraft:skeleton") || ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType()).toString().equals("minecraft:stray") || ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType()).toString().equals("minecraft:wither_skeleton") || ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType()).toString().equals("annoyingvillagers:villager_scout") || ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType()).toString().equals("annoyingvillagers:villager_scout_captain") || ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType()).toString().equals("annoyingvillagers:jev"))) {
            performIdleAction(mob, IdleAction.BURN_ITEM);
        }
        if (event.getEntity() != null && ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType()).toString().equals("player_mobs:player_mob")) {
            scheduleIdleActionDecision((Mob) event.getEntity());
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

    private static void performIdleAction(Mob mob, IdleAction action) {
        if (mob == null || mob.isRemoved() || mob.isDeadOrDying()) return;
        CompoundTag data = mob.getPersistentData();
        if (mob.getTarget() != null) {
            if (data.contains("av_idle_action")) {
                data.remove("av_idle_action");
            }
            if (data.contains("av_idle_animation_playing")) {
                data.remove("av_idle_animation_playing");
            }
            if (data.contains("av_idle_animate_backup_main_hand")) {
                CompoundTag fullTag = null;
                try {
                    fullTag = TagParser.parseTag(data.getString("av_idle_animate_backup_main_hand"));
                } catch (CommandSyntaxException e) {
                    
                }
                String id = fullTag.getString("id");
                String nbtPart = fullTag.contains("tag") ? fullTag.getCompound("tag").toString() : "";
                String cmd = "item replace entity @s weapon.mainhand with " + id;
                if (!nbtPart.isEmpty()) {
                    cmd += nbtPart;
                }
                try {
                    mob.getServer().getCommands().getDispatcher().execute(
                            cmd,
                            mob.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                    );
                } catch (CommandSyntaxException e) {
                    
                }
                data.remove("av_idle_animate_backup_main_hand");
                if (data.contains("av_idle_action")) {
                    data.remove("av_idle_action");
                }
                if (data.contains("idle_message_broadcasted")) {
                    data.remove("idle_message_broadcasted");
                }
            }
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
                final LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(mob, LivingEntityPatch.class);

                if (livingentitypatch != null) {
                    DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();
                    if (dynamicanimation instanceof StaticAnimation) {
                        if (!data.contains("av_idle_animate_backup_main_hand")) {
                            ItemStack held = mob.getMainHandItem();
                            if (!held.isEmpty()) {
                                CompoundTag tag = new CompoundTag();
                                held.save(tag);
                                data.putString("av_idle_animate_backup_main_hand", tag.toString());
                            }
                            IdleAnimation idleAnimation = IdleAnimation.values()[RANDOM.nextInt(IdleAnimation.values().length)];
                            if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return;
                            mob.getNavigation().stop();
                            mob.setDeltaMovement(0, 0, 0);
                            mob.setYRot(mob.getYHeadRot());
                            new AnimationSheduler(mob).run(idleAnimation, false, true);
                        }
                    }
//                mob.getNavigation().stop();
//                mob.setDeltaMovement(0, 0, 0);
//                mob.setYRot(mob.getYHeadRot());
//                if (!data.contains("av_idle_animate_backup_main_hand")) {
//                    ItemStack held = mob.getMainHandItem();
//                    if (!held.isEmpty()) {
//                        CompoundTag tag = new CompoundTag();
//                        held.save(tag);
//                        data.putString("av_idle_animate_backup_main_hand", tag.toString());
//                    }
//                    IdleAnimation idleAnimation = IdleAnimation.values()[RANDOM.nextInt(IdleAnimation.values().length)];
//                    new DelayedTask(40) {
//                        @Override
//                        public void run() {
//                            TaskScheduler.schedule(() -> {
//                                if (mob == null || !mob.isAlive() || mob.isRemoved() || ((LivingEntity) mob).isDeadOrDying()) return;
//                                new AnimationSheduler(mob).run(idleAnimation, false, false);
//                                TaskScheduler.schedule(() -> {
//                                    if (mob == null || !mob.isAlive() || mob.isRemoved() || ((LivingEntity) mob).isDeadOrDying()) return;
//                                    new AnimationSheduler(mob).run(idleAnimation, false, false);
//                                    TaskScheduler.schedule(() -> {
//                                        if (mob == null || !mob.isAlive() || mob.isRemoved() || ((LivingEntity) mob).isDeadOrDying()) return;
//                                        new AnimationSheduler(mob).run(idleAnimation, false, false);
//                                        TaskScheduler.schedule(() -> {
//                                            if (mob == null || !mob.isAlive() || mob.isRemoved() || ((LivingEntity) mob).isDeadOrDying()) return;
//                                            new AnimationSheduler(mob).run(idleAnimation, false, false);
//                                            TaskScheduler.schedule(() -> {
//                                                if (mob == null || !mob.isAlive() || mob.isRemoved() || ((LivingEntity) mob).isDeadOrDying()) return;
//                                                new AnimationSheduler(mob).run(idleAnimation, false, false);
//                                                TaskScheduler.schedule(() -> {
//                                                    if (mob == null || !mob.isAlive() || mob.isRemoved() || ((LivingEntity) mob).isDeadOrDying()) return;
//                                                    new AnimationSheduler(mob).run(idleAnimation, false, false);
//                                                    TaskScheduler.schedule(() -> {
//                                                        if (mob == null || !mob.isAlive() || mob.isRemoved() || ((LivingEntity) mob).isDeadOrDying()) return;
//                                                        new AnimationSheduler(mob).run(idleAnimation, false, false);
//                                                        TaskScheduler.schedule(() -> {
//                                                            if (mob == null || !mob.isAlive() || mob.isRemoved() || ((LivingEntity) mob).isDeadOrDying()) return;
//                                                            new AnimationSheduler(mob).run(idleAnimation, false, true);
//                                                        }, 5);
//                                                    }, 5);
//                                                }, 5);
//                                            }, 5);
//                                        }, 5);
//                                    }, 5);
//                                }, 5);
//                            }, 5);
//                        }
//                    };
                }
            }
        }
    }
}