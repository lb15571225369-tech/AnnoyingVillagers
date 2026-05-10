package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.ItemProjectile;
import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.util.CommonUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.ArrayList;
import java.util.List;

public class DiamondAttractorSwordItem extends SwordItem {

    public DiamondAttractorSwordItem() {
        super(new Tier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 6.0F;
            }

            public float getAttackDamageBonus() {
                return 2.4F;
            }

            public int getLevel() {
                return 5;
            }

            public int getEnchantmentValue() {
                return 21;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Items.DIAMOND));
            }
        }, 3, -2.8F, (new Properties()));
    }

    public static void pullWeapons(LivingEntity owner) {
        Level rawLevel = owner.level();

        if (!(rawLevel instanceof ServerLevel level)) {
            return;
        }

        double radius = 7;
        AABB area = owner.getBoundingBox().inflate(radius);

        pullDroppedWeapons(level, owner, area);
        pullHeldWeapons(level, owner, area);
    }

    private static void pullDroppedWeapons(ServerLevel level, LivingEntity owner, AABB area) {
        List<ItemEntity> itemEntities = level.getEntitiesOfClass(
                ItemEntity.class,
                area,
                itemEntity -> itemEntity.isAlive() && isPullableWeapon(itemEntity.getItem())
        );

        for (ItemEntity itemEntity : itemEntities) {
            ItemStack stackInWorld = itemEntity.getItem();

            if (stackInWorld.isEmpty()) {
                continue;
            }

            if (isBlacklistedWeapon(stackInWorld)) {
                fallBackOnBlackListWeapon(owner, itemEntity, stackInWorld);
                continue;
            }

            ItemStack pulledStack = stackInWorld.copy();
            pulledStack.setCount(1);

            ItemStack remainder = stackInWorld.copy();
            remainder.shrink(1);

            if (remainder.isEmpty()) {
                itemEntity.discard();
            } else {
                itemEntity.setItem(remainder);
            }

            spawnPulledWeapon(level, owner, pulledStack, itemEntity.position());
        }
    }

    private static void pullHeldWeapons(ServerLevel level, LivingEntity owner, AABB area) {
        List<LivingEntity> livingEntities = level.getEntitiesOfClass(
                LivingEntity.class,
                area,
                entity -> entity.isAlive()
                        && entity != owner
        );

        for (LivingEntity target : livingEntities) {
            CommonUtil.pullEntityTowardCaster(target, owner);
            LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
            if (targetPatch != null) {
                targetPatch.playAnimationSynchronized(AVAnimations.KNOCKDOWN_FORWARD, 0.0F);
            }
            if (entityCanBeDisarmed(target)) {
                List<InteractionHand> candidateHands = new ArrayList<>(2);

                ItemStack mainHand = target.getMainHandItem();
                ItemStack offHand = target.getOffhandItem();

                if (isPullableWeapon(mainHand)) {
                    candidateHands.add(InteractionHand.MAIN_HAND);
                }

                if (isPullableWeapon(offHand)) {
                    candidateHands.add(InteractionHand.OFF_HAND);
                }

                if (candidateHands.isEmpty()) {
                    continue;
                }

                InteractionHand chosenHand = candidateHands.get(
                        level.random.nextInt(candidateHands.size())
                );

                ItemStack chosenStack = target.getItemInHand(chosenHand);

                if (isBlacklistedWeapon(chosenStack)) {
                    fallBackOnBlackListWeapon(owner, target, chosenStack);
                    continue;
                }

                ItemStack pulledStack = chosenStack.copy();
                target.setItemInHand(chosenHand, ItemStack.EMPTY);
                if (target instanceof AVNpc avNpc) {
                    if (chosenHand == InteractionHand.MAIN_HAND) {
                        avNpc.setMainWeaponItem(ItemStack.EMPTY);
                    } else {
                        avNpc.setOffWeaponItem(ItemStack.EMPTY);
                    }
                }

                if (target instanceof PlayerNpcEntity playerNpcEntity) {
                    if (chosenHand == InteractionHand.MAIN_HAND) {
                        playerNpcEntity.setMainWeaponItem(ItemStack.EMPTY);
                    } else {
                        playerNpcEntity.setOffWeaponItem(ItemStack.EMPTY);
                    }
                }
                Vec3 spawnPos = getHeldWeaponSpawnPos(target);
                spawnPulledWeapon(level, owner, pulledStack, spawnPos);
            }
        }
    }

    private static Vec3 getHeldWeaponSpawnPos(LivingEntity target) {
        return target.getEyePosition().subtract(0.0D, 0.25D, 0.0D);
    }

    private static void spawnPulledWeapon(
            ServerLevel level,
            LivingEntity owner,
            ItemStack stack,
            Vec3 spawnPos
    ) {
        if (stack.isEmpty()) {
            return;
        }

        ItemProjectile projectile =
                new ItemProjectile(
                        level,
                        owner,
                        stack,
                        spawnPos.add(0.0D, 0.15D, 0.0D)
                );

        level.addFreshEntity(projectile);
    }

    public static boolean isPullableWeapon(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        Item item = stack.getItem();

        return item instanceof SwordItem
                || item instanceof DiggerItem
                || item instanceof TridentItem;
    }

    public static boolean isBlacklistedWeapon(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        for (String entry : AnnoyingVillagersConfig.DIAMOND_ATTRACTOR_WEAPON_BLACKLIST.get()) {
            if (matchesItemEntry(stack, entry)) {
                return true;
            }
        }

        return false;
    }

    private static boolean entityCanBeDisarmed(LivingEntity entity) {
        List<? extends String> entries =
                AnnoyingVillagersConfig.DIAMOND_ATTRACTOR_AFFECTED_ENTITY_TYPES.get();

        if (entries.isEmpty()) {
            return false;
        }

        for (String entry : entries) {
            if (matchesEntityEntry(entity, entry)) {
                return true;
            }
        }

        return false;
    }

    private static boolean matchesItemEntry(ItemStack stack, String entry) {
        if (stack.isEmpty() || entry == null || entry.isBlank()) {
            return false;
        }

        ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(stack.getItem());

        if (itemId == null) {
            return false;
        }

        if (entry.startsWith("#")) {
            ResourceLocation tagId = ResourceLocation.tryParse(entry.substring(1));

            if (tagId == null) {
                return false;
            }

            return stack.is(TagKey.create(Registries.ITEM, tagId));
        }

        if (entry.endsWith(":*")) {
            String namespace = entry.substring(0, entry.length() - 2);
            return itemId.getNamespace().equals(namespace);
        }
        if (!entry.contains(":")) {
            return itemId.getNamespace().equals(entry);
        }
        return itemId.toString().equals(entry);
    }

    private static boolean matchesEntityEntry(LivingEntity entity, String entry) {
        if (entry == null || entry.isBlank()) {
            return false;
        }

        EntityType<?> type = entity.getType();
        ResourceLocation typeId = ForgeRegistries.ENTITY_TYPES.getKey(type);

        if (typeId == null) {
            return false;
        }

        if (entry.startsWith("#")) {
            ResourceLocation tagId = ResourceLocation.tryParse(entry.substring(1));

            if (tagId == null) {
                return false;
            }

            return type.is(TagKey.create(Registries.ENTITY_TYPE, tagId));
        }

        if (entry.endsWith(":*")) {
            String namespace = entry.substring(0, entry.length() - 2);
            return typeId.getNamespace().equals(namespace);
        }

        if (!entry.contains(":")) {
            return typeId.getNamespace().equals(entry);
        }

        return typeId.toString().equals(entry);
    }

    public static void fallBackOnBlackListWeapon(
            LivingEntity owner,
            Entity source,
            ItemStack blacklistedStack
    ) {

    }
}
