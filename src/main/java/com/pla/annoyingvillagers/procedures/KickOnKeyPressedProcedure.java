package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.item.HerobrineEnderEyeItem;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import com.pla.annoyingvillagers.animations.types.AttackBreakAnimation;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;

import java.util.Optional;

public class KickOnKeyPressedProcedure {
    private static boolean isSpectatorGamemode(Entity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            return serverPlayer.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
        } else if (entity instanceof Player player && entity.level().isClientSide()) {
            var info = Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId());
            return info != null && info.getGameMode() == GameType.SPECTATOR;
        }
        return false;
    }

    public static void execute(LevelAccessor levelaccessor, final Entity entity) {
        if (entity != null) {
            if (!isSpectatorGamemode(entity)) {
                boolean flag;

                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity)entity;

                    flag = livingentity.hasEffect((MobEffect)AnnoyingVillagersModMobEffects.EC.get());
                } else {
                    flag = false;
                }

                if (!flag) {
                    if (!entity.getPersistentData().getBoolean("kick_x")) {
                        LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

                        if (livingentitypatch != null) {
                            AssetAccessor<? extends DynamicAnimation> dynamicanimation = livingentitypatch.getAnimator().getPlayerFor(null).getAnimation();

                            if (!(dynamicanimation instanceof AttackBreakAnimation)) {
                                if (!(dynamicanimation instanceof LongHitAnimation)) {
                                    entity.getPersistentData().putBoolean("kick_x", true);
                                    new DelayedTask(6) {
                                        @Override
                                        public void run() {
                                            entity.getPersistentData().putBoolean("kick_x", false);
                                        }
                                    };
                                    if (entity.isShiftKeyDown()) {
                                        if (entity.isSprinting()) {
                                            PlayerPatch<?> playerpatch = (PlayerPatch)EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);

                                            if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.FIST && !entity.level().isClientSide() && entity.getServer() != null) {
                                                try {
                                                    entity.getServer().getCommands().getDispatcher().execute("indestructible @s play \"annoyingvillagers:biped/combat/kick_combo\" 0 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                } catch (CommandSyntaxException e) {
                                                    
                                                }
                                            }
                                        } else if (!entity.level().isClientSide() && entity.getServer() != null) {
                                            try {
                                                entity.getServer().getCommands().getDispatcher().execute("indestructible @s play \"annoyingvillagers:biped/combat/kick_h\" 0 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                            } catch (CommandSyntaxException e) {
                                                
                                            }
                                        }
                                    } else {
                                        if (entity.isSprinting()) {
                                            if (entity.getPersistentData().getDouble("air_kick") != 1.0D) {
                                                entity.getPersistentData().putDouble("air_kick", 1.0D);
                                                if (!entity.level().isClientSide() && entity.getServer() != null) {
                                                    try {
                                                        entity.getServer().getCommands().getDispatcher().execute("indestructible @s play \"annoyingvillagers:biped/combat/kick_rush\" 0 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                    } catch (CommandSyntaxException e) {
                                                        
                                                    }
                                                }

                                                new DelayedTask(80) {
                                                    @Override
                                                    public void run() {
                                                        if (entity.getPersistentData().getDouble("air_kick") == 1.0D) {
                                                            entity.getPersistentData().putDouble("air_kick", 0.0D);
                                                        }
                                                    }
                                                };
                                            }
                                        } else if (entity.getPersistentData().getDouble("kick") < 1.0D) {
                                            entity.getPersistentData().putDouble("kick", 1.5D);
                                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                                try {
                                                    entity.getServer().getCommands().getDispatcher().execute("indestructible @s play \"annoyingvillagers:biped/combat/kick_1\" 0 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                } catch (CommandSyntaxException e) {
                                                    
                                                }
                                            }

                                            entity.getPersistentData().putBoolean("kick_x", true);
                                            new DelayedTask(15) {
                                                @Override
                                                public void run() {
                                                    entity.getPersistentData().putDouble("kick", 2.0D);
                                                }
                                            };
                                        } else if (entity.getPersistentData().getDouble("kick") == 2.0D) {
                                            entity.getPersistentData().putDouble("kick", 2.5D);
                                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                                try {
                                                    entity.getServer().getCommands().getDispatcher().execute("indestructible @s play \"annoyingvillagers:biped/combat/kick_2\" 0 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                } catch (CommandSyntaxException e) {
                                                    
                                                }
                                            }

                                            entity.getPersistentData().putBoolean("kick_x", true);
                                            new DelayedTask(11) {
                                                @Override
                                                public void run() {
                                                    entity.getPersistentData().putDouble("kick", 3.0D);
                                                }
                                            };
                                        } else if (entity.getPersistentData().getDouble("kick") == 3.0D) {
                                            entity.getPersistentData().putDouble("kick", 3.5D);
                                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                                try {
                                                    entity.getServer().getCommands().getDispatcher().execute("indestructible @s play \"annoyingvillagers:biped/combat/kick_3\" 0 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                } catch (CommandSyntaxException e) {
                                                    
                                                }
                                            }

                                            entity.getPersistentData().putBoolean("kick_x", true);
                                            new DelayedTask(14) {
                                                @Override
                                                public void run() {
                                                    PlayerPatch<?> playerpatch1 = (PlayerPatch)EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);
                                                    if (playerpatch1.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.FIST) {
                                                        entity.getPersistentData().putDouble("kick", 4.0D);
                                                    } else {
                                                        entity.getPersistentData().putDouble("kick", 0.0D);
                                                    }
                                                }
                                            };
                                        } else if (entity.getPersistentData().getDouble("kick") == 4.0D) {
                                            PlayerPatch<?> playerpatch1 = (PlayerPatch)EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);

                                            if (playerpatch1.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.FIST) {
                                                if (!entity.level().isClientSide() && entity.getServer() != null) {
                                                    try {
                                                        entity.getServer().getCommands().getDispatcher().execute("indestructible @s play \"annoyingvillagers:biped/combat/kick_c\" 0 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                    } catch (CommandSyntaxException e) {
                                                        
                                                    }
                                                }

                                                entity.getPersistentData().putBoolean("kick_x", true);
                                                entity.getPersistentData().putDouble("kick", 4.5D);
                                                new DelayedTask(11) {
                                                    @Override
                                                    public void run() {
                                                        entity.getPersistentData().putDouble("kick", 0.0D);
                                                    }
                                                };
                                            } else {
                                                entity.getPersistentData().putDouble("kick", 0.0D);
                                            }
                                        }
                                    }
                                } else {
                                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                                        try {
                                            entity.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/skill/roll_backward\" 0 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                        } catch (CommandSyntaxException e) {
                                            
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
