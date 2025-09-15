package com.pla.annoyingvillagers.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class HerobrineEnderEyeItem extends Item {
    public HerobrineEnderEyeItem() {
        super((new Properties()).stacksTo(1).durability(200));
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.literal(
                "An Ender Eye infused with §5Herobrine§r's power.\n" +
                        "§6Right-click§r to shoot 3 Shadow Obsidian Pillars.\n" +
                        "§6Shift Right-click§r to activate Shadow Obsidian Machine Gun."
        ));
    }
}
