package com.pla.annoyingvillagers.item;

import java.util.List;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraftforge.registries.ForgeRegistries;

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
                return 6.0F;
            }

            public int getLevel() {
                return 3;
            }

            public int getEnchantmentValue() {
                return 10;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[]{new ItemStack(Items.DIAMOND)});
            }
        }, 3, -1.5F, (new Properties()));
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        InteractionResultHolder<ItemStack> interactionresultholder = super.use(level, player, interactionhand);

        ItemStack itemstack = interactionresultholder.getObject();
        double d0 = player.getX();
        double d1 = player.getY();
        double d2 = player.getZ();
        if (player.isShiftKeyDown()) {
            player.getCooldowns().addCooldown(itemstack.getItem(), 60);
            player.setDeltaMovement(new Vec3(0.0D, 1.5D, 0.0D));
            if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
                itemstack.hurtAndBreak(2, serverPlayer, p -> p.broadcastBreakEvent(interactionhand));
            }
            level.addParticle(ParticleTypes.EXPLOSION, 0, d1, d2, 0.0D, 1.0D, 0.0D);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.POOF, d0, d1, d2, 20, 0.0D, 0.0D, 0.0D, 1.0D);
            }

            if (!level.isClientSide()) {
                level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "wing")), SoundSource.NEUTRAL, 1.0F, 1.0F);
            } else {
                level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "wing")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
            }

            if (!level.isClientSide()) {
                level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "cooldown")), SoundSource.NEUTRAL, 1.0F, 1.0F);
            } else {
                level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "cooldown")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
            }
        }

        return interactionresultholder;
    }

    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
    }
}
