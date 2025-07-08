package com.pla.annoyingvillagers.procedures;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import com.pla.annoyingvillagers.animations.types.AttackBreakAnimation;
import com.pla.annoyingvillagers.capabilities.AVCategories;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import tictim.paraglider.capabilities.Caps;
import tictim.paraglider.capabilities.PlayerMovement;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;

public class WeaponsMoreAttackAnXiaAnJianShiProcedure {

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
                PlayerMovement playermovement = (PlayerMovement)entity.getCapability(Caps.playerMovement, (Direction)null).resolve().orElseThrow();

                if (playermovement.canAction()) {
                    LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

                    if (livingentitypatch != null) {
                        DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();

                        if (!(dynamicanimation instanceof AttackBreakAnimation) && !(dynamicanimation instanceof LongHitAnimation) && !entity.getPersistentData().getBoolean("kick_x")) {
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
                            })).start(levelaccessor, 4);
                            PlayerPatch<?> playerpatch = (PlayerPatch)EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);
                            PlayerMovement playermovement1;

                            if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.SWORD && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.TACHI && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.LONGSWORD && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.UCHIGATANA) {
                                if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.AXE) {
                                    if (playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE) {
                                        if (entity.isShiftKeyDown()) {
                                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/spinning_death\" 0 1");
                                            }

                                            playermovement1 = (PlayerMovement)entity.getCapability(Caps.playerMovement, (Direction)null).resolve().orElseThrow();
                                            playermovement1.takeStamina(40, false, false);
                                        } else {
                                            playermovement1 = (PlayerMovement)entity.getCapability(Caps.playerMovement, (Direction)null).resolve().orElseThrow();
                                            playermovement1.takeStamina(40, false, false);
                                            if (entity.getPersistentData().getDouble("axe_a") < 1.0D) {
                                                entity.getPersistentData().putDouble("axe_a", 1.5D);
                                                if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"efdg:biped/combat/greatsword_dual_airslash\" 0 1");
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
                                                        entity.getPersistentData().putDouble("axe_a", 2.0D);
                                                        MinecraftForge.EVENT_BUS.unregister(this);
                                                    }
                                                })).start(levelaccessor, 8);
                                            } else if (entity.getPersistentData().getDouble("axe_a") == 2.0D) {
                                                entity.getPersistentData().putDouble("axe_a", 2.5D);
                                                if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"efdg:biped/combat/greatsword_dual_auto_2\" 0 1");
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
                                                        entity.getPersistentData().putDouble("axe_a", 3.0D);
                                                        MinecraftForge.EVENT_BUS.unregister(this);
                                                    }
                                                })).start(levelaccessor, 8);
                                            } else if (entity.getPersistentData().getDouble("axe_a") == 3.0D) {
                                                entity.getPersistentData().putDouble("axe_a", 3.5D);
                                                if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"wom:biped/skill/torment_berserk_auto_1\" 0 1");
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
                                                        entity.getPersistentData().putDouble("axe_a", 4.0D);
                                                        MinecraftForge.EVENT_BUS.unregister(this);
                                                    }
                                                })).start(levelaccessor, 8);
                                            } else if (entity.getPersistentData().getDouble("axe_a") == 4.0D) {
                                                entity.getPersistentData().putDouble("axe_a", 4.5D);
                                                if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"wom:biped/skill/torment_berserk_auto2\" 0 1");
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
                                                        entity.getPersistentData().putDouble("axe_a", 5.0D);
                                                        MinecraftForge.EVENT_BUS.unregister(this);
                                                    }
                                                })).start(levelaccessor, 8);
                                            } else if (entity.getPersistentData().getDouble("axe_a") == 5.0D) {
                                                entity.getPersistentData().putDouble("axe_a", 5.5D);
                                                if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"efdg:biped/combat/greatsword_dual_auto_3\" 0 1");
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
                                                        entity.getPersistentData().putDouble("axe_a", 6.0D);
                                                        MinecraftForge.EVENT_BUS.unregister(this);
                                                    }
                                                })).start(levelaccessor, 8);
                                            } else if (entity.getPersistentData().getDouble("axe_a") == 6.0D) {
                                                entity.getPersistentData().putDouble("axe_a", 6.5D);
                                                if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"epicfight:illager/swing_axe3\" 0 1");
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
                                                        entity.getPersistentData().putDouble("axe_a", 0.0D);
                                                        MinecraftForge.EVENT_BUS.unregister(this);
                                                    }
                                                })).start(levelaccessor, 8);
                                            }
                                        }
                                    } else {
                                        playermovement1 = (PlayerMovement)entity.getCapability(Caps.playerMovement, (Direction)null).resolve().orElseThrow();
                                        playermovement1.takeStamina(50, false, false);
                                        if (entity.getPersistentData().getDouble("axe_a") > 2.0D) {
                                            entity.getPersistentData().putDouble("axe_a", 0.0D);
                                        }

                                        if (dynamicanimation == Animations.THE_GUILLOTINE) {
                                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/axe_fun_skill\" 0 1");
                                            }
                                        } else if (entity.getPersistentData().getDouble("axe_a") == 0.0D) {
                                            entity.getPersistentData().putDouble("axe_a", 1.5D);
                                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/axe_heavy_auto1\" 0 1");
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
                                                    entity.getPersistentData().putDouble("axe_a", 2.0D);
                                                    MinecraftForge.EVENT_BUS.unregister(this);
                                                }
                                            })).start(levelaccessor, 10);
                                        } else if (entity.getPersistentData().getDouble("axe_a") == 2.0D) {
                                            entity.getPersistentData().putDouble("axe_a", 2.5D);
                                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/axe_heavy_auto2\" 0 1");
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
                                                    entity.getPersistentData().putDouble("axe_a", 0.0D);
                                                    MinecraftForge.EVENT_BUS.unregister(this);
                                                }
                                            })).start(levelaccessor, 10);
                                        }
                                    }
                                } else if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.FIST && !entity.getPersistentData().getBoolean("fist_a")) {
                                    playermovement1 = (PlayerMovement)entity.getCapability(Caps.playerMovement, (Direction)null).resolve().orElseThrow();
                                    playermovement1.takeStamina(30, false, false);
                                    if (entity.isSprinting()) {
                                        if (entity.isShiftKeyDown()) {
                                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/whirlwind_kick_left\" 0 1");
                                            }

                                            entity.getPersistentData().putBoolean("fist_a", true);
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
                                            })).start(levelaccessor, 20);
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
                                                    entity.getPersistentData().putBoolean("fist_a", false);
                                                    MinecraftForge.EVENT_BUS.unregister(this);
                                                }
                                            })).start(levelaccessor, 35);
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
                                                    entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * 0.25D, 0.0D, entity.getLookAngle().z * 0.25D));
                                                    MinecraftForge.EVENT_BUS.unregister(this);
                                                }
                                            })).start(levelaccessor, 9);
                                        } else {
                                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/whirlwind_kick\" 0 1");
                                            }

                                            entity.getPersistentData().putBoolean("fist_a", true);
                                            entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * 0.2D, entity.getLookAngle().y * 0.5D, entity.getLookAngle().z * 0.2D));
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
                                            })).start(levelaccessor, 20);
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
                                                    entity.getPersistentData().putBoolean("fist_a", false);
                                                    MinecraftForge.EVENT_BUS.unregister(this);
                                                }
                                            })).start(levelaccessor, 40);
                                        }
                                    } else if (entity.isShiftKeyDown()) {
                                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                                            entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/fist_up\" 0 1");
                                        }
                                    } else if (playerpatch != null) {
                                        DynamicAnimation dynamicanimation1 = playerpatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();

                                        if (dynamicanimation1 == AVAnimations.FIST_UP) {
                                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/fist_left\" 0 1");
                                            }

                                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect give @s epicfight:stun_immunity 3 5 true");
                                            }

                                            entity.getPersistentData().putBoolean("fist_a", true);
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
                                            })).start(levelaccessor, 20);
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
                                                    entity.getPersistentData().putBoolean("fist_a", false);
                                                    MinecraftForge.EVENT_BUS.unregister(this);
                                                }
                                            })).start(levelaccessor, 20);
                                        } else {
                                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/fist_dash\" 0 1");
                                            }

                                            entity.getPersistentData().putBoolean("fist_a", true);
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
                                            })).start(levelaccessor, 25);
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
                                                    entity.getPersistentData().putBoolean("fist_a", false);
                                                    MinecraftForge.EVENT_BUS.unregister(this);
                                                }
                                            })).start(levelaccessor, 40);
                                        }
                                    }
                                }
                            } else if ((playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.SWORD || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD) && (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.TACHI || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI) && (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.TACHI || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD) && (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.SWORD || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI)) {
                                playermovement1 = (PlayerMovement)entity.getCapability(Caps.playerMovement, (Direction)null).resolve().orElseThrow();
                                playermovement1.takeStamina(40, false, false);
                                if (entity.getPersistentData().getDouble("sword_a") > 3.5D) {
                                    entity.getPersistentData().putDouble("sword_a", 0.0D);
                                } else if (entity.getPersistentData().getDouble("sword_a") == 0.0D) {
                                    entity.getPersistentData().putDouble("sword_a", 1.5D);
                                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/sword_heavy_auto1\" 0 1");
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
                                            entity.getPersistentData().putDouble("sword_a", 2.0D);
                                            MinecraftForge.EVENT_BUS.unregister(this);
                                        }
                                    })).start(levelaccessor, 10);
                                } else if (entity.getPersistentData().getDouble("sword_a") == 2.0D) {
                                    entity.getPersistentData().putDouble("sword_a", 2.5D);
                                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/sword_heavy_auto2\" 0 1");
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
                                            entity.getPersistentData().putDouble("sword_a", 3.0D);
                                            MinecraftForge.EVENT_BUS.unregister(this);
                                        }
                                    })).start(levelaccessor, 10);
                                } else if (entity.getPersistentData().getDouble("sword_a") == 3.0D) {
                                    entity.getPersistentData().putDouble("sword_a", 3.5D);
                                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/sword_heavy_auto3\" 0 1");
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
                                            entity.getPersistentData().putDouble("sword_a", 0.0D);
                                            MinecraftForge.EVENT_BUS.unregister(this);
                                        }
                                    })).start(levelaccessor, 10);
                                }
                            } else {
                                playermovement1 = (PlayerMovement)entity.getCapability(Caps.playerMovement, (Direction)null).resolve().orElseThrow();
                                playermovement1.takeStamina(40, false, false);
                                if (dynamicanimation == Animations.DANCING_EDGE) {
                                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/dual_sword_skill\" 0 1");
                                    }

                                    playermovement1.takeStamina(100, false, false);
                                } else {
                                    if (entity.getPersistentData().getDouble("sword_a") < 1.0D) {
                                        entity.getPersistentData().putDouble("sword_a", 1.0D);
                                    }

                                    if (entity.getPersistentData().getDouble("sword_a") == 1.0D) {
                                        entity.getPersistentData().putDouble("sword_a", 1.5D);
                                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                                            entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"epicfight:biped/combat/dagger_dual_dash\" 0 1");
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
                                                entity.getPersistentData().putDouble("sword_a", 2.0D);
                                                MinecraftForge.EVENT_BUS.unregister(this);
                                            }
                                        })).start(levelaccessor, 10);
                                    } else if (entity.getPersistentData().getDouble("sword_a") == 2.0D) {
                                        entity.getPersistentData().putDouble("sword_a", 2.5D);
                                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                                            entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"epicfight:biped/combat/longsword_auto2\" 0 1");
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
                                                entity.getPersistentData().putDouble("sword_a", 3.0D);
                                                MinecraftForge.EVENT_BUS.unregister(this);
                                            }
                                        })).start(levelaccessor, 10);
                                    } else if (entity.getPersistentData().getDouble("sword_a") == 3.0D) {
                                        entity.getPersistentData().putDouble("sword_a", 3.5D);
                                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                                            entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/dancing_edge\" 0 1");
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
                                                entity.getPersistentData().putDouble("sword_a", 4.0D);
                                                MinecraftForge.EVENT_BUS.unregister(this);
                                            }
                                        })).start(levelaccessor, 20);
                                    } else if (entity.getPersistentData().getDouble("sword_a") == 4.0D) {
                                        entity.getPersistentData().putDouble("sword_a", 4.5D);
                                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                                            entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/dual_sword_dancing_edge\" 0 1");
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
                                                entity.getPersistentData().putDouble("sword_a", 1.0D);
                                                MinecraftForge.EVENT_BUS.unregister(this);
                                            }
                                        })).start(levelaccessor, 20);
                                    }
                                }
                            }

                            if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.GREATSWORD || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.LEGENDARYSWORD || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.HARDGREATSWORD) {
                                playermovement1 = (PlayerMovement)entity.getCapability(Caps.playerMovement, (Direction)null).resolve().orElseThrow();
                                playermovement1.takeStamina(60, false, false);
                                LivingEntityPatch<?> livingentitypatch1 = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                DynamicAnimation dynamicanimation2 = livingentitypatch1.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();

                                if (dynamicanimation2 != Animations.STEEL_WHIRLWIND && dynamicanimation2 != Animations.STEEL_WHIRLWIND_CHARGING) {
                                    if (entity.getPersistentData().getDouble("sword_a") < 1.0D) {
                                        entity.getPersistentData().putDouble("sword_a", 1.5D);
                                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                                            entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/giant_whirlwind\" 0 1");
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
                                                entity.getPersistentData().putDouble("sword_a", 2.0D);
                                                MinecraftForge.EVENT_BUS.unregister(this);
                                            }
                                        })).start(levelaccessor, 30);
                                    } else if (entity.getPersistentData().getDouble("sword_a") == 2.0D) {
                                        entity.getPersistentData().putDouble("sword_a", 2.5D);
                                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                                            entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/giant_whirlwind_2\" 0 1");
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
                                                entity.getPersistentData().putDouble("sword_a", 0.0D);
                                                MinecraftForge.EVENT_BUS.unregister(this);
                                            }
                                        })).start(levelaccessor, 20);
                                    } else {
                                        entity.getPersistentData().putDouble("sword_a", 0.0D);
                                    }
                                } else if (!entity.level.isClientSide() && entity.getServer() != null) {
                                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/greatsword_skill\" 0 1");
                                }
                            }

                            if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.SPEAR) {
                                playermovement1 = (PlayerMovement)entity.getCapability(Caps.playerMovement, (Direction)null).resolve().orElseThrow();
                                playermovement1.takeStamina(45, false, false);
                                if (entity.getPersistentData().getDouble("sword_a") < 1.0D) {
                                    entity.getPersistentData().putDouble("sword_a", 1.5D);
                                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/spear_thrust\" 0 1");
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
                                            entity.getPersistentData().putDouble("sword_a", 0.0D);
                                            MinecraftForge.EVENT_BUS.unregister(this);
                                        }
                                    })).start(levelaccessor, 30);
                                } else {
                                    entity.getPersistentData().putDouble("sword_a", 0.0D);
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
