package com.pla.annoyingvillagers.item;

import java.util.List;

import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class DemoniacVoltageReaverItem extends SwordItem {

    public DemoniacVoltageReaverItem() {
        super(new Tier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 3.0F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 4;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 3, -3.0F, (new Properties()));
    }

    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.literal(Component.translatable("tooltip.annoyingvillagers.demoniac_voltage_reaver").getString() + ")§r"));
    }

    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        if (flag && entity instanceof Player player) {
            PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
            if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.DEMONIAC_VOLTAGE_REAVER);
                if (skillContainer != null) {
                    if (skillContainer.getStack() >= 1) {
                        HerobrineUtil.spawnEliteEffect(level, entity.getX(), entity.getY(), entity.getZ(), entity);
                        if (itemstack.getTag() != null && !itemstack.getTag().getBoolean("SecondForm")) {
                            itemstack.getTag().putBoolean("SecondForm", true);
                        }
                    } else if (skillContainer.getStack() < 1 && itemstack.getTag() != null && itemstack.getTag().getBoolean("SecondForm")) {
                        itemstack.getTag().remove("SecondForm");
                    }
                }
            }
        }
        if (!flag && itemstack.getTag().getBoolean("SnakeAnimation")) {
            itemstack.getTag().remove("SnakeAnimation");
        }
    }
}
