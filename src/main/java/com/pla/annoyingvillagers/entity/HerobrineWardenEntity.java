//package com.pla.annoyingvillagers.entity;
//
//import com.mojang.brigadier.exceptions.CommandSyntaxException;
//import net.minecraft.world.damagesource.DamageSource;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.Mob;
//import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
//import net.minecraft.world.entity.ai.attributes.Attributes;
//import net.minecraft.world.entity.monster.Monster;
//import net.minecraft.world.entity.monster.warden.Warden;
//import net.minecraft.world.level.Level;
//
//public class HerobrineWardenEntity extends Warden {
//    public HerobrineWardenEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
//        super(pEntityType, pLevel);
//    }
//
//    @Override
//    protected void registerGoals() {
//    }
//
//    @Override
//    public boolean hurt(DamageSource pSource, float pAmount) {
//        if (!this.level().isClientSide()) {
//            try {
//                this.getServer().getCommands().getDispatcher().execute(
//                        "playsound epicfight:entity.hit.clash neutral @p",
//                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                this.getServer().getCommands().getDispatcher().execute(
//                        "execute at @s run particle epicfight:hit_blade ^ ^1.5 ^0.8 0.1 0.1 0.1 1 1",
//                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//            } catch (CommandSyntaxException e) {
//            }
//        }
//        return false;
//    }
//
//    public static AttributeSupplier.Builder createAttributes() {
//        AttributeSupplier.Builder builder = Mob.createMobAttributes();
//
//        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.26D);
//        builder = builder.add(Attributes.MAX_HEALTH, 500.0D);
//        builder = builder.add(Attributes.ARMOR, 20.0D);
//        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0D);
//        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
//        return builder;
//    }
//}
