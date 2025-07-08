package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class FireworkuseProcedure {

    @SubscribeEvent
    public static void onRightClickBlock(RightClickBlock rightclickblock) {
        if (rightclickblock.getHand() == rightclickblock.getPlayer().getUsedItemHand()) {
            execute(rightclickblock, rightclickblock.getWorld(), (double) rightclickblock.getPos().getX(), (double) rightclickblock.getPos().getY(), (double) rightclickblock.getPos().getZ(), rightclickblock.getPlayer());
        }
    }

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        execute((Event) null, levelaccessor, d0, d1, d2, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            if (!entity.isShiftKeyDown()) {
                int i;

                if (entity instanceof Player) {
                    Player player = (Player)entity;

                    i = player.experienceLevel;
                } else {
                    i = 0;
                }

                if (i >= 5) {
                    ItemStack itemstack;

                    if (entity instanceof LivingEntity) {
                        LivingEntity livingentity = (LivingEntity)entity;

                        itemstack = livingentity.getMainHandItem();
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    if (itemstack.getItem() == Items.FIREWORK_ROCKET) {
                        if (entity instanceof LivingEntity) {
                            LivingEntity livingentity1 = (LivingEntity)entity;

                            if (livingentity1.hasEffect(MobEffects.LUCK)) {
                                if (!entity.level.isClientSide() && entity.getServer() != null) {
                                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "1");
                                }

                                return;
                            }
                        }

                        LivingEntity livingentity2;

                        if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity)entity;
                            if (!livingentity2.level.isClientSide()) {
                                livingentity2.addEffect(new MobEffectInstance(MobEffects.LUCK, 250, 1, false, false));
                            }
                        }

                        if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity)entity;
                            livingentity2.swing(InteractionHand.MAIN_HAND, true);
                        }

                        Player player1;

                        if (entity instanceof Player) {
                            player1 = (Player)entity;
                            ItemCooldowns itemcooldowns = player1.getCooldowns();
                            ItemStack itemstack1;

                            if (entity instanceof LivingEntity) {
                                LivingEntity livingentity3 = (LivingEntity)entity;

                                itemstack1 = livingentity3.getMainHandItem();
                            } else {
                                itemstack1 = ItemStack.EMPTY;
                            }

                            itemcooldowns.addCooldown(itemstack1.getItem(), 250);
                        }

                        if (entity instanceof Player) {
                            player1 = (Player)entity;
                            player1.giveExperienceLevels(-5);
                        }

                        if (entity instanceof Player) {
                            player1 = (Player)entity;
                            ItemStack itemstack2 = new ItemStack(Items.FIREWORK_ROCKET);

                            player1.getInventory().clearOrCountMatchingItems((itemstack3) -> {
                                return itemstack2.getItem() == itemstack3.getItem();
                            }, 1, player1.inventoryMenu.getCraftSlots());
                        }

                        if (levelaccessor instanceof ServerLevel) {
                            ServerLevel serverlevel = (ServerLevel)levelaccessor;

                            serverlevel.sendParticles(ParticleTypes.FIREWORK, d0, d1, d2, 40, 0.0D, 3.0D, 0.0D, 1.0D);
                        }

                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                            entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/summon firework_rocket ~ ~10 ~ {LifeTime:10,FireworksItem:{id:firework_rocket,Count:1,tag:{Fireworks:{Explosions:[{Type:3,Colors:[0],Flicker:1}]}},display:{Name:\"Black Creeper Firework\"}}}");
                        }

                        if (levelaccessor instanceof Level) {
                            Level level = (Level)levelaccessor;

                            if (!level.isClientSide()) {
                                level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.firework_rocket.launch")), SoundSource.NEUTRAL, 1.0F, 2.0F);
                            } else {
                                level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.firework_rocket.launch")), SoundSource.NEUTRAL, 1.0F, 2.0F, false);
                            }
                        }

                        ((<undefinedtype>)(new Object() {
                            private int ticks = 0;
                            private float waitTicks;
                            private LevelAccessor world;

                            public void start(LevelAccessor levelaccessor1, int j) {
                                this.waitTicks = (float)j;
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
                                LevelAccessor levelaccessor1 = this.world;

                                if (levelaccessor1 instanceof Level) {
                                    Level level1 = (Level)levelaccessor1;

                                    if (!level1.isClientSide()) {
                                        level1.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.experience_orb.pickup")), SoundSource.NEUTRAL, 1.0F, 2.0F);
                                    } else {
                                        level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.experience_orb.pickup")), SoundSource.NEUTRAL, 1.0F, 2.0F, false);
                                    }
                                }

                                Entity entity1;

                                if (Math.random() <= 0.6D) {
                                    if (!this.world.isClientSide() && this.world.getServer() != null) {
                                        this.world.getServer().getPlayerList().broadcastMessage(new TextComponent("<\u6751\u6c11\u4fa6\u5bdf\u5175> What the matter?"), ChatType.SYSTEM, Util.NIL_UUID);
                                    }

                                    entity1 = entity;
                                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                        entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/summon annoying_villagersbychentu:cun_min_zhen_cha_bing ~ ~5 ~10");
                                    }

                                    entity1 = entity;
                                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                        entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/summon annoying_villagersbychentu:cun_min_zhen_cha_bing ~10 ~5 ~-5");
                                    }

                                    entity1 = entity;
                                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                        entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/summon annoying_villagersbychentu:lan_cun_qi ~-10 ~5 ~20");
                                    }
                                } else if (Math.random() <= 0.1D) {
                                    if (!this.world.isClientSide() && this.world.getServer() != null) {
                                        this.world.getServer().getPlayerList().broadcastMessage(new TextComponent("<\u6751\u6c11\u84dd\u9a91\u5175> What the matter?"), ChatType.SYSTEM, Util.NIL_UUID);
                                    }

                                    entity1 = entity;
                                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                        entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/summon annoying_villagersbychentu:lan_cun_qi ~10 ~5 ~-20");
                                    }

                                    entity1 = entity;
                                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                        entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/summon annoying_villagersbychentu:lan_cun_qi ~-5 ~5 ~20");
                                    }

                                    entity1 = entity;
                                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                        entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/summon annoying_villagersbychentu:cun_min_zhen_cha_bing ~ ~5 ~10");
                                    }
                                } else if (Math.random() <= 0.1D) {
                                    if (!this.world.isClientSide() && this.world.getServer() != null) {
                                        this.world.getServer().getPlayerList().broadcastMessage(new TextComponent("<\u6751\u6c11\u7d2b\u9a91\u5175> What the matter?"), ChatType.SYSTEM, Util.NIL_UUID);
                                    }

                                    entity1 = entity;
                                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                        entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/summon annoying_villagersbychentu:zi_cun_qi ~-5 ~5 ~20");
                                    }

                                    entity1 = entity;
                                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                        entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/summon annoying_villagersbychentu:zi_cun_qi ~10 ~5 ~-20");
                                    }
                                } else if (Math.random() <= 0.1D) {
                                    if (!this.world.isClientSide() && this.world.getServer() != null) {
                                        this.world.getServer().getPlayerList().broadcastMessage(new TextComponent("<\u6751\u6c11\u7ea2\u9a91\u5175> What the matter?"), ChatType.SYSTEM, Util.NIL_UUID);
                                    }

                                    entity1 = entity;
                                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                        entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/summon annoying_villagersbychentu:hong_cun_qi ~10 ~5 ~20");
                                    }

                                    entity1 = entity;
                                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                        entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/summon annoying_villagersbychentu:hong_cun_qi ~5 ~5 ~-20");
                                    }

                                    entity1 = entity;
                                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                        entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/summon annoying_villagersbychentu:cun_min_zhen_cha_bing ~ ~5 ~-10");
                                    }
                                } else {
                                    if (!this.world.isClientSide() && this.world.getServer() != null) {
                                        this.world.getServer().getPlayerList().broadcastMessage(new TextComponent("<\u6751\u6c11\u7eff\u9a91\u5175> What the matter?"), ChatType.SYSTEM, Util.NIL_UUID);
                                    }

                                    entity1 = entity;
                                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                        entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/summon annoying_villagersbychentu:cun_min_zhen_cha_bing ~ ~5 ~-10");
                                    }

                                    entity1 = entity;
                                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                        entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/summon annoying_villagersbychentu:lan_cun_qi ~10 ~5 ~20");
                                    }

                                    entity1 = entity;
                                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                        entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/summon annoying_villagersbychentu:lu_cun_qi ~-5 ~5 ~20");
                                    }
                                }

                                MinecraftForge.EVENT_BUS.unregister(this);
                            }
                        })).start(levelaccessor, 50);
                    }
                }
            }

        }
    }
}
