package com.pla.annoyingvillagers.procedures;

import java.util.Comparator;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class GreenVillagerGeneralOnAttackingEntityProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            float f;

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;

                f = livingentity.getHealth();
            } else {
                f = -1.0F;
            }

            if (f <= 7.0F) {
                LivingEntity livingentity1;

                if (entity instanceof Mob) {
                    Mob mob = (Mob)entity;

                    livingentity1 = mob.getTarget();
                } else {
                    livingentity1 = null;
                }

                LivingEntity livingentity2 = livingentity1;
                ItemStack itemstack;

                if (livingentity2 instanceof LivingEntity) {
                    LivingEntity livingentity3 = (LivingEntity)livingentity2;

                    itemstack = livingentity3.getMainHandItem();
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                Level level;

                if (itemstack.getItem() == Blocks.OBSIDIAN.asItem()) {
                    if (!entity.level.isClientSide()) {
                        entity.discard();
                    }

                    if (levelaccessor instanceof Level) {
                        level = (Level)levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "item.armor.equip_leather")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "item.armor.equip_leather")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                        }
                    }

                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        try {
                            entity.getServer().getCommands().getDispatcher().execute("summon annoying_villagersbychentu:zi_cun_qi_fu_lu ^ ^ ^ {VillagerData:{level:5,profession:\"minecraft:weaponsmith\"}}", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                            
                        }
                    }

                    Mob mob1;

                    if (entity instanceof Mob) {
                        mob1 = (Mob)entity;
                        livingentity1 = mob1.getTarget();
                    } else {
                        livingentity1 = null;
                    }

                    LivingEntity livingentity4 = livingentity1;
                    Player player;

                    if (livingentity4 instanceof Player) {
                        player = (Player)livingentity4;
                        ItemStack itemstack1 = new ItemStack(Blocks.OBSIDIAN);

                        player.getInventory().clearOrCountMatchingItems((itemstack2) -> {
                            return itemstack1.getItem() == itemstack2.getItem();
                        }, 1, player.inventoryMenu.getCraftSlots());
                    }

                    if (entity instanceof Mob) {
                        mob1 = (Mob)entity;
                        livingentity1 = mob1.getTarget();
                    } else {
                        livingentity1 = null;
                    }

                    livingentity4 = livingentity1;
                    if (livingentity4 instanceof Player) {
                        player = (Player)livingentity4;
                        if (!player.level.isClientSide()) {
                            player.displayClientMessage(Component.literal("Target captured"), true);
                        }
                    }
                } else {
                    Entity entity1 = levelaccessor.getEntitiesOfClass(Player.class,
                                    AABB.ofSize(new Vec3(d0, d1, d2), 5.0D, 5.0D, 5.0D),
                                    player -> true
                            ).stream()
                            .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(d0, d1, d2)))
                            .findFirst()
                            .orElse(null);

                    if (entity1 instanceof LivingEntity) {
                        LivingEntity livingentity5 = (LivingEntity)entity1;

                        itemstack = livingentity5.getMainHandItem();
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    if (itemstack.getItem() == Blocks.OBSIDIAN.asItem()) {
                        if (!entity.level.isClientSide()) {
                            entity.discard();
                        }

                        if (levelaccessor instanceof Level) {
                            level = (Level)levelaccessor;
                            if (!level.isClientSide()) {
                                level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "item.armor.equip_leather")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                            } else {
                                level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "item.armor.equip_leather")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                            }
                        }

                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                            try {
                                entity.getServer().getCommands().getDispatcher().execute("summon annoying_villagersbychentu:zi_cun_qi_fu_lu ^ ^ ^ {VillagerData:{level:3,profession:\"minecraft:weaponsmith\"}}", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {
                                
                            }
                        }

                        Entity entity2 = levelaccessor.getEntitiesOfClass(Player.class,
                                        AABB.ofSize(new Vec3(d0, d1, d2), 5.0D, 5.0D, 5.0D),
                                        player -> true
                                ).stream()
                                .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(d0, d1, d2)))
                                .findFirst()
                                .orElse(null);

                        Player player1;

                        if (entity2 instanceof Player) {
                            player1 = (Player)entity2;
                            ItemStack itemstack2 = new ItemStack(Blocks.OBSIDIAN);

                            player1.getInventory().clearOrCountMatchingItems((itemstack3) -> {
                                return itemstack2.getItem() == itemstack3.getItem();
                            }, 1, player1.inventoryMenu.getCraftSlots());
                        }

                        entity2 = levelaccessor.getEntitiesOfClass(Player.class,
                                        AABB.ofSize(new Vec3(d0, d1, d2), 5.0D, 5.0D, 5.0D),
                                        player -> true
                                ).stream()
                                .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(d0, d1, d2)))
                                .findFirst()
                                .orElse(null);

                        if (entity2 instanceof Player) {
                            player1 = (Player)entity2;
                            if (!player1.level.isClientSide()) {
                                player1.displayClientMessage(Component.literal("Target captured"), true);
                            }
                        }
                    }
                }
            }

        }
    }
}

