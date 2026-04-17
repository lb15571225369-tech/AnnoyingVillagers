package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;


public class GreenVillagerGeneralEntity extends AVNpc {

    public GreenVillagerGeneralEntity(SpawnEntity spawnentity, Level level) {
        this(AnnoyingVillagersModEntities.PURPLE_VILLAGER_GENERAL.get(), level);
    }

    public GreenVillagerGeneralEntity(EntityType<GreenVillagerGeneralEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.0F);
        this.xpReward = 0;
        this.setNoAi(false);
        this.setPlaceBlockToParryChance(0.7);
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        CommonGoals.registerGoalForVillagerKnightNpc(this);
    }

    public @NotNull MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public SoundEvent getAmbientSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.villager.ambient"));
    }

    public SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.villager.hurt"));
    }

    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.villager.death"));
    }

    public boolean hurt(@NotNull DamageSource damageSource, float f) {
        LivingEntityPatch<?> livingEntityPatch = this.getLivingEntityPatch();
        AssetAccessor<? extends StaticAnimation> dynamicAnimation = Animations.EMPTY_ANIMATION;
        if (livingEntityPatch != null) {
            AnimationPlayer animationPlayer = livingEntityPatch.getAnimator().getPlayerFor(null);
            if (animationPlayer != null) {
                dynamicAnimation = animationPlayer.getRealAnimation();
            }
        }

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
                new DelayedTask(40) {
                    public void run() {
                        if (entity.isAlive()) {
                            if (entity.getLivingEntityPatch() != null) {
                                entity.getLivingEntityPatch().playAnimationSynchronized(AVAnimations.CASTING_ONE_HAND_BUFF, 0.0F);
                            }
                            CombatBehaviour.throwEnderPearl(entity, 0.0F);
                        }
                    }
                };
            }

            new DelayedTask(150) {
                @Override
                public void run() {
                    if (entity.isAlive()) {
                        entity.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.ENDER_PEARL));
                    }
                }
            };

            if (Math.random() <= 0.2D) {
                new DelayedTask(20) {
                    @Override
                    public void run() {
                        if (entity.getLivingEntityPatch() != null) {
                            entity.getLivingEntityPatch().playAnimationSynchronized(AVAnimations.CASTING_ONE_HAND_BUFF, 0.0F);
                        }
                        CombatBehaviour.throwEnderPearl(entity, 180.0F);
                    }
                };
            }

            if (Math.random() <= 0.1D) {
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

    public void die(@NotNull DamageSource damageSource) {
        super.die(damageSource);

        if (this.level() instanceof ServerLevel serverLevel) {
            final double x = this.getX();
            final double y = this.getY() + 1.0D;
            final double z = this.getZ();

            Consumer<ItemStack> dropStack = (stack) -> {
                ItemEntity drop = new ItemEntity(serverLevel, x, y, z, stack);
                drop.setPickUpDelay(10);
                serverLevel.addFreshEntity(drop);
            };

            ItemStack[] drops = new ItemStack[] {
                    new ItemStack(Items.APPLE),
                    new ItemStack(Items.APPLE),

                    new ItemStack(Items.BREAD),

                    new ItemStack(Items.EMERALD),
                    new ItemStack(Items.EMERALD),

                    new ItemStack(Items.ENDER_PEARL),
                    new ItemStack(Items.ENDER_PEARL),
                    new ItemStack(Items.ENDER_PEARL),
                    new ItemStack(Items.ENDER_PEARL),

                    new ItemStack(Items.ENCHANTED_GOLDEN_APPLE),
                    new ItemStack(Items.ENCHANTED_GOLDEN_APPLE),

                    new ItemStack(Items.DIAMOND_SWORD),

                    new ItemStack(Items.ARROW),
                    new ItemStack(Items.ARROW),
                    new ItemStack(Items.ARROW),
                    new ItemStack(Items.ARROW),

                    new ItemStack(Items.GOLD_INGOT),
                    new ItemStack(Items.GOLD_INGOT),
                    new ItemStack(Items.GOLD_INGOT),

                    new ItemStack(Blocks.OAK_PLANKS),

                    new ItemStack(Items.EMERALD),
                    new ItemStack(Items.EMERALD),
                    new ItemStack(Items.EMERALD),
                    new ItemStack(Items.EMERALD)
            };

            for (ItemStack stack : drops) {
                dropStack.accept(stack);
            }
        }
    }

    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverLevelAccessor, @NotNull DifficultyInstance difficultyInstance, @NotNull MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        SpawnGroupData returnSpawnGroupData = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);

        TeamUtil.addOrJoinTeam(this, "villagers");

        this.setItemSlot(EquipmentSlot.MAINHAND, VillagerUtil.generateMainWeaponItem());
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.ENDER_PEARL));
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(AnnoyingVillagersModItems.GREEN_VILLAGER_GENERAL_HELMET_FIX.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(AnnoyingVillagersModItems.PURPLE_VILLAGER_GENERAL_CHESTPLATE.get()));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(AnnoyingVillagersModItems.VILLAGER_GENERAL_LEGGINGS.get()));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(AnnoyingVillagersModItems.VILLAGER_GENERAL_BOOTS.get()));
        this.setMainWeaponItem(this.getMainHandItem().copy());
        this.setOffWeaponItem(this.getOffWeaponItem().copy());

        if (new Random().nextBoolean()) {
            this.setUseBow(false);
        }

        return returnSpawnGroupData;
    }

    @Override
    protected void implementFirstTick(ServerLevel serverLevel) {
        super.implementFirstTick(serverLevel);
        if (new Random().nextInt() <= 0.3D) {
            if (new Random().nextInt() <= 0.3D) {
                RidingUtil.rideRandomAnimal(serverLevel, this);
            }
        }
    }

    public static boolean canSpawn(EntityType<GreenVillagerGeneralEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        return PathfinderMob.checkMobSpawnRules(entityType, level, spawnType, position, random);
    }

    public static Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.45D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ARMOR, 30.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(EpicFightAttributes.IMPACT.get(), 2.0D)
                .add(EpicFightAttributes.ARMOR_NEGATION.get(), 15.0D)
                .add(EpicFightAttributes.STUN_ARMOR.get(), 20.0D)
                .add(EpicFightAttributes.MAX_STRIKES.get(), 50.0D)
                .add(EpicFightAttributes.MAX_STAMINA.get(), 30.0D)
                .add(EpicFightAttributes.STAMINA_REGEN.get(), 1.5D);
    }
}
