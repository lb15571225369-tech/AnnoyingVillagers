package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import net.corruptdog.cdm.skill.identity.Execute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class ExecuteProcedure {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRightClickEntity(EntityInteract entityinteract) {
        if (entityinteract.getHand() == entityinteract.getEntity().getUsedItemHand()) {
            Player player = entityinteract.getEntity();
            Level level = entityinteract.getEntity().level();

            if (player != null && level != null) {
                if (!level.isClientSide() && AnnoyingVillagersConfig.EXECUTION_PLAYER.get()) {
                    Execute.execute(player, level);
                }
            }
        }
    }
}
