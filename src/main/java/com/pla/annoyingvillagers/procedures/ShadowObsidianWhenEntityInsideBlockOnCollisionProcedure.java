package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.block.ShadowObsidianBlock;
import com.pla.annoyingvillagers.blockentity.ShadowObsidianBlockEntity;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.ObsidianWeaponUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class ShadowObsidianWhenEntityInsideBlockOnCollisionProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity) {
        if (entity != null) {
            BlockPos pos = BlockPos.containing(d0, d1, d2);
            BlockState state = levelaccessor.getBlockState(pos);

            if (entity instanceof Player && levelaccessor.getBlockEntity(pos) instanceof ShadowObsidianBlockEntity shadowObsidianBlockEntity) {
                var owner = shadowObsidianBlockEntity.getOwner();
                if (owner != null && owner.equals(((Player) entity).getUUID())) {
                    return;
                }
            }

            boolean fromPlayer =
                    state.getBlock() instanceof ShadowObsidianBlock
                            && state.hasProperty(ShadowObsidianBlock.FROM_PLAYER)
                            && state.getValue(ShadowObsidianBlock.FROM_PLAYER);

            if (!fromPlayer && ObsidianWeaponUtil.isHerobrineFaction(entity)) {
                return;
            }

            if (entity instanceof Player && fromPlayer && levelaccessor.getServer() != null && !levelaccessor.getServer().isPvpAllowed()) {
                return;
            }

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "execute at @s run particle epicfight:hit_blunt ^ ^1.5 ^0.8 0.1 0.1 0.1 1 1",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {

                }
            }

            Level level;

            if (levelaccessor instanceof Level) {
                level = (Level) levelaccessor;
                if (!level.isClientSide()) {
                    level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "obsidian_hit")), SoundSource.BLOCKS, 1.0F, (float) (0.5 + Math.random() * 0.5));
                } else {
                    level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "obsidian_hit")), SoundSource.BLOCKS, 1.0F, (float) (0.5 + Math.random() * 0.5), false);
                }
            }

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                entity.hurt(entity.level().damageSources().generic(), 1.0F);
                entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * -2.0D, 0.4D, entity.getLookAngle().z * -2.0D));
            }
            if (Math.random() <= 0.5D) {
                new DelayedTask(1) {
                    @Override
                    public void run() {
                        Entity entity1 = entity;

                        if (!entity1.level().isClientSide() && entity1.getServer() != null) {
                            LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity1, LivingEntityPatch.class);
                            if (livingEntityPatch != null) {
                                livingEntityPatch.playAnimationSynchronized(Animations.BIPED_HIT_LONG, 0.0F);
                            }
                        }
                    }
                };

                if (Math.random() <= 0.3D) {
                    new DelayedTask(1) {
                        @Override
                        public void run() {
                            Entity entity1 = entity;

                            if (!entity1.level().isClientSide() && entity1.getServer() != null) {
                                if (!entity1.level().isClientSide() && entity1.getServer() != null) {
                                    LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity1, LivingEntityPatch.class);
                                    if (livingEntityPatch != null) {
                                        livingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN, 0.0F);
                                    }
                                }
                            }
                        }
                    };
                }
            }
        }
    }
}
