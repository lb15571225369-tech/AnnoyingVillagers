package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.procedures.ShadowObsidianPillarSpecialAttackProcedure;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShadowObsidianPillarItem extends SwordItem {

    public ShadowObsidianPillarItem() {
        super(new Tier() {
            public int getUses() {
                return 3000;
            }

            public float getSpeed() {
                return 50.0F;
            }

            public float getAttackDamageBonus() {
                return 2.0F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 0;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()));
            }
        }, 3, 0.5F, (new Properties()).fireResistant());
    }

//    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand interactionhand) {
//        ItemStack stack = player.getItemInHand(interactionhand);
////        ShadowObsidianPillarItemOnUseProcedure.execute(level, player.getX(), player.getY(), player.getZ(), player, stack, interactionhand);
//        if (level instanceof ServerLevel serverLevel) {
//            if (player.isShiftKeyDown()) {
//                HerobrineUtil.summonShadowObsidianLongPillarDefense(serverLevel, player);
//            } else {
//                HerobrineUtil.summonShadowObsidianLongPillarShootToward(serverLevel, player);
//            }
//        }
//        return InteractionResultHolder.success(stack);
//    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.translatable("tooltip.annoyingvillagers.shadow_obsidian_pillar"));
    }

    public void specialAttack(LivingEntity entity) {
        ShadowObsidianPillarSpecialAttackProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity, entity.getMainHandItem(), InteractionHand.MAIN_HAND);
    }
}
