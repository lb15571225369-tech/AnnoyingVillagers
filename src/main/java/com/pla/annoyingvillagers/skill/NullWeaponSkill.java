package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.NullWeapon;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.item.DemoniacVoltageReaverItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;
import yesman.epicfight.world.entity.eventlistener.SkillCastEvent;

import java.util.*;

public class NullWeaponSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("08b6bf0d-2fbe-4b7a-87da-ad4c4ebb9597");
    private static final String NBT_SPENT_STACKS = "AV_NullWeaponSpentStacks";

    public NullWeaponSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        if (!skillContainer.isActivated()) {
            skillContainer.getExecutor().playAnimationSynchronized(AVAnimations.CASTING_ONE_HAND_TOP, 0.0F);
            Player player = skillContainer.getExecutor().getOriginal();

            int stack = player.getPersistentData().getInt(NBT_SPENT_STACKS);
            player.getPersistentData().remove(NBT_SPENT_STACKS);

            if (player.level() instanceof ServerLevel serverLevel) {
                List<String> weaponKeys = List.of(
                        "NullAxeUUID",
                        "NullPickaxeUUID",
                        "NullShovelUUID",
                        "NullHoeUUID",
                        "NullSwordUUID"
                );
                List<String> shuffledKeys = new ArrayList<>(weaponKeys);
                Collections.shuffle(shuffledKeys, new Random());
                AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG] stack is {}", stack);

                for (int i = 0; i < stack; i++) {
                    String key = shuffledKeys.get(i);

                    if (player.getPersistentData().hasUUID(key)) {
                        UUID uuid = player.getPersistentData().getUUID(key);
                        Entity entity = serverLevel.getEntity(uuid);

                        if (entity instanceof NullWeapon nullWeapon) {
                            nullWeapon.setReleased(true);
                        }
                    }
                }
            }
            super.executeOnServer(skillContainer, friendlyByteBuf);
            skillContainer.activate();
        }
    }

    @Override
    public boolean resourcePredicate(PlayerPatch<?> playerPatch, SkillCastEvent event) {
        if (!(playerPatch instanceof ServerPlayerPatch serverPatch)) {
            return true;
        }

        SkillContainer container = serverPatch.getSkill(AVSkills.NULL_WEAPON);
        if (container == null || container.getSkill() != this) {
            return false;
        }

        Player player = serverPatch.getOriginal();
        if (player.isCreative()) {
            return true;
        }

        int available = container.getStack();
        if (available <= 0) {
            return false;
        }
        player.getPersistentData().putInt(NBT_SPENT_STACKS, available);

        Skill.setSkillStackSynchronize(container, 0);
        Skill.setSkillConsumptionSynchronize(container, 0.0F);
        return true;
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecutor().getEventListener().addEventListener(PlayerEventListener.EventType.TAKE_DAMAGE_EVENT_ATTACK, EVENT_UUID, (pre) -> {
            Player player = pre.getPlayerPatch().getOriginal();

            if (player.level() instanceof ServerLevel serverLevel) {
                CompoundTag data = player.getPersistentData();
                if (new Random().nextFloat() > 0.3F) return;
                boolean parried = false;
                NullWeapon nullWeapon = null;

                if (data.contains("NullSwordUUID") && Math.random() <= 0.5D) {
                    Entity entity = serverLevel.getEntity(data.getUUID("NullSwordUUID"));
                    if (entity instanceof NullSwordEntity nullSwordEntity) {
                        parried = true;
                        nullSwordEntity.moveTo(player.getX(), player.getY(), player.getZ());
                        nullWeapon = nullSwordEntity;
                    }
                }
                if (data.contains("NullAxeUUID") && Math.random() <= 0.5D && !parried) {
                    Entity entity = serverLevel.getEntity(data.getUUID("NullAxeUUID"));
                    if (entity instanceof NullAxeEntity nullAxeEntity) {
                        parried = true;
                        nullAxeEntity.moveTo(player.getX(), player.getY(), player.getZ());
                        nullWeapon = nullAxeEntity;
                    }
                }
                if (data.contains("NullPickaxeUUID") && Math.random() <= 0.5D && !parried) {
                    Entity entity = serverLevel.getEntity(data.getUUID("NullPickaxeUUID"));
                    parried = true;
                    if (entity instanceof NullPickaxeEntity nullPickaxeEntity) {
                        nullPickaxeEntity.moveTo(player.getX(), player.getY(), player.getZ());
                        nullWeapon = nullPickaxeEntity;
                    }
                }
                if (data.contains("NullHoeUUID") && Math.random() <= 0.5D && !parried) {
                    Entity entity = serverLevel.getEntity(data.getUUID("NullHoeUUID"));
                    if (entity instanceof NullHoeEntity nullHoeEntity) {
                        parried = true;
                        nullHoeEntity.moveTo(player.getX(), player.getY(), player.getZ());
                        nullWeapon = nullHoeEntity;
                    }
                }
                if (data.contains("NullShovelUUID") && Math.random() <= 0.5D && !parried) {
                    Entity entity = serverLevel.getEntity(data.getUUID("NullShovelUUID"));
                    if (entity instanceof NullShovelEntity nullShovelEntity) {
                        nullShovelEntity.moveTo(player.getX(), player.getY(), player.getZ());
                        nullWeapon = nullShovelEntity;
                    }
                }

                if (parried && nullWeapon != null) {
                    pre.setCanceled(true);
                    pre.setResult(AttackResult.ResultType.BLOCKED);
                    pre.getPlayerPatch().playSound(EpicFightSounds.CLASH.get(), -0.05F, 0.1F);
                    EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument(serverLevel, HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, player, pre.getDamageSource().getEntity());
                }
            }
        });
        container.getExecutor().getEventListener().addEventListener(
                PlayerEventListener.EventType.SKILL_CAST_EVENT, EVENT_UUID, (event) -> {
                    Player player = container.getExecutor().getOriginal();
                    Skill skill = event.getSkillContainer().getSkill();

                    if (skill.getCategory() == SkillCategories.GUARD){
                        List<String> weaponKeys = List.of(
                                "NullAxeUUID",
                                "NullPickaxeUUID",
                                "NullShovelUUID",
                                "NullHoeUUID",
                                "NullSwordUUID"
                        );

                        for (String key : weaponKeys) {
                            if (player.getPersistentData().hasUUID(key) && player.level() instanceof ServerLevel serverLevel) {
                                UUID uuid = player.getPersistentData().getUUID(key);
                                Entity entity = serverLevel.getEntity(uuid);

                                if (entity instanceof NullWeapon nullWeapon
                                        && !nullWeapon.isReleased()) {
                                    nullWeapon.setSpinning(true);
                                }
                            }
                        }
                    }
                });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.TAKE_DAMAGE_EVENT_ATTACK, EVENT_UUID);
    }

    @Override
    public void cancelOnServer(SkillContainer container, FriendlyByteBuf args) {
        container.deactivate();
        Player player = container.getExecutor().getOriginal();
        List<String> weaponKeys = List.of(
                "NullAxeUUID",
                "NullPickaxeUUID",
                "NullShovelUUID",
                "NullHoeUUID",
                "NullSwordUUID"
        );

        for (String key : weaponKeys) {
            if (player.getPersistentData().hasUUID(key) && player.level() instanceof ServerLevel serverLevel) {
                UUID uuid = player.getPersistentData().getUUID(key);
                Entity entity = serverLevel.getEntity(uuid);

                if (entity instanceof NullWeapon nullWeapon) {
                    nullWeapon.setReleased(false);
                }
            }
        }
        super.cancelOnServer(container, args);
    }

    public void executeOnClient(SkillContainer container, FriendlyByteBuf args) {
        super.executeOnClient(container, args);
        container.activate();
    }

    public void cancelOnClient(SkillContainer container, FriendlyByteBuf args) {
        super.cancelOnClient(container, args);
        container.deactivate();
    }

    private static void removeTrackedEntityIfWrongType(ServerLevel serverLevel, CompoundTag persistentData, String uuidKey, Class<? extends Entity> expectedClass) {
        if (!persistentData.hasUUID(uuidKey)) {
            return;
        }

        Entity trackedEntity = serverLevel.getEntity(persistentData.getUUID(uuidKey));
        if (trackedEntity != null && (!expectedClass.isInstance(trackedEntity) || !trackedEntity.isAlive())) {
            persistentData.remove(uuidKey);
        }
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        if (container.getExecutor().getValidItemInHand(InteractionHand.MAIN_HAND) != null) {
            LivingEntityPatch<?> livingEntityPatch = container.getExecutor();
            byte b0 = 3;
            float f = 1.0F / (float) (b0 - 1);
            float f1 = 0.0F;
            OpenMatrix4f openmatrix4f;
            int i;
            int j;
            OpenMatrix4f openmatrix4f1;
            for (j = 0; j < b0; ++j) {
                openmatrix4f = livingEntityPatch.getArmature().getBoundTransformFor(livingEntityPatch.getAnimator().getPose(f1), Armatures.BIPED.get().toolL);
                openmatrix4f.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F)), new Vec3f(0.0F, 1.0F, 0.0F)), openmatrix4f, openmatrix4f);

                for (i = 0; i < 1; ++i) {
                    livingEntityPatch.getOriginal().level().addParticle(AnnoyingVillagersModParticleTypes.NULL.get(), (double) openmatrix4f.m30 + livingEntityPatch.getOriginal().getX(), (double) openmatrix4f.m31 + ((Player) livingEntityPatch.getOriginal()).getY(), (double) openmatrix4f.m32 + ((Player) livingEntityPatch.getOriginal()).getZ(), ((new Random()).nextFloat() - 0.5F) * 0.15F, ((new Random()).nextFloat() - 0.5F) * 0.15F, ((new Random()).nextFloat() - 0.5F) * 0.15F);
                }

                for (i = 0; i < 1; ++i) {
                    livingEntityPatch.getOriginal().level().addParticle(AnnoyingVillagersModParticleTypes.NULL.get(), (double) openmatrix4f.m30 + livingEntityPatch.getOriginal().getX(), (double) openmatrix4f.m31 + ((Player) livingEntityPatch.getOriginal()).getY(), (double) openmatrix4f.m32 + ((Player) livingEntityPatch.getOriginal()).getZ(), 0.0D, 0.0D, 0.0D);
                }
                f1 += f;
            }

            f1 = 0.0F;

            for (i = 0; i < b0; ++i) {
                openmatrix4f1 = livingEntityPatch.getArmature().getBoundTransformFor(livingEntityPatch.getAnimator().getPose(f1), Armatures.BIPED.get().toolR);
                openmatrix4f1.translate(new Vec3f(0.0F, 0.0F, 1.8F));
                OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F)), new Vec3f(0.0F, 1.0F, 0.0F)), openmatrix4f1, openmatrix4f1);
                openmatrix4f1.translate(new Vec3f(0.0F, 0.0F, -((new Random()).nextFloat() * 4.0F)));
                livingEntityPatch.getOriginal().level().addParticle(AnnoyingVillagersModParticleTypes.NULL.get(), (double) openmatrix4f1.m30 + livingEntityPatch.getOriginal().getX(), (double) openmatrix4f1.m31 + ((Player) livingEntityPatch.getOriginal()).getY(), (double) openmatrix4f1.m32 + ((Player) livingEntityPatch.getOriginal()).getZ(), ((new Random()).nextFloat() - 0.5F) * 0.15F, ((new Random()).nextFloat() - 0.5F) * 0.15F, ((new Random()).nextFloat() - 0.5F) * 0.15F);
                livingEntityPatch.getOriginal().level().addParticle(AnnoyingVillagersModParticleTypes.NULL.get(), (double) openmatrix4f1.m30 + livingEntityPatch.getOriginal().getX(), (double) openmatrix4f1.m31 + ((Player) livingEntityPatch.getOriginal()).getY(), (double) openmatrix4f1.m32 + ((Player) livingEntityPatch.getOriginal()).getZ(), 0.0D, 0.0D, 0.0D);
                f1 += f;
            }

            for (i = 0; i < 14; ++i) {
                livingEntityPatch.getOriginal().level().addParticle(AnnoyingVillagersModParticleTypes.NULL.get(), livingEntityPatch.getOriginal().getX(), livingEntityPatch.getOriginal().getY() + 0.029999999329447746D, livingEntityPatch.getOriginal().getZ(), ((new Random()).nextFloat() - 0.5F) * 0.65F, ((new Random()).nextFloat() - 0.5F) * 0.05F, ((new Random()).nextFloat() - 0.5F) * 0.65F);
            }

            f = 1.0F;
            f1 = 0.0F;

            for (i = 0; i < b0; ++i) {
                openmatrix4f1 = livingEntityPatch.getArmature().getBoundTransformFor(livingEntityPatch.getAnimator().getPose(f1), Armatures.BIPED.get().head);
                openmatrix4f1.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F)), new Vec3f(0.0F, 1.0F, 0.0F)), openmatrix4f1, openmatrix4f1);
                livingEntityPatch.getOriginal().level().addParticle(AnnoyingVillagersModParticleTypes.NULL.get(), (double) openmatrix4f1.m30 + livingEntityPatch.getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), (double) openmatrix4f1.m31 + livingEntityPatch.getOriginal().getY() + (double) (((new Random()).nextFloat() + 0.1F) * 0.55F), (double) openmatrix4f1.m32 + livingEntityPatch.getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), ((new Random()).nextFloat() - 0.5F) * 0.15F, ((new Random()).nextFloat() - 1.0F) * 0.55F, ((new Random()).nextFloat() - 0.5F) * 0.15F);
                f1 += f;
            }

            f1 = 0.0F;

            for (i = 0; i < b0; ++i) {
                openmatrix4f1 = livingEntityPatch.getArmature().getBoundTransformFor(livingEntityPatch.getAnimator().getPose(f1), Armatures.BIPED.get().chest);
                openmatrix4f1.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F)), new Vec3f(0.0F, 1.0F, 0.0F)), openmatrix4f1, openmatrix4f1);
                livingEntityPatch.getOriginal().level().addParticle(AnnoyingVillagersModParticleTypes.NULL.get(), (double) openmatrix4f1.m30 + livingEntityPatch.getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), (double) openmatrix4f1.m31 + livingEntityPatch.getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), (double) openmatrix4f1.m32 + livingEntityPatch.getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), ((new Random()).nextFloat() - 0.5F) * 0.15F, ((new Random()).nextFloat() - 1.0F) * 0.55F, ((new Random()).nextFloat() - 0.5F) * 0.15F);
                f1 += f;
            }

            f1 = 0.0F;

            for (i = 0; i < b0; ++i) {
                openmatrix4f1 = livingEntityPatch.getArmature().getBoundTransformFor(livingEntityPatch.getAnimator().getPose(f1), Armatures.BIPED.get().armL);
                openmatrix4f1.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F)), new Vec3f(0.0F, 1.0F, 0.0F)), openmatrix4f1, openmatrix4f1);
                livingEntityPatch.getOriginal().level().addParticle(AnnoyingVillagersModParticleTypes.NULL.get(), (double) openmatrix4f1.m30 + livingEntityPatch.getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), (double) openmatrix4f1.m31 + livingEntityPatch.getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), (double) openmatrix4f1.m32 + livingEntityPatch.getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), ((new Random()).nextFloat() - 0.5F) * 0.15F, ((new Random()).nextFloat() - 1.0F) * 0.55F, ((new Random()).nextFloat() - 0.5F) * 0.15F);
                f1 += f;
            }

            f1 = 0.0F;

            for (i = 0; i < b0; ++i) {
                openmatrix4f1 = livingEntityPatch.getArmature().getBoundTransformFor(livingEntityPatch.getAnimator().getPose(f1), Armatures.BIPED.get().armR);
                openmatrix4f1.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F)), new Vec3f(0.0F, 1.0F, 0.0F)), openmatrix4f1, openmatrix4f1);
                livingEntityPatch.getOriginal().level().addParticle(AnnoyingVillagersModParticleTypes.NULL.get(), (double) openmatrix4f1.m30 + livingEntityPatch.getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), (double) openmatrix4f1.m31 + livingEntityPatch.getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), (double) openmatrix4f1.m32 + livingEntityPatch.getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), ((new Random()).nextFloat() - 0.5F) * 0.15F, ((new Random()).nextFloat() - 1.0F) * 0.55F, ((new Random()).nextFloat() - 0.5F) * 0.15F);
                f1 += f;
            }

            f1 = 0.0F;

            for (i = 0; i < b0; ++i) {
                openmatrix4f1 = livingEntityPatch.getArmature().getBoundTransformFor(livingEntityPatch.getAnimator().getPose(f1), Armatures.BIPED.get().torso);
                openmatrix4f1.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F)), new Vec3f(0.0F, 1.0F, 0.0F)), openmatrix4f1, openmatrix4f1);
                livingEntityPatch.getOriginal().level().addParticle(AnnoyingVillagersModParticleTypes.NULL.get(), (double) openmatrix4f1.m30 + livingEntityPatch.getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), (double) openmatrix4f1.m31 + livingEntityPatch.getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), (double) openmatrix4f1.m32 + livingEntityPatch.getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), ((new Random()).nextFloat() - 0.5F) * 0.15F, ((new Random()).nextFloat() - 1.0F) * 0.55F, ((new Random()).nextFloat() - 0.5F) * 0.15F);
                f1 += f;
            }

            f1 = 0.0F;

            for (i = 0; i < b0; ++i) {
                openmatrix4f1 = livingEntityPatch.getArmature().getBoundTransformFor(livingEntityPatch.getAnimator().getPose(f1), Armatures.BIPED.get().thighL);
                openmatrix4f1.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F)), new Vec3f(0.0F, 1.0F, 0.0F)), openmatrix4f1, openmatrix4f1);
                livingEntityPatch.getOriginal().level().addParticle(AnnoyingVillagersModParticleTypes.NULL.get(), (double) openmatrix4f1.m30 + livingEntityPatch.getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), (double) openmatrix4f1.m31 + livingEntityPatch.getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), (double) openmatrix4f1.m32 + livingEntityPatch.getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), ((new Random()).nextFloat() - 0.5F) * 0.15F, ((new Random()).nextFloat() - 1.0F) * 0.55F, ((new Random()).nextFloat() - 0.5F) * 0.15F);
                f1 += f;
            }

            f1 = 0.0F;

            for (i = 0; i < b0; ++i) {
                openmatrix4f1 = livingEntityPatch.getArmature().getBoundTransformFor(livingEntityPatch.getAnimator().getPose(f1), Armatures.BIPED.get().thighR);
                openmatrix4f1.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F)), new Vec3f(0.0F, 1.0F, 0.0F)), openmatrix4f1, openmatrix4f1);
                livingEntityPatch.getOriginal().level().addParticle(AnnoyingVillagersModParticleTypes.NULL.get(), (double) openmatrix4f1.m30 + livingEntityPatch.getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), (double) openmatrix4f1.m31 + livingEntityPatch.getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), (double) openmatrix4f1.m32 + livingEntityPatch.getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), ((new Random()).nextFloat() - 0.5F) * 0.15F, ((new Random()).nextFloat() - 1.0F) * 0.55F, ((new Random()).nextFloat() - 0.5F) * 0.15F);
                f1 += f;
            }

            f1 = 0.0F;

            for (i = 0; i < b0; ++i) {
                openmatrix4f1 = livingEntityPatch.getArmature().getBoundTransformFor(livingEntityPatch.getAnimator().getPose(f1), Armatures.BIPED.get().legL);
                openmatrix4f1.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F)), new Vec3f(0.0F, 1.0F, 0.0F)), openmatrix4f1, openmatrix4f1);
                livingEntityPatch.getOriginal().level().addParticle(AnnoyingVillagersModParticleTypes.NULL.get(), (double) openmatrix4f1.m30 + livingEntityPatch.getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), (double) openmatrix4f1.m31 + livingEntityPatch.getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), (double) openmatrix4f1.m32 + livingEntityPatch.getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), ((new Random()).nextFloat() - 0.5F) * 0.15F, ((new Random()).nextFloat() - 1.0F) * 0.55F, ((new Random()).nextFloat() - 0.5F) * 0.15F);
                f1 += f;
            }

            f1 = 0.0F;

            for (i = 0; i < b0; ++i) {
                openmatrix4f1 = livingEntityPatch.getArmature().getBoundTransformFor(livingEntityPatch.getAnimator().getPose(f1), Armatures.BIPED.get().legR);
                openmatrix4f1.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F)), new Vec3f(0.0F, 1.0F, 0.0F)), openmatrix4f1, openmatrix4f1);
                livingEntityPatch.getOriginal().level().addParticle(AnnoyingVillagersModParticleTypes.NULL.get(), (double) openmatrix4f1.m30 + livingEntityPatch.getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), (double) openmatrix4f1.m31 + livingEntityPatch.getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), (double) openmatrix4f1.m32 + livingEntityPatch.getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F), ((new Random()).nextFloat() - 0.5F) * 0.15F, ((new Random()).nextFloat() - 1.0F) * 0.55F, ((new Random()).nextFloat() - 0.5F) * 0.15F);
                f1 += f;
            }
        }

        if (container.getExecutor().isLogicalClient()) return;

        ServerPlayerPatch serverPlayerPatch = container.getServerExecutor();
        Player player = serverPlayerPatch.getOriginal();
        ServerLevel serverLevel = (ServerLevel) player.level();
        CompoundTag data = player.getPersistentData();

        if (player.tickCount >= 40 && player.tickCount % 20 == 0) {
            ensureWeapon(serverLevel, player, data, "NullSwordUUID", AnnoyingVillagersModEntities.NULL_SWORD.get(), NullSwordEntity.class);
            ensureWeapon(serverLevel, player, data, "NullAxeUUID", AnnoyingVillagersModEntities.NULL_AXE.get(), NullAxeEntity.class);
            ensureWeapon(serverLevel, player, data, "NullPickaxeUUID", AnnoyingVillagersModEntities.NULL_PICKAXE.get(), NullPickaxeEntity.class);
            ensureWeapon(serverLevel, player, data, "NullShovelUUID", AnnoyingVillagersModEntities.NULL_SHOVEL.get(), NullShovelEntity.class);
            ensureWeapon(serverLevel, player, data, "NullHoeUUID", AnnoyingVillagersModEntities.NULL_HOE.get(), NullHoeEntity.class);

            if (!data.hasUUID("NullSwordUUID") && player.isAlive()) {
                NullWeapon nullWeapon = AnnoyingVillagersModEntities.NULL_SWORD.get().create(serverLevel);
                if (nullWeapon != null) {
                    nullWeapon.summonNullWeaponForPlayer("NullSwordUUID", serverLevel, player);
                }
            }

            if (!data.hasUUID("NullAxeUUID") && player.isAlive()) {
                NullWeapon nullWeapon = AnnoyingVillagersModEntities.NULL_AXE.get().create(serverLevel);
                if (nullWeapon != null) {
                    nullWeapon.summonNullWeaponForPlayer("NullAxeUUID", serverLevel, player);
                }
            }

            if (!data.hasUUID("NullPickaxeUUID") && player.isAlive()) {
                NullWeapon nullWeapon = AnnoyingVillagersModEntities.NULL_PICKAXE.get().create(serverLevel);
                if (nullWeapon != null) {
                    nullWeapon.summonNullWeaponForPlayer("NullPickaxeUUID", serverLevel, player);
                }
            }

            if (!data.hasUUID("NullShovelUUID") && player.isAlive()) {
                NullWeapon nullWeapon = AnnoyingVillagersModEntities.NULL_SHOVEL.get().create(serverLevel);
                if (nullWeapon != null) {
                    nullWeapon.summonNullWeaponForPlayer("NullShovelUUID", serverLevel, player);
                }
            }

            if (!data.hasUUID("NullHoeUUID") && player.isAlive()) {
                NullHoeEntity nullHoeEntity = AnnoyingVillagersModEntities.NULL_HOE.get().create(serverLevel);
                if (nullHoeEntity != null) {
                    nullHoeEntity.summonNullWeaponForPlayer("NullHoeUUID", serverLevel, player);
                }
            }

            teleportWeapon("NullSwordUUID", serverLevel, data);
            teleportWeapon("NullAxeUUID", serverLevel, data);
            teleportWeapon("NullPickaxeUUID", serverLevel, data);
            teleportWeapon("NullHoeUUID", serverLevel, data);
            teleportWeapon("NullShovelUUID", serverLevel, data);
        }
    }

    private static void ensureWeapon(ServerLevel level, Player player, CompoundTag data,
                                     String key,
                                     EntityType<? extends NullWeapon> type,
                                     Class<? extends Entity> expectedClass) {
        removeTrackedEntityIfWrongType(level, data, key, expectedClass);
        if (data.hasUUID(key)) return;
        NullWeapon nullWeapon = type.create(level);
        if (nullWeapon == null) return;

        nullWeapon.summonNullWeaponForPlayer(key, level, player);
    }

    private void teleportWeapon(String uuidNbt, ServerLevel serverLevel, CompoundTag compoundTag) {
        if (compoundTag.hasUUID(uuidNbt)) {
            Entity entity = serverLevel.getEntity(compoundTag.getUUID(uuidNbt));
            if (entity instanceof NullWeapon nullWeapon) {
                nullWeapon.processTeleportByPlayer();
            }
        }
    }
}
