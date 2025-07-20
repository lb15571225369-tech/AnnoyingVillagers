package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.KnockdownAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@EventBusSubscriber
public class NpcStandUpProcedure {

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent livingattackevent) {
        if (livingattackevent != null && livingattackevent.getEntity() != null) {
            execute(livingattackevent, livingattackevent.getEntity().level, livingattackevent.getEntity());
        }

    }

    public static void execute(LevelAccessor levelaccessor, Entity entity) {
        execute((Event) null, levelaccessor, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, final Entity entity) {
        if (entity != null) {
            if (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString().equals("player_mobs:player_mob") && entity.getPersistentData().getDouble("npc_level") != 0.0D) {
                LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

                if (livingentitypatch != null) {
                    final DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();
                    new DelayedTask(10) {
                        @Override
                        public void run() throws CommandSyntaxException {
                            if (dynamicanimation instanceof KnockdownAnimation) {
                                Entity entity1;

                                if (Math.random() <= 0.4D) {
                                    entity1 = entity;
                                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                        entity1.getServer().getCommands().getDispatcher().execute(
                                                "indestructible @s play \"epicfight:biped/skill/knockdown_wakeup_left\" 0 1",
                                                entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                    }
                                } else {
                                    entity1 = entity;
                                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                        entity1.getServer().getCommands().getDispatcher().execute(
                                                "indestructible @s play \"epicfight:biped/skill/knockdown_wakeup_right\" 0 1",
                                                entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                    }
                                }
                            }
                        }
                    };
                }
            }

        }
    }
}
