package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.compat.player_mobs.ModCapabilities;
import com.pla.annoyingvillagers.util.CommonGoals;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

@Mixin(value = {Mob.class}, remap = true)
public class MobMixin {
    @Inject(method = "dropCustomDeathLoot", at = @At("TAIL"))
    private void dropCustomInventoryOnDeath(DamageSource source, int looting, boolean recentlyHit, CallbackInfo ci) {
        if (!((Object) this instanceof PlayerMobEntity entity)) return;
        entity.getCapability(ModCapabilities.PLAYER_MOB_INVENTORY).ifPresent(cap -> {
            ItemStackHandler inventory = cap.getInventory();
            for (int i = 0; i < inventory.getSlots(); i++) {
                ItemStack stack = inventory.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    ItemEntity itemEntity = new ItemEntity(
                            entity.level(),
                            entity.getX() + (entity.level().random.nextFloat() - 0.5) * 0.5,
                            entity.getY() + 0.5,
                            entity.getZ() + (entity.level().random.nextFloat() - 0.5) * 0.5,
                            stack.copy()
                    );

                    itemEntity.setDefaultPickUpDelay();
                    float speed = 0.05F;
                    itemEntity.setDeltaMovement(
                            (entity.level().random.nextFloat() - 0.5) * speed,
                            entity.level().random.nextFloat() * speed,
                            (entity.level().random.nextFloat() - 0.5) * speed
                    );
                    new DelayedTask(20) {
                        @Override
                        public void run() {
                            entity.level().addFreshEntity(itemEntity);
                        }
                    };
                }
            }
        });
    }

    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void villagerRunAway(CallbackInfo ci) {
        Mob self = (Mob) (Object) this;
        if (self instanceof AbstractVillager abstractVillager) {
            CommonGoals.runAwayFromHerobrineGoals(abstractVillager, 5.0F);
            abstractVillager.goalSelector.addGoal(1, new AvoidEntityGoal<>(abstractVillager, AbstractIllager.class, 5.0F, 1.2D, 1.8D));
            abstractVillager.goalSelector.addGoal(1, new AvoidEntityGoal<>(abstractVillager, PlayerMobEntity.class, 5.0F, 1.2D, 1.8D));
            abstractVillager.goalSelector.addGoal(1, new AvoidEntityGoal<>(abstractVillager, Player.class, 5.0F, 1.2D, 1.8D));
        }
    }
}

