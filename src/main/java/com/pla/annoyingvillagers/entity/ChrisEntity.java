package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.spawnhandler.ChrisData;
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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.ArrayList;
import java.util.List;
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
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("State", this.state);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
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

    @Override
    protected boolean hasEnderPearlCounter() {
        return true;
    }

    @Override
    protected void doEnderPearlCounterPattern(@NotNull DamageSource damageSource) {
        this.doChrisStyleEnderPearlCounter();
    }

    @Override
    protected boolean afterBurstProtection(@NotNull ServerLevel serverLevel,
                                           @NotNull DamageSource source,
                                           float finalDamage) {
        if (this.state == 0
                && (this.getHealth() - finalDamage) <= 1.0F
                && !this.getOffhandItem().is(Items.TOTEM_OF_UNDYING)) {
            this.setHealth(1.0F);
            return true;
        }
        return false;
    }

    @Override
    public float getBurstProtectCapRatio() {
        return 0.25F;
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        if (this.level() instanceof ServerLevel serverLevel) {
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

            ItemStack bow = this.getBowItem();
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
    protected void implementFirstTick(ServerLevel serverLevel) {
        super.implementFirstTick(serverLevel);
        this.playSound(
                AnnoyingVillagersModSounds.CHRIS_SAY_ON_SPAWN.get(),
                1.0F, 1.0F
        );
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
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.45D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(EpicFightAttributes.IMPACT.get(), 2.0D)
                .add(EpicFightAttributes.ARMOR_NEGATION.get(), 5.0D)
                .add(EpicFightAttributes.STUN_ARMOR.get(), 20.0D)
                .add(EpicFightAttributes.MAX_STRIKES.get(), 50.0D)
                .add(EpicFightAttributes.MAX_STAMINA.get(), 30.0D)
                .add(EpicFightAttributes.STAMINA_REGEN.get(), 1.5D);
    }
}

