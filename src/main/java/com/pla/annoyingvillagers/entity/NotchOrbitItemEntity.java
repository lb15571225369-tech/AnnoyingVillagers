package com.pla.annoyingvillagers.entity;

import com.google.common.collect.Multimap;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.UUID;

public class NotchOrbitItemEntity extends ThrowableProjectile {

    private static final EntityDataAccessor<ItemStack> DATA_STACK =
            SynchedEntityData.defineId(NotchOrbitItemEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Float> ROT_X =
            SynchedEntityData.defineId(NotchOrbitItemEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> ROT_Y =
            SynchedEntityData.defineId(NotchOrbitItemEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> ROT_Z =
            SynchedEntityData.defineId(NotchOrbitItemEntity.class, EntityDataSerializers.FLOAT);

    private boolean notReadyForShoot = false;
    private UUID ownerUUID;
    private static final float NO_DAMAGE_OVERRIDE = -1.0F;
    private float damageOverride = NO_DAMAGE_OVERRIDE;

    public NotchOrbitItemEntity(EntityType<? extends NotchOrbitItemEntity> type, Level level) {
        super(type, level);
        initRandomRotation();
    }

    public NotchOrbitItemEntity(Level level, LivingEntity shooter, ItemStack stack) {
        super(AnnoyingVillagersModEntities.NOTCH_ORBIT_ITEM.get(), shooter, level);
        setItem(stack);
        initRandomRotation();
    }

    private void initRandomRotation() {
        if (!level().isClientSide) {
            var r = this.random;
            setRotX((r.nextFloat() - 0.5f) * 10f);
            setRotY((r.nextFloat() - 0.5f) * 10f);
            setRotZ((r.nextFloat() - 0.5f) * 10f);
        }
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_STACK, ItemStack.EMPTY);
        this.entityData.define(ROT_X, 0f);
        this.entityData.define(ROT_Y, 0f);
        this.entityData.define(ROT_Z, 0f);
    }

    public void setItem(ItemStack stack) {
        this.entityData.set(DATA_STACK, stack.copy());
    }

    public ItemStack getItem() {
        return this.entityData.get(DATA_STACK);
    }

    public void setRotX(float v) { this.entityData.set(ROT_X, v); }
    public void setRotY(float v) { this.entityData.set(ROT_Y, v); }
    public void setRotZ(float v) { this.entityData.set(ROT_Z, v); }
    public float getRotX() { return this.entityData.get(ROT_X); }
    public float getRotY() { return this.entityData.get(ROT_Y); }
    public float getRotZ() { return this.entityData.get(ROT_Z); }

    public void setNotReadyForShoot(boolean notReadyForShoot) {
        this.notReadyForShoot = notReadyForShoot;
    }

    public void setOwnerUUID(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    public void setDamageOverride(float damageOverride) {
        this.damageOverride = Math.max(0.0F, damageOverride);
    }

    public void clearDamageOverride() {
        this.damageOverride = NO_DAMAGE_OVERRIDE;
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);
        if (this.notReadyForShoot) return;

        Entity target = result.getEntity();
        final UUID ownerId = this.ownerUUID;

        // Skip if target is owner
        if (ownerId != null && target instanceof Player p && p.getUUID().equals(ownerId)) return;
        // Skip if target is Herobrine faction
        if (HerobrineUtil.isHerobrineFaction(target)) return;

        if (target.level() instanceof ServerLevel) {
            float damage = resolveImpactDamage(target);

            if (this.getOwner() == null) {
                target.hurt(target.level().damageSources().generic(), damage);
            } else {
                target.hurt(target.level().damageSources().indirectMagic(this, this.getOwner()), damage);
            }

            LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                livingEntityPatch.applyStun(StunType.LONG, 20.0F);
            }

            if (target instanceof LivingEntity livingEntity) {
                float strength = 1.0F;
                double dx = this.getX() - target.getX();
                double dz = this.getZ() - target.getZ();
                livingEntity.knockback(strength, dx, dz);
            }
        }
    }

    private float resolveImpactDamage(Entity target) {
        if (this.damageOverride >= 0.0F) {
            return this.damageOverride;
        }
        return calculateWeaponDamage(target);
    }

    private float calculateWeaponDamage(Entity target) {
        ItemStack stack = this.getItem();
        double damage = 1.0D;

        Multimap<Attribute, AttributeModifier> modifiers =
                stack.getAttributeModifiers(EquipmentSlot.MAINHAND);

        for (AttributeModifier modifier : modifiers.get(Attributes.ATTACK_DAMAGE)) {
            if (modifier.getOperation() == AttributeModifier.Operation.ADDITION) {
                damage += modifier.getAmount();
            } else if (modifier.getOperation() == AttributeModifier.Operation.MULTIPLY_BASE) {
                damage += damage * modifier.getAmount();
            } else if (modifier.getOperation() == AttributeModifier.Operation.MULTIPLY_TOTAL) {
                damage *= 1.0D + modifier.getAmount();
            }
        }

        return (float) Math.max(1.0D, damage);
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        if (this.notReadyForShoot) return;
        this.discard();
    }

    @Override
    protected float getGravity() {
        return 0.005F;
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return EntityDimensions.fixed(0.5F, 0.5F);
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("Item", getItem().save(new CompoundTag()));
        tag.putFloat("RotX", getRotX());
        tag.putFloat("RotY", getRotY());
        tag.putFloat("RotZ", getRotZ());
        tag.putBoolean("NotReadyForShoot", notReadyForShoot);
        tag.putFloat("DamageOverride", this.damageOverride);
        if (this.ownerUUID != null) {
            tag.putUUID("OwnerUUID", this.ownerUUID);
        }
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Item", 10)) {
            setItem(ItemStack.of(tag.getCompound("Item")));
        }
        setRotX(tag.contains("RotX") ? tag.getFloat("RotX") : 0f);
        setRotY(tag.contains("RotY") ? tag.getFloat("RotY") : 0f);
        setRotZ(tag.contains("RotZ") ? tag.getFloat("RotZ") : 0f);
        notReadyForShoot = tag.getBoolean("NotReadyForShoot");
        this.damageOverride = tag.contains("DamageOverride")
                ? tag.getFloat("DamageOverride")
                : NO_DAMAGE_OVERRIDE;
        this.ownerUUID = tag.hasUUID("OwnerUUID")
                ? tag.getUUID("OwnerUUID")
                : null;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
