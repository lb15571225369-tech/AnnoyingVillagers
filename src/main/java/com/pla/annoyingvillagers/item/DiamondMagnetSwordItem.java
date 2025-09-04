package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.procedures.DiamondMagnetSwordOnTickProcedure;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class DiamondMagnetSwordItem extends SwordItem {

    public DiamondMagnetSwordItem() {
        super(new Tier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 8.0F;
            }

            public float getAttackDamageBonus() {
                return 10.0F;
            }

            public int getLevel() {
                return 3;
            }

            public int getEnchantmentValue() {
                return 10;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 3, -2.5F, (new Properties()));
    }

    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        if (flag) {
            DiamondMagnetSwordOnTickProcedure.execute(level, entity.getX(), entity.getY(), entity.getZ(), entity);
        }

        if (entity != null) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;
                livingentity.removeEffect(MobEffects.WEAKNESS);
            }

        }
    }
}
