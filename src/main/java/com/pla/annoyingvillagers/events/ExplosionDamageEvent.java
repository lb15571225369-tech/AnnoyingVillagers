package com.pla.annoyingvillagers.events;

import com.pla.annoyingvillagers.entity.ObsidianSledgehammerHitEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.item.EnderGlaiveItem;
import com.pla.annoyingvillagers.item.ObsidianSledgehammerItem;
import com.pla.annoyingvillagers.item.WoopieTheSwordItem;
import com.pla.annoyingvillagers.skill.EnderGlaiveSkill;
import com.pla.annoyingvillagers.skill.WoopieTheSwordSkill;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
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
            if (livingEntityPatch == null) return;
            AssetAccessor<? extends DynamicAnimation> dynamicAnimation = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getAnimation();
            if (livingEntity.getMainHandItem().getItem() instanceof EnderGlaiveItem && dynamicAnimation == AnimsAgony.AGONY_AUTO_1) {
                SkillContainer skillContainer = null;
                if (livingEntityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                    skillContainer = serverPlayerPatch.getSkill(AVSkills.ENDER_GLAIVE);
                }
                for (Entity entity : detonate.getAffectedEntities()) {
                    if (entity != detonate.getExplosion().getIndirectSourceEntity()
                            && !(entity instanceof Projectile) && !(entity instanceof ObsidianSledgehammerHitEntity)) {
                        LivingEntityPatch<?> explodedPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                        if (explodedPatch != null) {
                            explodedPatch.playAnimationSynchronized(AVAnimations.GUARD_BREAK_ATTACK, 0.0F);
                        }
                        new DelayedTask(10) {
                            @Override
                            public void run() {
                                ObsidianSledgehammerItem.spawnObsidianSpike(entity.getX(), entity.getZ(),
                                        entity.getOnPos().getY(), entity.getOnPos().getY() + 0.1,
                                        new Random().nextFloat(0.0F, 180.0F), 0, livingEntity);
                            }
                        };

                        if (skillContainer != null && skillContainer.getStack() < 3) {
                            EnderGlaiveSkill enderGlaiveSkill = (EnderGlaiveSkill) skillContainer.getSkill();
                            float currentResource = skillContainer.getResource();
                            float neededResource = skillContainer.getNeededResource();
                            float addResource = Math.min(50.0F, neededResource);
                            enderGlaiveSkill.setConsumptionSynchronize(skillContainer, currentResource + addResource);
                        }
                    }
                }
            }

            if (livingEntity.getMainHandItem().getItem() instanceof WoopieTheSwordItem && dynamicAnimation == AnimsHerrscher.HERRSCHER_AUTO_2) {
                SkillContainer skillContainer = null;
                if (livingEntityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                    skillContainer = serverPlayerPatch.getSkill(AVSkills.WOOPIE_THE_SWORD);
                }
                for (Entity entity : detonate.getAffectedEntities()) {
                    if (entity != detonate.getExplosion().getIndirectSourceEntity()
                            && !(entity instanceof Projectile) && !(entity instanceof ObsidianSledgehammerHitEntity)) {
                        LivingEntityPatch<?> explodedPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                        if (explodedPatch != null) {
                            explodedPatch.playAnimationSynchronized(AVAnimations.LONGEST_HIT, 0.0F);
                        }
                        if (!entity.isAlive()) continue;
                        double dx = center.x - entity.getX();
                        double dz = center.z - entity.getZ();
                        double dist = entity.position().distanceTo(center);
                        double falloff = Mth.clamp(1.0D - (dist / 8.0D), 0.0D, 1.0D);

                        double horizontal = 6.0D * falloff;
                        double up = 2.6D * falloff;

                        LivingEntity living = (LivingEntity) entity;
                        living.knockback(horizontal, dx, dz);
                        living.push(0.0D, up, 0.0D);
                        living.hurtMarked = true;

                        if (skillContainer != null && skillContainer.getStack() < 1) {
                            WoopieTheSwordSkill woopieTheSwordSkill = (WoopieTheSwordSkill) skillContainer.getSkill();
                            float currentResource = skillContainer.getResource();
                            float neededResource = skillContainer.getNeededResource();
                            float addResource = Math.min(50.0F, neededResource);
                            woopieTheSwordSkill.setConsumptionSynchronize(skillContainer, currentResource + addResource);
                        }
                    }
                }
            }
        }
    }
}
