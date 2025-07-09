//package com.pla.annoyingvillagers.item;
//
//import java.util.List;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.InteractionResultHolder;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.CreativeModeTab;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.Item.Properties;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Rarity;
//import net.minecraft.world.item.TooltipFlag;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.state.BlockState;
//import com.pla.annoyingvillagers.procedures.TimeBombDangYouJianDianJiKongQiShiProcedure;
//
//public class TimeBombItem extends Item {
//
//    public TimeBombItem() {
//        super((new Properties()).tab(CreativeModeTab.TAB_TOOLS).stacksTo(1).rarity(Rarity.COMMON));
//    }
//
//    public float getDestroySpeed(ItemStack itemstack, BlockState blockstate) {
//        return 0.0F;
//    }
//
//    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
//        super.appendHoverText(itemstack, level, list, tooltipflag);
//        list.add(new TextComponent("\u5b9a\u65f6C4\u70b8\u5f39\uff0c\u4e0b\u8e72\u53f3\u952e\u5b89\u653e"));
//    }
//
//    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
//        InteractionResultHolder<ItemStack> interactionresultholder = super.use(level, player, interactionhand);
//        ItemStack itemstack = (ItemStack) interactionresultholder.getObject();
//        double d0 = player.getX();
//        double d1 = player.getY();
//        double d2 = player.getZ();
//
//        TimeBombDangYouJianDianJiKongQiShiProcedure.execute(level, d0, d1, d2, player, itemstack);
//        return interactionresultholder;
//    }
//}
