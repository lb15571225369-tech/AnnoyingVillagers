package com.pla.annoyingvillagers.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.pla.annoyingvillagers.entity.BlueDemonTridentEntity;
import com.pla.annoyingvillagers.procedures.BlueDemonTridentOnRangedItemUseProcedure;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.Random;

public class BlueDemonTridentItem extends Item {

    public BlueDemonTridentItem() {
        super((new Properties()).durability(2031));
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        player.startUsingItem(interactionhand);
        return new InteractionResultHolder(InteractionResult.SUCCESS, player.getItemInHand(interactionhand));
    }

    public UseAnim getUseAnimation(ItemStack itemstack) {
        return UseAnim.SPEAR;
    }

    public int getUseDuration(ItemStack itemstack) {
        return 72000;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentslot) {
        if (equipmentslot == EquipmentSlot.MAINHAND) {
            Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

            builder.putAll(super.getDefaultAttributeModifiers(equipmentslot));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BlueDemonTridentItem.BASE_ATTACK_DAMAGE_UUID, "Ranged item modifier", 6.4D, Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BlueDemonTridentItem.BASE_ATTACK_SPEED_UUID, "Ranged item modifier", -2.4D, Operation.ADDITION));
            return builder.build();
        } else {
            return super.getDefaultAttributeModifiers(equipmentslot);
        }
    }

    public void releaseUsing(ItemStack itemstack, Level level, LivingEntity livingentity, int i) {
        if (!level.isClientSide() && livingentity instanceof ServerPlayer) {
            ServerPlayer serverplayer = (ServerPlayer) livingentity;
            double d0 = serverplayer.getX();
            double d1 = serverplayer.getY();
            double d2 = serverplayer.getZ();
            BlueDemonTridentEntity bluedemontridententity = BlueDemonTridentEntity.shoot(level, serverplayer, (Random) level.getRandom(), 1.1F, 9.0D, 4);

            itemstack.hurtAndBreak(1, serverplayer, (serverplayer1) -> {
                serverplayer1.broadcastBreakEvent(serverplayer.getUsedItemHand());
            });
            bluedemontridententity.pickup = Pickup.DISALLOWED;
            BlueDemonTridentOnRangedItemUseProcedure.execute(level, d0, d1, d2, serverplayer);
        }

    }
}

