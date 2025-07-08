package com.pla.annoyingvillagers.procedures;

import java.util.Map;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.registries.ForgeRegistries;

public class ProteProcedure {

    public static void execute(Entity entity, Entity entity1) {
        if (entity != null && entity1 != null) {
            LivingEntity livingentity;
            ItemStack itemstack;
            ItemStack itemstack1;
            Enchantment enchantment;
            LivingEntity livingentity1;
            LivingEntity livingentity2;
            Map map;

            if (ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("minecraft:player")) {
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    itemstack = livingentity.getItemBySlot(EquipmentSlot.LEGS);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                Map<Enchantment, Integer> map1 = EnchantmentHelper.getEnchantments(itemstack);

                if (map1.containsKey(Enchantments.THORNS)) {
                    map1.remove(Enchantments.THORNS);
                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity) entity;
                        itemstack1 = livingentity.getItemBySlot(EquipmentSlot.LEGS);
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    EnchantmentHelper.setEnchantments(map1, itemstack1);
                }

                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                map1 = EnchantmentHelper.getEnchantments(itemstack);
                if (map1.containsKey(Enchantments.THORNS)) {
                    map1.remove(Enchantments.THORNS);
                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity) entity;
                        itemstack1 = livingentity.getItemBySlot(EquipmentSlot.CHEST);
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    EnchantmentHelper.setEnchantments(map1, itemstack1);
                }

                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                map1 = EnchantmentHelper.getEnchantments(itemstack);
                if (map1.containsKey(Enchantments.THORNS)) {
                    map1.remove(Enchantments.THORNS);
                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity) entity;
                        itemstack1 = livingentity.getItemBySlot(EquipmentSlot.HEAD);
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    EnchantmentHelper.setEnchantments(map1, itemstack1);
                }

                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    itemstack = livingentity.getItemBySlot(EquipmentSlot.FEET);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                map1 = EnchantmentHelper.getEnchantments(itemstack);
                if (map1.containsKey(Enchantments.THORNS)) {
                    map1.remove(Enchantments.THORNS);
                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity) entity;
                        itemstack1 = livingentity.getItemBySlot(EquipmentSlot.FEET);
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    EnchantmentHelper.setEnchantments(map1, itemstack1);
                }

                enchantment = Enchantments.ALL_DAMAGE_PROTECTION;
                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.FEET);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 4) {
                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity) entity;
                        itemstack = livingentity2.getItemBySlot(EquipmentSlot.FEET);
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    map = EnchantmentHelper.getEnchantments(itemstack);
                    if (map.containsKey(Enchantments.ALL_DAMAGE_PROTECTION)) {
                        map.remove(Enchantments.ALL_DAMAGE_PROTECTION);
                        if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity) entity;
                            itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.FEET);
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        EnchantmentHelper.setEnchantments(map, itemstack1);
                    }
                }

                enchantment = Enchantments.ALL_DAMAGE_PROTECTION;
                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.LEGS);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 4) {
                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity) entity;
                        itemstack = livingentity2.getItemBySlot(EquipmentSlot.LEGS);
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    map = EnchantmentHelper.getEnchantments(itemstack);
                    if (map.containsKey(Enchantments.ALL_DAMAGE_PROTECTION)) {
                        map.remove(Enchantments.ALL_DAMAGE_PROTECTION);
                        if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity) entity;
                            itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.LEGS);
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        EnchantmentHelper.setEnchantments(map, itemstack1);
                    }
                }

                enchantment = Enchantments.ALL_DAMAGE_PROTECTION;
                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.CHEST);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 4) {
                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity) entity;
                        itemstack = livingentity2.getItemBySlot(EquipmentSlot.CHEST);
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    map = EnchantmentHelper.getEnchantments(itemstack);
                    if (map.containsKey(Enchantments.ALL_DAMAGE_PROTECTION)) {
                        map.remove(Enchantments.ALL_DAMAGE_PROTECTION);
                        if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity) entity;
                            itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.CHEST);
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        EnchantmentHelper.setEnchantments(map, itemstack1);
                    }
                }

                enchantment = Enchantments.ALL_DAMAGE_PROTECTION;
                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.HEAD);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 4) {
                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity) entity;
                        itemstack = livingentity2.getItemBySlot(EquipmentSlot.HEAD);
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    map = EnchantmentHelper.getEnchantments(itemstack);
                    if (map.containsKey(Enchantments.ALL_DAMAGE_PROTECTION)) {
                        map.remove(Enchantments.ALL_DAMAGE_PROTECTION);
                        if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity) entity;
                            itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.HEAD);
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        EnchantmentHelper.setEnchantments(map, itemstack1);
                    }
                }

                enchantment = Enchantments.UNBREAKING;
                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.FEET);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 3) {
                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity) entity;
                        itemstack = livingentity2.getItemBySlot(EquipmentSlot.FEET);
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    map = EnchantmentHelper.getEnchantments(itemstack);
                    if (map.containsKey(Enchantments.UNBREAKING)) {
                        map.remove(Enchantments.UNBREAKING);
                        if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity) entity;
                            itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.FEET);
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        EnchantmentHelper.setEnchantments(map, itemstack1);
                    }
                }

                enchantment = Enchantments.UNBREAKING;
                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.LEGS);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 3) {
                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity) entity;
                        itemstack = livingentity2.getItemBySlot(EquipmentSlot.LEGS);
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    map = EnchantmentHelper.getEnchantments(itemstack);
                    if (map.containsKey(Enchantments.UNBREAKING)) {
                        map.remove(Enchantments.UNBREAKING);
                        if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity) entity;
                            itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.LEGS);
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        EnchantmentHelper.setEnchantments(map, itemstack1);
                    }
                }

                enchantment = Enchantments.UNBREAKING;
                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.CHEST);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 3) {
                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity) entity;
                        itemstack = livingentity2.getItemBySlot(EquipmentSlot.CHEST);
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    map = EnchantmentHelper.getEnchantments(itemstack);
                    if (map.containsKey(Enchantments.UNBREAKING)) {
                        map.remove(Enchantments.UNBREAKING);
                        if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity) entity;
                            itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.CHEST);
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        EnchantmentHelper.setEnchantments(map, itemstack1);
                    }
                }

                enchantment = Enchantments.UNBREAKING;
                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.HEAD);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 3) {
                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity) entity;
                        itemstack = livingentity2.getItemBySlot(EquipmentSlot.HEAD);
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    map = EnchantmentHelper.getEnchantments(itemstack);
                    if (map.containsKey(Enchantments.UNBREAKING)) {
                        map.remove(Enchantments.UNBREAKING);
                        if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity) entity;
                            itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.HEAD);
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        EnchantmentHelper.setEnchantments(map, itemstack1);
                    }
                }
            }

            enchantment = Enchantments.SHARPNESS;
            if (entity1 instanceof LivingEntity) {
                livingentity1 = (LivingEntity) entity1;
                itemstack1 = livingentity1.getMainHandItem();
            } else {
                itemstack1 = ItemStack.EMPTY;
            }

            LivingEntity livingentity3;
            Map map2;

            if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 5) {
                enchantment = Enchantments.KNOCKBACK;
                if (entity1 instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity1;
                    itemstack1 = livingentity.getMainHandItem();
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) == 9) {
                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "1");
                    }
                } else {
                    if (entity1 instanceof LivingEntity) {
                        livingentity3 = (LivingEntity) entity1;
                        itemstack = livingentity3.getMainHandItem();
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    map2 = EnchantmentHelper.getEnchantments(itemstack);
                    if (map2.containsKey(Enchantments.SHARPNESS)) {
                        map2.remove(Enchantments.SHARPNESS);
                        if (entity1 instanceof LivingEntity) {
                            livingentity3 = (LivingEntity) entity1;
                            itemstack1 = livingentity3.getMainHandItem();
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        EnchantmentHelper.setEnchantments(map2, itemstack1);
                    }

                    if (entity1 instanceof LivingEntity) {
                        livingentity3 = (LivingEntity) entity1;
                        itemstack = livingentity3.getMainHandItem();
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    map2 = EnchantmentHelper.getEnchantments(itemstack);
                    if (map2.containsKey(Enchantments.UNBREAKING)) {
                        map2.remove(Enchantments.UNBREAKING);
                        if (entity1 instanceof LivingEntity) {
                            livingentity3 = (LivingEntity) entity1;
                            itemstack1 = livingentity3.getMainHandItem();
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        EnchantmentHelper.setEnchantments(map2, itemstack1);
                    }
                }
            }

            enchantment = Enchantments.SHARPNESS;
            if (entity1 instanceof LivingEntity) {
                livingentity1 = (LivingEntity) entity1;
                itemstack1 = livingentity1.getOffhandItem();
            } else {
                itemstack1 = ItemStack.EMPTY;
            }

            if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 5) {
                enchantment = Enchantments.KNOCKBACK;
                if (entity1 instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity1;
                    itemstack1 = livingentity.getOffhandItem();
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) == 9) {
                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "1");
                    }
                } else {
                    if (entity1 instanceof LivingEntity) {
                        livingentity3 = (LivingEntity) entity1;
                        itemstack = livingentity3.getOffhandItem();
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    map2 = EnchantmentHelper.getEnchantments(itemstack);
                    if (map2.containsKey(Enchantments.SHARPNESS)) {
                        map2.remove(Enchantments.SHARPNESS);
                        if (entity1 instanceof LivingEntity) {
                            livingentity3 = (LivingEntity) entity1;
                            itemstack1 = livingentity3.getOffhandItem();
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        EnchantmentHelper.setEnchantments(map2, itemstack1);
                    }

                    if (entity1 instanceof LivingEntity) {
                        livingentity3 = (LivingEntity) entity1;
                        itemstack = livingentity3.getOffhandItem();
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    map2 = EnchantmentHelper.getEnchantments(itemstack);
                    if (map2.containsKey(Enchantments.UNBREAKING)) {
                        map2.remove(Enchantments.UNBREAKING);
                        if (entity1 instanceof LivingEntity) {
                            livingentity3 = (LivingEntity) entity1;
                            itemstack1 = livingentity3.getOffhandItem();
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        EnchantmentHelper.setEnchantments(map2, itemstack1);
                    }
                }
            }

            if (entity instanceof TamableAnimal) {
                TamableAnimal tamableanimal = (TamableAnimal) entity;

                if (tamableanimal.isTame()) {
                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity) entity;
                        itemstack = livingentity2.getItemBySlot(EquipmentSlot.LEGS);
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    map = EnchantmentHelper.getEnchantments(itemstack);
                    if (map.containsKey(Enchantments.THORNS)) {
                        map.remove(Enchantments.THORNS);
                        if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity) entity;
                            itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.LEGS);
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        EnchantmentHelper.setEnchantments(map, itemstack1);
                    }

                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity) entity;
                        itemstack = livingentity2.getItemBySlot(EquipmentSlot.CHEST);
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    map = EnchantmentHelper.getEnchantments(itemstack);
                    if (map.containsKey(Enchantments.THORNS)) {
                        map.remove(Enchantments.THORNS);
                        if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity) entity;
                            itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.CHEST);
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        EnchantmentHelper.setEnchantments(map, itemstack1);
                    }

                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity) entity;
                        itemstack = livingentity2.getItemBySlot(EquipmentSlot.HEAD);
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    map = EnchantmentHelper.getEnchantments(itemstack);
                    if (map.containsKey(Enchantments.THORNS)) {
                        map.remove(Enchantments.THORNS);
                        if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity) entity;
                            itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.HEAD);
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        EnchantmentHelper.setEnchantments(map, itemstack1);
                    }

                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity) entity;
                        itemstack = livingentity2.getItemBySlot(EquipmentSlot.FEET);
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    map = EnchantmentHelper.getEnchantments(itemstack);
                    if (map.containsKey(Enchantments.THORNS)) {
                        map.remove(Enchantments.THORNS);
                        if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity) entity;
                            itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.FEET);
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        EnchantmentHelper.setEnchantments(map, itemstack1);
                    }

                    enchantment = Enchantments.ALL_DAMAGE_PROTECTION;
                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity) entity;
                        itemstack1 = livingentity.getItemBySlot(EquipmentSlot.FEET);
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 4) {
                        if (entity instanceof LivingEntity) {
                            livingentity3 = (LivingEntity) entity;
                            itemstack = livingentity3.getItemBySlot(EquipmentSlot.FEET);
                        } else {
                            itemstack = ItemStack.EMPTY;
                        }

                        map2 = EnchantmentHelper.getEnchantments(itemstack);
                        if (map2.containsKey(Enchantments.ALL_DAMAGE_PROTECTION)) {
                            map2.remove(Enchantments.ALL_DAMAGE_PROTECTION);
                            if (entity instanceof LivingEntity) {
                                livingentity3 = (LivingEntity) entity;
                                itemstack1 = livingentity3.getItemBySlot(EquipmentSlot.FEET);
                            } else {
                                itemstack1 = ItemStack.EMPTY;
                            }

                            EnchantmentHelper.setEnchantments(map2, itemstack1);
                        }

                        if (!entity.level.isClientSide()) {
                            entity.discard();
                        }
                    }

                    enchantment = Enchantments.ALL_DAMAGE_PROTECTION;
                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity) entity;
                        itemstack1 = livingentity.getItemBySlot(EquipmentSlot.LEGS);
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 4) {
                        if (entity instanceof LivingEntity) {
                            livingentity3 = (LivingEntity) entity;
                            itemstack = livingentity3.getItemBySlot(EquipmentSlot.LEGS);
                        } else {
                            itemstack = ItemStack.EMPTY;
                        }

                        map2 = EnchantmentHelper.getEnchantments(itemstack);
                        if (map2.containsKey(Enchantments.ALL_DAMAGE_PROTECTION)) {
                            map2.remove(Enchantments.ALL_DAMAGE_PROTECTION);
                            if (entity instanceof LivingEntity) {
                                livingentity3 = (LivingEntity) entity;
                                itemstack1 = livingentity3.getItemBySlot(EquipmentSlot.LEGS);
                            } else {
                                itemstack1 = ItemStack.EMPTY;
                            }

                            EnchantmentHelper.setEnchantments(map2, itemstack1);
                        }
                    }

                    enchantment = Enchantments.ALL_DAMAGE_PROTECTION;
                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity) entity;
                        itemstack1 = livingentity.getItemBySlot(EquipmentSlot.CHEST);
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 4) {
                        if (entity instanceof LivingEntity) {
                            livingentity3 = (LivingEntity) entity;
                            itemstack = livingentity3.getItemBySlot(EquipmentSlot.CHEST);
                        } else {
                            itemstack = ItemStack.EMPTY;
                        }

                        map2 = EnchantmentHelper.getEnchantments(itemstack);
                        if (map2.containsKey(Enchantments.ALL_DAMAGE_PROTECTION)) {
                            map2.remove(Enchantments.ALL_DAMAGE_PROTECTION);
                            if (entity instanceof LivingEntity) {
                                livingentity3 = (LivingEntity) entity;
                                itemstack1 = livingentity3.getItemBySlot(EquipmentSlot.CHEST);
                            } else {
                                itemstack1 = ItemStack.EMPTY;
                            }

                            EnchantmentHelper.setEnchantments(map2, itemstack1);
                        }

                        if (!entity.level.isClientSide()) {
                            entity.discard();
                        }
                    }

                    enchantment = Enchantments.ALL_DAMAGE_PROTECTION;
                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity) entity;
                        itemstack1 = livingentity.getItemBySlot(EquipmentSlot.HEAD);
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 4) {
                        if (entity instanceof LivingEntity) {
                            livingentity3 = (LivingEntity) entity;
                            itemstack = livingentity3.getItemBySlot(EquipmentSlot.HEAD);
                        } else {
                            itemstack = ItemStack.EMPTY;
                        }

                        map2 = EnchantmentHelper.getEnchantments(itemstack);
                        if (map2.containsKey(Enchantments.ALL_DAMAGE_PROTECTION)) {
                            map2.remove(Enchantments.ALL_DAMAGE_PROTECTION);
                            if (entity instanceof LivingEntity) {
                                livingentity3 = (LivingEntity) entity;
                                itemstack1 = livingentity3.getItemBySlot(EquipmentSlot.HEAD);
                            } else {
                                itemstack1 = ItemStack.EMPTY;
                            }

                            EnchantmentHelper.setEnchantments(map2, itemstack1);
                        }

                        if (!entity.level.isClientSide()) {
                            entity.discard();
                        }
                    }

                    enchantment = Enchantments.UNBREAKING;
                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity) entity;
                        itemstack1 = livingentity.getItemBySlot(EquipmentSlot.FEET);
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 3) {
                        if (entity instanceof LivingEntity) {
                            livingentity3 = (LivingEntity) entity;
                            itemstack = livingentity3.getItemBySlot(EquipmentSlot.FEET);
                        } else {
                            itemstack = ItemStack.EMPTY;
                        }

                        map2 = EnchantmentHelper.getEnchantments(itemstack);
                        if (map2.containsKey(Enchantments.UNBREAKING)) {
                            map2.remove(Enchantments.UNBREAKING);
                            if (entity instanceof LivingEntity) {
                                livingentity3 = (LivingEntity) entity;
                                itemstack1 = livingentity3.getItemBySlot(EquipmentSlot.FEET);
                            } else {
                                itemstack1 = ItemStack.EMPTY;
                            }

                            EnchantmentHelper.setEnchantments(map2, itemstack1);
                        }
                    }

                    enchantment = Enchantments.UNBREAKING;
                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity) entity;
                        itemstack1 = livingentity.getItemBySlot(EquipmentSlot.LEGS);
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 3) {
                        if (entity instanceof LivingEntity) {
                            livingentity3 = (LivingEntity) entity;
                            itemstack = livingentity3.getItemBySlot(EquipmentSlot.LEGS);
                        } else {
                            itemstack = ItemStack.EMPTY;
                        }

                        map2 = EnchantmentHelper.getEnchantments(itemstack);
                        if (map2.containsKey(Enchantments.UNBREAKING)) {
                            map2.remove(Enchantments.UNBREAKING);
                            if (entity instanceof LivingEntity) {
                                livingentity3 = (LivingEntity) entity;
                                itemstack1 = livingentity3.getItemBySlot(EquipmentSlot.LEGS);
                            } else {
                                itemstack1 = ItemStack.EMPTY;
                            }

                            EnchantmentHelper.setEnchantments(map2, itemstack1);
                        }
                    }

                    enchantment = Enchantments.UNBREAKING;
                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity) entity;
                        itemstack1 = livingentity.getItemBySlot(EquipmentSlot.CHEST);
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 3) {
                        if (entity instanceof LivingEntity) {
                            livingentity3 = (LivingEntity) entity;
                            itemstack = livingentity3.getItemBySlot(EquipmentSlot.CHEST);
                        } else {
                            itemstack = ItemStack.EMPTY;
                        }

                        map2 = EnchantmentHelper.getEnchantments(itemstack);
                        if (map2.containsKey(Enchantments.UNBREAKING)) {
                            map2.remove(Enchantments.UNBREAKING);
                            if (entity instanceof LivingEntity) {
                                livingentity3 = (LivingEntity) entity;
                                itemstack1 = livingentity3.getItemBySlot(EquipmentSlot.CHEST);
                            } else {
                                itemstack1 = ItemStack.EMPTY;
                            }

                            EnchantmentHelper.setEnchantments(map2, itemstack1);
                        }
                    }

                    enchantment = Enchantments.UNBREAKING;
                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity) entity;
                        itemstack1 = livingentity.getItemBySlot(EquipmentSlot.HEAD);
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) > 3) {
                        if (entity instanceof LivingEntity) {
                            livingentity3 = (LivingEntity) entity;
                            itemstack = livingentity3.getItemBySlot(EquipmentSlot.HEAD);
                        } else {
                            itemstack = ItemStack.EMPTY;
                        }

                        map2 = EnchantmentHelper.getEnchantments(itemstack);
                        if (map2.containsKey(Enchantments.UNBREAKING)) {
                            map2.remove(Enchantments.UNBREAKING);
                            if (entity instanceof LivingEntity) {
                                livingentity3 = (LivingEntity) entity;
                                itemstack1 = livingentity3.getItemBySlot(EquipmentSlot.HEAD);
                            } else {
                                itemstack1 = ItemStack.EMPTY;
                            }

                            EnchantmentHelper.setEnchantments(map2, itemstack1);
                        }
                    }
                }
            }

        }
    }
}
