package com.pla.annoyingvillagers.procedures;

import java.util.Iterator;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.ItemHandlerHelper;

public class TongjiDangXiaoGuoJieShuShiProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (entity.isAlive()) {
                if (entity instanceof ServerPlayer) {
                    ServerPlayer serverplayer = (ServerPlayer) entity;
                    Advancement advancement = serverplayer.server.getAdvancements().getAdvancement(new ResourceLocation("annoyingvillagers:fankangjunxianfeng"));
                    AdvancementProgress advancementprogress = serverplayer.getAdvancements().getOrStartProgress(advancement);

                    if (!advancementprogress.isDone()) {
                        Iterator iterator = advancementprogress.getRemainingCriteria().iterator();

                        while (iterator.hasNext()) {
                            serverplayer.getAdvancements().award(advancement, (String) iterator.next());
                        }
                    }
                }

                Player player;
                ItemStack itemstack;

                if (entity instanceof Player) {
                    player = (Player) entity;
                    itemstack = new ItemStack(Items.DIAMOND);
                    itemstack.setCount(1);
                    ItemHandlerHelper.giveItemToPlayer(player, itemstack);
                }

                if (entity instanceof Player) {
                    player = (Player) entity;
                    itemstack = new ItemStack(Items.ENDER_PEARL);
                    itemstack.setCount(1);
                    ItemHandlerHelper.giveItemToPlayer(player, itemstack);
                }

                if (entity instanceof Player) {
                    player = (Player) entity;
                    itemstack = new ItemStack(Items.ENCHANTED_GOLDEN_APPLE);
                    itemstack.setCount(1);
                    ItemHandlerHelper.giveItemToPlayer(player, itemstack);
                }
            }

        }
    }
}
