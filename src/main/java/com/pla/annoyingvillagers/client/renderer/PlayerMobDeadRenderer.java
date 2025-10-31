package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.client.overlaylayer.PlayerMobBloodOverlayLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import se.gory_moon.player_mobs.client.render.PlayerMobRenderer;

public class PlayerMobDeadRenderer extends PlayerMobRenderer {
    public PlayerMobDeadRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.addLayer(new PlayerMobBloodOverlayLayer(this));
    }
}