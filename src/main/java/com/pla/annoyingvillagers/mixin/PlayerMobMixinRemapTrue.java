package com.pla.annoyingvillagers.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.compat.player_mobs.ModCapabilities;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(value = {PlayerMobEntity.class}, remap = true)
public class PlayerMobMixinRemapTrue {
    @Inject(method = "isBaby", at = @At("HEAD"), cancellable = true)
    private void forceIsNotBaby(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

    @Inject(method = "setBaby", at = @At("HEAD"), cancellable = true)
    private void blockSetBaby(boolean isChild, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "finalizeSpawn", at = @At("RETURN"))
    private void teleportToSurfaceIfUnderground(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag, CallbackInfoReturnable<SpawnGroupData> cir) {
        PlayerMobEntity self = (PlayerMobEntity) (Object) this;

        if (world instanceof ServerLevel level && level.isDay()) {
            BlockPos pos = self.blockPosition();
            int currentY = pos.getY();
            int surfaceY = world.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, pos).getY();
            if (currentY < surfaceY - 5 && world.getRandom().nextFloat() < 0.6F) {
                BlockPos surfacePos = new BlockPos(pos.getX(), surfaceY, pos.getZ());
                self.setPos(surfacePos.getX() + 0.5, surfacePos.getY(), surfacePos.getZ() + 0.5);
            }
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void addInventorySaveData(CompoundTag compound, CallbackInfo ci) {
        PlayerMobEntity self = (PlayerMobEntity) (Object) this;
        self.getCapability(ModCapabilities.PLAYER_MOB_INVENTORY).ifPresent(cap -> {
            compound.put("CustomInventory", cap.getInventory().serializeNBT());
        });
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readInventorySaveData(CompoundTag compound, CallbackInfo ci) {
        PlayerMobEntity self = (PlayerMobEntity) (Object) this;
        self.getCapability(ModCapabilities.PLAYER_MOB_INVENTORY).ifPresent(cap -> {
            cap.getInventory().deserializeNBT(compound.getCompound("CustomInventory"));
        });
    }


    @Inject(method = "tick", at = @At("TAIL"))
    private void tickingInventory(CallbackInfo ci) {
        PlayerMobEntity self = (PlayerMobEntity) (Object) this;
        if (!self.level.isClientSide && self.tickCount % 20 == 0) {
            if (self.getHealth() <= 0.0F) return;
            List<ItemEntity> items = self.level.getEntitiesOfClass(ItemEntity.class, self.getBoundingBox().inflate(2));
            for (ItemEntity item : items) {
                if (!item.hasPickUpDelay() && !item.isRemoved()) {
                    final ItemEntity itemEntity = item;
                    self.getCapability(ModCapabilities.PLAYER_MOB_INVENTORY).ifPresent(cap -> {
                        ItemStackHandler inv = cap.getInventory();
                        ItemStack stack = itemEntity.getItem();
                        ItemStack remaining = stack.copy();

                        for (int i = 0; i < inv.getSlots(); i++) {
                            remaining = inv.insertItem(i, remaining, false);
                            if (remaining.isEmpty()) break;
                        }
                        if (remaining.isEmpty()) {
                            itemEntity.setDeltaMovement(
                                    (self.getX() - itemEntity.getX()) * 0.25,
                                    (self.getY() + 1.0 - itemEntity.getY()) * 0.25,
                                    (self.getZ() - itemEntity.getZ()) * 0.25
                            );
                            itemEntity.setPickUpDelay(0);
                            new DelayedTask(5) {
                                @Override
                                public void run() {
                                    itemEntity.discard();
                                    self.level.playSound(null, self.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.HOSTILE, 0.2F, 1.0F);
                                }
                            };
                        } else {
                            itemEntity.setItem(remaining);
                        }
                    });
                }
            }
        }
    }
}