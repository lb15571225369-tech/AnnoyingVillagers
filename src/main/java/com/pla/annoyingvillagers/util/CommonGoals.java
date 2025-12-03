package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.entity.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

public class CommonGoals {
    public static void registerGoalForHostileNpc(Monster monster) {
        monster.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
        monster.targetSelector.addGoal(1, new HurtByTargetGoal(monster, new Class[0]));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, Player.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, PlayerNpcEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, SteveEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, Steve2Entity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, AngrySteveEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, ChrisEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, AlexEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, JevEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, Villager.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, IronGolem.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, VillagerScoutEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, VillagerScoutCaptainEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, RedVillagerGeneralEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, BlueVillagerGeneralEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, GreenVillagerGeneralEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, PurpleVillagerGeneralEntity.class, true, false));
        monster.goalSelector.addGoal(2, new MeleeAttackGoal(monster, 1.2D, false) {
            protected double getAttackReachSqr(LivingEntity livingentity) {
                return (double) (monster.getBbWidth() * monster.getBbWidth() + livingentity.getBbWidth());
            }
        });
        monster.goalSelector.addGoal(3, new RandomStrollGoal(monster, 1.0D));
        monster.goalSelector.addGoal(4, new RandomLookAroundGoal(monster));
        monster.goalSelector.addGoal(5, new FloatGoal(monster));
    }

    public static void registerGoalForBlueDemonNpc(Monster monster) {
        monster.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
        monster.targetSelector.addGoal(1, new HurtByTargetGoal(monster, new Class[0]));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, HerobrineCloneEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, ShadowHerobrineCloneEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, HerobrineChrisEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, LowHerobrineCloneEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, LowShadowHerobrineCloneEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, Herobrine7Entity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, AegisHerobrineEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, SwordsmanHerobrineEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, GlaiveHerobrineEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, ReaperHerobrineEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, SledgehammerHerobrineEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, ShadowHerobrineEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, ArmoredHerobrineEntity.class, true, false));
        monster.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(monster, Player.class, true, false));
        monster.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(monster, PlayerNpcEntity.class, true, false));
        monster.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(monster, SteveEntity.class, true, false));
        monster.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(monster, Steve2Entity.class, true, false));
        monster.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(monster, AlexEntity.class, true, false));
        monster.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(monster, ChrisEntity.class, true, false));
        monster.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(monster, Villager.class, true, false));
        monster.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(monster, NullEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, VillagerScoutEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, VillagerScoutCaptainEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, RedVillagerGeneralEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, BlueVillagerGeneralEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, GreenVillagerGeneralEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, PurpleVillagerGeneralEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, AbstractIllager.class, true, false));
        monster.goalSelector.addGoal(3, new MeleeAttackGoal(monster, 1.2D, false) {
            protected double getAttackReachSqr(LivingEntity livingentity) {
                return (double) (monster.getBbWidth() * monster.getBbWidth() + livingentity.getBbWidth());
            }
        });
        monster.goalSelector.addGoal(4, new RandomStrollGoal(monster, 1.0D));
        monster.goalSelector.addGoal(5, new RandomLookAroundGoal(monster));
        monster.goalSelector.addGoal(6, new FloatGoal(monster));
    }

    public static void registerGoalForVillagerKnightNpc(PathfinderMob mob) {
        mob.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
        mob.targetSelector.addGoal(1, (new HurtByTargetGoal(mob, new Class[0])).setAlertOthers(new Class[0]));

        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, HerobrineCloneEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, ShadowHerobrineCloneEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, HerobrineChrisEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, LowHerobrineCloneEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, LowShadowHerobrineCloneEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, Herobrine7Entity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, NullEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, AegisHerobrineEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, SwordsmanHerobrineEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, GlaiveHerobrineEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, ReaperHerobrineEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, SledgehammerHerobrineEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, ShadowHerobrineEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, ArmoredHerobrineEntity.class, true, false));

        mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(mob, PlayerNpcEntity.class, true, false));
        mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(mob, Player.class, true, false));
        mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(mob, Monster.class, true, false));
        mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(mob, AbstractIllager.class, true, false));
        mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(mob, BlueDemonEntity.class, true, false));
        mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(mob, BlueDemon2Entity.class, true, false));

        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, SteveEntity.class, true, false));
        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, Steve2Entity.class, true, false));
        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, AlexEntity.class, true, false));
        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, ChrisEntity.class, true, false));
        mob.goalSelector.addGoal(5, new MeleeAttackGoal(mob, 1.5D, false) {
            protected double getAttackReachSqr(LivingEntity livingentity) {
                return (double) (mob.getBbWidth() * mob.getBbWidth() + livingentity.getBbWidth());
            }
        });
        mob.goalSelector.addGoal(6, new RandomStrollGoal(mob, 1.0D));
        mob.goalSelector.addGoal(7, new FollowMobGoal(mob, 1.3D, 20.0F, 15.0F));
        mob.goalSelector.addGoal(8, new OpenDoorGoal(mob, true));
        mob.goalSelector.addGoal(9, new OpenDoorGoal(mob, false));
        mob.goalSelector.addGoal(10, new RandomLookAroundGoal(mob));
        mob.goalSelector.addGoal(11, new FloatGoal(mob));
    }

    public static void registerGoalForNeutralNpc(PathfinderMob mob) {
        mob.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, HerobrineCloneEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, ShadowHerobrineCloneEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, HerobrineChrisEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, LowHerobrineCloneEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, LowShadowHerobrineCloneEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, Herobrine7Entity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, NullEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, AegisHerobrineEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, SwordsmanHerobrineEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, GlaiveHerobrineEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, ReaperHerobrineEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, SledgehammerHerobrineEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, ShadowHerobrineEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, ArmoredHerobrineEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, EliteHerobrineKnockedEntity.class, true, false));

        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, BlueDemonEntity.class, false, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, BlueDemon2Entity.class, false, false));
        mob.goalSelector.addGoal(2, new MeleeAttackGoal(mob, 1.2D, false) {
            protected double getAttackReachSqr(LivingEntity livingentity) {
                return (double) (mob.getBbWidth() * mob.getBbWidth() + livingentity.getBbWidth());
            }
        });

        if (!(mob.getTarget() instanceof VillagerScoutEntity)) {
            mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, VillagerScoutEntity.class, 12.0F, 1.2D, 1.8D));
        }
        if (!(mob.getTarget() instanceof VillagerScoutCaptainEntity)) {
            mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, VillagerScoutCaptainEntity.class, 12.0F, 1.2D, 1.8D));
        }
        if (!(mob.getTarget() instanceof BlueVillagerGeneralEntity)) {
            mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, BlueVillagerGeneralEntity.class, 12.0F, 1.2D, 1.8D));
        }
        if (!(mob.getTarget() instanceof GreenVillagerGeneralEntity)) {
            mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, GreenVillagerGeneralEntity.class, 12.0F, 1.2D, 1.8D));
        }
        if (!(mob.getTarget() instanceof RedVillagerGeneralEntity)) {
            mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, RedVillagerGeneralEntity.class, 12.0F, 1.2D, 1.8D));
        }
        if (!(mob.getTarget() instanceof PurpleVillagerGeneralEntity)) {
            mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, PurpleVillagerGeneralEntity.class, 12.0F, 1.2D, 1.8D));
        }
        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, Monster.class, false, (target) -> !(target instanceof PlayerMobEntity)));
        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, AbstractIllager.class, true, false));
        mob.goalSelector.addGoal(3, new MeleeAttackGoal(mob, 1.5D, false) {
            protected double getAttackReachSqr(LivingEntity livingentity) {
                return (double) (mob.getBbWidth() * mob.getBbWidth() + livingentity.getBbWidth());
            }
        });
        mob.goalSelector.addGoal(4, new RandomStrollGoal(mob, 1.0D));
        mob.goalSelector.addGoal(5, new OpenDoorGoal(mob, true));
        mob.targetSelector.addGoal(6, new HurtByTargetGoal(mob, new Class[0]));
        mob.goalSelector.addGoal(7, new OpenDoorGoal(mob, false));
        mob.goalSelector.addGoal(8, new RandomLookAroundGoal(mob));
        mob.goalSelector.addGoal(9, new FloatGoal(mob));
    }

    public static void registerGoalForCrazyNpc(PathfinderMob mob) {
        mob.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, Monster.class, false, false));
        mob.targetSelector.addGoal(2, new HurtByTargetGoal(mob, new Class[0]));
        mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(mob, PlayerNpcEntity.class, true, false));
        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, VillagerScoutEntity.class, false, false));
        mob.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(mob, VillagerScoutCaptainEntity.class, false, false));
        mob.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(mob, RedVillagerGeneralEntity.class, false, false));
        mob.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(mob, BlueVillagerGeneralEntity.class, false, false));
        mob.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(mob, GreenVillagerGeneralEntity.class, false, false));
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(mob, PurpleVillagerGeneralEntity.class, false, false));
        mob.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(mob, BlueDemonEntity.class, false, false));
        mob.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(mob, BlueDemon2Entity.class, false, false));
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(mob, HerobrineCloneEntity.class, true, false));
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(mob, ShadowHerobrineCloneEntity.class, true, false));
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(mob, HerobrineChrisEntity.class, true, false));
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(mob, LowHerobrineCloneEntity.class, true, false));
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(mob, LowShadowHerobrineCloneEntity.class, true, false));
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(mob, Herobrine7Entity.class, true, false));
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(mob, NullEntity.class, true, false));
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(mob, AegisHerobrineEntity.class, true, false));
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(mob, SwordsmanHerobrineEntity.class, true, false));
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(mob, GlaiveHerobrineEntity.class, true, false));
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(mob, ReaperHerobrineEntity.class, true, false));
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(mob, SledgehammerHerobrineEntity.class, true, false));
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(mob, ShadowHerobrineEntity.class, true, false));
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(mob, ArmoredHerobrineEntity.class, true, false));
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(mob, EliteHerobrineKnockedEntity.class, true, false));
        mob.targetSelector.addGoal(20, new NearestAttackableTargetGoal<>(mob, AlexEntity.class, false, false));
        mob.targetSelector.addGoal(20, new NearestAttackableTargetGoal<>(mob, ChrisEntity.class, false, false));
        mob.targetSelector.addGoal(21, new NearestAttackableTargetGoal<>(mob, Player.class, true, true));
        mob.goalSelector.addGoal(22, new MeleeAttackGoal(mob, 1.5D, false) {
            protected double getAttackReachSqr(LivingEntity livingentity) {
                return (double) (mob.getBbWidth() * mob.getBbWidth() + livingentity.getBbWidth());
            }
        });
        mob.goalSelector.addGoal(23, new RandomStrollGoal(mob, 1.0D));
        mob.goalSelector.addGoal(24, new RandomLookAroundGoal(mob));
        mob.goalSelector.addGoal(25, new FloatGoal(mob));
    }

    public static void attackAllMonstersGoals(PlayerNpcEntity playerMobEntity) {
        playerMobEntity.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(playerMobEntity, Monster.class, true, (target) -> !(target instanceof PlayerMobEntity)));
        playerMobEntity.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(playerMobEntity, AbstractIllager.class, true, false));
        playerMobEntity.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(playerMobEntity, HerobrineCloneEntity.class, true, false));
        playerMobEntity.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(playerMobEntity, ShadowHerobrineCloneEntity.class, true, false));
        playerMobEntity.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(playerMobEntity, HerobrineChrisEntity.class, true, false));
        playerMobEntity.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(playerMobEntity, LowHerobrineCloneEntity.class, true, false));
        playerMobEntity.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(playerMobEntity, LowShadowHerobrineCloneEntity.class, true, false));
        playerMobEntity.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(playerMobEntity, Herobrine7Entity.class, true, false));
        playerMobEntity.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(playerMobEntity, NullEntity.class, true, false));
        playerMobEntity.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(playerMobEntity, AegisHerobrineEntity.class, true, false));
        playerMobEntity.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(playerMobEntity, SwordsmanHerobrineEntity.class, true, false));
        playerMobEntity.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(playerMobEntity, GlaiveHerobrineEntity.class, true, false));
        playerMobEntity.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(playerMobEntity, ReaperHerobrineEntity.class, true, false));
        playerMobEntity.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(playerMobEntity, SledgehammerHerobrineEntity.class, true, false));
        playerMobEntity.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(playerMobEntity, ShadowHerobrineEntity.class, true, false));
        playerMobEntity.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(playerMobEntity, ArmoredHerobrineEntity.class, true, false));
        playerMobEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(playerMobEntity, BlueDemonEntity.class, true));
        playerMobEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(playerMobEntity, BlueDemon2Entity.class, true));
        playerMobEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(playerMobEntity, EliteHerobrineKnockedEntity.class, true));
    }

    public static void runAwayFromHerobrineGoals(PathfinderMob playerMobEntity, float distance) {
        if (!(playerMobEntity.getTarget() instanceof HerobrineMob)) {
            playerMobEntity.goalSelector.addGoal(1, new AvoidEntityGoal<>(playerMobEntity, HerobrineMob.class, distance, 1.2D, 1.8D));
        }
        if (!(playerMobEntity.getTarget() instanceof HerobrineGregEntity)) {
            playerMobEntity.goalSelector.addGoal(1, new AvoidEntityGoal<>(playerMobEntity, HerobrineGregEntity.class, distance, 1.2D, 1.8D));
        }
        if (!(playerMobEntity.getTarget() instanceof LowHerobrineCloneEntity)) {
            playerMobEntity.goalSelector.addGoal(1, new AvoidEntityGoal<>(playerMobEntity, LowHerobrineCloneEntity.class, distance, 1.2D, 1.8D));
        }
        if (!(playerMobEntity.getTarget() instanceof LowShadowHerobrineCloneEntity)) {
            playerMobEntity.goalSelector.addGoal(1, new AvoidEntityGoal<>(playerMobEntity, LowShadowHerobrineCloneEntity.class, distance, 1.2D, 1.8D));
        }
    }

    public static void runAwayFromVillagerArmyGoals(PlayerMobEntity playerMobEntity) {
        if (!(playerMobEntity.getTarget() instanceof VillagerScoutEntity)) {
            playerMobEntity.goalSelector.addGoal(1, new AvoidEntityGoal<>(playerMobEntity, VillagerScoutEntity.class, 12.0F, 1.2D, 1.8D));
        }
        if (!(playerMobEntity.getTarget() instanceof VillagerScoutCaptainEntity)) {
            playerMobEntity.goalSelector.addGoal(1, new AvoidEntityGoal<>(playerMobEntity, VillagerScoutCaptainEntity.class, 12.0F, 1.2D, 1.8D));
        }
        if (!(playerMobEntity.getTarget() instanceof BlueVillagerGeneralEntity)) {
            playerMobEntity.goalSelector.addGoal(1, new AvoidEntityGoal<>(playerMobEntity, BlueVillagerGeneralEntity.class, 12.0F, 1.2D, 1.8D));
        }
        if (!(playerMobEntity.getTarget() instanceof GreenVillagerGeneralEntity)) {
            playerMobEntity.goalSelector.addGoal(1, new AvoidEntityGoal<>(playerMobEntity, GreenVillagerGeneralEntity.class, 12.0F, 1.2D, 1.8D));
        }
        if (!(playerMobEntity.getTarget() instanceof RedVillagerGeneralEntity)) {
            playerMobEntity.goalSelector.addGoal(1, new AvoidEntityGoal<>(playerMobEntity, RedVillagerGeneralEntity.class, 12.0F, 1.2D, 1.8D));
        }
        if (!(playerMobEntity.getTarget() instanceof PurpleVillagerGeneralEntity)) {
            playerMobEntity.goalSelector.addGoal(1, new AvoidEntityGoal<>(playerMobEntity, PurpleVillagerGeneralEntity.class, 12.0F, 1.2D, 1.8D));
        }
    }

    public static void attackAllNpcGoals(Mob playerMobEntity) {
        playerMobEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(playerMobEntity, AlexEntity.class, true));
        playerMobEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(playerMobEntity, JevEntity.class, true));
        playerMobEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(playerMobEntity, ChrisEntity.class, true));
        playerMobEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(playerMobEntity, SteveEntity.class, true));
        playerMobEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(playerMobEntity, Steve2Entity.class, true));
        playerMobEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(playerMobEntity, AngrySteveEntity.class, true));
    }

    public static void attackAllVillagerArmyGoal(Mob playerMobEntity) {
        playerMobEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(playerMobEntity, VillagerScoutEntity.class, true));
        playerMobEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(playerMobEntity, VillagerScoutCaptainEntity.class, true));
        playerMobEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(playerMobEntity, RedVillagerGeneralEntity.class, true));
        playerMobEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(playerMobEntity, BlueVillagerGeneralEntity.class, true));
        playerMobEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(playerMobEntity, GreenVillagerGeneralEntity.class, true));
        playerMobEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(playerMobEntity, PurpleVillagerGeneralEntity.class, true));
    }
}
