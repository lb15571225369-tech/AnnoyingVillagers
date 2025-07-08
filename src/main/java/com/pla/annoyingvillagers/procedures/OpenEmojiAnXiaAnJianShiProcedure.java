package com.pla.annoyingvillagers.procedures;

import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import com.pla.annoyingvillagers.world.inventory.EmojiMenu;
import com.pla.annoyingvillagers.world.inventory.EmoteMenu;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.HitAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class OpenEmojiAnXiaAnJianShiProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

            if (livingentitypatch != null) {
                DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();

                if (!(dynamicanimation instanceof AttackAnimation) && !(dynamicanimation instanceof LongHitAnimation) && !(dynamicanimation instanceof HitAnimation)) {
                    if (!((<undefinedtype>)(new Object() {
                        public boolean checkGamemode(Entity entity1) {
                            if (entity1 instanceof ServerPlayer) {
                                ServerPlayer serverplayer = (ServerPlayer)entity1;

                                return serverplayer.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
                            } else if (entity1.level.isClientSide() && entity1 instanceof Player) {
                                Player player = (Player)entity1;

                                return Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId()).getGameMode() == GameType.SPECTATOR;
                            } else {
                                return false;
                            }
                        }
                    })).checkGamemode(entity)) {
                        ServerPlayer serverplayer;
                        final BlockPos blockpos;

                        if (!entity.getPersistentData().getBoolean("next_page")) {
                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "epicfight mode mining @s");
                            }

                            if (entity instanceof ServerPlayer) {
                                serverplayer = (ServerPlayer)entity;
                                blockpos = new BlockPos(d0, d1, d2);
                                NetworkHooks.openGui(serverplayer, new MenuProvider() {
                                    public Component getDisplayName() {
                                        return new TextComponent("Emoji");
                                    }

                                    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                                        return new EmojiMenu(i, inventory, (new FriendlyByteBuf(Unpooled.buffer())).writeBlockPos(blockpos));
                                    }
                                }, blockpos);
                            }
                        } else {
                            if (entity instanceof ServerPlayer) {
                                serverplayer = (ServerPlayer)entity;
                                blockpos = new BlockPos(d0, d1, d2);
                                NetworkHooks.openGui(serverplayer, new MenuProvider() {
                                    public Component getDisplayName() {
                                        return new TextComponent("Emote");
                                    }

                                    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                                        return new EmoteMenu(i, inventory, (new FriendlyByteBuf(Unpooled.buffer())).writeBlockPos(blockpos));
                                    }
                                }, blockpos);
                            }

                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "epicfight mode mining @s");
                            }
                        }
                    }
                } else {
                    entity.clearFire();
                }
            }

        }
    }
}
