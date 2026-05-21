package com.pla.annoyingvillagers.skill.hint;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class MoveRegistry {
    private static final Map<Item, WeaponMoveSet> ITEM_MAP = new HashMap<>();
    private static final List<DeferredEntry> DEFERRED = new ArrayList<>();

    private MoveRegistry() {}

    public static synchronized void register(Item item, WeaponMoveSet moveSet) {
        if (item == null || moveSet == null) return;
        ITEM_MAP.put(item, moveSet);
    }

    public static synchronized void register(Supplier<? extends Item> item, WeaponMoveSet moveSet) {
        if (item == null || moveSet == null) return;
        DEFERRED.add(new DeferredEntry(item, moveSet));
    }

    public static synchronized void register(RegistryObject<? extends Item> ro, WeaponMoveSet moveSet) {
        if (ro == null || moveSet == null) return;
        DEFERRED.add(new DeferredEntry(ro, moveSet));
    }

    public static synchronized int resolveDeferred() {
        int resolved = 0;
        for (Iterator<DeferredEntry> it = DEFERRED.iterator(); it.hasNext(); ) {
            DeferredEntry e = it.next();
            try {
                Item item = e.supplier.get();
                if (item == null) continue;
                ITEM_MAP.putIfAbsent(item, e.moveSet);
                it.remove();
                resolved++;
            } catch (Throwable ignored) {
                // RegistryObject suppliers can be temporarily unavailable during early init.
                // Keep unresolved entries queued so a later bootstrap call can resolve them.
            }
        }
        return resolved;
    }

    public static synchronized WeaponMoveSet get(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return null;
        return ITEM_MAP.get(stack.getItem());
    }

    public static synchronized WeaponMoveSet get(Item item) {
        return ITEM_MAP.get(item);
    }

    public static List<Move> activeMoves(ItemStack stack, MoveContext ctx, int max) {
        WeaponMoveSet ms = get(stack);
        if (ms == null) return Collections.emptyList();
        return ms.activeMoves(ctx, max);
    }

    public static synchronized int size() {
        return ITEM_MAP.size();
    }

    public static synchronized int pendingDeferredCount() {
        return DEFERRED.size();
    }

    private record DeferredEntry(Supplier<? extends Item> supplier, WeaponMoveSet moveSet) {}
}
