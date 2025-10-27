package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.NullEntity;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;

public class NullOnHurtProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, final NullEntity nullEntity, Entity attacker) {
        if (nullEntity != null && attacker != null) {
            if (!nullEntity.getPersistentData().getBoolean("kick_x")) {
                RandomSource randomSource = RandomSource.create();
                LivingEntity target = nullEntity.getTarget();

                if (attacker == target) {
                    if (Math.random() <= 0.2D && nullEntity.isAvailableWitherSkeletonSlot() && levelaccessor instanceof ServerLevel) {
                        ServerLevel serverlevel = (ServerLevel)levelaccessor;
                        WitherSkeleton witherskeleton = new WitherSkeleton(EntityType.WITHER_SKELETON, serverlevel);
                        witherskeleton.moveTo(d0 + (double)Mth.nextInt(randomSource, 1, 10), d1 + (double)Mth.nextInt(randomSource, 5, 10), d2 + (double)Mth.nextInt(randomSource, 1, 10), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                        witherskeleton.getPersistentData().putUUID("SpawnByNull", nullEntity.getUUID());
                        witherskeleton.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(witherskeleton.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                        levelaccessor.addFreshEntity(witherskeleton);
                        nullEntity.claimWitherSkeletonSlot(witherskeleton);
                        if (!witherskeleton.level().isClientSide() && witherskeleton.getServer() != null) {
                            try {
                                witherskeleton.getServer().getCommands().getDispatcher().execute(
                                        "execute as @s at @s run particle minecraft:reverse:portal ~ ~0.8 ~ 0.2 0.2 0.2 0.07 50 force",
                                        witherskeleton.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {

                            }
                        }
                        if (!witherskeleton.level().isClientSide() && witherskeleton.getServer() != null) {
                            try {
                                witherskeleton.getServer().getCommands().getDispatcher().execute(
                                        "team join herobrine @s",
                                        witherskeleton.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {

                            }
                        }
                        ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
                        if (Math.random() <= 0.25) {
                            sword.enchant(Enchantments.FIRE_ASPECT, 1);
                        } else if (Math.random() <= 0.25) {
                            sword.enchant(Enchantments.UNBREAKING, 1);
                        } else if (Math.random() <= 0.25) {
                            sword.enchant(Enchantments.KNOCKBACK, 1);
                        } else {
                            sword.enchant(Enchantments.SHARPNESS, 1);
                        }
                        witherskeleton.setItemSlot(EquipmentSlot.MAINHAND, sword);
                        ItemStack helmet = new ItemStack(Items.DIAMOND_HELMET);
                        if (Math.random() <= 0.25) {
                            helmet.enchant(Enchantments.THORNS, 1);
                        } else if (Math.random() <= 0.25) {
                            helmet.enchant(Enchantments.UNBREAKING, 1);
                        } else {
                            helmet.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 1);
                        }
                        witherskeleton.setItemSlot(EquipmentSlot.HEAD, helmet);
                    }

                    nullEntity.setSprinting(true);
                    new DelayedTask(10) {
                        @Override
                        public void run() {
                            nullEntity.setSprinting(false);
                        }
                    };
                }
            }

        }
    }
}
