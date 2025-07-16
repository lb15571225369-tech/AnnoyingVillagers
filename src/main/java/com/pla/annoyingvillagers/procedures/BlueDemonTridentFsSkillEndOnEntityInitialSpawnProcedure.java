package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;
import com.pla.annoyingvillagers.util.DelayedTask;

public class BlueDemonTridentFsSkillEndOnEntityInitialSpawnProcedure {

    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) return;

        // Enchant chestplate with Protection V
        if (entity instanceof LivingEntity living) {
            ItemStack chest = living.getItemBySlot(EquipmentSlot.CHEST);
            chest.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
        }

        // Run commands
        if (!entity.level.isClientSide() && entity.getServer() != null) {
            String[] commands = new String[] {
                    "item replace entity @s weapon.mainhand with annoyingvillagers:bluedemontrident",
                    "item replace entity @s weapon.offhand with annoyingvillagers:bluedemontrident",
                    "effect give @s annoyingvillagers:fulu 20000 0 true",
                    "indestructible @s play \"annoyingvillagers:biped/other/blue_demon_end_skill\" 0 1"
            };

            for (String cmd : commands) {
                entity.getServer().getCommands().performCommand(
                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                        cmd
                );
            }
        }

        // Schedule transformation after 400 ticks
        new DelayedTask(100) {
            @Override
            public void run() {
                if (!entity.isAlive()) return;

                String summonCommand = "summon annoyingvillagers:blue_demon_2";

                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().performCommand(
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                            summonCommand
                    );
                }

                if (!entity.level.isClientSide()) {
                    entity.discard();
                }
            }
        };
    }
}
