package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber
public class BlueDemonDeathSkillProcedure {

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (event != null && event.getEntity() != null) {
            execute(event, event.getEntity().level, event.getEntity(), event.getSource().getEntity());
        }
    }

    public static void execute(LevelAccessor world, Entity entity, Entity sourceEntity) {
        execute(null, world, entity, sourceEntity);
    }

    private static void execute(Event event, LevelAccessor world, Entity entity, Entity sourceEntity) {
        if (entity == null || sourceEntity == null || world == null) return;

        if (!ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString().equals(AnnoyingVillagers.MODID + ":blue_demon")) return;

        new DelayedTask(90) {
            @Override
            public void run() {
                if (isSpectator(sourceEntity) || isCreative(sourceEntity)) {
                    if (entity instanceof Player player && !player.level.isClientSide()) {
                        player.displayClientMessage(Component.literal("No target"), true);
                    }
                    return;
                }

                if (sourceEntity instanceof Player player) {
                    player.causeFoodExhaustion(6.0F);
                }

                if (world instanceof Level level && !level.isClientSide()) {
                    level.explode(null, sourceEntity.getX(), sourceEntity.getY(), sourceEntity.getZ(), 10.0F, Explosion.BlockInteraction.NONE);
                }

                applyEffect(sourceEntity, MobEffects.BLINDNESS, 60, 2);
                applyEffect(sourceEntity, MobEffects.CONFUSION, 70, 2);
                applyEffect(sourceEntity, MobEffects.MOVEMENT_SLOWDOWN, 70, 2);

                if (world instanceof Level level2 && !level2.isClientSide()) {
                    level2.explode(null, sourceEntity.getX(), sourceEntity.getY(), sourceEntity.getZ(), 8.0F, Explosion.BlockInteraction.DESTROY);
                }

                for (int i = 0; i < 2; i++) {
                    if (world instanceof ServerLevel serverLevel) {
                        LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(serverLevel);
                        if (bolt != null) {
                            bolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(sourceEntity.getX(), sourceEntity.getY(), sourceEntity.getZ())));
                            bolt.setVisualOnly(false);
                            serverLevel.addFreshEntity(bolt);
                        }
                    }
                }

                if (world instanceof ServerLevel serverLevel) {
                    ThrownTrident trident = new ThrownTrident(EntityType.TRIDENT, serverLevel);
                    trident.moveTo(sourceEntity.getX(), sourceEntity.getY() + 5.0, sourceEntity.getZ(), world.getRandom().nextFloat() * 360.0F, 0.0F);
                    serverLevel.addFreshEntity(trident);
                }
            }
        };
    }

    private static boolean isSpectator(Entity entity) {
        if (entity instanceof ServerPlayer sp)
            return sp.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
        if (entity instanceof Player player && entity.level.isClientSide()) {
            var info = Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId());
            return info != null && info.getGameMode() == GameType.SPECTATOR;
        }
        return false;
    }

    private static boolean isCreative(Entity entity) {
        if (entity instanceof ServerPlayer sp)
            return sp.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
        if (entity instanceof Player player && entity.level.isClientSide()) {
            var info = Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId());
            return info != null && info.getGameMode() == GameType.CREATIVE;
        }
        return false;
    }

    private static void applyEffect(Entity entity, MobEffect effect, int duration, int amplifier) {
        if (entity instanceof LivingEntity living && !living.level.isClientSide()) {
            living.addEffect(new MobEffectInstance(effect, duration, amplifier, false, false));
        }
    }
}
