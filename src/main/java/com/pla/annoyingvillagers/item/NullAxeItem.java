package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.entity.NullAxeEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.List;

public class NullAxeItem extends AxeItem {

    public NullAxeItem() {
        super(new Tier() {
            public int getUses() {
                return 100;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 5.0F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 2;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 3, -3.0F, (new Properties()));
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        ItemStack stack = player.getItemInHand(interactionhand);

        if (!player.isShiftKeyDown()) {
            return InteractionResultHolder.pass(stack);
        }

        if (!(level instanceof ServerLevel server)) {
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }

        if (player.getPersistentData().contains("NullAxeUUID")) {
            return InteractionResultHolder.pass(stack);
        }

        var look = player.getLookAngle();
        double sx = player.getX() + look.x * 1.0;
        double sy = player.getEyeY() - 0.2;
        double sz = player.getZ() + look.z * 1.0;

        NullAxeEntity sword = AnnoyingVillagersModEntities.NULL_AXE.get().create(server);
        if (sword == null) {
            return InteractionResultHolder.fail(stack);
        }

        sword.moveTo(sx, sy, sz, player.getYRot(), player.getXRot());
        sword.setPlayer(player);
        sword.setPlayerUUID(player.getUUID());
        sword.setReturnGameTime(server.getGameTime() + 600L);
        sword.finalizeSpawn(server, server.getCurrentDifficultyAt(sword.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
        server.addFreshEntity(sword);
        sword.setItemInHand(InteractionHand.MAIN_HAND, stack.copy());

        player.setItemInHand(interactionhand, ItemStack.EMPTY);
        player.getCooldowns().addCooldown(this, 10);
        player.getPersistentData().putUUID("NullAxeUUID", sword.getUUID());

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.translatable("tooltip.annoyingvillagers.null_weapon"));
    }
}
