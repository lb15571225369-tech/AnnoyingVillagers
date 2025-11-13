package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.entity.BlueDemon2Entity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.spawnhandler.BluedemonData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;
import com.pla.annoyingvillagers.util.DelayedTask;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class BlueDemonTridentFsSkillEndOnEntityInitialSpawnProcedure {

    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) return;

        if (entity instanceof LivingEntity living) {
            ItemStack chest = living.getItemBySlot(EquipmentSlot.CHEST);
            chest.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
        }

        // Run commands
        if (!entity.level().isClientSide() && entity.getServer() != null) {
            String[] commands = new String[] {
                    "item replace entity @s weapon.mainhand with annoyingvillagers:bluedemontrident",
                    "item replace entity @s weapon.offhand with annoyingvillagers:bluedemontrident",
                    "effect give @s annoyingvillagers:captive 20000 0 true"
            };

            for (String cmd : commands) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            cmd,
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                    );
                } catch (CommandSyntaxException e) {
                    
                }
            }
        }

        LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

        if (livingEntityPatch != null) {
            livingEntityPatch.playAnimationSynchronized(AVAnimations.BLUE_DEMON_END_SKILL, 0.0F);
        }

        new DelayedTask(100) {
            @Override
            public void run() {
                if (!entity.isAlive()) return;

                if (world instanceof ServerLevel serverLevel) {
                    BlueDemon2Entity blueDemon2Entity = new BlueDemon2Entity((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON_2.get(), serverLevel);
                    blueDemon2Entity.moveTo(entity.getX(), entity.getY(), entity.getZ());

                    entity.discard();

                    BluedemonData bluedemonData = BluedemonData.get(serverLevel);
                    bluedemonData.forceClaim(serverLevel, blueDemon2Entity.getUUID());
                    blueDemon2Entity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(blueDemon2Entity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                    serverLevel.addFreshEntity(blueDemon2Entity);
                }
            }
        };
    }
}
