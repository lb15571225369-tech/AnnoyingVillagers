package com.pla.annoyingvillagers.entity;

import java.util.Objects;
import java.util.Random;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.procedures.BlueDemonTridentOnProjectileHitBlockProcedure;
import com.pla.annoyingvillagers.procedures.BlueDemonTridentOnProjectileHitEntityProcedure;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
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


@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class BlueDemonTridentEntity extends AbstractArrow implements ItemSupplier {

    public BlueDemonTridentEntity(SpawnEntity spawnentity, Level level) {
        super((EntityType) AnnoyingVillagersModEntities.BLUEDEMONTRIDENT.get(), level);
    }

    public BlueDemonTridentEntity(EntityType<? extends BlueDemonTridentEntity> entitytype, Level level) {
        super(entitytype, level);
    }

    public BlueDemonTridentEntity(EntityType<? extends BlueDemonTridentEntity> entitytype, double d0, double d1, double d2, Level level) {
        super(entitytype, d0, d1, d2, level);
    }

    public BlueDemonTridentEntity(EntityType<? extends BlueDemonTridentEntity> entitytype, LivingEntity livingentity, Level level) {
        super(entitytype, livingentity, level);
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return new ItemStack((ItemLike) AnnoyingVillagersModItems.BLUEDEMONTRIDENT.get());
    }

    public ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    protected void doPostHurtEffects(LivingEntity livingentity) {
        super.doPostHurtEffects(livingentity);
        livingentity.setArrowCount(livingentity.getArrowCount() - 1);
    }

    public void onHitEntity(EntityHitResult entityhitresult) {
        super.onHitEntity(entityhitresult);
        BlueDemonTridentOnProjectileHitEntityProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), entityhitresult.getEntity());
    }

    public void onHitBlock(BlockHitResult blockhitresult) {
        super.onHitBlock(blockhitresult);
        BlueDemonTridentOnProjectileHitBlockProcedure.execute(this.level(), (double) blockhitresult.getBlockPos().getX(), (double) blockhitresult.getBlockPos().getY(), (double) blockhitresult.getBlockPos().getZ());
    }

    public void tick() {
        super.tick();
        if (this.inGround) {
            this.discard();
        }

    }

    public static BlueDemonTridentEntity shoot(Level level, LivingEntity livingentity, Random random, float f, double d0, int i) {
        BlueDemonTridentEntity bluedemontridententity = new BlueDemonTridentEntity((EntityType) AnnoyingVillagersModEntities.BLUEDEMONTRIDENT.get(), livingentity, level);

        bluedemontridententity.shoot(livingentity.getViewVector(1.0F).x, livingentity.getViewVector(1.0F).y, livingentity.getViewVector(1.0F).z, f * 2.0F, 0.0F);
        bluedemontridententity.setSilent(true);
        bluedemontridententity.setCritArrow(false);
        bluedemontridententity.setBaseDamage(d0);
        bluedemontridententity.setKnockback(i);
        bluedemontridententity.setSecondsOnFire(100);
        level.addFreshEntity(bluedemontridententity);
        level.playSound((Player) null, livingentity.getX(), livingentity.getY(), livingentity.getZ(), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "item.trident.throw"))), SoundSource.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.5F + 1.0F) + f / 2.0F);
        return bluedemontridententity;
    }

    public static BlueDemonTridentEntity shoot(LivingEntity livingentity, LivingEntity livingentity1) {
        BlueDemonTridentEntity bluedemontridententity = new BlueDemonTridentEntity((EntityType) AnnoyingVillagersModEntities.BLUEDEMONTRIDENT.get(), livingentity, livingentity.level());
        double d0 = livingentity1.getX() - livingentity.getX();
        double d1 = livingentity1.getY() + (double) livingentity1.getEyeHeight() - 1.1D;
        double d2 = livingentity1.getZ() - livingentity.getZ();

        bluedemontridententity.shoot(d0, d1 - bluedemontridententity.getY() + Math.hypot(d0, d2) * 0.20000000298023224D, d2, 2.2F, 12.0F);
        bluedemontridententity.setSilent(true);
        bluedemontridententity.setBaseDamage(9.0D);
        bluedemontridententity.setKnockback(4);
        bluedemontridententity.setCritArrow(false);
        bluedemontridententity.setSecondsOnFire(100);
        livingentity.level().addFreshEntity(bluedemontridententity);
        livingentity.level().playSound((Player) null, livingentity.getX(), livingentity.getY(), livingentity.getZ(), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "item.trident.throw"))), SoundSource.PLAYERS, 1.0F, 1.0F / ((new Random()).nextFloat() * 0.5F + 1.0F));
        return bluedemontridententity;
    }
}
