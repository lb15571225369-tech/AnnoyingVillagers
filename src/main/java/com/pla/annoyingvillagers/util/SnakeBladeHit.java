package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.capabilities.TidalTentacleCapability;
import com.pla.annoyingvillagers.entity.Tidal_Tentacle_Entity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModCapabilities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SnakeBladeHit {
    private static boolean isCharged(Player player){
        return player.getAttackStrengthScale(0.5F) > 0.9F;
    }

    public static boolean process(ItemStack stack, LivingEntity playerIn) {
        if(stack.is(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER_AWAKENED.get()) && (!(playerIn instanceof Player) || isCharged((Player)playerIn))){
            Level worldIn = playerIn.level();
            Entity closestValid = null;
            Vec3 playerEyes = playerIn.getEyePosition(1.0F);
            HitResult hitresult = worldIn.clip(new ClipContext(playerEyes, playerEyes.add(playerIn.getLookAngle().scale(16.0D)), ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, playerIn));
            if (hitresult instanceof EntityHitResult) {
                Entity entity = ((EntityHitResult) hitresult).getEntity();
                if (!entity.equals(playerIn) && !playerIn.isAlliedTo(entity) && !entity.isAlliedTo(playerIn) && entity instanceof Mob && playerIn.hasLineOfSight(entity)) {
                    closestValid = entity;
                }
            } else {
                for (Entity entity : worldIn.getEntitiesOfClass(LivingEntity.class, playerIn.getBoundingBox().inflate(16.0D))) {
                    if (!entity.equals(playerIn) && !playerIn.isAlliedTo(entity) && !entity.isAlliedTo(playerIn) && entity instanceof Mob && playerIn.hasLineOfSight(entity)) {
                        if (closestValid == null || playerIn.distanceTo(entity) < playerIn.distanceTo(closestValid)) {
                            closestValid = entity;
                        }
                    }
                }
            }
            return launchTendonsAt(playerIn, closestValid, stack);
        }
        return false;
    }

    public static boolean launchTendonsAt(LivingEntity playerIn, Entity closestValid, ItemStack stack) {
        Level worldIn = playerIn.level();
        TidalTentacleCapability.ITentacleCapability tentacleCapability = AnnoyingVillagersModCapabilities.getCapability(playerIn, AnnoyingVillagersModCapabilities.TENTACLE_CAPABILITY);
        if (tentacleCapability != null) {
            if (TidalTentacleUtil.canLaunchTentacles(worldIn, playerIn)) {
                TidalTentacleUtil.retractFarTentacles(worldIn, playerIn);
                if (!worldIn.isClientSide) {
                    if (closestValid != null) {
                        Tidal_Tentacle_Entity segment = AnnoyingVillagersModEntities.TIDAL_TENTACLE.get().create(worldIn);
                        segment.copyPosition(playerIn);
                        worldIn.addFreshEntity(segment);
                        segment.setCreatorEntityUUID(playerIn.getUUID());
                        segment.setFromEntityID(playerIn.getId());
                        segment.setToEntityID(closestValid.getId());
                        segment.copyPosition(playerIn);
                        segment.setProgress(0.0F);
                        TidalTentacleUtil.setLastTentacle(playerIn, segment);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
