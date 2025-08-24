package com.pla.annoyingvillagers.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.PlayerMobDeadEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

@Mixin(value = {LivingEntity.class}, remap = true)
public class LivingEntityMixin {
    @Inject(method = "die", at = @At("TAIL"), remap = true)
    private void onPlayerMobDie(DamageSource source, CallbackInfo ci) {
        if ((Object) this instanceof PlayerMobEntity entity) {
            ResourceLocation entityTypeId = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());

            if (entityTypeId != null && entityTypeId.equals(new ResourceLocation("player_mobs", "player_mob"))) {
                if (entity.getPersistentData().getBoolean("die_by_possess")) {
                    entity.setInvisible(true);
                    entity.remove(Entity.RemovalReason.KILLED);
                } else if (entity.level() instanceof ServerLevel levelaccessor && AnnoyingVillagersConfig.PHYSIC_MOD_COMPAT.get()) {
                    PlayerMobDeadEntity corpse =  new PlayerMobDeadEntity(AnnoyingVillagersModEntities.PLAYER_MOB_DEAD.get(), levelaccessor);
                    corpse.moveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
                    corpse.setUsername(entity.getUsername());
                    corpse.setProfile(entity.getProfile());
                    corpse.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
                    entity.setInvisible(true);
                    entity.remove(Entity.RemovalReason.KILLED);
                    levelaccessor.addFreshEntity(corpse);
                    new DelayedTask(3) {
                        @Override
                        public void run() {
                            try {
                                corpse.getServer().getCommands().getDispatcher().execute(
                                        "kill @s",
                                        corpse.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {
                            }
                        }
                    };
                }
            }
        }
    }
}
