package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.entity.ChrisEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class InfectedChrisOnInteractProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity, Entity entity1) {
        if (entity != null && entity1 != null) {
            ItemStack itemstack;

            if (entity1 instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity1;

                itemstack = livingentity.getMainHandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            if (itemstack.getItem() == Items.TOTEM_OF_UNDYING) {
                if (!entity.level().isClientSide()) {
                    entity.discard();
                }

                ServerLevel serverlevel;

                if (levelaccessor instanceof ServerLevel) {
                    serverlevel = (ServerLevel) levelaccessor;
                    ChrisEntity kelisientity = new ChrisEntity((EntityType) AnnoyingVillagersModEntities.CHRIS.get(), serverlevel);

                    kelisientity.moveTo(d0, d1, d2, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                    if (kelisientity instanceof Mob) {
                        Mob mob = (Mob) kelisientity;

                        mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(kelisientity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
                    }

                    levelaccessor.addFreshEntity(kelisientity);
                }

                if (levelaccessor instanceof Level) {
                    Level level = (Level) levelaccessor;

                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "item.totem.use")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "item.totem.use")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (levelaccessor instanceof ServerLevel) {
                    serverlevel = (ServerLevel) levelaccessor;
                    serverlevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, d0, d1, d2, 20, 0.0D, 0.0D, 0.0D, 0.2D);
                }

                if (entity1 instanceof LivingEntity) {
                    LivingEntity livingentity1 = (LivingEntity) entity1;
                    ItemStack itemstack1 = new ItemStack(Blocks.AIR);

                    itemstack1.setCount(1);
                    livingentity1.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                    if (livingentity1 instanceof Player) {
                        Player player = (Player) livingentity1;

                        player.getInventory().setChanged();
                    }
                }
            }

        }
    }
}
