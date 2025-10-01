package com.pla.annoyingvillagers.compat.player_mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.util.HerobrineEyesUtil;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
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

public class PlayerMobEpicFightOverlayLayer<E extends LivingEntity, AM extends HumanoidMesh>
        extends ModelRenderLayer<E, LivingEntityPatch<E>, HumanoidModel<E>, RenderLayer<E, HumanoidModel<E>>, AM> {
    private final ResourceLocation BLOOD_TEXTURE = new ResourceLocation(AnnoyingVillagers.MODID, "textures/entities/player_mob_blood.png");
    private final ResourceLocation DEFAULT_EYE = new ResourceLocation(AnnoyingVillagers.MODID, "textures/entities/herobrine_eyes/default/default.png");

    public PlayerMobEpicFightOverlayLayer(MeshProvider<AM> mesh) { super(mesh); }

    private ResourceLocation pickTexture(E e) {
        if (EntityType.getKey(e.getType()).equals(new ResourceLocation(AnnoyingVillagers.MODID, "herobrine_5"))) {
            String name = e.hasCustomName() ? e.getCustomName().getString() : e.getName().getString();
            return HerobrineEyesUtil.getHerobrineEyesTexture(name);
        } else if (EntityType.getKey(e.getType()).equals(new ResourceLocation(AnnoyingVillagers.MODID, "infected_player_mob"))) {
            return BLOOD_TEXTURE;
        }  else if (EntityType.getKey(e.getType()).equals(new ResourceLocation(AnnoyingVillagers.MODID, "herobrine_1")) ||
                EntityType.getKey(e.getType()).equals(new ResourceLocation(AnnoyingVillagers.MODID, "herobrine_2")) ||
                EntityType.getKey(e.getType()).equals(new ResourceLocation(AnnoyingVillagers.MODID, "herobrine_3")) ||
//                EntityType.getKey(e.getType()).equals(new ResourceLocation(AnnoyingVillagers.MODID, "herobrine_4")) ||
                EntityType.getKey(e.getType()).equals(new ResourceLocation(AnnoyingVillagers.MODID, "herobrine_6")) ||
                EntityType.getKey(e.getType()).equals(new ResourceLocation(AnnoyingVillagers.MODID, "herobrine_7")) ||
                EntityType.getKey(e.getType()).equals(new ResourceLocation(AnnoyingVillagers.MODID, "null")) ||
                EntityType.getKey(e.getType()).equals(new ResourceLocation(AnnoyingVillagers.MODID, "armored_herobrine")) ||
                EntityType.getKey(e.getType()).equals(new ResourceLocation(AnnoyingVillagers.MODID, "shadow_herobrine")) ||
                EntityType.getKey(e.getType()).equals(new ResourceLocation(AnnoyingVillagers.MODID, "glaive_herobrine")) ||
                EntityType.getKey(e.getType()).equals(new ResourceLocation(AnnoyingVillagers.MODID, "reaper_herobrine")) ||
                EntityType.getKey(e.getType()).equals(new ResourceLocation(AnnoyingVillagers.MODID, "swordsman_herobrine")) ||
                EntityType.getKey(e.getType()).equals(new ResourceLocation(AnnoyingVillagers.MODID, "sledgehammer_herobrine")) ||
                EntityType.getKey(e.getType()).equals(new ResourceLocation(AnnoyingVillagers.MODID, "aegis_herobrine"))) {
            return DEFAULT_EYE;
        }
        return null;
    }

    @Override
    protected void renderLayer(LivingEntityPatch<E> eLivingEntityPatch, E e, @Nullable RenderLayer<E, HumanoidModel<E>> eHumanoidModelRenderLayer, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, OpenMatrix4f[] openMatrix4fs, float v, float v1, float v2, float v3) {
        ResourceLocation tex = pickTexture(e);
        if (tex == null) return;
        if (tex == BLOOD_TEXTURE) {
            ((AnimatedMesh) this.mesh.get()).draw(
                    poseStack, multiBufferSource, RenderType.entityCutoutNoCull(tex),
                    i,
                    1f, 1f, 1f, 1f, OverlayTexture.NO_OVERLAY,
                    eLivingEntityPatch.getArmature(), openMatrix4fs
            );
        } else {
            ((AnimatedMesh) this.mesh.get()).draw(
                    poseStack, multiBufferSource, RenderType.eyes(tex),
                    i,
                    1f, 1f, 1f, 1f, OverlayTexture.NO_OVERLAY,
                    eLivingEntityPatch.getArmature(), openMatrix4fs
            );
        }
    }
}
