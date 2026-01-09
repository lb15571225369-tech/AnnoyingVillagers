package com.pla.annoyingvillagers.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.block.CryingObsidianSpikeBlock;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.procedures.HerobrineWeaponEffectProcedure;
import com.pla.annoyingvillagers.skill.ObsidianSledgeHammerSkill;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import reascer.wom.world.entity.mob.EnderHand;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.StunType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ObsidianSledgehammerHitEntity extends Entity {
    private static final EntityDataAccessor<Boolean> ACTIVATE =
            SynchedEntityData.defineId(ObsidianSledgehammerHitEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> DAMAGE =
            SynchedEntityData.defineId(ObsidianSledgehammerHitEntity.class, EntityDataSerializers.FLOAT);

    private static final float MAX_PROGRESS = 10.0F;
    private static final float RISE_SPEED = 3.0F;

    private int warmupDelayTicks;
    private boolean sentSpikeEvent;
    private int lifeTicks = 34;
    private boolean clientSideAttackStarted;

    private LivingEntity caster;
    private UUID casterUuid;

    public float activateProgress;
    public float prevActivateProgress;

    public ObsidianSledgehammerHitEntity(EntityType<? extends ObsidianSledgehammerHitEntity> type, Level level) {
        super(type, level);
    }

    public ObsidianSledgehammerHitEntity(Level level, double x, double y, double z, float rotation, int delay, float damage, LivingEntity caster) {
        this(AnnoyingVillagersModEntities.OBSIDIAN_SLEDGEHAMMER_HIT.get(), level);
        this.warmupDelayTicks = delay;
        this.setCaster(caster);
        this.setDamage(damage);
        this.setYRot(rotation * (180F / (float) Math.PI));
        this.setPos(x, y, z);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(ACTIVATE, false);
        this.entityData.define(DAMAGE, 0.0F);
    }

    public float getDamage() {
        return this.entityData.get(DAMAGE);
    }

    public void setDamage(float damage) {
        this.entityData.set(DAMAGE, damage);
    }

    public void setCaster(@Nullable LivingEntity caster) {
        this.caster = caster;
        this.casterUuid = caster == null ? null : caster.getUUID();
    }

    @Nullable
    public LivingEntity getCaster() {
        if (this.caster == null && this.casterUuid != null && this.level() instanceof ServerLevel serverLevel) {
            Entity entity = serverLevel.getEntity(this.casterUuid);
            if (entity instanceof LivingEntity living) {
                this.caster = living;
            }
        }
        return this.caster;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.warmupDelayTicks = tag.getInt("Warmup");
        if (tag.hasUUID("Owner")) {
            this.casterUuid = tag.getUUID("Owner");
        }
        this.setDamage(tag.getFloat("damage"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("Warmup", this.warmupDelayTicks);
        if (this.casterUuid != null) {
            tag.putUUID("Owner", this.casterUuid);
        }
        tag.putFloat("damage", this.getDamage());
    }

    private void tryPlaceCryingSpikeOnVictim() {
        if (!(this.level() instanceof ServerLevel)) return;

        AABB box = this.getBoundingBox().inflate(0.2D, 0.0D, 0.2D);
        LivingEntity caster = this.getCaster();

        List<LivingEntity> victims = this.level().getEntitiesOfClass(
                LivingEntity.class,
                box,
                e -> e.isAlive() && e != caster
        );

        if (victims.isEmpty()) return;

        BlockPos base = BlockPos.containing(this.getX(), this.getY(), this.getZ());
        BlockPos placePos = base;

        if (!this.level().isEmptyBlock(placePos) && this.level().isEmptyBlock(base.above())) {
            placePos = base.above();
        }

        boolean fromPlayer = caster instanceof Player;
        BlockState state = AnnoyingVillagersModBlocks.CRYING_OBSIDIAN_SPIKE_BLOCK.get()
                .defaultBlockState()
                .setValue(CryingObsidianSpikeBlock.FROM_PLAYER, fromPlayer);

        if (this.level().setBlock(placePos, state, 3) && fromPlayer && caster instanceof Player player) {
            state.getBlock().setPlacedBy(this.level(), placePos, state, player, ItemStack.EMPTY);
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.prevActivateProgress = this.activateProgress;

        if (this.isActivate() && this.activateProgress > 0.0F) {
            this.activateProgress -= 1.0F;
        }

        if (this.lifeTicks == 20) {
            HerobrineWeaponEffectProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
        }

        if (this.level().isClientSide) {
            if (this.clientSideAttackStarted) {
                --this.lifeTicks;
                if (!this.isActivate() && this.activateProgress < MAX_PROGRESS) {
                    this.activateProgress = Math.min(MAX_PROGRESS, this.activateProgress + RISE_SPEED);
                }
            }
            return;
        }

        LivingEntity caster = this.getCaster();

        if (--this.warmupDelayTicks < 0) {
            if (this.warmupDelayTicks == -10 && this.isActivate()) {
                this.setActivate(false);
            }

            if (this.warmupDelayTicks < -10 && this.warmupDelayTicks > -30) {
                AABB box = this.getBoundingBox().inflate(0.2D, 0.0D, 0.2D);
                for (LivingEntity target : this.level().getEntitiesOfClass(LivingEntity.class, box)) {
                    if (!target.isAlive() || target instanceof EnderHand) continue;

                    this.damage(target);

                    if (this.tickCount % 20 == 0
                            && this.level() instanceof ServerLevel serverLevel
                            && caster != null
                            && target != caster
                            && !target.isAlliedTo(caster)) {
                        EnderHand enderHand = new EnderHand(serverLevel, new Vec3(this.getX(), this.getY(), this.getZ()), caster, target);
                        serverLevel.addFreshEntity(enderHand);
                        increaseSkillPoint(caster, 2.0F);
                    }
                }
            }

            if (!this.sentSpikeEvent) {
                this.level().broadcastEntityEvent(this, (byte) 4);
                this.sentSpikeEvent = true;
            }

            if (--this.lifeTicks < 0) {
                tryPlaceCryingSpikeOnVictim();
                this.discard();
            }
        }
    }

    public void increaseSkillPoint(Entity entity, float value) {
        if (!(entity instanceof Player player)) return;

        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
        if (!(playerPatch instanceof ServerPlayerPatch serverPlayerPatch)) return;

        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.OBSIDIAN_SLEDGEHAMMER);
        if (skillContainer == null) return;

        ObsidianSledgeHammerSkill skill = (ObsidianSledgeHammerSkill) skillContainer.getSkill();

        float currentResource = skillContainer.getResource();
        float neededResource = skillContainer.getNeededResource();
        float addResource = Math.min(value, neededResource);

        skill.setConsumptionSynchronize(skillContainer, currentResource + addResource);
    }

    public boolean isActivate() {
        return this.entityData.get(ACTIVATE);
    }

    public void setActivate(boolean activate) {
        this.entityData.set(ACTIVATE, activate);
    }

    private void damage(LivingEntity hitEntity) {
        LivingEntity caster = this.getCaster();

        if (!hitEntity.isAlive() || hitEntity.isInvulnerable() || hitEntity instanceof EnderHand) return;
        if (caster != null && hitEntity == caster) return;

        hitEntity.setDeltaMovement(Vec3.ZERO);

        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    EpicFightParticles.HIT_BLUNT.get(),
                    this.getX(), this.getY() + 1.5, this.getZ() + 0.8,
                    1,
                    0.1, 0.1, 0.1,
                    1
            );
        }

        if (caster == null) {
            hitEntity.hurt(this.level().damageSources().magic(), this.getDamage());
        } else {
            if (caster.isAlliedTo(hitEntity)) return;
            hitEntity.hurt(this.level().damageSources().indirectMagic(this, caster), this.getDamage());
        }

        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(hitEntity, LivingEntityPatch.class);
        if (patch != null) {
            patch.applyStun(StunType.LONG, 40.0F);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 4) {
            this.clientSideAttackStarted = true;
            if (!this.isSilent()) {
                this.level().playLocalSound(
                        this.getX(), this.getY(), this.getZ(),
                        AnnoyingVillagersModSounds.OB_PLACE.get(),
                        this.getSoundSource(),
                        0.5F,
                        this.random.nextFloat() * 0.2F + 0.85F,
                        false
                );
            }
        }
    }

    @Override
    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
