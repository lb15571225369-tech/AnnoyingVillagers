package com.pla.annoyingvillagers.block;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.pla.annoyingvillagers.blockentity.ShadowObsidianBlockEntity;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.skill.ObsidianWeaponSkill;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.ForgeSoundType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.StunType;

public class ShadowObsidianBlock extends Block implements EntityBlock {
    public static final BooleanProperty FROM_PLAYER = BooleanProperty.create("from_player");
    public static final IntegerProperty REPLACE_BY_LIQUID = IntegerProperty.create("replace_by_liquid", 0, 2);

    public ShadowObsidianBlock() {
        super(Properties.of()
                .sound(new ForgeSoundType(1.0F, 1.0F,
                        AnnoyingVillagersModSounds.LOST,
                        () -> SoundEvents.DEEPSLATE_STEP,
                        () -> SoundEvents.DEEPSLATE_PLACE,
                        () -> SoundEvents.STONE_HIT,
                        AnnoyingVillagersModSounds.SILENT
                ))
                .strength(30.0F, 40.0F)
                .lightLevel((blockstate) -> 4)
                .noOcclusion()
                .hasPostProcess((blockstate, blockgetter, blockpos) -> true)
                .emissiveRendering((blockstate, blockgetter, blockpos) -> true)
                .isRedstoneConductor((blockstate, blockgetter, blockpos) -> false));
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FROM_PLAYER, Boolean.FALSE)
                        .setValue(REPLACE_BY_LIQUID, 0)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FROM_PLAYER, REPLACE_BY_LIQUID);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
        BlockState base = super.getStateForPlacement(blockPlaceContext);
        if (base == null) base = this.defaultBlockState();
        return base.setValue(FROM_PLAYER, blockPlaceContext.getPlayer() != null);
    }

    public void appendHoverText(@NotNull ItemStack itemstack, BlockGetter blockgetter, @NotNull List<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, blockgetter, list, tooltipflag);
        list.add(Component.translatable("tooltip.annoyingvillagers.shadow_obsidian"));
    }

    public int getLightBlock(@NotNull BlockState blockstate, @NotNull BlockGetter blockgetter, @NotNull BlockPos blockpos) {
        return 15;
    }

    public @NotNull VoxelShape getVisualShape(@NotNull BlockState blockstate, @NotNull BlockGetter blockgetter, @NotNull BlockPos blockpos, @NotNull CollisionContext collisioncontext) {
        return Shapes.empty();
    }

    public @NotNull VoxelShape getShape(@NotNull BlockState blockstate, @NotNull BlockGetter blockgetter, @NotNull BlockPos blockpos, @NotNull CollisionContext collisioncontext) {
        return box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 17.0D);
    }

    public @NotNull List<ItemStack> getDrops(@NotNull BlockState blockstate, LootParams.@NotNull Builder builder) {
        List<ItemStack> list = super.getDrops(blockstate, builder);

        return !list.isEmpty() ? list : Collections.singletonList(new ItemStack(this, 1));
    }

    @Override
    public void tick(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
        super.tick(blockState, serverLevel, blockPos, randomSource);
        serverLevel.playSound(
                null,
                blockPos.getX(), blockPos.getY(), blockPos.getZ(),
                SoundEvents.DEEPSLATE_BREAK,
                SoundSource.BLOCKS,
                1.0F, 1.0F
        );
        BlockState current = serverLevel.getBlockState(blockPos);
        int replace = 0;
        if (current.getBlock() instanceof ShadowObsidianBlock && current.hasProperty(ShadowObsidianBlock.REPLACE_BY_LIQUID)) {
            replace = current.getValue(ShadowObsidianBlock.REPLACE_BY_LIQUID);
        }
        BlockState replacement = switch (replace) {
            case 1 -> Blocks.WATER.defaultBlockState();
            case 2 -> Blocks.LAVA.defaultBlockState();
            default -> Blocks.AIR.defaultBlockState();
        };
        serverLevel.setBlock(blockPos, replacement, 3);
    }

    public void onPlace(@NotNull BlockState blockstate, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState, boolean flag) {
        super.onPlace(blockstate, level, blockPos, blockState, flag);
        level.scheduleTick(blockPos, this, 25);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.playSound(
                    null,
                    blockPos.getX(), blockPos.getY(), blockPos.getZ(),
                    SoundEvents.DEEPSLATE_PLACE,
                    SoundSource.BLOCKS,
                    new Random().nextFloat(0.0F, 0.7F), 1.0F
            );
            serverLevel.playSound(
                    null,
                    blockPos.getX(), blockPos.getY(), blockPos.getZ(),
                    AnnoyingVillagersModSounds.OB_PLACE.get(),
                    SoundSource.BLOCKS,
                    new Random().nextFloat(0.2F, 0.6F), 1.0F
            );
            new DelayedTask(new Random().nextInt(5, 15)) {
                @Override
                public void run() {
                    serverLevel.playSound(
                            null,
                            blockPos.getX(), blockPos.getY(), blockPos.getZ(),
                            AnnoyingVillagersModSounds.POP.get(),
                            SoundSource.BLOCKS,
                            1.0F, 1.0F
                    );
                }
            };
        }
    }

    public void increaseSkillPoint(Entity entity, float value) {
        if (!(entity instanceof Player pEntity)) return;

        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(pEntity, PlayerPatch.class);
        if (!(playerPatch instanceof ServerPlayerPatch serverPlayerPatch)) return;

        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.OBSIDIAN_WEAPON);
        if (skillContainer == null) return;

        ObsidianWeaponSkill skill = (ObsidianWeaponSkill) skillContainer.getSkill();

        float currentResource = skillContainer.getResource();
        float neededResource = skillContainer.getNeededResource();
        float addResource = Math.min(value, neededResource);

        skill.setConsumptionSynchronize(skillContainer, currentResource + addResource);
    }

    public void entityInside(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull Entity entity) {
        super.entityInside(blockState, level, blockPos, entity);
        if (entity.tickCount % 5 == 0) {
            if (entity instanceof Player && level.getBlockEntity(blockPos) instanceof ShadowObsidianBlockEntity shadowObsidianBlockEntity) {
                UUID owner = shadowObsidianBlockEntity.getOwner();
                if (owner != null && owner.equals(entity.getUUID())) {
                    return;
                }
            }

            boolean fromPlayer =
                    blockState.getBlock() instanceof ObsidianBlock
                            && blockState.hasProperty(ObsidianBlock.FROM_PLAYER)
                            && blockState.getValue(ObsidianBlock.FROM_PLAYER);

            if (!fromPlayer && HerobrineUtil.isHerobrineFaction(entity)) {
                return;
            }

            if (entity instanceof Player && fromPlayer && level.getServer() != null && !level.getServer().isPvpAllowed()) {
                return;
            }

            if (entity.level() instanceof ServerLevel serverLevel) {
                EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument(serverLevel, HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, entity, entity);
                serverLevel.playSound(
                        null,
                        blockPos.getX(), blockPos.getY(), blockPos.getZ(),
                        AnnoyingVillagersModSounds.OBSIDIAN_HIT.get(),
                        SoundSource.BLOCKS,
                        1.0F, 1.0F
                );
                entity.hurt(entity.level().damageSources().generic(), 1.0F);
                if (level.getBlockEntity(blockPos) instanceof ShadowObsidianBlockEntity shadowObsidianBlockEntity) {
                    UUID owner = shadowObsidianBlockEntity.getOwner();
                    if (owner != null && serverLevel.getEntity(owner) instanceof Player player) {
                        increaseSkillPoint(player, 1.0F);
                    }
                }
                entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * -2.0D, 0.4D, entity.getLookAngle().z * -2.0D));
                if (Math.random() <= 0.5D) {
                    new DelayedTask(1) {
                        @Override
                        public void run() {
                            if (entity.level() instanceof ServerLevel) {
                                LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                if (livingEntityPatch != null) {
                                    livingEntityPatch.applyStun(StunType.LONG, 20.0F);
                                }
                            }
                        }
                    };

                    if (Math.random() <= 0.3D) {
                        new DelayedTask(1) {
                            @Override
                            public void run() {
                                if (entity.level() instanceof ServerLevel) {
                                    LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                    if (livingEntityPatch != null) {
                                        livingEntityPatch.applyStun(StunType.KNOCKDOWN, 20.0F);
                                    }
                                }
                            }
                        };
                    }
                }
            }
        }
    }

    @Override
    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, LivingEntity placer, @NotNull ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide) {
            var blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ShadowObsidianBlockEntity shadowObsidianBlockEntity) {
                shadowObsidianBlockEntity.setOwner(placer instanceof Player ? placer.getUUID() : null);
                blockEntity.setChanged();
                level.sendBlockUpdated(pos, state, state, 3);
            }
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new ShadowObsidianBlockEntity(pPos, pState);
    }
}
