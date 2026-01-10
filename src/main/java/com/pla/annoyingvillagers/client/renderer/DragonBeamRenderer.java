package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.DragonBeamEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class DragonBeamRenderer extends EntityRenderer<DragonBeamEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/dragon_beam.png");

    public DragonBeamRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    public @NotNull Vec3 getRenderOffset(DragonBeamEntity dragonBeam, float p_114484_) {
        return new Vec3(dragonBeam.level().random.nextGaussian() * 0.03, dragonBeam.level().random.nextGaussian() * 0.03, dragonBeam.level().random.nextGaussian() * 0.03);
    }

    public void render(@NotNull DragonBeamEntity beam, float entityYaw, float delta, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int light) {
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull DragonBeamEntity dragonBeam) {
        return TEXTURE;
    }
}