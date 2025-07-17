package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.pla.annoyingvillagers.enchantment.BreakArmorEnchantment;
import com.pla.annoyingvillagers.enchantment.FastshotEnchantment;

public class AnnoyingVillagersModEnchantments {

    public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, AnnoyingVillagers.MODID);
    public static final RegistryObject<Enchantment> FAST_SHOT = AnnoyingVillagersModEnchantments.REGISTRY.register("fast_shot", () -> {
        return new FastshotEnchantment(new EquipmentSlot[0]);
    });
    public static final RegistryObject<Enchantment> BREAK_ARMOR = AnnoyingVillagersModEnchantments.REGISTRY.register("break_armor", () -> {
        return new BreakArmorEnchantment(new EquipmentSlot[0]);
    });
}
