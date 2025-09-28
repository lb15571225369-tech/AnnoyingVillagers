package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.procedures.*;
import com.pla.annoyingvillagers.util.CommonGoals;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.util.HerobrineMob;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import se.gory_moon.player_mobs.utils.NameManager;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;


public class Herobrine7Entity extends HerobrineMob {
    private boolean wasAiming = false;
    public Herobrine7Entity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.HEROBRINE_7.get(), level);
    }

    public Herobrine7Entity(EntityType<Herobrine7Entity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.0F);
        this.xpReward = 50;
        this.setNoAi(false);
        this.setCustomName(Component.literal("§5Herobrine 7§r"));
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get()));
        this.setChatName("§5Herobrine 7§r");
    }

    public Herobrine7Entity(EntityType<Herobrine7Entity> entitytype, Level level, boolean renderPortal) {
        this(entitytype, level);
        this.setRenderPortal(renderPortal);;
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    public boolean hurt(DamageSource damagesource, float f) {
        Herobrine7OnHurtProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this, damagesource.getEntity());
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.WITHER)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.WITHER_SKULL)) return false;
        if (damagesource.is(DamageTypes.DRAGON_BREATH)) return false;
        return super.hurt(damagesource, f);
    }

    private static String currentEfAnimIdOrNull(LivingEntity self) {
        try {
            var patch = EpicFightCapabilities
                    .getEntityPatch(self, LivingEntityPatch.class);
            if (patch == null) return null;

            var player = patch.getAnimator().getPlayerFor((DynamicAnimation) null);
            if (player == null) return null;

            var anim = player.getAnimation();
            if (anim == null) return null;
            try {
                var m = anim.getClass().getMethod("getLocation");
                var rl = (net.minecraft.resources.ResourceLocation) m.invoke(anim);
                return rl != null ? rl.getPath().toLowerCase(java.util.Locale.ROOT) : null;
            } catch (Exception ignored) {
                return anim.toString().toLowerCase(java.util.Locale.ROOT);
            }
        } catch (Throwable t) {
            return null;
        }
    }

    private static boolean isAiming(String id) {
        if (id == null) return false;
        return id.contains("biped/combat/fist_dash") || id.endsWith("/fist_dash") || id.contains("fist_dash");
    }

    public void shootDarkObsAtTarget(double speed) {
        if (this.level().isClientSide) return;

        Vec3 to;
        LivingEntity target = this.getTarget();
        if (target != null && target.isAlive()) {
            to = target.getEyePosition(1.0F);
        } else {
            to = this.getEyePosition().add(this.getLookAngle().scale(16.0));
        }

        BlockState block = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().defaultBlockState();
        BlockProjectileEntity throwingObsidian = new BlockProjectileEntity(
                this.level(),
                this,
                block
        );
        this.level().addFreshEntity(throwingObsidian);
        Vec3 dir = to.subtract(throwingObsidian.position());
        if (dir.lengthSqr() < 1.0e-6) dir = this.getLookAngle();
        Vec3 vel = dir.normalize().scale(speed);
        throwingObsidian.setDeltaMovement(vel);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            String animId = currentEfAnimIdOrNull(this);
            boolean aimingNow = isAiming(animId);
            if (aimingNow && !wasAiming) {
                Herobrine7Entity herobrine = this;
                new DelayedTask(10) {
                    @Override
                    public void run() {
                        if (herobrine.isAlive()) {
                            herobrine.shootDarkObsAtTarget(2.0F);
                        }
                    }
                };
            }
            wasAiming = aimingNow;
        }
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        if (this.level() instanceof ServerLevel serverLevel) {
            InfectedPlayerMobEntity corpse = new InfectedPlayerMobEntity(AnnoyingVillagersModEntities.INFECTED_PLAYER_MOB.get(), serverLevel);
            corpse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            String killedName = this.getPersistentData().getString("killed_name");
            corpse.getPersistentData().putString("possessed_by", "herobrine_7");
            if (killedName.isEmpty()) {
                killedName = String.valueOf(NameManager.INSTANCE.getRandomName());
            }
            corpse.setUsername(killedName);
            corpse.setCustomName(Component.literal(killedName));
            corpse.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()),
                    MobSpawnType.MOB_SUMMONED, null, null);
            this.setInvisible(true);
            this.remove(RemovalReason.KILLED);
            serverLevel.addFreshEntity(corpse);
        }
    }

    public void baseTick() {
        super.baseTick();
    }

    public void playerTouch(Player player) {
        super.playerTouch(player);
        Herobrine7OnPlayerTouchProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
    }

    public static void init() {
        SpawnPlacements.register((EntityType) AnnoyingVillagersModEntities.HEROBRINE_7.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, (entitytype, serverlevelaccessor, mobspawntype, blockpos, random) -> {
            return serverlevelaccessor.getRawBrightness(blockpos, 0) <= 8;
        });
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35D);
        builder = builder.add(Attributes.MAX_HEALTH, 100.0D);
        builder = builder.add(Attributes.ARMOR, 10.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 7.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 256.0D);
        return builder;
    }
}
