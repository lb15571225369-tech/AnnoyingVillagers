package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.List;

public class BlueDemonTridentItem extends SwordItem {
    public BlueDemonTridentItem() {
        super(new Tier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 8.0F;
            }

            public float getAttackDamageBonus() {
                return 5.5F;
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
    public void appendHoverText(@NotNull ItemStack itemstack, Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.translatable("tooltip.annoyingvillagers.blue_demon_item"));
    }
}