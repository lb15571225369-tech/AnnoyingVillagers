package com.pla.annoyingvillagers.item;

import java.util.List;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.procedures.LegendarySwordMobItemOnHurtEnemyProcedure;
import com.pla.annoyingvillagers.procedures.LegendarySwordMobItemOnEntityWingProcedure;
import com.pla.annoyingvillagers.procedures.LegendarySwordMobItemOnUseProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
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

public class LegendarySwordMobItem extends SwordItem {

    public LegendarySwordMobItem() {
        super(new Tier() {
            public int getUses() {
                return 9999;
            }

            public float getSpeed() {
                return 50.0F;
            }

            public float getAttackDamageBonus() {
                return 22.8F;
            }

            public int getLevel() {
                return 6;
            }

            public int getEnchantmentValue() {
                return 49;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[]{new ItemStack(Items.DIAMOND), new ItemStack(Items.ENCHANTED_BOOK)});
            }
        }, 3, -2.2F, (new Properties()).tab((CreativeModeTab) null).fireResistant());
    }

    public boolean hurtEnemy(ItemStack itemstack, LivingEntity livingentity, LivingEntity livingentity1) {
        boolean flag = super.hurtEnemy(itemstack, livingentity, livingentity1);

        LegendarySwordMobItemOnHurtEnemyProcedure.execute(livingentity.level, livingentity.getX(), livingentity.getY(), livingentity.getZ(), livingentity);
        return flag;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        InteractionResultHolder<ItemStack> interactionresultholder = super.use(level, player, interactionhand);

        LegendarySwordMobItemOnUseProcedure.execute(level, player.getX(), player.getY(), player.getZ(), player, (ItemStack) interactionresultholder.getObject());
        return interactionresultholder;
    }

    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.literal("One of the Legendary Weapons"));
    }

    public void onCraftedBy(ItemStack itemstack, Level level, Player player) {
        super.onCraftedBy(itemstack, level, player);
    }

    public boolean onEntitySwing(ItemStack itemstack, LivingEntity livingentity) {
        boolean flag = super.onEntitySwing(itemstack, livingentity);

        LegendarySwordMobItemOnEntityWingProcedure.execute(livingentity.level, livingentity.getX(), livingentity.getY(), livingentity.getZ());
        return flag;
    }

    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        if (entity != null) {
            if (entity instanceof Player) {
                Player player = (Player) entity;
                ItemStack item = new ItemStack((ItemLike) AnnoyingVillagersModItems.LEGENDARY_SWORD_MOB.get());

                player.getInventory().clearOrCountMatchingItems((it) -> {
                    return item.getItem() == it.getItem();
                }, 1, player.inventoryMenu.getCraftSlots());
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(ItemStack itemstack) {
        return true;
    }
}

