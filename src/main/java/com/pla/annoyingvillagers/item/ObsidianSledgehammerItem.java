package com.pla.annoyingvillagers.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class ObsidianSledgehammerItem extends AxeItem {

    public ObsidianSledgehammerItem() {
        super(new Tier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 12.0F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 32;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[]{new ItemStack(Blocks.CRYING_OBSIDIAN)});
            }
        }, 1.0F, -2.6F, (new Properties()).fireResistant());
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        InteractionResultHolder<ItemStack> interactionresultholder = super.use(level, player, interactionhand);

        if (!player.level().isClientSide()) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20, 3));
        }
        if (player != null) {
            player.getCooldowns().addCooldown(((ItemStack) interactionresultholder.getObject()).getItem(), 28);
        }
        return interactionresultholder;
    }
}
