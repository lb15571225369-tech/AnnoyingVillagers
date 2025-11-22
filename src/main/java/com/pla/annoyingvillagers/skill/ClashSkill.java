package com.pla.annoyingvillagers.skill;

import java.util.List;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.AttackResult.ResultType;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

public class ClashSkill extends GuardSkill {
    public ClashSkill(GuardSkill.Builder builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer skillcontainer) {
        super.onInitiate(skillcontainer);
        skillcontainer.getDataManager();
        skillcontainer.getExecutor().getEventListener().addEventListener(EventType.TAKE_DAMAGE_EVENT_ATTACK, ClashSkill.EVENT_UUID, (pre) -> {
            PlayerPatch<?> playerpatch = pre.getPlayerPatch();
            ServerPlayer serverplayer = (ServerPlayer) ((ServerPlayerPatch) pre.getPlayerPatch()).getOriginal();
            DamageSource damagesource = (DamageSource) pre.getDamageSource();
            AssetAccessor<? extends DynamicAnimation> dynamicanimation = playerpatch.getAnimator().getPlayerFor(null).getAnimation();
            float f = pre.getDamage();

            if ((playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.SWORD || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.AXE || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.LONGSWORD || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.TACHI || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.SPEAR || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.UCHIGATANA || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.GREATSWORD || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.TRIDENT) && !damagesource.is(DamageTypes.MAGIC) && !damagesource.is(DamageTypes.EXPLOSION) && !damagesource.is(DamageTypes.ON_FIRE) && !damagesource.is(DamageTypes.IN_FIRE) && !damagesource.is(DamageTypes.FALL)) {
                boolean flag = false;
                Entity entity = damagesource.getEntity();

                if (entity != null) {
                    Vec3 vec3 = entity.position();
                    Vec3 vec31 = ((ServerPlayer) ((ServerPlayerPatch) pre.getPlayerPatch()).getOriginal()).getViewVector(1.0F);
                    Vec3 vec32 = vec3.subtract(((ServerPlayer) ((ServerPlayerPatch) pre.getPlayerPatch()).getOriginal()).getEyePosition()).normalize();

                    if (vec32.dot(vec31) > 0.0D) {
                        flag = true;
                    }
                }

                CapabilityItem capabilityitem = EpicFightCapabilities.getItemStackCapability(serverplayer.getMainHandItem());
                List<AnimationManager.AnimationAccessor<? extends AttackAnimation>> list = capabilityitem.getAutoAttackMotion(pre.getPlayerPatch());
                LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

                if (flag && list.contains(dynamicanimation) || dynamicanimation == AVAnimations.DUAL_SWORD_DANCING_EDGE) {
                    pre.setCanceled(true);
                    pre.setResult(ResultType.BLOCKED);
                    playerpatch.playSound((SoundEvent) EpicFightSounds.CLASH.get(), -0.05F, 0.1F);
                    if (entity != null) {
                        entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * -0.2D, 0.0D, entity.getLookAngle().z * -0.2D));
                    }

                    serverplayer.setDeltaMovement(new Vec3(serverplayer.getLookAngle().x * -0.2D, 0.0D, serverplayer.getLookAngle().z * -0.2D));
                    if (serverplayer.level() instanceof ServerLevel serverLevel) {
                        ((HitParticleType) EpicFightParticles.HIT_BLUNT.get()).spawnParticleWithArgument(serverLevel, HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, serverplayer, damagesource.getEntity());
                    }

                    ItemStack itemstack = ((Player) playerpatch.getOriginal()).getMainHandItem();
                    ItemStack itemstack1 = serverplayer.getOffhandItem();

                    if (!serverplayer.level().isClientSide() && serverplayer.getServer() != null) {
                        try {
                            serverplayer.getServer().getCommands().getDispatcher().execute("execute at @s run particle annoyingvillagers:spark ^ ^1.5 ^0.8 0 0 0 0.1 100", serverplayer.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {

                        }
                    }

                    try {
                        serverplayer.getServer().getCommands().getDispatcher().execute("impactful @s shake 15 10 10", serverplayer.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {

                    }
                    if (itemstack.isDamageableItem()) {
                        int i = itemstack.getDamageValue();
                        int j = itemstack.getMaxDamage();
                        int k = (int) ((float) i + f);

                        itemstack.setDamageValue(Math.min(k, j));
                        itemstack1.setDamageValue(Math.min(k, j));
                    }

                    Entity entity1 = damagesource.getEntity();

                    if (flag && list.contains(dynamicanimation) && entity1 instanceof LivingEntity) {
                        LivingEntity livingentity = (LivingEntity) entity1;
                    }
                }
            }

        });
    }

    @Override
    public void onRemoved(SkillContainer skillcontainer) {
        super.onRemoved(skillcontainer);
        skillcontainer.getExecutor().getEventListener().removeListener(EventType.TAKE_DAMAGE_EVENT_ATTACK, ClashSkill.EVENT_UUID);
        skillcontainer.getExecutor().getEventListener().removeListener(EventType.MODIFY_ATTACK_SPEED_EVENT, ClashSkill.EVENT_UUID);
    }
}