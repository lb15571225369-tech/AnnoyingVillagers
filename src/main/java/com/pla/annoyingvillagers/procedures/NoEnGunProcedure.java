//package com.pla.annoyingvillagers.procedures;
//
//import javax.annotation.Nullable;
//import net.minecraft.commands.CommandSourceStack;
//import net.minecraft.commands.Commands;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.item.ItemStack;
//import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
//import net.minecraftforge.eventbus.api.Event;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
//import net.minecraftforge.registries.ForgeRegistries;
//import net.minecraftforge.registries.IForgeRegistry;
//
//@EventBusSubscriber
//public class NoEnGunProcedure {
//
//    @SubscribeEvent
//    public static void onRightClickItem(RightClickItem rightclickitem) {
//        if (rightclickitem.getHand() == rightclickitem.getPlayer().getUsedItemHand()) {
//            execute(rightclickitem, rightclickitem.getPlayer());
//        }
//    }
//
//    public static void execute(Entity entity) {
//        execute((Event) null, entity);
//    }
//
//    private static void execute(@Nullable Event event, Entity entity) {
//        if (entity != null) {
//            ItemStack itemstack;
//
//            if (entity instanceof LivingEntity) {
//                LivingEntity livingentity = (LivingEntity) entity;
//
//                itemstack = livingentity.getMainHandItem();
//            } else {
//                itemstack = ItemStack.EMPTY;
//            }
//
//            if (itemstack.getOrCreateTag().getDouble("AmmoCount") >= 2.0D) {
//                if (entity instanceof LivingEntity) {
//                    LivingEntity livingentity1 = (LivingEntity) entity;
//
//                    itemstack = livingentity1.getMainHandItem();
//                } else {
//                    itemstack = ItemStack.EMPTY;
//                }
//
//                if (itemstack.isEnchanted() && !entity.level.isClientSide() && entity.getServer() != null) {
//                    Commands commands = entity.getServer().getCommands();
//                    CommandSourceStack commandsourcestack = entity.createCommandSourceStack().withSuppressedOutput().withPermission(4);
//                    IForgeRegistry iforgeregistry = ForgeRegistries.ITEMS;
//                    ItemStack itemstack1;
//
//                    if (entity instanceof LivingEntity) {
//                        LivingEntity livingentity2 = (LivingEntity) entity;
//
//                        itemstack1 = livingentity2.getMainHandItem();
//                    } else {
//                        itemstack1 = ItemStack.EMPTY;
//                    }
//
//                    commands.performCommand(commandsourcestack, "item replace entity @s weapon.mainhand with " + iforgeregistry.getKey(itemstack1.getItem()).toString());
//                }
//            }
//
//        }
//    }
//}
