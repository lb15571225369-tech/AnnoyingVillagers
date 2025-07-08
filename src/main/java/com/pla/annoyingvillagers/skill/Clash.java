package com.pla.annoyingvillagers.skill;

import java.util.List;
import java.util.UUID;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import com.pla.annoyingvillagers.capabilities.AVCategories;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import tictim.paraglider.capabilities.Caps;
import tictim.paraglider.capabilities.PlayerMovement;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.AttackResult.ResultType;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.Skill.Builder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

public class Clash extends PassiveSkill {

    private static final UUID EVENT_UUID = UUID.fromString("b422f7a0-f378-3344-1111-0252ac130003");

    public Clash(Builder<? extends Skill> builder) {
        super(builder);
    }

    public void onInitiate(SkillContainer skillcontainer) {
        super.onInitiate(skillcontainer);
        skillcontainer.getDataManager();
        skillcontainer.getExecuter().getEventListener().addEventListener(EventType.HURT_EVENT_PRE, Clash.EVENT_UUID, (pre) -> {
            PlayerPatch<?> playerpatch = pre.getPlayerPatch();
            ServerPlayer serverplayer = (ServerPlayer) ((ServerPlayerPatch) pre.getPlayerPatch()).getOriginal();
            DamageSource damagesource = (DamageSource) pre.getDamageSource();
            DynamicAnimation dynamicanimation = playerpatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();
            float f = pre.getAmount();

            if ((playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.SWORD || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.AXE || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.LONGSWORD || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.TACHI || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.SPEAR || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.UCHIGATANA || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.GREATSWORD || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.TRIDENT || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.LEGENDARYSWORD || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.HARDGREATSWORD) && !damagesource.isMagic() && !damagesource.isExplosion() && !damagesource.isFire() && !damagesource.isFall()) {
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
                List<StaticAnimation> list = capabilityitem.getAutoAttckMotion(pre.getPlayerPatch());
                LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

                if (flag && list.contains(dynamicanimation) || dynamicanimation == AVAnimations.GIANT_WHIRLWIND_2 || dynamicanimation == AVAnimations.DUAL_SWORD_DANCING_EDGE || dynamicanimation == AVAnimations.SpinningDeath) {
                    pre.setCanceled(true);
                    pre.setResult(ResultType.BLOCKED);
                    playerpatch.playSound(EpicFightSounds.CLASH, -0.05F, 0.1F);
                    if (entity != null) {
                        entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * -0.2D, 0.0D, entity.getLookAngle().z * -0.2D));
                    }

                    serverplayer.setDeltaMovement(new Vec3(serverplayer.getLookAngle().x * -0.2D, 0.0D, serverplayer.getLookAngle().z * -0.2D));
                    PlayerMovement playermovement = (PlayerMovement) serverplayer.getCapability(Caps.playerMovement, (Direction) null).resolve().orElseThrow();

                    if (!playermovement.canAction()) {
                        playerpatch.playAnimationSynchronized(Animations.BIPED_COMMON_NEUTRALIZED, 0.0F);
                        playerpatch.playSound(EpicFightSounds.NEUTRALIZE_MOBS, -0.05F, 0.1F);
                    }

                    if (serverplayer.level instanceof ServerLevel) {
                        ((HitParticleType) EpicFightParticles.HIT_BLUNT.get()).spawnParticleWithArgument((ServerLevel) serverplayer.level, HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, serverplayer, damagesource.getEntity());
                    }

                    ItemStack itemstack = ((Player) playerpatch.getOriginal()).getMainHandItem();
                    ItemStack itemstack1 = serverplayer.getOffhandItem();

                    if (!serverplayer.level.isClientSide() && serverplayer.getServer() != null) {
                        serverplayer.getServer().getCommands().performCommand(serverplayer.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute at @s run particle annoying_villagersbychentu:spark ^ ^1.5 ^0.8 0 0 0 0.1 100");
                    }

                    serverplayer.getServer().getCommands().performCommand(serverplayer.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/impactful @s shake 15 10 10");
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

    public void onRemoved(SkillContainer skillcontainer) {
        super.onRemoved(skillcontainer);
        skillcontainer.getExecuter().getEventListener().removeListener(EventType.HURT_EVENT_PRE, Clash.EVENT_UUID);
        skillcontainer.getExecuter().getEventListener().removeListener(EventType.MODIFY_ATTACK_SPEED_EVENT, Clash.EVENT_UUID);
    }
}
