//package com.pla.annoyingvillagers.compat.player_mobs;
//
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.entity.Mob;
//import net.minecraft.world.entity.ai.goal.Goal;
//import net.minecraft.world.entity.item.ItemEntity;
//
//import java.util.EnumSet;
//import java.util.List;
//
//public class BurningItemGoal extends Goal {
//    private final Mob mob;
//    private ItemEntity targetItem;
//
//    public BurningItemGoal(Mob mob) {
//        this.mob = mob;
//        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
//    }
//
//    @Override
//    public boolean canUse() {
//        if (mob.getPersistentData().getBoolean("burn_item")) return false;
//        List<ItemEntity> items = mob.level.getEntitiesOfClass(ItemEntity.class, mob.getBoundingBox().inflate(10));
//        targetItem = items.stream()
//                .filter(item -> item.isAlive() && !item.getItem().isEmpty())
//                .findFirst()
//                .orElse(null);
//        return targetItem != null;
//    }
//
//    @Override
//    public void tick() {
//        if (targetItem == null || !targetItem.isAlive()) return;
//
//        mob.getNavigation().moveTo(targetItem, 1.0);
//
//        if (mob.distanceTo(targetItem) < 1.5 && mob.level instanceof ServerLevel) {
//            mob.getPersistentData().putBoolean("burn_item", true);
//        }
//    }
//
//    @Override
//    public boolean canContinueToUse() {
//        return targetItem != null && targetItem.isAlive();
//    }
//
//    @Override
//    public void stop() {
//        targetItem = null;
//    }
//}
//
