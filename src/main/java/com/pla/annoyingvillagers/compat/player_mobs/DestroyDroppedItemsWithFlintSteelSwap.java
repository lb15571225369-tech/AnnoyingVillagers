package com.pla.annoyingvillagers.compat.player_mobs;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.HitAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.EnumSet;
import java.util.List;

public class DestroyDroppedItemsWithFlintSteelSwap extends Goal {
    private final Mob mob;
    private ItemEntity targetItem;
    private String originalItemId = null;
    private DelayedTask pendingRestoreTask = null;

    public DestroyDroppedItemsWithFlintSteelSwap(Mob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (mob.getTarget() != null && mob.getTarget().isAlive()) {
            return false;
        }

        LivingEntityPatch<?> patch = (LivingEntityPatch<?>) EpicFightCapabilities.getEntityPatch(mob, LivingEntityPatch.class);
        if (patch != null) {
            var anim = patch.getAnimator().getPlayerFor(null).getAnimation();
            if (anim instanceof AttackAnimation || anim instanceof HitAnimation || anim instanceof LongHitAnimation) {
                return false;
            }
        }

        List<ItemEntity> items = mob.level.getEntitiesOfClass(ItemEntity.class, mob.getBoundingBox().inflate(10));
        targetItem = items.stream()
                .filter(item -> item.isAlive() && !item.getItem().isEmpty())
                .findFirst()
                .orElse(null);

        return targetItem != null;
    }

    @Override
    public void start() {
        ItemStack held = mob.getMainHandItem();
        if (!held.isEmpty() && held.getItem() != net.minecraft.world.item.Items.FLINT_AND_STEEL) {
            CompoundTag tag = new CompoundTag();
            held.save(tag);
            originalItemId = tag.toString();
        } else {
            originalItemId = "{id:\"minecraft:air\",Count:1b}";
        }
    }

    @Override
    public void tick() {
        if (targetItem == null || !targetItem.isAlive()) {
            if (pendingRestoreTask == null) {
                pendingRestoreTask = new DelayedTask(100) {
                    @Override
                    public void run() {
                        if (mob.level instanceof ServerLevel serverLevel) {
                            serverLevel.getServer().tell(
                                    new TickTask(0, () -> {
                                stop();
                                pendingRestoreTask = null;
                            }));
                        }
                    }
                };
            }
        } else {
            mob.getNavigation().moveTo(targetItem, 1.0);
            if (mob.distanceTo(targetItem) < 1.5 && mob.level instanceof ServerLevel serverLevel) {
                try {
                    mob.getServer().getCommands().performCommand(
                            mob.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                            "item replace entity @s weapon.mainhand with minecraft:flint_and_steel"
                    );
                } catch (Exception e) {
                    AnnoyingVillagers.LOGGER.warn("[AV DEBUG] Failed to switch to flint and steel: {}", e.getMessage());
                }

                serverLevel.getServer().tell(new TickTask(0, () -> {
                    try {
                        mob.swing(InteractionHand.MAIN_HAND);

                        if (targetItem != null && targetItem.isAlive()) {
                            targetItem.kill();

                            serverLevel.sendParticles(ParticleTypes.FLAME,
                                    targetItem.getX(), targetItem.getY(), targetItem.getZ(),
                                    8, 0.2, 0.2, 0.2, 0.01);

                            mob.level.playSound(null, mob.blockPosition(), SoundEvents.FLINTANDSTEEL_USE,
                                    SoundSource.HOSTILE, 1.0f, 1.0f);
                        }

                        targetItem = null;
                    } catch (Exception e) {
                        AnnoyingVillagers.LOGGER.warn("[AV DEBUG] Error during deferred burn action: {}", e.getMessage());
                    }
                }));
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        return targetItem != null && targetItem.isAlive();
    }

    @Override
    public void stop() {
        if (originalItemId != null && mob.level instanceof ServerLevel) {
            try {
                CompoundTag fullTag = TagParser.parseTag(originalItemId);

                String id = fullTag.getString("id");
                String nbtPart = fullTag.contains("tag") ? fullTag.getCompound("tag").toString() : "";

                try {
                    String cmd = "item replace entity @s weapon.mainhand with " + id + nbtPart;
                    mob.getServer().getCommands().performCommand(
                            mob.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                            cmd
                    );
                } catch (Exception e) {
                    AnnoyingVillagers.LOGGER.warn("[AV DEBUG] Failed to switch to old item: {}", e.getMessage());
                }
            } catch (Exception e) {
                System.err.println("Failed to parse or restore item: " + e.getMessage());
            }
        }
        originalItemId = null;
    }
}

