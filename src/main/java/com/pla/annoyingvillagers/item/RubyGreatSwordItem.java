package com.pla.annoyingvillagers.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class RubyGreatSwordItem extends TieredItem {

    public RubyGreatSwordItem() {
        super(new Tier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 8.0F;
            }

            public float getAttackDamageBonus() {
                return 11.5F;
            }

            public int getLevel() {
                return 3;
            }

            public int getEnchantmentValue() {
                return 8;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[]{new ItemStack(Items.DIAMOND), new ItemStack((ItemLike) AnnoyingVillagersModItems.PURPLE_GEM.get()), new ItemStack((ItemLike) AnnoyingVillagersModItems.RUBY.get())});
            }
        }, (new Properties()));
    }

    public boolean isCorrectToolForDrops(BlockState blockstate) {
        byte b0 = 3;

        return b0 < 3 && blockstate.is(BlockTags.NEEDS_DIAMOND_TOOL) ? false : (b0 < 2 && blockstate.is(BlockTags.NEEDS_IRON_TOOL) ? false : (b0 < 1 && blockstate.is(BlockTags.NEEDS_STONE_TOOL) ? false : blockstate.is(BlockTags.MINEABLE_WITH_AXE) || blockstate.is(BlockTags.MINEABLE_WITH_HOE) || blockstate.is(BlockTags.MINEABLE_WITH_PICKAXE) || blockstate.is(BlockTags.MINEABLE_WITH_SHOVEL)));
    }

    public boolean canPerformAction(ItemStack itemstack, ToolAction toolaction) {
        return ToolActions.DEFAULT_AXE_ACTIONS.contains(toolaction) || ToolActions.DEFAULT_HOE_ACTIONS.contains(toolaction) || ToolActions.DEFAULT_SHOVEL_ACTIONS.contains(toolaction) || ToolActions.DEFAULT_PICKAXE_ACTIONS.contains(toolaction) || ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolaction);
    }

    public float getDestroySpeed(ItemStack itemstack, BlockState blockstate) {
        return 8.0F;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentslot) {
        if (equipmentslot == EquipmentSlot.MAINHAND) {
            Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

            builder.putAll(super.getDefaultAttributeModifiers(equipmentslot));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(RubyGreatSwordItem.BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 11.5D, Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(RubyGreatSwordItem.BASE_ATTACK_SPEED_UUID, "Tool modifier", -3.0D, Operation.ADDITION));
            return builder.build();
        } else {
            return super.getDefaultAttributeModifiers(equipmentslot);
        }
    }

    public boolean mineBlock(ItemStack itemstack, Level level, BlockState blockstate, BlockPos blockpos, LivingEntity livingentity) {
        itemstack.hurtAndBreak(1, livingentity, (livingentity1) -> {
            livingentity1.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    public boolean hurtEnemy(ItemStack itemstack, LivingEntity livingentity, LivingEntity livingentity1) {
        itemstack.hurtAndBreak(2, livingentity, (livingentity2) -> {
            livingentity2.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    public boolean hasContainerItem(ItemStack itemstack) {
        return true;
    }

    public ItemStack getContainerItem(ItemStack itemstack) {
        return new ItemStack(this);
    }

    public boolean isRepairable(ItemStack itemstack) {
        return false;
    }
}
