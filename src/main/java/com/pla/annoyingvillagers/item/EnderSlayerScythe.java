package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.procedures.HerobrineWeaponEffectProcedure;
import com.pla.annoyingvillagers.util.SnakeBladeHit;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class EnderSlayerScythe extends SwordItem {

    public EnderSlayerScythe() {
        super(new Tier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 14.0F;
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
        }, 3, -2.3F, (new Properties()).fireResistant());
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (!itemstack.getTag().getBoolean("SecondForm")) {
            itemstack.getTag().putInt("HitCount", (itemstack.getTag().contains("HitCount") ? itemstack.getTag().getInt("HitCount") : 0) + 1);
        }
        return super.hurtEnemy(itemstack, pTarget, pAttacker);
    }

    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        if (flag && itemstack.getTag().getBoolean("SecondForm")) {
            HerobrineWeaponEffectProcedure.execute(level, entity.getX(), entity.getY(), entity.getZ(), entity);
        }
        if (!itemstack.getTag().getBoolean("SecondForm") && itemstack.getTag().getInt("HitCount") >= 5) {
            if (entity instanceof Player player) {
                ItemCooldowns cooldowns = player.getCooldowns();
                cooldowns.addCooldown(itemstack.getItem(), 600);
                itemstack.getTag().remove("HitCount");
            }
        }
        if (entity instanceof Player player) {
            float percent = player.getCooldowns().getCooldownPercent(itemstack.getItem(), 0);
            if (percent > 0.0F) {
                if (!itemstack.getTag().getBoolean("SecondForm")) {
                    itemstack.getTag().putBoolean("SecondForm", true);
                    if (entity instanceof LivingEntity livingEntity) {
                        if (!livingEntity.level().isClientSide()) {
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 2));
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.JUMP, 200, 2));
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 2));
                        }
                    }

                    if (!player.level().isClientSide()) {
                        player.level().playSound((Player) null, new BlockPos((int) player.getX(), (int) player.getY(), (int) player.getZ()), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:second_form_release")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        player.level().playLocalSound(player.getX(), player.getY(), player.getZ(), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:second_form_release")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }


                }
            } else {
                if (itemstack.getTag().getBoolean("SecondForm")) {
                    itemstack.getTag().remove("SecondForm");
                }
            }
        }
    }
//
//    @Override
//    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
//        if (pPlayer.isSprinting()) {
//
//        }
//        return super.use(pLevel, pPlayer, pUsedHand);
//    }
}
