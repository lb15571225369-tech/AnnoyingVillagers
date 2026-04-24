package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class BurnNearbyItemGoal extends Goal {
    private final Mob mob;
    private final double speed;
    private final double searchRadius;
    private ItemEntity targetItem;

    private static List<String> keys(String prefix, int count) {
        List<String> list = new ArrayList<>(count);
        for (int i = 1; i <= count; i++) {
            list.add(prefix + "." + i);
        }
        return List.copyOf(list);
    }

    private static final List<String> burnMessageKeys = keys("burn_item.annoyingvillagers", 56);

    public BurnNearbyItemGoal(Mob mob, double speed, double searchRadius) {
        this.mob = mob;
        this.speed = speed;
        this.searchRadius = searchRadius;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (mob.level().isClientSide) return false;
        if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return false;
        if (mob.isPassenger()) return false;
        if (mob.getTarget() != null) return false;
        if (mob.isNoAi()) return false;
        if (!AnnoyingVillagersConfig.AV_MOB_CAN_BURN_ITEM.get()) return false;
        if (mob instanceof PlayerNpcEntity playerNpcEntity && playerNpcEntity.isHealing()) {
            return false;
        }
        if (mob instanceof AVNpc avNpc && avNpc.isHealing()) {
            return false;
        }
        targetItem = findTargetItem();
        return targetItem != null;
    }

    @Override
    public boolean canContinueToUse() {
        if (mob.level().isClientSide) return false;
        if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return false;
        if (mob.isPassenger()) return false;
        if (mob.getTarget() != null) return false;
        if (mob.isNoAi()) return false;
        if (!AnnoyingVillagersConfig.AV_MOB_CAN_BURN_ITEM.get()) return false;

        return targetItem != null && targetItem.isAlive() && !targetItem.getItem().isEmpty();
    }

    @Override
    public void start() {
        if (mob.getMainHandItem().getItem() != Items.FLINT_AND_STEEL) {
            mob.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.FLINT_AND_STEEL));
        }
        mob.getNavigation().moveTo(targetItem, speed);
    }

    @Override
    public void tick() {
        if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return;
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;
        if (targetItem == null || !targetItem.isAlive() || targetItem.getItem().isEmpty()) {
            return;
        }

        if (mob.getNavigation().isDone()) {
            if (mob.getMainHandItem().getItem() != Items.FLINT_AND_STEEL) {
                mob.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.FLINT_AND_STEEL));
            }
            var path = mob.getNavigation().createPath(targetItem, 0);
            if (path == null) {
                return;
            }
            mob.getNavigation().moveTo(targetItem, speed);
        }

        mob.getLookControl().setLookAt(
                targetItem.getX(),
                targetItem.getY() + targetItem.getBbHeight() / 2.0,
                targetItem.getZ(),
                30.0F, 30.0F
        );

        double dist = mob.distanceTo(targetItem);
        if (dist <= 1.5D) {
            ItemStack burnedStack = targetItem.getItem().copy();

            mob.swing(InteractionHand.MAIN_HAND);
            targetItem.kill();

            serverLevel.sendParticles(
                    ParticleTypes.FLAME,
                    targetItem.getX(), targetItem.getY(), targetItem.getZ(),
                    8, 0.2, 0.2, 0.2, 0.01
            );

            mob.level().playSound(
                    null,
                    mob.blockPosition(),
                    SoundEvents.FLINTANDSTEEL_USE,
                    SoundSource.HOSTILE,
                    1.0f,
                    1.0f
            );

            tryBroadcastBurnMessage(serverLevel, burnedStack);
        }
    }

    @Override
    public void stop() {
        targetItem = null;
        mob.getNavigation().stop();
        if (findTargetItem() == null) {
            restoreMainWeapon();
        }
    }

    private void tryBroadcastBurnMessage(ServerLevel serverLevel, ItemStack burnedStack) {
        if (!AnnoyingVillagersConfig.TURN_ON_NPC_CHAT.get()) return;
        if (!(mob instanceof PlayerNpcEntity)) return;
        if (mob.getRandom().nextFloat() >= 0.05F) return;

        String key = burnMessageKeys.get(mob.getRandom().nextInt(burnMessageKeys.size()));

        serverLevel.getServer().getPlayerList().broadcastSystemMessage(
                Component.empty()
                        .append(Component.literal("<"))
                        .append(mob.getDisplayName())
                        .append(Component.literal("> "))
                        .append(Component.translatable(key, burnedStack.getHoverName())),
                false
        );
    }

    private void restoreMainWeapon() {
        ItemStack weapon = null;
        if (mob instanceof PlayerNpcEntity playerNpcEntity) {
            weapon = playerNpcEntity.getMainWeaponItem();
            playerNpcEntity.setPlayingIdleCooldown(playerNpcEntity.getPlayingIdleCooldown() + 40);
        }
        if (mob instanceof AVNpc avNpc) {
            weapon = avNpc.getMainWeaponItem();
            avNpc.setPlayingIdleCooldown(avNpc.getPlayingIdleCooldown() + 40);
        }
        if (weapon != null && !weapon.isEmpty()) {
            mob.setItemSlot(EquipmentSlot.MAINHAND, weapon.copy());
        } else {
            mob.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
    }

    private ItemEntity findTargetItem() {
        List<ItemEntity> items = mob.level().getEntitiesOfClass(
                ItemEntity.class,
                mob.getBoundingBox().inflate(searchRadius),
                e -> e.isAlive() && !e.getItem().isEmpty()
        );

        if (items.isEmpty()) return null;
        ItemEntity best = null;
        double bestDist = Double.MAX_VALUE;
        for (ItemEntity it : items) {
            double d = mob.distanceToSqr(it);
            if (d < bestDist) {
                bestDist = d;
                best = it;
            }
        }
        return best;
    }
}