package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.spawnhandler.ChrisData;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.*;
import com.pla.annoyingvillagers.clazz.AVNpc;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;


public class ChrisEntity extends AVNpc {
    private int state = 0;

    public ChrisEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.CHRIS.get(), level);
    }

    public ChrisEntity(EntityType<ChrisEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.6F);
        this.xpReward = 50;
        this.setNoAi(false);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setPlaceBlockToParryChance(0.6);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("State", this.state);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        state = tag.getInt("State");
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

    public boolean hurt(@NotNull DamageSource damageSource, float f) {
        if (this.getUnableToDamageCooldown() > 0) {
            return false;
        }

        AssetAccessor<? extends DynamicAnimation> dynamicAnimation = Objects.requireNonNull(this.getLivingEntityPatch().getAnimator().getPlayerFor(null)).getAnimation();

        if (damageSource.getEntity() != null && this.getEnderPearlCooldown() == 0
                && !EpicfightUtil.isLongHitAnimation(dynamicAnimation)
                && CombatCommon.canPerformNormalAttackLogic((MobPatch<?>) this.getLivingEntityPatch())) {
            AVNpc entity = this;

            if (entity.getLivingEntityPatch() != null) {
                entity.getLivingEntityPatch().playAnimationSynchronized(AVAnimations.CASTING_ONE_HAND_BUFF, 0.0F);
            }
            CombatBehaviour.throwEnderPearl(this, 180.0F);
            if (Math.random() <= 0.2D) {
                new DelayedTask(20) {
                    @Override
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

    public void die(@NotNull DamageSource damageSource) {
        super.die(damageSource);
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.getServer().getPlayerList().broadcastSystemMessage(
                    Component.literal("<" + this.getDisplayName().getString() + "> Steve, I'm sorry."),
                    false
            );

            final double x = this.getX();
            final double y = this.getY() + 1.0D;
            final double z = this.getZ();

            Consumer<ItemStack> dropStack = (stack) -> {
                ItemEntity drop = new ItemEntity(serverLevel, x, y, z, stack);
                drop.setPickUpDelay(10);
                serverLevel.addFreshEntity(drop);
            };

            Consumer<Integer> dropArrows = (count) -> {
                for (int i = 0; i < count; i++) dropStack.accept(new ItemStack(Items.ARROW));
            };

            List<ItemStack> damagedStacks = new ArrayList<>();

            ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
            sword.enchant(Enchantments.KNOCKBACK, 5);
            sword.enchant(Enchantments.SHARPNESS, 5);
            sword.enchant(Enchantments.UNBREAKING, 5);
            damagedStacks.add(sword);

            ItemStack diamondHelmet = new ItemStack(Items.DIAMOND_HELMET);
            diamondHelmet.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
            diamondHelmet.enchant(Enchantments.UNBREAKING, 5);
            damagedStacks.add(diamondHelmet);

            ItemStack diamondChestplate = new ItemStack(Items.DIAMOND_CHESTPLATE);
            diamondChestplate.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
            diamondChestplate.enchant(Enchantments.UNBREAKING, 5);
            damagedStacks.add(diamondChestplate);

            ItemStack diamondBoots = new ItemStack(Items.DIAMOND_BOOTS);
            diamondBoots.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
            diamondBoots.enchant(Enchantments.FROST_WALKER, 2);
            diamondBoots.enchant(Enchantments.UNBREAKING, 5);
            damagedStacks.add(diamondBoots);

            ItemStack bow = new ItemStack(Items.BOW);
            bow.enchant(Enchantments.POWER_ARROWS, 2);
            bow.enchant(Enchantments.PUNCH_ARROWS, 2);
            damagedStacks.add(bow);

            ItemStack ironPickaxe = new ItemStack(Items.IRON_PICKAXE);
            ironPickaxe.enchant(Enchantments.UNBREAKING, 3);
            damagedStacks.add(ironPickaxe);

            ItemStack ironAxe = new ItemStack(Items.IRON_AXE);
            ironAxe.enchant(Enchantments.UNBREAKING, 3);
            damagedStacks.add(ironAxe);

            for (ItemStack stack : damagedStacks) {
                stack.setDamageValue(EquipmentDataLoader.getRandomDamage(stack));
                dropStack.accept(stack);
            }

            ItemStack[] simpleDrops = new ItemStack[] {
                    new ItemStack(Items.TOTEM_OF_UNDYING),
                    new ItemStack(Items.SHIELD),
                    new ItemStack(Items.SPYGLASS),

                    new ItemStack(Items.ENCHANTED_GOLDEN_APPLE),
                    new ItemStack(Items.ENCHANTED_GOLDEN_APPLE),
                    new ItemStack(Items.ENCHANTED_GOLDEN_APPLE),
                    new ItemStack(Items.ENCHANTED_GOLDEN_APPLE),
                    new ItemStack(Items.ENCHANTED_GOLDEN_APPLE),

                    new ItemStack(Items.ENDER_PEARL),
                    new ItemStack(Items.ENDER_PEARL),
                    new ItemStack(Items.ENDER_PEARL),
                    new ItemStack(Items.ENDER_PEARL),
                    new ItemStack(Items.ENDER_PEARL),

                    new ItemStack(Items.OAK_BOAT),

                    new ItemStack(Items.IRON_INGOT),
                    new ItemStack(Items.IRON_INGOT),
                    new ItemStack(Items.IRON_INGOT),
                    new ItemStack(Items.IRON_INGOT),

                    new ItemStack(Items.CRAFTING_TABLE),
                    new ItemStack(Items.CRAFTING_TABLE),

                    new ItemStack(Items.DIAMOND),
                    new ItemStack(Items.DIAMOND),
                    new ItemStack(Items.DIAMOND),
                    new ItemStack(Items.DIAMOND),

                    new ItemStack(Items.GOLD_INGOT),
                    new ItemStack(Items.GOLD_INGOT),
                    new ItemStack(Items.GOLD_INGOT),

                    new ItemStack(Items.EMERALD),
                    new ItemStack(Items.EMERALD),
                    new ItemStack(Items.EMERALD),
                    new ItemStack(Items.EMERALD),

                    new ItemStack(Items.GOLDEN_APPLE),
                    new ItemStack(Items.GOLDEN_APPLE),
                    new ItemStack(Items.GOLDEN_APPLE),
                    new ItemStack(Items.GOLDEN_APPLE),
                    new ItemStack(Items.GOLDEN_APPLE),
                    new ItemStack(Items.GOLDEN_APPLE),

                    new ItemStack(Items.WHITE_BED)
            };

            for (ItemStack stack : simpleDrops) {
                dropStack.accept(stack);
            }

            dropArrows.accept(new Random().nextInt(10, 20));

            if (AnnoyingVillagersConfig.PHYSIC_MOD_COMPAT.get()) {
                ChrisDeadEntity deadEntity = new ChrisDeadEntity(AnnoyingVillagersModEntities.CHRIS_DEAD.get(), serverLevel);
                deadEntity.moveTo(this.getX(), this.getY(), this.getZ(), serverLevel.getRandom().nextFloat() * 360.0F, 0.0F);
                deadEntity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(deadEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                this.remove(RemovalReason.KILLED);
                serverLevel.addFreshEntity(deadEntity);
                deadEntity.kill();
            }
        }
    }

    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverLevelAccessor, @NotNull DifficultyInstance difficultyInstance, @NotNull MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        if (mobSpawnType == MobSpawnType.NATURAL || mobSpawnType == MobSpawnType.CHUNK_GENERATION) {
            ServerLevel serverLevel = serverLevelAccessor.getLevel();
            ChrisData chrisData = ChrisData.get(serverLevel);

            if (!chrisData.tryClaim(serverLevel, this.getUUID())) {
                this.discard();
                return null;
            }
        }

        SpawnGroupData returnSpawnGroupData = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawngroupdata, compoundtag);

        ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
        sword.enchant(Enchantments.KNOCKBACK, 5);
        sword.enchant(Enchantments.SHARPNESS, 5);
        sword.enchant(Enchantments.UNBREAKING, 5);
        this.setItemSlot(EquipmentSlot.MAINHAND, sword);

        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.ENDER_PEARL));

        ItemStack diamondHelmet = new ItemStack(Items.DIAMOND_HELMET);
        diamondHelmet.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
        diamondHelmet.enchant(Enchantments.UNBREAKING, 5);
        this.setItemSlot(EquipmentSlot.HEAD, diamondHelmet);

        ItemStack diamondChestplate = new ItemStack(Items.DIAMOND_CHESTPLATE);
        diamondChestplate.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
        diamondChestplate.enchant(Enchantments.UNBREAKING, 5);
        this.setItemSlot(EquipmentSlot.CHEST, diamondChestplate);

        ItemStack diamondBoots = new ItemStack(Items.DIAMOND_BOOTS);
        diamondBoots.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
        diamondBoots.enchant(Enchantments.FROST_WALKER, 2);
        diamondBoots.enchant(Enchantments.UNBREAKING, 5);
        this.setItemSlot(EquipmentSlot.FEET, diamondBoots);

        TeamUtil.addOrJoinTeam(this, "steve");
        return returnSpawnGroupData;
    }

    public void awardKillScore(@NotNull Entity entity, int i, @NotNull DamageSource damagesource) {
        super.awardKillScore(entity, i, damagesource);
    }

    public static boolean canSpawn(EntityType<ChrisEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        ServerLevel serverLevel = level.getLevel();
        if (ChrisData.get(serverLevel).isOccupied(serverLevel)) {
            return false;
        }
        return PathfinderMob.checkMobSpawnRules(entityType, level, spawnType, position, random);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (this.state == 0
                    && this.getHealth() <= 20
                    && !this.getItemInHand(InteractionHand.OFF_HAND).getItem().equals(Items.TOTEM_OF_UNDYING)) {
                this.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.TOTEM_OF_UNDYING));
            }
        }
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        super.remove(reason);
        if (!level().isClientSide && level() instanceof ServerLevel serverLevel &&
                (reason == RemovalReason.KILLED || reason == RemovalReason.DISCARDED)) {
            ChrisData.get(serverLevel).releaseIfMatches(serverLevel, this.getUUID());
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

