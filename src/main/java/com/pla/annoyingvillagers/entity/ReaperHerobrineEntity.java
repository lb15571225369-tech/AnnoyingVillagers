package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.procedures.Herobrine7OnEntityInitialSpawnProcedure;
import com.pla.annoyingvillagers.procedures.HerobrineTransfromProcedure;
import com.pla.annoyingvillagers.procedures.HerobrineWeaponEffectProcedure;
import com.pla.annoyingvillagers.util.CommonGoals;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;
import java.util.UUID;


public class ReaperHerobrineEntity extends Monster {
    private EnderDragon enderDragon;
    private UUID enderDragonUUID;
    private boolean spawnEnderDragon = false;
    private int breathCooldown = 0;
    private int nextStack = 3;
    private int dragonSummonCooldown = 3600;

    public ReaperHerobrineEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.REAPER_HEROBRINE.get(), level);
    }

    public ReaperHerobrineEntity(EntityType<ReaperHerobrineEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.9F);
        this.xpReward = 300;
        this.setNoAi(false);
        this.setCustomName(Component.literal("§5Reaper Herobrine§r"));
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike) AnnoyingVillagersModItems.ENDER_SLAYER_SCYTHE.get()));
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        CommonGoals.registerGoalForHostileNpc(this);
    }

    public int getCooldownTicks() {
        return this.getPersistentData().getInt("DragonCooldown");
    }

    public void setCooldownTicks(int ticks) {
        this.getPersistentData().putInt("DragonCooldown", ticks);
    }

    public MobType getMobType() {
        return MobType.UNDEFINED;
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
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (enderDragonUUID != null) {
            tag.putUUID("EnderDragonUUID", enderDragonUUID);
        }
        tag.putBoolean("SpawnEnderDragon", spawnEnderDragon);
        tag.putInt("NextStack", nextStack);
        tag.putInt("DragonSummonCooldown", dragonSummonCooldown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("EnderDragonUUID")) {
            enderDragonUUID = tag.getUUID("EnderDragonUUID");
        }
        spawnEnderDragon = tag.getBoolean("SpawnEnderDragon");
        nextStack = tag.contains("NextStack") ? tag.getInt("NextStack") : nextStack;
        dragonSummonCooldown = tag.contains("DragonSummonCooldown") ? tag.getInt("DragonSummonCooldown") : dragonSummonCooldown;
    }

    private void spawnEnderDragon() {
        if (this.level() instanceof ServerLevel levelaccessor) {
            ServerLevel serverlevel = (ServerLevel) levelaccessor;

            EnderDragon dragon = new EnderDragon(EntityType.ENDER_DRAGON, serverlevel);
            dragon.moveTo(this.getX(), this.getY() + 20.0D, this.getZ(), new Random().nextFloat() * 360.0F, 0.0F);
            dragon.finalizeSpawn(serverlevel, serverlevel.getCurrentDifficultyAt(dragon.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);

            dragon.setFightOrigin(BlockPos.containing(this.getX(), this.getY(), this.getZ()));
            dragon.getPhaseManager().setPhase(EnderDragonPhase.HOVERING);

            dragon.addTag("av_dragon");
            dragon.getPersistentData().putUUID("herobrine_uuid", this.getUUID());

            serverlevel.addFreshEntity(dragon);
            try {
                dragon.getServer().getCommands().getDispatcher().execute(
                        "team join herobrine @s",
                        dragon.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {

            }

            this.enderDragonUUID = dragon.getUUID();
            this.enderDragon = dragon;

            if (this.level().getServer() != null) {
                this.level().getServer().getPlayerList().broadcastSystemMessage(Component.literal("<Herobrine> Summon!!!"), false);
            }
        }
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (!pEntity.level().isClientSide()) {
            if (!this.getPersistentData().getBoolean("SecondForm")) {
                this.getPersistentData().putInt("HitCount", (this.getPersistentData().contains("HitCount") ? this.getPersistentData().getInt("HitCount") : 0) + 1);
            }
        }
        return super.doHurtTarget(pEntity);
    }

    @Override
    public void tick() {
        super.tick();
        boolean playSound = false;
        if (!level().isClientSide) {
            if (!spawnEnderDragon) {
                this.spawnEnderDragon = true;
                spawnEnderDragon();
            }
            if (enderDragon == null && enderDragonUUID == null) {
                if (dragonSummonCooldown <=0) {
                    spawnEnderDragon = false;
                } else {
                    dragonSummonCooldown--;
                }
            }
            if (enderDragon == null && enderDragonUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(enderDragonUUID);
                if (entity instanceof EnderDragon dragon) {
                    enderDragon = dragon;
                } else {
                    enderDragon = null;
                }
            }
            if (enderDragon != null && enderDragon.getHealth() <= 50) {
                enderDragon.discard();
                enderDragon = null;
                enderDragonUUID = null;
                if (this.level().getServer() != null) {
                    this.level().getServer().getPlayerList().broadcastSystemMessage(Component.literal("<Herobrine> Return!!!"), false);
                }
                dragonSummonCooldown = 3600;
            }
            if (enderDragon != null && enderDragon.isAlive()) {
                enderDragon.setFightOrigin(BlockPos.containing(this.getX(), this.getY(), this.getZ()));
                enderDragon.getPhaseManager().setPhase(EnderDragonPhase.HOVERING);
                enderDragon.setDragonFight(null);
                LivingEntity target = this.getTarget();
                if (target != null && target.isAlive() && this.getPersistentData().getBoolean("SecondForm")) {
                    if (breathCooldown <= 0) {
                        shootThunderBreathAtTarget(target);
                        breathCooldown = 60 + this.getRandom().nextInt(20);
                    }
                }
            }
            if (breathCooldown > 0) breathCooldown--;

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
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 2));
                this.addEffect(new MobEffectInstance(MobEffects.JUMP, 200, 2));
                setCooldownTicks(200);
                this.getPersistentData().remove("HitCount");
                nextStack = new Random().nextInt(3, 6);
                playSound = true;
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

    private void shootThunderBreathAtTarget(LivingEntity target) {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;
        EnderDragonPart head = enderDragon.head;
        Vec3 headPos = new Vec3(head.getX(), head.getY(), head.getZ());
        DragonBeamEntity beam = new DragonBeamEntity(
                AnnoyingVillagersModEntities.DRAGON_BEAM.get(),
                serverLevel,
                enderDragon,
                target,
                headPos.x, headPos.y, headPos.z,
                100, 2);
        serverLevel.addFreshEntity(beam);
    }

    public boolean hurt(DamageSource damagesource, float f) {
        if (!this.getPersistentData().getBoolean("kick_x")) {
            this.setSprinting(true);
            ReaperHerobrineEntity entity = this;
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

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        if (this.enderDragon != null) {
            this.enderDragon.discard();
        }
//        ReaperHerobrineOnDeathProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
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
        SpawnPlacements.register((EntityType) AnnoyingVillagersModEntities.REAPER_HEROBRINE.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, (entitytype, serverlevelaccessor, mobspawntype, blockpos, random) -> {
            return serverlevelaccessor.getRawBrightness(blockpos, 0) <= 8;
        });
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35D);
        builder = builder.add(Attributes.MAX_HEALTH, 230.0D);
        builder = builder.add(Attributes.ARMOR, 20.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 5.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
        return builder;
    }
}
