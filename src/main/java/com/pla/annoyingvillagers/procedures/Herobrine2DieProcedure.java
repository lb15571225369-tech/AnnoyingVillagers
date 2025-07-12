package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.entity.DarkOBFarEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class Herobrine2DieProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceEntity, Entity targetEntity) {
        if (sourceEntity == null || targetEntity == null) return;

        if (!sourceEntity.level.isClientSide() && sourceEntity.getServer() != null) {
            sourceEntity.getServer().getCommands().performCommand(
                    sourceEntity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                    "tag @a remove aim"
            );
        }

        if (sourceEntity.isVehicle() && sourceEntity.getType() == EntityType.PLAYER) {
            for (Entity passenger : new ArrayList<>(sourceEntity.getPassengers())) {
                if (isSpectatorGamemode(passenger)) {
                    passenger.getServer().getCommands().performCommand(
                            passenger.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                            "tag @s remove sp"
                    );

                    transferArmor(sourceEntity, passenger);

//                    if (passenger instanceof LivingEntity living) {
//                        living.removeEffect(AnnoyingVillagersModMobEffects.HEROBRINE_EFFECT.get());
//                    }

                    passenger.stopRiding();
                    if (passenger instanceof ServerPlayer sp) {
                        sp.setGameMode(GameType.SURVIVAL);
                    }
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            spawnProjectile(world, sourceEntity, 5.0F, i >= 6 ? randomFloat(0.1F, 2.0F) : 1.0F);
        }
        
        new DelayedTask(20) {
            @Override
            public void run() {
                dropLoot(world, x, y, z);
            }
        };

        if (Math.random() <= 0.5 && targetEntity instanceof LivingEntity living) {
            living.removeAllEffects();
            living.setHealth(4.0F);
            if (!living.level.isClientSide()) {
                living.addEffect(new MobEffectInstance(MobEffects.WITHER, 9999, 3, false, false));
            }
        }
    }

    private static void transferArmor(Entity from, Entity to) {
        if (!(from instanceof LivingEntity fromLiving)) return;

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
            ItemStack armor = fromLiving.getItemBySlot(slot);
            if (to instanceof Player player) {
                player.getInventory().armor.set(3 - slot.getIndex(), armor); // 0 = boots, 1 = legs, 2 = chest, 3 = head
                player.getInventory().setChanged();
            } else if (to instanceof LivingEntity living) {
                living.setItemSlot(slot, armor);
            }
        }
    }

    private static void spawnProjectile(LevelAccessor world, Entity owner, float damage, float speed) {
        if (!(world instanceof Level level) || level.isClientSide()) return;

        DarkOBFarEntity projectile = new DarkOBFarEntity(AnnoyingVillagersModEntities.DARK_OB_FAR.get(), level);
        projectile.setOwner(owner);
        projectile.setBaseDamage(damage);
        projectile.setKnockback(0);
        projectile.setSilent(true);

        projectile.setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
        projectile.shoot(owner.getLookAngle().x, owner.getLookAngle().y, owner.getLookAngle().z, speed, 1.0F);

        level.addFreshEntity(projectile);
    }

    private static void dropLoot(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof Level level) || level.isClientSide()) return;

        Item[] drops = new Item[]{
                Items.DIAMOND, Items.DIAMOND,
                Items.MUSIC_DISC_11, Items.IRON_INGOT,
                Items.WRITABLE_BOOK, Items.EMERALD, Items.EMERALD,
                Items.ENCHANTED_GOLDEN_APPLE, Items.NETHERITE_INGOT,
                Items.ENDER_PEARL, Items.ENCHANTED_GOLDEN_APPLE,
                Items.ENDER_EYE, Items.TNT, Items.TNT
        };

        for (Item item : drops) {
            ItemEntity entity = new ItemEntity(level, x, y, z, new ItemStack(item));
            entity.setPickUpDelay(10);
            level.addFreshEntity(entity);
        }
    }

    private static boolean isSpectatorGamemode(Entity entity) {
        if (entity instanceof ServerPlayer sp) {
            return sp.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
        } else if (entity instanceof Player player && entity.level.isClientSide()) {
            var info = Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId());
            return info != null && info.getGameMode() == GameType.SPECTATOR;
        }
        return false;
    }

    private static float randomFloat(float min, float max) {
        return (float) (Math.random() * (max - min) + min);
    }
}
