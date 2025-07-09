package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import tictim.paraglider.capabilities.Caps;
import tictim.paraglider.capabilities.PlayerMovement;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@EventBusSubscriber
public class CanUseItemProcedure {

    @SubscribeEvent
    public static void onRightClickItem(RightClickItem rightclickitem) {
        if (rightclickitem.getHand() == rightclickitem.getPlayer().getUsedItemHand()) {
            execute(rightclickitem, rightclickitem.getPlayer());
        }
    }

    public static void execute(Entity entity) {
        execute((Event) null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity != null) {
            if (entity instanceof Player) {
                PlayerMovement playermovement = (PlayerMovement) entity.getCapability(Caps.playerMovement, (Direction) null).resolve().orElseThrow();

                if (!playermovement.canAction()) {
                    if (event != null && event.isCancelable()) {
                        event.setCanceled(true);
                    }

                    LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                    DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();

                    if (!(dynamicanimation instanceof ActionAnimation) && !entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoyingvillagers:biped/combat/tried\" 0 1");
                    }
                }
            }

        }
    }
}
