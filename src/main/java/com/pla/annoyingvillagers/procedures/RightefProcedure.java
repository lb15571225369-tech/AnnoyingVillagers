package com.pla.annoyingvillagers.procedures;

import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class RightefProcedure {

    @SubscribeEvent
    public static void onRightClickItem(RightClickItem rightclickitem) {
        if (rightclickitem.getHand() == rightclickitem.getPlayer().getUsedItemHand()) {
            execute(rightclickitem, rightclickitem.getWorld(), rightclickitem.getPlayer());
        }
    }

    public static void execute(LevelAccessor levelaccessor, Entity entity) {
        execute((Event) null, levelaccessor, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, final Entity entity) {
        if (entity != null) {
            Enchantment enchantment = Enchantments.CHANNELING;
            LivingEntity livingentity;
            ItemStack itemstack;

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                itemstack = livingentity.getMainHandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack) != 0 && !entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect give @s annoying_villagersbychentu:electify 4 0 true");
            }

            enchantment = Enchantments.FIRE_ASPECT;
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                itemstack = livingentity.getMainHandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            LivingEntity livingentity1;
            ItemStack itemstack1;
            Map map;
            LivingEntity livingentity2;

            if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack) != 0) {
                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity)entity;
                    itemstack1 = livingentity1.getMainHandItem();
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                map = EnchantmentHelper.getEnchantments(itemstack1);
                if (map.containsKey(Enchantments.FIRE_ASPECT)) {
                    map.remove(Enchantments.FIRE_ASPECT);
                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity)entity;
                        itemstack = livingentity1.getMainHandItem();
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    EnchantmentHelper.setEnchantments(map, itemstack);
                }
            } else {
                enchantment = Enchantments.FIRE_ASPECT;
                if (entity instanceof LivingEntity) {
                    livingentity2 = (LivingEntity)entity;
                    itemstack = livingentity2.getOffhandItem();
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack) != 0) {
                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity)entity;
                        itemstack1 = livingentity1.getOffhandItem();
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    map = EnchantmentHelper.getEnchantments(itemstack1);
                    if (map.containsKey(Enchantments.FIRE_ASPECT)) {
                        map.remove(Enchantments.FIRE_ASPECT);
                        if (entity instanceof LivingEntity) {
                            livingentity1 = (LivingEntity)entity;
                            itemstack = livingentity1.getOffhandItem();
                        } else {
                            itemstack = ItemStack.EMPTY;
                        }

                        EnchantmentHelper.setEnchantments(map, itemstack);
                    }
                }
            }

            if (entity.isShiftKeyDown()) {
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack1 = livingentity.getMainHandItem();
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                if (itemstack1.getItem() instanceof SwordItem) {
                    if (!entity.getPersistentData().getBoolean("s_g") && !entity.getPersistentData().getBoolean("coodown")) {
                        entity.getPersistentData().putBoolean("s_g", true);
                        entity.getPersistentData().putBoolean("coodown", true);
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
                                entity.getPersistentData().putBoolean("s_g", false);
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
                                        entity.getPersistentData().putBoolean("coodown", false);
                                        MinecraftForge.EVENT_BUS.unregister(this);
                                    }
                                })).start(this.world, 20);
                                MinecraftForge.EVENT_BUS.unregister(this);
                            }
                        })).start(levelaccessor, 2);
                    }
                } else {
                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity)entity;
                        itemstack1 = livingentity2.getMainHandItem();
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    if (itemstack1.getItem() instanceof AxeItem && !entity.getPersistentData().getBoolean("s_g") && !entity.getPersistentData().getBoolean("coodown")) {
                        entity.getPersistentData().putBoolean("s_g", true);
                        entity.getPersistentData().putBoolean("coodown", true);
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
                                entity.getPersistentData().putBoolean("s_g", false);
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
                                        entity.getPersistentData().putBoolean("coodown", false);
                                        MinecraftForge.EVENT_BUS.unregister(this);
                                    }
                                })).start(this.world, 20);
                                MinecraftForge.EVENT_BUS.unregister(this);
                            }
                        })).start(levelaccessor, 3);
                    }
                }
            }

        }
    }
}
