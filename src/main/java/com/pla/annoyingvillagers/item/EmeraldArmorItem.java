package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.procedures.EmeraldArmorJumpBootProcedure;
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

    public EmeraldArmorItem(ArmorItem.Type type, Properties properties) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(ArmorItem.Type type) {
                return switch (type) {
                    case BOOTS      -> 13 * 48;  // 624
                    case LEGGINGS   -> 15 * 48;  // 720
                    case CHESTPLATE -> 16 * 48;  // 768
                    case HELMET     -> 11 * 48;  // 528
                };
            }

            @Override
            public int getDefenseForType(ArmorItem.Type type) {
                return switch (type) {
                    case BOOTS      -> 4;
                    case LEGGINGS   -> 5;
                    case CHESTPLATE -> 8;
                    case HELMET     -> 5;
                };
            }

            public int getEnchantmentValue() {
                return 10;
            }

            public SoundEvent getEquipSound() {
                return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "item.armor.equip_diamond"));
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[]{new ItemStack(Items.EMERALD)});
            }

            public String getName() {
                return "emerald_armor";
            }

            public float getToughness() {
                return 2.0F;
            }

            public float getKnockbackResistance() {
                return 0.2F;
            }
        }, type, properties);
    }

    public static class Boots extends EmeraldArmorItem {

        public Boots() {
            super(Type.BOOTS, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/emerald_armor_layer_1.png";
        }

        @Override
        public void onArmorTick(ItemStack itemstack, Level level, Player player) {
            EmeraldArmorJumpBootProcedure.execute(player);
        }
    }

    public static class Leggings extends EmeraldArmorItem {

        public Leggings() {
            super(Type.LEGGINGS, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/emerald_armor_layer_2.png";
        }

        @Override
        public void onArmorTick(ItemStack itemstack, Level level, Player player) {
            EmeraldArmorJumpBootProcedure.execute(player);
        }
    }

    public static class Chestplate extends EmeraldArmorItem {

        public Chestplate() {
            super(Type.CHESTPLATE, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/emerald_armor_layer_1.png";
        }

        @Override
        public void onArmorTick(ItemStack itemstack, Level level, Player player) {
            EmeraldArmorJumpBootProcedure.execute(player);
        }
    }

    public static class Helmet extends EmeraldArmorItem {

        public Helmet() {
            super(Type.HELMET, (new Properties()).fireResistant());
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/emerald_armor_layer_1.png";
        }

        @Override
        public void onArmorTick(ItemStack itemstack, Level level, Player player) {
            EmeraldArmorJumpBootProcedure.execute(player);
        }
    }
}
