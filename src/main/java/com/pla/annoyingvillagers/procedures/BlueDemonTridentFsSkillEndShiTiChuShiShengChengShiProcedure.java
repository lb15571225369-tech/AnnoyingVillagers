package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BlueDemonTridentFsSkillEndShiTiChuShiShengChengShiProcedure {

    public static void execute(LevelAccessor levelaccessor, final Entity entity) {
        if (entity != null) {
            ItemStack itemstack;

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;

                itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "item replace entity @s weapon.mainhand with annoying_villagersbychentu:bluedemontrident");
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "item replace entity @s weapon.offhand with annoying_villagersbychentu:bluedemontrident");
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect give @s annoying_villagersbychentu:fulu 20000 0 true");
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/other/blue_demon_end_skill\" 0 1");
            }

            ((<undefinedtype>)(new Object() {
                private int ticks = 0;
                private float waitTicks;
                private LevelAccessor world;

                public void start(LevelAccessor levelaccessor1, int i) {
                    this.waitTicks = (float)i;
                    MinecraftForge.EVENT_BUS.register(this);
                    this.world = levelaccessor1;
                }

                @SubscribeEvent
                public void tick(ServerTickEvent servertickevent) {
                    if (servertickevent.phase == Phase.END) {
                        ++this.ticks;
                        if ((float)this.ticks >= this.waitTicks) {
                            this.run();
                        }
                    }

                }

                private void run() {
                    if (entity.isAlive()) {
                        Entity entity1;

                        if (Math.random() <= 0.37D) {
                            entity1 = entity;
                            if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "summon annoying_villagers:blue_demon");
                            }
                        } else {
                            entity1 = entity;
                            if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "summon annoying_villagers:blue_demon_2");
                            }
                        }

                        if (!entity.level.isClientSide()) {
                            entity.discard();
                        }
                    }

                    MinecraftForge.EVENT_BUS.unregister(this);
                }
            })).start(levelaccessor, 400);
        }
    }
}
