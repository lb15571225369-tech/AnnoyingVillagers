package com.pla.annoyingvillagers.entity;

import com.google.common.collect.Multimap;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.util.EpicfightUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import net.minecraftforge.network.NetworkHooks;

import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

import java.util.HashMap;
import java.util.Map;

public class ItemProjectile extends Projectile implements ItemSupplier {
    private static final int MIN_ARC_TRAVEL_TICKS = 14;
    private static final int MAX_ARC_TRAVEL_TICKS = 34;

    private boolean arcInitialized = false;
    private Vec3 arcStart = Vec3.ZERO;
    private Vec3 arcSide = Vec3.ZERO;
    private int arcTravelTicks = MIN_ARC_TRAVEL_TICKS;
    private double arcHeight = 1.0D;

    private static final EntityDataAccessor<ItemStack> DATA_STACK =
            SynchedEntityData.defineId(
                    ItemProjectile.class,
                    EntityDataSerializers.ITEM_STACK
            );

    private static final double ARRIVE_DISTANCE = 0.65D;
    private static final int MAX_LIFE = 80;
    private static final int HIT_COOLDOWN_TICKS = 8;

    private final Map<Integer, Integer> recentHits = new HashMap<>();

    public ItemProjectile(EntityType<? extends ItemProjectile> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public ItemProjectile(Level level, LivingEntity owner, ItemStack stack, Vec3 spawnPos) {
        this(AnnoyingVillagersModEntities.ITEM_PROJECTILE.get(), level);
        this.setOwner(owner);
        this.setWeaponStack(stack);
        this.setPos(spawnPos.x, spawnPos.y + 0.25D, spawnPos.z);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_STACK, ItemStack.EMPTY);
    }

    public void setWeaponStack(ItemStack stack) {
        this.entityData.set(DATA_STACK, stack.copy());
    }

    public ItemStack getWeaponStack() {
        return this.entityData.get(DATA_STACK);
    }

    @Override
    public @NotNull ItemStack getItem() {
        return this.getWeaponStack();
    }

    private void initializeArcPath(Vec3 firstTargetPos) {
        if (this.arcInitialized) {
            return;
        }

        this.arcInitialized = true;
        this.arcStart = this.position();

        double distance = this.arcStart.distanceTo(firstTargetPos);

        this.arcTravelTicks = Mth.clamp(
                (int) Math.round(distance * 4.5D),
                MIN_ARC_TRAVEL_TICKS,
                MAX_ARC_TRAVEL_TICKS
        );

        this.arcHeight = Mth.clamp(
                0.65D + distance * 0.22D,
                0.75D,
                2.25D
        );

        Vec3 direction = firstTargetPos.subtract(this.arcStart);

        if (direction.horizontalDistanceSqr() > 0.0001D) {
            Vec3 flat = new Vec3(direction.x, 0.0D, direction.z).normalize();

            this.arcSide = new Vec3(-flat.z, 0.0D, flat.x)
                    .scale((this.random.nextBoolean() ? 1.0D : -1.0D) * Mth.clamp(distance * 0.12D, 0.15D, 0.55D));
        } else {
            this.arcSide = Vec3.ZERO;
        }
    }

    private Vec3 getArcPosition(double progress, Vec3 currentTargetPos) {
        Vec3 start = this.arcStart;
        Vec3 end = currentTargetPos;

        Vec3 middle = start.add(end)
                .scale(0.5D)
                .add(0.0D, this.arcHeight, 0.0D)
                .add(this.arcSide);

        double inverse = 1.0D - progress;
        return start.scale(inverse * inverse)
                .add(middle.scale(2.0D * inverse * progress))
                .add(end.scale(progress * progress));
    }

    @Override
    public void tick() {
        super.tick();

        this.noPhysics = true;
        this.setNoGravity(true);

        ItemStack stack = this.getWeaponStack();
        if (stack.isEmpty()) {
            this.discard();
            return;
        }

        Entity ownerEntity = this.getOwner();
        if (!(ownerEntity instanceof LivingEntity owner) || !owner.isAlive()) {
            if (!this.level().isClientSide) {
                this.dropBackToItem();
            }

            return;
        }

        Vec3 oldPos = this.position();
        Vec3 targetPos = this.getTargetHandPosition(owner);

        this.initializeArcPath(targetPos);

        double rawProgress = Mth.clamp(
                (double) this.tickCount / (double) this.arcTravelTicks,
                0.0D,
                1.0D
        );

        double progress = rawProgress * rawProgress * (3.0D - 2.0D * rawProgress);

        Vec3 newPos = this.getArcPosition(progress, targetPos);
        Vec3 motion = newPos.subtract(oldPos);

        this.setDeltaMovement(motion);
        this.setPos(newPos.x, newPos.y, newPos.z);

        if (!this.level().isClientSide) {
            this.damageEntitiesAlongPath(oldPos, newPos, owner);
        }

        this.updateRotationFromMotion(motion);
        this.clearOldHitCooldowns();

        if (rawProgress >= 1.0D) {
            if (!this.level().isClientSide) {
                this.dropBackToItem();
            }

            return;
        }
    }

    private Vec3 getTargetHandPosition(LivingEntity owner) {
        Vec3 jointPos = null;

        try {
            jointPos = EpicfightUtil.getJointWithTranslation(
                    owner,
                    new Vec3f(0.0F, 0.0F, 0.0F),
                    Armatures.BIPED.get().toolR,
                    0.0F,
                    0.0D
            );
        } catch (Exception ignored) {
            // Fallback below.
        }

        if (jointPos != null) {
            return jointPos;
        }

        // Fallback if Epic Fight patch/armature is not available.
        return owner.getEyePosition()
                .add(owner.getLookAngle().scale(0.45D))
                .subtract(0.0D, 0.25D, 0.0D);
    }

    private void damageEntitiesAlongPath(Vec3 from, Vec3 to, LivingEntity owner) {
        if (this.level().isClientSide) {
            return;
        }

        AABB sweepBox = new AABB(from, to).inflate(0.75D);

        for (LivingEntity target : this.level().getEntitiesOfClass(
                LivingEntity.class,
                sweepBox,
                entity -> this.canDamage(entity, owner)
        )) {
            int nextAllowedHitTick = this.recentHits.getOrDefault(target.getId(), 0);
            if (nextAllowedHitTick > this.tickCount) {
                continue;
            }

            if (target.getBoundingBox().inflate(0.3D).clip(from, to).isEmpty()) {
                continue;
            }

            float damage = this.calculateWeaponDamage(target);

            DamageSource source = this.level().damageSources().thrown(this, owner);
            if (target.hurt(source, damage)) {
                this.recentHits.put(target.getId(), this.tickCount + HIT_COOLDOWN_TICKS);
                this.applyWeaponEnchantEffects(owner, target);
                this.playSound(SoundEvents.TRIDENT_HIT, 0.9F, 1.25F);
            }
        }
    }

    private boolean canDamage(LivingEntity target, LivingEntity owner) {
        if (!target.isAlive()) {
            return false;
        }

        if (target.isSpectator()) {
            return false;
        }

        if (target == owner || target.getUUID().equals(owner.getUUID())) {
            return false;
        }

        return !target.isAlliedTo(owner);
    }

    private float calculateWeaponDamage(LivingEntity target) {
        ItemStack stack = this.getWeaponStack();

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

        damage += EnchantmentHelper.getDamageBonus(stack, target.getMobType());

        return (float) Math.max(1.0D, damage);
    }

    private void applyWeaponEnchantEffects(LivingEntity owner, LivingEntity target) {
        ItemStack stack = this.getWeaponStack();

        int fireAspect = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, stack);
        if (fireAspect > 0) {
            target.setSecondsOnFire(fireAspect * 4);
        }

        EnchantmentHelper.doPostHurtEffects(target, owner);
        EnchantmentHelper.doPostDamageEffects(owner, target);
    }

    private void dropBackToItem() {
        if (!this.level().isClientSide) {
            ItemStack stack = this.getWeaponStack().copy();

            if (!stack.isEmpty()) {
                ItemEntity itemEntity = new ItemEntity(
                        this.level(),
                        this.getX(),
                        this.getY(),
                        this.getZ(),
                        stack
                );

                itemEntity.setPickUpDelay(20);
                itemEntity.setDeltaMovement(0.0D, -0.05D, 0.0D);

                this.level().addFreshEntity(itemEntity);
            }
        }

        this.discard();
    }

    private void updateRotationFromMotion(Vec3 motion) {
        double horizontal = motion.horizontalDistance();

        if (horizontal > 1.0E-7D) {
            this.setYRot((float) (Mth.atan2(motion.x, motion.z) * Mth.RAD_TO_DEG));
            this.setXRot((float) (Mth.atan2(motion.y, horizontal) * Mth.RAD_TO_DEG));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }
    }

    private void clearOldHitCooldowns() {
        if (this.recentHits.isEmpty()) {
            return;
        }

        this.recentHits.entrySet().removeIf(entry -> entry.getValue() <= this.tickCount);
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("WeaponStack", this.getWeaponStack().save(new CompoundTag()));
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        if (tag.contains("WeaponStack", 10)) {
            this.setWeaponStack(ItemStack.of(tag.getCompound("WeaponStack")));
        }
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}