package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.procedures.*;
import com.pla.annoyingvillagers.util.CommonGoals;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
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
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import se.gory_moon.player_mobs.utils.NameManager;


public class ShadowHerobrineEntity extends Monster {
    private boolean wasKneeling = false;

    public ShadowHerobrineEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.SHADOW_HEROBRINE.get(), level);
    }

    public ShadowHerobrineEntity(EntityType<ShadowHerobrineEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.8F);
        this.xpReward = 60;
        this.setNoAi(false);
        this.setCustomName(Component.literal("§5Shadow Herobrine§r"));
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get()));
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        CommonGoals.registerGoalForHostileNpc(this);
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
        DarkHerobrineOnHurtProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.WITHER)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.WITHER_SKULL)) return false;
        if (damagesource.is(DamageTypes.DRAGON_BREATH)) return false;
        return super.hurt(damagesource, f);
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        if (this.level() instanceof ServerLevel serverLevel) {
            InfectedPlayerMobEntity corpse = new InfectedPlayerMobEntity(AnnoyingVillagersModEntities.INFECTED_PLAYER_MOB.get(), serverLevel);
            corpse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            String killedName = this.getPersistentData().getString("killed_name");
            corpse.getPersistentData().putString("possessed_by", "shadow_herobrine");
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

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        SpawnGroupData spawngroupdata1 = super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);

        Herobrine7OnEntityInitialSpawnProcedure.execute(serverlevelaccessor, this.getX(), this.getY(), this.getZ(), this);
        return spawngroupdata1;
    }

    public void awardKillScore(Entity entity, int i, DamageSource damagesource) {
        super.awardKillScore(entity, i, damagesource);
        HerobrineTransfromProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), entity, this);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.getPersistentData().contains("Shooting")) {
            tag.putInt("Shooting", this.getPersistentData().getInt("Shooting"));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Shooting")) {
            this.getPersistentData().putInt("Shooting", tag.getInt("Shooting"));
        }
    }

    public void baseTick() {
        super.baseTick();
    }

    public void playerTouch(Player player) {
        super.playerTouch(player);
        DarkHerobrineOnPlayerTouchProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
    }

    public static void init() {
        SpawnPlacements.register((EntityType) AnnoyingVillagersModEntities.SHADOW_HEROBRINE.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, (entitytype, serverlevelaccessor, mobspawntype, blockpos, random) -> {
            return serverlevelaccessor.getRawBrightness(blockpos, 0) <= 8;
        });
    }

    private static void shootChain(Entity shooter, BlockState block, float velocity, int length) {
        Level level = shooter.level();
        if (level.isClientSide) return;

        double eyeY = shooter.getEyeY();
        Vec3 look = shooter.getLookAngle().normalize();
        RandomSource rand = level.getRandom();

        for (int i = 0; i < length; i++) {
            BlockProjectileEntity proj = new BlockProjectileEntity(
                    level,
                    shooter instanceof LivingEntity ? (LivingEntity) shooter : null,
                    block
            );

            Vec3 forward = look.scale(i * 1.0);

            double sideX = (rand.nextDouble() - 0.5) * 2.0;
            double sideY = (rand.nextDouble() - 0.5) * 2.0;
            double sideZ = (rand.nextDouble() - 0.5) * 2.0;

            proj.setPos(
                    shooter.getX() + forward.x + sideX,
                    eyeY + forward.y + sideY,
                    shooter.getZ() + forward.z + sideZ
            );
            proj.setDeltaMovement(look.scale(velocity));

            level.addFreshEntity(proj);
        }
    }

    private static String currentEfAnimIdOrNull(LivingEntity self) {
        try {
            var patch = yesman.epicfight.world.capabilities.EpicFightCapabilities
                    .getEntityPatch(self, yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch.class);
            if (patch == null) return null;

            var player = patch.getAnimator().getPlayerFor((yesman.epicfight.api.animation.types.DynamicAnimation) null);
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

    private static boolean isKneelAnimId(String id) {
        if (id == null) return false;
        return id.contains("biped/living/landing") || id.endsWith("/landing") || id.contains("landing");
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            String animId = currentEfAnimIdOrNull(this);
            boolean isKneelingNow = isKneelAnimId(animId);

            if (isKneelingNow && !wasKneeling) {
                this.getPersistentData().putInt("Shooting", 15);
            }
            wasKneeling = isKneelingNow;

            int shooting = this.getPersistentData().getInt("Shooting");
            if (shooting > 0) {
                BlockState block = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().defaultBlockState();
                setDeltaMovement(Vec3.ZERO);
                shootChain(this, block, 2.5F, 5);
                this.getPersistentData().putInt("Shooting", shooting - 1);
            } else {
                if (this.getMainHandItem().getItem().equals(AnnoyingVillagersModItems.ELITE_OBSIDIAN_LONG.get())) {
                    this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                }
            }
        }
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35D);
        builder = builder.add(Attributes.MAX_HEALTH, 120.0D);
        builder = builder.add(Attributes.ARMOR, 25.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 4.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
        return builder;
    }
}
