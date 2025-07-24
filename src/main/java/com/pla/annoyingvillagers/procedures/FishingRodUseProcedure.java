package com.pla.annoyingvillagers.procedures;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class FishingRodUseProcedure {

    @SubscribeEvent
    public static void onRightClickItem(RightClickItem rightclickitem) {
        if (rightclickitem.getHand() == rightclickitem.getEntity().getUsedItemHand()) {
            execute(rightclickitem, rightclickitem.getLevel(), (double) rightclickitem.getPos().getX(), (double) rightclickitem.getPos().getY(), (double) rightclickitem.getPos().getZ(), rightclickitem.getEntity());
        }
    }

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        execute((Event) null, levelaccessor, d0, d1, d2, entity);
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

            if (itemstack.getItem() == Items.FISHING_ROD) {
                if (entity.isShiftKeyDown()) {
                    entity.setShiftKeyDown(true);
                } else if (!levelaccessor.getEntitiesOfClass(FishingHook.class, AABB.ofSize(new Vec3(d0, d1, d2), 70.0D, 70.0D, 70.0D), (fishinghook) -> {
                    return true;
                }).isEmpty()) {
                    Vec3 vec3 = new Vec3(d0, d1, d2);
                    List<Entity> list = (List)levelaccessor.getEntitiesOfClass(Entity.class, (new AABB(vec3, vec3)).inflate(35.0D), (entity1) -> {
                        return true;
                    }).stream().sorted(Comparator.comparingDouble((entity1) -> {
                        return entity1.distanceToSqr(vec3);
                    })).collect(Collectors.toList());
                    Iterator iterator = list.iterator();

                    while(iterator.hasNext()) {
                        Entity entity1 = (Entity)iterator.next();

                        if (levelaccessor.getEntitiesOfClass(FishingHook.class,
                                        AABB.ofSize(new Vec3(d0, d1, d2), 70.0D, 70.0D, 70.0D),
                                        fishinghook -> true)
                                .stream()
                                .sorted(Comparator.comparingDouble(entity2 -> entity2.distanceToSqr(d0, d1, d2)))
                                .findFirst()
                                .orElse(null) == entity1 && entity1.onGround()) {
                            entity1.lookAt(Anchor.EYES, new Vec3(entity.getX(), entity.getY(), entity.getZ()));
                            entity.setDeltaMovement(new Vec3(0.0D, 0.9D, 0.0D));
                            if (entity instanceof LivingEntity) {
                                LivingEntity livingentity1 = (LivingEntity)entity;

                                if (!livingentity1.level().isClientSide()) {
                                    livingentity1.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 5, 2, false, false));
                                }
                            }

                            entity.setDeltaMovement(new Vec3(entity1.getLookAngle().x * -3.1D, entity1.getLookAngle().y * -3.1D, entity1.getLookAngle().z * -3.1D));
                            if (entity instanceof Player) {
                                Player player = (Player)entity;
                                ItemCooldowns itemcooldowns = player.getCooldowns();
                                ItemStack itemstack1;

                                if (entity instanceof LivingEntity) {
                                    LivingEntity livingentity2 = (LivingEntity)entity;

                                    itemstack1 = livingentity2.getMainHandItem();
                                } else {
                                    itemstack1 = ItemStack.EMPTY;
                                }

                                itemcooldowns.addCooldown(itemstack1.getItem(), 20);
                            }
                        }
                    }
                }
            }

        }
    }
}
