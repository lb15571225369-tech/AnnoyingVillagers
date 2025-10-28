package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.entity.Steve2Entity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.clazz.PathfinderMobInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.Random;

public class SteveOnHurtProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final PathfinderMobInventory entity, Entity attacker, double amount) {
        if (entity != null) {
            if (!entity.getPersistentData().getBoolean("kick_x")) {
                if (entity.getEnderPearlCooldown() == 0) {
                    if (Math.random() <= 0.2D) {
                        new DelayedTask(100) {
                            public void run() {
                                if (entity.isAlive()) {
                                    if (Math.random() <= 0.12D) {
                                        new DelayedTask(40) {
                                            public void run() {
                                                if (entity.isAlive()) {
                                                    entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.LEGENDARY_SWORD_MOB.get()));
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SMITE, 4);
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SWEEPING_EDGE, 4);
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SHARPNESS, 4);
                                                }
                                            }
                                        };
                                    } else if (Math.random() <= 0.14D) {
                                        new DelayedTask(40) {
                                            public void run() {
                                                if (entity.isAlive()) {
                                                    entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.CRAFTING_TABLE.get()));
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SMITE, 4);
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SWEEPING_EDGE, 4);
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SHARPNESS, 4);
                                                }
                                            }
                                        };
                                    } else if (Math.random() <= 0.1D) {
                                        new DelayedTask(40) {
                                            public void run() {
                                                if (entity.isAlive()) {
                                                    entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.DIAMOND_SWORD));
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SMITE, 4);
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SWEEPING_EDGE, 4);
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SHARPNESS, 4);
                                                }
                                            }
                                        };
                                    } else if (Math.random() <= 0.1D) {
                                        new DelayedTask(40) {
                                            public void run() {
                                                if (entity.isAlive()) {
                                                    entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.DIAMOND_GREAT_SWORD.get()));
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SMITE, 4);
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SWEEPING_EDGE, 4);
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SHARPNESS, 4);
                                                }

                                            }
                                        };
                                    } else if (Math.random() <= 0.1D) {
                                        new DelayedTask(40) {
                                            public void run() {
                                                if (entity.isAlive()) {
                                                    entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.ENCHANTED_WOODEN_DOOR.get()));
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SMITE, 4);
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SWEEPING_EDGE, 4);
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SHARPNESS, 4);
                                                }
                                            }
                                        };
                                    } else if (Math.random() <= 0.3D) {
                                        new DelayedTask(40) {
                                            public void run() {
                                                if (entity.isAlive()) {
                                                    entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.DIAMOND_SWORD));
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SMITE, 4);
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SWEEPING_EDGE, 4);
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SHARPNESS, 4);
                                                }
                                            }
                                        };
                                    } else {
                                        new DelayedTask(20) {
                                            public void run() {
                                                if (entity.isAlive()) {
                                                    entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get()));
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SMITE, 4);
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SWEEPING_EDGE, 4);
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SHARPNESS, 4);
                                                }
                                            }
                                        };
                                    }
                                }
                            }
                        };
                    }

                    if (Math.random() <= 0.1D) {
                        new DelayedTask(20) {
                            public void run() {
                                CombatBehaviour.throwEnderPearl(entity, (float) new Random().nextDouble(90.0D, 180.0D));

                                if (Math.random() <= 0.2D) {
                                    new DelayedTask(40) {
                                        public void run() {
                                            CombatBehaviour.throwEnderPearl(entity, 0.0F);

                                            if (levelaccessor instanceof Level level) {
                                                if (!level.isClientSide()) {
                                                    level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevechuanqi"))), SoundSource.NEUTRAL, 0.4F, 1.0F);
                                                } else {
                                                    level.playLocalSound(d0, d1, d2, (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevechuanqi"))), SoundSource.NEUTRAL, 0.4F, 1.0F, false);
                                                }
                                            }
                                        }
                                    };
                                }
                            }
                        };
                    } else if (Math.random() <= 0.2D && levelaccessor instanceof Level level) {
                        if (!level.isClientSide()) {
                            level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "steveattack"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "steveattack"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                        }
                    } else if (Math.random() <= 0.1D && levelaccessor instanceof Level level) {
                        if (!level.isClientSide()) {
                            level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevechuanqi"))), SoundSource.NEUTRAL, 0.4F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevechuanqi"))), SoundSource.NEUTRAL, 0.4F, 1.0F, false);
                        }
                    } else if (Math.random() <= 0.1D) {
                        if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                            levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity.getDisplayName().getString() + "> Why do we have to keep fighting ?"), false);
                        }

                        if (levelaccessor instanceof Level level) {
                            if (!level.isClientSide()) {
                                level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevesaywhyfighting"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                            } else {
                                level.playLocalSound(d0, d1, d2, (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevesaywhyfighting"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                            }
                        }
                    } else if (Math.random() <= 0.1D) {
                        if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                            levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity.getDisplayName().getString() + "> Why ?"), false);
                        }

                        if (levelaccessor instanceof Level level) {
                            if (!level.isClientSide()) {
                                level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevesaywhy"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                            } else {
                                level.playLocalSound(d0, d1, d2, (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevesaywhy"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                            }
                        }
                    }

                    if (Math.random() <= 0.2D) {
                        new DelayedTask(20) {
                            public void run() {
                                CombatBehaviour.throwEnderPearl(entity, 180.0F);

                                entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BOW));
                                if (!entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.POWER_ARROWS, 5);
                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.PUNCH_ARROWS, 5);
                                }
                                new DelayedTask(80) {
                                    @Override
                                    public void run() {
                                        CombatBehaviour.throwEnderPearl(entity, 0.0F);
                                        entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get()));
                                        entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.KNOCKBACK, 2);
                                        entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SMITE, 5);
                                        entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SHARPNESS, 5);
                                    }
                                };
                            }
                        };
                    }

                    if (entity.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
                        entity.setItemSlot(EquipmentSlot.HEAD, new ItemStack((ItemLike) AnnoyingVillagersModItems.COMPRESSED_DIAMOND_HELMET.get()));
                        if (levelaccessor instanceof Level level) {
                            if (!level.isClientSide()) {
                                level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_diamond"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                            } else {
                                level.playLocalSound(d0, d1, d2, (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_diamond"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                            }
                        }
                        new DelayedTask(10) {
                            @Override
                            public void run() {
                                if (entity.isAlive()) {
                                    entity.setItemSlot(EquipmentSlot.CHEST, new ItemStack((ItemLike) AnnoyingVillagersModItems.COMPRESSED_DIAMOND_CHESTPLATE.get()));
                                    if (levelaccessor instanceof Level level) {
                                        if (!level.isClientSide()) {
                                            level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_diamond"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                        } else {
                                            level.playLocalSound(d0, d1, d2, (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_diamond"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                        }
                                    }
                                }
                            }
                        };
                    }

                    if (Math.random() <= 0.5D) {
                        if (!entity.level().isClientSide()) {
                            entity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 1, 1, false, false));
                        }
                    }

                    if (Math.random() <= 0.37D) {
                        if (!entity.level().isClientSide()) {
                            entity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.ESCAPE.get(), 1, 1, false, false));
                        }
                    }
                    new DelayedTask(80) {
                        @Override
                        public void run() {
                            if (entity.isAlive()) {
                                entity.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.ENDER_PEARL));
                            }
                        }
                    };

                    if (entity instanceof Steve2Entity && attacker != null && attacker.isAlive()) {
                        if (Math.random() <= 0.2D && !entity.getPersistentData().getBoolean("steve_l_g_h_a")) {
                            entity.getPersistentData().putBoolean("steve_l_g_h_a", true);
                            new DelayedTask(2) {
                                @Override
                                public void run() {
                                    if (entity.isAlive()) {
                                        entity.setDeltaMovement(new Vec3(0.0D, 1.3D, 0.0D));
                                    }
                                }
                            };
                            entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.HEAVY_ATTACK_LEGENDARY_SWORD_MOB.get()));

                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                try {
                                    entity.getServer().getCommands().getDispatcher().execute(
                                            "playsound epicfight:sfx.entity_move neutral @p",
                                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {
                                }
                            }

                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                try {
                                    entity.getServer().getCommands().getDispatcher().execute(
                                            "indestructible @s play \"epicfight:biped/skill/demolition_leap\" 0 1",
                                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {
                                }
                            }

                            if (levelaccessor instanceof Level level) {
                                if (!level.isClientSide()) {
                                    level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "steveattack"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                } else {
                                    level.playLocalSound(d0, d1, d2, (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "steveattack"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                }
                            }
                            new DelayedTask(8) {
                                public void run() {
                                    if (entity.isAlive()) {
                                        entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.AXE_ATTACK_LEGENDARY_SWORD_MOB_AWAKENED.get()));
                                    }

                                    if (levelaccessor instanceof Level level) {
                                        if (!level.isClientSide()) {
                                            level.playSound((Player)null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "l_g_w_u"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                        } else {
                                            level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "l_g_w_u"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                        }
                                    }

                                    if (levelaccessor instanceof Level level) {
                                        if (!level.isClientSide()) {
                                            level.playSound((Player)null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "zhanshenzhirenjuexing"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                        } else {
                                            level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "zhanshenzhirenjuexing"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                        }
                                    }

                                    if (levelaccessor instanceof ServerLevel serverLevel) {
                                        serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, entity.getX(), entity.getZ(), entity.getY(), 15, 0.0D, 0.0D, 0.0D, 0.2D);
                                    }

                                    entity.setDeltaMovement(new Vec3(attacker.getX(), attacker.getY(), attacker.getZ()));
                                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                                        try {
                                            entity.getServer().getCommands().getDispatcher().execute(
                                                    "playsound annoyingvillagers:heavy_attack_start neutral @p",
                                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                        } catch (CommandSyntaxException e) {
                                        }
                                    }

                                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                                        try {
                                            entity.getServer().getCommands().getDispatcher().execute(
                                                    "execute as @s at @s anchored eyes run particle minecraft:totem_of_undying ~ ~ ~ 0 0 0 0.5 100",
                                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                        } catch (CommandSyntaxException e) {
                                        }
                                    }

                                    if (!entity.level().isClientSide()) {
                                        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 120, 2, false, false));
                                    }
                                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                                        try {
                                            entity.getServer().getCommands().getDispatcher().execute(
                                                    "indestructible @s play \"annoyingvillagers:biped/combat/legendary_sword_heavy_attack\" 0 1",
                                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                        } catch (CommandSyntaxException e) {
                                        }
                                    }
                                    new DelayedTask(25) {
                                        public void run() {
                                            if (entity.isAlive()) {
                                                entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.HEAVY_ATTACK_LEGENDARY_SWORD_MOB.get()));
                                                new DelayedTask(20) {
                                                    public void run() {
                                                        if (entity.isAlive()) {
                                                            entity.getPersistentData().putBoolean("steve_l_g_h_a", false);
                                                            entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get()));
                                                            if (!entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                                                                entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SHARPNESS, 5);
                                                            }
                                                        }
                                                    }
                                                };
                                            }
                                        }
                                    };
                                }
                            };
                        }
                    }

                    entity.setEnderPearlCooldown();
                }

                if (entity.getGapCooldown() == 0 && entity.getHealth() <= ((float) 2/3 * entity.getMaxHealth())) {
                    CombatBehaviour.eatingGoldenApple(entity, levelaccessor, amount);
                    entity.setGapCooldown();
                }
            }
        }
    }
}

