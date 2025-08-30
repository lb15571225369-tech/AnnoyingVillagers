package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.item.ObsidianSledgehammerItem;
import com.pla.annoyingvillagers.util.GroundSlamUtil;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID)
public final class GroundSlamHitProcedure {
    private GroundSlamHitProcedure() {}

    private static final Class<? extends Item> REQUIRED_WEAPON = ObsidianSledgehammerItem.class;

    private static final Vec3f TIP_LOCAL = new Vec3f(0, 0, -1.4f);

    private static final float EPS = 0.001f;

    private static final Map<String, float[]> HIT_TIMES = Map.of(
            "torment_auto_4",           new float[]{0.55f},
            "torment_dash",             new float[]{0.45f},
            "torment_charged_attack_1", new float[]{1.30f},
            "torment_charged_attack_3", new float[]{1.15f},
            "torment_airslam",          new float[]{0.55f},
            "torment_berserk_dash",     new float[]{0.50f, 0.85f, 1.40f}
    );

    private static final class State {
        String animId = "";
        int nextIndex = 0;
        float lastT = 0f;
    }
    private static final Map<UUID, State> STATES = new Object2ObjectOpenHashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (e.phase != TickEvent.Phase.END || e.player.level().isClientSide()) return;
        tickFor(e.player);
    }

    public static void tickFor(LivingEntity entity) {
        if (!entity.isAlive() || entity.level().isClientSide()) { STATES.remove(entity.getUUID()); return; }

        if (!(REQUIRED_WEAPON.isInstance(entity.getMainHandItem().getItem()))) {
            STATES.remove(entity.getUUID());
            return;
        }

        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (patch == null) { STATES.remove(entity.getUUID()); return; }

        var animPlayer = patch.getAnimator().getPlayerFor((DynamicAnimation) null);
        if (animPlayer == null) { STATES.remove(entity.getUUID()); return; }

        DynamicAnimation anim = animPlayer.getAnimation();
        if (anim == null) { STATES.remove(entity.getUUID()); return; }

        String id = animationId(anim);

        String key = keyForKnown(id);
        if (key == null) { STATES.remove(entity.getUUID()); return; }

        State st = STATES.computeIfAbsent(entity.getUUID(), k -> new State());

        if (!Objects.equals(st.animId, id)) {
            st.animId = id;
            st.nextIndex = 0;
            st.lastT = getElapsedRaw(animPlayer);
        }

        float t = getElapsedRaw(animPlayer);
        float[] hits = HIT_TIMES.get(key);
        if (hits != null && st.nextIndex < hits.length) {
            float hitT = hits[st.nextIndex];

            if ((st.lastT < hitT && t >= hitT) || Math.abs(t - hitT) <= EPS) {
                handleGroundHit(entity, patch);
                st.nextIndex++;
            }
        }
        st.lastT = t;
    }

    private static void handleGroundHit(LivingEntity who, LivingEntityPatch<?> patch) {
        Vec3 tip = GroundSlamUtil.jointWorldPoint(patch, TIP_LOCAL, Armatures.BIPED.toolR);
        BlockHitResult hit = GroundSlamUtil.raycastDown(who.level(), tip.add(0, 0.25, 0), patch, 6.0);
        if (hit == null) return;

        BlockPos bp = hit.getBlockPos();
        BlockState st = who.level().getBlockState(bp);
//        AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG] {} ground-slam hit {} at {}",
//                who.getName().getString(), st.getBlock().getName().getString(), bp);

        if (!who.level().isClientSide()) {
            ItemStack itemStack = who.getMainHandItem();
            if (itemStack.getItem() instanceof ObsidianSledgehammerItem obsidianSledgehammerItem && itemStack.getTag().contains("SecondForm")) {
                obsidianSledgehammerItem.circleHit(who, bp);
            }
        }
    }

    private static String animationId(DynamicAnimation anim) {
        try {
            ResourceLocation rl = (ResourceLocation) anim.getClass().getMethod("getLocation").invoke(anim);
            if (rl != null) return rl.getPath().toLowerCase(Locale.ROOT);
        } catch (Exception ignored) {}
        return anim.toString().toLowerCase(Locale.ROOT);
    }

    private static String keyForKnown(String id) {
        for (String key : HIT_TIMES.keySet()) {
            if (id.contains(key)) return key;
        }
        return null;
    }

    private static float getElapsedRaw(Object animPlayer) {
        try {
            return (float) animPlayer.getClass().getMethod("getElapsedTime").invoke(animPlayer);
        } catch (Exception e) {
            return 0f;
        }
    }
}
