package com.pla.annoyingvillagers.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.clazz.PlayerNpcTarget;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.procedures.PlayerNpcOnHurtProcedure;
import com.pla.annoyingvillagers.util.CommonGoals;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class PlayerNpcEntity extends PlayerMobEntity {
    private final SimpleContainer inventory = new SimpleContainer(27);
    private int gapCooldown;
    private int enderPearlCooldown;
    private PlayerNpcTarget target;
    private ItemStack mainWeaponItem = ItemStack.EMPTY;
    private boolean healing = false;

    public boolean isHealing() {
        return healing;
    }

    public void setHealing(boolean healing) {
        this.healing = healing;
    }

    public int getGapCooldown() {
        return gapCooldown;
    }

    public int getEnderPearlCooldown() {
        return enderPearlCooldown;
    }

    public void setGapCooldown() {
        this.gapCooldown = random.nextInt(100, 300);
    }

    public void setEnderPearlCooldown() {
        this.enderPearlCooldown = random.nextInt(100, 300);
    }

    public SimpleContainer getInventory() {
        return inventory;
    }

    public PlayerNpcEntity(PlayMessages.SpawnEntity spawnentity, Level level) {
        this(AnnoyingVillagersModEntities.PLAYER_NPC.get(), level);
    }

    public ItemStack getMainWeaponItem() {
        return mainWeaponItem;
    }

    public PlayerNpcEntity(EntityType<? extends PlayerNpcEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.6F);
        this.xpReward = 50;
        this.setNoAi(false);
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("Inventory", this.inventory.createTag());
        tag.putInt("GapCooldown", this.gapCooldown);
        tag.putInt("EnderPearlCooldown", this.enderPearlCooldown);
        if (this.target != null) {
            tag.putString("PlayerNpcTarget", this.target.name());
        }
        if (!this.mainWeaponItem.isEmpty()) {
            CompoundTag itemTag = new CompoundTag();
            this.mainWeaponItem.save(itemTag);
            tag.put("MainHandItem", itemTag);
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Inventory", Tag.TAG_COMPOUND)) {
            this.inventory.fromTag(tag.getList("Inventory", Tag.TAG_COMPOUND));
        }
        this.gapCooldown = tag.getInt("GapCooldown");
        this.enderPearlCooldown = tag.getInt("EnderPearlCooldown");
        if (tag.contains("PlayerNpcTarget", Tag.TAG_STRING)) {
            String name = tag.getString("PlayerNpcTarget");
            try {
                this.target = PlayerNpcTarget.valueOf(name);
            } catch (IllegalArgumentException e) {
                this.target = PlayerNpcTarget.MOSNTER_HUNTER;
            }
        }
        if (tag.contains("MainHandItem", Tag.TAG_COMPOUND)) {
            this.mainWeaponItem = ItemStack.of(tag.getCompound("MainHandItem"));
        } else {
            this.mainWeaponItem = ItemStack.EMPTY;
        }
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);

        for (int i = 0; i < this.inventory.getContainerSize(); i++) {
            ItemStack stack = this.inventory.getItem(i);
            if (!stack.isEmpty()) {
                this.spawnAtLocation(stack);
            }
        }
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private void hostileHunterPlayerMob() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerMobEntity.class, true));
        CommonGoals.attackAllMonstersGoals(this);
        CommonGoals.attackAllNpcGoals(this);
    }
    private void villagerHunterPlayerMob() {
        CommonGoals.runAwayFromHerobrineGoals(this, 20.0F);
        if (!(this.getTarget() instanceof PlayerNpcEntity)) {
            this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PlayerNpcEntity.class, 12.0F, 1.2D, 1.8D));
        }
        if (!(this.getTarget() instanceof Player)) {
            this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 12.0F, 1.2D, 1.8D));
        }
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Villager.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, JevEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        CommonGoals.attackAllVillagerArmyGoal(this);
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2D, false));
    }
    private void monsterHunterPlayerMob() {
        CommonGoals.attackAllMonstersGoals(this);
        CommonGoals.runAwayFromVillagerArmyGoals(this);
    }
    private void playerHunterPlayerMob() {
        CommonGoals.runAwayFromHerobrineGoals(this, 20.0F);
        CommonGoals.runAwayFromVillagerArmyGoals(this);
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerNpcEntity.class, true));
        CommonGoals.attackAllNpcGoals(this);
    }
    private void animalHunterPlayerMob() {
        CommonGoals.runAwayFromHerobrineGoals(this, 20.0F);
        CommonGoals.runAwayFromVillagerArmyGoals(this);
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Animal.class, true));
        if (!(this.getTarget() instanceof PlayerNpcEntity)) {
            this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PlayerNpcEntity.class, 12.0F, 1.2D,
                    1.8D));
        }
        if (!(this.getTarget() instanceof Player)) {
            this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 12.0F, 1.2D, 1.8D));
        }
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new OpenDoorGoal(this, true));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
        if (this.target != null) {
            switch (this.target) {
                case HOSTILE_HUNTER -> {
                    hostileHunterPlayerMob();
                }
                case VILLAGER_HUNTER -> {
                    villagerHunterPlayerMob();
                }
                case MOSNTER_HUNTER -> {
                    monsterHunterPlayerMob();
                }
                case PLAYER_HUNTER -> {
                    playerHunterPlayerMob();
                }
                case ANIMAL_HUNTER -> {
                    animalHunterPlayerMob();
                }
                default -> {
                }
            }
        }
    }

    public @NotNull MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public @NotNull SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.generic.hurt")));
    }

    public @NotNull SoundEvent getDeathSound() {
        return Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.generic.death")));
    }

    public boolean hurt(DamageSource damagesource, float f) {
        PlayerNpcOnHurtProcedure.execute(this, damagesource.getEntity());
        return super.hurt(damagesource, f);
    }

    public void die(@NotNull DamageSource damageSource) {
        super.die(damageSource);
        if (this.level() instanceof ServerLevel serverLevel) {
            if (this.getPersistentData().getBoolean("die_by_possess")) {
                this.remove(Entity.RemovalReason.KILLED);
            } else if (AnnoyingVillagersConfig.PHYSIC_MOD_COMPAT.get()) {
                PlayerMobDeadEntity corpse = new PlayerMobDeadEntity(AnnoyingVillagersModEntities.PLAYER_MOB_DEAD.get(), serverLevel);
                corpse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                corpse.setUsername(this.getUsername());
                corpse.setProfile(this.getProfile());
                corpse.setInvisible(true);
                corpse.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
                serverLevel.addFreshEntity(corpse);
                this.remove(Entity.RemovalReason.KILLED);
                new DelayedTask(3) {
                    @Override
                    public void run() {
                        try {
                            Objects.requireNonNull(corpse.getServer()).getCommands().getDispatcher().execute(
                                    "kill @s",
                                    corpse.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException ignored) {
                        }
                    }
                };
            }
        }
    }

    private boolean isInventoryFull() {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack s = inventory.getItem(i);
            if (s.isEmpty() || s.getCount() < s.getMaxStackSize()) {
                return false;
            }
        }
        return true;
    }

    private void pickupNearbyItems() {
        if (!isAlive() || isRemoved()) return;

        var box = getBoundingBox().inflate(1.5D);
        List<ItemEntity> items = level().getEntitiesOfClass(
                ItemEntity.class,
                box,
                e -> !e.isRemoved() && !e.hasPickUpDelay()
        );

        for (ItemEntity itemEntity : items) {
            tryPickup(itemEntity);
        }
    }

    private void tryPickup(ItemEntity itemEntity) {
        ItemStack remaining = itemEntity.getItem().copy();

        for (int i = 0; i < inventory.getContainerSize() && !remaining.isEmpty(); i++) {
            if (remaining.isEmpty()) break;
            ItemStack slotStack = this.inventory.getItem(i);

            if (slotStack.isEmpty()) {
                this.inventory.setItem(i, remaining);
                remaining = ItemStack.EMPTY;
                break;
            } else if (ItemStack.isSameItemSameTags(slotStack, remaining) &&
                    slotStack.getCount() < slotStack.getMaxStackSize()) {
                int transferable = Math.min(
                        remaining.getCount(),
                        slotStack.getMaxStackSize() - slotStack.getCount()
                );
                slotStack.grow(transferable);
                remaining.shrink(transferable);
            }
        }

        if (remaining.isEmpty()) {
            itemEntity.setDeltaMovement(
                    (this.getX() - itemEntity.getX()) * 0.25,
                    (this.getY() + 1.0 - itemEntity.getY()) * 0.25,
                    (this.getZ() - itemEntity.getZ()) * 0.25
            );
            itemEntity.setPickUpDelay(0);
            Entity entity = this;
            new DelayedTask(5) {
                @Override
                public void run() {
                    if (!entity.isAlive() || entity.isRemoved()) {
                        return;
                    } else {
                        entity.level();
                    }
                    itemEntity.discard();
                    entity.level().playSound(null, entity.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.HOSTILE, 0.2F, 1.0F);
                }
            };
        } else {
            itemEntity.setItem(remaining);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) return;

        if (gapCooldown > 0) gapCooldown--;
        if (enderPearlCooldown > 0) enderPearlCooldown--;

        if ((tickCount + getId()) % 20 != 0) {
            return;
        }

        if (isInventoryFull()) return;

        pickupNearbyItems();
    }

    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverLevelAccessor, @NotNull DifficultyInstance difficultyInstance, @NotNull MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        SpawnGroupData returnSpawnGroupData = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawngroupdata, compoundtag);

        ServerLevel serverLevel = serverLevelAccessor.getLevel();

        if ((mobSpawnType == MobSpawnType.CHUNK_GENERATION || mobSpawnType == MobSpawnType.NATURAL) && serverLevel.isDay() && Math.random() <= 0.8D) {
            BlockPos blockPos = this.getOnPos();
            int surfaceY = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockPos).getY();
            BlockPos spawnPos = new BlockPos(blockPos.getX(), surfaceY, blockPos.getZ());
            if (serverLevel.getFluidState(spawnPos).isEmpty()) {
                this.moveTo(spawnPos, this.getYRot(), this.getXRot());
            }
        }

        this.target = PlayerNpcTarget.values()[new Random().nextInt(PlayerNpcTarget.values().length)];
        this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.DIAMOND_SWORD));
        this.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.DIAMOND_SWORD));
        this.mainWeaponItem = this.getMainHandItem().copy();

//        List<String> commands = EquipmentDataLoader.getEquipCommands(0.85f, this);
//        for (String cmd : commands) {
//            try {
//                this.getServer().getCommands().getDispatcher().execute(
//                        cmd,
//                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4)
//                );
//            } catch (CommandSyntaxException e) {
//
//            }
//        }

        try {
            Objects.requireNonNull(this.getServer()).getCommands().getDispatcher().execute(
                    "tellraw @a {\"text\":\"" + this.getDisplayName().getString() + " has joined the game\",\"color\":\"yellow\"}",
                    this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
        } catch (CommandSyntaxException ignored) {
        }

        try {
            Objects.requireNonNull(this.getServer()).getCommands().getDispatcher().execute(
                    "data merge entity @s {CanPickUpLoot: 1b}",
                    this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
        } catch (CommandSyntaxException ignored) {
        }

        if (Math.random() <= 0.05D) {
            try {
                Objects.requireNonNull(this.getServer()).getCommands().getDispatcher().execute(
                        "team add player",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException ignored) {
            }
            try {
                Objects.requireNonNull(this.getServer()).getCommands().getDispatcher().execute(
                        "team join player @s",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException ignored) {
            }
        }

        if (Math.random() <= 0.15D) {
            this.getPersistentData().putDouble("npc_level", 3.0D);
        }

        return returnSpawnGroupData;
    }

    public void awardKillScore(@NotNull Entity entity, int i, @NotNull DamageSource damageSource) {
        super.awardKillScore(entity, i, damageSource);
    }

    @Override
    public void onEquipItem(@NotNull EquipmentSlot pSlot, @NotNull ItemStack pOldItem, @NotNull ItemStack pNewItem) {
        if (pSlot == EquipmentSlot.MAINHAND &&
                (pNewItem.getItem() instanceof SwordItem || pNewItem.getItem() instanceof AxeItem)) {
            this.mainWeaponItem = pNewItem.copy();
        }
        super.onEquipItem(pSlot, pOldItem, pNewItem);
    }

    public void baseTick() {
        super.baseTick();
    }

    public static boolean canSpawn(EntityType<PlayerNpcEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        ServerLevel serverLevel = level.getLevel();
        if (serverLevel.isNight()) {
            // Nerf Player NPC spawn at night
            return false;
        }
        return Monster.checkAnyLightMonsterSpawnRules(entityType, level, spawnType, position, random);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.45D);
        builder = builder.add(Attributes.MAX_HEALTH, 30.0D);
        builder = builder.add(Attributes.ARMOR, 0.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 24.0D);
        return builder;
    }
}