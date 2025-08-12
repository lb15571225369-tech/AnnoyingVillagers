package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEnchantments;

@EventBusSubscriber
public class FastbowProcedure {

    @SubscribeEvent
    public static void onRightClickItem(RightClickItem rightclickitem) {
        if (rightclickitem.getHand() == rightclickitem.getEntity().getUsedItemHand()) {
            execute(rightclickitem, rightclickitem.getLevel(), (double) rightclickitem.getPos().getX(), (double) rightclickitem.getPos().getY(), (double) rightclickitem.getPos().getZ(), rightclickitem.getEntity());
        }
    }

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        execute((Event) null, levelaccessor, d0, d1, d2, entity);
    }

    private static Projectile getArrow(Level level1, Entity entity1, float f, int i) {
        Arrow arrow = new Arrow(EntityType.ARROW, level1);
        arrow.setOwner(entity1);
        arrow.setBaseDamage(f);
        arrow.setKnockback(i);
        arrow.setCritArrow(true);
        arrow.pickup = Pickup.ALLOWED;
        return arrow;
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            ItemStack itemstack;

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;

                itemstack = livingentity.getMainHandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            if (itemstack.getItem() instanceof BowItem && entity instanceof Player) {
                Player player = (Player)entity;

                if (player.getInventory().contains(new ItemStack(Items.ARROW))) {
                    Enchantment enchantment = (Enchantment)AnnoyingVillagersModEnchantments.FAST_SHOT.get();
                    ItemStack itemstack1;

                    if (entity instanceof LivingEntity) {
                        LivingEntity livingentity1 = (LivingEntity)entity;

                        itemstack1 = livingentity1.getMainHandItem();
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack1) != 0) {
                        Level level = entity.level();

                        if (!level.isClientSide()) {
                            Enchantment enchantment1 = (Enchantment)AnnoyingVillagersModEnchantments.FAST_SHOT.get();
                            LivingEntity livingentity2;
                            ItemStack itemstack2;

                            if (entity instanceof LivingEntity) {
                                livingentity2 = (LivingEntity)entity;
                                itemstack2 = livingentity2.getMainHandItem();
                            } else {
                                itemstack2 = ItemStack.EMPTY;
                            }


                            Projectile projectile = getArrow(level, entity, (float)(1 + EnchantmentHelper.getItemEnchantmentLevel(enchantment1, itemstack2)), 0);

                            projectile.setPos(entity.getX(), entity.getEyeY() - 0.1D, entity.getZ());
                            double d3 = entity.getLookAngle().x;
                            double d4 = entity.getLookAngle().y;
                            double d5 = entity.getLookAngle().z;
                            Enchantment enchantment2 = (Enchantment)AnnoyingVillagersModEnchantments.FAST_SHOT.get();
                            ItemStack itemstack3;

                            if (entity instanceof LivingEntity) {
                                livingentity2 = (LivingEntity)entity;
                                itemstack3 = livingentity2.getMainHandItem();
                            } else {
                                itemstack3 = ItemStack.EMPTY;
                            }

                            projectile.shoot(d3, d4, d5, 2.5F, (float)(3 - EnchantmentHelper.getItemEnchantmentLevel(enchantment2, itemstack3)));
                            level.addFreshEntity(projectile);
                        }

                        if (levelaccessor instanceof Level) {
                            Level level1 = (Level)levelaccessor;

                            if (!level1.isClientSide()) {
                                level1.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.arrow.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                            } else {
                                level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.arrow.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                            }
                        }

                        if (entity instanceof Player) {
                            Player player1 = (Player)entity;
                            ItemStack itemstack4 = new ItemStack(Items.ARROW);

                            player1.getInventory().clearOrCountMatchingItems((itemstack5) -> {
                                return itemstack4.getItem() == itemstack5.getItem();
                            }, 1, player1.inventoryMenu.getCraftSlots());
                        }

                        LivingEntity livingentity3;

                        if (entity instanceof LivingEntity) {
                            livingentity3 = (LivingEntity)entity;
                            itemstack = livingentity3.getMainHandItem();
                        } else {
                            itemstack = ItemStack.EMPTY;
                        }

                        ItemStack itemstack5 = itemstack;
                        Enchantment enchantment3 = (Enchantment)AnnoyingVillagersModEnchantments.FAST_SHOT.get();
                        ItemStack itemstack6;

                        if (entity instanceof LivingEntity) {
                            livingentity3 = (LivingEntity)entity;
                            itemstack6 = livingentity3.getMainHandItem();
                        } else {
                            itemstack6 = ItemStack.EMPTY;
                        }

                        if (itemstack5.hurt(1 + EnchantmentHelper.getItemEnchantmentLevel(enchantment3, itemstack6), RandomSource.create(), (ServerPlayer)null)) {
                            itemstack5.shrink(1);
                            itemstack5.setDamageValue(0);
                        }
                    }
                }
            }

        }
    }
}
