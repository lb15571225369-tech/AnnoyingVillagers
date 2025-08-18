package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.capabilities.AVCategories;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.corruptdog.cdm.gameasset.CorruptSound;
import net.corruptdog.cdm.network.server.NetworkManager;
import net.corruptdog.cdm.network.server.SPLockOn;
import net.corruptdog.cdm.skill.identity.Execute;
import net.corruptdog.cdm.world.CDWeaponCapabilityPresets;
import net.corruptdog.cdm.world.CorruptWeaponCategories;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.network.server.SPPlayAnimation;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.effect.EpicFightMobEffects;

@Mixin(value = {Execute.class}, remap = false)
public class ExecutionMixin {
    @Inject(method = {"isAnimationValid"}, at = {@At("HEAD")}, cancellable = true)
    private static boolean addKnockDownToExecution(LivingEntityPatch<?> targetPatch, PlayerPatch<?> playerPatch, CallbackInfoReturnable<Boolean> cir) {
        DynamicAnimation var4 = targetPatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();
        DynamicAnimation dynamicanimation = targetPatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();
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

    @Inject(method = {"onRightClickEntity"}, at = {@At("HEAD")}, cancellable = true)
    private static void unregisterEvent(PlayerInteractEvent.RightClickItem event, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = {"handleExecution"}, at = {@At("HEAD")}, cancellable = true)
    private static void ignoreExecution(ServerPlayer player, LivingEntityPatch<?> targetPatch, PlayerPatch<?> playerPatch, CallbackInfo ci) {
        Vec3 viewVec = ((LivingEntity)targetPatch.getOriginal()).getViewVector(1.0F);
        playerPatch.setGrapplingTarget((LivingEntity)targetPatch.getOriginal());
        SPLockOn msg = new SPLockOn();
        NetworkManager.sendToAllPlayerTrackingThisEntityWithSelf(msg, player);
        player.addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 100, 0));
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 50, 1));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 5));
        ((LivingEntity)targetPatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect)EpicFightMobEffects.STUN_IMMUNITY.get(), 40, 0));
        targetPatch.playSound((SoundEvent) CorruptSound.EXECUTE.get(), 1.0F, 1.0F);
        if (!((LivingEntity) playerPatch.getOriginal()).hasEffect((MobEffect) AnnoyingVillagersModMobEffects.EC.get()) && !player.getPersistentData().getBoolean("kick_x"))
        {
            if (playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CorruptWeaponCategories.YAMATO && playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CDWeaponCapabilityPresets.EX_YAMATO) {
                if (playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CorruptWeaponCategories.KATANA && playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.UCHIGATANA) {
                    if ((playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CorruptWeaponCategories.S_DAGGER || playerPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CorruptWeaponCategories.S_DAGGER) && (playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.DAGGER || playerPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.DAGGER)) {
                        if (playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CorruptWeaponCategories.S_SPEAR && playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.SPEAR) {
                            if (playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CorruptWeaponCategories.S_GREATSWORD && playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.GREATSWORD && playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != AVCategories.LEGENDARYSWORD) {
                                if (playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.TRIDENT) {
                                    player.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                    ((LivingEntity) targetPatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 160, 0, false, false));
                                    player.getPersistentData().putBoolean("kick_x", true);
                                    player.teleportTo(((LivingEntity) targetPatch.getOriginal()).getX() + viewVec.x() * (double) 1.5F, ((LivingEntity) targetPatch.getOriginal()).getY(), ((LivingEntity) targetPatch.getOriginal()).getZ() + viewVec.z() * (double) 1.5F);
                                    playerPatch.playAnimationSynchronized(CorruptAnimations.TRIDENT_EXECUTE, 0.0F);
                                    targetPatch.playAnimationSynchronized(CorruptAnimations.TRIDENT_EXECUTED, 0.0F, SPPlayAnimation::new);
                                } else if ((playerPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CorruptWeaponCategories.S_DAGGER || playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CorruptWeaponCategories.S_DAGGER) && (playerPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.DAGGER || playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.DAGGER)) {
                                    if (playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CorruptWeaponCategories.S_TACHI && playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.TACHI) {
                                        // This part is customized
                                        if (playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CorruptWeaponCategories.GREAT_TACHI && playerPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CorruptWeaponCategories.GREAT_TACHI) {
                                            if ((playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CorruptWeaponCategories.S_LONGSWORD || playerPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD) && (playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.LONGSWORD || playerPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD) && playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != AVCategories.HARDGREATSWORD) {
                                                player.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                                ((LivingEntity) targetPatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 160, 0, false, false));
                                                player.getPersistentData().putBoolean("kick_x", true);
                                                player.teleportTo(((LivingEntity) targetPatch.getOriginal()).getX() + viewVec.x() * (double) 1.5F, ((LivingEntity) targetPatch.getOriginal()).getY(), ((LivingEntity) targetPatch.getOriginal()).getZ() + viewVec.z() * (double) 1.5F);
                                                playerPatch.playAnimationSynchronized(CorruptAnimations.EXECUTE_WEAPON, 0.0F);
                                                targetPatch.playAnimationSynchronized(CorruptAnimations.EXECUTED_WEAPON, 0.25F, SPPlayAnimation::new);
                                            } else {
                                                player.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                                ((LivingEntity) targetPatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 160, 0, false, false));
                                                player.getPersistentData().putBoolean("kick_x", true);
                                                player.teleportTo(((LivingEntity) targetPatch.getOriginal()).getX() + viewVec.x() * (double) 2.0F, ((LivingEntity) targetPatch.getOriginal()).getY(), ((LivingEntity) targetPatch.getOriginal()).getZ() + viewVec.z() * (double) 2.0F);
                                                playerPatch.playAnimationSynchronized(CorruptAnimations.LONGSWORD_EXECUTE, 0.0F);
                                                targetPatch.playAnimationSynchronized(CorruptAnimations.LONGSWORD_EXECUTED, 0.0F, SPPlayAnimation::new);
                                            }
                                        } else if (playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.FIST) {
                                            Vec3 vec3;

                                            if (!player.isSprinting()) {
                                                if (player.isShiftKeyDown()) {
                                                    player.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                                    ((LivingEntity) targetPatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 160, 0, false, false));
                                                    player.getPersistentData().putBoolean("kick_x", true);
                                                    vec3 = ((LivingEntity) targetPatch.getOriginal()).getViewVector(3.0F);
                                                    playerPatch.setGrapplingTarget((LivingEntity) targetPatch.getOriginal());
                                                    player.teleportTo(((LivingEntity) targetPatch.getOriginal()).getX() + vec3.x * 1.0D, ((LivingEntity) targetPatch.getOriginal()).getY(), ((LivingEntity) targetPatch.getOriginal()).getZ() + vec3.z * 1.0D);
                                                    player.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(((LivingEntity) targetPatch.getOriginal()).getX(), ((LivingEntity) targetPatch.getOriginal()).getY() + 1.0D, ((LivingEntity) targetPatch.getOriginal()).getZ()));
                                                    ((LivingEntity) targetPatch.getOriginal()).lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(player.getX(), player.getY() + 1.0D, player.getZ()));
                                                    playerPatch.playAnimationSynchronized(AVAnimations.WRESTLING_BACK, 0.0F);
                                                    targetPatch.playAnimationSynchronized(AVAnimations.WRESTLING_HIT_BACK, 0.0F);
                                                } else {
                                                    player.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                                    ((LivingEntity) targetPatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 160, 0, false, false));
                                                    player.getPersistentData().putBoolean("kick_x", true);
                                                    vec3 = ((LivingEntity) targetPatch.getOriginal()).getViewVector(3.0F);
                                                    playerPatch.setGrapplingTarget((LivingEntity) targetPatch.getOriginal());
                                                    player.teleportTo(((LivingEntity) targetPatch.getOriginal()).getX() + vec3.x * 1.0D, ((LivingEntity) targetPatch.getOriginal()).getY(), ((LivingEntity) targetPatch.getOriginal()).getZ() + vec3.z * 1.0D);
                                                    player.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(((LivingEntity) targetPatch.getOriginal()).getX(), ((LivingEntity) targetPatch.getOriginal()).getY() + 1.0D, ((LivingEntity) targetPatch.getOriginal()).getZ()));
                                                    ((LivingEntity) targetPatch.getOriginal()).lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(player.getX(), player.getY() + 1.0D, player.getZ()));
                                                    playerPatch.playAnimationSynchronized(AVAnimations.WRESTLING, 0.0F);
                                                    targetPatch.playAnimationSynchronized(AVAnimations.WRESTLING_HIT, 0.0F);
                                                }
                                            } else {
                                                player.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                                ((LivingEntity) targetPatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 160, 0, false, false));
                                                player.getPersistentData().putBoolean("kick_x", true);
                                                vec3 = ((LivingEntity) targetPatch.getOriginal()).getViewVector(3.0F);
                                                playerPatch.setGrapplingTarget((LivingEntity) targetPatch.getOriginal());
                                                player.teleportTo(((LivingEntity) targetPatch.getOriginal()).getX() + vec3.x * 3.0D, ((LivingEntity) targetPatch.getOriginal()).getY(), ((LivingEntity) targetPatch.getOriginal()).getZ() + vec3.z * 3.0D);
                                                player.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(((LivingEntity) targetPatch.getOriginal()).getX(), ((LivingEntity) targetPatch.getOriginal()).getY() + 1.0D, ((LivingEntity) targetPatch.getOriginal()).getZ()));
                                                ((LivingEntity) targetPatch.getOriginal()).lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(player.getX(), player.getY() + 1.0D, player.getZ()));
                                                playerPatch.playAnimationSynchronized(AVAnimations.BOSS_EXECUTE, 0.0F);
                                                targetPatch.playAnimationSynchronized(AVAnimations.BOSS_EXECUTE_HIT, 0.0F);
                                            }
                                        } else if (playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD || playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.AXE || playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CorruptWeaponCategories.S_SWORD) {
                                            if (playerPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD) {
                                                player.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                                ((LivingEntity) targetPatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 160, 0, false, false));
                                                player.getPersistentData().putBoolean("kick_x", true);
                                                player.teleportTo(((LivingEntity) targetPatch.getOriginal()).getX() + viewVec.x() * (double) 1.0F, ((LivingEntity) targetPatch.getOriginal()).getY(), ((LivingEntity) targetPatch.getOriginal()).getZ() + viewVec.z() * (double) 1.0F);
                                                playerPatch.playAnimationSynchronized(AVAnimations.EXECUTE_WEAPON_SHIELD, 0.0F);
                                                targetPatch.playAnimationSynchronized(AVAnimations.EXECUTE_WEAPON_SHIELD_HIT, 0.0F, SPPlayAnimation::new);
                                            } else if (playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.AXE && playerPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.AXE) {
                                                player.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                                ((LivingEntity) targetPatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 160, 0, false, false));
                                                player.getPersistentData().putBoolean("kick_x", true);
                                                player.teleportTo(((LivingEntity) targetPatch.getOriginal()).getX() + viewVec.x() * (double) 1.0F, ((LivingEntity) targetPatch.getOriginal()).getY(), ((LivingEntity) targetPatch.getOriginal()).getZ() + viewVec.z() * (double) 1.0F);
                                                playerPatch.playAnimationSynchronized(AVAnimations.EXECUTE_DUAL_AXE, 0.0F);
                                                targetPatch.playAnimationSynchronized(AVAnimations.EXECUTE_DUAL_AXE_HIT, 0.0F, SPPlayAnimation::new);
                                            } else if (playerPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD || playerPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CorruptWeaponCategories.S_SWORD) {
                                                player.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                                ((LivingEntity) targetPatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 160, 0, false, false));
                                                player.getPersistentData().putBoolean("kick_x", true);
                                                player.teleportTo(((LivingEntity) targetPatch.getOriginal()).getX() + viewVec.x() * (double) 1.9F, ((LivingEntity) targetPatch.getOriginal()).getY(), ((LivingEntity) targetPatch.getOriginal()).getZ() + viewVec.z() * (double) 1.9F);
                                                playerPatch.playAnimationSynchronized(AVAnimations.EXECUTE_DUAL, 0.0F);
                                                targetPatch.playAnimationSynchronized(AVAnimations.EXECUTE_DUAL_HIT, 0.0F, SPPlayAnimation::new);
                                            } else if (playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD) {
                                                player.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                                ((LivingEntity) targetPatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 160, 0, false, false));
                                                player.getPersistentData().putBoolean("kick_x", true);
                                                player.teleportTo(((LivingEntity) targetPatch.getOriginal()).getX() + viewVec.x() * (double) 1.5F, ((LivingEntity) targetPatch.getOriginal()).getY(), ((LivingEntity) targetPatch.getOriginal()).getZ() + viewVec.z() * (double) 1.5F);
                                                playerPatch.playAnimationSynchronized(CorruptAnimations.EXECUTE_WEAPON, 0.0F);
                                                targetPatch.playAnimationSynchronized(CorruptAnimations.EXECUTED_WEAPON, 0.0F, SPPlayAnimation::new);
                                            } else {
                                                player.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                                ((LivingEntity) targetPatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 160, 0, false, false));
                                                player.getPersistentData().putBoolean("kick_x", true);
                                                player.teleportTo(((LivingEntity) targetPatch.getOriginal()).getX() + viewVec.x() * (double) 1.4F, ((LivingEntity) targetPatch.getOriginal()).getY(), ((LivingEntity) targetPatch.getOriginal()).getZ() + viewVec.z() * (double) 1.4F);
                                                playerPatch.playAnimationSynchronized(AVAnimations.EXECUTE_ONE_HAND, 0.0F);
                                                targetPatch.playAnimationSynchronized(AVAnimations.EXECUTE_DUAL_HIT, 0.0F, SPPlayAnimation::new);
                                            }
                                        } else {
                                            player.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                            ((LivingEntity) targetPatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 160, 0, false, false));
                                            player.getPersistentData().putBoolean("kick_x", true);
                                            player.teleportTo(((LivingEntity) targetPatch.getOriginal()).getX() + viewVec.x() * (double) 2.0F, ((LivingEntity) targetPatch.getOriginal()).getY(), ((LivingEntity) targetPatch.getOriginal()).getZ() + viewVec.z() * (double) 2.0F);
                                            playerPatch.playAnimationSynchronized(CorruptAnimations.LONGSWORD_EXECUTE, 0.0F);
                                            targetPatch.playAnimationSynchronized(CorruptAnimations.LONGSWORD_EXECUTED, 0.0F, SPPlayAnimation::new);
                                        }
                                    } else {
                                        player.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                        ((LivingEntity) targetPatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 160, 0, false, false));
                                        player.getPersistentData().putBoolean("kick_x", true);
                                        player.teleportTo(((LivingEntity) targetPatch.getOriginal()).getX() + viewVec.x() * (double) 2.0F, ((LivingEntity) targetPatch.getOriginal()).getY(), ((LivingEntity) targetPatch.getOriginal()).getZ() + viewVec.z() * (double) 2.0F);
                                        playerPatch.playAnimationSynchronized(CorruptAnimations.TACHI_EXECUTE, 0.0F);
                                        targetPatch.playAnimationSynchronized(CorruptAnimations.TACHI_EXECUTED, 0.0F, SPPlayAnimation::new);
                                    }
                                } else {
                                    player.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                    ((LivingEntity) targetPatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 160, 0, false, false));
                                    player.getPersistentData().putBoolean("kick_x", true);
                                    player.teleportTo(((LivingEntity) targetPatch.getOriginal()).getX() + viewVec.x(), ((LivingEntity) targetPatch.getOriginal()).getY(), ((LivingEntity) targetPatch.getOriginal()).getZ() + viewVec.z());
                                    playerPatch.playAnimationSynchronized(CorruptAnimations.DUAL_DAGGER_EXECUTE, 0.0F);
                                    targetPatch.playAnimationSynchronized(CorruptAnimations.DUAL_DAGGER_EXECUTED, 0.25F, SPPlayAnimation::new);
                                }
                            } else {
                                player.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                ((LivingEntity) targetPatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 160, 0, false, false));
                                player.getPersistentData().putBoolean("kick_x", true);
                                player.teleportTo(((LivingEntity) targetPatch.getOriginal()).getX() + viewVec.x() * (double) 1.5F, ((LivingEntity) targetPatch.getOriginal()).getY(), ((LivingEntity) targetPatch.getOriginal()).getZ() + viewVec.z() * (double) 1.5F);
                                playerPatch.playAnimationSynchronized(CorruptAnimations.GREATSWORD_EXECUTE, 0.0F);
                                targetPatch.playAnimationSynchronized(CorruptAnimations.GREATSWORD_EXECUTED, 0.8F, SPPlayAnimation::new);
                            }
                        } else {
                            player.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                            ((LivingEntity) targetPatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 160, 0, false, false));
                            player.getPersistentData().putBoolean("kick_x", true);
                            player.teleportTo(((LivingEntity) targetPatch.getOriginal()).getX() + viewVec.x() * (double) 2.0F, ((LivingEntity) targetPatch.getOriginal()).getY(), ((LivingEntity) targetPatch.getOriginal()).getZ() + viewVec.z() * (double) 2.0F);
                            playerPatch.playAnimationSynchronized(CorruptAnimations.SPEAR_EXECUTE, 0.0F);
                            targetPatch.playAnimationSynchronized(CorruptAnimations.SPEAR_EXECUTED, 0.25F, SPPlayAnimation::new);
                        }
                    } else {
                        player.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                        ((LivingEntity) targetPatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 160, 0, false, false));
                        player.getPersistentData().putBoolean("kick_x", true);
                        player.teleportTo(((LivingEntity) targetPatch.getOriginal()).getX() + viewVec.x() * (double) 2.0F, ((LivingEntity) targetPatch.getOriginal()).getY(), ((LivingEntity) targetPatch.getOriginal()).getZ() + viewVec.z() * (double) 2.0F);
                        playerPatch.playAnimationSynchronized(CorruptAnimations.DAGGER_EXECUTE, 0.0F);
                        targetPatch.playAnimationSynchronized(CorruptAnimations.DAGGER_EXECUTED, 0.05F, SPPlayAnimation::new);
                    }
                } else {
                    player.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                    ((LivingEntity) targetPatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 160, 0, false, false));
                    player.getPersistentData().putBoolean("kick_x", true);
                    player.teleportTo(((LivingEntity) targetPatch.getOriginal()).getX() + viewVec.x() * (double) 2.0F, ((LivingEntity) targetPatch.getOriginal()).getY(), ((LivingEntity) targetPatch.getOriginal()).getZ() + viewVec.z() * (double) 2.0F);
                    playerPatch.playAnimationSynchronized(CorruptAnimations.KATANA_EXECUTE, 0.0F);
                    targetPatch.playAnimationSynchronized(CorruptAnimations.KATANA_EXECUTED, 0.05F, SPPlayAnimation::new);
                }
            } else {
                player.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                ((LivingEntity) targetPatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 160, 0, false, false));
                player.getPersistentData().putBoolean("kick_x", true);
                player.teleportTo(((LivingEntity) targetPatch.getOriginal()).getX() + viewVec.x() * (double) 2.0F, ((LivingEntity) targetPatch.getOriginal()).getY(), ((LivingEntity) targetPatch.getOriginal()).getZ() + viewVec.z() * (double) 2.0F);
                playerPatch.playAnimationSynchronized(CorruptAnimations.EXECUTE_YAMATO, 0.4F);
                targetPatch.playAnimationSynchronized(CorruptAnimations.EXECUTED_YAMATO, 0.6F, SPPlayAnimation::new);
            }
        }

        ci.cancel();
    }
}
