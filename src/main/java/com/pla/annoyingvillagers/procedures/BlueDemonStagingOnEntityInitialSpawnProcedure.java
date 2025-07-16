package com.pla.annoyingvillagers.procedures;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import com.pla.annoyingvillagers.entity.BlueDemonTridentParticleEntity;
import com.pla.annoyingvillagers.entity.BlueDemonEndStagingEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;

public class BlueDemonStagingOnEntityInitialSpawnProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            Vec3 vec3 = new Vec3(d0, d1, d2);
            List<Entity> list = (List)levelaccessor.getEntitiesOfClass(Entity.class, (new AABB(vec3, vec3)).inflate(32.0D), (entity1) -> {
                return true;
            }).stream().sorted(Comparator.comparingDouble((entity1) -> {
                return entity1.distanceToSqr(vec3);
            })).collect(Collectors.toList());
            Iterator iterator = list.iterator();

            while(iterator.hasNext()) {
                Entity entity1 = (Entity)iterator.next();

                if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                    entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "impactful @s shake 400 5 5");
                }
            }

            ItemStack itemstack;

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;

                itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 4);
            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect give @s annoyingvillagers:captive 20000 0 true");
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "item replace entity @s weapon.mainhand with annoyingvillagers:bluedemontrident");
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "item replace entity @s weapon.offhand with annoyingvillagers:bluedemontrident");
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoyingvillagers:biped/other/blue_demon_start_skill\" 0 1");
            }

            new DelayedTask(25) {
                @Override
                public void run() {
                    LevelAccessor levelaccessor1 = levelaccessor;

                    if (levelaccessor1 instanceof Level) {
                        Level level = (Level)levelaccessor1;

                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:tridentfs_skill")), SoundSource.NEUTRAL, 5.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:tridentfs_skill")), SoundSource.NEUTRAL, 5.0F, 1.0F, false);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    ServerLevel serverlevel;
                    BlueDemonTridentParticleEntity bdtridententity;
                    Mob mob;

                    if (levelaccessor1 instanceof ServerLevel) {
                        serverlevel = (ServerLevel)levelaccessor1;
                        bdtridententity = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel);
                        bdtridententity.moveTo(d0 + (double)Mth.nextInt(new Random(), -3, 3), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -3, 3), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                        if (bdtridententity instanceof Mob) {
                            mob = (Mob)bdtridententity;
                            mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(bdtridententity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                        }

                        levelaccessor.addFreshEntity(bdtridententity);
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof ServerLevel) {
                        serverlevel = (ServerLevel)levelaccessor1;
                        bdtridententity = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel);
                        bdtridententity.moveTo(d0 + (double)Mth.nextInt(new Random(), -3, 3), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -3, 3), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                        if (bdtridententity instanceof Mob) {
                            mob = (Mob)bdtridententity;
                            mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(bdtridententity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                        }

                        levelaccessor.addFreshEntity(bdtridententity);
                    }

                    new DelayedTask(10) {
                        @Override
                        public void run() {
                            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<Blue Demon> Trident Carnival!!!"), ChatType.SYSTEM, Util.NIL_UUID);
                            }

                            LevelAccessor levelaccessor2 = levelaccessor;

                            if (levelaccessor2 instanceof ServerLevel) {
                                ServerLevel serverlevel1 = (ServerLevel)levelaccessor2;
                                BlueDemonTridentParticleEntity bdtridententity1 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel1);

                                bdtridententity1.moveTo(d0 + (double)Mth.nextInt(new Random(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                if (bdtridententity1 instanceof Mob) {
                                    Mob mob1 = (Mob)bdtridententity1;

                                    mob1.finalizeSpawn(serverlevel1, levelaccessor.getCurrentDifficultyAt(bdtridententity1.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                }

                                levelaccessor.addFreshEntity(bdtridententity1);

                                new DelayedTask(10) {
                                    @Override
                                    public void run() {
                                        LevelAccessor levelaccessor3 = levelaccessor;
                                        ServerLevel serverlevel2;
                                        BlueDemonTridentParticleEntity bdtridententity2;
                                        Mob mob2;

                                        if (levelaccessor3 instanceof ServerLevel) {
                                            serverlevel2 = (ServerLevel)levelaccessor3;
                                            bdtridententity2 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel2);
                                            bdtridententity2.moveTo(d0 + (double)Mth.nextInt(new Random(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                            if (bdtridententity2 instanceof Mob) {
                                                mob2 = (Mob)bdtridententity2;
                                                mob2.finalizeSpawn(serverlevel2, levelaccessor.getCurrentDifficultyAt(bdtridententity2.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                            }

                                            levelaccessor.addFreshEntity(bdtridententity2);
                                        }

                                        levelaccessor3 = levelaccessor;
                                        if (levelaccessor3 instanceof ServerLevel) {
                                            serverlevel2 = (ServerLevel)levelaccessor3;
                                            bdtridententity2 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel2);
                                            bdtridententity2.moveTo(d0 + (double)Mth.nextInt(new Random(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                            if (bdtridententity2 instanceof Mob) {
                                                mob2 = (Mob)bdtridententity2;
                                                mob2.finalizeSpawn(serverlevel2, levelaccessor.getCurrentDifficultyAt(bdtridententity2.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                            }

                                            levelaccessor.addFreshEntity(bdtridententity2);
                                        }

                                        new DelayedTask(10) {
                                            @Override
                                            public void run() {
                                                LevelAccessor levelaccessor4 = levelaccessor;
                                                ServerLevel serverlevel3;
                                                BlueDemonTridentParticleEntity bdtridententity3;
                                                Mob mob3;

                                                if (levelaccessor4 instanceof ServerLevel) {
                                                    serverlevel3 = (ServerLevel)levelaccessor4;
                                                    bdtridententity3 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel3);
                                                    bdtridententity3.moveTo(d0 + (double)Mth.nextInt(new Random(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                                    if (bdtridententity3 instanceof Mob) {
                                                        mob3 = (Mob)bdtridententity3;
                                                        mob3.finalizeSpawn(serverlevel3, levelaccessor.getCurrentDifficultyAt(bdtridententity3.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                    }

                                                    levelaccessor.addFreshEntity(bdtridententity3);
                                                }

                                                levelaccessor4 = levelaccessor;
                                                if (levelaccessor4 instanceof ServerLevel) {
                                                    serverlevel3 = (ServerLevel)levelaccessor4;
                                                    bdtridententity3 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel3);
                                                    bdtridententity3.moveTo(d0 + (double)Mth.nextInt(new Random(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                                    if (bdtridententity3 instanceof Mob) {
                                                        mob3 = (Mob)bdtridententity3;
                                                        mob3.finalizeSpawn(serverlevel3, levelaccessor.getCurrentDifficultyAt(bdtridententity3.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                    }

                                                    levelaccessor.addFreshEntity(bdtridententity3);
                                                }

                                                new DelayedTask(5) {
                                                    @Override
                                                    public void run() {
                                                        LevelAccessor levelaccessor5 = levelaccessor;
                                                        ServerLevel serverlevel4;
                                                        BlueDemonTridentParticleEntity bdtridententity4;
                                                        Mob mob4;

                                                        if (levelaccessor5 instanceof ServerLevel) {
                                                            serverlevel4 = (ServerLevel)levelaccessor5;
                                                            bdtridententity4 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel4);
                                                            bdtridententity4.moveTo(d0 + (double)Mth.nextInt(new Random(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                                            if (bdtridententity4 instanceof Mob) {
                                                                mob4 = (Mob)bdtridententity4;
                                                                mob4.finalizeSpawn(serverlevel4, levelaccessor.getCurrentDifficultyAt(bdtridententity4.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                            }

                                                            levelaccessor.addFreshEntity(bdtridententity4);
                                                        }

                                                        levelaccessor5 = levelaccessor;
                                                        if (levelaccessor5 instanceof ServerLevel) {
                                                            serverlevel4 = (ServerLevel)levelaccessor5;
                                                            bdtridententity4 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel4);
                                                            bdtridententity4.moveTo(d0 + (double)Mth.nextInt(new Random(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), 3, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                                            if (bdtridententity4 instanceof Mob) {
                                                                mob4 = (Mob)bdtridententity4;
                                                                mob4.finalizeSpawn(serverlevel4, levelaccessor.getCurrentDifficultyAt(bdtridententity4.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                            }

                                                            levelaccessor.addFreshEntity(bdtridententity4);
                                                        }

                                                        levelaccessor5 = levelaccessor;
                                                        if (levelaccessor5 instanceof ServerLevel) {
                                                            serverlevel4 = (ServerLevel)levelaccessor5;
                                                            bdtridententity4 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel4);
                                                            bdtridententity4.moveTo(d0 + (double)Mth.nextInt(new Random(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), 3, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                                            if (bdtridententity4 instanceof Mob) {
                                                                mob4 = (Mob)bdtridententity4;
                                                                mob4.finalizeSpawn(serverlevel4, levelaccessor.getCurrentDifficultyAt(bdtridententity4.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                            }

                                                            levelaccessor.addFreshEntity(bdtridententity4);
                                                        }

                                                        new DelayedTask(5) {
                                                            @Override
                                                            public void run() {
                                                                LevelAccessor levelaccessor6 = levelaccessor;
                                                                ServerLevel serverlevel5;
                                                                BlueDemonTridentParticleEntity bdtridententity5;
                                                                Mob mob5;

                                                                if (levelaccessor6 instanceof ServerLevel) {
                                                                    serverlevel5 = (ServerLevel)levelaccessor6;
                                                                    bdtridententity5 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel5);
                                                                    bdtridententity5.moveTo(d0 + (double)Mth.nextInt(new Random(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                    if (bdtridententity5 instanceof Mob) {
                                                                        mob5 = (Mob)bdtridententity5;
                                                                        mob5.finalizeSpawn(serverlevel5, levelaccessor.getCurrentDifficultyAt(bdtridententity5.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                    }

                                                                    levelaccessor.addFreshEntity(bdtridententity5);
                                                                }

                                                                levelaccessor6 = levelaccessor;
                                                                if (levelaccessor6 instanceof ServerLevel) {
                                                                    serverlevel5 = (ServerLevel)levelaccessor6;
                                                                    bdtridententity5 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel5);
                                                                    bdtridententity5.moveTo(d0 + (double)Mth.nextInt(new Random(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), 3, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                    if (bdtridententity5 instanceof Mob) {
                                                                        mob5 = (Mob)bdtridententity5;
                                                                        mob5.finalizeSpawn(serverlevel5, levelaccessor.getCurrentDifficultyAt(bdtridententity5.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                    }

                                                                    levelaccessor.addFreshEntity(bdtridententity5);
                                                                }

                                                                new DelayedTask(5) {
                                                                    @Override
                                                                    public void run() {
                                                                        LevelAccessor levelaccessor7 = levelaccessor;
                                                                        ServerLevel serverlevel6;
                                                                        BlueDemonTridentParticleEntity bdtridententity6;
                                                                        Mob mob6;

                                                                        if (levelaccessor7 instanceof ServerLevel) {
                                                                            serverlevel6 = (ServerLevel)levelaccessor7;
                                                                            bdtridententity6 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel6);
                                                                            bdtridententity6.moveTo(d0 + (double)Mth.nextInt(new Random(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                            if (bdtridententity6 instanceof Mob) {
                                                                                mob6 = (Mob)bdtridententity6;
                                                                                mob6.finalizeSpawn(serverlevel6, levelaccessor.getCurrentDifficultyAt(bdtridententity6.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                            }

                                                                            levelaccessor.addFreshEntity(bdtridententity6);
                                                                        }

                                                                        levelaccessor7 = levelaccessor;
                                                                        if (levelaccessor7 instanceof ServerLevel) {
                                                                            serverlevel6 = (ServerLevel)levelaccessor7;
                                                                            bdtridententity6 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel6);
                                                                            bdtridententity6.moveTo(d0 + (double)Mth.nextInt(new Random(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                            if (bdtridententity6 instanceof Mob) {
                                                                                mob6 = (Mob)bdtridententity6;
                                                                                mob6.finalizeSpawn(serverlevel6, levelaccessor.getCurrentDifficultyAt(bdtridententity6.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                            }

                                                                            levelaccessor.addFreshEntity(bdtridententity6);
                                                                        }

                                                                        levelaccessor7 = levelaccessor;
                                                                        if (levelaccessor7 instanceof ServerLevel) {
                                                                            serverlevel6 = (ServerLevel)levelaccessor7;
                                                                            bdtridententity6 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel6);
                                                                            bdtridententity6.moveTo(d0 + (double)Mth.nextInt(new Random(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                            if (bdtridententity6 instanceof Mob) {
                                                                                mob6 = (Mob)bdtridententity6;
                                                                                mob6.finalizeSpawn(serverlevel6, levelaccessor.getCurrentDifficultyAt(bdtridententity6.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                            }

                                                                            levelaccessor.addFreshEntity(bdtridententity6);
                                                                        }

                                                                        levelaccessor7 = levelaccessor;
                                                                        if (levelaccessor7 instanceof ServerLevel) {
                                                                            serverlevel6 = (ServerLevel)levelaccessor7;
                                                                            bdtridententity6 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel6);
                                                                            bdtridententity6.moveTo(d0 + (double)Mth.nextInt(new Random(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                            if (bdtridententity6 instanceof Mob) {
                                                                                mob6 = (Mob)bdtridententity6;
                                                                                mob6.finalizeSpawn(serverlevel6, levelaccessor.getCurrentDifficultyAt(bdtridententity6.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                            }

                                                                            levelaccessor.addFreshEntity(bdtridententity6);
                                                                        }

                                                                        new DelayedTask(10) {
                                                                            @Override
                                                                            public void run() {
                                                                                LevelAccessor levelaccessor8 = levelaccessor;
                                                                                ServerLevel serverlevel7;
                                                                                BlueDemonTridentParticleEntity bdtridententity7;
                                                                                Mob mob7;

                                                                                if (levelaccessor8 instanceof ServerLevel) {
                                                                                    serverlevel7 = (ServerLevel)levelaccessor8;
                                                                                    bdtridententity7 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel7);
                                                                                    bdtridententity7.moveTo(d0 + (double)Mth.nextInt(new Random(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                                    if (bdtridententity7 instanceof Mob) {
                                                                                        mob7 = (Mob)bdtridententity7;
                                                                                        mob7.finalizeSpawn(serverlevel7, levelaccessor.getCurrentDifficultyAt(bdtridententity7.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                                    }

                                                                                    levelaccessor.addFreshEntity(bdtridententity7);
                                                                                }

                                                                                levelaccessor8 = levelaccessor;
                                                                                if (levelaccessor8 instanceof ServerLevel) {
                                                                                    serverlevel7 = (ServerLevel)levelaccessor8;
                                                                                    bdtridententity7 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel7);
                                                                                    bdtridententity7.moveTo(d0 + (double)Mth.nextInt(new Random(), -20, -20), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -20, -20), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                                    if (bdtridententity7 instanceof Mob) {
                                                                                        mob7 = (Mob)bdtridententity7;
                                                                                        mob7.finalizeSpawn(serverlevel7, levelaccessor.getCurrentDifficultyAt(bdtridententity7.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                                    }

                                                                                    levelaccessor.addFreshEntity(bdtridententity7);
                                                                                }

                                                                                levelaccessor8 = levelaccessor;
                                                                                if (levelaccessor8 instanceof ServerLevel) {
                                                                                    serverlevel7 = (ServerLevel)levelaccessor8;
                                                                                    bdtridententity7 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel7);
                                                                                    bdtridententity7.moveTo(d0 + (double)Mth.nextInt(new Random(), -20, -20), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -20, -20), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                                    if (bdtridententity7 instanceof Mob) {
                                                                                        mob7 = (Mob)bdtridententity7;
                                                                                        mob7.finalizeSpawn(serverlevel7, levelaccessor.getCurrentDifficultyAt(bdtridententity7.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                                    }

                                                                                    levelaccessor.addFreshEntity(bdtridententity7);
                                                                                }

                                                                                levelaccessor8 = levelaccessor;
                                                                                if (levelaccessor8 instanceof ServerLevel) {
                                                                                    serverlevel7 = (ServerLevel)levelaccessor8;
                                                                                    bdtridententity7 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel7);
                                                                                    bdtridententity7.moveTo(d0 + (double)Mth.nextInt(new Random(), -20, -20), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -20, -20), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                                    if (bdtridententity7 instanceof Mob) {
                                                                                        mob7 = (Mob)bdtridententity7;
                                                                                        mob7.finalizeSpawn(serverlevel7, levelaccessor.getCurrentDifficultyAt(bdtridententity7.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                                    }

                                                                                    levelaccessor.addFreshEntity(bdtridententity7);
                                                                                }

                                                                                levelaccessor8 = levelaccessor;
                                                                                if (levelaccessor8 instanceof ServerLevel) {
                                                                                    serverlevel7 = (ServerLevel)levelaccessor8;
                                                                                    bdtridententity7 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel7);
                                                                                    bdtridententity7.moveTo(d0 + (double)Mth.nextInt(new Random(), -20, -20), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -20, -20), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                                    if (bdtridententity7 instanceof Mob) {
                                                                                        mob7 = (Mob)bdtridententity7;
                                                                                        mob7.finalizeSpawn(serverlevel7, levelaccessor.getCurrentDifficultyAt(bdtridententity7.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                                    }

                                                                                    levelaccessor.addFreshEntity(bdtridententity7);
                                                                                }

                                                                                new DelayedTask(10) {
                                                                                    @Override
                                                                                    public void run() {
                                                                                        LevelAccessor levelaccessor9 = levelaccessor;
                                                                                        ServerLevel serverlevel8;
                                                                                        LightningBolt lightningbolt;

                                                                                        if (levelaccessor9 instanceof ServerLevel) {
                                                                                            serverlevel8 = (ServerLevel)levelaccessor9;
                                                                                            lightningbolt = (LightningBolt)EntityType.LIGHTNING_BOLT.create(serverlevel8);
                                                                                            lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(d0 + (double)Mth.nextInt(new Random(), -25, 25), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -25, 25))));
                                                                                            lightningbolt.setVisualOnly(true);
                                                                                            serverlevel8.addFreshEntity(lightningbolt);
                                                                                        }

                                                                                        levelaccessor9 = levelaccessor;
                                                                                        if (levelaccessor9 instanceof ServerLevel) {
                                                                                            serverlevel8 = (ServerLevel)levelaccessor9;
                                                                                            lightningbolt = (LightningBolt)EntityType.LIGHTNING_BOLT.create(serverlevel8);
                                                                                            lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(d0 + (double)Mth.nextInt(new Random(), -25, 25), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -25, 25))));
                                                                                            lightningbolt.setVisualOnly(true);
                                                                                            serverlevel8.addFreshEntity(lightningbolt);
                                                                                        }

                                                                                        levelaccessor9 = levelaccessor;
                                                                                        if (levelaccessor9 instanceof ServerLevel) {
                                                                                            serverlevel8 = (ServerLevel)levelaccessor9;
                                                                                            lightningbolt = (LightningBolt)EntityType.LIGHTNING_BOLT.create(serverlevel8);
                                                                                            lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(d0 + (double)Mth.nextInt(new Random(), -25, 25), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -25, 25))));
                                                                                            lightningbolt.setVisualOnly(true);
                                                                                            serverlevel8.addFreshEntity(lightningbolt);
                                                                                        }

                                                                                        levelaccessor9 = levelaccessor;
                                                                                        if (levelaccessor9 instanceof ServerLevel) {
                                                                                            serverlevel8 = (ServerLevel)levelaccessor9;
                                                                                            lightningbolt = (LightningBolt)EntityType.LIGHTNING_BOLT.create(serverlevel8);
                                                                                            lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(d0 + (double)Mth.nextInt(new Random(), -25, 25), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -25, 25))));
                                                                                            lightningbolt.setVisualOnly(true);
                                                                                            serverlevel8.addFreshEntity(lightningbolt);
                                                                                        }

                                                                                        levelaccessor9 = levelaccessor;
                                                                                        BlueDemonTridentParticleEntity bdtridententity8;
                                                                                        Mob mob8;

                                                                                        if (levelaccessor9 instanceof ServerLevel) {
                                                                                            serverlevel8 = (ServerLevel)levelaccessor9;
                                                                                            bdtridententity8 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel8);
                                                                                            bdtridententity8.moveTo(d0 + (double)Mth.nextInt(new Random(), -25, 25), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -25, 25), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                                            if (bdtridententity8 instanceof Mob) {
                                                                                                mob8 = (Mob)bdtridententity8;
                                                                                                mob8.finalizeSpawn(serverlevel8, levelaccessor.getCurrentDifficultyAt(bdtridententity8.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                                            }

                                                                                            levelaccessor.addFreshEntity(bdtridententity8);
                                                                                        }

                                                                                        levelaccessor9 = levelaccessor;
                                                                                        if (levelaccessor9 instanceof ServerLevel) {
                                                                                            serverlevel8 = (ServerLevel)levelaccessor9;
                                                                                            bdtridententity8 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel8);
                                                                                            bdtridententity8.moveTo(d0 + (double)Mth.nextInt(new Random(), -25, -25), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -25, -25), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                                            if (bdtridententity8 instanceof Mob) {
                                                                                                mob8 = (Mob)bdtridententity8;
                                                                                                mob8.finalizeSpawn(serverlevel8, levelaccessor.getCurrentDifficultyAt(bdtridententity8.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                                            }

                                                                                            levelaccessor.addFreshEntity(bdtridententity8);
                                                                                        }

                                                                                        levelaccessor9 = levelaccessor;
                                                                                        if (levelaccessor9 instanceof ServerLevel) {
                                                                                            serverlevel8 = (ServerLevel)levelaccessor9;
                                                                                            bdtridententity8 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel8);
                                                                                            bdtridententity8.moveTo(d0 + (double)Mth.nextInt(new Random(), -25, -25), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -25, -25), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                                            if (bdtridententity8 instanceof Mob) {
                                                                                                mob8 = (Mob)bdtridententity8;
                                                                                                mob8.finalizeSpawn(serverlevel8, levelaccessor.getCurrentDifficultyAt(bdtridententity8.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                                            }

                                                                                            levelaccessor.addFreshEntity(bdtridententity8);
                                                                                        }

                                                                                        levelaccessor9 = levelaccessor;
                                                                                        if (levelaccessor9 instanceof ServerLevel) {
                                                                                            serverlevel8 = (ServerLevel)levelaccessor9;
                                                                                            bdtridententity8 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel8);
                                                                                            bdtridententity8.moveTo(d0 + (double)Mth.nextInt(new Random(), -25, -25), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -25, -25), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                                            if (bdtridententity8 instanceof Mob) {
                                                                                                mob8 = (Mob)bdtridententity8;
                                                                                                mob8.finalizeSpawn(serverlevel8, levelaccessor.getCurrentDifficultyAt(bdtridententity8.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                                            }

                                                                                            levelaccessor.addFreshEntity(bdtridententity8);
                                                                                        }

                                                                                        levelaccessor9 = levelaccessor;
                                                                                        if (levelaccessor9 instanceof ServerLevel) {
                                                                                            serverlevel8 = (ServerLevel)levelaccessor9;
                                                                                            bdtridententity8 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel8);
                                                                                            bdtridententity8.moveTo(d0 + (double)Mth.nextInt(new Random(), -25, -25), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -25, -25), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                                            if (bdtridententity8 instanceof Mob) {
                                                                                                mob8 = (Mob)bdtridententity8;
                                                                                                mob8.finalizeSpawn(serverlevel8, levelaccessor.getCurrentDifficultyAt(bdtridententity8.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                                            }

                                                                                            levelaccessor.addFreshEntity(bdtridententity8);
                                                                                        }

                                                                                        Entity entity2 = entity;

                                                                                        if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                                                            entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect clear @e annoyingvillagers:block");
                                                                                        }

                                                                                        levelaccessor9 = levelaccessor;
                                                                                        Level level1;

                                                                                        if (levelaccessor9 instanceof Level) {
                                                                                            level1 = (Level)levelaccessor9;
                                                                                            if (!level1.isClientSide()) {
                                                                                                level1.explode((Entity)null, d0, d1 + 3.0D, d2, 60.0F, BlockInteraction.DESTROY);
                                                                                            }
                                                                                        }

                                                                                        levelaccessor9 = levelaccessor;
                                                                                        if (levelaccessor9 instanceof Level) {
                                                                                            level1 = (Level)levelaccessor9;
                                                                                            if (!level1.isClientSide()) {
                                                                                                level1.explode((Entity)null, d0, d1 + 1.0D, d2, 0.0F, BlockInteraction.NONE);
                                                                                            }
                                                                                        }

                                                                                        levelaccessor9 = levelaccessor;
                                                                                        if (levelaccessor9 instanceof Level) {
                                                                                            level1 = (Level)levelaccessor9;
                                                                                            if (!level1.isClientSide()) {
                                                                                                level1.explode((Entity)null, d0, d1 + 1.0D, d2, 0.0F, BlockInteraction.NONE);
                                                                                            }
                                                                                        }

                                                                                        if (!entity2.level.isClientSide()) {
                                                                                            entity2.discard();
                                                                                        }

                                                                                        new DelayedTask(1) {
                                                                                            @Override
                                                                                            public void run() {
                                                                                                LevelAccessor levelaccessor10 = levelaccessor;

                                                                                                if (levelaccessor10 instanceof ServerLevel) {
                                                                                                    ServerLevel serverlevel9 = (ServerLevel)levelaccessor10;
                                                                                                    BlueDemonEndStagingEntity bluedemonendentity = new BlueDemonEndStagingEntity((EntityType)AnnoyingVillagersModEntities.BLUE_DEMON_END_STAGING.get(), serverlevel9);

                                                                                                    bluedemonendentity.moveTo(d0, d1, d2, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                                                    if (bluedemonendentity instanceof Mob) {
                                                                                                        Mob mob9 = (Mob)bluedemonendentity;

                                                                                                        mob9.finalizeSpawn(serverlevel9, levelaccessor.getCurrentDifficultyAt(bluedemonendentity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                                                    }

                                                                                                    levelaccessor.addFreshEntity(bluedemonendentity);
                                                                                                }
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
                    };
                }
            };
        }
    }
}
