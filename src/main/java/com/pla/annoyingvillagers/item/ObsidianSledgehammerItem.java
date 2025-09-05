package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.entity.ObsidianSledgehammerHitEntity;
import com.pla.annoyingvillagers.procedures.HerobrineWeaponEffectProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;

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
                return 12.0F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 32;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 1.0F, -2.6F, (new Properties()).fireResistant());
    }

    private void spawnObsidianSpike(double x, double z, double minY, double maxY, float rotation, int delay, Entity entity) {
        BlockPos blockpos = new BlockPos((int) x, (int) maxY, (int) z);
        boolean flag = false;
        double d0 = 0.0D;

        do {
            BlockPos blockpos1 = blockpos.below();
            BlockState blockstate = entity.level().getBlockState(blockpos1);
            if (blockstate.isFaceSturdy(entity.level(), blockpos1, Direction.UP)) {
                if (!entity.level().isEmptyBlock(blockpos)) {
                    BlockState blockstate1 = entity.level().getBlockState(blockpos);
                    VoxelShape voxelshape = blockstate1.getCollisionShape(entity.level(), blockpos);
                    if (!voxelshape.isEmpty()) {
                        d0 = voxelshape.max(Direction.Axis.Y);
                    }
                }

                flag = true;
                break;
            }

            blockpos = blockpos.below();
        } while(blockpos.getY() >= Mth.floor(minY) - 1);

        if (flag) {
            LivingEntity entity1 = (LivingEntity) entity;
            entity.level().addFreshEntity(new ObsidianSledgehammerHitEntity(entity.level(), x, (double)blockpos.getY() + d0, z, rotation, delay, 3.0F, entity1));
        }
    }

    public void circleHit(Entity entity, BlockPos blockPos) {
        if (!entity.level().isClientSide) {
            int standingOnY = Mth.floor(entity.getY()) - 3;
            for (int k = 0; k < 6; ++k) {
                float f2 = (float) k * (float) Math.PI * 2.0F / 6.0F + ((float) Math.PI * 2F / 5F);
                this.spawnObsidianSpike(blockPos.getX() + (double) Mth.cos(f2) * 2.5D, blockPos.getZ() + (double) Mth.sin(f2) * 2.5D, standingOnY, blockPos.getY() + 1, f2, 0, entity);
            }
            for (int k = 0; k < 11; ++k) {
                float f3 = (float) k * (float) Math.PI * 2.0F / 11.0F + ((float) Math.PI * 2F / 10F);
                this.spawnObsidianSpike(blockPos.getX() + (double) Mth.cos(f3) * 3.5D, blockPos.getZ() + (double) Mth.sin(f3) * 3.5D, standingOnY, blockPos.getY() + 1, f3, 2, entity);
            }
            for (int k = 0; k < 14; ++k) {
                float f4 = (float) k * (float) Math.PI * 2.0F / 14.0F + ((float) Math.PI * 2F / 20F);
                this.spawnObsidianSpike(blockPos.getX() + (double) Mth.cos(f4) * 4.5D, blockPos.getZ() + (double) Mth.sin(f4) * 4.5D, standingOnY, blockPos.getY() + 1, f4, 4, entity);
            }
            for (int k = 0; k < 19; ++k) {
                float f5 = (float) k * (float) Math.PI * 2.0F / 19.0F + ((float) Math.PI * 2F / 25F);
                this.spawnObsidianSpike(blockPos.getX() + (double) Mth.cos(f5) * 5.5D, blockPos.getZ() + (double) Mth.sin(f5) * 5.5D, standingOnY, blockPos.getY() + 1, f5, 6, entity);
            }
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (!itemstack.getTag().getBoolean("SecondForm")) {
            itemstack.getTag().putInt("HitCount", (itemstack.getTag().contains("HitCount") ? itemstack.getTag().getInt("HitCount") : 0) + 1);
        }
        return super.hurtEnemy(itemstack, pTarget, pAttacker);
    }

    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        if (flag && itemstack.getTag().getBoolean("SecondForm")) {
            HerobrineWeaponEffectProcedure.execute(level, entity.getX(), entity.getY(), entity.getZ(), entity);
            if (entity instanceof LivingEntity livingEntity) {
                if (!livingEntity.level().isClientSide()) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.JUMP, 1, 2));
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1, 2));
                }
            }
        }
        if (!itemstack.getTag().getBoolean("SecondForm") && itemstack.getTag().getInt("HitCount") >= 10) {
            if (entity instanceof Player player) {
                ItemCooldowns cooldowns = player.getCooldowns();
                cooldowns.addCooldown(itemstack.getItem(), 200);
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
                }
            } else {
                if (itemstack.getTag().getBoolean("SecondForm")) {
                    itemstack.getTag().remove("SecondForm");
                }
            }
        }
    }

    String getCurrentComboAttack(ItemStack itemstack) {
        if (!itemstack.getTag().getBoolean("SecondForm")) {
            return String.format("%d/10", itemstack.getTag().contains("HitCount") ? itemstack.getTag().getInt("HitCount") : 0);
        } else {
            return String.format("∞/∞");
        }
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.literal("One of Herobrine's legendary weapons.\n" +
                "§aNormal Form§r: A normal hammer with no special powers. After 10 successful hits, it awakens into its §5Second Form§r.\n" +
                "§5Second Form§r: Lasts for 10 seconds.\n" +
                "- Grants §9JUMP BOOST§r and §dMOVEMENT SPEED§r effects.\n" +
                "- Each ground slam summons a ring of shadow obsidian spikes from the ground.\n" +
                "- The spikes knock enemies back and force them into a knocked-down state.\n" +
                "§7(Current Combo Attack: " + getCurrentComboAttack(itemstack) + ")§r"));
    }
}
