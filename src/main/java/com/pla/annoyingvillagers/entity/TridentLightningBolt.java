package com.pla.annoyingvillagers.entity;

import com.google.common.collect.Sets;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.PlayMessages;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TridentLightningBolt extends LightningBolt {
    private int tridentLife = 2;
    private int tridentFlashes;
    private long tridentSeed;
    private final Set<Entity> tridentHitEntities = Sets.newHashSet();
    boolean superLightning = false;

    public void setSuperLightning(boolean superLightning) {
        this.superLightning = superLightning;
    }

    @Nullable
    private LivingEntity owner;

    private boolean tridentVisualOnly = false;

    public TridentLightningBolt(EntityType<? extends LightningBolt> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noCulling = true;
        this.tridentLife = 2;
        this.tridentSeed = this.random.nextLong();
        this.tridentFlashes = this.random.nextInt(3) + 1;
    }

    public TridentLightningBolt(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.TRIDENT_LIGHTNING_BOLT.get(), level);
    }

    public void setOwner(@Nullable LivingEntity owner) {
        this.owner = owner;
    }

    @Nullable
    public LivingEntity getOwner() {
        return this.owner;
    }

    public void setTridentVisualOnly(boolean visualOnly) {
        this.tridentVisualOnly = visualOnly;
    }

    public boolean isTridentVisualOnly() {
        return this.tridentVisualOnly;
    }

    @Override
    public void tick() {
        super.baseTick();

        if (this.tridentLife == 2) {
            if (this.level().isClientSide()) {
                this.level().playLocalSound(
                        this.getX(), this.getY(), this.getZ(),
                        SoundEvents.LIGHTNING_BOLT_THUNDER,
                        SoundSource.WEATHER,
                        10000.0F,
                        0.8F + this.random.nextFloat() * 0.2F,
                        false
                );
                this.level().playLocalSound(
                        this.getX(), this.getY(), this.getZ(),
                        SoundEvents.LIGHTNING_BOLT_IMPACT,
                        SoundSource.WEATHER,
                        2.0F,
                        0.5F + this.random.nextFloat() * 0.2F,
                        false
                );
            } else {
                this.gameEvent(GameEvent.LIGHTNING_STRIKE);
                if (this.owner instanceof ServerPlayer serverPlayer) {
                    this.setCause(serverPlayer);
                }
            }
        }

        --this.tridentLife;

        if (this.tridentLife < 0) {
            if (this.tridentFlashes == 0) {
                if (this.level() instanceof ServerLevel serverLevel) {
                    List<Entity> list = this.level().getEntities(
                            this,
                            new AABB(
                                    this.getX() - 15.0D, this.getY() - 15.0D, this.getZ() - 15.0D,
                                    this.getX() + 15.0D, this.getY() + 21.0D, this.getZ() + 15.0D
                            ),
                            entity -> entity.isAlive() && !this.tridentHitEntities.contains(entity)
                    );

                    for (ServerPlayer serverPlayer : serverLevel.getPlayers(p -> p.distanceTo(this) < 256.0F)) {
                        CriteriaTriggers.LIGHTNING_STRIKE.trigger(serverPlayer, this, list);
                    }
                }

                this.discard();
            } else if (this.tridentLife < -this.random.nextInt(10)) {
                --this.tridentFlashes;
                this.tridentLife = 1;
                this.tridentSeed = this.random.nextLong();
            }
        }

        if (this.tridentLife >= 0) {
            if (!(this.level() instanceof ServerLevel serverLevel)) {
                this.level().setSkyFlashTime(2);
            } else if (!this.tridentVisualOnly) {
                List<Entity> list = this.level().getEntities(
                        this,
                        new AABB(
                                this.getX() - 3.0D, this.getY() - 3.0D, this.getZ() - 3.0D,
                                this.getX() + 3.0D, this.getY() + 9.0D, this.getZ() + 3.0D
                        ),
                        entity -> entity instanceof LivingEntity && entity.isAlive() && entity != this.owner && this.owner instanceof BlueDemonEntity blueDemonEntity && blueDemonEntity.getBbqEntity() != null && entity != blueDemonEntity.getBbqEntity() && !entity.isSpectator() && !(entity instanceof Player player && player.isCreative())
                );

                if (this.superLightning) {
                    serverLevel.explode(
                            this,
                            this.getX(),
                            this.getY(),
                            this.getZ(),
                            new Random().nextFloat(10.0F, 15.0F),
                            Level.ExplosionInteraction.BLOCK
                    );
                }

                for (Entity entity : list) {
                    if (!ForgeEventFactory.onEntityStruckByLightning(entity, this)) {
                        if (entity instanceof LivingEntity livingEntity) {
                            if (this.superLightning) {
                                livingEntity.addEffect(new MobEffectInstance(
                                        AnnoyingVillagersModMobEffects.ELECTRIFY.get(),
                                        100,
                                        2
                                ));
                            } else {
                                livingEntity.addEffect(new MobEffectInstance(
                                        AnnoyingVillagersModMobEffects.ELECTRIFY.get(),
                                        60,
                                        1
                                ));
                            }
                        }

                        if (this.superLightning) {
                            entity.hurt(level().damageSources().indirectMagic(this, owner), 50.0F);
                        } else {
                            entity.hurt(level().damageSources().indirectMagic(this, owner), 5.0F);
                        }
                    }
                }

                this.tridentHitEntities.addAll(list);

                if (this.owner instanceof ServerPlayer serverPlayer) {
                    CriteriaTriggers.CHANNELED_LIGHTNING.trigger(serverPlayer, list);
                }
            }
        }
    }
}