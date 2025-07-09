//package com.pla.annoyingvillagers.procedures;
//
//import com.mojang.brigadier.context.CommandContext;
//import com.mojang.brigadier.exceptions.CommandSyntaxException;
//import net.minecraft.commands.CommandSourceStack;
//import net.minecraft.commands.arguments.EntityArgument;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.GameRules;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraftforge.items.ItemHandlerHelper;
//
//public class GivePlayerItemProcedure {
//
//    public static void execute(LevelAccessor levelaccessor, final CommandContext<CommandSourceStack> commandcontext, Entity entity) {
//        if (entity != null) {
//            if (levelaccessor.getLevelData().getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
//                int i;
//
//                if (entity instanceof Player) {
//                    Player player = (Player)entity;
//
//                    i = player.experienceLevel;
//                } else {
//                    i = 0;
//                }
//
//                Player player1;
//
//                if (i >= 13) {
//                    Entity entity1 = ((<undefinedtype>)(new Object() {
//                        public Entity getEntity() {
//                            try {
//                                return EntityArgument.getEntity(commandcontext, "players");
//                            } catch (CommandSyntaxException commandsyntaxexception) {
//                                commandsyntaxexception.printStackTrace();
//                                return null;
//                            }
//                        }
//                    })).getEntity();
//                    LivingEntity livingentity;
//                    ItemStack itemstack;
//                    ItemStack itemstack1;
//
//                    if (entity1 instanceof Player) {
//                        player1 = (Player)entity1;
//                        if (entity instanceof LivingEntity) {
//                            livingentity = (LivingEntity)entity;
//                            itemstack = livingentity.getMainHandItem();
//                        } else {
//                            itemstack = ItemStack.EMPTY;
//                        }
//
//                        itemstack1 = itemstack;
//                        itemstack1.setCount(1);
//                        ItemHandlerHelper.giveItemToPlayer(player1, itemstack1);
//                    }
//
//                    if (entity instanceof Player) {
//                        player1 = (Player)entity;
//                        if (!player1.level.isClientSide()) {
//                            Object object = new Object() {
//                                public Entity getEntity() {
//                                    try {
//                                        return EntityArgument.getEntity(commandcontext, "players");
//                                    } catch (CommandSyntaxException commandsyntaxexception) {
//                                        commandsyntaxexception.printStackTrace();
//                                        return null;
//                                    }
//                                }
//                            };
//
//                            player1.displayClientMessage(new TextComponent("\u5df2\u5c06\u4e3b\u624b\u7269\u54c1\u5bc4\u5230" + ((<undefinedtype>)object).getEntity() + "\u80cc\u5305\u4e2d"), false);
//                        }
//                    }
//
//                    if (entity instanceof Player) {
//                        player1 = (Player)entity;
//                        player1.giveExperienceLevels(-13);
//                    }
//
//                    if (entity instanceof Player) {
//                        player1 = (Player)entity;
//                        if (entity instanceof LivingEntity) {
//                            livingentity = (LivingEntity)entity;
//                            itemstack = livingentity.getMainHandItem();
//                        } else {
//                            itemstack = ItemStack.EMPTY;
//                        }
//
//                        itemstack1 = itemstack;
//                        player1.getInventory().clearOrCountMatchingItems((itemstack2) -> {
//                            return itemstack1.getItem() == itemstack2.getItem();
//                        }, 1, player1.inventoryMenu.getCraftSlots());
//                    }
//                } else if (entity instanceof Player) {
//                    player1 = (Player)entity;
//                    if (!player1.level.isClientSide()) {
//                        player1.displayClientMessage(new TextComponent("\u4f60\u7684\u7ecf\u9a8c\u7b49\u7ea7\u4e0d\u8db313\u7ea7\uff0c\u65e0\u6cd5\u8fdc\u7a0b\u4f20\u9001\u7269\u54c1"), false);
//                    }
//                }
//            }
//
//        }
//    }
//}
