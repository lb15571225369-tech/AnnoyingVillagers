package com.pla.annoyingvillagers.item;

import java.util.List;

import com.pla.annoyingvillagers.procedures.DemoniacVoltageReaverOnUseProcedure;
import com.pla.annoyingvillagers.procedures.HerobrineWeaponEffectProcedure;
import com.pla.annoyingvillagers.util.SnakeBladeHit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

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
                return 18.0F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 4;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 3, -3.0F, (new Properties()));
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        InteractionResultHolder<ItemStack> interactionresultholder = super.use(level, player, interactionhand);

        DemoniacVoltageReaverOnUseProcedure.execute(level, player.getX(), player.getY(), player.getZ(), player, player.getMainHandItem());
        return interactionresultholder;
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (itemstack.getTag().getBoolean("SecondForm") && !itemstack.getTag().getBoolean("SnakeAnimation")) {
            if (itemstack.getTag().getInt("HitCount") >= 5) {
                if (SnakeBladeHit.process(itemstack, pAttacker)) {
                    itemstack.getOrCreateTag().putBoolean("SnakeAnimation", true);
                    itemstack.removeTagKey("HitCount");
                }

                if (!pAttacker.level().isClientSide()) {
                    pAttacker.level().playSound((Player) null, new BlockPos((int) pAttacker.getX(), (int) pAttacker.getY(), (int) pAttacker.getZ()), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:second_form_release")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                } else {
                    pAttacker.level().playLocalSound(pAttacker.getX(), pAttacker.getY(), pAttacker.getZ(), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:second_form_release")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                }
            } else {
                itemstack.getTag().putInt("HitCount", (itemstack.getTag().contains("HitCount") ? itemstack.getTag().getInt("HitCount") : 0) + 1);
            }
        }

        return super.hurtEnemy(itemstack, pTarget, pAttacker);
    }

    String getCurrentComboAttack(ItemStack itemstack) {
        if (itemstack.getTag().getBoolean("SecondForm") && !itemstack.getTag().getBoolean("SnakeAnimation")) {
            return String.format("%d/5", itemstack.getTag().contains("HitCount") ? itemstack.getTag().getInt("HitCount") : 0);
        } else {
            return String.format("∞/∞");
        }
    }

    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.literal("One of Herobrine's legendary weapons.\n" +
                "§aNormal Form§r: A basic greatsword with no special abilities.\n" +
                "§eAwakened Form§r: Shift + right-click to swap forms. After 5 successful hits, the weapon transforms into the §5Second Form§r.\n" +
                "§5Second Form§r: Unleashes a chain of Snake Blades that track enemies in a wide area, dealing damage, stunning, and knocking them back.\n" +
                "§7(Current Combo Attack: " + getCurrentComboAttack(itemstack) + ")§r"));
    }

    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        if (flag && itemstack.getTag().getBoolean("SecondForm")) {
            HerobrineWeaponEffectProcedure.execute(level, entity.getX(), entity.getY(), entity.getZ(), entity);
        }
        if (!flag && itemstack.getTag().getBoolean("SecondForm") && itemstack.getTag().getBoolean("SnakeAnimation")) {
            itemstack.getTag().remove("SnakeAnimation");
        }
    }
}
