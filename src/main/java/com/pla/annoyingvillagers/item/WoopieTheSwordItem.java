package com.pla.annoyingvillagers.item;

import java.util.List;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.procedures.WoopieTheSwordItemOnEntityHitProcedure;
import com.pla.annoyingvillagers.procedures.WoopieTheSwordItemOnUseProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
public class WoopieTheSwordItem extends SwordItem {

    public WoopieTheSwordItem() {
        super(new Tier() {
            public int getUses() {
                return 1850;
            }

            public float getSpeed() {
                return 8.0F;
            }

            public float getAttackDamageBonus() {
                return 7.5F;
            }

            public int getLevel() {
                return 3;
            }

            public int getEnchantmentValue() {
                return 10;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[]{new ItemStack(Items.DIAMOND)});
            }
        }, 3, -2.8F, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB).fireResistant());
    }

    public boolean hurtEnemy(ItemStack itemstack, LivingEntity livingentity, LivingEntity livingentity1) {
        boolean flag = super.hurtEnemy(itemstack, livingentity, livingentity1);

        WoopieTheSwordItemOnEntityHitProcedure.execute(itemstack);
        return flag;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        InteractionResultHolder<ItemStack> interactionresultholder = super.use(level, player, interactionhand);

        WoopieTheSwordItemOnUseProcedure.execute(level, player.getX(), player.getY(), player.getZ(), player, (ItemStack) interactionresultholder.getObject());
        return interactionresultholder;
    }

    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(new TextComponent("\u4e00\u628a\u7279\u6b8a\u7684\u6b66\u5668\uff0c\u547d\u4e2d\u654c\u4eba\u540e\u51b2\u523a\u72b6\u6001\u4e0b\u53f3\u952e\u53ef\u91ca\u653e\u6280\u80fd"));
    }
}
