package com.pla.annoyingvillagers.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.CommonGoals;
import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.RidingUtil;
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

    @Override
    protected boolean hasEnderPearlCounter() {
        return true;
    }

    @Override
    protected void beforeEnderPearlCounter(@NotNull DamageSource damageSource) {
        this.swapOffhandDuringEnderPearlCounter(new ItemStack(Items.DIAMOND_SWORD), 120);
    }

    @Override
    protected ItemStack getEnderPearlCounterRestoreOffhandItem() {
        return new ItemStack(Items.ENDER_PEARL);
    }

    @Override
    protected void doEnderPearlCounterPattern(@NotNull DamageSource damageSource) {
        this.doSteveStyleEnderPearlCounter();
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

        if (new Random().nextDouble() <= 0.3D) {
            RidingUtil.rideRandomAnimal(serverLevel, this);
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
                    new ItemStack(Items.ARROW),
                    new ItemStack(Items.ARROW),
                    new ItemStack(Items.ARROW),

                    new ItemStack(AnnoyingVillagersModItems.ENCHANTED_ARROW.get()),
                    new ItemStack(AnnoyingVillagersModItems.ENCHANTED_ARROW.get()),
                    new ItemStack(AnnoyingVillagersModItems.ENCHANTED_ARROW.get()),
                    new ItemStack(AnnoyingVillagersModItems.ENCHANTED_ARROW.get()),
                    new ItemStack(AnnoyingVillagersModItems.ENCHANTED_ARROW.get()),
                    new ItemStack(AnnoyingVillagersModItems.ENCHANTED_ARROW.get()),

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
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
        return builder;
    }
}

