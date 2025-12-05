package com.pla.annoyingvillagers.events;

import com.pla.annoyingvillagers.capabilities.AVCategories;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegisterEvent;

import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.WOMSkills;
import reascer.wom.gameasset.animations.weapons.AnimsAgony;
import reascer.wom.gameasset.animations.weapons.AnimsEnderblaster;
import reascer.wom.gameasset.animations.weapons.AnimsRuine;
import reascer.wom.gameasset.animations.weapons.AnimsSolar;
import yesman.epicfight.api.client.forgeevent.WeaponCategoryIconRegisterEvent;
import yesman.epicfight.compat.ICompatModule;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class WomCompatRegisterEvent implements ICompatModule {
    public static void registerGuard(Event event) {
    }

    @Override
    public void onModEventBus(IEventBus iEventBus) {
    }
    @Override
    public void onForgeEventBus(IEventBus iEventBus) {
    }
    @Override
    public void onModEventBusClient(IEventBus iEventBus) {
    }
    @Override
    public void onForgeEventBusClient(IEventBus iEventBus) {
    }

    public static void regIcon(WeaponCategoryIconRegisterEvent event) {
        event.registerCategory(AVCategories.AV_AXE, new ItemStack(AnnoyingVillagersModItems.DIAMOND_DOUBLE_BIT_AXE.get()));
        event.registerCategory(AVCategories.AV_GREATSWORD, new ItemStack(AnnoyingVillagersModItems.DIAMOND_GREAT_SWORD.get()));
        event.registerCategory(AVCategories.AV_SWORD, new ItemStack(AnnoyingVillagersModItems.EMERALD_SWORD.get()));
        event.registerCategory(AVCategories.AV_LONGSWORD, new ItemStack(AnnoyingVillagersModItems.DIAMOND_LONG_SWORD.get()));
        event.registerCategory(AVCategories.AV_SPEAR, new ItemStack(AnnoyingVillagersModItems.DIAMOND_SPEAR.get()));
        event.registerCategory(AVCategories.AV_TACHI, new ItemStack(AnnoyingVillagersModItems.DIAMOND_LONG_BLADE.get()));
        event.registerCategory(AVCategories.DEMONIAC_VOLTAGE_REAVER, new ItemStack(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get()));
        event.registerCategory(AVCategories.ENDER_GLAIVE, new ItemStack(AnnoyingVillagersModItems.ENDER_GLAIVE.get()));
        event.registerCategory(AVCategories.ENDER_AEGIS, new ItemStack(AnnoyingVillagersModItems.ENDER_AEGIS.get()));
        event.registerCategory(AVCategories.ENDER_SLAYER_SCYTHE, new ItemStack(AnnoyingVillagersModItems.ENDER_SLAYER_SCYTHE.get()));
        event.registerCategory(AVCategories.OBSIDIAN_SLEDGEHAMMER, new ItemStack(AnnoyingVillagersModItems.OBSIDIAN_SLEDGEHAMMER.get()));
        event.registerCategory(AVCategories.LEGENDARY_SWORD, new ItemStack(AnnoyingVillagersModItems.LEGENDARY_SWORD.get()));
        event.registerCategory(AVCategories.HARD_GREATSWORD, new ItemStack(AnnoyingVillagersModItems.HARD_GREATSWORD.get()));
    }

    public static boolean regGuarded = false;

    public static void buildSkillEvent(RegisterEvent event) {
        if (EpicFightSkills.GUARD == null) {
            return;
        }
        if (regGuarded) {
            return;
        }
        try {
            regGuard();
        } catch (Exception ignored) {
        }
        regGuarded = true;
    }

    public static void regGuard() throws NoSuchFieldException, IllegalAccessException {
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> guardMotions = new HashMap<>();
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> guardBreakMotions = new HashMap<>();
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> advancedGuardMotions = new HashMap<>();

        guardMotions.put(AVCategories.AV_AXE, (item, player) ->
                Animations.SWORD_GUARD_HIT);
        guardBreakMotions.put(AVCategories.AV_AXE, (item, player) ->
                Animations.BIPED_COMMON_NEUTRALIZED);
        advancedGuardMotions.put(AVCategories.AV_AXE, (itemCap, playerpatch) ->
                Animations.AXE_AIRSLASH);

        guardMotions.put(AVCategories.AV_GREATSWORD, (item, player) ->
                Animations.GREATSWORD_GUARD_HIT);
        guardBreakMotions.put(AVCategories.AV_GREATSWORD, (item, player) ->
                Animations.GREATSWORD_GUARD_BREAK);
        advancedGuardMotions.put(AVCategories.AV_GREATSWORD, (itemCap, playerpatch) ->
                Animations.GREATSWORD_AIR_SLASH);

        guardMotions.put(AVCategories.AV_SWORD, (item, player) ->
                Animations.SWORD_GUARD_HIT);
        guardBreakMotions.put(AVCategories.AV_SWORD, (item, player) ->
                Animations.BIPED_COMMON_NEUTRALIZED);
        advancedGuardMotions.put(AVCategories.AV_SWORD, (itemCap, playerpatch) ->
                Animations.SWORD_AIR_SLASH);

        guardMotions.put(AVCategories.AV_LONGSWORD, (item, player) ->
                Animations.SWORD_GUARD_HIT);
        guardBreakMotions.put(AVCategories.AV_LONGSWORD, (item, player) ->
                Animations.BIPED_COMMON_NEUTRALIZED);
        advancedGuardMotions.put(AVCategories.AV_LONGSWORD, (itemCap, playerpatch) ->
                Animations.LONGSWORD_AIR_SLASH);

        guardMotions.put(AVCategories.AV_SPEAR, (item, player) ->
                AVAnimations.SPEAR_GUARD_HIT);
        guardBreakMotions.put(AVCategories.AV_SPEAR, (item, player) ->
                Animations.BIPED_COMMON_NEUTRALIZED);
        advancedGuardMotions.put(AVCategories.AV_SPEAR, (itemCap, playerpatch) ->
                Animations.SPEAR_ONEHAND_AIR_SLASH);

        guardMotions.put(AVCategories.AV_TACHI, (item, player) ->
                AVAnimations.DUAL_TACHI_GUARD_HIT);
        guardBreakMotions.put(AVCategories.AV_TACHI, (item, player) ->
                Animations.BIPED_COMMON_NEUTRALIZED);
        advancedGuardMotions.put(AVCategories.AV_TACHI, (itemCap, playerpatch) ->
                Animations.LONGSWORD_AIR_SLASH);

        guardMotions.put(AVCategories.DEMONIAC_VOLTAGE_REAVER, (item, player) ->
                Animations.GREATSWORD_GUARD_HIT);
        guardBreakMotions.put(AVCategories.DEMONIAC_VOLTAGE_REAVER, (item, player) ->
                Animations.GREATSWORD_GUARD_BREAK);
        advancedGuardMotions.put(AVCategories.DEMONIAC_VOLTAGE_REAVER, (itemCap, playerpatch) ->
                AnimsRuine.RUINE_COMET);

        guardMotions.put(AVCategories.ENDER_GLAIVE, (item, player) ->
                AVAnimations.SPEAR_GUARD_HIT);
        guardBreakMotions.put(AVCategories.ENDER_GLAIVE, (item, player) ->
                Animations.BIPED_COMMON_NEUTRALIZED);
        advancedGuardMotions.put(AVCategories.ENDER_GLAIVE, (itemCap, playerpatch) ->
                AnimsAgony.AGONY_RIPPING_FANGS);

        guardMotions.put(AVCategories.ENDER_AEGIS, (item, player) ->
                Animations.SWORD_GUARD_HIT);
        guardBreakMotions.put(AVCategories.ENDER_AEGIS, (item, player) ->
                Animations.BIPED_COMMON_NEUTRALIZED);
        advancedGuardMotions.put(AVCategories.ENDER_AEGIS, (itemCap, playerpatch) ->
                AnimsSolar.SOLAR_HORNO);

        guardMotions.put(AVCategories.ENDER_SLAYER_SCYTHE, (item, player) ->
                AVAnimations.SPEAR_GUARD_HIT);
        guardBreakMotions.put(AVCategories.ENDER_SLAYER_SCYTHE, (item, player) ->
                Animations.BIPED_COMMON_NEUTRALIZED);
        advancedGuardMotions.put(AVCategories.ENDER_SLAYER_SCYTHE, (itemCap, playerpatch) ->
                AVAnimations.ENDER_SLAYER_ANTITHEUS_GUILLOTINE);

        guardMotions.put(AVCategories.OBSIDIAN_SLEDGEHAMMER, (item, player) ->
                Animations.GREATSWORD_GUARD_HIT);
        guardBreakMotions.put(AVCategories.OBSIDIAN_SLEDGEHAMMER, (item, player) ->
                Animations.GREATSWORD_GUARD_BREAK);
        advancedGuardMotions.put(AVCategories.OBSIDIAN_SLEDGEHAMMER, (itemCap, playerpatch) ->
                AnimsEnderblaster.ENDERBLASTER_TWOHAND_TISHNAW);

        guardMotions.put(AVCategories.LEGENDARY_SWORD, (item, player) ->
                AVAnimations.LEGENDARY_SWORD_GUARD_HIT);
        guardBreakMotions.put(AVCategories.LEGENDARY_SWORD, (item, player) ->
                Animations.BIPED_COMMON_NEUTRALIZED);
        advancedGuardMotions.put(AVCategories.LEGENDARY_SWORD, (itemCap, playerpatch) ->
                WOMAnimations.TORMENT_AIRSLAM);

        guardMotions.put(AVCategories.HARD_GREATSWORD, (item, player) ->
                AVAnimations.HARD_GREATSWORD_GUARD_HIT);
        guardBreakMotions.put(AVCategories.HARD_GREATSWORD, (item, player) ->
                Animations.GREATSWORD_GUARD_BREAK);
        advancedGuardMotions.put(AVCategories.HARD_GREATSWORD, (itemCap, playerpatch) ->
                Animations.LONGSWORD_AIR_SLASH);

        Field temp;
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> target;
        temp = GuardSkill.class.getDeclaredField("guardMotions");
        temp.setAccessible(true);
        target = (Map) temp.get(WOMSkills.COUNTER_ATTACK);
        for (WeaponCategory weaponCapability : guardMotions.keySet()) {
            target.put(weaponCapability, guardMotions.get(weaponCapability));
        }
        target = (Map) temp.get(WOMSkills.VENGEFUL_PARRY);
        for (WeaponCategory weaponCapability : guardMotions.keySet()) {
            target.put(weaponCapability, guardMotions.get(weaponCapability));
        }

        temp = GuardSkill.class.getDeclaredField("guardBreakMotions");
        temp.setAccessible(true);
        target = (Map) temp.get(WOMSkills.COUNTER_ATTACK);
        for (WeaponCategory weaponCapability : guardBreakMotions.keySet()) {
            target.put(weaponCapability, guardBreakMotions.get(weaponCapability));
        }
        target = (Map) temp.get(WOMSkills.VENGEFUL_PARRY);
        for (WeaponCategory weaponCapability : guardBreakMotions.keySet()) {
            target.put(weaponCapability, guardBreakMotions.get(weaponCapability));
        }

        temp = GuardSkill.class.getDeclaredField("advancedGuardMotions");
        temp.setAccessible(true);
        target = (Map) temp.get(WOMSkills.COUNTER_ATTACK);
        for (WeaponCategory weaponCapability : advancedGuardMotions.keySet()) {
            target.put(weaponCapability, advancedGuardMotions.get(weaponCapability));
        }
    }
}