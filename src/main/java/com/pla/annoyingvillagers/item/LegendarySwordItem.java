package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.skill.LegendarySwordSkill;
import com.pla.annoyingvillagers.util.ArmorUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.List;
import java.util.Random;

public class LegendarySwordItem extends SwordItem {

    public LegendarySwordItem() {
        super(new Tier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 4.0F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 2;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 3, -2.32F, (new Properties()).fireResistant());
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        if (!pAttacker.level().isClientSide()) {
            ArmorUtil.damageArmor(pTarget, new Random().nextInt(1, 5));
        }
        if (pAttacker instanceof Player player) {
            PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
            if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.LEGENDARY_SWORD);
                if (skillContainer == null) return super.hurtEnemy(pStack, pTarget, pAttacker);
                LegendarySwordSkill legendarySwordSkill = (LegendarySwordSkill) skillContainer.getSkill();

                float currentResource = skillContainer.getResource();
                float neededResource = skillContainer.getNeededResource();
                float addResource = Math.min(2.0F, neededResource);
                legendarySwordSkill.setConsumptionSynchronize(skillContainer, currentResource + addResource);
            }
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    public boolean isRepairable(@NotNull ItemStack itemStack) {
        return false;
    }

    public void appendHoverText(@NotNull ItemStack itemStack, Level level, @NotNull List<Component> componentList, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, componentList, tooltipFlag);
        componentList.add(Component.translatable("tooltip.annoyingvillagers.legendary_sword"));
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(@NotNull ItemStack itemStack) {
        return true;
    }
}
