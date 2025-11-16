package com.pla.annoyingvillagers.procedures;

import com.hm.efn.gameasset.animations.EFNGreatSwordAnimations;
import com.hm.efn.gameasset.animations.EFNTachiAnimations;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.capabilities.AVCategories;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.item.*;
import com.pla.annoyingvillagers.network.ClientboundGlaiveExplosionFx;
import com.pla.annoyingvillagers.network.ClientboundMuteExplosionAtPos;
import com.pla.annoyingvillagers.skill.DemoniacVoltageReaverSkill;
import com.pla.annoyingvillagers.skill.EnderGlaiveSkill;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.util.SnakeBladeHit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsNapoleon;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;

import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpecialAttackOnKeyPressedProcedure {
    private static final String NBT_SPECIAL_CD = "SpecialAttackCooldown";
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase != TickEvent.Phase.END) return;
        if (player.level().isClientSide()) return;

        CompoundTag data = player.getPersistentData();

        if (player.tickCount % 20 == 0 && data.contains(NBT_SPECIAL_CD)) {
            int coolDownValue = data.getInt(NBT_SPECIAL_CD);
            if (coolDownValue > 0) {
                data.putInt(NBT_SPECIAL_CD, coolDownValue - 1);
            } else {
                data.remove(NBT_SPECIAL_CD);
            }
        }
    }

    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) return;

        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (patch == null) return;

        if (entity.getPersistentData().getInt(NBT_SPECIAL_CD) > 0) {
            return;
        }

        AssetAccessor<? extends DynamicAnimation> dynamicAnimation = Objects.requireNonNull(patch.getAnimator().getPlayerFor(null)).getAnimation();
        if (dynamicAnimation instanceof LongHitAnimation) return;

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

        PlayerPatch<?> playerpatch = EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (livingEntityPatch != null && entity instanceof Player player) {
            // Check by item
            ItemStack holdingItem = player.getMainHandItem();
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.ENDER_AEGIS.get())) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    if (holdingItem.getTag() != null && holdingItem.getTag().getBoolean("SecondForm")) {
                        livingEntityPatch.playAnimationSynchronized(WOMAnimations.RAVANGER_CHARGE, 0.0F);
                    } else {
                        livingEntityPatch.playAnimationSynchronized(EFNGreatSwordAnimations.NG_GREATSWORD_AIRSLASH, 0.0F);
                    }
                    player.getPersistentData().putInt(NBT_SPECIAL_CD, 2);
                    return;
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.ENDER_GLAIVE.get())) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
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
                                        Vec3 tipPos = EnderGlaiveItem.getJointWithTranslation(
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
                                            Vec3 glaivePos = EnderGlaiveItem.getJointWithTranslation(player, new Vec3f(0, 0, 0),
                                                    Armatures.BIPED.get().toolR, 1.3F, 2.3F);
                                            Vec3 explosionPos = EnderGlaiveItem.getJointWithTranslation(player, new Vec3f(0, 0, 0),
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
                    player.getPersistentData().putInt(NBT_SPECIAL_CD, 2);
                    return;
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get())) {
                if (!entity.level().isClientSide() && entity.getServer() != null
                        && holdingItem.getTag() != null && !holdingItem.getTag().getBoolean("SnakeAnimation")) {
                    livingEntityPatch.playAnimationSynchronized(WOMAnimations.TORMENT_CHARGED_ATTACK_1, 0.0F);
                    player.getPersistentData().putInt(NBT_SPECIAL_CD, 2);
                }
                return;
            }

            // Check by categories
            if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.SWORD && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.TACHI && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.LONGSWORD && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.UCHIGATANA) {
                if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.AXE) {
                    if (playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE) {
                        if (entity.isShiftKeyDown()) {
                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                livingEntityPatch.playAnimationSynchronized(Animations.VINDICATOR_SWING_AXE3, 0.0F);
                            }
                        } else {
                            if (entity.getPersistentData().getDouble("axe_a") < 1.0D) {
                                entity.getPersistentData().putDouble("axe_a", 1.5D);
                                if (!entity.level().isClientSide() && entity.getServer() != null) {
                                    livingEntityPatch.playAnimationSynchronized(Animations.VINDICATOR_SWING_AXE2, 0.0F);
                                }

                                new DelayedTask(8) {
                                    @Override
                                    public void run() {
                                        entity.getPersistentData().putDouble("axe_a", 2.0D);
                                    }
                                };
                            } else if (entity.getPersistentData().getDouble("axe_a") == 2.0D) {
                                entity.getPersistentData().putDouble("axe_a", 2.5D);
                                if (!entity.level().isClientSide() && entity.getServer() != null) {
                                    livingEntityPatch.playAnimationSynchronized(Animations.VINDICATOR_SWING_AXE3, 0.0F);
                                }

                                new DelayedTask(8) {
                                    @Override
                                    public void run() {
                                        entity.getPersistentData().putDouble("axe_a", 3.0D);
                                    }
                                };
                            } else if (entity.getPersistentData().getDouble("axe_a") == 3.0D) {
                                entity.getPersistentData().putDouble("axe_a", 3.5D);
                                if (!entity.level().isClientSide() && entity.getServer() != null) {
                                    livingEntityPatch.playAnimationSynchronized(WOMAnimations.TORMENT_BERSERK_AUTO_1, 0.0F);
                                }
                                new DelayedTask(8) {
                                    @Override
                                    public void run() {
                                        entity.getPersistentData().putDouble("axe_a", 4.0D);
                                    }
                                };

                            } else if (entity.getPersistentData().getDouble("axe_a") == 4.0D) {
                                entity.getPersistentData().putDouble("axe_a", 4.5D);
                                if (!entity.level().isClientSide() && entity.getServer() != null) {
                                    livingEntityPatch.playAnimationSynchronized(WOMAnimations.TORMENT_BERSERK_AUTO_2, 0.0F);
                                }
                            } else if (entity.getPersistentData().getDouble("axe_a") == 5.0D) {
                                entity.getPersistentData().putDouble("axe_a", 5.5D);
                                if (!entity.level().isClientSide() && entity.getServer() != null) {
                                    livingEntityPatch.playAnimationSynchronized(Animations.GREATSWORD_AIR_SLASH, 0.0F);
                                }

                                new DelayedTask(8) {
                                    @Override
                                    public void run() {
                                        entity.getPersistentData().putDouble("axe_a", 6.0D);
                                    }
                                };

                            } else if (entity.getPersistentData().getDouble("axe_a") == 6.0D) {
                                entity.getPersistentData().putDouble("axe_a", 6.5D);
                                if (!entity.level().isClientSide() && entity.getServer() != null) {
                                    livingEntityPatch.playAnimationSynchronized(Animations.VINDICATOR_SWING_AXE3, 0.0F);
                                }

                                new DelayedTask(8) {
                                    @Override
                                    public void run() {
                                        entity.getPersistentData().putDouble("axe_a", 0.0D);
                                    }
                                };

                            }
                        }
                    } else {
                        if (entity.getPersistentData().getDouble("axe_a") > 2.0D) {
                            entity.getPersistentData().putDouble("axe_a", 0.0D);
                        }

                        if (dynamicAnimation == Animations.THE_GUILLOTINE) {
                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                livingEntityPatch.playAnimationSynchronized(AVAnimations.AXE_FUN_SKILL, 0.0F);
                            }
                        } else if (entity.getPersistentData().getDouble("axe_a") == 0.0D) {
                            entity.getPersistentData().putDouble("axe_a", 1.5D);
                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                livingEntityPatch.playAnimationSynchronized(AVAnimations.AXE_HEAVY_AUTO_1, 0.0F);
                            }

                            new DelayedTask(10) {
                                @Override
                                public void run() {
                                    entity.getPersistentData().putDouble("axe_a", 2.0D);
                                }
                            };
                        } else if (entity.getPersistentData().getDouble("axe_a") == 2.0D) {
                            entity.getPersistentData().putDouble("axe_a", 2.5D);
                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                livingEntityPatch.playAnimationSynchronized(AVAnimations.AXE_HEAVY_AUTO_2, 0.0F);
                            }

                            new DelayedTask(10) {
                                @Override
                                public void run() {
                                    entity.getPersistentData().putDouble("axe_a", 0.0D);
                                }
                            };
                        }
                    }
                } else if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.FIST && !entity.getPersistentData().getBoolean("fist_a")) {
                    if (!player.level().isClientSide() &&
                            (player.getMainHandItem().getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get()) ||
                                    player.getOffhandItem().getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get()))) {
                        livingEntityPatch.playAnimationSynchronized(Animations.BIPED_LANDING, 0.0F);
                        HerobrineEnderEyeItem.startShadowObsidianMachineGun((ServerLevel) player.level(), player);
                        if (player.getMainHandItem().getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get())) {
                            player.getMainHandItem().hurtAndBreak(10, player, p -> {
                            });
                        } else if (player.getOffhandItem().getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get())) {
                            player.getOffhandItem().hurtAndBreak(10, player, p -> {
                            });
                        }
                        entity.getPersistentData().putBoolean("fist_a", true);
                        
                        new DelayedTask(60) {
                            @Override
                            public void run() {
                                entity.getPersistentData().putBoolean("fist_a", false);
                            }
                        };
                        return;
                    }
                    if (entity.isSprinting()) {
                        if (entity.isShiftKeyDown()) {
                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                livingEntityPatch.playAnimationSynchronized(AVAnimations.WHIRLWIND_KICK_LEFT, 0.0F);
                            }

                            entity.getPersistentData().putBoolean("fist_a", true);
                            
                            new DelayedTask(20) {
                                @Override
                                public void run() {
                                }
                            };
                            new DelayedTask(35) {
                                @Override
                                public void run() {
                                    entity.getPersistentData().putBoolean("fist_a", false);
                                }
                            };
                            new DelayedTask(9) {
                                @Override
                                public void run() {
                                    entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * 0.25D, 0.0D, entity.getLookAngle().z * 0.25D));
                                }
                            };
                        } else {
                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                livingEntityPatch.playAnimationSynchronized(AVAnimations.WHIRLWIND_KICK, 0.0F);
                            }

                            entity.getPersistentData().putBoolean("fist_a", true);
                            entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * 0.2D, entity.getLookAngle().y * 0.5D, entity.getLookAngle().z * 0.2D));
                            new DelayedTask(40) {
                                @Override
                                public void run() {
                                    entity.getPersistentData().putBoolean("fist_a", false);
                                }
                            };
                        }
                    } else if (entity.isShiftKeyDown()) {
                        if (!entity.level().isClientSide() && entity.getServer() != null) {
                            livingEntityPatch.playAnimationSynchronized(AVAnimations.FIST_UP, 0.0F);
                        }
                    } else if (playerpatch != null) {
                        AssetAccessor<? extends DynamicAnimation> dynamicanimation1 = playerpatch.getAnimator().getPlayerFor(null).getAnimation();

                        if (dynamicanimation1 == AVAnimations.FIST_UP) {
                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                livingEntityPatch.playAnimationSynchronized(AVAnimations.FIST_LEFT, 0.0F);
                            }

                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                try {
                                    entity.getServer().getCommands().getDispatcher().execute(
                                            "effect give @s epicfight:stun_immunity 3 5 true",
                                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {

                                }
                            }

                            entity.getPersistentData().putBoolean("fist_a", true);
                            new DelayedTask(20) {
                                @Override
                                public void run() {
                                    entity.getPersistentData().putBoolean("fist_a", false);
                                }
                            };
                        } else {
                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                livingEntityPatch.playAnimationSynchronized(AVAnimations.FIST_DASH, 0.0F);
                            }

                            entity.getPersistentData().putBoolean("fist_a", true);
                            new DelayedTask(40) {
                                @Override
                                public void run() {
                                    entity.getPersistentData().putBoolean("fist_a", false);
                                }
                            };
                        }
                    }
                    if (!(entity instanceof LivingEntity livingEntity) || !entity.isAlive()) {
                        return;
                    }
                    ItemStack itemStack = livingEntity.getMainHandItem();
                    if (itemStack.getItem() instanceof ObsidianWeaponItem obsidianWeaponItem) {
                        obsidianWeaponItem.specialAttack(livingEntity);
                    } else if (itemStack.getItem() instanceof ShadowObsidianWeaponItem shadowObsidianWeaponItem) {
                        shadowObsidianWeaponItem.specialAttack(livingEntity);
                    } else if (itemStack.getItem() instanceof ShadowObsidianPillarItem shadowObsidianPillarItem) {
                        shadowObsidianPillarItem.specialAttack(livingEntity);
                    }
                }
            } else if ((playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.SWORD || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD) && (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.TACHI || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI) && (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.TACHI || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD) && (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.SWORD || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI)) {
                if (!(entity instanceof LivingEntity livingEntity) || !entity.isAlive()) {
                    return;
                }
                ItemStack itemStack = livingEntity.getMainHandItem();
                if (entity.getPersistentData().getDouble("sword_a") > 3.5D) {
                    entity.getPersistentData().putDouble("sword_a", 0.0D);
                } else if (entity.getPersistentData().getDouble("sword_a") == 0.0D) {
                    entity.getPersistentData().putDouble("sword_a", 1.5D);
                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        if (itemStack.getItem() instanceof ShadowObsidianSwordItem) {
                            livingEntityPatch.playAnimationSynchronized(WOMAnimations.TORMENT_BERSERK_DASH, 0.0F);
                        } else {
                            livingEntityPatch.playAnimationSynchronized(AVAnimations.SWORD_HEAVY_AUTO_1, 0.0F);
                        }
                    }
                    new DelayedTask(10) {
                        @Override
                        public void run() {
                            entity.getPersistentData().putDouble("sword_a", 2.0D);
                        }
                    };
                } else if (entity.getPersistentData().getDouble("sword_a") == 2.0D) {
                    entity.getPersistentData().putDouble("sword_a", 2.5D);
                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        if (itemStack.getItem() instanceof ShadowObsidianSwordItem) {
                            livingEntityPatch.playAnimationSynchronized(WOMAnimations.TORMENT_BERSERK_DASH, 0.0F);
                        } else {
                            livingEntityPatch.playAnimationSynchronized(AVAnimations.SWORD_HEAVY_AUTO_2, 0.0F);
                        }
                    }
                    new DelayedTask(10) {
                        @Override
                        public void run() {
                            entity.getPersistentData().putDouble("sword_a", 3.0D);
                        }
                    };
                } else if (entity.getPersistentData().getDouble("sword_a") == 3.0D) {
                    entity.getPersistentData().putDouble("sword_a", 3.5D);
                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        if (itemStack.getItem() instanceof ShadowObsidianSwordItem) {
                            livingEntityPatch.playAnimationSynchronized(WOMAnimations.TORMENT_BERSERK_DASH, 0.0F);
                        } else {
                            livingEntityPatch.playAnimationSynchronized(AVAnimations.SWORD_HEAVY_AUTO_3, 0.0F);
                        }
                    }

                    new DelayedTask(10) {
                        @Override
                        public void run() {
                            entity.getPersistentData().putDouble("sword_a", 0.0D);
                        }
                    };
                }
            } else {
                if (dynamicAnimation == Animations.DANCING_EDGE) {
                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.DUAL_SWORD_SKILL, 0.0F);
                    }
                } else {
                    if (entity.getPersistentData().getDouble("sword_a") < 1.0D) {
                        entity.getPersistentData().putDouble("sword_a", 1.0D);
                    }

                    if (entity.getPersistentData().getDouble("sword_a") == 1.0D) {
                        entity.getPersistentData().putDouble("sword_a", 1.5D);
                        if (!entity.level().isClientSide() && entity.getServer() != null) {
                            livingEntityPatch.playAnimationSynchronized(Animations.DAGGER_DUAL_DASH, 0.0F);
                        }

                        new DelayedTask(10) {
                            @Override
                            public void run() {
                                entity.getPersistentData().putDouble("sword_a", 2.0D);
                            }
                        };
                    } else if (entity.getPersistentData().getDouble("sword_a") == 2.0D) {
                        entity.getPersistentData().putDouble("sword_a", 2.5D);
                        if (!entity.level().isClientSide() && entity.getServer() != null) {
                            livingEntityPatch.playAnimationSynchronized(Animations.LONGSWORD_AUTO2, 0.0F);
                        }

                        new DelayedTask(10) {
                            @Override
                            public void run() {
                                entity.getPersistentData().putDouble("sword_a", 3.0D);
                            }
                        };
                    } else if (entity.getPersistentData().getDouble("sword_a") == 3.0D) {
                        entity.getPersistentData().putDouble("sword_a", 3.5D);
                        if (!entity.level().isClientSide() && entity.getServer() != null) {
                            livingEntityPatch.playAnimationSynchronized(AVAnimations.DUAL_DANCING_EDGE, 0.0F);
                        }

                        new DelayedTask(20) {
                            @Override
                            public void run() {
                                entity.getPersistentData().putDouble("sword_a", 4.0D);
                            }
                        };
                    } else if (entity.getPersistentData().getDouble("sword_a") == 4.0D) {
                        entity.getPersistentData().putDouble("sword_a", 4.5D);
                        if (!entity.level().isClientSide() && entity.getServer() != null) {
                            livingEntityPatch.playAnimationSynchronized(AVAnimations.DUAL_SWORD_DANCING_EDGE, 0.0F);
                        }

                        new DelayedTask(20) {
                            @Override
                            public void run() {
                                entity.getPersistentData().putDouble("sword_a", 1.0D);
                            }
                        };
                    }
                }
            }

            if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.GREATSWORD || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.LEGENDARY_SWORD || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.HARD_GREAT_SWORD) {
                LivingEntityPatch<?> livingentitypatch1 = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                AssetAccessor<? extends DynamicAnimation> dynamicanimation2 = livingentitypatch1.getAnimator().getPlayerFor(null).getAnimation();

                if (dynamicanimation2 != Animations.STEEL_WHIRLWIND && dynamicanimation2 != Animations.STEEL_WHIRLWIND_CHARGING) {
                    if (!(entity instanceof LivingEntity livingEntity) || !entity.isAlive()) {
                        return;
                    }
                    ItemStack itemStack = livingEntity.getMainHandItem();
                    if (entity.getPersistentData().getDouble("sword_a") < 1.0D) {
                        entity.getPersistentData().putDouble("sword_a", 1.5D);
                        if (!entity.level().isClientSide() && entity.getServer() != null) {
                            if (itemStack.getItem() instanceof ObsidianSledgehammerItem) {
                                livingEntityPatch.playAnimationSynchronized(WOMAnimations.TORMENT_BERSERK_DASH, 0.0F);
                            } else {
                                livingEntityPatch.playAnimationSynchronized(AVAnimations.GIANT_WHIRLWIND, 0.0F);
                            }
                        }

                        new DelayedTask(30) {
                            @Override
                            public void run() {
                                entity.getPersistentData().putDouble("sword_a", 2.0D);
                            }
                        };
                    } else if (entity.getPersistentData().getDouble("sword_a") == 2.0D) {
                        entity.getPersistentData().putDouble("sword_a", 2.5D);
                        if (!entity.level().isClientSide() && entity.getServer() != null) {
                            if (itemStack.getItem() instanceof ObsidianSledgehammerItem) {
                                livingEntityPatch.playAnimationSynchronized(WOMAnimations.TORMENT_BERSERK_DASH, 0.0F);
                            } else {
                                livingEntityPatch.playAnimationSynchronized(AVAnimations.GIANT_WHIRLWIND_2, 0.0F);
                            }
                        }

                        new DelayedTask(20) {
                            @Override
                            public void run() {
                                entity.getPersistentData().putDouble("sword_a", 0.0D);
                            }
                        };
                    } else {
                        entity.getPersistentData().putDouble("sword_a", 0.0D);
                    }
                } else if (!entity.level().isClientSide() && entity.getServer() != null) {
                    livingEntityPatch.playAnimationSynchronized(AVAnimations.GREATSWORD_SKILL, 0.0F);
                }
            }

            if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.SPEAR) {
                if (entity.getPersistentData().getDouble("sword_a") < 1.0D) {
                    entity.getPersistentData().putDouble("sword_a", 1.5D);
                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.SPEAR_THRUST, 0.0F);
                    }

                    new DelayedTask(30) {
                        @Override
                        public void run() {
                            entity.getPersistentData().putDouble("sword_a", 0.0D);
                        }
                    };
                } else {
                    entity.getPersistentData().putDouble("sword_a", 0.0D);
                }
            }
        }
    }
}
