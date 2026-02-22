package com.pla.annoyingvillagers.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.particle.EpicFightParticles;

public class DiamondGreatSwordItem extends SwordItem {

    public DiamondGreatSwordItem() {
        super(new Tier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 8.0F;
            }

            public float getAttackDamageBonus() {
                return 8.0F;
            }

            public int getLevel() {
                return 3;
            }

            public int getEnchantmentValue() {
                return 10;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Items.DIAMOND));
            }
        }, 3, -2.8F, (new Properties()));
    }

    public boolean hurtEnemy(@NotNull ItemStack itemstack, @NotNull LivingEntity livingentity, @NotNull LivingEntity livingentity1) {
        boolean flag = super.hurtEnemy(itemstack, livingentity, livingentity1);
        itemstack.getOrCreateTag().putDouble("sword_skill", itemstack.getOrCreateTag().getDouble("sword_skill") + 1.0D);
        return flag;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand interactionhand) {
        InteractionResultHolder<ItemStack> interactionresultholder = super.use(level, player, interactionhand);
        ItemStack itemstack = interactionresultholder.getObject();
        double d0 = player.getX();
        double d1 = player.getY();
        double d2 = player.getZ();

        if (player.isShiftKeyDown() && itemstack.getOrCreateTag().getDouble("sword_skill") >= 1.0D) {
            player.setDeltaMovement(new Vec3(0.0D, 1.0D, 0.0D));
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        EpicFightParticles.AIR_BURST.get(),
                        player.getX(), player.getY() + 1.5D, player.getZ(),
                        1,
                        0.0D, 0.0D, 0.0D,
                        7.0D
                );
                serverLevel.playSound(
                        null,
                        BlockPos.containing(d0, d1, d2),
                        SoundEvents.ENDER_DRAGON_FLAP,
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F
                );
                serverLevel.sendParticles(
                        ParticleTypes.EXPLOSION,
                        d0, d1, d2,
                        1,
                        0.0D, 0.0D, 0.0D,
                        0.0D
                );
            }
            player.getCooldowns().addCooldown(itemstack.getItem(), 40);
            itemstack.getOrCreateTag().putDouble("sword_skill", itemstack.getOrCreateTag().getDouble("sword_skill") - 1.0D);
        }
        return interactionresultholder;
    }
}

