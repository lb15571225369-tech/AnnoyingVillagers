package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class ClassicgoldenaItem extends ArmorItem {

    public ClassicgoldenaItem(EquipmentSlot equipmentslot, Properties properties) {
        super(new ArmorMaterial() {
            public int getDurabilityForSlot(EquipmentSlot equipmentslot1) {
                return (new int[]{13, 15, 16, 11})[equipmentslot1.getIndex()] * 27;
            }

            public int getDefenseForSlot(EquipmentSlot equipmentslot1) {
                return (new int[]{3, 5, 6, 3})[equipmentslot1.getIndex()];
            }

            public int getEnchantmentValue() {
                return 9;
            }

            public SoundEvent getEquipSound() {
                return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }

            public String getName() {
                return "classicgoldena";
            }

            public float getToughness() {
                return 0.7F;
            }

            public float getKnockbackResistance() {
                return 0.0F;
            }
        }, equipmentslot, properties);
    }

    public static class Boots extends ClassicgoldenaItem {

        public Boots() {
            super(EquipmentSlot.FEET, (new Properties()).tab((CreativeModeTab) null));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/gold__layer_1.png";
        }
    }

    public static class Leggings extends ClassicgoldenaItem {

        public Leggings() {
            super(EquipmentSlot.LEGS, (new Properties()).tab((CreativeModeTab) null));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/gold__layer_2.png";
        }
    }

    public static class Chestplate extends ClassicgoldenaItem {

        public Chestplate() {
            super(EquipmentSlot.CHEST, (new Properties()).tab((CreativeModeTab) null));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/gold__layer_1.png";
        }
    }

    public static class Helmet extends ClassicgoldenaItem {

        public Helmet() {
            super(EquipmentSlot.HEAD, (new Properties()).tab((CreativeModeTab) null));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/gold__layer_1.png";
        }
    }
}
