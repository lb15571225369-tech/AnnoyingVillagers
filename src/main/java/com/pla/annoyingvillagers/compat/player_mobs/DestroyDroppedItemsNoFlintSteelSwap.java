package com.pla.annoyingvillagers.compat.player_mobs;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;

import java.util.EnumSet;
import java.util.List;

public class DestroyDroppedItemsNoFlintSteelSwap extends Goal {
    private final Mob mob;
    private ItemEntity targetItem;

    public DestroyDroppedItemsNoFlintSteelSwap(Mob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        List<ItemEntity> items = mob.level.getEntitiesOfClass(ItemEntity.class, mob.getBoundingBox().inflate(10));
        targetItem = items.stream()
                .filter(item -> item.isAlive() && !item.getItem().isEmpty())
                .findFirst()
                .orElse(null);
        return isHoldingFlintAndSteel() && targetItem != null;
    }

    @Override
    public void tick() {
        if (targetItem == null || !targetItem.isAlive()) return;

        mob.getNavigation().moveTo(targetItem, 1.0);

        if (mob.distanceTo(targetItem) < 1.5 && mob.level instanceof ServerLevel serverLevel) {
            mob.swing(InteractionHand.OFF_HAND);

            targetItem.kill();

            serverLevel.sendParticles(ParticleTypes.FLAME,
                    targetItem.getX(), targetItem.getY(), targetItem.getZ(),
                    8, 0.2, 0.2, 0.2, 0.01);

            mob.level.playSound(null, mob.blockPosition(), SoundEvents.FLINTANDSTEEL_USE,
                    SoundSource.HOSTILE, 1.0f, 1.0f);

            targetItem = null;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return targetItem != null && targetItem.isAlive() && isHoldingFlintAndSteel();
    }

    @Override
    public void stop() {
        targetItem = null;
    }

    private boolean isHoldingFlintAndSteel() {
        return (mob.getMainHandItem().getItem() == Items.FLINT_AND_STEEL)
                || (mob.getOffhandItem().getItem() == Items.FLINT_AND_STEEL);
    }
}

