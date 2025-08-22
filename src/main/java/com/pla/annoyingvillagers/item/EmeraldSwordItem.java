package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.compat.aaa_particles.DragonBeamParticleEmitterInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class EmeraldSwordItem extends SwordItem {
    private static final DragonBeamParticleEmitterInfo LIGHTNING = new DragonBeamParticleEmitterInfo(new ResourceLocation(AnnoyingVillagers.MODID, "Laser01"));

    public EmeraldSwordItem() {
        super(new Tier() {
            public int getUses() {
                return 1680;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 5.4F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 18;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[]{new ItemStack(Items.EMERALD)});
            }
        }, 3, -1.5F, (new Properties()));
    }
}
