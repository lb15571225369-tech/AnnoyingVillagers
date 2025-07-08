package com.pla.annoyingvillagers.procedures;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import com.pla.annoyingvillagers.animations.types.AttackBreakAnimation;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import tictim.paraglider.capabilities.Caps;
import tictim.paraglider.capabilities.PlayerMovement;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;

public class KickAnXiaAnJianShiProcedure {

    public static void execute(LevelAccessor levelaccessor, final Entity entity) {
        if (entity != null) {
            if (!((<undefinedtype>)(new Object() {
                public boolean checkGamemode(Entity entity1) {
                    if (entity1 instanceof ServerPlayer) {
                        ServerPlayer serverplayer = (ServerPlayer)entity1;

                        return serverplayer.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
                    } else if (entity1.level.isClientSide() && entity1 instanceof Player) {
                        Player player = (Player)entity1;

                        return Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId()).getGameMode() == GameType.SPECTATOR;
                    } else {
                        return false;
                    }
                }
            })).checkGamemode(entity)) {
                boolean flag;

                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity)entity;

                    flag = livingentity.hasEffect((MobEffect)AnnoyingVillagersModMobEffects.EC.get());
                } else {
                    flag = false;
                }

                if (!flag) {
                    PlayerMovement playermovement = (PlayerMovement)entity.getCapability(Caps.playerMovement, (Direction)null).resolve().orElseThrow();

                    if (playermovement.canAction() && !entity.getPersistentData().getBoolean("kick_x")) {
                        LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

                        if (livingentitypatch != null) {
                            DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();

                            if (!(dynamicanimation instanceof AttackBreakAnimation)) {
                                PlayerMovement playermovement1;

                                if (!(dynamicanimation instanceof LongHitAnimation)) {
                                    entity.getPersistentData().putBoolean("kick_x", true);
                                    ((<undefinedtype>)(new Object() {
                                        private int ticks = 0;
                                        private float waitTicks;
                                        private LevelAccessor world;

                                        public void start(LevelAccessor levelaccessor1, int i) {
                                            this.waitTicks = (float)i;
                                            MinecraftForge.EVENT_BUS.register(this);
                                            this.world = levelaccessor1;
                                        }

                                        @SubscribeEvent
                                        public void tick(ServerTickEvent servertickevent) {
                                            if (servertickevent.phase == Phase.END) {
                                                ++this.ticks;
                                                if ((float)this.ticks >= this.waitTicks) {
                                                    this.run();
                                                }
                                            }

                                        }

                                        private void run() {
                                            entity.getPersistentData().putBoolean("kick_x", false);
                                            MinecraftForge.EVENT_BUS.unregister(this);
                                        }
                                    })).start(levelaccessor, 6);
                                    if (entity.isShiftKeyDown()) {
                                        if (entity.isSprinting()) {
                                            PlayerPatch<?> playerpatch = (PlayerPatch)EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);

                                            if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.FIST && !entity.level.isClientSide() && entity.getServer() != null) {
                                                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/kick_combo\" 0 1");
                                            }
                                        } else if (!entity.level.isClientSide() && entity.getServer() != null) {
                                            entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/kick_h\" 0 1");
                                        }
                                    } else {
                                        playermovement1 = (PlayerMovement)entity.getCapability(Caps.playerMovement, (Direction)null).resolve().orElseThrow();
                                        playermovement1.takeStamina(30, false, false);
                                        if (entity.isSprinting()) {
                                            if (entity.getPersistentData().getDouble("air_kick") != 1.0D) {
                                                entity.getPersistentData().putDouble("air_kick", 1.0D);
                                                if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/kick_rush\" 0 1");
                                                }

                                                ((<undefinedtype>)(new Object() {
                                                    private int ticks = 0;
                                                    private float waitTicks;
                                                    private LevelAccessor world;

                                                    public void start(LevelAccessor levelaccessor1, int i) {
                                                        this.waitTicks = (float)i;
                                                        MinecraftForge.EVENT_BUS.register(this);
                                                        this.world = levelaccessor1;
                                                    }

                                                    @SubscribeEvent
                                                    public void tick(ServerTickEvent servertickevent) {
                                                        if (servertickevent.phase == Phase.END) {
                                                            ++this.ticks;
                                                            if ((float)this.ticks >= this.waitTicks) {
                                                                this.run();
                                                            }
                                                        }

                                                    }

                                                    private void run() {
                                                        if (entity.getPersistentData().getDouble("air_kick") == 1.0D) {
                                                            entity.getPersistentData().putDouble("air_kick", 0.0D);
                                                        }

                                                        MinecraftForge.EVENT_BUS.unregister(this);
                                                    }
                                                })).start(levelaccessor, 80);
                                            }
                                        } else if (entity.getPersistentData().getDouble("kick") < 1.0D) {
                                            entity.getPersistentData().putDouble("kick", 1.5D);
                                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/kick_1\" 0 1");
                                            }

                                            entity.getPersistentData().putBoolean("kick_x", true);
                                            ((<undefinedtype>)(new Object() {
                                                private int ticks = 0;
                                                private float waitTicks;
                                                private LevelAccessor world;

                                                public void start(LevelAccessor levelaccessor1, int i) {
                                                    this.waitTicks = (float)i;
                                                    MinecraftForge.EVENT_BUS.register(this);
                                                    this.world = levelaccessor1;
                                                }

                                                @SubscribeEvent
                                                public void tick(ServerTickEvent servertickevent) {
                                                    if (servertickevent.phase == Phase.END) {
                                                        ++this.ticks;
                                                        if ((float)this.ticks >= this.waitTicks) {
                                                            this.run();
                                                        }
                                                    }

                                                }

                                                private void run() {
                                                    entity.getPersistentData().putDouble("kick", 2.0D);
                                                    MinecraftForge.EVENT_BUS.unregister(this);
                                                }
                                            })).start(levelaccessor, 15);
                                        } else if (entity.getPersistentData().getDouble("kick") == 2.0D) {
                                            entity.getPersistentData().putDouble("kick", 2.5D);
                                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/kick_2\" 0 1");
                                            }

                                            entity.getPersistentData().putBoolean("kick_x", true);
                                            ((<undefinedtype>)(new Object() {
                                                private int ticks = 0;
                                                private float waitTicks;
                                                private LevelAccessor world;

                                                public void start(LevelAccessor levelaccessor1, int i) {
                                                    this.waitTicks = (float)i;
                                                    MinecraftForge.EVENT_BUS.register(this);
                                                    this.world = levelaccessor1;
                                                }

                                                @SubscribeEvent
                                                public void tick(ServerTickEvent servertickevent) {
                                                    if (servertickevent.phase == Phase.END) {
                                                        ++this.ticks;
                                                        if ((float)this.ticks >= this.waitTicks) {
                                                            this.run();
                                                        }
                                                    }

                                                }

                                                private void run() {
                                                    entity.getPersistentData().putDouble("kick", 3.0D);
                                                    MinecraftForge.EVENT_BUS.unregister(this);
                                                }
                                            })).start(levelaccessor, 11);
                                        } else if (entity.getPersistentData().getDouble("kick") == 3.0D) {
                                            entity.getPersistentData().putDouble("kick", 3.5D);
                                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/kick_3\" 0 1");
                                            }

                                            entity.getPersistentData().putBoolean("kick_x", true);
                                            ((<undefinedtype>)(new Object() {
                                                private int ticks = 0;
                                                private float waitTicks;
                                                private LevelAccessor world;

                                                public void start(LevelAccessor levelaccessor1, int i) {
                                                    this.waitTicks = (float)i;
                                                    MinecraftForge.EVENT_BUS.register(this);
                                                    this.world = levelaccessor1;
                                                }

                                                @SubscribeEvent
                                                public void tick(ServerTickEvent servertickevent) {
                                                    if (servertickevent.phase == Phase.END) {
                                                        ++this.ticks;
                                                        if ((float)this.ticks >= this.waitTicks) {
                                                            this.run();
                                                        }
                                                    }

                                                }

                                                private void run() {
                                                    PlayerPatch<?> playerpatch1 = (PlayerPatch)EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);

                                                    if (playerpatch1.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.FIST) {
                                                        entity.getPersistentData().putDouble("kick", 4.0D);
                                                    } else {
                                                        entity.getPersistentData().putDouble("kick", 0.0D);
                                                    }

                                                    MinecraftForge.EVENT_BUS.unregister(this);
                                                }
                                            })).start(levelaccessor, 14);
                                        } else if (entity.getPersistentData().getDouble("kick") == 4.0D) {
                                            PlayerPatch<?> playerpatch1 = (PlayerPatch)EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);

                                            if (playerpatch1.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.FIST) {
                                                if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/kick_c\" 0 1");
                                                }

                                                entity.getPersistentData().putBoolean("kick_x", true);
                                                entity.getPersistentData().putDouble("kick", 4.5D);
                                                ((<undefinedtype>)(new Object() {
                                                    private int ticks = 0;
                                                    private float waitTicks;
                                                    private LevelAccessor world;

                                                    public void start(LevelAccessor levelaccessor1, int i) {
                                                        this.waitTicks = (float)i;
                                                        MinecraftForge.EVENT_BUS.register(this);
                                                        this.world = levelaccessor1;
                                                    }

                                                    @SubscribeEvent
                                                    public void tick(ServerTickEvent servertickevent) {
                                                        if (servertickevent.phase == Phase.END) {
                                                            ++this.ticks;
                                                            if ((float)this.ticks >= this.waitTicks) {
                                                                this.run();
                                                            }
                                                        }

                                                    }

                                                    private void run() {
                                                        entity.getPersistentData().putDouble("kick", 0.0D);
                                                        MinecraftForge.EVENT_BUS.unregister(this);
                                                    }
                                                })).start(levelaccessor, 11);
                                            } else {
                                                entity.getPersistentData().putDouble("kick", 0.0D);
                                            }
                                        }
                                    }
                                } else {
                                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"epicfight:biped/skill/roll_backward\" 0 1");
                                    }

                                    playermovement1 = (PlayerMovement)entity.getCapability(Caps.playerMovement, (Direction)null).resolve().orElseThrow();
                                    playermovement1.takeStamina(80, false, false);
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
