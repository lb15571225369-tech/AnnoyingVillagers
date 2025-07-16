package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class CompressedDiamondArmorItem extends ArmorItem {

    public CompressedDiamondArmorItem(EquipmentSlot equipmentslot, Properties properties) {
        super(new ArmorMaterial() {
            public int getDurabilityForSlot(EquipmentSlot equipmentslot1) {
                return (new int[]{13, 15, 16, 11})[equipmentslot1.getIndex()] * 71;
            }

            public int getDefenseForSlot(EquipmentSlot equipmentslot1) {
                return (new int[]{5, 6, 8, 6})[equipmentslot1.getIndex()];
            }

            public int getEnchantmentValue() {
                return 10;
            }

            public SoundEvent getEquipSound() {
                return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_diamond"));
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[]{new ItemStack((ItemLike) AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()), new ItemStack(Items.DIAMOND_HELMET), new ItemStack(Items.DIAMOND)});
            }

            public String getName() {
                return "cchunzuantao";
            }

            public float getToughness() {
                return 1.8F;
            }

            public float getKnockbackResistance() {
                return 0.0F;
            }
        }, equipmentslot, properties);
    }

    public static class Boots extends CompressedDiamondArmorItem {

        public Boots() {
            super(EquipmentSlot.FEET, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/bd__layer_1.png";
        }
    }

    public static class Leggings extends CompressedDiamondArmorItem {

        public Leggings() {
            super(EquipmentSlot.LEGS, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/bd__layer_2.png";
        }
    }

    public static class Chestplate extends CompressedDiamondArmorItem {

        public Chestplate() {
            super(EquipmentSlot.CHEST, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/bd__layer_1.png";
        }
    }

    public static class Helmet extends CompressedDiamondArmorItem {

        public Helmet() {
            super(EquipmentSlot.HEAD, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/bd__layer_1.png";
        }
    }
}
