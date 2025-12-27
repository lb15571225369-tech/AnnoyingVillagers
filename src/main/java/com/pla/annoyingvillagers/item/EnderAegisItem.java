package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.entity.StealthAttackEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.procedures.HerobrineWeaponEffectProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.List;

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
                return 2.0F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 2;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 3, -2.3F, (new Properties()).fireResistant());
    }

    private static Vec3 aimDirection(Entity entity) {
        if (entity instanceof Player) {
            return entity.getLookAngle();
        }
        if (entity instanceof Mob mob) {
            LivingEntity tgt = mob.getTarget();
            if (tgt != null && tgt.isAlive()) {
                Vec3 from = mob.getEyePosition(1.0F);
                Vec3 to = tgt.getEyePosition(1.0F);
                return to.subtract(from);
            }
        }
        return entity.getLookAngle();
    }

    public static void shieldShoot(Level level, Entity entity) {
        if (!(level instanceof ServerLevel serverLevel)) return;

        final float velocity = 1.2F;
        final float inaccuracy = 0.0F;
        final double spawnForward = 0.0D;
        final double lateralOffset = 0.15D;
        final double spread = 0.05D;

        Vec3 forward = aimDirection(entity);
        if (forward.lengthSqr() < 1.0E-6D) {
            float yawRad = (float) Math.toRadians(entity.getYRot());
            forward = new Vec3(-Mth.sin(yawRad), 0.0D, Mth.cos(yawRad));
        }
        forward = forward.normalize();

        Vec3 UP = new Vec3(0.0D, 1.0D, 0.0D);
        Vec3 right = forward.cross(UP);
        if (right.lengthSqr() < 1.0E-6D) {
            float yawRad = (float) Math.toRadians(entity.getYRot());
            right = new Vec3(-Mth.cos(yawRad), 0.0D, -Mth.sin(yawRad));
        }
        right = right.normalize();
        Vec3 upOrtho = right.cross(forward).normalize();

        Vec3 eye = (entity instanceof LivingEntity le)
                ? le.getEyePosition(1.0F)
                : entity.position().add(0.0D, entity.getBbHeight() * 0.7D, 0.0D);

        Vec3[] pattern = new Vec3[] {
                Vec3.ZERO,
                upOrtho, upOrtho.scale(-1.0D),
                right.scale(-1.0D), right
        };

        for (Vec3 off : pattern) {
            Vec3 spawnPos = eye.add(forward.scale(spawnForward)).add(off.scale(lateralOffset));
            Vec3 dir = forward.add(off.scale(spread)).normalize();

            StealthAttackEntity proj = new StealthAttackEntity(
                    AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level
            );
            proj.setOwner(entity);
            proj.setBaseDamage(15.0F);
            proj.setKnockback(5);
            proj.setSilent(true);
            proj.setPierceLevel((byte) 5);
            proj.fromAegis = true;

            proj.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
            proj.shoot(dir.x, dir.y, dir.z, velocity, inaccuracy);

            serverLevel.addFreshEntity(proj);
        }

        Vec3 tipPos = eye.add(forward.scale(2.0D));
        serverLevel.sendParticles(
                AnnoyingVillagersModParticleTypes.SPARK.get(),
                tipPos.x, tipPos.y, tipPos.z,
                300, 0.0D, 0.0D, 0.0D, 0.2D
        );
        level.playSound(null, entity.blockPosition(), AnnoyingVillagersModSounds.COOL_DOWN.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
        level.playSound(null, entity.blockPosition(), AnnoyingVillagersModSounds.ENDER_SHOT.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
        level.playSound(null, entity.blockPosition(), AnnoyingVillagersModSounds.BLOOM.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (livingEntityPatch != null) {
            livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
        }
    }

    public void inventoryTick(@NotNull ItemStack itemstack, @NotNull Level level, @NotNull Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        if (flag) {
            if (itemstack.getTag() != null && itemstack.getTag().getBoolean("SecondForm")) {
                HerobrineWeaponEffectProcedure.execute(level, entity.getX(), entity.getY(), entity.getZ(), entity);
            }
        }
        if (entity instanceof Player player) {
            PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
            if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.ENDER_AEGIS);
                if (skillContainer != null && itemstack.getTag() != null) {
                    if (!skillContainer.isActivated() && itemstack.getTag().getBoolean("SecondForm")) {
                        itemstack.getTag().putBoolean("SecondForm", false);
                    }
                    if (skillContainer.isActivated() && !itemstack.getTag().getBoolean("SecondForm")) {
                        itemstack.getTag().putBoolean("SecondForm", true);
                    }
                }
            }
        }
    }

    public void appendHoverText(@NotNull ItemStack itemstack, Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.literal(Component.translatable("tooltip.annoyingvillagers.ender_aegis").getString() + ")§r"));
    }
}