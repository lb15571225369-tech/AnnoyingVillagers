package com.pla.annoyingvillagers.procedures;

import java.util.Comparator;
import java.util.List;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import com.pla.annoyingvillagers.capabilities.AVCategories;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.effect.EpicFightMobEffects;

@EventBusSubscriber
public class Execute {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRightClickEntity(EntityInteract entityinteract) {
        if (entityinteract.getHand() == entityinteract.getPlayer().getUsedItemHand()) {
            Player player = entityinteract.getPlayer();
            Level level = entityinteract.getEntity().getLevel();

            if (player != null && level != null) {
                if (!level.isClientSide()) {
                    execute(player, level);
                }

            }
        }
    }

    public static void execute(Player player, Level level) {
        if (player != null && level != null) {
            Vec3 vec3 = new Vec3(player.getX(), player.getY(), player.getZ());

            getNearbyEntities(level, vec3).forEach((livingentity) -> {
                LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(livingentity, LivingEntityPatch.class);

                if (livingentitypatch != null && livingentity != player) {
                    PlayerPatch<?> playerpatch = (PlayerPatch) EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);

                    if (isAnimationValid(livingentitypatch, playerpatch)) {
                        handleExecution((ServerPlayer) player, livingentitypatch, playerpatch);
                    }
                }

            });
        }
    }

    private static List<LivingEntity> getNearbyEntities(Level level, Vec3 vec3) {
        return level.getEntitiesOfClass(LivingEntity.class, (new AABB(vec3, vec3)).inflate(3.0D), (livingentity) -> {
            return true;
        }).stream().sorted(Comparator.comparingDouble((livingentity) -> {
            return livingentity.distanceToSqr(vec3);
        })).toList();
    }

    private static boolean isAnimationValid(LivingEntityPatch<?> livingentitypatch, PlayerPatch<?> playerpatch) {
        DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();
        boolean flag = dynamicanimation instanceof StaticAnimation && (StaticAnimation) dynamicanimation == Animations.BIPED_KNEEL;
        boolean flag1 = dynamicanimation instanceof LongHitAnimation;

        if (flag1) {
            LongHitAnimation longhitanimation = (LongHitAnimation) dynamicanimation;
            StaticAnimation[] astaticanimation = new StaticAnimation[]{Animations.WITHER_NEUTRALIZED, Animations.VEX_NEUTRALIZED, Animations.SPIDER_NEUTRALIZED, Animations.DRAGON_NEUTRALIZED, Animations.ENDERMAN_NEUTRALIZED, Animations.BIPED_COMMON_NEUTRALIZED, Animations.GREATSWORD_GUARD_BREAK};
            StaticAnimation[] astaticanimation1 = astaticanimation;
            int i = astaticanimation.length;

            for (int j = 0; j < i; ++j) {
                StaticAnimation staticanimation = astaticanimation1[j];

                if (staticanimation == longhitanimation) {
                    flag1 = true;
                    break;
                }
            }
        }

        return flag || flag1;
    }

    private static void handleExecution(ServerPlayer serverplayer, LivingEntityPatch<?> livingentitypatch, PlayerPatch<?> playerpatch) {
        if (!((LivingEntity) livingentitypatch.getOriginal()).hasEffect((MobEffect) AnnoyingVillagersModMobEffects.EC.get()) && !serverplayer.getPersistentData().getBoolean("kick_x")) {
            if (((LivingEntity) livingentitypatch.getOriginal()).isAlive()) {
                Vec3 vec3;

                if ((playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.SWORD || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SHIELD) && (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.AXE || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SHIELD) && (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.DAGGER || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SHIELD) && (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.LONGSWORD || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SHIELD)) {

                    if ((playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.TACHI || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI) && (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.TACHI || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD) && (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.SWORD || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI) && (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.TACHI || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE) && (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.SWORD || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD) && (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.DAGGER || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.DAGGER)) {
                        if ((playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.AXE || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE) && (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.AXE || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI) && (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.AXE || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD)) {
                            if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.SWORD || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.AXE || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.DAGGER || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.UCHIGATANA || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.TRIDENT) {
                                if (!serverplayer.isSprinting()) {
                                    serverplayer.getPersistentData().putBoolean("kick_x", true);
                                    vec3 = ((LivingEntity) livingentitypatch.getOriginal()).getViewVector(1.0F);
                                    serverplayer.addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 80, 0, false, false));
                                    serverplayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 29, 10, false, false));
                                    serverplayer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 50, false, false));
                                    serverplayer.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 0, false, false));
                                    serverplayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 5, false, false));
                                    ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 5, false, false));
                                    ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 30, 0, false, false));
                                    serverplayer.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 40, 0, false, false));
                                    ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 120, 0, false, false));
                                    ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 10, false, false));
                                    serverplayer.teleportTo(((LivingEntity) livingentitypatch.getOriginal()).getX() + vec3.x * 1.4D, ((LivingEntity) livingentitypatch.getOriginal()).getY(), ((LivingEntity) livingentitypatch.getOriginal()).getZ() + vec3.z * 1.4D);
                                    serverplayer.lookAt(Anchor.EYES, new Vec3(((LivingEntity) livingentitypatch.getOriginal()).getX(), ((LivingEntity) livingentitypatch.getOriginal()).getY() + 1.0D, ((LivingEntity) livingentitypatch.getOriginal()).getZ()));
                                    ((LivingEntity) livingentitypatch.getOriginal()).lookAt(Anchor.EYES, new Vec3(serverplayer.getX(), serverplayer.getY() + 1.0D, serverplayer.getZ()));
                                    playerpatch.playAnimationSynchronized(AVAnimations.EXECUTE_ONE_HAND, 0.0F);
                                    livingentitypatch.playAnimationSynchronized(AVAnimations.EXECUTE_DUAL_HIT, 0.0F);
                                } else {
                                    serverplayer.getPersistentData().putBoolean("kick_x", true);
                                    serverplayer.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 20, 0, false, false));
                                    vec3 = ((LivingEntity) livingentitypatch.getOriginal()).getViewVector(1.0F);
                                    playerpatch.setGrapplingTarget((LivingEntity) livingentitypatch.getOriginal());
                                    playerpatch.playAnimationSynchronized(AVAnimations.SWEEPING_EDGE, 0.0F);
                                }
                            }
                        } else {
                            serverplayer.getPersistentData().putBoolean("kick_x", true);
                            vec3 = ((LivingEntity) livingentitypatch.getOriginal()).getViewVector(1.0F);
                            playerpatch.setGrapplingTarget((LivingEntity) livingentitypatch.getOriginal());
                            serverplayer.addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 80, 0, false, false));
                            serverplayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 32, 10, false, false));
                            serverplayer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 50, false, false));
                            serverplayer.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 0, false, false));
                            serverplayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 5, false, false));
                            ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 5, false, false));
                            ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 10, 0, false, false));
                            serverplayer.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 50, 0, false, false));
                            ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 120, 0, false, false));
                            ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 10, false, false));
                            serverplayer.teleportTo(((LivingEntity) livingentitypatch.getOriginal()).getX() + vec3.x * 1.85D, ((LivingEntity) livingentitypatch.getOriginal()).getY(), ((LivingEntity) livingentitypatch.getOriginal()).getZ() + vec3.z * 1.85D);
                            serverplayer.lookAt(Anchor.EYES, new Vec3(((LivingEntity) livingentitypatch.getOriginal()).getX(), ((LivingEntity) livingentitypatch.getOriginal()).getY() + 1.0D, ((LivingEntity) livingentitypatch.getOriginal()).getZ()));
                            ((LivingEntity) livingentitypatch.getOriginal()).lookAt(Anchor.EYES, new Vec3(serverplayer.getX(), serverplayer.getY() + 1.0D, serverplayer.getZ()));
                            playerpatch.playAnimationSynchronized(AVAnimations.EXECUTE_DUAL_AXE, 0.0F);
                            livingentitypatch.playAnimationSynchronized(AVAnimations.EXECUTE_DUAL_AXE_HIT, 0.0F);
                        }
                    } else {
                        serverplayer.getPersistentData().putBoolean("kick_x", true);
                        vec3 = ((LivingEntity) livingentitypatch.getOriginal()).getViewVector(1.0F);
                        playerpatch.setGrapplingTarget((LivingEntity) livingentitypatch.getOriginal());
                        serverplayer.addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 80, 0, false, false));
                        serverplayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 29, 10, false, false));
                        serverplayer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 50, false, false));
                        serverplayer.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 0, false, false));
                        serverplayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 5, false, false));
                        ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 5, false, false));
                        ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 30, 0, false, false));
                        serverplayer.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 40, 0, false, false));
                        ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 120, 0, false, false));
                        ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 10, false, false));
                        serverplayer.teleportTo(((LivingEntity) livingentitypatch.getOriginal()).getX() + vec3.x * 1.9D, ((LivingEntity) livingentitypatch.getOriginal()).getY(), ((LivingEntity) livingentitypatch.getOriginal()).getZ() + vec3.z * 1.9D);
                        serverplayer.lookAt(Anchor.EYES, new Vec3(((LivingEntity) livingentitypatch.getOriginal()).getX(), ((LivingEntity) livingentitypatch.getOriginal()).getY() + 1.0D, ((LivingEntity) livingentitypatch.getOriginal()).getZ()));
                        ((LivingEntity) livingentitypatch.getOriginal()).lookAt(Anchor.EYES, new Vec3(serverplayer.getX(), serverplayer.getY() + 1.0D, serverplayer.getZ()));
                        playerpatch.playAnimationSynchronized(AVAnimations.EXECUTE_DUAL, 0.0F);
                        livingentitypatch.playAnimationSynchronized(AVAnimations.EXECUTE_DUAL_HIT, 0.0F);
                    }

                    if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.GREATSWORD && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != AVCategories.LEGENDARYSWORD) {
                        if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.LONGSWORD && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != AVCategories.HARDGREATSWORD && (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.TACHI || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.FIST)) {
                            if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.SPEAR) {
                                serverplayer.getPersistentData().putBoolean("kick_x", true);
                                vec3 = ((LivingEntity) livingentitypatch.getOriginal()).getViewVector(1.0F);
                                playerpatch.setGrapplingTarget((LivingEntity) livingentitypatch.getOriginal());
                                serverplayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 5, false, false));
                                ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 5, false, false));
                                serverplayer.addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 100, 0, false, false));
                                serverplayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 10, false, false));
                                serverplayer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 50, false, false));
                                ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 60, 0, false, false));
                                serverplayer.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 70, 0, false, false));
                                ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 30, 0, false, false));
                                ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 10, false, false));
                                serverplayer.teleportTo(((LivingEntity) livingentitypatch.getOriginal()).getX() + vec3.x * 1.35D, ((LivingEntity) livingentitypatch.getOriginal()).getY(), ((LivingEntity) livingentitypatch.getOriginal()).getZ() + vec3.z * 1.35D);
                                serverplayer.lookAt(Anchor.EYES, new Vec3(((LivingEntity) livingentitypatch.getOriginal()).getX(), ((LivingEntity) livingentitypatch.getOriginal()).getY() + 1.0D, ((LivingEntity) livingentitypatch.getOriginal()).getZ()));
                                ((LivingEntity) livingentitypatch.getOriginal()).lookAt(Anchor.EYES, new Vec3(serverplayer.getX(), serverplayer.getY() + 1.0D, serverplayer.getZ()));
                                playerpatch.playAnimationSynchronized(WOMAnimations.TORMENT_CHARGED_ATTACK_1, 0.0F);
                                livingentitypatch.playAnimationSynchronized(AVAnimations.EXECUTED_SKILL, 0.0F);
                            }
                        } else {
                            serverplayer.getPersistentData().putBoolean("kick_x", true);
                            vec3 = ((LivingEntity) livingentitypatch.getOriginal()).getViewVector(1.0F);
                            playerpatch.setGrapplingTarget((LivingEntity) livingentitypatch.getOriginal());
                            serverplayer.addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 100, 0, false, false));
                            serverplayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 10, false, false));
                            serverplayer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 50, false, false));
                            serverplayer.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 0, false, false));
                            ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 60, 0, false, false));
                            serverplayer.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 70, 0, false, false));
                            ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 150, 0, false, false));
                            ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 10, false, false));
                            serverplayer.teleportTo(((LivingEntity) livingentitypatch.getOriginal()).getX() + vec3.x * 1.35D, ((LivingEntity) livingentitypatch.getOriginal()).getY(), ((LivingEntity) livingentitypatch.getOriginal()).getZ() + vec3.z * 1.35D);
                            serverplayer.lookAt(Anchor.EYES, new Vec3(((LivingEntity) livingentitypatch.getOriginal()).getX(), ((LivingEntity) livingentitypatch.getOriginal()).getY() + 1.0D, ((LivingEntity) livingentitypatch.getOriginal()).getZ()));
                            ((LivingEntity) livingentitypatch.getOriginal()).lookAt(Anchor.EYES, new Vec3(serverplayer.getX(), serverplayer.getY() + 1.0D, serverplayer.getZ()));
                            serverplayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 5, false, false));
                            ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 5, false, false));
                            playerpatch.playAnimationSynchronized(AVAnimations.EXECUTE_LONGSWORD, 0.0F);
                            livingentitypatch.playAnimationSynchronized(AVAnimations.EXECUTE_LONGSWORD_HIT, 0.0F);
                        }
                    } else {
                        serverplayer.getPersistentData().putBoolean("kick_x", true);
                        vec3 = ((LivingEntity) livingentitypatch.getOriginal()).getViewVector(1.0F);
                        serverplayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 5, false, false));
                        ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 5, false, false));
                        playerpatch.setGrapplingTarget((LivingEntity) livingentitypatch.getOriginal());
                        serverplayer.addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 100, 0, false, false));
                        serverplayer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 50, false, false));
                        serverplayer.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 0, false, false));
                        serverplayer.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 75, 0, false, false));
                        ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 150, 0, false, false));
                        ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 130, 0, false, false));
                        serverplayer.teleportTo(((LivingEntity) livingentitypatch.getOriginal()).getX() + vec3.x * 1.5D, ((LivingEntity) livingentitypatch.getOriginal()).getY(), ((LivingEntity) livingentitypatch.getOriginal()).getZ() + vec3.z * 1.5D);
                        serverplayer.lookAt(Anchor.EYES, new Vec3(((LivingEntity) livingentitypatch.getOriginal()).getX(), ((LivingEntity) livingentitypatch.getOriginal()).getY() + 1.0D, ((LivingEntity) livingentitypatch.getOriginal()).getZ()));
                        ((LivingEntity) livingentitypatch.getOriginal()).lookAt(Anchor.EYES, new Vec3(serverplayer.getX(), serverplayer.getY() + 1.0D, serverplayer.getZ()));
                        playerpatch.playAnimationSynchronized(AVAnimations.EXECUTE_GREATSWORD, 0.0F);
                        livingentitypatch.playAnimationSynchronized(AVAnimations.EXECUTE_GREATSWORD_HIT, 0.0F);
                    }
                } else {
                    vec3 = ((LivingEntity) livingentitypatch.getOriginal()).getViewVector(1.0F);
                }
            }
        }

    }
}
