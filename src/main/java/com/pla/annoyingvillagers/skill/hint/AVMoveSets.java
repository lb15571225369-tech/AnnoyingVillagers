package com.pla.annoyingvillagers.skill.hint;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.function.Supplier;

import static com.pla.annoyingvillagers.skill.hint.MoveLibrary.*;

public final class AVMoveSets {
    private static boolean bootstrapped;

    private AVMoveSets() {}

    public static synchronized void bootstrap() {
        if (bootstrapped) {
            MoveRegistry.resolveDeferred();
            return;
        }

        registerCustomWeapons();
        registerSwordFamily();
        registerLongswordFamily();
        registerTachiFamily();
        registerGreatswordFamily();
        registerSpearFamily();
        registerAxeFamily();
        registerDaggerFamily();
        registerHookFamily();
        registerSpecialFamily();
        registerBowFamily();
        MoveRegistry.resolveDeferred();
        bootstrapped = true;
    }

    private static void registerCustomWeapons() {
        // Ender Aegis — duration innate skill, two-hand sword w/ shield projectile
        register(AnnoyingVillagersModItems.ENDER_AEGIS, "ender_aegis",
                groundedCombo("auto"),
                dashAttack("dash"),
                airAttack("airslam"),
                specialTap("ender_aegis_skill"));

        // Ender Glaive — long polearm, ranged shockwave special
        register(AnnoyingVillagersModItems.ENDER_GLAIVE, "ender_glaive",
                groundedCombo("agony_combo"),
                dashAttack("agony_dash"),
                airAttack("agony_air"),
                MoveLibrary.mountAttack("mount_pierce"),
                specialTap("austerlitz"));

        // Demoniac Voltage Reaver — greatsword, snake combo state
        register(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER, "demoniac_voltage_reaver",
                groundedCombo("ruine_combo"),
                airAttack("ruine_airslash"),
                MoveLibrary.mountAttack("mount_cleave"),
                specialTap("voltage_burst"));

        register(AnnoyingVillagersModItems.OBSIDIAN_SLEDGEHAMMER, "obsidian_sledgehammer",
                groundedCombo("solar_combo"),
                airAttack("solar_airslash"),
                specialTap("seismic_impact"));

        register(AnnoyingVillagersModItems.ENDER_SLAYER_SCYTHE, "ender_slayer_scythe",
                groundedCombo("scythe_combo"),
                airAttack("scythe_airslash"),
                MoveLibrary.mountAttack("mount_reap"),
                specialTap("phantom_swarm"));

        register(AnnoyingVillagersModItems.NULL_WEAPON, "null_weapon",
                groundedCombo("antitheus_combo"),
                airAttack("antitheus_air"),
                specialTap("blackhole"));

        register(AnnoyingVillagersModItems.OBSIDIAN_WEAPON, "obsidian_weapon",
                groundedCombo("obsidian_fist_combo"),
                airAttack("obsidian_airslash"),
                specialTap("obsidian_burst"));

        register(AnnoyingVillagersModItems.BEDROCK_WEAPON, "bedrock_weapon",
                groundedCombo("fist_combo"),
                airAttack("fist_air"),
                dashAttack("fist_dash"),
                specialTap("bedrock_aura"));

        register(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR, "shadow_obsidian_pillar",
                groundedCombo("pillar_combo"),
                airAttack("pillar_airslash"),
                Move.builder("shadow_obsidian_pillar.ochs_combo")
                        .input(MoveInputHint.LMB).priority(60)
                        .when(StyleConditions.ochsStyle().and(MoveContext::grounded))
                        .build(),
                specialTap("pillar_summon"));

        register(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD, "shadow_obsidian_sword",
                Move.builder("shadow_obsidian_sword.solo_combo")
                        .input(MoveInputHint.LMB).priority(50)
                        .when(StyleConditions.oneHand().and(MoveContext::grounded))
                        .build(),
                Move.builder("shadow_obsidian_sword.dual_combo")
                        .input(MoveInputHint.LMB).priority(60)
                        .when(StyleConditions.twoHand().and(MoveContext::grounded))
                        .build(),
                airAttack("airslam"),
                specialTap("dual_burst"));

        register(AnnoyingVillagersModItems.LEGENDARY_SWORD, "legendary_sword",
                groundedCombo("torment_combo"),
                MoveLibrary.mountAttack("mount_cleave"),
                Move.builder("legendary_sword.combined_form")
                        .input(MoveInputHint.LMB).priority(85)
                        .when(StyleConditions.offhandIs(CapabilityItem.WeaponCategories.TRIDENT)
                                .and(MoveContext::grounded))
                        .build(),
                specialTap("yellow_form"));

        register(AnnoyingVillagersModItems.BLUE_DEMON_TRIDENT, "blue_demon_trident",
                Move.builder("blue_demon_trident.solo_combo")
                        .input(MoveInputHint.LMB).priority(50)
                        .when(StyleConditions.oneHand().and(MoveContext::grounded))
                        .build(),
                Move.builder("blue_demon_trident.dual_combo")
                        .input(MoveInputHint.LMB).priority(60)
                        .when(StyleConditions.twoHand().and(MoveContext::grounded))
                        .build(),
                airAttack("airdrop"),
                MoveLibrary.mountAttack("mount_pierce"),
                specialTap("trident_festival"));

        register(AnnoyingVillagersModItems.WOOPIE_THE_SWORD, "woopie_the_sword",
                groundedCombo("satsujin_combo"),
                airAttack("satsujin_air"),
                dashAttack("satsujin_dash"),
                specialTap("tsukuyomi"));

        register(AnnoyingVillagersModItems.GREAT_SWORD, "great_sword",
                groundedCombo("herrscher_combo"),
                airAttack("ausrottung_air"),
                specialTap("ausrottung"));

        register(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_WEAPON, "shadow_obsidian_weapon",
                groundedCombo("shadow_obsidian_fist"),
                airAttack("airslash"),
                specialTap("burst"));
    }

    private static void registerSwordFamily() {
        register(AnnoyingVillagersModItems.BLACK_FIRE_SWORD, "black_fire_sword",
                groundedCombo("herrscher_combo"),
                airAttack("airslash"),
                dashAttack("dash"),
                specialTap("black_fire"));

        register(AnnoyingVillagersModItems.BLUE_FLAME_SWORD, "blue_flame_sword",
                groundedCombo("herrscher_combo"),
                airAttack("airslash"),
                dashAttack("dash"),
                specialTap("blue_flame_burst"));

        register(AnnoyingVillagersModItems.CLOW_SWORD, "clow_sword",
                groundedCombo("herrscher_combo"),
                airAttack("airslash"),
                dashAttack("dash"),
                specialTap("befreiung"));

        register(AnnoyingVillagersModItems.THUNDER_DIAMOND_BLADE, "thunder_diamond_blade",
                Move.builder("thunder_diamond_blade.solo_combo")
                        .input(MoveInputHint.LMB).priority(50)
                        .when(StyleConditions.oneHand().and(MoveContext::grounded)).build(),
                Move.builder("thunder_diamond_blade.dual_combo")
                        .input(MoveInputHint.LMB).priority(55)
                        .when(StyleConditions.twoHand().and(MoveContext::grounded)).build(),
                airAttack("airslash"),
                dashAttack("dash"),
                specialTap("thunder_strike"));

        register(AnnoyingVillagersModItems.DIAMOND_ATTRACTOR_SWORD, "diamond_attractor_sword",
                groundedCombo("auto_combo"),
                airAttack("airslash"),
                dashAttack("dash"),
                specialTap("attract"));

        register(AnnoyingVillagersModItems.DIAMOND_BLASTER_SWORD, "diamond_blaster_sword",
                groundedCombo("auto_combo"),
                airAttack("airslash"),
                dashAttack("dash"),
                specialTap("blaster"));

        register(AnnoyingVillagersModItems.HACKER_SWORD, "hacker_sword",
                groundedCombo("auto_combo"),
                airAttack("airslash"),
                dashAttack("dash"),
                specialTap("hack"));

        register(AnnoyingVillagersModItems.DIAMOND_KNIGHT_SWORD, "av_sword.knight",
                "av_sword",
                groundedCombo("auto_combo"),
                airAttack("airslash"),
                dashAttack("dash"));

        register(AnnoyingVillagersModItems.RUBY_KNIGHT_SWORD, "av_sword.knight",
                "av_sword",
                groundedCombo("auto_combo"),
                airAttack("airslash"),
                dashAttack("dash"));

        register(AnnoyingVillagersModItems.RUBY_SWORD, "av_sword",
                groundedCombo("auto_combo"),
                airAttack("airslash"),
                dashAttack("dash"));

        register(AnnoyingVillagersModItems.JADE_SWORD, "av_sword",
                groundedCombo("auto_combo"),
                airAttack("airslash"),
                dashAttack("dash"));

        register(AnnoyingVillagersModItems.RED_DIAMOND_SWORD, "av_sword",
                groundedCombo("auto_combo"),
                airAttack("airslash"),
                dashAttack("dash"));

        register(AnnoyingVillagersModItems.PALADIN_SWORD, "av_sword",
                groundedCombo("auto_combo"),
                airAttack("airslash"),
                dashAttack("dash"));

        register(AnnoyingVillagersModItems.CENTRANOS_SWORD, "cleaver",
                groundedCombo("squire_combo"),
                airAttack("airslash"),
                dashAttack("squire_dash"),
                sneakHeavy("heavy_blow"),
                specialTap("cleave"));

        register(AnnoyingVillagersModItems.IRON_CLEAVER, "cleaver",
                groundedCombo("squire_combo"),
                dashAttack("squire_dash"),
                sneakHeavy("heavy_blow"),
                specialTap("cleave"));
    }

    private static void registerLongswordFamily() {
        registerEach("av_longsword", new RegistryObject[]{
                AnnoyingVillagersModItems.DIAMOND_LONGSWORD,
                AnnoyingVillagersModItems.GOLDEN_LONGSWORD,
                AnnoyingVillagersModItems.IRON_LONGSWORD,
                AnnoyingVillagersModItems.RUBY_LONGSWORD
        }, groundedCombo("liechtenauer_combo"),
                airAttack("longsword_air"),
                dashAttack("longsword_dash"),
                sneakHeavy("dual_longsword_skill"));

        register(AnnoyingVillagersModItems.DIAMOND_CHIPPED_LONGSWORD, "chipped_longsword",
                groundedCombo("ruine_combo"),
                airAttack("ruine_air"),
                dashAttack("ruine_dash"),
                specialTap("redemption"));
    }

    private static void registerTachiFamily() {
        registerEach("av_tachi", new RegistryObject[]{
                AnnoyingVillagersModItems.DIAMOND_FALCHION,
                AnnoyingVillagersModItems.DIAMOND_GREAT_FALCHION,
                AnnoyingVillagersModItems.NETHERITE_FALCHION
        }, groundedCombo("tachi_combo"),
                airAttack("tachi_air"),
                dashAttack("tachi_dash"));

        register(AnnoyingVillagersModItems.DIAMOND_SABRE, "diamond_sabre",
                groundedCombo("sabre_combo"),
                airAttack("sabre_aerial"),
                dashAttack("sabre_dash"),
                specialTap("quad_sting"));

        register(AnnoyingVillagersModItems.NETHERITE_SABRE, "diamond_sabre",
                groundedCombo("sabre_combo"),
                airAttack("sabre_aerial"),
                dashAttack("sabre_dash"),
                specialTap("quad_sting"));

        register(AnnoyingVillagersModItems.DIAMOND_WARBLADE, "diamond_warblade",
                groundedCombo("warblade_combo"),
                airAttack("longsword_air"),
                dashAttack("tachi_dash"),
                specialTap("tsukuyomi"));

        register(AnnoyingVillagersModItems.DIAMOND_LAEVATEINN, "diamond_laevateinn",
                groundedCombo("laevateinn_combo"),
                airAttack("longsword_air"),
                dashAttack("tachi_dash"),
                specialTap("blossom_slash"));

        register(AnnoyingVillagersModItems.IRON_TWIN_BLADE_KATANA, "iron_twin_blade_katana",
                groundedCombo("staff_combo"),
                airAttack("staff_air"),
                dashAttack("staff_dash"));
    }

    private static void registerGreatswordFamily() {
        registerEach("av_greatsword", new RegistryObject[]{
                AnnoyingVillagersModItems.DIAMOND_GREATSWORD,
                AnnoyingVillagersModItems.RUBY_GREATSWORD
        }, groundedCombo("greatsword_combo"),
                airAttack("greatsword_airslam"),
                dashAttack("greatsword_dash"),
                sneakHeavy("power_geyser"));
    }

    private static void registerSpearFamily() {
        registerEach("av_spear", new RegistryObject[]{
                AnnoyingVillagersModItems.DIAMOND_SPEAR,
                AnnoyingVillagersModItems.NETHERITE_SPEAR,
                AnnoyingVillagersModItems.DIAMOND_BOLT,
                AnnoyingVillagersModItems.BLACKSCRATCHER,
                AnnoyingVillagersModItems.IRON_SICKLE,
                AnnoyingVillagersModItems.DIAMOND_SICKLE,
                AnnoyingVillagersModItems.DOUBLE_DIAMOND_GLAIVE,
                AnnoyingVillagersModItems.TWIN_DIAMOND_SPEAR,
                AnnoyingVillagersModItems.SPEAR_AXE
        }, groundedCombo("spear_combo"),
                airAttack("spear_air"),
                MoveLibrary.mountAttack("mount_pierce"),
                dashAttack("spear_dash"));

        register(AnnoyingVillagersModItems.DIAMOND_HALBERD, "halberd",
                groundedCombo("halberd_combo"),
                airAttack("halberd_air"),
                dashAttack("halberd_dash"),
                MoveLibrary.mountAttack("mount_reach"),
                sneakHeavy("helicopter"));

        register(AnnoyingVillagersModItems.IRON_HALBERD, "halberd",
                groundedCombo("halberd_combo"),
                airAttack("halberd_air"),
                dashAttack("halberd_dash"),
                MoveLibrary.mountAttack("mount_reach"),
                sneakHeavy("helicopter"));

        register(AnnoyingVillagersModItems.IRON_DOUBLE_BLADED_HALBERD, "double_halberd",
                groundedCombo("halberd_combo"),
                airAttack("halberd_air"),
                dashAttack("halberd_dash"),
                MoveLibrary.mountAttack("mount_reach"),
                sneakHeavy("helicopter"));
    }

    private static void registerAxeFamily() {
        register(AnnoyingVillagersModItems.RED_AXE, "red_axe",
                groundedCombo("axe_combo"),
                airAttack("axe_air"),
                dashAttack("axe_dash"),
                MoveLibrary.mountAttack("mount_chop"),
                specialTap("dual_spin"));

        register(AnnoyingVillagersModItems.GIANT_RED_AXE, "giant_axe",
                groundedCombo("baxe_combo"),
                airAttack("baxe_air"),
                dashAttack("baxe_dash"),
                MoveLibrary.mountAttack("mount_chop"),
                specialTap("seismic_impact"));

        register(AnnoyingVillagersModItems.GIANT_NETHERITE_AXE, "giant_axe",
                groundedCombo("baxe_combo"),
                airAttack("baxe_air"),
                dashAttack("baxe_dash"),
                MoveLibrary.mountAttack("mount_chop"),
                specialTap("seismic_impact"));

        register(AnnoyingVillagersModItems.EARTH_AXE, "earth_axe",
                groundedCombo("axe_combo"),
                airAttack("axe_air"),
                dashAttack("axe_dash"),
                MoveLibrary.mountAttack("mount_chop"),
                specialTap("earth_quake"));

        register(AnnoyingVillagersModItems.EXTERMINATOR_BATTLEAXE, "exterminator_battleaxe",
                groundedCombo("axe_combo"),
                airAttack("axe_air"),
                dashAttack("axe_dash"),
                specialTap("dual_spin"));
        register(AnnoyingVillagersModItems.EXTERMINATOR_BATTLEAXE_GREEN, "exterminator_battleaxe",
                groundedCombo("axe_combo"),
                airAttack("axe_air"),
                dashAttack("axe_dash"),
                specialTap("dual_spin"));

        register(AnnoyingVillagersModItems.SAMANTHA_THE_KILLER_AXE, "killer_axe",
                groundedCombo("axe_combo"),
                airAttack("axe_air"),
                dashAttack("axe_dash"),
                specialTap("killer_strike"));

        registerEach("greataxe", new RegistryObject[]{
                AnnoyingVillagersModItems.DIAMOND_GREATAXE,
                AnnoyingVillagersModItems.IRON_GREATAXE,
                AnnoyingVillagersModItems.NETHERITE_GREATAXE
        }, groundedCombo("baxe_combo"),
                airAttack("baxe_air"),
                dashAttack("baxe_dash"),
                MoveLibrary.mountAttack("mount_chop"),
                specialTap("seismic_impact"));

        register(AnnoyingVillagersModItems.DIAMOND_BATTLEAXE, "battle_axe",
                groundedCombo("axe_combo"),
                airAttack("axe_air"),
                dashAttack("axe_dash"),
                specialTap("dual_spin"));

        // Mace mapped to av_axe in data
        registerEach("av_axe", new RegistryObject[]{
                AnnoyingVillagersModItems.DIAMOND_MACE,
                AnnoyingVillagersModItems.GOLDEN_MACE
        }, groundedCombo("axe_combo"),
                airAttack("axe_air"),
                dashAttack("axe_dash"),
                MoveLibrary.mountAttack("mount_chop"));
    }

    private static void registerDaggerFamily() {
        register(AnnoyingVillagersModItems.KNIFE, "knife",
                Move.builder("knife.solo_combo")
                        .input(MoveInputHint.LMB).priority(50)
                        .when(StyleConditions.oneHand().and(MoveContext::grounded))
                        .build(),
                Move.builder("knife.dual_combo")
                        .input(MoveInputHint.LMB).priority(55)
                        .when(StyleConditions.twoHand().and(MoveContext::grounded))
                        .build(),
                airAttack("dagger_air"),
                dashAttack("dagger_dash"),
                specialTap("knife"));

        register(AnnoyingVillagersModItems.DIAMOND_KNIFE, "knife",
                Move.builder("knife.solo_combo").input(MoveInputHint.LMB).priority(50)
                        .when(StyleConditions.oneHand().and(MoveContext::grounded)).build(),
                Move.builder("knife.dual_combo").input(MoveInputHint.LMB).priority(55)
                        .when(StyleConditions.twoHand().and(MoveContext::grounded)).build(),
                airAttack("dagger_air"),
                dashAttack("dagger_dash"),
                specialTap("knife"));

        register(AnnoyingVillagersModItems.NETHERITE_KNIFE, "knife",
                Move.builder("knife.solo_combo").input(MoveInputHint.LMB).priority(50)
                        .when(StyleConditions.oneHand().and(MoveContext::grounded)).build(),
                Move.builder("knife.dual_combo").input(MoveInputHint.LMB).priority(55)
                        .when(StyleConditions.twoHand().and(MoveContext::grounded)).build(),
                airAttack("dagger_air"),
                dashAttack("dagger_dash"),
                specialTap("knife"));

        register(AnnoyingVillagersModItems.DIAMOND_ARMBLADE, "arm_blade",
                groundedCombo("iron_lotus_combo"),
                airAttack("dagger_air"),
                dashAttack("iron_lotus_dash"));

        register(AnnoyingVillagersModItems.GOLDEN_MOON_BLADE, "moon_blade",
                groundedCombo("iron_lotus_combo"),
                airAttack("dagger_air"),
                dashAttack("iron_lotus_dash"));
        register(AnnoyingVillagersModItems.DIAMOND_MOON_BLADE, "moon_blade",
                groundedCombo("iron_lotus_combo"),
                airAttack("dagger_air"),
                dashAttack("iron_lotus_dash"));

        register(AnnoyingVillagersModItems.DIAMOND_CLAW, "claw",
                groundedCombo("iron_lotus_combo"),
                airAttack("dagger_air"),
                dashAttack("iron_lotus_dash"));
    }

    private static void registerHookFamily() {
        registerEach("hook_sword", new RegistryObject[]{
                AnnoyingVillagersModItems.HOOKED_DIAMOND_SWORD,
                AnnoyingVillagersModItems.HOOKED_IRON_SWORD,
                AnnoyingVillagersModItems.HOOKED_GOLDEN_SWORD
        }, Move.builder("hook_sword.solo_combo").input(MoveInputHint.LMB).priority(50)
                .when(StyleConditions.oneHand().and(MoveContext::grounded)).build(),
                Move.builder("hook_sword.dual_combo").input(MoveInputHint.LMB).priority(55)
                        .when(StyleConditions.twoHand().and(MoveContext::grounded)).build(),
                airAttack("airslash"),
                dashAttack("dash"),
                specialTap("hook_sweep"));

        register(AnnoyingVillagersModItems.FLANKER_HOOKED_SWORD, "flanker_hook_sword",
                groundedCombo("flanker_combo"),
                airAttack("airslash"),
                dashAttack("dash"),
                specialTap("flank_uppercut"));

        register(AnnoyingVillagersModItems.DNAX_HOOKED_SWORD, "dnax_hook_sword",
                Move.builder("dnax_hook_sword.solo_combo").input(MoveInputHint.LMB).priority(50)
                        .when(StyleConditions.oneHand().and(MoveContext::grounded)).build(),
                Move.builder("dnax_hook_sword.dual_combo").input(MoveInputHint.LMB).priority(55)
                        .when(StyleConditions.twoHand().and(MoveContext::grounded)).build(),
                airAttack("airslash"),
                dashAttack("dash"),
                specialTap("sweeping_edge"));
    }

    private static void registerSpecialFamily() {
        register(AnnoyingVillagersModItems.WOODEN_DOOR, "wooden_door",
                groundedCombo("door_combo"),
                airAttack("airslash"),
                specialTap("torment_dash"));
        register(AnnoyingVillagersModItems.TRAPDOOR, "trapdoor",
                groundedCombo("trap_combo"),
                airAttack("airslash"),
                specialTap("whirlwind"));
        register(AnnoyingVillagersModItems.LADDER, "ladder",
                groundedCombo("ladder_combo"),
                airAttack("airslash"),
                specialTap("ladder_smash"));
        register(AnnoyingVillagersModItems.CRAFTING_TABLE, "crafting_table",
                groundedCombo("table_combo"),
                airAttack("airslash"),
                specialTap("solar_charge"));
    }

    private static void registerBowFamily() {
        for (Item item : ForgeRegistries.ITEMS) {
            if (item instanceof BowItem) {
                MoveRegistry.register(item, WeaponMoveSet.builder("av_bow")
                        .add(MoveLibrary.bowAim("av_bow.aim"))
                        .add(MoveLibrary.bowRelease("av_bow.release"))
                        .add(Move.builder("av_bow.cancel")
                                .input(MoveInputHint.SNEAK_LMB).priority(40)
                                .when(MoveContext::drawingBow).build())
                        .build());
            }
        }
    }

    private static void registerEach(String setIdBase, RegistryObject<? extends Item>[] items, Move... moves) {
        for (RegistryObject<? extends Item> ro : items) {
            WeaponMoveSet set = build(setIdBase + "." + ro.getId().getPath(), moves);
            MoveRegistry.register(ro, set);
        }
    }

    private static void register(Supplier<? extends Item> item, String setId, Move... moves) {
        MoveRegistry.register(item::get, build(setId, moves));
    }

    private static void register(RegistryObject<? extends Item> item, String setId, Move... moves) {
        MoveRegistry.register(item, build(setId, moves));
    }

    private static void register(RegistryObject<? extends Item> item, String displayId, String setId, Move... moves) {
        MoveRegistry.register(item, build(setId + "." + displayId, moves));
    }

    private static WeaponMoveSet build(String setId, Move... moves) {
        WeaponMoveSet.Builder b = WeaponMoveSet.builder(setId);
        for (Move m : moves) b.add(m);
        return b.build();
    }
}
