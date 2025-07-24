package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.world.entity.Entity;

public class EcPlayerWhenPotionEffectBeginsToApplyProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute("indestructible @s play \"annoyingvillagers:biped/combat/execute\" 0 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                    
                }
            }

        }
    }
}
