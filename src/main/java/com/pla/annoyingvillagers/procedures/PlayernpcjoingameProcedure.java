package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class PlayernpcjoingameProcedure {

    @SubscribeEvent
    public static void onEntitySpawned(EntityJoinWorldEvent entityjoinworldevent) {
        execute(entityjoinworldevent, entityjoinworldevent.getWorld(), entityjoinworldevent.getEntity().getX(), entityjoinworldevent.getEntity().getY(), entityjoinworldevent.getEntity().getZ(), entityjoinworldevent.getEntity());
    }

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        execute((Event) null, levelaccessor, d0, d1, d2, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity) {
        if (entity != null) {
            if (ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("annoying_villagersbychentu:bei_gan_ran_jian_bing_guo_zi") && !entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "kill @s");
            }

            LivingEntity livingentity;

            if (ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("player_mobs:player_mob")) {
                ((LivingEntity)entity).getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
                ((LivingEntity)entity).getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1.0D);
                ((LivingEntity)entity).getAttribute(Attributes.ARMOR).setBaseValue(0.0D);
                entity.setCustomNameVisible(true);
                if (!levelaccessor.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(d0, d1, d2), 64.0D, 64.0D, 64.0D), (player) -> {
                    return true;
                }).isEmpty() && !levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("\u00a7e" + entity.getDisplayName().getString() + "\u00a7e\u52a0\u5165\u4e86\u6e38\u620f"), ChatType.SYSTEM, Util.NIL_UUID);
                }

                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "team modify villagers friendlyFire false");
                }

                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "data merge entity @s {CanPickUpLoot: 1b}");
                }

                ((LivingEntity)entity).getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    if (!livingentity.level.isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 9999999, 0, false, false));
                    }
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
                        int i;

                        if (entity instanceof LivingEntity) {
                            LivingEntity livingentity1 = (LivingEntity)entity;

                            i = livingentity1.getArmorValue();
                        } else {
                            i = 0;
                        }

                        if (i >= 12) {
                            Entity entity1 = entity;

                            if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "tag @s add 1");
                            }

                            entity1 = entity;
                            if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "tag @s add 2");
                            }

                            entity1 = entity;
                            if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "tag @s add 3");
                            }

                            entity1 = entity;
                            if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "tag @s add 4");
                            }
                        }

                        MinecraftForge.EVENT_BUS.unregister(this);
                    }
                })).start(levelaccessor, 5);
                if (Math.random() <= 0.3D) {
                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "team add player");
                    }

                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "team join player @s");
                    }
                }

                if (Math.random() <= 0.3D) {
                    entity.getPersistentData().putDouble("npc_level", 3.0D);
                }
            }

            float f;

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                f = livingentity.getMaxHealth();
            } else {
                f = -1.0F;
            }

            if (f == 40.0F && entity instanceof LivingEntity) {
                LivingEntity livingentity1 = (LivingEntity)entity;

                if (!livingentity1.level.isClientSide()) {
                    livingentity1.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 9999999, 0, false, false));
                }
            }

            if (ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("minecraft:villager") && !entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "team join villagers @s");
            }

            if (ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("epicfight:dodge_left") && !entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "playsound annoying_villagersbychentu:whoosh neutral @p");
            }

        }
    }
}
