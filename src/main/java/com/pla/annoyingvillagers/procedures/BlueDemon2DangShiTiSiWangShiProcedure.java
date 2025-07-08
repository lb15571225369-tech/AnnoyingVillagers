package com.pla.annoyingvillagers.procedures;

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
            if (Math.random() < 0.3D) {
                ((<undefinedtype>)(new Object() {
                    private int ticks = 0;
                    private float waitTicks;
                    private LevelAccessor world;

                    public void start(LevelAccessor levelaccessor1, int i) {
                        this.waitTicks = (float)i;
                        MinecraftForge.EVENT_BUS.register(this);
                        this.world = levelaccessor1;
                    }

                    @SubscribeEvent
                    public void tick(ServerTickEvent servertickevent) {
                        if (servertickevent.phase == Phase.END) {
                            ++this.ticks;
                            if ((float)this.ticks >= this.waitTicks) {
                                this.run();
                            }
                        }

                    }

                    private void run() {
                        if (!this.world.isClientSide() && this.world.getServer() != null) {
                            this.world.getServer().getPlayerList().broadcastMessage(new TextComponent("<\u84dd\u6076\u9b54> \u6211\u8fd8\u6ca1\u6b7b\u5462"), ChatType.SYSTEM, Util.NIL_UUID);
                        }

                        Entity entity1 = entity;

                        if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                            entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "summon annoying_villagers:blue_demon");
                        }

                        MinecraftForge.EVENT_BUS.unregister(this);
                    }
                })).start(levelaccessor, 40);
            } else {
                ((<undefinedtype>)(new Object() {
                    private int ticks = 0;
                    private float waitTicks;
                    private LevelAccessor world;

                    public void start(LevelAccessor levelaccessor1, int i) {
                        this.waitTicks = (float)i;
                        MinecraftForge.EVENT_BUS.register(this);
                        this.world = levelaccessor1;
                    }

                    @SubscribeEvent
                    public void tick(ServerTickEvent servertickevent) {
                        if (servertickevent.phase == Phase.END) {
                            ++this.ticks;
                            if ((float)this.ticks >= this.waitTicks) {
                                this.run();
                            }
                        }

                    }

                    private void run() {
                        if (!this.world.isClientSide() && this.world.getServer() != null) {
                            this.world.getServer().getPlayerList().broadcastMessage(new TextComponent("<\u84dd\u6076\u9b54> \u7ec8\u7a76\u9876\u4e0d\u4f4f\u8fd9\u66b4\u4e71\u4e16\u754c\u7684\u538b\u529b\u2026\u2026"), ChatType.SYSTEM, Util.NIL_UUID);
                        }

                        LevelAccessor levelaccessor1 = this.world;
                        ServerLevel serverlevel;

                        if (levelaccessor1 instanceof ServerLevel) {
                            serverlevel = (ServerLevel)levelaccessor1;
                            ThrownTrident throwntrident = new ThrownTrident(EntityType.TRIDENT, serverlevel);

                            throwntrident.moveTo(d0, d1 + 16.0D, d2, this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                            if (throwntrident instanceof Mob) {
                                Mob mob = (Mob)throwntrident;

                                mob.finalizeSpawn(serverlevel, this.world.getCurrentDifficultyAt(throwntrident.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                            }

                            this.world.addFreshEntity(throwntrident);
                        }

                        levelaccessor1 = this.world;
                        if (levelaccessor1 instanceof ServerLevel) {
                            serverlevel = (ServerLevel)levelaccessor1;
                            LightningBolt lightningbolt = (LightningBolt)EntityType.LIGHTNING_BOLT.create(serverlevel);

                            lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(d0, d1, d2)));
                            lightningbolt.setVisualOnly(true);
                            serverlevel.addFreshEntity(lightningbolt);
                        }

                        levelaccessor1 = this.world;
                        if (levelaccessor1 instanceof Level) {
                            Level level = (Level)levelaccessor1;

                            if (!level.isClientSide()) {
                                ItemEntity itementity = new ItemEntity(level, d0, d1, d2, new ItemStack((ItemLike)AnnoyingVillagersModItems.BLUE_DEMON_CHESTPLATE_CHESTPLATE.get()));

                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }

                        MinecraftForge.EVENT_BUS.unregister(this);
                    }
                })).start(levelaccessor, 30);
            }

        }
    }
}
