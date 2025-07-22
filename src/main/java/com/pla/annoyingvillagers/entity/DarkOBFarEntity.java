package com.pla.annoyingvillagers.entity;

import java.util.Random;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.procedures.DarkObFarEntityOnHitBlockProcedure;
import com.pla.annoyingvillagers.procedures.DarkObFarEntityOnHitEntityProcedure;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class DarkOBFarEntity extends AbstractArrow implements ItemSupplier {

    public DarkOBFarEntity(SpawnEntity spawnentity, Level level) {
        super((EntityType) AnnoyingVillagersModEntities.DARK_OB_FAR.get(), level);
    }

    public DarkOBFarEntity(EntityType<? extends DarkOBFarEntity> entitytype, Level level) {
        super(entitytype, level);
    }

    public DarkOBFarEntity(EntityType<? extends DarkOBFarEntity> entitytype, double d0, double d1, double d2, Level level) {
        super(entitytype, d0, d1, d2, level);
    }

    public DarkOBFarEntity(EntityType<? extends DarkOBFarEntity> entitytype, LivingEntity livingentity, Level level) {
        super(entitytype, livingentity, level);
    }

    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return new ItemStack((ItemLike) AnnoyingVillagersModBlocks.DARK_OB_UP.get());
    }

    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    protected void doPostHurtEffects(LivingEntity livingentity) {
        super.doPostHurtEffects(livingentity);
        livingentity.setArrowCount(livingentity.getArrowCount() - 1);
    }

    public void onHitEntity(EntityHitResult entityhitresult) {
        super.onHitEntity(entityhitresult);
        DarkObFarEntityOnHitEntityProcedure.execute(this.level, this.getX(), this.getY(), this.getZ());
    }

    public void onHitBlock(BlockHitResult blockhitresult) {
        super.onHitBlock(blockhitresult);
        DarkObFarEntityOnHitBlockProcedure.execute(this.level, (double) blockhitresult.getBlockPos().getX(), (double) blockhitresult.getBlockPos().getY(), (double) blockhitresult.getBlockPos().getZ(), this.getOwner());
    }

    public void tick() {
        super.tick();
        if (this.inGround) {
            this.discard();
        }

    }

    public static DarkOBFarEntity shoot(Level level, LivingEntity livingentity, Random random, float f, double d0, int i) {
        DarkOBFarEntity darkobfarentity = new DarkOBFarEntity((EntityType) AnnoyingVillagersModEntities.DARK_OB_FAR.get(), livingentity, level);

        darkobfarentity.shoot(livingentity.getViewVector(1.0F).x, livingentity.getViewVector(1.0F).y, livingentity.getViewVector(1.0F).z, f * 2.0F, 0.0F);
        darkobfarentity.setSilent(true);
        darkobfarentity.setCritArrow(false);
        darkobfarentity.setBaseDamage(d0);
        darkobfarentity.setKnockback(i);
        level.addFreshEntity(darkobfarentity);
        level.playSound((Player) null, livingentity.getX(), livingentity.getY(), livingentity.getZ(), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.arrow.shoot")), SoundSource.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.5F + 1.0F) + f / 2.0F);
        return darkobfarentity;
    }

    public static DarkOBFarEntity shoot(LivingEntity livingentity, LivingEntity livingentity1) {
        DarkOBFarEntity darkobfarentity = new DarkOBFarEntity((EntityType) AnnoyingVillagersModEntities.DARK_OB_FAR.get(), livingentity, livingentity.level);
        double d0 = livingentity1.getX() - livingentity.getX();
        double d1 = livingentity1.getY() + (double) livingentity1.getEyeHeight() - 1.1D;
        double d2 = livingentity1.getZ() - livingentity.getZ();

        darkobfarentity.shoot(d0, d1 - darkobfarentity.getY() + Math.hypot(d0, d2) * 0.20000000298023224D, d2, 2.0F, 12.0F);
        darkobfarentity.setSilent(true);
        darkobfarentity.setBaseDamage(9.0D);
        darkobfarentity.setKnockback(0);
        darkobfarentity.setCritArrow(false);
        livingentity.level.addFreshEntity(darkobfarentity);
        livingentity.level.playSound((Player) null, livingentity.getX(), livingentity.getY(), livingentity.getZ(), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.arrow.shoot")), SoundSource.PLAYERS, 1.0F, 1.0F / ((new Random()).nextFloat() * 0.5F + 1.0F));
        return darkobfarentity;
    }
}
