package com.pla.annoyingvillagers.init;

import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Category;
import net.minecraft.world.level.GameRules.Key;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD)
public class AnnoyingVillagersModGameRules {

    public static final Key<BooleanValue> MUSIC_BOX = GameRules.register("musicBox", Category.PLAYER, BooleanValue.create(false));
    public static final Key<BooleanValue> LEAVE_TO_SERVER = GameRules.register("leaveToServer", Category.PLAYER, BooleanValue.create(false));
    public static final Key<BooleanValue> BAN_OTHER_WORLD = GameRules.register("banOtherWorld", Category.MISC, BooleanValue.create(false));
    public static final Key<BooleanValue> KILL_PLAYER_RETURN = GameRules.register("killPlayerReturn", Category.PLAYER, BooleanValue.create(false));
    public static final Key<BooleanValue> PLAYER_SPAWN_CLEAR = GameRules.register("playerSpawnClear", Category.PLAYER, BooleanValue.create(false));
    public static final Key<BooleanValue> RADOM_SPAWN = GameRules.register("radomSpawn", Category.PLAYER, BooleanValue.create(true));
    public static final Key<BooleanValue> GAME_RESTART = GameRules.register("gameRestart", Category.MISC, BooleanValue.create(false));
    public static final Key<BooleanValue> MAKE_A_TEAM = GameRules.register("makeATeam", Category.PLAYER, BooleanValue.create(false));
}
