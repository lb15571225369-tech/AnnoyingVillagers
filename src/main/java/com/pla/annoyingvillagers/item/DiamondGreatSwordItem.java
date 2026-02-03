package com.pla.annoyingvillagers.item;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

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
                return Ingredient.of(new ItemStack[]{new ItemStack(Items.DIAMOND)});
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
            if (!player.level().isClientSide() && player.getServer() != null) {
                try {
                    player.getServer().getCommands().getDispatcher().execute("execute at @s run particle epicfight:air_burst ^ ^1.5 ^ 0 0 0 7 1", player.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {

                }
            }

            player.setDeltaMovement(new Vec3(0.0D, 1.0D, 0.0D));
            if (!level.isClientSide()) {
                level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.ender_dragon.flap")), SoundSource.NEUTRAL, 1.0F, 1.0F);
            } else {
                level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.ender_dragon.flap")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
            }

            level.addParticle(ParticleTypes.EXPLOSION, d0, d1, d2, 0.0D, 1.0D, 0.0D);
            player.getCooldowns().addCooldown(itemstack.getItem(), 40);
            itemstack.getOrCreateTag().putDouble("sword_skill", itemstack.getOrCreateTag().getDouble("sword_skill") - 1.0D);
        }
        return interactionresultholder;
    }
}

