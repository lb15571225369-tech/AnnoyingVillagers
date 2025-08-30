package com.pla.annoyingvillagers.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public final class GroundSlamUtil {
    private GroundSlamUtil() {}

    public static Vec3 jointWorldPoint(LivingEntityPatch<?> patch, Vec3f localOffset, Joint joint) {
        Vec3 pos = patch.getOriginal().position();
        OpenMatrix4f model =
                patch.getArmature()
                        .getBindedTransformFor(patch.getAnimator().getPose(1.0F), joint)
                        .mulFront(OpenMatrix4f.createTranslation((float) pos.x, (float) pos.y, (float) pos.z)
                                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                                        .mulBack(patch.getModelMatrix(1.0F))));
        return OpenMatrix4f.transform(model, localOffset.toDoubleVector());
    }

    public static BlockHitResult raycastDown(Level level, Vec3 start, LivingEntityPatch<?> patch, double maxDist) {
        Vec3 end = start.subtract(0.0, maxDist, 0.0);
        BlockHitResult hit = level.clip(new ClipContext(
                start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, patch.getOriginal()));
        return hit.getType() == HitResult.Type.BLOCK ? hit : null;
    }
}