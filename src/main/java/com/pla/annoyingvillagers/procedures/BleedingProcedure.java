package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.HitAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@EventBusSubscriber
public class BleedingProcedure {

    @SubscribeEvent
    public static void onEntityAttacked(LivingHurtEvent livinghurtevent) {
        if (livinghurtevent != null && livinghurtevent.getEntity() != null) {
            execute(livinghurtevent, livinghurtevent.getEntity().level(), (int) livinghurtevent.getEntity().getX(), (int) livinghurtevent.getEntity().getY(), (int) livinghurtevent.getEntity().getZ(), livinghurtevent.getEntity(), livinghurtevent.getSource().getEntity(), (double) livinghurtevent.getAmount());
        }

    }

    public static void execute(LevelAccessor levelaccessor, int d0, int d1, int d2, Entity entity, Entity entity1, double d3) {
        execute((Event) null, levelaccessor, d0, d1, d2, entity, entity1, d3);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, final int d0, final int d1, final int d2, final Entity entity, Entity entity1, double d3) {
        if (entity != null && entity1 != null) {
            LivingEntity livingentity;

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int)(d3 * 2.0D), 2, false, false));
                }
            }

            if (d3 >= 50.0D && entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                livingentity.setHealth(0.0F);
            }

            if (entity instanceof Mob) {
                Mob mob = (Mob)entity;

                if (entity1 instanceof LivingEntity) {
                    LivingEntity livingentity1 = (LivingEntity)entity1;

                    mob.setTarget(livingentity1);
                }
            }

            if (!entity1.getPersistentData().getBoolean("kick_x")) {
                LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

                if (livingentitypatch != null) {
                    final DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();
                    LivingEntity livingentity2;

                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity)entity;
                        if (!livingentity2.level().isClientSide()) {
                            livingentity2.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLEED.get(), (int)(d3 * 1.5D), 0, false, false));
                        }
                    }

                    if (!(entity instanceof Player)) {
                        if (!entity.getPersistentData().getBoolean("av_npc")) {
                            float f;

                            if (entity instanceof LivingEntity) {
                                livingentity2 = (LivingEntity)entity;
                                f = livingentity2.getMaxHealth();
                            } else {
                                f = -1.0F;
                            }

                            if (f == 20.0F) {
                                if (entity.fireImmune()) {
                                    if (entity instanceof LivingEntity) {
                                        LivingEntity livingentity3 = (LivingEntity)entity;

                                        f = livingentity3.getHealth();
                                    } else {
                                        f = -1.0F;
                                    }

                                    if (f <= 10.0F && entity.isAlive()) {
                                        new DelayedTask(50) {
                                            @Override
                                            public void run() {
                                                if (entity.isAlive()) {
                                                    float f1;

                                                    if (entity instanceof LivingEntity) {
                                                        LivingEntity livingentity4 = (LivingEntity)entity;

                                                        f1 = livingentity4.getHealth();
                                                    } else {
                                                        f1 = -1.0F;
                                                    }

                                                    if (f1 <= 10.0F) {
                                                        Entity entity2;

                                                        if (!(dynamicanimation instanceof AttackAnimation) && !(dynamicanimation instanceof LongHitAnimation) && !(dynamicanimation instanceof HitAnimation)) {
                                                            if (!entity.getPersistentData().getBoolean("eating")) {
                                                                entity.getPersistentData().putBoolean("eating", true);
                                                                LivingEntity livingentity5;

                                                                if (entity instanceof LivingEntity) {
                                                                    livingentity5 = (LivingEntity)entity;
                                                                    ItemStack itemstack = new ItemStack(Items.GOLDEN_APPLE);

                                                                    itemstack.setCount(1);
                                                                    livingentity5.setItemInHand(InteractionHand.OFF_HAND, itemstack);
                                                                    if (livingentity5 instanceof Player) {
                                                                        Player player = (Player)livingentity5;

                                                                        player.getInventory().setChanged();
                                                                    }
                                                                }

                                                                entity2 = entity;
                                                                if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                                                    try {
                                                                        entity2.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/eat_offhand\" 0 1", entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                    } catch (CommandSyntaxException e) {
                                                                        
                                                                    }
                                                                }

                                                                if (entity instanceof LivingEntity) {
                                                                    livingentity5 = (LivingEntity)entity;
                                                                    if (!livingentity5.level().isClientSide()) {
                                                                        livingentity5.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60, 0));
                                                                    }
                                                                }

                                                                new DelayedTask(4) {
                                                                    @Override
                                                                    public void run() {
                                                                        LevelAccessor levelaccessor1 = levelaccessor;

                                                                        if (levelaccessor1 instanceof Level) {
                                                                            Level level = (Level)levelaccessor1;

                                                                            if (!level.isClientSide()) {
                                                                                level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                            } else {
                                                                                level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                            }
                                                                        }

                                                                        Entity entity3 = entity;

                                                                        if (!entity3.level().isClientSide() && entity3.getServer() != null) {
                                                                            try {
                                                                                entity3.getServer().getCommands().getDispatcher().execute("execute at @s run particle minecraft:item golden_apple ^ ^1.5 ^0.5 0 0 0 0.01 10", entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                            } catch (CommandSyntaxException e) {
                                                                                
                                                                            }
                                                                        }

                                                                        if (!(dynamicanimation instanceof AttackAnimation) && !(dynamicanimation instanceof LongHitAnimation) && !(dynamicanimation instanceof HitAnimation)) {
                                                                            entity3 = entity;
                                                                            if (!entity3.level().isClientSide() && entity3.getServer() != null) {
                                                                                try {
                                                                                    entity3.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/eat_offhand\" 0 1", entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                } catch (CommandSyntaxException e) {
                                                                                    
                                                                                }
                                                                            }
                                                                        }

                                                                        new DelayedTask(4) {
                                                                            public void run() {
                                                                                LevelAccessor levelaccessor2 = levelaccessor;

                                                                                if (levelaccessor2 instanceof Level) {
                                                                                    Level level1 = (Level)levelaccessor2;

                                                                                    if (!level1.isClientSide()) {
                                                                                        level1.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                                    } else {
                                                                                        level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                                    }
                                                                                }

                                                                                Entity entity4 = entity;

                                                                                if (!entity4.level().isClientSide() && entity4.getServer() != null) {
                                                                                    try {
                                                                                        entity4.getServer().getCommands().getDispatcher().execute("execute at @s run particle minecraft:item golden_apple ^ ^1.5 ^0.5 0 0 0 0.01 10", entity4.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                    } catch (CommandSyntaxException e) {
                                                                                        
                                                                                    }
                                                                                }

                                                                                LivingEntity livingentity6;

                                                                                if (entity instanceof LivingEntity) {
                                                                                    livingentity6 = (LivingEntity)entity;
                                                                                    ItemStack itemstack1 = new ItemStack(Items.GOLDEN_APPLE);

                                                                                    itemstack1.setCount(1);
                                                                                    livingentity6.setItemInHand(InteractionHand.OFF_HAND, itemstack1);
                                                                                    if (livingentity6 instanceof Player) {
                                                                                        Player player1 = (Player)livingentity6;

                                                                                        player1.getInventory().setChanged();
                                                                                    }
                                                                                }

                                                                                if (entity instanceof LivingEntity) {
                                                                                    livingentity6 = (LivingEntity)entity;
                                                                                    if (!livingentity6.level().isClientSide()) {
                                                                                        livingentity6.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20, 0));
                                                                                    }
                                                                                }

                                                                                if (!(dynamicanimation instanceof AttackAnimation) && !(dynamicanimation instanceof LongHitAnimation) && !(dynamicanimation instanceof HitAnimation)) {
                                                                                    entity4 = entity;
                                                                                    if (!entity4.level().isClientSide() && entity4.getServer() != null) {
                                                                                        try {
                                                                                            entity4.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/eat_offhand\" 0 1", entity4.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                        } catch (
                                                                                                CommandSyntaxException e) {
                                                                                            
                                                                                        }
                                                                                    }
                                                                                }

                                                                                new DelayedTask(4) {
                                                                                    public void run() {
                                                                                        LevelAccessor levelaccessor3 = levelaccessor;

                                                                                        if (levelaccessor3 instanceof Level) {
                                                                                            Level level2 = (Level)levelaccessor3;

                                                                                            if (!level2.isClientSide()) {
                                                                                                level2.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                                            } else {
                                                                                                level2.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                                            }
                                                                                        }

                                                                                        Entity entity5 = entity;

                                                                                        if (!entity5.level().isClientSide() && entity5.getServer() != null) {
                                                                                            try {
                                                                                                entity5.getServer().getCommands().getDispatcher().execute("execute at @s run particle minecraft:item golden_apple ^ ^1.5 ^0.5 0 0 0 0.01 10", entity5.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                            } catch (
                                                                                                    CommandSyntaxException e) {
                                                                                                
                                                                                            }
                                                                                        }

                                                                                        if (!(dynamicanimation instanceof AttackAnimation) && !(dynamicanimation instanceof LongHitAnimation) && !(dynamicanimation instanceof HitAnimation)) {
                                                                                            entity5 = entity;
                                                                                            if (!entity5.level().isClientSide() && entity5.getServer() != null) {
                                                                                                try {
                                                                                                    entity5.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/eat_offhand\" 0 1", entity5.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                } catch (
                                                                                                        CommandSyntaxException e) {
                                                                                                    
                                                                                                }
                                                                                            }
                                                                                        }

                                                                                        new DelayedTask(4) {
                                                                                            public void run() {
                                                                                                LevelAccessor levelaccessor4 = levelaccessor;

                                                                                                if (levelaccessor4 instanceof Level) {
                                                                                                    Level level3 = (Level)levelaccessor4;

                                                                                                    if (!level3.isClientSide()) {
                                                                                                        level3.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                                                    } else {
                                                                                                        level3.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                                                    }
                                                                                                }

                                                                                                Entity entity6 = entity;

                                                                                                if (!entity6.level().isClientSide() && entity6.getServer() != null) {
                                                                                                    try {
                                                                                                        entity6.getServer().getCommands().getDispatcher().execute("execute at @s run particle minecraft:item golden_apple ^ ^1.5 ^0.5 0 0 0 0.01 10", entity6.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                    } catch (
                                                                                                            CommandSyntaxException e) {
                                                                                                        
                                                                                                    }
                                                                                                }

                                                                                                if (!(dynamicanimation instanceof AttackAnimation) && !(dynamicanimation instanceof LongHitAnimation) && !(dynamicanimation instanceof HitAnimation)) {
                                                                                                    entity6 = entity;
                                                                                                    if (!entity6.level().isClientSide() && entity6.getServer() != null) {
                                                                                                        try {
                                                                                                            entity6.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/eat_offhand\" 0 1", entity6.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                        } catch (
                                                                                                                CommandSyntaxException e) {
                                                                                                            
                                                                                                        }
                                                                                                    }
                                                                                                }

                                                                                                new DelayedTask(4) {
                                                                                                    public void run() {
                                                                                                        LevelAccessor levelaccessor5 = levelaccessor;

                                                                                                        if (levelaccessor5 instanceof Level) {
                                                                                                            Level level4 = (Level)levelaccessor5;

                                                                                                            if (!level4.isClientSide()) {
                                                                                                                level4.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                                                            } else {
                                                                                                                level4.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                                                            }
                                                                                                        }

                                                                                                        Entity entity7 = entity;

                                                                                                        if (!entity7.level().isClientSide() && entity7.getServer() != null) {
                                                                                                            try {
                                                                                                                entity7.getServer().getCommands().getDispatcher().execute("execute at @s run particle minecraft:item golden_apple ^ ^1.5 ^0.5 0 0 0 0.01 10", entity7.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                            } catch (
                                                                                                                    CommandSyntaxException e) {
                                                                                                                
                                                                                                            }
                                                                                                        }

                                                                                                        if (!(dynamicanimation instanceof AttackAnimation) && !(dynamicanimation instanceof LongHitAnimation) && !(dynamicanimation instanceof HitAnimation)) {
                                                                                                            entity7 = entity;
                                                                                                            if (!entity7.level().isClientSide() && entity7.getServer() != null) {
                                                                                                                try {
                                                                                                                    entity7.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/eat_offhand\" 0 1", entity7.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                                } catch (
                                                                                                                        CommandSyntaxException e) {
                                                                                                                    
                                                                                                                }
                                                                                                            }
                                                                                                        }

                                                                                                        new DelayedTask(4) {
                                                                                                            public void run() {
                                                                                                                LevelAccessor levelaccessor6 = levelaccessor;

                                                                                                                if (levelaccessor6 instanceof Level) {
                                                                                                                    Level level5 = (Level)levelaccessor6;

                                                                                                                    if (!level5.isClientSide()) {
                                                                                                                        level5.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                                                                    } else {
                                                                                                                        level5.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                                                                    }
                                                                                                                }

                                                                                                                Entity entity8 = entity;

                                                                                                                if (!entity8.level().isClientSide() && entity8.getServer() != null) {
                                                                                                                    try {
                                                                                                                        entity8.getServer().getCommands().getDispatcher().execute("execute at @s run particle minecraft:item golden_apple ^ ^1.5 ^0.5 0 0 0 0.01 10", entity8.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                                    } catch (
                                                                                                                            CommandSyntaxException e) {
                                                                                                                        
                                                                                                                    }
                                                                                                                }
                                                                                                                entity8 = entity;
                                                                                                                if (!entity8.level().isClientSide() && entity8.getServer() != null) {
                                                                                                                    try {
                                                                                                                        entity8.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/eat_offhand\" 0 1", entity8.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                                    } catch (
                                                                                                                            CommandSyntaxException e) {
                                                                                                                        
                                                                                                                    }
                                                                                                                }

                                                                                                                new DelayedTask(4) {
                                                                                                                    public void run() {
                                                                                                                        LevelAccessor levelaccessor7 = levelaccessor;

                                                                                                                        if (levelaccessor7 instanceof Level) {
                                                                                                                            Level level6 = (Level)levelaccessor7;

                                                                                                                            if (!level6.isClientSide()) {
                                                                                                                                level6.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                                                                            } else {
                                                                                                                                level6.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                                                                            }
                                                                                                                        }

                                                                                                                        Entity entity9 = entity;

                                                                                                                        if (!entity9.level().isClientSide() && entity9.getServer() != null) {
                                                                                                                            try {
                                                                                                                                entity9.getServer().getCommands().getDispatcher().execute("execute at @s run particle minecraft:item golden_apple ^ ^1.5 ^0.5 0 0 0 0.01 10", entity9.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                                            } catch (
                                                                                                                                    CommandSyntaxException e) {
                                                                                                                                
                                                                                                                            }
                                                                                                                        }

                                                                                                                        if (!(dynamicanimation instanceof AttackAnimation) && !(dynamicanimation instanceof LongHitAnimation) && !(dynamicanimation instanceof HitAnimation)) {
                                                                                                                            entity9 = entity;
                                                                                                                            if (!entity9.level().isClientSide() && entity9.getServer() != null) {
                                                                                                                                try {
                                                                                                                                    entity9.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/eat_offhand\" 0 1", entity9.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                                                } catch (
                                                                                                                                        CommandSyntaxException e) {
                                                                                                                                    
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }

                                                                                                                        new DelayedTask(3) {
                                                                                                                            public void run() {
                                                                                                                                if (Math.random() <= 0.4D) {
                                                                                                                                    LevelAccessor levelaccessor8 = levelaccessor;

                                                                                                                                    if (levelaccessor8 instanceof Level) {
                                                                                                                                        Level level7 = (Level)levelaccessor8;

                                                                                                                                        if (!level7.isClientSide()) {
                                                                                                                                            level7.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.player.burp")), SoundSource.NEUTRAL, 1.5F, 1.0F);
                                                                                                                                        } else {
                                                                                                                                            level7.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.player.burp")), SoundSource.NEUTRAL, 1.5F, 1.0F, false);
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        };
                                                                                                                        new DelayedTask(20) {
                                                                                                                            public void run() {
                                                                                                                                LivingEntity livingentity7;

                                                                                                                                if (entity instanceof LivingEntity) {
                                                                                                                                    livingentity7 = (LivingEntity)entity;
                                                                                                                                    ItemStack itemstack2 = new ItemStack(Items.ENDER_PEARL);

                                                                                                                                    itemstack2.setCount(1);
                                                                                                                                    livingentity7.setItemInHand(InteractionHand.OFF_HAND, itemstack2);
                                                                                                                                    if (livingentity7 instanceof Player) {
                                                                                                                                        Player player2 = (Player)livingentity7;

                                                                                                                                        player2.getInventory().setChanged();
                                                                                                                                    }
                                                                                                                                }

                                                                                                                                if (entity instanceof LivingEntity) {
                                                                                                                                    livingentity7 = (LivingEntity)entity;
                                                                                                                                    if (!livingentity7.level().isClientSide()) {
                                                                                                                                        livingentity7.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 500, 2));
                                                                                                                                    }
                                                                                                                                }

                                                                                                                                if (entity instanceof LivingEntity) {
                                                                                                                                    livingentity7 = (LivingEntity)entity;
                                                                                                                                    if (!livingentity7.level().isClientSide()) {
                                                                                                                                        livingentity7.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 0));
                                                                                                                                    }
                                                                                                                                }

                                                                                                                                entity.getPersistentData().putBoolean("eating", false);
                                                                                                                            }
                                                                                                                        };
                                                                                                                    }
                                                                                                                };
                                                                                                            }
                                                                                                        };
                                                                                                    }
                                                                                                };
                                                                                            }
                                                                                        };
                                                                                    }
                                                                                };
                                                                            }
                                                                        };
                                                                    }
                                                                };
                                                            }
                                                        }
                                                    }
                                                }

                                            }
                                        };
                                    }
                                }

                                if (entity.isPassenger()) {
                                    entity.stopRiding();
                                }
                            }
                        }

                        if (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString().equals("player_mobs:player_mob")  || ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString().equals("guardvillagers:guard")) {
                            entity1.getPersistentData().putDouble("hit_npc", entity1.getPersistentData().getDouble("hit_npc") + 1.0D);
                            entity.getPersistentData().putDouble("hit_npc", entity.getPersistentData().getDouble("hit_npc") + 1.0D);
                            entity1.getPersistentData().putBoolean("dont_kill", false);
                            entity.getPersistentData().putBoolean("dont_kill", false);
                            if (entity.isAlive() && entity instanceof LivingEntity player_mob) {
                                ItemStack oldItem = player_mob.getOffhandItem();
                                new DelayedTask(50) {
                                    public void run() {
                                        if (entity.isAlive()) {
                                            if (player_mob.getHealth() <= 10.0F) {
                                                LivingEntityPatch<?> livingentitypatch1 = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

                                                if (livingentitypatch1 != null) {
                                                    DynamicAnimation dynamicanimation1 = livingentitypatch1.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();
                                                    Entity entity2;

                                                    if (!(dynamicanimation1 instanceof AttackAnimation) && !(dynamicanimation1 instanceof LongHitAnimation) && !(dynamicanimation1 instanceof HitAnimation)) {
                                                        if (!entity.getPersistentData().getBoolean("eating")) {
                                                            entity.getPersistentData().putBoolean("eating", true);
                                                            if (entity instanceof LivingEntity) {
                                                                LivingEntity livingentity5 = (LivingEntity)entity;
                                                                ItemStack itemstack = new ItemStack(Items.GOLDEN_APPLE);

                                                                itemstack.setCount(1);
                                                                livingentity5.setItemInHand(InteractionHand.OFF_HAND, itemstack);
                                                                if (livingentity5 instanceof Player) {
                                                                    Player player = (Player)livingentity5;

                                                                    player.getInventory().setChanged();
                                                                }
                                                            }

                                                            entity2 = entity;
                                                            if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                                                try {
                                                                    entity2.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/eat_offhand\" 0 1", entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                } catch (CommandSyntaxException e) {
                                                                    
                                                                }
                                                            }

                                                            new DelayedTask(4) {
                                                                public void run() {
                                                                    LevelAccessor levelaccessor1 = levelaccessor;

                                                                    if (levelaccessor1 instanceof Level) {
                                                                        Level level = (Level)levelaccessor1;

                                                                        if (!level.isClientSide()) {
                                                                            level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                        } else {
                                                                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                        }
                                                                    }

                                                                    Entity entity3 = entity;

                                                                    if (!entity3.level().isClientSide() && entity3.getServer() != null) {
                                                                        try {
                                                                            entity3.getServer().getCommands().getDispatcher().execute("execute at @s run particle minecraft:item golden_apple ^ ^1.5 ^0.5 0 0 0 0.01 10", entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                        } catch (CommandSyntaxException e) {
                                                                            
                                                                        }
                                                                    }

                                                                    if (!(dynamicanimation instanceof AttackAnimation) && !(dynamicanimation instanceof LongHitAnimation) && !(dynamicanimation instanceof HitAnimation)) {
                                                                        entity3 = entity;
                                                                        if (!entity3.level().isClientSide() && entity3.getServer() != null) {
                                                                            try {
                                                                                entity3.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/eat_offhand\" 0 1", entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                            } catch (CommandSyntaxException e) {
                                                                                
                                                                            }
                                                                        }
                                                                    }

                                                                    new DelayedTask(4) {
                                                                        public void run() {
                                                                            LevelAccessor levelaccessor2 = levelaccessor;

                                                                            if (levelaccessor2 instanceof Level) {
                                                                                Level level1 = (Level)levelaccessor2;

                                                                                if (!level1.isClientSide()) {
                                                                                    level1.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                                } else {
                                                                                    level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                                }
                                                                            }

                                                                            Entity entity4 = entity;

                                                                            if (!entity4.level().isClientSide() && entity4.getServer() != null) {
                                                                                try {
                                                                                    entity4.getServer().getCommands().getDispatcher().execute("execute at @s run particle minecraft:item golden_apple ^ ^1.5 ^0.5 0 0 0 0.01 10", entity4.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                } catch (CommandSyntaxException e) {
                                                                                    
                                                                                }
                                                                            }

                                                                            LivingEntity livingentity6;

                                                                            if (entity instanceof LivingEntity) {
                                                                                livingentity6 = (LivingEntity)entity;
                                                                                ItemStack itemstack1 = new ItemStack(Items.GOLDEN_APPLE);

                                                                                itemstack1.setCount(1);
                                                                                livingentity6.setItemInHand(InteractionHand.OFF_HAND, itemstack1);
                                                                                if (livingentity6 instanceof Player) {
                                                                                    Player player1 = (Player)livingentity6;

                                                                                    player1.getInventory().setChanged();
                                                                                }
                                                                            }

                                                                            if (entity instanceof LivingEntity) {
                                                                                livingentity6 = (LivingEntity)entity;
                                                                                if (!livingentity6.level().isClientSide()) {
                                                                                    livingentity6.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20, 0));
                                                                                }
                                                                            }

                                                                            if (!(dynamicanimation1 instanceof AttackAnimation) && !(dynamicanimation1 instanceof LongHitAnimation) && !(dynamicanimation1 instanceof HitAnimation)) {
                                                                                entity4 = entity;
                                                                                if (!entity4.level().isClientSide() && entity4.getServer() != null) {
                                                                                    try {
                                                                                        entity4.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/eat_offhand\" 0 1", entity4.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                    } catch (CommandSyntaxException e) {
                                                                                        
                                                                                    }
                                                                                }
                                                                            }

                                                                            new DelayedTask(4) {
                                                                                public void run() {
                                                                                    LevelAccessor levelaccessor3 = levelaccessor;

                                                                                    if (levelaccessor3 instanceof Level) {
                                                                                        Level level2 = (Level)levelaccessor3;

                                                                                        if (!level2.isClientSide()) {
                                                                                            level2.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                                        } else {
                                                                                            level2.playLocalSound(0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                                        }
                                                                                    }

                                                                                    Entity entity5 = entity;

                                                                                    if (!entity5.level().isClientSide() && entity5.getServer() != null) {
                                                                                        try {
                                                                                            entity5.getServer().getCommands().getDispatcher().execute("execute at @s run particle minecraft:item golden_apple ^ ^1.5 ^0.5 0 0 0 0.01 10", entity5.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                        } catch (
                                                                                                CommandSyntaxException e) {
                                                                                            
                                                                                        }
                                                                                    }

                                                                                    if (!(dynamicanimation1 instanceof AttackAnimation) && !(dynamicanimation1 instanceof LongHitAnimation) && !(dynamicanimation1 instanceof HitAnimation)) {
                                                                                        entity5 = entity;
                                                                                        if (!entity5.level().isClientSide() && entity5.getServer() != null) {
                                                                                            try {
                                                                                                entity5.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/eat_offhand\" 0 1", entity5.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                            } catch (
                                                                                                    CommandSyntaxException e) {
                                                                                                
                                                                                            }
                                                                                        }
                                                                                    }

                                                                                    new DelayedTask(4) {
                                                                                        public void run() {
                                                                                            LevelAccessor levelaccessor4 = levelaccessor;

                                                                                            if (levelaccessor4 instanceof Level) {
                                                                                                Level level3 = (Level)levelaccessor4;

                                                                                                if (!level3.isClientSide()) {
                                                                                                    level3.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                                                } else {
                                                                                                    level3.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                                                }
                                                                                            }

                                                                                            Entity entity6 = entity;

                                                                                            if (!entity6.level().isClientSide() && entity6.getServer() != null) {
                                                                                                try {
                                                                                                    entity6.getServer().getCommands().getDispatcher().execute("execute at @s run particle minecraft:item golden_apple ^ ^1.5 ^0.5 0 0 0 0.01 10", entity6.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                } catch (
                                                                                                        CommandSyntaxException e) {
                                                                                                    
                                                                                                }
                                                                                            }

                                                                                            if (!(dynamicanimation1 instanceof AttackAnimation) && !(dynamicanimation1 instanceof LongHitAnimation) && !(dynamicanimation1 instanceof HitAnimation)) {
                                                                                                entity6 = entity;
                                                                                                if (!entity6.level().isClientSide() && entity6.getServer() != null) {
                                                                                                    try {
                                                                                                        entity6.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/eat_offhand\" 0 1", entity6.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                    } catch (
                                                                                                            CommandSyntaxException e) {
                                                                                                        
                                                                                                    }
                                                                                                }
                                                                                            }

                                                                                            new DelayedTask(4) {
                                                                                                public void run() {
                                                                                                    LevelAccessor levelaccessor5 = levelaccessor;

                                                                                                    if (levelaccessor5 instanceof Level) {
                                                                                                        Level level4 = (Level)levelaccessor5;

                                                                                                        if (!level4.isClientSide()) {
                                                                                                            level4.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                                                        } else {
                                                                                                            level4.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                                                        }
                                                                                                    }

                                                                                                    Entity entity7 = entity;

                                                                                                    if (!entity7.level().isClientSide() && entity7.getServer() != null) {
                                                                                                        try {
                                                                                                            entity7.getServer().getCommands().getDispatcher().execute("execute at @s run particle minecraft:item golden_apple ^ ^1.5 ^0.5 0 0 0 0.01 10", entity7.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                        } catch (
                                                                                                                CommandSyntaxException e) {
                                                                                                            
                                                                                                        }
                                                                                                    }

                                                                                                    if (!(dynamicanimation1 instanceof AttackAnimation) && !(dynamicanimation1 instanceof LongHitAnimation) && !(dynamicanimation1 instanceof HitAnimation)) {
                                                                                                        entity7 = entity;
                                                                                                        if (!entity7.level().isClientSide() && entity7.getServer() != null) {
                                                                                                            try {
                                                                                                                entity7.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/eat_offhand\" 0 1", entity7.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                            } catch (
                                                                                                                    CommandSyntaxException e) {
                                                                                                                
                                                                                                            }
                                                                                                        }
                                                                                                    }

                                                                                                    new DelayedTask(4) {
                                                                                                        public void run() {
                                                                                                            LevelAccessor levelaccessor6 = levelaccessor;

                                                                                                            if (levelaccessor6 instanceof Level) {
                                                                                                                Level level5 = (Level)levelaccessor6;

                                                                                                                if (!level5.isClientSide()) {
                                                                                                                    level5.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                                                                } else {
                                                                                                                    level5.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                                                                }
                                                                                                            }

                                                                                                            Entity entity8 = entity;

                                                                                                            if (!entity8.level().isClientSide() && entity8.getServer() != null) {
                                                                                                                try {
                                                                                                                    entity8.getServer().getCommands().getDispatcher().execute("execute at @s run particle minecraft:item golden_apple ^ ^1.5 ^0.5 0 0 0 0.01 10", entity8.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                                } catch (
                                                                                                                        CommandSyntaxException e) {
                                                                                                                    
                                                                                                                }
                                                                                                            }

                                                                                                            entity8 = entity;
                                                                                                            if (!entity8.level().isClientSide() && entity8.getServer() != null) {
                                                                                                                try {
                                                                                                                    entity8.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/eat_offhand\" 0 1", entity8.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                                } catch (
                                                                                                                        CommandSyntaxException e) {
                                                                                                                    
                                                                                                                }
                                                                                                            }

                                                                                                            new DelayedTask(4) {
                                                                                                                public void run() {
                                                                                                                    LevelAccessor levelaccessor7 = levelaccessor;

                                                                                                                    if (levelaccessor7 instanceof Level) {
                                                                                                                        Level level6 = (Level)levelaccessor7;

                                                                                                                        if (!level6.isClientSide()) {
                                                                                                                            level6.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                                                                        } else {
                                                                                                                            level6.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                                                                        }
                                                                                                                    }

                                                                                                                    Entity entity9 = entity;

                                                                                                                    if (!entity9.level().isClientSide() && entity9.getServer() != null) {
                                                                                                                        try {
                                                                                                                            entity9.getServer().getCommands().getDispatcher().execute("execute at @s run particle minecraft:item golden_apple ^ ^1.5 ^0.5 0 0 0 0.01 10", entity9.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                                        } catch (
                                                                                                                                CommandSyntaxException e) {
                                                                                                                            
                                                                                                                        }
                                                                                                                    }

                                                                                                                    if (!(dynamicanimation1 instanceof AttackAnimation) && !(dynamicanimation1 instanceof LongHitAnimation) && !(dynamicanimation1 instanceof HitAnimation)) {
                                                                                                                        entity9 = entity;
                                                                                                                        if (!entity9.level().isClientSide() && entity9.getServer() != null) {
                                                                                                                            try {
                                                                                                                                entity9.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/eat_offhand\" 0 1", entity9.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                                            } catch (
                                                                                                                                    CommandSyntaxException e) {
                                                                                                                                
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                    new DelayedTask(3) {
                                                                                                                        public void run() {
                                                                                                                            if (Math.random() <= 0.4D) {
                                                                                                                                LevelAccessor levelaccessor8 = levelaccessor;

                                                                                                                                if (levelaccessor8 instanceof Level) {
                                                                                                                                    Level level7 = (Level)levelaccessor8;

                                                                                                                                    if (!level7.isClientSide()) {
                                                                                                                                        level7.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.player.burp")), SoundSource.NEUTRAL, 1.5F, 1.0F);
                                                                                                                                    } else {
                                                                                                                                        level7.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.player.burp")), SoundSource.NEUTRAL, 1.5F, 1.0F, false);
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    };

                                                                                                                    new DelayedTask(20) {
                                                                                                                        public void run() {
                                                                                                                            LivingEntity livingentity7;

                                                                                                                            if (entity instanceof LivingEntity) {
                                                                                                                                livingentity7 = (LivingEntity)entity;
                                                                                                                                livingentity7.setItemInHand(InteractionHand.OFF_HAND, oldItem);
                                                                                                                                if (livingentity7 instanceof Player) {
                                                                                                                                    Player player2 = (Player)livingentity7;

                                                                                                                                    player2.getInventory().setChanged();
                                                                                                                                }
                                                                                                                            }

                                                                                                                            if (entity instanceof LivingEntity) {
                                                                                                                                livingentity7 = (LivingEntity)entity;
                                                                                                                                if (!livingentity7.level().isClientSide()) {
                                                                                                                                    livingentity7.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 500, 2));
                                                                                                                                }
                                                                                                                            }

                                                                                                                            if (entity instanceof LivingEntity) {
                                                                                                                                livingentity7 = (LivingEntity)entity;
                                                                                                                                if (!livingentity7.level().isClientSide()) {
                                                                                                                                    livingentity7.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 0));
                                                                                                                                }
                                                                                                                            }

                                                                                                                            entity.getPersistentData().putBoolean("eating", false);
                                                                                                                        }
                                                                                                                    };
                                                                                                                }
                                                                                                            };
                                                                                                        }
                                                                                                    };
                                                                                                }
                                                                                            };
                                                                                        }
                                                                                    };
                                                                                }
                                                                            };
                                                                        }
                                                                    };
                                                                }
                                                            };
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                };
                            }
                        }

                        if (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString().equals("minecraft:zombie") || ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString().equals("minecraft:husk") || ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString().equals("minecraft:skeleton") || ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString().equals("minecraft:wither_skeleton")) {
                            if (entity instanceof LivingEntity zombie && zombie.getHealth() <= 18.0F && entity.isAlive()) {
                                new DelayedTask(50) {
                                    @Override
                                    public void run() {
                                        if (zombie.isAlive()) {
                                            if (zombie.getHealth() <= 15.0F) {
                                                LivingEntityPatch<?> livingentitypatch2 = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                                ItemStack oldItem = zombie.getOffhandItem();
                                                Entity entity2;

                                                if (livingentitypatch2 != null) {
                                                    DynamicAnimation dynamicanimation3 = livingentitypatch2.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();

                                                    if (!(dynamicanimation3 instanceof AttackAnimation) && !(dynamicanimation3 instanceof LongHitAnimation) && !(dynamicanimation3 instanceof HitAnimation)) {
                                                        if (!zombie.getPersistentData().getBoolean("hostile_healing")) {
                                                            zombie.getPersistentData().putBoolean("hostile_healing", true);
                                                            LivingEntity livingentity2;

                                                            if (entity instanceof LivingEntity) {
                                                                livingentity2 = (LivingEntity) zombie;
                                                                ItemStack itemstack = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HARMING);
                                                                itemstack.setCount(1);
                                                                livingentity2.setItemInHand(InteractionHand.OFF_HAND, itemstack);
                                                                if (livingentity2 instanceof Player) {
                                                                    Player player = (Player) livingentity2;
                                                                    player.getInventory().setChanged();
                                                                }
                                                            }

                                                            entity2 = zombie;
                                                            if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                                                try {
                                                                    entity2.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/drink_offhand\" 0 1", entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                } catch (CommandSyntaxException e) {
                                                                    
                                                                }
                                                            }

                                                            if (entity instanceof LivingEntity) {
                                                                livingentity2 = (LivingEntity) zombie;
                                                                if (!livingentity2.level().isClientSide()) {
                                                                    livingentity2.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60, 0));
                                                                }
                                                            }

                                                            new DelayedTask(4) {
                                                                @Override
                                                                public void run() {
                                                                    LevelAccessor levelaccessor1 = levelaccessor;

                                                                    if (levelaccessor1 instanceof Level) {
                                                                        Level level = (Level) levelaccessor1;

                                                                        if (!level.isClientSide()) {
                                                                            level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.drink")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                        } else {
                                                                            level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.drink")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                        }
                                                                    }

                                                                    Entity entity3 = entity;
                                                                    if (!(dynamicanimation3 instanceof AttackAnimation) && !(dynamicanimation3 instanceof LongHitAnimation) && !(dynamicanimation3 instanceof HitAnimation)) {
                                                                        entity3 = entity;
                                                                        if (!entity3.level().isClientSide() && entity3.getServer() != null) {
                                                                            try {
                                                                                entity3.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/drink_offhand\" 0 1", entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                            } catch (CommandSyntaxException e) {
                                                                                
                                                                            }
                                                                        }
                                                                    }

                                                                    new DelayedTask(4) {
                                                                        public void run() {
                                                                            LevelAccessor levelaccessor2 = levelaccessor;

                                                                            if (levelaccessor2 instanceof Level) {
                                                                                Level level1 = (Level) levelaccessor2;

                                                                                if (!level1.isClientSide()) {
                                                                                    level1.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.drink")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                                } else {
                                                                                    level1.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.drink")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                                }
                                                                            }

                                                                            Entity entity4;
                                                                            LivingEntity livingentity6;

                                                                            if (zombie instanceof LivingEntity) {
                                                                                livingentity6 = (LivingEntity) zombie;
                                                                                ItemStack itemstack1 = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HARMING);

                                                                                itemstack1.setCount(1);
                                                                                livingentity6.setItemInHand(InteractionHand.OFF_HAND, itemstack1);
                                                                                if (livingentity6 instanceof Player) {
                                                                                    Player player1 = (Player) livingentity6;

                                                                                    player1.getInventory().setChanged();
                                                                                }
                                                                            }

                                                                            if (zombie instanceof LivingEntity) {
                                                                                livingentity6 = (LivingEntity) zombie;
                                                                                if (!livingentity6.level().isClientSide()) {
                                                                                    livingentity6.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20, 0));
                                                                                }
                                                                            }

                                                                            if (!(dynamicanimation3 instanceof AttackAnimation) && !(dynamicanimation3 instanceof LongHitAnimation) && !(dynamicanimation3 instanceof HitAnimation)) {
                                                                                entity4 = zombie;
                                                                                if (!entity4.level().isClientSide() && entity4.getServer() != null) {
                                                                                    try {
                                                                                        entity4.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/drink_offhand\" 0 1", entity4.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                    } catch (CommandSyntaxException e) {
                                                                                        
                                                                                    }
                                                                                }
                                                                            }

                                                                            new DelayedTask(4) {
                                                                                public void run() {
                                                                                    LevelAccessor levelaccessor3 = levelaccessor;

                                                                                    if (levelaccessor3 instanceof Level) {
                                                                                        Level level2 = (Level) levelaccessor3;

                                                                                        if (!level2.isClientSide()) {
                                                                                            level2.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.drink")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                                        } else {
                                                                                            level2.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.drink")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                                        }
                                                                                    }

                                                                                    Entity entity5 = entity;
                                                                                    if (!(dynamicanimation3 instanceof AttackAnimation) && !(dynamicanimation3 instanceof LongHitAnimation) && !(dynamicanimation3 instanceof HitAnimation)) {
                                                                                        entity5 = zombie;
                                                                                        if (!entity5.level().isClientSide() && entity5.getServer() != null) {
                                                                                            try {
                                                                                                entity5.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/drink_offhand\" 0 1", entity5.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                            } catch (
                                                                                                    CommandSyntaxException e) {
                                                                                                
                                                                                            }
                                                                                        }
                                                                                    }

                                                                                    new DelayedTask(4) {
                                                                                        public void run() {
                                                                                            LevelAccessor levelaccessor4 = levelaccessor;

                                                                                            if (levelaccessor4 instanceof Level) {
                                                                                                Level level3 = (Level) levelaccessor4;

                                                                                                if (!level3.isClientSide()) {
                                                                                                    level3.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.drink")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                                                } else {
                                                                                                    level3.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                                                }
                                                                                            }

                                                                                            Entity entity6 = zombie;
                                                                                            if (!(dynamicanimation3 instanceof AttackAnimation) && !(dynamicanimation3 instanceof LongHitAnimation) && !(dynamicanimation3 instanceof HitAnimation)) {
                                                                                                entity6 = zombie;
                                                                                                if (!entity6.level().isClientSide() && entity6.getServer() != null) {
                                                                                                    try {
                                                                                                        entity6.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/drink_offhand\" 0 1", entity6.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                    } catch (
                                                                                                            CommandSyntaxException e) {
                                                                                                        
                                                                                                    }
                                                                                                }
                                                                                            }

                                                                                            new DelayedTask(4) {
                                                                                                public void run() {
                                                                                                    LevelAccessor levelaccessor5 = levelaccessor;

                                                                                                    if (levelaccessor5 instanceof Level) {
                                                                                                        Level level4 = (Level) levelaccessor5;

                                                                                                        if (!level4.isClientSide()) {
                                                                                                            level4.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.drink")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                                                        } else {
                                                                                                            level4.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.drink")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                                                        }
                                                                                                    }

                                                                                                    Entity entity7 = zombie;
                                                                                                    if (!(dynamicanimation3 instanceof AttackAnimation) && !(dynamicanimation3 instanceof LongHitAnimation) && !(dynamicanimation3 instanceof HitAnimation)) {
                                                                                                        entity7 = zombie;
                                                                                                        if (!entity7.level().isClientSide() && entity7.getServer() != null) {
                                                                                                            try {
                                                                                                                entity7.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/drink_offhand\" 0 1", entity7.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                            } catch (
                                                                                                                    CommandSyntaxException e) {
                                                                                                                
                                                                                                            }
                                                                                                        }
                                                                                                    }

                                                                                                    new DelayedTask(4) {
                                                                                                        public void run() {
                                                                                                            LevelAccessor levelaccessor6 = levelaccessor;

                                                                                                            if (levelaccessor6 instanceof Level) {
                                                                                                                Level level5 = (Level) levelaccessor6;

                                                                                                                if (!level5.isClientSide()) {
                                                                                                                    level5.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.drink")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                                                                } else {
                                                                                                                    level5.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.drink")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                                                                }
                                                                                                            }

                                                                                                            Entity entity8 = zombie;
                                                                                                            if (!entity8.level().isClientSide() && entity8.getServer() != null) {
                                                                                                                try {
                                                                                                                    entity8.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/drink_offhand\" 0 1", entity8.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                                } catch (
                                                                                                                        CommandSyntaxException e) {
                                                                                                                    
                                                                                                                }
                                                                                                            }

                                                                                                            new DelayedTask(4) {
                                                                                                                public void run() {
                                                                                                                    LevelAccessor levelaccessor7 = levelaccessor;

                                                                                                                    if (levelaccessor7 instanceof Level) {
                                                                                                                        Level level6 = (Level) levelaccessor7;

                                                                                                                        if (!level6.isClientSide()) {
                                                                                                                            level6.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.drink")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                                                                        } else {
                                                                                                                            level6.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.drink")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                                                                        }
                                                                                                                    }

                                                                                                                    Entity entity9 = zombie;
                                                                                                                    if (!(dynamicanimation3 instanceof AttackAnimation) && !(dynamicanimation3 instanceof LongHitAnimation) && !(dynamicanimation3 instanceof HitAnimation)) {
                                                                                                                        entity9 = zombie;
                                                                                                                        if (!entity9.level().isClientSide() && entity9.getServer() != null) {
                                                                                                                            try {
                                                                                                                                entity9.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/living/drink_offhand\" 0 1", entity9.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                                            } catch (
                                                                                                                                    CommandSyntaxException e) {
                                                                                                                                
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }

                                                                                                                    new DelayedTask(3) {
                                                                                                                        public void run() {
                                                                                                                            if (Math.random() <= 0.4D) {
                                                                                                                                LevelAccessor levelaccessor8 = levelaccessor;

                                                                                                                                if (levelaccessor8 instanceof Level) {
                                                                                                                                    Level level7 = (Level) levelaccessor8;

                                                                                                                                    if (!level7.isClientSide()) {
                                                                                                                                        level7.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.player.burp")), SoundSource.NEUTRAL, 1.5F, 1.0F);
                                                                                                                                    } else {
                                                                                                                                        level7.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.player.burp")), SoundSource.NEUTRAL, 1.5F, 1.0F, false);
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    };
                                                                                                                    new DelayedTask(20) {
                                                                                                                        public void run() {
                                                                                                                            LivingEntity livingentity7;

                                                                                                                            if (zombie instanceof LivingEntity) {
                                                                                                                                livingentity7 = (LivingEntity) zombie;
                                                                                                                                livingentity7.setItemInHand(InteractionHand.OFF_HAND, oldItem);
                                                                                                                                if (livingentity7 instanceof Player) {
                                                                                                                                    Player player2 = (Player) livingentity7;

                                                                                                                                    player2.getInventory().setChanged();
                                                                                                                                }
                                                                                                                            }

                                                                                                                            if (entity instanceof LivingEntity) {
                                                                                                                                livingentity7 = (LivingEntity) zombie;
                                                                                                                                if (!livingentity7.level().isClientSide()) {
                                                                                                                                    livingentity7.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 2));
                                                                                                                                }
                                                                                                                            }

                                                                                                                            entity.getPersistentData().putBoolean("hostile_healing", false);
                                                                                                                        }
                                                                                                                    };
                                                                                                                }
                                                                                                            };
                                                                                                        }
                                                                                                    };
                                                                                                }
                                                                                            };
                                                                                        }
                                                                                    };
                                                                                }
                                                                            };
                                                                        }
                                                                    };
                                                                }
                                                            };
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    }
                                };
                            };
                        }
                    }
                }
            }

        }
    }
}

