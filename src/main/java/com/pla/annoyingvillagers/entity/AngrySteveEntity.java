package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.spawnhandler.SteveData;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import net.shelmarow.combat_evolution.effect.CEMobEffects;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class AngrySteveEntity extends AVNpc {
    public AngrySteveEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.ANGRY_STEVE.get(), level);
    }

    public AngrySteveEntity(EntityType<AngrySteveEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(3.0F);
        this.xpReward = 8;
        this.setNoAi(false);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setPlaceBlockToParryChance(1.0);
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        CommonGoals.registerGoalForCrazyNpc(this);
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
        if (this.getUnableToDamageCooldown() > 0) {
            return false;
        }

        if (damageSource.getEntity() != null && this.getEnderPearlCooldown() == 0) {
            AVNpc entity = this;

            if (entity.getLivingEntityPatch() != null) {
                entity.getLivingEntityPatch().playAnimationSynchronized(AVAnimations.CASTING_ONE_HAND_BUFF, 0.0F);
            }
            CombatBehaviour.throwEnderPearl(this, (float) new Random().nextDouble(90.0D, 180.0D));

            if (Math.random() <= 0.5D) {
                new DelayedTask(20) {
                    @Override
                    public void run() {
                        if(entity.isAlive()) {
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
    public boolean doHurtTarget(@NotNull Entity pEntity) {
        if (!this.level().isClientSide() && pEntity instanceof LivingEntity living) {
            ArmorUtil.damageArmor(living, new Random().nextInt(1, 5));
        }
        return super.doHurtTarget(pEntity);
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

            Consumer<Integer> dropArrows = (count) -> {
                for (int i = 0; i < count; i++) dropStack.accept(new ItemStack(Items.ARROW));
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

            ItemStack bow = new ItemStack(Items.BOW);
            bow.enchant(Enchantments.POWER_ARROWS, 5);
            bow.enchant(Enchantments.PUNCH_ARROWS, 5);
            damagedStacks.add(bow);

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
            dropArrows.accept(new Random().nextInt(10, 30));

            if (AnnoyingVillagersConfig.PHYSIC_MOD_COMPAT.get()) {
                SteveDeadEntity steveDeadEntity = new SteveDeadEntity(AnnoyingVillagersModEntities.STEVE_DEAD.get(), serverLevel);

                steveDeadEntity.moveTo(this.getX(), this.getY(), this.getZ(), serverLevel.getRandom().nextFloat() * 360.0F, 0.0F);
                steveDeadEntity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(steveDeadEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                this.remove(RemovalReason.KILLED);
                serverLevel.addFreshEntity(steveDeadEntity);
                steveDeadEntity.kill();
            }
        }
    }

    @Override
    protected void implementFirstTick(ServerLevel serverLevel) {
        super.implementFirstTick(serverLevel);
        serverLevel.playSound(
                null,
                this.getX(), this.getY(), this.getZ(),
                AnnoyingVillagersModSounds.STEVE_ANGRY.get(),
                SoundSource.NEUTRAL,
                1.0F, 1.0F
        );
        this.setUnableToDamageCooldown(60);
        if (this.getLivingEntityPatch() != null) {
            this.getLivingEntityPatch().playAnimationSynchronized(AVAnimations.GUARD_BREAK_ATTACK, 0.0F);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            this.addEffect(new MobEffectInstance(CEMobEffects.FULL_STUN_IMMUNITY.get(), 3, 3));
        }
    }

    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverLevelAccessor, @NotNull DifficultyInstance difficultyInstance, @NotNull MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        ItemStack legendarySword = new ItemStack(AnnoyingVillagersModItems.LEGENDARY_SWORD.get());
        legendarySword.enchant(Enchantments.SHARPNESS, 5);
        legendarySword.enchant(Enchantments.SMITE, 5);
        legendarySword.enchant(Enchantments.SWEEPING_EDGE, 5);
        this.setItemInHand(InteractionHand.MAIN_HAND, legendarySword);
        this.setItemSlot(EquipmentSlot.MAINHAND, legendarySword);
        this.setMainWeaponItem(legendarySword);
        TeamUtil.addOrJoinTeam(this, "steve");
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawngroupdata, compoundtag);
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
        builder = builder.add(Attributes.MAX_HEALTH, 250.0D);
        builder = builder.add(Attributes.ARMOR, 50.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
        return builder;
    }
}
