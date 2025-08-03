package com.pla.annoyingvillagers.item;

import java.util.List;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.procedures.HeavyAttackLegendarySwordMobOnHurtProcedure;
import com.pla.annoyingvillagers.procedures.HeavyAttackLegendarySwordMobOnUserProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HeavyAttackLegendarySwordMobItem extends SwordItem {

    public HeavyAttackLegendarySwordMobItem() {
        super(new Tier() {
            public int getUses() {
                return 9999;
            }

            public float getSpeed() {
                return 50.0F;
            }

            public float getAttackDamageBonus() {
                return 17.9F;
            }

            public int getLevel() {
                return 50;
            }

            public int getEnchantmentValue() {
                return 50;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[]{new ItemStack((ItemLike) AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()), new ItemStack(Items.DIAMOND)});
            }
        }, 3, -0.6F, (new Properties()).fireResistant());
    }

    public boolean hurtEnemy(ItemStack itemstack, LivingEntity livingentity, LivingEntity livingentity1) {
        boolean flag = super.hurtEnemy(itemstack, livingentity, livingentity1);

        HeavyAttackLegendarySwordMobOnHurtProcedure.execute(livingentity.level(), livingentity.getX(), livingentity.getY(), livingentity.getZ(), livingentity);
        return flag;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        InteractionResultHolder<ItemStack> interactionresultholder = super.use(level, player, interactionhand);

        HeavyAttackLegendarySwordMobOnUserProcedure.execute(level, player.getX(), player.getY(), player.getZ(), player);
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

    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
    }

    public void onCraftedBy(ItemStack itemstack, Level level, Player player) {
        super.onCraftedBy(itemstack, level, player);
    }

    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        if (entity != null) {
            if (entity instanceof Player) {
                Player player = (Player) entity;
                ItemStack stack = new ItemStack((ItemLike) AnnoyingVillagersModItems.HEAVY_ATTACK_LEGENDARY_SWORD_MOB.get());

                player.getInventory().clearOrCountMatchingItems((itemstack1) -> {
                    return stack.getItem() == itemstack1.getItem();
                }, 1, player.inventoryMenu.getCraftSlots());
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(ItemStack itemstack) {
        return true;
    }
}
