//package com.pla.annoyingvillagers.procedures;
//
//import java.util.Map;
//import javax.annotation.Nullable;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.EquipmentSlot;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.enchantment.Enchantment;
//import net.minecraft.world.item.enchantment.EnchantmentHelper;
//import net.minecraft.world.item.enchantment.Enchantments;
//import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
//import net.minecraftforge.eventbus.api.Event;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
//
//@EventBusSubscriber
//public class EnjoinProcedure {
//
//    @SubscribeEvent
//    public static void onPlayerLoggedIn(PlayerLoggedInEvent playerloggedinevent) {
//        execute(playerloggedinevent, playerloggedinevent.getPlayer());
//    }
//
//    public static void execute(Entity entity) {
//        execute((Event) null, entity);
//    }
//
//    private static void execute(@Nullable Event event, Entity entity) {
//        if (entity != null) {
//            LivingEntity livingentity;
//            ItemStack itemstack;
//
//            if (entity instanceof LivingEntity) {
//                livingentity = (LivingEntity) entity;
//                itemstack = livingentity.getItemBySlot(EquipmentSlot.LEGS);
//            } else {
//                itemstack = ItemStack.EMPTY;
//            }
//
//            Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemstack);
//            ItemStack itemstack1;
//
//            if (map.containsKey(Enchantments.THORNS)) {
//                map.remove(Enchantments.THORNS);
//                if (entity instanceof LivingEntity) {
//                    livingentity = (LivingEntity) entity;
//                    itemstack1 = livingentity.getItemBySlot(EquipmentSlot.LEGS);
//                } else {
//                    itemstack1 = ItemStack.EMPTY;
//                }
//
//                EnchantmentHelper.setEnchantments(map, itemstack1);
//            }
//
//            if (entity instanceof LivingEntity) {
//                livingentity = (LivingEntity) entity;
//                itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
//            } else {
//                itemstack = ItemStack.EMPTY;
//            }
//
//            map = EnchantmentHelper.getEnchantments(itemstack);
//            if (map.containsKey(Enchantments.THORNS)) {
//                map.remove(Enchantments.THORNS);
//                if (entity instanceof LivingEntity) {
//                    livingentity = (LivingEntity) entity;
//                    itemstack1 = livingentity.getItemBySlot(EquipmentSlot.CHEST);
//                } else {
//                    itemstack1 = ItemStack.EMPTY;
//                }
//
//                EnchantmentHelper.setEnchantments(map, itemstack1);
//            }
//
//            if (entity instanceof LivingEntity) {
//                livingentity = (LivingEntity) entity;
//                itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
//            } else {
//                itemstack = ItemStack.EMPTY;
//            }
//
//            map = EnchantmentHelper.getEnchantments(itemstack);
//            if (map.containsKey(Enchantments.THORNS)) {
//                map.remove(Enchantments.THORNS);
//                if (entity instanceof LivingEntity) {
//                    livingentity = (LivingEntity) entity;
//                    itemstack1 = livingentity.getItemBySlot(EquipmentSlot.HEAD);
//                } else {
//                    itemstack1 = ItemStack.EMPTY;
//                }
//
//                EnchantmentHelper.setEnchantments(map, itemstack1);
//            }
//
//            if (entity instanceof LivingEntity) {
//                livingentity = (LivingEntity) entity;
//                itemstack = livingentity.getItemBySlot(EquipmentSlot.FEET);
//            } else {
//                itemstack = ItemStack.EMPTY;
//            }
//
//            map = EnchantmentHelper.getEnchantments(itemstack);
//            if (map.containsKey(Enchantments.THORNS)) {
//                map.remove(Enchantments.THORNS);
//                if (entity instanceof LivingEntity) {
//                    livingentity = (LivingEntity) entity;
//                    itemstack1 = livingentity.getItemBySlot(EquipmentSlot.FEET);
//                } else {
//                    itemstack1 = ItemStack.EMPTY;
//                }
//
//                EnchantmentHelper.setEnchantments(map, itemstack1);
//            }
//
//            Enchantment enchantment = Enchantments.ALL_DAMAGE_PROTECTION;
//            LivingEntity livingentity1;
//
//            if (entity instanceof LivingEntity) {
//                livingentity1 = (LivingEntity) entity;
//                itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.FEET);
//            } else {
//                itemstack1 = ItemStack.EMPTY;
//            }
//
//            LivingEntity livingentity2;
//            Map map1;
//
//            if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 4) {
//                if (entity instanceof LivingEntity) {
//                    livingentity2 = (LivingEntity) entity;
//                    itemstack = livingentity2.getItemBySlot(EquipmentSlot.FEET);
//                } else {
//                    itemstack = ItemStack.EMPTY;
//                }
//
//                map1 = EnchantmentHelper.getEnchantments(itemstack);
//                if (map1.containsKey(Enchantments.ALL_DAMAGE_PROTECTION)) {
//                    map1.remove(Enchantments.ALL_DAMAGE_PROTECTION);
//                    if (entity instanceof LivingEntity) {
//                        livingentity2 = (LivingEntity) entity;
//                        itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.FEET);
//                    } else {
//                        itemstack1 = ItemStack.EMPTY;
//                    }
//
//                    EnchantmentHelper.setEnchantments(map1, itemstack1);
//                }
//            }
//
//            enchantment = Enchantments.ALL_DAMAGE_PROTECTION;
//            if (entity instanceof LivingEntity) {
//                livingentity1 = (LivingEntity) entity;
//                itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.LEGS);
//            } else {
//                itemstack1 = ItemStack.EMPTY;
//            }
//
//            if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 4) {
//                if (entity instanceof LivingEntity) {
//                    livingentity2 = (LivingEntity) entity;
//                    itemstack = livingentity2.getItemBySlot(EquipmentSlot.LEGS);
//                } else {
//                    itemstack = ItemStack.EMPTY;
//                }
//
//                map1 = EnchantmentHelper.getEnchantments(itemstack);
//                if (map1.containsKey(Enchantments.ALL_DAMAGE_PROTECTION)) {
//                    map1.remove(Enchantments.ALL_DAMAGE_PROTECTION);
//                    if (entity instanceof LivingEntity) {
//                        livingentity2 = (LivingEntity) entity;
//                        itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.LEGS);
//                    } else {
//                        itemstack1 = ItemStack.EMPTY;
//                    }
//
//                    EnchantmentHelper.setEnchantments(map1, itemstack1);
//                }
//            }
//
//            enchantment = Enchantments.ALL_DAMAGE_PROTECTION;
//            if (entity instanceof LivingEntity) {
//                livingentity1 = (LivingEntity) entity;
//                itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.CHEST);
//            } else {
//                itemstack1 = ItemStack.EMPTY;
//            }
//
//            if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 4) {
//                if (entity instanceof LivingEntity) {
//                    livingentity2 = (LivingEntity) entity;
//                    itemstack = livingentity2.getItemBySlot(EquipmentSlot.CHEST);
//                } else {
//                    itemstack = ItemStack.EMPTY;
//                }
//
//                map1 = EnchantmentHelper.getEnchantments(itemstack);
//                if (map1.containsKey(Enchantments.ALL_DAMAGE_PROTECTION)) {
//                    map1.remove(Enchantments.ALL_DAMAGE_PROTECTION);
//                    if (entity instanceof LivingEntity) {
//                        livingentity2 = (LivingEntity) entity;
//                        itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.CHEST);
//                    } else {
//                        itemstack1 = ItemStack.EMPTY;
//                    }
//
//                    EnchantmentHelper.setEnchantments(map1, itemstack1);
//                }
//            }
//
//            enchantment = Enchantments.ALL_DAMAGE_PROTECTION;
//            if (entity instanceof LivingEntity) {
//                livingentity1 = (LivingEntity) entity;
//                itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.HEAD);
//            } else {
//                itemstack1 = ItemStack.EMPTY;
//            }
//
//            if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 4) {
//                if (entity instanceof LivingEntity) {
//                    livingentity2 = (LivingEntity) entity;
//                    itemstack = livingentity2.getItemBySlot(EquipmentSlot.HEAD);
//                } else {
//                    itemstack = ItemStack.EMPTY;
//                }
//
//                map1 = EnchantmentHelper.getEnchantments(itemstack);
//                if (map1.containsKey(Enchantments.ALL_DAMAGE_PROTECTION)) {
//                    map1.remove(Enchantments.ALL_DAMAGE_PROTECTION);
//                    if (entity instanceof LivingEntity) {
//                        livingentity2 = (LivingEntity) entity;
//                        itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.HEAD);
//                    } else {
//                        itemstack1 = ItemStack.EMPTY;
//                    }
//
//                    EnchantmentHelper.setEnchantments(map1, itemstack1);
//                }
//            }
//
//            enchantment = Enchantments.UNBREAKING;
//            if (entity instanceof LivingEntity) {
//                livingentity1 = (LivingEntity) entity;
//                itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.FEET);
//            } else {
//                itemstack1 = ItemStack.EMPTY;
//            }
//
//            if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 3) {
//                if (entity instanceof LivingEntity) {
//                    livingentity2 = (LivingEntity) entity;
//                    itemstack = livingentity2.getItemBySlot(EquipmentSlot.FEET);
//                } else {
//                    itemstack = ItemStack.EMPTY;
//                }
//
//                map1 = EnchantmentHelper.getEnchantments(itemstack);
//                if (map1.containsKey(Enchantments.UNBREAKING)) {
//                    map1.remove(Enchantments.UNBREAKING);
//                    if (entity instanceof LivingEntity) {
//                        livingentity2 = (LivingEntity) entity;
//                        itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.FEET);
//                    } else {
//                        itemstack1 = ItemStack.EMPTY;
//                    }
//
//                    EnchantmentHelper.setEnchantments(map1, itemstack1);
//                }
//            }
//
//            enchantment = Enchantments.UNBREAKING;
//            if (entity instanceof LivingEntity) {
//                livingentity1 = (LivingEntity) entity;
//                itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.LEGS);
//            } else {
//                itemstack1 = ItemStack.EMPTY;
//            }
//
//            if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 3) {
//                if (entity instanceof LivingEntity) {
//                    livingentity2 = (LivingEntity) entity;
//                    itemstack = livingentity2.getItemBySlot(EquipmentSlot.LEGS);
//                } else {
//                    itemstack = ItemStack.EMPTY;
//                }
//
//                map1 = EnchantmentHelper.getEnchantments(itemstack);
//                if (map1.containsKey(Enchantments.UNBREAKING)) {
//                    map1.remove(Enchantments.UNBREAKING);
//                    if (entity instanceof LivingEntity) {
//                        livingentity2 = (LivingEntity) entity;
//                        itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.LEGS);
//                    } else {
//                        itemstack1 = ItemStack.EMPTY;
//                    }
//
//                    EnchantmentHelper.setEnchantments(map1, itemstack1);
//                }
//            }
//
//            enchantment = Enchantments.UNBREAKING;
//            if (entity instanceof LivingEntity) {
//                livingentity1 = (LivingEntity) entity;
//                itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.CHEST);
//            } else {
//                itemstack1 = ItemStack.EMPTY;
//            }
//
//            if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 3) {
//                if (entity instanceof LivingEntity) {
//                    livingentity2 = (LivingEntity) entity;
//                    itemstack = livingentity2.getItemBySlot(EquipmentSlot.CHEST);
//                } else {
//                    itemstack = ItemStack.EMPTY;
//                }
//
//                map1 = EnchantmentHelper.getEnchantments(itemstack);
//                if (map1.containsKey(Enchantments.UNBREAKING)) {
//                    map1.remove(Enchantments.UNBREAKING);
//                    if (entity instanceof LivingEntity) {
//                        livingentity2 = (LivingEntity) entity;
//                        itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.CHEST);
//                    } else {
//                        itemstack1 = ItemStack.EMPTY;
//                    }
//
//                    EnchantmentHelper.setEnchantments(map1, itemstack1);
//                }
//            }
//
//            enchantment = Enchantments.UNBREAKING;
//            if (entity instanceof LivingEntity) {
//                livingentity1 = (LivingEntity) entity;
//                itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.HEAD);
//            } else {
//                itemstack1 = ItemStack.EMPTY;
//            }
//
//            if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 3) {
//                if (entity instanceof LivingEntity) {
//                    livingentity2 = (LivingEntity) entity;
//                    itemstack = livingentity2.getItemBySlot(EquipmentSlot.HEAD);
//                } else {
//                    itemstack = ItemStack.EMPTY;
//                }
//
//                map1 = EnchantmentHelper.getEnchantments(itemstack);
//                if (map1.containsKey(Enchantments.UNBREAKING)) {
//                    map1.remove(Enchantments.UNBREAKING);
//                    if (entity instanceof LivingEntity) {
//                        livingentity2 = (LivingEntity) entity;
//                        itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.HEAD);
//                    } else {
//                        itemstack1 = ItemStack.EMPTY;
//                    }
//
//                    EnchantmentHelper.setEnchantments(map1, itemstack1);
//                }
//            }
//
//        }
//    }
//}
