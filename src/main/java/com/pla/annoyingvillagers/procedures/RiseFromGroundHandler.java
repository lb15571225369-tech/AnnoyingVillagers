package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.ArmoredHerobrineEntity;
import com.pla.annoyingvillagers.entity.Herobrine4Entity;
import com.pla.annoyingvillagers.entity.NullEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.HerobrineMob;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RiseFromGroundHandler {

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        var level = entity.level();
        if (level.isClientSide()) return;

        var tag = entity.getPersistentData();
        if (tag.getBoolean(HerobrinePortalProcedure.NBT_RISING)) {

            double targetY = tag.getDouble(HerobrinePortalProcedure.NBT_TARGET_Y);
            double speed   = tag.getDouble(HerobrinePortalProcedure.NBT_SPEED);
            int    ticks   = tag.getInt(HerobrinePortalProcedure.NBT_TICKS);
            int    max     = tag.getInt(HerobrinePortalProcedure.NBT_MAX_TICKS);

            double ny = entity.getY() + speed;
            if (ny >= targetY || ticks > max) {
                entity.setPos(entity.getX(), targetY, entity.getZ());
                finishRise(entity);
            } else {
                entity.setPos(entity.getX(), ny, entity.getZ());
                tag.putInt(HerobrinePortalProcedure.NBT_TICKS, ticks + 1);
            }
            return;
        }

        if (tag.getBoolean(HerobrinePortalProcedure.NBT_SINKING)) {
            double speed   = tag.getDouble(HerobrinePortalProcedure.NBT_SINK_SPEED);
            int    ticks   = tag.getInt(HerobrinePortalProcedure.NBT_SINK_TICKS);

            entity.setPos(entity.getX(), entity.getY() - speed, entity.getZ());
            tag.putInt(HerobrinePortalProcedure.NBT_SINK_TICKS, ticks + 1);
        }
    }

    private static void finishRise(LivingEntity entity) {
        var tag = entity.getPersistentData();

        entity.noPhysics = false;
        entity.setNoGravity(false);
        entity.setInvulnerable(false);
        if (entity instanceof Mob mob) {
            mob.setNoAi(false);
        }

        tag.remove(HerobrinePortalProcedure.NBT_RISING);
        tag.remove(HerobrinePortalProcedure.NBT_TARGET_Y);
        tag.remove(HerobrinePortalProcedure.NBT_SPEED);
        tag.remove(HerobrinePortalProcedure.NBT_TICKS);
        tag.remove(HerobrinePortalProcedure.NBT_MAX_TICKS);

        if (entity instanceof HerobrineMob herobrineMob) {
            if (herobrineMob.getGregUUID() != null) {
                Entity greg = ((ServerLevel) herobrineMob.level()).getEntity(herobrineMob.getGregUUID());
                if (greg instanceof Herobrine4Entity herobrine4Entity && herobrine4Entity.isAlive()) {
                    if (herobrine4Entity.isSummoning()) {
                        herobrine4Entity.setSummoning(false);
                        herobrine4Entity.setNoAi(false);
                    }
                }
            }
            if (entity instanceof NullEntity nullEntity) {
                nullEntity.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                nullEntity.setInitialSpawn(false);
            }
            if (entity instanceof ArmoredHerobrineEntity armoredHerobrineEntity) {
                armoredHerobrineEntity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get()));
            }
        }
    }
}
