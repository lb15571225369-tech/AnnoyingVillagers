package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

import java.util.*;

public class HerobrineOnDeathProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, Entity source) {
        if (source == null) return;

        if (!source.level().isClientSide() && source.getServer() != null) {
            try {
                source.getServer().getCommands().getDispatcher().execute(
                        "tag @a remove aim",
                        source.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                );
            } catch (CommandSyntaxException e) {
                
            }
        }

        // Armor transfer from source to passengers
        if (source.isVehicle() && source.getType().toString().equals("minecraft:player")) {
            for (Entity passenger : new ArrayList<>(source.getPassengers())) {
                if (isSpectatorGamemode(passenger)) {
                    if (!passenger.level().isClientSide() && passenger.getServer() != null) {
                        try {
                            passenger.getServer().getCommands().getDispatcher().execute(
                                    "tag @s remove sp",
                                    passenger.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                            );
                        } catch (CommandSyntaxException e) {
                            
                        }
                    }

                    transferArmor(source, passenger);
                    passenger.stopRiding();
                    if (passenger instanceof ServerPlayer sp) {
                        sp.setGameMode(GameType.SURVIVAL);
                    }
                }
            }
        }

        // Drop loot after 1s
        new DelayedTask(20) {
            @Override
            public void run() {
                dropLoot(world, x, y, z);
            }
        };
    }

    private static void transferArmor(Entity from, Entity to) {
        if (!(from instanceof LivingEntity source)) return;

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
            ItemStack armor = source.getItemBySlot(slot);

            if (to instanceof Player player) {
                player.getInventory().armor.set(3 - slot.getIndex(), armor); // 0=boots, 1=legs, 2=chest, 3=head
                player.getInventory().setChanged();
            } else if (to instanceof LivingEntity living) {
                living.setItemSlot(slot, armor);
            }
        }
    }

    private static void dropLoot(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof Level level) || level.isClientSide()) return;

        Item[] items = new Item[] {
                Items.DIAMOND, Items.DIAMOND, Items.MUSIC_DISC_11, Items.IRON_INGOT,
                Items.WRITABLE_BOOK, Items.EMERALD, Items.EMERALD, Items.ENCHANTED_GOLDEN_APPLE,
                Items.NETHERITE_INGOT, Items.ENDER_PEARL, Items.ENCHANTED_GOLDEN_APPLE,
                Items.ENDER_EYE, Items.TNT, Items.TNT
        };

        for (Item item : items) {
            ItemEntity drop = new ItemEntity(level, x, y, z, new ItemStack(item));
            drop.setPickUpDelay(10);
            level.addFreshEntity(drop);
        }
    }

    private static boolean isSpectatorGamemode(Entity entity) {
        if (entity instanceof ServerPlayer sp) {
            return sp.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
        } else if (entity instanceof Player player && entity.level().isClientSide()) {
            var info = Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId());
            return info != null && info.getGameMode() == GameType.SPECTATOR;
        }
        return false;
    }
}
