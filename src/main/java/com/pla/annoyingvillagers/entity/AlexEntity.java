package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.spawnhandler.AlexData;
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
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
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
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.*;
import java.util.function.Consumer;


public class AlexEntity extends PathfinderMobInventory {
    private JevEntity jevToProtect;
    private UUID jevUUID;
    private boolean jevDeathMessageSent = false;
    private boolean spawnJev = false;
    private int state = 0;

    public void setProtectingJev(JevEntity jev) {
        this.jevToProtect = jev;
    }

    public void setJevUUID(UUID jevUUID) {
        this.jevUUID = jevUUID;
    }

    public AlexEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.ALEX.get(), level);
    }

    public int getState() {
        return state;
    }

    public AlexEntity(EntityType<AlexEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.8F);
        this.xpReward = 60;
        this.setNoAi(false);
        this.setCustomName(Component.translatable(this.getType().getDescriptionId()));
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, (target) -> jevToProtect != null
                && jevToProtect.isAlive()
                && target != null
                && target.getLastHurtMob() == jevToProtect));
        CommonGoals.registerGoalForNeutralNpc(this);
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (jevUUID != null) {
            tag.putUUID("JevUUID", jevUUID);
        }
        tag.putInt("State", this.state);
        tag.putBoolean("JevDeathMessageSent", jevDeathMessageSent);
        tag.putBoolean("SpawnJev", spawnJev);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("JevUUID")) {
            jevUUID = tag.getUUID("JevUUID");
        }
        state = tag.getInt("State");
        jevDeathMessageSent = tag.getBoolean("JevDeathMessageSent");
        spawnJev = tag.getBoolean("SpawnJev");
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
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.generic.death"));
    }

    public boolean hurt(@NotNull DamageSource damageSource, float f) {
        if (this.getEnderPearlCooldown() == 0) {
            if (Math.random() <= 0.2D && !this.level().isClientSide() && this.getServer() != null) {
                this.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + this.getDisplayName().getString() + "> Are you being serious ?"), false);
            }
            CombatBehaviour.throwEnderPearl(this, 180.0F);
            Entity entity = this;
            if (Math.random() <= 0.2D) {
                new DelayedTask(20) {
                    @Override
                    public void run() {
                        if (entity.isAlive()) {
                            CombatBehaviour.throwEnderPearl(entity, 90.0F);
                        }
                    }
                };
            }
            this.setEnderPearlCooldown();
        }
        if (this.level() instanceof ServerLevel && !damageSource.is(DamageTypes.FELL_OUT_OF_WORLD)) {
            float health = this.getHealth();
            if (health - f <= 0.0F) {
                if (this.state == 0 && !this.getOffhandItem().getItem().equals(Items.TOTEM_OF_UNDYING)) {
                    this.setHealth(1.0F);
                    return super.hurt(damageSource, 1.0F);
                }
            }
        }
        return super.hurt(damageSource, f);
    }

    public void die(@NotNull DamageSource damageSource) {
        super.die(damageSource);
        if (this.level() instanceof ServerLevel serverLevel) {
            if (this.getServer() != null) {
                this.getServer().getPlayerList().broadcastSystemMessage(
                        Component.literal("<" + this.getDisplayName().getString() + "> Damn it !"),
                        false
                );
            }

            final double x = this.getX();
            final double y = this.getY() + 1.0D;
            final double z = this.getZ();

            Consumer<ItemStack> dropStack = (stack) -> {
                ItemEntity drop = new ItemEntity(serverLevel, x, y, z, stack);
                drop.setPickUpDelay(10);
                serverLevel.addFreshEntity(drop);
            };

            List<ItemStack> damagedStacks = new ArrayList<>();

            ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
            sword.enchant(Enchantments.SHARPNESS, 5);
            sword.enchant(Enchantments.FIRE_ASPECT, 2);
            sword.enchant(Enchantments.KNOCKBACK, 2);
            sword.enchant(Enchantments.UNBREAKING, 5);
            damagedStacks.add(sword);

            ItemStack bow = new ItemStack(Items.BOW);
            bow.enchant(Enchantments.PUNCH_ARROWS, 3);
            bow.enchant(Enchantments.POWER_ARROWS, 3);
            bow.enchant(Enchantments.FLAMING_ARROWS, 2);
            damagedStacks.add(bow);

            for (ItemStack stack : damagedStacks) {
                stack.setDamageValue(EquipmentDataLoader.getRandomDamage(stack));
                dropStack.accept(stack);
            }

            ItemStack[] simpleDrops = new ItemStack[] {
                    new ItemStack(Items.BREAD),
                    new ItemStack(Items.GOLDEN_APPLE),
                    new ItemStack(Items.WHEAT),
                    new ItemStack(Items.POISONOUS_POTATO),
                    new ItemStack(Items.GOLD_INGOT),

                    new ItemStack(Items.IRON_INGOT),
                    new ItemStack(Items.DIAMOND),
                    new ItemStack(Items.DIAMOND),

                    new ItemStack(Items.IRON_INGOT),
                    new ItemStack(Items.IRON_INGOT),
                    new ItemStack(Items.IRON_INGOT),
                    new ItemStack(Items.IRON_INGOT),
                    new ItemStack(Items.IRON_INGOT),
                    new ItemStack(Items.ENCHANTED_GOLDEN_APPLE),
                    new ItemStack(Items.GOLDEN_APPLE),
                    new ItemStack(Items.WHITE_BED),
                    new ItemStack(Items.CAKE)
            };

            for (ItemStack stack : simpleDrops) {
                dropStack.accept(stack);
            }

            if (AnnoyingVillagersConfig.PHYSIC_MOD_COMPAT.get()) {
                AlexDeadEntity alexDeadEntity = new AlexDeadEntity(AnnoyingVillagersModEntities.ALEX_DEAD.get(), serverLevel);

                alexDeadEntity.moveTo(this.getX(), this.getY(), this.getZ(), serverLevel.getRandom().nextFloat() * 360.0F, 0.0F);
                alexDeadEntity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(alexDeadEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                this.remove(RemovalReason.KILLED);
                serverLevel.addFreshEntity(alexDeadEntity);
                alexDeadEntity.kill();
            }
        }
    }

    private void spawnJev() {
        if (this.level() instanceof ServerLevel serverLevel) {
            JevEntity jevEntity = new JevEntity(AnnoyingVillagersModEntities.JEV.get(), serverLevel);
            jevEntity.moveTo(this.getX() + new Random().nextDouble(1.0D, 10.0D), this.getY() + new Random().nextDouble(1.0D, 10.0D), this.getZ() + new Random().nextDouble(1.0D, 10.0D), serverLevel.getRandom().nextFloat() * 360.0F, 0.0F);
            jevEntity.setFollowTarget(this);
            jevEntity.setFollowTargetUUID(this.getUUID());
            jevEntity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
            serverLevel.addFreshEntity(jevEntity);

            this.setJevUUID(jevEntity.getUUID());
            this.setProtectingJev(jevEntity);
        }
    }

    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverLevelAccessor, @NotNull DifficultyInstance difficultyInstance, @NotNull MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        if (mobSpawnType == MobSpawnType.NATURAL || mobSpawnType == MobSpawnType.CHUNK_GENERATION) {
            ServerLevel serverLevel = serverLevelAccessor.getLevel();
            AlexData alexData = AlexData.get(serverLevel);

            if (!alexData.tryClaim(serverLevel, this.getUUID())) {
                this.discard();
                return null;
            }
        }

        SpawnGroupData returnSpawnGroupData = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawngroupdata, compoundtag);
        TeamUtil.addOrJoinTeam(this, "alex");

        ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
        sword.enchant(Enchantments.SHARPNESS, 5);
        sword.enchant(Enchantments.FIRE_ASPECT, 2);
        sword.enchant(Enchantments.KNOCKBACK, 2);
        sword.enchant(Enchantments.UNBREAKING, 5);
        this.setItemSlot(EquipmentSlot.MAINHAND, sword);
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.ENDER_PEARL));
        this.setMainWeaponItem(sword);
        this.setOffWeaponItem(new ItemStack(Items.ENDER_PEARL));
        return returnSpawnGroupData;
    }

    @Override
    protected void implementFirstTick(ServerLevel serverLevel) {
        super.implementFirstTick(serverLevel);
        Objects.requireNonNull(this.getServer()).getPlayerList().broadcastSystemMessage(Component.literal("<" + this.getDisplayName().getString() + "> " + "Hah, a loser beneath me"), false);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (!spawnJev) {
                this.spawnJev = true;
                spawnJev();
            }
            if (jevToProtect == null && jevUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(jevUUID);
                if (entity instanceof JevEntity jev) {
                    jevToProtect = jev;
                } else {
                    jevUUID = null;
                }
            }
            if (jevToProtect != null && !jevToProtect.isAlive()) {
                if (!jevDeathMessageSent) {
                    jevDeathMessageSent = true;
                    if (level() instanceof ServerLevel serverLevel) {
                        String[] JEV_DEATH_LINES = {
                                "Jev...? Jev!? No... please no...",
                                "Not you too, Jev...",
                                "They killed him... They actually killed Jev...",
                                "I told you to stay back, Jev...",
                                "You idiot... you weren't supposed to die...",
                                "Damn it, Jev... you were all I had left...",
                                "Rest now, Jev... I'll handle this.",
                                "Heh... even in death, you're still loyal, Jev...",
                                "They’ll pay for this... I swear it, Jev"
                        };

                        String message = JEV_DEATH_LINES[level().getRandom().nextInt(JEV_DEATH_LINES.length)];
                        serverLevel.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + this.getDisplayName().getString() + "> " + message), false);
                    }
                }
                jevToProtect = null;
                jevUUID = null;
            }
            if (this.tickCount % 20 == 0 && this.jevToProtect != null) {
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 30, 1));
                this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 30, 0));
            }
            if (this.tickCount % 20 == 0 && this.state == 1) {
                this.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 30, 1));
            }
            if (this.state == 0
                    && this.getHealth() <= 20
                    && !this.getItemInHand(InteractionHand.OFF_HAND).getItem().equals(Items.TOTEM_OF_UNDYING)) {
                this.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.TOTEM_OF_UNDYING));
            }
        }
    }

    public static boolean canSpawn(EntityType<AlexEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        ServerLevel serverLevel = level.getLevel();
        if (AlexData.get(serverLevel).isOccupied(serverLevel)) {
            return false;
        }
        return PathfinderMob.checkMobSpawnRules(entityType, level, spawnType, position, random);
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        super.remove(reason);
        if (!level().isClientSide && level() instanceof ServerLevel serverLevel &&
                (reason == RemovalReason.KILLED || reason == RemovalReason.DISCARDED)) {
            AlexData.get(serverLevel).releaseIfMatches(serverLevel, this.getUUID());
        }
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.45D);
        builder = builder.add(Attributes.MAX_HEALTH, 50.0D);
        builder = builder.add(Attributes.ARMOR, 20.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 5.0D);
        return builder;
    }
}
