package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.compat.aaa_particles.EnderGlaiveExplosionParticleEmitterInfo;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.item.EnderGlaiveItem;
import com.pla.annoyingvillagers.network.ClientboundGlaiveExplosionFx;
import com.pla.annoyingvillagers.network.ClientboundMuteExplosionAtPos;
import com.pla.annoyingvillagers.procedures.GlaiveHerobrineOnDeathProcedure;
import com.pla.annoyingvillagers.procedures.Herobrine7OnEntityInitialSpawnProcedure;
import com.pla.annoyingvillagers.procedures.HerobrineTransfromProcedure;
import com.pla.annoyingvillagers.procedures.HerobrineWeaponEffectProcedure;
import com.pla.annoyingvillagers.util.CommonGoals;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.util.SnakeBladeHit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

import java.util.Random;


public class GlaiveHerobrineEntity extends Monster {
    private int nextStack = 3;

    public GlaiveHerobrineEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.GLAIVE_HEROBRINE.get(), level);
    }

    public GlaiveHerobrineEntity(EntityType<GlaiveHerobrineEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.9F);
        this.xpReward = 300;
        this.setNoAi(false);
        this.setCustomName(Component.literal("§5Glaive Herobrine§r"));
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike) AnnoyingVillagersModItems.ENDER_GLAIVE.get()));
    }

    public int getCooldownTicks() {
        return this.getPersistentData().getInt("SwordCooldown");
    }

    public void setCooldownTicks(int ticks) {
        this.getPersistentData().putInt("SwordCooldown", ticks);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        pCompound.putInt("NextStack", nextStack);

    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        nextStack = pCompound.contains("NextStack") ? pCompound.getInt("NextStack") : nextStack;
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (!pEntity.level().isClientSide()) {
            this.getPersistentData().putInt("HitCount", (this.getPersistentData().contains("HitCount") ? this.getPersistentData().getInt("HitCount") : 0) + 1);
        }
        return super.doHurtTarget(pEntity);
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

    public boolean hurt(DamageSource damagesource, float f) {
        if (!this.getPersistentData().getBoolean("kick_x")) {
            this.setSprinting(true);
            GlaiveHerobrineEntity entity = this;
            new DelayedTask(10) {
                @Override
                public void run() {
                    entity.setSprinting(false);
                }
            };
            if (Math.random() <= 0.5D && this instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)this;

                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 1, 1, false, false));
                }
            }
        }
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.WITHER)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.WITHER_SKULL)) return false;
        if (damagesource.is(DamageTypes.DRAGON_BREATH)) return false;
        if (damagesource.getDirectEntity() instanceof AbstractArrow) return false;
        return super.hurt(damagesource, f);
    }

    @Override
    public void tick() {
        super.tick();
        boolean playSound = false;
        ItemStack itemStack = this.getMainHandItem();

        if (this.getPersistentData().getBoolean("SecondForm") && this.getPersistentData().getInt("HitCount") >= 3) {
            GlaiveHerobrineEntity glaiveHerobrineEntity = this;
            if (itemStack.getItem() instanceof EnderGlaiveItem enderGlaiveItem) {
                try {
                    if (!this.level().isClientSide()) {
                        this.getServer().getCommands().getDispatcher().execute(
                                "indestructible @s play \"wom:biped/combat/agony_auto_1\" 0 1",
                                this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    }
                    new DelayedTask(3) {
                        @Override
                        public void run() {
                            if (!glaiveHerobrineEntity.level().isClientSide()) {
                                Vec3 tipPos = enderGlaiveItem.getJointWithTranslation(
                                        glaiveHerobrineEntity,
                                        new Vec3f(0.0F, 0.0F, 0.0F),
                                        Armatures.BIPED.toolR,
                                        4.3F,
                                        2.3F
                                );
                                BlockPos mutePos = BlockPos.containing(tipPos);
                                AnnoyingVillagers.PACKET_HANDLER.send(
                                        PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> glaiveHerobrineEntity),
                                        new ClientboundMuteExplosionAtPos(mutePos, 4)
                                );
                                glaiveHerobrineEntity.level().explode(glaiveHerobrineEntity, tipPos.x, tipPos.y, tipPos.z,
                                        2.0F, true, Level.ExplosionInteraction.TNT);
                                Vec3 glaivePos = enderGlaiveItem.getJointWithTranslation(glaiveHerobrineEntity, new Vec3f(0,0,0),
                                        Armatures.BIPED.toolR, 1.3F, 2.3F);
                                Vec3 explosionPos = enderGlaiveItem.getJointWithTranslation(glaiveHerobrineEntity, new Vec3f(0,0,0),
                                        Armatures.BIPED.toolR, 10.3F, 2.3F);
                                AnnoyingVillagers.PACKET_HANDLER.send(
                                        PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> glaiveHerobrineEntity),
                                        new ClientboundGlaiveExplosionFx(glaivePos, explosionPos)
                                );
                                glaiveHerobrineEntity.level().playSound((Player) null, new BlockPos((int) explosionPos.x, (int) explosionPos.y, (int) explosionPos.z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:ender_shot")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                glaiveHerobrineEntity.getPersistentData().putInt("HitCount", glaiveHerobrineEntity.getPersistentData().contains("HitCount") ? glaiveHerobrineEntity.getPersistentData().getInt("HitCount") - 3 : 0);
                            }
                        }
                    };
                } catch (CommandSyntaxException e) {

                }
            }
        }

        if (!this.level().isClientSide()) {
            if (this.getPersistentData().getBoolean("SecondForm")) {
                HerobrineWeaponEffectProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);

                int cooldown = getCooldownTicks();
                if (cooldown > 0) {
                    setCooldownTicks(cooldown - 1);
                } else {
                    this.getPersistentData().remove("SecondForm");
                }
            } else if (!this.getPersistentData().getBoolean("SecondForm") && this.getPersistentData().getInt("HitCount") >= nextStack) {
                this.getPersistentData().putBoolean("SecondForm", true);
                setCooldownTicks(200);
                this.getPersistentData().putInt("HitCount", 3);
                nextStack = new Random().nextInt(3, 6);
                playSound = true;
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 2));
                this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 2));
                this.addEffect(new MobEffectInstance(MobEffects.JUMP, 200, 2));
            }
        }
        if (playSound) {
            if (!this.level().isClientSide()) {
                this.level().playSound((Player) null, new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ()), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:second_form_release")), SoundSource.NEUTRAL, 1.0F, 1.0F);
            } else {
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:second_form_release")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
            }
        }
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        GlaiveHerobrineOnDeathProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        SpawnGroupData spawngroupdata1 = super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);

        Herobrine7OnEntityInitialSpawnProcedure.execute(serverlevelaccessor, this.getX(), this.getY(), this.getZ(), this);
        return spawngroupdata1;
    }

    public void awardKillScore(Entity entity, int i, DamageSource damagesource) {
        super.awardKillScore(entity, i, damagesource);
        HerobrineTransfromProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), entity, this);
    }

    public void baseTick() {
        super.baseTick();
    }

    public static void init() {
        SpawnPlacements.register((EntityType) AnnoyingVillagersModEntities.GLAIVE_HEROBRINE.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, (entitytype, serverlevelaccessor, mobspawntype, blockpos, random) -> {
            return serverlevelaccessor.getRawBrightness(blockpos, 0) <= 8;
        });
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35D);
        builder = builder.add(Attributes.MAX_HEALTH, 240.0D);
        builder = builder.add(Attributes.ARMOR, 23.9D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 4.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
        return builder;
    }
}
