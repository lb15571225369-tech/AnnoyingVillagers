package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HerobrineDangShiTiGengXinKeShiProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) return;

        Vec3 center = new Vec3(x, y, z);

        List<Entity> nearbyEntities = world.getEntitiesOfClass(Entity.class, new AABB(center, center).inflate(16.0D))
                .stream()
                .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(center)))
                .collect(Collectors.toList());

        for (Entity target : nearbyEntities) {
            LivingEntity mobTarget = entity instanceof Mob mob ? mob.getTarget() : null;

            if (target == mobTarget) {
                if (!target.level.isClientSide() && target.getServer() != null) {
                    target.getServer().getCommands().performCommand(
                            target.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                            "tag @s add aim"
                    );
                }
            } else {
                // Clear main and offhand
                if (entity instanceof LivingEntity living) {
                    clearHand(living, InteractionHand.MAIN_HAND);
                    clearHand(living, InteractionHand.OFF_HAND);
                }

                if (!target.level.isClientSide() && target.getServer() != null) {
                    target.getServer().getCommands().performCommand(
                            target.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                            "tag @s remove aim"
                    );
                }
            }
        }

        if (entity.isPassenger()) {
            entity.stopRiding();
        }

        if (!entity.level.isClientSide() && entity.getServer() != null) {
            entity.getServer().getCommands().performCommand(
                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                    "fill ~-1 ~ ~ ~ ~ ~ minecraft:air replace"
            );
        }

        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (patch != null) {
            DynamicAnimation currentAnim = patch.getAnimator().getPlayerFor(null).getAnimation();

            if (!(currentAnim instanceof AttackAnimation
                    || currentAnim instanceof LongHitAnimation
                    || currentAnim instanceof HitAnimation)) {

                if (currentAnim instanceof KnockdownAnimation) {
                    new DelayedTask(10) {
                        @Override
                        public void run() {
                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                entity.getServer().getCommands().performCommand(
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                        "indestructible @s play \"epicfight:biped/skill/knockdown_wakeup_left\" 0 1"
                                );
                            }
                        }
                    };
                }

            } else {
                entity.clearFire();
            }
        }
    }

    private static void clearHand(LivingEntity entity, InteractionHand hand) {
        entity.setItemInHand(hand, new ItemStack(Blocks.AIR));
        if (entity instanceof Player player) {
            player.getInventory().setChanged();
        }
    }
}
