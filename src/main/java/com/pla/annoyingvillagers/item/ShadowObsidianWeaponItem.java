package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import com.pla.annoyingvillagers.procedures.ShadowObsidianProcedure;

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
                return 16.0F;
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
        }, 3, 0.5F, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB).fireResistant());
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        InteractionResultHolder<ItemStack> interactionresultholder = super.use(level, player, interactionhand);

        ShadowObsidianProcedure.execute(level, player.getX(), player.getY(), player.getZ(), player, (ItemStack) interactionresultholder.getObject());
        return interactionresultholder;
    }

    public InteractionResult useOn(UseOnContext useoncontext) {
        super.useOn(useoncontext);
        ShadowObsidianProcedure.execute(useoncontext.getLevel(), (double) useoncontext.getClickedPos().getX(), (double) useoncontext.getClickedPos().getY(), (double) useoncontext.getClickedPos().getZ(), useoncontext.getPlayer(), useoncontext.getItemInHand());
        return InteractionResult.SUCCESS;
    }

    public boolean onEntitySwing(ItemStack itemstack, LivingEntity livingentity) {
        boolean flag = super.onEntitySwing(itemstack, livingentity);

        ShadowObsidianProcedure.execute(livingentity.level, livingentity.getX(), livingentity.getY(), livingentity.getZ(), livingentity, itemstack);
        return flag;
    }
}
