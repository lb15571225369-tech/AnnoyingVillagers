package com.pla.annoyingvillagers.item;

//import com.pla.annoyingvillagers.AnnoyingVillagers;
//import mod.chloeprime.aaaparticles.api.common.AAALevel;
//import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
//import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class EmeraldSwordItem extends SwordItem {
    public EmeraldSwordItem() {
        super(new Tier() {
            public int getUses() {
                return 1680;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 5.4F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 18;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[]{new ItemStack(Items.EMERALD)});
            }
        }, 3, -1.5F, (new Properties()));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pPlayer.level().isClientSide() && pPlayer instanceof ServerPlayer serverPlayer) {
//            SwordConvergenceBurst.shootOnly(serverPlayer);
        }
//        if (pPlayer.isShiftKeyDown()) {
//            if (pLevel.isClientSide) {
//                AAALevel.addParticle(pLevel, false,
//                        new ParticleEmitterInfo(new ResourceLocation(AnnoyingVillagers.MODID, "dragon_beam"))
//                                .clone()
//                                .position(pPlayer.getX(), pPlayer.getY(), pPlayer.getZ()));
//            }
//        } else {
//            if (pLevel.isClientSide) {
//                AAALevel.addParticle(pLevel, false,
//                        new ParticleEmitterInfo(new ResourceLocation(AnnoyingVillagers.MODID, "dragon_beam_old"))
//                                .clone()
//                                .position(pPlayer.getX(), pPlayer.getY(), pPlayer.getZ()));
//            }
//        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
