package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class FFireHitProcedure {

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent livingattackevent) {
        if (livingattackevent != null && livingattackevent.getEntity() != null) {
            execute(livingattackevent, livingattackevent.getEntity(), livingattackevent.getSource().getEntity());
        }

    }

    public static void execute(Entity entity, Entity entity1) {
        execute((Event) null, entity, entity1);
    }

    private static void execute(@Nullable Event event, Entity entity, Entity entity1) {
        if (entity != null && entity1 != null) {
            if (entity.getPersistentData().getString(entity1.getUUID().toString() + "_f_fire").equals(entity1.getUUID().toString())) {
                if (event != null && event.isCancelable()) {
                    event.setCanceled(true);
                }

                if (entity1 instanceof Player) {
                    Player player = (Player) entity1;

                    if (!player.level.isClientSide()) {
                        player.displayClientMessage(new TextComponent("\u4f60\u5bf9\u4ed6\u5173\u95ed\u4e86\u53cb\u4f24\uff0c\u4f60\u65e0\u6cd5\u5bf9\u4ed6\u653b\u51fb"), true);
                    }
                }
            }

        }
    }
}
