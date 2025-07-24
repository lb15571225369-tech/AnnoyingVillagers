package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class EnderPearlKillProcedure {

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent projectileimpactevent) {
        if (projectileimpactevent != null && projectileimpactevent.getEntity() != null) {
            double d0 = projectileimpactevent.getEntity().getX();
            double d1 = projectileimpactevent.getEntity().getY();
            double d2 = projectileimpactevent.getEntity().getZ();
            double d3 = projectileimpactevent.getRayTraceResult().getLocation().x;
            double d4 = projectileimpactevent.getRayTraceResult().getLocation().y;
            double d5 = projectileimpactevent.getRayTraceResult().getLocation().z;
            double d6 = Math.floor(d3 + 0.01D * Math.signum(d3 - d0));
            double d7 = Math.floor(d4 + 0.01D * Math.signum(d4 - d1));
            double d8 = Math.floor(d5 + 0.01D * Math.signum(d5 - d2));

            execute(projectileimpactevent, projectileimpactevent.getEntity(), projectileimpactevent.getProjectile().getOwner());
        }

    }

    public static void execute(Entity entity, Entity entity1) {
        execute((Event) null, entity, entity1);
    }

    private static void execute(@Nullable Event event, Entity entity, Entity entity1) {
        if (entity != null && entity1 != null) {
            if (!entity1.isAlive()) {
                if (event != null && event.isCancelable()) {
                    event.setCanceled(true);
                }

                if (!entity.level().isClientSide()) {
                    entity.discard();
                }
            }

        }
    }
}
