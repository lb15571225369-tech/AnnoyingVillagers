package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import net.shelmarow.combat_evolution.effect.CEMobEffects;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

public class InfectedTheMostMoistBurrit0Entity extends PathfinderMob {
    final LivingEntityPatch<?> livingEntityPatch =  EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);

    public InfectedTheMostMoistBurrit0Entity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.INFECTED_THEMOSTMOISTBURRIT0.get(), level);
    }

    public InfectedTheMostMoistBurrit0Entity(EntityType<InfectedTheMostMoistBurrit0Entity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(0.6F);
        this.xpReward = 0;
        this.setNoAi(true);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(true);
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_HELMET.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_CHESTPLATE.get()));
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
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

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide() && this.livingEntityPatch != null) {
            this.addEffect(new MobEffectInstance(AnnoyingVillagersModMobEffects.HEROBRINE.get(), 2, 0, false, false));
            this.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 2, 0, false, false));
            this.addEffect(new MobEffectInstance(CEMobEffects.FULL_STUN_IMMUNITY.get(), 2, 0, false, false));
            livingEntityPatch.playAnimationSynchronized(AVAnimations.DEATH_IDLE, 0.0F);
        }
    }

    public void die(@NotNull DamageSource damageSource) {
        super.die(damageSource);
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.getServer().getPlayerList().broadcastSystemMessage(
                    Component.translatable("subtitles.herobrine_clone_die"),
                    false
            );

            Item[] items = new Item[] {
                    AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                    AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                    AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                    AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                    AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                    Items.ENDER_EYE,
                    Items.ENDER_EYE,
                    Items.SPLASH_POTION,
                    Blocks.DIAMOND_BLOCK.asItem(),
                    Items.IRON_SWORD,
                    AnnoyingVillagersModItems.ENCHANTED_ENDER_PEARL.get(),
                    AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get()
            };

            for (Item item : items) {
                ItemEntity drop = new ItemEntity(serverLevel, x, y + 1.0D, z, new ItemStack(item));
                drop.setPickUpDelay(10);
                serverLevel.addFreshEntity(drop);
            }
        }
        this.discard();
    }

    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverLevelAccessor, @NotNull DifficultyInstance difficultyInstance, @NotNull MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        if (!this.level().isClientSide()) {
            TeamUtil.addOrJoinTeam(this, "herobrine");
        }
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

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
