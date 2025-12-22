package com.pla.annoyingvillagers.clazz;

import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.Random;

public class PathfinderMobInventory extends PathfinderMob implements RangedAttackMob {
    private final SimpleContainer inventory = new SimpleContainer(27);
    private int gapCooldown;
    private int enderPearlCooldown;
    private int swapToBowCooldown = 0;
    private ItemStack mainWeaponItem = ItemStack.EMPTY;
    private ItemStack offWeaponItem = ItemStack.EMPTY;
    private boolean healing = false;
    private boolean initialSpawn = false;
    private boolean useBow = true;
    private final LivingEntityPatch<?> livingEntityPatch =  EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);
    private Entity blockDamage = null;
    private double blockProjectileChance;

    public Entity getBlockDamage() {
        return blockDamage;
    }

    public double getBlockProjectileChance() {
        return blockProjectileChance;
    }

    public void setBlockProjectileChance(double blockProjectileChance) {
        this.blockProjectileChance = blockProjectileChance;
    }

    public void setBlockDamage(Entity blockDamage) {
        this.blockDamage = blockDamage;
    }

    public boolean isHealing() {
        return healing;
    }

    public void setHealing(boolean healing) {
        this.healing = healing;
    }

    public int getSwapToBowCooldown() {
        return swapToBowCooldown;
    }

    public void setSwapToBowCooldown() {
        this.swapToBowCooldown = random.nextInt(100, 300);
    }

    public LivingEntityPatch<?> getLivingEntityPatch() {
        return livingEntityPatch;
    }

    public int getGapCooldown() {
        return gapCooldown;
    }

    public int getEnderPearlCooldown() {
        return enderPearlCooldown;
    }

    public void setGapCooldown() {
        this.gapCooldown = random.nextInt(100, 300);
    }

    public void resetGapCooldown() {this.gapCooldown = 0; }

    public void setEnderPearlCooldown() {
        this.enderPearlCooldown = random.nextInt(100, 300);
    }

    public ItemStack getMainWeaponItem() {
        return mainWeaponItem;
    }

    public void setMainWeaponItem(ItemStack mainWeaponItem) {
        this.mainWeaponItem = mainWeaponItem;
    }

    public ItemStack getOffWeaponItem() { return offWeaponItem; }

    public void setOffWeaponItem(ItemStack offWeaponItem) {
        this.offWeaponItem = offWeaponItem;
    }

    public SimpleContainer getInventory() {
        return inventory;
    }

    public void setUseBow(boolean useBow) {
        this.useBow = useBow;
    }

    public boolean isUseBow() {
        return useBow;
    }

    protected PathfinderMobInventory(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        this.setDropChance(EquipmentSlot.OFFHAND, 0.0F);
        this.setDropChance(EquipmentSlot.CHEST, 0.0F);
        this.setDropChance(EquipmentSlot.HEAD, 0.0F);
        this.setDropChance(EquipmentSlot.LEGS, 0.0F);
        this.setDropChance(EquipmentSlot.FEET, 0.0F);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("Inventory", this.inventory.createTag());
        tag.putInt("GapCooldown", this.gapCooldown);
        tag.putInt("EnderPearlCooldown", this.enderPearlCooldown);
        tag.putInt("SwapToBowCooldown", this.swapToBowCooldown);
        tag.putBoolean("InitialSpawn", this.initialSpawn);
        tag.putBoolean("UseBow", this.useBow);
        tag.putDouble("BlockProjectileChance", this.blockProjectileChance);
        if (!this.mainWeaponItem.isEmpty()) {
            CompoundTag itemTag = new CompoundTag();
            this.mainWeaponItem.save(itemTag);
            tag.put("MainHandItem", itemTag);
        }
        if (!this.offWeaponItem.isEmpty()) {
            CompoundTag itemTag = new CompoundTag();
            this.offWeaponItem.save(itemTag);
            tag.put("OffHandItem", itemTag);
        }
    }

    @Override
    public void onEquipItem(@NotNull EquipmentSlot pSlot, @NotNull ItemStack pOldItem, @NotNull ItemStack pNewItem) {
        if (pSlot == EquipmentSlot.MAINHAND &&
                (pNewItem.getItem() instanceof SwordItem || pNewItem.getItem() instanceof AxeItem || pNewItem.getItem() instanceof ShieldItem)) {
            this.mainWeaponItem = pNewItem.copy();
        }
        if (pSlot == EquipmentSlot.OFFHAND &&
                (pNewItem.getItem() instanceof SwordItem || pNewItem.getItem() instanceof AxeItem || pNewItem.getItem() instanceof ShieldItem)) {
            this.offWeaponItem = pNewItem.copy();
        }
        super.onEquipItem(pSlot, pOldItem, pNewItem);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Inventory", Tag.TAG_COMPOUND)) {
            this.inventory.fromTag(tag.getList("Inventory", Tag.TAG_COMPOUND));
        }
        this.gapCooldown = tag.getInt("GapCooldown");
        this.enderPearlCooldown = tag.getInt("EnderPearlCooldown");
        this.swapToBowCooldown = tag.getInt("SwapToBowCooldown");
        this.initialSpawn = tag.getBoolean("InitialSpawn");
        this.useBow = tag.getBoolean("UseBow");
        this.blockProjectileChance = tag.getDouble("BlockProjectileChance");
        if (tag.contains("MainHandItem", Tag.TAG_COMPOUND)) {
            this.mainWeaponItem = ItemStack.of(tag.getCompound("MainHandItem"));
        } else {
            this.mainWeaponItem = ItemStack.EMPTY;
        }
        if (tag.contains("OffHandItem", Tag.TAG_COMPOUND)) {
            this.offWeaponItem = ItemStack.of(tag.getCompound("OffHandItem"));
        } else {
            this.offWeaponItem = ItemStack.EMPTY;
        }
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);

        for (int i = 0; i < this.inventory.getContainerSize(); i++) {
            ItemStack stack = this.inventory.getItem(i);
            if (!stack.isEmpty()) {
                this.spawnAtLocation(stack);
            }
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        if (this.getMainHandItem().getItem() instanceof BowItem) {
            this.goalSelector.addGoal(4, new RangedBowAttackGoal<>(this, 1.0D, 20, 10.0F));
        }
    }

    @Override
    public boolean canFireProjectileWeapon(ProjectileWeaponItem item) {
        return item instanceof BowItem;
    }

    public boolean canFireProjectileWeapon(Item item) {
        boolean var10000;
        if (item instanceof ProjectileWeaponItem weaponItem) {
            if (this.canFireProjectileWeapon(weaponItem)) {
                var10000 = true;
                return var10000;
            }
        }

        var10000 = false;
        return var10000;
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
        ItemStack weaponStack = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, this::canFireProjectileWeapon));
        ItemStack itemstack = this.getProjectile(weaponStack);
        AbstractArrow mobArrow = ProjectileUtil.getMobArrow(this, itemstack, pVelocity);
        if (this.getMainHandItem().getItem() instanceof BowItem) {
            mobArrow = ((BowItem)this.getMainHandItem().getItem()).customArrow(mobArrow);
        }

        double x = pTarget.getX() - this.getX();
        double y = pTarget.getY(0.3333333333333333) - mobArrow.getY();
        double z = pTarget.getZ() - this.getZ();
        double d3 = Math.sqrt(x * x + z * z);
        mobArrow.setOwner(this);
        mobArrow.shoot(x, y + d3 * (double)0.2F, z, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
        this.playSound(SoundEvents.ARROW_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(mobArrow);
    }

    private boolean isInventoryFull() {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack s = inventory.getItem(i);
            if (s.isEmpty() || s.getCount() < s.getMaxStackSize()) {
                return false;
            }
        }
        return true;
    }

    private void pickupNearbyItems() {
        if (!isAlive() || isRemoved()) return;

        var box = getBoundingBox().inflate(1.5D);
        List<ItemEntity> items = level().getEntitiesOfClass(
                ItemEntity.class,
                box,
                e -> !e.isRemoved() && !e.hasPickUpDelay()
        );

        for (ItemEntity itemEntity : items) {
            tryPickup(itemEntity);
        }
    }

    private void tryPickup(ItemEntity itemEntity) {
        ItemStack remaining = itemEntity.getItem().copy();

        for (int i = 0; i < inventory.getContainerSize() && !remaining.isEmpty(); i++) {
            if (remaining.isEmpty()) break;
            ItemStack slotStack = this.inventory.getItem(i);

            if (slotStack.isEmpty()) {
                this.inventory.setItem(i, remaining);
                remaining = ItemStack.EMPTY;
                break;
            } else if (ItemStack.isSameItemSameTags(slotStack, remaining) &&
                    slotStack.getCount() < slotStack.getMaxStackSize()) {
                int transferable = Math.min(
                        remaining.getCount(),
                        slotStack.getMaxStackSize() - slotStack.getCount()
                );
                slotStack.grow(transferable);
                remaining.shrink(transferable);
            }
        }

        if (remaining.isEmpty()) {
            itemEntity.setDeltaMovement(
                    (this.getX() - itemEntity.getX()) * 0.25,
                    (this.getY() + 1.0 - itemEntity.getY()) * 0.25,
                    (this.getZ() - itemEntity.getZ()) * 0.25
            );
            itemEntity.setPickUpDelay(0);
            Entity entity = this;
            new DelayedTask(5) {
                @Override
                public void run() {
                    if (!entity.isAlive() || entity.isRemoved() || entity.level() == null) return;
                    itemEntity.discard();
                    entity.level().playSound(null, entity.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.HOSTILE, 0.2F, 1.0F);
                }
            };
        } else {
            itemEntity.setItem(remaining);
        }
    }

    protected void implementFirstTick(ServerLevel serverLevel) {}

    public void jump() {
        this.jumpFromGround();
        Vec3 motion = this.getDeltaMovement();
        Vec3 forward = this.getForward();
        double strength = new Random().nextDouble(0.2, 0.4);
        this.setDeltaMovement(
                motion.x + forward.x * strength,
                motion.y,
                motion.z + forward.z * strength
        );
        this.hasImpulse = true;
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) return;

        if (this.tickCount == 1 && !this.initialSpawn) {
            implementFirstTick((ServerLevel) this.level());
            this.initialSpawn = true;
        }

        if (gapCooldown > 0) gapCooldown--;
        if (enderPearlCooldown > 0) enderPearlCooldown--;
        if (swapToBowCooldown > 0) swapToBowCooldown--;

        if ((tickCount + getId()) % 20 == 0) {
            if (!isInventoryFull()) {
                pickupNearbyItems();
            }
        }
    }
}
