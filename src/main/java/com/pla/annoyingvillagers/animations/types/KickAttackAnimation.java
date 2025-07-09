package com.pla.annoyingvillagers.animations.types;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Keyframe;
import yesman.epicfight.api.animation.TransformSheet;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.MoveCoordFunctions;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation.Phase;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.EntityState.StateFactor;
import yesman.epicfight.api.client.animation.Layer.Priority;
import yesman.epicfight.api.client.animation.property.JointMask;
import yesman.epicfight.api.client.animation.property.JointMask.BindModifier;
import yesman.epicfight.api.client.animation.property.JointMaskEntry;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.TypeFlexibleHashMap;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.gamerule.EpicFightGamerules;

public class KickAttackAnimation extends AttackAnimation {

    public KickAttackAnimation(float f, float f1, float f2, float f3, @Nullable Collider collider, Joint joint, String s, Armature armature) {
        this(f, f1, f1, f2, f3, collider, joint, s, armature);
    }

    public KickAttackAnimation(float f, float f1, float f2, float f3, float f4, @Nullable Collider collider, Joint joint, String s, Armature armature) {
        this(f, s, armature, new Phase(0.0F, f1, f2, f3, f4, Float.MAX_VALUE, joint, collider));
    }

    public KickAttackAnimation(float f, float f1, float f2, float f3, InteractionHand interactionhand, @Nullable Collider collider, Joint joint, String s, Armature armature) {
        this(f, s, armature, new Phase(0.0F, f1, f1, f2, f3, Float.MAX_VALUE, interactionhand, joint, collider));
    }

    public KickAttackAnimation(float f, String s, Armature armature, boolean flag, Phase... aphase) {
        super(f, s, armature, aphase);
    }

    public KickAttackAnimation(float f, String s, Armature armature, Phase... aphase) {
        super(f, s, armature, aphase);
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

    protected void onLoaded() {
        super.onLoaded();
        if (!this.properties.containsKey(AttackAnimationProperty.BASIS_ATTACK_SPEED)) {
            float f = Float.parseFloat(String.format(Locale.US, "%.2f", 1.0F / this.totalTime));

            this.addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, f);
        }

    }

    public void end(LivingEntityPatch<?> livingentitypatch, DynamicAnimation dynamicanimation, boolean flag) {
        super.end(livingentitypatch, dynamicanimation, flag);
        boolean flag1 = ((BooleanValue) ((LivingEntity) livingentitypatch.getOriginal()).level.getGameRules().getRule(EpicFightGamerules.STIFF_COMBO_ATTACKS)).get();

        if (!flag && !dynamicanimation.isMainFrameAnimation() && livingentitypatch.isLogicalClient() && !flag1) {
            float f = 0.05F * this.getPlaySpeed(livingentitypatch);

            livingentitypatch.getClientAnimator().baseLayer.copyLayerTo(livingentitypatch.getClientAnimator().baseLayer.getLayer(Priority.HIGHEST), f);
        }

    }

    public TypeFlexibleHashMap<StateFactor<?>> getStatesMap(LivingEntityPatch<?> livingentitypatch, float f) {
        TypeFlexibleHashMap<StateFactor<?>> typeflexiblehashmap = super.getStatesMap(livingentitypatch, f);

        if (!((BooleanValue) ((LivingEntity) livingentitypatch.getOriginal()).level.getGameRules().getRule(EpicFightGamerules.STIFF_COMBO_ATTACKS)).get()) {
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

    public boolean isJointEnabled(LivingEntityPatch<?> livingentitypatch, Priority priority, String s) {
        return priority == Priority.HIGHEST ? !JointMaskEntry.BASIC_ATTACK_MASK.isMasked(livingentitypatch.getCurrentLivingMotion(), s) : super.isJointEnabled(livingentitypatch, priority, s);
    }

    public BindModifier getBindModifier(LivingEntityPatch<?> livingentitypatch, Priority priority, String s) {
        if (priority == Priority.HIGHEST) {
            List<JointMask> list = JointMaskEntry.BIPED_UPPER_JOINTS_WITH_ROOT;
            int i = list.indexOf(JointMask.of(s));

            return i >= 0 ? ((JointMask) list.get(i)).getBindModifier() : null;
        } else {
            return super.getBindModifier(livingentitypatch, priority, s);
        }
    }

    public boolean isBasicAttackAnimation() {
        return true;
    }

    public float applyAntiStunLock(Entity entity, float f, EpicFightDamageSource epicfightdamagesource, Phase phase, String s, String s1) {
        boolean flag = false;
        String s2;
        String s3;
        int i;

        if (entity.level.getBlockState(new BlockPos(new Vec3(entity.getX(), entity.getY() - 1.0D, entity.getZ()))).isAir() && epicfightdamagesource.getStunType() != StunType.FALL) {
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
