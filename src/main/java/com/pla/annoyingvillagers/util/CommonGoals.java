package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.entity.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

public class CommonGoals {
    public static void registerGoalForHostileNpc(Monster monster) {
        monster.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
        monster.targetSelector.addGoal(1, new HurtByTargetGoal(monster, new Class[0]));
        monster.targetSelector.addGoal(2, new NearestAttackableTargetGoal(monster, Player.class, true, false));
        monster.targetSelector.addGoal(2, new NearestAttackableTargetGoal(monster, PlayerMobEntity.class, true, false));
        monster.targetSelector.addGoal(2, new NearestAttackableTargetGoal(monster, SteveEntity.class, true, false));
        monster.targetSelector.addGoal(2, new NearestAttackableTargetGoal(monster, Steve2Entity.class, true, false));
        monster.targetSelector.addGoal(2, new NearestAttackableTargetGoal(monster, AlexEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal(monster, Villager.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal(monster, IronGolem.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal(monster, VillagerScoutEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal(monster, VillagerScoutCaptainEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal(monster, RedVillagerGeneralEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal(monster, BlueVillagerGeneralEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal(monster, GreenVillagerGeneralEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal(monster, PurpleVillagerGeneralEntity.class, true, false));
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
        mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal(mob, Monster.class, true, false));
        mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal(mob, BlueDemonEntity.class, true, false));
        mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal(mob, BlueDemon2Entity.class, true, false));
        mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal(mob, HerobrineEntity.class, true, false));
        mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal(mob, Herobrine2Entity.class, true, false));
        mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal(mob, Herobrine3Entity.class, true, false));
        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal(mob, PlayerMobEntity.class, true, false));
        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal(mob, Player.class, true, false));
        mob.targetSelector.addGoal(4, new NearestAttackableTargetGoal(mob, SteveEntity.class, true, false));
        mob.targetSelector.addGoal(4, new NearestAttackableTargetGoal(mob, Steve2Entity.class, true, false));
        mob.targetSelector.addGoal(4, new NearestAttackableTargetGoal(mob, AlexEntity.class, true, false));
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
//        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal(mob, Herobrine7Entity.class, true, false));
//        mob.targetSelector.addGoal(4, new NearestAttackableTargetGoal(mob, HbGaoJiFenShenEntity.class, true, false));
//        mob.targetSelector.addGoal(5, new NearestAttackableTargetGoal(mob, DiJiherobrineEntity.class, true, false));
//        mob.targetSelector.addGoal(6, new NearestAttackableTargetGoal(mob, SteveEntity.class, true, false));
//        mob.targetSelector.addGoal(7, new NearestAttackableTargetGoal(mob, ShiTi303Entity.class, true, false));
//        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal(mob, KeLiSiEntity.class, true, false));
//        mob.targetSelector.addGoal(9, new NearestAttackableTargetGoal(mob, GeLeiGeEntity.class, true, false));
//        mob.targetSelector.addGoal(10, new NearestAttackableTargetGoal(mob, JianbingguoziEntity.class, true, false));
//        mob.targetSelector.addGoal(12, new NearestAttackableTargetGoal(mob, GraveEntity.class, true, false));
//        mob.targetSelector.addGoal(13, new NearestAttackableTargetGoal(mob, WanJia1Entity.class, true, false));
//        mob.targetSelector.addGoal(14, new NearestAttackableTargetGoal(mob, MrcolderEntity.class, true, false));
//        mob.targetSelector.addGoal(15, new NearestAttackableTargetGoal(mob, MrMudgeMonkeyEntity.class, true, true));
//        mob.targetSelector.addGoal(16, new NearestAttackableTargetGoal(mob, LingZhiEntity.class, true, false));
//        mob.targetSelector.addGoal(17, new NearestAttackableTargetGoal(mob, ZaiEZhiWangEntity.class, true, false));
//        mob.targetSelector.addGoal(19, new NearestAttackableTargetGoal(mob, Steve2Entity.class, true, false));
//        mob.targetSelector.addGoal(27, new NearestAttackableTargetGoal(mob, CunZhenFuLuEntity.class, true, true));
//        mob.targetSelector.addGoal(28, new NearestAttackableTargetGoal(mob, LanCunQiFuLuEntity.class, true, true));
//        mob.targetSelector.addGoal(29, new NearestAttackableTargetGoal(mob, LvcunqifuluEntity.class, true, true));
//        mob.targetSelector.addGoal(30, new NearestAttackableTargetGoal(mob, HongCunQiFuLuEntity.class, true, true));
//        mob.targetSelector.addGoal(31, new NearestAttackableTargetGoal(mob, ZiCunQiFuLuEntity.class, true, true));
    }

    public static void registerGoalForNeutralNpc(PathfinderMob mob) {
        mob.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal(mob, HerobrineEntity.class, false, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal(mob, Herobrine2Entity.class, false, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal(mob, Herobrine3Entity.class, false, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal(mob, BlueDemonEntity.class, false, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal(mob, BlueDemon2Entity.class, false, false));
        mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal(mob, Monster.class, false, false));
        mob.goalSelector.addGoal(2, new MeleeAttackGoal(mob, 1.2D, false) {
            protected double getAttackReachSqr(LivingEntity livingentity) {
                return (double) (mob.getBbWidth() * mob.getBbWidth() + livingentity.getBbWidth());
            }
        });
//        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal(mob, HbGaoJiFenShenEntity.class, false, false));
//        mob.targetSelector.addGoal(4, new NearestAttackableTargetGoal(mob, Herobrine7Entity.class, false, false));
//        mob.targetSelector.addGoal(5, new NearestAttackableTargetGoal(mob, DiJiherobrineEntity.class, false, false));
//        mob.targetSelector.addGoal(6, new NearestAttackableTargetGoal(mob, LingZhiEntity.class, false, false));
        mob.targetSelector.addGoal(7, new NearestAttackableTargetGoal(mob, BlueVillagerGeneralEntity.class, false, false));
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal(mob, GreenVillagerGeneralEntity.class, false, false));
        mob.targetSelector.addGoal(9, new NearestAttackableTargetGoal(mob, RedVillagerGeneralEntity.class, false, false));
        mob.targetSelector.addGoal(10, new NearestAttackableTargetGoal(mob, PurpleVillagerGeneralEntity.class, false, false));
        mob.targetSelector.addGoal(11, new NearestAttackableTargetGoal(mob, VillagerScoutCaptainEntity.class, false, false));
        mob.targetSelector.addGoal(12, new NearestAttackableTargetGoal(mob, VillagerScoutEntity.class, false, false));
//        mob.targetSelector.addGoal(13, new NearestAttackableTargetGoal(mob, ShiTi303Entity.class, false, false));
//        mob.targetSelector.addGoal(14, new NearestAttackableTargetGoal(mob, ZaiEZhiWangEntity.class, false, false));
        mob.goalSelector.addGoal(15, new MeleeAttackGoal(mob, 1.5D, false) {
            protected double getAttackReachSqr(LivingEntity livingentity) {
                return (double) (mob.getBbWidth() * mob.getBbWidth() + livingentity.getBbWidth());
            }
        });
        mob.goalSelector.addGoal(16, new RandomStrollGoal(mob, 1.0D));
        mob.goalSelector.addGoal(17, new OpenDoorGoal(mob, true));
        mob.targetSelector.addGoal(18, new HurtByTargetGoal(mob, new Class[0]));
        mob.goalSelector.addGoal(19, new OpenDoorGoal(mob, false));
        mob.goalSelector.addGoal(20, new RandomLookAroundGoal(mob));
        mob.goalSelector.addGoal(21, new FloatGoal(mob));
    }

    public static void registerGoalForCrazyNpc(PathfinderMob mob) {
        mob.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal(mob, Monster.class, false, false));
        mob.targetSelector.addGoal(2, new HurtByTargetGoal(mob, new Class[0]));
        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal(mob, VillagerScoutEntity.class, false, false));
        mob.targetSelector.addGoal(4, new NearestAttackableTargetGoal(mob, VillagerScoutCaptainEntity.class, false, false));
        mob.targetSelector.addGoal(5, new NearestAttackableTargetGoal(mob, RedVillagerGeneralEntity.class, false, false));
        mob.targetSelector.addGoal(6, new NearestAttackableTargetGoal(mob, BlueVillagerGeneralEntity.class, false, false));
        mob.targetSelector.addGoal(7, new NearestAttackableTargetGoal(mob, GreenVillagerGeneralEntity.class, false, false));
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal(mob, PurpleVillagerGeneralEntity.class, false, false));
        mob.targetSelector.addGoal(6, new NearestAttackableTargetGoal(mob, BlueDemonEntity.class, false, false));
        mob.targetSelector.addGoal(7, new NearestAttackableTargetGoal(mob, BlueDemon2Entity.class, false, false));
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal(mob, HerobrineEntity.class, false, false));
        mob.targetSelector.addGoal(7, new NearestAttackableTargetGoal(mob, Herobrine2Entity.class, false, false));
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal(mob, Herobrine3Entity.class, false, false));
        mob.targetSelector.addGoal(20, new NearestAttackableTargetGoal(mob, AlexEntity.class, false, false));
//        mob.targetSelector.addGoal(9, new NearestAttackableTargetGoal(mob, GraveEntity.class, false, false));
//        mob.targetSelector.addGoal(10, new NearestAttackableTargetGoal(mob, JianbingguoziEntity.class, false, false));
//        mob.targetSelector.addGoal(11, new NearestAttackableTargetGoal(mob, HbGaoJiFenShenEntity.class, false, false));
//        mob.targetSelector.addGoal(12, new NearestAttackableTargetGoal(mob, LingZhiEntity.class, false, false));
//        mob.targetSelector.addGoal(13, new NearestAttackableTargetGoal(mob, DiJiherobrineEntity.class, false, false));
//        mob.targetSelector.addGoal(14, new NearestAttackableTargetGoal(mob, Herobrine7Entity.class, false, false));
//        mob.targetSelector.addGoal(15, new NearestAttackableTargetGoal(mob, ShiTi303Entity.class, false, false));
//        mob.targetSelector.addGoal(16, new NearestAttackableTargetGoal(mob, Zaiezhiwang2Entity.class, false, false));
//        mob.targetSelector.addGoal(17, new NearestAttackableTargetGoal(mob, ZaiEZhiWangEntity.class, false, false));
//        mob.targetSelector.addGoal(18, new NearestAttackableTargetGoal(mob, DarkHerobrineEntity.class, false, false));
//        mob.targetSelector.addGoal(19, new NearestAttackableTargetGoal(mob, Grave2Entity.class, false, false));
//        mob.targetSelector.addGoal(20, new NearestAttackableTargetGoal(mob, Shiti303fenshenEntity.class, false, false));
        mob.targetSelector.addGoal(21, new NearestAttackableTargetGoal(mob, Player.class, true, true));
        mob.goalSelector.addGoal(22, new MeleeAttackGoal(mob, 1.5D, false) {
            protected double getAttackReachSqr(LivingEntity livingentity) {
                return (double) (mob.getBbWidth() * mob.getBbWidth() + livingentity.getBbWidth());
            }
        });
        mob.goalSelector.addGoal(23, new RandomStrollGoal(mob, 1.0D));
        mob.goalSelector.addGoal(24, new RandomLookAroundGoal(mob));
        mob.goalSelector.addGoal(25, new FloatGoal(mob));
    }
}
