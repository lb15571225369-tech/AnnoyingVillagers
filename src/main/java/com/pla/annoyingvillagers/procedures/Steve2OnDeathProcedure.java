package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class Steve2OnDeathProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            if (Math.random() >= 0.38D) {
                LevelAccessor levelaccessor1 = levelaccessor;

                if (levelaccessor1 instanceof Level) {
                    Level level = (Level)levelaccessor1;

                    if (!level.isClientSide()) {
                        level.explode((Entity)null, d0, d1 + 1.0D, d2, 3.0F, Level.ExplosionInteraction.NONE);
                    }
                }
                Entity entity1 = entity;

                if (!entity1.level().isClientSide() && entity1.getServer() != null) {
                    try {
                        entity1.getServer().getCommands().getDispatcher().execute(
                                "execute at @s run particle minecraft:totem_of_undying ^ ^1.5 ^ 0 0 0 1 1000",
                                entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (levelaccessor instanceof ServerLevel) {
                    ServerLevel serverlevel = (ServerLevel)levelaccessor;
                    AngrySteveEntity angrySteveEntity = new AngrySteveEntity((EntityType) AnnoyingVillagersModEntities.ANGRY_STEVE.get(), serverlevel);

                    angrySteveEntity.moveTo(d0, d1, d2, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                    InventoryUtils.transferInventory(((Steve2Entity) entity).getInventory(), angrySteveEntity.getInventory());
                    levelaccessor.addFreshEntity(angrySteveEntity);
                }
            } else {
                if (levelaccessor instanceof Level) {
                    Level level = (Level)levelaccessor;

                    if (!level.isClientSide()) {
                        level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevesayno")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevesayno")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<Steve> Noooooooooooooooooooooooooo!"), false);
                }

                new DelayedTask(20) {
                    public void run() {
                        LevelAccessor levelaccessor1 = levelaccessor;
                        Level level1;
                        ItemEntity itementity;

                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike) AnnoyingVillagersModItems.COMPRESSED_DIAMOND_HELMET.get()));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.COMPRESSED_DIAMOND_CHESTPLATE.get()));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND_CHESTPLATE));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLDEN_APPLE));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLDEN_APPLE));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLDEN_APPLE));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLDEN_APPLE));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLDEN_APPLE));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLDEN_APPLE));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLDEN_APPLE));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.DIAMOND_GREAT_SWORD.get()));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.ENCHANTED_WOODEN_DOOR.get()));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND_SWORD));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get()));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND_SWORD));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.WHITE_BED));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.CAKE));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                itementity = new ItemEntity(level1, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()));
                                itementity.setPickUpDelay(10);
                                level1.addFreshEntity(itementity);
                            }
                        }

                        if (Math.random() <= 0.2D) {
                            new DelayedTask(40) {
                                public void run() {
                                    Entity entity1 = entity;

                                    if (!entity1.level().isClientSide() && entity1.getServer() != null) {
                                        try {
                                            entity1.getServer().getCommands().getDispatcher().execute(
                                                    "tellraw @a[gamemode=survival] {\"text\":\"<Steve> What happened?\"}",
                                                    entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                        } catch (CommandSyntaxException e) {
                                        }
                                    }
                                    new DelayedTask(35) {
                                        public void run() {
                                            Entity entity2 = entity;

                                            if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                                try {
                                                    entity2.getServer().getCommands().getDispatcher().execute(
                                                            "tellraw @a[gamemode=survival] {\"text\":\"<Steve> What ?",
                                                            entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                } catch (CommandSyntaxException e) {
                                                }
                                            }
                                            new DelayedTask(60) {
                                                public void run() {
                                                    Entity entity3 = entity;

                                                    if (!entity3.level().isClientSide() && entity3.getServer() != null) {
                                                        try {
                                                            entity3.getServer().getCommands().getDispatcher().execute(
                                                                    "tellraw @a[gamemode=survival] {\"text\":\"<Steve> Who are you?\"}",
                                                                    entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                        } catch (CommandSyntaxException e) {
                                                        }
                                                    }
                                                    new DelayedTask(67) {
                                                        public void run() {
                                                            Entity entity4 = entity;

                                                            if (!entity4.level().isClientSide() && entity4.getServer() != null) {
                                                                try {
                                                                    entity4.getServer().getCommands().getDispatcher().execute(
                                                                            "tellraw @a[gamemode=survival] {\"text\":\"<Steve> You are so strong!\"}",
                                                                            entity4.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                } catch (CommandSyntaxException e) {
                                                                }
                                                            }
                                                            new DelayedTask(100) {
                                                                public void run() {
                                                                    if (levelaccessor.players().size() >= 5) {
                                                                        Entity entity5 = entity;

                                                                        if (!entity5.level().isClientSide() && entity5.getServer() != null) {
                                                                            try {
                                                                                entity5.getServer().getCommands().getDispatcher().execute(
                                                                                        "tellraw @a[gamemode=survival] {\"text\":\"<Steve> Please defeat Herobrine, and save my friend ...",
                                                                                        entity5.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                            } catch (CommandSyntaxException e) {
                                                                            }
                                                                        }
                                                                    }
                                                                    new DelayedTask(70) {
                                                                        public void run() {
                                                                            Entity entity6 = entity;

                                                                            if (!entity6.level().isClientSide() && entity6.getServer() != null) {
                                                                                try {
                                                                                    entity6.getServer().getCommands().getDispatcher().execute(
                                                                                            "tellraw @a[gamemode=survival] {\"text\":\"<Steve> You can have that gear, it's your loot after all.",
                                                                                            entity6.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                } catch (CommandSyntaxException e) {
                                                                                }
                                                                            }
                                                                            new DelayedTask(100) {
                                                                                public void run() {
                                                                                    Entity entity7 = entity;

                                                                                    if (!entity7.level().isClientSide() && entity7.getServer() != null) {
                                                                                        try {
                                                                                            entity7.getServer().getCommands().getDispatcher().execute(
                                                                                                    "tellraw @a[gamemode=survival] {\"text\":\"<Steve> I was just looking for a player to take my place. Looks like you're the one, because you're the only player who can defeat me",
                                                                                                    entity7.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                        } catch (
                                                                                                CommandSyntaxException e) {
                                                                                        }
                                                                                    }
                                                                                    new DelayedTask(60) {
                                                                                        public void run() {
                                                                                            Entity entity8 = entity;

                                                                                            if (!entity8.level().isClientSide() && entity8.getServer() != null) {
                                                                                                try {
                                                                                                    entity8.getServer().getCommands().getDispatcher().execute(
                                                                                                            "tellraw @a[gamemode=survival] {\"text\":\"<Steve> Alright then, good luck.",
                                                                                                            entity8.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                } catch (
                                                                                                        CommandSyntaxException e) {
                                                                                                }
                                                                                            }
                                                                                            new DelayedTask(50) {
                                                                                                public void run() {
                                                                                                    Entity entity9 = entity;

                                                                                                    if (!entity9.level().isClientSide() && entity9.getServer() != null) {
                                                                                                        try {
                                                                                                            entity9.getServer().getCommands().getDispatcher().execute(
                                                                                                                    "tellraw @a[gamemode=survival] {\"text\":\"<Steve> I might be back",
                                                                                                                    entity9.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                        } catch (
                                                                                                                CommandSyntaxException e) {
                                                                                                        }
                                                                                                    }
                                                                                                    new DelayedTask(65) {
                                                                                                        public void run() {
                                                                                                            Entity entity10 = entity;

                                                                                                            if (!entity10.level().isClientSide() && entity10.getServer() != null) {
                                                                                                                try {
                                                                                                                    entity10.getServer().getCommands().getDispatcher().execute(
                                                                                                                            "tellraw @a[gamemode=survival] {\"text\":\"<Steve> Goodbye\"}",
                                                                                                                            entity10.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                                } catch (
                                                                                                                        CommandSyntaxException e) {
                                                                                                                }
                                                                                                            }
                                                                                                            new DelayedTask(50) {
                                                                                                                public void run() {
                                                                                                                    Entity entity11;

                                                                                                                    if (levelaccessor.players().size() >= 4) {
                                                                                                                        entity11 = entity;
                                                                                                                        if (!entity11.level().isClientSide() && entity11.getServer() != null) {
                                                                                                                            try {
                                                                                                                                entity11.getServer().getCommands().getDispatcher().execute("tellraw @a[gamemode=survival] {\"text\":\"Steve has left the game\",\"color\":\"yellow\"}",
                                                                                                                                        entity11.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                                            } catch (
                                                                                                                                    CommandSyntaxException e) {
                                                                                                                            }
                                                                                                                        }
                                                                                                                    } else {
                                                                                                                        entity11 = entity;
                                                                                                                        if (!entity11.level().isClientSide() && entity11.getServer() != null) {
                                                                                                                            try {
                                                                                                                                entity11.getServer().getCommands().getDispatcher().execute("tellraw @a[gamemode=survival] {\"text\":\"Steve has left the game\",\"color\":\"yellow\"}",
                                                                                                                                        entity11.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                                                            } catch (
                                                                                                                                    CommandSyntaxException e) {
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            };
                                                                                                        }
                                                                                                    };
                                                                                                }
                                                                                            };
                                                                                        }
                                                                                    };
                                                                                }
                                                                            };
                                                                        }
                                                                    };
                                                                }
                                                            };
                                                        }
                                                    };
                                                }
                                            };
                                        }
                                    };
                                }
                            };
                        }
                    }
                };

                double posX = entity.getX();
                double posY = entity.getY();
                double posZ = entity.getZ();
                LevelAccessor levelAccessor = entity.level();
                if (levelAccessor instanceof ServerLevel && AnnoyingVillagersConfig.PHYSIC_MOD_COMPAT.get()) {
                    ServerLevel serverlevel = (ServerLevel)levelaccessor;
                    SteveDeadEntity steveDeadEntity = new SteveDeadEntity((EntityType) AnnoyingVillagersModEntities.STEVE_DEAD.get(), serverlevel);

                    steveDeadEntity.moveTo(posX, posY, posZ, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                    if (steveDeadEntity instanceof Mob) {
                        Mob mob = (Mob)steveDeadEntity;

                        mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(steveDeadEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                    }
                    entity.remove(Entity.RemovalReason.KILLED);
                    levelaccessor.addFreshEntity(steveDeadEntity);
                    steveDeadEntity.hurt(steveDeadEntity.damageSources().generic(), Float.MAX_VALUE);
                }
            }

        }
    }
}
