package com.pla.annoyingvillagers.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

import java.util.Objects;
import java.util.Random;


public class HerobrineUtil {
    public static boolean isHerobrineFaction(Entity e) {
        return e instanceof HerobrineMob
                || e instanceof HerobrineGregEntity
                || e instanceof LowHerobrineCloneEntity
                || e instanceof LowShadowHerobrineCloneEntity
                || e instanceof InfectedPlayerMobEntity
                || e instanceof InfectedTheMostMoistBurrit0Entity
                || e instanceof InfectedChrisEntity
                || e instanceof NullSwordEntity
                || e instanceof NullAxeEntity
                || e instanceof NullPickaxeEntity
                || e instanceof NullShovelEntity
                || e instanceof NullHoeEntity
                || e instanceof BlockProjectileEntity
                || e instanceof EliteHerobrineKnockedEntity;
    }

    private static void placeIfReplaceable(ServerLevel level, BlockPos pos, BlockState state) {
        if (level.getBlockState(pos).canBeReplaced()) {
            level.setBlockAndUpdate(pos, state);
        }
    }

    private static Basis basisFromEntity(Entity e) {
        Vec3 forward = e.getLookAngle().normalize();

        Vec3 worldUp = new Vec3(0.0, 1.0, 0.0);
        Vec3 right = forward.cross(worldUp);
        if (right.lengthSqr() < 1.0e-6) {
            right = new Vec3(1.0, 0.0, 0.0);
        } else {
            right = right.normalize();
        }
        Vec3 up = right.cross(forward).normalize();
        return new Basis(forward, right, up);
    }

    public static void transformHerobrine(LevelAccessor world, double x, double y, double z, Entity entity, Entity herobrineEntity) {
        if (entity == null) return;
        Random random = new Random();
        if (random.nextFloat() >= AnnoyingVillagersConfig.HEROBRINE_POSSESS_RATE.get().floatValue()) {
            return;
        }

        if (entity instanceof PlayerNpcEntity victim) {
            if (!(world instanceof ServerLevel serverLevel)) return;
            entity.getPersistentData().putBoolean("die_by_possess", true);
            Entity possessed;
            if (herobrineEntity instanceof HerobrineCloneEntity || herobrineEntity instanceof HerobrineChrisEntity || herobrineEntity instanceof NullEntity
                    || herobrineEntity instanceof NullSwordEntity || herobrineEntity instanceof NullAxeEntity
                    || herobrineEntity instanceof NullPickaxeEntity || herobrineEntity instanceof NullShovelEntity
                    || herobrineEntity instanceof NullHoeEntity || herobrineEntity instanceof GlaiveHerobrineEntity
                    || herobrineEntity instanceof AegisHerobrineEntity || herobrineEntity instanceof ReaperHerobrineEntity
                    || herobrineEntity instanceof SwordsmanHerobrineEntity || herobrineEntity instanceof SledgehammerHerobrineEntity) {
                possessed = new LowHerobrineCloneEntity(AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE.get(), serverLevel);
            } else {
                possessed = new LowShadowHerobrineCloneEntity(AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get(), serverLevel);
            }
            possessed.moveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
            victim.getCustomName();
            possessed.getPersistentData().putString("killed_name", victim.getCustomName().getString());

            if (!victim.getItemBySlot(EquipmentSlot.HEAD).getItem().equals(Items.PLAYER_HEAD)) {
                possessed.setItemSlot(EquipmentSlot.HEAD, victim.getItemBySlot(EquipmentSlot.HEAD).copy());
            }
            possessed.setItemSlot(EquipmentSlot.CHEST, victim.getItemBySlot(EquipmentSlot.CHEST).copy());
            possessed.setItemSlot(EquipmentSlot.LEGS, victim.getItemBySlot(EquipmentSlot.LEGS).copy());
            possessed.setItemSlot(EquipmentSlot.FEET, victim.getItemBySlot(EquipmentSlot.FEET).copy());
            possessed.setItemSlot(EquipmentSlot.MAINHAND, victim.getItemBySlot(EquipmentSlot.MAINHAND).copy());
            possessed.setItemSlot(EquipmentSlot.OFFHAND, victim.getItemBySlot(EquipmentSlot.OFFHAND).copy());
            Mob mob = (Mob) possessed;
            if (mob instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity) {
                lowHerobrineCloneEntity.setUsername(((PlayerMobEntity) entity).getUsername());
                lowHerobrineCloneEntity.setProfile(((PlayerMobEntity) entity).getProfile());
                if (herobrineEntity instanceof HerobrineMob herobrineMob) {
                    lowHerobrineCloneEntity.setPossessedByEntity(herobrineMob);
                    lowHerobrineCloneEntity.setPossessedByUuid(herobrineMob.getUUID());
                } else if (herobrineEntity instanceof NullSwordEntity nullSwordEntity) {
                    lowHerobrineCloneEntity.setPossessedByEntity(nullSwordEntity.getNullEntity());
                    lowHerobrineCloneEntity.setPossessedByUuid(nullSwordEntity.getNullUUID());
                } else if (herobrineEntity instanceof NullAxeEntity nullAxeEntity) {
                    lowHerobrineCloneEntity.setPossessedByEntity(nullAxeEntity.getNullEntity());
                    lowHerobrineCloneEntity.setPossessedByUuid(nullAxeEntity.getNullUUID());
                } else if (herobrineEntity instanceof NullPickaxeEntity nullPickaxeEntity) {
                    lowHerobrineCloneEntity.setPossessedByEntity(nullPickaxeEntity.getNullEntity());
                    lowHerobrineCloneEntity.setPossessedByUuid(nullPickaxeEntity.getNullUUID());
                } else if (herobrineEntity instanceof NullShovelEntity nullShovelEntity) {
                    lowHerobrineCloneEntity.setPossessedByEntity(nullShovelEntity.getNullEntity());
                    lowHerobrineCloneEntity.setPossessedByUuid(nullShovelEntity.getNullUUID());
                } else {
                    NullHoeEntity nullHoeEntity = (NullHoeEntity) herobrineEntity;
                    lowHerobrineCloneEntity.setPossessedByEntity(nullHoeEntity.getNullEntity());
                    lowHerobrineCloneEntity.setPossessedByUuid(nullHoeEntity.getNullUUID());
                }
            }
            if (mob instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity) {
                if (herobrineEntity instanceof HerobrineMob herobrineMob) {
                    lowShadowHerobrineCloneEntity.setPossessedByEntity(herobrineMob);
                    lowShadowHerobrineCloneEntity.setPossessedByUuid(herobrineMob.getUUID());
                }
            }
            mob.finalizeSpawn(serverLevel, world.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            serverLevel.addFreshEntity(possessed);
        }
    }

    public static void initialSpawn(LevelAccessor levelaccessor, final Entity entity, int recallTicks, MobSpawnType mobSpawnType) {
        int min = AnnoyingVillagersConfig.HEROBRINE_RECALL_MIN_TIME.get();
        int max = AnnoyingVillagersConfig.HEROBRINE_RECALL_MAX_TIME.get();
        int randomMin = Math.min(min, max);
        int randomMax = Math.max(min, max);

        if (entity != null) {
            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                String killedName = entity.getPersistentData().getString("killed_name");
                if (!killedName.isEmpty()) { // Low Herobrine Clone
                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal(killedName + " " + Component.translatable("subtitles.possessed_npc").getString()), false);
                } else {
                    if ((entity instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity && !lowHerobrineCloneEntity.isSummoned()) || (entity instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity && !lowShadowHerobrineCloneEntity.isSummoned())) {
                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("§5Herobrine§r " + Component.translatable("subtitles.possessed_random").getString()), false);
                    } else {
                        if (recallTicks == 0) {
                            recallTicks = (randomMin + new Random().nextInt(randomMax - randomMin + 1)) * 60 * 20;
                            if (entity instanceof HerobrineMob herobrineMob) {
                                herobrineMob.setRecallTicks(recallTicks);
                            }
                        }
                        if (mobSpawnType.equals(MobSpawnType.NATURAL) || mobSpawnType.equals(MobSpawnType.CHUNK_GENERATION)) { // For natural spawn
                            if (Math.random() <= 0.5D) { // Natural possessed
                                levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("§5Herobrine§r " + Component.translatable("subtitles.possessed_random").getString()), false);
                            } else { // Portal animation
                                if (entity instanceof HerobrineMob herobrineMob) {
                                    herobrineMob.setRenderPortal(true);
                                    HerobrinePortalUtil.spawnHerobrine(herobrineMob);
                                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal(herobrineMob.getChatName() + " " + Component.translatable("subtitles.herobrine_arrive").getString()), false);
                                } else if (entity instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity) {
                                    lowShadowHerobrineCloneEntity.setRenderPortal(true);
                                    HerobrinePortalUtil.spawnHerobrine(lowShadowHerobrineCloneEntity);
                                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("§5Netherite Herobrine§r " + Component.translatable("subtitles.herobrine_arrive").getString()), false);
                                }
                            }
                        } else {
                            if (entity instanceof HerobrineMob herobrineMob) {
                                if (mobSpawnType.equals(MobSpawnType.SPAWN_EGG) || mobSpawnType.equals(MobSpawnType.COMMAND)) {
                                    herobrineMob.setRenderPortal(true);
                                }
                                HerobrinePortalUtil.spawnHerobrine(herobrineMob);
                                levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal(herobrineMob.getChatName() + " " + Component.translatable("subtitles.herobrine_arrive").getString()), false);
                            } else if (entity instanceof LivingEntity livingEntity) {
                                // This logic is for #5 and #6 ground spawn
                                HerobrinePortalUtil.spawnHerobrine(livingEntity);
                            }
                        }
                    }
                }
            }

            if (entity instanceof HerobrineChrisEntity) {
                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + Component.translatable("entity.annoyingvillagers.herobrine_chris").getString() + "> " + Component.translatable("subtitles.herobrine_chris_spawn").getString()), false);
                }

                if (levelaccessor instanceof Level level) {

                    if (!level.isClientSide()) {
                        level.playSound(null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), AnnoyingVillagersModSounds.HEROBRINE_AREYOUTALKINGABOUTME.get(), SoundSource.BLOCKS, 5.0F, 1.0F);
                    } else {
                        level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), AnnoyingVillagersModSounds.HEROBRINE_AREYOUTALKINGABOUTME.get(), SoundSource.BLOCKS, 5.0F, 1.0F, false);
                    }
                }
            }

            if (entity instanceof ArmoredHerobrineEntity) {
                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + Component.translatable("entity.annoyingvillagers.armored_herobrine").getString() + "> " + Component.translatable("subtitles.armored_herobrine_spawn").getString()), false);
                }

                if (levelaccessor instanceof Level level) {

                    if (!level.isClientSide()) {
                        level.playSound(null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), AnnoyingVillagersModSounds.HEROBRINE_WHOEVERGETINMYWAY.get(), SoundSource.BLOCKS, 5.0F, 1.0F);
                    } else {
                        level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), AnnoyingVillagersModSounds.HEROBRINE_WHOEVERGETINMYWAY.get(), SoundSource.BLOCKS, 5.0F, 1.0F, false);
                    }
                }
            }

            TeamUtil.addOrJoinTeam(entity, "herobrine");
        }
    }

    public static void spawnEliteEffect(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null && levelaccessor instanceof ServerLevel serverLevel) {
            if (Math.random() <= 0.3D) {
                serverLevel.addParticle(AnnoyingVillagersModParticleTypes.PE.get(), d0, d1, d2,0.4, 1.1, 0.4);
                if (Math.random() <= 0.87D) {
                    serverLevel.addParticle(AnnoyingVillagersModParticleTypes.PE.get(), d0, d1, d2,0.45, 1.5, 0.3);
                    serverLevel.playSound(entity, entity.blockPosition(), AnnoyingVillagersModSounds.ELECTIFY.get(), SoundSource.NEUTRAL, (float) Mth.nextDouble(RandomSource.create(), 0.05D, 0.4D), (float) Mth.nextDouble(RandomSource.create(), 0.5D, 1.2D));
                }
            }

        }
    }

    private record Basis(Vec3 fwd, Vec3 right, Vec3 up) {}

    public static void spawnObsidianEyeLineStaggered(ServerLevel level, Entity entity, BlockState state, int tickGap) {
        if (level == null || entity == null) return;

        Basis b = basisFromEntity(entity);
        Vec3 eye = entity.getEyePosition(1.0F);

        BlockPos[] sequence = new BlockPos[1 + 6];
        sequence[0] = BlockPos.containing(eye.add(b.fwd().scale(1.0)).add(b.up().scale(-1.0)));
        for (int i = 1; i <= 6; i++) {
            sequence[i] = BlockPos.containing(eye.add(b.fwd().scale(i)));
        }

        for (int i = 0; i < sequence.length; i++) {
            final BlockPos pos = sequence[i];
            new DelayedTask(i * Math.max(1, tickGap)) {
                @Override public void run() {
                    placeIfReplaceable(level, pos, state);
                }
            };
        }
    }

    private static final class Pattern2D {
        final int w, h;
        final int[][] cells;
        Pattern2D(int w, int h, int[][] cells) { this.w = w; this.h = h; this.cells = cells; }
        int centerX() { return w / 2; } // integer center (works fine for w=2 as well)
    }

    private static final Pattern2D[] OBSIDIAN_PATTERNS = new Pattern2D[] {
            new Pattern2D(1, 3, new int[][] { {0,0},{0,1},{0,2} }),
            new Pattern2D(2, 3, new int[][] { {0,0},{0,1},{0,2},{1,2} }),
            new Pattern2D(2, 3, new int[][] { {1,0},{1,1},{1,2},{0,1} }),
            new Pattern2D(3, 3, new int[][] { {0,0},{1,0},{2,0},{1,1},{1,2} }),
            new Pattern2D(3, 3, new int[][] { {0,2},{1,2},{2,2},{1,1},{1,0} }),
            new Pattern2D(3, 3, new int[][] { {1,1},{1,2},{1,0},{0,1},{2,1} }),
            new Pattern2D(3, 4, new int[][] { {1,0},{1,1},{1,2},{1,3},{0,2},{2,2} }),
            new Pattern2D(2, 2, new int[][] { {0,0},{1,0},{0,1},{1,1} }),
            new Pattern2D(3, 3, new int[][] { {0,0},{0,1},{1,1},{2,1},{0,2},{1,2} }),
            new Pattern2D(3, 3, new int[][] { {0,0},{1,0},{1,1},{1,2},{2,2} }),
            new Pattern2D(3, 2, new int[][] { {0,0},{1,0},{2,0},{0,1} }),
    };

    private static boolean hasGroundWithin(ServerLevel level, Entity e, int maxDown) {
        Vec3 start = new Vec3(e.getX(), e.getBoundingBox().minY + 1.0E-3D, e.getZ());
        Vec3 end = start.add(0.0D, -maxDown, 0.0D);

        BlockHitResult hit = level.clip(new ClipContext(
                start, end,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                e
        ));
        return hit.getType() != HitResult.Type.MISS;
    }

    public static void spawnObsidianPatternAtBody(ServerLevel level, Entity entity, BlockState state) {
        if (level == null || entity == null) return;
        if (!hasGroundWithin(level, entity, 3)) return;

        int minY = level.getMinBuildHeight();
        int maxY = level.getMaxBuildHeight() - 1;

        BlockPos feet = BlockPos.containing(entity.getX(), entity.getBoundingBox().minY, entity.getZ());

        var rand = level.getRandom();
        Pattern2D pat = OBSIDIAN_PATTERNS[rand.nextInt(OBSIDIAN_PATTERNS.length)];

        Direction face = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
        boolean mirror = rand.nextBoolean();
        BlockPos origin = feet.relative(face);

        Direction side = mirror ? face.getCounterClockWise() : face.getClockWise();
        int cx = pat.centerX();

        for (int[] c : pat.cells) {
            int localX = c[0] - cx;
            int localY = c[1];

            int y = origin.getY() + localY;
            if (y < minY || y > maxY) continue;

            BlockPos p = origin.offset(
                    side.getStepX() * localX,
                    localY,
                    side.getStepZ() * localX
            );

            placeIfReplaceable(level, p, state);
        }
    }
}
