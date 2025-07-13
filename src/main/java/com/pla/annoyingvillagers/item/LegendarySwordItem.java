package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.pla.annoyingvillagers.procedures.LegendarySwordDangGongJuChuXianZaiShouZhongShiProcedure;
import com.pla.annoyingvillagers.procedures.LegendarySwordDangGongJuChuXianZaiWuPinLanShiProcedure;
import com.pla.annoyingvillagers.procedures.LegendarySwordDangShiTiBeiGongJuJiZhongShiProcedure;
import com.pla.annoyingvillagers.procedures.LegendarySwordDangYouJianDianJiKongQiShiProcedure;

public class LegendarySwordItem extends SwordItem {

    public LegendarySwordItem() {
        super(new Tier() {
            public int getUses() {
                return 9999;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 14.0F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 2;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[]{new ItemStack(Items.DIAMOND)});
            }
        }, 3, -2.32F, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB).fireResistant());
    }

    public boolean hurtEnemy(ItemStack itemstack, LivingEntity livingentity, LivingEntity livingentity1) {
        boolean flag = super.hurtEnemy(itemstack, livingentity, livingentity1);

        LegendarySwordDangShiTiBeiGongJuJiZhongShiProcedure.execute(livingentity, itemstack);
        return flag;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        InteractionResultHolder<ItemStack> interactionresultholder = super.use(level, player, interactionhand);

        LegendarySwordDangYouJianDianJiKongQiShiProcedure.execute(level, player, (ItemStack) interactionresultholder.getObject());
        return interactionresultholder;
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

    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        if (flag) {
            LegendarySwordDangGongJuChuXianZaiShouZhongShiProcedure.execute(entity);
        }

        LegendarySwordDangGongJuChuXianZaiWuPinLanShiProcedure.execute(entity, itemstack);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(ItemStack itemstack) {
        return true;
    }
}
