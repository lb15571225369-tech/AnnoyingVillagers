package com.pla.annoyingvillagers.tobe_removed;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.spawnhandler.BluedemonData;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import com.pla.annoyingvillagers.entity.BlueDemonTridentParticleEntity;
import com.pla.annoyingvillagers.entity.BlueDemonEndStagingEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

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

                if (!entity1.level().isClientSide() && entity1.getServer() != null) {
                    try {
                        entity1.getServer().getCommands().getDispatcher().execute("impactful @s shake 400 5 5", entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                        
                    }
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
            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute("effect give @s annoyingvillagers:captive 20000 0 true", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));

                    entity.getServer().getCommands().getDispatcher().execute( "item replace entity @s weapon.mainhand with annoyingvillagers:bluedemontrident", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));

                    entity.getServer().getCommands().getDispatcher().execute("item replace entity @s weapon.offhand with annoyingvillagers:bluedemontrident", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                    
                }
                LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                if (livingEntityPatch != null) {
                    livingEntityPatch.playAnimationSynchronized(AVAnimations.BLUE_DEMON_START_SKILL, 0.0F);
                }
            }

            new DelayedTask(25) {
                @Override
                public void run() {
                    LevelAccessor levelaccessor1 = levelaccessor;

                    if (levelaccessor1 instanceof Level) {
                        Level level = (Level)levelaccessor1;

                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "tridentfs_skill"))), SoundSource.NEUTRAL, 5.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "tridentfs_skill"))), SoundSource.NEUTRAL, 5.0F, 1.0F, false);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    ServerLevel serverlevel;
                    BlueDemonTridentParticleEntity bdtridententity;
                    Mob mob;

                    if (levelaccessor1 instanceof ServerLevel) {
                        serverlevel = (ServerLevel)levelaccessor1;
                        bdtridententity = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel);
                        bdtridententity.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -3, 3), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), -3, 3), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                        bdtridententity.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -3, 3), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), -3, 3), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<Blue Demon> Trident Carnival!!!"), false);
                            }

                            LevelAccessor levelaccessor2 = levelaccessor;

                            if (levelaccessor2 instanceof ServerLevel) {
                                ServerLevel serverlevel1 = (ServerLevel)levelaccessor2;
                                BlueDemonTridentParticleEntity bdtridententity1 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel1);

                                bdtridententity1.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                            bdtridententity2.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                            bdtridententity2.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                                    bdtridententity3.moveTo(d0 + (double)Mth.nextInt((RandomSource) RandomSource.create(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt((RandomSource) RandomSource.create(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                                    bdtridententity3.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                                            bdtridententity4.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                                            bdtridententity4.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), 3, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                                            bdtridententity4.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), 3, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                                                    bdtridententity5.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                                                    bdtridententity5.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), 3, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                                                            bdtridententity6.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                                                            bdtridententity6.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                                                            bdtridententity6.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                                                            bdtridententity6.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                                                                    bdtridententity7.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), -10, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                                                                    bdtridententity7.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -20, -20), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), -20, -20), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                                                                    bdtridententity7.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -20, -20), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), -20, -20), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                                                                    bdtridententity7.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -20, -20), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), -20, -20), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                                                                    bdtridententity7.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -20, -20), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), -20, -20), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                                                                            lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos((int) d0 + (int) Mth.nextInt(RandomSource.create(), -25, 25), (int) d1 - 1, (int) d2 + (int) Mth.nextInt(RandomSource.create(), -25, 25))));
                                                                                            lightningbolt.setVisualOnly(true);
                                                                                            serverlevel8.addFreshEntity(lightningbolt);
                                                                                        }

                                                                                        levelaccessor9 = levelaccessor;
                                                                                        if (levelaccessor9 instanceof ServerLevel) {
                                                                                            serverlevel8 = (ServerLevel)levelaccessor9;
                                                                                            lightningbolt = (LightningBolt)EntityType.LIGHTNING_BOLT.create(serverlevel8);
                                                                                            lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos((int) d0 + (int)Mth.nextInt(RandomSource.create(), -25, 25), (int) d1 - 1, (int) d2 + (int) Mth.nextInt(RandomSource.create(), -25, 25))));
                                                                                            lightningbolt.setVisualOnly(true);
                                                                                            serverlevel8.addFreshEntity(lightningbolt);
                                                                                        }

                                                                                        levelaccessor9 = levelaccessor;
                                                                                        if (levelaccessor9 instanceof ServerLevel) {
                                                                                            serverlevel8 = (ServerLevel)levelaccessor9;
                                                                                            lightningbolt = (LightningBolt)EntityType.LIGHTNING_BOLT.create(serverlevel8);
                                                                                            lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos((int) d0 + (int) Mth.nextInt(RandomSource.create(), -25, 25), (int) d1 - 1, (int) d2 + (int) Mth.nextInt(RandomSource.create(), -25, 25))));
                                                                                            lightningbolt.setVisualOnly(true);
                                                                                            serverlevel8.addFreshEntity(lightningbolt);
                                                                                        }

                                                                                        levelaccessor9 = levelaccessor;
                                                                                        if (levelaccessor9 instanceof ServerLevel) {
                                                                                            serverlevel8 = (ServerLevel)levelaccessor9;
                                                                                            lightningbolt = (LightningBolt)EntityType.LIGHTNING_BOLT.create(serverlevel8);
                                                                                            lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos((int) d0 + (int) Mth.nextInt(RandomSource.create(), -25, 25), (int) d1 - 1, (int) d2 + (int) Mth.nextInt(RandomSource.create(), -25, 25))));
                                                                                            lightningbolt.setVisualOnly(true);
                                                                                            serverlevel8.addFreshEntity(lightningbolt);
                                                                                        }

                                                                                        levelaccessor9 = levelaccessor;
                                                                                        BlueDemonTridentParticleEntity bdtridententity8;
                                                                                        Mob mob8;

                                                                                        if (levelaccessor9 instanceof ServerLevel) {
                                                                                            serverlevel8 = (ServerLevel)levelaccessor9;
                                                                                            bdtridententity8 = new BlueDemonTridentParticleEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel8);
                                                                                            bdtridententity8.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -25, 25), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), -25, 25), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                                                                            bdtridententity8.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -25, -25), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), -25, -25), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                                                                            bdtridententity8.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -25, -25), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), -25, -25), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                                                                            bdtridententity8.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -25, -25), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), -25, -25), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
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
                                                                                            bdtridententity8.moveTo(d0 + (double)Mth.nextInt(RandomSource.create(), -25, -25), d1 - 1.0D, d2 + (double)Mth.nextInt(RandomSource.create(), -25, -25), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                                            if (bdtridententity8 instanceof Mob) {
                                                                                                mob8 = (Mob)bdtridententity8;
                                                                                                mob8.finalizeSpawn(serverlevel8, levelaccessor.getCurrentDifficultyAt(bdtridententity8.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                                            }

                                                                                            levelaccessor.addFreshEntity(bdtridententity8);
                                                                                        }

                                                                                        Entity entity2 = entity;

                                                                                        levelaccessor9 = levelaccessor;
                                                                                        Level level1;

                                                                                        if (levelaccessor9 instanceof Level) {
                                                                                            level1 = (Level)levelaccessor9;
                                                                                            if (!level1.isClientSide()) {
                                                                                                level1.explode((Entity)null, d0, d1 + 3.0D, d2, 60.0F, Level.ExplosionInteraction.BLOCK);
                                                                                            }
                                                                                        }

                                                                                        levelaccessor9 = levelaccessor;
                                                                                        if (levelaccessor9 instanceof Level) {
                                                                                            level1 = (Level)levelaccessor9;
                                                                                            if (!level1.isClientSide()) {
                                                                                                level1.explode((Entity)null, d0, d1 + 1.0D, d2, 0.0F, Level.ExplosionInteraction.NONE);
                                                                                            }
                                                                                        }

                                                                                        levelaccessor9 = levelaccessor;
                                                                                        if (levelaccessor9 instanceof Level) {
                                                                                            level1 = (Level)levelaccessor9;
                                                                                            if (!level1.isClientSide()) {
                                                                                                level1.explode((Entity)null, d0, d1 + 1.0D, d2, 0.0F, Level.ExplosionInteraction.NONE);
                                                                                            }
                                                                                        }

                                                                                        if (!entity2.level().isClientSide()) {
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

                                                                                                    BluedemonData bluedemonData = BluedemonData.get(serverlevel9);
                                                                                                    bluedemonData.forceClaim(serverlevel9, bluedemonendentity.getUUID());
                                                                                                    bluedemonendentity.finalizeSpawn(serverlevel9, levelaccessor.getCurrentDifficultyAt(bluedemonendentity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);

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
