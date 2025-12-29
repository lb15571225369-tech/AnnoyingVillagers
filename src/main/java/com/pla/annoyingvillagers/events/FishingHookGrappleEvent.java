package com.pla.annoyingvillagers.events;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FishingHookGrappleEvent {
    private static final String KEY_LATCHED = "avLatched";
    private static final String KEY_AX = "avAX";
    private static final String KEY_AY = "avAY";
    private static final String KEY_AZ = "avAZ";

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Player player = event.player;
        if (player.level().isClientSide()) return;

        FishingHook hook = player.fishing;
        if (hook == null || !hook.isAlive()) return;

        var tag = hook.getPersistentData();
        if (tag.getBoolean(KEY_LATCHED)) return;

        boolean inWater = hook.level().getFluidState(hook.blockPosition()).is(FluidTags.WATER);
        if (inWater) return;

        boolean nearlyStopped = hook.getDeltaMovement().lengthSqr() < 1.0e-3;
        if (hook.onGround() || nearlyStopped) {
            Vec3 anchor = hook.position();
            tag.putBoolean(KEY_LATCHED, true);
            tag.putDouble(KEY_AX, anchor.x);
            tag.putDouble(KEY_AY, anchor.y);
            tag.putDouble(KEY_AZ, anchor.z);
        }
    }

    private static boolean tryPlunge(Player player) {
        FishingHook hook = player.fishing;
        if (hook == null || !hook.isAlive()) return false;

        var tag = hook.getPersistentData();
        if (!tag.getBoolean(KEY_LATCHED)) return false;

        if (player.getCooldowns().isOnCooldown(Items.FISHING_ROD)) return false;

        Vec3 anchor = new Vec3(tag.getDouble(KEY_AX), tag.getDouble(KEY_AY), tag.getDouble(KEY_AZ));
        Vec3 eye = player.position().add(0, player.getEyeHeight(), 0);
        Vec3 dir = anchor.subtract(eye);
        if (dir.lengthSqr() < 1.0e-6) return false;

        dir = dir.normalize();

        boolean grounded = player.onGround();

        double maxY = grounded ? 1.0 : 0.7;
        dir = new Vec3(dir.x, Math.max(-maxY, Math.min(maxY, dir.y)), dir.z);
        Vec3 vel = player.getDeltaMovement();
        if (grounded) {
            vel = vel.add(0, 0.42, 0);
        }

        if (!player.level().isClientSide())
             player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 5, 1, false, false));

        double power = grounded ? 4.1 : 3.1;
        vel = vel.add(dir.scale(power));

        player.setDeltaMovement(vel);
        player.hurtMarked = true;
        player.fallDistance = 0;
        if (true) player.getCooldowns().addCooldown(Items.FISHING_ROD, 20);
        tag.putBoolean(KEY_LATCHED, false);
        return true;
    }

    private static ItemStack getRodInHand(Player p, InteractionHand preferred) {
        ItemStack s = p.getItemInHand(preferred);
        if (s.is(Items.FISHING_ROD)) return s;
        return p.getMainHandItem().is(Items.FISHING_ROD) ? p.getMainHandItem() : p.getOffhandItem();
    }

    private static void retrieveNow(Player player, InteractionHand hand) {
        FishingHook hook = player.fishing;
        if (hook == null || !hook.isAlive()) return;

        ItemStack rod = getRodInHand(player, hand);
        if (!rod.is(Items.FISHING_ROD)) return;

        int damage = hook.retrieve(rod);
        if (damage > 0) {
            rod.hurtAndBreak(damage, player, pl -> pl.broadcastBreakEvent(hand));
        }
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;
        boolean holdingRod = player.getMainHandItem().is(Items.FISHING_ROD) || player.getOffhandItem().is(Items.FISHING_ROD);
        if (!holdingRod || player.fishing == null) return;
        if (tryPlunge(player)) {
            retrieveNow(player, event.getHand());
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        boolean holdingRod = player.getMainHandItem().is(Items.FISHING_ROD) || player.getOffhandItem().is(Items.FISHING_ROD);
        if (!holdingRod || player.fishing == null) return;

        if (tryPlunge(player)) {
            retrieveNow(player, event.getHand());
            event.setUseItem(Event.Result.DENY);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }
    }

    @SubscribeEvent
    public static void onRightClickEmpty(PlayerInteractEvent.RightClickEmpty event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        boolean holdingRod = player.getMainHandItem().is(Items.FISHING_ROD) || player.getOffhandItem().is(Items.FISHING_ROD);
        if (!holdingRod || player.fishing == null) return;

        if (tryPlunge(player)) {
            retrieveNow(player, event.getHand());
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }
    }
}
