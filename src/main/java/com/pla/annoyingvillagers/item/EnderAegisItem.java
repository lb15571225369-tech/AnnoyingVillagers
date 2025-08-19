package com.pla.annoyingvillagers.item;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.StealthAttackEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.procedures.HerobrineWeaponEffectProcedure;
import com.pla.annoyingvillagers.util.SnakeBladeHit;
import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class EnderAegisItem extends SwordItem {

    public EnderAegisItem() {
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

    public void shieldShoot(Level level, Entity entity) {
        if (!level.isClientSide()) {
            for(float i = 1.0F; i <= 5.0F; i = i + 1.0F) {
                StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level);
                stealthAttackEntity.setOwner(entity);
                stealthAttackEntity.setBaseDamage((double) 8.0F);
                stealthAttackEntity.setKnockback(10);
                stealthAttackEntity.setSilent(true);
                stealthAttackEntity.setPierceLevel((byte) 5);
                stealthAttackEntity.setPos(entity.getX(), entity.getEyeY() - 0.1D, entity.getZ());
                stealthAttackEntity.fromAegis = true;
                stealthAttackEntity.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, i, 0.0F);
                level.addFreshEntity(stealthAttackEntity);
            }

            try {
                entity.getServer().getCommands().getDispatcher().execute("execute at @s run particle annoyingvillagers:spark ^ ^1 ^2 0 0 0 0.1 2000",
                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {
            }
        }
        if (!level.isClientSide()) {
            level.playSound((Player) null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:cooldown")), SoundSource.NEUTRAL, 1.0F, 1.0F);
        } else {
            level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:cooldown")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
        }

        if (!level.isClientSide()) {
            level.playSound((Player) null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:ender_shot")), SoundSource.NEUTRAL, 1.0F, 1.0F);
        } else {
            level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:ender_shot")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
        }

        if (!level.isClientSide()) {
            level.playSound((Player) null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:bloom")), SoundSource.NEUTRAL, 1.0F, 1.0F);
        } else {
            level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:bloom")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (pStack.getTag().getBoolean("SecondForm")) {
            if (!pTarget.level().isClientSide()) {
                pTarget.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10, 2));
                pTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 2));
                pTarget.addEffect(new MobEffectInstance(AnnoyingVillagersModMobEffects.HEROBRINE.get(), 10, 2));
            }
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        if (flag) {
            if (itemstack.getTag().getBoolean("SecondForm")) {
                HerobrineWeaponEffectProcedure.execute(level, entity.getX(), entity.getY(), entity.getZ(), entity);
            } else if (itemstack.getTag().getInt("ParryCount") >= 5) {
                if (entity instanceof Player player) {
                    ItemCooldowns cooldowns = player.getCooldowns();
                    cooldowns.addCooldown(itemstack.getItem(), 200);
                    itemstack.getTag().remove("ParryCount");
                }
            }
            if (entity instanceof Player player) {
                float percent = player.getCooldowns().getCooldownPercent(itemstack.getItem(), 0);
                if (percent > 0.0F) {
                    if (!itemstack.getTag().getBoolean("SecondForm")) {
                        itemstack.getTag().putBoolean("SecondForm", true);
                        if (entity instanceof LivingEntity livingEntity) {
                            if (!livingEntity.level().isClientSide()) {
                                livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 2));
                                livingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, 2));
                                livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 2));
                            }
                        }

                        shieldShoot(level, player);
                    }
                } else {
                    if (itemstack.getTag().getBoolean("SecondForm")) {
                        itemstack.getTag().remove("SecondForm");
                    }
                }
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pPlayer.getMainHandItem().getTag().getBoolean("SecondForm")) {
//            shieldShoot(pLevel, pPlayer);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}