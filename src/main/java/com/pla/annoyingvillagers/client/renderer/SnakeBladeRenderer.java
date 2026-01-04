/*
 * SPDX-License-Identifier: CC-BY-NC-ND-4.0
 *
 * Portions of this file are based on source code from "The Cataclysm"
 * © MCL_Ender (mcl_ender). The Cataclysm assets are all rights reserved.
 *
 * The Cataclysm source code is licensed under Creative Commons
 * Attribution-NonCommercial-NoDerivatives 4.0 International (CC BY-NC-ND 4.0).
 */

package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import com.mojang.math.Axis;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.model.ModelSnakeBlade;
import com.pla.annoyingvillagers.client.model.ModelSnakeBladeFragment;
import com.pla.annoyingvillagers.entity.SnakeBladeEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.SnakeBladeHit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.function.DoubleFunction;

public class SnakeBladeRenderer extends EntityRenderer<SnakeBladeEntity> {
    private static final ResourceLocation SNAKE_BLADE_TEXTURE =ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID,"textures/entities/snake_blade.png");
    private static final ResourceLocation FRAGMENT_CHAIN_TEXTURE =ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID,"textures/entities/fragment_chain.png");
    private static ModelSnakeBlade snakeBladeModel;
    private static ModelSnakeBladeFragment tongueModel;
    public static final int MAX_NECK_SEGMENTS = 128;
    private static final float FRAG_LEN = 0.45F;
    private static final float HEAD_CLEAR = 0.35F;
    private final EntityRendererProvider.Context context;

    private static float tipClear(SnakeBladeEntity snakeBladeEntity) {
        return (snakeBladeEntity.hasBlade() || snakeBladeEntity.isRetracting()) ? HEAD_CLEAR : 0.0F;
    }

    public SnakeBladeRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
        ModelPart fragRoot = renderManagerIn.bakeLayer(ModelSnakeBladeFragment.LAYER_LOCATION);
        this.tongueModel = new ModelSnakeBladeFragment<>(fragRoot);
        ModelPart bladeRoot = renderManagerIn.bakeLayer(ModelSnakeBlade.LAYER_LOCATION);
        this.snakeBladeModel = new ModelSnakeBlade<>(bladeRoot);
        this.context = renderManagerIn;
    }

    @Override
    public boolean shouldRender(SnakeBladeEntity entity, Frustum frustum, double x, double y, double z) {
        Entity next = entity.getFromEntity();
        return next != null && frustum.isVisible(entity.getBoundingBox().minmax(next.getBoundingBox())) || super.shouldRender(entity, frustum, x, y, z);
    }

    @Override
    public void render(@NotNull SnakeBladeEntity entity, float pEntityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int light) {
        super.render(entity, pEntityYaw, partialTicks, poseStack, buffer, light);
        poseStack.pushPose();
        Entity fromEntity = entity.getFromEntity();
        float x = (float)Mth.lerp(partialTicks, entity.xo, entity.getX());
        float y = (float)Mth.lerp(partialTicks, entity.yo, entity.getY());
        float z = (float)Mth.lerp(partialTicks, entity.zo, entity.getZ());

        if (fromEntity != null) {
            float progress = (entity.prevProgress + (entity.getProgress() - entity.prevProgress) * partialTicks) / SnakeBladeEntity.MAX_EXTEND_TIME;
            Vec3 distVec;

            float tipOffset;
            if (entity.isGuard()) {
                tipOffset = 1.8F;
            } else {
                tipOffset = 2.2F;
            }
            Vec3 swordPos = SnakeBladeHit.getToolTipPos(fromEntity, partialTicks, tipOffset);

            if (swordPos != null) {
                distVec = swordPos.subtract(x, y + 1.2F, z);
            } else {
                distVec = getPositionOfPriorMob(entity, fromEntity, partialTicks).subtract(x, y, z);
            }

            Vec3 to = distVec.scale(1F - progress);
            Vec3 from = distVec;
            int segmentCount = 0;
            Vec3 currentNeckButt = from;
            VertexConsumer snakebladeFragmentConsumer;
            if (entity.isEnchanted()) {
                snakebladeFragmentConsumer = ItemRenderer.getFoilBuffer(
                        buffer,
                        RenderType.entityCutoutNoCull(FRAGMENT_CHAIN_TEXTURE),
                        true,
                        true
                );
            } else {
                snakebladeFragmentConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(FRAGMENT_CHAIN_TEXTURE));
            }

            if (entity.isGuard()) {
                double distanceLeft = from.distanceTo(to);
                double buildUpTo = Math.max(0.0, distanceLeft - tipClear(entity));
                while (segmentCount < MAX_NECK_SEGMENTS && buildUpTo > 1.0e-3) {
                    double step = Math.min(buildUpTo, FRAG_LEN);

                    Vec3 dir = to.subtract(currentNeckButt);
                    Vec3 next = dir.normalize().scale(step).add(currentNeckButt);

                    int neckLight = getLightColor(entity, next.add(x, y, z));
                    renderNeckCube(currentNeckButt, next, poseStack, snakebladeFragmentConsumer, neckLight, OverlayTexture.NO_OVERLAY, 0);

                    currentNeckButt = next;
                    buildUpTo -= step;
                    segmentCount++;
                }
            } else {
                double distanceLeft = from.distanceTo(to);
                double buildUpTo = Math.max(0.0, distanceLeft - tipClear(entity));

                if (distanceLeft > 1.0e-4) {
                    Vec3 fromW = new Vec3(x, y, z).add(from);
                    Vec3 toW = new Vec3(x, y, z).add(to);

                    Vec3 fwd = toW.subtract(fromW).normalize();
                    Vec3 right = new Vec3(fwd.z, 0, -fwd.x);
                    if (right.lengthSqr() < 1.0e-6) right = new Vec3(1, 0, 0);
                    right = right.normalize();

                    double ampSide = Mth.clamp(distanceLeft * 0.18, 0.25, 2.0);
                    double ampUp = Mth.clamp(distanceLeft * 0.10, 0.00, 1.0);

                    int entityId = entity.getId();
                    int fromEntityID = entity.getFromEntityID();
                    int toEntityID = entity.getToEntityID();
                    long seed = (((long) entityId) << 32) ^ (((long) fromEntityID) << 16) ^ (long) toEntityID ^ 0x9E3779B97F4A7C15L;
                    RandomSource rand = RandomSource.create(seed);
                    double sideSign = rand.nextBoolean() ? 1.0 : -1.0;

                    double time = (double) entity.tickCount + (double) partialTicks;
                    double phase1 = rand.nextDouble() * Math.PI * 2.0;
                    double phase2 = rand.nextDouble() * Math.PI * 2.0;
                    double phaseYaw = rand.nextDouble() * Math.PI * 2.0;

                    double jitterSideBase = 0.05;
                    double jitterUpBase = 0.03;

                    Vec3 finalRight = right;

                    DoubleFunction<Vec3> wavePoint = (s) -> {
                        double u = s / distanceLeft;
                        double sin = Math.sin(Math.PI * u);

                        Vec3 base = fromW
                                .add(fwd.scale(s))
                                .add(finalRight.scale(ampSide * sideSign * sin))
                                .add(0, ampUp * sin, 0);

                        double w1 = 20.0;
                        double w2 = 17.0;

                        double headBias = Math.pow(sin, 0.8);
                        double jitterSide = (jitterSideBase * (0.6 + 0.8 * headBias))
                                * Math.sin(w1 * time + 28.0 * u + phase1);
                        double jitterUp = (jitterUpBase * (0.5 + 0.7 * headBias))
                                * Math.sin(w2 * time + 19.0 * u + phase2);

                        return base
                                .add(finalRight.scale(jitterSide))
                                .add(0, jitterUp, 0);
                    };

                    double s = 0.0;
                    Vec3 prevW = wavePoint.apply(s);
                    while (segmentCount < MAX_NECK_SEGMENTS && buildUpTo > 1.0e-3) {
                        double step = Math.min(buildUpTo, FRAG_LEN);
                        s += step;
                        Vec3 nextW = wavePoint.apply(Math.min(s, distanceLeft - HEAD_CLEAR));

                        Vec3 prevLocal = prevW.subtract(x, y, z);
                        Vec3 nextLocal = nextW.subtract(x, y, z);

                        int neckLight = getLightColor(entity, nextW);

                        float yawShake = (float) (
                                4.0 * Math.sin(18.0 * time + 0.9 * segmentCount + phaseYaw)
                        );

                        renderNeckCube(prevLocal, nextLocal, poseStack, snakebladeFragmentConsumer, neckLight, OverlayTexture.NO_OVERLAY, yawShake);

                        prevW = nextW;
                        buildUpTo -= step;
                        segmentCount++;
                    }

                    currentNeckButt = prevW.subtract(x, y, z);
                } else {
                    while (segmentCount < MAX_NECK_SEGMENTS && buildUpTo > 1.0e-3) {
                        double step = Math.min(buildUpTo, FRAG_LEN);
                        Vec3 dir = to.subtract(currentNeckButt);
                        Vec3 next = dir.normalize().scale(step).add(currentNeckButt);
                        int neckLight = getLightColor(entity, next.add(x, y, z));

                        double time = (double) entity.tickCount + (double) partialTicks;
                        float yawShake = (float) (3.0 * Math.sin(16.0 * time + 0.7 * segmentCount));

                        renderNeckCube(currentNeckButt, next, poseStack, snakebladeFragmentConsumer, neckLight, OverlayTexture.NO_OVERLAY, yawShake);
                        currentNeckButt = next;
                        buildUpTo -= step;
                        segmentCount++;
                    }
                }
            }

            VertexConsumer snakeBladeComsumer;
            if (entity.isEnchanted()) {
                snakeBladeComsumer = ItemRenderer.getFoilBuffer(
                        buffer,
                        RenderType.entityCutoutNoCull(SNAKE_BLADE_TEXTURE),
                        true,
                        true
                );
            } else {
                snakeBladeComsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(SNAKE_BLADE_TEXTURE));
            }
            if (entity.hasBlade() || entity.isRetracting()) {
                poseStack.pushPose();
                poseStack.translate(to.x, to.y, to.z);

                Vec3 headDir = to.subtract(currentNeckButt);
                float rotY = (float)(Mth.atan2(headDir.x, headDir.z) * 180F / Math.PI);
                float rotX = (float)(-Mth.atan2(headDir.y, headDir.horizontalDistance()) * 180F / Math.PI);

                poseStack.mulPose(Axis.YP.rotationDegrees(rotY));
                poseStack.mulPose(Axis.XP.rotationDegrees(rotX));

                double time = (double)entity.tickCount + (double)partialTicks;
                float headYawWobble = (float)(1.6 * Math.sin(22.0 * time + 0.5));
                float headPitchWobble = (float)(1.0 * Math.sin(27.0 * time + 1.2));
                poseStack.mulPose(Axis.YP.rotationDegrees(headYawWobble));
                poseStack.mulPose(Axis.XP.rotationDegrees(headPitchWobble));

                snakeBladeModel.renderToBuffer(poseStack, snakeBladeComsumer,
                        getLightColor(entity, to.add(x, y, z)), OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
                poseStack.popPose();
            }
        }
        poseStack.popPose();
    }

    public static void renderNeckCube(Vec3 from, Vec3 to, PoseStack poseStack, VertexConsumer buffer, int packedLightIn, int overlayCoords, float additionalYaw) {
        Vec3 dir = to.subtract(from);

        float yaw   = (float)(Mth.atan2(dir.x, dir.z) * (180F / Math.PI));
        float pitch = (float)(-Mth.atan2(dir.y, dir.horizontalDistance()) * (180F / Math.PI));

        poseStack.pushPose();
        poseStack.translate(from.x, from.y, from.z);
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw + additionalYaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch));

        tongueModel.renderToBuffer(poseStack, buffer, packedLightIn, overlayCoords, 1, 1, 1, 1);
        poseStack.popPose();
    }

    private Vec3 getPositionOfPriorMob(SnakeBladeEntity segment, Entity mob, float partialTicks){
        double d4 = Mth.lerp(partialTicks, mob.xo, mob.getX());
        double d5 = Mth.lerp(partialTicks, mob.yo, mob.getY());
        double d6 = Mth.lerp(partialTicks, mob.zo, mob.getZ());
        float f3 = 0;
        if(mob instanceof Player && segment.isCreator(mob)){
            Player player = (Player) mob;
            float f = player.getAttackAnim(partialTicks);
            float f1 = Mth.sin(Mth.sqrt(f) * (float) Math.PI);
            float f2 = Mth.lerp(partialTicks, player.yBodyRotO, player.yBodyRot) * ((float) Math.PI / 180F);
            int i = player.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;

            ItemStack itemstack = player.getMainHandItem();
            if (!itemstack.is(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get())) {
                i = -i;
            }
            double d0 = (double) Mth.sin(f2);
            double d1 = (double) Mth.cos(f2);
            double d2 = (double) i * 0.35D;
            if ((this.entityRenderDispatcher.options == null || this.entityRenderDispatcher.options.getCameraType().isFirstPerson()) && player == Minecraft.getInstance().player) {
                double d7 = 960.0D / (double)this.entityRenderDispatcher.options.fov().get().intValue();
                Vec3 vec3 = this.entityRenderDispatcher.camera.getNearPlane().getPointOnPlane((float) i * 0.6F, -1);
                vec3 = vec3.scale(d7);
                vec3 = vec3.yRot(f1 * 0.25F);
                vec3 = vec3.xRot(-f1 * 0.35F);
                d4 = Mth.lerp((double) partialTicks, player.xo, player.getX()) + vec3.x;
                d5 = Mth.lerp((double) partialTicks, player.yo, player.getY()) + vec3.y;
                d6 = Mth.lerp((double) partialTicks, player.zo, player.getZ()) + vec3.z;
                f3 = player.getEyeHeight() * 0.5F;
            } else {
                d4 = Mth.lerp((double) partialTicks, player.xo, player.getX()) - d1 * d2 - d0 * 0.2D;
                d5 = player.yo + (double) player.getEyeHeight() + (player.getY() - player.yo) * (double) partialTicks - 1D;
                d6 = Mth.lerp((double) partialTicks, player.zo, player.getZ()) - d0 * d2 + d1 * 0.2D;
                f3 = (player.isCrouching() ? -0.1875F : 0.0F) - player.getEyeHeight() * 0.4F;
            }
        }

        return new Vec3(d4, d5 + f3, d6);
    }

    private int getLightColor(Entity head, Vec3 vec3) {
        Vec3i vec3i = new Vec3i(
                Mth.floor(vec3.x),
                Mth.floor(vec3.y),
                Mth.floor(vec3.z)
        );
        BlockPos blockpos = new BlockPos(vec3i);
        if(head.level().hasChunkAt(blockpos)){
            int i = LevelRenderer.getLightColor(head.level(), blockpos);
            int j = LevelRenderer.getLightColor(head.level(), blockpos.above());
            int k = i & 255;
            int l = j & 255;
            int i1 = i >> 16 & 255;
            int j1 = j >> 16 & 255;
            return (k > l ? k : l) | (i1 > j1 ? i1 : j1) << 16;
        }else{
            return 0;
        }
    }

    @Override
    public ResourceLocation getTextureLocation(SnakeBladeEntity entity) {
        return SNAKE_BLADE_TEXTURE;
    }

}