package com.pla.annoyingvillagers.gameasset;
import java.util.Random;
import java.util.Set;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import com.pla.annoyingvillagers.animations.types.AttackBreakAnimation;
import com.pla.annoyingvillagers.animations.types.HeavyAttackAnimation;
import com.pla.annoyingvillagers.animations.types.KickAttackAnimation;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.animation.attacks.SpecialAttackAnimation;
import reascer.wom.gameasset.ReuseableEvents;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import reascer.wom.particle.WOMParticles;
import reascer.wom.skill.WOMSkillDataKeys;
import reascer.wom.world.damagesources.WOMExtraDamageInstance;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationEvent.Side;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.animation.types.AttackAnimation.Phase;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.utils.HitEntityList.Priority;
import yesman.epicfight.api.utils.LevelUtil;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations.ReusableSources;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.skill.SkillDataKey;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AVAnimations {
    public static AnimationManager.AnimationAccessor<StaticAnimation> EAT_OFFHAND;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DRINK_OFFHAND;
    public static AnimationManager.AnimationAccessor<StaticAnimation> BLOCK_MAINHAND;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> COUNTER;
    public static AnimationManager.AnimationAccessor<StaticAnimation> FIST_GUARD;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> FIST_DASH;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> WHIRLWIND_KICK;
    public static AnimationManager.AnimationAccessor<HeavyAttackAnimation> LEGENDARY_SWORD_HEAVY_ATTACK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> BLUE_DEMON_START_SKILL;
    public static AnimationManager.AnimationAccessor<StaticAnimation> BLUE_DEMON_END_SKILL;
    public static AnimationManager.AnimationAccessor<AttackAnimation> HACKER_SWORD_SKILL;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD_AUTO4;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD_AUTO5;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD3;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DEATH_IDLE;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> KICK_H;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> KICK_1;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> KICK_2;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> KICK_3;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> KICK_4;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> KICK_RUSH;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> FIST_UP;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> RUSH_SWORD;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DUAL_DANCING_EDGE;
    public static AnimationManager.AnimationAccessor<KnockdownAnimation> LEFT_KNOCKDOWN;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SWEEPING_EDGE;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> KICK_C;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> KICK_COMBO;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> HIT_C;
    public static AnimationManager.AnimationAccessor<GuardAnimation> SPEAR_GUARD_HIT;
    public static AnimationManager.AnimationAccessor<StaticAnimation> LEGENDARY_SWORD_GUARD;
    public static AnimationManager.AnimationAccessor<GuardAnimation> LEGENDARY_SWORD_GUARD_HIT;
    public static AnimationManager.AnimationAccessor<GuardAnimation> LEGENDARY_SWORD_GUARD_PARRY;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> EXECUTED_SKILL;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> EXECUTED_WEAPON_HIT;
    public static AnimationManager.AnimationAccessor<ActionAnimation> SWORD_SKILL;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DAGGER_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DAGGER_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DAGGER_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DAGGER_DUAL_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DAGGER_DUAL_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DAGGER_DUAL_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DAGGER_DUAL_AUTO4;
    public static AnimationManager.AnimationAccessor<StaticAnimation> CHECK;
    public static AnimationManager.AnimationAccessor<MovementAnimation> BIPED_RUN_ESWORD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> KNIFE_IDLE;
    public static AnimationManager.AnimationAccessor<MovementAnimation> KNIFE_RUN;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> KNIFE_ATTACK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> KNIFE_CHECK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> CARRY;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> FIST_LEFT;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> KNOCKDOWN_FORWRAD;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> KNOCKDOWN_RIGHT;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> KNOCKDOWN_LEFT;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> AXE_HEAVY_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> AXE_HEAVY_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SWORD_HEAVY_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SWORD_HEAVY_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SWORD_HEAVY_AUTO_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> EXECUTE_COMBO;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> EXECUTE_COMBO_HIT;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> HARD_KICK;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> HARD_KICK_HIT;
    public static AnimationManager.AnimationAccessor<ActionAnimation> RUN_START;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> LONGSWORD_AUTO1;
    public static AnimationManager.AnimationAccessor<MovementAnimation> RUN_DUAL_BIG;
    public static AnimationManager.AnimationAccessor<MovementAnimation> RUN_HOLD;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> LONGEST_HIT;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HARD_GREAT_SWORD_GUARD;
    public static AnimationManager.AnimationAccessor<GuardAnimation> HARD_GREAT_SWORD_GUARD_HIT;
    public static AnimationManager.AnimationAccessor<ActionAnimation> HARD_GREAT_SWORD_GUARD_SKILL;
    public static AnimationManager.AnimationAccessor<ActionAnimation> HIT_LEFT;
    public static AnimationManager.AnimationAccessor<ActionAnimation> HIT_RIGHT;
    public static AnimationManager.AnimationAccessor<ActionAnimation> SHAKE_HAND_TRY;
    public static AnimationManager.AnimationAccessor<ActionAnimation> SHAKE_HAND;
    public static AnimationManager.AnimationAccessor<StaticAnimation> FUNNY_IDLE;
    public static AnimationManager.AnimationAccessor<ActionAnimation> FIST_TRY;
    public static AnimationManager.AnimationAccessor<ActionAnimation> FISTING;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> GIANT_WHIRLWIND;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD_DANCING_EDGE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SPEAR_THRUST;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DUAL_TACHI_GUARD;
    public static AnimationManager.AnimationAccessor<GuardAnimation> DUAL_TACHI_GUARD_HIT;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> WHIRLWIND_KICK_LEFT;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> SUPER_PUNCH;
    public static AnimationManager.AnimationAccessor<StaticAnimation> LAY_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> PUSH_UP_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SIT_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SLIGHT_IDLE;
    public static AnimationManager.AnimationAccessor<AttackBreakAnimation> GUARD_BREAK_ATTACK;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> SWORD_DASH;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> TACHI_DASH;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD_SKILL;
    public static AnimationManager.AnimationAccessor<ActionAnimation> STEP_BACK;
    public static AnimationManager.AnimationAccessor<ActionAnimation> DUAL_END;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> TRIED;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> GREATSWORD_SKILL;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> LEGENDARY_SWORD_WAKE_UP_ATTACK;
    public static AnimationManager.AnimationAccessor<ActionAnimation> DUAL_E_END;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AXE_FUN_SKILL;
    public static AnimationManager.AnimationAccessor<StaticAnimation> GLOWING_AGONY_GUARD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> PORTAL_SUMMON;
    public static AnimationManager.AnimationAccessor<StaticAnimation> KNOCKED_ELITE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_ANIMATE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_1;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_2;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_3;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_4;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_HEALING;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_SACRIFICING;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> BULL_CHARGE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SNAKE_BLADE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> IDLE_BREAK;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> SLEDGE_HAMMER_INNATE_DASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_SLAYER_ANTITHEUS_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_SLAYER_ANTITHEUS_AUTO_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_SLAYER_ANTITHEUS_AUTO_4;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_SLAYER_ANTITHEUS_AGRESSION;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_SLAYER_ANTITHEUS_GUILLOTINE;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> ENDER_SLAYER_MOONLESS_LUNAR_FULLMOON;
    public static AnimationManager.AnimationAccessor<StaticAnimation> ENDER_SLAYER_ANTITHEUS_AIMING;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_AEGIS_MOONLESS_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_AEGIS_MOONLESS_AUTO_2;

    @SubscribeEvent
    public static void registerAnimations(AnimationManager.AnimationRegistryEvent event) {
        event.newBuilder(AnnoyingVillagers.MODID, AVAnimations::build);
    }

    private static void build(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidarmature = Armatures.BIPED;
        AVAnimations.HEROBRINE_ANIMATE = builder.nextAccessor("biped/other/herobrine_animate",
                (accessor) -> new StaticAnimation(false, accessor, humanoidarmature));
        AVAnimations.HEROBRINE_HEALING = builder.nextAccessor("biped/other/herobrine_healing",
                (accessor) -> new StaticAnimation(true, accessor, humanoidarmature));
        AVAnimations.HEROBRINE_SACRIFICING = builder.nextAccessor("biped/other/herobrine_sacrificing",
                (accessor) -> new StaticAnimation(true, accessor, humanoidarmature));
        AVAnimations.KNOCKED_ELITE = builder.nextAccessor("biped/other/knocked_elite",
                (accessor) -> new StaticAnimation(true, accessor, humanoidarmature));
        AVAnimations.EATING_ELITE_1 = builder.nextAccessor("biped/other/eating_elite_1",
                (accessor) -> new StaticAnimation(true, accessor, humanoidarmature));
        AVAnimations.EATING_ELITE_2 = builder.nextAccessor("biped/other/eating_elite_2",
                (accessor) -> new StaticAnimation(true, accessor, humanoidarmature));
        AVAnimations.EATING_ELITE_3 = builder.nextAccessor("biped/other/eating_elite_3",
                (accessor) -> new StaticAnimation(true, accessor, humanoidarmature));
        AVAnimations.EATING_ELITE_4 = builder.nextAccessor("biped/other/eating_elite_4",
                (accessor) -> new StaticAnimation(true, accessor, humanoidarmature));
        AVAnimations.SNAKE_BLADE = builder.nextAccessor("biped/other/snake_blade",
                (accessor) -> new StaticAnimation(true, accessor, humanoidarmature));
        AVAnimations.IDLE_BREAK = builder.nextAccessor("biped/other/idle_break",
                (accessor) -> new StaticAnimation(false, accessor, humanoidarmature));
        AVAnimations.GLOWING_AGONY_GUARD = builder.nextAccessor("biped/skill/glowing_agony_guard",
                (accessor) -> (new StaticAnimation(0.05F, true, accessor, humanoidarmature))
                .addEvents(
                        AnimationEvent.InTimeEvent.create(0.0F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(0.1F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(0.2F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(0.3F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(0.4F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(0.5F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(0.6F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(0.7F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT)));
        AVAnimations.EAT_OFFHAND = builder.nextAccessor("biped/living/eat_offhand",
                (accessor) -> new StaticAnimation(0.35F, true, accessor, humanoidarmature));
        AVAnimations.DRINK_OFFHAND = builder.nextAccessor("biped/living/drink_offhand",
                (accessor) -> new StaticAnimation(0.35F, true, accessor, humanoidarmature));
        AVAnimations.BLOCK_MAINHAND = builder.nextAccessor("biped/living/block_mainhand",
                (accessor) -> new StaticAnimation(0.35F, true, accessor, humanoidarmature));
        AVAnimations.COUNTER = builder.nextAccessor("biped/guard/counter",
                (accessor) -> (new BasicMultipleAttackAnimation(0.3F, 0.08F, 0.1F, 0.15F, 0.525F, ColliderPreset.FIST, humanoidarmature.get().legR, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.COUNTER))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.5F))
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F)));
        AVAnimations.LAY_IDLE = builder.nextAccessor("biped/idle/lay",
                (accessor) -> new StaticAnimation(true, accessor, humanoidarmature));
        AVAnimations.PUSH_UP_IDLE = builder.nextAccessor("biped/idle/push_up",
                (accessor) -> new StaticAnimation(true, accessor, humanoidarmature));
        AVAnimations.SIT_IDLE = builder.nextAccessor("biped/idle/sit",
                (accessor) -> new StaticAnimation(true, accessor, humanoidarmature));
        AVAnimations.SLIGHT_IDLE = builder.nextAccessor("biped/idle/slight",
                (accessor) -> new StaticAnimation(true, accessor, humanoidarmature));
        AVAnimations.FIST_GUARD = builder.nextAccessor("biped/guard/fist_guard",
                (accessor) -> new StaticAnimation(false, accessor, humanoidarmature));
        AVAnimations.FIST_DASH = builder.nextAccessor("biped/combat/fist_dash",
                (accessor) -> (new KickAttackAnimation(0.15F, 0.25F, 0.45F, 0.7F, 0.95F, ColliderPreset.BIPED_BODY_COLLIDER, humanoidarmature.get().toolR, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.WHIRLWIND_KICK = builder.nextAccessor("biped/combat/whirlwind_kick",
                (accessor) -> (new KickAttackAnimation(0.2F, 0.29F, 0.45F, 0.85F, 1.8F, ColliderPreset.BIPED_BODY_COLLIDER, humanoidarmature.get().legR, accessor, humanoidarmature)).addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST).addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get()).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addEvents(
                                new AnimationEvent.InTimeEvent[]{
                                        AnimationEvent.InTimeEvent.create(0.1F, ReusableSources.PLAY_SOUND, Side.SERVER)
                                .params(EpicFightSounds.WHOOSH.get())})
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.LEGENDARY_SWORD_HEAVY_ATTACK = builder.nextAccessor("biped/combat/legendary_sword_heavy_attack",
                (accessor) -> (new HeavyAttackAnimation(0.05F, 0.05F, 0.5F, 0.7F, 1.2F, WOMWeaponColliders.TORMENT_BERSERK_AIRSLAM, humanoidarmature.get().rootJoint, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.5F)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.3F))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicanimation, livingentitypatch, f, f1, pose) -> {
                            if (f1 >= 0.3F && f1 < 0.35F) {
                                float f2 = (float) livingentitypatch.getOriginal().getX();
                                float f3 = (float) livingentitypatch.getOriginal().getY();
                                float f4 = (float) livingentitypatch.getOriginal().getZ();

                                for (BlockState blockstate = livingentitypatch.getOriginal().level().getBlockState(new BlockPos(new Vec3i((int) f2, (int) f3, (int) f4))); (blockstate.getBlock() instanceof BushBlock || blockstate.isAir()) && !blockstate.is(Blocks.VOID_AIR); blockstate = livingentitypatch.getOriginal().level().getBlockState(new BlockPos(new Vec3i((int) f2, (int) f3, (int) f4)))) {
                                    --f3;
                                }

                                float f5 = (float) Math.max(Math.abs(livingentitypatch.getOriginal().getY() - (double) f3) - 1.0D, 0.0D);

                                return 1.0F - (1.0F / (-f5 - 1.0F) + 1.0F);
                            } else {
                                return 1.0F;
                            }
                        }).addEvents(
                                new AnimationEvent.InTimeEvent[]{
                                        AnimationEvent.InTimeEvent.create(0.0F, (livingentitypatch, staticanimation, aobject) -> {
                                            if (livingentitypatch instanceof PlayerPatch) {
                                                ((PlayerPatch<?>) livingentitypatch).setStamina(((PlayerPatch<?>) livingentitypatch).getStamina() - 2.0F);
                                            }}, Side.CLIENT),
                                        AnimationEvent.InTimeEvent.create(0.6F, AVAnimations.ReuseableEvents.GROUNDSLAM, Side.CLIENT)}));
        AVAnimations.BLUE_DEMON_START_SKILL = builder.nextAccessor("biped/other/blue_demon_start_skill",
                (accessor) -> new StaticAnimation(true, accessor, humanoidarmature));
        AVAnimations.BLUE_DEMON_END_SKILL = builder.nextAccessor("biped/other/blue_demon_end_skill",
                (accessor) -> new StaticAnimation(true, accessor, humanoidarmature));
        AVAnimations.HACKER_SWORD_SKILL = builder.nextAccessor("biped/combat/hacker_sword_skill",
                (accessor) -> (new AttackAnimation(0.05F, accessor, humanoidarmature, new Phase(0.0F, 0.016F, 0.066F, 0.133F, 0.133F, InteractionHand.MAIN_HAND, humanoidarmature.get().toolL, ColliderPreset.SWORD), new Phase(0.133F, 0.133F, 0.183F, 0.25F, 0.25F, humanoidarmature.get().toolR, ColliderPreset.SWORD), new Phase(0.25F, 0.25F, 0.3F, 0.366F, 0.366F, InteractionHand.MAIN_HAND, humanoidarmature.get().toolL, ColliderPreset.SWORD), new Phase(0.366F, 0.366F, 0.416F, 0.483F, 0.483F, humanoidarmature.get().toolR, ColliderPreset.SWORD), new Phase(0.483F, 0.483F, 0.533F, 0.6F, 0.6F, InteractionHand.MAIN_HAND, humanoidarmature.get().toolL, ColliderPreset.SWORD), new Phase(0.6F, 0.6F, 0.65F, 0.716F, 0.716F, humanoidarmature.get().toolR, ColliderPreset.SWORD), new Phase(0.716F, 0.716F, 0.766F, 0.833F, 0.833F, InteractionHand.MAIN_HAND, humanoidarmature.get().toolL, ColliderPreset.SWORD), new Phase(0.833F, 0.833F, 0.883F, 1.1F, 1.1F, humanoidarmature.get().toolR, ColliderPreset.SWORD), new Phase(0.933F, 1.133F, 1.183F, 1.6F, 1.6F, humanoidarmature.get().toolL, ColliderPreset.SWORD))).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 4.0F));
        AVAnimations.DUAL_SWORD_AUTO1 = builder.nextAccessor("biped/combat/dual_sword_auto1",
                (accessor) -> (new BasicAttackAnimation(0.1F, accessor, humanoidarmature, new Phase(0.0F, 0.5F, 0.63F, 0.667F, 0.667F, InteractionHand.MAIN_HAND, humanoidarmature.get().toolR, null), new Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidarmature.get().toolL, null)))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        AVAnimations.DUAL_SWORD_AUTO2 = builder.nextAccessor("biped/combat/dual_sword_auto2",
                (accessor) -> (new BasicAttackAnimation(0.15F, accessor, humanoidarmature, new Phase(0.0F, 0.5F, 0.63F, 0.667F, 0.667F, InteractionHand.MAIN_HAND, humanoidarmature.get().toolR, null), new Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidarmature.get().toolL, null)))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        AVAnimations.DUAL_SWORD_AUTO3 = builder.nextAccessor("biped/combat/dual_sword_auto3",
                (accessor) -> (new BasicAttackAnimation(0.16F, accessor, humanoidarmature, new Phase(0.0F, 0.66F, 0.69F, 0.733F, 1.0F, InteractionHand.MAIN_HAND, humanoidarmature.get().toolR, null), new Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidarmature.get().toolL, null)))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        AVAnimations.DUAL_SWORD_AUTO4 = builder.nextAccessor("biped/combat/dual_sword_auto4",
                (accessor) -> (new BasicAttackAnimation(0.1F, accessor, humanoidarmature, new Phase(0.0F, 0.633F, 0.69F, 0.8F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidarmature.get().toolR, null), new Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidarmature.get().toolL, null)))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));
        AVAnimations.DUAL_SWORD_AUTO5 = builder.nextAccessor("biped/combat/dual_sword_auto5",
                (accessor) -> (new BasicAttackAnimation(0.1F, accessor, humanoidarmature, new Phase(0.0F, 0.633F, 0.69F, 0.8F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidarmature.get().toolR, null), new Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidarmature.get().toolL, null)))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));
        AVAnimations.DUAL_SWORD1 = builder.nextAccessor("biped/combat/dual_auto1",
                (accessor) -> (new BasicAttackAnimation(0.1F, accessor, humanoidarmature, new Phase(0.0F, 0.05F, 0.3F, 0.4F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidarmature.get().toolL, null), new Phase(0.1F, 0.1F, 0.4F, 0.6F, 0.6F, humanoidarmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.HIT_PRIORITY, Priority.TARGET)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.5F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        AVAnimations.DUAL_SWORD2 = builder.nextAccessor("biped/combat/dual_auto2",
                (accessor) -> (new BasicAttackAnimation(0.1F, accessor, humanoidarmature, new Phase(0.0F, 0.05F, 0.4F, 0.8F, 1.167F, 2.5F, InteractionHand.MAIN_HAND, humanoidarmature.get().toolR, null), new Phase(0.2F, 0.1F, 1.2F, 1.3F, 1.5F, humanoidarmature.get().toolR, null), new Phase(0.2F, 0.1F, 1.4F, 1.5F, 2.1F, humanoidarmature.get().toolL, null)))
                        .addProperty(AttackPhaseProperty.HIT_PRIORITY, Priority.TARGET)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.5F));
        AVAnimations.DUAL_SWORD3 = builder.nextAccessor("biped/combat/dual_auto3",
                (accessor) -> (new BasicAttackAnimation(0.1F, 0.0F, 0.0F, 0.06F, 0.3F, ColliderPreset.SWORD, humanoidarmature.get().rootJoint, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.HIT_PRIORITY, Priority.TARGET)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        AVAnimations.DEATH_IDLE = builder.nextAccessor("biped/other/death_idle",
                (accessor) -> new StaticAnimation(true, accessor, humanoidarmature));
        AVAnimations.KICK_H = builder.nextAccessor("biped/combat/kick_h",
                (accessor) -> (new KickAttackAnimation(0.1F, 0.05F, 0.4F, 0.4F, 0.8F, WOMWeaponColliders.KICK, humanoidarmature.get().legR, accessor, humanoidarmature)).addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(5.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.KICK_1 = builder.nextAccessor("biped/combat/kick_1",
                (accessor) -> (new KickAttackAnimation(0.1F, 0.05F, 0.4F, 0.4F, 0.5F, WOMWeaponColliders.KICK, humanoidarmature.get().legR, accessor, humanoidarmature)).addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(3.0F))
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.KICK_2 = builder.nextAccessor("biped/combat/kick_2",
                (accessor) -> (new KickAttackAnimation(0.1F, 0.05F, 0.4F, 0.4F, 0.4F, WOMWeaponColliders.KICK, humanoidarmature.get().legL, accessor, humanoidarmature)).addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get()).addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(2.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.KICK_3 = builder.nextAccessor("biped/combat/kick_3",
                (accessor) -> (new KickAttackAnimation(0.05F, 0.05F, 0.3F, 0.4F, 0.6F, WOMWeaponColliders.KICK, humanoidarmature.get().legR, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(5.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.KICK_4 = builder.nextAccessor("biped/combat/kick_4",
                (accessor) -> (new KickAttackAnimation(0.1F, 0.05F, 0.4F, 0.4F, 0.4F, WOMWeaponColliders.KICK, humanoidarmature.get().legL, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.KICK_RUSH = builder.nextAccessor("biped/combat/kick_rush",
                (accessor) -> (new KickAttackAnimation(0.05F, 0.05F, 0.1F, 0.4F, 0.6F, WOMWeaponColliders.KICK, humanoidarmature.get().legR, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, AVSounds.KICK.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(6.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.FIST_UP = builder.nextAccessor("biped/combat/fist_up",
                (accessor) -> (new KickAttackAnimation(0.15F, 0.25F, 0.45F, 0.85F, 0.95F, WOMWeaponColliders.KICK, humanoidarmature.get().toolR, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, AVSounds.KICK.get())
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.RUSH_SWORD = builder.nextAccessor("biped/combat/rush_sword",
                (accessor) -> (new BasicMultipleAttackAnimation(0.15F, 0.0F, 0.1F, 0.26F, 0.75F, ColliderPreset.SWORD, humanoidarmature.get().toolR, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        AVAnimations.DUAL_DANCING_EDGE = builder.nextAccessor("biped/combat/dancing_edge",
                (accessor) -> (new BasicMultipleAttackAnimation(0.25F, accessor, humanoidarmature, new Phase(0.0F, 0.2F, 0.31F, 0.4F, 0.4F, humanoidarmature.get().toolR, null), new Phase(0.4F, 0.5F, 0.61F, 0.65F, 0.65F, InteractionHand.OFF_HAND, humanoidarmature.get().toolL, null), new Phase(0.65F, 0.76F, 0.85F, 1.15F, Float.MAX_VALUE, humanoidarmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 2)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F));
        AVAnimations.LEFT_KNOCKDOWN = builder.nextAccessor("biped/other/left_kd",
                (accessor) -> new KnockdownAnimation(0.08F, accessor, humanoidarmature));
        AVAnimations.SWEEPING_EDGE = builder.nextAccessor("biped/combat/sweeping_edge",
                (accessor) -> (new AttackAnimation(0.2F, 0.1F, 0.35F, 0.46F, 0.79F, ColliderPreset.BIPED_BODY_COLLIDER, humanoidarmature.get().toolR, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.9F))
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.45F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        AVAnimations.KICK_C = builder.nextAccessor("biped/combat/kick_c",
                (accessor) -> (new KickAttackAnimation(0.05F, accessor, humanoidarmature, new Phase(0.0F, 0.4F, 0.45F, 0.49F, 0.49F, humanoidarmature.get().legL, WOMWeaponColliders.KICK), new Phase(0.49F, 0.5F, 0.55F, 0.59F, 0.59F, humanoidarmature.get().legL, WOMWeaponColliders.KICK), new Phase(0.59F, 0.6F, 0.65F, 0.69F, 0.69F, humanoidarmature.get().legL, WOMWeaponColliders.KICK), new Phase(0.69F, 0.7F, 0.75F, 0.79F, 0.79F, humanoidarmature.get().legL, WOMWeaponColliders.KICK), new Phase(0.79F, 0.8F, 0.85F, 0.9F, Float.MAX_VALUE, humanoidarmature.get().legL, WOMWeaponColliders.KICK)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F), 2)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F), 3)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F), 4)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 3)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 4)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get(), 1)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get(), 2)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get(), 3)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get(), 4)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.KICK_COMBO = builder.nextAccessor("biped/combat/kick_combo",
                (accessor) -> (new KickAttackAnimation(0.05F, accessor, humanoidarmature, new Phase(0.0F, 0.15F, 0.25F, 0.3F, 0.3F, humanoidarmature.get().legL, WOMWeaponColliders.KICK), new Phase(0.3F, 0.35F, 0.45F, 0.5F, 0.5F, humanoidarmature.get().legR, WOMWeaponColliders.KICK), new Phase(0.5F, 0.55F, 0.65F, 0.7F, 0.7F, humanoidarmature.get().legL, WOMWeaponColliders.KICK), new Phase(0.7F, 0.75F, 0.85F, 0.9F, 0.9F, humanoidarmature.get().legR, WOMWeaponColliders.KICK), new Phase(0.9F, 1.05F, 1.15F, 1.8F, Float.MAX_VALUE, humanoidarmature.get().legR, WOMWeaponColliders.KICK)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.25F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.25F), 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25F), 2)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.25F), 3)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F), 4)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(5.8F), 4)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 1)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 2)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 2)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 3)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 3)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 3)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG, 4)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.HIT_C = builder.nextAccessor("biped/other/hit_c",
                (accessor) -> new LongHitAnimation(0.08F, accessor, humanoidarmature));
        AVAnimations.SPEAR_GUARD_HIT =  builder.nextAccessor("biped/combat/spear_guard_hit",
                (accessor) -> (new GuardAnimation(0.05F, 0.2F, accessor, humanoidarmature))
                        .addEvents(new AnimationEvent.InTimeEvent[]{
                                AnimationEvent.InTimeEvent.create(0.1F, AVAnimations.ReuseableEvents.FAST_SPINING, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.2F, AVAnimations.ReuseableEvents.FAST_SPINING, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.3F, AVAnimations.ReuseableEvents.FAST_SPINING, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.4F, AVAnimations.ReuseableEvents.FAST_SPINING, Side.CLIENT)}));
        AVAnimations.LEGENDARY_SWORD_GUARD = builder.nextAccessor("biped/combat/legendary_sword_guard",
                (accessor) -> new StaticAnimation(true, accessor, humanoidarmature));
        AVAnimations.LEGENDARY_SWORD_GUARD_HIT = builder.nextAccessor("biped/combat/legendary_sword_guard_hit",
                (accessor) -> new GuardAnimation(0.05F, accessor, humanoidarmature));
        AVAnimations.LEGENDARY_SWORD_GUARD_PARRY = builder.nextAccessor("biped/combat/legendary_sword_guard_parry",
                (accessor) -> new GuardAnimation(0.05F, accessor, humanoidarmature));
        AVAnimations.EXECUTED_SKILL = builder.nextAccessor("biped/combat/executed_skill",
                (accessor) -> (new LongHitAnimation(0.01F, accessor, humanoidarmature))
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false).addState(EntityState.CAN_BASIC_ATTACK, false));
        AVAnimations.EXECUTED_WEAPON_HIT = builder.nextAccessor("biped/combat/executed_weapon_hit",
                (accessor) -> (new LongHitAnimation(0.01F, accessor, humanoidarmature)).addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.SWORD_SKILL = builder.nextAccessor("biped/combat/sword_skill",
                (accessor) -> (new ActionAnimation(0.0F, 1.85F, accessor, humanoidarmature)).addProperty(ActionAnimationProperty.MOVE_VERTICAL, true));
        AVAnimations.DAGGER_AUTO1 = builder.nextAccessor("biped/combat/dagger_auto1",
                (accessor) -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.2F, null, humanoidarmature.get().toolR, accessor, humanoidarmature));
        AVAnimations.DAGGER_AUTO2 = builder.nextAccessor("biped/combat/dagger_auto2",
                (accessor) -> new BasicAttackAnimation(0.08F, 0.0F, 0.1F, 0.2F, null, humanoidarmature.get().toolR, accessor, humanoidarmature));
        AVAnimations.DAGGER_AUTO3 = builder.nextAccessor("biped/combat/dagger_auto3",
                (accessor) -> new BasicAttackAnimation(0.08F, 0.15F, 0.26F, 0.5F, null, humanoidarmature.get().toolR, accessor, humanoidarmature));
        AVAnimations.DAGGER_DUAL_AUTO1 = builder.nextAccessor("biped/combat/dagger_dual_auto1",
                (accessor) -> new BasicAttackAnimation(0.08F, 0.05F, 0.16F, 0.25F, null, humanoidarmature.get().toolR, accessor, humanoidarmature));
        AVAnimations.DAGGER_DUAL_AUTO2 =  builder.nextAccessor("biped/combat/dagger_dual_auto2",
                (accessor) -> new BasicAttackAnimation(0.08F, 0.0F, 0.11F, 0.16F, InteractionHand.OFF_HAND, null, humanoidarmature.get().toolL, accessor, humanoidarmature));
        AVAnimations.DAGGER_DUAL_AUTO3 = builder.nextAccessor("biped/combat/dagger_dual_auto3",
                (accessor) -> new BasicAttackAnimation(0.08F, 0.0F, 0.11F, 0.2F, null, humanoidarmature.get().toolR, accessor, humanoidarmature));
        AVAnimations.DAGGER_DUAL_AUTO4 =  builder.nextAccessor("biped/combat/dagger_dual_auto4",
                (accessor) -> new BasicAttackAnimation(0.13F, 0.1F, 0.21F, 0.4F, ColliderPreset.DUAL_DAGGER_DASH, humanoidarmature.get().toolR, accessor, humanoidarmature));
        AVAnimations.CHECK = builder.nextAccessor("biped/other/check",
                (accessor) -> new StaticAnimation(false, accessor, humanoidarmature));
        AVAnimations.BIPED_RUN_ESWORD = builder.nextAccessor("biped/other/run_esword",
                (accessor) -> new MovementAnimation(true, accessor, humanoidarmature));
        AVAnimations.KNIFE_IDLE = builder.nextAccessor("biped/other/knife_idle",
                (accessor) -> new StaticAnimation(true, accessor, humanoidarmature));
        AVAnimations.KNIFE_RUN = builder.nextAccessor("biped/other/knife_run",
                (accessor) -> new MovementAnimation(true, accessor, humanoidarmature));
        AVAnimations.KNIFE_ATTACK = builder.nextAccessor("biped/combat/knife_attack",
                (accessor) -> (new BasicAttackAnimation(0.15F, 0.01F, 0.2F, 0.5F, 0.6F, null, humanoidarmature.get().toolR, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addState(EntityState.MOVEMENT_LOCKED, false)
                        .addState(EntityState.TURNING_LOCKED, false)
                        .addState(EntityState.LOCKON_ROTATE, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.KNIFE_CHECK = builder.nextAccessor("biped/other/knife_check",
                (accessor) -> new StaticAnimation(false, accessor, humanoidarmature));
        AVAnimations.CARRY = builder.nextAccessor("biped/other/carry",
                (accessor) -> new StaticAnimation(true, accessor, humanoidarmature));
        AVAnimations.FIST_LEFT = builder.nextAccessor("biped/combat/fist_left",
                (accessor) -> (new BasicMultipleAttackAnimation(0.15F, 0.25F, 0.45F, 0.85F, 1.1F, null, humanoidarmature.get().toolL, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.KNOCKDOWN_FORWRAD = builder.nextAccessor("biped/combat/knockdown_forward",
                (accessor) -> (new LongHitAnimation(0.1F, accessor, humanoidarmature))
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.KNOCKDOWN_RIGHT = builder.nextAccessor("biped/combat/knockdown_forward",
                (accessor) -> (new LongHitAnimation(0.1F, accessor, humanoidarmature)).addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.KNOCKDOWN_LEFT = builder.nextAccessor("biped/combat/knockdown_left",
                (accessor) -> (new LongHitAnimation(0.1F, accessor, humanoidarmature))
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.AXE_HEAVY_AUTO_1 = builder.nextAccessor("biped/combat/axe_heavy_auto1",
                (accessor) -> (new BasicMultipleAttackAnimation(0.1F, 0.15F, 0.3F, 0.6F, 0.95F, null, humanoidarmature.get().toolR, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.AXE_HEAVY_AUTO_2 = builder.nextAccessor("biped/combat/axe_heavy_auto2",
                (accessor) -> (new BasicMultipleAttackAnimation(0.1F, 0.15F, 0.8F, 1.2F, 1.95F, null, humanoidarmature.get().toolR, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.4F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.SWORD_HEAVY_AUTO_1 = builder.nextAccessor("biped/combat/sword_heavy_auto1",
                (accessor) -> (new BasicMultipleAttackAnimation(0.1F, 0.11F, 0.27F, 0.5F, 0.95F, null, humanoidarmature.get().toolR, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.4F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.SWORD_HEAVY_AUTO_2 = builder.nextAccessor("biped/combat/sword_heavy_auto2",
                (accessor) -> (new BasicMultipleAttackAnimation(0.01F, 0.1F, 0.12F, 0.22F, 0.95F, null, humanoidarmature.get().toolR, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.4F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.SWORD_HEAVY_AUTO_3 = builder.nextAccessor("biped/combat/sword_heavy_auto3",
                (accessor) -> (new BasicMultipleAttackAnimation(0.01F, 0.1F, 0.21F, 0.32F, 1.2F, null, humanoidarmature.get().toolR, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.EXECUTE_COMBO = builder.nextAccessor("biped/combat/execute_combo",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, accessor, humanoidarmature, new Phase(0.1F, 1.8F, 1.95F, 2.1F, 2.3F, humanoidarmature.get().toolR, null), new Phase(2.3F, 2.3F, 2.34F, 2.42F, 2.45F, humanoidarmature.get().toolR, null), new Phase(2.45F, 2.5F, 2.51F, 2.58F, 2.7F, humanoidarmature.get().toolR, null), new Phase(2.7F, 2.7F, 2.74F, 2.8F, 2.89F, humanoidarmature.get().toolR, null), new Phase(2.89F, 2.9F, 2.93F, 3.15F, 3.2F, humanoidarmature.get().toolR, null), new Phase(3.2F, 3.2F, 3.25F, 3.5F, Float.MAX_VALUE, humanoidarmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1F), 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1F), 2)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1F), 3)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1F), 4)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.1F), 5)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 2)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 3)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 4)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 5)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.EXECUTE_COMBO_HIT = builder.nextAccessor("biped/combat/execute_combo_hit",
                (accessor) -> (new LongHitAnimation(0.1F, accessor, humanoidarmature))
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.HARD_KICK = builder.nextAccessor("biped/combat/hard_kick",
                (accessor) -> (new BasicMultipleAttackAnimation(0.1F, 0.29F, 1.1F, 1.2F, 3.1F, WOMWeaponColliders.TORMENT_BERSERK_AIRSLAM, humanoidarmature.get().legR, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.HARD_KICK_HIT = builder.nextAccessor("biped/combat/hard_kick_hit",
                (accessor) -> (new LongHitAnimation(0.1F, accessor, humanoidarmature))
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false).addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.RUN_START = builder.nextAccessor("biped/other/run_start",
                (accessor) -> (new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidarmature)).addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.LONGSWORD_AUTO1 = builder.nextAccessor("biped/combat/tachi_auto1",
                (accessor) -> (new BasicMultipleAttackAnimation(0.1F, 0.15F, 0.2F, 0.3F, 0.75F, null, humanoidarmature.get().toolR, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        AVAnimations.RUN_HOLD = builder.nextAccessor("biped/other/run_hold",
                (accessor) -> new MovementAnimation(true, accessor, humanoidarmature));
        AVAnimations.RUN_DUAL_BIG = builder.nextAccessor("biped/other/run_dual_big",
                (accessor) -> new MovementAnimation(true, accessor, humanoidarmature));
        AVAnimations.LONGEST_HIT = builder.nextAccessor("biped/combat/longest_hit",
                (accessor) -> (new LongHitAnimation(0.1F, accessor, humanoidarmature))
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.HARD_GREAT_SWORD_GUARD = builder.nextAccessor("biped/combat/hard_great_sword",
                (accessor) -> new StaticAnimation(true, accessor, humanoidarmature));
        AVAnimations.HARD_GREAT_SWORD_GUARD_HIT = builder.nextAccessor("biped/combat/hard_great_sword_hit",
                (accessor) -> new GuardAnimation(0.05F, accessor, humanoidarmature));
        AVAnimations.HARD_GREAT_SWORD_GUARD_SKILL = builder.nextAccessor("biped/combat/hard_great_sword_skill",
                (accessor) -> (new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidarmature))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.HIT_LEFT = builder.nextAccessor("biped/combat/hit_left",
                (accessor) -> (new ActionAnimation(0.1F, accessor, humanoidarmature))
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.HIT_RIGHT = builder.nextAccessor("biped/combat/hit_right",
                (accessor) -> (new ActionAnimation(0.1F, accessor, humanoidarmature))
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.SHAKE_HAND_TRY = builder.nextAccessor("biped/other/shake_hand_try",
                (accessor) -> (new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidarmature))
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.SHAKE_HAND = builder.nextAccessor("biped/other/shake_hand",
                (accessor) -> (new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidarmature))
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.FUNNY_IDLE = builder.nextAccessor("biped/other/funny_idle",
                (accessor) -> new StaticAnimation(true, accessor, humanoidarmature));
        AVAnimations.FIST_TRY = builder.nextAccessor("biped/other/fist_try",
                (accessor) -> (new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidarmature))
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.FISTING = builder.nextAccessor("biped/other/fisting",
                (accessor) -> (new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidarmature))
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addEvents(
                                new AnimationEvent.InTimeEvent[]{
                                        AnimationEvent.InTimeEvent.create(0.15F, ReusableSources.PLAY_SOUND, Side.SERVER).params(EpicFightSounds.WHOOSH.get())})
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.GIANT_WHIRLWIND = builder.nextAccessor("biped/combat/giant_whirlwind",
                (accessor) -> (new BasicAttackAnimation(0.41F, accessor, humanoidarmature, new Phase(0.0F, 0.3F, 0.35F, 0.55F, 0.9F, 0.9F, humanoidarmature.get().toolL, null), new Phase(0.9F, 0.95F, 1.05F, 1.2F, 1.5F, 1.5F, humanoidarmature.get().toolL, null), (new Phase(1.5F, 1.65F, 1.75F, 1.95F, 2.5F, Float.MAX_VALUE, humanoidarmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)))
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        AVAnimations.DUAL_SWORD_DANCING_EDGE = builder.nextAccessor("biped/combat/dual_sword_dancing_edge",
                (accessor) -> (new BasicAttackAnimation(0.25F, accessor, humanoidarmature, new Phase(0.0F, 0.2F, 0.31F, 0.4F, 0.4F, humanoidarmature.get().toolR, null), new Phase(0.4F, 0.5F, 0.61F, 0.65F, 0.65F, InteractionHand.OFF_HAND, humanoidarmature.get().toolL, null), new Phase(0.65F, 0.76F, 0.85F, 1.15F, Float.MAX_VALUE, humanoidarmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG, 2)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F));
        AVAnimations.SPEAR_THRUST = builder.nextAccessor("biped/combat/spear_thrust",
                (accessor) -> (new AttackAnimation(0.11F, accessor, humanoidarmature, new Phase(0.0F, 0.3F, 0.36F, 0.5F, 0.5F, humanoidarmature.get().toolL, null), new Phase(0.5F, 0.5F, 0.56F, 0.75F, 0.75F, humanoidarmature.get().toolL, null), new Phase(0.75F, 0.75F, 0.81F, 1.05F, Float.MAX_VALUE, humanoidarmature.get().toolL, null)))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));
        AVAnimations.DUAL_TACHI_GUARD = builder.nextAccessor("biped/combat/dual_tachi_guard",
                (accessor) -> new StaticAnimation(true, accessor, humanoidarmature));
        AVAnimations.DUAL_TACHI_GUARD_HIT = builder.nextAccessor("biped/combat/dual_tachi_guard_hit",
                (accessor) -> new GuardAnimation(0.05F, accessor, humanoidarmature));
        AVAnimations.WHIRLWIND_KICK_LEFT = builder.nextAccessor("biped/combat/whirlwind_kick_left",
                (accessor) -> (new KickAttackAnimation(0.1F, 0.3F, 0.7F, 0.9F, Float.MAX_VALUE, ColliderPreset.BIPED_BODY_COLLIDER, humanoidarmature.get().legL, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(10.8F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addEvents(
                                new AnimationEvent.InTimeEvent[]{
                                        AnimationEvent.InTimeEvent.create(0.23F, ReusableSources.PLAY_SOUND, Side.SERVER).params(EpicFightSounds.WHOOSH.get())})
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.SUPER_PUNCH = builder.nextAccessor("biped/combat/super_punch",
                (accessor) -> (new KickAttackAnimation(0.05F, 1.0F, 1.25F, 1.4F, Float.MAX_VALUE, ColliderPreset.BIPED_BODY_COLLIDER, humanoidarmature.get().toolR, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.GUARD_BREAK_ATTACK = builder.nextAccessor("biped/combat/guard_break_attack",
                (accessor) -> new AttackBreakAnimation(0.05F, accessor, humanoidarmature));
        AVAnimations.SWORD_DASH = builder.nextAccessor("biped/combat/sword_dash",
                (accessor) -> (new DashAttackAnimation(0.12F, 0.1F, 0.25F, 0.4F, 0.65F, null, humanoidarmature.get().toolR, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F));
        AVAnimations.TACHI_DASH = builder.nextAccessor("biped/combat/tachi_dash",
                (accessor) -> (new DashAttackAnimation(0.15F, 0.1F, 0.2F, 0.45F, 0.7F, null, humanoidarmature.get().toolR, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG));
        AVAnimations.DUAL_SWORD_SKILL = builder.nextAccessor("biped/combat/dual_sword_skill",
                (accessor) -> (new BasicAttackAnimation(0.05F, accessor, humanoidarmature, (new Phase(0.0F, 0.15F, 0.25F, 0.25F, 0.25F, humanoidarmature.get().toolR, null)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(0.25F, 0.25F, 0.4F, 0.5F, 0.5F, humanoidarmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(0.5F, 0.5F, 0.6F, 0.6F, 0.6F, humanoidarmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(0.6F, 0.6F, 0.75F, 0.75F, 0.75F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(0.75F, 0.75F, 0.8F, 0.9F, 0.9F, humanoidarmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(0.9F, 0.9F, 1.0F, 1.0F, 1.0F, humanoidarmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(1.0F, 1.0F, 1.1F, 1.1F, 1.1F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(1.1F, 1.1F, 1.22F, 1.22F, 1.22F, humanoidarmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(1.22F, 1.22F, 1.35F, 1.35F, 1.35F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(1.35F, 1.35F, 1.42F, 1.42F, 1.42F, humanoidarmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(1.42F, 1.42F, 1.5F, 1.5F, 1.5F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(1.5F, 1.5F, 1.6F, 1.6F, 1.6F, humanoidarmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(1.6F, 1.6F, 1.7F, 1.7F, 1.7F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(1.7F, 1.7F, 1.8F, 1.8F, 1.8F, humanoidarmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(1.8F, 1.8F, 1.9F, 1.9F, 1.9F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(1.9F, 2.0F, 2.2F, Float.MAX_VALUE, Float.MAX_VALUE, humanoidarmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F)).addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)))
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, false)
                        .addState(EntityState.LOCKON_ROTATE, false)
                        .addEvents(
                                new AnimationEvent.InTimeEvent[]{
                                        AnimationEvent.InTimeEvent.create(2.5F, AVAnimations.ReuseableEvents.END_ATTACK, Side.BOTH)})
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.STEP_BACK = builder.nextAccessor("biped/combat/step_backward",
                (accessor) -> (new ActionAnimation(0.2F, Float.MAX_VALUE, accessor, humanoidarmature))
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.DUAL_END = builder.nextAccessor("biped/combat/dual_back_end",
                (accessor) -> (new ActionAnimation(0.2F, Float.MAX_VALUE, accessor, humanoidarmature))
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.TRIED = builder.nextAccessor("biped/combat/tried",
                (accessor) -> (new LongHitAnimation(0.2F, accessor, humanoidarmature))
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.GREATSWORD_SKILL = builder.nextAccessor("biped/combat/greatsword_skill",
                (accessor) -> (new BasicAttackAnimation(0.05F, accessor, humanoidarmature, (new Phase(0.0F, 0.1F, 0.25F, 0.25F, 0.25F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(0.25F, 0.25F, 0.4F, 0.5F, 0.5F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(0.5F, 0.5F, 0.6F, 0.6F, 0.6F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(0.6F, 0.6F, 0.75F, 0.75F, 0.75F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(0.75F, 0.75F, 0.8F, 0.9F, 0.9F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(0.9F, 0.9F, 1.0F, 1.0F, 1.0F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.0F, 1.0F, 1.1F, 1.1F, 1.1F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.1F, 1.1F, 1.22F, 1.22F, 1.22F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.22F, 1.22F, 1.35F, 1.35F, 1.35F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.35F, 1.35F, 1.42F, 1.42F, 1.42F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.42F, 1.42F, 1.5F, 1.5F, 1.5F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.5F, 1.5F, 1.6F, 1.6F, 1.6F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.6F, 1.6F, 1.7F, 1.7F, 1.7F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.7F, 1.7F, 1.8F, 1.85F, 1.85F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.85F, 1.85F, 2.2F, Float.MAX_VALUE, Float.MAX_VALUE, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)))
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true).addState(EntityState.CAN_SKILL_EXECUTION, false).addState(EntityState.CAN_BASIC_ATTACK, false).addState(EntityState.MOVEMENT_LOCKED, true).addState(EntityState.TURNING_LOCKED, false).addState(EntityState.LOCKON_ROTATE, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.LEGENDARY_SWORD_WAKE_UP_ATTACK = builder.nextAccessor("biped/combat/legendary_sword_wake_up_attack",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, accessor, humanoidarmature, new Phase(0.0F, 0.15F, 0.4F, 0.45F, 0.45F, humanoidarmature.get().toolR, null), new Phase(0.45F, 0.5F, 0.8F, Float.MAX_VALUE, Float.MAX_VALUE, humanoidarmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.6F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, false));
        AVAnimations.DUAL_E_END = builder.nextAccessor("biped/combat/dual_e_end",
                (accessor) -> (new ActionAnimation(0.2F, Float.MAX_VALUE, accessor, humanoidarmature))
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.AXE_FUN_SKILL = builder.nextAccessor("biped/combat/axe_fun_skill",
                (accessor) -> (new BasicAttackAnimation(0.05F, accessor, humanoidarmature, (new Phase(0.0F, 0.1F, 0.25F, 0.25F, 0.25F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(0.25F, 0.25F, 0.4F, 0.5F, 0.5F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(0.5F, 0.5F, 0.6F, 0.6F, 0.6F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(0.6F, 0.6F, 0.75F, 0.75F, 0.75F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(0.75F, 0.75F, 0.8F, 0.9F, 0.9F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(0.9F, 0.9F, 1.0F, 1.0F, 1.0F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.0F, 1.0F, 1.1F, 1.1F, 1.1F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.1F, 1.1F, 1.22F, 1.22F, 1.22F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.22F, 1.22F, 1.35F, 1.35F, 1.35F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.35F, 1.35F, 1.42F, 1.42F, 1.42F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.42F, 1.42F, 1.5F, 1.5F, 1.5F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.5F, 1.5F, 1.55F, 1.55F, 1.55F, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.55F, 1.6F, 1.7F, Float.MAX_VALUE, Float.MAX_VALUE, humanoidarmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)))
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true).addState(EntityState.CAN_SKILL_EXECUTION, false).addState(EntityState.CAN_BASIC_ATTACK, false).addState(EntityState.MOVEMENT_LOCKED, true).addState(EntityState.TURNING_LOCKED, false).addState(EntityState.LOCKON_ROTATE, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.BULL_CHARGE = builder.nextAccessor("biped/other/bull_charge",
                (accessor) -> (new BasicMultipleAttackAnimation(0.2F, accessor, humanoidarmature, new Phase(0.0F, 0.2F, 0.25F, 0.29F, 0.29F,
                        humanoidarmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new Phase(0.29F, 0.3F, 0.35F, 0.39F, 0.39F,
                                humanoidarmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new Phase(0.39F, 0.4F, 0.45F, 0.49F, 0.49F,
                                humanoidarmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new Phase(0.49F, 0.5F, 0.55F, 0.59F, 0.59F,
                                humanoidarmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new Phase(0.59F, 0.6F, 0.65F, 0.69F, 0.69F,
                                humanoidarmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new Phase(0.69F, 0.7F, 0.75F, 0.79F, 0.79F,
                                humanoidarmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new Phase(0.79F, 0.8F, 0.85F, 0.89F, 0.89F,
                                humanoidarmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new Phase(0.89F, 1.0F, 1.1F, 1.3F, Float.MAX_VALUE,
                                humanoidarmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 1)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 2)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 2)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 2)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 2)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 2)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 2)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 3)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 3)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 3)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 3)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 3)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 3)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 4)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 4)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 4)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 4)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 4)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 4)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 5)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 5)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 5)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 5)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 5)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 5)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 6)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 6)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 6)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 6)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 6)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 6)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(2.0F), 7)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(3.0F), 7)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 7)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL, 7)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get(), 7)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 7)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.0F)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null));
        AVAnimations.SLEDGE_HAMMER_INNATE_DASH = builder.nextAccessor("biped/other/sledge_hammer_innate_dash",
                (accessor) -> (new SpecialAttackAnimation(0.05F, accessor, humanoidarmature,
                        new Phase(0.0F, 0.35F, 0.39F, 0.4F, 0.4F, humanoidarmature.get().rootJoint, WOMWeaponColliders.ENDER_PISTOLERO),
                        new Phase(0.4F, 0.45F, 0.49F, 0.5F, 0.5F, InteractionHand.OFF_HAND, humanoidarmature.get().rootJoint, WOMWeaponColliders.ENDER_PISTOLERO),
                        new Phase(0.5F, 0.55F, 0.59F, 0.6F, 0.6F, humanoidarmature.get().rootJoint, WOMWeaponColliders.ENDER_PISTOLERO),
                        new Phase(0.6F, 0.65F, 0.69F, 0.7F, 0.7F, InteractionHand.OFF_HAND, humanoidarmature.get().rootJoint, WOMWeaponColliders.ENDER_PISTOLERO),
                        new Phase(0.7F, 0.75F, 0.79F, 0.8F, 0.8F, humanoidarmature.get().rootJoint, WOMWeaponColliders.ENDER_PISTOLERO),
                        new Phase(0.8F, 0.85F, 0.9F, 1.5F, Float.MAX_VALUE, InteractionHand.OFF_HAND, humanoidarmature.get().rootJoint, WOMWeaponColliders.ENDER_PISTOLERO)))
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.8F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 1.5F)));
        AVAnimations.ENDER_SLAYER_ANTITHEUS_AGRESSION = builder.nextAccessor("biped/combat/ender_slayer_antitheus_agression",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, accessor, humanoidarmature,
                        new Phase(0.0F, 0.2F, 0.35F, 0.59F, 0.59F, humanoidarmature.get().toolR, WOMWeaponColliders.ANTITHEUS_AGRESSION),
                        new Phase(0.59F, 0.6F, 0.65F, 0.85F, Float.MAX_VALUE, humanoidarmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_AGRESSION_REAP))).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_DOWN)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F), 1)
                        .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(WOMExtraDamageInstance.WOM_TARGET_CURRENT_HEALTH.create(1.0F)), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL, 1)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE.get(), 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_UP, 1)
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE), 1)
                        .addProperty(AttackAnimationProperty.EXTRA_COLLIDERS, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true));
        AVAnimations.ENDER_SLAYER_ANTITHEUS_GUILLOTINE = builder.nextAccessor("biped/combat/ender_slayer_antitheus_guillotine",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, accessor, humanoidarmature,
                        new Phase(0.0F, 0.5F, 0.75F, 0.79F, 0.79F, humanoidarmature.get().toolR, null),
                        new Phase(0.79F, 0.8F, 1.0F, 1.1F, Float.MAX_VALUE, humanoidarmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.4F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F), 1)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE, 1)
                        .addProperty(AttackAnimationProperty.EXTRA_COLLIDERS, 6)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.3F))
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null));
        AVAnimations.ENDER_SLAYER_ANTITHEUS_AUTO_2 = builder.nextAccessor("biped/combat/ender_slayer_antitheus_auto_2",
                (accessor) -> (new BasicMultipleAttackAnimation(0.15F, 0.15F, 0.45F, 0.45F, null, humanoidarmature.get().toolR, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        AVAnimations.ENDER_SLAYER_ANTITHEUS_AUTO_3 = builder.nextAccessor("biped/combat/ender_slayer_antitheus_auto_3",
                (accessor) -> (new BasicMultipleAttackAnimation(0.15F, accessor, humanoidarmature,
                        new Phase(0.0F, 0.15F, 0.35F, 0.5F, 0.5F, humanoidarmature.get().toolR, null),
                        new Phase(0.5F, 0.55F, 0.7F, 0.75F, Float.MAX_VALUE, humanoidarmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F), 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE, 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AttackAnimationProperty.EXTRA_COLLIDERS, 5)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F));
        AVAnimations.ENDER_SLAYER_ANTITHEUS_AUTO_4 = builder.nextAccessor("biped/combat/ender_slayer_antitheus_auto_4",
                (accessor) -> (new BasicMultipleAttackAnimation(0.15F, 0.5F, 0.75F, 0.9F, null, humanoidarmature.get().toolR, accessor, humanoidarmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                        .addProperty(AttackAnimationProperty.EXTRA_COLLIDERS, 2)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        AVAnimations.ENDER_SLAYER_MOONLESS_LUNAR_FULLMOON = builder.nextAccessor("biped/other/ender_slayer_moonless_lunar_fullmoon", (accessor) -> (new SpecialAttackAnimation(0.05F, accessor, humanoidarmature,
                new Phase(0.0F, 0.6F, 0.7F, 0.85F, Float.MAX_VALUE, humanoidarmature.get().rootJoint, null)))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                .addProperty(StaticAnimationProperty.POSE_MODIFIER, null));
        AVAnimations.ENDER_SLAYER_ANTITHEUS_AIMING = builder.nextAccessor("biped/other/ender_slayer_antitheus_aiming",
                (accessor) -> new StaticAnimation(false, accessor, humanoidarmature)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> 2.0F));
        AVAnimations.ENDER_AEGIS_MOONLESS_AUTO_1 = builder.nextAccessor("biped/combat/ender_aegis_moonless_auto_1",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, accessor, humanoidarmature,
                        new Phase(0.0F, 0.25F, 0.45F, 0.5F, Float.MAX_VALUE, humanoidarmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SMALL.get())
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));
        AVAnimations.ENDER_AEGIS_MOONLESS_AUTO_2 = builder.nextAccessor("biped/combat/ender_aegis_moonless_auto_2",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, accessor, humanoidarmature,
                        new Phase(0.0F, 0.8F, 1.0F, 1.0F, Float.MAX_VALUE, humanoidarmature.get().toolR, WOMWeaponColliders.MOONLESS_BYPASS)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(0.5F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.SHARPCUT_ANGLED_DOWN_LEFT_SLASH)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F));
    }

    private static class ReuseableEvents {
        public static final AnimationEvent.E0 FAST_SPINING =
                (livingentitypatch, staticanimation, aobject) -> livingentitypatch.getOriginal().level().playSound((Player) livingentitypatch.getOriginal(), livingentitypatch.getOriginal(), EpicFightSounds.WHOOSH.get(), SoundSource.NEUTRAL, 0.5F, 1.1F - ((new Random()).nextFloat() - 0.5F) * 0.2F);

        private static final AnimationEvent.E0 GROUNDSLAM =
                (livingentitypatch, staticanimation, aobject) -> {
            Vec3 vec3 = livingentitypatch.getOriginal().position();
            OpenMatrix4f openmatrix4f = livingentitypatch.getArmature()
                    .getBoundTransformFor(livingentitypatch.getAnimator().getPose(1.0F), Armatures.BIPED.get().toolR)
                    .mulFront(OpenMatrix4f.createTranslation((float) vec3.x, (float) vec3.y, (float) vec3.z)
                            .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                                    .mulBack(livingentitypatch.getModelMatrix(1.0F))));
            Vec3 vec31 = OpenMatrix4f.transform(openmatrix4f, (new Vec3f(0.0F, -0.0F, -1.4F)).toDoubleVector());
            Level level = livingentitypatch.getOriginal().level();
            Vec3 vec32 = getFloor(livingentitypatch, new Vec3f(0.0F, 0.0F, -1.4F), Armatures.BIPED.get().toolR);
            BlockState blockstate = livingentitypatch.getOriginal().level().getBlockState(new BlockPos(new Vec3i((int)Math.floor(vec32.x), (int)Math.floor(vec32.y), (int)Math.floor(vec32.z))));

            level.playLocalSound(vec32.x, vec32.y, vec32.z, blockstate.is(Blocks.WATER) ?
                    SoundEvents.GENERIC_SPLASH :
                    EpicFightSounds.SLAM_HEAVY.get(),
                    SoundSource.BLOCKS,
                    1.0F, 1.0F, false);
            vec31 = new Vec3(vec31.x, vec32.y, vec31.z);
            livingentitypatch.getOriginal().level().addParticle(WOMParticles.WOM_GROUND_SLAM.get(), vec32.x, (int) vec32.y + 1, vec32.z, 1.0D, 50.0D, 1.0D);
            LevelUtil.circleSlamFracture(livingentitypatch.getOriginal(), level, vec31, 3.5D, true, false);
        };

        private static final AnimationEvent.E0 END_ATTACK =
                (livingentitypatch, staticanimation, aobject) -> {
            if (livingentitypatch instanceof PlayerPatch) {
                livingentitypatch.playAnimationSynchronized(AVAnimations.DUAL_END, 0.1F);
            }
        };

        public static Vec3 getFloor(LivingEntityPatch<?> livingentitypatch, Vec3f vec3f, Joint joint) {
            OpenMatrix4f openmatrix4f = livingentitypatch.getArmature().getBoundTransformFor(livingentitypatch.getAnimator().getPose(1.0F), joint);

            openmatrix4f.translate(vec3f);
            OpenMatrix4f openmatrix4f1 = (new OpenMatrix4f()).rotate(-((float) Math.toRadians(livingentitypatch.getOriginal().yRotO + 180.0F)), new Vec3f(0.0F, 1.0F, 0.0F));

            OpenMatrix4f.mul(openmatrix4f1, openmatrix4f, openmatrix4f);
            float f = openmatrix4f.m30 + (float) livingentitypatch.getOriginal().getX();
            float f1 = openmatrix4f.m31 + (float) livingentitypatch.getOriginal().getY();
            float f2 = openmatrix4f.m32 + (float) livingentitypatch.getOriginal().getZ();

            for (BlockState blockstate = livingentitypatch.getOriginal().level().getBlockState(new BlockPos(new Vec3i((int) f, (int) f1, (int) f2))); (blockstate.getBlock() instanceof BushBlock || blockstate.isAir()) && !blockstate.is(Blocks.VOID_AIR); blockstate = livingentitypatch.getOriginal().level().getBlockState(new BlockPos(new Vec3i((int) f, (int) f1, (int) f2)))) {
                --f1;
            }

            return new Vec3(f, f1, f2);
        }
    }
}
