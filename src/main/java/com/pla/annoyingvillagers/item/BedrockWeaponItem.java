package com.pla.annoyingvillagers.item;


import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BedrockWeaponItem extends Item {

    public BedrockWeaponItem() {
        super((new Properties()).durability(200).fireResistant());
    }

    public float getDestroySpeed(ItemStack itemstack, BlockState blockstate) {
        return 1.0F;
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

    public int getEnchantmentValue() {
        return 2;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentslot) {
        if (equipmentslot == EquipmentSlot.MAINHAND) {
            Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

            builder.putAll(super.getDefaultAttributeModifiers(equipmentslot));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BedrockWeaponItem.BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 2.0D, Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BedrockWeaponItem.BASE_ATTACK_SPEED_UUID, "Tool modifier", -3.0D, Operation.ADDITION));
            return builder.build();
        } else {
            return super.getDefaultAttributeModifiers(equipmentslot);
        }
    }
}
