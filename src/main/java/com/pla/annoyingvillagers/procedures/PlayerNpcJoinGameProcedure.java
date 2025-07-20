package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.util.EquipmentDataLoader;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

import java.util.List;

@EventBusSubscriber
public class PlayerNpcJoinGameProcedure {

    @SubscribeEvent
    public static void onEntitySpawned(EntityJoinLevelEvent entityjoinworldevent) throws CommandSyntaxException {
        execute(entityjoinworldevent, entityjoinworldevent.getLevel(), entityjoinworldevent.getEntity().getX(), entityjoinworldevent.getEntity().getY(), entityjoinworldevent.getEntity().getZ(), entityjoinworldevent.getEntity());
    }

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) throws CommandSyntaxException {
        execute((Event) null, levelaccessor, d0, d1, d2, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity) throws CommandSyntaxException {
        if (entity != null) {
            if (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString().equals("annoying_villagersbychentu:bei_gan_ran_jian_bing_guo_zi") && !entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().getDispatcher().execute(
                        "kill @s",
                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            }

            LivingEntity livingentity;

            if (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString().equals("player_mobs:player_mob")) {
                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    List<String> commands = EquipmentDataLoader.getEquipCommands(0.7f, entity);
                    for (String cmd : commands) {
                        entity.getServer().getCommands().getDispatcher().execute(
                                cmd,
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                        );
                    }
                }
                ((LivingEntity) entity).getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
                ((LivingEntity) entity).getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1.0D);
                ((LivingEntity) entity).getAttribute(Attributes.ARMOR).setBaseValue(0.0D);
                entity.setCustomNameVisible(true);
                if (!levelaccessor.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(d0, d1, d2), 64.0D, 64.0D, 64.0D), (player) -> {
                    return true;
                }).isEmpty() && !levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("\u00a7e" + entity.getDisplayName().getString() + "\u00a7e has joined the game"), false);
                }

                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team modify villagers friendlyFire false",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    entity.getServer().getCommands().getDispatcher().execute(
                            "data merge entity @s {CanPickUpLoot: 1b}",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                }

                ((LivingEntity) entity).getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    if (!livingentity.level.isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 9999999, 0, false, false));
                    }
                }
                new DelayedTask(5) {
                    @Override
                    public void run() throws CommandSyntaxException {
                        int i;

                        if (entity instanceof LivingEntity) {
                            LivingEntity livingentity1 = (LivingEntity) entity;

                            i = livingentity1.getArmorValue();
                        } else {
                            i = 0;
                        }

                        if (i >= 12) {
                            Entity entity1 = entity;

                            if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                entity1.getServer().getCommands().getDispatcher().execute(
                                        "tag @s add 1",
                                        entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            }

                            entity1 = entity;
                            if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                entity1.getServer().getCommands().getDispatcher().execute(
                                        "tag @s add 2",
                                        entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            }

                            entity1 = entity;
                            if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                entity1.getServer().getCommands().getDispatcher().execute(
                                        "tag @s add 3",
                                        entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            }

                            entity1 = entity;
                            if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                entity1.getServer().getCommands().getDispatcher().execute(
                                        "tag @s add 4",
                                        entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            }
                        }
                    }
                };
                if (Math.random() <= 0.3D) {
                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "team add player",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    }

                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "team join player @s",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
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

            if (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString().equals("minecraft:villager") && !entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().getDispatcher().execute(
                        "team join villagers @s",
                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            }

            if (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString().equals("epicfight:dodge_left") && !entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().getDispatcher().execute(
                        "playsound annoyingvillagers:whoosh neutral @p",
                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            }

            if ((entity instanceof Monster monster) && !entity.level.isClientSide() && entity.getServer() != null) {
                monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(
                        monster,
                        PlayerMobEntity.class,
                        true
                ));
            }

            if (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString().equals("guardvillagers:guard") && !entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().getDispatcher().execute(
                        "team join villagers @s",
                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                entity.getServer().getCommands().getDispatcher().execute(
                        "data merge entity @s {CanPickUpLoot: 1b}",
                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            }

            if (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString().equals("minecraft:zombie") && !entity.level.isClientSide() && entity.getServer() != null) {
                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "data merge entity @s {CanPickUpLoot: 1b}",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    if (entity.level.getRandom().nextFloat() < 0.5f) {
                        List<String> commands = EquipmentDataLoader.getEquipCommands(0.0f, entity);
                        for (String cmd : commands) {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    cmd,
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                            );
                        }
                    }
                    if (entity.level.getRandom().nextFloat() < 0.5f) {
                        EquipmentDataLoader.getRandomSpecificSlot("CHEST").ifPresent(cmd -> {
                            try {
                                entity.getServer().getCommands().getDispatcher().execute(
                                        cmd,
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                                );
                            } catch (CommandSyntaxException e) {
                                
                            }
                        });
                    }
                }
            }

            if (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString().equals("minecraft:skeleton") && !entity.level.isClientSide() && entity.getServer() != null) {
                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "data merge entity @s {CanPickUpLoot: 1b}",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    if (entity.level.getRandom().nextFloat() < 0.6f) {
                        EquipmentDataLoader.getRandomSpecificSlot("CHEST").ifPresent(cmd -> {
                            try {
                                entity.getServer().getCommands().getDispatcher().execute(
                                        cmd,
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                                );
                            } catch (CommandSyntaxException e) {
                                
                            }
                        });
                    }
                    if (entity.level.getRandom().nextFloat() < 0.6f) {
                        EquipmentDataLoader.getRandomSpecificSlot("HEAD").ifPresent(cmd -> {
                            try {
                                entity.getServer().getCommands().getDispatcher().execute(
                                        cmd,
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                                );
                            } catch (CommandSyntaxException e) {
                                
                            }
                        });
                    }
                }
            }

        }
    }
}
