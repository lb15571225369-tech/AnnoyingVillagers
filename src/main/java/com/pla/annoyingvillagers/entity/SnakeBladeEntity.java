package com.pla.annoyingvillagers.entity;

import java.util.*;

import com.google.common.collect.Multimap;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.procedures.HerobrineWeaponEffectProcedure;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.util.SnakeBladeHit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;

public class SnakeBladeEntity extends Entity {

    private static final EntityDataAccessor<Optional<UUID>> CREATOR_ID = SynchedEntityData.defineId(SnakeBladeEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> FROM_ID = SynchedEntityData.defineId(SnakeBladeEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TARGET_COUNT = SynchedEntityData.defineId(SnakeBladeEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> CURRENT_TARGET_ID = SynchedEntityData.defineId(SnakeBladeEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> PROGRESS = SynchedEntityData.defineId(SnakeBladeEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(SnakeBladeEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> RETRACTING = SynchedEntityData.defineId(SnakeBladeEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_BLADE = SynchedEntityData.defineId(SnakeBladeEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ENCHANTED = SynchedEntityData.defineId(SnakeBladeEntity.class, EntityDataSerializers.BOOLEAN);

    private List<Entity> previouslyTouched = new ArrayList<>();
    private boolean hasChained = false;
    public float prevProgress = 0;
    public static final float MAX_EXTEND_TIME = 5F;
//    private boolean retractionHandled = false;

    public SnakeBladeEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    public SnakeBladeEntity(PlayMessages.SpawnEntity spawnEntity, Level world) {
        this(AnnoyingVillagersModEntities.SNAKE_BLADE.get(), world);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(CREATOR_ID, Optional.empty());
        this.entityData.define(FROM_ID, -1);
        this.entityData.define(TARGET_COUNT, 0);
        this.entityData.define(CURRENT_TARGET_ID, -1);
        this.entityData.define(PROGRESS, 0F);
        this.entityData.define(DAMAGE, 3F);
        this.entityData.define(RETRACTING, false);
        this.entityData.define(HAS_BLADE, true);
        this.entityData.define(ENCHANTED, false);
    }

    public void setEnchanted(boolean enchanted) {
        this.entityData.set(ENCHANTED, enchanted);
    }

    public boolean isEnchanted() {
        return this.entityData.get(ENCHANTED);
    }

    private float getBaseDamage() {
        return this.entityData.get(DAMAGE);
    }

    @Override
    public void tick() {
        HerobrineWeaponEffectProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
        float progress = this.getProgress();
        this.prevProgress = progress;
        super.tick();
        Entity creator = getCreatorEntity();
        Entity current = getToEntity();
        if(tickCount == 1){
            if(!this.level().isClientSide){
//                this.playSound(ModSounds.TIDAL_TENTACLE.get(),1.0F, 0.8F + this.random.nextFloat() * 0.4F);
            }
        }
        if(!this.isRetracting() && progress < MAX_EXTEND_TIME){
            this.setProgress(progress + 1);
        }
        if(this.isRetracting() && progress > 0F){
            this.setProgress(progress - 1);
        }
//        if(this.isRetracting() && progress == 0F && !retractionHandled){
//            retractionHandled = true;
        if(this.isRetracting() && progress == 0F) {
            Entity from = this.getFromEntity();
            if(from instanceof SnakeBladeEntity){
                SnakeBladeEntity snakeBladeFragment = (SnakeBladeEntity) from;
                snakeBladeFragment.setRetracting(true);
                updateLastFragment(snakeBladeFragment);
            } else {
                updateLastFragment(null);
                if (creator instanceof LivingEntity livingEntity) {
                    ItemStack held = livingEntity.getMainHandItem();
                    if (held.is(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get())) {
                        held.removeTagKey("SnakeAnimation");
                    }
                }
            }

            this.remove(RemovalReason.DISCARDED);
        }
        if (creator instanceof LivingEntity) {
            if (current != null) {
                Vec3 target = new Vec3(current.getX(), current.getY(0.4F), current.getZ());
                Vec3 lerp = target.subtract(this.position());
                this.setDeltaMovement(lerp.scale(0.5F));
                if(!this.level().isClientSide){
                    if(progress >= MAX_EXTEND_TIME){
                        if (this.tickCount % 2 == 0) {
                            Entity entity = getCreatorEntity();
                            if(entity instanceof LivingEntity) {
                                if (current != creator && current.hurt(this.level().damageSources().indirectMagic(this, (LivingEntity) entity), this.getBaseDamage())) {
//                                    AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG]: Snake blade hit owner: {}; victim: {}", entity, current);
                                    if (!this.level().isClientSide() && entity.getServer() != null) {
                                        try {
                                            this.getServer().getCommands().getDispatcher().execute(
                                                    "execute at @s run particle epicfight:hit_blunt ^ ^1.5 ^0.8 0.1 0.1 0.1 1 1",
                                                    this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                        } catch (CommandSyntaxException e) {

                                        }
                                    }

                                    if (!this.level().isClientSide()) {
                                        this.level().playSound(null, new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ()), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "obsidian_hit")), SoundSource.BLOCKS, 1.0F, (float) Mth.nextDouble(RandomSource.create(), 0.5D, 1.0D));
                                    } else {
                                        this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "obsidian_hit")), SoundSource.BLOCKS, 1.0F, (float) Mth.nextDouble(RandomSource.create(), 0.5D, 1.0D), false);
                                    }
                                    if (!current.level().isClientSide() && current.getServer() != null) {
                                        try {
                                            current.getServer().getCommands().getDispatcher().execute(
                                                    "indestructible @s play \"epicfight:biped/combat/hit_long\" 0 10",
                                                    current.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                        } catch (CommandSyntaxException e) {

                                        }
                                    }
                                    if (current instanceof LivingEntity livingEntity) {
                                        float strength = 3.0F;
                                        double dx = this.getX() - current.getX();
                                        double dz = this.getZ() - current.getZ();
                                        livingEntity.knockback(strength, dx, dz);
                                    }
                                    if (new Random().nextBoolean()) {
                                        if (!current.level().isClientSide() && current.getServer() != null) {
                                            try {
                                                current.getServer().getCommands().getDispatcher().execute(
                                                        "indestructible @s play \"epicfight:biped/combat/knockdown\" 0 10",
                                                        current.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                            } catch (CommandSyntaxException e) {

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Vec3 vector3d = this.getDeltaMovement();
        if(!this.level().isClientSide){
            if(!hasChained){
                if(this.getTargetsHit() > 5){
                    this.setRetracting(true);
                }else if(creator instanceof LivingEntity && this.getProgress() >= MAX_EXTEND_TIME) {
                    Entity closestValid = null;
                    for (Entity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(12.0D))) {
                        if (!entity.equals(creator) && !previouslyTouched.contains(entity) && isValidTarget((LivingEntity) creator, entity) && this.hasLineOfSight(entity)) {
                            if (closestValid == null || this.distanceTo(entity) < this.distanceTo(closestValid)) {
                                closestValid = entity;
                            }
                        }
                    }
                    if(closestValid != null){
                        createChain(closestValid);
                        hasChained = true;
                    }else{
                        this.setRetracting(true);
                    }
                }
            }
        }

        double d0 = this.getX() + vector3d.x;
        double d1 = this.getY() + vector3d.y;
        double d2 = this.getZ() + vector3d.z;
        this.setDeltaMovement(vector3d.scale(0.99F));
        this.setPos(d0, d1, d2);
    }

    private boolean isValidTarget(LivingEntity creator, Entity entity) {
        if(!creator.isAlliedTo(entity) && !entity.isAlliedTo(creator) && entity instanceof Mob){
            return true;
        }
        return creator.getLastHurtMob() != null && creator.getLastHurtMob().getUUID().equals(entity.getUUID()) || creator.getLastHurtByMob() != null && creator.getLastHurtByMob().getUUID().equals(entity.getUUID());
    }

    private double getDamageForItem(ItemStack itemStack) {
        Multimap<Attribute, AttributeModifier> map = itemStack.getAttributeModifiers(EquipmentSlot.MAINHAND);
        if (!map.isEmpty()) {
            double d = 0;
            for (AttributeModifier mod : map.get(Attributes.ATTACK_DAMAGE)) {
                d += mod.getAmount();
            }
            return d;
        }
        return 0;
    }

    private boolean hasLineOfSight(Entity entity) {
        if (entity.level() != this.level()) {
            return false;
        } else {
            Vec3 vec3 = new Vec3(this.getX(), this.getEyeY(), this.getZ());
            Vec3 vec31 = new Vec3(entity.getX(), entity.getEyeY(), entity.getZ());
            if (vec31.distanceTo(vec3) > 128.0D) {
                return false;
            } else {
                return this.level().clip(new ClipContext(vec3, vec31, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).getType() == HitResult.Type.MISS;
            }
        }
    }

    private void updateLastFragment(SnakeBladeEntity lastTendon){
        Entity creator = getCreatorEntity();
        if(creator == null){
            creator = level().getPlayerByUUID(this.getCreatorEntityUUID());
        }
        if(creator instanceof LivingEntity){
            SnakeBladeHit.setLastFragment((LivingEntity)creator, lastTendon);
        }
    }

    private void createChain(Entity closestValid) {
        this.entityData.set(HAS_BLADE, false);
        SnakeBladeEntity child = AnnoyingVillagersModEntities.SNAKE_BLADE.get().create(this.level());
        if (this.isEnchanted()) {
            child.setEnchanted(true);
        }
        child.previouslyTouched = new ArrayList<>(previouslyTouched);
        child.previouslyTouched.add(closestValid);
        child.setCreatorEntityUUID(this.getCreatorEntityUUID());
        child.setFromEntityID(this.getId());
        child.setToEntityID(closestValid.getId());
        child.setPos(closestValid.getX(), closestValid.getY(0.4F), closestValid.getZ());
        child.setTargetsHit(this.getTargetsHit() + 1);
        updateLastFragment(child);
        this.level().addFreshEntity(child);
    }

    public UUID getCreatorEntityUUID() {
        return this.entityData.get(CREATOR_ID).orElse(null);
    }

    public void setCreatorEntityUUID(UUID id) {
        this.entityData.set(CREATOR_ID, Optional.ofNullable(id));
    }

    public Entity getCreatorEntity() {
        UUID uuid = getCreatorEntityUUID();
        if(uuid != null && !this.level().isClientSide){
            return ((ServerLevel) level()).getEntity(uuid);
        }
        return null;
    }

    public int getFromEntityID() {
        return this.entityData.get(FROM_ID);
    }

    public void setFromEntityID(int id) {
        this.entityData.set(FROM_ID, id);
    }

    public Entity getFromEntity() {
        return getFromEntityID() == -1 ? null : this.level().getEntity(getFromEntityID());
    }

    public int getToEntityID() {
        return this.entityData.get(CURRENT_TARGET_ID);
    }

    public void setToEntityID(int id) {
        this.entityData.set(CURRENT_TARGET_ID, id);
    }

    public Entity getToEntity() {
        return getToEntityID() == -1 ? null : this.level().getEntity(getToEntityID());
    }

    public int getTargetsHit() {
        return this.entityData.get(TARGET_COUNT);
    }

    public void setTargetsHit(int i) {
        this.entityData.set(TARGET_COUNT, i);
    }

    public float getProgress() {
        return this.entityData.get(PROGRESS);
    }

    public void setProgress(float progress) {
        this.entityData.set(PROGRESS, progress);
    }

    public boolean isRetracting() {
        return this.entityData.get(RETRACTING);
    }

    public void setRetracting(boolean retract) {
        this.entityData.set(RETRACTING, retract);
    }


    public boolean hasBlade() {
        return this.entityData.get(HAS_BLADE);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
//        tag.putBoolean("RetractionHandled", retractionHandled);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
//        retractionHandled = tag.getBoolean("RetractionHandled");
    }

    public boolean isCreator(Entity mob) {
        return this.getCreatorEntityUUID() != null && mob.getUUID().equals(this.getCreatorEntityUUID());
    }
}
