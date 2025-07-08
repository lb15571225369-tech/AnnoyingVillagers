package com.pla.annoyingvillagers.procedures;

import java.util.Comparator;
import java.util.Iterator;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;

public class C4ZaiFangKuaiShangYouJianProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            ItemStack itemstack;

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;

                itemstack = livingentity.getMainHandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            Level level;
            Entity entity1;
            Player player;

            if (itemstack.getItem() == Items.SHEARS) {
                if (levelaccessor instanceof Level) {
                    level = (Level)levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4defusing")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4defusing")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                entity1 = (Entity)levelaccessor.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(d0, d1, d2), 32.0D, 32.0D, 32.0D), (player1) -> {
                    return true;
                }).stream().sorted(((<undefinedtype>)(new Object() {
                    Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
                        return Comparator.comparingDouble((entity2) -> {
                            return entity2.distanceToSqr(d3, d4, d5);
                        });
                    }
                })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null);
                if (entity1 instanceof Player) {
                    player = (Player)entity1;
                    if (!player.level.isClientSide()) {
                        player.displayClientMessage(new TextComponent("\u6b63\u5728\u62c6\u9664\uff0c\u8bf7\u4e0d\u8981\u79fb\u52a8"), true);
                    }
                }

                entity1 = (Entity)levelaccessor.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(d0, d1, d2), 32.0D, 32.0D, 32.0D), (player1) -> {
                    return true;
                }).stream().sorted(((<undefinedtype>)(new Object() {
                    Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
                        return Comparator.comparingDouble((entity2) -> {
                            return entity2.distanceToSqr(d3, d4, d5);
                        });
                    }
                })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null);
                if (entity1 instanceof Player) {
                    player = (Player)entity1;
                    if (!player.level.isClientSide()) {
                        player.displayClientMessage(new TextComponent("\u6b63\u5728\u62c6\u9664\uff0c\u8bf7\u4e0d\u8981\u79fb\u52a8"), false);
                    }
                }

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
                        if (entity.isAlive()) {
                            ItemStack itemstack1;

                            if (entity instanceof LivingEntity) {
                                LivingEntity livingentity1 = (LivingEntity)entity;

                                itemstack1 = livingentity1.getMainHandItem();
                            } else {
                                itemstack1 = ItemStack.EMPTY;
                            }

                            Entity entity2;
                            Player player1;

                            if (itemstack1.getItem() == Items.SHEARS) {
                                if (this.world.getBlockState(new BlockPos(d0, d1, d2)).getBlock() == AnnoyingVillagersModBlocks.C_4.get()) {
                                    LevelAccessor levelaccessor1 = this.world;

                                    if (levelaccessor1 instanceof Level) {
                                        Level level1 = (Level)levelaccessor1;

                                        if (!level1.isClientSide()) {
                                            level1.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4defused")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                        } else {
                                            level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4defused")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                        }
                                    }

                                    if (entity instanceof ServerPlayer) {
                                        ServerPlayer serverplayer = (ServerPlayer)entity;
                                        Advancement advancement = serverplayer.server.getAdvancements().getAdvancement(new ResourceLocation("annoying_villagers:onemoretime"));
                                        AdvancementProgress advancementprogress = serverplayer.getAdvancements().getOrStartProgress(advancement);

                                        if (!advancementprogress.isDone()) {
                                            Iterator iterator = advancementprogress.getRemainingCriteria().iterator();

                                            while(iterator.hasNext()) {
                                                serverplayer.getAdvancements().award(advancement, (String)iterator.next());
                                            }
                                        }
                                    }

                                    entity2 = (Entity)this.world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(d0, d1, d2), 32.0D, 32.0D, 32.0D), (player2) -> {
                                        return true;
                                    }).stream().sorted(((<undefinedtype>)(new Object() {
                                        Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
                                            return Comparator.comparingDouble((entity3) -> {
                                                return entity3.distanceToSqr(d3, d4, d5);
                                            });
                                        }
                                    })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null);
                                    if (entity2 instanceof Player) {
                                        player1 = (Player)entity2;
                                        if (!player1.level.isClientSide()) {
                                            player1.displayClientMessage(new TextComponent("C4\u70b8\u5f39\u5df2\u88ab\u62c6\u9664"), true);
                                        }
                                    }

                                    entity2 = (Entity)this.world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(d0, d1, d2), 32.0D, 32.0D, 32.0D), (player2) -> {
                                        return true;
                                    }).stream().sorted(((<undefinedtype>)(new Object() {
                                        Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
                                            return Comparator.comparingDouble((entity3) -> {
                                                return entity3.distanceToSqr(d3, d4, d5);
                                            });
                                        }
                                    })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null);
                                    if (entity2 instanceof Player) {
                                        player1 = (Player)entity2;
                                        if (!player1.level.isClientSide()) {
                                            player1.displayClientMessage(new TextComponent("C4\u70b8\u5f39\u5df2\u88ab\u62c6\u9664"), false);
                                        }
                                    }

                                    Entity entity3 = entity;

                                    if (!entity3.level.isClientSide() && entity3.getServer() != null) {
                                        entity3.getServer().getCommands().performCommand(entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4), "tellraw @a[team=T] {\"text\":\"C4\u70b8\u5f39\u5df2\u88ab\u62c6\u9664\",\"color\":\"red\"}");
                                    }

                                    entity3 = entity;
                                    if (!entity3.level.isClientSide() && entity3.getServer() != null) {
                                        entity3.getServer().getCommands().performCommand(entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4), "tellraw @a[team=CT] {\"text\":\"C4\u70b8\u5f39\u5df2\u88ab\u62c6\u9664\",\"color\":\"green\"}");
                                    }

                                    this.world.setBlock(new BlockPos(d0, d1, d2), ((Block)AnnoyingVillagersModBlocks.C_4DAMAGE.get()).defaultBlockState(), 3);
                                } else {
                                    entity2 = (Entity)this.world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(d0, d1, d2), 32.0D, 32.0D, 32.0D), (player2) -> {
                                        return true;
                                    }).stream().sorted(((<undefinedtype>)(new Object() {
                                        Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
                                            return Comparator.comparingDouble((entity4) -> {
                                                return entity4.distanceToSqr(d3, d4, d5);
                                            });
                                        }
                                    })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null);
                                    if (entity2 instanceof Player) {
                                        player1 = (Player)entity2;
                                        if (!player1.level.isClientSide()) {
                                            player1.displayClientMessage(new TextComponent("C4\u70b8\u5f39\u62c6\u9664\u5931\u8d25"), true);
                                        }
                                    }

                                    entity2 = (Entity)this.world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(d0, d1, d2), 32.0D, 32.0D, 32.0D), (player2) -> {
                                        return true;
                                    }).stream().sorted(((<undefinedtype>)(new Object() {
                                        Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
                                            return Comparator.comparingDouble((entity4) -> {
                                                return entity4.distanceToSqr(d3, d4, d5);
                                            });
                                        }
                                    })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null);
                                    if (entity2 instanceof Player) {
                                        player1 = (Player)entity2;
                                        if (!player1.level.isClientSide()) {
                                            player1.displayClientMessage(new TextComponent("C4\u70b8\u5f39\u62c6\u9664\u5931\u8d25"), false);
                                        }
                                    }
                                }
                            } else {
                                entity2 = (Entity)this.world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(d0, d1, d2), 32.0D, 32.0D, 32.0D), (player2) -> {
                                    return true;
                                }).stream().sorted(((<undefinedtype>)(new Object() {
                                    Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
                                        return Comparator.comparingDouble((entity4) -> {
                                            return entity4.distanceToSqr(d3, d4, d5);
                                        });
                                    }
                                })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null);
                                if (entity2 instanceof Player) {
                                    player1 = (Player)entity2;
                                    if (!player1.level.isClientSide()) {
                                        player1.displayClientMessage(new TextComponent("\u4f60\u53d6\u6d88\u4e86\u62c6\u9664"), false);
                                    }
                                }

                                entity2 = (Entity)this.world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(d0, d1, d2), 32.0D, 32.0D, 32.0D), (player2) -> {
                                    return true;
                                }).stream().sorted(((<undefinedtype>)(new Object() {
                                    Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
                                        return Comparator.comparingDouble((entity4) -> {
                                            return entity4.distanceToSqr(d3, d4, d5);
                                        });
                                    }
                                })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null);
                                if (entity2 instanceof Player) {
                                    player1 = (Player)entity2;
                                    if (!player1.level.isClientSide()) {
                                        player1.displayClientMessage(new TextComponent("\u4f60\u53d6\u6d88\u4e86\u62c6\u9664"), true);
                                    }
                                }
                            }
                        }

                        MinecraftForge.EVENT_BUS.unregister(this);
                    }
                })).start(levelaccessor, 80);
            } else {
                if (levelaccessor instanceof Level) {
                    level = (Level)levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4defusing")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4defusing")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                entity1 = (Entity)levelaccessor.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(d0, d1, d2), 32.0D, 32.0D, 32.0D), (player1) -> {
                    return true;
                }).stream().sorted(((<undefinedtype>)(new Object() {
                    Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
                        return Comparator.comparingDouble((entity2) -> {
                            return entity2.distanceToSqr(d3, d4, d5);
                        });
                    }
                })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null);
                if (entity1 instanceof Player) {
                    player = (Player)entity1;
                    if (!player.level.isClientSide()) {
                        player.displayClientMessage(new TextComponent("\u6b63\u5728\u62c6\u9664\uff0c\u8bf7\u4e0d\u8981\u79fb\u52a8"), true);
                    }
                }

                entity1 = (Entity)levelaccessor.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(d0, d1, d2), 32.0D, 32.0D, 32.0D), (player1) -> {
                    return true;
                }).stream().sorted(((<undefinedtype>)(new Object() {
                    Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
                        return Comparator.comparingDouble((entity2) -> {
                            return entity2.distanceToSqr(d3, d4, d5);
                        });
                    }
                })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null);
                if (entity1 instanceof Player) {
                    player = (Player)entity1;
                    if (!player.level.isClientSide()) {
                        player.displayClientMessage(new TextComponent("\u6b63\u5728\u62c6\u9664\uff0c\u8bf7\u4e0d\u8981\u79fb\u52a8"), false);
                    }
                }

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
                        if (entity.isAlive()) {
                            Entity entity2;
                            Player player1;

                            if (this.world.getBlockState(new BlockPos(d0, d1, d2)).getBlock() == AnnoyingVillagersModBlocks.C_4.get()) {
                                LevelAccessor levelaccessor1 = this.world;

                                if (levelaccessor1 instanceof Level) {
                                    Level level1 = (Level)levelaccessor1;

                                    if (!level1.isClientSide()) {
                                        level1.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4defused")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                    } else {
                                        level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4defused")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                    }
                                }

                                if (entity instanceof ServerPlayer) {
                                    ServerPlayer serverplayer = (ServerPlayer)entity;
                                    Advancement advancement = serverplayer.server.getAdvancements().getAdvancement(new ResourceLocation("annoying_villagers:onemoretime"));
                                    AdvancementProgress advancementprogress = serverplayer.getAdvancements().getOrStartProgress(advancement);

                                    if (!advancementprogress.isDone()) {
                                        Iterator iterator = advancementprogress.getRemainingCriteria().iterator();

                                        while(iterator.hasNext()) {
                                            serverplayer.getAdvancements().award(advancement, (String)iterator.next());
                                        }
                                    }
                                }

                                Entity entity3 = entity;

                                if (!entity3.level.isClientSide() && entity3.getServer() != null) {
                                    entity3.getServer().getCommands().performCommand(entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4), "tellraw @a[team=T] {\"text\":\"C4\u70b8\u5f39\u5df2\u88ab\u62c6\u9664\",\"color\":\"red\"}");
                                }

                                entity3 = entity;
                                if (!entity3.level.isClientSide() && entity3.getServer() != null) {
                                    entity3.getServer().getCommands().performCommand(entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4), "tellraw @a[team=CT] {\"text\":\"C4\u70b8\u5f39\u5df2\u88ab\u62c6\u9664\",\"color\":\"green\"}");
                                }

                                entity2 = (Entity)this.world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(d0, d1, d2), 32.0D, 32.0D, 32.0D), (player2) -> {
                                    return true;
                                }).stream().sorted(((<undefinedtype>)(new Object() {
                                    Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
                                        return Comparator.comparingDouble((entity4) -> {
                                            return entity4.distanceToSqr(d3, d4, d5);
                                        });
                                    }
                                })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null);
                                if (entity2 instanceof Player) {
                                    player1 = (Player)entity2;
                                    if (!player1.level.isClientSide()) {
                                        player1.displayClientMessage(new TextComponent("C4\u70b8\u5f39\u5df2\u88ab\u62c6\u9664"), true);
                                    }
                                }

                                entity2 = (Entity)this.world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(d0, d1, d2), 32.0D, 32.0D, 32.0D), (player2) -> {
                                    return true;
                                }).stream().sorted(((<undefinedtype>)(new Object() {
                                    Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
                                        return Comparator.comparingDouble((entity4) -> {
                                            return entity4.distanceToSqr(d3, d4, d5);
                                        });
                                    }
                                })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null);
                                if (entity2 instanceof Player) {
                                    player1 = (Player)entity2;
                                    if (!player1.level.isClientSide()) {
                                        player1.displayClientMessage(new TextComponent("C4\u70b8\u5f39\u5df2\u88ab\u62c6\u9664"), false);
                                    }
                                }

                                this.world.setBlock(new BlockPos(d0, d1, d2), ((Block)AnnoyingVillagersModBlocks.C_4DAMAGE.get()).defaultBlockState(), 3);
                            } else {
                                entity2 = (Entity)this.world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(d0, d1, d2), 32.0D, 32.0D, 32.0D), (player2) -> {
                                    return true;
                                }).stream().sorted(((<undefinedtype>)(new Object() {
                                    Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
                                        return Comparator.comparingDouble((entity4) -> {
                                            return entity4.distanceToSqr(d3, d4, d5);
                                        });
                                    }
                                })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null);
                                if (entity2 instanceof Player) {
                                    player1 = (Player)entity2;
                                    if (!player1.level.isClientSide()) {
                                        player1.displayClientMessage(new TextComponent("C4\u70b8\u5f39\u62c6\u9664\u5931\u8d25"), true);
                                    }
                                }

                                entity2 = (Entity)this.world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(d0, d1, d2), 32.0D, 32.0D, 32.0D), (player2) -> {
                                    return true;
                                }).stream().sorted(((<undefinedtype>)(new Object() {
                                    Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
                                        return Comparator.comparingDouble((entity4) -> {
                                            return entity4.distanceToSqr(d3, d4, d5);
                                        });
                                    }
                                })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null);
                                if (entity2 instanceof Player) {
                                    player1 = (Player)entity2;
                                    if (!player1.level.isClientSide()) {
                                        player1.displayClientMessage(new TextComponent("C4\u70b8\u5f39\u62c6\u9664\u5931\u8d25"), false);
                                    }
                                }
                            }
                        }

                        MinecraftForge.EVENT_BUS.unregister(this);
                    }
                })).start(levelaccessor, 200);
            }

        }
    }
}
