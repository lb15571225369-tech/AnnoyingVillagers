package com.pla.annoyingvillagers.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.procedures.BlueDemonTridentParticleOnEntityUpdate;
import com.pla.annoyingvillagers.procedures.BlueDemonTridentParticleWhenEntityDiesProcedure;

public class BlueDemonTridentParticleEntity extends PathfinderMob {

    public BlueDemonTridentParticleEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.BD_TRIDENT.get(), level);
    }

    public BlueDemonTridentParticleEntity(EntityType<BlueDemonTridentParticleEntity> entitytype, Level level) {
        super(entitytype, level);
        this.maxUpStep = 0.6F;
        this.xpReward = 0;
        this.setNoAi(true);
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.TRIDENT));
    }

    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.generic.death"));
    }

    public boolean hurt(DamageSource damagesource, float f) {
        return damagesource.getDirectEntity() instanceof AbstractArrow ? false : (damagesource.getDirectEntity() instanceof Player ? false : (!(damagesource.getDirectEntity() instanceof ThrownPotion) && !(damagesource.getDirectEntity() instanceof AreaEffectCloud) ? (damagesource == DamageSource.FALL ? false : (damagesource == DamageSource.CACTUS ? false : (damagesource == DamageSource.DROWN ? false : (damagesource == DamageSource.LIGHTNING_BOLT ? false : (damagesource.isExplosion() ? false : (damagesource.getMsgId().equals("trident") ? false : (damagesource == DamageSource.ANVIL ? false : (damagesource == DamageSource.DRAGON_BREATH ? false : (damagesource == DamageSource.WITHER ? false : (damagesource.getMsgId().equals("witherSkull") ? false : super.hurt(damagesource, f))))))))))) : false));
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        BlueDemonTridentParticleWhenEntityDiesProcedure.execute(this.level, this.getX(), this.getY(), this.getZ());
    }

    public void baseTick() {
        super.baseTick();
        try {
            BlueDemonTridentParticleOnEntityUpdate.execute(this.level, this.getX(), this.getY(), this.getZ(), this);
        } catch (CommandSyntaxException e) {
            
        }
    }

    public boolean isPushable() {
        return false;
    }

    protected void doPush(Entity entity) {}

    protected void pushEntities() {}

    public static void init() {}

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3D);
        builder = builder.add(Attributes.MAX_HEALTH, 10.0D);
        builder = builder.add(Attributes.ARMOR, 0.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 3.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16.0D);
        return builder;
    }
}
