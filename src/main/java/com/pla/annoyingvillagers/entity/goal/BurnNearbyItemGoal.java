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

import java.util.EnumSet;
import java.util.List;

public class BurnNearbyItemGoal extends Goal {
    private final Mob mob;
    private final double speed;
    private final double searchRadius;
    private ItemEntity targetItem;

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

            if (mob.getRandom().nextFloat() < 0.05F && AnnoyingVillagersConfig.TURN_ON_NPC_CHAT.get()) {
                String rawName = targetItem.getItem().getDisplayName().getString();
                if (rawName.startsWith("[") && rawName.endsWith("]")) {
                    rawName = rawName.substring(1, rawName.length() - 1).toLowerCase();
                }
                String msg = "<" + mob.getDisplayName().getString() + "> " + getRandomBurnLine(rawName);
                serverLevel.getServer().getPlayerList().broadcastSystemMessage(Component.literal(msg), false);
            }
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

    private String getRandomBurnLine(String itemName) {
        String[] lines = new String[]{
                "I burned a %s",
                "I destroyed a %s",
                "Trash item removed, %s",
                "No room for this garbage, %s",
                "Away with this worthless %s",
                "This %s? Pathetic.",
                "I have no use for this %s",
                "Begone, filthy %s",
                "What even is this %s?",
                "%s? Into the flames you go.",
                "I spit on this %s",
                "Disgusting %s eliminated",
                "Only fools carry %s",
                "Carrying this %s would insult me",
                "Just setting fire to a %s, nothing special",
                "Another useless %s gone",
                "Why would anyone keep a %s?",
                "%s… trash belongs in the fire",
                "I don't hoard junk like this %s",
                "Hah, this %s is beneath me",
                "My inventory deserves better than a %s",
                "This %s offends me.",
                "Did someone seriously drop a %s?",
                "Get that %s out of my sight.",
                "This %s belongs in a dumpster fire.",
                "I'd rather hold a rotten potato than this %s",
                "The flames deserve the %s more than I do.",
                "I wouldn't even trade a stick for this %s",
                "Burning a %s improves the world",
                "Even zombies wouldn't use this %s",
                "Let fire consume the pathetic %s",
                "My hand feels dirty from touching this %s",
                "Purging the %s like it never existed",
                "This %s? Yeah, no.",
                "Useless %s detected and removed",
                "Say goodbye to your precious %s",
                "Who dropped this excuse of a %s?",
                "I refuse to carry this %s any longer.",
                "Only peasants use a %s",
                "Cursed %s gone.",
                "Trash fire activated, %s",
                "Firing up the pit for a %s",
                "Look what I found… a disgusting %s",
                "I clean the world of %s like this",
                "Into the fire, you go, %s",
                "Burning this %s makes me stronger",
                "This %s is a disgrace to all gear",
                "Flames, take this %s from my sight",
                "An offering to the lava gods, %s",
                "You call this a %s? Please.",
                "Throwing away this %s like it's nothing",
                "A toddler's toy? No, it's just a %s",
                "This %s was a mistake to create",
                "Forged in mediocrity, %s",
                "This %s makes me lose brain cells",
                "The %s has been sentenced to fire"
        };
        String template = lines[mob.getRandom().nextInt(lines.length)];
        return String.format(template, itemName);
    }
}