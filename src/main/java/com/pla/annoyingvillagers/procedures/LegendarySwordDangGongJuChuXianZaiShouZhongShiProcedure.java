package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;

public class LegendarySwordDangGongJuChuXianZaiShouZhongShiProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                if (livingentity.isHolding((Item) AnnoyingVillagersModItems.LEGENDARY_SWORD.get()) && entity instanceof Player) {
                    Player player = (Player) entity;

                    player.causeFoodExhaustion(1.0F);
                }
            }

        }
    }
}
