package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.spawnhandler.SteveData;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.util.CommonGoals;
import com.pla.annoyingvillagers.clazz.PathfinderMobInventory;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class SteveEntity extends PathfinderMobInventory {
    // 0: normal
    // 1: second
    // 2: angry
    private int state = 0;
    private int swapWeaponCooldown;

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

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft","entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft","entity.generic.death"));
    }

    public boolean hurt(DamageSource damageSource, float f) {
        if (damageSource.getEntity() != null && this.getEnderPearlCooldown() == 0) {
            CombatBehaviour.throwEnderPearl(this, (float) new Random().nextDouble(90.0D, 180.0D));
            LivingEntity entity = this;

            if (Math.random() <= 0.5D) {
                if (entity.isAlive()) {
                    CombatBehaviour.throwEnderPearl(entity, 180.0F);
                }
            }

            if (Math.random() <= 0.3D) {
                new DelayedTask(20) {
                    public void run() {
                        if (entity.isAlive()) {
                            CombatBehaviour.throwEnderPearl(entity, 90.0F);
                        }
                    }
                };
            }
        }
        return super.hurt(damageSource, f);
    }

    public void die(@NotNull DamageSource damageSource) {
        super.die(damageSource);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("State", this.state);
        tag.putInt("SwapWeaponCooldown", this.swapWeaponCooldown);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.state = tag.getInt("State");
        this.swapWeaponCooldown = tag.getInt("SwapWeaponCooldown");
    }

    public void rollItem() {
        int chance;
        boolean setWeapon = false;
        if (this.state == 1) {
            chance = new Random().nextInt();
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
                }
            } else {
                if (chance <= 0.3) {
                    ItemStack woopieTheSword = new ItemStack(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get());
                    woopieTheSword.enchant(Enchantments.SHARPNESS, 5);
                    woopieTheSword.enchant(Enchantments.SMITE, 5);
                    woopieTheSword.enchant(Enchantments.SWEEPING_EDGE, 5);
                    this.setItemInHand(InteractionHand.MAIN_HAND, woopieTheSword);

                    this.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(AnnoyingVillagersModItems.JESSICA_THE_DARK_SHIELD.get()));
                    this.setOffWeaponItem(this.getOffWeaponItem().copy());
                    setWeapon = true;
                } else if (chance <= 0.8) {
                    ItemStack legendarySword = new ItemStack(AnnoyingVillagersModItems.LEGENDARY_SWORD.get());
                    legendarySword.enchant(Enchantments.SHARPNESS, 5);
                    legendarySword.enchant(Enchantments.SMITE, 5);
                    legendarySword.enchant(Enchantments.SWEEPING_EDGE, 5);
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
        }

        if (!setWeapon) {
            chance = new Random().nextInt();
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
        this.swapWeaponCooldown = new Random().nextInt(200, 400);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            if (this.getTarget() != null && this.getTarget().isAlive() && this.getMainHandItem().isEmpty()) {
                rollItem();
            }
            if (this.getTarget() == null && !this.getMainHandItem().isEmpty()) {
                this.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                this.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
            }
            if (this.getTarget() != null && this.state == 0
                    && this.getHealth() <= 20
                    && !this.getItemInHand(InteractionHand.OFF_HAND).getItem().equals(Items.TOTEM_OF_UNDYING)) {
                rollItem();
            }

            if (swapWeaponCooldown > 0) swapWeaponCooldown--;
            else if (swapWeaponCooldown == 0 && this.getTarget() != null && this.getTarget().isAlive()) rollItem();

            // Second phase
//            if (this.state == 0 && this.getHealth() < 1) {
//                this.setHealth(50);
//                this.state = 1;
//            }
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
        this.swapWeaponCooldown = new Random().nextInt(200, 400);
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
        builder = builder.add(Attributes.ARMOR, 5.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
        return builder;
    }
}
