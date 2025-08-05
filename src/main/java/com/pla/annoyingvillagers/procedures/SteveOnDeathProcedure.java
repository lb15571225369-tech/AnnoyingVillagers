package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.entity.AlexDeadEntity;
import com.pla.annoyingvillagers.entity.Steve2Entity;
import com.pla.annoyingvillagers.entity.SteveEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class SteveOnDeathProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            Entity entity1 = entity;

            if (!entity1.level().isClientSide() && entity1.getServer() != null) {
                try {
                    entity1.getServer().getCommands().getDispatcher().execute(
                            "execute at @s run particle minecraft:totem_of_undying ^ ^1.5 ^ 0 0 0 1 1000",
                            entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
            }

            LevelAccessor levelaccessor1 = levelaccessor;

            if (levelaccessor1 instanceof Level) {
                Level level = (Level)levelaccessor1;

                if (!level.isClientSide()) {
                    level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.totem.use")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                } else {
                    level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.totem.use")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                }
            }
            if (levelaccessor instanceof ServerLevel) {
                ServerLevel serverlevel = (ServerLevel)levelaccessor;
                Steve2Entity steve2Entity = new Steve2Entity((EntityType) AnnoyingVillagersModEntities.STEVE_2.get(), serverlevel);

                steve2Entity.moveTo(d0, d1, d2, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                InventoryUtils.transferInventory(((SteveEntity) entity).getInventory(), steve2Entity.getInventory());
                levelaccessor.addFreshEntity(steve2Entity);
            }
        }
    }
}
