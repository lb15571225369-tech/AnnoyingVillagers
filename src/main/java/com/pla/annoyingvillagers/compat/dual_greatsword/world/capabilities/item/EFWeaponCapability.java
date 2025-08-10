package com.pla.annoyingvillagers.compat.dual_greatsword.world.capabilities.item;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

public class EFWeaponCapability extends WeaponCapability {

    protected final Map<Style, List<StaticAnimation>> heavyAutoAttackMotions;

    public EFWeaponCapability(yesman.epicfight.world.capabilities.item.WeaponCapability.Builder yesman_epicfight_world_capabilities_item_weaponcapability_builder, EFWeaponCapability.Builder efweaponcapability_builder) {
        super(yesman_epicfight_world_capabilities_item_weaponcapability_builder);
        this.heavyAutoAttackMotions = efweaponcapability_builder.heavyAutoAttackMotionMap;
    }

    public static EFWeaponCapability.Builder EFbuilder() {
        return new EFWeaponCapability.Builder();
    }

    public static class Builder {

        Map<Style, List<StaticAnimation>> heavyAutoAttackMotionMap = Maps.newHashMap();

        public EFWeaponCapability.Builder newHeavyStyleCombo(Style style, StaticAnimation... astaticanimation) {
            this.heavyAutoAttackMotionMap.put(style, Lists.newArrayList());
            return this;
        }
    }
}
