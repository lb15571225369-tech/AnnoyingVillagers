/*
 * Annoying Villagers - Unified Attribution & License Notice
 *
 * This file may include or adapt code/assets from third-party projects listed below.
 * Keep this notice and all upstream notices.
 *
 * [1] EpicACG - author: dfdyz - License: GPL-3.0
 *     - Portions of this file are adapted from the EpicACG mod.
 *     - See: licenses/GPL-3.0.txt and https://www.gnu.org/licenses/gpl-3.0.html
 *
 * [2] Epic Fight - Valour Guard - author: namelesslk - License: LGPL-2.1
 *     - Derived assets (e.g., animations).
 *     - See: licenses/LGPL-2.1.txt
 *
 * [3] Epic Fight x Iron's Spells: Enhanced Animations - author: YukamiNeeSan - License: MIT
 *     - Derived assets/data used with attribution.
 *     - Source: https://www.curseforge.com/minecraft/mc-mods/epic-fight-x-irons-spells-enhanced-animations
 *     - See: licenses/MIT-YukamiNeeSan.txt
 *
 * [4] Epic Fight - Infernal Gainer - author: reascer - License: GNU
 *     - Code and/or assets referenced/derived.
 *     - Source: https://www.curseforge.com/minecraft/mc-mods/epic-fight-infernal-gainer
 *     - See: http://www.gnu.org/licenses
 *
 * Notes:
 * - Where GPL-3.0/LGPL-2.1 applies, provide the corresponding license texts and
 *   make source changes available under the same license terms as required.
 * - MIT components require retaining the original MIT notice with copyright.
 */

package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.animations.BowAttackAnimation;
import com.pla.annoyingvillagers.animations.HeavyAttackAnimation;
import com.pla.annoyingvillagers.animations.KickAttackAnimation;
import com.pla.annoyingvillagers.animations.RushSwordAnimation;
import com.pla.annoyingvillagers.block.CryingObsidianSpikeBlock;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.entity.BlockProjectileEntity;
import com.pla.annoyingvillagers.entity.NullEntity;
import com.pla.annoyingvillagers.entity.NullSkeletonEntity;
import com.pla.annoyingvillagers.entity.ObsidianSledgehammerProjectileEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.EnderAegisItem;
import com.pla.annoyingvillagers.item.ShadowObsidianWeaponItem;
import com.pla.annoyingvillagers.network.ClientboundGlaiveExplosionFx;
import com.pla.annoyingvillagers.network.ClientboundMuteExplosionAtPos;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.*;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import reascer.wom.animation.WomAnimationProperty;
import reascer.wom.animation.attacks.AntitheusShootAttackAnimation;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.animation.attacks.SpecialAttackAnimation;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.WOMSounds;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import reascer.wom.particle.WOMParticles;
import reascer.wom.world.damagesources.WOMExtraDamageInstance;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Keyframe;
import yesman.epicfight.api.animation.TransformSheet;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationEvent.Side;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.animation.types.AttackAnimation.Phase;
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
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AVAnimations {
    // Animation clone and re-registered from EpicFight
    public static AnimationManager.AnimationAccessor<StaticAnimation> EAT_OFFHAND;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DRINK_OFFHAND;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHIELD_MAINHAND;
    public static AnimationManager.AnimationAccessor<ActionAnimation> AEGIS_SHIELD_SHOOT;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHIELD_OFFHAND;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> OBSIDIAN_FIST_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> OBSIDIAN_FIST_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> OBSIDIAN_FIST_AUTO3;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> OBSIDIAN_FIST_AIR_SLASH;
    public static AnimationManager.AnimationAccessor<AttackAnimation> OBSIDIAN_BIPED_LANDING;
    public static AnimationManager.AnimationAccessor<AttackAnimation> OBSIDIAN_ZOMBIE_ATTACK3;

    // Animation from EpicFight Infernal Gainer
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> KICK_C;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> KICK_COMBO;

    // Animation from EpicFight ACG
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> BOW_AUTO_1;
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> BOW_AUTO_2;
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> BOW_AUTO_3;
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> BOW_AUTO_4;
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> BOW_AUTO_5;

    // Animation from Epicfight Valour Guard
    public static AnimationManager.AnimationAccessor<StaticAnimation> VALOUR_HOLD_GREATSWORD;
    public static AnimationManager.AnimationAccessor<MovementAnimation> VALOUR_WALK_GREATSWORD;
    public static AnimationManager.AnimationAccessor<MovementAnimation> VALOUR_RUN_GREATSWORD;

    // Animation from EpicFight x Iron's Spells
    public static AnimationManager.AnimationAccessor<StaticAnimation> CASTING_ONE_HAND_TOP;
    public static AnimationManager.AnimationAccessor<StaticAnimation> CASTING_ONE_HAND_INWARD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> CASTING_ONE_HAND_BUFF;

    // Animation from Pugilist Steve Annoying Villagers 1.18.2
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> COUNTER;
    public static AnimationManager.AnimationAccessor<StaticAnimation> FIST_GUARD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> NULL_GUARD;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> FIST_DASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> WHIRLWIND_KICK;
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
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> FIST_UP;
    public static AnimationManager.AnimationAccessor<RushSwordAnimation> RUSH_SWORD;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DUAL_DANCING_EDGE;
    public static AnimationManager.AnimationAccessor<KnockdownAnimation> LEFT_KNOCKDOWN;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SWEEPING_EDGE;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> HIT_C;
    public static AnimationManager.AnimationAccessor<GuardAnimation> SPEAR_GUARD_HIT;
    public static AnimationManager.AnimationAccessor<StaticAnimation> LEGENDARY_SWORD_GUARD;
    public static AnimationManager.AnimationAccessor<GuardAnimation> LEGENDARY_SWORD_GUARD_HIT;
    public static AnimationManager.AnimationAccessor<GuardAnimation> LEGENDARY_SWORD_GUARD_PARRY;
    public static AnimationManager.AnimationAccessor<ActionAnimation> POSE_UP;
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
    public static AnimationManager.AnimationAccessor<LongHitAnimation> KNOCKDOWN_FORWARD;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> KNOCKDOWN_RIGHT;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> KNOCKDOWN_LEFT;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> AXE_HEAVY_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> AXE_HEAVY_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SWORD_HEAVY_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SWORD_HEAVY_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SWORD_HEAVY_AUTO_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> HARD_KICK;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> HARD_KICK_HIT;
    public static AnimationManager.AnimationAccessor<ActionAnimation> RUN_START;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> LONGSWORD_AUTO1;
    public static AnimationManager.AnimationAccessor<MovementAnimation> RUN_DUAL_BIG;
    public static AnimationManager.AnimationAccessor<MovementAnimation> RUN_HOLD;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> LONGEST_HIT;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HARD_GREATSWORD_GUARD;
    public static AnimationManager.AnimationAccessor<GuardAnimation> HARD_GREATSWORD_GUARD_HIT;
    public static AnimationManager.AnimationAccessor<ActionAnimation> HARD_GREATSWORD_GUARD_SKILL;
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
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> WHIRLWIND_KICK_LEFT;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> SUPER_PUNCH;
    public static AnimationManager.AnimationAccessor<StaticAnimation> LAY_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> PUSH_UP_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SIT_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SLIGHT_IDLE;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> GUARD_BREAK_ATTACK;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> SWORD_DASH;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> TACHI_DASH;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD_SKILL;
    public static AnimationManager.AnimationAccessor<ActionAnimation> DUAL_END;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> TRIED;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> GREATSWORD_SKILL;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> LEGENDARY_SWORD_WAKE_UP_ATTACK;
    public static AnimationManager.AnimationAccessor<ActionAnimation> DUAL_E_END;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AXE_FUN_SKILL;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> LEGENDARY_SWORD_AUTO_4;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_FIST_DASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_WHIRLWIND_KICK;

    // Animation made by me
    public static AnimationManager.AnimationAccessor<StaticAnimation> PORTAL_SUMMON;
    public static AnimationManager.AnimationAccessor<StaticAnimation> KNOCKED_ELITE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_ANIMATE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_1;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_2;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_3;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_4;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_HEALING;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_SACRIFICING;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_ASSISTANCE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_STAGE_CHANGE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> LOW_CLONE_ESCAPE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SNAKE_BLADE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SNAKE_BLADE_GUARD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> IDLE_BREAK;
    public static AnimationManager.AnimationAccessor<ActionAnimation> PLACE_BLOCK;

    // Animation clone and re-registered from WOM
    public static AnimationManager.AnimationAccessor<StaticAnimation> GLOWING_AGONY_GUARD;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_AEGIS_BULL_CHARGE;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_AEGIS_MOONLESS_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_AEGIS_MOONLESS_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> YELLOW_SOLAR_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> YELLOW_NAPOLEON_AUTO_3;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> YELLOW_NAPOLEON_AUSTERLITZ_SHOOT;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> ENDER_AEGIS_NAPOLEON_RELOAD_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_NAPOLEON_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_NAPOLEON_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_NAPOLEON_AUTO_4;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_NAPOLEON_AUSTERLITZ;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DEMONIAC_RUINE_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DEMONIAC_RUINE_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DEMONIAC_RUINE_AUTO_4;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DEMONIAC_TORMENT_CHARGED_ATTACK_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DEMONIAC_RUINE_COMET;
    public static AnimationManager.AnimationAccessor<ActionAnimation> AGONY_GUARD_HIT_1;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> ENDER_GLAIVE_NAPOLEON_SHOOT_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_AGONY_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_AUTO_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_AUTO_4;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_AGRESSION;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_GUILLOTINE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> CLONE_ANTITHEUS_ASCENSION;
    public static AnimationManager.AnimationAccessor<AttackAnimation> CLONE_ANTITHEUS_LAPSE;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_ASCENDED_DEATHFALL;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_ASCENDED_BLINK;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_ASCENDED_BLACKHOLE;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_NAPOLEON_AUTO_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_NAPOLEON_WATERLOW;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ENDERBLASTER_TWOHAND_TOMAHAWK;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> YELLOW_TORMENT_CHARGED_ATTACK_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ENDERBLASTER_ONEHAND_DASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SLEDGEHAMMER_TORMENT_BERSERK_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SLEDGEHAMMER_TORMENT_BERSERK_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SLEDGEHAMMER_SOLAR_AUTO_3;
    public static AnimationManager.AnimationAccessor<AntitheusShootAttackAnimation> CLONE_ANTITHEUS_SHOOT;
    public static AnimationManager.AnimationAccessor<StaticAnimation> CLONE_ANTITHEUS_ASCENDED_IDLE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> NULL_SKELETON_ANTITHEUS_ASCENSION;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> NULL_ANTITHEUS_ASCENDED_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> NULL_ANTITHEUS_ASCENDED_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> NULL_ANTITHEUS_ASCENDED_AUTO_3;
    public static AnimationManager.AnimationAccessor<DodgeAnimation> HEROBRINE_MOB_ENDERSTEP_OBSCURIS;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_ANTITHEUS_ASCENDED_DEATHFALL;
    public static AnimationManager.AnimationAccessor<MovementAnimation> OLD_MOONLESS_RUN;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> OBSIDIAN_STRONG_KICK;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> OBSIDIAN_KICK_AUTO_3;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> OBSIDIAN_KICK_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_STRONG_PUNCH;

    @SubscribeEvent
    public static void registerAnimations(AnimationManager.AnimationRegistryEvent event) {
        event.newBuilder(AnnoyingVillagers.MODID, AVAnimations::build);
    }

    private static void build(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        // Animation clone and re-registered from EpicFight
        AVAnimations.EAT_OFFHAND = builder.nextAccessor("biped/epicfight_clone/eat_offhand",
                (accessor) -> new StaticAnimation(0.35F, true, accessor, humanoidArmature));
        AVAnimations.DRINK_OFFHAND = builder.nextAccessor("biped/epicfight_clone/drink_offhand",
                (accessor) -> new StaticAnimation(0.35F, true, accessor, humanoidArmature));
        AVAnimations.SHIELD_MAINHAND = builder.nextAccessor("biped/epicfight_clone/shield_mainhand",
                (accessor) -> new StaticAnimation(0.35F, true, accessor, humanoidArmature));
        AVAnimations.AEGIS_SHIELD_SHOOT = builder.nextAccessor("biped/epicfight_clone/aegis_shield_shoot",
                (accessor) -> new ActionAnimation(0.35F, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.5F, (livingEntityPatch, self, p) -> {
                                    EnderAegisItem.shieldShoot(livingEntityPatch.getOriginal().level(), livingEntityPatch.getOriginal());
                                    if (livingEntityPatch.getOriginal() instanceof Player player) {
                                        ItemCooldowns cooldowns = player.getCooldowns();
                                        cooldowns.addCooldown(player.getMainHandItem().getItem(), 10);
                                    }
                                }, Side.SERVER)
                        ));
        AVAnimations.SHIELD_OFFHAND = builder.nextAccessor("biped/epicfight_clone/shield_offhand",
                (accessor) -> new StaticAnimation(0.35F, true, accessor, humanoidArmature));
        AVAnimations.OBSIDIAN_FIST_AUTO1 = builder.nextAccessor("biped/epicfight_clone/obsidian_fist_auto1",
                (accessor) -> (new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.15F, InteractionHand.OFF_HAND, null, Armatures.BIPED.get().toolL, accessor, Armatures.BIPED))
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.2F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, ReuseableEvents.SUMMON_2_OBSIDIAN_HAND_LEFT, Side.SERVER)
                        ));
        AVAnimations.OBSIDIAN_FIST_AUTO2 = builder.nextAccessor("biped/epicfight_clone/obsidian_fist_auto2",
                (accessor) -> (new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.15F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.2F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, ReuseableEvents.SUMMON_2_OBSIDIAN_HAND_RIGHT, Side.SERVER)
                        ));
        AVAnimations.OBSIDIAN_FIST_AUTO3 = builder.nextAccessor("biped/epicfight_clone/obsidian_fist_auto3",
                (accessor) -> (new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.5F, InteractionHand.OFF_HAND, null, Armatures.BIPED.get().toolL, accessor, Armatures.BIPED))
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.2F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, ReuseableEvents.SUMMON_3_OBSIDIAN_HAND_LEFT, Side.SERVER)
                        ));
        AVAnimations.OBSIDIAN_FIST_AIR_SLASH = builder.nextAccessor("biped/epicfight_clone/obsidian_fist_airslash",
                (accessor) -> (new AirSlashAnimation(0.1F, 0.15F, 0.26F, 0.4F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 4.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.SUMMON_6_OBSIDIAN_HAND_RIGHT, Side.SERVER)
                        ));
        AVAnimations.OBSIDIAN_BIPED_LANDING = builder.nextAccessor("biped/epicfight_clone/obsidian_landing",
                (accessor) -> new AttackAnimation(0.0F, 0.0F, 0.0F, 0.0F, Float.MAX_VALUE, null, Armatures.BIPED.get().head, accessor, Armatures.BIPED)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, ReuseableEvents.SUMMON_OBSIDIAN_PILLAR, Side.SERVER)
                        ));
        AVAnimations.OBSIDIAN_ZOMBIE_ATTACK3 = builder.nextAccessor("biped/epicfight_clone/obsidian_zombie_attack3",
                (accessor) -> new AttackAnimation(0.1F, 0.5F, 0.5F, 0.6F, 1.15F, ColliderPreset.HEAD, Armatures.BIPED.get().head, accessor, Armatures.BIPED)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.5F, ReuseableEvents.SUMMON_OBSIDIAN_WALL, Side.SERVER)
                        ));

        // Animation from EpicFight Infernal Gainer
        AVAnimations.KICK_C = builder.nextAccessor("biped/epicfight_infernal_gainer/kick_c",
                (accessor) -> (new KickAttackAnimation(0.05F, accessor, humanoidArmature, new Phase(0.0F, 0.4F, 0.45F, 0.49F, 0.49F, humanoidArmature.get().legL, WOMWeaponColliders.KICK), new Phase(0.49F, 0.5F, 0.55F, 0.59F, 0.59F, humanoidArmature.get().legL, WOMWeaponColliders.KICK), new Phase(0.59F, 0.6F, 0.65F, 0.69F, 0.69F, humanoidArmature.get().legL, WOMWeaponColliders.KICK), new Phase(0.69F, 0.7F, 0.75F, 0.79F, 0.79F, humanoidArmature.get().legL, WOMWeaponColliders.KICK), new Phase(0.79F, 0.8F, 0.85F, 0.9F, Float.MAX_VALUE, humanoidArmature.get().legL, WOMWeaponColliders.KICK)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F), 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F), 2)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F), 3)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F), 4)
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
        AVAnimations.KICK_COMBO = builder.nextAccessor("biped/epicfight_infernal_gainer/kick_combo",
                (accessor) -> (new KickAttackAnimation(0.05F, accessor, humanoidArmature, new Phase(0.0F, 0.15F, 0.25F, 0.3F, 0.3F, humanoidArmature.get().legL, WOMWeaponColliders.KICK), new Phase(0.3F, 0.35F, 0.45F, 0.5F, 0.5F, humanoidArmature.get().legR, WOMWeaponColliders.KICK), new Phase(0.5F, 0.55F, 0.65F, 0.7F, 0.7F, humanoidArmature.get().legL, WOMWeaponColliders.KICK), new Phase(0.7F, 0.75F, 0.85F, 0.9F, 0.9F, humanoidArmature.get().legR, WOMWeaponColliders.KICK), new Phase(0.9F, 1.05F, 1.15F, 1.8F, Float.MAX_VALUE, humanoidArmature.get().legR, WOMWeaponColliders.KICK)))
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

        // Animation from EpicFight ACG
        AVAnimations.BOW_AUTO_1 = builder.nextAccessor("biped/epic_agc/bow_auto1",
                (accessor) -> new BowAttackAnimation(0.1F, 0.0F, 0.62F, 0.8333F, 1.2F, InteractionHand.MAIN_HAND, null, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.4F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), Side.BOTH)
                        )
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (anim, entity, a, b, c) -> 3.0F));

        AVAnimations.BOW_AUTO_2 = builder.nextAccessor("biped/epic_agc/bow_auto2",
                (accessor) -> new BowAttackAnimation(0.1F, 0.0F, 0.7F, 0.98F, 1.2F, InteractionHand.MAIN_HAND, null, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.6F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), Side.BOTH)
                        )
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (anim, entity, a, b, c) -> 3.0F));

        AVAnimations.BOW_AUTO_3 = builder.nextAccessor("biped/epic_agc/bow_auto3",
                (accessor) -> new BowAttackAnimation(0.1F, 0.0F, 0.88F, 1.03F, 1.3F, InteractionHand.MAIN_HAND, null, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (anim, entity, a, b, c) -> 3.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.84F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), Side.BOTH)
                        ));

        AVAnimations.BOW_AUTO_4 = builder.nextAccessor("biped/epic_agc/bow_auto4",
                (accessor) -> new BowAttackAnimation(0.05F, 0, 2.12F, 2.733F, 1.2F, InteractionHand.MAIN_HAND, null, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.2083F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), Side.BOTH),
                                AnimationEvent.InTimeEvent.create(1.7916F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), Side.BOTH),
                                AnimationEvent.InTimeEvent.create(2.0416F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), Side.BOTH))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (anim, entity, a, b, c) -> 3.0F));

        AVAnimations.BOW_AUTO_5 = builder.nextAccessor("biped/epic_agc/bow_auto5",
                (accessor) -> new BowAttackAnimation(0.02F, 0, 0.2F, 1.51F, 1.2F, InteractionHand.MAIN_HAND, null, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (anim, entity, a, b, c) -> 3.0F)
                        .addEvents(AnimationEvent.InTimeEvent.create(0.7083F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), Side.BOTH)
                        ));

        // Animation from Epicfight Valour Guard
        AVAnimations.VALOUR_HOLD_GREATSWORD = builder.nextAccessor("biped/epicfight_valour_guard/valour_hold_greatsword",
                (accessor) -> new StaticAnimation(true, accessor, Armatures.BIPED));
        AVAnimations.VALOUR_RUN_GREATSWORD = builder.nextAccessor("biped/epicfight_valour_guard/valour_run_greatsword",
                (accessor) -> new MovementAnimation(true, accessor, Armatures.BIPED));
        AVAnimations.VALOUR_WALK_GREATSWORD = builder.nextAccessor("biped/epicfight_valour_guard/valour_walk_greatsword",
                (accessor) -> new MovementAnimation(true, accessor, Armatures.BIPED));

        // Animation from EpicFight x Iron's Spells
        AVAnimations.CASTING_ONE_HAND_TOP = builder.nextAccessor("biped/epicfight_ironspell/casting_one_hand_top",
                (accessor) -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));
        AVAnimations.CASTING_ONE_HAND_INWARD = builder.nextAccessor("biped/epicfight_ironspell/casting_one_hand_inward",
                (accessor) -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));
        AVAnimations.CASTING_ONE_HAND_BUFF = builder.nextAccessor("biped/epicfight_ironspell/casting_one_hand_buff",
                (accessor) -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));

        // Animation from Pugilist Steve Annoying Villagers 1.18.2
        AVAnimations.COUNTER = builder.nextAccessor("biped/pugilist_steve/counter",
                (accessor) -> (new BasicMultipleAttackAnimation(0.3F, 0.08F, 0.1F, 0.15F, 0.525F, ColliderPreset.FIST, humanoidArmature.get().legR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.COUNTER))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.5F))
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F)));
        AVAnimations.LAY_IDLE = builder.nextAccessor("biped/pugilist_steve/lay",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature));
        AVAnimations.PUSH_UP_IDLE = builder.nextAccessor("biped/pugilist_steve/push_up",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature));
        AVAnimations.SIT_IDLE = builder.nextAccessor("biped/pugilist_steve/sit",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature));
        AVAnimations.SLIGHT_IDLE = builder.nextAccessor("biped/pugilist_steve/slight",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature));
        AVAnimations.FIST_GUARD = builder.nextAccessor("biped/pugilist_steve/fist_guard",
                (accessor) -> new StaticAnimation(false, accessor, humanoidArmature));
        AVAnimations.NULL_GUARD = builder.nextAccessor("biped/pugilist_steve/null_guard",
                (accessor) -> new StaticAnimation(false, accessor, humanoidArmature));
        AVAnimations.FIST_DASH = builder.nextAccessor("biped/pugilist_steve/fist_dash",
                (accessor) -> (new BasicMultipleAttackAnimation(0.15F, 0.25F, 0.45F, 0.7F, 0.95F, ColliderPreset.BIPED_BODY_COLLIDER, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.WHIRLWIND_KICK = builder.nextAccessor("biped/pugilist_steve/whirlwind_kick",
                (accessor) -> (new BasicMultipleAttackAnimation(0.2F, 0.29F, 0.45F, 0.85F, 1.8F, ColliderPreset.BIPED_BODY_COLLIDER, humanoidArmature.get().legR, accessor, humanoidArmature)).addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST).addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get()).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addEvents(
                                new AnimationEvent.InTimeEvent[]{
                                        AnimationEvent.InTimeEvent.create(0.1F, ReusableSources.PLAY_SOUND, Side.SERVER)
                                                .params(EpicFightSounds.WHOOSH.get())})
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.LEGENDARY_SWORD_HEAVY_ATTACK = builder.nextAccessor("biped/pugilist_steve/legendary_sword_heavy_attack",
                (accessor) -> (new HeavyAttackAnimation(0.05F, 0.05F, 0.5F, 0.7F, 1.2F, WOMWeaponColliders.TORMENT_BERSERK_AIRSLAM, humanoidArmature.get().rootJoint, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
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
                                        AnimationEvent.InTimeEvent.create(0.6F, ReuseableEvents.LEGENDARY_SWORD_GROUND_SLAM, Side.CLIENT),
                                        AnimationEvent.InTimeEvent.create(0.6F, ReuseableEvents.SHOCK_WAVE, Side.SERVER)
                                })
        );
        AVAnimations.BLUE_DEMON_START_SKILL = builder.nextAccessor("biped/pugilist_steve/blue_demon_start_skill",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature));
        AVAnimations.BLUE_DEMON_END_SKILL = builder.nextAccessor("biped/pugilist_steve/blue_demon_end_skill",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature));
        AVAnimations.HACKER_SWORD_SKILL = builder.nextAccessor("biped/pugilist_steve/hacker_sword_skill",
                (accessor) -> (new AttackAnimation(0.05F, accessor, humanoidArmature, new Phase(0.0F, 0.016F, 0.066F, 0.133F, 0.133F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolL, ColliderPreset.SWORD), new Phase(0.133F, 0.133F, 0.183F, 0.25F, 0.25F, humanoidArmature.get().toolR, ColliderPreset.SWORD), new Phase(0.25F, 0.25F, 0.3F, 0.366F, 0.366F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolL, ColliderPreset.SWORD), new Phase(0.366F, 0.366F, 0.416F, 0.483F, 0.483F, humanoidArmature.get().toolR, ColliderPreset.SWORD), new Phase(0.483F, 0.483F, 0.533F, 0.6F, 0.6F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolL, ColliderPreset.SWORD), new Phase(0.6F, 0.6F, 0.65F, 0.716F, 0.716F, humanoidArmature.get().toolR, ColliderPreset.SWORD), new Phase(0.716F, 0.716F, 0.766F, 0.833F, 0.833F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolL, ColliderPreset.SWORD), new Phase(0.833F, 0.833F, 0.883F, 1.1F, 1.1F, humanoidArmature.get().toolR, ColliderPreset.SWORD), new Phase(0.933F, 1.133F, 1.183F, 1.6F, 1.6F, humanoidArmature.get().toolL, ColliderPreset.SWORD))).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 4.0F));
        AVAnimations.DUAL_SWORD_AUTO1 = builder.nextAccessor("biped/pugilist_steve/dual_sword_auto1",
                (accessor) -> (new BasicAttackAnimation(0.1F, accessor, humanoidArmature, new Phase(0.0F, 0.5F, 0.63F, 0.667F, 0.667F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null)))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        AVAnimations.DUAL_SWORD_AUTO2 = builder.nextAccessor("biped/pugilist_steve/dual_sword_auto2",
                (accessor) -> (new BasicAttackAnimation(0.15F, accessor, humanoidArmature, new Phase(0.0F, 0.5F, 0.63F, 0.667F, 0.667F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null)))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        AVAnimations.DUAL_SWORD_AUTO3 = builder.nextAccessor("biped/pugilist_steve/dual_sword_auto3",
                (accessor) -> (new BasicAttackAnimation(0.16F, accessor, humanoidArmature, new Phase(0.0F, 0.66F, 0.69F, 0.733F, 1.0F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null)))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        AVAnimations.DUAL_SWORD_AUTO4 = builder.nextAccessor("biped/pugilist_steve/dual_sword_auto4",
                (accessor) -> (new BasicAttackAnimation(0.1F, accessor, humanoidArmature, new Phase(0.0F, 0.633F, 0.69F, 0.8F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null)))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));
        AVAnimations.DUAL_SWORD_AUTO5 = builder.nextAccessor("biped/pugilist_steve/dual_sword_auto5",
                (accessor) -> (new BasicAttackAnimation(0.1F, accessor, humanoidArmature, new Phase(0.0F, 0.633F, 0.69F, 0.8F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null)))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));
        AVAnimations.DUAL_SWORD1 = builder.nextAccessor("biped/pugilist_steve/dual_auto1",
                (accessor) -> (new BasicAttackAnimation(0.1F, accessor, humanoidArmature, new Phase(0.0F, 0.05F, 0.3F, 0.4F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolL, null), new Phase(0.1F, 0.1F, 0.4F, 0.6F, 0.6F, humanoidArmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.HIT_PRIORITY, Priority.TARGET)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.5F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        AVAnimations.DUAL_SWORD2 = builder.nextAccessor("biped/pugilist_steve/dual_auto2",
                (accessor) -> (new BasicAttackAnimation(0.1F, accessor, humanoidArmature, new Phase(0.0F, 0.05F, 0.4F, 0.8F, 1.167F, 2.5F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new Phase(0.2F, 0.1F, 1.2F, 1.3F, 1.5F, humanoidArmature.get().toolR, null), new Phase(0.2F, 0.1F, 1.4F, 1.5F, 2.1F, humanoidArmature.get().toolL, null)))
                        .addProperty(AttackPhaseProperty.HIT_PRIORITY, Priority.TARGET)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.5F));
        AVAnimations.DUAL_SWORD3 = builder.nextAccessor("biped/pugilist_steve/dual_auto3",
                (accessor) -> (new BasicAttackAnimation(0.1F, 0.0F, 0.0F, 0.06F, 0.3F, ColliderPreset.SWORD, humanoidArmature.get().rootJoint, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.HIT_PRIORITY, Priority.TARGET)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        AVAnimations.DEATH_IDLE = builder.nextAccessor("biped/pugilist_steve/death_idle",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature));
        AVAnimations.KICK_H = builder.nextAccessor("biped/pugilist_steve/kick_h",
                (accessor) -> (new KickAttackAnimation(0.1F, 0.05F, 0.4F, 0.4F, 0.8F, WOMWeaponColliders.KICK, humanoidArmature.get().legR, accessor, humanoidArmature)).addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(5.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.KICK_1 = builder.nextAccessor("biped/pugilist_steve/kick_1",
                (accessor) -> (new KickAttackAnimation(0.1F, 0.05F, 0.4F, 0.4F, 0.5F, WOMWeaponColliders.KICK, humanoidArmature.get().legR, accessor, humanoidArmature)).addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(3.0F))
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.KICK_2 = builder.nextAccessor("biped/pugilist_steve/kick_2",
                (accessor) -> (new KickAttackAnimation(0.1F, 0.05F, 0.4F, 0.4F, 0.4F, WOMWeaponColliders.KICK, humanoidArmature.get().legL, accessor, humanoidArmature)).addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get()).addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(2.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.KICK_3 = builder.nextAccessor("biped/pugilist_steve/kick_3",
                (accessor) -> (new KickAttackAnimation(0.05F, 0.05F, 0.3F, 0.4F, 0.6F, WOMWeaponColliders.KICK, humanoidArmature.get().legR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(5.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.KICK_4 = builder.nextAccessor("biped/pugilist_steve/kick_4",
                (accessor) -> (new KickAttackAnimation(0.1F, 0.05F, 0.4F, 0.4F, 0.4F, WOMWeaponColliders.KICK, humanoidArmature.get().legL, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.KICK_RUSH = builder.nextAccessor("biped/pugilist_steve/kick_rush",
                (accessor) -> (new KickAttackAnimation(0.05F, 0.05F, 0.1F, 0.4F, 0.6F, WOMWeaponColliders.KICK, humanoidArmature.get().legR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, AVSounds.KICK.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(6.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.FIST_UP = builder.nextAccessor("biped/pugilist_steve/fist_up",
                (accessor) -> (new BasicMultipleAttackAnimation(0.15F, 0.25F, 0.45F, 0.85F, 0.95F, WOMWeaponColliders.KICK, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, AVSounds.KICK.get())
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.RUSH_SWORD = builder.nextAccessor("biped/pugilist_steve/rush_sword",
                (accessor) -> (new RushSwordAnimation(
                        0.15F, 0.0F, 0.1F, 0.26F, 0.75F,
                        ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        AVAnimations.DUAL_DANCING_EDGE = builder.nextAccessor("biped/pugilist_steve/dancing_edge",
                (accessor) -> (new BasicMultipleAttackAnimation(0.25F, accessor, humanoidArmature, new Phase(0.0F, 0.2F, 0.31F, 0.4F, 0.4F, humanoidArmature.get().toolR, null), new Phase(0.4F, 0.5F, 0.61F, 0.65F, 0.65F, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null), new Phase(0.65F, 0.76F, 0.85F, 1.15F, Float.MAX_VALUE, humanoidArmature.get().toolR, ColliderPreset.DUAL_SWORD)))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 2)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F));
        AVAnimations.LEFT_KNOCKDOWN = builder.nextAccessor("biped/pugilist_steve/left_kd",
                (accessor) -> new KnockdownAnimation(0.08F, accessor, humanoidArmature));
        AVAnimations.SWEEPING_EDGE = builder.nextAccessor("biped/pugilist_steve/sweeping_edge",
                (accessor) -> (new AttackAnimation(0.2F, 0.1F, 0.35F, 0.46F, 0.79F, ColliderPreset.BIPED_BODY_COLLIDER, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.9F))
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.45F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        AVAnimations.HIT_C = builder.nextAccessor("biped/pugilist_steve/hit_c",
                (accessor) -> new LongHitAnimation(0.08F, accessor, humanoidArmature));
        AVAnimations.SPEAR_GUARD_HIT = builder.nextAccessor("biped/pugilist_steve/spear_guard_hit",
                (accessor) -> (new GuardAnimation(0.05F, 0.2F, accessor, humanoidArmature))
                        .addEvents(new AnimationEvent.InTimeEvent[]{
                                AnimationEvent.InTimeEvent.create(0.1F, ReuseableEvents.FAST_SPINING, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.FAST_SPINING, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.3F, ReuseableEvents.FAST_SPINING, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.4F, ReuseableEvents.FAST_SPINING, Side.CLIENT)}));
        AVAnimations.LEGENDARY_SWORD_GUARD = builder.nextAccessor("biped/pugilist_steve/legendary_sword_guard",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature));
        AVAnimations.LEGENDARY_SWORD_GUARD_HIT = builder.nextAccessor("biped/pugilist_steve/legendary_sword_guard_hit",
                (accessor) -> new GuardAnimation(0.05F, accessor, humanoidArmature));
        AVAnimations.LEGENDARY_SWORD_GUARD_PARRY = builder.nextAccessor("biped/pugilist_steve/legendary_sword_guard_parry",
                (accessor) -> new GuardAnimation(0.05F, accessor, humanoidArmature));
        AVAnimations.POSE_UP = builder.nextAccessor("biped/pugilist_steve/pose_up",
                (accessor) -> (new ActionAnimation(0.0F, 1.85F, accessor, humanoidArmature))
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true));
        AVAnimations.DAGGER_AUTO1 = builder.nextAccessor("biped/pugilist_steve/dagger_auto1",
                (accessor) -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.2F, null, humanoidArmature.get().toolR, accessor, humanoidArmature));
        AVAnimations.DAGGER_AUTO2 = builder.nextAccessor("biped/pugilist_steve/dagger_auto2",
                (accessor) -> new BasicAttackAnimation(0.08F, 0.0F, 0.1F, 0.2F, null, humanoidArmature.get().toolR, accessor, humanoidArmature));
        AVAnimations.DAGGER_AUTO3 = builder.nextAccessor("biped/pugilist_steve/dagger_auto3",
                (accessor) -> new BasicAttackAnimation(0.08F, 0.15F, 0.26F, 0.5F, null, humanoidArmature.get().toolR, accessor, humanoidArmature));
        AVAnimations.DAGGER_DUAL_AUTO1 = builder.nextAccessor("biped/pugilist_steve/dagger_dual_auto1",
                (accessor) -> new BasicAttackAnimation(0.08F, 0.05F, 0.16F, 0.25F, null, humanoidArmature.get().toolR, accessor, humanoidArmature));
        AVAnimations.DAGGER_DUAL_AUTO2 = builder.nextAccessor("biped/pugilist_steve/dagger_dual_auto2",
                (accessor) -> new BasicAttackAnimation(0.08F, 0.0F, 0.11F, 0.16F, InteractionHand.OFF_HAND, null, humanoidArmature.get().toolL, accessor, humanoidArmature));
        AVAnimations.DAGGER_DUAL_AUTO3 = builder.nextAccessor("biped/pugilist_steve/dagger_dual_auto3",
                (accessor) -> new BasicAttackAnimation(0.08F, 0.0F, 0.11F, 0.2F, null, humanoidArmature.get().toolR, accessor, humanoidArmature));
        AVAnimations.DAGGER_DUAL_AUTO4 = builder.nextAccessor("biped/pugilist_steve/dagger_dual_auto4",
                (accessor) -> new BasicAttackAnimation(0.13F, 0.1F, 0.21F, 0.4F, ColliderPreset.DUAL_DAGGER_DASH, humanoidArmature.get().toolR, accessor, humanoidArmature));
        AVAnimations.CHECK = builder.nextAccessor("biped/pugilist_steve/check",
                (accessor) -> new StaticAnimation(false, accessor, humanoidArmature));
        AVAnimations.BIPED_RUN_ESWORD = builder.nextAccessor("biped/pugilist_steve/run_esword",
                (accessor) -> new MovementAnimation(true, accessor, humanoidArmature));
        AVAnimations.KNIFE_IDLE = builder.nextAccessor("biped/pugilist_steve/knife_idle",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature));
        AVAnimations.KNIFE_RUN = builder.nextAccessor("biped/pugilist_steve/knife_run",
                (accessor) -> new MovementAnimation(true, accessor, humanoidArmature));
        AVAnimations.KNIFE_ATTACK = builder.nextAccessor("biped/pugilist_steve/knife_attack",
                (accessor) -> (new BasicAttackAnimation(0.15F, 0.01F, 0.2F, 0.5F, 0.6F, null, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addState(EntityState.MOVEMENT_LOCKED, false)
                        .addState(EntityState.TURNING_LOCKED, false)
                        .addState(EntityState.LOCKON_ROTATE, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.KNIFE_CHECK = builder.nextAccessor("biped/pugilist_steve/knife_check",
                (accessor) -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));
        AVAnimations.CARRY = builder.nextAccessor("biped/pugilist_steve/carry",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature));
        AVAnimations.FIST_LEFT = builder.nextAccessor("biped/pugilist_steve/fist_left",
                (accessor) -> (new BasicMultipleAttackAnimation(0.15F, 0.25F, 0.45F, 0.85F, 1.1F, ColliderPreset.FIST, humanoidArmature.get().toolL, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.KNOCKDOWN_FORWARD = builder.nextAccessor("biped/pugilist_steve/knockdown_forward",
                (accessor) -> (new LongHitAnimation(0.1F, accessor, humanoidArmature))
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.KNOCKDOWN_RIGHT = builder.nextAccessor("biped/pugilist_steve/knockdown_right",
                (accessor) -> (new LongHitAnimation(0.1F, accessor, humanoidArmature))
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.KNOCKDOWN_LEFT = builder.nextAccessor("biped/pugilist_steve/knockdown_left",
                (accessor) -> (new LongHitAnimation(0.1F, accessor, humanoidArmature))
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.AXE_HEAVY_AUTO_1 = builder.nextAccessor("biped/pugilist_steve/axe_heavy_auto1",
                (accessor) -> (new BasicMultipleAttackAnimation(0.1F, 0.15F, 0.3F, 0.6F, 0.95F, ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.5F));
        AVAnimations.AXE_HEAVY_AUTO_2 = builder.nextAccessor("biped/pugilist_steve/axe_heavy_auto2",
                (accessor) -> (new BasicMultipleAttackAnimation(0.1F, 0.15F, 0.8F, 1.2F, 1.95F, ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.4F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F));
        AVAnimations.SWORD_HEAVY_AUTO_1 = builder.nextAccessor("biped/pugilist_steve/sword_heavy_auto1",
                (accessor) -> (new BasicMultipleAttackAnimation(0.1F, 0.11F, 0.27F, 0.5F, 0.95F, ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.4F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        AVAnimations.SWORD_HEAVY_AUTO_2 = builder.nextAccessor("biped/pugilist_steve/sword_heavy_auto2",
                (accessor) -> (new BasicMultipleAttackAnimation(0.01F, 0.1F, 0.12F, 0.22F, 0.95F, ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.4F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        AVAnimations.SWORD_HEAVY_AUTO_3 = builder.nextAccessor("biped/pugilist_steve/sword_heavy_auto3",
                (accessor) -> (new BasicMultipleAttackAnimation(0.01F, 0.1F, 0.21F, 0.32F, 1.2F, ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.5F));
        AVAnimations.HARD_KICK = builder.nextAccessor("biped/pugilist_steve/hard_kick",
                (accessor) -> (new BasicMultipleAttackAnimation(0.1F, 0.29F, 1.1F, 1.2F, 3.1F, WOMWeaponColliders.TORMENT_BERSERK_AIRSLAM, humanoidArmature.get().legR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.HARD_KICK_HIT = builder.nextAccessor("biped/pugilist_steve/hard_kick_hit",
                (accessor) -> (new LongHitAnimation(0.1F, accessor, humanoidArmature))
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false).addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.RUN_START = builder.nextAccessor("biped/pugilist_steve/run_start",
                (accessor) -> (new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)).addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.LONGSWORD_AUTO1 = builder.nextAccessor("biped/pugilist_steve/tachi_auto1",
                (accessor) -> (new BasicMultipleAttackAnimation(0.1F, 0.15F, 0.2F, 0.3F, 0.75F, null, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        AVAnimations.RUN_HOLD = builder.nextAccessor("biped/pugilist_steve/run_hold",
                (accessor) -> new MovementAnimation(true, accessor, humanoidArmature));
        AVAnimations.RUN_DUAL_BIG = builder.nextAccessor("biped/pugilist_steve/run_dual_big",
                (accessor) -> new MovementAnimation(true, accessor, humanoidArmature));
        AVAnimations.LONGEST_HIT = builder.nextAccessor("biped/pugilist_steve/longest_hit",
                (accessor) -> (new LongHitAnimation(0.1F, accessor, humanoidArmature))
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.HARD_GREATSWORD_GUARD = builder.nextAccessor("biped/pugilist_steve/hard_greatsword",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature));
        AVAnimations.HARD_GREATSWORD_GUARD_HIT = builder.nextAccessor("biped/pugilist_steve/hard_greatsword_hit",
                (accessor) -> new GuardAnimation(0.05F, accessor, humanoidArmature));
        AVAnimations.HARD_GREATSWORD_GUARD_SKILL = builder.nextAccessor("biped/pugilist_steve/hard_greatsword_skill",
                (accessor) -> (new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.HIT_LEFT = builder.nextAccessor("biped/pugilist_steve/hit_left",
                (accessor) -> (new ActionAnimation(0.1F, accessor, humanoidArmature))
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.HIT_RIGHT = builder.nextAccessor("biped/pugilist_steve/hit_right",
                (accessor) -> (new ActionAnimation(0.1F, accessor, humanoidArmature))
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.SHAKE_HAND_TRY = builder.nextAccessor("biped/pugilist_steve/shake_hand_try",
                (accessor) -> (new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature))
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.SHAKE_HAND = builder.nextAccessor("biped/pugilist_steve/shake_hand",
                (accessor) -> (new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature))
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.FUNNY_IDLE = builder.nextAccessor("biped/pugilist_steve/funny_idle",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature));
        AVAnimations.FIST_TRY = builder.nextAccessor("biped/pugilist_steve/fist_try",
                (accessor) -> (new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature))
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.FISTING = builder.nextAccessor("biped/pugilist_steve/fisting",
                (accessor) -> (new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature))
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addEvents(
                                new AnimationEvent.InTimeEvent[]{
                                        AnimationEvent.InTimeEvent.create(0.15F, ReusableSources.PLAY_SOUND, Side.SERVER).params(EpicFightSounds.WHOOSH.get())})
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.GIANT_WHIRLWIND = builder.nextAccessor("biped/pugilist_steve/giant_whirlwind",
                (accessor) -> (new BasicAttackAnimation(0.41F, accessor, humanoidArmature, new Phase(0.0F, 0.3F, 0.35F, 0.55F, 0.9F, 0.9F, humanoidArmature.get().toolL, null), new Phase(0.9F, 0.95F, 1.05F, 1.2F, 1.5F, 1.5F, humanoidArmature.get().toolL, null), (new Phase(1.5F, 1.65F, 1.75F, 1.95F, 2.5F, Float.MAX_VALUE, humanoidArmature.get().toolL, ColliderPreset.GREATSWORD))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)))
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        AVAnimations.DUAL_SWORD_DANCING_EDGE = builder.nextAccessor("biped/pugilist_steve/dual_sword_dancing_edge",
                (accessor) -> (new BasicAttackAnimation(0.25F, accessor, humanoidArmature, new Phase(0.0F, 0.2F, 0.31F, 0.4F, 0.4F, humanoidArmature.get().toolR, null), new Phase(0.4F, 0.5F, 0.61F, 0.65F, 0.65F, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null), new Phase(0.65F, 0.76F, 0.85F, 1.15F, Float.MAX_VALUE, humanoidArmature.get().toolR, ColliderPreset.DUAL_SWORD)))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG, 2)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F));
        AVAnimations.SPEAR_THRUST = builder.nextAccessor("biped/pugilist_steve/spear_thrust",
                (accessor) -> (new AttackAnimation(0.11F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.3F, 0.36F, 0.5F, 0.5F, humanoidArmature.get().toolL, ColliderPreset.SPEAR),
                        new Phase(0.5F, 0.5F, 0.56F, 0.75F, 0.75F, humanoidArmature.get().toolL, ColliderPreset.SPEAR),
                        new Phase(0.75F, 0.75F, 0.81F, 1.05F, Float.MAX_VALUE, humanoidArmature.get().toolL, ColliderPreset.SPEAR)))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));
        AVAnimations.DUAL_TACHI_GUARD = builder.nextAccessor("biped/pugilist_steve/dual_tachi_guard",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature));
        AVAnimations.DUAL_TACHI_GUARD_HIT = builder.nextAccessor("biped/pugilist_steve/dual_tachi_guard_hit",
                (accessor) -> new GuardAnimation(0.05F, accessor, humanoidArmature));
        AVAnimations.WHIRLWIND_KICK_LEFT = builder.nextAccessor("biped/pugilist_steve/whirlwind_kick_left",
                (accessor) -> (new BasicMultipleAttackAnimation(0.1F, 0.3F, 0.7F, 0.9F, Float.MAX_VALUE, ColliderPreset.BIPED_BODY_COLLIDER, humanoidArmature.get().legL, accessor, humanoidArmature))
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
        AVAnimations.SUPER_PUNCH = builder.nextAccessor("biped/pugilist_steve/super_punch",
                (accessor) -> (new KickAttackAnimation(0.05F, 1.0F, 1.25F, 1.4F, Float.MAX_VALUE, ColliderPreset.BIPED_BODY_COLLIDER, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.GUARD_BREAK_ATTACK = builder.nextAccessor("biped/pugilist_steve/guard_break_attack",
                (accessor) -> new LongHitAnimation(0.05F, accessor, humanoidArmature));
        AVAnimations.SWORD_DASH = builder.nextAccessor("biped/pugilist_steve/sword_dash",
                (accessor) -> (new DashAttackAnimation(0.12F, 0.1F, 0.25F, 0.4F, 0.65F, null, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F));
        AVAnimations.TACHI_DASH = builder.nextAccessor("biped/pugilist_steve/tachi_dash",
                (accessor) -> (new DashAttackAnimation(0.15F, 0.1F, 0.2F, 0.45F, 0.7F, null, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG));
        AVAnimations.DUAL_SWORD_SKILL = builder.nextAccessor("biped/pugilist_steve/dual_sword_skill",
                (accessor) -> (new BasicAttackAnimation(0.05F, accessor, humanoidArmature, (new Phase(0.0F, 0.15F, 0.25F, 0.25F, 0.25F, humanoidArmature.get().toolR, null)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(0.25F, 0.25F, 0.4F, 0.5F, 0.5F, humanoidArmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(0.5F, 0.5F, 0.6F, 0.6F, 0.6F, humanoidArmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(0.6F, 0.6F, 0.75F, 0.75F, 0.75F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(0.75F, 0.75F, 0.8F, 0.9F, 0.9F, humanoidArmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(0.9F, 0.9F, 1.0F, 1.0F, 1.0F, humanoidArmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(1.0F, 1.0F, 1.1F, 1.1F, 1.1F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(1.1F, 1.1F, 1.22F, 1.22F, 1.22F, humanoidArmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(1.22F, 1.22F, 1.35F, 1.35F, 1.35F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(1.35F, 1.35F, 1.42F, 1.42F, 1.42F, humanoidArmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(1.42F, 1.42F, 1.5F, 1.5F, 1.5F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(1.5F, 1.5F, 1.6F, 1.6F, 1.6F, humanoidArmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(1.6F, 1.6F, 1.7F, 1.7F, 1.7F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(1.7F, 1.7F, 1.8F, 1.8F, 1.8F, humanoidArmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(1.8F, 1.8F, 1.9F, 1.9F, 1.9F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new Phase(1.9F, 2.0F, 2.2F, Float.MAX_VALUE, Float.MAX_VALUE, humanoidArmature.get().toolL, null))
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
                                        AnimationEvent.InTimeEvent.create(2.5F, ReuseableEvents.END_ATTACK, Side.BOTH)})
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.DUAL_END = builder.nextAccessor("biped/pugilist_steve/dual_back_end",
                (accessor) -> (new ActionAnimation(0.2F, Float.MAX_VALUE, accessor, humanoidArmature))
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.TRIED = builder.nextAccessor("biped/pugilist_steve/tried",
                (accessor) -> (new LongHitAnimation(0.2F, accessor, humanoidArmature))
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.GREATSWORD_SKILL = builder.nextAccessor("biped/pugilist_steve/greatsword_skill",
                (accessor) -> (new BasicAttackAnimation(0.05F, accessor, humanoidArmature, (new Phase(0.0F, 0.1F, 0.25F, 0.25F, 0.25F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(0.25F, 0.25F, 0.4F, 0.5F, 0.5F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(0.5F, 0.5F, 0.6F, 0.6F, 0.6F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(0.6F, 0.6F, 0.75F, 0.75F, 0.75F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(0.75F, 0.75F, 0.8F, 0.9F, 0.9F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(0.9F, 0.9F, 1.0F, 1.0F, 1.0F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.0F, 1.0F, 1.1F, 1.1F, 1.1F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.1F, 1.1F, 1.22F, 1.22F, 1.22F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.22F, 1.22F, 1.35F, 1.35F, 1.35F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.35F, 1.35F, 1.42F, 1.42F, 1.42F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.42F, 1.42F, 1.5F, 1.5F, 1.5F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.5F, 1.5F, 1.6F, 1.6F, 1.6F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.6F, 1.6F, 1.7F, 1.7F, 1.7F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.7F, 1.7F, 1.8F, 1.85F, 1.85F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.85F, 1.85F, 2.2F, Float.MAX_VALUE, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)))
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true).addState(EntityState.CAN_SKILL_EXECUTION, false).addState(EntityState.CAN_BASIC_ATTACK, false).addState(EntityState.MOVEMENT_LOCKED, true).addState(EntityState.TURNING_LOCKED, false).addState(EntityState.LOCKON_ROTATE, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.LEGENDARY_SWORD_WAKE_UP_ATTACK = builder.nextAccessor("biped/pugilist_steve/legendary_sword_wake_up_attack",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature, new Phase(0.0F, 0.15F, 0.4F, 0.45F, 0.45F, humanoidArmature.get().toolR, null), new Phase(0.45F, 0.5F, 0.8F, Float.MAX_VALUE, Float.MAX_VALUE, humanoidArmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, false));
        AVAnimations.DUAL_E_END = builder.nextAccessor("biped/pugilist_steve/dual_e_end",
                (accessor) -> (new ActionAnimation(0.2F, Float.MAX_VALUE, accessor, humanoidArmature))
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.AXE_FUN_SKILL = builder.nextAccessor("biped/pugilist_steve/axe_fun_skill",
                (accessor) -> (new BasicAttackAnimation(0.05F, accessor, humanoidArmature, (new Phase(0.0F, 0.1F, 0.25F, 0.25F, 0.25F, humanoidArmature.get().toolR, ColliderPreset.SWORD))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(0.25F, 0.25F, 0.4F, 0.5F, 0.5F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(0.5F, 0.5F, 0.6F, 0.6F, 0.6F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(0.6F, 0.6F, 0.75F, 0.75F, 0.75F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(0.75F, 0.75F, 0.8F, 0.9F, 0.9F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(0.9F, 0.9F, 1.0F, 1.0F, 1.0F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.0F, 1.0F, 1.1F, 1.1F, 1.1F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.1F, 1.1F, 1.22F, 1.22F, 1.22F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.22F, 1.22F, 1.35F, 1.35F, 1.35F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.35F, 1.35F, 1.42F, 1.42F, 1.42F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.42F, 1.42F, 1.5F, 1.5F, 1.5F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.5F, 1.5F, 1.55F, 1.55F, 1.55F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), (new Phase(1.55F, 1.6F, 1.7F, Float.MAX_VALUE, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)))
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true).addState(EntityState.CAN_SKILL_EXECUTION, false).addState(EntityState.CAN_BASIC_ATTACK, false).addState(EntityState.MOVEMENT_LOCKED, true).addState(EntityState.TURNING_LOCKED, false).addState(EntityState.LOCKON_ROTATE, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AVAnimations.LEGENDARY_SWORD_AUTO_4 = builder.nextAccessor("biped/pugilist_steve/legendary_sword_auto_4",
                (accessor) -> (new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature, new Phase(0.0F, 0.2F, 0.4F, 0.45F, 0.45F, humanoidArmature.get().toolR, null),
                        new Phase(0.45F, 0.55F, 0.7F, 0.7F, Float.MAX_VALUE, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F), 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.45F, ReuseableEvents.LEGENDARY_SWORD_GROUND_SLAM, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.2F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        ));
        AVAnimations.OBSIDIAN_FIST_DASH = builder.nextAccessor("biped/pugilist_steve/obsidian_fist_dash",
                (accessor) -> (new BasicMultipleAttackAnimation(0.15F, 0.25F, 0.45F, 0.7F, 0.95F, ColliderPreset.BIPED_BODY_COLLIDER, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.6F, ReuseableEvents.THROW_OBSIDIAN, Side.SERVER)
                        ));
        AVAnimations.OBSIDIAN_WHIRLWIND_KICK = builder.nextAccessor("biped/pugilist_steve/obsidian_whirlwind_kick",
                (accessor) -> (new BasicMultipleAttackAnimation(0.2F, 0.29F, 0.45F, 0.85F, 1.8F, ColliderPreset.BIPED_BODY_COLLIDER, humanoidArmature.get().legR, accessor, humanoidArmature)).addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST).addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get()).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addEvents(
                                new AnimationEvent.InTimeEvent[]{
                                        AnimationEvent.InTimeEvent.create(0.1F, ReusableSources.PLAY_SOUND, Side.SERVER)
                                                .params(EpicFightSounds.WHOOSH.get())})
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.65F, ReuseableEvents.SUMMON_6_OBSIDIAN_LEG_RIGHT, Side.SERVER)
                        ));

        // Animation made by me
        AVAnimations.HEROBRINE_ANIMATE = builder.nextAccessor("biped/pla/herobrine_animate",
                (accessor) -> new StaticAnimation(false, accessor, humanoidArmature));
        AVAnimations.HEROBRINE_HEALING = builder.nextAccessor("biped/pla/herobrine_healing",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature));
        AVAnimations.HEROBRINE_SACRIFICING = builder.nextAccessor("biped/pla/herobrine_sacrificing",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature));
        AVAnimations.HEROBRINE_ASSISTANCE = builder.nextAccessor("biped/pla/herobrine_assistance",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature));
        AVAnimations.HEROBRINE_STAGE_CHANGE = builder.nextAccessor("biped/pla/herobrine_stage_change",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature));
        AVAnimations.LOW_CLONE_ESCAPE = builder.nextAccessor("biped/pla/low_clone_escape",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature));
        AVAnimations.PORTAL_SUMMON = builder.nextAccessor("biped/pla/portal_summon",
                (accessor) -> new StaticAnimation(false, accessor, humanoidArmature));
        AVAnimations.KNOCKED_ELITE = builder.nextAccessor("biped/pla/knocked_elite",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature));
        AVAnimations.EATING_ELITE_1 = builder.nextAccessor("biped/pla/eating_elite_1",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature));
        AVAnimations.EATING_ELITE_2 = builder.nextAccessor("biped/pla/eating_elite_2",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature));
        AVAnimations.EATING_ELITE_3 = builder.nextAccessor("biped/pla/eating_elite_3",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature));
        AVAnimations.EATING_ELITE_4 = builder.nextAccessor("biped/pla/eating_elite_4",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature));
        AVAnimations.SNAKE_BLADE = builder.nextAccessor("biped/pla/snake_blade",
                (accessor) -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.0F, (livingEntityPatch, self, p) -> {
                                    SnakeBladeHit.process(livingEntityPatch.getOriginal().getMainHandItem(), livingEntityPatch.getOriginal());
                                    livingEntityPatch.getOriginal().getMainHandItem().getOrCreateTag().putBoolean("SnakeAnimation", true);
                                }, Side.SERVER)
                        ));
        AVAnimations.SNAKE_BLADE_GUARD = builder.nextAccessor("biped/pla/snake_blade_guard",
                (accessor) -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.0F, (livingEntityPatch, self, p) -> {
                                    SnakeBladeHit.processGuard(livingEntityPatch.getOriginal().getMainHandItem(), livingEntityPatch.getOriginal());
                                    livingEntityPatch.getOriginal().getMainHandItem().getOrCreateTag().putBoolean("SnakeAnimation", true);
                                }, Side.SERVER)
                        ));
        AVAnimations.IDLE_BREAK = builder.nextAccessor("biped/pla/idle_break",
                (accessor) -> new StaticAnimation(false, accessor, humanoidArmature));
        AVAnimations.PLACE_BLOCK = builder.nextAccessor("biped/pla/place_block",
                (accessor) -> new ActionAnimation(0.0F, accessor, humanoidArmature));

        // Animation clone and re-registered from WOM
        AVAnimations.GLOWING_AGONY_GUARD = builder.nextAccessor("biped/wom_clone/glowing_agony_guard",
                (accessor) -> (new StaticAnimation(0.05F, true, accessor, humanoidArmature))
                        .addEvents(AnimationEvent.InTimeEvent.create(0.0F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.1F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.2F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.3F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.4F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.5F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.6F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.7F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT)));
        AVAnimations.ENDER_AEGIS_BULL_CHARGE = builder.nextAccessor("biped/wom_clone/ender_aegis_bull_charge",
                (accessor) -> (new BasicMultipleAttackAnimation(0.2F, accessor, humanoidArmature, new Phase(0.0F, 0.2F, 0.25F, 0.29F, 0.29F,
                        humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new Phase(0.29F, 0.3F, 0.35F, 0.39F, 0.39F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new Phase(0.39F, 0.4F, 0.45F, 0.49F, 0.49F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new Phase(0.49F, 0.5F, 0.55F, 0.59F, 0.59F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new Phase(0.59F, 0.6F, 0.65F, 0.69F, 0.69F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new Phase(0.69F, 0.7F, 0.75F, 0.79F, 0.79F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new Phase(0.79F, 0.8F, 0.85F, 0.89F, 0.89F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new Phase(0.89F, 1.0F, 1.1F, 1.3F, Float.MAX_VALUE,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP)))
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
        AVAnimations.CLONE_ANTITHEUS_AGRESSION = builder.nextAccessor("biped/wom_clone/clone_antitheus_agression",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.2F, 0.35F, 0.59F, 0.59F, humanoidArmature.get().toolR, WOMWeaponColliders.ANTITHEUS_AGRESSION),
                        new Phase(0.59F, 0.6F, 0.65F, 0.85F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_AGRESSION_REAP))).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
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
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        AVAnimations.CLONE_ANTITHEUS_GUILLOTINE = builder.nextAccessor("biped/wom_clone/clone_antitheus_guillotine",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.5F, 0.75F, 0.79F, 0.79F, humanoidArmature.get().toolR, null),
                        new Phase(0.79F, 0.8F, 1.0F, 1.1F, Float.MAX_VALUE, humanoidArmature.get().toolR, null)))
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
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null).addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        AVAnimations.CLONE_ANTITHEUS_AUTO_1 = builder.nextAccessor("biped/wom_clone/clone_antitheus_auto_1",
                (accessor) -> (new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.35F, 0.55F, 0.69F, 0.69F, humanoidArmature.get().toolR, null),
                        new Phase(0.69F, 0.7F, 0.9F, 0.9F, Float.MAX_VALUE, humanoidArmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.55F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.75F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT, 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackAnimationProperty.EXTRA_COLLIDERS, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false));
        AVAnimations.CLONE_ANTITHEUS_AUTO_2 = builder.nextAccessor("biped/wom_clone/clone_antitheus_auto_2",
                (accessor) -> (new BasicMultipleAttackAnimation(0.15F, 0.15F, 0.45F, 0.45F, null, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        AVAnimations.CLONE_ANTITHEUS_AUTO_3 = builder.nextAccessor("biped/wom_clone/clone_antitheus_auto_3",
                (accessor) -> (new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.15F, 0.35F, 0.5F, 0.5F, humanoidArmature.get().toolR, null),
                        new Phase(0.5F, 0.55F, 0.7F, 0.75F, Float.MAX_VALUE, humanoidArmature.get().toolR, null)))
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
        AVAnimations.CLONE_ANTITHEUS_AUTO_4 = builder.nextAccessor("biped/wom_clone/clone_antitheus_auto_4",
                (accessor) -> (new BasicMultipleAttackAnimation(0.15F, 0.5F, 0.75F, 0.9F, null, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                        .addProperty(AttackAnimationProperty.EXTRA_COLLIDERS, 2)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.2F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        AVAnimations.CLONE_ANTITHEUS_ASCENSION = builder.nextAccessor("biped/wom_clone/clone_antitheus_ascension",
                (accessor) -> (new AttackAnimation(0.1F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.5F, 0.6F, 0.65F, 0.65F, (humanoidArmature.get()).rootJoint, WOMWeaponColliders.PLUNDER_PERDITION),
                        new Phase(0.65F, 1.75F, 2.05F, 2.8F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.PLUNDER_PERDITION)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(4.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F))
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F), 1)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F), 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT, 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                        .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.0F));
        AVAnimations.NULL_SKELETON_ANTITHEUS_ASCENSION = builder.nextAccessor("biped/wom_clone/null_skeleton_antitheus_ascension",
                (accessor) -> (new AttackAnimation(0.1F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.5F, 0.6F, 0.65F, 0.65F, (humanoidArmature.get()).rootJoint, WOMWeaponColliders.PLUNDER_PERDITION),
                        new Phase(0.65F, 1.75F, 2.05F, 2.8F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.PLUNDER_PERDITION)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(4.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F))
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F), 1)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F), 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT, 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                        .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.0F)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.5F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        NullSkeletonEntity nullSkeletonEntity = new NullSkeletonEntity(AnnoyingVillagersModEntities.NULL_SKELETON.get(), serverLevel);
                                        LivingEntity owner = livingEntityPatch.getOriginal();
                                        Vec3 forward = getVec3(owner);

                                        double dist = 2.0D;
                                        Vec3 spawnPos = owner.position().add(forward.scale(dist));

                                        nullSkeletonEntity.moveTo(
                                                spawnPos.x,
                                                spawnPos.y,
                                                spawnPos.z,
                                                owner.getYRot(),
                                                owner.getXRot()
                                        );
                                        if (owner instanceof Player player) {
                                            nullSkeletonEntity.setPlayer(player);
                                        } else if (owner instanceof NullEntity nullEntity) {
                                            nullSkeletonEntity.setNullEntity(nullEntity);
                                        }

                                        nullSkeletonEntity.finalizeSpawn(serverLevel,
                                                serverLevel.getCurrentDifficultyAt(nullSkeletonEntity.blockPosition()),
                                                MobSpawnType.MOB_SUMMONED,
                                                null, null
                                        );
                                        serverLevel.addFreshEntity(nullSkeletonEntity);
                                        if (owner instanceof NullEntity nullEntity) {
                                            nullEntity.claimWitherSkeletonSlot(nullSkeletonEntity);
                                        }
                                        LivingEntityPatch<?> nullSkeletonPatch = EpicFightCapabilities.getEntityPatch(nullSkeletonEntity, LivingEntityPatch.class);
                                        if (nullSkeletonPatch != null) {
                                            nullSkeletonPatch.playAnimationSynchronized(AVAnimations.CLONE_ANTITHEUS_LAPSE, 0.0F);
                                        }
                                    }
                                }, Side.SERVER)
                        }));
        AVAnimations.CLONE_ANTITHEUS_LAPSE = builder.nextAccessor("biped/wom_clone/clone_antitheus_lapse",
                (accessor) -> (new AttackAnimation(0.1F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.65F, 0.75F, 0.8F, 0.8F, humanoidArmature.get().rootJoint, WOMWeaponColliders.PLUNDER_PERDITION),
                        new Phase(0.8F, 1.3F, 1.4F, 1.45F, 1.45F, humanoidArmature.get().rootJoint, WOMWeaponColliders.PLUNDER_PERDITION),
                        new Phase(1.45F, 1.75F, 1.85F, 2.3F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.PLUNDER_PERDITION)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(4.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F))
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, SoundEvents.WITHER_HURT)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(4.0F), 1)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F), 1)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, SoundEvents.WITHER_HURT, 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F), 2)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F), 2)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT, 2)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 2)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                        .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.0F));
        AVAnimations.CLONE_ANTITHEUS_ASCENDED_DEATHFALL = builder.nextAccessor("biped/wom_clone/clone_antitheus_ascended_deathfall",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, 0.5F, 0.55F, 0.75F, WOMWeaponColliders.ANTITHEUS_ASCENDED_DEATHFALL, humanoidArmature.get().rootJoint, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.75F))
                        .addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 0.7F, 0.7F);
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

                        }, Side.CLIENT), AnimationEvent.InTimeEvent.create(0.35F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().resetFallDistance();
                        }, Side.SERVER), AnimationEvent.InTimeEvent.create(0.45F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 0.7F, 0.7F);
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

                            float f = (float) livingEntityPatch.getOriginal().getX();
                            float f1 = (float) livingEntityPatch.getOriginal().getY();

                            for (int i = 0; i < 24; ++i) {
                                livingEntityPatch.getOriginal().level().addParticle(ParticleTypes.LARGE_SMOKE, livingEntityPatch.getOriginal().getX() + (double) ((new Random()).nextFloat() - 0.5F), livingEntityPatch.getOriginal().getY() + 2.200000047683716D, livingEntityPatch.getOriginal().getZ() + (double) ((new Random()).nextFloat() - 0.5F), ((new Random()).nextFloat() - 0.5F) * 0.05F, -((double) (new Random()).nextFloat() * (livingEntityPatch.getOriginal().getY() - (double) f1) * 0.4000000059604645D), ((new Random()).nextFloat() - 0.5F) * 0.05F);
                            }

                        }, Side.CLIENT), AnimationEvent.InTimeEvent.create(0.5F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().resetFallDistance();
                        }, Side.SERVER), AnimationEvent.InTimeEvent.create(0.55F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), SoundEvents.WITHER_SHOOT, SoundSource.NEUTRAL, 0.7F, 0.5F);
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.BLUNT_HIT_HARD.get(), SoundSource.NEUTRAL, 0.7F, 0.7F);

                            float f = (float) livingEntityPatch.getOriginal().getX();
                            float f1 = (float) livingEntityPatch.getOriginal().getY();

                            Vec3 vec3 = new Vec3(0.0D, (double) (f1 - 2.0F) - livingEntityPatch.getOriginal().getY(), 0.0D);

                            livingEntityPatch.getOriginal().move(MoverType.SELF, vec3);
                            byte b0 = 80;
                            double d0 = 0.6D;
                            double d1 = 0.01D;

                            for (int i = 0; i < b0; ++i) {
                                double d2 = 6.283185307179586D * (new Random()).nextDouble();
                                double d3 = ((new Random()).nextDouble() - 0.5D) * 3.141592653589793D * d1 / d0;
                                double d4 = d0 * Math.cos(d3) * Math.cos(d2);
                                double d5 = d0 * Math.cos(d3) * Math.sin(d2);
                                double d6 = d0 * Math.sin(d3);
                                float f3 = (new Random()).nextFloat() + 0.4F;
                                Vec3f vec3f = new Vec3f((float) d4 * f3, (float) d5 * f3, (float) d6 * f3);
                                OpenMatrix4f openmatrix4f = (new OpenMatrix4f()).rotate((float) Math.toRadians(90.0F), new Vec3f(1.0F, 0.0F, 0.0F));

                                OpenMatrix4f.transform3v(openmatrix4f, vec3f, vec3f);
                                livingEntityPatch.getOriginal().level().addParticle(ParticleTypes.LARGE_SMOKE, livingEntityPatch.getOriginal().getX() + (double) vec3f.x, (float) ((int) livingEntityPatch.getOriginal().getY()) + vec3f.y + 0.02F, livingEntityPatch.getOriginal().getZ() + (double) vec3f.z, vec3f.x, vec3f.y, vec3f.z);
                            }

                        }, Side.CLIENT), AnimationEvent.InTimeEvent.create(0.55F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().resetFallDistance();
                        }, Side.SERVER)})
        );
        AVAnimations.CLONE_ANTITHEUS_ASCENDED_BLINK = builder.nextAccessor("biped/wom_clone/clone_antitheus_ascended_blink", (accessor) -> (new BasicMultipleAttackAnimation(0.05F, 0.3F, 0.4F, 0.4F, WOMWeaponColliders.ANTITHEUS_ASCENDED_BLINK, humanoidArmature.get().rootJoint, accessor, humanoidArmature))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.4F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT.get())
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 0.0F)
                .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addEvents(AnimationEvent.InTimeEvent.create(0.05F, (entitypatch, self, params) -> {
                    entitypatch.getOriginal().level().playSound(null, entitypatch.getOriginal().blockPosition(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 0.7F, 0.7F);
                    entitypatch.getOriginal().level().playSound(null, entitypatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

                }, Side.CLIENT)));
        AVAnimations.CLONE_ANTITHEUS_ASCENDED_BLACKHOLE = builder.nextAccessor("biped/wom_clone/clone_antitheus_ascended_blackhole", (accessor) -> (new BasicMultipleAttackAnimation(0.05F, 1.45F, 1.5F, 1.7F, WOMWeaponColliders.PLUNDER_PERDITION, humanoidArmature.get().rootJoint, accessor, humanoidArmature))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(30.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.5F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
                .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT.get())
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 1.7F))
                .addEvents(AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), WOMSounds.ANTITHEUS_BLACKKHOLE_CHARGEUP.get(), SoundSource.PLAYERS, 2.0F, 1.0F), Side.SERVER), AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, self, params) -> {
                    OpenMatrix4f transformMatrix = livingEntityPatch.getArmature().getBoundTransformFor(livingEntityPatch.getAnimator().getPose(0.0F), Armatures.BIPED.get().toolL);
                    transformMatrix.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                    OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-org.joml.Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F), new Vec3f(0.0F, 1.0F, 0.0F)), transformMatrix, transformMatrix);
                    int n = 70;
                    double r = 5.0F;

                    for (int i = 0; i < n; ++i) {
                        double theta = (Math.PI * 2D) * (new Random()).nextDouble();
                        double phi = org.joml.Math.acos((double) 2.0F * (new Random()).nextDouble() - (double) 1.0F);
                        double x = r * org.joml.Math.sin(phi) * org.joml.Math.cos(theta);
                        double y = r * org.joml.Math.sin(phi) * org.joml.Math.sin(theta);
                        double z = r * org.joml.Math.cos(phi);
                        livingEntityPatch.getOriginal().level().addParticle(AnnoyingVillagersModParticleTypes.NULL.get(), (double) transformMatrix.m30 + livingEntityPatch.getOriginal().getX() + x, (double) transformMatrix.m31 + livingEntityPatch.getOriginal().getY() + y, (double) transformMatrix.m32 + livingEntityPatch.getOriginal().getZ() + z, (float) (-x * (double) 0.15F), (float) (-y * (double) 0.15F), (float) (-z * (double) 0.15F));
                    }

                }, Side.CLIENT), AnimationEvent.InTimeEvent.create(1.05F, (livingEntityPatch, self, params) -> {
                    livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 0.7F, 0.7F);
                    livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

                }, Side.CLIENT), AnimationEvent.InTimeEvent.create(1.45F, (livingEntityPatch, self, params) -> {
                    if (!(livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel)) return;

                    livingEntityPatch.getOriginal().level().playSound(
                            null,
                            livingEntityPatch.getOriginal(),
                            SoundEvents.WITHER_BREAK_BLOCK,
                            SoundSource.PLAYERS,
                            1.0F, 0.5F
                    );

                    OpenMatrix4f transformMatrix = livingEntityPatch.getArmature()
                            .getBoundTransformFor(livingEntityPatch.getAnimator().getPose(0.0F), Armatures.BIPED.get().handR);

                    OpenMatrix4f CORRECTION = new OpenMatrix4f()
                            .rotate(-org.joml.Math.toRadians(livingEntityPatch.getOriginal().yRotO + 180.0F), new Vec3f(0.0F, 1.0F, 0.0F));
                    CORRECTION.translate(new Vec3f(0.0F, 0.0F, -3.5F));
                    OpenMatrix4f.mul(CORRECTION, transformMatrix, transformMatrix);

                    serverLevel.sendParticles(WOMParticles.ANTITHEUS_BLACKHOLE_START.get(), (double) transformMatrix.m30 + livingEntityPatch.getOriginal().getX(), (double) transformMatrix.m31 + livingEntityPatch.getOriginal().getY(), (double) transformMatrix.m32 + livingEntityPatch.getOriginal().getZ(), 1, 0.0F, 0.0F, 0.0F, 0.0F);
                    serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, (double) transformMatrix.m30 + livingEntityPatch.getOriginal().getX(), (double) transformMatrix.m31 + livingEntityPatch.getOriginal().getY(), (double) transformMatrix.m32 + livingEntityPatch.getOriginal().getZ(), 48, 0.0F, 0.0F, 0.0F, 0.5F);
                }, Side.SERVER), AnimationEvent.InTimeEvent.create(1.45F, (livingEntityPatch, self, params) -> {
                    OpenMatrix4f transformMatrix = livingEntityPatch.getArmature().getBoundTransformFor(livingEntityPatch.getAnimator().getPose(0.0F), Armatures.BIPED.get().handR);
                    OpenMatrix4f CORRECTION = (new OpenMatrix4f()).rotate(-org.joml.Math.toRadians(livingEntityPatch.getOriginal().yRotO + 180.0F), new Vec3f(0.0F, 1.0F, 0.0F));
                    CORRECTION.translate(new Vec3f(0.0F, 0.0F, -3.5F));
                    OpenMatrix4f.mul(CORRECTION, transformMatrix, transformMatrix);
                    Level level = livingEntityPatch.getOriginal().level();
                    Vec3 FractureCenter = new Vec3((double) transformMatrix.m30 + livingEntityPatch.getOriginal().getX(), (double) transformMatrix.m31 + livingEntityPatch.getOriginal().getY() - (double) 2.0F, (double) transformMatrix.m32 + livingEntityPatch.getOriginal().getZ());
                    LevelUtil.circleSlamFracture(livingEntityPatch.getOriginal(), level, FractureCenter, 4.0F, true, true);
                }, Side.CLIENT)));
        AVAnimations.ENDER_AEGIS_MOONLESS_AUTO_1 = builder.nextAccessor("biped/wom_clone/ender_aegis_moonless_auto_1",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.25F, 0.45F, 0.5F, Float.MAX_VALUE, humanoidArmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SMALL.get())
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));
        AVAnimations.ENDER_AEGIS_MOONLESS_AUTO_2 = builder.nextAccessor("biped/wom_clone/ender_aegis_moonless_auto_2",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.8F, 1.0F, 1.0F, Float.MAX_VALUE, humanoidArmature.get().toolR, WOMWeaponColliders.MOONLESS_BYPASS)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(0.5F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.SHARPCUT_ANGLED_DOWN_LEFT_SLASH)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F));

        AVAnimations.YELLOW_SOLAR_AUTO_2 = builder.nextAccessor("biped/wom_clone/yellow_solar_auto_2",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, 0.65F, 0.8F, 1.0F, null, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.SOLAR_HIT_UP)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.8F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, Side.CLIENT)
                        ));
        AVAnimations.YELLOW_NAPOLEON_AUTO_3 = builder.nextAccessor("biped/wom_clone/yellow_napoleon_auto_3",
                (accessor) -> (new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.15F, 0.35F, 0.39F, 0.39F, humanoidArmature.get().toolR, null),
                        new Phase(0.39F, 0.5F, 0.7F, 0.74F, 0.74F, humanoidArmature.get().toolR, null),
                        new Phase(0.74F, 0.75F, 0.85F, 1.19F, 1.19F, humanoidArmature.get().toolR, null),
                        new Phase(1.19F, 1.2F, 2.2F, 2.25F, Float.MAX_VALUE, humanoidArmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.2F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.1F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 2)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 2)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F), 2)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 3)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F), 3)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.1F), 3)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL, 3)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.15F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(1.2F, 2.25F)).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, true).newTimePair(0.0F, 0.85F).addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(2.4F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        AVAnimations.YELLOW_NAPOLEON_AUSTERLITZ_SHOOT = builder.nextAccessor("biped/wom_clone/yellow_napoleon_austerlitz_shoot",
                (accessor) -> (new SpecialAttackAnimation(0.05F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.15F, 0.4F, 0.41F, 0.41F, humanoidArmature.get().toolR, null),
                        new Phase(0.41F, 0.85F, 1.05F, 1.15F, Float.MAX_VALUE, humanoidArmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 1.0F)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InPeriodEvent.create(0.0F, 0.4F, (entityPatch, self, params) -> {
                                    Level level = entityPatch.getOriginal().level();
                                    LivingEntity entity = entityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                                }, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(WOMAnimations.TORMENT_DASH, 0.0F);
                                    }
                                }, Side.SERVER)
                        })
        );
        AVAnimations.ENDER_AEGIS_NAPOLEON_RELOAD_1 = builder.nextAccessor("biped/wom_clone/ender_aegis_napoleon_reload_1",
                (accessor) -> (new SpecialAttackAnimation(0.15F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.15F, 0.25F, 0.3F, 0.3F, humanoidArmature.get().toolR, null),
                        new Phase(0.3F, 0.35F, 0.45F, 0.5F, 0.5F, humanoidArmature.get().toolR, null),
                        new Phase(0.5F, 0.55F, 0.65F, 0.7F, 0.7F, humanoidArmature.get().toolR, null),
                        new Phase(0.7F, 0.75F, 0.95F, 1.0F, 1.0F, humanoidArmature.get().toolR, null),
                        new Phase(1.0F, 1.05F, 1.2F, 1.25F, Float.MAX_VALUE, humanoidArmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F), 2)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 2)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 3)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 3)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 3)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 4)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 4)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 4)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 1.0F)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(2.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));

        AVAnimations.ENDER_GLAIVE_NAPOLEON_AUTO_1 = builder.nextAccessor("biped/wom_clone/ender_glaive_napoleon_auto_1",
                (accessor) -> (new BasicMultipleAttackAnimation(0.2F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.1F, 0.45F, 0.79F, 0.79F, humanoidArmature.get().toolR, null),
                        new Phase(0.79F, 0.8F, 1.0F, 1.05F, Float.MAX_VALUE, humanoidArmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.2F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.1F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AttackAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F).addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(2.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        AVAnimations.ENDER_GLAIVE_NAPOLEON_AUTO_2 = builder.nextAccessor("biped/wom_clone/ender_glaive_napoleon_auto_2",
                (accessor) -> (new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.5F, 0.6F, 0.64F, 0.64F, humanoidArmature.get().toolR, null),
                        new Phase(0.64F, 0.65F, 0.95F, 1.0F, Float.MAX_VALUE, humanoidArmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.2F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F).addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(2.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        AVAnimations.ENDER_GLAIVE_NAPOLEON_AUTO_4 = builder.nextAccessor("biped/wom_clone/ender_glaive_napoleon_auto_4",
                (accessor) -> (new BasicMultipleAttackAnimation(0.1F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.6F, 1.0F, 1.9F, Float.MAX_VALUE, humanoidArmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 1.2F))
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(2.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        AVAnimations.ENDER_GLAIVE_NAPOLEON_AUSTERLITZ = builder.nextAccessor("biped/wom_clone/ender_glaive_napoleon_austerlitz",
                (accessor) -> (new BasicMultipleAttackAnimation(0.1F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.05F, 0.1F, 0.14F, 0.14F, humanoidArmature.get().toolR, null),
                        new Phase(0.14F, 0.15F, 0.3F, 0.35F, 0.35F, humanoidArmature.get().toolR, null),
                        new Phase(0.35F, 0.45F, 0.55F, 0.59F, 0.59F, humanoidArmature.get().toolR, null),
                        new Phase(0.59F, 0.6F, 0.8F, 0.9F, Float.MAX_VALUE, humanoidArmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.5F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL, 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F), 2)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F), 3)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F), 3)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL, 3)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(2.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        AVAnimations.DEMONIAC_RUINE_AUTO_1 = builder.nextAccessor("biped/wom_clone/demoniac_ruine_auto_1",
                (accessor) -> (new BasicMultipleAttackAnimation(0.25F, 0.2F, 0.55F, 0.55F, null, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.75F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null).addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        AVAnimations.DEMONIAC_RUINE_AUTO_2 = builder.nextAccessor("biped/wom_clone/demoniac_ruine_auto_2",
                (accessor) -> (new BasicMultipleAttackAnimation(0.2F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.15F, 0.55F, 0.59F, 0.59F, humanoidArmature.get().toolR, null),
                        new Phase(0.59F, 0.6F, 0.85F, 0.95F, Float.MAX_VALUE, humanoidArmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.95F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F).addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        AVAnimations.DEMONIAC_RUINE_AUTO_4 = builder.nextAccessor("biped/wom_clone/demoniac_ruine_auto_4",
                (accessor) -> (new BasicMultipleAttackAnimation(0.25F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.5F, 0.6F, 0.65F, 0.65F, humanoidArmature.get().toolR, WOMWeaponColliders.RUINE_COMET),
                        new Phase(0.65F, 0.8F, 1.05F, 1.45F, Float.MAX_VALUE, humanoidArmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.4F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F), 1)
                        .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(WOMExtraDamageInstance.WOM_TARGET_CURRENT_HEALTH.create(1.0F)), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.4F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NEUTRALIZE, 1)
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE), 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        AVAnimations.DEMONIAC_RUINE_COMET = builder.nextAccessor("biped/wom_clone/demoniac_ruine_comet",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, 0.25F, 0.55F, 0.75F, WOMWeaponColliders.RUINE_COMET, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(WOMExtraDamageInstance.WOM_TARGET_CURRENT_HEALTH.create(0.5F)))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.8F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackAnimationProperty.EXTRA_COLLIDERS, 20)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.3F)).addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> {
                            if (elapsedTime >= 0.35F && elapsedTime < 0.45F) {
                                float dpx = (float) livingEntityPatch.getOriginal().getX();
                                float dpy = (float) livingEntityPatch.getOriginal().getY();
                                float dpz = (float) livingEntityPatch.getOriginal().getZ();

                                for (BlockState block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz))) {
                                    --dpy;
                                }

                                float distanceToGround = (float) org.joml.Math.max(org.joml.Math.abs(livingEntityPatch.getOriginal().getY() - (double) dpy) - (double) 1.0F, 0.0F);
                                LivingEntity livingentity = livingEntityPatch.getOriginal();
                                Vec3f direction = new Vec3f(2.5F, -0.25F, 0.0F);
                                OpenMatrix4f rotation = (new OpenMatrix4f()).rotate(-org.joml.Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 90.0F), new Vec3f(0.0F, 1.0F, 0.0F));
                                OpenMatrix4f.transform3v(rotation, direction, direction);
                                AABB box = AABB.ofSize(livingEntityPatch.getOriginal().getPosition(1.0F), 3.0F, 3.0F, 3.0F);
                                List<Entity> list = livingEntityPatch.getOriginal().level().getEntities(livingEntityPatch.getOriginal(), box);
                                if (distanceToGround > 0.5F && list.isEmpty()) {
                                    livingentity.move(MoverType.SELF, direction.toDoubleVector());
                                    return 0.05F;
                                } else {
                                    return speed;
                                }
                            } else {
                                return speed;
                            }
                        })
                        .addEvents(AnimationEvent.InTimeEvent.create(0.25F, reascer.wom.gameasset.ReuseableEvents.RUINE_COMET_AIRBURST, Side.CLIENT), AnimationEvent.InTimeEvent.create(0.5F, reascer.wom.gameasset.ReuseableEvents.RUINE_COMET_GROUNDTHRUST, Side.CLIENT))
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        AVAnimations.DEMONIAC_TORMENT_CHARGED_ATTACK_2 = builder.nextAccessor("biped/wom_clone/demoniac_torment_charged_attack_2",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, 0.25F, 0.4F, 1.0F, null, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.15F, 0.65F))
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false).addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));

        AVAnimations.AGONY_GUARD_HIT_1 = builder.nextAccessor("biped/wom_clone/agony_guard_hit1",
                (accessor) -> (new ActionAnimation(0.05F, 0.5F, accessor, humanoidArmature))
                        .addEvents(
                                new AnimationEvent[]{
                                        AnimationEvent.InTimeEvent.create(0.1F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, Side.CLIENT),
                                        AnimationEvent.InTimeEvent.create(0.2F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, Side.CLIENT),
                                        AnimationEvent.InTimeEvent.create(0.3F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, Side.CLIENT),
                                        AnimationEvent.InTimeEvent.create(0.4F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, Side.CLIENT)
                                }));
        AVAnimations.ENDER_GLAIVE_NAPOLEON_SHOOT_3 = builder.nextAccessor("biped/wom_clone/ender_glaive_napoleon_shoot_3",
                (accessor) -> (new SpecialAttackAnimation(0.2F, accessor, humanoidArmature, new Phase(0.0F, 0.3F, 0.4F, 0.44F, 0.44F, humanoidArmature.get().toolR, null),
                        new Phase(0.44F, 0.45F, 0.5F, 0.95F, Float.MAX_VALUE, humanoidArmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 1.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.5F, (livingEntityPatch, self, p) -> {
                                    Vec3 tipPos = EpicfightUtil.getJointWithTranslation(
                                            livingEntityPatch.getOriginal(),
                                            new Vec3f(0.0F, 0.0F, 0.0F),
                                            Armatures.BIPED.get().toolR,
                                            4.3F,
                                            2.3F
                                    );
                                    if (tipPos != null) {
                                        BlockPos mutePos = BlockPos.containing(tipPos);
                                        AnnoyingVillagers.PACKET_HANDLER.send(
                                                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(livingEntityPatch::getOriginal),
                                                new ClientboundMuteExplosionAtPos(mutePos, 4)
                                        );
                                        livingEntityPatch.getOriginal().level().explode(livingEntityPatch.getOriginal(), tipPos.x, tipPos.y, tipPos.z,
                                                2.0F, true, Level.ExplosionInteraction.TNT);
                                        Vec3 glaivePos = EpicfightUtil.getJointWithTranslation(livingEntityPatch.getOriginal(), new Vec3f(0, 0, 0),
                                                Armatures.BIPED.get().toolR, 1.3F, 2.3F);
                                        Vec3 explosionPos = EpicfightUtil.getJointWithTranslation(livingEntityPatch.getOriginal(), new Vec3f(0, 0, 0),
                                                Armatures.BIPED.get().toolR, 10.3F, 2.3F);
                                        AnnoyingVillagers.PACKET_HANDLER.send(
                                                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(livingEntityPatch::getOriginal),
                                                new ClientboundGlaiveExplosionFx(glaivePos, explosionPos)
                                        );
                                        if (explosionPos != null) {
                                            livingEntityPatch.getOriginal().level().playSound(null, new BlockPos((int) explosionPos.x, (int) explosionPos.y, (int) explosionPos.z), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_shot"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                        }
                                    }
                                }, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER))
        );
        AVAnimations.ENDER_GLAIVE_AGONY_AUTO_1 = builder.nextAccessor("biped/wom_clone/ender_glaive_agony_auto_1",
                (accessor) -> (new BasicMultipleAttackAnimation(0.1F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.15F, 0.25F, 0.3F, 0.3F, humanoidArmature.get().toolR, null),
                        new Phase(0.3F, 0.55F, 0.65F, 0.7F, Float.MAX_VALUE, humanoidArmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.29F), 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                        .addProperty(ActionAnimationProperty.COORD_SET_TICK, (self, livingEntityPatch, transformSheet) -> {
                            LivingEntity attackTarget = livingEntityPatch.getTarget();
                            if (!(Boolean) self.getRealAnimation().get().getProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE).orElse(false) && attackTarget != null) {
                                TransformSheet transform = self.getTransfroms().get("Root").copyAll();
                                Keyframe[] keyframes = transform.getKeyframes();
                                int startFrame = 0;
                                int endFrame = transform.getKeyframes().length - 1;
                                Vec3f keyLast = keyframes[endFrame].transform().translation();
                                Vec3 pos = livingEntityPatch.getOriginal().getEyePosition();
                                Vec3 targetPos = attackTarget.position().add(attackTarget.getDeltaMovement().scale(8.0F));
                                float horizontalDistance = org.joml.Math.max((float) targetPos.subtract(pos).horizontalDistance() - (attackTarget.getBbWidth() + livingEntityPatch.getOriginal().getBbWidth()), 0.0F);
                                Vec3f worldPosition = new Vec3f(keyLast.x, 0.0F, -horizontalDistance);
                                float scale = org.joml.Math.min(worldPosition.length() / keyLast.length(), 2.0F);

                                for (int i = startFrame; i <= endFrame; ++i) {
                                    Vec3f translation = keyframes[i].transform().translation();
                                    translation.z *= scale;
                                }

                                transformSheet.readFrom(transform);
                            } else {
                                transformSheet.readFrom(self.getTransfroms().get("Root"));
                            }

                        }).addEvents(
                                AnimationEvent.InTimeEvent.create(0.3F, (livingEntityPatch, self, p) -> {
                                    Vec3 tipPos = EpicfightUtil.getJointWithTranslation(
                                            livingEntityPatch.getOriginal(),
                                            new Vec3f(0.0F, 0.0F, 0.0F),
                                            Armatures.BIPED.get().toolR,
                                            4.3F,
                                            2.3F
                                    );
                                    if (tipPos != null) {
                                        BlockPos mutePos = BlockPos.containing(tipPos);
                                        AnnoyingVillagers.PACKET_HANDLER.send(
                                                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(livingEntityPatch::getOriginal),
                                                new ClientboundMuteExplosionAtPos(mutePos, 4)
                                        );
                                        livingEntityPatch.getOriginal().level().explode(livingEntityPatch.getOriginal(), tipPos.x, tipPos.y, tipPos.z,
                                                2.0F, true, Level.ExplosionInteraction.TNT);
                                        Vec3 glaivePos = EpicfightUtil.getJointWithTranslation(livingEntityPatch.getOriginal(), new Vec3f(0, 0, 0),
                                                Armatures.BIPED.get().toolR, 1.3F, 2.3F);
                                        Vec3 explosionPos = EpicfightUtil.getJointWithTranslation(livingEntityPatch.getOriginal(), new Vec3f(0, 0, 0),
                                                Armatures.BIPED.get().toolR, 10.3F, 2.3F);
                                        AnnoyingVillagers.PACKET_HANDLER.send(
                                                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(livingEntityPatch::getOriginal),
                                                new ClientboundGlaiveExplosionFx(glaivePos, explosionPos)
                                        );
                                        if (explosionPos != null) {
                                            livingEntityPatch.getOriginal().level().playSound(null, new BlockPos((int) explosionPos.x, (int) explosionPos.y, (int) explosionPos.z), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_shot"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                        }
                                    }
                                }, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER))
        );
        AVAnimations.ENDER_GLAIVE_NAPOLEON_AUTO_3 = builder.nextAccessor("biped/wom_clone/ender_glaive_napoleon_auto_3",
                (accessor) -> (new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.15F, 0.35F, 0.39F, 0.39F, humanoidArmature.get().toolR, null),
                        new Phase(0.39F, 0.5F, 0.7F, 0.74F, 0.74F, humanoidArmature.get().toolR, null),
                        new Phase(0.74F, 0.75F, 0.85F, 1.19F, 1.19F, humanoidArmature.get().toolR, null),
                        new Phase(1.19F, 1.2F, 2.2F, 2.25F, Float.MAX_VALUE, humanoidArmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.2F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.1F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 2)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 2)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F), 2)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 3)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F), 3)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.1F), 3)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL, 3)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.15F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(1.2F, 2.25F)).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, true).newTimePair(0.0F, 0.85F).addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(2.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        AVAnimations.ENDER_GLAIVE_NAPOLEON_WATERLOW = builder.nextAccessor("biped/wom_clone/ender_glaive_napoleon_waterlow",
                (accessor) -> (new BasicMultipleAttackAnimation(0.1F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.15F, 0.35F, 0.39F, 0.39F, humanoidArmature.get().toolR, null),
                        new Phase(0.39F, 0.4F, 0.6F, 0.64F, 0.64F, humanoidArmature.get().toolR, null),
                        new Phase(0.64F, 0.65F, 1.0F, 1.1F, Float.MAX_VALUE, humanoidArmature.get().toolR, null)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.2F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F), 2)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F), 2)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL, 2)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                        .addProperty(AttackAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.2F))
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.4F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));

        AVAnimations.CLONE_ENDERBLASTER_TWOHAND_TOMAHAWK = builder.nextAccessor("biped/wom_clone/clone_enderblaster_twohand_dash",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature, new Phase(0.0F, 0.3F, 0.44F, 0.45F, 0.45F, humanoidArmature.get().legL, WOMWeaponColliders.KICK_HUGE),
                        new Phase(0.45F, 0.5F, 0.6F, 0.65F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.TORMENT_AIRSLAM)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F), 1)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(5.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(5.0F), 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 1)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get(), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.8F)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.45F, reascer.wom.gameasset.ReuseableEvents.GROUND_BODYSCRAPE_LAND, Side.CLIENT)
                        }));
        AVAnimations.YELLOW_TORMENT_CHARGED_ATTACK_3 = builder.nextAccessor("biped/wom_clone/yellow_torment_charged_attack_3",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, 1.0F, 1.2F, 1.5F, WOMWeaponColliders.TORMENT_BERSERK_AIRSLAM, humanoidArmature.get().rootJoint, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(3.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.FINISHER))
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.BYPASS_DODGE))
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.GUARD_PUNCTURE))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.35F, 0.9F))
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> {
                            if (elapsedTime >= 0.9F && elapsedTime < 1.15F) {
                                float dpx = (float) livingEntityPatch.getOriginal().getX();
                                float dpy = (float) livingEntityPatch.getOriginal().getY();
                                float dpz = (float) livingEntityPatch.getOriginal().getZ();

                                for (BlockState block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz))) {
                                    --dpy;
                                }

                                float distanceToGround = (float) Math.max(Math.abs(livingEntityPatch.getOriginal().getY() - (double) dpy) - (double) 1.0F, 0.0F);
                                return 1.0F - (1.0F / (-distanceToGround - 1.0F) + 1.0F);
                            } else {
                                return speed;
                            }
                        }).addEvents(
                                AnimationEvent.InTimeEvent.create(0.35F, reascer.wom.gameasset.ReuseableEvents.AIRBURST_JUMP, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.15F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.15F, ReuseableEvents.SHOCK_WAVE, Side.SERVER)
                        )
                        .addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.1F, (livingEntityPatch, self, params) -> {
                            LivingEntity target = livingEntityPatch.getOriginal().getLastHurtMob();
                            if (target != null && target.distanceTo(livingEntityPatch.getOriginal()) < 30.0F) {
                                double offset = 4.0F;
                                double referenceX = target.getX();
                                double referenceY = target.getY();
                                double referenceZ = target.getZ();
                                float referenceYaw = livingEntityPatch.getOriginal().yHeadRot;
                                double newX = referenceX + offset * (double) org.joml.Math.sin(org.joml.Math.toRadians(referenceYaw));
                                double newZ = referenceZ - offset * (double) org.joml.Math.cos(org.joml.Math.toRadians(referenceYaw));
                                BlockPos blockPos = new BlockPos((int) newX, (int) referenceY, (int) newZ);
                                BlockState block = livingEntityPatch.getOriginal().level().getBlockState(blockPos);
                                if (!block.isCollisionShapeFullBlock(livingEntityPatch.getOriginal().level(), blockPos)) {
                                    livingEntityPatch.getOriginal().teleportTo(newX, referenceY, newZ);
                                } else {
                                    livingEntityPatch.getOriginal().teleportTo(referenceX, referenceY, referenceZ);
                                }

                                livingEntityPatch.getOriginal().setDeltaMovement(target.getDeltaMovement());
                            }

                            ((ServerLevel) livingEntityPatch.getOriginal().level())
                                    .sendParticles(ParticleTypes.REVERSE_PORTAL,
                                            livingEntityPatch.getOriginal().getX(),
                                            livingEntityPatch.getOriginal().getY() + (double) 1.0F,
                                            livingEntityPatch.getOriginal().getZ(),
                                            60, 0.05, 0.05, 0.05, 0.5F);
                            livingEntityPatch.getOriginal().level().playSound(
                                    null,
                                    livingEntityPatch.getOriginal().xo,
                                    livingEntityPatch.getOriginal().yo + (double) 1.0F,
                                    livingEntityPatch.getOriginal().zo,
                                    SoundEvents.ENDERMAN_TELEPORT,
                                    livingEntityPatch.getOriginal().getSoundSource(),
                                    2.0F, 1.0F - ((new Random()).nextFloat() - 0.5F) * 0.2F
                            );

                        }, Side.SERVER), AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, self, params) -> {
                            LivingEntity entity = livingEntityPatch.getOriginal();
                            livingEntityPatch.getOriginal().level()
                                    .addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(),
                                            entity.getX(), entity.getY(), entity.getZ(),
                                            Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F
                                    );
                        }, Side.CLIENT)}));
        AVAnimations.CLONE_ENDERBLASTER_ONEHAND_DASH = builder.nextAccessor("biped/wom_clone/clone_enderblaster_onehand_dash",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.15F, 0.2F, 0.45F, 0.45F, humanoidArmature.get().legL, WOMWeaponColliders.KICK_HUGE),
                        new Phase(0.45F, 0.45F, 0.75F, 1.0F, Float.MAX_VALUE, humanoidArmature.get().legL, WOMWeaponColliders.KICK_HUGE)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F))
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 1)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get(), 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.8F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(ActionAnimationProperty.COORD_SET_TICK,
                                (self, entitypatch, transformSheet) -> {
                                    LivingEntity attackTarget = entitypatch.getTarget();
                                    if (!(Boolean) self.getRealAnimation().get().getProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE).orElse(false) && attackTarget != null) {
                                        TransformSheet transform = self.getTransfroms().get("Root").copyAll();
                                        Keyframe[] keyframes = transform.getKeyframes();
                                        int startFrame = 0;
                                        int endFrame = transform.getKeyframes().length - 1;
                                        Vec3f keyLast = keyframes[endFrame].transform().translation();
                                        Vec3 pos = entitypatch.getOriginal().getEyePosition();
                                        Vec3 targetpos = attackTarget.position().add(attackTarget.getDeltaMovement().scale(1.5F));
                                        float horizontalDistance = org.joml.Math.max((float) targetpos.subtract(pos).horizontalDistance() - (attackTarget.getBbWidth() + entitypatch.getOriginal().getBbWidth()), 0.0F);
                                        Vec3f worldPosition = new Vec3f(keyLast.x, 0.0F, -horizontalDistance);
                                        float scale = org.joml.Math.min(worldPosition.length() / keyLast.length(), 1.5F);

                                        for (int i = startFrame; i <= endFrame; ++i) {
                                            Vec3f translation = keyframes[i].transform().translation();
                                            translation.z *= scale;
                                        }

                                        transformSheet.readFrom(transform);
                                    } else if (transformSheet != null) {
                                        transformSheet.readFrom(self.getTransfroms().get("Root"));
                                    }
                                }));
        AVAnimations.SLEDGEHAMMER_TORMENT_BERSERK_AUTO_1 = builder.nextAccessor("biped/wom_clone/sledgehammer_torment_berserk_auto_1",
                (accessor) -> (new BasicMultipleAttackAnimation(0.4F, 0.15F, 0.5F, 0.5F, null, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.1F))
                        .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(9.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.OVERBLOOD_HIT)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.2F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.35F, ReuseableEvents.SLEDGEHAMMER_SHOOT, Side.SERVER)
                        )
        );
        AVAnimations.SLEDGEHAMMER_TORMENT_BERSERK_AUTO_2 = builder.nextAccessor("biped/wom_clone/sledgehammer_torment_berserk_auto_2",
                (accessor) -> (new BasicMultipleAttackAnimation(0.4F, 0.15F, 0.5F, 0.5F, null, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.1F))
                        .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(9.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.OVERBLOOD_HIT)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.2F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.31F, ReuseableEvents.SLEDGEHAMMER_SHOOT, Side.SERVER)
                        )
        );
        AVAnimations.SLEDGEHAMMER_SOLAR_AUTO_3 = builder.nextAccessor("biped/wom_clone/sledgehammer_solar_auto_3",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, 0.4F, 0.75F, 0.85F, null, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.3F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.3F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.6F, ReuseableEvents.SLEDGEHAMMER_SHOOT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        )
        );
        AVAnimations.CLONE_ANTITHEUS_SHOOT = builder.nextAccessor("biped/wom_clone/clone_antitheus_shoot",
                (accessor) -> (new AntitheusShootAttackAnimation(0.05F, 0.05F, 0.1F, 0.5F, WOMWeaponColliders.ANTITHEUS_SHOOT, humanoidArmature.get().toolL, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(WOMExtraDamageInstance.WOM_SWEEPING_EDGE_ENCHANTMENT.create(1.0F)))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, SoundEvents.WITHER_SHOOT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, SoundEvents.WITHER_BREAK_BLOCK)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                        .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.0F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false));
        AVAnimations.CLONE_ANTITHEUS_ASCENDED_IDLE = builder.nextAccessor("biped/wom_clone/clone_antitheus_ascended_idle",
                (accessor) -> new StaticAnimation(0.1F, true, accessor, humanoidArmature));
        AVAnimations.NULL_ANTITHEUS_ASCENDED_AUTO_1 = builder.nextAccessor("biped/wom_clone/null_antitheus_ascended_auto_1",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, 0.3F, 0.4F, 0.4F, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES, humanoidArmature.get().rootJoint, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F), Side.CLIENT)
                        }));
        AVAnimations.NULL_ANTITHEUS_ASCENDED_AUTO_2 = builder.nextAccessor("biped/wom_clone/null_antitheus_ascended_auto_2",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.3F, 0.4F, 0.5F, 0.5F, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES),
                        new Phase(0.5F, 0.6F, 0.7F, 0.7F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F), 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT, 1)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get(), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                new AnimationEvent[]{
                                        AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, self, params) -> {
                                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                        }, Side.CLIENT)}));
        AVAnimations.NULL_ANTITHEUS_ASCENDED_AUTO_3 = builder.nextAccessor("biped/wom_clone/null_antitheus_ascended_auto_3",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.2F, 0.3F, 0.35F, 0.35F, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES),
                        new Phase(0.35F, 0.4F, 0.5F, 0.55F, 0.55F, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES),
                        new Phase(0.55F, 0.7F, 0.8F, 0.85F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F), 2)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F), 2)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT, 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT, 2)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get(), 1)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get(), 2)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 0)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 2)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                new AnimationEvent[]{
                                        AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F), Side.CLIENT)}));
        HEROBRINE_MOB_ENDERSTEP_OBSCURIS = builder.nextAccessor("biped/wom_clone/herobrine_mob_ender_obscuris",
                (accessor) -> (new DodgeAnimation(0.05F, accessor, 0.6F, 1.65F, humanoidArmature))
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.15F))
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.1F, reascer.wom.gameasset.ReuseableEvents.ENDER_STEP, Side.BOTH),
                                AnimationEvent.InTimeEvent.create(0.3F, (livingEntityPatch, self, params) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        Entity entity = livingEntityPatch.getOriginal();
                                        if (entity instanceof HerobrineMob herobrineMob && herobrineMob.getTarget() != null) {
                                            LivingEntity target = herobrineMob.getTarget();
                                            if (target != null) {
                                                double offset = 2.0F;
                                                double referenceX = target.getX();
                                                double referenceY = target.getY();
                                                double referenceZ = target.getZ();
                                                float referenceYaw = target.yHeadRot;
                                                double newX = referenceX + offset * (double) org.joml.Math.sin(org.joml.Math.toRadians(referenceYaw));
                                                double newZ = referenceZ - offset * (double) org.joml.Math.cos(org.joml.Math.toRadians(referenceYaw));
                                                double newY = referenceY;

                                                for (BlockState block = herobrineMob.level().getBlockState(new BlockPos.MutableBlockPos(newX, referenceY, newZ)); !(block.getBlock() instanceof BushBlock) && !block.isAir() || block.is(Blocks.VOID_AIR); block = herobrineMob.level().getBlockState(new BlockPos.MutableBlockPos(newX, referenceY, newZ))) {
                                                    --offset;
                                                    newX = referenceX + offset * (double) org.joml.Math.sin(org.joml.Math.toRadians(referenceYaw));
                                                    newZ = referenceZ - offset * (double) org.joml.Math.cos(org.joml.Math.toRadians(referenceYaw));
                                                    newY = referenceY;
                                                }

                                                entity.teleportTo(newX, newY, newZ);
                                                entity.setDeltaMovement(target.getDeltaMovement());
                                                entity.lookAt(EntityAnchorArgument.Anchor.EYES, target.position());
                                            }
                                        }

                                        ((ServerLevel) entity.level()).sendParticles(ParticleTypes.REVERSE_PORTAL, entity.getX(), entity.getY() + (double) 1.0F, entity.getZ(), 60, 0.05, 0.05, 0.05, 0.5F);
                                        entity.level().playSound(null, entity.xo, entity.yo + (double) 1.0F, entity.zo, SoundEvents.ENDERMAN_TELEPORT, entity.getSoundSource(), 2.0F, 1.0F - ((new Random()).nextFloat() - 0.5F) * 0.2F);
                                    }
                                }, Side.BOTH)
                        }));
        AVAnimations.OBSIDIAN_ANTITHEUS_ASCENDED_DEATHFALL = builder.nextAccessor("biped/wom_clone/obsidian_antitheus_ascended_deathfall",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, 0.5F, 0.55F, 0.75F, WOMWeaponColliders.ANTITHEUS_ASCENDED_DEATHFALL, humanoidArmature.get().rootJoint, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.75F))
                        .addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 0.7F, 0.7F);
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

                        }, Side.CLIENT), AnimationEvent.InTimeEvent.create(0.35F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().resetFallDistance();
                        }, Side.SERVER), AnimationEvent.InTimeEvent.create(0.45F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 0.7F, 0.7F);
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

                            float f = (float) livingEntityPatch.getOriginal().getX();
                            float f1 = (float) livingEntityPatch.getOriginal().getY();

                            for (int i = 0; i < 24; ++i) {
                                livingEntityPatch.getOriginal().level().addParticle(ParticleTypes.LARGE_SMOKE, livingEntityPatch.getOriginal().getX() + (double) ((new Random()).nextFloat() - 0.5F), livingEntityPatch.getOriginal().getY() + 2.200000047683716D, livingEntityPatch.getOriginal().getZ() + (double) ((new Random()).nextFloat() - 0.5F), ((new Random()).nextFloat() - 0.5F) * 0.05F, -((double) (new Random()).nextFloat() * (livingEntityPatch.getOriginal().getY() - (double) f1) * 0.4000000059604645D), ((new Random()).nextFloat() - 0.5F) * 0.05F);
                            }

                        }, Side.CLIENT), AnimationEvent.InTimeEvent.create(0.5F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().resetFallDistance();
                        }, Side.SERVER), AnimationEvent.InTimeEvent.create(0.55F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), SoundEvents.WITHER_SHOOT, SoundSource.NEUTRAL, 0.7F, 0.5F);
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.BLUNT_HIT_HARD.get(), SoundSource.NEUTRAL, 0.7F, 0.7F);

                            float f = (float) livingEntityPatch.getOriginal().getX();
                            float f1 = (float) livingEntityPatch.getOriginal().getY();

                            Vec3 vec3 = new Vec3(0.0D, (double) (f1 - 2.0F) - livingEntityPatch.getOriginal().getY(), 0.0D);

                            livingEntityPatch.getOriginal().move(MoverType.SELF, vec3);
                            byte b0 = 80;
                            double d0 = 0.6D;
                            double d1 = 0.01D;

                            for (int i = 0; i < b0; ++i) {
                                double d2 = 6.283185307179586D * (new Random()).nextDouble();
                                double d3 = ((new Random()).nextDouble() - 0.5D) * 3.141592653589793D * d1 / d0;
                                double d4 = d0 * Math.cos(d3) * Math.cos(d2);
                                double d5 = d0 * Math.cos(d3) * Math.sin(d2);
                                double d6 = d0 * Math.sin(d3);
                                float f3 = (new Random()).nextFloat() + 0.4F;
                                Vec3f vec3f = new Vec3f((float) d4 * f3, (float) d5 * f3, (float) d6 * f3);
                                OpenMatrix4f openmatrix4f = (new OpenMatrix4f()).rotate((float) Math.toRadians(90.0F), new Vec3f(1.0F, 0.0F, 0.0F));

                                OpenMatrix4f.transform3v(openmatrix4f, vec3f, vec3f);
                                livingEntityPatch.getOriginal().level().addParticle(ParticleTypes.LARGE_SMOKE, livingEntityPatch.getOriginal().getX() + (double) vec3f.x, (float) ((int) livingEntityPatch.getOriginal().getY()) + vec3f.y + 0.02F, livingEntityPatch.getOriginal().getZ() + (double) vec3f.z, vec3f.x, vec3f.y, vec3f.z);
                            }

                        }, Side.CLIENT), AnimationEvent.InTimeEvent.create(0.55F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().resetFallDistance();
                        }, Side.SERVER)})
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.5F, ReuseableEvents.SUMMON_OBSIDIAN_CROSS, Side.SERVER)
                        )
        );
        AVAnimations.OLD_MOONLESS_RUN = builder.nextAccessor("biped/wom_clone/old_moonless_run",
                (accessor) -> new MovementAnimation(0.1F, true, accessor, humanoidArmature));
        AVAnimations.OBSIDIAN_STRONG_KICK = builder.nextAccessor("biped/wom_clone/obsidian_kick",
                (accessor) -> (new KickAttackAnimation(0.1F, 0.05F, 0.0F, 0.15F, 0.35F, WOMWeaponColliders.KICK, humanoidArmature.get().legR, accessor, humanoidArmature)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(2.0F)).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.0F))
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, ReuseableEvents.SUMMON_2_OBSIDIAN_LEG_RIGHT, Side.SERVER)
                        ));
        AVAnimations.OBSIDIAN_KICK_AUTO_3 = builder.nextAccessor("biped/wom_clone/obsidian_kick_3",
                (accessor) -> (new KickAttackAnimation(0.2F, 0.45F, 0.0F, 0.55F, 0.9F, WOMWeaponColliders.KICK, humanoidArmature.get().legL, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.51F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get())
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.6F, ReuseableEvents.SUMMON_2_OBSIDIAN_LEG_LEFT, Side.SERVER)
                        ));
        AVAnimations.OBSIDIAN_KICK_AUTO_1 = builder.nextAccessor("biped/wom_clone/obsidian_kick_1",
                (accessor) -> (new KickAttackAnimation(0.2F, 0.2F, 0.0F, 0.35F, 0.5F, WOMWeaponColliders.KICK, humanoidArmature.get().legR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.2F))
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SMALL.get())
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.3F, ReuseableEvents.SUMMON_2_OBSIDIAN_LEG_RIGHT, Side.SERVER)
                        ));
        AVAnimations.OBSIDIAN_STRONG_PUNCH = builder.nextAccessor("biped/wom_clone/obsidian_strong_punch",
                (accessor) -> (new BasicMultipleAttackAnimation(0.3F, 0.1F, 0.15F, 0.35F, WOMWeaponColliders.PUNCH, humanoidArmature.get().handL, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(4.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(5.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SMALL.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.0F)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.0F)
                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 0.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, ReuseableEvents.SUMMON_3_OBSIDIAN_HAND_LEFT, Side.SERVER)
                        ));

    }

    private static @NotNull Vec3 getVec3(LivingEntity owner) {
        Vec3 look = owner.getLookAngle();
        Vec3 forward = new Vec3(look.x, 0.0D, look.z);
        if (forward.lengthSqr() < 1.0E-6D) {
            float yawRad = (float) Math.toRadians(owner.getYRot());
            forward = new Vec3(-Mth.sin(yawRad), 0.0D, Mth.cos(yawRad));
        }
        forward = forward.normalize();
        return forward;
    }

    private static class ReuseableEvents {
        public static final AnimationEvent.E0 FAST_SPINING =
                (livingentitypatch, staticAnimation, aobject) -> livingentitypatch.getOriginal().level().playSound((Player) livingentitypatch.getOriginal(), livingentitypatch.getOriginal(), EpicFightSounds.WHOOSH.get(), SoundSource.NEUTRAL, 0.5F, 1.1F - ((new Random()).nextFloat() - 0.5F) * 0.2F);
        private static final AnimationEvent.E0 SUMMON_2_OBSIDIAN_LEG_RIGHT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        BlockState obsidian;
                        if (weapon instanceof ShadowObsidianWeaponItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        }
                        HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 2, Armatures.BIPED.get().legR);
                    }
                };
        private static final AnimationEvent.E0 SUMMON_2_OBSIDIAN_LEG_LEFT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        BlockState obsidian;
                        if (weapon instanceof ShadowObsidianWeaponItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        }
                        HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 2, Armatures.BIPED.get().legL);
                    }
                };
        private static final AnimationEvent.E0 SUMMON_2_OBSIDIAN_HAND_RIGHT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        BlockState obsidian;
                        if (weapon instanceof ShadowObsidianWeaponItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        }
                        HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 2, Armatures.BIPED.get().toolR);
                    }
                };
        private static final AnimationEvent.E0 SUMMON_2_OBSIDIAN_HAND_LEFT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        BlockState obsidian;
                        if (weapon instanceof ShadowObsidianWeaponItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        }
                        HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 2, Armatures.BIPED.get().toolL);
                    }
                };
        private static final AnimationEvent.E0 SUMMON_3_OBSIDIAN_HAND_RIGHT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        BlockState obsidian;
                        if (weapon instanceof ShadowObsidianWeaponItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        }
                        HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 3, Armatures.BIPED.get().toolR);
                    }
                };
        private static final AnimationEvent.E0 SUMMON_3_OBSIDIAN_HAND_LEFT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        BlockState obsidian;
                        if (weapon instanceof ShadowObsidianWeaponItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        }
                        HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 3, Armatures.BIPED.get().toolL);
                    }
                };
        private static final AnimationEvent.E0 SUMMON_6_OBSIDIAN_HAND_RIGHT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        BlockState obsidian;
                        if (weapon instanceof ShadowObsidianWeaponItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        }
                        HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 6, Armatures.BIPED.get().toolR);
                    }
                };
        private static final AnimationEvent.E0 SUMMON_6_OBSIDIAN_LEG_RIGHT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        BlockState obsidian;
                        if (weapon instanceof ShadowObsidianWeaponItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        }
                        HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 6, Armatures.BIPED.get().legR);
                    }
                };
        private static final AnimationEvent.E0 SUMMON_OBSIDIAN_PILLAR =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        BlockState obsidian;
                        if (weapon instanceof ShadowObsidianWeaponItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        }
                        HerobrineUtil.summonObsidianPillar(serverLevel, livingEntity, obsidian);
                    }
                };
        private static final AnimationEvent.E0 SUMMON_OBSIDIAN_WALL =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        BlockState obsidian;
                        if (weapon instanceof ShadowObsidianWeaponItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        }
                        HerobrineUtil.summonObsidianWall(serverLevel, livingEntity, obsidian);
                    }
                };

        private static final AnimationEvent.E0 SUMMON_OBSIDIAN_CROSS =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        BlockState obsidian;
                        if (weapon instanceof ShadowObsidianWeaponItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        }
                        HerobrineUtil.summonObsidianCross(serverLevel, livingEntity, obsidian);
                    }
                };

        private static final AnimationEvent.E0 THROW_OBSIDIAN =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        BlockState obsidian;
                        if (weapon instanceof ShadowObsidianWeaponItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, livingEntity instanceof Player);
                        }
                        LivingEntity attacker = livingEntityPatch.getOriginal();
                        Vec3 to = attacker.getEyePosition().add(attacker.getLookAngle().scale(16.0));
                        if (attacker instanceof Mob mob && mob.getTarget() != null) {
                            to = mob.getTarget().getEyePosition(1.0F);
                        }
                        BlockProjectileEntity throwingObsidian = new BlockProjectileEntity(
                                attacker.level(),
                                attacker,
                                obsidian
                        );
                        serverLevel.addFreshEntity(throwingObsidian);
                        Vec3 dir = to.subtract(throwingObsidian.position());
                        if (dir.lengthSqr() < 1.0e-6) dir = attacker.getLookAngle();
                        Vec3 vel = dir.normalize().scale(2.0F);
                        throwingObsidian.setDeltaMovement(vel);
                    }
                };

        private static final AnimationEvent.E0 SLEDGEHAMMER_SHOOT =
                (livingEntityPatch, staticAnimation, object) -> {
                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                        LivingEntity shooterEntity = livingEntityPatch.getOriginal();

                        Vec3 aimPosition = null;

                        if (shooterEntity instanceof Mob mob && mob.getTarget() != null) {
                            aimPosition = mob.getTarget().getEyePosition(1.0F);
                        } else if (shooterEntity instanceof Player player) {
                            Vec3 playerEyePosition = player.getEyePosition(1.0F);
                            Vec3 playerLookDirection = player.getLookAngle();
                            double aimDistance = 64.0D;
                            aimPosition = playerEyePosition.add(playerLookDirection.scale(aimDistance));
                        }

                        ObsidianSledgehammerProjectileEntity obsidianSledgehammerProjectileEntity = new ObsidianSledgehammerProjectileEntity(AnnoyingVillagersModEntities.OBSIDIAN_SLEDGEHAMMER_PROJECTILE.get(), serverLevel);
                        Vec3 hammerPos = EpicfightUtil.getJointWithTranslation(livingEntityPatch.getOriginal(), new Vec3f(0, 0, 0),
                                Armatures.BIPED.get().toolR, 1.0F, 0.0F);
                        if (hammerPos != null && aimPosition != null) {
                            obsidianSledgehammerProjectileEntity.moveTo(hammerPos.x, hammerPos.y, hammerPos.z, 0F, 0F);
                            obsidianSledgehammerProjectileEntity.setPosToAim(new Vec3(aimPosition.x, aimPosition.y, aimPosition.z));
                            obsidianSledgehammerProjectileEntity.setInvulnerable(true);
                            obsidianSledgehammerProjectileEntity.playSound(AnnoyingVillagersModSounds.METAL_HIT.get(), 1.0F, 1.0F);
                            obsidianSledgehammerProjectileEntity.setOwner(shooterEntity);
                            if (staticAnimation == AVAnimations.SLEDGEHAMMER_SOLAR_AUTO_3) {
                                obsidianSledgehammerProjectileEntity.setShouldStun(true);
                            }
                            serverLevel.addFreshEntity(obsidianSledgehammerProjectileEntity);
                        }
                    }
                };
        private static final AnimationEvent.E0 SHOCK_WAVE =
                (livingEntityPatch, staticAnimation, object) -> {

                    Vec3 legendarySwordPos = EpicfightUtil.getJointWithTranslation(livingEntityPatch.getOriginal(), new Vec3f(0, 0, 0),
                            Armatures.BIPED.get().toolR, 1.5F, 0.0F);
                    final int MAX_SHOCKWAVE_RADIUS = 6;
                    final int TICKS_BETWEEN_LAYERS = 2;
                    for (int radius = 1; radius <= MAX_SHOCKWAVE_RADIUS; radius++) {
                        int delayTicks = (radius - 1) * TICKS_BETWEEN_LAYERS;
                        int ringRadius = radius;
                        if (legendarySwordPos == null) return;
                        BlockPos finalVec = BlockPos.containing(legendarySwordPos);
                        new DelayedTask(delayTicks) {
                            @Override
                            public void run() {
                                ShockwaveUtil.spawnCircleRing((ServerLevel) livingEntityPatch.getOriginal().level(), finalVec, ringRadius, livingEntityPatch.getOriginal());
                            }
                        };
                    }
                };

        private static final AnimationEvent.E0 LEGENDARY_SWORD_GROUND_SLAM =
                (livingEntityPatch, staticAnimation, object) -> {
                    Vec3 vec3 = livingEntityPatch.getOriginal().position();
                    OpenMatrix4f openmatrix4f = livingEntityPatch.getArmature()
                            .getBoundTransformFor(livingEntityPatch.getAnimator().getPose(1.0F), Armatures.BIPED.get().toolR)
                            .mulFront(OpenMatrix4f.createTranslation((float) vec3.x, (float) vec3.y, (float) vec3.z)
                                    .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                                            .mulBack(livingEntityPatch.getModelMatrix(1.0F))));
                    vec3 = OpenMatrix4f.transform(openmatrix4f, (new Vec3f(0.0F, -0.0F, -1.4F)).toDoubleVector());
                    Level level = livingEntityPatch.getOriginal().level();
                    Vec3 vec32 = getFloor(livingEntityPatch, new Vec3f(0.0F, 0.0F, -1.4F), Armatures.BIPED.get().toolR);
                    BlockState blockstate = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos(new Vec3i((int) Math.floor(vec32.x), (int) Math.floor(vec32.y), (int) Math.floor(vec32.z))));

                    level.playLocalSound(vec32.x, vec32.y, vec32.z, blockstate.is(Blocks.WATER) ?
                                    SoundEvents.GENERIC_SPLASH :
                                    EpicFightSounds.SLAM_HEAVY.get(),
                            SoundSource.BLOCKS,
                            1.0F, 1.0F, false);
                    livingEntityPatch.getOriginal().level().addParticle(WOMParticles.WOM_GROUND_SLAM.get(), vec32.x, (int) vec32.y + 1, vec32.z, 1.0D, 50.0D, 1.0D);
                    LevelUtil.circleSlamFracture(livingEntityPatch.getOriginal(), level, vec3, 3.5D, true, false);
                };

        private static final AnimationEvent.E0 END_ATTACK =
                (livingentitypatch, staticAnimation, object) -> {
                    if (livingentitypatch instanceof PlayerPatch) {
                        livingentitypatch.playAnimationSynchronized(AVAnimations.DUAL_END, 0.1F);
                    }
                };

        public static Vec3 getFloor(LivingEntityPatch<?> livingentitypatch, Vec3f vec3f, Joint joint) {
            OpenMatrix4f openmatrix4f = livingentitypatch.getArmature().getBoundTransformFor(livingentitypatch.getAnimator().getPose(1.0F), joint);

            openmatrix4f.translate(vec3f);
            OpenMatrix4f openMatrix4f1 = (new OpenMatrix4f()).rotate(-((float) Math.toRadians(livingentitypatch.getOriginal().yRotO + 180.0F)), new Vec3f(0.0F, 1.0F, 0.0F));

            OpenMatrix4f.mul(openMatrix4f1, openmatrix4f, openmatrix4f);
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
