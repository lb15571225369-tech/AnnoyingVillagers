package com.pla.annoyingvillagers.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;

public class HardGreatSwordSkillDangYouJianDianJiKongQiShiProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (entity.isShiftKeyDown()) {
                if (itemstack.getOrCreateTag().getDouble("power") >= 10.0D) {
                    itemstack.getOrCreateTag().putDouble("power", itemstack.getOrCreateTag().getDouble("power") - 10.0D);
                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/hard_great_sword_skill\" 0 1");
                    }

                    if (entity instanceof LivingEntity) {
                        LivingEntity livingentity = (LivingEntity)entity;

                        if (!livingentity.level.isClientSide()) {
                            livingentity.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.EC_PLAYER.get(), 160, 0, false, false));
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
                            Entity entity1 = entity;

                            if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "execute at @s run particle annoying_villagers:red_spark ^ ^1.5 ^1 0 0 0 0.6 35");
                            }

                            LevelAccessor levelaccessor1 = this.world;

                            if (levelaccessor1 instanceof Level) {
                                Level level = (Level)levelaccessor1;

                                if (!level.isClientSide()) {
                                    level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:hard_great_sword_skill")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                } else {
                                    level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:hard_great_sword_skill")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                }
                            }

                            MinecraftForge.EVENT_BUS.unregister(this);
                        }
                    })).start(levelaccessor, 4);
                } else if (entity instanceof Player) {
                    Player player = (Player)entity;

                    if (!player.level.isClientSide()) {
                        CompoundTag compoundtag = itemstack.getOrCreateTag();

                        player.displayClientMessage(new TextComponent("\u80fd\u91cf\u4e0d\u8db3\uff0c\u76ee\u524d\u5145\u80fd" + compoundtag.getDouble("power") + "/10"), true);
                    }
                }
            }

        }
    }
}
