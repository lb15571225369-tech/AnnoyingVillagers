package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.capabilities.AVCategories;
import com.pla.annoyingvillagers.entity.BabyEnderDragonEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.item.*;
import com.pla.annoyingvillagers.network.ClientboundGlaiveExplosionFx;
import com.pla.annoyingvillagers.network.ClientboundMuteExplosionAtPos;
import com.pla.annoyingvillagers.skill.EnderGlaiveSkill;
import com.pla.annoyingvillagers.skill.EnderSlayerScytheSkill;
import com.pla.annoyingvillagers.skill.WoopieTheSwordSkill;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsHerrscher;
import reascer.wom.gameasset.animations.weapons.AnimsNapoleon;
import reascer.wom.gameasset.animations.weapons.AnimsRuine;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.Objects;

public class SpecialAttackOnKeyPressedProcedure {
    private static final String NBT_SPECIAL_CD = "SpecialAttackCooldown";

    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) return;

        if (entity.getPersistentData().getInt(NBT_SPECIAL_CD) > 0) {
            return;
        }


        PlayerPatch<?> playerpatch = EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        AssetAccessor<? extends DynamicAnimation> dynamicAnimation = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getAnimation();
        if (dynamicAnimation.get() instanceof LongHitAnimation) {
            return;
        }

        if (entity instanceof Player player && !player.level().isClientSide() &&
                !player.getMainHandItem().getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get()) &&
                !player.getOffhandItem().getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get())) {
            player.getInventory().items.stream()
                    .filter(s -> !s.isEmpty() && s.is(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get()))
                    .findFirst()
                    .map(stack -> {
                        if (stack.getItem() instanceof HerobrineEnderEyeItem herobrineEnderEyeItem) {
                            var cooldowns = player.getCooldowns();
                            if (cooldowns.isOnCooldown(herobrineEnderEyeItem)) {
                                return false;
                            }

                            HerobrineEnderEyeItem.spawnAndShootDarkObPillars((ServerLevel) player.level(), player, 10);
                            player.getCooldowns().addCooldown(herobrineEnderEyeItem, 40);
                            stack.hurtAndBreak(5, player, p -> {
                            });
                            return true;
                        }
                        return false;
                    });
        }

        if (entity instanceof Player player) {
            // Check by item
            ItemStack holdingItem = player.getMainHandItem();
            ItemStack offHandItem = player.getOffhandItem();
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.ENDER_AEGIS.get())) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    if (holdingItem.getTag() != null && holdingItem.getTag().getBoolean("SecondForm")) {
                        livingEntityPatch.playAnimationSynchronized(WOMAnimations.RAVANGER_CHARGE, 0.0F);
                    } else {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.ENDER_AEGIS_BULL_CHARGE, 0.0F);
                    }
                    player.getPersistentData().putInt(NBT_SPECIAL_CD, 3);
                    return;
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.ENDER_GLAIVE.get())) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    livingEntityPatch.getOriginal().addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 40, 3));
                    livingEntityPatch.playAnimationSynchronized(AnimsNapoleon.NAPOLEON_SHOOT_3, 0.0F);
                    PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                    if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.ENDER_GLAIVE);
                        if (skillContainer != null && skillContainer.getSkill() instanceof EnderGlaiveSkill enderGlaiveSkill) {
                            if (skillContainer.getStack() >= 1) {
                                enderGlaiveSkill.getResourceType().consumer
                                        .consume(skillContainer, serverPlayerPatch, enderGlaiveSkill.getDefaultConsumptionAmount(serverPlayerPatch));

                                new DelayedTask(10) {
                                    @Override
                                    public void run() {
                                        Vec3 tipPos = EpicfightUtil.getJointWithTranslation(
                                                player,
                                                new Vec3f(0.0F, 0.0F, 0.0F),
                                                Armatures.BIPED.get().toolR,
                                                4.3F,
                                                2.3F
                                        );
                                        if (tipPos != null) {
                                            BlockPos mutePos = BlockPos.containing(tipPos);
                                            AnnoyingVillagers.PACKET_HANDLER.send(
                                                    PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player),
                                                    new ClientboundMuteExplosionAtPos(mutePos, 4)
                                            );
                                            player.level().explode(player, tipPos.x, tipPos.y, tipPos.z,
                                                    2.0F, true, Level.ExplosionInteraction.TNT);
                                            Vec3 glaivePos = EpicfightUtil.getJointWithTranslation(player, new Vec3f(0, 0, 0),
                                                    Armatures.BIPED.get().toolR, 1.3F, 2.3F);
                                            Vec3 explosionPos = EpicfightUtil.getJointWithTranslation(player, new Vec3f(0, 0, 0),
                                                    Armatures.BIPED.get().toolR, 10.3F, 2.3F);
                                            AnnoyingVillagers.PACKET_HANDLER.send(
                                                    PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player),
                                                    new ClientboundGlaiveExplosionFx(glaivePos, explosionPos)
                                            );
                                            if (explosionPos != null) {
                                                player.level().playSound((Player) null, new BlockPos((int) explosionPos.x, (int) explosionPos.y, (int) explosionPos.z), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_shot"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                            }
                                        }
                                    }
                                };
                            };
                        }
                    }
                    player.getPersistentData().putInt(NBT_SPECIAL_CD, 3);
                    return;
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get())) {
                if (!entity.level().isClientSide() && entity.getServer() != null
                        && holdingItem.getTag() != null && !holdingItem.getTag().getBoolean("SnakeAnimation")) {
                    livingEntityPatch.playAnimationSynchronized(WOMAnimations.TORMENT_CHARGED_ATTACK_1, 0.0F);
                    player.getPersistentData().putInt(NBT_SPECIAL_CD, 3);
                }
                return;
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.OBSIDIAN_SLEDGEHAMMER.get())) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    livingEntityPatch.playAnimationSynchronized(WOMAnimations.TORMENT_BERSERK_DASH, 0.0F);
                    player.getPersistentData().putInt(NBT_SPECIAL_CD, 3);
                    return;
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.ENDER_SLAYER_SCYTHE.get())) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    boolean success = false;
                    PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                    if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.ENDER_SLAYER_SCYTHE);
                        if (skillContainer != null && skillContainer.getStack() == 5
                                && entity.getPersistentData().contains("DragonUUID")
                                && entity.level() instanceof ServerLevel serverLevel
                                && skillContainer.getSkill() instanceof EnderSlayerScytheSkill) {
                            Entity dragon = serverLevel.getEntity(player.getPersistentData().getUUID("DragonUUID"));
                            if (dragon instanceof BabyEnderDragonEntity babyEnderDragonEntity) {
                                LivingEntity near = BabyEnderDragonEntity.getNearestLivingEntity(entity.level(), entity, 15.0D);
                                if (near != null) {
                                    Skill.setSkillStackSynchronize(skillContainer, 0);
                                    Skill.setSkillConsumptionSynchronize(skillContainer, 0.0F);
                                    babyEnderDragonEntity.summonBeam();
                                    success = true;
                                }
                            }
                        }
                    }
                    if (success) {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.CASTING_ONE_HAND_INWARD, 0.0F);
                    } else {
                        livingEntityPatch.playAnimationSynchronized(AnimsNapoleon.NAPOLEON_AUTO_3, 0.0F);
                    }
                    player.getPersistentData().putInt(NBT_SPECIAL_CD, 3);
                    return;
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get())
                    || offHandItem.getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get())) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    livingEntityPatch.playAnimationSynchronized(Animations.BIPED_LANDING, 0.0F);
                    HerobrineEnderEyeItem.startShadowObsidianMachineGun((ServerLevel) player.level(), player);
                    if (player.getMainHandItem().getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get())) {
                        player.getMainHandItem().hurtAndBreak(10, player, p -> {
                        });
                    } else if (player.getOffhandItem().getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get())) {
                        player.getOffhandItem().hurtAndBreak(10, player, p -> {
                        });
                    }
                    player.getPersistentData().putInt(NBT_SPECIAL_CD, 3);
                    return;
                }
            }
            if (holdingItem.getItem() instanceof BowItem) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    livingEntityPatch.playAnimationSynchronized(AVAnimations.BOW_AUTO_2, 0.0F);
                    player.getPersistentData().putInt(NBT_SPECIAL_CD, 2);
                    return;
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.LEGENDARY_SWORD.get())) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 60, 2));
                    livingEntityPatch.playAnimationSynchronized(AnimsNapoleon.NAPOLEON_WATERLOW_SHOOT, 0.0F);
                    new DelayedTask(20) {
                        @Override
                        public void run() {
                            livingEntityPatch.playAnimationSynchronized(AVAnimations.LEGENDARY_SWORD_WAKE_UP_ATTACK, 0.0F);
                        }
                    };
                    player.getPersistentData().putInt(NBT_SPECIAL_CD, 3);
                    return;
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get())) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    boolean success = false;
                    PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                    if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.WOOPIE_THE_SWORD);
                        if (skillContainer != null && skillContainer.getStack() == 1
                                && entity.level() instanceof ServerLevel
                                && skillContainer.getSkill() instanceof WoopieTheSwordSkill woopieTheSwordSkill) {
                            success = true;
                            woopieTheSwordSkill.getResourceType().consumer
                                    .consume(skillContainer, serverPlayerPatch, woopieTheSwordSkill.getDefaultConsumptionAmount(serverPlayerPatch));
                        }
                    }
                    if (success) {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.RUSH_SWORD, 0.0F);
                    } else {
                        livingEntityPatch.playAnimationSynchronized(AnimsRuine.RUINE_AUTO_4, 0.0F);
                    }
                    player.getPersistentData().putInt(NBT_SPECIAL_CD, 2);
                    return;
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.HARD_GREATSWORD.get())) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    livingEntityPatch.playAnimationSynchronized(AnimsHerrscher.HERRSCHER_AUTO_2, 0.0F);
                    player.getPersistentData().putInt(NBT_SPECIAL_CD, 3);
                    return;
                }
            }

            // Check by categories
            if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.AXE
                    || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.AV_AXE) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    if (!entity.getPersistentData().contains("AxeCombo")) {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.AXE_HEAVY_AUTO_1, 0.0F);
                        entity.getPersistentData().putDouble("AxeCombo", 1.0);
                    } else if (entity.getPersistentData().getDouble("AxeCombo") == 1.0) {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.AXE_HEAVY_AUTO_2, 0.0F);
                        entity.getPersistentData().putDouble("AxeCombo", 2.0);
                    } else if (entity.getPersistentData().getDouble("AxeCombo") == 2.0) {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.AXE_FUN_SKILL, 0.0F);
                        entity.getPersistentData().remove("AxeCombo");
                    }
                    player.getPersistentData().putInt(NBT_SPECIAL_CD, 2);
                    return;
                }
            }

            if ((playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.SWORD
                    || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.LONGSWORD
                    || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.TACHI
                    || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.AV_LONGSWORD
                    || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.AV_TACHI
                    || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.DAGGER
                    || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.AV_SWORD)
                    && (playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                    || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == AVCategories.AV_SWORD
                    || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI
                    || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == AVCategories.AV_TACHI
                    || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE)) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    if (!entity.getPersistentData().contains("DualSwordCombo")) {
                        livingEntityPatch.playAnimationSynchronized(Animations.DAGGER_DUAL_DASH, 0.0F);
                        entity.getPersistentData().putDouble("DualSwordCombo", 1.0);
                    } else if (entity.getPersistentData().getDouble("DualSwordCombo") == 1.0) {
                        livingEntityPatch.playAnimationSynchronized(Animations.LONGSWORD_AUTO2, 0.0F);
                        entity.getPersistentData().putDouble("DualSwordCombo", 2.0);
                    } else if (entity.getPersistentData().getDouble("DualSwordCombo") == 2.0) {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.DUAL_DANCING_EDGE, 0.0F);
                        entity.getPersistentData().putDouble("DualSwordCombo", 3.0);
                    } else if (entity.getPersistentData().getDouble("DualSwordCombo") == 3.0) {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.DUAL_SWORD_DANCING_EDGE, 0.0F);
                        entity.getPersistentData().remove("DualSwordCombo");
                    }
                    player.getPersistentData().putInt(NBT_SPECIAL_CD, 2);
                    return;
                }
            }

            if ((playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.SWORD
                    || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.AV_SWORD
                    || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.LONGSWORD
                    || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.TACHI
                    || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.AV_LONGSWORD
                    || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.AV_TACHI
                    || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.DAGGER
                    || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.UCHIGATANA)
                    && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD
                    && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != AVCategories.AV_SWORD
                    && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI
                    && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != AVCategories.AV_TACHI
                    && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    if (!entity.getPersistentData().contains("SwordCombo")) {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.SWORD_HEAVY_AUTO_1, 0.0F);
                        entity.getPersistentData().putDouble("SwordCombo", 1.0);
                    } else if (entity.getPersistentData().getDouble("SwordCombo") == 1.0) {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.SWORD_HEAVY_AUTO_2, 0.0F);
                        entity.getPersistentData().putDouble("SwordCombo", 2.0);
                    } else if (entity.getPersistentData().getDouble("SwordCombo") == 2.0) {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.SWORD_HEAVY_AUTO_3, 0.0F);
                        entity.getPersistentData().remove("SwordCombo");
                    }
                    player.getPersistentData().putInt(NBT_SPECIAL_CD, 1);
                    return;
                }
            }

            if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.GREATSWORD
                    || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.AV_GREATSWORD) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    livingEntityPatch.playAnimationSynchronized(AVAnimations.GIANT_WHIRLWIND, 0.0F);
                    player.getPersistentData().putInt(NBT_SPECIAL_CD, 3);
                    return;
                }
            }

            if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.FIST
                    || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.NOT_WEAPON
                    || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.RANGED
                    || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.WOODEN_DOOR
                    || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.CRAFTING_TABLE
                    || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.TRAPDOOR
                    || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.LADDER) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    if (entity.isSprinting()) {
                        if (entity.isShiftKeyDown()) {
                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                livingEntityPatch.playAnimationSynchronized(AVAnimations.WHIRLWIND_KICK_LEFT, 0.0F);
                            }
                        } else {
                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                livingEntityPatch.playAnimationSynchronized(AVAnimations.WHIRLWIND_KICK, 0.0F);
                            }
                        }
                    } else {
                        if (!entity.getPersistentData().contains("FistCombo")) {
                            livingEntityPatch.playAnimationSynchronized(AVAnimations.FIST_LEFT, 0.0F);
                            entity.getPersistentData().putDouble("FistCombo", 1.0);
                        } else if (entity.getPersistentData().getDouble("FistCombo") == 1.0) {
                            livingEntityPatch.playAnimationSynchronized(AVAnimations.FIST_UP, 0.0F);
                            entity.getPersistentData().putDouble("FistCombo", 2.0);
                        } else if (entity.getPersistentData().getDouble("FistCombo") == 2.0) {
                            livingEntityPatch.playAnimationSynchronized(AVAnimations.FIST_DASH, 0.0F);
                            entity.getPersistentData().remove("FistCombo");
                        }
                    }
                    player.getPersistentData().putInt(NBT_SPECIAL_CD, 1);
                    return;
                }
            }

            if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.SPEAR
                    || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.TRIDENT
                    || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.AV_SPEAR) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    livingEntityPatch.playAnimationSynchronized(AVAnimations.SPEAR_THRUST, 0.0F);
                    player.getPersistentData().putInt(NBT_SPECIAL_CD, 2);
                    return;
                }
            }

            if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.WOODEN_DOOR) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    livingEntityPatch.playAnimationSynchronized(WOMAnimations.TORMENT_CHARGED_ATTACK_2, 0.0F);
                    player.getPersistentData().putInt(NBT_SPECIAL_CD, 2);
                    return;
                }
            }

            if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.CRAFTING_TABLE) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    livingEntityPatch.playAnimationSynchronized(WOMAnimations.TORMENT_AIRSLAM, 0.0F);
                    player.getPersistentData().putInt(NBT_SPECIAL_CD, 2);
                    return;
                }
            }

            if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.LADDER) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    livingEntityPatch.playAnimationSynchronized(Animations.VINDICATOR_SWING_AXE3, 0.0F);
                    player.getPersistentData().putInt(NBT_SPECIAL_CD, 2);
                    return;
                }
            }

            if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.TRAPDOOR) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    livingEntityPatch.playAnimationSynchronized(Animations.VINDICATOR_SWING_AXE2, 0.0F);
                    player.getPersistentData().putInt(NBT_SPECIAL_CD, 2);
                    return;
                }
            }
        }
    }
}
