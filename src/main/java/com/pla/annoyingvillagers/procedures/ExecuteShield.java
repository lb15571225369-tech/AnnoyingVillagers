package com.pla.annoyingvillagers.procedures;

import java.util.Comparator;
import java.util.List;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
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
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
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
public class ExecuteShield {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRightClickEntity(RightClickItem rightclickitem) {
        if (rightclickitem.getHand() == rightclickitem.getEntity().getUsedItemHand()) {
            Player player = rightclickitem.getEntity();
            Level level = rightclickitem.getEntity().level();

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
        if (!((LivingEntity) livingentitypatch.getOriginal()).hasEffect((MobEffect) AnnoyingVillagersModMobEffects.EC.get()) && !serverplayer.getPersistentData().getBoolean("kick_x") && ((LivingEntity) livingentitypatch.getOriginal()).isAlive() && (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.SWORD && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SHIELD || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.AXE && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SHIELD || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.DAGGER && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SHIELD || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.LONGSWORD && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SHIELD)) {
            serverplayer.getPersistentData().putBoolean("kick_x", true);
            Vec3 vec3 = ((LivingEntity) livingentitypatch.getOriginal()).getViewVector(1.0F);

            playerpatch.setGrapplingTarget((LivingEntity) livingentitypatch.getOriginal());
            serverplayer.teleportTo(((LivingEntity) livingentitypatch.getOriginal()).getX() + vec3.x * 2.2D, ((LivingEntity) livingentitypatch.getOriginal()).getY(), ((LivingEntity) livingentitypatch.getOriginal()).getZ() + vec3.z * 2.2D);
            serverplayer.addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 100, 0, false, false));
            serverplayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 10, false, false));
            serverplayer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 50, false, false));
            serverplayer.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 30, 0, false, false));
            ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 100, 0, false, false));
            serverplayer.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 70, 0, false, false));
            ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 120, 0, false, false));
            ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 10, false, false));
            serverplayer.lookAt(Anchor.EYES, new Vec3(((LivingEntity) livingentitypatch.getOriginal()).getX(), ((LivingEntity) livingentitypatch.getOriginal()).getY() + 1.0D, ((LivingEntity) livingentitypatch.getOriginal()).getZ()));
            ((LivingEntity) livingentitypatch.getOriginal()).lookAt(Anchor.EYES, new Vec3(serverplayer.getX(), serverplayer.getY() + 1.0D, serverplayer.getZ()));
            serverplayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 5, false, false));
            ((LivingEntity) livingentitypatch.getOriginal()).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 5, false, false));
            playerpatch.playAnimationSynchronized(AVAnimations.EXECUTE_WEAPON_SHIELD, 0.0F);
            livingentitypatch.playAnimationSynchronized(AVAnimations.EXECUTE_WEAPON_SHIELD_HIT, 0.0F);
        }

    }
}
