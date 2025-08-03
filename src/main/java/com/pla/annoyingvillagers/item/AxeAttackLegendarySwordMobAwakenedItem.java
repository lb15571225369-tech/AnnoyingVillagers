package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.procedures.AwakenedLegendarySwordMobOnHurtProcedure;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class AxeAttackLegendarySwordMobAwakenedItem extends SwordItem {

    public AxeAttackLegendarySwordMobAwakenedItem() {
        super(new Tier() {
            public int getUses() {
                return 99999;
            }

            public float getSpeed() {
                return 50.0F;
            }

            public float getAttackDamageBonus() {
                return 48.0F;
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

        AwakenedLegendarySwordMobOnHurtProcedure.execute(livingentity.level(), livingentity.getX(), livingentity.getY(), livingentity.getZ(), livingentity);
        return flag;
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
            if (entity != null) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    ItemStack stack = new ItemStack((ItemLike) AnnoyingVillagersModItems.AXE_ATTACK_LEGENDARY_SWORD_MOB_AWAKENED.get());

                    player.getInventory().clearOrCountMatchingItems((itemstack1) -> {
                        return stack.getItem() == itemstack1.getItem();
                    }, 1, player.inventoryMenu.getCraftSlots());
                }

            }
        }
        if (entity != null) {
            LivingEntity livingentity;

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1, 2));
                }
            }

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance(MobEffects.JUMP, 1, 2));
                }
            }

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 1, false, false));
                }
            }

        }
    }
}
