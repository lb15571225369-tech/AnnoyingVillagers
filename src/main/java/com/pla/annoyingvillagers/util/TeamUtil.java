package com.pla.annoyingvillagers.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;

public class TeamUtil {
    public static void addOrJoinTeam(Entity entity, String teamName) {
        if (!(entity.level() instanceof ServerLevel serverLevel)) return;
        Scoreboard scoreboard = serverLevel.getScoreboard();
        PlayerTeam team = scoreboard.getPlayerTeam(teamName);
        if (team == null) {
            team = scoreboard.addPlayerTeam(teamName);
            team.setAllowFriendlyFire(false);
        } else {
            team.setAllowFriendlyFire(false);
        }

        String entry = entity.getScoreboardName();
        scoreboard.addPlayerToTeam(entry, team);
    }
}
