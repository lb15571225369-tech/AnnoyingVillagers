package com.pla.annoyingvillagers.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.procedures.Herobrine3OnTickProcedure;
import com.pla.annoyingvillagers.procedures.Herobrine3OnTouchProcedure;
import com.pla.annoyingvillagers.util.CommonGoals;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

import javax.annotation.Nullable;

public class Herobrine5Entity extends PlayerMobEntity {

    public Herobrine5Entity(EntityType<? extends Herobrine5Entity> type, Level level) {
        super(type, level);
        this.setMaxUpStep(3.0F);
        this.xpReward = 50;
        this.setNoAi(false);
        this.setPersistenceRequired();
    }

    public Herobrine5Entity(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.HEROBRINE_5.get(), level);
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        CommonGoals.registerGoalForHostileNpc(this);
    }

    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    @Override
    public void die(DamageSource damagesource) {
        super.die(damagesource);
        if (this.level() instanceof ServerLevel serverLevel) {
            InfectedPlayerMobEntity corpse = new InfectedPlayerMobEntity(AnnoyingVillagersModEntities.INFECTED_PLAYER_MOB.get(), serverLevel);
            corpse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            String killedName = this.getCustomName().getString();
            corpse.getPersistentData().putString("possessed_by", "herobrine_5");
            corpse.setUsername(killedName);
            corpse.setCustomName(Component.literal(killedName));
            corpse.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()),
                    MobSpawnType.MOB_SUMMONED, null, null);
            this.setInvisible(true);
            this.remove(RemovalReason.KILLED);
            corpse.setItemSlot(EquipmentSlot.HEAD, this.getItemBySlot(EquipmentSlot.HEAD).copy());
            corpse.setItemSlot(EquipmentSlot.CHEST, this.getItemBySlot(EquipmentSlot.CHEST).copy());
            corpse.setItemSlot(EquipmentSlot.LEGS, this.getItemBySlot(EquipmentSlot.LEGS).copy());
            corpse.setItemSlot(EquipmentSlot.FEET, this.getItemBySlot(EquipmentSlot.FEET).copy());
            serverLevel.addFreshEntity(corpse);
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        if (!serverlevelaccessor.isClientSide() && serverlevelaccessor.getServer() != null) {
            String killedName = this.getPersistentData().getString("killed_name");
            if (!killedName.isEmpty()) {
                serverlevelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal(killedName + " has been possessed by §5Herobrine§r."), false);
            } else {
                serverlevelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("§5Herobrine§r has spawned a new possessed body."), false);
            }
        }

        if (!this.level().isClientSide() && this.getServer() != null) {
            try {
                this.getServer().getCommands().getDispatcher().execute(
                        "team add herobrine",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {

            }
        }

        if (!this.level().isClientSide() && this.getServer() != null) {
            try {
                this.getServer().getCommands().getDispatcher().execute(
                        "team modify herobrine friendlyFire false",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {

            }
        }

        if (!this.level().isClientSide() && this.getServer() != null) {
            try {
                this.getServer().getCommands().getDispatcher().execute(
                        "team join herobrine @s",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {

            }
        }
        return spawngroupdata;
    }

    public void baseTick() {
        super.baseTick();
        Herobrine3OnTickProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
    }

    @Override
    public void tick() {
        super.tick();
    }

    public void playerTouch(Player player) {
        super.playerTouch(player);
        Herobrine3OnTouchProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
    }

    public static void init() {
        SpawnPlacements.register((EntityType) AnnoyingVillagersModEntities.HEROBRINE_5.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entitytype, serverlevelaccessor, mobspawntype, blockpos, random) -> {
            return serverlevelaccessor.getRawBrightness(blockpos, 0) <= 8;
        });
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3D);
        builder = builder.add(Attributes.MAX_HEALTH, 40.0D);
        builder = builder.add(Attributes.ARMOR, 25.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 10.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
        return builder;
    }
}