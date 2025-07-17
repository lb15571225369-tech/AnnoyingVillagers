package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.AnnoyingVillagers;
//import com.pla.annoyingvillagers.client.model.ModelVillagerScoutHelmet;
import com.pla.annoyingvillagers.client.model.ModelVillagerScoutHelmet;
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
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

public abstract class VillagerScoutHelmetItem extends ArmorItem {

    public VillagerScoutHelmetItem(EquipmentSlot equipmentslot, Properties properties) {
        super(new ArmorMaterial() {
            public int getDurabilityForSlot(EquipmentSlot equipmentslot1) {
                return (new int[]{13, 15, 16, 11})[equipmentslot1.getIndex()] * 25;
            }

            public int getDefenseForSlot(EquipmentSlot equipmentslot1) {
                return (new int[]{2, 5, 6, 5})[equipmentslot1.getIndex()];
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
                return "villager_scout_helmet";
            }

            public float getToughness() {
                return 1.0F;
            }

            public float getKnockbackResistance() {
                return 0.0F;
            }
        }, equipmentslot, properties);
    }

    public static class Helmet extends VillagerScoutHelmetItem {

        public Helmet() {
            super(EquipmentSlot.HEAD, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB));
        }

        @Override
        public void initializeClient(Consumer<IItemRenderProperties> consumer) {
            consumer.accept(new IItemRenderProperties() {
                HumanoidModel<LivingEntity> armorModel = null;

                @Override
                public HumanoidModel<?> getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> defaultModel) {
                    if (armorModel == null) {
                        ModelVillagerScoutHelmet<?> helmetModel = new ModelVillagerScoutHelmet<>(
                                Minecraft.getInstance().getEntityModels().bakeLayer(ModelVillagerScoutHelmet.LAYER_LOCATION)
                        );

                        ModelPart root = new ModelPart(Collections.emptyList(), Map.of(
                                "head", helmetModel.Head,
                                "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                "body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                "left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())
                        ));

                        armorModel = new HumanoidModel<>(root);
                    }

                    armorModel.crouching = entity.isCrouching();
                    armorModel.riding = entity.isPassenger();
                    armorModel.young = entity.isBaby();

                    return armorModel;
                }
            });
        }


        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/villager_scout_layer.png";
        }
    }
}