package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.spawnhandler.HerobrineMobData;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;


public class HerobrineChrisEntity extends HerobrineMob {
    public HerobrineChrisEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.HEROBRINE_CHRIS.get(), level);
    }

    public HerobrineChrisEntity(EntityType<HerobrineChrisEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(3.0F);
        this.xpReward = 50;
        this.setNoAi(false);
        this.setPersistenceRequired();
        this.setChatName(this.getDisplayName().getString());
        this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.OBSIDIAN_WEAPON.get()));
    }

    public boolean hurt(@NotNull DamageSource damagesource, float f) {
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.WITHER)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.WITHER_SKULL)) return false;
        if (damagesource.is(DamageTypes.DRAGON_BREATH)) return false;
        return super.hurt(damagesource, f);
    }

    public void die(@NotNull DamageSource damagesource) {
        super.die(damagesource);
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.getServer().getPlayerList().broadcastSystemMessage(Component.translatable("subtitles.herobrine_clone_die"), false);
            InfectedChrisEntity corpse = new InfectedChrisEntity(AnnoyingVillagersModEntities.INJECTED_CHRIS.get(), serverLevel);
            corpse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            corpse.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()),
                    MobSpawnType.MOB_SUMMONED, null, null);
            this.setInvisible(true);
            this.remove(RemovalReason.KILLED);
            serverLevel.addFreshEntity(corpse);
        }
    }

    public static boolean canSpawn(EntityType<HerobrineChrisEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        ServerLevel serverLevel = level.getLevel();
        int passesDay = (int) (serverLevel.getGameTime() / 24000);
        if (passesDay != 0 && passesDay % 3 != 0) {
            return false;
        }
        if (HerobrineMobData.get(serverLevel).isOccupied(serverLevel)) {
            return false;
        }
        if (!serverLevel.isNight()) {
            return false;
        }
        return Monster.checkMonsterSpawnRules(entityType, level, spawnType, position, random);
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.45D);
        builder = builder.add(Attributes.MAX_HEALTH, 40.0D);
        builder = builder.add(Attributes.ARMOR, 25.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 10.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
        return builder;
    }
}
