package com.pla.annoyingvillagers.compat.efdg.animation;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Keyframe;
import yesman.epicfight.api.animation.TransformSheet;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.animation.property.MoveCoordFunctions;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.EntityState.StateFactor;
import yesman.epicfight.api.client.animation.property.ClientAnimationProperties;
import yesman.epicfight.api.client.animation.property.JointMaskEntry;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.HitEntityList;
import yesman.epicfight.api.utils.HitEntityList.Priority;
import yesman.epicfight.api.utils.datastruct.TypeFlexibleHashMap;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.HurtableEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.effect.EpicFightMobEffects;
import yesman.epicfight.world.entity.eventlistener.DealtDamageEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;
import yesman.epicfight.world.gamerule.EpicFightGamerules;

public class BasicMultipleAttackAnimation extends AttackAnimation {
    void init() {
        if (!this.properties.containsKey(AttackAnimationProperty.BASIS_ATTACK_SPEED)) {
            float f = Float.parseFloat(String.format(Locale.US, "%.2f", 1.0F / this.getTotalTime()));
            this.addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, f);
        }
        JointMaskEntry jointMask = JointMaskEntry.builder()
                .defaultMask(JointMaskEntry.BIPED_UPPER_JOINTS_WITH_ROOT)
                .create();

        this.addProperty(ClientAnimationProperties.JOINT_MASK, jointMask);
    }

    public BasicMultipleAttackAnimation(float f, float f1, float f2, float f3, @Nullable Collider collider, Joint joint, String s, Armature armature) {
        this(f, f1, f1, f2, f3, collider, joint, s, armature);
    }

    public BasicMultipleAttackAnimation(float f, float f1, float f2, float f3, float f4, @Nullable Collider collider, Joint joint, String s, Armature armature) {
        this(f, s, armature, new Phase(0.0F, f1, f2, f3, f4, Float.MAX_VALUE, joint, collider));
    }

    public BasicMultipleAttackAnimation(float f, float f1, float f2, float f3, InteractionHand interactionhand, @Nullable Collider collider, Joint joint, String s, Armature armature) {
        this(f, s, armature, new Phase(0.0F, f1, f1, f2, f3, Float.MAX_VALUE, interactionhand, joint, collider));
    }

    public BasicMultipleAttackAnimation(float f, String s, Armature armature, boolean flag, Phase... aphase) {
        super(f, s, armature, aphase);
        init();
    }

    public BasicMultipleAttackAnimation(float f, String s, Armature armature, Phase... aphase) {
        super(f, s, armature, aphase);
        init();
        this.newTimePair(0.0F, Float.MAX_VALUE);
        this.addStateRemoveOld(EntityState.TURNING_LOCKED, false);
        this.addProperty(ActionAnimationProperty.COORD_SET_BEGIN, MoveCoordFunctions.TRACE_LOC_TARGET);
        this.addProperty(ActionAnimationProperty.COORD_SET_TICK, (dynamicanimation, livingentitypatch, transformsheet) -> {
            LivingEntity livingentity = livingentitypatch.getTarget();

            if (!(Boolean) dynamicanimation.getRealAnimation().getProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE).orElse(false) && livingentity != null) {
                TransformSheet transformsheet1 = ((TransformSheet) dynamicanimation.getTransfroms().get("Root")).copyAll();
                Keyframe[] akeyframe = transformsheet1.getKeyframes();
                byte b0 = 0;
                int i = transformsheet1.getKeyframes().length - 1;
                Vec3f vec3f = akeyframe[i].transform().translation();
                Vec3 vec3 = ((LivingEntity) livingentitypatch.getOriginal()).getEyePosition();
                Vec3 vec31 = livingentity.position();
                float f1 = Math.max((float) vec31.subtract(vec3).horizontalDistance() * 1.75F - (livingentity.getBbWidth() + ((LivingEntity) livingentitypatch.getOriginal()).getBbWidth()) * 0.75F, 0.0F);
                Vec3f vec3f1 = new Vec3f(vec3f.x, 0.0F, -f1);
                float f2 = Math.min(vec3f1.length() / vec3f.length(), 2.0F);

                for (int j = b0; j <= i; ++j) {
                    Vec3f vec3f2 = akeyframe[j].transform().translation();

                    vec3f2.z *= f2;
                }

                transformsheet.readFrom(transformsheet1);
            } else {
                transformsheet.readFrom((TransformSheet) dynamicanimation.getTransfroms().get("Root"));
            }

        });
    }

    protected void hurtCollidingEntities(LivingEntityPatch<?> livingentitypatch, float f, float f1, EntityState entitystate, EntityState entitystate1, Phase phase) {
        LivingEntity livingentity = (LivingEntity) livingentitypatch.getOriginal();

        livingentitypatch.getArmature().getRootJoint().initOriginTransform(new OpenMatrix4f());
        float f2 = entitystate.attacking() ? f : phase.preDelay;
        float f3 = entitystate1.attacking() ? f1 : phase.contact;
        List<Entity> list = this.getPhaseByTime(f1).getCollidingEntities(livingentitypatch, this, f2, f3, this.getPlaySpeed(livingentitypatch, this));

        if (list.size() > 0) {
            HitEntityList hitentitylist = new HitEntityList(livingentitypatch, list, (Priority) phase.getProperty(AttackPhaseProperty.HIT_PRIORITY).orElse(Priority.DISTANCE));
            int i = this.getMaxStrikes(livingentitypatch, phase);

            while (livingentitypatch.getCurrenltyHurtEntities().size() < i && hitentitylist.next()) {
                Entity entity = hitentitylist.getEntity();
                LivingEntity livingentity1 = this.getTrueEntity(entity);

                if (livingentity1 != null && livingentity1.isAlive() && !livingentitypatch.getCurrenltyAttackedEntities().contains(livingentity1) && !livingentitypatch.isTeammate(entity) && (entity instanceof LivingEntity || entity instanceof PartEntity) && livingentity.hasLineOfSight(entity)) {
                    HurtableEntityPatch<?> hurtableentitypatch = (HurtableEntityPatch) EpicFightCapabilities.getEntityPatch(entity, HurtableEntityPatch.class);

                    if (hurtableentitypatch == null) {
                        break;
                    }

                    EpicFightDamageSource epicfightdamagesource = this.getEpicFightDamageSource(livingentitypatch, entity, phase);
                    float f4 = 1.0F;

                    if (hurtableentitypatch != null) {
                        if (phase.getProperty(AttackPhaseProperty.STUN_TYPE).isPresent()) {
                            if (phase.getProperty(AttackPhaseProperty.STUN_TYPE).get() == StunType.NONE) {
                                if (livingentity1 instanceof Player) {
                                    epicfightdamagesource.setStunType(StunType.LONG);
                                    epicfightdamagesource.setImpact(epicfightdamagesource.getImpact() * 5.0F);
                                } else {
                                    epicfightdamagesource.setStunType(StunType.NONE);
                                }
                            } else if (phase.getProperty(AttackPhaseProperty.STUN_TYPE).get() == StunType.HOLD && ((LivingEntity) hurtableentitypatch.getOriginal()).hasEffect((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get())) {
                                epicfightdamagesource.setStunType(StunType.NONE);
                            } else if (phase.getProperty(AttackPhaseProperty.STUN_TYPE).get() == StunType.FALL && ((LivingEntity) hurtableentitypatch.getOriginal()).hasEffect((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get())) {
                                epicfightdamagesource.setStunType(StunType.NONE);
                            } else {
                                epicfightdamagesource = this.getEpicFightDamageSource(livingentitypatch, entity, phase);
                            }
                        } else {
                            epicfightdamagesource = this.getEpicFightDamageSource(livingentitypatch, entity, phase);
                        }

                        String s = "anti_stunlock:" + f4 + ":" + entity.tickCount + ":" + this.getId() + "-" + phase.contact;
                        Iterator iterator;
                        String s1;

                        if (hurtableentitypatch.isStunned()) {
                            iterator = entity.getTags().iterator();

                            while (iterator.hasNext()) {
                                s1 = (String) iterator.next();
                                if (s1.contains("anti_stunlock:")) {
                                    f4 = this.applyAntiStunLock(entity, f4, epicfightdamagesource, phase, s1, s);
                                    break;
                                }
                            }
                        } else {
                            boolean flag = true;
                            Iterator iterator1 = entity.getTags().iterator();

                            while (iterator1.hasNext()) {
                                String s2 = (String) iterator1.next();

                                if (s2.contains("anti_stunlock:")) {
                                    if ((float) entity.tickCount - Float.valueOf(s2.split(":")[2]) > 20.0F) {
                                        f4 = 1.0F;
                                    } else {
                                        f4 = this.applyAntiStunLock(entity, f4, epicfightdamagesource, phase, s2, s);
                                        flag = false;
                                    }
                                    break;
                                }
                            }

                            if (flag) {
                                int j = 0;

                                while (j < entity.getTags().size()) {
                                    if (((String) entity.getTags().toArray()[j]).contains("anti_stunlock:")) {
                                        entity.getTags().remove(entity.getTags().toArray()[j]);
                                    } else {
                                        ++j;
                                    }
                                }

                                entity.addTag(s);
                            }
                        }

                        if (f4 < (entity instanceof Player ? 0.3F : 0.2F)) {
                            iterator = entity.getTags().iterator();

                            while (iterator.hasNext()) {
                                s1 = (String) iterator.next();
                                if (s1.contains("anti_stunlock:")) {
                                    entity.removeTag(s1);
                                    break;
                                }
                            }

                            epicfightdamagesource.setStunType(StunType.KNOCKDOWN);
                        }

                        epicfightdamagesource.setImpact(epicfightdamagesource.getImpact() * f4);
                    }

                    int k = entity.invulnerableTime;

                    entity.invulnerableTime = 0;
                    AttackResult attackresult = livingentitypatch.attack(epicfightdamagesource, entity, phase.hand);

                    entity.invulnerableTime = k;
                    if (attackresult.resultType.dealtDamage()) {
                        if (livingentitypatch instanceof ServerPlayerPatch) {
                            ServerPlayerPatch serverplayerpatch = (ServerPlayerPatch) livingentitypatch;
                            LivingDamageEvent damageEvent = new LivingDamageEvent(livingentity1, epicfightdamagesource, attackresult.damage);
                            serverplayerpatch.getEventListener().triggerEvents(EventType.DEALT_DAMAGE_EVENT_DAMAGE, new DealtDamageEvent.Damage(serverplayerpatch, livingentity1, epicfightdamagesource, damageEvent));
                        }

                        if (epicfightdamagesource.getStunType() == StunType.KNOCKDOWN) {
                            ((LivingEntity) hurtableentitypatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 60, 0, true, false, false));
                        }

                        entity.level().playSound((Player) null, entity.getX(), entity.getY(), entity.getZ(), this.getHitSound(livingentitypatch, phase), entity.getSoundSource(), 1.0F, 1.0F);
                        this.spawnHitParticle((ServerLevel) entity.level(), livingentitypatch, entity, phase);
                        if (hurtableentitypatch != null && phase.getProperty(AttackPhaseProperty.STUN_TYPE).isPresent() && !((LivingEntity) hurtableentitypatch.getOriginal()).hasEffect((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get())) {
                            float f5;

                            if (phase.getProperty(AttackPhaseProperty.STUN_TYPE).get() == StunType.NONE && !(livingentity1 instanceof Player)) {
                                f5 = (float) ((double) (epicfightdamagesource.getImpact() * f4 * 0.2F) * (1.0D - livingentity1.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)));
                                if (((LivingEntity) hurtableentitypatch.getOriginal()).isAlive()) {
                                    hurtableentitypatch.applyStun(f4 > 0.3F ? StunType.LONG : StunType.KNOCKDOWN, f5);
                                    float f6 = epicfightdamagesource.getImpact() / f4 * 0.25F;
                                    double d0 = livingentity.getX() - entity.getX();

                                    double d1;

                                    for (d1 = livingentity.getZ() - entity.getZ(); d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D) {
                                        d0 = (Math.random() - Math.random()) * 0.01D;
                                    }

                                    if (!(livingentity1 instanceof Player)) {
                                        f6 = (float) ((double) f6 * (1.0D - livingentity1.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)));
                                    }

                                    if ((double) f6 > 0.0D) {
                                        entity.hasImpulse = true;
                                        Vec3 vec3 = entity.getDeltaMovement();
                                        Vec3 vec31 = (new Vec3(d0, 0.0D, d1)).normalize().scale((double) f6);

                                        entity.setDeltaMovement(vec3.x / 2.0D - vec31.x, entity.onGround() ? Math.min(0.4D, vec3.y / 2.0D) : vec3.y, vec3.z / 2.0D - vec31.z);
                                    }
                                }
                            }

                            if (phase.getProperty(AttackPhaseProperty.STUN_TYPE).get() == StunType.FALL) {
                                f5 = (float) ((double) (epicfightdamagesource.getImpact() * 0.4F) * (1.0D - livingentity1.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)));
                                if (((LivingEntity) hurtableentitypatch.getOriginal()).isAlive()) {
                                    hurtableentitypatch.applyStun(f4 > 0.3F ? StunType.SHORT : StunType.KNOCKDOWN, f5);
                                    double d2 = (double) (epicfightdamagesource.getImpact() / f4 * 0.25F);
                                    double d3 = livingentity.getX() - entity.getX();
                                    double d4 = livingentity.getY() - 8.0D - entity.getY();

                                    double d5;

                                    for (d5 = livingentity.getZ() - entity.getZ(); d3 * d3 + d5 * d5 < 1.0E-4D; d5 = (Math.random() - Math.random()) * 0.01D) {
                                        d3 = (Math.random() - Math.random()) * 0.01D;
                                    }

                                    if (!(livingentity1 instanceof Player)) {
                                        d2 *= 1.0D - livingentity1.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
                                    }

                                    if (d2 > 0.0D) {
                                        entity.hasImpulse = true;
                                        Vec3 vec32 = livingentity.getDeltaMovement();
                                        Vec3 vec33 = (new Vec3(d3, d4, d5)).normalize().scale(d2);

                                        entity.setDeltaMovement(vec32.x / 2.0D - vec33.x, vec32.y / 2.0D - vec33.y, vec32.z / 2.0D - vec33.z);
                                    }

                                    if (livingentity1 instanceof Player) {
                                        livingentity1.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 5, (int) (d2 * 4.0D * 6.0D), true, false, false));
                                    }
                                }
                            }
                        }
                    }

                    livingentitypatch.getCurrenltyAttackedEntities().add(livingentity1);
                    if (attackresult.resultType.shouldCount()) {
                        livingentitypatch.getCurrenltyHurtEntities().add(livingentity1);
                    }
                }
            }
        }

    }

    public void end(LivingEntityPatch<?> livingentitypatch, DynamicAnimation dynamicanimation, boolean flag) {
        super.end(livingentitypatch, dynamicanimation, flag);
        boolean flag1 = ((BooleanValue) ((LivingEntity) livingentitypatch.getOriginal()).level().getGameRules().getRule(EpicFightGamerules.STIFF_COMBO_ATTACKS)).get();

        if (!flag && !dynamicanimation.isMainFrameAnimation() && livingentitypatch.isLogicalClient() && !flag1) {
            float f = 0.05F * this.getPlaySpeed(livingentitypatch, dynamicanimation);

            livingentitypatch.getClientAnimator().baseLayer.copyLayerTo(livingentitypatch.getClientAnimator().baseLayer.getLayer(yesman.epicfight.api.client.animation.Layer.Priority.HIGHEST), f);
        }

    }

    public TypeFlexibleHashMap<StateFactor<?>> getStatesMap(LivingEntityPatch<?> livingentitypatch, float f) {
        TypeFlexibleHashMap<StateFactor<?>> typeflexiblehashmap = super.getStatesMap(livingentitypatch, f);

        if (!((BooleanValue) ((LivingEntity) livingentitypatch.getOriginal()).level().getGameRules().getRule(EpicFightGamerules.STIFF_COMBO_ATTACKS)).get()) {
            typeflexiblehashmap.put(EntityState.MOVEMENT_LOCKED, Optional.of(false));
        }

        return typeflexiblehashmap;
    }

    public Vec3 getCoordVector(LivingEntityPatch<?> livingentitypatch, DynamicAnimation dynamicanimation) {
        Vec3 vec3 = super.getCoordVector(livingentitypatch, dynamicanimation);

        if (livingentitypatch.shouldBlockMoving() && (Boolean) this.getProperty(ActionAnimationProperty.CANCELABLE_MOVE).orElse(false)) {
            vec3 = vec3.scale(0.0D);
        }

        return vec3;
    }

    public boolean isBasicAttackAnimation() {
        return true;
    }

    public float applyAntiStunLock(Entity entity, float f, EpicFightDamageSource epicfightdamagesource, Phase phase, String s, String s1) {
        boolean flag = false;
        String s2;
        String s3;
        int i;

        if (entity.level().getBlockState(new BlockPos(new Vec3i((int) entity.getX(), (int) entity.getY() - 1, (int) entity.getZ()))).isAir() && epicfightdamagesource.getStunType() != StunType.FALL) {
            s2 = String.valueOf(this.getId());
            s3 = s2 + "-" + String.valueOf(phase.contact);
            if (s.split(":").length > 3) {
                if (String.valueOf(this.getId()).equals(s.split(":")[3].split("-")[0]) && !String.valueOf(phase.contact).equals(s.split(":")[3].split("-")[1])) {
                    f = Float.valueOf(s.split(":")[1]) * 0.98F;
                    flag = true;
                } else {
                    f = Float.valueOf(s.split(":")[1]) * 0.9F;
                    flag = false;
                }
            }

            for (i = 3; i < s.split(":").length && i < 7; ++i) {
                if (s.split(":")[i].equals(s3)) {
                    f *= 0.6F;
                }
            }
        } else {
            s2 = String.valueOf(this.getId());
            s3 = s2 + "-" + String.valueOf(phase.contact);
            if (s.split(":").length > 3) {
                if (String.valueOf(this.getId()).equals(s.split(":")[3].split("-")[0]) && !String.valueOf(phase.contact).equals(s.split(":")[3].split("-")[1])) {
                    f = Float.valueOf(s.split(":")[1]) * 0.98F;
                    flag = true;
                } else {
                    f = Float.valueOf(s.split(":")[1]) * 0.8F;
                    flag = false;
                }
            }

            for (i = 3; i < s.split(":").length && i < 5; ++i) {
                if (s.split(":")[i].equals(s3)) {
                    f *= 0.6F;
                }
            }
        }

        entity.removeTag(s);
        boolean flag1 = true;
        byte b0;

        if (flag) {
            s1 = "anti_stunlock:" + f + ":" + entity.tickCount;
            b0 = 6;
        } else {
            s1 = "anti_stunlock:" + f + ":" + entity.tickCount + ":" + this.getId() + "-" + phase.contact;
            b0 = 5;
        }

        for (i = 3; i < s.split(":").length && i < b0; ++i) {
            s1 = s1.concat(":" + s.split(":")[i]);
        }

        entity.addTag(s1);
        return f;
    }
}
