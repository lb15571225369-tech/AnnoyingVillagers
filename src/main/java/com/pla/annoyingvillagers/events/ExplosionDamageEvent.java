package com.pla.annoyingvillagers.events;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.item.EnderGlaiveItem;
import com.pla.annoyingvillagers.item.WoopieTheSwordItem;
import com.pla.annoyingvillagers.skill.EnderGlaiveSkill;
import com.pla.annoyingvillagers.skill.WoopieTheSwordSkill;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.animations.weapons.AnimsAgony;
import reascer.wom.gameasset.animations.weapons.AnimsHerrscher;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Objects;
import java.util.Random;

@Mod.EventBusSubscriber
public class ExplosionDamageEvent {
    @SubscribeEvent
    public static void onExplode(ExplosionEvent.Detonate detonate) {
        LivingEntity livingEntity = detonate.getExplosion().getIndirectSourceEntity();
        final Vec3 center = detonate.getExplosion().getPosition();

        if (livingEntity != null && livingEntity.isAlive()) {
            LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(livingEntity, LivingEntityPatch.class);
            if (livingEntityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                AssetAccessor<? extends DynamicAnimation> dynamicAnimation = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getAnimation();
                if (livingEntity.getMainHandItem().getItem() instanceof EnderGlaiveItem && dynamicAnimation == AnimsAgony.AGONY_AUTO_1) {
                    SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.ENDER_GLAIVE);
                    if (skillContainer != null) {
                        if (skillContainer.getStack() < 3) {
                            EnderGlaiveSkill enderGlaiveSkill = (EnderGlaiveSkill) skillContainer.getSkill();
                            float currentResource = skillContainer.getResource();
                            float neededResource = skillContainer.getNeededResource();
                            float addResource = Math.min(50.0F, neededResource);
                            enderGlaiveSkill.setConsumptionSynchronize(skillContainer, currentResource + addResource);
                        }
                        for (Entity entity : detonate.getAffectedEntities()) {
                            if (entity != detonate.getExplosion().getIndirectSourceEntity()) {
                                LivingEntityPatch<?> explodedPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                if (explodedPatch != null) {
                                    explodedPatch.playAnimationSynchronized(AVAnimations.GUARD_BREAK_ATTACK, 0.0F);
                                }
                            }
                        }
                    }
                }

                if (livingEntity.getMainHandItem().getItem() instanceof WoopieTheSwordItem && dynamicAnimation == AnimsHerrscher.HERRSCHER_AUTO_2) {
                    SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.WOOPIE_THE_SWORD);
                    if (skillContainer != null) {
                        if (skillContainer.getStack() < 1) {
                            WoopieTheSwordSkill woopieTheSwordSkill = (WoopieTheSwordSkill) skillContainer.getSkill();
                            float currentResource = skillContainer.getResource();
                            float neededResource = skillContainer.getNeededResource();
                            float addResource = Math.min(50.0F, neededResource);
                            woopieTheSwordSkill.setConsumptionSynchronize(skillContainer, currentResource + addResource);
                        }
                        for (Entity entity : detonate.getAffectedEntities()) {
                            if (entity != detonate.getExplosion().getIndirectSourceEntity() && entity instanceof LivingEntity living) {
                                LivingEntityPatch<?> explodedPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                if (explodedPatch != null) {
                                    explodedPatch.playAnimationSynchronized(AVAnimations.LONGEST_HIT, 0.0F);
                                }
                                if (!living.isAlive()) continue;
                                double dx = center.x - living.getX();
                                double dz = center.z - living.getZ();
                                double dist = living.position().distanceTo(center);
                                double falloff = Mth.clamp(1.0D - (dist / 8.0D), 0.0D, 1.0D);

                                double horizontal = 6.0D * falloff;
                                double up = 2.6D * falloff;

                                living.knockback(horizontal, dx, dz);
                                living.push(0.0D, up, 0.0D);
                                living.hurtMarked = true;
                            }
                        }
                    }
                }
            }
        }
    }
}
