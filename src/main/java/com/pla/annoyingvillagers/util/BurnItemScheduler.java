package com.pla.annoyingvillagers.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;

import java.util.List;

public class BurnItemScheduler extends DelayedTask {
    private final Mob mob;
    private final CompoundTag data;
    public BurnItemScheduler(Mob mob) {
        super(20);
        this.mob = mob;
        this.data = mob.getPersistentData();
    }

    private void resetItem() throws CommandSyntaxException {
        if (data.contains("backup_main_hand")) {
            CompoundTag fullTag = TagParser.parseTag(data.getString("backup_main_hand"));
            String id = fullTag.getString("id");
            String nbtPart = fullTag.contains("tag") ? fullTag.getCompound("tag").toString() : "";
            String cmd = "item replace entity @s weapon.mainhand with " + id;
            if (!nbtPart.isEmpty()) {
                cmd += nbtPart;
            }
            mob.getServer().getCommands().performCommand(
                    mob.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                    cmd
            );
            data.remove("backup_main_hand");
        }
    }

    @Override
    public void run() throws CommandSyntaxException {
        if (!(mob.level instanceof ServerLevel serverLevel)) return;

        if (mob.getTarget() != null) {
            resetItem();
            return;
        }

        List<ItemEntity> items = mob.level.getEntitiesOfClass(ItemEntity.class, mob.getBoundingBox().inflate(10));
        ItemEntity targetItem = items.stream()
                .filter(item -> item.isAlive() && !item.getItem().isEmpty())
                .findFirst()
                .orElse(null);

        if (targetItem != null) {
            if (!data.contains("backup_main_hand")) {
                ItemStack held = mob.getMainHandItem();
                if (!held.isEmpty() && held.getItem() != Items.FLINT_AND_STEEL) {
                    CompoundTag tag = new CompoundTag();
                    held.save(tag);
                    data.putString("backup_main_hand", tag.toString());
                }
            }

            if (mob.getMainHandItem().getItem() != Items.FLINT_AND_STEEL) {
                mob.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.FLINT_AND_STEEL));
            }

            if (mob.getNavigation().createPath(targetItem, 0) == null) {
                resetItem();
                return;
            }

            mob.getNavigation().moveTo(targetItem, 1.0);

            if (mob.getTarget() != null) {
                resetItem();
                return;
            }

            if (mob.distanceTo(targetItem) < 1.5) {
                mob.getLookControl().setLookAt(
                        targetItem.getX(),
                        targetItem.getY() + targetItem.getBbHeight() / 2.0,
                        targetItem.getZ(),
                        30.0F,
                        30.0F
                );
                mob.swing(InteractionHand.MAIN_HAND);
                targetItem.kill();

                serverLevel.sendParticles(ParticleTypes.FLAME,
                        targetItem.getX(), targetItem.getY(), targetItem.getZ(),
                        8, 0.2, 0.2, 0.2, 0.01);

                mob.level.playSound(null, mob.blockPosition(), SoundEvents.FLINTANDSTEEL_USE,
                        SoundSource.HOSTILE, 1.0f, 1.0f);
            }
            new BurnItemScheduler(mob);
        } else {
            resetItem();
        }
    }
}