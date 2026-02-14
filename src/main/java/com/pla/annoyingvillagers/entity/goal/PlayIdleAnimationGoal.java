package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.JevEntity;
import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.clazz.IdleAnimation;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.*;

public class PlayIdleAnimationGoal extends Goal {
    private final Mob mob;
    private final int minDurationTicks;
    private int ticksLeft;

    private static final Map<IdleAnimation, List<String>> idleMessages = Map.of(
            IdleAnimation.LAY, Arrays.asList(
                    "Just laying down a bit, nothing to worry about.",
                    "Oof... I need to lay flat for a sec.",
                    "You ever just flop? Yeah.",
                    "Don't step on me, I'm laying here!",
                    "I'm not dead, just horizontal.",
                    "Gravity wins again.",
                    "This is my combat strategy: play dead.",
                    "Let me lay in peace!",
                    "No thoughts. Just laying.",
                    "I'm a pancake now.",
                    "The floor is oddly comfortable today.",
                    "Ugh, Mondays...",
                    "Back problems? Nah. Just vibes.",
                    "Flat and fabulous.",
                    "Laying low... literally.",
                    "Give me 5 minutes. Or 50.",
                    "I fell. On purpose.",
                    "This is fine.",
                    "Laying is productive, okay?",
                    "You wish you were this chill."
            ),
            IdleAnimation.SLEEP, Arrays.asList(
                    "Zzz... Wait what?",
                    "I was not sleeping, I was... recharging.",
                    "I dream of better loot.",
                    "Don't wake me unless it's urgent.",
                    "I take naps seriously.",
                    "This is strategic slumber.",
                    "Goodnight, cruel world.",
                    "I'm in stealth mode (asleep).",
                    "Being awake is overrated.",
                    "Wake me up when it's diamond o'clock.",
                    "You ever nap in full armor? No? Weak.",
                    "If I snore, it's intentional.",
                    "Yes, I sleep with one eye open.",
                    "I'm testing the bed physics.",
                    "Rest is resistance.",
                    "I'm on an AFK break.",
                    "I fought, I survived, I nap.",
                    "Don't judge me, I'm healing.",
                    "I look peaceful, don't I?",
                    "Sleeping beauty? That's me."
            ),
            IdleAnimation.SIT, Arrays.asList(
                    "Just sitting... contemplating my existence.",
                    "Taking a break from saving the world.",
                    "Is it weird to sit here? Nah.",
                    "Sit down and appreciate nature with me.",
                    "I claim this patch of grass.",
                    "My legs demanded this.",
                    "You stand, I sit. Balance.",
                    "Big brain thoughts happen in this pose.",
                    "This seat is taken. By me.",
                    "I'm not lazy, I'm energy efficient.",
                    "Sometimes you gotta sit to reflect.",
                    "Sitting builds character.",
                    "I'm too cool to stand up right now.",
                    "This is my vibe zone.",
                    "I'm one with the terrain.",
                    "Low center of gravity, maximum chill.",
                    "Sitting: 10/10 would recommend.",
                    "I squat with intent.",
                    "Oh I just sit here, don't kill me!",
                    "If you can't beat them, sit near them."
            ),
            IdleAnimation.FUN_SIT, Arrays.asList(
                    "Check out this fun pose!",
                    "I sit like no one's watching.",
                    "I'm not normal, I'm fun-sitting.",
                    "Vibing intensifies.",
                    "Bet you can't sit this fabulously.",
                    "Fun mode: activated.",
                    "Stylish and seated.",
                    "I'm not just sitting. I'm entertaining.",
                    "This pose is patented.",
                    "Join me, let's sit funny together!",
                    "Why sit boring when you can sit awesome?",
                    "Strike a pose: chairless edition.",
                    "This is my emote of power.",
                    "Look at me! I'm fabulous.",
                    "Do I look cool yet?",
                    "This is my signature move.",
                    "Chaotic neutral sitting.",
                    "The flowers envy my posture.",
                    "This is what peak performance looks like.",
                    "If sitting was an art, I'm Picasso."
            ),
            IdleAnimation.SLIGHT, Arrays.asList(
                    "Just a subtle motion...",
                    "That was barely an emote, but I meant it.",
                    "Did you see that? Blink and you miss it.",
                    "Minimal effort, maximum expression.",
                    "I call that a slight vibe.",
                    "Nothing major. Just flexin'.",
                    "Slight movements, strong emotions.",
                    "I twitched with style.",
                    "Little gestures matter too.",
                    "The art of the subtle flex.",
                    "Keeping it lowkey.",
                    "Just making sure you're paying attention.",
                    "Did I move? Maybe.",
                    "Smooth like butter.",
                    "Calm, but expressive.",
                    "That's my signal for 'hi'.",
                    "Small movement, big energy.",
                    "Blink and you'll miss this drip.",
                    "I emote in Morse code.",
                    "One frame of pure charisma."
            ),
            IdleAnimation.PUSH_UP, Arrays.asList(
                    "One! Two! Ugh, who needs cardio?",
                    "I do push-ups to impress the mobs.",
                    "Getting blocky gains.",
                    "Training to punch harder.",
                    "Strength comes from suffering.",
                    "No gym? No problem.",
                    "Push-up speedrun!",
                    "Working on my Minecraft muscles.",
                    "Bet you can't beat 20 reps!",
                    "This is my flex routine.",
                    "Push-ups make me immune to arrows.",
                    "Core strength? Check.",
                    "Mobility training in progress.",
                    "Fitness is survival.",
                    "Strength. Endurance. Blocks.",
                    "Sweat mode: enabled.",
                    "Even Steve does push-ups.",
                    "Just flexin' on creepers.",
                    "Training arc begins now.",
                    "Do I look stronger yet?"
            )
    );

    public PlayIdleAnimationGoal(Mob mob, int minDurationTicks) {
        this.mob = mob;
        this.minDurationTicks = minDurationTicks;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (mob.level().isClientSide) return false;
        if (mob instanceof JevEntity) return false;
        if (mob.tickCount <= 30) return false;
        if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return false;
        if (mob.isPassenger()) return false;
        if (mob.getTarget() != null) return false;
        if (mob.getNavigation().isInProgress()) return false;
        if (!mob.onGround()) return false;
        if (mob instanceof PlayerNpcEntity playerNpcEntity
                && (playerNpcEntity.isHealing()
                || playerNpcEntity.getPlayingIdleCooldown() != 0
                || playerNpcEntity.isStrolling())) {
            return false;
        }
        if (mob instanceof AVNpc avNpc
                && (avNpc.isHealing()
                || avNpc.getPlayingIdleCooldown() != 0
                || avNpc.isStrolling())) {
            return false;
        }
        LivingEntityPatch<?> patch = null;
        if (mob instanceof PlayerNpcEntity playerNpcEntity) {
            patch = playerNpcEntity.getLivingEntityPatch();
        }
        if (mob instanceof AVNpc avNpc) {
            patch = avNpc.getLivingEntityPatch();
        }
        if (patch == null) return false;
        AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(patch.getAnimator().getPlayerFor(null)).getRealAnimation();
        if (EpicfightUtil.isLongHitAnimation(dynamicAnimation, patch)) return false;
        return dynamicAnimation == Animations.EMPTY_ANIMATION;
    }

    @Override
    public boolean canContinueToUse() {
        if (mob.level().isClientSide) return false;
        if (mob instanceof JevEntity) return false;
        if (mob.tickCount <= 30) return false;
        if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return false;
        if (mob.isPassenger()) return false;
        if (!mob.onGround()) return false;
        if (mob.getTarget() != null) return false;
        if (mob.getNavigation().isInProgress()) return false;
        if (mob instanceof PlayerNpcEntity playerNpcEntity
                && (playerNpcEntity.isHealing()
                || playerNpcEntity.getPlayingIdleCooldown() != 0
                || playerNpcEntity.isStrolling())) {
            return false;
        }
        if (mob instanceof AVNpc avNpc
                && (avNpc.isHealing()
                || avNpc.getPlayingIdleCooldown() != 0
                || avNpc.isStrolling())) {
            return false;
        }
        LivingEntityPatch<?> patch = null;
        if (mob instanceof PlayerNpcEntity playerNpcEntity) {
            patch = playerNpcEntity.getLivingEntityPatch();
        }
        if (mob instanceof AVNpc avNpc) {
            patch = avNpc.getLivingEntityPatch();
        }
        if (patch == null) return false;
        AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(patch.getAnimator().getPlayerFor(null)).getRealAnimation();
        if (EpicfightUtil.isLongHitAnimation(dynamicAnimation, patch)) return false;
        return ticksLeft > 0;
    }

    @Override
    public void start() {
        if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return;
        ticksLeft = minDurationTicks;

        mob.getNavigation().stop();
        mob.setDeltaMovement(0, 0, 0);
        IdleAnimation choice = null;
        if (mob instanceof PlayerNpcEntity playerNpcEntity) {
            choice = playerNpcEntity.getIdleAnimationChoice();
        }
        if (mob instanceof AVNpc avNpc) {
            choice = avNpc.getIdleAnimationChoice();
        }
        if (choice == null) {
            choice = pickIdleAnimation();
            if (mob instanceof PlayerNpcEntity playerNpcEntity) {
                playerNpcEntity.setIdleAnimationChoice(choice);
            }
            if (mob instanceof AVNpc avNpc) {
                avNpc.setIdleAnimationChoice(choice);
            }
        }

        AssetAccessor<? extends StaticAnimation> anim = resolveAnimation(choice);
        if (mob instanceof PlayerNpcEntity playerNpcEntity) {
            playerNpcEntity.setIdleAnimation(anim);
        }
        if (mob instanceof AVNpc avNpc) {
            avNpc.setIdleAnimation(anim);
        }

        if (mob instanceof PlayerNpcEntity playerNpcEntity) {
            playerNpcEntity.setPlayingIdle(true);
        }
        if (mob instanceof AVNpc avNpc) {
            avNpc.setPlayingIdle(true);
        }

        IdleAnimation finalChoice = choice;
        new DelayedTask(30) {
            @Override
            public void run() {
                if (mob.getTarget() != null) return;
                if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return;
                playIdleAnimation(anim);
                tryBroadcastIdleMessage(finalChoice);
            }
        };
    }

    @Override
    public void tick() {
        if (mob.getTarget() != null || mob.getNavigation().isInProgress() || !mob.onGround()) {
            ticksLeft = 0;
            return;
        }

        if (!(mob.level() instanceof ServerLevel)) return;

        mob.getNavigation().stop();
        mob.setDeltaMovement(0, 0, 0);

        LivingEntityPatch<?> patch = null;
        if (mob instanceof PlayerNpcEntity playerNpcEntity) {
            patch = playerNpcEntity.getLivingEntityPatch();
        }
        if (mob instanceof AVNpc avNpc) {
            patch = avNpc.getLivingEntityPatch();
        }
        AssetAccessor<? extends StaticAnimation> idleAnimation = null;
        if (mob instanceof PlayerNpcEntity playerNpcEntity) {
            idleAnimation = playerNpcEntity.getIdleAnimation();
        }
        if (mob instanceof AVNpc avNpc) {
            idleAnimation = avNpc.getIdleAnimation();
        }
        if (patch != null && idleAnimation != null) {
            AssetAccessor<? extends StaticAnimation> staticAnimation =
                    Objects.requireNonNull(patch.getAnimator().getPlayerFor(null)).getRealAnimation();
            if (staticAnimation == idleAnimation) {
                // correct animation, do nothing
            } else {
                if (mob instanceof PlayerNpcEntity playerNpcEntity) {
                    playIdleAnimation(playerNpcEntity.getIdleAnimation());
                }
                if (mob instanceof AVNpc avNpc) {
                    playIdleAnimation(avNpc.getIdleAnimation());
                }
            }
        }
        ticksLeft--;
    }

    @Override
    public void stop() {
        if (mob instanceof AVNpc avNpc) {
            avNpc.clearIdleAnimationState();
            LivingEntityPatch<?> patch = avNpc.getLivingEntityPatch();
            if (patch != null) patch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
            avNpc.setPlayingIdle(false);
            avNpc.setPlayingIdleCooldown(new Random().nextInt(400, 1200));
        } else if (mob instanceof PlayerNpcEntity playerNpcEntity) {
            playerNpcEntity.clearIdleAnimationState();
            LivingEntityPatch<?> patch = playerNpcEntity.getLivingEntityPatch();
            if (patch != null) patch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
            playerNpcEntity.setPlayingIdle(false);
            playerNpcEntity.setPlayingIdleCooldown(new Random().nextInt(400, 1200));
        }
    }

    private void playIdleAnimation(AssetAccessor<? extends StaticAnimation> anim) {
        if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return;
        LivingEntityPatch<?> patch = null;
        if (mob instanceof PlayerNpcEntity playerNpcEntity) {
            patch = playerNpcEntity.getLivingEntityPatch();
        }
        if (mob instanceof AVNpc avNpc) {
            patch = avNpc.getLivingEntityPatch();
        }
        if (patch != null) {
            patch.playAnimationSynchronized(anim, 0.0F);
        }
    }

    private IdleAnimation pickIdleAnimation() {
        IdleAnimation[] all = IdleAnimation.values();
        return all[mob.getRandom().nextInt(all.length)];
    }

    private AssetAccessor<? extends StaticAnimation> resolveAnimation(IdleAnimation idle) {
        return switch (idle) {
            case PUSH_UP -> AVAnimations.PUSH_UP_IDLE;
            case LAY -> AVAnimations.LAY_IDLE;
            case SLEEP -> AVAnimations.DEATH_IDLE;
            case SIT -> AVAnimations.SIT_IDLE;
            case FUN_SIT -> AVAnimations.FUNNY_IDLE;
            case SLIGHT -> AVAnimations.SLIGHT_IDLE;
        };
    }

    private void tryBroadcastIdleMessage(IdleAnimation idle) {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;
        if (!AnnoyingVillagersConfig.TURN_ON_NPC_CHAT.get()) return;
        if (mob instanceof PlayerNpcEntity playerNpcEntity && playerNpcEntity.isIdleMessageBroadcast()) {
            return;
        }
        if (mob instanceof AVNpc avNpc && avNpc.isIdleMessageBroadcast()) {
            return;
        }
        String msg = "<" + mob.getDisplayName().getString() + "> " + idleMessages
                .getOrDefault(idle, List.of("..."))
                .get(new Random().nextInt(idleMessages.get(idle).size()));
        serverLevel.getServer().getPlayerList().broadcastSystemMessage(Component.literal(msg), false);

        if (mob instanceof PlayerNpcEntity playerNpcEntity) {
            playerNpcEntity.setIdleMessageBroadcast(true);
        }
        if (mob instanceof AVNpc avNpc) {
            avNpc.setIdleMessageBroadcast(true);
        }
    }
}

