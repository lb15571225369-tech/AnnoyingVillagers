package com.pla.annoyingvillagers.world;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class EmoteActions {
    public static final int PAGE_SIZE = 8;

    private EmoteActions() {}

    @FunctionalInterface
    public interface EmoteExecutor {
        boolean execute(ServerPlayer player);
    }

    public record EmoteAction(
            ResourceLocation key,
            String translationKey,
            ResourceLocation icon,
            AssetAccessor<? extends StaticAnimation> animation,
            EmoteExecutor executor
    ) {}

    private static ResourceLocation key(String path) {
        return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, path);
    }

    private static ResourceLocation icon(String path) {
        return ResourceLocation.fromNamespaceAndPath(
                AnnoyingVillagers.MODID,
                "textures/gui/emotes/" + path + ".png"
        );
    }

    private static EmoteAction emote(String name, AssetAccessor<? extends StaticAnimation> animation) {
        return new EmoteAction(
                key(name),
                "gui.annoying_villagers.emote." + name,
                icon(name),
                animation,
                player -> playEmote(player, animation)
        );
    }

    private static final List<EmoteAction> ACTIONS = List.of(
            emote("nl_bow_emote", AVAnimations.NL_BOW_EMOTE),
            emote("nl_bowing_emote", AVAnimations.NL_BOWING_EMOTE),
            emote("nl_goad_emote", AVAnimations.NL_GOAD_EMOTE),
            emote("nl_lol_emote", AVAnimations.NL_LOL_EMOTE),
            emote("nl_wave_emote", AVAnimations.NL_WAVE_EMOTE),
            emote("lay_emote", AVAnimations.LAY_EMOTE),
            emote("push_up_emote", AVAnimations.PUSH_UP_EMOTE),
            emote("sit_emote", AVAnimations.SIT_EMOTE),
            emote("slight_emote", AVAnimations.SLIGHT_EMOTE),
            emote("death_emote", AVAnimations.DEATH_EMOTE),
            emote("funny_emote", AVAnimations.FUNNY_EMOTE),
            emote("attention_emote", AVAnimations.ATTENTION_EMOTE),
            emote("flapping_emote", AVAnimations.FLAPPING_EMOTE),
            emote("fun_jump_emote", AVAnimations.FUN_JUMP_EMOTE),
            emote("jump_emote", AVAnimations.JUMP_EMOTE),
            emote("prone_emote", AVAnimations.PRONE_EMOTE),
            emote("salute_emote", AVAnimations.SALUTE_EMOTE),
            emote("lay_relax_emote", AVAnimations.LAY_RELAX_EMOTE),
            emote("one_arm_lay_emote", AVAnimations.ONE_ARM_LAY_EMOTE),
            emote("salute_left_hand_emote", AVAnimations.SALUTE_LEFT_HAND_EMOTE),
            emote("sit_no_weapon_emote", AVAnimations.SIT_NO_WEAPON_EMOTE),
            emote("sorrow_emote", AVAnimations.SORROW_EMOTE),
            emote("surrender_emote", AVAnimations.SURRENDER_EMOTE),
            emote("dance_1", AVAnimations.DANCE_1),
            emote("dance_2", AVAnimations.DANCE_2),
            emote("dance_3", AVAnimations.DANCE_3),
            emote("dance_4", AVAnimations.DANCE_4),
            emote("dance_5", AVAnimations.DANCE_5),
            emote("dance_6", AVAnimations.DANCE_6),
            emote("dance_7", AVAnimations.DANCE_7),
            emote("dance_8", AVAnimations.DANCE_8),
            emote("dance_9", AVAnimations.DANCE_9),
            emote("dance_10", AVAnimations.DANCE_10),
            emote("dance_11", AVAnimations.DANCE_11),
            emote("dance_12", AVAnimations.DANCE_12)
    );

    private static final Set<AssetAccessor<? extends StaticAnimation>> EMOTE_ANIMATIONS;
    static {
        Set<AssetAccessor<? extends StaticAnimation>> set =
                Collections.newSetFromMap(new IdentityHashMap<>());
        for (EmoteAction action : ACTIONS) {
            set.add(action.animation());
        }
        EMOTE_ANIMATIONS = Collections.unmodifiableSet(set);
    }

    private static boolean playEmote(ServerPlayer player, AssetAccessor<? extends StaticAnimation> animation) {
        PlayerPatch<?> patch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
        if (patch instanceof ServerPlayerPatch serverPlayerPatch) {
            serverPlayerPatch.playAnimationSynchronized(animation, 0.0F);
            return true;
        }
        return false;
    }

    public static boolean isEmoteAnimation(AssetAccessor<? extends DynamicAnimation> animation) {
        return animation != null && EMOTE_ANIMATIONS.contains(animation);
    }

    private static final Map<ResourceLocation, EmoteAction> BY_KEY = ACTIONS.stream()
            .collect(Collectors.toMap(EmoteAction::key, Function.identity()));

    public static int pageCount() {
        return Math.max(1, (ACTIONS.size() + PAGE_SIZE - 1) / PAGE_SIZE);
    }

    public static List<EmoteAction> getPage(int page) {
        int clampedPage = Math.max(0, Math.min(page, pageCount() - 1));
        int from = clampedPage * PAGE_SIZE;
        int to = Math.min(from + PAGE_SIZE, ACTIONS.size());
        return ACTIONS.subList(from, to);
    }

    public static boolean run(ResourceLocation key, ServerPlayer player) {
        EmoteAction action = BY_KEY.get(key);
        return action != null && action.executor().execute(player);
    }
}