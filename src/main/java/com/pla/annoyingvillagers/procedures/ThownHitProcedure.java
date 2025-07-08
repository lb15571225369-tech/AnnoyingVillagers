package com.pla.annoyingvillagers.procedures;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.GuardAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;

@EventBusSubscriber
public class ThownHitProcedure {

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent livingattackevent) {
        if (livingattackevent != null && livingattackevent.getEntity() != null) {
            execute(livingattackevent, livingattackevent.getEntity(), livingattackevent.getSource().getDirectEntity(), livingattackevent.getSource().getEntity());
        }

    }

    public static void execute(Entity entity, Entity entity1, Entity entity2) {
        execute((Event) null, entity, entity1, entity2);
    }

    private static void execute(@Nullable Event event, Entity entity, Entity entity1, Entity entity2) {
        if (entity != null && entity1 != null && entity2 != null) {
            if (ForgeRegistries.ENTITIES.getKey(entity1.getType()).toString().equals("weaponthrow:weaponthrow") && entity2.isAlive()) {
                boolean flag;

                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity)entity;

                    flag = livingentity.hasEffect((MobEffect)AnnoyingVillagersModMobEffects.EC.get());
                } else {
                    flag = false;
                }

                DynamicAnimation dynamicanimation;

                if (!flag) {
                    if (entity instanceof Player) {
                        if (!((<undefinedtype>)(new Object() {
                            public boolean checkGamemode(Entity entity3) {
                                if (entity3 instanceof ServerPlayer) {
                                    ServerPlayer serverplayer = (ServerPlayer)entity3;

                                    return serverplayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
                                } else if (entity3.level.isClientSide() && entity3 instanceof Player) {
                                    Player player = (Player)entity3;

                                    return Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
                                } else {
                                    return false;
                                }
                            }
                        })).checkGamemode(entity)) {
                            PlayerPatch<?> playerpatch = (PlayerPatch)EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);

                            if (playerpatch != null) {
                                if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.FIST) {
                                    dynamicanimation = playerpatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();
                                    CapabilityItem capabilityitem = EpicFightCapabilities.getItemStackCapability(((Player)playerpatch.getOriginal()).getMainHandItem());
                                    List<StaticAnimation> list = capabilityitem.getAutoAttckMotion(playerpatch);

                                    if (!list.contains(dynamicanimation) && !(dynamicanimation instanceof GuardAnimation)) {
                                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                                            entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"epicfight:biped/combat/hit_long\" 0 10");
                                        }
                                    } else {
                                        if (event != null && event.isCancelable()) {
                                            event.setCanceled(true);
                                        }

                                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                                            entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "execute at @s run particle epicfight:hit_blunt ^ ^1.5 ^0.8 0.1 0.1 0.1 1 1");
                                        }

                                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                                            entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "execute at @s run particle annoying_villagersbychentu:spark ^ ^1.5 ^0.8 0 0 0 0.1 100");
                                        }

                                        if (entity instanceof Player) {
                                            Player player = (Player)entity;
                                            ItemCooldowns itemcooldowns = player.getCooldowns();
                                            ItemStack itemstack;

                                            if (entity instanceof LivingEntity) {
                                                LivingEntity livingentity1 = (LivingEntity)entity;

                                                itemstack = livingentity1.getMainHandItem();
                                            } else {
                                                itemstack = ItemStack.EMPTY;
                                            }

                                            itemcooldowns.addCooldown(itemstack.getItem(), 100);
                                        }

                                        playerpatch.playSound(EpicFightSounds.CLASH, -0.05F, 0.1F);
                                        entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * -0.2D, 0.2D, entity.getLookAngle().z * -0.2D));
                                        entity1.setDeltaMovement(new Vec3(entity.getLookAngle().x * 1.5D, 0.2D, entity.getLookAngle().z * 1.5D));
                                    }
                                } else if (!entity.level.isClientSide() && entity.getServer() != null) {
                                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"epicfight:biped/combat/hit_long\" 0 10");
                                }
                            }
                        }
                    } else {
                        HumanoidMobPatch<?> humanoidmobpatch = (HumanoidMobPatch)EpicFightCapabilities.getEntityPatch(entity, HumanoidMobPatch.class);

                        if (humanoidmobpatch != null) {
                            dynamicanimation = humanoidmobpatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();
                            if (!(dynamicanimation instanceof AttackAnimation) && !entity.level.isClientSide() && entity.getServer() != null) {
                                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"epicfight:biped/combat/hit_long\" 0 10");
                            }
                        }
                    }
                } else {
                    LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity2, LivingEntityPatch.class);

                    if (livingentitypatch != null) {
                        dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();
                        if (dynamicanimation instanceof LongHitAnimation && event != null && event.isCancelable()) {
                            event.setCanceled(true);
                        }
                    }
                }
            }

        }
    }
}
