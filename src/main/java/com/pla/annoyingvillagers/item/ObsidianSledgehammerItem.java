package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.entity.ObsidianSledgehammerHitEntity;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.procedures.HerobrineWeaponEffectProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.List;

public class ObsidianSledgehammerItem extends AxeItem {

    public ObsidianSledgehammerItem() {
        super(new Tier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 4.0F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 32;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 1.0F, -2.6F, (new Properties()).fireResistant());
    }

    public static void spawnObsidianSpike(double x, double z, double minY, double maxY, float rotation, int delay, Entity entity) {
        BlockPos blockpos = BlockPos.containing(x, maxY, z);
        boolean found = false;
        double topOffset = 0.0D;

        while (blockpos.getY() >= Mth.floor(minY) - 1) {
            BlockPos below = blockpos.below();
            BlockState belowState = entity.level().getBlockState(below);
            if (belowState.isFaceSturdy(entity.level(), below, Direction.UP)) {
                if (!entity.level().isEmptyBlock(blockpos)) {
                    VoxelShape vs = entity.level().getBlockState(blockpos).getCollisionShape(entity.level(), blockpos);
                    if (!vs.isEmpty()) topOffset = vs.max(Direction.Axis.Y);
                }
                found = true;
                break;
            }
            blockpos = blockpos.below();
        }

        if (found) {
            LivingEntity owner = (LivingEntity) entity;
            entity.level().addFreshEntity(
                    new ObsidianSledgehammerHitEntity(entity.level(),
                            x, blockpos.getY() + topOffset, z, rotation, delay, 3.0F, owner));
        }
    }

    public static void circleHit(Entity entity, BlockHitResult firstHit) {
        if (entity.level().isClientSide) return;

        Level level = entity.level();
        Vec3 firstHitLocation = firstHit.getLocation();

        BlockHitResult ground = level.clip(new ClipContext(
                firstHitLocation.add(0, 0.25, 0),
                firstHitLocation.subtract(0, 6.0, 0),
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                entity));

        if (ground.getType() != HitResult.Type.BLOCK) return;

        Vec3 groundLocation = ground.getLocation();

        double surfaceY = groundLocation.y;
        double maxY = surfaceY + 0.01;
        int standingOnY = Mth.floor(surfaceY) - 3;

        final double rMax = 5.5;
        final double spacing = 1.0;
        final double dr = spacing * Math.sqrt(3.0) / 2.0;

        spawnObsidianSpike(groundLocation.x, groundLocation.z, standingOnY, maxY, 0.0F, 0, entity);

        int ring = 1;
        for (double r = dr; r <= rMax + 1e-6; r += dr, ring++) {
            int n = Math.max(1, (int) Math.round((2.0 * Math.PI * r) / spacing));
            double offset = ((ring & 1) == 0) ? 0.0 : (Math.PI / n);

            for (int k = 0; k < n; ++k) {
                double theta = offset + k * (2.0 * Math.PI / n);
                double x = groundLocation.x + Math.cos(theta) * r;
                double z = groundLocation.z + Math.sin(theta) * r;
                spawnObsidianSpike(x, z, standingOnY, maxY, (float) theta, ring, entity);
            }
        }
    }

    public static Vec3 jointWorldPoint(LivingEntityPatch<?> patch, Vec3f localOffset, Joint joint) {
        Vec3 pos = patch.getOriginal().position();
        OpenMatrix4f model =
                patch.getArmature()
                        .getBoundTransformFor(patch.getAnimator().getPose(1.0F), joint)
                        .mulFront(OpenMatrix4f.createTranslation((float) pos.x, (float) pos.y, (float) pos.z)
                                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                                        .mulBack(patch.getModelMatrix(1.0F))));
        return OpenMatrix4f.transform(model, localOffset.toDoubleVector());
    }

    public static BlockHitResult raycastDown(Level level, Vec3 start, LivingEntityPatch<?> patch, double maxDist) {
        Vec3 end = start.subtract(0.0, maxDist, 0.0);
        BlockHitResult hit = level.clip(new ClipContext(
                start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, patch.getOriginal()));
        return hit.getType() == HitResult.Type.BLOCK ? hit : null;
    }

    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        if (flag && entity instanceof Player player) {
            PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
            if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.OBSIDIAN_SLEDGEHAMMER);
                if (skillContainer != null) {
                    if (skillContainer.getStack() >= 1) {
                        HerobrineWeaponEffectProcedure.execute(level, entity.getX(), entity.getY(), entity.getZ(), entity);
                    }
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.literal(Component.translatable("tooltip.annoyingvillagers.obsidian_sledgehammer").getString() + ")§r"));
    }
}
