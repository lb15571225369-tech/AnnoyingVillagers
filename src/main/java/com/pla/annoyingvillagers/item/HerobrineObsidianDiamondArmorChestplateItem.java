package com.pla.annoyingvillagers.item;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

import com.pla.annoyingvillagers.client.model.ModelHerobrineObsidianDiamondChestplate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import static com.pla.annoyingvillagers.util.ArmorUtil.dropArmorSlot;

public abstract class HerobrineObsidianDiamondArmorChestplateItem extends ArmorItem {

    public HerobrineObsidianDiamondArmorChestplateItem(ArmorItem.Type type, Properties properties) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(Type pType) {
                return switch (pType) {
                    case BOOTS      -> 13 * 25;
                    case LEGGINGS   -> 15 * 25;
                    case CHESTPLATE -> 16 * 25;
                    case HELMET     -> 11 * 25;
                };
            }

            @Override
            public int getDefenseForType(Type pType) {
                return switch (pType) {
                    case BOOTS      -> 0;
                    case LEGGINGS   -> 0;
                    case CHESTPLATE -> 24;
                    case HELMET     -> 0;
                };
            }

            public int getEnchantmentValue() {
                return 0;
            }

            public SoundEvent getEquipSound() {
                return SoundEvents.ARMOR_EQUIP_GENERIC;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }

            public String getName() {
                return "herobrine_obsidian_diamond_armor";
            }

            public float getToughness() {
                return 2.0F;
            }

            public float getKnockbackResistance() {
                return 0.0F;
            }
        }, type, properties);
    }

    public static class Chestplate extends HerobrineObsidianDiamondArmorChestplateItem {

        public Chestplate() {
            super(Type.CHESTPLATE, (new Properties()));
        }

        public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
                @Override
                public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                    HumanoidModel humanoidmodel1 = new HumanoidModel(new ModelPart(Collections.emptyList(), Map.of("body", (new ModelHerobrineObsidianDiamondChestplate<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelHerobrineObsidianDiamondChestplate.LAYER_LOCATION))).Body, "left_arm", (new ModelHerobrineObsidianDiamondChestplate<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelHerobrineObsidianDiamondChestplate.LAYER_LOCATION))).LeftArm, "right_arm", (new ModelHerobrineObsidianDiamondChestplate<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelHerobrineObsidianDiamondChestplate.LAYER_LOCATION))).RightArm, "head", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));

                    humanoidmodel1.crouching = livingEntity.isShiftKeyDown();
                    humanoidmodel1.riding = original.riding;
                    humanoidmodel1.young = livingEntity.isBaby();
                    return humanoidmodel1;
                }
            });
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/herobrine_obsidian_armor_layer_1.png";
        }

        @Override
        public void onArmorTick(ItemStack itemstack, Level level, Player player) {
            if (player != null) {
                dropArmorSlot(player, EquipmentSlot.FEET, "Herobrine Obsidian Diamond Chestplate");
                dropArmorSlot(player, EquipmentSlot.LEGS, "Herobrine Obsidian Diamond Chestplate");
            }
        }
    }
}
