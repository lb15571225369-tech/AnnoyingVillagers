package com.pla.annoyingvillagers.item;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.model.ModelVillagerGeneralArmor;
import com.pla.annoyingvillagers.client.model.ModelGreenVillagerGeneralArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class GreenVillagerGeneralArmorItem extends ArmorItem {

    public GreenVillagerGeneralArmorItem(EquipmentSlot equipmentslot, Properties properties) {
        super(new ArmorMaterial() {
            public int getDurabilityForSlot(EquipmentSlot equipmentslot1) {
                return (new int[]{13, 15, 16, 11})[equipmentslot1.getIndex()] * 25;
            }

            public int getDefenseForSlot(EquipmentSlot equipmentslot1) {
                return (new int[]{4, 5, 7, 5})[equipmentslot1.getIndex()];
            }

            public int getEnchantmentValue() {
                return 10;
            }

            public SoundEvent getEquipSound() {
                return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }

            public String getName() {
                return "green_villager_general_armor";
            }

            public float getToughness() {
                return 2.0F;
            }

            public float getKnockbackResistance() {
                return 0.0F;
            }
        }, equipmentslot, properties);
    }

    public static class Chestplate extends GreenVillagerGeneralArmorItem {

        public Chestplate() {
            super(EquipmentSlot.CHEST, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB));
        }

        public void initializeClient(Consumer<IItemRenderProperties> consumer) {
            consumer.accept(new IItemRenderProperties() {
                @OnlyIn(Dist.CLIENT)
                public HumanoidModel getArmorModel(LivingEntity livingentity, ItemStack itemstack, EquipmentSlot equipmentslot, HumanoidModel humanoidmodel) {
                    HumanoidModel humanoidmodel1 = new HumanoidModel(new ModelPart(Collections.emptyList(), Map.of("body", (new ModelGreenVillagerGeneralArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelGreenVillagerGeneralArmor.LAYER_LOCATION))).Body, "left_arm", (new ModelGreenVillagerGeneralArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelGreenVillagerGeneralArmor.LAYER_LOCATION))).LeftArm, "right_arm", (new ModelGreenVillagerGeneralArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelGreenVillagerGeneralArmor.LAYER_LOCATION))).RightArm, "head", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));

                    humanoidmodel1.crouching = livingentity.isShiftKeyDown();
                    humanoidmodel1.riding = humanoidmodel.riding;
                    humanoidmodel1.young = livingentity.isBaby();
                    return humanoidmodel1;
                }
            });
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/green_villager_general_armor_layer.png";
        }
    }

    public static class Helmet extends GreenVillagerGeneralArmorItem {

        public Helmet() {
            super(EquipmentSlot.HEAD, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB));
        }

        public void initializeClient(Consumer<IItemRenderProperties> consumer) {
            consumer.accept(new IItemRenderProperties() {
                public HumanoidModel getArmorModel(LivingEntity livingentity, ItemStack itemstack, EquipmentSlot equipmentslot, HumanoidModel humanoidmodel) {
                    HumanoidModel humanoidmodel1 = new HumanoidModel(new ModelPart(Collections.emptyList(), Map.of("head", (new ModelVillagerGeneralArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelVillagerGeneralArmor.LAYER_LOCATION))).Head, "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "body", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));

                    humanoidmodel1.crouching = livingentity.isShiftKeyDown();
                    humanoidmodel1.riding = humanoidmodel.riding;
                    humanoidmodel1.young = livingentity.isBaby();
                    return humanoidmodel1;
                }
            });
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/entities/green.png";
        }
    }
}

