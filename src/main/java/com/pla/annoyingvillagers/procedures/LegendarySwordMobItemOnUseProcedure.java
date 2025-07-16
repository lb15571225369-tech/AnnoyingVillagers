package com.pla.annoyingvillagers.procedures;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class LegendarySwordMobItemOnUseProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity, final ItemStack itemstack) {
        if (entity != null) {
            if (entity.isShiftKeyDown()) {
                Vec3 vec3 = new Vec3(d0, d1, d2);
                List<Entity> list = (List)levelaccessor.getEntitiesOfClass(Entity.class, (new AABB(vec3, vec3)).inflate(2.5D), (entity1) -> {
                    return true;
                }).stream().sorted(Comparator.comparingDouble((entity1) -> {
                    return entity1.distanceToSqr(vec3);
                })).collect(Collectors.toList());
                Iterator iterator = list.iterator();

                while(iterator.hasNext()) {
                    Entity entity1 = (Entity)iterator.next();

                    entity1.hurt(DamageSource.MAGIC, 2.0F);
                    if (entity1 instanceof LivingEntity) {
                        LivingEntity livingentity = (LivingEntity)entity1;

                        if (!livingentity.level.isClientSide()) {
                            livingentity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 5, false, false));
                        }
                    }
                }

                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute at @s run particle minecraft:totem_of_undying ^ ^1.5 ^ 0 0 0 1 1000");
                }

                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute at @s run particle epicfight:air_burst ^ ^1.5 ^ 0 0 0 8 1");
                }

                if (entity instanceof Player) {
                    Player player = (Player)entity;

                    player.getCooldowns().addCooldown(itemstack.getItem(), 500);
                }

                itemstack.setHoverName(new TextComponent("\u00a7lWake up!"));
                entity.setDeltaMovement(new Vec3(0.0D, 1.2D, 0.0D));
                entity.setYRot(0.0F);
                entity.setXRot(45.0F);
                entity.setYBodyRot(entity.getYRot());
                entity.setYHeadRot(entity.getYRot());
                entity.yRotO = entity.getYRot();
                entity.xRotO = entity.getXRot();
                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity1 = (LivingEntity)entity;

                    livingentity1.yBodyRotO = livingentity1.getYRot();
                    livingentity1.yHeadRotO = livingentity1.getYRot();
                }

                Level level;

                if (levelaccessor instanceof Level) {
                    level = (Level)levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:wing")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:wing")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (levelaccessor instanceof Level) {
                    level = (Level)levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:l_g_w_u")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:l_g_w_u")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (levelaccessor instanceof Level) {
                    level = (Level)levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:zhanshenzhirenjuexing")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:zhanshenzhirenjuexing")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (levelaccessor instanceof Level) {
                    level = (Level)levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_dragon.flap")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_dragon.flap")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                LivingEntity livingentity2;

                if (entity instanceof LivingEntity) {
                    livingentity2 = (LivingEntity)entity;
                    if (!livingentity2.level.isClientSide()) {
                        livingentity2.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.ENDURANCE.get(), 50, 1, false, false));
                    }
                }

                if (entity instanceof LivingEntity) {
                    livingentity2 = (LivingEntity)entity;
                    if (!livingentity2.level.isClientSide()) {
                        livingentity2.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 50, 2, false, false));
                    }
                }

                new DelayedTask(50) {
                    @Override
                    public void run() {
                        itemstack.setHoverName(new TextComponent("Legendary Sword"));
                    }
                };
            }

        }
    }
}

