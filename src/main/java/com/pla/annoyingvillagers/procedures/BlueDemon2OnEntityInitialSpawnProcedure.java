package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.BbqEntity;
import com.pla.annoyingvillagers.entity.BlueDemon2Entity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ServerLevelAccessor;

public class BlueDemon2OnEntityInitialSpawnProcedure {

    public static void execute(ServerLevelAccessor levelaccessor, Entity entity) {
        if (entity != null) {
            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute("item replace entity @s weapon.mainhand with annoyingvillagers:legendary_sword_mob", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                    
                }
            }

            ItemStack itemstack;

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 10);

            if (levelaccessor instanceof ServerLevel) {
                ServerLevel serverlevel = (ServerLevel) levelaccessor;

                BbqEntity bbqEntity = new BbqEntity((EntityType) AnnoyingVillagersModEntities.BBQ.get(), serverlevel);
                bbqEntity.moveTo(entity.getX() + Mth.nextDouble(AnnoyingVillagers.randomSource, 1.0D, 10.0D), entity.getY() + Mth.nextDouble(AnnoyingVillagers.randomSource, 1.0D, 10.0D), entity.getZ() + Mth.nextDouble(AnnoyingVillagers.randomSource, 1.0D, 10.0D), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                bbqEntity.setFollowTarget(entity);
                bbqEntity.setFollowTargetUUID(entity.getUUID());
                bbqEntity.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(bbqEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);

                levelaccessor.addFreshEntity(bbqEntity);
                ((BlueDemon2Entity) entity).setProtectingBbq(bbqEntity);
                ((BlueDemon2Entity) entity).setBbqUUID(bbqEntity.getUUID());
            }

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team add blue_demon",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team modify blue_demon friendlyFire false",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team join blue_demon @s",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
            }
        }
    }
}
