package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.entity.Entity;

public class PlayerNpcOnHurtProcedure {
    public static void execute(final PlayerNpcEntity entity, Entity attacker) {
        if (entity != null && attacker != null) {
            if (entity.getEnderPearlCooldown() == 0) {
                CombatBehaviour.throwEnderPearl(entity, 180.0F);

                if (Math.random() <= 0.5D) {
                    new DelayedTask(20) {
                        @Override
                        public void run() {
                            if (entity.isAlive()) {
                                CombatBehaviour.throwEnderPearl(entity, 90.0F);
                            }
                        }
                    };
                }

                entity.setEnderPearlCooldown();
            }
        }
    }
}

