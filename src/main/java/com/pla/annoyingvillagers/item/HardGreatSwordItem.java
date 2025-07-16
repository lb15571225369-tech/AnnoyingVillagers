package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import com.pla.annoyingvillagers.procedures.HardGreatSwordOnHurtEnemyProcedure;
import com.pla.annoyingvillagers.procedures.HardGreatSwordSkillRightClickInAirProcedure;

public class HardGreatSwordItem extends SwordItem {

    public HardGreatSwordItem() {
        super(new Tier() {
            public int getUses() {
                return 1650;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 10.0F;
            }

            public int getLevel() {
                return 4;
            }

            public int getEnchantmentValue() {
                return 5;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 3, -2.0F, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB).fireResistant());
    }

    public boolean hurtEnemy(ItemStack itemstack, LivingEntity livingentity, LivingEntity livingentity1) {
        boolean flag = super.hurtEnemy(itemstack, livingentity, livingentity1);

        HardGreatSwordOnHurtEnemyProcedure.execute(itemstack);
        return flag;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        InteractionResultHolder<ItemStack> interactionresultholder = super.use(level, player, interactionhand);

        HardGreatSwordSkillRightClickInAirProcedure.execute(level, player.getX(), player.getY(), player.getZ(), player, (ItemStack) interactionresultholder.getObject());
        return interactionresultholder;
    }
}
