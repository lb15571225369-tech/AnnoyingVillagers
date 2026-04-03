package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.entity.BlueDemonEntity;
import com.pla.annoyingvillagers.entity.TridentLightningBolt;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModDamageTypes;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Redirect(
            method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/damagesource/DamageSource;is(Lnet/minecraft/tags/TagKey;)Z",
                    ordinal = 0
            )
    )
    private boolean customBypassInvulnerability(DamageSource source, TagKey<DamageType> tag) {
        Player self = (Player) (Object) this;
        boolean original = source.is(tag);

        if (DamageTypeTags.BYPASSES_INVULNERABILITY.equals(tag)
                && self.getAbilities().invulnerable
                && source.is(AnnoyingVillagersModDamageTypes.IMPACT_EXPLOSION)
                && source.getDirectEntity() instanceof TridentLightningBolt) {
            return false;
        }

        return original;
    }
}
