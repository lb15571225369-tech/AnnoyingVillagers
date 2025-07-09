package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class EnderPearlLandingProcedure {

    @SubscribeEvent
    public static void onRightClickItem(RightClickItem rightclickitem) {
        if (rightclickitem.getHand() == rightclickitem.getPlayer().getUsedItemHand()) {
            execute(rightclickitem, rightclickitem.getWorld(), (double) rightclickitem.getPos().getX(), (double) rightclickitem.getPos().getY(), (double) rightclickitem.getPos().getZ(), rightclickitem.getPlayer());
        }
    }

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        execute((Event) null, levelaccessor, d0, d1, d2, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            ItemStack itemstack;

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                itemstack = livingentity.getMainHandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity1 = (LivingEntity) entity;

                    itemstack = livingentity1.getOffhandItem();
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                if (itemstack.getItem() == Blocks.TNT.asItem()) {
                    if (levelaccessor instanceof ServerLevel) {
                        ServerLevel serverlevel = (ServerLevel) levelaccessor;
                        PrimedTnt primedtnt = new PrimedTnt(EntityType.TNT, serverlevel);

                        primedtnt.moveTo(d0, d1, d2, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
//                        if (primedtnt instanceof Mob) {
//                            Mob mob = (Mob) primedtnt;
//
//                            mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(primedtnt.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
//                        }

                        levelaccessor.addFreshEntity(primedtnt);
                    }

                    if (entity instanceof Player) {
                        Player player = (Player) entity;
                        ItemStack itemstack1 = new ItemStack(Blocks.TNT);

                        player.getInventory().clearOrCountMatchingItems((itemstack2) -> {
                            return itemstack1.getItem() == itemstack2.getItem();
                        }, 1, player.inventoryMenu.getCraftSlots());
                    }
                }
            }

        }
    }
}
