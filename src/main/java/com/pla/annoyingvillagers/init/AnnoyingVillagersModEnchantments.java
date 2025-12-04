package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.pla.annoyingvillagers.enchantment.BreakArmorEnchantment;

public class AnnoyingVillagersModEnchantments {

    public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, AnnoyingVillagers.MODID);
    public static final RegistryObject<Enchantment> BREAK_ARMOR = AnnoyingVillagersModEnchantments.REGISTRY.register("break_armor", () -> new BreakArmorEnchantment());
}
