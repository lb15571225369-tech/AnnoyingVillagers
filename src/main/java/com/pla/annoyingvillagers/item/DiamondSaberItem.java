package com.pla.annoyingvillagers.item;

import java.util.List;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class DiamondSaberItem extends SwordItem {

    public DiamondSaberItem() {
        super(new Tier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 8.0F;
            }

            public float getAttackDamageBonus() {
                return 4.0F;
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
        }, 3, -1.5F, (new Properties()));
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand interactionhand) {
        InteractionResultHolder<ItemStack> interactionresultholder = super.use(level, player, interactionhand);

        ItemStack itemstack = interactionresultholder.getObject();
        double d0 = player.getX();
        double d1 = player.getY();
        double d2 = player.getZ();
        if (player.isShiftKeyDown()) {
            player.getCooldowns().addCooldown(itemstack.getItem(), 60);
            player.setDeltaMovement(new Vec3(0.0D, 1.5D, 0.0D));
            if (level instanceof ServerLevel serverLevel) {
                if (player instanceof ServerPlayer serverPlayer) {
                    itemstack.hurtAndBreak(2, serverPlayer, p -> p.broadcastBreakEvent(interactionhand));
                }
                serverLevel.sendParticles(
                        ParticleTypes.EXPLOSION,
                        d0, d1, d2,
                        1,
                        0.0D, 0.0D, 0.0D,
                        0.0
                );

                serverLevel.sendParticles(
                        ParticleTypes.POOF,
                        d0, d1, d2,
                        20,
                        0.0D, 0.0D, 0.0D,
                        1.0D
                );

                serverLevel.playSound(
                        null,
                        BlockPos.containing(d0, d1, d2),
                        AnnoyingVillagersModSounds.WING.get(),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F
                );

                serverLevel.playSound(
                        null,
                        BlockPos.containing(d0, d1, d2),
                        AnnoyingVillagersModSounds.COOL_DOWN.get(),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F
                );
            }
        }

        return interactionresultholder;
    }

    public void appendHoverText(@NotNull ItemStack itemstack, Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
    }
}
