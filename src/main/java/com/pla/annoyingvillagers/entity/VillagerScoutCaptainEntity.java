package com.pla.annoyingvillagers.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.util.CommonGoals;
import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import com.pla.annoyingvillagers.util.TeamUtil;
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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Sheep;
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
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;


public class VillagerScoutCaptainEntity extends AVNpc {
    public VillagerScoutCaptainEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN.get(), level);
    }

    public VillagerScoutCaptainEntity(EntityType<VillagerScoutCaptainEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.5F);
        this.xpReward = 0;
        this.setNoAi(false);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setPlaceBlockToParryChance(0.5);
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

    public boolean removeWhenFarAway(double d0) {
        return false;
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

    public boolean hurt(DamageSource damageSource, float f) {
        AssetAccessor<? extends DynamicAnimation> dynamicAnimation = Objects.requireNonNull(this.getLivingEntityPatch().getAnimator().getPlayerFor(null)).getAnimation();

        if (damageSource.getEntity() != null && this.getEnderPearlCooldown() == 0
                && !EpicfightUtil.isLongHitAnimation(dynamicAnimation)
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

            this.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.DIAMOND_SWORD));
            new DelayedTask(120) {
                public void run() {
                    if (entity.isAlive()) {
                        entity.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.ENDER_PEARL));
                    }
                }
            };

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

    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverLevelAccessor, @NotNull DifficultyInstance difficultyInstance, @NotNull MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        SpawnGroupData returnSpawnGroupData = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);

        TeamUtil.addOrJoinTeam(this, "villagers");

        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
        if (new Random().nextBoolean()) {
            this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.ENDER_PEARL));
        }

        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(AnnoyingVillagersModItems.VILLAGER_SCOUT_HELMET_FIX.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.DIAMOND_LEGGINGS));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
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
            Sheep sheep = EntityType.SHEEP.create(serverLevel);
            if (sheep != null) {
                sheep.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                sheep.setPersistenceRequired();
                sheep.finalizeSpawn(
                        serverLevel,
                        serverLevel.getCurrentDifficultyAt(this.blockPosition()),
                        MobSpawnType.MOB_SUMMONED,
                        null,
                        null
                );
                serverLevel.addFreshEntity(sheep);
                this.startRiding(sheep);
                sheep.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 99999, 1, false, false));
                sheep.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 99999, 1, false, false));
                sheep.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 99999, 9, false, false));
            }
        }
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
                    new ItemStack(Blocks.OAK_PLANKS),

                    new ItemStack(Blocks.DIRT),
                    new ItemStack(Blocks.DIRT),

                    new ItemStack(Items.BREAD),
                    new ItemStack(Items.BREAD),
                    new ItemStack(Items.BREAD),

                    new ItemStack(Items.GOLD_INGOT),
                    new ItemStack(Items.GOLD_INGOT),
                    new ItemStack(Items.GOLD_INGOT),

                    new ItemStack(Items.IRON_INGOT),

                    new ItemStack(Items.EMERALD),
                    new ItemStack(Items.EMERALD),
                    new ItemStack(Items.EMERALD),

                    new ItemStack(Blocks.WHEAT),
                    new ItemStack(Blocks.WHEAT),

                    new ItemStack(Items.ENDER_PEARL),
                    new ItemStack(Items.ENDER_PEARL),
                    new ItemStack(Items.ENDER_PEARL),

                    new ItemStack(Items.ENCHANTED_GOLDEN_APPLE),

                    new ItemStack(Items.ENDER_PEARL),

                    new ItemStack(Items.GOLDEN_APPLE),
                    new ItemStack(Items.GOLDEN_APPLE),

                    new ItemStack(Items.IRON_PICKAXE),
                    new ItemStack(Items.IRON_INGOT),

                    new ItemStack(Items.ARROW),

                    new ItemStack(Items.APPLE),
                    new ItemStack(Items.APPLE),

                    new ItemStack(Items.ARROW),
                    new ItemStack(Items.ARROW),
                    new ItemStack(Items.ARROW),
                    new ItemStack(Items.ARROW),

                    new ItemStack(Items.APPLE),
                    new ItemStack(Items.APPLE)
            };

            for (ItemStack stack : drops) {
                dropStack.accept(stack);
            }

            if (Math.random() <= 0.3D) {
                Entity entity = this;

                try {
                    Objects.requireNonNull(this.getServer()).getCommands().getDispatcher().execute(
                            "summon firework_rocket ~ ~10 ~ {LifeTime:10,FireworksItem:{id:firework_rocket,Count:1,tag:{Fireworks:{Explosions:[{Type:3,Colors:[0],Flicker:1}]}},display:{Name:\"Black Creeper Firework\"}}}",
                            this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException ignored) {
                }

                serverLevel.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity.getDisplayName().getString() + "> We've encountered a skilled enemy, requesting support!"), false);

                new DelayedTask(400) {
                    public void run() {
                        serverLevel.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + Component.translatable("entity.annoyingvillagers.villager_scout").getString() + "> Reinforcements have arrived!"), false);

                        try {
                            Objects.requireNonNull(entity.getServer()).getCommands().getDispatcher().execute(
                                    "summon annoyingvillagers:villager_scout ^ ^ ^10",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException ignored) {
                        }

                        try {
                            Objects.requireNonNull(entity.getServer()).getCommands().getDispatcher().execute(
                                    "summon annoyingvillagers:villager_scout ^ ^ ^15",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException ignored) {
                        }

                        double chance = new Random().nextDouble(0.0, 1.0);
                        if (chance < 0.2) {
                            try {
                                Objects.requireNonNull(entity.getServer()).getCommands().getDispatcher().execute(
                                        "summon annoyingvillagers:red_villager_general ^10 ^ ^20",
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException ignored) {
                            }
                        } else if (chance < 0.4) {
                            try {
                                Objects.requireNonNull(entity.getServer()).getCommands().getDispatcher().execute(
                                        "summon annoyingvillagers:blue_villager_general ^10 ^ ^20",
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException ignored) {
                            }
                        } else if (chance < 0.6) {
                            try {
                                Objects.requireNonNull(entity.getServer()).getCommands().getDispatcher().execute(
                                        "summon annoyingvillagers:green_villager_general ^10 ^ ^20",
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException ignored) {
                            }
                        } else if (chance < 0.8) {
                            try {
                                Objects.requireNonNull(entity.getServer()).getCommands().getDispatcher().execute(
                                        "summon annoyingvillagers:purple_villager_general ^10 ^ ^20",
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException ignored) {
                            }
                        }
                    }
                };
            }

            if (AnnoyingVillagersConfig.PHYSIC_MOD_COMPAT.get()) {
                VillagerScoutDeadEntity deadEntity = new VillagerScoutDeadEntity(AnnoyingVillagersModEntities.VILLAGER_SCOUT_DEAD.get(), serverLevel);
                deadEntity.moveTo(this.getX(), this.getY(), this.getZ(), serverLevel.getRandom().nextFloat() * 360.0F, 0.0F);
                deadEntity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(deadEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                this.remove(Entity.RemovalReason.KILLED);
                serverLevel.addFreshEntity(deadEntity);
                deadEntity.kill();
            }
        }
    }

    public static boolean canSpawn(EntityType<VillagerScoutCaptainEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        return PathfinderMob.checkMobSpawnRules(entityType, level, spawnType, position, random);
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.45D);
        builder = builder.add(Attributes.MAX_HEALTH, 20.0D);
        builder = builder.add(Attributes.ARMOR, 6.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 1.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
        return builder;
    }
}

