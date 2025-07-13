package com.pla.annoyingvillagers.item;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

import com.pla.annoyingvillagers.client.model.ModelKnightH;
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
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class ZiCunQiKuiJiaItem extends ArmorItem {

    public ZiCunQiKuiJiaItem(EquipmentSlot equipmentslot, Properties properties) {
        super(new ArmorMaterial() {
            public int getDurabilityForSlot(EquipmentSlot equipmentslot1) {
                return (new int[]{13, 15, 16, 11})[equipmentslot1.getIndex()] * 25;
            }

            public int getDefenseForSlot(EquipmentSlot equipmentslot1) {
                return (new int[]{4, 6, 7, 5})[equipmentslot1.getIndex()];
            }

            public int getEnchantmentValue() {
                return 10;
            }

            public SoundEvent getEquipSound() {
                return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_chain"));
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }

            public String getName() {
                return "zi_cun_qi_kui_jia";
            }

            public float getToughness() {
                return 2.0F;
            }

            public float getKnockbackResistance() {
                return 0.0F;
            }
        }, equipmentslot, properties);
    }

    public static class Boots extends ZiCunQiKuiJiaItem {

        public Boots() {
            super(EquipmentSlot.FEET, (new Properties()).tab(CreativeModeTab.TAB_COMBAT));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/cun_min_qi_shi_zi_se_xiong_jia___layer_1.png";
        }
    }

    public static class Leggings extends ZiCunQiKuiJiaItem {

        public Leggings() {
            super(EquipmentSlot.LEGS, (new Properties()).tab(CreativeModeTab.TAB_COMBAT));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/cun_min_qi_shi_zi_se_xiong_jia___layer_2.png";
        }
    }

    public static class Chestplate extends ZiCunQiKuiJiaItem {

        public Chestplate() {
            super(EquipmentSlot.CHEST, (new Properties()).tab(CreativeModeTab.TAB_COMBAT));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/cun_min_qi_shi_zi_se_xiong_jia___layer_1.png";
        }
    }

    public static class Helmet extends ZiCunQiKuiJiaItem {

        public Helmet() {
            super(EquipmentSlot.HEAD, (new Properties()).tab(CreativeModeTab.TAB_COMBAT));
        }

        public void initializeClient(Consumer<IItemRenderProperties> consumer) {
            consumer.accept(new IItemRenderProperties() {
                public HumanoidModel getArmorModel(LivingEntity livingentity, ItemStack itemstack, EquipmentSlot equipmentslot, HumanoidModel humanoidmodel) {
                    HumanoidModel humanoidmodel1 = new HumanoidModel(new ModelPart(Collections.emptyList(), Map.of("head", (new ModelKnightH<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelKnightH.LAYER_LOCATION))).Head, "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "body", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));

                    humanoidmodel1.crouching = livingentity.isShiftKeyDown();
                    humanoidmodel1.riding = humanoidmodel.riding;
                    humanoidmodel1.young = livingentity.isBaby();
                    return humanoidmodel1;
                }
            });
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/entities/purple.png";
        }
    }
}

