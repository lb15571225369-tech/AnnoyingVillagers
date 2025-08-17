package com.pla.annoyingvillagers.item;

import java.util.List;

import com.pla.annoyingvillagers.procedures.DemoniacVoltageReaverOnUseProcedure;
import com.pla.annoyingvillagers.procedures.HerobrineWeaponEffectProcedure;
import com.pla.annoyingvillagers.util.SnakeBladeHit;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class DemoniacVoltageReaverItem extends SwordItem {

    public DemoniacVoltageReaverItem() {
        super(new Tier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 18.0F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 4;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 3, -3.0F, (new Properties()));
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        InteractionResultHolder<ItemStack> interactionresultholder = super.use(level, player, interactionhand);

        DemoniacVoltageReaverOnUseProcedure.execute(level, player.getX(), player.getY(), player.getZ(), player, player.getMainHandItem());
        return interactionresultholder;
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (itemstack.getTag().getBoolean("SecondForm")) {
            if (itemstack.getTag().getInt("HitCount") == 10) {
                if (SnakeBladeHit.process(itemstack, pAttacker)) {
                    itemstack.getOrCreateTag().putBoolean("SnakeAnimation", true);
                    itemstack.removeTagKey("HitCount");
                }
            } else {
                itemstack.getTag().putInt("HitCount", (itemstack.getTag().contains("HitCount") ? itemstack.getTag().getInt("HitCount") : 0) + 1);
            }
        }

        return super.hurtEnemy(itemstack, pTarget, pAttacker);
    }

    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.literal("§5One of Herobrine's legendary weapons, has two form§r"));
    }

    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        if (flag && itemstack.getTag().getBoolean("SecondForm")) {
            HerobrineWeaponEffectProcedure.execute(level, entity.getX(), entity.getY(), entity.getZ(), entity);
        }

    }
}
