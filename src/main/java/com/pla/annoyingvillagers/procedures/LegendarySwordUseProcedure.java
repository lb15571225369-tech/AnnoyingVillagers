package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class LegendarySwordUseProcedure {

    public static void execute(LevelAccessor levelaccessor, final Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (entity.isShiftKeyDown()) {
                Player player;

                if (itemstack.getOrCreateTag().getString("l_g_ower").equals(entity.getUUID().toString())) {
                    CompoundTag compoundtag;

                    if (entity.isSprinting()) {
                        if (itemstack.getOrCreateTag().getDouble("power") >= 25.0D) {
                            if (entity instanceof Player) {
                                player = (Player)entity;
                                player.getCooldowns().addCooldown(itemstack.getItem(), 250);
                            }

                            if (levelaccessor instanceof Level) {
                                Level level = (Level)levelaccessor;

                                if (!level.isClientSide()) {
                                    level.playSound((Player)null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":heavy_attack_legendary_sword")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                } else {
                                    level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":heavy_attack_legendary_sword")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                }
                            }

                            itemstack.getOrCreateTag().putDouble("power", itemstack.getOrCreateTag().getDouble("power") - 25.0D);
                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoyingvillagers:biped/combat/legendary_sword_wake_up_attack\" 0 1");
                            }

                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute at @s run particle annoyingvillagers:blue_spark ~ ~1 ~ 0 0 0 0.1 500");
                            }

                            entity.setDeltaMovement(new Vec3(0.0D, 0.2D, 0.0D));
                        } else if (entity instanceof Player) {
                            player = (Player)entity;
                            if (!player.level.isClientSide()) {
                                compoundtag = itemstack.getOrCreateTag();
                                player.displayClientMessage(new TextComponent("\u80fd\u91cf\u4e0d\u8db3\uff0c\u76ee\u524d\u5145\u80fd" + compoundtag.getDouble("power") + "/25"), true);
                            }
                        }
                    } else if (itemstack.getOrCreateTag().getDouble("power") >= 20.0D) {
                        if (entity instanceof Player) {
                            player = (Player)entity;
                            player.getCooldowns().addCooldown(itemstack.getItem(), 250);
                        }

                        entity.setDeltaMovement(new Vec3(0.0D, 1.3D, 0.0D));
                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                            entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "playsound epicfight:sfx.entity_move neutral @p");
                        }

                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                            entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"epicfight:biped/skill/demolition_leap\" 0 1");
                        }

                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                            entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "particle epicfight:air_burst ~ ~1.5 ~ 0 0 0 6 1");
                        }

                        itemstack.getOrCreateTag().putDouble("power", itemstack.getOrCreateTag().getDouble("power") - 20.0D);
                        new DelayedTask(8) {
                            @Override
                            public void run() {
                                Entity entity1 = entity;

                                if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                    entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "playsound annoyingvillagers:heavy_attack_start neutral @a ~ ~ ~");
                                }

                                LevelAccessor levelaccessor1 = levelaccessor;
                                Level level1;

                                if (levelaccessor1 instanceof Level) {
                                    level1 = (Level)levelaccessor1;
                                    if (!level1.isClientSide()) {
                                        level1.playSound((Player)null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":heavy_attack_legendary_sword")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                    } else {
                                        level1.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":heavy_attack_legendary_sword")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                    }
                                }

                                levelaccessor1 = levelaccessor;
                                if (levelaccessor1 instanceof Level) {
                                    level1 = (Level)levelaccessor1;
                                    if (!level1.isClientSide()) {
                                        level1.playSound((Player)null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":heavy_attack_legendary_sword_2")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                    } else {
                                        level1.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":heavy_attack_legendary_sword_2")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                    }
                                }

                                levelaccessor1 = levelaccessor;
                                if (levelaccessor1 instanceof ServerLevel) {
                                    ServerLevel serverlevel = (ServerLevel)levelaccessor1;

                                    serverlevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, entity.getX(), entity.getY(), entity.getZ(), 15, 0.0D, 0.0D, 0.0D, 0.2D);
                                }

                                entity1 = entity;
                                if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                    entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "execute as @s at @s anchored eyes run particle minecraft:totem_of_undying ~ ~ ~ 0 0 0 0.5 100");
                                }

                                entity1 = entity;
                                if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                    entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoyingvillagers:biped/combat/legendary_sword_heavy_attack\" 0 1");
                                }
                            }
                        };
                    } else if (entity instanceof Player) {
                        player = (Player)entity;
                        if (!player.level.isClientSide()) {
                            compoundtag = itemstack.getOrCreateTag();
                            player.displayClientMessage(new TextComponent("\u80fd\u91cf\u4e0d\u8db3\uff0c\u76ee\u524d\u5145\u80fd" + compoundtag.getDouble("power") + "/20"), true);
                        }
                    }
                } else if (entity instanceof Player) {
                    player = (Player)entity;
                    if (!player.level.isClientSide()) {
                        player.displayClientMessage(new TextComponent("\u4f60\u4e0d\u662f\u6b64\u6b66\u5668\u7684\u4e3b\u4eba\uff0c\u65e0\u6cd5\u64cd\u63a7\u6b64\u6280\u80fd"), true);
                    }
                }
            }

        }
    }
}
