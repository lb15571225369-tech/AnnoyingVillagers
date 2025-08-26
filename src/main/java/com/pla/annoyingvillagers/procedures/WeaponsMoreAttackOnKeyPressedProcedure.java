package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.capabilities.AVCategories;
import com.pla.annoyingvillagers.compat.aaa_particles.EnderGlaiveExplosionParticleEmitterInfo;
import com.pla.annoyingvillagers.entity.StealthAttackEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.item.EnderGlaiveItem;
import com.pla.annoyingvillagers.network.ClientboundGlaiveExplosionFx;
import com.pla.annoyingvillagers.util.DelayedTask;
import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import net.corruptdog.cdm.world.CorruptWeaponCategories;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
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

        DynamicAnimation dynamicanimation = patch.getAnimator().getPlayerFor(null).getAnimation();
        if (dynamicanimation instanceof LongHitAnimation || entity.getPersistentData().getBoolean("kick_x")) return;

        // Prevent immediate re-use
        entity.getPersistentData().putBoolean("kick_x", true);
        new DelayedTask(4) {
            @Override
            public void run() {
                entity.getPersistentData().putBoolean("kick_x", false);
            }
        };
        PlayerPatch<?> playerpatch = (PlayerPatch) EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);
        if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.SWORD && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.TACHI && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.LONGSWORD && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.UCHIGATANA && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CorruptWeaponCategories.YAMATO && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CorruptWeaponCategories.S_SWORD && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CorruptWeaponCategories.S_TACHI && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CorruptWeaponCategories.GREAT_TACHI && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != CorruptWeaponCategories.S_LONGSWORD) {
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
                    } else {
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
                    DynamicAnimation dynamicanimation1 = playerpatch.getAnimator().getPlayerFor(null).getAnimation();

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
            }
        } else if ((playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.SWORD || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD) && (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.TACHI || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI) && (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.TACHI || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD) && (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.SWORD || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI)) {
            if (entity.getPersistentData().getDouble("sword_a") > 3.5D) {
                entity.getPersistentData().putDouble("sword_a", 0.0D);
            } else if (entity.getPersistentData().getDouble("sword_a") == 0.0D) {
                entity.getPersistentData().putDouble("sword_a", 1.5D);
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "indestructible @s play \"annoyingvillagers:biped/combat/sword_heavy_auto1\" 0 1",
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
                                "indestructible @s play \"annoyingvillagers:biped/combat/sword_heavy_auto2\" 0 1",
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
                                "indestructible @s play \"annoyingvillagers:biped/combat/sword_heavy_auto3\" 0 1",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
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

        if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.GREATSWORD || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.LEGENDARYSWORD || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.HARDGREATSWORD || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CorruptWeaponCategories.S_GREATSWORD) {
            LivingEntityPatch<?> livingentitypatch1 = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
            DynamicAnimation dynamicanimation2 = livingentitypatch1.getAnimator().getPlayerFor(null).getAnimation();

            if (dynamicanimation2 != Animations.STEEL_WHIRLWIND && dynamicanimation2 != Animations.STEEL_WHIRLWIND_CHARGING) {
                if (entity.getPersistentData().getDouble("sword_a") < 1.0D) {
                    entity.getPersistentData().putDouble("sword_a", 1.5D);
                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        try {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "indestructible @s play \"annoyingvillagers:biped/combat/giant_whirlwind\" 0 1",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
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
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "indestructible @s play \"annoyingvillagers:biped/combat/giant_whirlwind_2\" 0 1",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
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

        if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.SPEAR || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CorruptWeaponCategories.S_SPEAR) {
            if (entity.getPersistentData().getDouble("sword_a") < 1.0D) {
                entity.getPersistentData().putDouble("sword_a", 1.5D);
                if (!(entity instanceof LivingEntity livingEntity) || !entity.isAlive()) {
                    return;
                }
                ItemStack itemStack = livingEntity.getMainHandItem();
                if (itemStack.getItem() instanceof EnderGlaiveItem enderGlaiveItem) {
                    try {
                        if (!entity.level().isClientSide()) {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "indestructible @s play \"wom:biped/combat/agony_auto_1\" 0 1",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            new DelayedTask(3) {
                                @Override
                                public void run() {
                                    if (!entity.level().isClientSide()) {
                                        Vec3 tipPos = enderGlaiveItem.getJointWithTranslation(
                                                entity,
                                                new Vec3f(0.0F, 0.0F, 0.0F),
                                                Armatures.BIPED.toolR,
                                                4.3F,
                                                2.3F
                                        );
                                        entity.level().explode(entity, tipPos.x, tipPos.y, tipPos.z,
                                                2.0F, true, Level.ExplosionInteraction.TNT);
                                        Vec3 glaivePos = enderGlaiveItem.getJointWithTranslation(entity, new Vec3f(0,0,0),
                                                Armatures.BIPED.toolR, 1.3F, 2.3F);
                                        Vec3 explosionPos = enderGlaiveItem.getJointWithTranslation(entity, new Vec3f(0,0,0),
                                                Armatures.BIPED.toolR, 10.3F, 2.3F);
                                        AnnoyingVillagers.PACKET_HANDLER.send(
                                                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity),
                                                new ClientboundGlaiveExplosionFx(glaivePos, explosionPos)
                                        );
                                    }
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
