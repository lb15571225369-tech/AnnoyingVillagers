package com.pla.annoyingvillagers.events;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.clazz.PathfinderMobInventory;
import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import com.pla.annoyingvillagers.task.MobExecutionTask;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.shelmarow.combat_evolution.execution.ExecutionHandler;
import net.shelmarow.combat_evolution.execution.ExecutionTask;
import net.shelmarow.combat_evolution.execution.ExecutionTypeManager;
import net.shelmarow.combat_evolution.tickTask.TickTaskManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.Objects;

@Mod.EventBusSubscriber
public class CEBossExecuteEvent {
    public static boolean isHoldingWeapon(LivingEntity entity){
        CapabilityItem capabilityItem = EpicFightCapabilities.getItemStackCapability(entity.getItemInHand(InteractionHand.MAIN_HAND));
        return capabilityItem.getWeaponCategory() != CapabilityItem.WeaponCategories.NOT_WEAPON && capabilityItem.getWeaponCategory() != CapabilityItem.WeaponCategories.FIST;
    }

    public static boolean targetIsInRange(LivingEntity attacker, LivingEntity target, double minDist, double maxDist, double maxAngleDegrees) {
        Vec3 targetPos = target.position();
        Vec3 playerPos = attacker.position();

        double distance = playerPos.distanceTo(targetPos);
        if (distance < minDist || distance > maxDist) return false;

        float yaw = target.getYRot();
        double yawRad = Math.toRadians(yaw);
        Vec3 forward = new Vec3(-Math.sin(yawRad), 0, Math.cos(yawRad)).normalize();
        Vec3 toPlayer = playerPos.subtract(targetPos).normalize();

        double dot = forward.dot(toPlayer);
        double angle = Math.toDegrees(Math.acos(dot));

        return angle <= maxAngleDegrees;
    }

    public static boolean canExecute(LivingEntity attacker, LivingEntity victim, LivingEntityPatch<?> victimEntityPatch) {
        return attacker.isAlive() && victim.isAlive()
                && ExecutionHandler.isExecutingTarget(attacker, victim)
                && ExecutionHandler.isTargetSupported(victimEntityPatch)
                && isHoldingWeapon(attacker)
                && targetIsInRange(attacker, victim, 0, ExecutionHandler.EXECUTION_DISTANCE, 180);
    }

    public static Vec3 calculateExecutionPosition(LivingEntity target, Vec3 offset) {
        float yaw = target.getYRot();
        double rad = Math.toRadians(yaw);

        double forwardX = -Math.sin(rad);
        double forwardZ = Math.cos(rad);

        double rightX = Math.cos(rad);
        double rightZ = Math.sin(rad);

        double offsetX = forwardX * offset.x + rightX * offset.z;
        double offsetY = offset.y;
        double offsetZ = forwardZ * offset.x + rightZ * offset.z;

        return target.position().add(offsetX, offsetY, offsetZ);
    }

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        LivingEntity victim = event.getEntity();
        Entity attacker = event.getSource().getEntity();

        if (victim instanceof Player || attacker instanceof Player) return;

        LivingEntityPatch<?> victimEntityPatch = EpicFightCapabilities.getEntityPatch(victim, LivingEntityPatch.class);
        LivingEntityPatch<?> attackerEntityPatch = EpicFightCapabilities.getEntityPatch(attacker, LivingEntityPatch.class);
        if (victimEntityPatch != null && attackerEntityPatch != null
                && (attacker instanceof PlayerNpcEntity || attacker instanceof PathfinderMobInventory || attacker instanceof HerobrineMob)) {
            AssetAccessor<? extends StaticAnimation> currentAnimation = Objects.requireNonNull(victimEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();

            LivingEntity livingAttacker = (LivingEntity) attacker;
            if (ExecutionHandler.isTargetGuardBreak(currentAnimation, victimEntityPatch) && canExecute(livingAttacker, victim, victimEntityPatch)) {
                ExecutionTypeManager.Type executionType = ExecutionHandler.getExecutionType(attackerEntityPatch, victimEntityPatch);
                Level level = attacker.level();
                Vec3 frontPos = calculateExecutionPosition(victim, executionType.offset());
                if (ExecutionHandler.canStandHere(level, frontPos, attackerEntityPatch.getOriginal())) {
                    attacker.teleportTo(frontPos.x, frontPos.y, frontPos.z);
                    TickTaskManager.addTask(victim.getUUID(), new MobExecutionTask(livingAttacker, victim, executionType, executionType.totalTick()));
                }
            }
        }
    }
}