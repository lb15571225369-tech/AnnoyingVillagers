package com.pla.annoyingvillagers.item;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.StealthAttackEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.procedures.HerobrineWeaponEffectProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Objects;
import java.util.Random;

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
                return 8.0F;
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
                stealthAttackEntity.setKnockback(5);
                stealthAttackEntity.setSilent(true);
                stealthAttackEntity.setPierceLevel((byte) 5);
                stealthAttackEntity.setPos(entity.getX(), entity.getEyeY() - 0.1D, entity.getZ());
                stealthAttackEntity.fromAegis = true;
                stealthAttackEntity.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, i, 0.0F);
                level.addFreshEntity(stealthAttackEntity);
            }

            try {
                entity.getServer().getCommands().getDispatcher().execute("execute as @s at @s anchored eyes run particle annoyingvillagers:spark ^ ^1 ^2 0 0 0 0.1 2000",
                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {
            }
        }
        if (!level.isClientSide()) {
            level.playSound((Player) null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "cooldown"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
        } else {
            level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "cooldown"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
        }

        if (!level.isClientSide()) {
            level.playSound((Player) null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_shot"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
        } else {
            level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_shot"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
        }

        if (!level.isClientSide()) {
            level.playSound((Player) null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "bloom"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
        } else {
            level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "bloom"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
        }
    }

    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        if (flag) {
            if (itemstack.getTag().getBoolean("SecondForm")) {
                HerobrineWeaponEffectProcedure.execute(level, entity.getX(), entity.getY(), entity.getZ(), entity);
            }
        }
    }

    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.literal(Component.translatable("tooltip.annoyingvillagers.ender_aegis").getString() + ")§r"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pPlayer.getMainHandItem().getTag().getBoolean("SecondForm")) {
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}