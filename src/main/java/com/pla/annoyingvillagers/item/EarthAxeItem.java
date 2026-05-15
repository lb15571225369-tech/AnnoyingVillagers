package com.pla.annoyingvillagers.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import com.pla.annoyingvillagers.entity.RisingWallBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class EarthAxeItem extends SwordItem {
    private static final int WALL_WIDTH = 5;
    private static final int WALL_HEIGHT = 4;
    private static final int WALL_DISTANCE = 2;
    private static final int RISE_TICKS = 10;

    public EarthAxeItem() {
        super(new Tier() {
            public int getUses() {
                return 2031;
            }

            public float getSpeed() {
                return 6.0F;
            }

            public float getAttackDamageBonus() {
                return 6.0F;
            }

            public int getLevel() {
                return 5;
            }

            public int getEnchantmentValue() {
                return 21;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Items.NETHERITE_INGOT));
            }
        }, 3, -3.0F, (new Properties()));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel instanceof ServerLevel serverLevel) {
            summonEarthWall(serverLevel, pPlayer);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public static void summonEarthWall(ServerLevel level, LivingEntity caster) {
        if (caster.isInWater()) {
            return;
        }

        BlockPos groundPos = caster.getOnPos();
        BlockState groundState = level.getBlockState(groundPos);

        if (groundState.getFluidState().is(FluidTags.WATER)) {
            return;
        }

        BlockState wallState = chooseWallBlock(groundState);

        if (wallState == null) {
            return;
        }

        Direction forward = caster.getDirection();
        Direction right = forward.getClockWise();

        BlockPos casterFeet = caster.blockPosition();

        BlockPos centerBase = new BlockPos(
                casterFeet.getX(),
                groundPos.getY() + 1,
                casterFeet.getZ()
        ).relative(forward, WALL_DISTANCE);

        int halfWidth = WALL_WIDTH / 2;
        boolean spawnedAny = false;

        for (int y = 0; y < WALL_HEIGHT; y++) {
            for (int x = -halfWidth; x <= halfWidth; x++) {
                BlockPos finalPos = centerBase.relative(right, x).above(y);

                if (!canSpawnWallBlockAt(level, finalPos)) {
                    continue;
                }

                int delay = y * 3 + Math.abs(x) * 2;

                RisingWallBlockEntity blockEntity = new RisingWallBlockEntity(
                        level,
                        finalPos,
                        wallState,
                        delay,
                        RISE_TICKS
                );

                level.addFreshEntity(blockEntity);
                spawnedAny = true;
            }
        }

    }

    private static boolean canSpawnWallBlockAt(ServerLevel level, BlockPos pos) {
        if (!level.getFluidState(pos).isEmpty()) {
            return false;
        }

        BlockState current = level.getBlockState(pos);
        return current.isAir() || current.canBeReplaced();
    }

    @Nullable
    private static BlockState chooseWallBlock(BlockState groundState) {
        if (groundState.is(BlockTags.SAND)
                || groundState.is(Blocks.SAND)
                || groundState.is(Blocks.RED_SAND)
                || groundState.is(Blocks.SANDSTONE)
                || groundState.is(Blocks.RED_SANDSTONE)
                || groundState.is(Blocks.SMOOTH_SANDSTONE)
                || groundState.is(Blocks.SMOOTH_RED_SANDSTONE)) {
            return Blocks.SAND.defaultBlockState();
        }

        if (groundState.is(BlockTags.DIRT)
                || groundState.is(Blocks.GRASS_BLOCK)
                || groundState.is(Blocks.DIRT)
                || groundState.is(Blocks.COARSE_DIRT)
                || groundState.is(Blocks.ROOTED_DIRT)
                || groundState.is(Blocks.PODZOL)
                || groundState.is(Blocks.MYCELIUM)
                || groundState.is(Blocks.FARMLAND)
                || groundState.is(Blocks.MUD)) {
            return Blocks.DIRT.defaultBlockState();
        }

        if (isWoodRelated(groundState)) {
            return Blocks.DIRT.defaultBlockState();
        }

        if (isStoneRelated(groundState)
                || groundState.is(Blocks.GRAVEL)) {
            return Blocks.GRAVEL.defaultBlockState();
        }

        return null;
    }

    private static boolean isWoodRelated(BlockState state) {
        return state.is(BlockTags.LOGS)
                || state.is(BlockTags.PLANKS)
                || state.is(BlockTags.WOODEN_STAIRS)
                || state.is(BlockTags.WOODEN_SLABS)
                || state.is(BlockTags.WOODEN_FENCES)
                || state.is(BlockTags.WOODEN_DOORS)
                || state.is(BlockTags.WOODEN_TRAPDOORS)
                || state.is(BlockTags.WOODEN_PRESSURE_PLATES)
                || state.is(BlockTags.WOODEN_BUTTONS);
    }

    private static boolean isStoneRelated(BlockState state) {
        return state.is(BlockTags.BASE_STONE_OVERWORLD)
                || state.is(BlockTags.BASE_STONE_NETHER)
                || state.is(BlockTags.STONE_BRICKS)
                || state.is(Blocks.STONE)
                || state.is(Blocks.COBBLESTONE)
                || state.is(Blocks.MOSSY_COBBLESTONE)
                || state.is(Blocks.DEEPSLATE)
                || state.is(Blocks.COBBLED_DEEPSLATE)
                || state.is(Blocks.BLACKSTONE)
                || state.is(Blocks.BASALT)
                || state.is(Blocks.SMOOTH_BASALT)
                || state.is(Blocks.NETHERRACK)
                || state.is(Blocks.END_STONE);
    }
}
