package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class InfectedChrisEntity extends PathfinderMob {

    public InfectedChrisEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.INJECTED_CHRIS.get(), level);
    }

    public InfectedChrisEntity(EntityType<InfectedChrisEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(0.6F);
        this.xpReward = 7;
        this.setNoAi(true);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(true);
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

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft","entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft","entity.generic.death"));
    }

    public void die(@NotNull DamageSource damageSource) {
        super.die(damageSource);
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        LevelAccessor levelAccessor = this.level();
        if (levelAccessor instanceof ServerLevel serverLevel) {
            Item[] items = new Item[] {
                    AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get().asItem(), AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get().asItem(),
                    AnnoyingVillagersModItems.BEDROCK_WEAPON.get(), AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get().asItem(), Blocks.OAK_SIGN.asItem(), Blocks.OBSIDIAN.asItem(), Blocks.OBSIDIAN.asItem(), Items.NETHERITE_INGOT, Items.ENDER_PEARL, Items.ENCHANTED_GOLDEN_APPLE, Items.ENDER_EYE, Items.ENDER_EYE, AnnoyingVillagersModItems.ENCHANTED_ENDER_PEARL.get()
            };

            for (Item item : items) {
                ItemEntity drop = new ItemEntity(serverLevel, x, y + 1.0D, z, new ItemStack(item));
                drop.setPickUpDelay(10);
                serverLevel.addFreshEntity(drop);
            }

            if (AnnoyingVillagersConfig.PHYSIC_MOD_COMPAT.get()) {
                InfectedChrisDeadEntity deadEntity = new InfectedChrisDeadEntity(AnnoyingVillagersModEntities.INFECTED_CHRIS_DEAD.get(), serverLevel);
                deadEntity.moveTo(x, y, z, serverLevel.getRandom().nextFloat() * 360.0F, 0.0F);
                deadEntity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(deadEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                this.remove(RemovalReason.KILLED);
                serverLevel.addFreshEntity(deadEntity);
                deadEntity.setPose(Pose.DYING);
                deadEntity.kill();
            }
        }
    }

    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverLevelAccessor, @NotNull DifficultyInstance difficultyInstance, @NotNull MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        SpawnGroupData returnSpawnGroupData = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawngroupdata, compoundtag);

        if (!this.level().isClientSide()) {
            this.addEffect(new MobEffectInstance(AnnoyingVillagersModMobEffects.HEROBRINE.get(), 3000, 1));
        }
        return returnSpawnGroupData;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide() && this.tickCount % 20 == 0) {
            this.addEffect(new MobEffectInstance(AnnoyingVillagersModMobEffects.HEROBRINE.get(), 30, 0, false, false));
        }
    }

    public boolean isPushable() {
        return false;
    }

    protected void doPush(@NotNull Entity entity) {}

    protected void pushEntities() {}

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.26D);
        builder = builder.add(Attributes.MAX_HEALTH, 10.0D);
        builder = builder.add(Attributes.ARMOR, 0.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 1.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32.0D);
        return builder;
    }
}
