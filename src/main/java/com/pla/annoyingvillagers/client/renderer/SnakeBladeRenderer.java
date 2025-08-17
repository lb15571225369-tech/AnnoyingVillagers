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
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

public class SnakeBladeRenderer extends EntityRenderer<SnakeBladeEntity> {
    private static final ResourceLocation SNAKE_BLADE_TEXTURE =new ResourceLocation(AnnoyingVillagers.MODID,"textures/entities/snake_blade.png");
    private static final ResourceLocation FRAGMENT_CHAIN_TEXTURE =new ResourceLocation(AnnoyingVillagers.MODID,"textures/entities/fragment_chain.png");
    private static ModelSnakeBlade snakeBladeModel;
    private static ModelSnakeBladeFragment tongueModel;
    public static final int MAX_NECK_SEGMENTS = 128;
    private static final float FRAG_LEN     = 0.6F;
    private static final float HEAD_CLEAR   = 0.35F;


    public SnakeBladeRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
        ModelPart fragRoot = renderManagerIn.bakeLayer(ModelSnakeBladeFragment.LAYER_LOCATION);
        this.tongueModel = new ModelSnakeBladeFragment<>(fragRoot);
        ModelPart bladeRoot = renderManagerIn.bakeLayer(ModelSnakeBlade.LAYER_LOCATION);
        this.snakeBladeModel = new ModelSnakeBlade<>(bladeRoot);
    }

    @Override
    public boolean shouldRender(SnakeBladeEntity entity, Frustum frustum, double x, double y, double z) {
        Entity next = entity.getFromEntity();
        return next != null && frustum.isVisible(entity.getBoundingBox().minmax(next.getBoundingBox())) || super.shouldRender(entity, frustum, x, y, z);
    }

    @Override
    public void render(SnakeBladeEntity entity, float pEntityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
        super.render(entity, pEntityYaw, partialTicks, poseStack, buffer, light);
        poseStack.pushPose();
        Entity fromEntity = entity.getFromEntity();
        float x = (float)Mth.lerp(partialTicks, entity.xo, entity.getX());
        float y = (float)Mth.lerp(partialTicks, entity.yo, entity.getY());
        float z = (float)Mth.lerp(partialTicks, entity.zo, entity.getZ());

        if (fromEntity != null) {
            float progress = (entity.prevProgress + (entity.getProgress() - entity.prevProgress) * partialTicks) / SnakeBladeEntity.MAX_EXTEND_TIME;
            Vec3 distVec;

            Vec3 swordPos = SnakeBladeHit.getJointWithTranslation(
                    fromEntity,
                    new Vec3f(0F, 0F, 0F),
                    Armatures.BIPED.toolR
            );

            if (swordPos != null) {
                distVec = swordPos.subtract(x, y + 1.2F, z);
                Minecraft.getInstance().particleEngine.createParticle(ParticleTypes.ENCHANT, swordPos.x, swordPos.y, swordPos.z, entity.getDeltaMovement().x, entity.getDeltaMovement().y, entity.getDeltaMovement().z);
            } else {
                distVec = getPositionOfPriorMob(entity, fromEntity, partialTicks).subtract(x, y, z);
            }

            Vec3 to = distVec.scale(1F - progress);
            Vec3 from = distVec;
            int segmentCount = 0;
            Vec3 currentNeckButt = from;
            VertexConsumer neckConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(FRAGMENT_CHAIN_TEXTURE));
            double distanceLeft = from.distanceTo(to);
            double buildUpTo = Math.max(0.0, distanceLeft - HEAD_CLEAR);
            while (segmentCount < MAX_NECK_SEGMENTS && buildUpTo > 1.0e-3) {
                double step = Math.min(buildUpTo, FRAG_LEN);

                Vec3 dir = to.subtract(currentNeckButt);
                Vec3 next = dir.normalize().scale(step).add(currentNeckButt);

                int neckLight = getLightColor(entity, next.add(x, y, z));
                renderNeckCube(currentNeckButt, next, poseStack, neckConsumer, neckLight, OverlayTexture.NO_OVERLAY, 0);

                currentNeckButt = next;
                buildUpTo      -= step;
                segmentCount++;
            }
            VertexConsumer clawConsumer  = buffer.getBuffer(RenderType.entityCutoutNoCull(SNAKE_BLADE_TEXTURE));
            if (entity.hasClaw() || entity.isRetracting()) {
                poseStack.pushPose();
                poseStack.translate(to.x, to.y, to.z);

                Vec3 headDir = to.subtract(currentNeckButt);
                float rotY = (float)(Mth.atan2(headDir.x, headDir.z) * 180F / Math.PI);
                float rotX = (float)(-Mth.atan2(headDir.y, headDir.horizontalDistance()) * 180F / Math.PI);
                poseStack.mulPose(Axis.YP.rotationDegrees(rotY));
                poseStack.mulPose(Axis.XP.rotationDegrees(rotX));

                snakeBladeModel.renderToBuffer(poseStack, clawConsumer,
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

    private double modifyVecAngle(double dimension) {
        float abs = (float) Math.abs(dimension);
        return Math.signum(dimension) * Mth.clamp(Math.pow(abs, 0.1), 0.05 * abs, abs);
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