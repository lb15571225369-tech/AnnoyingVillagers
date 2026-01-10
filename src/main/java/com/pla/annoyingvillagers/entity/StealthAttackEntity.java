package com.pla.annoyingvillagers.entity;

import java.util.Objects;
import java.util.Random;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.utils.LevelUtil;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class StealthAttackEntity extends AbstractArrow implements ItemSupplier {
    public boolean fromAegis = false;

    public StealthAttackEntity(SpawnEntity spawnentity, Level level) {
        super(AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level);
    }

    public StealthAttackEntity(EntityType<? extends StealthAttackEntity> entitytype, Level level) {
        super(entitytype, level);
    }

    public StealthAttackEntity(EntityType<? extends StealthAttackEntity> entitytype, double d0, double d1, double d2, Level level) {
        super(entitytype, d0, d1, d2, level);
    }

    public StealthAttackEntity(EntityType<? extends StealthAttackEntity> entitytype, LivingEntity livingentity, Level level) {
        super(entitytype, livingentity, level);
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @OnlyIn(Dist.CLIENT)
    public @NotNull ItemStack getItem() {
        return new ItemStack(Blocks.AIR);
    }

    public @NotNull ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    public void tick() {
        super.tick();
        if (this.inGround) {
            this.discard();
        }
        if (this.fromAegis && !this.level().isClientSide()) {
            HerobrineUtil.spawnEliteEffect(this.level(), this.getX(), this.getY(), this.getZ(), this);
            doGroundSlamAtSelf();
        }
    }

    private void doGroundSlamAtSelf() {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;

        BlockPos floor = BlockPos.containing(this.position().x, this.position().y - 1.0, this.position().z);
        Vec3 center = new Vec3(this.getX(), floor.getY(), this.getZ());
        Entity src = (this.getOwner() != null) ? this.getOwner() : this;
        if (src instanceof LivingEntity livingSrc) {
            LevelUtil.circleSlamFracture(livingSrc, serverLevel, center, 3.5D, true, true, true);
        }
    }

    public static StealthAttackEntity shoot(Level level, LivingEntity livingentity, Random random, float f, double d0, int i) {
        StealthAttackEntity stealthAttackEntity = new StealthAttackEntity(AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), livingentity, level);

        stealthAttackEntity.shoot(livingentity.getViewVector(1.0F).x, livingentity.getViewVector(1.0F).y, livingentity.getViewVector(1.0F).z, f * 2.0F, 0.0F);
        stealthAttackEntity.setSilent(true);
        stealthAttackEntity.setCritArrow(false);
        stealthAttackEntity.setBaseDamage(d0);
        stealthAttackEntity.setKnockback(i);
        level.addFreshEntity(stealthAttackEntity);
        level.playSound(null, livingentity.getX(), livingentity.getY(), livingentity.getZ(), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft","entity.arrow.shoot"))), SoundSource.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.5F + 1.0F) + f / 2.0F);
        return stealthAttackEntity;
    }

    public static StealthAttackEntity shoot(LivingEntity livingentity, LivingEntity livingentity1) {
        StealthAttackEntity stealthAttackEntity = new StealthAttackEntity(AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), livingentity, livingentity.level());
        double d0 = livingentity1.getX() - livingentity.getX();
        double d1 = livingentity1.getY() + (double) livingentity1.getEyeHeight() - 1.1D;
        double d2 = livingentity1.getZ() - livingentity.getZ();

        stealthAttackEntity.shoot(d0, d1 - stealthAttackEntity.getY() + Math.hypot(d0, d2) * 0.20000000298023224D, d2, 2.0F, 12.0F);
        stealthAttackEntity.setSilent(true);
        stealthAttackEntity.setBaseDamage(18.0D);
        stealthAttackEntity.setKnockback(7);
        stealthAttackEntity.setCritArrow(false);
        livingentity.level().addFreshEntity(stealthAttackEntity);
        livingentity.level().playSound(null, livingentity.getX(), livingentity.getY(), livingentity.getZ(), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.arrow.shoot"))), SoundSource.PLAYERS, 1.0F, 1.0F / ((new Random()).nextFloat() * 0.5F + 1.0F));
        return stealthAttackEntity;
    }

    @Override
    protected boolean canHitEntity(@NotNull Entity entity) {
        Entity owner = this.getOwner();
        if (entity == owner) return false;
        if (owner instanceof LivingEntity livingOwner && entity instanceof LivingEntity livingTarget && livingOwner.isAlliedTo(livingTarget)) return false;
        return super.canHitEntity(entity);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Entity vicTim = pResult.getEntity();
        Entity owner = this.getOwner();
        if (vicTim == owner) return;
        if (fromAegis && vicTim.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(EpicFightParticles.HIT_BLUNT.get(),
                    vicTim.getX(), vicTim.getY() + 1.5, vicTim.getZ() + 0.8,
                    1,
                    0.1, 0.1, 0.1,
                    1);
            serverLevel.sendParticles(AnnoyingVillagersModParticleTypes.SPARK.get(),
                    vicTim.getX(), vicTim.getY() + 1.5, vicTim.getZ() + 0.8,
                    5,
                    0, 0, 0,
                    0.1);
            LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(vicTim, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                livingEntityPatch.playAnimationSynchronized(AVAnimations.LONGEST_HIT, 0.0F);
            }
        }
        super.onHitEntity(pResult);
    }
}
