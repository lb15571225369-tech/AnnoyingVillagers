package com.pla.annoyingvillagers.events;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.capabilities.AVCategories;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import yesman.epicfight.api.client.forgeevent.WeaponCategoryIconRegisterEvent;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.forgeevent.SkillBuildEvent.ModRegistryWorker.SkillCreateEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.skill.guard.ParryingSkill;
import yesman.epicfight.skill.passive.EmergencyEscapeSkill;
import yesman.epicfight.skill.passive.SwordmasterSkill;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GuardRegisterEvent {
    public static void forceGuard(SkillBuildEvent bus) {
    }

    @SubscribeEvent
    public static void onGuardSkillCreate(SkillCreateEvent<GuardSkill.Builder> event) {
        if (event.getRegistryName().equals(ResourceLocation.fromNamespaceAndPath("epicfight","guard"))) {
            GuardSkill.Builder builder = event.getSkillBuilder();
            builder
                    .addGuardMotion(AVCategories.AV_SWORD, (item, player) -> Animations.SWORD_GUARD_HIT)
                    .addGuardMotion(AVCategories.AV_AXE, (item, player) -> Animations.SWORD_GUARD_HIT)
                    .addGuardMotion(AVCategories.AV_GREATSWORD, (item, player) -> Animations.GREATSWORD_GUARD_HIT)
                    .addGuardMotion(AVCategories.AV_LONGSWORD, (item, player) -> Animations.LONGSWORD_GUARD_HIT)
                    .addGuardMotion(AVCategories.AV_TACHI, (item, player) -> AVAnimations.DUAL_TACHI_GUARD_HIT)
                    .addGuardMotion(AVCategories.AV_SPEAR, (item, player) -> AVAnimations.SPEAR_GUARD_HIT)
                    .addGuardMotion(AVCategories.DEMONIAC_VOLTAGE_REAVER, (item, player) -> Animations.GREATSWORD_GUARD_HIT)
                    .addGuardMotion(AVCategories.ENDER_AEGIS, (item, player) -> Animations.SWORD_GUARD_HIT)
                    .addGuardMotion(AVCategories.OBSIDIAN_SLEDGEHAMMER, (item, player) -> Animations.GREATSWORD_GUARD_HIT)
                    .addGuardMotion(AVCategories.ENDER_GLAIVE, (item, player) -> AVAnimations.SPEAR_GUARD_HIT)
                    .addGuardMotion(AVCategories.ENDER_SLAYER_SCYTHE, (item, player) -> AVAnimations.SPEAR_GUARD_HIT)
                    .addGuardMotion(AVCategories.LEGENDARY_SWORD, (item, player) -> AVAnimations.LEGENDARY_SWORD_GUARD_HIT)
                    .addGuardBreakMotion(AVCategories.AV_SWORD, (item, player) -> Animations.BIPED_COMMON_NEUTRALIZED)
                    .addGuardBreakMotion(AVCategories.AV_AXE, (item, player) -> Animations.BIPED_COMMON_NEUTRALIZED)
                    .addGuardBreakMotion(AVCategories.AV_GREATSWORD, (item, player) -> Animations.GREATSWORD_GUARD_BREAK)
                    .addGuardBreakMotion(AVCategories.AV_LONGSWORD, (item, player) -> Animations.BIPED_COMMON_NEUTRALIZED)
                    .addGuardBreakMotion(AVCategories.AV_TACHI, (item, player) -> Animations.BIPED_COMMON_NEUTRALIZED)
                    .addGuardBreakMotion(AVCategories.AV_SPEAR, (item, player) -> Animations.BIPED_COMMON_NEUTRALIZED)
                    .addGuardBreakMotion(AVCategories.DEMONIAC_VOLTAGE_REAVER, (item, player) -> Animations.GREATSWORD_GUARD_BREAK)
                    .addGuardBreakMotion(AVCategories.ENDER_AEGIS, (item, player) -> Animations.BIPED_COMMON_NEUTRALIZED)
                    .addGuardBreakMotion(AVCategories.OBSIDIAN_SLEDGEHAMMER, (item, player) -> Animations.GREATSWORD_GUARD_BREAK)
                    .addGuardBreakMotion(AVCategories.ENDER_GLAIVE, (item, player) -> Animations.BIPED_COMMON_NEUTRALIZED)
                    .addGuardBreakMotion(AVCategories.ENDER_SLAYER_SCYTHE, (item, player) -> Animations.BIPED_COMMON_NEUTRALIZED)
                    .addGuardBreakMotion(AVCategories.LEGENDARY_SWORD, (item, player) -> Animations.BIPED_COMMON_NEUTRALIZED);
        }
    }

    @SubscribeEvent
    public static void onParrySkillCreate(SkillCreateEvent<ParryingSkill.Builder> event) {
        if (event.getRegistryName().equals(ResourceLocation.fromNamespaceAndPath("epicfight","parrying"))) {
            GuardSkill.Builder builder = event.getSkillBuilder();
            builder
                    .addGuardMotion(AVCategories.AV_SWORD, (item, player) -> Animations.SWORD_GUARD_HIT)
                    .addGuardMotion(AVCategories.AV_AXE, (item, player) -> Animations.SWORD_GUARD_HIT)
                    .addGuardMotion(AVCategories.AV_GREATSWORD, (item, player) -> Animations.GREATSWORD_GUARD_HIT)
                    .addGuardMotion(AVCategories.AV_LONGSWORD, (item, player) -> Animations.LONGSWORD_GUARD_HIT)
                    .addGuardMotion(AVCategories.AV_TACHI, (item, player) -> AVAnimations.DUAL_TACHI_GUARD_HIT)
                    .addGuardMotion(AVCategories.AV_SPEAR, (item, player) -> AVAnimations.SPEAR_GUARD_HIT)
                    .addGuardMotion(AVCategories.DEMONIAC_VOLTAGE_REAVER, (item, player) -> Animations.GREATSWORD_GUARD_HIT)
                    .addGuardMotion(AVCategories.ENDER_AEGIS, (item, player) -> Animations.SWORD_GUARD_HIT)
                    .addGuardMotion(AVCategories.OBSIDIAN_SLEDGEHAMMER, (item, player) -> Animations.GREATSWORD_GUARD_HIT)
                    .addGuardMotion(AVCategories.ENDER_GLAIVE, (item, player) -> AVAnimations.SPEAR_GUARD_HIT)
                    .addGuardMotion(AVCategories.ENDER_SLAYER_SCYTHE, (item, player) -> AVAnimations.SPEAR_GUARD_HIT)
                    .addGuardMotion(AVCategories.LEGENDARY_SWORD, (item, player) -> AVAnimations.LEGENDARY_SWORD_GUARD_HIT)
                    .addGuardMotion(AVCategories.HARD_GREATSWORD, (item, player) -> AVAnimations.HARD_GREATSWORD_GUARD_HIT)
                    .addGuardBreakMotion(AVCategories.AV_SWORD, (item, player) -> Animations.BIPED_COMMON_NEUTRALIZED)
                    .addGuardBreakMotion(AVCategories.AV_AXE, (item, player) -> Animations.BIPED_COMMON_NEUTRALIZED)
                    .addGuardBreakMotion(AVCategories.AV_GREATSWORD, (item, player) -> Animations.GREATSWORD_GUARD_BREAK)
                    .addGuardBreakMotion(AVCategories.AV_LONGSWORD, (item, player) -> Animations.BIPED_COMMON_NEUTRALIZED)
                    .addGuardBreakMotion(AVCategories.AV_TACHI, (item, player) -> Animations.BIPED_COMMON_NEUTRALIZED)
                    .addGuardBreakMotion(AVCategories.AV_SPEAR, (item, player) -> Animations.BIPED_COMMON_NEUTRALIZED)
                    .addGuardBreakMotion(AVCategories.DEMONIAC_VOLTAGE_REAVER, (item, player) -> Animations.GREATSWORD_GUARD_BREAK)
                    .addGuardBreakMotion(AVCategories.ENDER_AEGIS, (item, player) -> Animations.BIPED_COMMON_NEUTRALIZED)
                    .addGuardBreakMotion(AVCategories.OBSIDIAN_SLEDGEHAMMER, (item, player) -> Animations.GREATSWORD_GUARD_BREAK)
                    .addGuardBreakMotion(AVCategories.ENDER_GLAIVE, (item, player) -> Animations.BIPED_COMMON_NEUTRALIZED)
                    .addGuardBreakMotion(AVCategories.ENDER_SLAYER_SCYTHE, (item, player) -> Animations.BIPED_COMMON_NEUTRALIZED)
                    .addGuardBreakMotion(AVCategories.LEGENDARY_SWORD, (item, player) -> Animations.BIPED_COMMON_NEUTRALIZED)
                    .addGuardBreakMotion(AVCategories.HARD_GREATSWORD, (item, player) -> Animations.GREATSWORD_GUARD_BREAK);
        }
    }

    @SubscribeEvent
    public static void onScapeSkillCreate(SkillCreateEvent<EmergencyEscapeSkill.Builder> event) {
        if (event.getRegistryName().equals(ResourceLocation.fromNamespaceAndPath("epicfight","emergency_escape"))) {
            EmergencyEscapeSkill.Builder builder = event.getSkillBuilder();
            builder
                    .addAvailableWeaponCategory(AVCategories.AV_AXE)
                    .addAvailableWeaponCategory(AVCategories.AV_GREATSWORD)
                    .addAvailableWeaponCategory(AVCategories.AV_SWORD)
                    .addAvailableWeaponCategory(AVCategories.AV_LONGSWORD)
                    .addAvailableWeaponCategory(AVCategories.AV_SPEAR)
                    .addAvailableWeaponCategory(AVCategories.AV_TACHI)
                    .addAvailableWeaponCategory(AVCategories.DEMONIAC_VOLTAGE_REAVER)
                    .addAvailableWeaponCategory(AVCategories.ENDER_AEGIS)
                    .addAvailableWeaponCategory(AVCategories.ENDER_GLAIVE)
                    .addAvailableWeaponCategory(AVCategories.ENDER_SLAYER_SCYTHE)
                    .addAvailableWeaponCategory(AVCategories.OBSIDIAN_SLEDGEHAMMER)
                    .addAvailableWeaponCategory(AVCategories.LEGENDARY_SWORD)
                    .addAvailableWeaponCategory(AVCategories.HARD_GREATSWORD);
        }
    }

    @SubscribeEvent
    public static void onSwordSkillCreate(SkillCreateEvent<SwordmasterSkill.Builder> event) {
        if (event.getRegistryName().equals(ResourceLocation.fromNamespaceAndPath("epicfight","swordmaster"))) {
            SwordmasterSkill.Builder builder = event.getSkillBuilder();
            builder
                    .addAvailableWeaponCategory(AVCategories.AV_SWORD)
                    .addAvailableWeaponCategory(AVCategories.AV_LONGSWORD)
                    .addAvailableWeaponCategory(AVCategories.AV_TACHI)
                    .addAvailableWeaponCategory(AVCategories.HARD_GREATSWORD);
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onIconCreate(WeaponCategoryIconRegisterEvent icon){
        icon.registerCategory(AVCategories.AV_AXE, new ItemStack(AnnoyingVillagersModItems.DIAMOND_DOUBLE_BIT_AXE.get()));
        icon.registerCategory(AVCategories.AV_GREATSWORD, new ItemStack(AnnoyingVillagersModItems.DIAMOND_GREAT_SWORD.get()));
        icon.registerCategory(AVCategories.AV_SWORD, new ItemStack(AnnoyingVillagersModItems.EMERALD_SWORD.get()));
        icon.registerCategory(AVCategories.AV_LONGSWORD, new ItemStack(AnnoyingVillagersModItems.DIAMOND_LONG_SWORD.get()));
        icon.registerCategory(AVCategories.AV_SPEAR, new ItemStack(AnnoyingVillagersModItems.DIAMOND_SPEAR.get()));
        icon.registerCategory(AVCategories.AV_TACHI, new ItemStack(AnnoyingVillagersModItems.DIAMOND_LONG_BLADE.get()));
        icon.registerCategory(AVCategories.DEMONIAC_VOLTAGE_REAVER, new ItemStack(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get()));
        icon.registerCategory(AVCategories.ENDER_GLAIVE, new ItemStack(AnnoyingVillagersModItems.ENDER_GLAIVE.get()));
        icon.registerCategory(AVCategories.ENDER_AEGIS, new ItemStack(AnnoyingVillagersModItems.ENDER_AEGIS.get()));
        icon.registerCategory(AVCategories.ENDER_SLAYER_SCYTHE, new ItemStack(AnnoyingVillagersModItems.ENDER_SLAYER_SCYTHE.get()));
        icon.registerCategory(AVCategories.OBSIDIAN_SLEDGEHAMMER, new ItemStack(AnnoyingVillagersModItems.OBSIDIAN_SLEDGEHAMMER.get()));
        icon.registerCategory(AVCategories.LEGENDARY_SWORD, new ItemStack(AnnoyingVillagersModItems.LEGENDARY_SWORD.get()));
        icon.registerCategory(AVCategories.HARD_GREATSWORD, new ItemStack(AnnoyingVillagersModItems.HARD_GREATSWORD.get()));
    }
}