package com.pla.annoyingvillagers.procedures;

import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModGameRules;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;

@EventBusSubscriber
public class EnchantbeddeathProcedure {

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent livingdeathevent) {
        if (livingdeathevent != null && livingdeathevent.getEntity() != null) {
            execute(livingdeathevent, livingdeathevent.getEntity().level, livingdeathevent.getEntity(), livingdeathevent.getSource().getEntity());
        }

    }

    public static void execute(LevelAccessor levelaccessor, Entity entity, Entity entity1) {
        execute((Event) null, levelaccessor, entity, entity1);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, final Entity entity, final Entity entity1) {
        if (entity != null && entity1 != null) {
            LivingEntity livingentity;
            LivingEntity livingentity1;
            boolean flag;
            Player player;

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                if (livingentity.hasEffect((MobEffect)AnnoyingVillagersModMobEffects.ENCHANT_BED_EFFECT.get())) {
                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity)entity;
                        flag = livingentity1.hasEffect((MobEffect)AnnoyingVillagersModMobEffects.EC.get());
                    } else {
                        flag = false;
                    }

                    if (!flag) {
                        if (event != null && event.isCancelable()) {
                            event.setCanceled(true);
                        }

                        if (entity instanceof Player) {
                            player = (Player)entity;
                            player.closeContainer();
                        }

                        ServerPlayer serverplayer;
                        double d0;
                        label179: {
                            if (entity instanceof ServerPlayer) {
                                serverplayer = (ServerPlayer)entity;
                                if (!serverplayer.level.isClientSide()) {
                                    d0 = (double)(serverplayer.getRespawnDimension().equals(serverplayer.level.dimension()) && serverplayer.getRespawnPosition() != null ? serverplayer.getRespawnPosition().getX() : serverplayer.level.getLevelData().getXSpawn());
                                    break label179;
                                }
                            }

                            d0 = 0.0D;
                        }

                        int i;
                        label169: {
                            if (entity instanceof ServerPlayer) {
                                serverplayer = (ServerPlayer)entity;
                                if (!serverplayer.level.isClientSide()) {
                                    i = serverplayer.getRespawnDimension().equals(serverplayer.level.dimension()) && serverplayer.getRespawnPosition() != null ? serverplayer.getRespawnPosition().getY() : serverplayer.level.getLevelData().getYSpawn();
                                    break label169;
                                }
                            }

                            i = 0;
                        }

                        double d1;
                        double d2;
                        label160: {
                            d1 = (double)(i + 1);
                            if (entity instanceof ServerPlayer) {
                                serverplayer = (ServerPlayer)entity;
                                if (!serverplayer.level.isClientSide()) {
                                    d2 = (double)(serverplayer.getRespawnDimension().equals(serverplayer.level.dimension()) && serverplayer.getRespawnPosition() != null ? serverplayer.getRespawnPosition().getZ() : serverplayer.level.getLevelData().getZSpawn());
                                    break label160;
                                }
                            }

                            d2 = 0.0D;
                        }

                        entity.teleportTo(d0, d1, d2);
                        if (entity instanceof ServerPlayer) {
                            ServerGamePacketListenerImpl servergamepacketlistenerimpl;
                            label148: {
                                serverplayer = (ServerPlayer)entity;
                                servergamepacketlistenerimpl = serverplayer.connection;
                                if (entity instanceof ServerPlayer) {
                                    ServerPlayer serverplayer1 = (ServerPlayer)entity;

                                    if (!serverplayer1.level.isClientSide()) {
                                        d0 = (double)(serverplayer1.getRespawnDimension().equals(serverplayer1.level.dimension()) && serverplayer1.getRespawnPosition() != null ? serverplayer1.getRespawnPosition().getX() : serverplayer1.level.getLevelData().getXSpawn());
                                        break label148;
                                    }
                                }

                                d0 = 0.0D;
                            }

                            label138: {
                                if (entity instanceof ServerPlayer) {
                                    ServerPlayer serverplayer2 = (ServerPlayer)entity;

                                    if (!serverplayer2.level.isClientSide()) {
                                        i = serverplayer2.getRespawnDimension().equals(serverplayer2.level.dimension()) && serverplayer2.getRespawnPosition() != null ? serverplayer2.getRespawnPosition().getY() : serverplayer2.level.getLevelData().getYSpawn();
                                        break label138;
                                    }
                                }

                                i = 0;
                            }

                            label129: {
                                d1 = (double)(i + 1);
                                if (entity instanceof ServerPlayer) {
                                    ServerPlayer serverplayer3 = (ServerPlayer)entity;

                                    if (!serverplayer3.level.isClientSide()) {
                                        d2 = (double)(serverplayer3.getRespawnDimension().equals(serverplayer3.level.dimension()) && serverplayer3.getRespawnPosition() != null ? serverplayer3.getRespawnPosition().getZ() : serverplayer3.level.getLevelData().getZSpawn());
                                        break label129;
                                    }
                                }

                                d2 = 0.0D;
                            }

                            servergamepacketlistenerimpl.teleport(d0, d1, d2, entity.getYRot(), entity.getXRot());
                        }

                        LivingEntity livingentity2;

                        if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity)entity;
                            livingentity2.setHealth(20.0F);
                        }

                        if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity)entity;
                            livingentity2.removeEffect((MobEffect)AnnoyingVillagersModMobEffects.ENCHANT_BED_EFFECT.get());
                        }

                        if (entity instanceof Player) {
                            player = (Player)entity;
                            if (!player.level.isClientSide()) {
                                player.displayClientMessage(new TextComponent("\u4f60\u7684\u9644\u9b54\u5e8a\u6551\u4e86\u4f60\u4e00\u6b21\uff0c\u5982\u9700\u518d\u6b21\u4f7f\u7528\u8bf7\u518d\u6b21\u53f3\u952e\uff01"), true);
                            }
                        }
                    }
                }
            }

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                if (livingentity.isHolding(Items.TOTEM_OF_UNDYING)) {
                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity)entity;
                        flag = livingentity1.hasEffect((MobEffect)AnnoyingVillagersModMobEffects.EC.get());
                    } else {
                        flag = false;
                    }

                    if (!flag) {
                        entity.kill();
                        if (entity instanceof Player) {
                            player = (Player)entity;
                            ItemStack itemstack = new ItemStack(Items.TOTEM_OF_UNDYING);

                            player.getInventory().clearOrCountMatchingItems((itemstack1) -> {
                                return itemstack.getItem() == itemstack1.getItem();
                            }, 1, player.inventoryMenu.getCraftSlots());
                        }
                    }
                }
            }

            if (levelaccessor.getLevelData().getGameRules().getBoolean(AnnoyingVillagersModGameRules.KILL_PLAYER_RETURN) && entity instanceof Player && entity1 instanceof Player) {
                if (entity1.getPersistentData().getString("kill_return").equals(entity.getUUID().toString())) {
                    if (entity1 instanceof ServerPlayer) {
                        ServerPlayer serverplayer4 = (ServerPlayer)entity1;
                        Advancement advancement = serverplayer4.server.getAdvancements().getAdvancement(new ResourceLocation("annoying_villagers:kill_return"));
                        AdvancementProgress advancementprogress = serverplayer4.getAdvancements().getOrStartProgress(advancement);

                        if (!advancementprogress.isDone()) {
                            Iterator iterator = advancementprogress.getRemainingCriteria().iterator();

                            while(iterator.hasNext()) {
                                serverplayer4.getAdvancements().award(advancement, (String)iterator.next());
                            }
                        }
                    }
                } else {
                    ((<undefinedtype>)(new Object() {
                        private int ticks = 0;
                        private float waitTicks;
                        private LevelAccessor world;

                        public void start(LevelAccessor levelaccessor1, int j) {
                            this.waitTicks = (float)j;
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
                            entity.getPersistentData().putString("kill_return", entity1.getUUID().toString());
                            MinecraftForge.EVENT_BUS.unregister(this);
                        }
                    })).start(levelaccessor, 20);
                }
            }

        }
    }
}
