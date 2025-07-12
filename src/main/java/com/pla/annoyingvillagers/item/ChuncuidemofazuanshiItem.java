package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class ChuncuidemofazuanshiItem extends ArmorItem {

    public ChuncuidemofazuanshiItem(EquipmentSlot equipmentslot, Properties properties) {
        super(new ArmorMaterial() {
            public int getDurabilityForSlot(EquipmentSlot equipmentslot1) {
                return (new int[]{13, 15, 16, 11})[equipmentslot1.getIndex()] * 59;
            }

            public int getDefenseForSlot(EquipmentSlot equipmentslot1) {
                return (new int[]{6, 7, 9, 7})[equipmentslot1.getIndex()];
            }

            public int getEnchantmentValue() {
                return 10;
            }

            public SoundEvent getEquipSound() {
                return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_diamond"));
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[]{new ItemStack(Items.DIAMOND), new ItemStack((ItemLike) AnnoyingVillagersModItems.ENCHANED_DIAMOND.get())});
            }

            public String getName() {
                return "chuncuidemofazuanshi";
            }

            public float getToughness() {
                return 2.2F;
            }

            public float getKnockbackResistance() {
                return 0.0F;
            }
        }, equipmentslot, properties);
    }

    public static class Boots extends ChuncuidemofazuanshiItem {

        public Boots() {
            super(EquipmentSlot.FEET, (new Properties()).tab(CreativeModeTab.TAB_COMBAT).fireResistant());
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/diamond__layer_1.png";
        }
    }

    public static class Leggings extends ChuncuidemofazuanshiItem {

        public Leggings() {
            super(EquipmentSlot.LEGS, (new Properties()).tab(CreativeModeTab.TAB_COMBAT).fireResistant());
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/diamond__layer_2.png";
        }
    }

    public static class Chestplate extends ChuncuidemofazuanshiItem {

        public Chestplate() {
            super(EquipmentSlot.CHEST, (new Properties()).tab(CreativeModeTab.TAB_COMBAT).fireResistant());
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/diamond__layer_1.png";
        }
    }

    public static class Helmet extends ChuncuidemofazuanshiItem {

        public Helmet() {
            super(EquipmentSlot.HEAD, (new Properties()).tab(CreativeModeTab.TAB_COMBAT).fireResistant());
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/diamond__layer_1.png";
        }
    }
}
