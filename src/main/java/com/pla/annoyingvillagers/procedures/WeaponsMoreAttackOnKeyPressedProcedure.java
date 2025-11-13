package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.capabilities.AVCategories;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.item.*;
import com.pla.annoyingvillagers.network.ClientboundGlaiveExplosionFx;
import com.pla.annoyingvillagers.network.ClientboundMuteExplosionAtPos;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;

public class WeaponsMoreAttackOnKeyPressedProcedure {
    private static boolean isSpectatorGamemode(Entity entity) {
        if (entity instanceof ServerPlayer sp) {
            return sp.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
        } else if (entity instanceof Player player && entity.level().isClientSide()) {
            var info = Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId());
            return info != null && info.getGameMode() == GameType.SPECTATOR;
        }
        return false;
    }

    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null || isSpectatorGamemode(entity)) return;

        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (patch == null) return;

        AssetAccessor<? extends DynamicAnimation> dynamicanimation = patch.getAnimator().getPlayerFor(null).getAnimation();
        if (dynamicanimation instanceof LongHitAnimation || entity.getPersistentData().getBoolean("kick_x")) return;

        entity.getPersistentData().putBoolean("kick_x", true);
        new DelayedTask(4) {
            @Override
            public void run() {
                entity.getPersistentData().putBoolean("kick_x", false);
            }
        };

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

        PlayerPatch<?> playerpatch = (PlayerPatch) EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);
        if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.SWORD && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.TACHI && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.LONGSWORD && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.UCHIGATANA) {
            if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.AXE) {
                if (playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE) {
                    if (entity.isShiftKeyDown()) {
                        if (!entity.level().isClientSide() && entity.getServer() != null) {
                            try {
                                entity.getServer().getCommands().getDispatcher().execute(
                                        "indestructible @s play \"dualaxes:biped/skill/spinning_death\" 0 1",
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {

                            }
                        }
                    } else {
                        if (entity.getPersistentData().getDouble("axe_a") < 1.0D) {
                            entity.getPersistentData().putDouble("axe_a", 1.5D);
                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                try {
                                    entity.getServer().getCommands().getDispatcher().execute(
                                            "indestructible @s play \"annoyingvillagers:biped/combat/greatsword_dual_airslash\" 0 1",
                                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {

                                }
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
                                try {
                                    entity.getServer().getCommands().getDispatcher().execute(
                                            "indestructible @s play \"annoyingvillagers:biped/combat/greatsword_dual_auto_2\" 0 1",
                                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {

                                }
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
                                try {
                                    entity.getServer().getCommands().getDispatcher().execute(
                                            "indestructible @s play \"wom:biped/skill/torment_berserk_auto_1\" 0 1",
                                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {

                                }
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
                                try {
                                    entity.getServer().getCommands().getDispatcher().execute(
                                            "indestructible @s play \"wom:biped/skill/torment_berserk_auto2\" 0 1",
                                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {

                                }
                            }
                        } else if (entity.getPersistentData().getDouble("axe_a") == 5.0D) {
                            entity.getPersistentData().putDouble("axe_a", 5.5D);
                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                try {
                                    entity.getServer().getCommands().getDispatcher().execute(
                                            "indestructible @s play \"annoyingvillagers:biped/combat/greatsword_dual_auto_3\" 0 1",
                                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {

                                }
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
                                try {
                                    entity.getServer().getCommands().getDispatcher().execute(
                                            "indestructible @s play \"epicfight:illager/swing_axe3\" 0 1",
                                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {

                                }
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

                    if (dynamicanimation == Animations.THE_GUILLOTINE) {
                        if (!entity.level().isClientSide() && entity.getServer() != null) {
                            try {
                                entity.getServer().getCommands().getDispatcher().execute(
                                        "indestructible @s play \"annoyingvillagers:biped/combat/axe_fun_skill\" 0 1",
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {
                                
                            }
                        }
                    } else if (entity.getPersistentData().getDouble("axe_a") == 0.0D) {
                        entity.getPersistentData().putDouble("axe_a", 1.5D);
                        if (!entity.level().isClientSide() && entity.getServer() != null) {
                            try {
                                entity.getServer().getCommands().getDispatcher().execute(
                                        "indestructible @s play \"annoyingvillagers:biped/combat/axe_heavy_auto1\" 0 1",
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {
                                
                            }
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
                            try {
                                entity.getServer().getCommands().getDispatcher().execute(
                                        "indestructible @s play \"annoyingvillagers:biped/combat/axe_heavy_auto2\" 0 1",
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {
                                
                            }
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
                if (entity instanceof Player player && !player.level().isClientSide() &&
                        (player.getMainHandItem().getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get()) ||
                                player.getOffhandItem().getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get()))) {
                    try {
                        player.getServer().getCommands().getDispatcher().execute(
                                "indestructible @s play \"epicfight:biped/living/landing\" 0 1",
                                player.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                    HerobrineEnderEyeItem.startShadowObsidianMachineGun((ServerLevel) player.level(), player);
                    if (player.getMainHandItem().getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get())) {
                        player.getMainHandItem().hurtAndBreak(10, player, p -> {});
                    } else if (player.getOffhandItem().getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get())) {
                        player.getOffhandItem().hurtAndBreak(10, player, p -> {
                        });
                    }
                    entity.getPersistentData().putBoolean("fist_a", true);
                    entity.getPersistentData().putBoolean("kick_x", true);
                    new DelayedTask(60) {
                        @Override
                        public void run() {
                            entity.getPersistentData().putBoolean("kick_x", false);
                            entity.getPersistentData().putBoolean("fist_a", false);
                        }
                    };
                    return;
                }
                if (entity.isSprinting()) {
                    if (entity.isShiftKeyDown()) {
                        if (!entity.level().isClientSide() && entity.getServer() != null) {
                            try {
                                entity.getServer().getCommands().getDispatcher().execute(
                                        "indestructible @s play \"annoyingvillagers:biped/combat/whirlwind_kick_left\" 0 1",
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {
                                
                            }
                        }

                        entity.getPersistentData().putBoolean("fist_a", true);
                        entity.getPersistentData().putBoolean("kick_x", true);
                        new DelayedTask(20) {
                            @Override
                            public void run() {
                                entity.getPersistentData().putBoolean("kick_x", false);
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
                    }
                    else {
                        if (!entity.level().isClientSide() && entity.getServer() != null) {
                            try {
                                entity.getServer().getCommands().getDispatcher().execute(
                                        "indestructible @s play \"annoyingvillagers:biped/combat/whirlwind_kick\" 0 1",
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {
                                
                            }
                        }

                        entity.getPersistentData().putBoolean("fist_a", true);
                        entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * 0.2D, entity.getLookAngle().y * 0.5D, entity.getLookAngle().z * 0.2D));
                        entity.getPersistentData().putBoolean("kick_x", true);
                        new DelayedTask(20) {
                            @Override
                            public void run() {
                                entity.getPersistentData().putBoolean("kick_x", false);
                            }
                        };
                        new DelayedTask(40) {
                            @Override
                            public void run() {
                                entity.getPersistentData().putBoolean("fist_a", false);
                            }
                        };
                    }
                } else if (entity.isShiftKeyDown()) {
                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        try {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "indestructible @s play \"annoyingvillagers:biped/combat/fist_up\" 0 1",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                            
                        }
                    }
                } else if (playerpatch != null) {
                    AssetAccessor<? extends DynamicAnimation> dynamicanimation1 = playerpatch.getAnimator().getPlayerFor(null).getAnimation();

                    if (dynamicanimation1 == AVAnimations.FIST_UP) {
                        if (!entity.level().isClientSide() && entity.getServer() != null) {
                            try {
                                entity.getServer().getCommands().getDispatcher().execute(
                                        "indestructible @s play \"annoyingvillagers:biped/combat/fist_left\" 0 1",
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {
                                
                            }
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
                        entity.getPersistentData().putBoolean("kick_x", true);
                        new DelayedTask(20) {
                            @Override
                            public void run() {
                                entity.getPersistentData().putBoolean("kick_x", false);
                            }
                        };
                        new DelayedTask(20) {
                            @Override
                            public void run() {
                                entity.getPersistentData().putBoolean("fist_a", false);
                            }
                        };
                    } else {
                        if (!entity.level().isClientSide() && entity.getServer() != null) {
                            try {
                                entity.getServer().getCommands().getDispatcher().execute(
                                        "indestructible @s play \"annoyingvillagers:biped/combat/fist_dash\" 0 1",
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {
                                
                            }
                        }

                        entity.getPersistentData().putBoolean("fist_a", true);
                        entity.getPersistentData().putBoolean("kick_x", true);
                        new DelayedTask(25) {
                            @Override
                            public void run() {
                                entity.getPersistentData().putBoolean("kick_x", false);
                            }
                        };
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
                    try {
                        if (itemStack.getItem() instanceof ShadowObsidianSwordItem) {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "indestructible @s play \"wom:biped/skill/torment_berserk_dash\" 0 1",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } else {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "indestructible @s play \"annoyingvillagers:biped/combat/sword_heavy_auto1\" 0 1",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        }
                    } catch (CommandSyntaxException e) {
                        
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
                    try {
                        if (itemStack.getItem() instanceof ShadowObsidianSwordItem) {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "indestructible @s play \"wom:biped/skill/torment_berserk_dash\" 0 1",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } else {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "indestructible @s play \"annoyingvillagers:biped/combat/sword_heavy_auto2\" 0 1",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        }
                    } catch (CommandSyntaxException e) {
                        
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
                    try {
                        if (itemStack.getItem() instanceof ShadowObsidianSwordItem) {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "indestructible @s play \"wom:biped/skill/torment_berserk_dash\" 0 1",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } else {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "indestructible @s play \"annoyingvillagers:biped/combat/sword_heavy_auto3\" 0 1",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        }
                    } catch (CommandSyntaxException e) {
                        
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
            if (dynamicanimation == Animations.DANCING_EDGE) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "indestructible @s play \"annoyingvillagers:biped/combat/dual_sword_skill\" 0 1",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                        
                    }
                }
            } else {
                if (entity.getPersistentData().getDouble("sword_a") < 1.0D) {
                    entity.getPersistentData().putDouble("sword_a", 1.0D);
                }

                if (entity.getPersistentData().getDouble("sword_a") == 1.0D) {
                    entity.getPersistentData().putDouble("sword_a", 1.5D);
                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        try {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "indestructible @s play \"epicfight:biped/combat/dagger_dual_dash\" 0 1",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                            
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
                        try {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "indestructible @s play \"epicfight:biped/combat/longsword_auto2\" 0 1",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                            
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
                        try {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "indestructible @s play \"annoyingvillagers:biped/combat/dancing_edge\" 0 1",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                            
                        }
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
                        try {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "indestructible @s play \"annoyingvillagers:biped/combat/dual_sword_dancing_edge\" 0 1",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                            
                        }
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

        if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.GREATSWORD || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.LEGENDARY_SWORD || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.HARD_GREAT_SWORD ) {
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
                        try {
                            if (itemStack.getItem() instanceof ObsidianSledgehammerItem) {
                                entity.getServer().getCommands().getDispatcher().execute(
                                        "indestructible @s play \"wom:biped/skill/torment_berserk_dash\" 0 1",
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } else {
                                entity.getServer().getCommands().getDispatcher().execute(
                                        "indestructible @s play \"annoyingvillagers:biped/combat/giant_whirlwind\" 0 1",
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            }
                        } catch (CommandSyntaxException e) {
                            
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
                        try {
                            if (itemStack.getItem() instanceof ObsidianSledgehammerItem) {
                                entity.getServer().getCommands().getDispatcher().execute(
                                        "indestructible @s play \"wom:biped/skill/torment_berserk_dash\" 0 1",
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } else {
                                entity.getServer().getCommands().getDispatcher().execute(
                                        "indestructible @s play \"annoyingvillagers:biped/combat/giant_whirlwind_2\" 0 1",
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            }
                        } catch (CommandSyntaxException e) {
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
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "indestructible @s play \"annoyingvillagers:biped/combat/greatsword_skill\" 0 1",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                    
                }
            }
        }

        if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.SPEAR) {
            if (entity.getPersistentData().getDouble("sword_a") < 1.0D) {
                entity.getPersistentData().putDouble("sword_a", 1.5D);
                if (!(entity instanceof LivingEntity livingEntity) || !entity.isAlive()) {
                    return;
                }
                ItemStack itemStack = livingEntity.getMainHandItem();
                if (itemStack.getItem() instanceof EnderGlaiveItem enderGlaiveItem
                        && itemStack.getTag().getBoolean("SecondForm")
                        && itemStack.getTag().getInt("HitCount") >= 3) {
                    try {
                        if (!entity.level().isClientSide()) {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "indestructible @s play \"wom:biped/combat/agony_auto_1\" 0 1",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            new DelayedTask(3) {
                                @Override
                                public void run() {
                                    Vec3 tipPos = enderGlaiveItem.getJointWithTranslation(
                                            entity,
                                            new Vec3f(0.0F, 0.0F, 0.0F),
                                            Armatures.BIPED.get().toolR,
                                            4.3F,
                                            2.3F
                                    );
                                    BlockPos mutePos = BlockPos.containing(tipPos);
                                    AnnoyingVillagers.PACKET_HANDLER.send(
                                            PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity),
                                            new ClientboundMuteExplosionAtPos(mutePos, 4)
                                    );
                                    entity.level().explode(entity, tipPos.x, tipPos.y, tipPos.z,
                                            2.0F, true, Level.ExplosionInteraction.TNT);
                                    Vec3 glaivePos = enderGlaiveItem.getJointWithTranslation(entity, new Vec3f(0,0,0),
                                            Armatures.BIPED.get().toolR, 1.3F, 2.3F);
                                    Vec3 explosionPos = enderGlaiveItem.getJointWithTranslation(entity, new Vec3f(0,0,0),
                                            Armatures.BIPED.get().toolR, 10.3F, 2.3F);
                                    AnnoyingVillagers.PACKET_HANDLER.send(
                                            PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity),
                                            new ClientboundGlaiveExplosionFx(glaivePos, explosionPos)
                                    );
                                    entity.level().playSound((Player) null, new BlockPos((int) explosionPos.x, (int) explosionPos.y, (int) explosionPos.z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_shot")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                    itemStack.getTag().putInt("HitCount", itemStack.getTag().contains("HitCount") ? itemStack.getTag().getInt("HitCount") - 3 : 0);
                                }
                            };
                        }
                    } catch (CommandSyntaxException e) {

                    }
                } else {
                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        try {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "indestructible @s play \"annoyingvillagers:biped/combat/spear_thrust\" 0 1",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {

                        }
                    }
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
