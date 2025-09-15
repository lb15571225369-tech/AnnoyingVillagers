package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.procedures.ShadowObsidianPillarItemOnUseProcedure;
import com.pla.annoyingvillagers.procedures.ShadowObsidianPillarSpecialAttackProcedure;
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

import java.util.List;

public class ShadowObsidianPillarItem extends SwordItem {

    public ShadowObsidianPillarItem() {
        super(new Tier() {
            public int getUses() {
                return 3000;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 8.0F;
            }

            public int getLevel() {
                return 4;
            }

            public int getEnchantmentValue() {
                return 0;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 3, -1.0F, (new Properties()));
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        ItemStack stack = player.getItemInHand(interactionhand);
        ShadowObsidianPillarItemOnUseProcedure.execute(level, player.getX(), player.getY(), player.getZ(), player, stack, interactionhand);
        return InteractionResultHolder.success(stack);
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.literal(
                "One of the §5Shadow Herobrine§r's weapon.\n" +
                        "§6Right-click§r to launch a chain of Shadow Obsidian Pillar.\n" +
                        "§6Weapon More Attack§r to summon a wall of Shadow Obsidian Pillar."
        ));
    }

    public InteractionResult useOn(UseOnContext useoncontext) {
        super.useOn(useoncontext);
        ShadowObsidianPillarItemOnUseProcedure.execute(useoncontext.getLevel(), (double) useoncontext.getClickedPos().getX(), (double) useoncontext.getClickedPos().getY(), (double) useoncontext.getClickedPos().getZ(), useoncontext.getPlayer(), useoncontext.getItemInHand(), useoncontext.getHand());
        return InteractionResult.SUCCESS;
    }

    public void specialAttack(LivingEntity entity) {
        ShadowObsidianPillarSpecialAttackProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity, entity.getMainHandItem(), InteractionHand.MAIN_HAND);
    }
}
