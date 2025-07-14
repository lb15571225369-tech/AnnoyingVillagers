package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;

public class BlueDemon2DangShiTiSiWangShiProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            new DelayedTask(30) {
                @Override
                public void run() {
                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                        levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<Blue Demon> Ultimately couldn't withstand the pressure of this chaotic world..."), ChatType.SYSTEM, Util.NIL_UUID);
                    }

                    LevelAccessor levelaccessor1 = levelaccessor;
                    ServerLevel serverlevel;

                    if (levelaccessor1 instanceof ServerLevel) {
                        serverlevel = (ServerLevel)levelaccessor1;
                        ThrownTrident throwntrident = new ThrownTrident(EntityType.TRIDENT, serverlevel);

                        throwntrident.moveTo(d0, d1 + 16.0D, d2, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                        levelaccessor.addFreshEntity(throwntrident);
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof ServerLevel) {
                        serverlevel = (ServerLevel)levelaccessor1;
                        LightningBolt lightningbolt = (LightningBolt)EntityType.LIGHTNING_BOLT.create(serverlevel);

                        lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(d0, d1, d2)));
                        lightningbolt.setVisualOnly(true);
                        serverlevel.addFreshEntity(lightningbolt);
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        Level level = (Level)levelaccessor1;

                        if (!level.isClientSide()) {
                            ItemEntity itementity = new ItemEntity(level, d0, d1, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.BLUE_DEMON_CHESTPLATE_CHESTPLATE.get()));

                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }
                }
            };
        }
    }
}
