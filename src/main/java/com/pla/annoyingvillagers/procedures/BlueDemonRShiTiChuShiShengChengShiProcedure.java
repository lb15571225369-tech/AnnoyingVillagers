package com.pla.annoyingvillagers.procedures;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import com.pla.annoyingvillagers.entity.BdTridentEntity;
import com.pla.annoyingvillagers.entity.BlueDemonEndEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;

public class BlueDemonRShiTiChuShiShengChengShiProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            Vec3 vec3 = new Vec3(d0, d1, d2);
            List<Entity> list = (List)levelaccessor.getEntitiesOfClass(Entity.class, (new AABB(vec3, vec3)).inflate(32.0D), (entity1) -> {
                return true;
            }).stream().sorted(Comparator.comparingDouble((entity1) -> {
                return entity1.distanceToSqr(vec3);
            })).collect(Collectors.toList());
            Iterator iterator = list.iterator();

            while(iterator.hasNext()) {
                Entity entity1 = (Entity)iterator.next();

                if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                    entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "impactful @s shake 400 5 5");
                }
            }

            ItemStack itemstack;

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;

                itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 4);
            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect give @s annoying_villagersbychentu:fulu 20000 0 true");
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "item replace entity @s weapon.mainhand with annoying_villagersbychentu:bluedemontrident");
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "item replace entity @s weapon.offhand with annoying_villagersbychentu:bluedemontrident");
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/other/blue_demon_start_skill\" 0 1");
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
                    LevelAccessor levelaccessor1 = this.world;

                    if (levelaccessor1 instanceof Level) {
                        Level level = (Level)levelaccessor1;

                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:tridentfs_skill")), SoundSource.NEUTRAL, 5.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:tridentfs_skill")), SoundSource.NEUTRAL, 5.0F, 1.0F, false);
                        }
                    }

                    levelaccessor1 = this.world;
                    ServerLevel serverlevel;
                    BdTridentEntity bdtridententity;
                    Mob mob;

                    if (levelaccessor1 instanceof ServerLevel) {
                        serverlevel = (ServerLevel)levelaccessor1;
                        bdtridententity = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel);
                        bdtridententity.moveTo(d0 + (double)Mth.nextInt(new Random(), -3, 3), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -3, 3), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                        if (bdtridententity instanceof Mob) {
                            mob = (Mob)bdtridententity;
                            mob.finalizeSpawn(serverlevel, this.world.getCurrentDifficultyAt(bdtridententity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                        }

                        this.world.addFreshEntity(bdtridententity);
                    }

                    levelaccessor1 = this.world;
                    if (levelaccessor1 instanceof ServerLevel) {
                        serverlevel = (ServerLevel)levelaccessor1;
                        bdtridententity = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel);
                        bdtridententity.moveTo(d0 + (double)Mth.nextInt(new Random(), -3, 3), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -3, 3), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                        if (bdtridententity instanceof Mob) {
                            mob = (Mob)bdtridententity;
                            mob.finalizeSpawn(serverlevel, this.world.getCurrentDifficultyAt(bdtridententity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                        }

                        this.world.addFreshEntity(bdtridententity);
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
                            if (!this.world.isClientSide() && this.world.getServer() != null) {
                                this.world.getServer().getPlayerList().broadcastMessage(new TextComponent("<\u84dd\u6076\u9b54> \u4e09\u53c9\u621f\u72c2\u6b22\u8282\uff01\uff01\uff01"), ChatType.SYSTEM, Util.NIL_UUID);
                            }

                            LevelAccessor levelaccessor2 = this.world;

                            if (levelaccessor2 instanceof ServerLevel) {
                                ServerLevel serverlevel1 = (ServerLevel)levelaccessor2;
                                BdTridentEntity bdtridententity1 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel1);

                                bdtridententity1.moveTo(d0 + (double)Mth.nextInt(new Random(), -10, 10), d1 - 1.0D, d2 + (double)Mth.nextInt(new Random(), -10, 10), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                if (bdtridententity1 instanceof Mob) {
                                    Mob mob1 = (Mob)bdtridententity1;

                                    mob1.finalizeSpawn(serverlevel1, this.world.getCurrentDifficultyAt(bdtridententity1.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                }

                                this.world.addFreshEntity(bdtridententity1);
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
                                    ServerLevel serverlevel2;
                                    BdTridentEntity bdtridententity2;
                                    Mob mob2;

                                    if (levelaccessor3 instanceof ServerLevel) {
                                        serverlevel2 = (ServerLevel)levelaccessor3;
                                        bdtridententity2 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel2);
                                        bdtridententity2.moveTo(this.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -10, 10), this.this$1.this$0.val$y - 1.0D, this.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -10, 10), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                        if (bdtridententity2 instanceof Mob) {
                                            mob2 = (Mob)bdtridententity2;
                                            mob2.finalizeSpawn(serverlevel2, this.world.getCurrentDifficultyAt(bdtridententity2.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                        }

                                        this.world.addFreshEntity(bdtridententity2);
                                    }

                                    levelaccessor3 = this.world;
                                    if (levelaccessor3 instanceof ServerLevel) {
                                        serverlevel2 = (ServerLevel)levelaccessor3;
                                        bdtridententity2 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel2);
                                        bdtridententity2.moveTo(this.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -10, 10), this.this$1.this$0.val$y - 1.0D, this.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -10, 10), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                        if (bdtridententity2 instanceof Mob) {
                                            mob2 = (Mob)bdtridententity2;
                                            mob2.finalizeSpawn(serverlevel2, this.world.getCurrentDifficultyAt(bdtridententity2.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                        }

                                        this.world.addFreshEntity(bdtridententity2);
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
                                            ServerLevel serverlevel3;
                                            BdTridentEntity bdtridententity3;
                                            Mob mob3;

                                            if (levelaccessor4 instanceof ServerLevel) {
                                                serverlevel3 = (ServerLevel)levelaccessor4;
                                                bdtridententity3 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel3);
                                                bdtridententity3.moveTo(this.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -10, 10), this.this$2.this$1.this$0.val$y - 1.0D, this.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -10, 10), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                                if (bdtridententity3 instanceof Mob) {
                                                    mob3 = (Mob)bdtridententity3;
                                                    mob3.finalizeSpawn(serverlevel3, this.world.getCurrentDifficultyAt(bdtridententity3.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                }

                                                this.world.addFreshEntity(bdtridententity3);
                                            }

                                            levelaccessor4 = this.world;
                                            if (levelaccessor4 instanceof ServerLevel) {
                                                serverlevel3 = (ServerLevel)levelaccessor4;
                                                bdtridententity3 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel3);
                                                bdtridententity3.moveTo(this.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -10, 10), this.this$2.this$1.this$0.val$y - 1.0D, this.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -10, 10), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                                if (bdtridententity3 instanceof Mob) {
                                                    mob3 = (Mob)bdtridententity3;
                                                    mob3.finalizeSpawn(serverlevel3, this.world.getCurrentDifficultyAt(bdtridententity3.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                }

                                                this.world.addFreshEntity(bdtridententity3);
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
                                                    ServerLevel serverlevel4;
                                                    BdTridentEntity bdtridententity4;
                                                    Mob mob4;

                                                    if (levelaccessor5 instanceof ServerLevel) {
                                                        serverlevel4 = (ServerLevel)levelaccessor5;
                                                        bdtridententity4 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel4);
                                                        bdtridententity4.moveTo(this.this$3.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -10, 10), this.this$3.this$2.this$1.this$0.val$y - 1.0D, this.this$3.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -10, 10), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                                        if (bdtridententity4 instanceof Mob) {
                                                            mob4 = (Mob)bdtridententity4;
                                                            mob4.finalizeSpawn(serverlevel4, this.world.getCurrentDifficultyAt(bdtridententity4.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                        }

                                                        this.world.addFreshEntity(bdtridententity4);
                                                    }

                                                    levelaccessor5 = this.world;
                                                    if (levelaccessor5 instanceof ServerLevel) {
                                                        serverlevel4 = (ServerLevel)levelaccessor5;
                                                        bdtridententity4 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel4);
                                                        bdtridententity4.moveTo(this.this$3.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -10, 10), this.this$3.this$2.this$1.this$0.val$y - 1.0D, this.this$3.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), 3, 10), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                                        if (bdtridententity4 instanceof Mob) {
                                                            mob4 = (Mob)bdtridententity4;
                                                            mob4.finalizeSpawn(serverlevel4, this.world.getCurrentDifficultyAt(bdtridententity4.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                        }

                                                        this.world.addFreshEntity(bdtridententity4);
                                                    }

                                                    levelaccessor5 = this.world;
                                                    if (levelaccessor5 instanceof ServerLevel) {
                                                        serverlevel4 = (ServerLevel)levelaccessor5;
                                                        bdtridententity4 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel4);
                                                        bdtridententity4.moveTo(this.this$3.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -10, 10), this.this$3.this$2.this$1.this$0.val$y - 1.0D, this.this$3.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), 3, 10), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                                        if (bdtridententity4 instanceof Mob) {
                                                            mob4 = (Mob)bdtridententity4;
                                                            mob4.finalizeSpawn(serverlevel4, this.world.getCurrentDifficultyAt(bdtridententity4.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                        }

                                                        this.world.addFreshEntity(bdtridententity4);
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
                                                            ServerLevel serverlevel5;
                                                            BdTridentEntity bdtridententity5;
                                                            Mob mob5;

                                                            if (levelaccessor6 instanceof ServerLevel) {
                                                                serverlevel5 = (ServerLevel)levelaccessor6;
                                                                bdtridententity5 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel5);
                                                                bdtridententity5.moveTo(this.this$4.this$3.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -10, 10), this.this$4.this$3.this$2.this$1.this$0.val$y - 1.0D, this.this$4.this$3.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -10, 10), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                if (bdtridententity5 instanceof Mob) {
                                                                    mob5 = (Mob)bdtridententity5;
                                                                    mob5.finalizeSpawn(serverlevel5, this.world.getCurrentDifficultyAt(bdtridententity5.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                }

                                                                this.world.addFreshEntity(bdtridententity5);
                                                            }

                                                            levelaccessor6 = this.world;
                                                            if (levelaccessor6 instanceof ServerLevel) {
                                                                serverlevel5 = (ServerLevel)levelaccessor6;
                                                                bdtridententity5 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel5);
                                                                bdtridententity5.moveTo(this.this$4.this$3.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -10, 10), this.this$4.this$3.this$2.this$1.this$0.val$y - 1.0D, this.this$4.this$3.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), 3, 10), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                if (bdtridententity5 instanceof Mob) {
                                                                    mob5 = (Mob)bdtridententity5;
                                                                    mob5.finalizeSpawn(serverlevel5, this.world.getCurrentDifficultyAt(bdtridententity5.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                }

                                                                this.world.addFreshEntity(bdtridententity5);
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
                                                                    ServerLevel serverlevel6;
                                                                    BdTridentEntity bdtridententity6;
                                                                    Mob mob6;

                                                                    if (levelaccessor7 instanceof ServerLevel) {
                                                                        serverlevel6 = (ServerLevel)levelaccessor7;
                                                                        bdtridententity6 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel6);
                                                                        bdtridententity6.moveTo(this.this$5.this$4.this$3.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -10, 10), this.this$5.this$4.this$3.this$2.this$1.this$0.val$y - 1.0D, this.this$5.this$4.this$3.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -10, 10), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                        if (bdtridententity6 instanceof Mob) {
                                                                            mob6 = (Mob)bdtridententity6;
                                                                            mob6.finalizeSpawn(serverlevel6, this.world.getCurrentDifficultyAt(bdtridententity6.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                        }

                                                                        this.world.addFreshEntity(bdtridententity6);
                                                                    }

                                                                    levelaccessor7 = this.world;
                                                                    if (levelaccessor7 instanceof ServerLevel) {
                                                                        serverlevel6 = (ServerLevel)levelaccessor7;
                                                                        bdtridententity6 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel6);
                                                                        bdtridententity6.moveTo(this.this$5.this$4.this$3.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -10, 10), this.this$5.this$4.this$3.this$2.this$1.this$0.val$y - 1.0D, this.this$5.this$4.this$3.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -10, 10), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                        if (bdtridententity6 instanceof Mob) {
                                                                            mob6 = (Mob)bdtridententity6;
                                                                            mob6.finalizeSpawn(serverlevel6, this.world.getCurrentDifficultyAt(bdtridententity6.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                        }

                                                                        this.world.addFreshEntity(bdtridententity6);
                                                                    }

                                                                    levelaccessor7 = this.world;
                                                                    if (levelaccessor7 instanceof ServerLevel) {
                                                                        serverlevel6 = (ServerLevel)levelaccessor7;
                                                                        bdtridententity6 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel6);
                                                                        bdtridententity6.moveTo(this.this$5.this$4.this$3.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -10, 10), this.this$5.this$4.this$3.this$2.this$1.this$0.val$y - 1.0D, this.this$5.this$4.this$3.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -10, 10), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                        if (bdtridententity6 instanceof Mob) {
                                                                            mob6 = (Mob)bdtridententity6;
                                                                            mob6.finalizeSpawn(serverlevel6, this.world.getCurrentDifficultyAt(bdtridententity6.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                        }

                                                                        this.world.addFreshEntity(bdtridententity6);
                                                                    }

                                                                    levelaccessor7 = this.world;
                                                                    if (levelaccessor7 instanceof ServerLevel) {
                                                                        serverlevel6 = (ServerLevel)levelaccessor7;
                                                                        bdtridententity6 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel6);
                                                                        bdtridententity6.moveTo(this.this$5.this$4.this$3.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -10, 10), this.this$5.this$4.this$3.this$2.this$1.this$0.val$y - 1.0D, this.this$5.this$4.this$3.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -10, 10), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                        if (bdtridententity6 instanceof Mob) {
                                                                            mob6 = (Mob)bdtridententity6;
                                                                            mob6.finalizeSpawn(serverlevel6, this.world.getCurrentDifficultyAt(bdtridententity6.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                        }

                                                                        this.world.addFreshEntity(bdtridententity6);
                                                                    }

                                                                    ((<undefinedtype>)(new Object() {
                                                                        private int ticks;
                                                                        private float waitTicks;
                                                                        private LevelAccessor world;

                                                                        {
                                                                            this.this$6 = 0;
                                                                            this.ticks = 0;
                                                                        }

                                                                        public void start(LevelAccessor levelaccessor8, int i) {
                                                                            this.waitTicks = (float)i;
                                                                            MinecraftForge.EVENT_BUS.register(this);
                                                                            this.world = levelaccessor8;
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
                                                                            LevelAccessor levelaccessor8 = this.world;
                                                                            ServerLevel serverlevel7;
                                                                            BdTridentEntity bdtridententity7;
                                                                            Mob mob7;

                                                                            if (levelaccessor8 instanceof ServerLevel) {
                                                                                serverlevel7 = (ServerLevel)levelaccessor8;
                                                                                bdtridententity7 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel7);
                                                                                bdtridententity7.moveTo(this.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -10, 10), this.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y - 1.0D, this.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -10, 10), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                                if (bdtridententity7 instanceof Mob) {
                                                                                    mob7 = (Mob)bdtridententity7;
                                                                                    mob7.finalizeSpawn(serverlevel7, this.world.getCurrentDifficultyAt(bdtridententity7.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                                }

                                                                                this.world.addFreshEntity(bdtridententity7);
                                                                            }

                                                                            levelaccessor8 = this.world;
                                                                            if (levelaccessor8 instanceof ServerLevel) {
                                                                                serverlevel7 = (ServerLevel)levelaccessor8;
                                                                                bdtridententity7 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel7);
                                                                                bdtridententity7.moveTo(this.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -20, -20), this.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y - 1.0D, this.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -20, -20), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                                if (bdtridententity7 instanceof Mob) {
                                                                                    mob7 = (Mob)bdtridententity7;
                                                                                    mob7.finalizeSpawn(serverlevel7, this.world.getCurrentDifficultyAt(bdtridententity7.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                                }

                                                                                this.world.addFreshEntity(bdtridententity7);
                                                                            }

                                                                            levelaccessor8 = this.world;
                                                                            if (levelaccessor8 instanceof ServerLevel) {
                                                                                serverlevel7 = (ServerLevel)levelaccessor8;
                                                                                bdtridententity7 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel7);
                                                                                bdtridententity7.moveTo(this.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -20, -20), this.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y - 1.0D, this.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -20, -20), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                                if (bdtridententity7 instanceof Mob) {
                                                                                    mob7 = (Mob)bdtridententity7;
                                                                                    mob7.finalizeSpawn(serverlevel7, this.world.getCurrentDifficultyAt(bdtridententity7.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                                }

                                                                                this.world.addFreshEntity(bdtridententity7);
                                                                            }

                                                                            levelaccessor8 = this.world;
                                                                            if (levelaccessor8 instanceof ServerLevel) {
                                                                                serverlevel7 = (ServerLevel)levelaccessor8;
                                                                                bdtridententity7 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel7);
                                                                                bdtridententity7.moveTo(this.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -20, -20), this.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y - 1.0D, this.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -20, -20), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                                if (bdtridententity7 instanceof Mob) {
                                                                                    mob7 = (Mob)bdtridententity7;
                                                                                    mob7.finalizeSpawn(serverlevel7, this.world.getCurrentDifficultyAt(bdtridententity7.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                                }

                                                                                this.world.addFreshEntity(bdtridententity7);
                                                                            }

                                                                            levelaccessor8 = this.world;
                                                                            if (levelaccessor8 instanceof ServerLevel) {
                                                                                serverlevel7 = (ServerLevel)levelaccessor8;
                                                                                bdtridententity7 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel7);
                                                                                bdtridententity7.moveTo(this.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -20, -20), this.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y - 1.0D, this.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -20, -20), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                                if (bdtridententity7 instanceof Mob) {
                                                                                    mob7 = (Mob)bdtridententity7;
                                                                                    mob7.finalizeSpawn(serverlevel7, this.world.getCurrentDifficultyAt(bdtridententity7.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                                }

                                                                                this.world.addFreshEntity(bdtridententity7);
                                                                            }

                                                                            ((<undefinedtype>)(new Object() {
                                                                                private int ticks;
                                                                                private float waitTicks;
                                                                                private LevelAccessor world;

                                                                                {
                                                                                    this.this$7 = 0;
                                                                                    this.ticks = 0;
                                                                                }

                                                                                public void start(LevelAccessor levelaccessor9, int i) {
                                                                                    this.waitTicks = (float)i;
                                                                                    MinecraftForge.EVENT_BUS.register(this);
                                                                                    this.world = levelaccessor9;
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
                                                                                    LevelAccessor levelaccessor9 = this.world;
                                                                                    ServerLevel serverlevel8;
                                                                                    LightningBolt lightningbolt;

                                                                                    if (levelaccessor9 instanceof ServerLevel) {
                                                                                        serverlevel8 = (ServerLevel)levelaccessor9;
                                                                                        lightningbolt = (LightningBolt)EntityType.LIGHTNING_BOLT.create(serverlevel8);
                                                                                        lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -25, 25), this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y - 1.0D, this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -25, 25))));
                                                                                        lightningbolt.setVisualOnly(true);
                                                                                        serverlevel8.addFreshEntity(lightningbolt);
                                                                                    }

                                                                                    levelaccessor9 = this.world;
                                                                                    if (levelaccessor9 instanceof ServerLevel) {
                                                                                        serverlevel8 = (ServerLevel)levelaccessor9;
                                                                                        lightningbolt = (LightningBolt)EntityType.LIGHTNING_BOLT.create(serverlevel8);
                                                                                        lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -25, 25), this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y - 1.0D, this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -25, 25))));
                                                                                        lightningbolt.setVisualOnly(true);
                                                                                        serverlevel8.addFreshEntity(lightningbolt);
                                                                                    }

                                                                                    levelaccessor9 = this.world;
                                                                                    if (levelaccessor9 instanceof ServerLevel) {
                                                                                        serverlevel8 = (ServerLevel)levelaccessor9;
                                                                                        lightningbolt = (LightningBolt)EntityType.LIGHTNING_BOLT.create(serverlevel8);
                                                                                        lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -25, 25), this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y - 1.0D, this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -25, 25))));
                                                                                        lightningbolt.setVisualOnly(true);
                                                                                        serverlevel8.addFreshEntity(lightningbolt);
                                                                                    }

                                                                                    levelaccessor9 = this.world;
                                                                                    if (levelaccessor9 instanceof ServerLevel) {
                                                                                        serverlevel8 = (ServerLevel)levelaccessor9;
                                                                                        lightningbolt = (LightningBolt)EntityType.LIGHTNING_BOLT.create(serverlevel8);
                                                                                        lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -25, 25), this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y - 1.0D, this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -25, 25))));
                                                                                        lightningbolt.setVisualOnly(true);
                                                                                        serverlevel8.addFreshEntity(lightningbolt);
                                                                                    }

                                                                                    levelaccessor9 = this.world;
                                                                                    BdTridentEntity bdtridententity8;
                                                                                    Mob mob8;

                                                                                    if (levelaccessor9 instanceof ServerLevel) {
                                                                                        serverlevel8 = (ServerLevel)levelaccessor9;
                                                                                        bdtridententity8 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel8);
                                                                                        bdtridententity8.moveTo(this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -25, 25), this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y - 1.0D, this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -25, 25), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                                        if (bdtridententity8 instanceof Mob) {
                                                                                            mob8 = (Mob)bdtridententity8;
                                                                                            mob8.finalizeSpawn(serverlevel8, this.world.getCurrentDifficultyAt(bdtridententity8.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                                        }

                                                                                        this.world.addFreshEntity(bdtridententity8);
                                                                                    }

                                                                                    levelaccessor9 = this.world;
                                                                                    if (levelaccessor9 instanceof ServerLevel) {
                                                                                        serverlevel8 = (ServerLevel)levelaccessor9;
                                                                                        bdtridententity8 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel8);
                                                                                        bdtridententity8.moveTo(this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -25, -25), this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y - 1.0D, this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -25, -25), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                                        if (bdtridententity8 instanceof Mob) {
                                                                                            mob8 = (Mob)bdtridententity8;
                                                                                            mob8.finalizeSpawn(serverlevel8, this.world.getCurrentDifficultyAt(bdtridententity8.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                                        }

                                                                                        this.world.addFreshEntity(bdtridententity8);
                                                                                    }

                                                                                    levelaccessor9 = this.world;
                                                                                    if (levelaccessor9 instanceof ServerLevel) {
                                                                                        serverlevel8 = (ServerLevel)levelaccessor9;
                                                                                        bdtridententity8 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel8);
                                                                                        bdtridententity8.moveTo(this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -25, -25), this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y - 1.0D, this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -25, -25), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                                        if (bdtridententity8 instanceof Mob) {
                                                                                            mob8 = (Mob)bdtridententity8;
                                                                                            mob8.finalizeSpawn(serverlevel8, this.world.getCurrentDifficultyAt(bdtridententity8.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                                        }

                                                                                        this.world.addFreshEntity(bdtridententity8);
                                                                                    }

                                                                                    levelaccessor9 = this.world;
                                                                                    if (levelaccessor9 instanceof ServerLevel) {
                                                                                        serverlevel8 = (ServerLevel)levelaccessor9;
                                                                                        bdtridententity8 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel8);
                                                                                        bdtridententity8.moveTo(this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -25, -25), this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y - 1.0D, this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -25, -25), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                                        if (bdtridententity8 instanceof Mob) {
                                                                                            mob8 = (Mob)bdtridententity8;
                                                                                            mob8.finalizeSpawn(serverlevel8, this.world.getCurrentDifficultyAt(bdtridententity8.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                                        }

                                                                                        this.world.addFreshEntity(bdtridententity8);
                                                                                    }

                                                                                    levelaccessor9 = this.world;
                                                                                    if (levelaccessor9 instanceof ServerLevel) {
                                                                                        serverlevel8 = (ServerLevel)levelaccessor9;
                                                                                        bdtridententity8 = new BdTridentEntity((EntityType)AnnoyingVillagersModEntities.BD_TRIDENT.get(), serverlevel8);
                                                                                        bdtridententity8.moveTo(this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x + (double)Mth.nextInt(new Random(), -25, -25), this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y - 1.0D, this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z + (double)Mth.nextInt(new Random(), -25, -25), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                                        if (bdtridententity8 instanceof Mob) {
                                                                                            mob8 = (Mob)bdtridententity8;
                                                                                            mob8.finalizeSpawn(serverlevel8, this.world.getCurrentDifficultyAt(bdtridententity8.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                                        }

                                                                                        this.world.addFreshEntity(bdtridententity8);
                                                                                    }

                                                                                    Entity entity2 = this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$entity;

                                                                                    if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                                                        entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect clear @e annoying_villagersbychentu:gedang");
                                                                                    }

                                                                                    levelaccessor9 = this.world;
                                                                                    Level level1;

                                                                                    if (levelaccessor9 instanceof Level) {
                                                                                        level1 = (Level)levelaccessor9;
                                                                                        if (!level1.isClientSide()) {
                                                                                            level1.explode((Entity)null, this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y + 3.0D, this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z, 60.0F, BlockInteraction.DESTROY);
                                                                                        }
                                                                                    }

                                                                                    levelaccessor9 = this.world;
                                                                                    if (levelaccessor9 instanceof Level) {
                                                                                        level1 = (Level)levelaccessor9;
                                                                                        if (!level1.isClientSide()) {
                                                                                            level1.explode((Entity)null, this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y + 1.0D, this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z, 0.0F, BlockInteraction.NONE);
                                                                                        }
                                                                                    }

                                                                                    levelaccessor9 = this.world;
                                                                                    if (levelaccessor9 instanceof Level) {
                                                                                        level1 = (Level)levelaccessor9;
                                                                                        if (!level1.isClientSide()) {
                                                                                            level1.explode((Entity)null, this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y + 1.0D, this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z, 0.0F, BlockInteraction.NONE);
                                                                                        }
                                                                                    }

                                                                                    if (!this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$entity.level.isClientSide()) {
                                                                                        this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$entity.discard();
                                                                                    }

                                                                                    ((<undefinedtype>)(new Object() {
                                                                                        private int ticks;
                                                                                        private float waitTicks;
                                                                                        private LevelAccessor world;

                                                                                        {
                                                                                            this.this$8 = 0;
                                                                                            this.ticks = 0;
                                                                                        }

                                                                                        public void start(LevelAccessor levelaccessor10, int i) {
                                                                                            this.waitTicks = (float)i;
                                                                                            MinecraftForge.EVENT_BUS.register(this);
                                                                                            this.world = levelaccessor10;
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
                                                                                            LevelAccessor levelaccessor10 = this.world;

                                                                                            if (levelaccessor10 instanceof ServerLevel) {
                                                                                                ServerLevel serverlevel9 = (ServerLevel)levelaccessor10;
                                                                                                BlueDemonEndEntity bluedemonendentity = new BlueDemonEndEntity((EntityType)AnnoyingVillagersModEntities.BLUE_DEMON_END.get(), serverlevel9);

                                                                                                bluedemonendentity.moveTo(this.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z, this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                                                                                if (bluedemonendentity instanceof Mob) {
                                                                                                    Mob mob9 = (Mob)bluedemonendentity;

                                                                                                    mob9.finalizeSpawn(serverlevel9, this.world.getCurrentDifficultyAt(bluedemonendentity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                                                                                }

                                                                                                this.world.addFreshEntity(bluedemonendentity);
                                                                                            }

                                                                                            MinecraftForge.EVENT_BUS.unregister(this);
                                                                                        }
                                                                                    })).start(this.world, 1);
                                                                                    MinecraftForge.EVENT_BUS.unregister(this);
                                                                                }
                                                                            })).start(this.world, 10);
                                                                            MinecraftForge.EVENT_BUS.unregister(this);
                                                                        }
                                                                    })).start(this.world, 10);
                                                                    MinecraftForge.EVENT_BUS.unregister(this);
                                                                }
                                                            })).start(this.world, 5);
                                                            MinecraftForge.EVENT_BUS.unregister(this);
                                                        }
                                                    })).start(this.world, 5);
                                                    MinecraftForge.EVENT_BUS.unregister(this);
                                                }
                                            })).start(this.world, 5);
                                            MinecraftForge.EVENT_BUS.unregister(this);
                                        }
                                    })).start(this.world, 10);
                                    MinecraftForge.EVENT_BUS.unregister(this);
                                }
                            })).start(this.world, 10);
                            MinecraftForge.EVENT_BUS.unregister(this);
                        }
                    })).start(this.world, 10);
                    MinecraftForge.EVENT_BUS.unregister(this);
                }
            })).start(levelaccessor, 25);
        }
    }
}
