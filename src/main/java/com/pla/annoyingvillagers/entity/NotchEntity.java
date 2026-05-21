package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.clazz.BurstProtectEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.util.CommonGoals;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.List;
import java.util.UUID;

public class NotchEntity extends Monster implements BurstProtectEntity {

    private int state = 0;
    private float orbitAngle = 0.0F;
    private final BlockProjectileEntity[] orbitBlocks = new BlockProjectileEntity[5];
    private final UUID[] orbitBlockUUIDs = new UUID[5];
    private final NotchOrbitItemEntity[] orbitSwords = new NotchOrbitItemEntity[5];
    private final UUID[] orbitSwordUUIDs = new UUID[5];
    private int swordAttackCooldown = 0;
    private int blockAttackCooldown = 0;
    private int swordRespawnCooldown = 0;
    private int nextSwordAttackIndex = 0;

    // Phase 0 cooldowns
    private int passiveEffectCooldown = 0;

    // Phase 2 cooldowns
    private int healCooldown = 0;
    private int lightningCooldown = 0;

    protected float recentDamageTaken = 0.0F;
    protected int recentHitCounter = 0;

    private static final BlockState[] PHASE0_BLOCKS = {
            Blocks.GRASS_BLOCK.defaultBlockState(),
            Blocks.COBBLESTONE.defaultBlockState(),
            Blocks.OAK_LOG.defaultBlockState(),
            Blocks.DIRT.defaultBlockState(),
            Blocks.STONE.defaultBlockState()
    };

    private static final BlockState[] PHASE2_SHOOT_BLOCKS = {
            Blocks.GRASS_BLOCK.defaultBlockState(),
            Blocks.COBBLESTONE.defaultBlockState(),
            Blocks.OAK_LOG.defaultBlockState(),
            Blocks.DIRT.defaultBlockState(),
            Blocks.STONE.defaultBlockState(),
            Blocks.IRON_BLOCK.defaultBlockState(),
            Blocks.GOLD_BLOCK.defaultBlockState()
    };

    public NotchEntity(EntityType<? extends NotchEntity> type, Level level) {
        super(type, level);
        this.setCustomName(Component.literal("Notch"));
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setNoGravity(true);
        this.moveControl = new FlyingMoveControl(this, 20, true);
    }

    public NotchEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.NOTCH.get(), level);
    }

    public int getState() {
        return state;
    }

    @Override
    public float getRecentDamageTaken() {
        return recentDamageTaken;
    }

    @Override
    public void setRecentDamageTaken(float value) {
        recentDamageTaken = value;
    }

    @Override
    public int getRecentHitCounter() {
        return recentHitCounter;
    }

    @Override
    public void setRecentHitCounter(int value) {
        recentHitCounter = value;
    }

    @Override
    public float getBurstProtectCapRatio() {
        return 0.03F;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 500.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.4D)
                .add(Attributes.FLYING_SPEED, 0.4D)
                .add(Attributes.ATTACK_DAMAGE, 15.0D)
                .add(Attributes.FOLLOW_RANGE, 80.0D)
                .add(Attributes.ARMOR, 15.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 25.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .add(EpicFightAttributes.IMPACT.get(), 5.0D)
                .add(EpicFightAttributes.ARMOR_NEGATION.get(), 15.0D)
                .add(EpicFightAttributes.STUN_ARMOR.get(), 25.0D)
                .add(EpicFightAttributes.MAX_STRIKES.get(), 100.0D)
                .add(EpicFightAttributes.MAX_STAMINA.get(), 80.0D)
                .add(EpicFightAttributes.STAMINA_REGEN.get(), 2.0D);
    }

    @Override
    protected void registerGoals() {
        CommonGoals.registerGoalForHostileNpc(this);
    }

    @Override
    public void tick() {
        super.tick();
        tickBurstProtectionDecay(this);

        if (!this.level().isClientSide) {
            // Hover maintenance
            if (!this.onGround()) {
                double groundY = this.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, this.blockPosition()).getY();
                double hoverHeight = this.getY() - groundY;
                if (hoverHeight < 1.0) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0, 0.04, 0));
                } else if (hoverHeight > 1.8) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0, -0.02, 0));
                }
            } else {
                this.setDeltaMovement(this.getDeltaMovement().add(0, 0.1, 0));
            }

            // Stop movement in phase 1 only (phase 2 actively fights)
            if (state == 1) {
                this.getNavigation().stop();
                Vec3 dm = this.getDeltaMovement();
                this.setDeltaMovement(0, dm.y, 0);
            }

            // Update orbit angle
            orbitAngle += 0.05F;
            if (orbitAngle > (float)(Math.PI * 2.0)) {
                orbitAngle -= (float)(Math.PI * 2.0);
            }

            // Resolve orbit entity references from UUIDs if needed
            resolveOrbitReferences();

            // Update orbit positions
            updateOrbitPositions();

            // Tick cooldowns
            if (swordAttackCooldown > 0) swordAttackCooldown--;
            if (blockAttackCooldown > 0) blockAttackCooldown--;
            if (swordRespawnCooldown > 0) swordRespawnCooldown--;
            if (passiveEffectCooldown > 0) passiveEffectCooldown--;
            if (healCooldown > 0) healCooldown--;
            if (lightningCooldown > 0) lightningCooldown--;

            // Phase-specific logic
            if (state == 0) {
                tickPhaseNull();
            } else if (state == 1) {
                tickPhaseDivineSword();
            } else {
                tickPhaseAbsolute();
            }
        }

    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (!(this.level() instanceof ServerLevel)) {
            LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);
            if (patch == null) return;
            if (patch.getAnimator() == null) return;
            if (patch.getArmature() == null) return;
            if (Armatures.BIPED.get() == null || Armatures.BIPED.get().toolL == null) return;

            byte poseSampleCount = 3;
            float poseStep = 1.0F / (float)(poseSampleCount - 1);
            float poseProgress = 0.0F;

            float healthRatio = this.getHealth() / this.getMaxHealth();
            int clientState = healthRatio > 0.6F ? 0 : (healthRatio > 0.3F ? 1 : 2);

            SimpleParticleType particleType;
            SimpleParticleType secondaryParticle = null;
            int particleCount;
            int footParticleCount;

            if (clientState == 0) {
                particleType = AnnoyingVillagersModParticleTypes.NULL.get();
                particleCount = 2;
                footParticleCount = 14;
            } else if (clientState == 1) {
                particleType = AnnoyingVillagersModParticleTypes.LIGHT.get();
                secondaryParticle = AnnoyingVillagersModParticleTypes.SPARK.get();
                particleCount = 2;
                footParticleCount = 10;
            } else {
                particleType = AnnoyingVillagersModParticleTypes.ELECTRIC_SPARK.get();
                secondaryParticle = AnnoyingVillagersModParticleTypes.RED_SPARK.get();
                particleCount = 3;
                footParticleCount = 14;
            }

            for (int poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                Pose pose;
                try {
                    pose = patch.getAnimator().getPose(poseProgress);
                } catch (Throwable t) {
                    return;
                }
                if (pose == null) return;

                OpenMatrix4f toolLeftTransform = patch.getArmature()
                        .getBoundTransformFor(pose, Armatures.BIPED.get().toolL);
                if (toolLeftTransform == null) {
                    poseProgress += poseStep;
                    continue;
                }
                toolLeftTransform = new OpenMatrix4f(toolLeftTransform);
                toolLeftTransform.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(this.yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        toolLeftTransform,
                        toolLeftTransform
                );

                for (int i = 0; i < particleCount; ++i) {
                    this.level().addParticle(
                            particleType,
                            (double) toolLeftTransform.m30 + this.getX(),
                            (double) toolLeftTransform.m31 + this.getY(),
                            (double) toolLeftTransform.m32 + this.getZ(),
                            (this.getRandom().nextFloat() - 0.5F) * 0.15F,
                            (this.getRandom().nextFloat() - 0.5F) * 0.15F,
                            (this.getRandom().nextFloat() - 0.5F) * 0.15F
                    );
                }
                if (secondaryParticle != null) {
                    this.level().addParticle(
                            secondaryParticle,
                            (double) toolLeftTransform.m30 + this.getX(),
                            (double) toolLeftTransform.m31 + this.getY(),
                            (double) toolLeftTransform.m32 + this.getZ(),
                            0.0D, 0.0D, 0.0D
                    );
                }

                poseProgress += poseStep;
            }

            poseProgress = 0.0F;
            for (int poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                OpenMatrix4f jointTransform = patch.getArmature().getBoundTransformFor(
                        patch.getAnimator().getPose(poseProgress),
                        Armatures.BIPED.get().toolR
                );

                if (jointTransform == null) {
                    poseProgress += poseStep;
                    continue;
                }
                jointTransform = new OpenMatrix4f(jointTransform);
                jointTransform.translate(new Vec3f(0.0F, 0.0F, 1.8F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(this.yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        jointTransform,
                        jointTransform
                );
                jointTransform.translate(new Vec3f(0.0F, 0.0F, -(this.getRandom().nextFloat() * 4.0F)));

                this.level().addParticle(
                        particleType,
                        (double) jointTransform.m30 + this.getX(),
                        (double) jointTransform.m31 + this.getY(),
                        (double) jointTransform.m32 + this.getZ(),
                        (this.getRandom().nextFloat() - 0.5F) * 0.15F,
                        (this.getRandom().nextFloat() - 0.5F) * 0.15F,
                        (this.getRandom().nextFloat() - 0.5F) * 0.15F
                );
                if (secondaryParticle != null) {
                    this.level().addParticle(
                            secondaryParticle,
                            (double) jointTransform.m30 + this.getX(),
                            (double) jointTransform.m31 + this.getY(),
                            (double) jointTransform.m32 + this.getZ(),
                            0.0D, 0.0D, 0.0D
                    );
                }

                poseProgress += poseStep;
            }

            for (int i = 0; i < footParticleCount; ++i) {
                this.level().addParticle(
                        particleType,
                        this.getX(),
                        this.getY() + 0.03D,
                        this.getZ(),
                        (this.getRandom().nextFloat() - 0.5F) * 0.65F,
                        (this.getRandom().nextFloat() - 0.5F) * 0.05F,
                        (this.getRandom().nextFloat() - 0.5F) * 0.65F
                );
            }
        }
    }

    private void tickPhaseNull() {
        // Spawn orbit blocks if not present
        boolean needsSpawn = true;
        for (int i = 0; i < 5; i++) {
            if (orbitBlocks[i] != null && orbitBlocks[i].isAlive()) {
                needsSpawn = false;
                break;
            }
        }
        if (needsSpawn && orbitBlockUUIDs[0] == null) {
            spawnOrbitBlocks(PHASE0_BLOCKS);
        }

        // Passive effect every 200 ticks
        if (passiveEffectCooldown <= 0) {
            List<Player> players = this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(15.0D));
            for (Player player : players) {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 0));
                player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 60, 0));
            }
            passiveEffectCooldown = 200;
        }
    }

    private void tickPhaseDivineSword() {
        // Spawn orbit swords if not present
        boolean needsSpawn = true;
        for (int i = 0; i < 5; i++) {
            if (orbitSwords[i] != null && orbitSwords[i].isAlive()) {
                needsSpawn = false;
                break;
            }
        }
        if (needsSpawn && orbitSwordUUIDs[0] == null) {
            spawnOrbitSwords();
        }

        // Sword attack every 60 ticks
        if (swordAttackCooldown <= 0 && this.getTarget() != null) {
            shootSwordAtTarget();
            swordAttackCooldown = 60;
        }

        // Respawn missing swords after 40 ticks
        if (swordRespawnCooldown > 0) {
            swordRespawnCooldown--;
            if (swordRespawnCooldown <= 0) {
                for (int i = 0; i < 5; i++) {
                    if (orbitSwords[i] == null || !orbitSwords[i].isAlive()) {
                        respawnMissingSword(i);
                    }
                }
            }
        }
    }

    private void tickPhaseAbsolute() {
        // Spawn orbit command blocks if not present
        boolean needsSpawn = true;
        for (int i = 0; i < 5; i++) {
            if (orbitBlocks[i] != null && orbitBlocks[i].isAlive()) {
                needsSpawn = false;
                break;
            }
        }
        if (needsSpawn && orbitBlockUUIDs[0] == null) {
            BlockState[] commandBlocks = new BlockState[5];
            for (int i = 0; i < 5; i++) {
                commandBlocks[i] = Blocks.COMMAND_BLOCK.defaultBlockState();
            }
            spawnOrbitBlocks(commandBlocks);
            for (BlockProjectileEntity block : orbitBlocks) {
                if (block != null) {
                    block.setDamageOverride(15.0F);
                }
            }
        }

        // Apply wither + slowness to nearby players every 100 ticks
        if (this.tickCount % 100 == 0) {
            List<Player> players = this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(10.0D));
            for (Player player : players) {
                player.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 1));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 80, 0));
            }
        }

        // Block attack every 40 ticks
        if (blockAttackCooldown <= 0 && this.getTarget() != null) {
            shootBlockAtTarget();
            blockAttackCooldown = 40;
        }

        // Healing every 400 ticks
        if (healCooldown <= 0) {
            this.heal(this.getMaxHealth() * 0.03F);
            healCooldown = 400;
        }

        // Lightning every 150 ticks
        if (lightningCooldown <= 0 && this.getTarget() != null && this.level() instanceof ServerLevel serverLevel) {
            LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(serverLevel);
            if (bolt != null) {
                bolt.moveTo(this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ());
                serverLevel.addFreshEntity(bolt);
            }
            lightningCooldown = 150;
        }
    }

    // --- Orbit Management Methods ---

    private void spawnOrbitBlocks(BlockState[] blocks) {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;
        for (int i = 0; i < 5; i++) {
            BlockProjectileEntity block = new BlockProjectileEntity(
                    AnnoyingVillagersModEntities.BLOCK_PROJECTILE.get(), serverLevel);
            block.setCarriedBlock(blocks[i]);
            block.setNoGravity(true);
            block.setNotReadyForShoot(true);
            block.setOwnerUUID(this.getUUID());

            float angle = orbitAngle + (float)(i * Math.PI * 2.0 / 5.0);
            double x = this.getX() + Math.cos(angle) * 2.5;
            double y = this.getEyeY();
            double z = this.getZ() + Math.sin(angle) * 2.5;
            block.moveTo(x, y, z, 0, 0);

            serverLevel.addFreshEntity(block);
            orbitBlocks[i] = block;
            orbitBlockUUIDs[i] = block.getUUID();
        }
    }

    private void spawnOrbitSwords() {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;
        for (int i = 0; i < 5; i++) {
            NotchOrbitItemEntity sword = new NotchOrbitItemEntity(
                    AnnoyingVillagersModEntities.NOTCH_ORBIT_ITEM.get(), serverLevel);
            sword.setItem(new ItemStack(AnnoyingVillagersModItems.RUBY_SWORD.get()));
            sword.setNoGravity(true);
            sword.setNotReadyForShoot(true);
            sword.setOwnerUUID(this.getUUID());
            sword.setDamageOverride(12.0F);

            float angle = orbitAngle + (float)(i * Math.PI * 2.0 / 5.0);
            double x = this.getX() + Math.cos(angle) * 2.5;
            double y = this.getEyeY();
            double z = this.getZ() + Math.sin(angle) * 2.5;
            sword.moveTo(x, y, z, 0, 0);

            serverLevel.addFreshEntity(sword);
            orbitSwords[i] = sword;
            orbitSwordUUIDs[i] = sword.getUUID();
        }
    }

    private void discardOrbitBlocks() {
        for (int i = 0; i < 5; i++) {
            if (orbitBlocks[i] != null && orbitBlocks[i].isAlive()) {
                orbitBlocks[i].discard();
            }
            orbitBlocks[i] = null;
            orbitBlockUUIDs[i] = null;
        }
    }

    private void discardOrbitSwords() {
        for (int i = 0; i < 5; i++) {
            if (orbitSwords[i] != null && orbitSwords[i].isAlive()) {
                orbitSwords[i].discard();
            }
            orbitSwords[i] = null;
            orbitSwordUUIDs[i] = null;
        }
    }

    private void updateOrbitPositions() {
        for (int i = 0; i < 5; i++) {
            float angle = orbitAngle + (float)(i * Math.PI * 2.0 / 5.0);
            double x = this.getX() + Math.cos(angle) * 2.5;
            double y = this.getEyeY();
            double z = this.getZ() + Math.sin(angle) * 2.5;

            if (state == 0 || state == 2) {
                if (orbitBlocks[i] != null && orbitBlocks[i].isAlive()) {
                    orbitBlocks[i].moveTo(x, y, z, 0, 0);
                    orbitBlocks[i].setDeltaMovement(Vec3.ZERO);
                }
            }
            if (state == 1) {
                if (orbitSwords[i] != null && orbitSwords[i].isAlive()) {
                    orbitSwords[i].moveTo(x, y, z, 0, 0);
                    orbitSwords[i].setDeltaMovement(Vec3.ZERO);
                }
            }
        }
    }

    private void shootSwordAtTarget() {
        LivingEntity target = this.getTarget();
        if (target == null) return;

        // Find the next available sword using round-robin
        for (int attempt = 0; attempt < 5; attempt++) {
            int idx = nextSwordAttackIndex % 5;
            nextSwordAttackIndex++;
            if (orbitSwords[idx] != null && orbitSwords[idx].isAlive()) {
                NotchOrbitItemEntity sword = orbitSwords[idx];
                sword.setNotReadyForShoot(false);
                sword.setNoGravity(false);

                Vec3 targetPos = target.getEyePosition();
                Vec3 swordPos = sword.position();
                Vec3 direction = targetPos.subtract(swordPos).normalize().scale(2.0);
                sword.setDeltaMovement(direction);

                orbitSwords[idx] = null;
                orbitSwordUUIDs[idx] = null;

                // Schedule respawn
                swordRespawnCooldown = 40;
                break;
            }
        }
    }

    private void shootBlockAtTarget() {
        LivingEntity target = this.getTarget();
        if (target == null) return;
        if (!(this.level() instanceof ServerLevel serverLevel)) return;

        BlockState randomBlock = PHASE2_SHOOT_BLOCKS[this.getRandom().nextInt(PHASE2_SHOOT_BLOCKS.length)];

        BlockProjectileEntity projectile = new BlockProjectileEntity(
                AnnoyingVillagersModEntities.BLOCK_PROJECTILE.get(), serverLevel);
        projectile.setCarriedBlock(randomBlock);
        projectile.setOwnerUUID(this.getUUID());
        projectile.setDamageOverride(10.0F);

        double offsetX = (this.getRandom().nextDouble() - 0.5) * 4.0;
        double offsetY = this.getRandom().nextDouble() * 2.0;
        double offsetZ = (this.getRandom().nextDouble() - 0.5) * 4.0;
        double spawnX = this.getX() + offsetX;
        double spawnY = this.getEyeY() + offsetY;
        double spawnZ = this.getZ() + offsetZ;
        projectile.moveTo(spawnX, spawnY, spawnZ, 0, 0);

        Vec3 targetPos = target.getEyePosition();
        Vec3 spawnPos = new Vec3(spawnX, spawnY, spawnZ);
        Vec3 direction = targetPos.subtract(spawnPos).normalize().scale(2.5);
        projectile.setDeltaMovement(direction);
        projectile.setNoGravity(true);

        serverLevel.addFreshEntity(projectile);
    }

    private void respawnMissingSword(int index) {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;
        if (orbitSwords[index] != null && orbitSwords[index].isAlive()) return;

        NotchOrbitItemEntity sword = new NotchOrbitItemEntity(
                AnnoyingVillagersModEntities.NOTCH_ORBIT_ITEM.get(), serverLevel);
        sword.setItem(new ItemStack(AnnoyingVillagersModItems.RUBY_SWORD.get()));
        sword.setNoGravity(true);
        sword.setNotReadyForShoot(true);
        sword.setOwnerUUID(this.getUUID());
        sword.setDamageOverride(12.0F);

        float angle = orbitAngle + (float)(index * Math.PI * 2.0 / 5.0);
        double x = this.getX() + Math.cos(angle) * 2.5;
        double y = this.getEyeY();
        double z = this.getZ() + Math.sin(angle) * 2.5;
        sword.moveTo(x, y, z, 0, 0);

        serverLevel.addFreshEntity(sword);
        orbitSwords[index] = sword;
        orbitSwordUUIDs[index] = sword.getUUID();
    }

    private void resolveOrbitReferences() {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;

        if (state == 0 || state == 2) {
            for (int i = 0; i < 5; i++) {
                if ((orbitBlocks[i] == null || !orbitBlocks[i].isAlive()) && orbitBlockUUIDs[i] != null) {
                    Entity entity = serverLevel.getEntity(orbitBlockUUIDs[i]);
                    if (entity instanceof BlockProjectileEntity bpe) {
                        orbitBlocks[i] = bpe;
                    } else {
                        orbitBlockUUIDs[i] = null;
                    }
                }
            }
        }

        if (state == 1) {
            for (int i = 0; i < 5; i++) {
                if ((orbitSwords[i] == null || !orbitSwords[i].isAlive()) && orbitSwordUUIDs[i] != null) {
                    Entity entity = serverLevel.getEntity(orbitSwordUUIDs[i]);
                    if (entity instanceof NotchOrbitItemEntity noie) {
                        orbitSwords[i] = noie;
                    } else {
                        orbitSwordUUIDs[i] = null;
                    }
                }
            }
        }
    }

    // --- Phase Transitions ---

    private void transitionToPhase1() {
        this.state = 1;
        discardOrbitBlocks();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AnnoyingVillagersModItems.RUBY_SWORD.get()));
        spawnOrbitSwords();
        this.playSound(SoundEvents.WITHER_SPAWN, 2.0F, 1.0F);
    }

    private void transitionToPhase2() {
        this.state = 2;
        discardOrbitSwords();
        this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        BlockState[] commandBlocks = new BlockState[5];
        for (int i = 0; i < 5; i++) {
            commandBlocks[i] = Blocks.COMMAND_BLOCK.defaultBlockState();
        }
        spawnOrbitBlocks(commandBlocks);
        for (BlockProjectileEntity block : orbitBlocks) {
            if (block != null) {
                block.setDamageOverride(15.0F);
            }
        }
        this.playSound(SoundEvents.WITHER_SPAWN, 2.0F, 1.0F);
        this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0, false, false));
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(25.0D);
        this.getAttribute(Attributes.ARMOR).setBaseValue(25.0D);
    }

    // --- Standard Overrides ---

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() || this.isControlledByLocalInstance()) {
            if (this.isInWater()) {
                this.moveRelative(0.02F, travelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.8D));
            } else if (this.isInLava()) {
                this.moveRelative(0.02F, travelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
            } else {
                this.moveRelative(0.1F, travelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            }
        }
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        FlyingPathNavigation nav = new FlyingPathNavigation(this, level);
        nav.setCanOpenDoors(false);
        nav.setCanFloat(true);
        nav.setCanPassDoors(true);
        return nav;
    }

    @Override
    public boolean hurt(@NotNull DamageSource damageSource, float amount) {
        if (damageSource.is(DamageTypes.FALL) || damageSource.is(DamageTypes.CACTUS)
                || damageSource.is(DamageTypes.WITHER) || damageSource.is(DamageTypes.DROWN)
                || damageSource.is(DamageTypes.DRAGON_BREATH)) {
            return false;
        }

        amount = applyBurstProtection(this, damageSource, amount);

        if (amount <= 0.0F) return false;

        // Orbit blocking mechanic - like Null's weapon parry
        if (!this.level().isClientSide && damageSource.getEntity() != null) {
            float blockChance = switch (state) {
                case 0 -> 0.20F;
                case 1 -> 0.35F;
                default -> 0.50F;
            };
            if (this.getRandom().nextFloat() < blockChance) {
                if (this.level() instanceof ServerLevel serverLevel) {
                    this.playSound(EpicFightSounds.CLASH.get(), 1.0F, 1.0F);
                    EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument(
                            serverLevel, HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO,
                            this, damageSource.getEntity());
                }
                return false;
            }
        }

        boolean result = super.hurt(damageSource, amount);

        if (result && !this.level().isClientSide) {
            float healthRatio = this.getHealth() / this.getMaxHealth();
            if (state == 0 && healthRatio <= 0.6F) {
                transitionToPhase1();
            } else if (state == 1 && healthRatio <= 0.3F) {
                transitionToPhase2();
            }
        }

        return result;
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        discardOrbitBlocks();
        discardOrbitSwords();
        super.remove(reason);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean causeFallDamage(float f, float f1, @NotNull DamageSource damageSource) {
        return false;
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        this.spawnAtLocation(new ItemStack(net.minecraft.world.item.Items.NETHER_STAR));
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    // --- NBT Save/Load ---

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("NotchState", this.state);
        tag.putFloat("OrbitAngle", this.orbitAngle);
        tag.putInt("SwordAttackCooldown", this.swordAttackCooldown);
        tag.putInt("BlockAttackCooldown", this.blockAttackCooldown);
        tag.putInt("SwordRespawnCooldown", this.swordRespawnCooldown);
        tag.putInt("NextSwordAttackIndex", this.nextSwordAttackIndex);
        tag.putInt("PassiveEffectCooldown", this.passiveEffectCooldown);
        tag.putInt("HealCooldown", this.healCooldown);
        tag.putInt("LightningCooldown", this.lightningCooldown);

        for (int i = 0; i < 5; i++) {
            if (orbitBlockUUIDs[i] != null) {
                tag.putUUID("OrbitBlock" + i, orbitBlockUUIDs[i]);
            }
            if (orbitSwordUUIDs[i] != null) {
                tag.putUUID("OrbitSword" + i, orbitSwordUUIDs[i]);
            }
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.state = tag.getInt("NotchState");
        this.orbitAngle = tag.getFloat("OrbitAngle");
        this.swordAttackCooldown = tag.getInt("SwordAttackCooldown");
        this.blockAttackCooldown = tag.getInt("BlockAttackCooldown");
        this.swordRespawnCooldown = tag.getInt("SwordRespawnCooldown");
        this.nextSwordAttackIndex = tag.getInt("NextSwordAttackIndex");
        this.passiveEffectCooldown = tag.getInt("PassiveEffectCooldown");
        this.healCooldown = tag.getInt("HealCooldown");
        this.lightningCooldown = tag.getInt("LightningCooldown");

        for (int i = 0; i < 5; i++) {
            if (tag.hasUUID("OrbitBlock" + i)) {
                orbitBlockUUIDs[i] = tag.getUUID("OrbitBlock" + i);
            }
            if (tag.hasUUID("OrbitSword" + i)) {
                orbitSwordUUIDs[i] = tag.getUUID("OrbitSword" + i);
            }
        }

        // Re-apply mainhand based on state
        if (this.state == 1) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AnnoyingVillagersModItems.RUBY_SWORD.get()));
        } else {
            this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
    }
}
