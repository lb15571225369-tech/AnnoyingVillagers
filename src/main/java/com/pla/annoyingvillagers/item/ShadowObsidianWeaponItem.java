package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.procedures.ShadowObsidianWeaponSpecialAttackProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import com.pla.annoyingvillagers.procedures.ShadowObsidianOnUseProcedure;

import java.util.List;

public class ShadowObsidianWeaponItem extends SwordItem {

    public ShadowObsidianWeaponItem() {
        super(new Tier() {
            public int getUses() {
                return 3000;
            }

            public float getSpeed() {
                return 50.0F;
            }

            public float getAttackDamageBonus() {
                return 6.0F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 0;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[]{new ItemStack(Blocks.OBSIDIAN)});
            }
        }, 3, 0.5F, (new Properties()).fireResistant());
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        ItemStack stack = player.getItemInHand(interactionhand);
        ShadowObsidianOnUseProcedure.execute(level, player.getX(), player.getY(), player.getZ(), player, stack, interactionhand);
        return InteractionResultHolder.success(stack);
    }

    public InteractionResult useOn(UseOnContext useoncontext) {
        super.useOn(useoncontext);
        ShadowObsidianOnUseProcedure.execute(useoncontext.getLevel(), (double) useoncontext.getClickedPos().getX(), (double) useoncontext.getClickedPos().getY(), (double) useoncontext.getClickedPos().getZ(), useoncontext.getPlayer(), useoncontext.getItemInHand(), useoncontext.getHand());
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.translatable("tooltip.annoyingvillagers.shadow_obsidian_weapon"));
    }

    public void specialAttack(LivingEntity entity) {
        ShadowObsidianWeaponSpecialAttackProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity, entity.getMainHandItem(), InteractionHand.MAIN_HAND);
    }
}
