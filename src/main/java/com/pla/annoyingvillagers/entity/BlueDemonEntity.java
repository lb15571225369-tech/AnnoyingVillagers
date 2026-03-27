package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.BlueDemonChestplateItem;
import com.pla.annoyingvillagers.item.BlueDemonTridentItem;
import com.pla.annoyingvillagers.spawnhandler.BluedemonData;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.util.CommonGoals;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import net.minecraft.util.RandomSource;
import net.shelmarow.combat_evolution.effect.CEMobEffects;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

public class BlueDemonEntity extends Monster {
    @Nullable
    private BbqEntity bbqSauce;
    @Nullable
    private UUID bbqSauceUUID;
    private int bbqSauceResolveCooldown;
    private int bbqSauceOrderCooldown;
    private int bbqSauceHeadAttackCooldown;
    private int bbqSauceModeCooldown;

    private int healingTick = 0;
    private int healingCooldown = 0;
    private int voiceCooldown = 0;
    private int stunEscapeCooldown = 0;
    private Entity blockDamage = null;
    private int swapWeaponCooldown;
    private int efnGuardHitState = 0;
    private int efnGuardHitCooldown = 0;
    private int state = 0;

    public int getEfnGuardHitState() {
        return efnGuardHitState;
    }

    public void postPlayEfnGuardHit() {
        if (efnGuardHitState == 2) {
            efnGuardHitState = 0;
        } else {
            efnGuardHitState = efnGuardHitState + 1;
        }
        efnGuardHitCooldown = 100;
    }

    public int getStunEscapeCooldown() {
        return stunEscapeCooldown;
    }

    public void setStunEscapeCooldown(int stunEscapeCooldown) {
        this.stunEscapeCooldown = stunEscapeCooldown;
    }

    public void setVoiceCooldown() {
        this.voiceCooldown = new Random().nextInt(60, 200);
    }

    public int getVoiceCooldown() {
        return voiceCooldown;
    }

    public void setBlockDamage(Entity blockDamage) {
        this.blockDamage = blockDamage;
    }

    public Entity getBlockDamage() {
        return blockDamage;
    }

    public int getSwapWeaponCooldown() {
        return swapWeaponCooldown;
    }

    public void setSwapWeaponCooldown(int swapWeaponCooldown) {
        this.swapWeaponCooldown = swapWeaponCooldown;
    }

    public int getHealingTick() {
        return healingTick;
    }

    public void setHealingTick(int healingTick) {
        this.healingTick = healingTick;
    }

    public void setHealingCooldown() {
        this.healingCooldown = random.nextInt(900, 1500);
    }

    public int getHealingCooldown() {
        return healingCooldown;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Nullable
    public LivingEntityPatch<?> getLivingEntityPatch() {
        return EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);
    }

    public BlueDemonEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.BLUE_DEMON.get(), level);
    }

    public BlueDemonEntity(EntityType<? extends BlueDemonEntity> type, Level level) {
        super(type, level);
        this.setMaxUpStep(3.0F);
        this.xpReward = 0;
        this.setNoAi(false);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AnnoyingVillagersModItems.BLUE_DEMON_TRIDENT.get()));
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(AnnoyingVillagersModItems.BLUE_DEMON_TRIDENT.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(AnnoyingVillagersModItems.BLUE_DEMON_CHESTPLATE.get()));
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        this.setDropChance(EquipmentSlot.CHEST, 0.0F);
        this.setDropChance(EquipmentSlot.OFFHAND, 0.0F);
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource pDamageSource) {
        return this.isInWater() ? SoundEvents.DROWNED_HURT_WATER : SoundEvents.DROWNED_HURT;
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return this.isInWater() ? SoundEvents.DROWNED_DEATH_WATER : SoundEvents.DROWNED_DEATH;
    }

    @Override
    protected @NotNull SoundEvent getSwimSound() {
        return SoundEvents.DROWNED_SWIM;
    }

    @Override
    protected void registerGoals() {
        CommonGoals.registerGoalForBlueDemonNpc(this);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect.getEffect() == MobEffects.POISON) {
            return false;
        }
        return super.canBeAffected(effect);
    }

    public boolean hurt(DamageSource damagesource, float f) {
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.LIGHTNING_BOLT)) return false;
        if (damagesource.is(DamageTypes.WITHER)) return false;
        if (damagesource.is(DamageTypes.TRIDENT)) return false;
        if (damagesource.is(DamageTypes.WITHER_SKULL)) return false;
        if (damagesource.getDirectEntity() instanceof ThrownPoisonEggEntity) return false;
        return super.hurt(damagesource, f);
    }

    @Nullable
    public BbqEntity getBbqEntity() {
        if (this.bbqSauce != null && this.bbqSauce.isAlive()) {
            return this.bbqSauce;
        }

        if (this.bbqSauceResolveCooldown > 0) {
            this.bbqSauceResolveCooldown--;
            return null;
        }

        this.bbqSauceResolveCooldown = 20;

        if (!this.level().isClientSide && this.bbqSauceUUID != null) {
            Entity entity = ((ServerLevel) this.level()).getEntity(this.bbqSauceUUID);
            if (entity instanceof BbqEntity bbqEntity && bbqEntity.isAlive()) {
                this.bbqSauce = bbqEntity;
                return bbqEntity;
            }
        }

        return null;
    }

    public void setBbqEntity(@Nullable BbqEntity bbqEntity) {
        this.bbqSauce = bbqEntity;
        this.bbqSauceUUID = bbqEntity == null ? null : bbqEntity.getUUID();
    }

    private void ensureBbqExists(BbqEntity bbqEntity, UUID uuid, Component name) {
        if (bbqEntity != null) {
            return;
        }

        if (uuid != null) {
            return;
        }

        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        bbqEntity = new BbqEntity(AnnoyingVillagersModEntities.BBQ.get(), serverLevel);

        double angle = this.random.nextDouble() * (Math.PI * 2.0D);
        double radius = 2.5D;
        double spawnX = this.getX() + Math.cos(angle) * radius;
        double spawnZ = this.getZ() + Math.sin(angle) * radius;
        int spawnY = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BlockPos.containing(spawnX, this.getY(), spawnZ)).getY();

        bbqEntity.moveTo(spawnX, spawnY, spawnZ, this.random.nextFloat() * 360.0F, 0.0F);
        bbqEntity.setLeader(this);
        bbqEntity.setCustomName(name);
        bbqEntity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(bbqEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
        serverLevel.addFreshEntity(bbqEntity);

        this.setBbqEntity(bbqEntity);
    }

    @Nullable
    private LivingEntity resolveBbqTarget(@Nullable LivingEntity target) {
        if (target == null || !target.isAlive()) {
            return null;
        }

        if (target instanceof HerobrineMob herobrineMob
                && (herobrineMob.isSacrificing() || herobrineMob.isHealing())) {
            if (herobrineMob.getFirstPossessedHerobrine() instanceof LivingEntity living && living.isAlive()) {
                return living;
            }
            if (herobrineMob.getSecondPossessedHerobrine() instanceof LivingEntity living && living.isAlive()) {
                return living;
            }
            if (herobrineMob.getThirdPossessedHerobrine() instanceof LivingEntity living && living.isAlive()) {
                return living;
            }
            if (herobrineMob.getFourthPossessedHerobrine() instanceof LivingEntity living && living.isAlive()) {
                return living;
            }
        }

        if (target instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity
                && lowHerobrineCloneEntity.isHealing()
                && lowHerobrineCloneEntity.getPossessedByEntity() != null) {
            if (!lowHerobrineCloneEntity.isAlive()) {
                return lowHerobrineCloneEntity.getPossessedByEntity();
            }
        }

        if (target instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity
                && (lowShadowHerobrineCloneEntity.isSacrificing() || lowShadowHerobrineCloneEntity.isHealing())
                && lowShadowHerobrineCloneEntity.getPossessedByEntity() != null) {
            if (!lowShadowHerobrineCloneEntity.isAlive()) {
                return lowShadowHerobrineCloneEntity.getPossessedByEntity();
            }
        }

        return target;
    }

    private void tickBbqOrders(BbqEntity bbqEntity) {
        if (bbqEntity == null) {
            return;
        }

        LivingEntity blueDemonTarget = this.getTarget();
        if (blueDemonTarget == null || !blueDemonTarget.isAlive()) {
            bbqEntity.clearCombat();
            return;
        }

        LivingEntity bbqTarget = this.resolveBbqTarget(blueDemonTarget);
        if (bbqTarget == null || !bbqTarget.isAlive()) {
            bbqEntity.clearCombat();
            return;
        }

        bbqEntity.setCombatTarget(bbqTarget);

        if (this.bbqSauceHeadAttackCooldown > 0) {
            this.bbqSauceHeadAttackCooldown--;
        }

        if (this.bbqSauceOrderCooldown > 0) {
            this.bbqSauceOrderCooldown--;
        }

        if (this.bbqSauceModeCooldown > 0) {
            this.bbqSauceModeCooldown--;
        }

        double blueDistance = this.distanceTo(blueDemonTarget);
        double bbqDistance = bbqEntity.distanceTo(bbqTarget);

        if (blueDistance > 10.0D) {
            bbqEntity.startParallelPursuit(bbqTarget, 25);

            if (this.bbqSauceOrderCooldown <= 0) {
                bbqEntity.shootChain(bbqTarget, 3, 10);
                this.bbqSauceOrderCooldown = 40;
            }
            return;
        }

        if (this.bbqSauceHeadAttackCooldown <= 0 && blueDistance < 4.5D && bbqDistance < 6.5D && !bbqEntity.isHeadAttacking()) {
            bbqEntity.startHeadAttack(bbqTarget, 35);
            this.bbqSauceHeadAttackCooldown = 110;
            this.bbqSauceModeCooldown = 20;
            this.bbqSauceOrderCooldown = 16;
            return;
        }

        if (!bbqEntity.isHeadAttacking() && this.bbqSauceModeCooldown <= 0) {
            int roll = this.random.nextInt(100);

            if (roll < 25) {
                bbqEntity.startParallelPursuit(bbqTarget, 28);
                this.bbqSauceModeCooldown = 28 + this.random.nextInt(12);
            } else if (roll < 60) {
                bbqEntity.startGroundOrbit(bbqTarget, 36);
                this.bbqSauceModeCooldown = 34 + this.random.nextInt(14);
            } else {
                bbqEntity.startOrbit(bbqTarget, 28);
                this.bbqSauceModeCooldown = 26 + this.random.nextInt(12);
            }
        }

        if (bbqEntity.isHeadAttacking() || this.bbqSauceOrderCooldown > 0) {
            return;
        }

        switch (bbqEntity.getCombatMode()) {
            case PARALLEL -> {
                bbqEntity.shootChain(bbqTarget, 3, 10);
                this.bbqSauceOrderCooldown = 40;
            }
            case GROUND_ORBIT -> {
                if (bbqDistance > 7.0D) {
                    bbqEntity.shootChain(bbqTarget, 3, 9);
                    this.bbqSauceOrderCooldown = 36;
                } else {
                    bbqEntity.shootCluster(bbqTarget, 3, 1.05F, 10.0F);
                    this.bbqSauceOrderCooldown = 48;
                }
            }
            case ORBIT -> {
                if (bbqDistance > 7.0D) {
                    bbqEntity.shootChain(bbqTarget, 4, 8);
                    this.bbqSauceOrderCooldown = 38;
                } else {
                    bbqEntity.shootCluster(bbqTarget, 4, 1.1F, 10.0F);
                    this.bbqSauceOrderCooldown = 52;
                }
            }
            default -> {
            }
        }
    }

    private void tickArmorBuff(ServerLevel serverLevel) {
        this.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                net.minecraft.world.effect.MobEffects.MOVEMENT_SPEED,
                1,
                1,
                false,
                false,
                false
        ));

        this.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                net.minecraft.world.effect.MobEffects.JUMP,
                1,
                1,
                false,
                false,
                false
        ));

        this.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE,
                1,
                2,
                false,
                false,
                false
        ));

        if (serverLevel.random.nextDouble() <= 0.1D) {
            serverLevel.sendParticles(
                    AnnoyingVillagersModParticleTypes.ELECTRIC_SPARK.get(),
                    this.getX(), this.getY(), this.getZ(),
                    1,
                    0.3D, 1.2D, 0.3D,
                    0.0D
            );

            if (serverLevel.random.nextDouble() <= 0.8D) {
                float volume = (float) Mth.nextDouble(serverLevel.random, 0.05D, 0.5D);
                float pitch = (float) Mth.nextDouble(serverLevel.random, 0.8D, 1.1D);

                serverLevel.playSound(
                        null,
                        BlockPos.containing(this.getX(), this.getY(), this.getZ()),
                        AnnoyingVillagersModSounds.ELECTRIFY.get(),
                        SoundSource.NEUTRAL,
                        volume,
                        pitch
                );
            }
        }

        if (this.tickCount % 2 == 0) {
            this.absorbNearbyGroundedOwnerTridents(serverLevel);
        }
    }

    private void absorbNearbyGroundedOwnerTridents(ServerLevel serverLevel) {
        AABB box = new AABB(
                this.getX() - BlueDemonChestplateItem.CHEST_TRIDENT_ABSORB_BOX_HALF,
                this.getY() - BlueDemonChestplateItem.CHEST_TRIDENT_ABSORB_BOX_HALF,
                this.getZ() - BlueDemonChestplateItem.CHEST_TRIDENT_ABSORB_BOX_HALF,
                this.getX() + BlueDemonChestplateItem.CHEST_TRIDENT_ABSORB_BOX_HALF,
                this.getY() + BlueDemonChestplateItem.CHEST_TRIDENT_ABSORB_BOX_HALF,
                this.getZ() + BlueDemonChestplateItem.CHEST_TRIDENT_ABSORB_BOX_HALF
        );

        List<BlueDemonThrownTridentEntity> tridents = serverLevel.getEntitiesOfClass(
                BlueDemonThrownTridentEntity.class,
                box,
                trident -> trident.isAlive()
                        && trident.isGroundedTrident()
                        && trident.belongsToOwner(this)
                        && !trident.isAbsorbingToWearer()
        );

        for (BlueDemonThrownTridentEntity trident : tridents) {
            trident.beginAbsorbToWearer(this);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level() instanceof ServerLevel serverLevel) {
            if (stunEscapeCooldown > 0) stunEscapeCooldown--;
            if (voiceCooldown > 0) voiceCooldown--;
            if (swapWeaponCooldown > 0) swapWeaponCooldown--;
            if (efnGuardHitCooldown > 0) efnGuardHitCooldown--;
            if (healingCooldown > 0) healingCooldown--;
            if (healingTick != 0) {
                if (healingTick > 0) {
                    healingTick--;
                }
                tickArmorBuff(serverLevel);
            }

            this.syncChestplateHealingFoil();

            if (efnGuardHitCooldown == 0 && efnGuardHitState != 0) {
                efnGuardHitState = 0;
            }

            if (this.stunEscapeCooldown == 0 && this.level() instanceof ServerLevel) {
                if (getLivingEntityPatch() != null) {
                    AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(getLivingEntityPatch().getAnimator().getPlayerFor(null)).getRealAnimation();
                    if (EpicfightUtil.isLongHitAnimationNotExecutedAnimation(dynamicAnimation, getLivingEntityPatch()) && this.isAlive()) {
                        if (this.getRandom().nextFloat() < CombatBehaviour.calculateGuardBreakWakeUpChance(this)) {
                            this.stunEscapeCooldown = 60;
                            BlueDemonEntity entity = this;
                            new DelayedTask(new Random().nextInt(5, 10)) {
                                @Override
                                public void run() {
                                    if (getLivingEntityPatch() != null && EpicfightUtil.isLongHitAnimationNotExecutedAnimation(dynamicAnimation, getLivingEntityPatch()) && entity.isAlive()) {
                                        CombatBehaviour.postGuardBreakWakeUp(entity, getLivingEntityPatch(), serverLevel);
                                    } else {
                                        entity.stunEscapeCooldown = 1;
                                    }
                                }
                            };
                        }
                    }
                }
            }

            if (this.state == 2) {
                this.addEffect(new MobEffectInstance(CEMobEffects.FULL_STUN_IMMUNITY.get(), 3, 3));
                this.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 3, 3));
            }

            this.ensureBbqExists(this.bbqSauce, this.bbqSauceUUID, Component.literal(Component.translatable("entity.annoyingvillagers.bbq_sauce").getString()));
            this.tickBbqOrders(this.bbqSauce);
        }
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.bbqSauceUUID != null) {
            tag.putUUID("BbqUUID", this.bbqSauceUUID);
        }
        tag.putInt("HealingCooldown", healingCooldown);
        tag.putInt("HealingTick", healingTick);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("BbqUUID")) {
            this.bbqSauceUUID = tag.getUUID("BbqUUID");
        }
        healingCooldown = tag.getInt("HealingCooldown");
        healingTick = tag.getInt("HealingTick");
    }

    public void rollItem() {
        ItemStack legendaryStack = new ItemStack(AnnoyingVillagersModItems.LEGENDARY_SWORD.get());
        legendaryStack.enchant(Enchantments.SHARPNESS, 5);
        legendaryStack.enchant(Enchantments.SMITE, 5);
        legendaryStack.enchant(Enchantments.SWEEPING_EDGE, 5);

        ItemStack tridentStack = new ItemStack(AnnoyingVillagersModItems.BLUE_DEMON_TRIDENT.get());
        legendaryStack.enchant(Enchantments.SHARPNESS, 5);
        legendaryStack.enchant(Enchantments.SMITE, 5);
        legendaryStack.enchant(Enchantments.SWEEPING_EDGE, 5);
        if (new Random().nextBoolean()) {
            this.setItemInHand(InteractionHand.MAIN_HAND, legendaryStack);
        } else {
            this.setItemInHand(InteractionHand.MAIN_HAND, tridentStack);
            this.setItemInHand(InteractionHand.OFF_HAND, tridentStack);
        }
        this.swapWeaponCooldown = new Random().nextInt(200, 600);
    }

    private void syncChestplateHealingFoil() {
        ItemStack chest = this.getItemBySlot(EquipmentSlot.CHEST);
        if (BlueDemonChestplateItem.isBlueDemonChestplate(chest)) {
            BlueDemonChestplateItem.setBlueDemonHealingFoil(chest, this.healingTick != 0);
        }
    }

    public @Nullable SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverlevelaccessor, @NotNull DifficultyInstance difficultyinstance, @NotNull MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        SpawnGroupData data = super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);
        if (!this.level().isClientSide()) {
            TeamUtil.addOrJoinTeam(this, "blue_demon");
        }
        if (mobspawntype == MobSpawnType.NATURAL || mobspawntype == MobSpawnType.CHUNK_GENERATION) {
            ServerLevel serverLevel = serverlevelaccessor.getLevel();
            BluedemonData bluedemonData = BluedemonData.get(serverLevel);

            if (!bluedemonData.tryClaim(serverLevel, this.getUUID())) {
                this.discard();
                return data;
            }

            BlockPos blockPos = this.getOnPos();
            int surfaceY = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockPos).getY();
            BlockPos spawnPos = new BlockPos(blockPos.getX(), surfaceY, blockPos.getZ());
            this.moveTo(spawnPos, this.getYRot(), this.getXRot());
        }
        return data;
    }

    public static boolean canSpawn(EntityType<BlueDemonEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        ServerLevel serverLevel = level.getLevel();
        if (!serverLevel.isThundering()) return false;
        if (BluedemonData.get(serverLevel).isOccupied(serverLevel)) {
            return false;
        }
        return Monster.checkAnyLightMonsterSpawnRules(entityType, level, spawnType, position, random);
    }

    @Override
    protected void actuallyHurt(@NotNull DamageSource pDamageSource, float pDamageAmount) {
        if (pDamageSource.is(DamageTypes.FELL_OUT_OF_WORLD)) {
            super.actuallyHurt(pDamageSource, pDamageAmount);
            return;
        }

        if (this.isInvulnerableTo(pDamageSource)) {
            return;
        }

        pDamageAmount = ForgeHooks.onLivingHurt(this, pDamageSource, pDamageAmount);
        if (pDamageAmount <= 0.0F) {
            return;
        }

        pDamageAmount = this.getDamageAfterArmorAbsorb(pDamageSource, pDamageAmount);
        pDamageAmount = this.getDamageAfterMagicAbsorb(pDamageSource, pDamageAmount);

        float f1 = Math.max(pDamageAmount - this.getAbsorptionAmount(), 0.0F);
        float absorbed = pDamageAmount - f1;
        if (absorbed > 0.0F) {
            this.setAbsorptionAmount(this.getAbsorptionAmount() - absorbed);
            if (this.getAbsorptionAmount() < 0.0F) this.setAbsorptionAmount(0.0F);
        }
        if (this.level() instanceof ServerLevel
                && this.state == 0 && (this.getHealth() - f1) <= 1.0F) {
            this.setHealth(1.0F);
            this.state = 1;
            if (this.getLivingEntityPatch() != null) {
                BlueDemonTridentItem.addStormEnergy(this.getMainHandItem(), 100);
                BlueDemonTridentItem.addStormEnergy(this.getOffhandItem(), 100);
                this.getLivingEntityPatch().playAnimationSynchronized(AVAnimations.TRIDENT_FESTIVAL, 0.0F);
            }
            return;
        }
        f1 = ForgeHooks.onLivingDamage(this, pDamageSource, f1);
        if (f1 <= 0.0F) {
            return;
        }
        this.getCombatTracker().recordDamage(pDamageSource, f1);
        this.setHealth(this.getHealth() - f1);
        this.gameEvent(GameEvent.ENTITY_DAMAGE);
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        super.remove(reason);
        if (!level().isClientSide && level() instanceof ServerLevel serverLevel &&
                (reason == RemovalReason.KILLED || reason == RemovalReason.DISCARDED)) {
            BluedemonData.get(serverLevel).releaseIfMatches(serverLevel, this.getUUID());
        }
    }

    public static @NotNull Builder createAttributes() {
        return Zombie.createAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.45D)
                .add(Attributes.MAX_HEALTH, 250.0D)
                .add(Attributes.ARMOR, 75.0D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }
}
