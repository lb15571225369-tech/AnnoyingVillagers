package com.pla.annoyingvillagers.item;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.procedures.HerobrineWeaponEffectProcedure;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.util.SnakeBladeHit;
import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

public class EnderGlaiveItem extends SwordItem {

    public EnderGlaiveItem() {
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
        }, 3, -2.5F, (new Properties().fireResistant()));
    }

    public static Vec3 getJointWithTranslation(Entity entity, Vec3f translation, Joint joint, float handToTip, double yOffset) {
        LivingEntityPatch entitypatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (entitypatch == null) return null;

        float interpolation = 0.0F;
        OpenMatrix4f m = entitypatch.getArmature()
                .getBindedTransformFor(entitypatch.getAnimator().getPose(interpolation), joint);

        if (translation != null) {
            OpenMatrix4f tLocal = new OpenMatrix4f().translate(translation);
            OpenMatrix4f.mul(m, tLocal, m);
        }

        if (handToTip != 0.0f) {
            OpenMatrix4f tipOffset = new OpenMatrix4f().translate(new Vec3f(0.0F, 0.0F, -handToTip));
            OpenMatrix4f.mul(m, tipOffset, m);
        }

        float yawRad = (float) -Math.toRadians(((LivingEntity) entitypatch.getOriginal()).yBodyRotO + 180.0F);
        OpenMatrix4f worldYaw = new OpenMatrix4f().rotate(yawRad, new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f.mul(worldYaw, m, m);

        LivingEntity base = (LivingEntity) entitypatch.getOriginal();
        return new Vec3(
                m.m30 + base.getX(),
                m.m31 + (base.getY() + (entity.getBbHeight() / 1.8) - 1.0) + yOffset,
                m.m32 + base.getZ()
        );
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (!pAttacker.level().isClientSide()) {
            itemstack.getTag().putInt("HitCount", (itemstack.getTag().contains("HitCount") ? itemstack.getTag().getInt("HitCount") : 0) + 1);
        }
        return super.hurtEnemy(itemstack, pTarget, pAttacker);
    }

    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        if (flag) {
            if (itemstack.getTag().getBoolean("SecondForm")) {
                HerobrineWeaponEffectProcedure.execute(level, entity.getX(), entity.getY(), entity.getZ(), entity);
                if (entity instanceof LivingEntity livingEntity) {
                    if (!livingEntity.level().isClientSide()) {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1, 2));
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1, 2));
                    }
                }
            }
        }
        if (!itemstack.getTag().getBoolean("SecondForm") && itemstack.getTag().getInt("HitCount") >= 5) {
            if (entity instanceof Player player) {
                ItemCooldowns cooldowns = player.getCooldowns();
                cooldowns.addCooldown(itemstack.getItem(), 200);
            }
        }
        if (entity instanceof Player player) {
            float percent = player.getCooldowns().getCooldownPercent(itemstack.getItem(), 0);
            if (percent > 0.0F) {
                if (!itemstack.getTag().getBoolean("SecondForm")) {
                    itemstack.getTag().putBoolean("SecondForm", true);

                    if (!player.level().isClientSide()) {
                        player.level().playSound((Player) null, new BlockPos((int) player.getX(), (int) player.getY(), (int) player.getZ()), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:second_form_release")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        player.level().playLocalSound(player.getX(), player.getY(), player.getZ(), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:second_form_release")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }

                    itemstack.getTag().putInt("HitCount", 3);
                }
            } else {
                if (itemstack.getTag().getBoolean("SecondForm")) {
                    itemstack.getTag().remove("SecondForm");
                    itemstack.getTag().putInt("HitCount", 0);
                }
            }
        }
    }

    String getCurrentComboAttack(ItemStack itemstack) {
        if (itemstack.getTag().getBoolean("SecondForm")) {
            return String.format("%d/3", itemstack.getTag().contains("HitCount") ? itemstack.getTag().getInt("HitCount") : 0);
        } else {
            return String.format("%d/5", itemstack.getTag().contains("HitCount") ? itemstack.getTag().getInt("HitCount") : 0);
        }
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.literal(Component.translatable("tooltip.annoyingvillagers.ender_glaive").getString() + getCurrentComboAttack(itemstack) + ")§r"));
    }
}
