package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.procedures.HerobrineWeaponEffectProcedure;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;


public class ReaperHerobrineEntity extends HerobrineMob {
    private EnderDragon enderDragon;
    private UUID enderDragonUUID;
    private boolean spawnEnderDragon = false;
    private int breathCooldown = 0;
    private int dragonSummonCooldown = 3600;

    public ReaperHerobrineEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.REAPER_HEROBRINE.get(), level);
    }

    public ReaperHerobrineEntity(EntityType<ReaperHerobrineEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.9F);
        this.xpReward = 300;
        this.setNoAi(false);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AnnoyingVillagersModItems.ENDER_SLAYER_SCYTHE.get()));
        this.setChatName(this.getDisplayName().getString());
    }

    public int getCooldownTicks() {
        return this.getPersistentData().getInt("DragonCooldown");
    }

    public void setCooldownTicks(int ticks) {
        this.getPersistentData().putInt("DragonCooldown", ticks);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (enderDragonUUID != null) {
            tag.putUUID("EnderDragonUUID", enderDragonUUID);
        }
        tag.putBoolean("SpawnEnderDragon", spawnEnderDragon);
        tag.putInt("DragonSummonCooldown", dragonSummonCooldown);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("EnderDragonUUID")) {
            enderDragonUUID = tag.getUUID("EnderDragonUUID");
        }
        spawnEnderDragon = tag.getBoolean("SpawnEnderDragon");
        dragonSummonCooldown = tag.contains("DragonSummonCooldown") ? tag.getInt("DragonSummonCooldown") : dragonSummonCooldown;
    }

    @Override
    public double getMyRidingOffset() {
        return -2.5D;
    }

    private void spawnEnderDragon() {
        if (this.level() instanceof ServerLevel serverLevel) {
            EnderDragon dragon = new EnderDragon(EntityType.ENDER_DRAGON, serverLevel);
            dragon.moveTo(this.getX(), this.getY() + 20.0D, this.getZ(), new Random().nextFloat() * 360.0F, 0.0F);
            dragon.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(dragon.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);

            dragon.setFightOrigin(BlockPos.containing(this.getX(), this.getY(), this.getZ()));
            dragon.getPhaseManager().setPhase(EnderDragonPhase.HOVERING);

            dragon.addTag("av_dragon");
            dragon.getPersistentData().putUUID("herobrine_uuid", this.getUUID());

            serverLevel.addFreshEntity(dragon);
            TeamUtil.addOrJoinTeam(dragon, "herobrine");

            this.enderDragonUUID = dragon.getUUID();
            this.enderDragon = dragon;

            if (this.level().getServer() != null) {
                Objects.requireNonNull(this.level().getServer()).getPlayerList().broadcastSystemMessage(Component.literal("<" + this.getChatName() + "> " +
                        Component.translatable("subtitles.herobrine_summon").getString()), false);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (!spawnEnderDragon) {
                this.spawnEnderDragon = true;
                spawnEnderDragon();
            }
            if (enderDragon == null && enderDragonUUID == null) {
                if (dragonSummonCooldown <=0) {
                    spawnEnderDragon = false;
                } else {
                    dragonSummonCooldown--;
                }
            }
            if (enderDragon == null && enderDragonUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(enderDragonUUID);
                if (entity instanceof EnderDragon dragon) {
                    enderDragon = dragon;
                } else {
                    enderDragon = null;
                }
            }
            if (enderDragon != null && enderDragon.getHealth() <= 50) {
                enderDragon.discard();
                enderDragon = null;
                enderDragonUUID = null;
                if (this.level().getServer() != null) {
                    Objects.requireNonNull(this.level().getServer()).getPlayerList().broadcastSystemMessage(Component.literal("<" + this.getChatName() + ">  " +
                            Component.translatable("subtitles.reaper_herobrine_return_dragon").getString()), false);
                }
                dragonSummonCooldown = 3600;
            }
            if (enderDragon != null && enderDragon.isAlive()) {
                enderDragon.setFightOrigin(BlockPos.containing(this.getX(), this.getY(), this.getZ()));
                enderDragon.getPhaseManager().setPhase(EnderDragonPhase.HOVERING);
                enderDragon.setDragonFight(null);
                LivingEntity target = this.getTarget();
                if (target != null && target.isAlive() && this.getPersistentData().getBoolean("SecondForm")) {
                    if (breathCooldown <= 0) {
                        shootThunderBreathAtTarget(target);
                        breathCooldown = 60 + this.getRandom().nextInt(20);
                    }
                }
            }
            if (this.getHealth() <= (float) 2 /3 * this.getMaxHealth() && enderDragon != null && !this.isPassenger()) {
                enderDragon.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                this.startRiding(enderDragon);
            }

            if (enderDragon != null) {
                LivingEntity target = this.getTarget();
                if (target != null && target.isAlive()) enderDragon.setTarget(target);
            }

            if (breathCooldown > 0) breathCooldown--;
            if (this.tickCount % 20 == 0) {
                if (this.getState() > 0) {
                    HerobrineWeaponEffectProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
                }
            }
        }
    }

    private void shootThunderBreathAtTarget(LivingEntity target) {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;
        EnderDragonPart head = enderDragon.head;
        Vec3 headPos = new Vec3(head.getX(), head.getY(), head.getZ());
        DragonBeamEntity beam = new DragonBeamEntity(
                AnnoyingVillagersModEntities.DRAGON_BEAM.get(),
                serverLevel,
                enderDragon,
                target,
                headPos.x, headPos.y, headPos.z,
                100, 2);
        serverLevel.addFreshEntity(beam);
    }

    public boolean hurt(@NotNull DamageSource damagesource, float f) {
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.WITHER)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.WITHER_SKULL)) return false;
        if (damagesource.is(DamageTypes.DRAGON_BREATH)) return false;
        if (damagesource.getDirectEntity() instanceof AbstractArrow) return false;
        AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG] took damage from {}", damagesource);
        return super.hurt(damagesource, f);
    }

    @Override
    public void remove(@NotNull RemovalReason pReason) {
        if (this.enderDragon != null) {
            this.enderDragon.discard();
        }
        super.remove(pReason);
    }

    public void die(@NotNull DamageSource damageSource) {
        super.die(damageSource);
        if (this.level() instanceof ServerLevel serverLevel) {
            EliteHerobrineKnockedEntity eliteHerobrineKnockedEntity = new EliteHerobrineKnockedEntity(AnnoyingVillagersModEntities.ELITE_HEROBRINE_KNOCKED.get(), serverLevel);

            eliteHerobrineKnockedEntity.moveTo(this.getX(), this.getY(), this.getZ(), serverLevel.getRandom().nextFloat() * 360.0F, 0.0F);
            eliteHerobrineKnockedEntity.getPersistentData().putString("FromElite", "EnderSlayerScythe");
            eliteHerobrineKnockedEntity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(eliteHerobrineKnockedEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
            this.remove(RemovalReason.KILLED);
            serverLevel.addFreshEntity(eliteHerobrineKnockedEntity);

            if (this.getGregUUID() != null) {
                Entity entity = serverLevel.getEntity(this.getGregUUID());
                if (entity instanceof HerobrineGregEntity herobrineGregEntity && entity.isAlive()) {
                    herobrineGregEntity.requestProtect(eliteHerobrineKnockedEntity.getUUID(), eliteHerobrineKnockedEntity);
                }
            }
        }
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.45D);
        builder = builder.add(Attributes.MAX_HEALTH, 250.0D);
        builder = builder.add(Attributes.ARMOR, 50.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 4.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
        return builder;
    }
}
