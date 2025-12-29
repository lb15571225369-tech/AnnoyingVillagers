package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.clazz.PathfinderMobInventory;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.util.EscapeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.shelmarow.combat_evolution.execution.ExecutionHandler;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.Objects;
import java.util.Random;
import java.util.function.BiFunction;

public class CombatCommon {
    public static boolean canPerformNormalAttackLogic(MobPatch<?> mobpatch) {
        LivingEntity attacker = mobpatch.getOriginal();
        LivingEntity victim = mobpatch.getOriginal().getTarget();
        if (victim != null) {
            return ExecutionHandler.isExecutingTarget(attacker, victim);
        }
        return false;
    }

    public static boolean canJump(MobPatch<?> mobpatch) {
        return mobpatch.getOriginal().onGround() && !mobpatch.getOriginal().isPassenger();
    }

    public static boolean canAttackWhileNotHealing(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof PlayerNpcEntity playerNpcEntity) {
            return !playerNpcEntity.isHealing();
        }
        if (mobpatch.getOriginal() instanceof PathfinderMobInventory pathfinderMobInventory) {
            return !pathfinderMobInventory.isHealing();
        }
        return false;
    }

    public static boolean canEscape(MobPatch<?> mobpatch) {
        Mob entity = mobpatch.getOriginal();
        if (EscapeUtil.checkEscape(entity)) {
            if (entity instanceof PathfinderMobInventory pathfinderMobInventory
                    && new Random().nextDouble() <= pathfinderMobInventory.getPlaceBlockToParryChance()) {
                return true;
            } else if (entity instanceof PlayerNpcEntity playerNpcEntity
                    && new Random().nextDouble() <= playerNpcEntity.getPlaceBlockToParryChance()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isWrongWeapon(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        return !canEscape(mobpatch) && entity instanceof LivingEntity livingEntity
                && !(livingEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof SwordItem
                || livingEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof AxeItem
                || livingEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof BowItem);
    }

    public static boolean canPerformEating(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof PlayerNpcEntity playerNpcEntity) {
            if (playerNpcEntity.getGapCooldown() > 0) {
                return false;
            }
            return !playerNpcEntity.isHealing();
        }
        if (mobpatch.getOriginal() instanceof PathfinderMobInventory pathfinderMobInventory) {
            if (pathfinderMobInventory.getGapCooldown() > 0) {
                return false;
            }
            return !pathfinderMobInventory.isHealing();
        }
        return false;
    }

    public static boolean canPerformGuarding(MobPatch<?> mobpatch) {
        if (canEscape(mobpatch)) return false;
        if (mobpatch.getOriginal() instanceof PlayerNpcEntity playerNpcEntity) {
            return !playerNpcEntity.isHealing();
        }
        if (mobpatch.getOriginal() instanceof PathfinderMobInventory pathfinderMobInventory) {
            return !pathfinderMobInventory.isHealing();
        }
        return false;
    }

    public static boolean canThrowEnderPearl(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal().isPassenger()) return false;

        LivingEntity target = mobpatch.getOriginal().getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }

        if (mobpatch.getOriginal() instanceof PlayerNpcEntity playerNpcEntity) {
            if (playerNpcEntity.isHealing()) {
                return false;
            }
            return playerNpcEntity.getEnderPearlCooldown() == 0;
        }
        if (mobpatch.getOriginal() instanceof PathfinderMobInventory pathfinderMobInventory) {
            if (pathfinderMobInventory.isHealing()) {
                return false;
            }
            return pathfinderMobInventory.getEnderPearlCooldown() == 0;
        }
        return false;
    }

    public static boolean canSwapToBow(MobPatch<?> mobpatch) {
        LivingEntity target = mobpatch.getOriginal().getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }

        if (mobpatch.getOriginal() instanceof PlayerNpcEntity playerNpcEntity) {
            return playerNpcEntity.isUseBow() && playerNpcEntity.getSwapToBowCooldown() == 0;
        }

        if (mobpatch.getOriginal() instanceof PathfinderMobInventory pathfinderMobInventory) {
            if ((pathfinderMobInventory instanceof SteveEntity || pathfinderMobInventory instanceof AngrySteveEntity
                    || pathfinderMobInventory instanceof AlexEntity || pathfinderMobInventory instanceof ChrisEntity)) {
                if (target instanceof HerobrineMob) {
                    return false;
                }
                ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(target.getType());
                if (key.getNamespace().equals("torchesbecomesunlight")
                        && (key.getPath().equals("gun_knight_patriot") || key.getPath().equals("turret"))) {
                    return false;
                }
                if (key.getNamespace().equals("nightfall_invade")
                        && (key.getPath().equals("arterius"))) {
                    return false;
                }
                if (pathfinderMobInventory instanceof SteveEntity steveEntity) {
                    if (steveEntity.getItemInHand(InteractionHand.OFF_HAND).getItem().equals(Items.TOTEM_OF_UNDYING)) return false;
                }
            }

            return pathfinderMobInventory.isUseBow() && pathfinderMobInventory.getSwapToBowCooldown() == 0;
        }

        return false;
    }

    public static boolean canSwitchWeapon(MobPatch<?> mobpatch) {
        LivingEntity target = mobpatch.getOriginal().getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }

        if (mobpatch.getOriginal() instanceof SteveEntity steveEntity) {
            return steveEntity.getSwapWeaponCooldown() == 0 || (steveEntity.getState() == 0 && steveEntity.getHealth() <= 20 && !steveEntity.getMainHandItem().getItem().equals(Items.DIAMOND_SWORD));
        }

        return false;
    }

    public static void performEnderPearlToTarget(MobPatch<?> mobpatch) {
        LivingEntity target = mobpatch.getOriginal().getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }

        LivingEntity entity = mobpatch.getOriginal();

        double dx = target.getX() - entity.getX();
        double dz = target.getZ() - entity.getZ();
        double dy = target.getEyeY() - entity.getEyeY();
        double horiz = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) (Mth.atan2(dz, dx) * (180F / (float) Math.PI)) - 90.0F;
        float pitch = (float) (-(Mth.atan2(dy, horiz) * (180F / (float) Math.PI)));

        entity.setYRot(yaw);
        entity.setXRot(pitch);
        entity.setYBodyRot(yaw);
        entity.setYHeadRot(yaw);

        entity.yRotO = yaw;
        entity.xRotO = pitch;
        entity.yBodyRotO = yaw;
        entity.yHeadRotO = yaw;

        if (entity instanceof PlayerNpcEntity playerNpcEntity) {
            playerNpcEntity.setEnderPearlCooldown();
        }

        if (entity instanceof PathfinderMobInventory pathfinderMobInventory) {
            pathfinderMobInventory.setEnderPearlCooldown();
        }

        CombatBehaviour.throwEnderPearl(entity, 0.0F);
    }

    public static void performEnderPearlAway(MobPatch<?> mobpatch) {
        LivingEntity target = mobpatch.getOriginal().getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }

        LivingEntity entity = mobpatch.getOriginal();

        double dx = entity.getX() - target.getX();
        double dz = entity.getZ() - target.getZ();
        double horiz = Math.sqrt(dx * dx + dz * dz);

        if (horiz < 1.0E-3D) {
            CombatBehaviour.throwEnderPearl(entity, 0.0F);
            if (entity instanceof PlayerNpcEntity playerNpcEntity) {
                playerNpcEntity.setEnderPearlCooldown();
            }
            if (entity instanceof PathfinderMobInventory pathfinderMobInventory) {
                pathfinderMobInventory.setEnderPearlCooldown();
            }
            return;
        }

        float yaw = (float) (Mth.atan2(dz, dx) * (180F / (float) Math.PI)) - 90.0F;

        float basePitch = -15.0F;
        float randomPitchOffset = (entity.getRandom().nextFloat() - 0.5F) * 10.0F;
        float randomYawOffset = (entity.getRandom().nextFloat() - 0.5F) * 30.0F;

        float pitch = basePitch + randomPitchOffset;
        yaw += randomYawOffset;

        entity.setYRot(yaw);
        entity.setXRot(pitch);
        entity.setYBodyRot(yaw);
        entity.setYHeadRot(yaw);

        entity.yRotO = yaw;
        entity.xRotO = pitch;
        entity.yBodyRotO = yaw;
        entity.yHeadRotO = yaw;

        if (entity instanceof PlayerNpcEntity playerNpcEntity) {
            playerNpcEntity.setEnderPearlCooldown();
        }
        if (entity instanceof PathfinderMobInventory pathfinderMobInventory) {
            pathfinderMobInventory.setEnderPearlCooldown();
        }
        CombatBehaviour.throwEnderPearl(entity, 0.0F);
    }

    public static void performEscapeRunAwayWithRandomBlocks(MobPatch<?> mobpatch) {
        performEscapeRunAway(mobpatch);
        new DelayedTask(1) {
            @Override public void run() {
                placeRandomFrontWall(mobpatch);
            }
        };
    }

    public static void placeRandomFrontWall(MobPatch<?> mobpatch) {
        final Mob mob = mobpatch.getOriginal();
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;

        final LivingEntity target = mob.getTarget();
        final Direction dir = (target != null)
                ? Direction.getNearest(target.getX() - mob.getX(), 0.0D, target.getZ() - mob.getZ())
                : mob.getDirection();

        final ItemStack handStack = mob.getItemInHand(InteractionHand.MAIN_HAND);
        BlockState placeState = net.minecraft.world.level.block.Blocks.COBBLESTONE.defaultBlockState();
        if (handStack.getItem() instanceof BlockItem blockItem) {
            placeState = blockItem.getBlock().defaultBlockState();
        }

        final Random random = new Random();
        final int lanes = 1 + random.nextInt(3);
        final float missChancePerLane = 0.25F;

        for (int dist = 1; dist <= lanes; dist++) {
            if (random.nextFloat() < missChancePerLane) continue;

            final int pattern = random.nextInt(11);
            final int rot = random.nextInt(4);
            final BiFunction<Integer, Integer, int[]> toWorld = getIntegerIntegerBiFunction(mob, rot);

            final BlockPos baseXZ = mob.blockPosition().relative(dir, dist);
            final int topY = Mth.floor(mob.getY() + mob.getBbHeight());

            final int surfaceY = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, baseXZ).getY();
            final BlockPos projXZ = new BlockPos(baseXZ.getX(), 0, baseXZ.getZ());

            for (int y = surfaceY; y <= topY; y++) {
                final int layer = y - surfaceY;

                final BlockPos center = new BlockPos(projXZ.getX(), y, projXZ.getZ());
                if (!serverLevel.getBlockState(center).canBeReplaced()) break;

                placeIfReplaceable(serverLevel, center, placeState, mobpatch, mob);

                int[][] extrasLocal = switch (pattern) {
                    case 0 -> new int[][]{};

                    case 1 -> (layer == 3) ? new int[][]{{1, 0}} : new int[][]{};

                    case 2 -> {
                        if (layer == 0) yield new int[][]{{-1, 0}, {1, 0}, {2, 0}};
                        else if (layer == 1) yield new int[][]{{1, 0}};
                        else yield new int[][]{};
                    }

                    case 3 -> (layer == 1) ? new int[][]{{-1, 0}, {1, 0}} : new int[][]{};

                    case 4 -> (layer == 0) ? new int[][]{{-1, 0}, {1, 0}} : new int[][]{};

                    case 5 -> new int[][]{{1, 0}};

                    case 6 -> (layer <= 1) ? new int[][]{{1, 0}} : new int[][]{};

                    case 7 -> (layer == 0) ? new int[][]{{1, 0}} : new int[][]{};

                    case 8 -> (layer == 1) ? new int[][]{{1, 0}} : new int[][]{};

                    case 9 -> (layer == 0) ? new int[][]{{-1, 0}} : new int[][]{};

                    default -> (layer == 1) ? new int[][]{{-1, 0}} : new int[][]{};
                };

                for (int[] ab : extrasLocal) {
                    int[] dzdx = toWorld.apply(ab[0], ab[1]);
                    int dx = dzdx[0];
                    int dz = dzdx[1];

                    BlockPos p = center.offset(dx, 0, dz);
                    placeIfReplaceable(serverLevel, p, placeState, mobpatch, mob);
                }
            }
        }
    }

    private static java.util.function.BiFunction<Integer, Integer, int[]> getIntegerIntegerBiFunction(Entity anchor, int rot) {
        Direction facing = anchor.getDirection();

        int fx = facing.getStepX();
        int fz = facing.getStepZ();
        int rx = -fz;
        int rz = fx;

        for (int i = 0; i < rot; i++) {
            int nfx = rx, nfz = rz;
            int nrx = -fz, nrz = fx;
            fx = nfx; fz = nfz;
            rx = nrx; rz = nrz;
        }

        int finalRx = rx;
        int finalFx = fx;
        int finalRz = rz;
        int finalFz = fz;

        return (a, b) -> new int[] { a * finalRx + b * finalFx, a * finalRz + b * finalFz };
    }

    private static void placeIfReplaceable(ServerLevel level, BlockPos pos, BlockState state, MobPatch<?> mobpatch, Mob mob) {
        if (!level.getBlockState(pos).canBeReplaced()) return;
        mobpatch.playAnimationSynchronized(AVAnimations.PLACE_BLOCK, 0.0F);
        mob.playSound(SoundEvents.STONE_PLACE, 2.0F, 1.0F);
        level.setBlockAndUpdate(pos, state);
    }

    public static void performEscapeRunAway(MobPatch<?> mobpatch) {
        final Mob mob = mobpatch.getOriginal();
        if (!(mob.level() instanceof ServerLevel)) return;

        final LivingEntity target = mob.getTarget();
        if (target != null) {
            mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }

        Vec3 away;
        if (target != null) {
            Vec3 toTarget = new Vec3(target.getX() - mob.getX(), 0.0D, target.getZ() - mob.getZ());
            away = toTarget.lengthSqr() > 1.0E-6D ? toTarget.normalize().scale(-1.0D) : Vec3.ZERO;
        } else {
            float yawRad = mob.yBodyRot * Mth.DEG_TO_RAD;
            away = new Vec3(-Mth.sin(yawRad), 0.0D, Mth.cos(yawRad)).normalize().scale(-1.0D);
        }
        if (away == Vec3.ZERO) return;
        Vec3 right = new Vec3(-away.z, 0.0D, away.x).normalize();

        mob.getNavigation().stop();
        Random r = new Random();
        double backMag = 0.55D + r.nextDouble() * 0.35D;
        double strafeMag = (r.nextBoolean() ? 1 : -1) * (0.05D + r.nextDouble() * 0.15D);
        Vec3 impulse = away.scale(backMag).add(right.scale(strafeMag));

        mob.setDeltaMovement(mob.getDeltaMovement().add(impulse.x, 0.0D, impulse.z));
        mob.hasImpulse = true;

        int pulses = 2 + r.nextInt(2);
        for (int i = 1; i <= pulses; i++) {
            Vec3 tail = away.scale(0.16D + r.nextDouble() * 0.10D)
                    .add(right.scale((r.nextDouble() - 0.5D) * 0.10D));
            int delay = i * 2;
            new DelayedTask(delay) {
                @Override public void run() {
                    if (!mob.isAlive()) return;
                    mob.setDeltaMovement(mob.getDeltaMovement().add(tail.x, 0.0D, tail.z));
                    mob.hasImpulse = true;
                }
            };
        }

        int jumpDelay = pulses * 2 + 1;
        new DelayedTask(jumpDelay) {
            @Override public void run() {
                if (!mob.isAlive() || !mob.onGround()) return;

                if (mob instanceof PathfinderMobInventory pathfinderMobInventory) {
                    pathfinderMobInventory.shortPillarJump();
                }
                mobpatch.playAnimationSynchronized(Animations.BIPED_JUMP, 0.0F);
            }
        };
    }

    public static void performEatingAnimation(MobPatch<?> mobpatch) {
        LivingEntity entity = mobpatch.getOriginal();
        boolean isEnchanted;

        if (entity instanceof PathfinderMobInventory pathfinderMobInventory
                && new Random().nextDouble() <= pathfinderMobInventory.getPlaceBlockToParryChance()) {
            entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
            isEnchanted = true;
        } else {
            entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.GOLDEN_APPLE));
            isEnchanted = false;
        }
        if (new Random().nextBoolean()) {
            CombatBehaviour.throwEnderPearl(entity, new Random().nextFloat(0.0F, 180.0F));
            if (entity instanceof PlayerNpcEntity playerNpcEntity) {
                playerNpcEntity.setEnderPearlCooldown();
            }
            if (entity instanceof PathfinderMobInventory pathfinderMobInventory) {
                pathfinderMobInventory.setEnderPearlCooldown();
            }
        } else {
            performEscapeRunAway(mobpatch);
        }

        if (entity instanceof PlayerNpcEntity playerNpcEntity) {
            playerNpcEntity.setGapCooldown();
        }
        if (entity instanceof PathfinderMobInventory pathfinderMobInventory) {
            pathfinderMobInventory.setGapCooldown();
        }

        CombatBehaviour.eatingGoldenApple(
                entity,
                entity.level(),
                20.0D,
                isEnchanted
        );
    }

    public static void performDrinkingAnimation(MobPatch<?> mobpatch) {
        LivingEntity entity = mobpatch.getOriginal();

        if (!entity.level().isClientSide) {
            ItemStack stack = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.STRONG_HEALING);
            entity.setItemInHand(InteractionHand.MAIN_HAND, stack);
        }
        if (entity instanceof PathfinderMobInventory pathfinderMobInventory) {
            pathfinderMobInventory.setGapCooldown();
        }

        CombatBehaviour.drinkingHealingPotion(
                entity,
                entity.level(),
                false,
                20.0D
        );
    }

    public static void swapToBow(MobPatch<?> mobpatch) {
        LivingEntity entity = mobpatch.getOriginal();
        ItemStack bow = new ItemStack(Items.BOW);

        if (entity instanceof VillagerScoutCaptainEntity) {
            bow.enchant(Enchantments.POWER_ARROWS, 1);
            bow.enchant(Enchantments.PUNCH_ARROWS, 1);
        }
        if (entity instanceof RedVillagerGeneralEntity) {
            bow.enchant(Enchantments.FLAMING_ARROWS, 2);
        }
        if (entity instanceof BlueVillagerGeneralEntity) {
            bow.enchant(Enchantments.POWER_ARROWS, 2);
        }
        if (entity instanceof GreenVillagerGeneralEntity) {
            bow.enchant(Enchantments.POWER_ARROWS, 1);
            bow.enchant(Enchantments.FLAMING_ARROWS, 1);
        }
        if (entity instanceof PurpleVillagerGeneralEntity) {
            bow.enchant(Enchantments.PUNCH_ARROWS, 2);
        }
        if ((entity instanceof SteveEntity steveEntity && steveEntity.getState() == 1)
        || entity instanceof AngrySteveEntity) {
            bow.enchant(Enchantments.POWER_ARROWS, 5);
            bow.enchant(Enchantments.PUNCH_ARROWS, 5);
            if (entity instanceof AngrySteveEntity) {
                bow.enchant(Enchantments.FLAMING_ARROWS, 2);
            }
        }
        if (entity instanceof AlexEntity alexEntity && alexEntity.getState() == 1) {
            bow.enchant(Enchantments.PUNCH_ARROWS, 3);
            bow.enchant(Enchantments.POWER_ARROWS, 3);
            bow.enchant(Enchantments.FLAMING_ARROWS, 2);
        }
        if (entity instanceof ChrisEntity chrisEntity && chrisEntity.getState() == 1) {
            bow.enchant(Enchantments.POWER_ARROWS, 2);
            bow.enchant(Enchantments.PUNCH_ARROWS, 2);
        }

        entity.setItemInHand(InteractionHand.MAIN_HAND, bow.copy());
        entity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);

        if (entity instanceof VillagerScoutEntity
                && !entity.level().isClientSide() && entity.level().getServer() != null) {
            Objects.requireNonNull(entity.level().getServer()).getPlayerList().broadcastSystemMessage(Component.literal("<" + entity.getDisplayName().getString() + "> Fire!"), false);
        }
    }

    public static void switchWeapon(MobPatch<?> mobpatch) {
        LivingEntity entity = mobpatch.getOriginal();
        if (entity instanceof SteveEntity steveEntity) {
            steveEntity.rollItem();
        }
    }

    public static void swapToMelee(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof PlayerNpcEntity playerNpcEntity) {
            ItemStack mainWeaponItem = playerNpcEntity.getMainWeaponItem();
            ItemStack offWeaponItem = playerNpcEntity.getOffWeaponItem();
            if (!mainWeaponItem.isEmpty()) {
                playerNpcEntity.setItemInHand(InteractionHand.MAIN_HAND, mainWeaponItem.copy());
            }
            if (!offWeaponItem.isEmpty()) {
                playerNpcEntity.setItemInHand(InteractionHand.OFF_HAND, offWeaponItem.copy());
            }
            playerNpcEntity.setSwapToBowCooldown();
        }

        if (mobpatch.getOriginal() instanceof PathfinderMobInventory pathfinderMobInventory) {
            ItemStack mainWeaponItem = pathfinderMobInventory.getMainWeaponItem();
            ItemStack offWeaponItem = pathfinderMobInventory.getOffWeaponItem();
            if (pathfinderMobInventory instanceof SteveEntity) {
                if (canSwitchWeapon(mobpatch)) {
                    switchWeapon(mobpatch);
                } else {
                    if (!mainWeaponItem.isEmpty() && !(mainWeaponItem.getItem() instanceof BowItem)) {
                        pathfinderMobInventory.setItemInHand(InteractionHand.MAIN_HAND, mainWeaponItem.copy());
                    }
                    if (!offWeaponItem.isEmpty()) {
                        pathfinderMobInventory.setItemInHand(InteractionHand.OFF_HAND, offWeaponItem.copy());
                    }
                }
            } else {
                if (!mainWeaponItem.isEmpty() && !(mainWeaponItem.getItem() instanceof BowItem)) {
                    pathfinderMobInventory.setItemInHand(InteractionHand.MAIN_HAND, mainWeaponItem.copy());
                } else if (pathfinderMobInventory instanceof VillagerScoutEntity villagerScoutEntity) {
                    villagerScoutEntity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.IRON_SWORD));
                }
                if (!offWeaponItem.isEmpty()) {
                    pathfinderMobInventory.setItemInHand(InteractionHand.OFF_HAND, offWeaponItem.copy());
                }
            }
            pathfinderMobInventory.setSwapToBowCooldown();
        }
    }

    public static void jump(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (entity instanceof PlayerNpcEntity playerNpcEntity) {
            playerNpcEntity.jump();
        }
        if (entity instanceof PathfinderMobInventory pathfinderMobInventory) {
            pathfinderMobInventory.jump();
        }
    }

    public static void swapToBlockToEscape(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (entity instanceof LivingEntity livingEntity) {
            double chance = new Random().nextDouble(0.0, 1.0);
            if (chance <= 0.33) {
                livingEntity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.COBBLESTONE));
            } else if (chance <= 0.66) {
                livingEntity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.MOSSY_COBBLESTONE));
            } else {
                livingEntity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.OAK_PLANKS));
            }
        }
    }

    public static void swapToBlock(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (entity instanceof LivingEntity livingEntity) {
            double chance = new Random().nextDouble(0.0, 1.0);
            if (chance <= 0.33) {
                livingEntity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.COBBLESTONE));
            } else if (chance <= 0.66) {
                livingEntity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.MOSSY_COBBLESTONE));
            } else {
                livingEntity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.OAK_PLANKS));
            }
        }
    }
}
