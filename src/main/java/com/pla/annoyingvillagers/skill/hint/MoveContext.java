package com.pla.annoyingvillagers.skill.hint;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public final class MoveContext {
    private final Player player;
    private final LivingEntityPatch<?> patch;
    private final ItemStack mainHand;
    private final ItemStack offHand;
    private final CapabilityItem mainCap;
    private final CapabilityItem offCap;
    private final boolean sprinting;
    private final boolean sneaking;
    private final boolean inAir;
    private final boolean swimming;
    private final boolean riding;
    private final boolean usingItem;
    private final int useTicks;
    private final boolean targetingLiving;
    private final HumanoidArm mainArm;
    private final ItemStack chestStack;

    private MoveContext(Player player, LivingEntityPatch<?> patch,
                        ItemStack mainHand, ItemStack offHand,
                        CapabilityItem mainCap, CapabilityItem offCap,
                        boolean sprinting, boolean sneaking, boolean inAir,
                        boolean swimming, boolean riding,
                        boolean usingItem, int useTicks,
                        boolean targetingLiving, HumanoidArm mainArm,
                        ItemStack chestStack) {
        this.player = player;
        this.patch = patch;
        this.mainHand = mainHand;
        this.offHand = offHand;
        this.mainCap = mainCap;
        this.offCap = offCap;
        this.sprinting = sprinting;
        this.sneaking = sneaking;
        this.inAir = inAir;
        this.swimming = swimming;
        this.riding = riding;
        this.usingItem = usingItem;
        this.useTicks = useTicks;
        this.targetingLiving = targetingLiving;
        this.mainArm = mainArm;
        this.chestStack = chestStack;
    }

    public static MoveContext snapshot(Player player) {
        if (player == null) return null;
        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();
        CapabilityItem mainCap = EpicFightCapabilities.getItemStackCapability(main);
        CapabilityItem offCap = EpicFightCapabilities.getItemStackCapability(off);
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(player, LivingEntityPatch.class);
        boolean sprint = player.isSprinting();
        boolean sneak = player.isShiftKeyDown() || player.isCrouching();
        boolean air = !player.onGround() && !player.isInWater() && !player.isPassenger();
        boolean swim = player.isInWater() || player.isSwimming();
        boolean ride = player.isPassenger();
        boolean using = player.isUsingItem();
        int useTicks = player.getTicksUsingItem();
        boolean target = peekTarget(player);
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        return new MoveContext(player, patch, main, off, mainCap, offCap,
                sprint, sneak, air, swim, ride, using, useTicks, target, player.getMainArm(), chest);
    }

    private static boolean peekTarget(Player player) {
        Minecraft mc = Minecraft.getInstance();
        if (!(player instanceof LocalPlayer)) return false;
        if (mc.hitResult == null) return false;
        return mc.hitResult.getType() == HitResult.Type.ENTITY
                && ((net.minecraft.world.phys.EntityHitResult) mc.hitResult).getEntity() instanceof LivingEntity;
    }

    public Player player() { return player; }
    public LivingEntityPatch<?> patch() { return patch; }
    public PlayerPatch<?> playerPatch() {
        if (patch instanceof PlayerPatch<?> pp) return pp;
        return null;
    }
    public ItemStack mainHand() { return mainHand; }
    public ItemStack offHand() { return offHand; }
    public ItemStack chestStack() { return chestStack; }
    public CapabilityItem mainCap() { return mainCap; }
    public CapabilityItem offCap() { return offCap; }
    public boolean sprinting() { return sprinting; }
    public boolean sneaking() { return sneaking; }
    public boolean inAir() { return inAir; }
    public boolean swimming() { return swimming; }
    public boolean riding() { return riding; }
    public boolean usingItem() { return usingItem; }
    public int useTicks() { return useTicks; }
    public boolean hasTarget() { return targetingLiving; }
    public HumanoidArm mainArm() { return mainArm; }

    public boolean drawingBow() {
        if (!usingItem) return false;
        ItemStack used = player.getUseItem();
        return used.getItem() instanceof BowItem || used.getItem() instanceof CrossbowItem;
    }

    public boolean grounded() {
        return player.onGround() && !player.isPassenger();
    }

    public CapabilityItem.WeaponCategories mainCategoryEnum() {
        if (mainCap == null) return null;
        var cat = mainCap.getWeaponCategory();
        return cat instanceof CapabilityItem.WeaponCategories c ? c : null;
    }

    public Entity vehicle() {
        return player.getVehicle();
    }
}
