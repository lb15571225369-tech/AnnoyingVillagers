package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import com.pla.annoyingvillagers.capabilities.AVCategories;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@EventBusSubscriber
public class KnifeaProcedure {

    @SubscribeEvent
    public static void onRightClickItem(RightClickItem rightclickitem) {
        if (rightclickitem.getHand() == rightclickitem.getEntity().getUsedItemHand()) {
            execute(rightclickitem, rightclickitem.getEntity());
        }
    }

    public static void execute(Entity entity) {
        execute((Event) null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity != null) {
            PlayerPatch<?> playerpatch = (PlayerPatch) EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);

            if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == AVCategories.KNIFE && !entity.level().isClientSide() && entity.getServer() != null) {
                LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                if (livingEntityPatch != null) {
                    livingEntityPatch.playAnimationSynchronized(AVAnimations.KNIFE_ATTACK, 0.0F);
                }
            }

        }
    }
}
