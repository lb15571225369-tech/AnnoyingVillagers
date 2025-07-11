//package com.pla.annoyingvillagers.module.efdg.gameasset;
//
//import java.util.Random;
//
//import com.pla.annoyingvillagers.AnnoyingVillagers;
//import com.pla.annoyingvillagers.module.efdg.animation.BasicMultipleAttackAnimation;
//import com.pla.annoyingvillagers.module.efdg.animation.SpecialAttackAnimation;
//import com.pla.annoyingvillagers.module.efdg.skill.EarthquakeSkill;
//import net.minecraft.core.BlockPos;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.sounds.SoundSource;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.BushBlock;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.phys.Vec3;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
//import yesman.epicfight.api.animation.Joint;
//import yesman.epicfight.api.animation.property.AnimationEvent.AnimationEventConsumer;
//import yesman.epicfight.api.animation.property.AnimationEvent.Side;
//import yesman.epicfight.api.animation.property.AnimationEvent.TimeStampedEvent;
//import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
//import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
//import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
//import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
//import yesman.epicfight.api.animation.types.AttackAnimation.Phase;
//import yesman.epicfight.api.animation.types.MovementAnimation;
//import yesman.epicfight.api.animation.types.StaticAnimation;
//import yesman.epicfight.api.collider.Collider;
//import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
//import yesman.epicfight.api.utils.LevelUtil;
//import yesman.epicfight.api.utils.TimePairList;
//import yesman.epicfight.api.utils.math.OpenMatrix4f;
//import yesman.epicfight.api.utils.math.ValueModifier;
//import yesman.epicfight.api.utils.math.Vec3f;
//import yesman.epicfight.gameasset.Armatures;
//import yesman.epicfight.gameasset.EpicFightSounds;
//import yesman.epicfight.model.armature.HumanoidArmature;
//import yesman.epicfight.particle.EpicFightParticles;
//import yesman.epicfight.skill.SkillSlots;
//import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
//import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
//import yesman.epicfight.world.damagesource.StunType;
//
//@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
//public class EFAnimations {
//
//    public static StaticAnimation GREATSWORD_TWOHAND_AUTO_1;
//    public static StaticAnimation GREATSWORD_TWOHAND_AUTO_2;
//    public static StaticAnimation GREATSWORD_TWOHAND_AUTO_3;
//    public static StaticAnimation GREATSWORD_DUAL_AUTO_1;
//    public static StaticAnimation GREATSWORD_DUAL_AUTO_2;
//    public static StaticAnimation GREATSWORD_DUAL_AUTO_3;
//    public static StaticAnimation GREATSWORD_DUAL_AUTO_4;
//    public static StaticAnimation GREATSWORD_DUAL_DASH;
//    public static StaticAnimation GREATSWORD_DUAL_AIRSLASH;
//    public static StaticAnimation GREATSWORD_DUAL_EARTHQUAKE;
//    public static StaticAnimation GREATSWORD_DUAL_IDLE;
//    public static StaticAnimation GREATSWORD_DUAL_WALK;
//    public static StaticAnimation GREATSWORD_DUAL_RUN;
//
//    @SubscribeEvent
//    public static void registerAnimations(AnimationRegistryEvent animationregistryevent) {
//        animationregistryevent.getRegistryMap().put(AnnoyingVillagers.MODID, EFAnimations::build);
//    }
//
//    private static void build() {
//        HumanoidArmature humanoidarmature = Armatures.BIPED;
//
//        EFAnimations.GREATSWORD_DUAL_AUTO_1 = (new BasicMultipleAttackAnimation(0.25F, "biped/combat/greatsword_dual_auto_1", humanoidarmature, new Phase[]{new Phase(0.0F, 0.2F, 0.4F, 0.45F, 0.45F, InteractionHand.OFF_HAND, humanoidarmature.toolL, (Collider) null), new Phase(0.45F, 0.5F, 0.7F, 0.8F, Float.MAX_VALUE, humanoidarmature.toolR, (Collider) null)})).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F), 1).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F);
//        EFAnimations.GREATSWORD_DUAL_AUTO_2 = (new BasicMultipleAttackAnimation(0.15F, 0.35F, 0.85F, 0.85F, EFColliders.GREATSWORD_DOUBLESWING, humanoidarmature.toolR, "biped/combat/greatsword_dual_auto_2", humanoidarmature)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F)).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.8F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F).addEvents(new TimeStampedEvent[]{TimeStampedEvent.create(0.85F, EFAnimations.ReuseableEvents.GROUNDSLAM_SMALL, Side.CLIENT)});
//        EFAnimations.GREATSWORD_DUAL_AUTO_3 = (new BasicMultipleAttackAnimation(0.15F, "biped/combat/greatsword_dual_auto_3", humanoidarmature, new Phase[]{new Phase(0.0F, 0.2F, 0.4F, 0.45F, 0.45F, humanoidarmature.toolR, (Collider) null), new Phase(0.45F, 0.55F, 0.7F, 0.7F, Float.MAX_VALUE, InteractionHand.OFF_HAND, humanoidarmature.toolL, (Collider) null)})).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F), 1).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F), 1).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F).addEvents(new TimeStampedEvent[]{TimeStampedEvent.create(0.45F, EFAnimations.ReuseableEvents.GROUNDSLAM_SMALL, Side.CLIENT)});
//        EFAnimations.GREATSWORD_DUAL_AUTO_4 = (new BasicMultipleAttackAnimation(0.1F, 0.8F, 1.0F, 1.25F, InteractionHand.OFF_HAND, EFColliders.GREATSWORD_DUAL, humanoidarmature.rootJoint, "biped/combat/greatsword_dual_auto_4", humanoidarmature)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.8F)).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.75F);
//        EFAnimations.GREATSWORD_DUAL_DASH = (new BasicMultipleAttackAnimation(0.05F, 0.1F, 0.4F, 0.4F, EFColliders.SHOULDER_BUMP, humanoidarmature.rootJoint, "biped/combat/greatsword_dual_dash", humanoidarmature)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F)).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F)).addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT).addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, false).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F);
//        EFAnimations.GREATSWORD_DUAL_AIRSLASH = (new BasicMultipleAttackAnimation(0.05F, 0.25F, 0.4F, 0.45F, InteractionHand.OFF_HAND, EFColliders.AIRSLAM, humanoidarmature.rootJoint, "biped/combat/greatsword_dual_airslash", humanoidarmature)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.8F)).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F).addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false).addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(new float[]{0.0F, 0.2F})).addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicanimation, livingentitypatch, f, f1) -> {
//            if (f1 >= 0.2F && f1 < 0.35F) {
//                float f2 = (float) ((LivingEntity) livingentitypatch.getOriginal()).getX();
//                float f3 = (float) ((LivingEntity) livingentitypatch.getOriginal()).getY();
//                float f4 = (float) ((LivingEntity) livingentitypatch.getOriginal()).getZ();
//
//                for (BlockState blockstate = ((LivingEntity) livingentitypatch.getOriginal()).level.getBlockState(new BlockPos(new Vec3((double) f2, (double) f3, (double) f4))); (blockstate.getBlock() instanceof BushBlock || blockstate.isAir()) && !blockstate.is(Blocks.VOID_AIR); blockstate = ((LivingEntity) livingentitypatch.getOriginal()).level.getBlockState(new BlockPos(new Vec3((double) f2, (double) f3, (double) f4)))) {
//                    --f3;
//                }
//
//                float f5 = (float) Math.max(Math.abs(((LivingEntity) livingentitypatch.getOriginal()).getY() - (double) f3) - 1.0D, 0.0D);
//
//                return 1.0F - (1.0F / (-f5 - 1.0F) + 1.0F);
//            } else {
//                return 1.0F;
//            }
//        }).addEvents(new TimeStampedEvent[]{TimeStampedEvent.create(0.4F, EFAnimations.ReuseableEvents.GROUNDSLAM_SMALL, Side.CLIENT)});
//        EFAnimations.GREATSWORD_DUAL_EARTHQUAKE = (new SpecialAttackAnimation(0.15F, "biped/skill/greatsword_dual_earthquake", humanoidarmature, new Phase[]{new Phase(0.0F, 1.1F, 1.1F, 1.25F, 1.25F, humanoidarmature.toolR, EFColliders.GREATSWORD_DOUBLESWING), new Phase(1.25F, 1.3F, 1.4F, 1.5F, Float.MAX_VALUE, humanoidarmature.rootJoint, EFColliders.GREATSWORD_DUAL)})).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.4F), 1).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F), 1).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT, 1).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F).addEvents(new TimeStampedEvent[]{TimeStampedEvent.create(1.25F, EFAnimations.ReuseableEvents.GROUNDSLAM_SMALL, Side.CLIENT), TimeStampedEvent.create(1.45F, (livingentitypatch, staticanimation, aobject) -> {
//                    ((PlayerPatch) livingentitypatch).getSkill(SkillSlots.WEAPON_INNATE).getDataManager().setDataSync(EarthquakeSkill.SUPERARMOR, false, (ServerPlayer) livingentitypatch.getOriginal());
//                }, Side.SERVER)});
//        EFAnimations.GREATSWORD_DUAL_IDLE = new StaticAnimation(0.1F, true, "biped/living/greatsword_dual_idle", humanoidarmature);
//        EFAnimations.GREATSWORD_DUAL_WALK = new MovementAnimation(0.1F, true, "biped/living/greatsword_dual_walk", humanoidarmature);
//        EFAnimations.GREATSWORD_DUAL_RUN = new MovementAnimation(0.1F, true, "biped/living/greatsword_dual_run", humanoidarmature);
//    }
//
//    private static class ReuseableEvents {
//
//        public static final AnimationEventConsumer GROUNDSLAM_SMALL = (livingentitypatch, staticanimation, aobject) -> {
//            Vec3 vec3 = ((LivingEntity) livingentitypatch.getOriginal()).position();
//            OpenMatrix4f openmatrix4f = livingentitypatch.getArmature().getBindedTransformFor(livingentitypatch.getArmature().getPose(1.0F), Armatures.BIPED.toolR).mulFront(OpenMatrix4f.createTranslation((float) vec3.x, (float) vec3.y, (float) vec3.z).mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS).mulBack(livingentitypatch.getModelMatrix(1.0F))));
//            Vec3 vec31 = OpenMatrix4f.transform(openmatrix4f, (new Vec3f(0.0F, 0.0F, -1.4F)).toDoubleVector());
//            Level level = ((LivingEntity) livingentitypatch.getOriginal()).level;
//            Vec3 vec32 = getfloor(livingentitypatch, staticanimation, new Vec3f(0.0F, 0.0F, -1.4F), Armatures.BIPED.toolR);
//            BlockState blockstate = ((LivingEntity) livingentitypatch.getOriginal()).level.getBlockState(new BlockPos(vec32));
//
//            if (livingentitypatch instanceof PlayerPatch) {
//                ((LivingEntity) livingentitypatch.getOriginal()).level.playSound((Player) livingentitypatch.getOriginal(), livingentitypatch.getOriginal(), blockstate.is(Blocks.WATER) ? SoundEvents.GENERIC_SPLASH : EpicFightSounds.GROUND_SLAM, SoundSource.PLAYERS, 1.5F, 1.5F - ((new Random()).nextFloat() - 0.5F) * 0.2F);
//            }
//
//            vec31 = new Vec3(vec31.x, vec32.y, vec31.z);
//            LevelUtil.circleSlamFracture((LivingEntity) livingentitypatch.getOriginal(), level, vec31, 2.0D, true, false);
//        };
//
//        public static Vec3 getfloor(LivingEntityPatch<?> livingentitypatch, StaticAnimation staticanimation, Vec3f vec3f, Joint joint) {
//            OpenMatrix4f openmatrix4f = livingentitypatch.getArmature().getBindedTransformFor(livingentitypatch.getArmature().getPose(1.0F), joint);
//
//            openmatrix4f.translate(vec3f);
//            OpenMatrix4f openmatrix4f1 = (new OpenMatrix4f()).rotate(-((float) Math.toRadians((double) (((LivingEntity) livingentitypatch.getOriginal()).yRotO + 180.0F))), new Vec3f(0.0F, 1.0F, 0.0F));
//
//            OpenMatrix4f.mul(openmatrix4f1, openmatrix4f, openmatrix4f);
//            float f = openmatrix4f.m30 + (float) ((LivingEntity) livingentitypatch.getOriginal()).getX();
//            float f1 = openmatrix4f.m31 + (float) ((LivingEntity) livingentitypatch.getOriginal()).getY();
//            float f2 = openmatrix4f.m32 + (float) ((LivingEntity) livingentitypatch.getOriginal()).getZ();
//
//            for (BlockState blockstate = ((LivingEntity) livingentitypatch.getOriginal()).level.getBlockState(new BlockPos(new Vec3((double) f, (double) f1, (double) f2))); (blockstate.getBlock() instanceof BushBlock || blockstate.isAir()) && !blockstate.is(Blocks.VOID_AIR); blockstate = ((LivingEntity) livingentitypatch.getOriginal()).level.getBlockState(new BlockPos(new Vec3((double) f, (double) f1, (double) f2)))) {
//                --f1;
//            }
//
//            return new Vec3((double) f, (double) f1, (double) f2);
//        }
//    }
//}
