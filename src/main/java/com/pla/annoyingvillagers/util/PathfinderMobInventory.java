package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.entity.JevEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class PathfinderMobInventory extends PathfinderMob {
    private final SimpleContainer inventory = new SimpleContainer(27);

    public SimpleContainer getInventory() {
        return inventory;
    }

    protected PathfinderMobInventory(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("Inventory", this.inventory.createTag());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Inventory", Tag.TAG_COMPOUND)) {
            this.inventory.fromTag(tag.getList("Inventory", Tag.TAG_COMPOUND));
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
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.tickCount % 20 == 0) {
            if (!this.isAlive() || this.isRemoved() || this.level() == null) return;
            List<ItemEntity> items = this.level().getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(2));
            for (ItemEntity item : items) {
                if (!item.hasPickUpDelay() && !item.isRemoved()) {
                    final ItemEntity itemEntity = item;
                    if (!this.isAlive() || this.isRemoved() || this.level() == null) return;

                    ItemStack stack = itemEntity.getItem();
                    ItemStack remaining = stack.copy();

                    for (int i = 0; i < this.inventory.getContainerSize(); i++) {
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
                        if (this.getHealth() <= 0.0F) return;
                        itemEntity.setItem(remaining);
                    }
                }
            }
        }
    }
}
