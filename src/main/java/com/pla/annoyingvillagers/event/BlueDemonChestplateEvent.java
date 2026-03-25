package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.item.BlueDemonChestplateItem;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class BlueDemonChestplateEvent {
    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if (event.getSlot() != EquipmentSlot.CHEST) {
            return;
        }

        ItemStack oldStack = event.getFrom();
        if (BlueDemonChestplateItem.isBlueDemonChestplate(oldStack)) {
            BlueDemonChestplateItem.stopBuff(oldStack);
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingHurtEvent event) {
        LivingEntity wearer = event.getEntity();
        if (!(wearer instanceof Player)) return;
        if (!wearer.isAlive()) {
            return;
        }

        ItemStack chest = wearer.getItemBySlot(EquipmentSlot.CHEST);
        if (!BlueDemonChestplateItem.isBlueDemonChestplate(chest)) {
            return;
        }

        float finalDamage = event.getAmount();
        if (finalDamage <= 0.0F) {
            return;
        }

        if (BlueDemonChestplateItem.isBuffActive(chest)) {
            Entity sourceEntity = event.getSource().getEntity();
            if (sourceEntity instanceof LivingEntity attacker && attacker != wearer) {
                float chance = new Random().nextFloat();
                if (chance <= 0.2F) {
                    attacker.addEffect(new MobEffectInstance(
                            AnnoyingVillagersModMobEffects.ELECTRIFY.get(),
                            20,
                            2
                    ));
                } else if (chance <= 0.6F) {
                    attacker.addEffect(new MobEffectInstance(
                            AnnoyingVillagersModMobEffects.ELECTRIFY.get(),
                            20,
                            1
                    ));
                }
            }
        } else {
            if (!BlueDemonChestplateItem.isFullyCharged(chest)) {
                int gainedCharge = Math.max(1, Mth.ceil(finalDamage));
                BlueDemonChestplateItem.addStoredCharge(chest, gainedCharge);
            }
        }
    }
}
