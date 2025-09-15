package com.pla.annoyingvillagers.item;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class BedrockWeaponItem extends SwordItem {
    public BedrockWeaponItem() {
        super(new Tier() {
            public int getUses() {
                return 3000;
            }

            public float getSpeed() {
                return 50.0F;
            }

            public float getAttackDamageBonus() {
                return 10.0F;
            }

            public int getLevel() {
                return 4;
            }

            public int getEnchantmentValue() {
                return 0;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[]{new ItemStack(Blocks.BEDROCK)});
            }
        }, 3, 0.5F, (new Properties()).fireResistant());
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (Math.random() <= 0.2D) {
            if (!pTarget.level().isClientSide()) {
                pTarget.level().playSound(null, new BlockPos((int) pTarget.getX(), (int) pTarget.getY(), (int) pTarget.getZ()), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "obsidian_hit")), SoundSource.BLOCKS, 1.0F, (float) (0.5 + Math.random() * 0.5));
            } else {
                pTarget.level().playLocalSound(pTarget.getX(), pTarget.getY(), pTarget.getZ(), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "obsidian_hit")), SoundSource.BLOCKS, 1.0F, (float) (0.5 + Math.random() * 0.5), false);
            }
            if (!pTarget.level().isClientSide() && pTarget.getServer() != null) {
                try {
                    pTarget.getServer().getCommands().getDispatcher().execute(
                            "indestructible @s play \"epicfight:biped/combat/hit_long\" 0 10",
                            pTarget.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {

                }
            }
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.literal(
                "Forged from §7the hardest block§r known to exist.\n" +
                        "No one knows how §5Herobrine§r obtained it.\n" +
                        "Can mine anything and may stun enemies on hit."
        ));
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return 50.0F;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction action) {
        return action == ToolActions.PICKAXE_DIG
                || action == ToolActions.AXE_DIG
                || action == ToolActions.SHOVEL_DIG
                || action == ToolActions.HOE_DIG
                || action == ToolActions.SHEARS_DIG
                || action == ToolActions.SWORD_DIG;
    }
}
