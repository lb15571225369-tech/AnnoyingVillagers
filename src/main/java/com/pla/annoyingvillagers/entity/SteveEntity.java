package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.spawnhandler.SteveData;
import com.pla.annoyingvillagers.util.*;
import com.pla.annoyingvillagers.clazz.PathfinderMobInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class SteveEntity extends PathfinderMobInventory {
    // 0: normal
    // 1: second
    // 2: angry
    private int state = 0;
    private int swapWeaponCooldown;
    private boolean sayLegendary = false;
    private boolean sayWhyKeepFighting = false;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        if (state == 2 && !this.level().isClientSide) {
            this.goalSelector.removeAllGoals(goal -> true);
            this.targetSelector.removeAllGoals(goal -> true);
            CommonGoals.registerGoalForCrazyNpc(this);
        }
    }

    public SteveEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.STEVE.get(), level);
    }

    private static final EntityDataAccessor<Boolean> USE_ANGRY_STEVE_TEXTURE =
            SynchedEntityData.defineId(SteveEntity.class, EntityDataSerializers.BOOLEAN);

    public SteveEntity(EntityType<SteveEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        this.setDropChance(EquipmentSlot.OFFHAND, 0.0F);
        this.setDropChance(EquipmentSlot.CHEST, 0.0F);
        this.setDropChance(EquipmentSlot.HEAD, 0.0F);
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

    public void setUseAngrySteveTexture(boolean useAngrySteveTexture) {
        this.entityData.set(USE_ANGRY_STEVE_TEXTURE, useAngrySteveTexture);
    }

    public boolean isUseAngrySteveTexture() { return this.entityData.get(USE_ANGRY_STEVE_TEXTURE); }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(USE_ANGRY_STEVE_TEXTURE, false);
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

            this.setEnderPearlCooldown();
        }
        if (this.level() instanceof ServerLevel serverLevel && !damageSource.is(DamageTypes.FELL_OUT_OF_WORLD)) {
            float health = this.getHealth();
            if (health - f <= 0.0F) {
                if (this.state == 0 && !this.getOffhandItem().getItem().equals(Items.TOTEM_OF_UNDYING)
                || this.state == 1) {
                    this.setHealth(1.0F);
                    if (this.state == 1) {
                        serverLevel.playSound(
                                null,
                                this.getX(), this.getY(), this.getZ(),
                                AnnoyingVillagersModSounds.STEVE_ANGRY.get(),
                                SoundSource.NEUTRAL,
                                1.0F, 1.0F
                        );
                        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);
                        if (patch != null) {
                            patch.playAnimationSynchronized(AVAnimations.GUARD_BREAK_ATTACK, 0.0F);
                        }
                        this.setState(2);
                        this.setUseAngrySteveTexture(true);
                        this.setHealth(this.getMaxHealth());
                        this.rollItem();
                        this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                        this.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
                    }
                    return true;
                }
            }
        }
        return super.hurt(damageSource, f);
    }

    public void die(@NotNull DamageSource damageSource) {
        super.die(damageSource);
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.playSound(
                    null,
                    this.getX(), this.getY(), this.getZ(),
                    AnnoyingVillagersModSounds.STEVE_NO.get(),
                    SoundSource.NEUTRAL,
                    1.0F, 1.0F
            );

            final double x = this.getX();
            final double y = this.getY() + 1.0D;
            final double z = this.getZ();

            Consumer<ItemStack> dropStack = (stack) -> {
                ItemEntity drop = new ItemEntity(serverLevel, x, y, z, stack);
                drop.setPickUpDelay(10);
                serverLevel.addFreshEntity(drop);
            };

            List<ItemStack> damagedStacks = new ArrayList<>();

            ItemStack compressedDiamondHelmet = new ItemStack(AnnoyingVillagersModItems.COMPRESSED_DIAMOND_HELMET.get());
            compressedDiamondHelmet.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
            compressedDiamondHelmet.enchant(Enchantments.PROJECTILE_PROTECTION, 5);
            compressedDiamondHelmet.enchant(Enchantments.FIRE_PROTECTION, 5);
            compressedDiamondHelmet.enchant(Enchantments.BLAST_PROTECTION, 5);
            damagedStacks.add(compressedDiamondHelmet);

            ItemStack compressedDiamondChestplate = new ItemStack(AnnoyingVillagersModItems.COMPRESSED_DIAMOND_CHESTPLATE.get());
            compressedDiamondChestplate.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
            compressedDiamondChestplate.enchant(Enchantments.PROJECTILE_PROTECTION, 5);
            compressedDiamondChestplate.enchant(Enchantments.FIRE_PROTECTION, 5);
            compressedDiamondChestplate.enchant(Enchantments.BLAST_PROTECTION, 5);
            damagedStacks.add(compressedDiamondChestplate);

            ItemStack diamondSword = new ItemStack(Items.DIAMOND_SWORD);
            diamondSword.enchant(Enchantments.SHARPNESS, 5);
            diamondSword.enchant(Enchantments.SMITE, 5);
            damagedStacks.add(diamondSword);

            if (new Random().nextBoolean()) {
                damagedStacks.add(diamondSword);
            }

            double chance = new Random().nextDouble(0.0, 1.0);
            if (chance < 0.2) {
                ItemStack woodenDoor = new ItemStack(AnnoyingVillagersModItems.WOODEN_DOOR.get());
                woodenDoor.enchant(Enchantments.SHARPNESS, 5);
                woodenDoor.enchant(Enchantments.KNOCKBACK, 3);
                woodenDoor.enchant(Enchantments.MENDING, 5);
                damagedStacks.add(woodenDoor);
            } else if (chance < 0.4) {
                ItemStack craftingTable = new ItemStack(AnnoyingVillagersModItems.CRAFTING_TABLE.get());
                craftingTable.enchant(Enchantments.SMITE, 5);
                craftingTable.enchant(Enchantments.KNOCKBACK, 3);
                craftingTable.enchant(Enchantments.MENDING, 5);
                damagedStacks.add(craftingTable);
            } else if (chance < 0.6) {
                ItemStack ladder = new ItemStack(AnnoyingVillagersModItems.LADDER.get());
                ladder.enchant(Enchantments.SMITE, 5);
                ladder.enchant(Enchantments.SWEEPING_EDGE, 3);
                ladder.enchant(Enchantments.MENDING, 5);
                damagedStacks.add(ladder);
            } else if (chance < 0.8) {
                ItemStack trapDoor = new ItemStack(AnnoyingVillagersModItems.TRAPDOOR.get());
                trapDoor.enchant(Enchantments.KNOCKBACK, 5);
                trapDoor.enchant(Enchantments.SWEEPING_EDGE, 3);
                trapDoor.enchant(Enchantments.MENDING, 5);
                damagedStacks.add(trapDoor);
            } else {
                ItemStack mendingDiamondSword = new ItemStack(Items.DIAMOND_SWORD);
                mendingDiamondSword.enchant(Enchantments.SHARPNESS, 5);
                mendingDiamondSword.enchant(Enchantments.SMITE, 5);
                mendingDiamondSword.enchant(Enchantments.MENDING, 5);
                damagedStacks.add(mendingDiamondSword);
            }

            if (new Random().nextBoolean()) {
                ItemStack woopieTheSword = new ItemStack(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get());
                woopieTheSword.enchant(Enchantments.SHARPNESS, 5);
                woopieTheSword.enchant(Enchantments.SMITE, 5);
                woopieTheSword.enchant(Enchantments.SWEEPING_EDGE, 5);
                damagedStacks.add(woopieTheSword);
                damagedStacks.add(new ItemStack(AnnoyingVillagersModItems.JESSICA_THE_DARK_SHIELD.get()));
            } else {
                ItemStack legendarySword = new ItemStack(AnnoyingVillagersModItems.LEGENDARY_SWORD.get());
                legendarySword.enchant(Enchantments.SHARPNESS, 5);
                legendarySword.enchant(Enchantments.SMITE, 5);
                legendarySword.enchant(Enchantments.SWEEPING_EDGE, 5);
                damagedStacks.add(legendarySword);
            }

            for (ItemStack stack : damagedStacks) {
                stack.setDamageValue(EquipmentDataLoader.getRandomDamage(stack));
                dropStack.accept(stack);
            }

            ItemLike[] simpleDrops = new ItemLike[] {
                    Items.GOLDEN_APPLE, Items.GOLDEN_APPLE, Items.GOLDEN_APPLE, Items.GOLDEN_APPLE,
                    Items.GOLDEN_APPLE, Items.GOLDEN_APPLE, Items.GOLDEN_APPLE, Items.GOLDEN_APPLE,
                    Items.ENCHANTED_GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE,

                    Items.ENDER_PEARL, Items.ENDER_PEARL, Items.ENDER_PEARL, Items.ENDER_PEARL, Items.ENDER_PEARL,
                    Items.ENDER_PEARL, Items.ENDER_PEARL, Items.ENDER_PEARL, Items.ENDER_PEARL, Items.ENDER_PEARL,

                    Blocks.DIRT, Blocks.DIRT, Blocks.DIRT, Blocks.DIRT, Blocks.DIRT, Blocks.DIRT, Blocks.DIRT, Blocks.DIRT,

                    Blocks.TNT, Blocks.TNT,
                    Blocks.DIAMOND_BLOCK,
                    Blocks.DRAGON_EGG,

                    Items.WHITE_BED,
                    Items.CAKE,
                    Items.WATER_BUCKET,
                    Items.COOKED_BEEF, Items.COOKED_BEEF, Items.COOKED_BEEF,
                    Items.FISHING_ROD,
                    Items.LIGHT_GRAY_DYE,
                    Items.CARROT, Items.CARROT,
                    Items.BAKED_POTATO, Items.BAKED_POTATO,

                    Items.STICK, Items.STICK, Items.STICK, Items.STICK, Items.STICK,
                    Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT,
                    Items.DIAMOND, Items.DIAMOND, Items.DIAMOND, Items.DIAMOND, Items.DIAMOND, Items.DIAMOND, Items.DIAMOND, Items.DIAMOND,

                    AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get(),
                    AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get(),
                    AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get(),
                    AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get(),
                    AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get(),
                    AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get(),
                    AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get(),
                    AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get(),
                    AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get(),
            };

            for (ItemLike itemLike : simpleDrops) {
                dropStack.accept(new ItemStack(itemLike));
            }
        }
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("State", this.state);
        tag.putInt("SwapWeaponCooldown", this.swapWeaponCooldown);
        tag.putBoolean("UseAngrySteveTexture", isUseAngrySteveTexture());
        tag.putBoolean("SayLegendary", sayLegendary);
        tag.putBoolean("sayWhyKeepFighting", sayWhyKeepFighting);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setUseAngrySteveTexture(tag.getBoolean("UseAngrySteveTexture"));
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
            setWeapon = true;
        }

        if (this.state == 2) {
            ItemStack legendarySword = new ItemStack(AnnoyingVillagersModItems.LEGENDARY_SWORD.get());
            legendarySword.enchant(Enchantments.SHARPNESS, 5);
            legendarySword.enchant(Enchantments.SMITE, 5);
            legendarySword.enchant(Enchantments.SWEEPING_EDGE, 5);
            this.setItemInHand(InteractionHand.MAIN_HAND, legendarySword);
            this.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
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
            if (this.tickCount == 1) {
                if (this.getState() != 2) {
                    CommonGoals.registerGoalForNeutralNpc(this);
                } else {
                    CommonGoals.registerGoalForCrazyNpc(this);
                }
            }
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
            if (this.getState() == 2 && this.tickCount % 20 == 0) {
                this.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 30, 2));
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 30, 2));
                this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 30, 1));
                this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 30, 0));
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
        builder = builder.add(Attributes.ARMOR, 5.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
        return builder;
    }
}
