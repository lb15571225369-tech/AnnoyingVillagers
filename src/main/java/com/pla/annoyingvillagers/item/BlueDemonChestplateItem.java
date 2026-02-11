package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import com.pla.annoyingvillagers.tobe_removed.BlueDemonChestplateEventProcedure;

public abstract class BlueDemonChestplateItem extends ArmorItem {

    public BlueDemonChestplateItem(ArmorItem.Type type, Properties properties) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(Type pType) {
                return switch (pType) {
                    case BOOTS -> 13 * 31;
                    case LEGGINGS -> 15 * 31;
                    case CHESTPLATE -> 16 * 31;
                    case HELMET -> 11 * 31;
                };
            }

            @Override
            public int getDefenseForType(Type pType) {
                return switch (pType) {
                    case BOOTS -> 2;
                    case LEGGINGS -> 5;
                    case CHESTPLATE -> 30;
                    case HELMET -> 2;
                };
            }

            public int getEnchantmentValue() {
                return 9;
            }

            public SoundEvent getEquipSound() {
                return SoundEvents.ARMOR_EQUIP_GENERIC;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[]{new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Items.PRISMARINE_CRYSTALS)});
            }

            public String getName() {
                return "blue_demon_chestplate";
            }

            public float getToughness() {
                return 2.0F;
            }

            public float getKnockbackResistance() {
                return 0.0F;
            }
        }, type, properties);
    }

    public static class Chestplate extends BlueDemonChestplateItem {

        public Chestplate() {
            super(Type.CHESTPLATE, (new Properties()).fireResistant());
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return AnnoyingVillagers.MODID + ":textures/models/armor/blue_demon_chestplate_layer.png";
        }

        @Override
        public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
            super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
            if (player.getItemBySlot(EquipmentSlot.CHEST) == stack) {
                BlueDemonChestplateEventProcedure.execute(player);
            }
        }
    }
}
