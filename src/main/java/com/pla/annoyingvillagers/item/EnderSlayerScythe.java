package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.entity.BabyEnderDragonEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.procedures.HerobrineWeaponEffectProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class EnderSlayerScythe extends SwordItem {

    public EnderSlayerScythe() {
        super(new Tier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 14.0F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 2;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 3, -2.3F, (new Properties()).fireResistant());
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (!itemstack.getTag().getBoolean("SecondForm")) {
            itemstack.getTag().putInt("HitCount", (itemstack.getTag().contains("HitCount") ? itemstack.getTag().getInt("HitCount") : 0) + 1);
        }
        return super.hurtEnemy(itemstack, pTarget, pAttacker);
    }

    private static Vec3 posBehind3D(Player p, double back, double up, double right) {
        Vec3 look = p.getLookAngle().normalize();
        Vec3 forwardXZ = new Vec3(look.x, 0, look.z);
        if (forwardXZ.lengthSqr() < 1e-6) forwardXZ = new Vec3(-Mth.sin(p.getYRot() * ((float)Math.PI/180F)), 0, Mth.cos(p.getYRot() * ((float)Math.PI/180F)));
        forwardXZ = forwardXZ.normalize();
        Vec3 rightVec = new Vec3(-forwardXZ.z, 0, forwardXZ.x);
        return p.position()
                .subtract(look.scale(back))
                .add(0, up, 0)
                .add(rightVec.scale(right));
    }

    private void spawnBabyEnderDragon(ItemStack itemstack, Player player) {
        if (player.level() instanceof ServerLevel levelaccessor) {
            ServerLevel serverlevel = (ServerLevel) levelaccessor;

            BabyEnderDragonEntity babyEnderDragonEntity = new BabyEnderDragonEntity((EntityType) AnnoyingVillagersModEntities.BABY_ENDER_DRAGON.get(), serverlevel);
            Vec3 posBehind3D = posBehind3D(player, 1.0D, 2.0D, 1.0D);
            babyEnderDragonEntity.moveTo(
                    posBehind3D.x,
                    posBehind3D.y,
                    posBehind3D.z
            );
            babyEnderDragonEntity.setFollowTarget(player);
            babyEnderDragonEntity.setFollowTargetUUID(player.getUUID());
            babyEnderDragonEntity.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(player.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            levelaccessor.addFreshEntity(babyEnderDragonEntity);
            itemstack.getTag().putUUID("DragonSummon", babyEnderDragonEntity.getUUID());
        }
    }

    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        if (flag && itemstack.getTag().getBoolean("SecondForm")) {
            HerobrineWeaponEffectProcedure.execute(level, entity.getX(), entity.getY(), entity.getZ(), entity);
            if (entity instanceof LivingEntity livingEntity) {
                if (!livingEntity.level().isClientSide()) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.JUMP, 1, 2));
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1, 2));
                }
            }
        }
        if (!itemstack.getTag().getBoolean("SecondForm") && itemstack.getTag().getInt("HitCount") >= 15) {
            if (entity instanceof Player player) {
                ItemCooldowns cooldowns = player.getCooldowns();
                cooldowns.addCooldown(itemstack.getItem(), 600);
                itemstack.getTag().remove("HitCount");
            }
        }
        if (entity instanceof Player player) {
            float percent = player.getCooldowns().getCooldownPercent(itemstack.getItem(), 0);
            if (percent > 0.0F) {
                if (!itemstack.getTag().getBoolean("SecondForm")) {
                    itemstack.getTag().putBoolean("SecondForm", true);

                    if (!player.level().isClientSide()) {
                        player.level().playSound((Player) null, new BlockPos((int) player.getX(), (int) player.getY(), (int) player.getZ()), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:second_form_release")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        player.level().playLocalSound(player.getX(), player.getY(), player.getZ(), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:second_form_release")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }

                    if (!itemstack.getTag().contains("DragonSummon")) {
                        spawnBabyEnderDragon(itemstack, player);
                    }
                }
            } else {
                if (itemstack.getTag().getBoolean("SecondForm")) {
                    itemstack.getTag().remove("SecondForm");
                    itemstack.getTag().remove("DragonSummon");
                }
            }
        }
    }

    String getCurrentComboAttack(ItemStack itemstack) {
        if (!itemstack.getTag().getBoolean("SecondForm")) {
            return String.format("%d/15", itemstack.getTag().contains("HitCount") ? itemstack.getTag().getInt("HitCount") : 0);
        } else {
            return String.format("∞/∞");
        }
    }

    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.literal("One of Herobrine's legendary weapons.\n" +
                "§aNormal Form§r: A normal scythe with no special powers. After 15 successful hits, it awakens into its §5Second Form§r.\n" +
                "§5Second Form§r: Lasts for 30 seconds.\n" +
                "- Grants §9JUMP BOOST§r, and §dDAMAGE BOOST§r effects.\n" +
                "- Summons a baby Ender Dragon that fires a tiny thunder beam every 6 seconds at any target that hurts or is hurt by the player.\n" +
                "- If no valid target exists, it attacks a random nearby entity.\n" +
                "§7(Current Combo Attack: " + getCurrentComboAttack(itemstack) + ")§r"));
    }
}
