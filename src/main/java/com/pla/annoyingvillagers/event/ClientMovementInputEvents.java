package com.pla.annoyingvillagers.client.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.network.BreakEmoteMessage;
import net.minecraft.client.player.Input;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientMovementInputEvents {
    private static boolean wasMovePressed;
    private static boolean wasJumpPressed;
    private static boolean wasSneakPressed;

    @SubscribeEvent
    public static void onMovementInput(MovementInputUpdateEvent event) {
        Input input = event.getInput();

        boolean movePressed = input.up || input.down || input.left || input.right;
        boolean jumpPressed = input.jumping;
        boolean sneakPressed = input.shiftKeyDown;

        boolean shouldBreak =
                (movePressed && !wasMovePressed)
                        || (jumpPressed && !wasJumpPressed)
                        || (sneakPressed && !wasSneakPressed);

        if (shouldBreak) {
            AnnoyingVillagers.PACKET_HANDLER.sendToServer(new BreakEmoteMessage());
        }

        wasMovePressed = movePressed;
        wasJumpPressed = jumpPressed;
        wasSneakPressed = sneakPressed;
    }
}