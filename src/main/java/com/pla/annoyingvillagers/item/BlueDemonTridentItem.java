package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.entity.BlueDemonThrownTridentEntity;
import com.pla.annoyingvillagers.entity.ElectricAreaEntity;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

public class BlueDemonTridentItem extends SwordItem {
    private static final double OWNER_HALF_BOX = 25.0D;

    private static final int DAMAGE_ZONE_DURATION = 100;
    private static final float DAMAGE_ZONE_DAMAGE = 4.0F;
    private static final int DAMAGE_ZONE_INTERVAL = 10;

    private static final float RELAUNCH_SPEED = 2.5F;
    public static final String TAG_STORM_ENERGY = "BlueDemonStormEnergy";
    public static final int MAX_STORM_ENERGY = 100;

    private static final int ENERGY_METER_STEPS = 18;
    private static final int ENERGY_COLOR = 0x55F7FF;
    private static final int ENERGY_DIM_COLOR = 0x3E4A4D;
    private static final int ENERGY_TEXT_COLOR = 0xBDFBFF;
    private static final int ENERGY_FULL_COLOR = 0x7CFFFF;


    public BlueDemonTridentItem() {
        super(new Tier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 8.0F;
            }

            public float getAttackDamageBonus() {
                return 3.5F;
            }

            public int getLevel() {
                return 3;
            }

            public int getEnchantmentValue() {
                return 10;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 3, -2.7F, (new Properties()));
    }

    public static Vec3 getTridentThrowDirection(LivingEntity livingEntity, Vec3 startPos) {
        if (livingEntity instanceof Player) {
            return livingEntity.getViewVector(1.0F).normalize();
        }

        if (livingEntity instanceof Mob mob) {
            LivingEntity target = mob.getTarget();
            if (target != null && target.isAlive()) {
                Vec3 targetPos = target.position().add(0.0D, target.getBbHeight() * 0.7D, 0.0D);
                Vec3 dir = targetPos.subtract(startPos);
                if (dir.lengthSqr() > 1.0E-7) {
                    return dir.normalize();
                }
            }
        }

        Vec3 fallback = livingEntity.getViewVector(1.0F);
        return fallback.lengthSqr() > 1.0E-7 ? fallback.normalize() : null;
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return super.isFoil(stack) || isFullyCharged(stack);
    }

    public static boolean isBlueDemonTrident(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() instanceof BlueDemonTridentItem;
    }

    public static int getOnlyStormEnergy(ItemStack stack) {
        if (!isBlueDemonTrident(stack)) {
            return 0;
        }

        CompoundTag tag = stack.getTag();
        return tag == null ? 0 : Mth.clamp(tag.getInt(TAG_STORM_ENERGY), 0, MAX_STORM_ENERGY);
    }

    public static boolean checkOnlyFullyCharged(ItemStack stack) {
        return getOnlyStormEnergy(stack) >= MAX_STORM_ENERGY;
    }

    public static int getStormEnergy(ItemStack stack) {
        if (!isBlueDemonTrident(stack)) {
            return 0;
        }

        CompoundTag tag = stack.getOrCreateTag();
        return Mth.clamp(tag.getInt(TAG_STORM_ENERGY), 0, MAX_STORM_ENERGY);
    }

    public static void setStormEnergy(ItemStack stack, int value) {
        if (!isBlueDemonTrident(stack)) {
            return;
        }

        stack.getOrCreateTag().putInt(TAG_STORM_ENERGY, Mth.clamp(value, 0, MAX_STORM_ENERGY));
    }

    public static boolean isFullyCharged(ItemStack stack) {
        return getStormEnergy(stack) >= MAX_STORM_ENERGY;
    }

    public static void addStormEnergy(ItemStack stack, int amount) {
        if (!isBlueDemonTrident(stack) || amount <= 0) {
            return;
        }

        int current = getStormEnergy(stack);
        int added = Math.min(amount, MAX_STORM_ENERGY - current);

        if (added > 0) {
            setStormEnergy(stack, current + added);
        }

    }

    public static void spawnDamageZones(ServerLevel serverLevel, LivingEntity owner) {
        ElectricAreaEntity ownerZone = new ElectricAreaEntity(
                serverLevel,
                owner,
                owner.position(),
                2.5D,
                DAMAGE_ZONE_DURATION,
                DAMAGE_ZONE_DAMAGE,
                DAMAGE_ZONE_INTERVAL
        );
        serverLevel.addFreshEntity(ownerZone);

        for (BlueDemonThrownTridentEntity trident : getGroundedOwnerTridents(serverLevel, owner)) {
            ElectricAreaEntity tridentZone = new ElectricAreaEntity(
                    serverLevel,
                    owner,
                    trident.position(),
                    1.5D,
                    DAMAGE_ZONE_DURATION,
                    DAMAGE_ZONE_DAMAGE,
                    DAMAGE_ZONE_INTERVAL
            );
            serverLevel.addFreshEntity(tridentZone);
        }
    }

    public static void relaunchGroundedTridents(ServerLevel serverLevel, LivingEntity owner) {
        List<BlueDemonThrownTridentEntity> tridents = getGroundedOwnerTridents(serverLevel, owner);
        if (tridents.isEmpty()) {
            return;
        }

        List<LivingEntity> targets = getNearbyTargets(serverLevel, owner);

        for (int i = 0; i < tridents.size(); i++) {
            BlueDemonThrownTridentEntity trident = tridents.get(i);

            LivingEntity target = targets.isEmpty() ? null : targets.get(i % targets.size());
            Vec3 fallback = target == null
                    ? com.pla.annoyingvillagers.item.BlueDemonTridentItem.getTridentThrowDirection(owner, trident.position())
                    : null;

            int extraDelay = 2 + i * 2 + serverLevel.random.nextInt(3);
            trident.beginAnimatedRelaunch(target, fallback, RELAUNCH_SPEED, 0.0F, extraDelay);
        }
    }

    public static void summonLightningAtGroundedTridents(ServerLevel serverLevel, LivingEntity owner) {
        for (BlueDemonThrownTridentEntity trident : getAllOwnerTridents(serverLevel, owner)) {
            trident.summonLightningAtSelf();
        }
    }

    public static void summonSuperLightningAtGroundedTridents(ServerLevel serverLevel, LivingEntity owner) {
        for (BlueDemonThrownTridentEntity trident : getAllOwnerTridents(serverLevel, owner)) {
            trident.summonSuperLightningAtSelf();
        }
    }

    private static List<BlueDemonThrownTridentEntity> getAllOwnerTridents(ServerLevel serverLevel, LivingEntity owner) {
        return serverLevel.getEntitiesOfClass(
                BlueDemonThrownTridentEntity.class,
                makeOwnerBox(owner),
                trident -> trident.isAlive()
                        && trident.belongsToOwner(owner)
        );
    }

    private static List<BlueDemonThrownTridentEntity> getGroundedOwnerTridents(ServerLevel serverLevel, LivingEntity owner) {
        return serverLevel.getEntitiesOfClass(
                BlueDemonThrownTridentEntity.class,
                makeOwnerBox(owner),
                trident -> trident.isAlive()
                        && trident.isGroundedTrident()
                        && trident.belongsToOwner(owner)
        );
    }

    private static List<LivingEntity> getNearbyTargets(ServerLevel serverLevel, LivingEntity owner) {
        List<LivingEntity> targets = serverLevel.getEntitiesOfClass(
                LivingEntity.class,
                makeOwnerBox(owner),
                target -> isValidTarget(owner, target)
        );

        targets.sort(Comparator.comparingDouble(target -> target.distanceToSqr(owner)));
        return targets;
    }

    private static boolean isValidTarget(LivingEntity owner, LivingEntity target) {
        if (target == owner) {
            return false;
        }

        if (!target.isAlive() || target.isSpectator()) {
            return false;
        }

        return !owner.isAlliedTo(target);
    }

    private static AABB makeOwnerBox(Entity owner) {
        return new AABB(
                owner.getX() - OWNER_HALF_BOX,
                owner.level().getMinBuildHeight(),
                owner.getZ() - OWNER_HALF_BOX,
                owner.getX() + OWNER_HALF_BOX,
                owner.level().getMaxBuildHeight(),
                owner.getZ() + OWNER_HALF_BOX
        );
    }

    public void inventoryTick(@NotNull ItemStack itemstack, @NotNull Level level, @NotNull Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        if (flag && entity instanceof Player player && entity.level() instanceof ServerLevel serverLevel) {
            PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
            if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.TRIDENT_FESTIVAL);
                if (skillContainer != null) {
                    if (skillContainer.getStack() >= 1) {
                        double d0 = entity.getX();
                        double d1 = entity.getY();
                        double d2 = entity.getZ();
                        if (Math.random() <= 0.1D) {
                            serverLevel.sendParticles(
                                    AnnoyingVillagersModParticleTypes.ELECTRIC_SPARK.get(),
                                    entity.getX(), entity.getY(), entity.getZ(),
                                    1,
                                    0.3D, 1.2D, 0.3D,
                                    0.0D
                            );

                            if (serverLevel.random.nextDouble() <= 0.8D) {
                                float volume = (float) Mth.nextDouble(serverLevel.random, 0.05D, 0.5D);
                                float pitch = (float) Mth.nextDouble(serverLevel.random, 0.8D, 1.1D);

                                serverLevel.playSound(
                                        null,
                                        BlockPos.containing(d0, d1, d2),
                                        AnnoyingVillagersModSounds.ELECTRIFY.get(),
                                        SoundSource.NEUTRAL,
                                        volume,
                                        pitch
                                );
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        int energy = getStormEnergy(stack);
        tooltip.add(Component.translatable("tooltip.annoyingvillagers.blue_demon_trident"));
        addStormChargeTooltip(tooltip, energy);
    }

    private static void addStormChargeTooltip(List<Component> tooltip, int energy) {
        tooltip.add(
                Component.literal("Thunder Charge")
                        .withStyle(style -> style.withBold(true).withColor(TextColor.fromRgb(ENERGY_COLOR)))
        );

        tooltip.add(
                Component.literal(energy + " / " + MAX_STORM_ENERGY)
                        .withStyle(style -> style.withColor(TextColor.fromRgb(ENERGY_TEXT_COLOR)))
        );

        tooltip.add(buildStormMeter(energy));

        if (energy >= MAX_STORM_ENERGY) {
            tooltip.add(
                    Component.literal("Charged")
                            .withStyle(style -> style.withBold(true).withColor(TextColor.fromRgb(ENERGY_FULL_COLOR)))
            );
        }
    }

    private static Component buildStormMeter(int energy) {
        int filledSteps = Math.round((energy / (float) MAX_STORM_ENERGY) * ENERGY_METER_STEPS);
        filledSteps = Mth.clamp(filledSteps, 0, ENERGY_METER_STEPS);

        MutableComponent meter = Component.empty();

        meter.append(
                Component.literal("⚡ ")
                        .withStyle(style -> style.withColor(TextColor.fromRgb(ENERGY_COLOR)))
        );

        for (int i = 0; i < ENERGY_METER_STEPS; i++) {
            boolean filled = i < filledSteps;

            meter.append(
                    Component.literal(filled ? "▰" : "▱")
                            .withStyle(style -> style.withColor(TextColor.fromRgb(filled ? ENERGY_COLOR : ENERGY_DIM_COLOR)))
            );
        }

        return meter;
    }
}