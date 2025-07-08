package com.pla.annoyingvillagers.procedures;

import java.util.HashMap;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.Entity;

public class ButtonLoginProcedure {

    public static void execute(Entity entity, HashMap hashmap) {
        if (entity != null && hashmap != null) {
            if (!entity.level.isClientSide() && entity.getServer() != null) {
                Commands commands = entity.getServer().getCommands();
                CommandSourceStack commandsourcestack = entity.createCommandSourceStack().withSuppressedOutput().withPermission(4);
                String s = hashmap.containsKey("text:id_check") ? ((EditBox) hashmap.get("text:id_check")).getValue() : "";

                commands.performCommand(commandsourcestack, "music login " + s);
            }

        }
    }
}
