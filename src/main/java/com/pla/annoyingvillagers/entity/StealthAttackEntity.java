package com.pla.annoyingvillagers.entity;

import java.util.Random;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class StealthAttackEntity extends AbstractArrow implements ItemSupplier {

    public StealthAttackEntity(SpawnEntity spawnentity, Level level) {
        super((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level);
    }

    public StealthAttackEntity(EntityType<? extends StealthAttackEntity> entitytype, Level level) {
        super(entitytype, level);
    }

    public StealthAttackEntity(EntityType<? extends StealthAttackEntity> entitytype, double d0, double d1, double d2, Level level) {
        super(entitytype, d0, d1, d2, level);
    }

    public StealthAttackEntity(EntityType<? extends StealthAttackEntity> entitytype, LivingEntity livingentity, Level level) {
        super(entitytype, livingentity, level);
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return new ItemStack(Blocks.AIR);
    }

    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    protected void doPostHurtEffects(LivingEntity livingentity) {
        super.doPostHurtEffects(livingentity);
        livingentity.setArrowCount(livingentity.getArrowCount() - 1);
    }

    public void tick() {
        super.tick();
        if (this.inGround) {
            this.discard();
        }

    }

    public static StealthAttackEntity shoot(Level level, LivingEntity livingentity, Random random, float f, double d0, int i) {
        StealthAttackEntity yinshenentity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), livingentity, level);

        yinshenentity.shoot(livingentity.getViewVector(1.0F).x, livingentity.getViewVector(1.0F).y, livingentity.getViewVector(1.0F).z, f * 2.0F, 0.0F);
        yinshenentity.setSilent(true);
        yinshenentity.setCritArrow(false);
        yinshenentity.setBaseDamage(d0);
        yinshenentity.setKnockback(i);
        level.addFreshEntity(yinshenentity);
        level.playSound((Player) null, livingentity.getX(), livingentity.getY(), livingentity.getZ(), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.shoot")), SoundSource.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.5F + 1.0F) + f / 2.0F);
        return yinshenentity;
    }

    public static StealthAttackEntity shoot(LivingEntity livingentity, LivingEntity livingentity1) {
        StealthAttackEntity yinshenentity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), livingentity, livingentity.level());
        double d0 = livingentity1.getX() - livingentity.getX();
        double d1 = livingentity1.getY() + (double) livingentity1.getEyeHeight() - 1.1D;
        double d2 = livingentity1.getZ() - livingentity.getZ();

        yinshenentity.shoot(d0, d1 - yinshenentity.getY() + Math.hypot(d0, d2) * 0.20000000298023224D, d2, 2.0F, 12.0F);
        yinshenentity.setSilent(true);
        yinshenentity.setBaseDamage(18.0D);
        yinshenentity.setKnockback(7);
        yinshenentity.setCritArrow(false);
        livingentity.level().addFreshEntity(yinshenentity);
        livingentity.level().playSound((Player) null, livingentity.getX(), livingentity.getY(), livingentity.getZ(), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.shoot")), SoundSource.PLAYERS, 1.0F, 1.0F / ((new Random()).nextFloat() * 0.5F + 1.0F));
        return yinshenentity;
    }
}
