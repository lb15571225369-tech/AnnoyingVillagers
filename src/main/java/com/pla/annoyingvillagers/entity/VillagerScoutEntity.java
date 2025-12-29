package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.*;
import com.pla.annoyingvillagers.clazz.PathfinderMobInventory;
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
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;


public class VillagerScoutEntity extends PathfinderMobInventory{
    public VillagerScoutEntity(SpawnEntity spawnentity, Level level) {
        this(AnnoyingVillagersModEntities.VILLAGER_SCOUT.get(), level);
    }

    public VillagerScoutEntity(EntityType<VillagerScoutEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.0F);
        this.xpReward = 8;
        this.setNoAi(false);
        this.setPersistenceRequired();
        this.setPlaceBlockToParryChance(0.4);
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

    public SoundEvent getHurtSound(@NotNull DamageSource damagesource) {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.villager.hurt"));
    }

    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.villager.death"));
    }

    @Override public boolean removeWhenFarAway(double d0) {
        return false;
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

            this.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.IRON_SWORD));
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
                            CombatBehaviour.throwEnderPearl(entity, 90.0F);
                        }
                    }
                };
            }
            this.setEnderPearlCooldown();
        }
        return super.hurt(damageSource, f);
    }

    public void die(@NotNull DamageSource damagesource) {
        super.die(damagesource);
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

                    new ItemStack(Items.ENDER_PEARL),
                    new ItemStack(Items.ENDER_PEARL),
                    new ItemStack(Items.ENDER_PEARL),

                    new ItemStack(Items.IRON_SWORD),

                    new ItemStack(Items.ARROW),
                    new ItemStack(Items.ARROW),
                    new ItemStack(Items.ARROW),
                    new ItemStack(Items.ARROW),

                    new ItemStack(Items.GOLD_INGOT),
                    new ItemStack(Items.GOLD_INGOT),
                    new ItemStack(Items.GOLD_INGOT),

                    new ItemStack(Blocks.OAK_PLANKS)
            };

            for (ItemStack stack : drops) {
                dropStack.accept(stack);
            }

            if (Math.random() <= 0.11D) {
                Entity entity = this;
                try {
                    Objects.requireNonNull(entity.getServer()).getCommands().getDispatcher().execute(
                            "summon firework_rocket ~ ~10 ~ {LifeTime:10,FireworksItem:{id:firework_rocket,Count:1,tag:{Fireworks:{Explosions:[{Type:3,Colors:[0],Flicker:1}]}},display:{Name:\"Black Creeper Firework\"}}}",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException ignored) {
                }

                serverLevel.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity.getDisplayName().getString() + "> Requesting backup!"), false);

                new DelayedTask(400) {
                    public void run() {
                        serverLevel.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + Component.translatable("entity.annoyingvillagers.villager_scout").getString() + "> Reinforcements have arrived!"), false);

                        try {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "summon annoyingvillagers:villager_scout ^ ^ ^10",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException ignored) {
                        }

                        try {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "summon annoyingvillagers:villager_scout ^ ^ ^15",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException ignored) {
                        }

                        try {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "summon annoyingvillagers:villager_scout_captain ^10 ^ ^20",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException ignored) {
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

    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverLevelAccessor, @NotNull DifficultyInstance difficultyInstance, @NotNull MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        SpawnGroupData returnSpawnGroupData = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);

        TeamUtil.addOrJoinTeam(this, "villagers");

        if (new Random().nextBoolean()) {
            this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BOW));
        } else {
            this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.IRON_SWORD));
            if (new Random().nextBoolean()) {
                this.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.IRON_SWORD));
            } else {
                this.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.ENDER_PEARL));
            }
            if (new Random().nextBoolean()) {
                this.setUseBow(false);
            }
        }

        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(AnnoyingVillagersModItems.VILLAGER_SCOUT_HELMET_FIX.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(AnnoyingVillagersModItems.CLASSICGOLDENA_CHESTPLATE.get()));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(AnnoyingVillagersModItems.CLASSICGOLDENA_LEGGINGS.get()));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(AnnoyingVillagersModItems.CLASSICGOLDENA_BOOTS.get()));
        this.setMainWeaponItem(this.getMainHandItem().copy());
        this.setOffWeaponItem(this.getOffWeaponItem().copy());

        return returnSpawnGroupData;
    }

    @Override
    protected void implementFirstTick(ServerLevel serverLevel) {
        super.implementFirstTick(serverLevel);
        if (new Random().nextInt() <= 0.3D) {
            RidingUtil.rideRandomAnimal(serverLevel, this);
        }
    }

    public static boolean canSpawn(EntityType<VillagerScoutEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        return PathfinderMob.checkMobSpawnRules(entityType, level, spawnType, position, random);
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.45D);
        builder = builder.add(Attributes.MAX_HEALTH, 20.0D);
        builder = builder.add(Attributes.ARMOR, 5.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
        return builder;
    }
}
