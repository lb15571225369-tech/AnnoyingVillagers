package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.spawnhandler.SteveData;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.*;
import com.pla.annoyingvillagers.clazz.AVNpc;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import net.shelmarow.combat_evolution.execution.ExecutionHandler;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.Objects;
import java.util.Random;

public class SteveEntity extends AVNpc {
    // 0: normal
    // 1: second
    private int state = 0;
    private int swapWeaponCooldown;
    private boolean sayLegendary = false;
    private boolean sayWhyKeepFighting = false;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public SteveEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.STEVE.get(), level);
    }

    public SteveEntity(EntityType<SteveEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(3.0F);
        this.xpReward = 8;
        this.setNoAi(false);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setPlaceBlockToParryChance(0.8);
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        CommonGoals.registerGoalForNeutralNpc(this);
    }

    public @NotNull MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    @Override
    public void awardKillScore(@NotNull Entity pKilled, int pScoreValue, @NotNull DamageSource pSource) {
        super.awardKillScore(pKilled, pScoreValue, pSource);
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.playSound(
                    null,
                    this.getX(), this.getY(), this.getZ(),
                    AnnoyingVillagersModSounds.STEVE_WIN.get(),
                    SoundSource.NEUTRAL,
                    1.0F, 1.0F
            );
        }
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public int getSwapWeaponCooldown() {
        return swapWeaponCooldown;
    }

    public void setSayWhyKeepFighting(boolean sayWhyKeepFighting) {
        this.sayWhyKeepFighting = sayWhyKeepFighting;
    }

    public boolean isSayWhyKeepFighting() {
        return sayWhyKeepFighting;
    }

    public SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft","entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft","entity.generic.death"));
    }

    public boolean hurt(@NotNull DamageSource damageSource, float f) {
        if (this.getUnableToDamageCooldown() > 0) {
            return false;
        }

        AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(Objects.requireNonNull(this.getLivingEntityPatch()).getAnimator().getPlayerFor(null)).getRealAnimation();

        if (damageSource.getEntity() != null && this.getEnderPearlCooldown() == 0
                && !EpicfightUtil.isLongHitAnimation(dynamicAnimation, getLivingEntityPatch())
                && (this.level() instanceof ServerLevel && dynamicAnimation == Animations.EMPTY_ANIMATION)
                && CombatCommon.canPerformNormalAttackLogic((MobPatch<?>) this.getLivingEntityPatch())) {
            AVNpc entity = this;
            if (entity.getLivingEntityPatch() != null) {
                entity.getLivingEntityPatch().playAnimationSynchronized(AVAnimations.CASTING_ONE_HAND_BUFF, 0.0F);
            }
            CombatBehaviour.throwEnderPearl(this, (float) new Random().nextDouble(90.0D, 180.0D));

            if (Math.random() <= 0.5D) {
                new DelayedTask(20) {
                    @Override
                    public void run() {
                        if (entity.isAlive()) {
                            if (entity.getLivingEntityPatch() != null) {
                                entity.getLivingEntityPatch().playAnimationSynchronized(AVAnimations.CASTING_ONE_HAND_BUFF, 0.0F);
                            }
                            CombatBehaviour.throwEnderPearl(entity, 180.0F);
                        }
                    }
                };
            }

            if (Math.random() <= 0.3D) {
                new DelayedTask(20) {
                    public void run() {
                        if (entity.isAlive()) {
                            if (entity.getLivingEntityPatch() != null) {
                                entity.getLivingEntityPatch().playAnimationSynchronized(AVAnimations.CASTING_ONE_HAND_BUFF, 0.0F);
                            }
                            CombatBehaviour.throwEnderPearl(entity, 90.0F);
                        }
                    }
                };
            }

            this.setEnderPearlCooldown();
        }
        return super.hurt(damageSource, f);
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
                && this.state == 0 && (this.getHealth() - f1) <= 1.0F
                && !this.getOffhandItem().getItem().equals(Items.TOTEM_OF_UNDYING)) {
            this.setHealth(1.0F);
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
    public void die(@NotNull DamageSource pDamageSource) {
        if (this.level() instanceof ServerLevel serverLevel) {
            LivingEntity target = null;
            if (pDamageSource.getEntity() instanceof LivingEntity living && living.isAlive()) {
                target = living;
            } else if (this.getTarget() != null && this.getTarget().isAlive()) {
                target = this.getTarget();
            } else if (this.getLastHurtByMob() != null && this.getLastHurtByMob().isAlive()) {
                target = this.getLastHurtByMob();
            }

            AngrySteveEntity angrySteveEntity = new AngrySteveEntity(AnnoyingVillagersModEntities.ANGRY_STEVE.get(), serverLevel);

            angrySteveEntity.moveTo(this.blockPosition(), this.getYRot(), this.getXRot());
            InventoryUtils.transferInventory(this.getInventory(), angrySteveEntity.getInventory());
            this.discard();
            SteveData steveData = SteveData.get(serverLevel);
            steveData.forceClaim(serverLevel, angrySteveEntity.getUUID());

            angrySteveEntity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(angrySteveEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
            serverLevel.addFreshEntity(angrySteveEntity);
            if (target != null) {
                angrySteveEntity.setTarget(target);
                angrySteveEntity.setLastHurtByMob(target);
            }
        }

        super.die(pDamageSource);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("State", this.state);
        tag.putInt("SwapWeaponCooldown", this.swapWeaponCooldown);
        tag.putBoolean("SayLegendary", sayLegendary);
        tag.putBoolean("sayWhyKeepFighting", sayWhyKeepFighting);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.state = tag.getInt("State");
        this.swapWeaponCooldown = tag.getInt("SwapWeaponCooldown");
        this.sayLegendary = tag.getBoolean("SayLegendary");
        this.sayWhyKeepFighting = tag.getBoolean("sayWhyKeepFighting");
    }

    public void rollItem() {
        double chance;
        boolean setWeapon = false;
        if (this.state == 1) {
            chance = new Random().nextDouble(0.0, 1.0);
            if (this.getHealth() > this.getMaxHealth() / 2) {
                if (chance <= 0.6) {
                    ItemStack woopieTheSword = new ItemStack(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get());
                    woopieTheSword.enchant(Enchantments.SHARPNESS, 5);
                    woopieTheSword.enchant(Enchantments.SMITE, 5);
                    woopieTheSword.enchant(Enchantments.SWEEPING_EDGE, 5);
                    this.setItemInHand(InteractionHand.MAIN_HAND, woopieTheSword);

                    this.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(AnnoyingVillagersModItems.JESSICA_THE_DARK_SHIELD.get()));
                    this.setOffWeaponItem(this.getOffWeaponItem().copy());
                    setWeapon = true;
                } else {
                    ItemStack diamondSword = new ItemStack(Items.DIAMOND_SWORD);
                    diamondSword.enchant(Enchantments.SHARPNESS, 5);
                    diamondSword.enchant(Enchantments.SMITE, 5);
                    this.setItemInHand(InteractionHand.MAIN_HAND, diamondSword);
                    this.setItemInHand(InteractionHand.OFF_HAND, diamondSword);
                    setWeapon = true;
                }
            } else {
                if (chance <= 0.4) {
                    ItemStack woopieTheSword = new ItemStack(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get());
                    woopieTheSword.enchant(Enchantments.SHARPNESS, 5);
                    woopieTheSword.enchant(Enchantments.SMITE, 5);
                    woopieTheSword.enchant(Enchantments.SWEEPING_EDGE, 5);
                    this.setItemInHand(InteractionHand.MAIN_HAND, woopieTheSword);

                    this.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(AnnoyingVillagersModItems.JESSICA_THE_DARK_SHIELD.get()));
                    this.setOffWeaponItem(this.getOffWeaponItem().copy());
                    setWeapon = true;
                } else if (this.level() instanceof ServerLevel serverLevel) {
                    if (!this.sayLegendary) {
                        serverLevel.playSound(
                                null,
                                this.getX(), this.getY(), this.getZ(),
                                AnnoyingVillagersModSounds.STEVE_LEGENDARYSWORD.get(),
                                SoundSource.NEUTRAL,
                                1.0F, 1.0F
                        );
                        this.sayLegendary = true;
                    }
                    ItemStack legendarySword = new ItemStack(AnnoyingVillagersModItems.LEGENDARY_SWORD.get());
                    this.setItemInHand(InteractionHand.MAIN_HAND, legendarySword);
                    setWeapon = true;
                }
            }
        } else if (this.state == 0 && this.getHealth() <= 20) {
            ItemStack diamondSword = new ItemStack(Items.DIAMOND_SWORD);
            diamondSword.enchant(Enchantments.SHARPNESS, 5);
            diamondSword.enchant(Enchantments.SMITE, 5);
            this.setItemInHand(InteractionHand.MAIN_HAND, diamondSword);
            this.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.TOTEM_OF_UNDYING));
            setWeapon = true;
        }

        if (!setWeapon) {
            chance = new Random().nextDouble(0.0, 1.0);
            if (chance <= 0.2) {
                ItemStack diamondSword = new ItemStack(Items.DIAMOND_SWORD);
                diamondSword.enchant(Enchantments.SHARPNESS, 5);
                diamondSword.enchant(Enchantments.SMITE, 5);
                this.setItemInHand(InteractionHand.MAIN_HAND, diamondSword);
            } else if (chance <= 0.4) {
                ItemStack woodenDoor = new ItemStack(AnnoyingVillagersModItems.WOODEN_DOOR.get());
                woodenDoor.enchant(Enchantments.SHARPNESS, 5);
                woodenDoor.enchant(Enchantments.KNOCKBACK, 3);
                this.setItemInHand(InteractionHand.MAIN_HAND, woodenDoor);
            } else if (chance <= 0.6) {
                ItemStack craftingTable = new ItemStack(AnnoyingVillagersModItems.CRAFTING_TABLE.get());
                craftingTable.enchant(Enchantments.SMITE, 5);
                craftingTable.enchant(Enchantments.KNOCKBACK, 3);
                this.setItemInHand(InteractionHand.MAIN_HAND, craftingTable);
            } else if (chance <= 0.8) {
                ItemStack ladder = new ItemStack(AnnoyingVillagersModItems.LADDER.get());
                ladder.enchant(Enchantments.SMITE, 5);
                ladder.enchant(Enchantments.SWEEPING_EDGE, 3);
                this.setItemInHand(InteractionHand.MAIN_HAND, ladder);
            } else {
                ItemStack trapDoor = new ItemStack(AnnoyingVillagersModItems.TRAPDOOR.get());
                trapDoor.enchant(Enchantments.KNOCKBACK, 5);
                trapDoor.enchant(Enchantments.SWEEPING_EDGE, 3);
                this.setItemInHand(InteractionHand.MAIN_HAND, trapDoor);
            }
        }
        this.setMainWeaponItem(this.getMainHandItem().copy());
        this.swapWeaponCooldown = new Random().nextInt(100, 200);
    }

    @Override
    protected void implementFirstTick(ServerLevel serverLevel) {
        super.implementFirstTick(serverLevel);
        serverLevel.playSound(
                null,
                this.getX(), this.getY(), this.getZ(),
                AnnoyingVillagersModSounds.STEVE_SPAWN.get(),
                SoundSource.NEUTRAL,
                1.0F, 1.0F
        );
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level() instanceof ServerLevel serverLevel) {
            if (this.getTarget() != null && this.getTarget().isAlive() && this.getMainHandItem().isEmpty()) {
                rollItem();
                serverLevel.playSound(
                        null,
                        this.getX(), this.getY(), this.getZ(),
                        AnnoyingVillagersModSounds.STEVE_WHY.get(),
                        SoundSource.NEUTRAL,
                        1.0F, 1.0F
                );
            }
            if (this.getState() != 2 && this.getTarget() == null && !this.getMainHandItem().isEmpty()) {
                this.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                this.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
            }
            if (this.state == 0
                    && this.getHealth() <= 20
                    && !this.getItemInHand(InteractionHand.OFF_HAND).getItem().equals(Items.TOTEM_OF_UNDYING)) {
                this.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.TOTEM_OF_UNDYING));
            }
            if (this.getTarget() != null && this.state == 0
                    && this.getHealth() > 20
                    && this.getItemInHand(InteractionHand.OFF_HAND).getItem().equals(Items.TOTEM_OF_UNDYING)
                    && !(this.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof ShieldItem)) {
                this.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
            }
            if (swapWeaponCooldown > 0) swapWeaponCooldown--;
        }
    }

    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverLevelAccessor, @NotNull DifficultyInstance difficultyInstance, @NotNull MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        if (mobSpawnType == MobSpawnType.NATURAL || mobSpawnType == MobSpawnType.CHUNK_GENERATION) {
            ServerLevel serverLevel = serverLevelAccessor.getLevel();
            SteveData steveData = SteveData.get(serverLevel);

            if (!steveData.tryClaim(serverLevel, this.getUUID())) {
                this.discard();
                return null;
            }
        }

        TeamUtil.addOrJoinTeam(this, "steve");
        this.swapWeaponCooldown = new Random().nextInt(100, 200);
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawngroupdata, compoundtag);
    }

    public static boolean canSpawn(EntityType<SteveEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        ServerLevel serverLevel = level.getLevel();
        if (SteveData.get(serverLevel).isOccupied(serverLevel)) {
            return false;
        }
        return PathfinderMob.checkMobSpawnRules(entityType, level, spawnType, position, random);
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        super.remove(reason);
        if (!level().isClientSide && level() instanceof ServerLevel serverLevel &&
                (reason == RemovalReason.KILLED || reason == RemovalReason.DISCARDED)) {
            SteveData.get(serverLevel).releaseIfMatches(serverLevel, this.getUUID());
        }
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.45D);
        builder = builder.add(Attributes.MAX_HEALTH, 50.0D);
        builder = builder.add(Attributes.ARMOR, 30.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
        return builder;
    }
}
