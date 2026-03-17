package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.item.BlueDemonChestplateItem;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class BlueDemonChestplateEvent {
    @SubscribeEvent
    public static void onLivingDamage(LivingHurtEvent event) {
        LivingEntity wearer = event.getEntity();
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

        int gainedCharge = Math.max(1, Mth.ceil(finalDamage));
        BlueDemonChestplateItem.addStoredCharge(chest, gainedCharge);

        if (!BlueDemonChestplateItem.isFullyCharged(chest)) {
            return;
        }

        Entity sourceEntity = event.getSource().getEntity();
        if (sourceEntity instanceof LivingEntity attacker && attacker != wearer) {
            attacker.addEffect(new MobEffectInstance(
                    AnnoyingVillagersModMobEffects.ELECTRIFY.get(),
                    20,
                    2
            ));
            BlueDemonChestplateItem.setStoredCharge(chest, 0);
        }
    }
}
