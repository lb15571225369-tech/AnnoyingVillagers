package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.procedures.HerobrineWeaponEffectProcedure;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;


public class ReaperHerobrineEntity extends HerobrineMob {
    private HerobrineDragonEntity herobrineDragon;
    private UUID herobrineDragonUUID;
    private boolean spawnHerobrineDragon = false;
    private int dragonSummonCooldown = 3600;

    public ReaperHerobrineEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.REAPER_HEROBRINE.get(), level);
    }

    public ReaperHerobrineEntity(EntityType<ReaperHerobrineEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.9F);
        this.xpReward = 300;
        this.setNoAi(false);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AnnoyingVillagersModItems.ENDER_SLAYER_SCYTHE.get()));
        this.setChatName(this.getDisplayName().getString());
    }

    public int getCooldownTicks() {
        return this.getPersistentData().getInt("DragonCooldown");
    }

    public void setCooldownTicks(int ticks) {
        this.getPersistentData().putInt("DragonCooldown", ticks);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (herobrineDragonUUID != null) {
            tag.putUUID("HerobrineDragonUUID", herobrineDragonUUID);
        }
        tag.putBoolean("SpawnHerobrineDragon", spawnHerobrineDragon);
        tag.putInt("DragonSummonCooldown", dragonSummonCooldown);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("HerobrineDragonUUID")) {
            herobrineDragonUUID = tag.getUUID("HerobrineDragonUUID");
        }
        spawnHerobrineDragon = tag.getBoolean("SpawnHerobrineDragon");
        dragonSummonCooldown = tag.contains("DragonSummonCooldown") ? tag.getInt("DragonSummonCooldown") : dragonSummonCooldown;
    }

    @Override
    public double getMyRidingOffset() {
        return -2.5D;
    }

    private void spawnHerobrineDragon() {
        if (this.level() instanceof ServerLevel serverLevel) {
            HerobrineDragonEntity dragon = new HerobrineDragonEntity(AnnoyingVillagersModEntities.HEROBRINE_DRAGON.get(), serverLevel);
            dragon.moveTo(this.getX(), this.getY() + 20.0D, this.getZ(), this.getRandom().nextFloat() * 360.0F, 0.0F);
            dragon.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(dragon.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);

            dragon.setPersistenceRequired();

            dragon.setSummoner(this);
            dragon.setSummonerUUID(this.getUUID());

            serverLevel.addFreshEntity(dragon);
            TeamUtil.addOrJoinTeam(dragon, "herobrine");

            this.herobrineDragonUUID = dragon.getUUID();
            this.herobrineDragon = dragon;

            if (this.level().getServer() != null) {
                Objects.requireNonNull(this.level().getServer()).getPlayerList().broadcastSystemMessage(
                        Component.literal("<" + this.getChatName() + "> " +
                                Component.translatable("subtitles.herobrine_summon").getString()),
                        false
                );
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (!spawnHerobrineDragon) {
                this.spawnHerobrineDragon = true;
                spawnHerobrineDragon();
            }

            if (herobrineDragon == null && herobrineDragonUUID == null) {
                if (dragonSummonCooldown <= 0) {
                    spawnHerobrineDragon = false;
                } else {
                    dragonSummonCooldown--;
                }
            }

            if (herobrineDragon == null && herobrineDragonUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(herobrineDragonUUID);
                if (entity instanceof HerobrineDragonEntity dragon) {
                    herobrineDragon = dragon;
                } else {
                    herobrineDragon = null;
                }
            }

            if (herobrineDragon != null && herobrineDragon.isAlive()
                    && herobrineDragon.getHealth() <= herobrineDragon.getMaxHealth() * 0.25f) {

                herobrineDragon.discard();
                herobrineDragon = null;
                herobrineDragonUUID = null;

                if (this.level().getServer() != null) {
                    Objects.requireNonNull(this.level().getServer()).getPlayerList().broadcastSystemMessage(
                            Component.literal("<" + this.getChatName() + ">  " +
                                    Component.translatable("subtitles.reaper_herobrine_return_dragon").getString()),
                            false
                    );
                }

                dragonSummonCooldown = 3600;
            }

            if (herobrineDragon != null && herobrineDragon.isAlive()) {
                if (herobrineDragon.getSummoner() != this) {
                    herobrineDragon.setSummoner(this);
                    herobrineDragon.setSummonerUUID(this.getUUID());
                }

                LivingEntity target = this.getTarget();
                if (target != null && target.isAlive()) {
                    herobrineDragon.setTarget(target);
                }
            }

            if (this.getHealth() <= (float) 2 / 3 * this.getMaxHealth()
                    && herobrineDragon != null
                    && !this.isPassenger()) {
                herobrineDragon.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                this.startRiding(herobrineDragon);
            }

            if (this.tickCount % 20 == 0) {
                if (this.getState() > 0) {
                    HerobrineWeaponEffectProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
                }
            }
        }
    }

    public boolean hurt(@NotNull DamageSource damagesource, float f) {
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
    public void remove(@NotNull RemovalReason reason) {
        if (this.herobrineDragon != null) {
            this.herobrineDragon.discard();
            this.herobrineDragon = null;
            this.herobrineDragonUUID = null;
        }
        super.remove(reason);
    }

    public void die(@NotNull DamageSource damageSource) {
        super.die(damageSource);
        if (this.level() instanceof ServerLevel serverLevel) {
            EliteHerobrineKnockedEntity eliteHerobrineKnockedEntity = new EliteHerobrineKnockedEntity(AnnoyingVillagersModEntities.ELITE_HEROBRINE_KNOCKED.get(), serverLevel);

            eliteHerobrineKnockedEntity.moveTo(this.getX(), this.getY(), this.getZ(), serverLevel.getRandom().nextFloat() * 360.0F, 0.0F);
            eliteHerobrineKnockedEntity.getPersistentData().putString("FromElite", "EnderSlayerScythe");
            eliteHerobrineKnockedEntity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(eliteHerobrineKnockedEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
            this.remove(RemovalReason.KILLED);
            serverLevel.addFreshEntity(eliteHerobrineKnockedEntity);

            if (this.getGregUUID() != null) {
                Entity entity = serverLevel.getEntity(this.getGregUUID());
                if (entity instanceof HerobrineGregEntity herobrineGregEntity && entity.isAlive()) {
                    herobrineGregEntity.requestProtect(eliteHerobrineKnockedEntity.getUUID(), eliteHerobrineKnockedEntity);
                }
            }
        }
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.45D);
        builder = builder.add(Attributes.MAX_HEALTH, 250.0D);
        builder = builder.add(Attributes.ARMOR, 50.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 4.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
        return builder;
    }
}
