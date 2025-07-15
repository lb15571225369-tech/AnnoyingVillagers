package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.procedures.KkxtsProcedure;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class EmeraldArmorItem extends ArmorItem {

    public EmeraldArmorItem(EquipmentSlot equipmentslot, Properties properties) {
        super(new ArmorMaterial() {
            public int getDurabilityForSlot(EquipmentSlot equipmentslot1) {
                return (new int[]{13, 15, 16, 11})[equipmentslot1.getIndex()] * 48;
            }

            public int getDefenseForSlot(EquipmentSlot equipmentslot1) {
                return (new int[]{4, 5, 8, 5})[equipmentslot1.getIndex()];
            }

            public int getEnchantmentValue() {
                return 10;
            }

            public SoundEvent getEquipSound() {
                return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_diamond"));
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[]{new ItemStack(Items.EMERALD)});
            }

            public String getName() {
                return "llbstz";
            }

            public float getToughness() {
                return 2.0F;
            }

            public float getKnockbackResistance() {
                return 0.2F;
            }
        }, equipmentslot, properties);
    }

    public static class Boots extends EmeraldArmorItem {

        public Boots() {
            super(EquipmentSlot.FEET, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/ik__e_layer_1.png";
        }

        public void onArmorTick(ItemStack itemstack, Level level, Player player) {
            KkxtsProcedure.execute(player);
        }
    }

    public static class Leggings extends EmeraldArmorItem {

        public Leggings() {
            super(EquipmentSlot.LEGS, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/ik__e_layer_2.png";
        }

        public void onArmorTick(ItemStack itemstack, Level level, Player player) {
            KkxtsProcedure.execute(player);
        }
    }

    public static class Chestplate extends EmeraldArmorItem {

        public Chestplate() {
            super(EquipmentSlot.CHEST, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/ik__e_layer_1.png";
        }

        public void onArmorTick(ItemStack itemstack, Level level, Player player) {
            KkxtsProcedure.execute(player);
        }
    }

    public static class Helmet extends EmeraldArmorItem {

        public Helmet() {
            super(EquipmentSlot.HEAD, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB).fireResistant());
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/ik__e_layer_1.png";
        }

        public void onArmorTick(ItemStack itemstack, Level level, Player player) {
            KkxtsProcedure.execute(player);
        }
    }
}
