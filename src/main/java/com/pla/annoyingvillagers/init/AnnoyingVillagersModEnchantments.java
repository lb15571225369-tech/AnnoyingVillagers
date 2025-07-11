package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.pla.annoyingvillagers.enchantment.BreakArmorEnchantment;
import com.pla.annoyingvillagers.enchantment.EnergyEnchantment;
import com.pla.annoyingvillagers.enchantment.ErrorEnchantment;
import com.pla.annoyingvillagers.enchantment.ExperienceEnchantment;
import com.pla.annoyingvillagers.enchantment.FastshotEnchantment;
import com.pla.annoyingvillagers.enchantment.HeavyAttackEnchantment;
import com.pla.annoyingvillagers.enchantment.HolyBleessingEnchantment;
import com.pla.annoyingvillagers.enchantment.LearningEnchantment;
import com.pla.annoyingvillagers.enchantment.PossessionEnchantment;
import com.pla.annoyingvillagers.enchantment.QuickDrawEnchantment;
import com.pla.annoyingvillagers.enchantment.UnknownEnchantment;

public class AnnoyingVillagersModEnchantments {

    public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, AnnoyingVillagers.MODID);
    public static final RegistryObject<Enchantment> FASTSHOT = AnnoyingVillagersModEnchantments.REGISTRY.register("fastshot", () -> {
        return new FastshotEnchantment(new EquipmentSlot[0]);
    });
    public static final RegistryObject<Enchantment> BREAK_ARMOR = AnnoyingVillagersModEnchantments.REGISTRY.register("break_armor", () -> {
        return new BreakArmorEnchantment(new EquipmentSlot[0]);
    });
    public static final RegistryObject<Enchantment> ENERGY = AnnoyingVillagersModEnchantments.REGISTRY.register("energy", () -> {
        return new EnergyEnchantment(new EquipmentSlot[0]);
    });
    public static final RegistryObject<Enchantment> HEAVY_ATTACK = AnnoyingVillagersModEnchantments.REGISTRY.register("heavy_attack", () -> {
        return new HeavyAttackEnchantment(new EquipmentSlot[0]);
    });
    public static final RegistryObject<Enchantment> QUICK_DRAW = AnnoyingVillagersModEnchantments.REGISTRY.register("quick_draw", () -> {
        return new QuickDrawEnchantment(new EquipmentSlot[0]);
    });
    public static final RegistryObject<Enchantment> POSSESSION = AnnoyingVillagersModEnchantments.REGISTRY.register("possession", () -> {
        return new PossessionEnchantment(new EquipmentSlot[0]);
    });
    public static final RegistryObject<Enchantment> HOLY_BLEESSING = AnnoyingVillagersModEnchantments.REGISTRY.register("holy_bleessing", () -> {
        return new HolyBleessingEnchantment(new EquipmentSlot[0]);
    });
    public static final RegistryObject<Enchantment> EXPERIENCE = AnnoyingVillagersModEnchantments.REGISTRY.register("experience", () -> {
        return new ExperienceEnchantment(new EquipmentSlot[0]);
    });
    public static final RegistryObject<Enchantment> LEARNING = AnnoyingVillagersModEnchantments.REGISTRY.register("learning", () -> {
        return new LearningEnchantment(new EquipmentSlot[0]);
    });
    public static final RegistryObject<Enchantment> UNKNOWN = AnnoyingVillagersModEnchantments.REGISTRY.register("unknown", () -> {
        return new UnknownEnchantment(new EquipmentSlot[0]);
    });
    public static final RegistryObject<Enchantment> ERROR = AnnoyingVillagersModEnchantments.REGISTRY.register("error", () -> {
        return new ErrorEnchantment(new EquipmentSlot[0]);
    });
}
