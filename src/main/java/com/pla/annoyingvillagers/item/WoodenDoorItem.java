package com.pla.annoyingvillagers.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import java.util.List;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.procedures.WoodenDoorItemOnUseProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class WoodenDoorItem extends TieredItem {

    public WoodenDoorItem() {
        super(new Tier() {
            public int getUses() {
                return 400;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 5.5F;
            }

            public int getLevel() {
                return 4;
            }

            public int getEnchantmentValue() {
                return 4;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[]{new ItemStack(Blocks.OAK_DOOR), new ItemStack(Blocks.STRIPPED_OAK_WOOD), new ItemStack(Blocks.STRIPPED_SPRUCE_WOOD)});
            }
        }, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB));
    }

    public boolean isCorrectToolForDrops(BlockState blockstate) {
        byte b0 = 4;

        return b0 < 3 && blockstate.is(BlockTags.NEEDS_DIAMOND_TOOL) ? false : (b0 < 2 && blockstate.is(BlockTags.NEEDS_IRON_TOOL) ? false : (b0 < 1 && blockstate.is(BlockTags.NEEDS_STONE_TOOL) ? false : blockstate.is(BlockTags.MINEABLE_WITH_AXE) || blockstate.is(BlockTags.MINEABLE_WITH_HOE) || blockstate.is(BlockTags.MINEABLE_WITH_PICKAXE) || blockstate.is(BlockTags.MINEABLE_WITH_SHOVEL)));
    }

    public boolean canPerformAction(ItemStack itemstack, ToolAction toolaction) {
        return ToolActions.DEFAULT_AXE_ACTIONS.contains(toolaction) || ToolActions.DEFAULT_HOE_ACTIONS.contains(toolaction) || ToolActions.DEFAULT_SHOVEL_ACTIONS.contains(toolaction) || ToolActions.DEFAULT_PICKAXE_ACTIONS.contains(toolaction) || ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolaction);
    }

    public float getDestroySpeed(ItemStack itemstack, BlockState blockstate) {
        return 4.0F;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentslot) {
        if (equipmentslot == EquipmentSlot.MAINHAND) {
            Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

            builder.putAll(super.getDefaultAttributeModifiers(equipmentslot));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(WoodenDoorItem.BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 5.5D, Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(WoodenDoorItem.BASE_ATTACK_SPEED_UUID, "Tool modifier", -2.4D, Operation.ADDITION));
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

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        InteractionResultHolder<ItemStack> interactionresultholder = super.use(level, player, interactionhand);

        WoodenDoorItemOnUseProcedure.execute(level, player.getX(), player.getY(), player.getZ());
        return interactionresultholder;
    }

    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(new TextComponent("\u975e\u5e38\u5947\u8469\u7684\u6728\u95e8\uff0c\u83dc\u9e1f\u7528\u4e86\u90fd\u8bf4\u597d"));
    }
}

