package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.entity.*;
import net.minecraft.world.entity.Entity;

public class ObsidianWeaponUtil {
    public static boolean isHerobrineFaction(Entity e) {
        return e instanceof Herobrine1Entity
                || e instanceof Herobrine2Entity
                || e instanceof Herobrine3Entity
                || e instanceof Herobrine5Entity
                || e instanceof Herobrine6Entity
                || e instanceof Herobrine7Entity
                || e instanceof ArmoredHerobrineEntity
                || e instanceof ShadowHerobrineEntity
                || e instanceof InfectedPlayerMobEntity
                || e instanceof InfectedTheMostMoistBurrit0Entity
                || e instanceof InfectedChrisEntity
                || e instanceof GlaiveHerobrineEntity
                || e instanceof AegisHerobrineEntity
                || e instanceof SwordsManHerobrineEntity
                || e instanceof ReaperHerobrineEntity
                || e instanceof SledgehammerHerobrineEntity
                || e instanceof NullEntity
                || e instanceof NullSwordEntity
                || e instanceof NullAxeEntity
                || e instanceof NullPickaxeEntity
                || e instanceof NullShovelEntity
                || e instanceof NullHoeEntity
                || e instanceof BlockProjectileEntity;
    }
}
