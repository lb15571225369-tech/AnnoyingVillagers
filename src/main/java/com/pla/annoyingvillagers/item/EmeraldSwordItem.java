package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.entity.DragonBeamEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class EmeraldSwordItem extends SwordItem {

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

    public static LivingEntity getNearestLivingEntity(Level level, Entity sourceEntity, double range) {
        AABB searchBox = sourceEntity.getBoundingBox().inflate(range);

        return level.getNearestEntity(
                level.getEntitiesOfClass(LivingEntity.class, searchBox, e -> e != sourceEntity && e.isAlive()),
                TargetingConditions.DEFAULT,
                (LivingEntity) sourceEntity,
                sourceEntity.getX(), sourceEntity.getY(), sourceEntity.getZ()
        );
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        LivingEntity target = getNearestLivingEntity(pPlayer.level(), pPlayer, 32.0D);
        if (target != null) {
            double ox = pPlayer.getX() + 0.8 * Math.sin(-pPlayer.getYRot() * Math.PI / 180.0);
            double oy = pPlayer.getY() + 1.0;
            double oz = pPlayer.getZ() + 0.8 * Math.cos(-pPlayer.getYRot() * Math.PI / 180.0);
            DragonBeamEntity beam = new DragonBeamEntity(
                    AnnoyingVillagersModEntities.DRAGON_BEAM.get(),
                    pPlayer.level(),
                    pPlayer,
                    target,
                    ox, oy, oz,
                    40, 2);
            pPlayer.level().addFreshEntity(beam);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
