package com.pla.annoyingvillagers.compat.player_mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.util.HerobrineEyesUtil;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.client.model.MeshProvider;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.layer.ModelRenderLayer;
import yesman.epicfight.client.mesh.HumanoidMesh;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class PlayerMobEyeLayer<E extends LivingEntity, AM extends HumanoidMesh>
        extends ModelRenderLayer<E, LivingEntityPatch<E>, HumanoidModel<E>, RenderLayer<E, HumanoidModel<E>>, AM> {

    public PlayerMobEyeLayer(MeshProvider<AM> mesh) { super(mesh); }

    private ResourceLocation pickTexture(E e) {
        if (EntityType.getKey(e.getType()).equals(new ResourceLocation(AnnoyingVillagers.MODID, "herobrine_5"))) {
            String name = e.hasCustomName() ? e.getCustomName().getString() : e.getName().getString();
            return HerobrineEyesUtil.getHerobrineEyesTexture(name);
        }
        return null;
    }

    @Override
    protected void renderLayer(LivingEntityPatch<E> eLivingEntityPatch, E e, @Nullable RenderLayer<E, HumanoidModel<E>> eHumanoidModelRenderLayer, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, OpenMatrix4f[] openMatrix4fs, float v, float v1, float v2, float v3) {
        ResourceLocation tex = pickTexture(e);
        if (tex == null) return;

        ((AnimatedMesh)this.mesh.get()).draw(
                poseStack, multiBufferSource, RenderType.eyes(tex),
                0x00F000F0,
                1f,1f,1f,1f, OverlayTexture.NO_OVERLAY,
                eLivingEntityPatch.getArmature(), openMatrix4fs
        );
    }
}
