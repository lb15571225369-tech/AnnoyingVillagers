package com.pla.annoyingvillagers.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;

public class TimeBombDangYouJianDianJiKongQiShiProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (entity.isShiftKeyDown()) {
                if (levelaccessor instanceof Level) {
                    Level level = (Level)levelaccessor;

                    if (!level.isClientSide()) {
                        level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4defusing")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4defusing")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (entity instanceof Player) {
                    Player player = (Player)entity;

                    player.getCooldowns().addCooldown(itemstack.getItem(), 41);
                }

                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/other/bomb_set\" 0 1");
                }

                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity)entity;

                    if (!livingentity.level.isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 5, false, false));
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
                        if (entity.isShiftKeyDown()) {
                            LevelAccessor levelaccessor1 = this.world;

                            if (levelaccessor1 instanceof Level) {
                                Level level1 = (Level)levelaccessor1;

                                if (!level1.isClientSide()) {
                                    level1.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4click")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                } else {
                                    level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4click")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                }
                            }

                            ((<undefinedtype>)(new Object() {
                                private int ticks = 0;
                                private float waitTicks;
                                private LevelAccessor world;

                                public void start(LevelAccessor levelaccessor2, int i) {
                                    this.waitTicks = (float)i;
                                    MinecraftForge.EVENT_BUS.register(this);
                                    this.world = levelaccessor2;
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
                                    if (entity.isShiftKeyDown()) {
                                        LevelAccessor levelaccessor2 = this.world;

                                        if (levelaccessor2 instanceof Level) {
                                            Level level2 = (Level)levelaccessor2;

                                            if (!level2.isClientSide()) {
                                                level2.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4click")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                            } else {
                                                level2.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4click")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                            }
                                        }

                                        ((<undefinedtype>)(new Object() {
                                            private int ticks;
                                            private float waitTicks;
                                            private LevelAccessor world;

                                            {
                                                this.this$1 = 0;
                                                this.ticks = 0;
                                            }

                                            public void start(LevelAccessor levelaccessor3, int i) {
                                                this.waitTicks = (float)i;
                                                MinecraftForge.EVENT_BUS.register(this);
                                                this.world = levelaccessor3;
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
                                                LevelAccessor levelaccessor3 = this.world;

                                                if (levelaccessor3 instanceof Level) {
                                                    Level level3 = (Level)levelaccessor3;

                                                    if (!level3.isClientSide()) {
                                                        level3.playSound((Player)null, new BlockPos(this.this$1.this$0.val$x, this.this$1.this$0.val$y, this.this$1.this$0.val$z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4click")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                    } else {
                                                        level3.playLocalSound(this.this$1.this$0.val$x, this.this$1.this$0.val$y, this.this$1.this$0.val$z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4click")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                    }
                                                }

                                                ((<undefinedtype>)(new Object() {
                                                    private int ticks;
                                                    private float waitTicks;
                                                    private LevelAccessor world;

                                                    {
                                                        this.this$2 = 0;
                                                        this.ticks = 0;
                                                    }

                                                    public void start(LevelAccessor levelaccessor4, int i) {
                                                        this.waitTicks = (float)i;
                                                        MinecraftForge.EVENT_BUS.register(this);
                                                        this.world = levelaccessor4;
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
                                                        LevelAccessor levelaccessor4 = this.world;

                                                        if (levelaccessor4 instanceof Level) {
                                                            Level level4 = (Level)levelaccessor4;

                                                            if (!level4.isClientSide()) {
                                                                level4.playSound((Player)null, new BlockPos(this.this$2.this$1.this$0.val$x, this.this$2.this$1.this$0.val$y, this.this$2.this$1.this$0.val$z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4click")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                            } else {
                                                                level4.playLocalSound(this.this$2.this$1.this$0.val$x, this.this$2.this$1.this$0.val$y, this.this$2.this$1.this$0.val$z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4click")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                            }
                                                        }

                                                        ((<undefinedtype>)(new Object() {
                                                            private int ticks;
                                                            private float waitTicks;
                                                            private LevelAccessor world;

                                                            {
                                                                this.this$3 = 0;
                                                                this.ticks = 0;
                                                            }

                                                            public void start(LevelAccessor levelaccessor5, int i) {
                                                                this.waitTicks = (float)i;
                                                                MinecraftForge.EVENT_BUS.register(this);
                                                                this.world = levelaccessor5;
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
                                                                LevelAccessor levelaccessor5 = this.world;

                                                                if (levelaccessor5 instanceof Level) {
                                                                    Level level5 = (Level)levelaccessor5;

                                                                    if (!level5.isClientSide()) {
                                                                        level5.playSound((Player)null, new BlockPos(this.this$3.this$2.this$1.this$0.val$x, this.this$3.this$2.this$1.this$0.val$y, this.this$3.this$2.this$1.this$0.val$z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4click")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                    } else {
                                                                        level5.playLocalSound(this.this$3.this$2.this$1.this$0.val$x, this.this$3.this$2.this$1.this$0.val$y, this.this$3.this$2.this$1.this$0.val$z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4click")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                    }
                                                                }

                                                                ((<undefinedtype>)(new Object() {
                                                                    private int ticks;
                                                                    private float waitTicks;
                                                                    private LevelAccessor world;

                                                                    {
                                                                        this.this$4 = 0;
                                                                        this.ticks = 0;
                                                                    }

                                                                    public void start(LevelAccessor levelaccessor6, int i) {
                                                                        this.waitTicks = (float)i;
                                                                        MinecraftForge.EVENT_BUS.register(this);
                                                                        this.world = levelaccessor6;
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
                                                                        LevelAccessor levelaccessor6 = this.world;

                                                                        if (levelaccessor6 instanceof Level) {
                                                                            Level level6 = (Level)levelaccessor6;

                                                                            if (!level6.isClientSide()) {
                                                                                level6.playSound((Player)null, new BlockPos(this.this$4.this$3.this$2.this$1.this$0.val$x, this.this$4.this$3.this$2.this$1.this$0.val$y, this.this$4.this$3.this$2.this$1.this$0.val$z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4click")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                            } else {
                                                                                level6.playLocalSound(this.this$4.this$3.this$2.this$1.this$0.val$x, this.this$4.this$3.this$2.this$1.this$0.val$y, this.this$4.this$3.this$2.this$1.this$0.val$z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4click")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                            }
                                                                        }

                                                                        ((<undefinedtype>)(new Object() {
                                                                            private int ticks;
                                                                            private float waitTicks;
                                                                            private LevelAccessor world;

                                                                            {
                                                                                this.this$5 = 0;
                                                                                this.ticks = 0;
                                                                            }

                                                                            public void start(LevelAccessor levelaccessor7, int i) {
                                                                                this.waitTicks = (float)i;
                                                                                MinecraftForge.EVENT_BUS.register(this);
                                                                                this.world = levelaccessor7;
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
                                                                                LevelAccessor levelaccessor7 = this.world;

                                                                                if (levelaccessor7 instanceof Level) {
                                                                                    Level level7 = (Level)levelaccessor7;

                                                                                    if (!level7.isClientSide()) {
                                                                                        level7.playSound((Player)null, new BlockPos(this.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$5.this$4.this$3.this$2.this$1.this$0.val$z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4click")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                                                    } else {
                                                                                        level7.playLocalSound(this.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$5.this$4.this$3.this$2.this$1.this$0.val$z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:c4click")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                                                    }
                                                                                }

                                                                                ItemStack itemstack1;

                                                                                if (this.this$5.this$4.this$3.this$2.this$1.this$0.val$entity instanceof LivingEntity) {
                                                                                    LivingEntity livingentity1 = (LivingEntity)this.this$5.this$4.this$3.this$2.this$1.this$0.val$entity;

                                                                                    itemstack1 = livingentity1.getMainHandItem();
                                                                                } else {
                                                                                    itemstack1 = ItemStack.EMPTY;
                                                                                }

                                                                                if (itemstack1.getItem() == AnnoyingVillagersModItems.TIME_BOMB.get()) {
                                                                                    Entity entity1 = this.this$5.this$4.this$3.this$2.this$1.this$0.val$entity;

                                                                                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                                                                        entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/other/bomb_after\" 0 1");
                                                                                    }
                                                                                }

                                                                                MinecraftForge.EVENT_BUS.unregister(this);
                                                                            }
                                                                        })).start(this.world, 4);
                                                                        MinecraftForge.EVENT_BUS.unregister(this);
                                                                    }
                                                                })).start(this.world, 4);
                                                                MinecraftForge.EVENT_BUS.unregister(this);
                                                            }
                                                        })).start(this.world, 4);
                                                        MinecraftForge.EVENT_BUS.unregister(this);
                                                    }
                                                })).start(this.world, 6);
                                                MinecraftForge.EVENT_BUS.unregister(this);
                                            }
                                        })).start(this.world, 6);
                                    }

                                    MinecraftForge.EVENT_BUS.unregister(this);
                                }
                            })).start(this.world, 8);
                        }

                        MinecraftForge.EVENT_BUS.unregister(this);
                    }
                })).start(levelaccessor, 8);
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
                        ItemStack itemstack1;

                        if (entity instanceof LivingEntity) {
                            LivingEntity livingentity1 = (LivingEntity)entity;

                            itemstack1 = livingentity1.getMainHandItem();
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        Player player1;

                        if (itemstack1.getItem() == AnnoyingVillagersModItems.TIME_BOMB.get()) {
                            label61: {
                                Player player2;

                                if (entity instanceof Player) {
                                    player1 = (Player)entity;
                                    if (player1.getInventory().contains(new ItemStack((ItemLike)AnnoyingVillagersModItems.TIME_BOMB.get()))) {
                                        if (this.world.getBlockState(new BlockPos(d0, d1, d2)).getBlock() == Blocks.AIR) {
                                            if (entity.isShiftKeyDown()) {
                                                if (this.world.getBlockState(new BlockPos(d0, d1 - 1.0D, d2)).getBlock() == Blocks.AIR) {
                                                    if (entity instanceof Player) {
                                                        player2 = (Player)entity;
                                                        if (!player2.level.isClientSide()) {
                                                            player2.displayClientMessage(new TextComponent("\u4f60\u4e0d\u80fd\u8ba9C4\u6d6e\u7a7a\uff01"), true);
                                                        }
                                                    }
                                                } else {
                                                    if (entity instanceof Player) {
                                                        player2 = (Player)entity;
                                                        ItemStack itemstack2 = new ItemStack((ItemLike)AnnoyingVillagersModItems.TIME_BOMB.get());

                                                        player2.getInventory().clearOrCountMatchingItems((itemstack3) -> {
                                                            return itemstack2.getItem() == itemstack3.getItem();
                                                        }, 1, player2.inventoryMenu.getCraftSlots());
                                                    }

                                                    if (entity instanceof Player) {
                                                        player2 = (Player)entity;
                                                        if (!player2.level.isClientSide()) {
                                                            player2.displayClientMessage(new TextComponent("C4\u70b8\u5f39\u5df2\u5b89\u653e"), true);
                                                        }
                                                    }

                                                    if (entity instanceof Player) {
                                                        player2 = (Player)entity;
                                                        if (!player2.level.isClientSide()) {
                                                            player2.displayClientMessage(new TextComponent("C4\u70b8\u5f39\u5df2\u5b89\u653e"), false);
                                                        }
                                                    }

                                                    this.world.setBlock(new BlockPos(d0, d1, d2), ((Block)AnnoyingVillagersModBlocks.C_4.get()).defaultBlockState(), 3);
                                                }
                                            } else if (entity instanceof Player) {
                                                player2 = (Player)entity;
                                                if (!player2.level.isClientSide()) {
                                                    player2.displayClientMessage(new TextComponent("\u4f60\u8d77\u8eab\u4e86\uff01\u8bf7\u4fdd\u6301\u4e00\u76f4\u6f5c\u884c"), true);
                                                }
                                            }
                                        }
                                        break label61;
                                    }
                                }

                                if (entity instanceof Player) {
                                    player2 = (Player)entity;
                                    if (!player2.level.isClientSide()) {
                                        player2.displayClientMessage(new TextComponent("\u4f60\u4e22\u5f03\u4e86C4\uff01"), true);
                                    }
                                }
                            }
                        } else if (entity instanceof Player) {
                            player1 = (Player)entity;
                            if (!player1.level.isClientSide()) {
                                player1.displayClientMessage(new TextComponent("\u8bf7\u624b\u6301C4\uff01"), true);
                            }
                        }

                        MinecraftForge.EVENT_BUS.unregister(this);
                    }
                })).start(levelaccessor, 45);
            }

        }
    }
}
