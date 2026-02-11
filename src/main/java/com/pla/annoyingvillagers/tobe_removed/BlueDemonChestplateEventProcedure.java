package com.pla.annoyingvillagers.tobe_removed;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

import static com.pla.annoyingvillagers.util.ArmorUtil.dropArmorSlot;

public class BlueDemonChestplateEventProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (!(entity instanceof LivingEntity living)) return;

            dropArmorSlot(living, EquipmentSlot.FEET, "Blue Demon Chestplate");
            dropArmorSlot(living, EquipmentSlot.LEGS, "Blue Demon Chestplate");
            dropArmorSlot(living, EquipmentSlot.HEAD, "Blue Demon Chestplate");

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute("effect give @s annoyingvillagers:electify 1 0 true", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                    
                }
            }

        }
    }
}
