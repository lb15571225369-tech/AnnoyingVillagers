package com.pla.annoyingvillagers.procedures;

import io.netty.buffer.Unpooled;
import java.util.HashMap;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
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
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import com.pla.annoyingvillagers.world.inventory.IdCheckMenu;

public class LoginPhoneIdProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity, HashMap hashmap) {
        if (entity != null && hashmap != null) {
            if (!entity.level.isClientSide() && entity.getServer() != null) {
                Commands commands = entity.getServer().getCommands();
                CommandSourceStack commandsourcestack = entity.createCommandSourceStack().withSuppressedOutput().withPermission(4);
                String s = hashmap.containsKey("text:code") ? ((EditBox) hashmap.get("text:code")).getValue() : "";

                commands.performCommand(commandsourcestack, "music code " + s);
            }

            if (entity instanceof Player) {
                Player player = (Player) entity;

                player.closeContainer();
            }

            if (entity instanceof ServerPlayer) {
                ServerPlayer serverplayer = (ServerPlayer) entity;
                final BlockPos blockpos = new BlockPos(d0, d1, d2);

                NetworkHooks.openGui(serverplayer, new MenuProvider() {
                    public Component getDisplayName() {
                        return new TextComponent("IdCheck");
                    }

                    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player1) {
                        return new IdCheckMenu(i, inventory, (new FriendlyByteBuf(Unpooled.buffer())).writeBlockPos(blockpos));
                    }
                }, blockpos);
            }

        }
    }
}
